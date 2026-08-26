package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.AuraTrinketBuff;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import necesse.level.maps.Level;

import java.awt.*;

public class JonasTrophyBuff extends AuraTrinketBuff
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 50);

    public JonasTrophyBuff() {
        super(500);
    }

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.ARMOR_FLAT, 50);
        buff.setModifier(BuffModifiers.SUMMON_DAMAGE, 0.25F);
        buff.setModifier(BuffModifiers.SUMMON_CRIT_CHANCE, 0.20F);
        buff.setModifier(BuffModifiers.MAX_SUMMONS, 2);
        buff.setModifier(BuffModifiers.SPEED, -0.4F);
    }

    public void tickEffect(ActiveBuff buff, Mob owner) {
    }

    public Buff getAuraBuff(Mob mob)
    {
        return BuffRegistry.getBuff("silvergobletbuff");
    }

    public void serverTick(ActiveBuff buff)
    {
        if (buff.owner.isItemAttacker)
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            float count = attackerMob.serverFollowersManager.getFollowerCount("livingarmorstandminion");
            if (count <= 0.0F)
            {
                Level level = buff.owner.getLevel();
                AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("livingarmorstandminion", level);
                attackerMob.serverFollowersManager.addFollower("livingarmorstandminion", mob, FollowPosition.PYRAMID, "summonedlivingarmorstandminionbuff", 1, 1, null, false);
                mob.updateDamage(damage);
                mob.setRemoveWhenNotInInventory(ItemRegistry.getItem("jonastrophy"), CheckSlotType.TRINKETS);
                Point spawnPoint = new Point(attackerMob.getX() + GameRandom.globalRandom.getIntBetween(-5, 5), attackerMob.getY() + GameRandom.globalRandom.getIntBetween(-5, 5));
                level.entityManager.addMob(mob, (float)spawnPoint.x, (float)spawnPoint.y);
            }
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "jonastrophytip"));
        return tooltips;
    }
}