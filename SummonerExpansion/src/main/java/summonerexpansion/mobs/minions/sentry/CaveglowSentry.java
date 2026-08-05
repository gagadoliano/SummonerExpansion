package summonerexpansion.mobs.minions.sentry;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.TrainingDummyMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.StationaryPlayerShooterAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.minions.base.SentryBase;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

import static summonerexpansion.codes.registries.RegistryMinionTextures.caveglowSentry;

public class CaveglowSentry extends SentryBase
{
    public float moveAngle;

    public CaveglowSentry()
    {
        super(3000F, 1000F);
        collision = new Rectangle(0, 0, 30, 68);
        hitBox = new Rectangle(0, 0, 30, 68);
        selectBox = new Rectangle();
    }

    public void init()
    {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new StationaryPlayerShooterAI<CaveglowSentry>(900)
        {
            public void shootTarget(CaveglowSentry mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);

                Projectile projectile = ProjectileRegistry.getProjectile("caveglowproj", mob.getLevel(), mob.x, mob.y, target.x, target.y, (80.0F * projVel), 900, summonDamage, mob);
                projectile.setTargetPrediction(target);
                attack((int)(mob.x + projectile.dx * 100.0F), (int)(mob.y + projectile.dy * 100.0F), true);
                projectile.x += Math.signum(attackDir.x) * 10.0F;
                projectile.y += attackDir.y * 6.0F;
                getLevel().entityManager.projectiles.add(projectile);
            }

            public Stream<Mob> streamTargets(CaveglowSentry mob, int shootDistance)
            {
                return GameUtils.streamTargets(mob, GameUtils.rangeBounds(x, y, 500)).filter((m) -> mob.isHostile || m.isHostile || m instanceof TrainingDummyMob).filter((m) -> m.getDistance(mob) <= 500.0F);
            }
        });
        if (this.isClient())
        {
            GameRandom random = GameRandom.globalRandom;
            float anglePerParticle = 36.0F;
            for(int i = 0; i < 10; ++i)
            {
                int angle = (int)((float)i * anglePerParticle + random.nextFloat() * anglePerParticle);
                float dx = (float)Math.sin(Math.toRadians(angle)) * 20.0F;
                float dy = (float)Math.cos(Math.toRadians(angle)) * 20.0F;
                this.getLevel().entityManager.addParticle(this, Particle.GType.IMPORTANT_COSMETIC).sprite(GameResources.puffParticles.sprite(random.nextInt(5), 0, 12)).sizeFades(12, 24).movesFriction(dx * 2.0F, dy * 2.0F, 0.8F).color(new Color(19, 239, 199)).heightMoves(0.0F, 30.0F).lifeTime(1500);
            }
            SoundManager.playSound(GameResources.magicbolt4, SoundEffect.effect(this).volume(0.3F).pitch(GameRandom.globalRandom.getFloatBetween(1.4F, 1.5F)));
        }
    }

    public void clientTick()
    {
        super.clientTick();
        getLevel().lightManager.refreshParticleLightFloat(x, y, 180.0F, 0.5F, 165);
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 20; ++i)
        {
            getLevel().entityManager.addParticle(x, y, Particle.GType.COSMETIC).movesConstantAngle((float) GameRandom.globalRandom.nextInt(360), (float)GameRandom.globalRandom.getIntBetween(5, 20)).color(new Color(85, 182, 125));
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 20;
        DrawOptions body = caveglowSentry.initDraw().light(light).rotate(moveAngle, 15, 30).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }
}