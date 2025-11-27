package summonerexpansion.objects.summonobjects;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.engine.registries.ContainerRegistry;
import necesse.engine.registries.ObjectRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.entity.objectEntity.ProcessingTechInventoryObjectEntity;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.InventoryRange;
import necesse.inventory.container.object.OEInventoryContainer;
import necesse.inventory.item.Item;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
import necesse.level.gameObject.StaticMultiObject;
import necesse.level.maps.Level;
import necesse.level.maps.levelData.settlementData.SettlementWorkstationObject;
import summonerexpansion.codes.summonentity.ArcanicConverterObjectEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ArcanicConverter extends StaticMultiObject implements SettlementWorkstationObject
{
    protected ArcanicConverter(String texturePath, int multiX, int multiY, int multiWidth, int multiHeight, int[] multiIDs, Rectangle fullCollision)
    {
        super(multiX, multiY, multiWidth, multiHeight, multiIDs, fullCollision, texturePath);
        stackSize = 100;
        rarity = Item.Rarity.RARE;
        mapColor = new Color(180, 94, 222);
        displayMapTooltip = true;
        objectHealth = 100;
        toolType = ToolType.ALL;
        isLightTransparent = true;
        hoverHitbox = new Rectangle(0, -32, 32, 64);
        setItemCategory("objects", "craftingstations");
        setCraftingCategory("craftingstations");
    }

    public void loadTextures() {
        super.loadTextures();
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        int spriteIndex = (int)(level.getLocalTime() / 100L % 4L);
        GameSprite sprite = new GameSprite(texture, spriteIndex, 0, 64);
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
        int spriteIndex = (int)(level.getLocalTime() / 100L % 4L);
        GameSprite sprite = new GameSprite(texture, spriteIndex, 0, 64);
        drawMultiTexturePreview(sprite, tileX, tileY, alpha, camera);
    }

    public static int[] registerArcanicConverter(String texturePath, boolean isObtainable, boolean isObtainableInCreative)
    {
        int[] ids = new int[2];
        Rectangle collision = new Rectangle(0, 0, 64, 32);
        ids[0] = ObjectRegistry.registerObject(texturePath, new ArcanicConverter(texturePath, 0, 0, 2, 1, ids, collision), -1.0F, isObtainable, isObtainable, isObtainableInCreative);
        ids[1] = ObjectRegistry.registerObject(texturePath + "2", new ArcanicConverter(texturePath, 1, 0, 2, 1, ids, collision), 0.0F, false);
        return ids;
    }

    public String getInteractTip(Level level, int x, int y, PlayerMob perspective, boolean debug)
    {
        return Localization.translate("controls", "opentip");
    }

    public boolean canInteract(Level level, int x, int y, PlayerMob player) {
        return true;
    }

    public void interact(Level level, int x, int y, PlayerMob player)
    {
        super.interact(level, x, y, player);
        if (level.isServer())
        {
            OEInventoryContainer.openAndSendContainer(ContainerRegistry.PROCESSING_INVENTORY_CONTAINER, player.getServerClient(), level, x, y);
        }
    }

    public ObjectEntity getNewObjectEntity(Level level, int x, int y)
    {
        return new ArcanicConverterObjectEntity(level, x, y);
    }

    public ProcessingTechInventoryObjectEntity getProcessingObjectEntity(Level level, int tileX, int tileY)
    {
        ObjectEntity objectEntity = level.entityManager.getObjectEntity(tileX, tileY);
        return objectEntity instanceof ProcessingTechInventoryObjectEntity ? (ProcessingTechInventoryObjectEntity)objectEntity : null;
    }

    public Stream<Recipe> streamSettlementRecipes(Level level, int tileX, int tileY)
    {
        ProcessingTechInventoryObjectEntity processingOE = this.getProcessingObjectEntity(level, tileX, tileY);
        return processingOE != null ? Recipes.streamRecipes(processingOE.techs) : Stream.empty();
    }

    public boolean isProcessingInventory(Level level, int tileX, int tileY) {
        return true;
    }

    public boolean canCurrentlyCraft(Level level, int tileX, int tileY, Recipe recipe)
    {
        ProcessingTechInventoryObjectEntity processingOE = this.getProcessingObjectEntity(level, tileX, tileY);
        if (processingOE != null)
        {
            return processingOE.getExpectedResults().crafts < 100;
        }
        else
        {
            return false;
        }
    }

    public int getMaxCraftsAtOnce(Level level, int tileX, int tileY, Recipe recipe) {
        return 10;
    }

    public InventoryRange getProcessingInputRange(Level level, int tileX, int tileY)
    {
        ProcessingTechInventoryObjectEntity processingOE = this.getProcessingObjectEntity(level, tileX, tileY);
        return processingOE != null ? processingOE.getInputInventoryRange() : null;
    }

    public InventoryRange getProcessingOutputRange(Level level, int tileX, int tileY)
    {
        ProcessingTechInventoryObjectEntity processingOE = this.getProcessingObjectEntity(level, tileX, tileY);
        return processingOE != null ? processingOE.getOutputInventoryRange() : null;
    }

    public ArrayList<InventoryItem> getCurrentAndFutureProcessingOutputs(Level level, int tileX, int tileY)
    {
        ProcessingTechInventoryObjectEntity processingOE = this.getProcessingObjectEntity(level, tileX, tileY);
        return processingOE != null ? processingOE.getCurrentAndExpectedResults().items : new ArrayList<>();
    }

    public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = super.getItemTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "arcanicconvertertip"));
        return tooltips;
    }

    protected boolean shouldPlayInteractSound(Level level, int tileX, int tileY) {
        return true;
    }
}