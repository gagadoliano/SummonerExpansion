package summonerexpansion.summonarmor;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorModifiers;
import necesse.inventory.item.armorItem.SetHelmetArmorItem;

public class ShadowHelmet extends SetHelmetArmorItem
{
    public ShadowHelmet()
    {
        super(5, DamageTypeRegistry.SUMMON, 600, Rarity.UNCOMMON, "shadowhelmet", "shadowmantle", "shadowboots", "shadowhelmetsetbonus");
        hairDrawOptions = HairDrawMode.NO_HAIR;
        facialFeatureDrawOptions = FacialFeatureDrawMode.NO_FACIAL_FEATURE;
    }

    public ArmorModifiers getArmorModifiers(InventoryItem item, Mob mob)
    {
        return new ArmorModifiers(new ModifierValue(BuffModifiers.SUMMON_DAMAGE, 0.10F));
    }
}