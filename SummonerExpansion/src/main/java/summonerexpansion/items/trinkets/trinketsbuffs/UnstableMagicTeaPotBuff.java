package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.explosionEvent.BombExplosionEvent;
import necesse.entity.levelEvent.explosionEvent.splashEvent.*;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffManager;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.ThemeColorRegistry;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import necesse.level.maps.Level;
import summonerexpansion.codes.events.CookpotHealEvent;

import java.awt.*;

public class UnstableMagicTeaPotBuff extends TrinketBuff
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 80);
    public int splashCap = 0;
    public int splashCooldown = 0;

    public UnstableMagicTeaPotBuff() {}

    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.MAX_FOOD_BUFFS, 2);
        activeBuff.setModifier(BuffModifiers.POTION_DURATION, 2.50F);
    }

    public void serverTick(ActiveBuff buff)
    {
        if (splashCap >= 3)
        {
            splashCooldown = 300;
        }
        if (splashCooldown > 0)
        {
            splashCooldown--;
            splashCap = 0;
        }
        if (buff.owner.isItemAttacker)
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            float count = attackerMob.serverFollowersManager.getFollowerCount("teapotminion");
            if (count <= 0F)
            {
                Level level = buff.owner.getLevel();
                AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("teapotminion", level);
                attackerMob.serverFollowersManager.addFollower("teapotminion", mob, FollowPosition.WALK_CLOSE, "summonedteapotminionbuff", 1F, 1, null, false);
                mob.updateDamage(damage);
                Point spawnPoint = new Point(attackerMob.getX() + GameRandom.globalRandom.getIntBetween(-5, 5), attackerMob.getY() + GameRandom.globalRandom.getIntBetween(-5, 5));
                level.entityManager.addMob(mob, (float)spawnPoint.x, (float)spawnPoint.y);
            }
        }
    }

    public void onRemoved(ActiveBuff buff)
    {
        BuffManager buffManager = buff.owner.buffManager;
        if (buff.owner.isServer() && buffManager.hasBuff("summonedteapotminionbuff"))
        {
            buffManager.removeBuff("summonedteapotminionbuff", true);
        }
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event)
    {
        float splashRange = buff.owner.buffManager.getModifier(BuffModifiers.SUMMONS_TARGET_RANGE);
        if (!event.wasPrevented && event.damageType == DamageTypeRegistry.SUMMON && splashCooldown <= 0)
        {
            if(GameRandom.globalRandom.getChance(0.01F))
            {
                buff.owner.getLevel().entityManager.events.add(new CookpotHealEvent(event.target.x, event.target.y, (int)(100 * splashRange), new GameDamage(0F), 0.0F, buff.owner));
                splashCap++;
            }
            if (GameRandom.globalRandom.getChance(0.01F))
            {
                buff.owner.getLevel().entityManager.events.add(new SmiteSplashEvent(event.target.x, event.target.y, (int)(120 * splashRange), damage.modFinalMultiplier(0.6F), 0.0F, buff.owner));
                splashCap++;
            }
            if (GameRandom.globalRandom.getChance(0.01F))
            {
                buff.owner.getLevel().entityManager.events.add(new SimpleSplashEvent(event.target.x, event.target.y, (int)(90 * splashRange), new GameDamage(0.0F), 0.0F, buff.owner, BuffRegistry.Debuffs.GENERIC_ONFIRE, 3000, ThemeColorRegistry.FIRE));
                splashCap++;
            }
            if (GameRandom.globalRandom.getChance(0.01F))
            {
                buff.owner.getLevel().entityManager.events.add(new SimpleSplashEvent(event.target.x, event.target.y, (int)(90 * splashRange), new GameDamage(0.0F), 0.0F, buff.owner, BuffRegistry.Debuffs.GENERIC_POISON, 3000, ThemeColorRegistry.POISON));
                splashCap++;
            }
            if (GameRandom.globalRandom.getChance(0.01F))
            {
                buff.owner.getLevel().entityManager.events.add(new BombExplosionEvent(event.target.x, event.target.y, (int)(200 * splashRange), damage.modFinalMultiplier(2F), false, false, 0.0F, buff.owner));
                splashCap++;
            }
            if (GameRandom.globalRandom.getChance(0.01F))
            {
                buff.owner.getLevel().entityManager.events.add(new NecroPoisonSplashEvent(event.target.x, event.target.y, (int)(90 * splashRange), damage.modFinalMultiplier(0.2F), 0.0F, buff.owner));
                splashCap++;
            }
            if (GameRandom.globalRandom.getChance(0.01F))
            {
                buff.owner.getLevel().entityManager.events.add(new SlimeSplashEvent(event.target.x, event.target.y, (int)(50 * splashRange), damage.modFinalMultiplier(0.5F), 0.0F, buff.owner));
                splashCap++;
            }
            if (GameRandom.globalRandom.getChance(0.01F))
            {
                buff.owner.getLevel().entityManager.events.add(new FreezeSplashEvent(event.target.x, event.target.y, (int)(90 * splashRange), new GameDamage(0F), 0.0F, buff.owner, 5000));
                splashCap++;
            }
            if (GameRandom.globalRandom.getChance(0.01F))
            {
                buff.owner.getLevel().entityManager.events.add(new PolymorphSplashEvent(event.target.x, event.target.y, (int)(120 * splashRange), new GameDamage(0F), 0.0F, buff.owner, 5000));
                splashCap++;
            }
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "unstablemagicteapottip"));
        return tooltips;
    }
}