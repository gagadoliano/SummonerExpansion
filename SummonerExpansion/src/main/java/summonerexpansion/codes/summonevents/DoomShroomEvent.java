package summonerexpansion.codes.summonevents;

import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.PointSetAbstract;
import necesse.entity.levelEvent.mobAbilityLevelEvent.HitboxEffectEvent;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.gfx.GameResources;
import necesse.level.maps.LevelObjectHit;

import java.awt.*;
import java.util.HashSet;

public class DoomShroomEvent extends HitboxEffectEvent implements Attacker
{
    public static float debuffDuration = 10.0F;
    private int lifeTime = 0;
    private HashSet<Integer> hits = new HashSet();

    public DoomShroomEvent() {
    }

    public DoomShroomEvent(Mob owner) {
        super(owner, new GameRandom());
    }

    public void setupSpawnPacket(PacketWriter writer)
    {
        super.setupSpawnPacket(writer);
        writer.putNextShortUnsigned(lifeTime);
    }

    public void applySpawnPacket(PacketReader reader)
    {
        super.applySpawnPacket(reader);
        lifeTime = reader.getNextShortUnsigned();
    }

    public void init()
    {
        super.init();
        hitsObjects = false;
        hits = new HashSet();
        if (owner != null)
        {
            SoundManager.playSound(GameResources.explosionHeavy, SoundEffect.effect(owner).volume(1.0F).pitch(0.8F));
        }
    }

    public void clientTick()
    {
        super.clientTick();
        lifeTime += 50;
        if (lifeTime >= 600)
        {
            over();
        }
    }

    public void serverTick()
    {
        super.serverTick();
        lifeTime += 50;
        if (lifeTime >= 600)
        {
            over();
        }
    }

    public Shape getHitBox()
    {
        if (owner != null)
        {
            int size = 380;
            return new Rectangle(owner.getX() - size / 2, owner.getY() - size / 2, size, size);
        }
        else
        {
            return new Rectangle();
        }
    }

    public boolean canHit(Mob mob) {
        return super.canHit(mob) && !hits.contains(mob.getUniqueID());
    }

    public void clientHit(Mob target) {
        hits.add(target.getUniqueID());
    }

    public void serverHit(Mob target, boolean clientSubmitted)
    {
        if (clientSubmitted || !hits.contains(target.getUniqueID()))
        {
            target.buffManager.addBuff(new ActiveBuff(BuffRegistry.FOW_ACTIVE, target, 0.2F, this), true);
            target.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.ABLAZE, target, debuffDuration, this), true);
            float modifier = target.getKnockbackModifier();
            if (modifier != 0.0F)
            {
                float knockback = 250.0F / modifier;
                target.isServerHit(new GameDamage(0.0F), target.x - owner.x, target.y - owner.y, knockback, owner);
            }
            hits.add(target.getUniqueID());
        }
    }

    public void hitObject(LevelObjectHit hit) {
    }

    public PointSetAbstract<?> getRegionPositions()
    {
        return this.owner != null ? this.owner.getRegionPositions() : super.getRegionPositions();
    }

    public Point getSaveToRegionPos()
    {
        return this.owner != null ? new Point(this.level.regionManager.getRegionCoordByTile(this.owner.getTileX()), this.level.regionManager.getRegionCoordByTile(this.owner.getTileY())) : super.getSaveToRegionPos();
    }
}