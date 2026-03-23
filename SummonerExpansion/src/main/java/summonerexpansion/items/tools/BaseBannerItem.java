package summonerexpansion.items.tools;

import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.Item;
import necesse.inventory.item.miscItem.BannerItem;

import java.util.function.Function;

public class BaseBannerItem extends BannerItem
{
    public BaseBannerItem(Item.Rarity rarity, int range, Function<Mob, Buff> buff)
    {
        super(rarity, range, buff);
        this.rarity = rarity;
        this.range = range;
        this.buff = buff;
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/banners/" + getStringID());
    }
}