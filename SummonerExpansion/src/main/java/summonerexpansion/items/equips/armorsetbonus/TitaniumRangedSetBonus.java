package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.GameLog;
import necesse.engine.GlobalData;
import necesse.engine.localization.Localization;
import necesse.engine.network.client.Client;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketMobAttack;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.arrowItem.ArrowItem;
import necesse.inventory.item.toolItem.ToolItem;
import necesse.inventory.item.toolItem.projectileToolItem.bowProjectileToolItem.BowProjectileToolItem;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;
import summonerexpansion.codes.packets.PacketTitaniumBowAimUpdate;
import summonerexpansion.mobs.summonminions.setminions.SetTitaniumRangedMinion;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class TitaniumRangedSetBonus extends TitaniumSetBonus
{
    public static ArrayList<Class<? extends ToolItem>> validSummonWeaponClasses = new ArrayList<>();
    public FloatUpgradeValue dashCooldown = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1, 0.20F).setUpgradedValue(10, 0.30F);
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(10, 4);

    public TitaniumRangedSetBonus()
    {
        validSummonWeaponClasses.add(BowProjectileToolItem.class);
        validSummonWeaponClasses.add(GunProjectileToolItem.class);
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.DASH_COOLDOWN, dashCooldown.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(buff.getUpgradeTier()));
    }

    public void clientTick(ActiveBuff buff)
    {
        super.clientTick(buff);
        if (buff.owner.isClient())
        {
            if (buff.owner.isPlayer)
            {
                PlayerMob player = (PlayerMob)buff.owner;
                Client client = player.getClient();
                if (client.getSlot() == player.getUniqueID())
                {
                    GameCamera camera = GlobalData.getCurrentState().getCamera();
                    if (camera == null)
                    {
                        return;
                    }
                    int newMouseLevelPosX = camera.getMouseLevelPosX();
                    int newMouseLevelPosY = camera.getMouseLevelPosY();
                    Point oldMouseLevelPos = getMousePos(buff);
                    if (newMouseLevelPosX != oldMouseLevelPos.x || newMouseLevelPosY != oldMouseLevelPos.y)
                    {
                        updateMousePos(buff, newMouseLevelPosX, newMouseLevelPosY);
                        client.network.sendPacket(new PacketTitaniumBowAimUpdate(player, newMouseLevelPosX, newMouseLevelPosY));
                    }
                }
            }
        }
    }

    public void serverTick(ActiveBuff buff)
    {
        if (buff.owner.isItemAttacker)
        {
            Level level = buff.owner.getLevel();
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            float count = attackerMob.serverFollowersManager.getFollowerCount("summonedtitaniumbow");
            FlyingAttackingFollowingMob mob = (FlyingAttackingFollowingMob) MobRegistry.getMob("settitaniumrangedminion", level);
            if (count <= 0.0F)
            {
                attackerMob.serverFollowersManager.addFollower("summonedtitaniumbow", mob, FollowPosition.WALK_CLOSE, "summonedtitaniumminionbuff", 1.0F, 1, null, false);
                mob.setRemoveWhenNotInInventory(ItemRegistry.getItem("titaniumrangedhelmet"), CheckSlotType.HELMET);
                Point spawnPoint = new Point(attackerMob.getX() + GameRandom.globalRandom.getIntBetween(-64, 64), attackerMob.getY() + GameRandom.globalRandom.getIntBetween(-64, 64));
                level.entityManager.addMob(mob, (float)spawnPoint.x, (float)spawnPoint.y);
                buff.getGndData().setInt("titaniumBowUniqueID", mob.getUniqueID());
            }
        }
    }

    public static void updateMousePos(ActiveBuff activeBuff, int levelX, int levelY)
    {
        GNDItemMap gndData = activeBuff.getGndData();
        gndData.setInt("mouseLevelX", levelX);
        gndData.setInt("mouseLevelY", levelY);
    }

    public static Point getMousePos(ActiveBuff activeBuff)
    {
        GNDItemMap gndData = activeBuff.getGndData();
        return new Point(gndData.getInt("mouseLevelX"), gndData.getInt("mouseLevelY"));
    }

    public void onItemAttacked(ActiveBuff buff, int targetX, int targetY, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, GNDItemMap attackMap)
    {
        super.onItemAttacked(buff, targetX, targetY, attackerMob, attackHeight, item, slot, animAttack, attackMap);
        if (!attackerMob.isClient())
        {
            if (isRangedItem(item))
            {
                int TitaniumBowUniqueID = buff.getGndData().getInt("titaniumBowUniqueID");
                Mob followingMob = attackerMob.getLevel().entityManager.mobs.get(TitaniumBowUniqueID, false);
                boolean isMoving = buff.owner.getCurrentSpeed() == 0F;
                if (TitaniumBowUniqueID != 0 && followingMob != null && isMoving)
                {
                    SetTitaniumRangedMinion bowMinion = (SetTitaniumRangedMinion)followingMob;
                    GameDamage attackDamage;
                    if (item.item.isToolItem())
                    {
                        attackDamage = ((ToolItem)item.item).getAttackDamage(item).modFinalMultiplier(0.5F);
                        int arrowID = attackMap.getShortUnsigned("arrowID", 65535);
                        if (arrowID != 65535)
                        {
                            Item arrow = ItemRegistry.getItem(arrowID);
                            if (arrow != null && arrow.type == Item.Type.ARROW)
                            {
                                attackDamage = ((ArrowItem)arrow).modDamage(attackDamage);
                            }
                        }
                    }
                    else
                    {
                        attackDamage = new GameDamage(DamageTypeRegistry.SUMMON, 20.0F);
                        GameLog.warn.println(item.item.getStringID() + " is not a toolitem");
                    }
                    spawnTitaniumArrow(bowMinion, targetX, targetY, null, attackDamage);
                }
            }
        }
    }

    private void spawnTitaniumArrow(SetTitaniumRangedMinion bowMinion, int targetX, int targetY, Mob target, GameDamage damage)
    {
        Projectile projectile = ProjectileRegistry.getProjectile("titaniumarrowproj", bowMinion.getLevel(), bowMinion.x, bowMinion.y + (float)((int)bowMinion.getDesiredHeight()) + 20.0F, (float)targetX, (float)targetY, 200.0F, 608, damage, bowMinion);
        if (target != null)
        {
            projectile.setTargetPrediction(target, -20.0F);
        }
        projectile.moveDist(20.0F);
        bowMinion.getLevel().entityManager.projectiles.add(projectile);
        bowMinion.showAttack(targetX, targetY, true);
        if (bowMinion.isServer())
        {
            bowMinion.getServer().network.sendToClientsWithEntity(new PacketMobAttack(bowMinion, targetX, targetY, true), bowMinion);
        }
    }

    private boolean isRangedItem(InventoryItem item)
    {
        if (item != null && item.item instanceof ToolItem)
        {
            return ((ToolItem) item.item).getDamageType(item) == DamageTypeRegistry.SUMMON && validSummonWeaponClasses.stream().anyMatch((clazz) -> clazz.isAssignableFrom(item.item.getClass()));
        }
        else
        {
            return false;
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "titaniumsettip"));
        tooltips.add(Localization.translate("itemtooltip", "titaniumrangedtip"));
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}