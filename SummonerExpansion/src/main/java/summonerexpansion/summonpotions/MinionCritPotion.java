package summonerexpansion.summonpotions;

import necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.SimplePotionItem;

public class MinionCritPotion extends SimplePotionItem
{
    public MinionCritPotion()
    {
        super(100, Rarity.COMMON,"minioncritbuff", 300, "minioncrittip");
    }
}
