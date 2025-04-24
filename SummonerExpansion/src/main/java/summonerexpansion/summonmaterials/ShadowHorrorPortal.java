package summonerexpansion.summonmaterials;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.network.packet.PacketMobChat;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.consumableItem.ConsumableItem;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.Level;

import java.awt.*;
import java.util.ArrayList;

public class ShadowHorrorPortal extends ConsumableItem
{
    public ShadowHorrorPortal()
    {
        super(1, true);
        itemCooldownTime.setBaseValue(2000);
        setItemCategory("consumable", "bossitems");
        dropsAsMatDeathPenalty = true;
        keyWords.add("boss");
        rarity = Rarity.LEGENDARY;
        worldDrawSize = 32;
        incinerationTimeMillis = 30000;
    }

    public String canPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, GNDItemMap mapContent)
    {
        if (level.isClient()) {
            return null;
        } else if (level instanceof IncursionLevel) {
            return "inincursion";
        } else if (!level.isIslandPosition()) {
            return "notisland";
        } else if (level.getIslandDimension() != 0) {
            return "notsurface";
        } else if (!level.getServer().world.worldEntity.isNight()) {
            return "notnight";
        } else {
            ArrayList<Point> spawnPoints = new ArrayList();
            Mob mob = MobRegistry.getMob("riftportalmob", level);
            int pTileX = player.getX() / 32;
            int pTileY = player.getY() / 32;
            for(int i = -10; i <= 10; ++i)
            {
                for(int j = -10; j <= 10; ++j)
                {
                    int tileX = pTileX + i;
                    int tileY = pTileY + j;
                    if (!level.isLiquidTile(tileX, tileY) && !level.isShore(tileX, tileY) && !mob.collidesWith(level, tileX * 32 + 16, tileY * 32 + 16))
                    {
                        spawnPoints.add(new Point(tileX, tileY));
                    }
                }
            }
            if (spawnPoints.size() == 0)
            {
                return "nospace";
            }
            else
            {
                return null;
            }
        }
    }

    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, int seed, InventoryItem item, GNDItemMap mapContent)
    {
        if (level.isServer())
        {
            ServerClient serverClient = player.getServerClient();
            if (level instanceof IncursionLevel)
            {
                GameMessage summonError = ((IncursionLevel)level).canSummonBoss("riftportalmob");
                if (summonError != null)
                {
                    if (player != null && player.isServerClient())
                    {
                        serverClient.sendChatMessage(summonError);
                    }
                    return item;
                }
            }
            ArrayList<Point> spawnPoints = new ArrayList();
            Mob mob = MobRegistry.getMob("riftportalmob", level);
            int pTileX = player.getX() / 32;
            int pTileY = player.getY() / 32;
            for(int i = -10; i <= 10; ++i)
            {
                for(int j = -10; j <= 10; ++j)
                {
                    int tileX = pTileX + i;
                    int tileY = pTileY + j;
                    if (!level.isLiquidTile(tileX, tileY) && !level.isShore(tileX, tileY) && !mob.collidesWith(level, tileX * 32 + 16, tileY * 32 + 16))
                    {
                        spawnPoints.add(new Point(tileX, tileY));
                    }
                }
            }
            System.out.println("An army of horror spirits has been summoned at " + level.getIdentifier() + ".");
            Point spawnPoint;
            if (!spawnPoints.isEmpty())
            {
                spawnPoint = (Point) GameRandom.globalRandom.getOneOf(spawnPoints);
            }
            else
            {
                spawnPoint = new Point(player.getTileX() + GameRandom.globalRandom.getIntBetween(-8, 8), player.getTileY() + GameRandom.globalRandom.getIntBetween(-8, 8));
            }

            level.entityManager.addMob(mob, (float)(spawnPoint.x * 32 + 16), (float)(spawnPoint.y * 32 + 16));
            level.getServer().network.sendToClientsWithEntity(new PacketChatMessage(new LocalMessage("misc", "bosssummon", "name", mob.getLocalization())), mob);
            if (level instanceof IncursionLevel)
            {
                ((IncursionLevel)level).onBossSummoned(mob);
            }
        }
        if (this.singleUse)
        {
            item.setAmount(item.getAmount() - 1);
        }
        return item;
    }

    public InventoryItem onAttemptPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, GNDItemMap mapContent, String error)
    {
        if (level.isServer() && player != null && player.isServerClient() && error.equals("inincursion"))
        {
            player.getServerClient().sendChatMessage(new LocalMessage("misc", "cannotsummoninincursion"));
            return item;
        }
        else
        {
            if (level.isServer())
            {
                String translationKey;
                if (error.equals("alreadyspawned")) {
                    translationKey = null;
                } else if (error.equals("notsurface")) {
                    translationKey = "portalnotonsurface";
                } else if (error.equals("notnight")) {
                    translationKey = "portalnotnight";
                } else if (error.equals("nospace")) {
                    translationKey = "portalnospace";
                } else {
                    translationKey = "portalerror";
                }
                if (translationKey != null) {
                    level.getServer().network.sendPacket(new PacketMobChat(player.getUniqueID(), "itemtooltip", translationKey), player.getServerClient());
                }
            }
            return item;
        }
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "horrorportaltip"));
        return tooltips;
    }
}