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
    private static final float STACK_DURATION_SECONDS = 60.0F;
    private static final int STACK_DURATION_MS = 60000;
    private static final float REFRESH_BELOW_SECONDS = 5.0F;
    private static final String[] BOSS_TIERS =
    {
            "ascendedwizard",
            "mutanthydra",
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
            "riftportalmob",
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
        applyStacks(buff, null);
    }

    public void onHasKilledTarget(ActiveBuff buff, MobWasKilledEvent event)
    {
        applyStacks(buff, event.target);
    }

    public void serverTick(ActiveBuff buff)
    {
        if (buff.owner.buffManager.getBuffDurationLeftSeconds(ADAPTIVEALIENSTACKS) > REFRESH_BELOW_SECONDS)
        {
            return;
        }
        applyStacks(buff, null);
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
        if (buff.owner.buffManager.getStacks(ADAPTIVEALIENSTACKS) == stacks && buff.owner.buffManager.getBuffDurationLeftSeconds(ADAPTIVEALIENSTACKS) > REFRESH_BELOW_SECONDS)
        {
            return;
        }
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