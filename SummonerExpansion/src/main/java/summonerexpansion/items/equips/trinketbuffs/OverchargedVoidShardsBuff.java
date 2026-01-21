package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

import java.awt.*;

public class OverchargedVoidShardsBuff extends TrinketBuff
{
    public boolean wasHurt;
    public OverchargedVoidShardsBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber buffEventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_MANA_FLAT, 50);
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        Mob owner = buff.owner;
        if (!event.wasPrevented)
        {
            if (owner.buffManager.hasBuff(BuffRegistry.Debuffs.MANA_EXHAUSTION))
            {
                owner.isManaExhausted = false;
                owner.buffManager.removeBuff(BuffRegistry.Debuffs.MANA_EXHAUSTION, false);
            }

            if (owner.isServer() && owner.getMana() < owner.getMaxMana())
            {
                float restoreAmount = (float)event.damage / (float)owner.getMaxHealth();
                owner.setMana(owner.getMana() + restoreAmount * (float)owner.getMaxMana());
                wasHurt = true;
            }
        }
    }

    public void tickEffect(ActiveBuff buff, Mob owner)
    {
        if (wasHurt)
        {
            ParticleTypeSwitcher typeSwitcher = new ParticleTypeSwitcher(Particle.GType.CRITICAL, Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC);
            int particleCount = 10;
            float anglePerParticle = 360.0F / (float)particleCount;
            for(int i = 0; i < particleCount; ++i)
            {
                int angle = (int)((float)i * anglePerParticle + GameRandom.globalRandom.nextFloat() * anglePerParticle);
                float dx = (float)Math.sin(Math.toRadians(angle)) * (float)GameRandom.globalRandom.getIntBetween(25, 50);
                float dy = (float)Math.cos(Math.toRadians(angle)) * (float)GameRandom.globalRandom.getIntBetween(25, 50);
                owner.getLevel().entityManager.addParticle(owner, typeSwitcher.next()).color(new Color(131, 198, 247)).movesFriction(dx, dy, 0.8F).sprite(GameResources.magicSparkParticles.sprite(GameRandom.globalRandom.nextInt(4), 0, 22)).sizeFades(30, 40).givesLight(180.0F, 200.0F).lifeTime(500);
            }
            wasHurt = false;
        }
    }

    public void clientTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    public void serverTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    private void updateModifiers(ActiveBuff buff)
    {
        Mob owner = buff.owner;
        if (owner.buffManager.hasBuff(BuffRegistry.Debuffs.MANA_EXHAUSTION))
        {
            buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 1.20f);
        }
        else if (owner.getMana() >= owner.getMaxMana())
        {
            buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 0.90f);
        }
        else
        {
            buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 1.00f);
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "overchargedvstip"));
        return tooltips;
    }
}