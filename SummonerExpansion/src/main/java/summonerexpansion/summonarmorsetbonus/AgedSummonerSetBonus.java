package summonerexpansion.summonarmorsetbonus;

import java.awt.*;
import java.util.LinkedList;
import necesse.engine.localization.Localization;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffManager;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;

public class AgedSummonerSetBonus extends SetBonusBuff
{
    public IntUpgradeValue maxResilience = (new IntUpgradeValue()).setBaseValue(30).setUpgradedValue(1.0F, 30);
    public FloatUpgradeValue resilienceGain = (new FloatUpgradeValue()).setBaseValue(0.2F).setUpgradedValue(1.0F, 0.2F);
    public FloatUpgradeValue agedDamage = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(25F).setUpgradedValue(1.0F, 25.0F);

    public AgedSummonerSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, maxResilience.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.RESILIENCE_GAIN, resilienceGain.getValue(buff.getUpgradeTier()));
    }

    public void clientTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    public void serverTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    private void updateModifiers(ActiveBuff buff)
    {
        boolean fullHealth = buff.owner.getHealthPercent() == 1.0F;
        if (buff.owner.isItemAttacker && fullHealth)
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            float count = attackerMob.serverFollowersManager.getFollowerCount("agedchampionminion");
            if (count <= 0.0F)
            {
                GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, agedDamage.getValue(buff.getUpgradeTier()));
                Level level = buff.owner.getLevel();
                AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("agedchampionminion", level);
                attackerMob.serverFollowersManager.addFollower("agedchampionminion", mob, FollowPosition.WALK_CLOSE, "summonedagedchampionminionbuff", 1.0F, 1, null, false);
                mob.updateDamage(damage);
                Point spawnPoint = new Point(attackerMob.getX() + GameRandom.globalRandom.getIntBetween(-5, 5), attackerMob.getY() + GameRandom.globalRandom.getIntBetween(-5, 5));
                level.entityManager.addMob(mob, (float)spawnPoint.x, (float)spawnPoint.y);
            }
        }
        else
        {
            BuffManager buffManager = buff.owner.buffManager;
            if (buff.owner.isServer() && buffManager.hasBuff("summonedagedchampionminionbuff"))
            {
                buffManager.removeBuff("summonedagedchampionminionbuff", true);
            }
        }
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onWasHit(buff, event);
        if (buff.owner.buffManager.hasBuff(BuffRegistry.PERFECT_BLOCK))
        {
            buff.owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.AGED_CHAMPION_PROWESS, buff.owner, 5000, null), false);
        }
    }

    public void onRemoved(ActiveBuff buff)
    {
        super.onRemoved(buff);
        BuffManager buffManager = buff.owner.buffManager;
        if (buff.owner.isServer() && buffManager.hasBuff("summonedagedchampionminionbuff"))
        {
            buffManager.removeBuff("summonedagedchampionminionbuff", true);
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "agedsummonersettip"));
        return tooltips;
    }
}