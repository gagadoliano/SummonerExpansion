package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.projectile.Projectile;
import necesse.inventory.item.DoubleItemStatTip;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.level.maps.Level;
import summonerexpansion.allprojs.armorprojs.SharpshooterProj;

import java.awt.*;
import java.util.Comparator;
import java.util.LinkedList;

public class SharpshooterSummonSetBonus extends SetBonusBuff
{
    public FloatUpgradeValue summonAttackSpeed = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(0.15F).setUpgradedValue(1.0F, 0.20F);
    public FloatUpgradeValue minionDamage = (new FloatUpgradeValue(0F, 0.2F)).setBaseValue(25F).setUpgradedValue(1.0F, 25.0F);

    public SharpshooterSummonSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.SUMMON_ATTACK_SPEED, summonAttackSpeed.getValue(buff.getUpgradeTier()));
    }

    public void serverTick(ActiveBuff buff)
    {
        super.serverTick(buff);
        if (buff.owner.isItemAttacker)
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob) buff.owner;
            float count = attackerMob.serverFollowersManager.getFollowerCount("summonedsharpshooterbuff");
            if (count <= 0.0F)
            {
                GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, minionDamage.getValue(buff.getUpgradeTier()));
                Level level = buff.owner.getLevel();
                AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("sharpshooterminion", level);
                attackerMob.serverFollowersManager.addFollower("summonedsharpshooterbuff", mob, FollowPosition.WALK_CLOSE, "summonedsharpshooterbuff", 1.0F, 1, null, false);
                mob.updateDamage(damage);
                mob.setRemoveWhenNotInInventory(ItemRegistry.getItem("sharpshootersummonhat"), CheckSlotType.HELMET);
                Point spawnPoint = new Point(attackerMob.getX() + GameRandom.globalRandom.getIntBetween(-5, 5), attackerMob.getY() + GameRandom.globalRandom.getIntBetween(-5, 5));
                level.entityManager.addMob(mob, (float)spawnPoint.x, (float)spawnPoint.y);
            }
        }
    }

    public void onHasKilledTarget(ActiveBuff buff, MobWasKilledEvent event)
    {
        if (this.mobIsValidTarget(buff.owner, event.target))
        {
            Mob validTarget = this.getValidTarget(buff.owner, event.target.x, event.target.y);
            if (validTarget != null)
            {
                if (event.attacker instanceof SharpshooterProj)
                {
                    SharpshooterProj projectile = (SharpshooterProj)event.attacker;
                    Projectile newProjectile = projectile.getRicochetProjectile(event.target.x, event.target.y, validTarget.x, validTarget.y, validTarget);
                    validTarget.getLevel().entityManager.projectiles.add(newProjectile);
                }
            }
        }
    }

    private Mob getValidTarget(Mob owner, float fromX, float fromY)
    {
        return GameUtils.streamTargets(owner, GameUtils.rangeBounds(fromX, fromY, 160)).filter((m) -> this.mobIsValidTarget(owner, m)).filter((m) -> m.getDistance(fromX, fromY) <= 160.0F).min(Comparator.comparing((m) -> m.getDistance(fromX, fromY))).orElse(null);
    }

    protected boolean mobIsValidTarget(Mob owner, Mob potentialTarget)
    {
        return owner.isHostile || potentialTarget.isHostile || potentialTarget instanceof TrainingDummyMob;
    }

    public void onRemoved(ActiveBuff buff)
    {
        super.onRemoved(buff);
        if (buff.owner.isServer() && buff.owner.buffManager.hasBuff("summonedsharpshooterbuff"))
        {
            buff.owner.buffManager.removeBuff("summonedsharpshooterbuff", true);
        }
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
        float damage = minionDamage.getValue(currentValues.getUpgradeTier());
        if (currentValues.owner != null)
        {
            damage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
        }
        DoubleItemStatTip minionDamageTip = new DoubleItemStatTip(damage, 0)
        {
            public GameMessage toMessage(Color betterColor, Color worseColor, Color neutralColor, boolean showDifference)
            {
                return (new GameMessageBuilder()).append(new LocalMessage("itemtooltip", "sharpshootersummonsettip")).append("\n").append(new LocalMessage("itemtooltip", "sharpshootersummonsettip2", "damage", this.getReplaceValue(betterColor, worseColor, showDifference)));
            }
        };
        if (lastValues != null)
        {
            float compareDamage = minionDamage.getValue(lastValues.getUpgradeTier());
            if (lastValues.owner != null)
            {
                compareDamage *= GameDamage.getDamageModifier(currentValues.owner, DamageTypeRegistry.SUMMON);
            }
            minionDamageTip.setCompareValue(compareDamage);
        }
        list.add(minionDamageTip);
    }
}