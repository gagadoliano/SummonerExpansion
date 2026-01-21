package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

import static summonerexpansion.codes.registry.SummonerBuffs.SummonBuffs.ADAPTIVEALIENSTACKS;

public class AlienParasiteBuff extends TrinketBuff
{
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
        updateModifiers(buff);
    }

    public void updateModifiers(ActiveBuff buff)
    {
        ActiveBuff ab = new ActiveBuff(ADAPTIVEALIENSTACKS, buff.owner, 1F, null);
        if (buff.owner.isClient())
        {
            if (buff.owner.getClient().characterStats.mob_kills.getKills("ascendedwizard") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(20, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("crystaldragon") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(19, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("moonlightdancer") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(18, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("sunlightchampion") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(17, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("spiderempress") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(16, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("nightswarm") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(15, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("motherslime") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(14, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("fallenwizard") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(13, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("sageandgrit") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(12, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("pestwarden") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(11, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("cryoqueen") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(10, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("reaper") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(9, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("piratecaptain") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(8, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("ancientvulture") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(7, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("swampguardian") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(6, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("chieftain") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(5, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("voidwizard") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(4, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("queenspider") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(3, 60, buff.owner);
            }
            else if (buff.owner.getClient().characterStats.mob_kills.getKills("evilsprotector") > 0)
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(2, 60, buff.owner);
            }
            else
            {
                buff.owner.buffManager.addBuff(ab, false).setStacks(1, 60, buff.owner);
            }
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "alienparasitetip"));
        return tooltips;
    }
}