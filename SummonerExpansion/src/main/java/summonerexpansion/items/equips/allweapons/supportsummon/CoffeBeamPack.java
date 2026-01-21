package summonerexpansion.items.equips.allweapons.supportsummon;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.ItemStatTipList;
import necesse.inventory.item.toolItem.projectileToolItem.throwToolItem.SettlerIgnoredThrowToolItem;
import necesse.level.maps.Level;
import summonerexpansion.mobs.summonminions.CoffeBeamSentry;

import java.awt.geom.Point2D;

public class CoffeBeamPack extends SettlerIgnoredThrowToolItem
{
    public CoffeBeamPack(Item.Rarity rarityTier)
    {
        rarity = rarityTier;
        damageType = DamageTypeRegistry.SUMMON;
        attackAnimTime.setBaseValue(300);
        attackDamage.setBaseValue(0);
        stackSize = 250;
        setItemCategory("equipment", "weapons", "summonweapons");
        setItemCategory(ItemCategory.equipmentManager, "weapons", "summonweapons");
        setItemCategory(ItemCategory.craftingManager, "equipment", "weapons", "summonweapons");
        keyWords.add("summon");
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
        InventoryItem out = super.onAttack(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);
        if (animAttack == 0 && attackerMob.isServer())
        {
            CoffeBeamSentry mob = new CoffeBeamSentry();
            Point2D.Float dir = GameMath.normalize((float)x - attackerMob.x, (float)y - attackerMob.y + (float)attackHeight);
            attackerMob.serverFollowersManager.addFollower("summonedsupportplantsentry", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, 1, null, false);
            mob.updateDamage(getAttackDamage(item));
            mob.setEnchantment(getEnchantment(item));
            mob.dx = dir.x * 300.0F;
            mob.dy = dir.y * 300.0F;
            attackerMob.getLevel().entityManager.addMob(mob, attackerMob.x + dir.x, attackerMob.y + dir.y);
            item.setAmount(item.getAmount() - 1);
        }
        return item;
    }

    public void addStatTooltips(ItemStatTipList list, InventoryItem currentItem, InventoryItem lastItem, ItemAttackerMob perspective, boolean forceAdd)
    {

    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "supportsentrytip"));
        tooltips.add(Localization.translate("itemtooltip", "supportweapontip"));
        tooltips.add(Localization.translate("itemtooltip", "coffebeampacktip"));
        return tooltips;
    }
}