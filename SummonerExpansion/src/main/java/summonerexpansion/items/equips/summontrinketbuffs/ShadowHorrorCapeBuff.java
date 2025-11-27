package summonerexpansion.items.equips.summontrinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
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

public class ShadowHorrorCapeBuff extends TrinketBuff
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 35);

    public ShadowHorrorCapeBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber buffEventSubscriber)
    {
        buff.setModifier(BuffModifiers.SPEED, 0.20F);
        buff.setModifier(BuffModifiers.MAX_SUMMONS, 1);
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
        tooltips.add(Localization.translate("itemtooltip", "shadowhorrorcapetip"));
        return tooltips;
    }
}