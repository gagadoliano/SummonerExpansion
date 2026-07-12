package summonerexpansion.mobs.minions.magic;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptionsEnd;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.minions.base.SentryBase;
import summonerexpansion.mobs.minions.sentry.CaveglowSentry;
import summonerexpansion.projectiles.magic.ThornSpikesProj;

import java.awt.*;
import java.util.List;
import static summonerexpansion.codes.registries.RegistryMinionTextures.thornSentry;

public class ThornSentry extends SentryBase
{
    public ThornSentry()
    {
        super(4000F, 1000F);
        collision = new Rectangle(0, 0, 34, 66);
        hitBox = new Rectangle(0, 0, 34, 66);
        selectBox = new Rectangle(0, 0, 34, 66);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<ThornSentry>(900, 900, false, false, 90000, 64)
        {
            public boolean attackTarget(ThornSentry mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    ThornSpikesProj projectile = new ThornSpikesProj(mob.getLevel(), mob, mob.x, mob.y, target.x, target.y, (40.0F * projVel), 600, summonDamage, 0);
                    projectile.setTargetPrediction(target, -20.0F);
                    projectile.moveDist(20.0);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), thornSentry, i, 8, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y)).minLevelCopy(100.0F);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 55;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY = (int)((float)drawY);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        final TextureDrawOptionsEnd drawOptions = thornSentry.initDraw().sprite(sprite.x, sprite.y, 64).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public Point getAnimSprite(int x, int y, int dir)
    {
        return new Point(GameUtils.getAnim(getWorldEntity().getTime(), 4, 100*60), dir);
    }

    public int getRockSpeed() {
        return 50*60;
    }
}