package summonerexpansion.summonpotions;

import necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.SimplePotionItem;

public class MinionCritChancePotion extends SimplePotionItem
{
    public MinionCritChancePotion()
    {
        super(100, Rarity.COMMON,"minioncritchancebuff", 300, "minioncritchancetip");
    }
}
