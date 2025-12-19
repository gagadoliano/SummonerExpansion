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
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.inventory.lootTable.presets.ArmorSetsLootTable;
import necesse.inventory.lootTable.presets.HeadArmorLootTable;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;

public class RedSpiderHelmet extends SetHelmetArmorItem
{
    private GameTexture lightTexture;
    public IntUpgradeValue summonMax = (new IntUpgradeValue()).setBaseValue(2).setUpgradedValue(1F, 3).setUpgradedValue(10F, 4);

    public RedSpiderHelmet(int enchantCost, Item.Rarity rarityTier)
    {
        super(5, DamageTypeRegistry.SUMMON, enchantCost, HeadArmorLootTable.headArmor, ArmorSetsLootTable.armorSets, rarityTier, "redspiderhelmet", "redspiderchestplate", "redspiderboots", "redspidersetbonus");
        hairDrawOptions = HairDrawMode.NO_HEAD;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue<>(BuffModifiers.MAX_SUMMONS, summonMax.getValue(getUpgradeTier(item))));
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