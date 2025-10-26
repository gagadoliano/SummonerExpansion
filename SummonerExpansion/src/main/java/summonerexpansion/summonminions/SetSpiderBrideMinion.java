package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.hostile.GiantCaveSpiderMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.CaveSpiderSpitProjectile;
import necesse.entity.projectile.CaveSpiderWebProjectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.toolItem.projectileToolItem.ProjectileToolItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class SetSpiderBrideMinion extends AttackingFollowingMob
{
    public static GameTexture texture;
    public int lifeTime = 0;

    public SetSpiderBrideMinion()
    {
        super(10);
        setSpeed(30.0F);
        setFriction(3.0F);
        attackAnimTime = 500;
        attackCooldown = 2000;
        collision = new Rectangle(-20, -20, 40, 40);
        hitBox = new Rectangle(-30, -25, 60, 50);
        selectBox = new Rectangle();
        swimMaskMove = 20;
        swimMaskOffset = -24;
        swimSinkOffset = 0;
    }

    public void init()
    {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<SetSpiderBrideMinion>(600, 300, true, false, 900, 100)
        {
            public boolean attackTarget(SetSpiderBrideMinion mob, Mob target)
            {
                if (mob.canAttack())
                {
                    int targetDistance = (int)mob.getDistance(target);
                    mob.attack(target.getX(), target.getY(), false);
                    Point point = ProjectileToolItem.controlledRangePosition(GameRandom.globalRandom, mob.getX(), mob.getY(), target.getX(), target.getY(), Math.max(320, targetDistance + 32), 32, 16);
                    int pointDistance = (int)mob.getDistance((float)point.x, (float)point.y);
                    if (GameRandom.globalRandom.nextInt(2) == 1)
                    {
                        mob.getLevel().entityManager.projectiles.add(new CaveSpiderSpitProjectile(GiantCaveSpiderMob.Variant.NORMAL, mob.x, mob.y, (float)point.x, (float)point.y, summonDamage, mob, pointDistance));
                    }
                    else if (GameRandom.globalRandom.nextInt(2) == 1)
                    {
                        mob.getLevel().entityManager.projectiles.add(new CaveSpiderWebProjectile(mob.x, mob.y, (float)point.x, (float)point.y, summonDamage, mob, pointDistance));
                    }
                    else
                    {
                        mob.getLevel().entityManager.projectiles.add(new CaveSpiderSpitProjectile(GiantCaveSpiderMob.Variant.NORMAL, mob.x, mob.y, (float)point.x, (float)point.y, summonDamage, mob, pointDistance));
                    }
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
        if (lifeTime >= 300)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }

    public Point getPathMoveOffset() {
        return new Point(32, 32);
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 10; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, 14 + i / 5, i % 5, 48, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 48;
        int drawY = camera.getDrawY(y) - 60;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        if (this.isAttacking) {sprite.x = 6;}
        final MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        final DrawOptions body = texture.initDraw().sprite(sprite.x, sprite.y, 96).addMaskShader(swimMask).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                swimMask.use();
                body.draw();
                swimMask.stop();
            }
        });
        TextureDrawOptions shadow = MobRegistry.Textures.giantCaveSpider.shadow.initDraw().sprite(0, sprite.y, 96).light(light).pos(drawX, drawY);
        tileList.add((tm) -> {
            shadow.draw();
        });
    }

    public int getRockSpeed() {
        return 15;
    }

    public float getAttackingMovementModifier() {
        return 0.0F;
    }

    public void attack(int x, int y, boolean showAllDirections)
    {
        super.attack(x, y, showAllDirections);
        this.setFacingDir(this.attackDir.x, this.attackDir.y);
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections)
    {
        super.showAttack(x, y, seed, showAllDirections);
        this.setFacingDir(this.attackDir.x, this.attackDir.y);
        if (this.isClient())
        {
            SoundManager.playSound(GameResources.spit, SoundEffect.effect(this));
        }
    }
}