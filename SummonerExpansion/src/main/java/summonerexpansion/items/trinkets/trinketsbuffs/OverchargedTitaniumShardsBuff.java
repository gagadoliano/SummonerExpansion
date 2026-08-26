package summonerexpansion.items.trinkets.trinketsbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
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
import summonerexpansion.codes.events.TitaniumLightningLevelEvent;

import java.awt.*;

import static summonerexpansion.codes.registries.RegistryTrinkets.TrinketBuffs.TITANIUMSHARDSTACKS;

public class OverchargedTitaniumShardsBuff extends TrinketBuff
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 40);
    public boolean wasHurt;

    public OverchargedTitaniumShardsBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_MANA_FLAT, 80);
        eventSubscriber.subscribeEvent(MobManaChangedEvent.class, (event) ->
        {
            if (!buff.owner.isClient())
            {
                if (event.currentMana < event.lastMana && !event.fromUpdatePacket)
                {
                    float manaSpent = event.lastMana - event.currentMana;
                    ActiveBuff manabuff = buff.owner.buffManager.getBuff(TITANIUMSHARDSTACKS);
                    int manaBuffStacks = manabuff == null ? 0 : manabuff.getStacks();
                    int manaNeededForSpawn = 10;
                    GameRandom random = GameRandom.globalRandom;
                    if ((float)manaBuffStacks + manaSpent >= (float)(manaNeededForSpawn - 1))
                    {
                        Point targetPoints = new Point((int)buff.owner.x + random.getIntBetween(-60, 60), (int)buff.owner.y + random.getIntBetween(-60, 60));
                        TitaniumLightningLevelEvent TitaniumEvent = new TitaniumLightningLevelEvent(buff.owner, new GameRandom(), targetPoints, damage, 0.1F);
                        buff.owner.getLevel().entityManager.events.add(TitaniumEvent);
                        buff.owner.buffManager.removeBuff(TITANIUMSHARDSTACKS, true);
                    }
                    int exceeding = (int)((float)manaBuffStacks + manaSpent) - manaNeededForSpawn;
                    int add = (int)manaSpent;
                    if (exceeding > 0)
                    {
                        add = exceeding;
                    }
                    float currentBelowOne = manaSpent - (float)((int)manaSpent);
                    float manaBelowOneBuffer = buff.getGndData().getFloat("manaBelowOneBuffer");
                    manaBelowOneBuffer += currentBelowOne;
                    if (manaBelowOneBuffer >= 1.0F)
                    {
                        --manaBelowOneBuffer;
                        ++add;
                    }
                    buff.getGndData().setFloat("manaBelowOneBuffer", manaBelowOneBuffer);
                    for(int i = 0; i < add; ++i)
                    {
                        ActiveBuff activeBuff = new ActiveBuff(TITANIUMSHARDSTACKS, buff.owner, 61F, null);
                        TitaniumShardsStacksBuff buffbuff = (TitaniumShardsStacksBuff)activeBuff.buff;
                        buffbuff.setManaNeeded(manaNeededForSpawn);
                        buff.owner.buffManager.addBuff(activeBuff, true);
                    }
                }
            }
        });
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
            buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 1.40f);
        }
        else if (owner.getMana() >= owner.getMaxMana())
        {
            buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 0.80f);
        }
        else
        {
            buff.setModifier(BuffModifiers.INCOMING_DAMAGE_MOD, 1.00f);
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "overchargedtitaniumtip"));
        return tooltips;
    }
}