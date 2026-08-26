package summonerexpansion.mobs.minions.summon;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.minions.base.SummonWalkBase;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryBuffs.WeaponBuffs.MIMICSPEEDBUFF;

public class HungryMimicMinion extends SummonWalkBase
{
    public HungryMimicMinion()
    {
        super();
        setSpeed(40.0F);
        setFriction(3.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle(-16, -24, 32, 32);
        swimMaskMove = 16;
        swimMaskOffset = -2;
        swimSinkOffset = -4;
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<HungryMimicMinion>(700, 64, false, false, 900, 100)
        {
            public boolean attackTarget(HungryMimicMinion mob, Mob target)
            {
                if (mob.canAttack())
                {
                    GameDamage damage = summonDamage;
                    Mob followingMob = getFollowingMob();
                    if (followingMob != null)
                    {
                        damage = damage.modDamage((float) followingMob.buffManager.getModifier(BuffModifiers.MAX_SUMMONS));
                    }
                    mob.attack(target.getX(), target.getY(), false);
                    target.isServerHit(damage, mob.dx, mob.dy, 15.0F, mob);
                    mob.buffManager.addBuff(new ActiveBuff(MIMICSPEEDBUFF, mob, 20.0F, null), true);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return summonDamage; }

    public float getSpeedModifier()
    {
        ActiveBuff buff = buffManager.getBuff(MIMICSPEEDBUFF);
        if (buff != null && isFollowing())
        {
            Mob attackOwner = getAttackOwner();
            if (attackOwner != null)
            {
                return attackOwner.buffManager.getModifier(BuffModifiers.SUMMONS_SPEED) * super.getSpeedModifier();
            }
        }
        return super.getSpeedModifier();
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), MobRegistry.Textures.skeleton.body, GameRandom.globalRandom.nextInt(5), 8, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 42;
        Point sprite = getAnimSprite(x, y, getDir());
        drawY += getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        final DrawOptions drawOptions = (MobRegistry.Textures.mimic.initDraw().sprite(sprite.x, sprite.y, 64).addMaskShader(swimMask).startGlowOptions(level, getID()).light(light).applyTreasure(perspective)).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
    }

    public int getRockSpeed() {
        return 20;
    }
}