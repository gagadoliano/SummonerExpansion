package summonerexpansion.summonweapons;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.attackHandler.GreatswordChargeLevel;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.swordToolItem.greatswordToolItem.GreatswordToolItem;
import necesse.inventory.lootTable.presets.SummonWeaponsLootTable;
import necesse.level.maps.Level;
import summonerexpansion.summonothers.HorrorScytheHandler;

import java.awt.*;

public class HorrorScythe extends GreatswordToolItem
{
    public HorrorScythe()
    {
        super(800, SummonWeaponsLootTable.summonWeapons, HorrorScythe.getChargeLevels(600, 800, 1200));
        rarity = Rarity.EPIC;
        damageType = DamageTypeRegistry.SUMMON;
        attackDamage.setBaseValue(60.0F).setUpgradedValue(1, 65.0F);
        resilienceGain.setBaseValue(0F).setUpgradedValue(1, 1F).setUpgradedValue(10, 1.2F);
        attackRange.setBaseValue(80);
        knockback.setBaseValue(120);
        attackXOffset = 12;
        attackYOffset = 14;
        canBeUsedForRaids = true;
    }

    public static GreatswordChargeLevel[] getChargeLevels(int level1Time, int level2Time, int level3Time)
    {
        return new GreatswordChargeLevel[]{new GreatswordChargeLevel(level1Time, 1.0F, new Color(10, 10, 10, 255)), new GreatswordChargeLevel(level2Time, 1.5F, new Color(30, 20, 20)), new GreatswordChargeLevel(level3Time, 2.0F, new Color(50, 20, 20))};
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        attackerMob.startAttackHandler(new HorrorScytheHandler(attackerMob, slot, item, this, seed, x, y, this.chargeLevels));
        return item;
    }

    @Override
    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "horrorscythetip"));
        return tooltips;
    }
}