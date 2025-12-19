package summonerexpansion.items.equips.armor;

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
    public FloatUpgradeValue critSummon = (new FloatUpgradeValue()).setBaseValue(0.05F).setUpgradedValue(1F, 0.07F).setUpgradedValue(10F, 0.15F);

    public ShadowHorrorHood(int enchantCost, Item.Rarity rarityTier)
    {
        super(20, DamageTypeRegistry.SUMMON, enchantCost, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, rarityTier, "shadowhorrorhood", "shadowhorrormantle", "shadowhorrorboots", "shadowhorrorsetbonus");
        hairDrawOptions = HairDrawMode.NO_HEAD;
        tierTwoEssencesUpgradeRequirement = "purehorror";
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 1), new ModifierValue<>(BuffModifiers.SUMMON_CRIT_CHANCE, critSummon.getValue(getUpgradeTier(item))));
    }

    protected void loadArmorTexture()
    {
        super.loadArmorTexture();
        lightTexture = GameTexture.fromFile("player/armor/" + textureName + "_light");
    }

    public DrawOptions getArmorDrawOptions(InventoryItem item, Level level, PlayerMob player, InventoryItem headItem, InventoryItem chestItem, InventoryItem feetItem, int spriteX, int spriteY, int spriteRes, int drawX, int drawY, int width, int height, boolean mirrorX, boolean mirrorY, GameLight light, float alpha, MaskShaderOptions mask)
    {
        DrawOptionsList options = new DrawOptionsList();
        options.add(super.getArmorDrawOptions(item, level, player, headItem, chestItem, feetItem, spriteX, spriteY, spriteRes, drawX, drawY, width, height, mirrorX, mirrorY, light, alpha, mask));
        Color col = getDrawColor(item, player);
        options.add(lightTexture.initDraw().sprite(spriteX, spriteY, spriteRes).colorLight(col, light.minLevelCopy(150.0F)).alpha(alpha).size(width, height).mirror(mirrorX, mirrorY).addMaskShader(mask).pos(drawX, drawY));
        return options;
    }
}