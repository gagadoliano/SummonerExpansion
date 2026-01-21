package summonerexpansion.items.equips.allweapons.basesummon;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.EnchantmentRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.ToolItemSummonedMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameFont.FontManager;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.enchants.Enchantable;
import necesse.inventory.enchants.ItemEnchantment;
import necesse.inventory.enchants.ToolItemEnchantment;
import necesse.inventory.item.*;
import necesse.inventory.item.toolItem.ToolItem;
import necesse.inventory.lootTable.lootItem.OneOfLootItems;
import necesse.level.maps.Level;

import java.awt.geom.Point2D;
import java.util.*;

public class BaseSupportWeapon extends ToolItem
{
    public boolean singleUse = false;
    public String mobStringID;
    public String summonType;
    public FollowPosition followPosition;
    public float summonSpaceTaken;
    public boolean drawMaxSummons = true;

    public BaseSupportWeapon(String mobStringID, FollowPosition followPosition, float summonSpaceTaken, int enchantCost, OneOfLootItems lootTableCategory)
    {
        super(enchantCost, lootTableCategory);
        this.mobStringID = mobStringID;
        this.summonType = "summonedmob";
        this.followPosition = followPosition;
        this.summonSpaceTaken = summonSpaceTaken;
        this.damageType = DamageTypeRegistry.SUMMON;
        this.attackAnimTime.setBaseValue(400);
        this.setItemCategory("equipment", "weapons", "summonweapons");
        this.setItemCategory(ItemCategory.equipmentManager, "weapons", "summonweapons");
        this.setItemCategory(ItemCategory.craftingManager, "equipment", "weapons", "summonweapons");
        this.keyWords.add("summon");
        this.attackXOffset = 4;
        this.attackYOffset = 4;
        this.tierOneEssencesUpgradeRequirement = "cryoessence";
        this.tierTwoEssencesUpgradeRequirement = "bloodessence";
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "supportweapontip"));
        GameTooltips spaceTaken = this.getSpaceTakenTooltip(item, perspective);
        if (spaceTaken != null)
        {
            tooltips.add(spaceTaken);
        }
        if (this.drawMaxSummons)
        {
            int maxSummons = this.getMaxSummons(item, perspective);
            if (maxSummons != 1)
            {
                tooltips.add(Localization.translate("itemtooltip", "summonslots", "count", maxSummons));
            }
        }
        if (this.singleUse)
        {
            tooltips.add(Localization.translate("itemtooltip", "singleuse"));
        }
        return tooltips;
    }

    public void addStatTooltips(ItemStatTipList list, InventoryItem currentItem, InventoryItem lastItem, ItemAttackerMob perspective, boolean forceAdd)
    {

    }

    public GameTooltips getSpaceTakenTooltip(InventoryItem item, PlayerMob perspective)
    {
        float spaceTaken = this.getSummonSpaceTaken(item, perspective);
        return spaceTaken != 1.0F ? new StringTooltips(Localization.translate("itemtooltip", "summonuseslots", "count", spaceTaken)) : null;
    }

    public int getMaxSummons(InventoryItem item, ItemAttackerMob attackerMob)
    {
        return attackerMob == null ? BuffModifiers.MAX_SUMMONS.defaultBuffManagerValue : attackerMob.buffManager.getModifier(BuffModifiers.MAX_SUMMONS);
    }

    public float getSummonSpaceTaken(InventoryItem item, ItemAttackerMob attackerMob) {
        return this.summonSpaceTaken;
    }

    public Set<Integer> getValidEnchantmentIDs(InventoryItem item)
    {
        return EnchantmentRegistry.summonItemEnchantments;
    }

    public ToolItemEnchantment getRandomEnchantment(GameRandom random, InventoryItem item)
    {
        return Enchantable.getRandomEnchantment(random, EnchantmentRegistry.summonItemEnchantments, this.getEnchantmentID(item), ToolItemEnchantment.class);
    }

    public boolean isValidEnchantment(InventoryItem item, ItemEnchantment enchantment)
    {
        return EnchantmentRegistry.summonItemEnchantments.contains(enchantment.getID());
    }

    protected void beforeSpawn(ToolItemSummonedMob mob, InventoryItem item, ItemAttackerMob attackerMob) {
    }

    public Point2D.Float findSpawnLocation(ToolItemSummonedMob mob, Level level, int x, int y, int attackHeight, ItemAttackerMob attackerMob, InventoryItem item)
    {
        return findSpawnLocation((Mob)mob, level, attackerMob.x, attackerMob.y);
    }

    public static Point2D.Float findSpawnLocation(Mob mob, Level level, float centerX, float centerY)
    {
        ArrayList<Point2D.Float> possibleSpawns = new ArrayList<>();
        for(int cX = -1; cX <= 1; ++cX)
        {
            for(int cY = -1; cY <= 1; ++cY)
            {
                if (cX != 0 || cY != 0)
                {
                    float posX = centerX + (float)(cX * 32);
                    float posY = centerY + (float)(cY * 32);
                    if (!mob.collidesWith(level, (int)posX, (int)posY))
                    {
                        possibleSpawns.add(new Point2D.Float(posX, posY));
                    }
                }
            }
        }
        if (!possibleSpawns.isEmpty())
        {
            return possibleSpawns.get(GameRandom.globalRandom.nextInt(possibleSpawns.size()));
        }
        else
        {
            return new Point2D.Float(centerX, centerY);
        }
    }

    protected SoundSettings getAttackSound() {
        return (new SoundSettings(GameResources.magicbolt4)).volume(0.3F);
    }

    public int getItemAttackerAttackRange(ItemAttackerMob mob, InventoryItem item) {
        return 320;
    }

    public int getItemAttackerRunAwayDistance(ItemAttackerMob attackerMob, InventoryItem item) {
        return 160;
    }

    public float getItemAttackerWeaponValue(ItemAttackerMob mob, InventoryItem item)
    {
        return mob != null && !mob.isPlayer && this.getSummonSpaceTaken(item, mob) > (float)this.getMaxSummons(item, mob) ? 0.0F : super.getItemAttackerWeaponValue(mob, item);
    }

    public String canAttack(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item)
    {
        if (!attackerMob.isPlayer && attackerMob.isServer())
        {
            float spaceTaken = this.getSummonSpaceTaken(item, attackerMob);
            float spaceLeft = (float)this.getMaxSummons(item, attackerMob) - attackerMob.serverFollowersManager.getFollowerCount(this.summonType);
            if (spaceTaken > spaceLeft || spaceTaken > (float)this.getMaxSummons(item, attackerMob))
            {
                return "";
            }
        }

        return super.canAttack(level, x, y, attackerMob, item);
    }

    public String superCanAttack(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item)
    {
        return super.canAttack(level, x, y, attackerMob, item);
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        if (level.isServer())
        {
            this.runServerSummon(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);
        }
        if (this.singleUse)
        {
            item.setAmount(item.getAmount() - 1);
        }
        return item;
    }

    public void runServerSummon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        ToolItemSummonedMob mob = (ToolItemSummonedMob)MobRegistry.getMob(this.mobStringID, level);
        this.summonServerMob(attackerMob, mob, x, y, attackHeight, item);
    }

    public void summonServerMob(ItemAttackerMob attackerMob, ToolItemSummonedMob mob, int x, int y, int attackHeight, InventoryItem item)
    {
        Mob castedMob = (Mob)mob;
        attackerMob.serverFollowersManager.addFollower(this.summonType, castedMob, this.followPosition, "summonedmob", this.getSummonSpaceTaken(item, attackerMob), (p) -> this.getMaxSummons(item, p), null, false);
        Point2D.Float spawnPoint = this.findSpawnLocation(mob, castedMob.getLevel(), x, y, attackHeight, attackerMob, item);
        mob.updateDamage(this.getAttackDamage(item));
        mob.setEnchantment(this.getEnchantment(item));
        if (!attackerMob.isPlayer)
        {
            mob.setRemoveWhenNotInInventory(item.item, CheckSlotType.WEAPON);
        }
        this.beforeSpawn(mob, item, attackerMob);
        castedMob.getLevel().entityManager.addMob(castedMob, spawnPoint.x, spawnPoint.y);
    }

    public void draw(InventoryItem item, PlayerMob perspective, int x, int y, boolean inInventory)
    {
        super.draw(item, perspective, x, y, inInventory);
        if (this.drawMaxSummons && inInventory)
        {
            int maxSummons = this.getMaxSummons(item, perspective);
            if (maxSummons > 999)
            {
                maxSummons = 999;
            }

            if (maxSummons != 1)
            {
                String amountString = String.valueOf(maxSummons);
                int width = FontManager.bit.getWidthCeil(amountString, tipFontOptions);
                FontManager.bit.drawString((float)(x + 28 - width), (float)(y + 16), amountString, tipFontOptions);
            }
        }
    }

    public String getTranslatedTypeName() {
        return Localization.translate("item", "supportweapon");
    }
}