package summonerexpansion.mobs.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.hostile.GiantCaveSpiderMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.CaveSpiderSpitProjectile;
import necesse.entity.projectile.CaveSpiderWebProjectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.item.toolItem.projectileToolItem.ProjectileToolItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class RedSpiderRangeMinion extends AttackingFollowingMob
{
    public RedSpiderRangeMinion()
    {
        super(10);
        setSpeed(50.0F);
        setFriction(2.0F);
        attackCooldown = 5000;
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle();
        swimMaskMove = 8;
        swimMaskOffset = 30;
        swimSinkOffset = 0;
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<RedSpiderRangeMinion>(600, 200, true, false, 900, 100)
        {
            public boolean attackTarget(RedSpiderRangeMinion mob, Mob target)
            {
                if (mob.canAttack())
                {
                    Mob owner = getAttackOwner();
                    int targetDistance = (int)mob.getDistance(target);
                    mob.attack(target.getX(), target.getY(), false);
                    Point point = ProjectileToolItem.controlledRangePosition(GameRandom.globalRandom, mob.getX(), mob.getY(), target.getX(), target.getY(), Math.max(320, targetDistance + 32), 32, 16);
                    int pointDistance = (int)mob.getDistance((float)point.x, (float)point.y);
                    if (owner.buffManager.hasBuff("redspidersetbonus"))
                    {
                        mob.getLevel().entityManager.projectiles.add(new CaveSpiderSpitProjectile(GiantCaveSpiderMob.Variant.BLACK, mob.x, mob.y, (float)point.x, (float)point.y, summonDamage.modFinalMultiplier(1.00F), mob, pointDistance));
                    }
                    else
                    {
                        mob.getLevel().entityManager.projectiles.add(new CaveSpiderSpitProjectile(GiantCaveSpiderMob.Variant.BLACK, mob.x, mob.y, (float)point.x, (float)point.y, summonDamage.modFinalMultiplier(0.50F), mob, pointDistance));
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

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), MobRegistry.Textures.spider.body, 12, i, 16, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 22;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        final MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        final DrawOptions options = MobRegistry.Textures.spider.body.initDraw().sprite(sprite.x, sprite.y, 32).addMaskShader(swimMask).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                swimMask.use();
                options.draw();
                swimMask.stop();
            }
        });
        TextureDrawOptions shadow = MobRegistry.Textures.spider.shadow.initDraw().sprite(0, dir, 32).light(light).pos(drawX, drawY);
        tileList.add((tm) -> {
            shadow.draw();
        });
    }

    public int getRockSpeed() {
        return 5;
    }
}