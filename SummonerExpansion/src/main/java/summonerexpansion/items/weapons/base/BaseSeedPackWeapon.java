package summonerexpansion.items.weapons.base;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.ToolItemSummonedMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.SettlerIgnoredThrowToolItem;
import necesse.level.maps.Level;

import java.awt.geom.Point2D;

public class BaseSeedPackWeapon extends SettlerIgnoredThrowToolItem
{
    public String weaponTooltip;
    public String minionType;
    public String minion;
    public Integer minionMax;

    public BaseSeedPackWeapon(float damage, int attackSpeed, int minionMax, String minion, String minionType, String weaponTooltip, Item.Rarity rarityTier)
    {
        rarity = rarityTier;
        damageType = DamageTypeRegistry.SUMMON;
        attackAnimTime.setBaseValue(attackSpeed);
        attackDamage.setBaseValue(damage);
        stackSize = 250;
        setItemCategory("equipment", "weapons", "summonweapons");
        setItemCategory(ItemCategory.equipmentManager, "weapons", "summonweapons");
        setItemCategory(ItemCategory.craftingManager, "equipment", "weapons", "summonweapons");
        keyWords.add("summon");
        canBeUsedForRaids = false;
        this.minion = minion;
        this.minionMax = minionMax;
        this.minionType = minionType;
        this.weaponTooltip = weaponTooltip;
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/weapons/" + getStringID());
    }

    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent)
    {
        if (level.isClient())
        {
            SoundManager.playSound(GameResources.magicbolt4, SoundEffect.effect(attackerMob).volume(0.3F).pitch(GameRandom.globalRandom.getFloatBetween(1.6F, 1.8F)));
        }
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        if (level.isServer())
        {
            this.runServerSummon(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);
            item.setAmount(item.getAmount() - 1);
        }
        return item;
    }

    public void runServerSummon(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        ToolItemSummonedMob mob = (ToolItemSummonedMob)MobRegistry.getMob(this.minion, level);
        this.summonServerMob(attackerMob, mob, x, y, attackHeight, item);
    }

    public void summonServerMob(ItemAttackerMob attackerMob, ToolItemSummonedMob mob, int x, int y, int attackHeight, InventoryItem item)
    {
        Mob castedMob = (Mob)mob;
        Point2D.Float dir = GameMath.normalize((float)x - attackerMob.x, (float)y - attackerMob.y + (float)attackHeight);
        attackerMob.serverFollowersManager.addFollower(this.minionType, castedMob, FollowPosition.WALK_CLOSE, "summonedmob", 1f, (p) -> minionMax, null, false);
        mob.updateDamage(this.getAttackDamage(item));
        mob.setEnchantment(this.getEnchantment(item));
        ((Mob) mob).dx = dir.x * 300.0F;
        ((Mob) mob).dy = dir.y * 300.0F;
        castedMob.getLevel().entityManager.addMob(castedMob, attackerMob.x + dir.x, attackerMob.y + dir.y);
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "sentrytip"));
        tooltips.add(Localization.translate("itemtooltip", weaponTooltip));
        return tooltips;
    }
}