package summonerexpansion.items.equips.summonweapons;

import necesse.engine.GameLog;
import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.itemAttacker.*;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.DuskMoonDiscFollowingMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import necesse.inventory.lootTable.lootItem.OneOfLootItems;
import necesse.level.maps.Level;
import summonerexpansion.mobs.summonminions.*;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.function.BiConsumer;

public class YinYangNinjastar extends GunProjectileToolItem
{
    public static LinkedHashSet<String> NINJA_AMMO_TYPES = new LinkedHashSet<>(Collections.singletonList("ninjastar"));
    public int ninjaStack;
    public int alterType = 0;

    public YinYangNinjastar(int enchantCost, OneOfLootItems lootTableCategory)
    {
        super(NINJA_AMMO_TYPES, enchantCost, lootTableCategory);
        rarity = Rarity.UNCOMMON;
        damageType = DamageTypeRegistry.SUMMON;
        attackAnimTime.setBaseValue(500).setUpgradedValue(1, 300);
        attackDamage.setBaseValue(40.0F).setUpgradedValue(1, 100F);
        setItemCategory("equipment", "weapons", "summonweapons");
        setItemCategory(ItemCategory.equipmentManager, "weapons", "summonweapons");
        setItemCategory(ItemCategory.craftingManager, "equipment", "weapons", "summonweapons");
        keyWords.add("summon");
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        int bulletID = mapContent.getShortUnsigned("bulletID", 65535);
        if (bulletID != 65535)
        {
            Item ninjaObjectItem = ItemRegistry.getItem(bulletID);
            if (ninjaObjectItem != null && ninjaObjectItem.type == Type.TOOL)
            {
                GameRandom random = new GameRandom(seed + 5);
                float ammoConsumeChance = getAmmoConsumeChance(attackerMob, item);
                boolean consumeAmmo = ammoConsumeChance >= 1.0F || ammoConsumeChance > 0.0F && random.getChance(ammoConsumeChance);
                boolean shouldFire;

                if (!consumeAmmo)
                {
                    shouldFire = true;
                }
                else if (attackerMob instanceof AmmoUserMob)
                {
                    AmmoConsumed consumed = ((AmmoUserMob)attackerMob).removeAmmo(ninjaObjectItem, 1, "bulletammo");
                    shouldFire = consumed.amount >= 1;
                }
                else
                {
                    shouldFire = true;
                }

                if (shouldFire)
                {
                    if (++ninjaStack >= 2)
                    {
                        if (alterType > 0)
                        {
                            YangNinjaMinion mob = new YangNinjaMinion();
                            Point2D.Float dir = GameMath.normalize((float)x - attackerMob.x, (float)y - attackerMob.y + (float)attackHeight);
                            attackerMob.serverFollowersManager.addFollower("ninjaminion", mob, FollowPosition.PYRAMID, "summonedmob", 1.0F, 6, null, false);
                            mob.updateDamage(getAttackDamage(item));
                            mob.setEnchantment(getEnchantment(item));
                            mob.dx = dir.x * 300.0F;
                            mob.dy = dir.y * 300.0F;
                            attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x, attackerMob.y);
                            alterType = 0;
                            ninjaStack = 0;
                        }
                        else
                        {
                            YinNinjaMinion mob = new YinNinjaMinion();
                            Point2D.Float dir = GameMath.normalize((float)x - attackerMob.x, (float)y - attackerMob.y + (float)attackHeight);
                            attackerMob.serverFollowersManager.addFollower("ninjaminion", mob, FollowPosition.PYRAMID, "summonedmob", 1.0F, 6, null, false);
                            mob.updateDamage(getAttackDamage(item));
                            mob.setEnchantment(getEnchantment(item));
                            mob.dx = dir.x * 300.0F;
                            mob.dy = dir.y * 300.0F;
                            attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x, attackerMob.y);
                            alterType++;
                            ninjaStack = 0;
                        }
                    }
                }
            }
            else
            {
                GameLog.warn.println(attackerMob.getDisplayName() + " tried to use item " + (ninjaObjectItem == null ? bulletID : ninjaObjectItem.getStringID()) + " as ammo ninjaObjectItem.");
            }
        }
        return item;
    }

    protected SoundSettings getAttackSound()
    {
        return null;
    }

    public GameSprite getAttackSprite(InventoryItem item, PlayerMob player)
    {
        return null;
    }

    public boolean animDrawBehindHand(InventoryItem item) {
        return false;
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "yinyangstartip"));
        return tooltips;
    }
}