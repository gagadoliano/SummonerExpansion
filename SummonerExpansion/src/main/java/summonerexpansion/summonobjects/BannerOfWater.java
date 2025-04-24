package summonerexpansion.summonobjects;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.level.gameObject.*;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.summonothers.BannerOfWaterObjectEntity;

import java.awt.*;
import java.util.List;

public class BannerOfWater extends GameObject
{
    public ObjectDamagedTextureArray texture;

    public BannerOfWater() 
    {
        super(new Rectangle(6, 6, 20, 20));
        stackSize = 1;
        objectHealth = 100;
        displayMapTooltip = true;
        isLightTransparent = true;
        rarity = Item.Rarity.LEGENDARY;
    }

    public void loadTextures()
    {
        super.loadTextures();
        this.texture = ObjectDamagedTextureArray.loadAndApplyOverlay(this, "objects/bannerofwater");
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        GameTexture texture = this.texture.getDamagedTexture(this, level, tileX, tileY);
        int sprite = GameUtils.getAnim(level.getWorldEntity().getTime() + getTileSeed(tileX, tileY, 52), 4, 1200);
        final TextureDrawOptions options = texture.initDraw().sprite(sprite, 0, 64, texture.getHeight()).light(light).pos(drawX - 16, drawY - (texture.getHeight() - 32));
        list.add(new LevelSortedDrawable(this, tileX, tileY)
        {
            public int getSortY() {
                return 16;
            }
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera)
    {
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        GameTexture texture = this.texture.getDamagedTexture(0.0F);
        texture.initDraw().sprite(0, 0, 64, texture.getHeight()).alpha(alpha).draw(drawX - 16, drawY - (texture.getHeight() - 32));
    }

    public ObjectEntity getNewObjectEntity(Level level, int x, int y)
    {
        return new BannerOfWaterObjectEntity(level, x, y);
    }

    public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = super.getItemTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "bannerofwatertip"));
        return tooltips;
    }

    public List<ObjectHoverHitbox> getHoverHitboxes(Level level, int layerID, int tileX, int tileY)
    {
        List<ObjectHoverHitbox> list = super.getHoverHitboxes(level, layerID, tileX, tileY);
        list.add(new ObjectHoverHitbox(layerID, tileX, tileY, 0, -32, 32, 32));
        return list;
    }
}