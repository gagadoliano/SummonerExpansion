package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.network.Packet;
import necesse.engine.network.packet.PacketBlinkScepter;
import necesse.engine.network.packet.PacketForceOfWind;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.BuffAddedEvent;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffAbility;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.level.maps.Level;

import java.awt.geom.Point2D;

public class LittleAngelBuff extends TrinketBuff implements BuffAbility
{
    public LittleAngelBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.DASH_STACKS, 3);
        buff.setModifier(BuffModifiers.DASH_COOLDOWN, -0.25F);
        buff.setModifier(BuffModifiers.MAX_SUMMONS, 1);
        buff.setModifier(BuffModifiers.ARMOR_FLAT, 10);
        buff.setModifier(BuffModifiers.SUMMON_CRIT_CHANCE, 0.10F);
        buff.setModifier(BuffModifiers.SUMMON_DAMAGE, 0.50F);
        buff.setModifier(BuffModifiers.MELEE_DAMAGE, -0.80F);
        buff.setModifier(BuffModifiers.RANGED_DAMAGE, -0.80F);
        buff.setModifier(BuffModifiers.MAGIC_DAMAGE, -0.80F);
        eventSubscriber.subscribeEvent(BuffAddedEvent.class, (event) ->
        {
            if (event.ab.buff == BuffRegistry.Debuffs.DASH_COOLDOWN && buff.owner.isServer())
            {
                buff.owner.addResilience(5.0F);
                buff.owner.sendResiliencePacket(false);
            }
        });
    }

    public void onWasHit(ActiveBuff buff, MobWasHitEvent event)
    {
        if (buff.owner.buffManager.hasBuff(BuffRegistry.Debuffs.DASH_COOLDOWN))
        {
            buff.owner.buffManager.removeBuff(BuffRegistry.Debuffs.DASH_COOLDOWN, false);
            if (buff.owner.isClient())
            {
                for(int i = 0; i < 5; ++i)
                {
                    int angle = GameRandom.globalRandom.nextInt(360);
                    Point2D.Float dir = GameMath.getAngleDir((float)angle);
                    float range = GameRandom.globalRandom.getFloatBetween(25.0F, 40.0F);
                    float startX = dir.x * range;
                    float startY = 20.0F;
                    float endHeight = 29.0F;
                    float startHeight = endHeight + dir.y * range;
                    int lifeTime = GameRandom.globalRandom.getIntBetween(200, 500);
                    float speed = dir.x * range * 250.0F / (float)lifeTime;
                    buff.owner.getLevel().entityManager.addParticle(buff.owner, startX, startY, Particle.GType.IMPORTANT_COSMETIC).sprite(GameResources.puffParticles.sprite(GameRandom.globalRandom.nextInt(5), 0, 12)).sizeFades(24, 48).rotates().heightMoves(startHeight, endHeight).movesConstant(-speed, 0.0F).fadesAlphaTime(100, 50).lifeTime(lifeTime);
                }
                SoundManager.playSound(GameResources.magicbolt4, SoundEffect.effect(buff.owner).volume(0.2F).pitch(GameRandom.globalRandom.getFloatBetween(1.9F, 2.1F)));
                SoundManager.playSound(GameResources.magicbolt1, SoundEffect.effect(buff.owner).volume(0.2F).pitch(GameRandom.globalRandom.getFloatBetween(1.9F, 2.1F)));
            }
        }
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        Level level = player.getLevel();
        int range = 224;
        Point2D.Float dir = PacketBlinkScepter.getMobDir(player);
        PacketBlinkScepter.applyToMob(level, player, dir.x, dir.y, (float)range);
        PacketForceOfWind.addCooldownStack(player, 5.0F, false);
        if (level.isClient())
        {
            SoundManager.playSound(GameResources.swoosh2, SoundEffect.effect(player).volume(0.5F));
        }
    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        return !buff.owner.isRiding() && !PacketForceOfWind.isOnCooldown(buff.owner);
    }
}