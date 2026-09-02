package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import summonerexpansion.codes.registries.RegistryTrinkets;

import java.awt.*;

public class GoldenCrossBuff extends TrinketBuff
{
    public GoldenCrossBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.ATTACK_MOVEMENT_MOD, 0.80F);
        eventSubscriber.subscribeEvent(MobBeforeDamageOverTimeTakenEvent.class, MobBeforeDamageOverTimeTakenEvent::prevent);
    }

    public void onBeforeHitCalculated(ActiveBuff buff, MobBeforeHitCalculatedEvent event)
    {
        super.onBeforeHitCalculated(buff, event);
        if(!buff.owner.buffManager.hasBuff("goldencrosscooldown"))
        {
            event.prevent();
            event.showDamageTip = false;
            event.playHitSound = false;
        }
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onWasHit(buff, event);
        if (event.wasPrevented)
        {
            if (buff.owner.isClient())
            {
                int particleCount = 20;
                for(int i = 0; i < particleCount; ++i)
                {
                    buff.owner.getLevel().entityManager.addParticle(buff.owner.x + (float)(GameRandom.globalRandom.nextGaussian() * (double)8.0F), buff.owner.y + 16.0F + (float)(GameRandom.globalRandom.nextGaussian() * (double)8.0F), Particle.GType.IMPORTANT_COSMETIC).sprite(GameResources.starParticles.sprite(0, 0, 32)).lifeTime(750).fadesAlphaTime(100, 250).movesFriction(16.0F * (float)GameRandom.globalRandom.nextGaussian(), 5.0F * (float)GameRandom.globalRandom.nextGaussian(), 1.0F).sizeFades(14, 18).heightMoves(20.0F, 64.0F);
                }
            }
            buff.owner.buffManager.addBuff(new ActiveBuff(RegistryTrinkets.TrinketBuffs.CROSSCOOLDOWN, buff.owner, 20.5f, null), false);
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "goldencrosstip"));
        return tooltips;
    }
}