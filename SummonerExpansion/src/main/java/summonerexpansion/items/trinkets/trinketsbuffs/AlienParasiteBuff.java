package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasKilledEvent;
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

    /**
     * The stacks buff is what carries the MAX_HEALTH penalty, so it must not keep
     * expiring and being re-applied. The original passed 60 to setStacks, which
     * takes milliseconds rather than the seconds the constructor takes, so every
     * update left the buff alive for 60ms and it had to be rebuilt every tick.
     */
    private static final float STACK_DURATION_SECONDS = 60.0F;
    private static final int STACK_DURATION_MS = 60000;
    private static final float REFRESH_BELOW_SECONDS = 5.0F;

    public AlienParasiteBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber buffEventSubscriber)
    {
        applyStacks(buff, null);
    }

    public void onHasKilledTarget(ActiveBuff buff, MobWasKilledEvent event)
    {
        applyStacks(buff, event.target);
    }

    public void serverTick(ActiveBuff buff)
    {
        // Safety net so the buff cannot lapse during a long stretch with no kills.
        // Does nothing until the existing buff is nearly expired, so in practice
        // this re-applies about once a minute rather than every tick.
        if (buff.owner.buffManager.getBuffDurationLeftSeconds(ADAPTIVEALIENSTACKS) > REFRESH_BELOW_SECONDS)
        {
            return;
        }
        applyStacks(buff, null);
    }

    public void clientTick(ActiveBuff buff)
    {
        // The stack count is derived from kill statistics that only the server
        // holds, and it reaches clients through the buff packet the server sends.
        // There is nothing for the client to recompute.
    }

    private void applyStacks(ActiveBuff buff, Mob justKilled)
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

        int tier = -1;
        for (int i = 0; i < BOSS_TIERS.length; i++)
        {
            if (client.characterStats().mob_kills.getKills(BOSS_TIERS[i]) > 0)
            {
                tier = i;
                break;
            }
        }

        // The kill that triggered this may not have been folded into characterStats
        // yet, so count the mob that just died as well rather than depending on the
        // order the stat update and the buff event happen in.
        if (justKilled != null)
        {
            for (int i = 0; i < BOSS_TIERS.length; i++)
            {
                if (BOSS_TIERS[i].equals(justKilled.getStringID()))
                {
                    if (tier < 0 || i < tier)
                    {
                        tier = i;
                    }
                    break;
                }
            }
        }

        int stacks = tier < 0 ? 1 : BOSS_TIERS.length + 1 - tier;

        // Re-applying recomputes MAX_HEALTH and sends a packet to every nearby
        // client, so skip it while the current buff already says the right thing.
        if (buff.owner.buffManager.getStacks(ADAPTIVEALIENSTACKS) == stacks
                && buff.owner.buffManager.getBuffDurationLeftSeconds(ADAPTIVEALIENSTACKS) > REFRESH_BELOW_SECONDS)
        {
            return;
        }

        // Set the count before adding, and override rather than stack, so the
        // modifiers are recomputed once against the final stack count. Adding onto
        // an existing buff would bump the count by one first, which momentarily
        // lowers max health further before setStacks corrects it.
        ActiveBuff ab = new ActiveBuff(ADAPTIVEALIENSTACKS, buff.owner, STACK_DURATION_SECONDS, null);
        ab.setStacks(stacks, STACK_DURATION_MS, buff.owner);
        buff.owner.buffManager.addBuff(ab, true, true);
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "alienparasitetip"));
        return tooltips;
    }
}
