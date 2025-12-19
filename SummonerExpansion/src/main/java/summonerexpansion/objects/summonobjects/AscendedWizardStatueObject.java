package summonerexpansion.objects.summonobjects;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.engine.registries.ObjectRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.ToolType;
import necesse.level.gameObject.StaticMultiObject;
import necesse.level.maps.Level;

import java.awt.*;
import java.util.List;

public class AscendedWizardStatueObject extends StaticMultiObject
{
    protected AscendedWizardStatueObject(String texturePath, int multiX, int multiY, int multiWidth, int multiHeight, int[] multiIDs, Rectangle fullCollision)
    {
        super(multiX, multiY, multiWidth, multiHeight, multiIDs, fullCollision, "statues/" + texturePath);
        stackSize = 1;
        rarity = Item.Rarity.LEGENDARY;
        mapColor = new Color(143, 143, 143);
        objectHealth = 100;
        toolType = ToolType.ALL;
        isLightTransparent = true;
        hoverHitbox = new Rectangle(0, 0, 32, 32);
        setItemCategory("objects", "landscaping", "masonry");
        setCraftingCategory("objects", "landscaping", "masonry");
    }

    public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips itemTooltips = super.getItemTooltips(item, perspective);
        itemTooltips.add(Localization.translate("itemtooltip", "ascendedwizardstatuetip"));
        return itemTooltips;
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        GameSprite sprite = new GameSprite(texture, 0, 0, 96, 160, 96, 160);
        final DrawOptions options = getMultiTextureDrawOptions(sprite, level, tileX, tileY, camera);
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
        GameSprite sprite = new GameSprite(texture, 0, 0, 96, 160, 96, 160);
        drawMultiTexturePreview(sprite, tileX, tileY, alpha, camera);
    }

    public static int[] registerAscendedWizardStatue(String texturePath, boolean isObtainable)
    {
        int[] ids = new int[6];
        Rectangle collision = new Rectangle(8, 10, 80, 54);
        ids[0] = ObjectRegistry.registerObject(texturePath, new AscendedWizardStatueObject(texturePath, 0, 0, 3, 2, ids, collision), 0.0F, isObtainable);
        ids[1] = ObjectRegistry.registerObject(texturePath + "2", new AscendedWizardStatueObject(texturePath, 1, 0, 3, 2, ids, collision), 0.0F, false);
        ids[2] = ObjectRegistry.registerObject(texturePath + "5", new AscendedWizardStatueObject(texturePath, 2, 0, 3, 2, ids, collision), 0.0F, false);
        ids[3] = ObjectRegistry.registerObject(texturePath + "3", new AscendedWizardStatueObject(texturePath, 0, 1, 3, 2, ids, collision), 0.0F, false);
        ids[4] = ObjectRegistry.registerObject(texturePath + "4", new AscendedWizardStatueObject(texturePath, 1, 1, 3, 2, ids, collision), 0.0F, false);
        ids[5] = ObjectRegistry.registerObject(texturePath + "6", new AscendedWizardStatueObject(texturePath, 2, 1, 3, 2, ids, collision), 0.0F, false);
        return ids;
    }
}