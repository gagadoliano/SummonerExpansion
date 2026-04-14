package summonerexpansion.items.armors;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.MaskShaderOptions;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.DrawOptionsList;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.lootTable.presets.ArmorSetsLootTable;
import necesse.inventory.lootTable.presets.HeadArmorLootTable;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;

public class ShadowHorrorHood extends SetHelmetArmorItem
{
    private GameTexture lightTexture;
    public FloatUpgradeValue summonCritChance = (new FloatUpgradeValue()).setBaseValue(0.05F).setUpgradedValue(1F, 0.07F).setUpgradedValue(10F, 0.15F);

    public ShadowHorrorHood(int enchantCost, Item.Rarity rarityTier)
    {
        super(20, DamageTypeRegistry.SUMMON, enchantCost, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, rarityTier, "shadowhorrorhood", "shadowhorrormantle", "shadowhorrorboots", "shadowhorrorsetbonus");
        hairDrawOptions = HairDrawMode.NO_HEAD;
        tierTwoEssencesUpgradeRequirement = "purehorror";
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue<>(BuffModifiers.SUMMON_CRIT_CHANCE, summonCritChance.getValue(getUpgradeTier(item))));
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