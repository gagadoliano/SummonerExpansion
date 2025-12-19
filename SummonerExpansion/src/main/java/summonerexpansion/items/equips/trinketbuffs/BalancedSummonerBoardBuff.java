package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffManager;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.DryadSpiritFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.SpacerGameTooltip;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.trinketItem.TrinketItem;

import java.awt.*;
import java.awt.geom.Point2D;

public class BalancedSummonerBoardBuff extends TrinketBuff
{
    public BalancedSummonerBoardBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.SUMMON_DAMAGE, 0.15F);
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onHasAttacked(buff, event);
        if (!event.wasPrevented && event.damageType == DamageTypeRegistry.SUMMON)
        {
            event.target.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.FROSTBURN, event.target, 10.0F, event.attacker), event.target.isServer());

            BuffManager targetBM = event.target.buffManager;
            targetBM.addBuff(new ActiveBuff(BuffRegistry.Debuffs.DRYAD_HAUNTED, event.target, 10.0F, event.attacker), event.target.isServer());
            if (targetBM.getStacks(BuffRegistry.Debuffs.DRYAD_HAUNTED) >= 10)
            {
                targetBM.removeBuff(BuffRegistry.Debuffs.DRYAD_HAUNTED, true);
                spawnDryadSpirit(event.attacker.getAttackOwner());
            }
        }
    }

    public static void spawnDryadSpirit(Mob owner)
    {
        if (owner != null && owner.isServer())
        {
            int maxSummons = 5;
            DryadSpiritFollowingMob summonedMob = (DryadSpiritFollowingMob) MobRegistry.getMob("dryadspirit", owner.getLevel());
            ((ItemAttackerMob)owner).serverFollowersManager.addFollower("summonedmobtemp", summonedMob, FollowPosition.FLYING_CIRCLE_FAST, "summonedmob", 1.0F, (p) -> maxSummons, null, false);
            Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(summonedMob, owner.getLevel(), owner.x, owner.y);
            owner.getLevel().entityManager.addMob(summonedMob, spawnPoint.x, spawnPoint.y);
        }
    }

    public void clientTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    public void serverTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    public void updateModifiers(ActiveBuff buff)
    {
        if (buff.owner instanceof PlayerMob)
        {
            int uniqueFollowerCount = ((PlayerMob)buff.owner).serverFollowersManager.getUniqueFollowerCount();
            if (uniqueFollowerCount > 0)
            {
                BuffManager buffManager = buff.owner.buffManager;
                if (buffManager.hasBuff(BuffRegistry.SUMMONERS_BESTIARY))
                {
                    buffManager.removeBuff(BuffRegistry.SUMMONERS_BESTIARY, true);
                }
                ActiveBuff ab = new ActiveBuff(BuffRegistry.SUMMONERS_BESTIARY, buff.owner, 1000, null);
                ab.setStacks(uniqueFollowerCount, 100, null);
                buffManager.addBuff(ab, true);
            }
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "balancedsummonerboardtip"));
        tooltips.add(new SpacerGameTooltip(5));
        tooltips.add(new StringTooltips(Localization.translate("itemtooltip", "dryadhauntweapontip", "value", 1), new Color(30, 177, 143), 400));
        return tooltips;
    }
}