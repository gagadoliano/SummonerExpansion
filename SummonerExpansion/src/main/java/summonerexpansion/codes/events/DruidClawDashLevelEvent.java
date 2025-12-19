package summonerexpansion.codes.events;

import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.LineHitbox;
import necesse.entity.levelEvent.mobAbilityLevelEvent.MobDashLevelEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.particle.Particle;
import summonerexpansion.codes.registry.SummonerBuffs;

import java.awt.*;
import java.awt.geom.Point2D;

public class DruidClawDashLevelEvent extends MobDashLevelEvent 
{
    protected int maxStacks = 100;
    protected boolean addedStack = false;

    public DruidClawDashLevelEvent() {
    }

    public DruidClawDashLevelEvent(Mob owner, int seed, float dirX, float dirY, float distance, int animTime, GameDamage damage, int maxStacks)
    {
        super(owner, seed, dirX, dirY, distance, animTime, damage);
        this.maxStacks = maxStacks;
    }

    public void setupSpawnPacket(PacketWriter writer)
    {
        super.setupSpawnPacket(writer);
        writer.putNextShortUnsigned(this.maxStacks);
        writer.putNextBoolean(this.addedStack);
    }

    public void applySpawnPacket(PacketReader reader)
    {
        super.applySpawnPacket(reader);
        this.maxStacks = reader.getNextShortUnsigned();
        this.addedStack = reader.getNextBoolean();
    }

    public void init()
    {
        super.init();
        if (this.level != null && this.level.isClient() && this.owner != null)
        {
            float forceMod = Math.min((float)this.animTime / 700.0F, 1.0F);
            float forceX = this.dirX * this.distance * forceMod;
            float forceY = this.dirY * this.distance * forceMod;
            for(int i = 0; i < 30; ++i)
            {
                this.level.entityManager.addParticle(this.owner.x + (float) GameRandom.globalRandom.nextGaussian() * 15.0F + forceX / 5.0F, this.owner.y + (float)GameRandom.globalRandom.nextGaussian() * 20.0F + forceY / 5.0F, Particle.GType.IMPORTANT_COSMETIC).movesConstant(forceX * GameRandom.globalRandom.getFloatBetween(0.8F, 1.2F) / 5.0F, forceY * GameRandom.globalRandom.getFloatBetween(0.8F, 1.2F) / 5.0F).color(new Color(200, 200, 220)).height(18.0F).lifeTime(700);
            }
        }

        if (this.owner != null)
        {
            this.owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.INVULNERABLE_ACTIVE, this.owner, this.animTime, null), false);
        }
    }

    public void clientHit(Mob target, Packet content)
    {
        super.clientHit(target, content);
        if (!this.addedStack)
        {
            if (this.owner.buffManager.getStacks(SummonerBuffs.SummonBuffs.CLAW_DASH_STACKS) < this.maxStacks - 1)
            {
                this.owner.buffManager.addBuff(new ActiveBuff(SummonerBuffs.SummonBuffs.CLAW_DASH_STACKS, this.owner, 10.0F, null), false);
                this.owner.buffManager.removeBuff(SummonerBuffs.SummonBuffs.CLAW_DASH_COOLDOWN, false);
            }
            else
            {
                this.owner.buffManager.removeBuff(SummonerBuffs.SummonBuffs.CLAW_DASH_STACKS, false);
                this.owner.buffManager.addBuff(new ActiveBuff(SummonerBuffs.SummonBuffs.CLAW_DASH_COOLDOWN, this.owner, 10.0F, null), false);
            }
            this.addedStack = true;
        }
    }

    public void dealServerDamage(Mob target, boolean isClientSubmitted)
    {
        super.dealServerDamage(target, isClientSubmitted);
        if (!this.addedStack)
        {
            if (this.owner.buffManager.getStacks(SummonerBuffs.SummonBuffs.CLAW_DASH_STACKS) < this.maxStacks - 1) {
                this.owner.buffManager.addBuff(new ActiveBuff(SummonerBuffs.SummonBuffs.CLAW_DASH_STACKS, this.owner, 10.0F, null), true);
                this.owner.buffManager.removeBuff(SummonerBuffs.SummonBuffs.CLAW_DASH_COOLDOWN, true);
            }
            else
            {
                this.owner.buffManager.removeBuff(SummonerBuffs.SummonBuffs.CLAW_DASH_STACKS, true);
                this.owner.buffManager.addBuff(new ActiveBuff(SummonerBuffs.SummonBuffs.CLAW_DASH_COOLDOWN, this.owner, 10.0F, null), true);
            }
            this.addedStack = true;
        }
    }

    public Shape getHitBox()
    {
        Point2D.Float dir;
        if (this.owner.getDir() == 3)
        {
            dir = GameMath.getPerpendicularDir(-this.dirX, -this.dirY);
        }
        else
        {
            dir = GameMath.getPerpendicularDir(this.dirX, this.dirY);
        }

        float width = 40.0F;
        float frontOffset = 20.0F;
        float range = 120.0F;
        float rangeOffset = -40.0F;
        return new LineHitbox(this.owner.x + dir.x * rangeOffset + this.dirX * frontOffset, this.owner.y + dir.y * rangeOffset + this.dirY * frontOffset, dir.x, dir.y, range, width);
    }
}