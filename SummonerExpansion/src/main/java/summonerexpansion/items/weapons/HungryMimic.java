package summonerexpansion.items.weapons;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;

import java.awt.*;

public class HungryMimic extends SummonToolItem
{
    public static float finalDamageModifier = 0.25F;

    public HungryMimic(int enchantCost, Item.Rarity rarityTier)
    {
        super("hungrymimicminion", FollowPosition.WALK_CLOSE, 1F, enchantCost, SummonWeaponsLootTable.summonWeapons);
        rarity = rarityTier;
        attackDamage.setBaseValue(15.0F).setUpgradedValue(1, 30.0F);
        canBeUsedForRaids = true;
    }

    public GameDamage getAttackDamage(InventoryItem item)
    {
        return super.getAttackDamage(item).modFinalMultiplier(finalDamageModifier);
    }

    public int getAttackDamageValue(InventoryItem item, Attacker attacker)
    {
        GameDamage damage = this.getAttackDamage(item);
        if (attacker != null)
        {
            Mob attackOwner = attacker.getAttackOwner();
            if (attackOwner != null)
            {
                damage = damage.modDamage((float)attackOwner.buffManager.getModifier(BuffModifiers.MAX_SUMMONS));
            }
        }
        return Math.round(damage.getBuffedDamage(attacker) / (1.0F / finalDamageModifier));
    }

    public void setDrawAttackRotation(InventoryItem item, ItemAttackDrawOptions drawOptions, float attackDirX, float attackDirY, float attackProgress)
    {
        drawOptions.pointRotation(attackDirX, attackDirY);
    }

    public float getSummonSpaceTaken(InventoryItem item, ItemAttackerMob attackerMob)
    {
        return attackerMob != null ? (float) attackerMob.buffManager.getModifier(BuffModifiers.MAX_SUMMONS) : super.getSummonSpaceTaken(item, attackerMob);
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/weapons/" + getStringID());
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "hungrymimictip"));
        return tooltips;
    }
}