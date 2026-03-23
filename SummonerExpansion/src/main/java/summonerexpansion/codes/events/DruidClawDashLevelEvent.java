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

import java.awt.*;
import java.awt.geom.Point2D;

import static summonerexpansion.codes.registries.RegistryBuffs.WeaponBuffs.CLAW_DASH_COOLDOWN;
import static summonerexpansion.codes.registries.RegistryBuffs.WeaponBuffs.CLAW_DASH_STACKS;

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
        writer.putNextShortUnsigned(maxStacks);
        writer.putNextBoolean(addedStack);
    }

    public void applySpawnPacket(PacketReader reader)
    {
        super.applySpawnPacket(reader);
        maxStacks = reader.getNextShortUnsigned();
        addedStack = reader.getNextBoolean();
    }

    public void init()
    {
        super.init();
        if (level != null && level.isClient() && owner != null)
        {
            float forceMod = Math.min((float)animTime / 700.0F, 1.0F);
            float forceX = dirX * distance * forceMod;
            float forceY = dirY * distance * forceMod;
            for(int i = 0; i < 30; ++i)
            {
                level.entityManager.addParticle(owner.x + (float) GameRandom.globalRandom.nextGaussian() * 15.0F + forceX / 5.0F, owner.y + (float)GameRandom.globalRandom.nextGaussian() * 20.0F + forceY / 5.0F, Particle.GType.IMPORTANT_COSMETIC).movesConstant(forceX * GameRandom.globalRandom.getFloatBetween(0.8F, 1.2F) / 5.0F, forceY * GameRandom.globalRandom.getFloatBetween(0.8F, 1.2F) / 5.0F).color(new Color(200, 200, 220)).height(18.0F).lifeTime(700);
            }
        }

        if (owner != null)
        {
            owner.buffManager.addBuff(new ActiveBuff(BuffRegistry.INVULNERABLE_ACTIVE, owner, animTime, null), false);
        }
    }

    public void clientHit(Mob target, Packet content)
    {
        super.clientHit(target, content);
        if (!addedStack)
        {
            if (owner.buffManager.getStacks(CLAW_DASH_STACKS) < maxStacks - 1)
            {
                owner.buffManager.addBuff(new ActiveBuff(CLAW_DASH_STACKS, owner, 10.0F, null), false);
                owner.buffManager.removeBuff(CLAW_DASH_COOLDOWN, false);
            }
            else
            {
                owner.buffManager.removeBuff(CLAW_DASH_STACKS, false);
                owner.buffManager.addBuff(new ActiveBuff(CLAW_DASH_COOLDOWN, owner, 10.0F, null), false);
            }
            addedStack = true;
        }
    }

    public void dealServerDamage(Mob target, boolean isClientSubmitted)
    {
        super.dealServerDamage(target, isClientSubmitted);
        if (!addedStack)
        {
            if (owner.buffManager.getStacks(CLAW_DASH_STACKS) < maxStacks - 1) {
                owner.buffManager.addBuff(new ActiveBuff(CLAW_DASH_STACKS, owner, 10.0F, null), true);
                owner.buffManager.removeBuff(CLAW_DASH_COOLDOWN, true);
            }
            else
            {
                owner.buffManager.removeBuff(CLAW_DASH_STACKS, true);
                owner.buffManager.addBuff(new ActiveBuff(CLAW_DASH_COOLDOWN, owner, 10.0F, null), true);
            }
            addedStack = true;
        }
    }

    public Shape getHitBox()
    {
        Point2D.Float dir;
        if (owner.getDir() == 3)
        {
            dir = GameMath.getPerpendicularDir(-dirX, -dirY);
        }
        else
        {
            dir = GameMath.getPerpendicularDir(dirX, dirY);
        }

        float width = 40.0F;
        float frontOffset = 20.0F;
        float range = 120.0F;
        float rangeOffset = -40.0F;
        return new LineHitbox(owner.x + dir.x * rangeOffset + dirX * frontOffset, owner.y + dir.y * rangeOffset + dirY * frontOffset, dir.x, dir.y, range, width);
    }
}