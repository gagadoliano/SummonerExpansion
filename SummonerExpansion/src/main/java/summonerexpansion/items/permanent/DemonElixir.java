package summonerexpansion.items.permanent;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketPlayerGeneral;
import necesse.engine.sound.PrimitiveSoundEmitter;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.placeableItem.consumableItem.ConsumableItem;
import necesse.level.maps.Level;

import java.awt.geom.Line2D;

public class DemonElixir extends ConsumableItem
{
    public DemonElixir()
    {
        super(1, true);
        this.rarity = Item.Rarity.UNIQUE;
        this.worldDrawSize = 32;
        this.incinerationTimeMillis = 60000;
        this.keyWords.add("resilience");
    }

    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, int seed, InventoryItem item, GNDItemMap mapContent)
    {
        player.setMaxResilience(player.getMaxResilienceFlat() + 10);
        if (level.isServer())
        {
            level.getServer().network.sendToAllClientsExcept(new PacketPlayerGeneral(player.getServerClient()), player.getServerClient());
        }
        else if (level.isClient())
        {
            SoundManager.playSound(GameResources.drink, SoundEffect.effect(player).pitch(0.8F));
        }
        if (isSingleUse(player))
        {
            item.setAmount(item.getAmount() - 1);
        }
        return item;
    }

    public String canPlace(Level level, int x, int y, PlayerMob player, Line2D playerPositionLine, InventoryItem item, GNDItemMap mapContent)
    {
        if (player.getMaxResilience() >= 10)
        {
            return "invalidupgrade";
        }
        return null;
    }

    public boolean shouldSendToOtherClients(Level level, int x, int y, PlayerMob player, InventoryItem item, String error, GNDItemMap mapContent)
    {
        return (error == null);
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "consumetip"));
        tooltips.add(Localization.translate("itemtooltip", "demonelixirtip"));
        return tooltips;
    }
}