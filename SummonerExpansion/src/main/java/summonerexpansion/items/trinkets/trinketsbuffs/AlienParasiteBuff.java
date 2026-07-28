package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

import static summonerexpansion.codes.registries.RegistryTrinkets.TrinketBuffs.ADAPTIVEALIENSTACKS;

public class AlienParasiteBuff extends TrinketBuff
{
    /**
     * Highest tier first. The first entry the player has a kill for decides the
     * stack count, so the order is the progression order and matters. Index 0
     * grants BOSS_TIERS.length + 1 stacks, counting down to 2 for the last entry,
     * and a player who has killed none of them gets 1.
     */
    private static final String[] BOSS_TIERS = {
        "ascendedwizard",
        "crystaldragon",
        "moonlightdancer",
        "sunlightchampion",
        "spiderempress",
        "nightswarm",
        "motherslime",
        "fallenwizard",
        "sageandgrit",
        "pestwarden",
        "cryoqueen",
        "reaper",
        "piratecaptain",
        "ancientvulture",
        "swampguardian",
        "chieftain",
        "voidwizard",
        "queenspider",
        "evilsprotector",
    };

    public AlienParasiteBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber buffEventSubscriber)
    {
    }

    public void serverTick(ActiveBuff buff)
    {
        updateModifiers(buff);
    }

    public void clientTick(ActiveBuff buff)
    {
        // The stack count is derived from kill statistics that only the server
        // holds, and it reaches clients through the buff packet the server sends.
        // There is nothing for the client to recompute.
    }

    public void updateModifiers(ActiveBuff buff)
    {
        if (!buff.owner.isServer() || !(buff.owner instanceof PlayerMob))
        {
            return;
        }
        ServerClient client = ((PlayerMob)buff.owner).getServerClient();
        if (client == null)
        {
            return;
        }

        int stacks = 1;
        for (int i = 0; i < BOSS_TIERS.length; i++)
        {
            if (client.characterStats().mob_kills.getKills(BOSS_TIERS[i]) > 0)
            {
                stacks = BOSS_TIERS.length + 1 - i;
                break;
            }
        }

        // Re-adding sends a buff packet to every nearby client, so only do it when
        // the tier actually changed or the existing buff is about to run out.
        if (buff.owner.buffManager.getStacks(ADAPTIVEALIENSTACKS) == stacks
                && buff.owner.buffManager.getBuffDurationLeftSeconds(ADAPTIVEALIENSTACKS) > 1.0F)
        {
            return;
        }

        ActiveBuff ab = new ActiveBuff(ADAPTIVEALIENSTACKS, buff.owner, 60F, null);
        buff.owner.buffManager.addBuff(ab, true).setStacks(stacks, 60, buff.owner);
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "alienparasitetip"));
        return tooltips;
    }
}
