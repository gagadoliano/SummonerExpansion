package summonerexpansion.summonpotions;

import necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.SimplePotionItem;

public class MinionSpeedPotion extends SimplePotionItem
{
    public MinionSpeedPotion()
    {
        super(100, Rarity.COMMON,"minionspeedbuff", 300, "minionspeedtip");
    }
}
