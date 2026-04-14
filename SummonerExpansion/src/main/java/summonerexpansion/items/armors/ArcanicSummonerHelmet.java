package summonerexpansion.items.armors;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.MaskShaderOptions;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.DrawOptionsList;
import necesse.gfx.drawOptions.texture.TextureDrawOptionsEnd;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.IncursionArmorSetsLootTable;
import necesse.inventory.lootTable.presets.IncursionHeadArmorLootTable;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import java.awt.*;
import java.awt.Color;

public class ArcanicSummonerHelmet extends SetHelmetArmorItem
{
    private GameTexture lightTexture;
    public FloatUpgradeValue summonRange = (new FloatUpgradeValue()).setBaseValue(0.10F).setUpgradedValue(1F, 0.20F).setUpgradedValue(10F, 0.30F);
    public FloatUpgradeValue summonCritDamage = (new FloatUpgradeValue()).setBaseValue(0.05F).setUpgradedValue(1F, 0.10F).setUpgradedValue(10F, 0.30F);
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(10, 3);

    public ArcanicSummonerHelmet(int enchantCost, Item.Rarity rarityTier)
    {
        super(20, DamageTypeRegistry.SUMMON, enchantCost, IncursionHeadArmorLootTable.incursionHeadArmor, IncursionArmorSetsLootTable.incursionArmorSets, rarityTier, "arcanicsummonhelmet", "arcanicchestplate", "arcanicboots", "arcanicsummonsetbonus");
        hairDrawOptions = HairDrawMode.NO_HEAD;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.SUMMON_CRIT_DAMAGE, summonCritDamage.getValue(getUpgradeTier(item))), new ModifierValue<>(BuffModifiers.SUMMONS_TARGET_RANGE, summonRange.getValue(getUpgradeTier(item))),  new ModifierValue<>(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(getUpgradeTier(item))));
    }

    protected void loadArmorTexture()
    {
        super.loadArmorTexture();
        lightTexture = GameTexture.fromFile("player/armor/" + textureName + "_light");
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/armors/" + getStringID());
    }
}