package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.ArcanicPylonLightningLevelEvent;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ability.EmptyMobAbility;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.leaves.ChaserAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.EmptyAINode;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SetArcanicPylonSentry extends AttackingFollowingMob
{
    public int lifeTime = 0;
    public int lifeStart = 0;
    public static GameTexture texture;

    public SetArcanicPylonSentry()
    {
        super(10);
        setSpeed(0F);
        setFriction(0F);
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
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<SetArcanicPylonSentry>(900, 900, false, false, 90000, 64)
        {
            public boolean attackTarget(SetArcanicPylonSentry mob, Mob target)
            {
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    mob.getLevel().entityManager.addLevelEvent(new ArcanicPylonLightningLevelEvent(mob, 300, summonDamage.getTotalDamage(target, mob, 1F), target.getPositionPoint()));
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
        lifeStart++;
        if (lifeStart >= lifeTime)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }

    public void clientTick()
    {
        super.clientTick();
        if (!isAttacking)
        {
            for(int i = 0; i < 4; ++i)
            {
                int angle = GameRandom.globalRandom.nextInt(360);
                Point2D.Float dir = GameMath.getAngleDir((float)angle);
                float range = GameRandom.globalRandom.getFloatBetween(25.0F, 40.0F);
                float startX = (float)getX() + dir.x * range;
                float startY = (float)(getY() + -20);
                float endHeight = 29.0F;
                float startHeight = endHeight + dir.y * range;
                int lifeTime = GameRandom.globalRandom.getIntBetween(200, 500);
                float speed = dir.x * range * 250.0F / (float)lifeTime;
                Color color1 = new Color(13, 118, 150);
                Color color2 = new Color(3, 167, 255);
                Color color3 = new Color(71, 221, 255);
                Color color = GameRandom.globalRandom.getOneOf(color1, color2, color3);
                getLevel().entityManager.addParticle(startX, startY, Particle.GType.IMPORTANT_COSMETIC).sprite(GameResources.puffParticles.sprite(GameRandom.globalRandom.nextInt(5), 0, 12)).sizeFades(10, 16).rotates().heightMoves(startHeight, endHeight).movesConstant(-speed, 0.0F).color(color).fadesAlphaTime(100, 50).lifeTime(lifeTime);
            }
        }
    }

    public boolean canBePushed(Mob other) {
        return false;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 6; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), texture, GameRandom.globalRandom.nextInt(5), 8, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(java.util.List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        final MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        final DrawOptions options = texture.initDraw().sprite(sprite.x, sprite.y, 64).addMaskShader(swimMask).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                swimMask.use();
                options.draw();
                swimMask.stop();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public int getRockSpeed() {
        return 0;
    }
}