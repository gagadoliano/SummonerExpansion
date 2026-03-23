package summonerexpansion.items.fishing;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.CirclingFishParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.matItem.FishItemInterface;
import necesse.inventory.item.matItem.MatItem;
import necesse.level.maps.Level;

public class BaseFishItem extends MatItem implements FishItemInterface
{
    public GameTexture circlingFishTexture;

    public BaseFishItem(int stackSize, Item.Rarity rarity, String... globalIngredients)
    {
        super(stackSize, rarity, globalIngredients);
    }

    public void loadTextures()
    {
        super.loadTextures();
        this.circlingFishTexture = GameTexture.fromFile("particles/circlingfish");
    }

    protected void loadItemTextures() {
        itemTexture = GameTexture.fromFile("items/fishing/" + getStringID());
    }

    public Particle getParticle(Level level, int x, int y, int lifeTime)
    {
        return new CirclingFishParticle(level, (float)x, (float)y, lifeTime, this.circlingFishTexture, 60);
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", getStringID() + "tip"));
        return tooltips;
    }
}