package summonerexpansion.objects.decoration;

import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.inventory.lootTable.lootItem.LootItemMultiplierIgnored;
import necesse.level.gameObject.StatueObject;
import necesse.level.maps.Level;

import java.awt.*;

public class SandMonkStatueObject extends StatueObject
{
    public SandMonkStatueObject(String texturePath, int xOffset, int spriteCounts)
    {
        super(texturePath);
        mapColor = new Color(142, 119, 51);
        spriteCount = spriteCounts;
        statueXOffset = xOffset;
        objectHealth = 800;
        toolTier = 9;
    }

    public LootTable getLootTable(Level level, int layerID, int tileX, int tileY)
    {
        return new LootTable(new LootItemMultiplierIgnored(new LootItemList((new LootItem("sandstone", 10)).splitItems(5), new LootItem("sandtemplemonkhead"))));
    }

    public GameTexture generateItemTexture()
    {
        return GameTexture.fromFile("items/objects/" + this.getStringID());
    }
}