package summonerexpansion.items.miscitems;

import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.FlyingBugSpawnItem;
import necesse.level.maps.Level;

public class SummonBugHostileSpawnItem extends FlyingBugSpawnItem
{
    public final String bugItemName;

    public SummonBugHostileSpawnItem(String bugItemName)
    {
        super(20, bugItemName);
        this.bugItemName = bugItemName;
        this.rarity = Rarity.NORMAL;
    }

    protected void spawnFlyingBug(Level level, int x, int y, PlayerMob player, int seed, InventoryItem item, GNDItemMap mapContent)
    {
        HostileMob summonBug = (HostileMob) MobRegistry.getMob(bugItemName, level);
        summonBug.canDespawn = false;
        level.entityManager.addMob(summonBug, (float)x, (float)y);
    }
}