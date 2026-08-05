package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
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

public class DragonicFlamesBuff extends TrinketBuff
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 5);

    public DragonicFlamesBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MELEE_DAMAGE, 0.10F);
        buff.setModifier(BuffModifiers.MAGIC_DAMAGE, 0.10F);
        buff.setModifier(BuffModifiers.RANGED_DAMAGE, 0.10F);
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event)
    {
        if (!event.wasPrevented && buff.owner.isItemAttacker && buff.owner.isServer())
        {
            if (event.damageType == DamageTypeRegistry.MELEE)
            {
                ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
                float count = attackerMob.serverFollowersManager.getFollowerCount("dragonicflamesminion");
                if (count <= 0.0F)
                {
                    FlyingAttackingFollowingMob mob = (FlyingAttackingFollowingMob) MobRegistry.getMob("dragonicflamesminion1", buff.owner.getLevel());
                    attackerMob.serverFollowersManager.addFollower("dragonicflamesminion", mob, FollowPosition.FLYING, "summonedmob", 1, 1, null, false);
                    mob.updateDamage(damage);
                    mob.getLevel().entityManager.addMob(mob, buff.owner.x, buff.owner.y);
                }
            }
            if (event.damageType == DamageTypeRegistry.MAGIC)
            {
                ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
                float count = attackerMob.serverFollowersManager.getFollowerCount("dragonicflamesminion");
                if (count <= 0.0F)
                {
                    FlyingAttackingFollowingMob mob = (FlyingAttackingFollowingMob) MobRegistry.getMob("dragonicflamesminion2", buff.owner.getLevel());
                    attackerMob.serverFollowersManager.addFollower("dragonicflamesminion", mob, FollowPosition.FLYING, "summonedmob", 1, 1, null, false);
                    mob.updateDamage(damage);
                    mob.getLevel().entityManager.addMob(mob, buff.owner.x, buff.owner.y);
                }
            }
            if (event.damageType == DamageTypeRegistry.RANGED)
            {
                ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
                float count = attackerMob.serverFollowersManager.getFollowerCount("dragonicflamesminion");
                if (count <= 0.0F)
                {
                    FlyingAttackingFollowingMob mob = (FlyingAttackingFollowingMob) MobRegistry.getMob("dragonicflamesminion3", buff.owner.getLevel());
                    attackerMob.serverFollowersManager.addFollower("dragonicflamesminion", mob, FollowPosition.FLYING, "summonedmob", 1, 1, null, false);
                    mob.updateDamage(damage);
                    mob.getLevel().entityManager.addMob(mob, buff.owner.x, buff.owner.y);
                }
            }
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "dragonicflamestip"));
        return tooltips;
    }
}