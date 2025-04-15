package summonerexpansion.summonmounts;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.*;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.mountItem.MountItem;

public class MagicCheese extends MountItem
{
    public MagicCheese()
    {
        super("mousesummonmount");
        rarity = Rarity.RARE;
    }

    @Override
    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "magiccheesetip"));
        return  tooltips;
    }
}
