package summonerexpansion.summonmounts;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.*;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.MinecartLinePos;
import necesse.entity.mobs.summon.MinecartLines;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlaceableItemInterface;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.mountItem.MountItem;
import necesse.level.gameObject.GameObject;
import necesse.level.gameObject.MinecartTrackObject;
import necesse.level.gameObject.TrapTrackObject;
import necesse.level.maps.Level;

import java.awt.geom.Point2D;

public class CavelingMinecartItem extends MountItem implements PlaceableItemInterface
{
    public CavelingMinecartItem()
    {
        super("cavelingminecartmount");
        this.setMounterPos = false;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = this.getBaseTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "cavelingminecarttip"));
        tooltips.remove();
        return tooltips;
    }

    public String canUseMount(InventoryItem item, PlayerMob player, Level level)
    {
        return Localization.translate("itemtooltip", "cavelingmounttip", "mount", this.getDisplayName(item));
    }

    public void setDrawAttackRotation(InventoryItem item, ItemAttackDrawOptions drawOptions, float attackDirX, float attackDirY, float attackProgress)
    {
        drawOptions.swingRotation(attackProgress);
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        if (!attackerMob.isPlayer)
        {
            return item;
        }
        else
        {
            PlayerMob player = (PlayerMob)attackerMob;
            if (this.canPlace(level, x, y, player, item, mapContent) == null)
            {
                if (level.isServer())
                {
                    Mob mob = MobRegistry.getMob("cavelingminecart", level);
                    if (mob instanceof CavelingMinecart)
                    {
                        ((CavelingMinecart)mob).minecartDir = attackerMob.isAttacking ? attackerMob.beforeAttackDir : attackerMob.getDir();
                        mob.resetUniqueID();
                        level.entityManager.addMob(mob, (float)x, (float)y);
                    }
                }
                if (level.isClient())
                {
                    SoundManager.playSound(GameResources.cling, SoundEffect.effect((float)x, (float)y).volume(0.8F));
                }
                item.setAmount(item.getAmount() - 1);
                return item;
            }
            else
            {
                return item;
            }
        }
    }

    public String canAttack(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item) {
        return null;
    }

    protected String canPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, GNDItemMap mapContent)
    {
        if (player.getPositionPoint().distance(x, y) > 100.0)
        {
            return "outofrange";
        }
        else
        {
            Mob mob = MobRegistry.getMob("cavelingminecart", level);
            if (mob != null)
            {
                mob.setPos((float)x, (float)y, true);
                if (mob.collidesWith(level))
                {
                    return "collision";
                }
                GameObject object = level.getObject(mob.getTileX(), mob.getTileY());
                if (!(object instanceof MinecartTrackObject) || object instanceof TrapTrackObject)
                {
                    return "nottracks";
                }
            }
            return null;
        }
    }

    public void drawPlacePreview(Level level, int x, int y, GameCamera camera, PlayerMob player, InventoryItem item, PlayerInventorySlot slot)
    {
        String error = this.canPlace(level, x, y, player, item, null);
        if (error == null)
        {
            int placeDir = player.isAttacking ? player.beforeAttackDir : player.getDir();
            CavelingMinecart.drawPlacePreview(level, x, y, placeDir, camera);
        }
    }
}