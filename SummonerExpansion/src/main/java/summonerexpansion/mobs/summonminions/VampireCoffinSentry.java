package summonerexpansion.mobs.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptionsEnd;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.summonminions.baseminions.SentryBase;

import java.awt.*;
import java.util.List;

public class VampireCoffinSentry extends SentryBase
{
    public static GameTexture texture;
    public float moveAngle;
    public int coffinCharge;

    public VampireCoffinSentry()
    {
        super(2000F, 1000F);
        collision = new Rectangle(0, 0, 32, 64);
        hitBox = new Rectangle(0, 0, 32, 64);
        selectBox = new Rectangle();
    }

    public void init()
    {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<VampireCoffinSentry>(900, 900, false, false, 9000, 64)
        {
            public boolean attackTarget(VampireCoffinSentry mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);

                if (mob.canAttack() && getAttackOwner().buffManager.hasBuff("bloodplatecowlsetbonus"))
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("bloodbolt", mob.getLevel(), mob.x, mob.y, target.x, target.y, (120.0F * projVel), 900, summonDamage.modFinalMultiplier(1.20F), mob);
                    projectile.setTargetPrediction(target, -20.0F);
                    projectile.moveDist(40.0);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    coffinCharge++;
                    return true;
                }
                else if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("bloodbolt", mob.getLevel(), mob.x, mob.y, target.x, target.y, (80.0F * projVel), 900, summonDamage, mob);
                    projectile.setTargetPrediction(target, -20.0F);
                    projectile.moveDist(20.0);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    coffinCharge++;
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
        if (coffinCharge >= 10)
        {
            FlyingAttackingFollowingMob mob = (FlyingAttackingFollowingMob) MobRegistry.getMob("batcryptminion", getFollowingItemAttacker().getLevel());
            getFollowingItemAttacker().serverFollowersManager.addFollower("batcryptminion", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, (p) -> 100, null, false);
            if (getAttackOwner().buffManager.hasBuff("bloodplatecowlsetbonus"))
            {
                mob.updateDamage(summonDamage.modFinalMultiplier(1F));
            }
            else
            {
                mob.updateDamage(summonDamage.modFinalMultiplier(0.20F));
            }
            this.getLevel().entityManager.addMob(mob, x, y);
            coffinCharge = 0;
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, i, 8, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y)).minLevelCopy(100.0F);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 55;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY = (int)((float)drawY);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        final TextureDrawOptionsEnd drawOptions = texture.initDraw().sprite(sprite.x, sprite.y, 64).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public Point getAnimSprite(int x, int y, int dir)
    {
        return new Point(GameUtils.getAnim(this.getWorldEntity().getTime(), 4, 100*60), dir);
    }

    public int getRockSpeed() {
        return 50*60;
    }
}