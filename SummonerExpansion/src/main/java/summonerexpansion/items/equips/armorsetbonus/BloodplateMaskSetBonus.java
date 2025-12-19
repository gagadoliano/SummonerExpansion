package summonerexpansion.items.equips.armorsetbonus;

import java.awt.*;
import java.util.LinkedList;
import necesse.engine.localization.Localization;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.levelEvent.mobAbilityLevelEvent.MobHealthChangeEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobHealthChangedEvent;
import necesse.entity.mobs.MobWasKilledEvent;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
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

public class BloodplateMaskSetBonus extends SetBonusBuff
{
    public FloatUpgradeValue batDamage = (new FloatUpgradeValue()).setBaseValue(0F).setUpgradedValue(1, 20.0F).setUpgradedValue(10, 100.0F);
    public FloatUpgradeValue summonSpeed = (new FloatUpgradeValue()).setBaseValue(0).setUpgradedValue(1, 0.20F).setUpgradedValue(10, 0.80F);
    public IntUpgradeValue maxHealth = (new IntUpgradeValue()).setBaseValue(0).setUpgradedValue(1, 20).setUpgradedValue(10, 100);

    public BloodplateMaskSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        eventSubscriber.subscribeEvent(MobHealthChangedEvent.class, (event) ->
        {
            if (event.currentHealth < event.lastHealth && !event.fromUpdatePacket)
            {
                ActiveBuff activeBuff = new ActiveBuff(BuffRegistry.BLOODPLATE_COWL_ACTIVE, buff.owner, 4F, null);
                buff.owner.buffManager.addBuff(activeBuff, false);
            }
        });

        buff.setModifier(BuffModifiers.SUMMONS_SPEED, summonSpeed.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.MAX_HEALTH_FLAT, maxHealth.getValue(buff.getUpgradeTier()));
    }

    public void onHasKilledTarget(ActiveBuff buff, MobWasKilledEvent event)
    {
        super.onHasKilledTarget(buff, event);
        if (batDamage.getValue(buff.getUpgradeTier()) > 0 && event.target.isHostile && buff.owner.isItemAttacker && buff.owner.getHealthPercent() <= 0.50F)
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            float count = attackerMob.serverFollowersManager.getFollowerCount("bloodplatesetbuff");
            if (count <= 0.0F)
            {
                GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, batDamage.getValue(buff.getUpgradeTier()));
                Level level = buff.owner.getLevel();
                AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("batsetminion", level);
                attackerMob.serverFollowersManager.addFollower("bloodplatesetbuff", mob, FollowPosition.FLYING_CIRCLE, "summonedmob", 1F, 1, null, false);
                mob.updateDamage(damage);
                Point spawnPoint = new Point(attackerMob.getX() + GameRandom.globalRandom.getIntBetween(-5, 5), attackerMob.getY() + GameRandom.globalRandom.getIntBetween(-5, 5));
                level.entityManager.addMob(mob, (float)spawnPoint.x, (float)spawnPoint.y);
            }

            float healthToAdd = (float)(attackerMob.getMaxHealth() - attackerMob.getHealth()) * 0.03F;
            LevelEvent healthEvent = new MobHealthChangeEvent(attackerMob, GameMath.max(1, (int)healthToAdd));
            buff.owner.getLevel().entityManager.events.add(healthEvent);
        }
    }

    public void serverTick(ActiveBuff buff) {
        super.serverTick(buff);
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "bloodplatemasksettip"));
        if (batDamage.getValue(ab.getUpgradeTier()) > 0)
        {
            tooltips.add(Localization.translate("itemtooltip", "bloodplatemasksettip2"));
        }
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}