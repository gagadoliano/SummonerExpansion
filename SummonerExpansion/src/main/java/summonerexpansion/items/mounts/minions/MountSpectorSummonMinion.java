package summonerexpansion.items.mounts.minions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameUtils;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ForestSpectorDrainSoulLevelEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryMounts.MountBuffs.FORESTDRAIN;

public class MountSpectorSummonMinion extends FlyingAttackingFollowingMob
{
    public int lifeTime = 0;

    public MountSpectorSummonMinion()
    {
        super(10);
        setSpeed(60.0F);
        setFriction(3.0F);
        collision = new Rectangle(-10, -13, 20, 26);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle(-16, -32, 32, 38);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<MountSpectorSummonMinion>(400, 200, false, false, 900, 64)
        {
            public boolean attackTarget(MountSpectorSummonMinion mob, Mob target)
            {
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    ForestSpectorDrainSoulLevelEvent event = new ForestSpectorDrainSoulLevelEvent(target, mob);
                    target.buffManager.addBuff(new ActiveBuff(FORESTDRAIN, target, 6.0F, mob), true);
                    getLevel().entityManager.events.add(event);
                    lifeTime += 10;
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public void serverTick()
    {
        super.serverTick();
        lifeTime++;
        if (lifeTime >= 600)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), MobRegistry.Textures.forestSpector, i, 8, 32, x, y, 10.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 56;
        int anim = GameUtils.getAnim(getTime(), 5, 500);
        drawY += getBobbing(x, y);
        final DrawOptions options = MobRegistry.Textures.forestSpector.initDraw().sprite(anim, getDir(), 64).light(light).alpha(0.8F).pos(drawX, drawY);
        topList.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
        TextureDrawOptions shadow = MobRegistry.Textures.forestSpector_shadow.initDraw().sprite(anim, getDir(), 64, 64).light(light).pos(drawX, drawY);
        tileList.add((tm) -> shadow.draw());
    }

    public int getRockSpeed() {
        return 10;
    }

    public int getFlyingHeight() {
        return 64;
    }

    public boolean isWaterWalking() {
        return true;
    }
}