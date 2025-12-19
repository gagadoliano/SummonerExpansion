package summonerexpansion.mobs.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.WaitForSecondsEvent;
import necesse.entity.manager.EntityManager;
import necesse.entity.mobs.MaskShaderOptions;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.InventoryItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registry.SummonerTextures.ninjaMinion;

public class YangNinjaMinion extends AttackingFollowingMob
{
    public YangNinjaMinion()
    {
        super(10);
        setSpeed(40.0F);
        setFriction(3.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle(-14, -41, 28, 48);
        swimMaskMove = 16;
        swimMaskOffset = -2;
        swimSinkOffset = -4;
        attackCooldown = 3000;
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<YangNinjaMinion>(600, 600, true, false, 900, 80)
        {
            public boolean attackTarget(YangNinjaMinion mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);

                if (mob.canAttack())
                {
                    final EntityManager entityManager = mob.getLevel().entityManager;
                    mob.attack(target.getX(), target.getY(), false);

                    for (int i = 0; i < 6; ++i)
                    {
                        Projectile duoProjectile = ProjectileRegistry.getProjectile("ninjastaryangproj", mob.getLevel(), mob.x, mob.y, target.x, target.y, (180.0F * projVel), 600, mob.summonDamage, mob);
                        duoProjectile.setAngle(duoProjectile.getAngle() - 30.0F + (i * 60F));
                        entityManager.projectiles.add(duoProjectile);
                    }

                    entityManager.addLevelEventHidden(new WaitForSecondsEvent(1.0F)
                    {
                        public void onWaitOver()
                        {
                            remove(0.0F, 0.0F, null, true);
                        }
                    });

                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    protected SoundSettings getDeathSound()
    {
        return null;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for (int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), ninjaMinion.body, GameRandom.globalRandom.nextInt(5), 8, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, ninjaMinion)).sprite(sprite).dir(dir).mask(swimMask).light(light);
        float animProgress = getAttackAnimProgress();
        if (isAttacking)
        {
            humanDrawOptions.itemAttack(new InventoryItem("ninjastar"), null, animProgress, attackDir.x, attackDir.y);
        }
        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public int getRockSpeed() {
        return 20;
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections)
    {
        super.showAttack(x, y, seed, showAllDirections);
        if (isClient())
        {
            SoundManager.playSound(GameResources.swing1, SoundEffect.effect(this));
        }
    }
}