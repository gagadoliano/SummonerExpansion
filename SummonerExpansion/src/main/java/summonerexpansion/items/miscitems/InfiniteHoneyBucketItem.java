package summonerexpansion.items.miscitems;

import necesse.engine.GameEvents;
import necesse.engine.events.players.ItemPlaceEvent;
import necesse.engine.input.Control;
import necesse.engine.input.Input;
import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.TileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.attackHandler.PlaceItemAttackHandler;
import necesse.entity.mobs.attackHandler.SimplePlaceAttackHandler;
import necesse.entity.mobs.attackHandler.SimplePlaceableItemAttackHandler;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.gameTooltips.InputTooltip;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.ItemControllerInteract;
import necesse.inventory.item.ItemInteractAction;
import necesse.inventory.item.placeableItem.PlaceableItem;
import necesse.level.gameTile.GameTile;
import necesse.level.maps.Level;
import necesse.level.maps.TilePosition;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Comparator;
import java.util.LinkedList;

import static summonerexpansion.codes.registry.SummonerTiles.liquidHoneyID;

public class InfiniteHoneyBucketItem extends PlaceableItem implements ItemInteractAction
{
    public InfiniteHoneyBucketItem()
    {
        super(1, false);
        controllerIsTileBasedPlacing = true;
        setItemCategory("equipment", "tools", "misc");
        keyWords.add("liquid");
        rarity = Rarity.EPIC;
        worldDrawSize = 32;
        incinerationTimeMillis = 30000;
    }

    public void setupAttackMapContent(GNDItemMap map, Level level, int x, int y, ItemAttackerMob attackerMob, int seed, InventoryItem item)
    {
        super.setupAttackMapContent(map, level, x, y, attackerMob, seed, item);
        int tileX = GameMath.getTileCoordinate(x);
        int tileY = GameMath.getTileCoordinate(y);
        level.setupTileAndObjectsHashGNDMap(map, tileX, tileY, false);
    }

    public String canPlace(Level level, int x, int y, PlayerMob player, Line2D playerPositionLine, InventoryItem item, GNDItemMap mapContent)
    {
        int tileX = GameMath.getTileCoordinate(x);
        int tileY = GameMath.getTileCoordinate(y);
        if (!level.isTileWithinBounds(tileX, tileY))
        {
            return "outsidelevel";
        }
        else if (level.isProtected(tileX, tileY))
        {
            return "protected";
        }
        else
        {
            if (mapContent != null && player.isServerClient())
            {
                level.checkTileAndObjectsHashGNDMap(player.getServerClient(), mapContent, tileX, tileY, false);
            }
            if (!isInPlaceRange(level, tileX * 32 + 16, tileY * 32 + 16, player, playerPositionLine, item))
            {
                return "outofrange";
            }
            else if (level.getTileID(tileX, tileY) == liquidHoneyID)
            {
                return "alreadywater";
            }
            else
            {
                GameTile waterTile = TileRegistry.getTile(liquidHoneyID);
                return waterTile.canPlace(level, tileX, tileY, true);
            }
        }
    }

    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, int seed, InventoryItem item, GNDItemMap mapContent)
    {
        int tileX = GameMath.getTileCoordinate(x);
        int tileY = GameMath.getTileCoordinate(y);
        ItemPlaceEvent event = new ItemPlaceEvent(level, tileX, tileY, player, item);
        GameEvents.triggerEvent(event);
        if (!event.isPrevented())
        {
            GameTile tile = TileRegistry.getTile(liquidHoneyID);
            tile.placeTile(level, tileX, tileY, true);
            level.tileLayer.setIsPlayerPlaced(tileX, tileY, true);
            if (level.isClient())
            {
                SoundManager.playSound(GameResources.watersplash, SoundEffect.effect((float)(tileX * 32 + 16), (float)(tileY * 32 + 16)));
            }
            else
            {
                level.sendTileUpdatePacket(tileX, tileY);
                level.getLevelTile(tileX, tileY).checkAround();
                level.getLevelObject(tileX, tileY).checkAround();
                level.onTilePlaced(tile, tileX, tileY, player.getServerClient());
            }
        }
        return item;
    }

    public void drawPlacePreview(Level level, int x, int y, GameCamera camera, PlayerMob player, InventoryItem item, PlayerInventorySlot slot)
    {
        if (canPlace(level, x, y, player, null, item, null) == null)
        {
            int tileX = GameMath.getTileCoordinate(x);
            int tileY = GameMath.getTileCoordinate(y);
            TileRegistry.getTile(liquidHoneyID).drawPreview(level, tileX, tileY, 0.5F, player, camera);
        }
        else if (!Input.lastInputIsController)
        {
            int tileX = GameMath.getTileCoordinate(x);
            int tileY = GameMath.getTileCoordinate(y);
            if (level.isProtected(tileX, tileY))
            {
                return;
            }
            if (player.getPositionPoint().distance(tileX * 32 + 16, tileY * 32 + 16) > (double)getPlaceRange(item, player))
            {
                return;
            }
            if (level.getTileID(tileX, tileY) != liquidHoneyID)
            {
                return;
            }
            if (level.liquidManager.getHeight(tileX, tileY) < -3)
            {
                return;
            }
            TileRegistry.getTile(TileRegistry.dirtID).drawPreview(level, tileX, tileY, 0.5F, player, camera);
        }
    }

    public ItemControllerInteract getControllerInteract(Level level, PlayerMob player, InventoryItem item, boolean beforeObjectInteract, int interactDir, LinkedList<Rectangle> mobInteractBoxes, LinkedList<Rectangle> tileInteractBoxes)
    {
        return beforeObjectInteract && !overridesObjectInteract(level, player, item) ? null : tileInteractBoxes.stream().flatMap((r) ->
        {
            LinkedList<TilePosition> tilePositions = new LinkedList<>();
            for(int i = 0; i < r.width; ++i)
            {
                for(int j = 0; j < r.height; ++j)
                {
                    tilePositions.add(new TilePosition(level, r.x + i, r.y + j));
                }
            }
            return tilePositions.stream();
        }).filter((tp) ->
        {
            int levelX = tp.tileX * 32 + 16;
            int levelY = tp.tileY * 32 + 16;
            return canLevelInteract(level, levelX, levelY, player, item);
        }).min(Comparator.comparingDouble((tp) -> (double)player.getDistance((float)(tp.tileX * 32 + 16), (float)(tp.tileY * 32 + 16)))).map((tp) ->
        {
            int levelX = tp.tileX * 32 + 16;
            int levelY = tp.tileY * 32 + 16;
            return new ItemControllerInteract(levelX, levelY)
            {
                public DrawOptions getDrawOptions(GameCamera camera)
                {
                    if (level.isProtected(tp.tileX, tp.tileY))
                    {
                        return null;
                    }
                    else if (player.getPositionPoint().distance(tp.tileX * 32 + 16, tp.tileY * 32 + 16) > (double)getPlaceRange(item, player))
                    {
                        return null;
                    }
                    else if (level.getTileID(tp.tileX, tp.tileY) != liquidHoneyID)
                    {
                        return null;
                    }
                    else
                    {
                        return level.liquidManager.getHeight(tp.tileX, tp.tileY) < -3 ? null : () -> TileRegistry.getTile(TileRegistry.dirtID).drawPreview(level, tp.tileX, tp.tileY, 0.5F, player, camera);
                    }
                }
                public void onCurrentlyFocused(GameCamera camera) {
                }
            };
        }).orElse(null);
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        int cooldown = getAttackCooldownTime(item, attackerMob);
        if (cooldown > 0)
        {
            return super.onAttack(level, x, y, attackerMob, attackHeight, item, slot, animAttack, seed, mapContent);
        }
        else if (!attackerMob.isPlayer)
        {
            return item;
        }
        else
        {
            attackerMob.startAttackHandler(new SimplePlaceableItemAttackHandler((PlayerMob)attackerMob, slot, x, y, seed, this, mapContent)
            {
                protected void onServerPlaceInvalid(InventoryItem item, PlaceItemAttackHandler.PlacePosition placePosition, Line2D playerPositionLine)
                {
                    Level level = attackerMob.getLevel();
                    PlayerMob player = (PlayerMob)attackerMob;
                    ServerClient client = player.getServerClient();
                    int tileX = GameMath.getTileCoordinate(placePosition.placeX);
                    int tileY = GameMath.getTileCoordinate(placePosition.placeY);
                    level.checkTileAndObjectsHashGNDMap(client, placePosition.attackMapContent, tileX, tileY, false);
                }
            });
            return slot.getItem();
        }
    }

    public void setupLevelInteractMapContent(GNDItemMap map, Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item)
    {
        setupAttackMapContent(map, level, x, y, attackerMob, 0, item);
    }

    public boolean canLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item)
    {
        return true;
    }

    public InventoryItem onLevelInteract(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int seed, GNDItemMap mapContent)
    {
        if (!attackerMob.isPlayer)
        {
            return item;
        }
        else
        {
            PlayerMob player = (PlayerMob)attackerMob;
            int cooldown = getLevelInteractCooldownTime(item, attackerMob);
            if (cooldown > 0)
            {
                return canPlaceWater(level, x, y, player, null, item, mapContent) == null ? onPlaceWater(level, x, y, player, seed, item, mapContent) : item;
            }
            else
            {
                attackerMob.startAttackHandler((new SimplePlaceAttackHandler(player, slot, x, y, seed, mapContent)
                {
                    public String canPlace(Level level, int x, int y, PlayerMob player, Line2D playerPositionLine, InventoryItem item, GNDItemMap mapContent)
                    {
                        return canPlaceWater(level, x, y, player, playerPositionLine, item, mapContent);
                    }
                    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, int seed, InventoryItem item, GNDItemMap mapContent)
                    {
                        return onPlaceWater(level, x, y, player, seed, item, mapContent);
                    }
                    protected void onServerPlaceInvalid(InventoryItem item, PlaceItemAttackHandler.PlacePosition placePosition, Line2D playerPositionLine)
                    {
                        Level level = attackerMob.getLevel();
                        PlayerMob player = (PlayerMob)attackerMob;
                        ServerClient client = player.getServerClient();
                        int tileX = GameMath.getTileCoordinate(placePosition.placeX);
                        int tileY = GameMath.getTileCoordinate(placePosition.placeY);
                        level.checkTileAndObjectsHashGNDMap(client, placePosition.attackMapContent, tileX, tileY, false);
                    }
                    protected int getPlaceCooldown()
                    {
                        InventoryItem item = slot.getItem();
                        return item != null ? getAttackHandlerPlaceCooldown(item, attackerMob) : 200;
                    }
                    protected void showAttackAndSendAttacker(int targetX, int targetY, InventoryItem item)
                    {
                        attackerMob.showItemLevelInteract(InfiniteHoneyBucketItem.this, item, targetX, targetY, seed, null);
                    }
                }).startFromInteract());
                return slot.getItem();
            }
        }
    }

    public String canPlaceWater(Level level, int x, int y, PlayerMob player, Line2D playerPositionLine, InventoryItem item, GNDItemMap mapContent)
    {
        int tileX = GameMath.getTileCoordinate(x);
        int tileY = GameMath.getTileCoordinate(y);
        if (!level.isTileWithinBounds(tileX, tileY))
        {
            return "outsidelevel";
        }
        else if (level.isProtected(tileX, tileY))
        {
            return "protected";
        }
        else
        {
            if (mapContent != null && player.isServerClient())
            {
                level.checkTileAndObjectsHashGNDMap(player.getServerClient(), mapContent, tileX, tileY, false);
            }
            if (!isInPlaceRange(level, x, y, player, playerPositionLine, item))
            {
                return "outofrange";
            }
            else if (level.getTileID(tileX, tileY) != liquidHoneyID)
            {
                return "notwater";
            }
            else
            {
                return null;
            }
        }
    }

    public InventoryItem onPlaceWater(Level level, int x, int y, PlayerMob player, int seed, InventoryItem item, GNDItemMap mapContent)
    {
        int tileX = GameMath.getTileCoordinate(x);
        int tileY = GameMath.getTileCoordinate(y);
        ItemPlaceEvent event = new ItemPlaceEvent(level, tileX, tileY, player, item);
        GameEvents.triggerEvent(event);
        if (!event.isPrevented())
        {
            GameTile tile = TileRegistry.getTile(TileRegistry.dirtID);
            tile.placeTile(level, tileX, tileY, true);
            level.tileLayer.setIsPlayerPlaced(tileX, tileY, true);
            if (level.isClient())
            {
                SoundManager.playSound(GameResources.waterblob, SoundEffect.effect((float)(tileX * 32 + 16), (float)(tileY * 32 + 16)));
            }
            else
            {
                level.sendTileUpdatePacket(tileX, tileY);
                level.getLevelTile(tileX, tileY).checkAround();
                level.getLevelObject(tileX, tileY).checkAround();
                level.onTilePlaced(tile, tileX, tileY, player.getServerClient());
            }
        }
        return item;
    }

    public boolean getConstantInteract(InventoryItem item) {
        return true;
    }

    public float getAttackSpeedModifier(InventoryItem item, ItemAttackerMob attackerMob)
    {
        float superModifier = super.getAttackSpeedModifier(item, attackerMob);
        return attackerMob != null && attackerMob.isPlayer && ((PlayerMob)attackerMob).hasGodMode() ? superModifier : superModifier * (attackerMob == null ? 1.0F : attackerMob.buffManager.getModifier(BuffModifiers.BUILDING_SPEED));
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "infhoneybuckettip"));
        tooltips.add(new InputTooltip(Control.MOUSE1, Localization.translate("itemtooltip", "infhoneybucketplace")));
        tooltips.add(new InputTooltip(Control.MOUSE2, Localization.translate("itemtooltip", "infhoneybucketpickup")));
        return tooltips;
    }
}
