package summonerexpansion.summonpotions;

import necesse.inventory.item.placeableItem.consumableItem.potionConsumableItem.SimplePotionItem;

public class MinionAttackSpeedPotion extends SimplePotionItem
{
    public MinionAttackSpeedPotion()
    {
        super(100, Rarity.COMMON,"minionattackspeedbuff", 300, "minionattackspeedtip");
    }
}
