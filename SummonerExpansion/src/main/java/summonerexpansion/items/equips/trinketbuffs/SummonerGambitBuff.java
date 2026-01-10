package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.network.Packet;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffAbility;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.inventory.item.ItemStatTip;

import java.util.LinkedList;

public class SummonerGambitBuff extends TrinketBuff implements BuffAbility
{
    public SummonerGambitBuff()
    {
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_SUMMONS, 1);
        buff.setModifier(BuffModifiers.ARMOR_FLAT, 10);
        buff.setModifier(BuffModifiers.SUMMON_CRIT_CHANCE, 0.10F);
        buff.setModifier(BuffModifiers.SUMMON_DAMAGE, 0.50F);
        buff.setModifier(BuffModifiers.MELEE_DAMAGE, -0.50F);
        buff.setModifier(BuffModifiers.RANGED_DAMAGE, -0.50F);
        buff.setModifier(BuffModifiers.MAGIC_DAMAGE, -0.50F);
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content) {}

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content) {
        return true;
    }
}
