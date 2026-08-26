package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameRandom;
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
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import summonerexpansion.items.trinkets.minions.PetJellyfishMinion;

import java.awt.*;

public class JellyEssenceEggsBuff extends TrinketBuff
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 80);

    public JellyEssenceEggsBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber buffEventSubscriber)
    {
        buff.setModifier(BuffModifiers.LIFE_ESSENCE_DURATION, 1.50F);
        buff.setModifier(BuffModifiers.LIFE_ESSENCE_GAIN, 1.50F);
        buff.setModifier(BuffModifiers.RESILIENCE_REGEN_FLAT, 3.0F);
        buff.setMaxModifier(BuffModifiers.BLINDNESS, 0F);
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event) {
        buff.owner.setResilienceHidden(0.0F);
    }

    public void serverTick(ActiveBuff buff)
    {
        updateModifiers(buff);
        if (buff.owner.isItemAttacker)
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            float count = attackerMob.serverFollowersManager.getFollowerCount("jellyfishminion");
            if (count <= 0.0F)
            {
                PetJellyfishMinion mob = new PetJellyfishMinion();
                attackerMob.serverFollowersManager.addFollower("jellyfishminion", mob, FollowPosition.WALK_CLOSE, "summonedjellyfishminionbuff", 1.0F, 1, null, false);
                mob.updateDamage(damage);
                Point spawnPoint = new Point(attackerMob.getX() + GameRandom.globalRandom.getIntBetween(-5, 5), attackerMob.getY() + GameRandom.globalRandom.getIntBetween(-5, 5));
                buff.owner.getLevel().entityManager.addMob(mob, (float)spawnPoint.x, (float)spawnPoint.y);
            }
        }
    }

    public void clientTick(ActiveBuff buff)
    {
        updateModifiers(buff);
    }

    public void updateModifiers(ActiveBuff buff)
    {
        if (buff.owner.buffManager.hasBuff(BuffRegistry.LIFE_ESSENCE))
        {
            if (buff.owner.buffManager.getStacks(BuffRegistry.LIFE_ESSENCE) >= 5 * 15)
            {
                buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 50);
                buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.20F);
            }
            else if (buff.owner.buffManager.getStacks(BuffRegistry.LIFE_ESSENCE) >= 4 * 15)
            {
                buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 40);
                buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.20F);
            }
            else if (buff.owner.buffManager.getStacks(BuffRegistry.LIFE_ESSENCE) >= 3 * 15)
            {
                buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 30);
                buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.20F);
            }
            else if (buff.owner.buffManager.getStacks(BuffRegistry.LIFE_ESSENCE) >= 2 * 15)
            {
                buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 20);
                buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.20F);
            }
            else if (buff.owner.buffManager.getStacks(BuffRegistry.LIFE_ESSENCE) >= 15)
            {
                buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 10);
                buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.20F);
            }
            else
            {
                buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 0);
                buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.0F);
            }
        }
        else
        {
            buff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 0);
            buff.setModifier(BuffModifiers.MAX_RESILIENCE, 0.0F);
        }
    }

    public void onRemoved(ActiveBuff buff)
    {
        BuffManager buffManager = buff.owner.buffManager;
        if (buff.owner.isServer() && buffManager.hasBuff("summonedjellyfishminionbuff"))
        {
            buffManager.removeBuff("summonedjellyfishminionbuff", true);
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "jellyessenceeggstip"));
        return tooltips;
    }
}