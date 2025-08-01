package summonerexpansion.summontrinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

import java.util.function.BiConsumer;

public class FrenzyHorrorCapeBuff extends TrinketBuff
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 50);

    public FrenzyHorrorCapeBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        this.updateBuff(buff);
        this.isImportant = true;
        buff.setModifier(BuffModifiers.SPEED, 0.20F);
        buff.setModifier(BuffModifiers.MAX_SUMMONS, 1);
    }

    public void serverTick(ActiveBuff buff)
    {
        this.updateBuff(buff);
        this.updateActiveBuff(buff);
    }

    public void clientTick(ActiveBuff buff)
    {
        this.updateBuff(buff);
        this.updateActiveBuff(buff);
    }

    public void updateActiveBuff(ActiveBuff buff)
    {
        float healthPercent = buff.owner.getHealthPercent();
        if (healthPercent < 0.5F && buff.owner.buffManager.getBuffDurationLeftSeconds(BuffRegistry.BLOODSTONE_RING_REGEN_ACTIVE_BUFF) <= 1.0F)
        {
            ActiveBuff activeBuff = new ActiveBuff(BuffRegistry.BLOODSTONE_RING_REGEN_ACTIVE_BUFF, buff.owner, 4.0F, (Attacker)null);
            buff.owner.buffManager.addBuff(activeBuff, true);
        }
        else if (healthPercent >= 0.5F && buff.owner.buffManager.hasBuff(BuffRegistry.BLOODSTONE_RING_REGEN_ACTIVE_BUFF))
        {
            buff.owner.buffManager.removeBuff(BuffRegistry.BLOODSTONE_RING_REGEN_ACTIVE_BUFF, true);
        }
    }

    private void updateBuff(ActiveBuff buff)
    {
        float current = buff.getModifier(BuffModifiers.SUMMON_DAMAGE);
        float next = getAttackBonusPerc((float)buff.owner.getHealth() / (float)buff.owner.getMaxHealth(), 0.1F) * 0.5F;
        next = GameMath.toDecimals(next, 2);
        if (current != next)
        {
            buff.setModifier(BuffModifiers.SUMMON_DAMAGE, next);
            buff.forceManagerUpdate();
        }
    }

    public static float getAttackBonusPerc(float healthPercent, float offset)
    {
        if (offset != 0.0F)
        {
            healthPercent = (offset - healthPercent) / (offset - 1.0F);
        }
        healthPercent = GameMath.limit(healthPercent, 0.0F, 1.0F);
        return Math.abs(healthPercent - 1.0F);
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onWasHit(buff, event);
        if (!event.wasPrevented && buff.owner.isServer())
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            FlyingAttackingFollowingMob mob = (FlyingAttackingFollowingMob) MobRegistry.getMob("horrorbatminion", buff.owner.getLevel());
            attackerMob.serverFollowersManager.addFollower("horrorcapebuff", mob, FollowPosition.FLYING_CIRCLE, "summonedmob", 1.0F, 3, (BiConsumer)null, false);
            mob.updateDamage(damage);
            mob.getLevel().entityManager.addMob(mob, buff.owner.x, buff.owner.y);
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "frenzyhorrorcapetip"));
        return tooltips;
    }
}