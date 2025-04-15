package summonerexpansion.summonpotions;

import necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.SimplePotionItem;

public class MinionCloseRangePotion extends SimplePotionItem
{
    public MinionCloseRangePotion()
    {
        super(100, Rarity.COMMON,"minioncloserangebuff", 300, "minioncloserangetip");
    }
}
