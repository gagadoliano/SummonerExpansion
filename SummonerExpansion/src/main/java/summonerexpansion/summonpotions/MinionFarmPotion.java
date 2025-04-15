package summonerexpansion.summonpotions;

import necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.SimplePotionItem;

public class MinionFarmPotion extends SimplePotionItem
{
    public MinionFarmPotion()
    {
        super(100, Rarity.COMMON,"minionfarmbuff", 300, "minionfarmtip");
    }
}
