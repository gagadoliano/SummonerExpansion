package summonerexpansion.items.trinkets.minions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.MaskShaderOptions;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryMinionTextures.dragonicFlamesMinion2;

public class TrinketDragonFlameMinion2 extends FlyingAttackingFollowingMob
{
    public int lifeTime = 0;
    public TrinketDragonFlameMinion2()
    {
        super(10);
        setSpeed(50.0F);
        setFriction(2.0F);
        moveAccuracy = 10;
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle(-16, -30, 32, 36);
        swimMaskMove = 8;
        swimMaskOffset = 0;
        swimSinkOffset = 0;
        attackCooldown = 3000;
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<TrinketDragonFlameMinion2>(400, 300, false, false, 800, 64)
        {
            public boolean attackTarget(TrinketDragonFlameMinion2 mob, Mob target)
            {
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("dragonicflames2", mob.getLevel(), mob.x, mob.y, target.x, target.y, 60F, 640, summonDamage, mob);
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

    public void serverTick()
    {
        super.serverTick();
        lifeTime++;
        if (lifeTime >= 300)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }

    public int getFlyingHeight() {
        return 50;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), dragonicFlamesMinion2, i, 8, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        int tileX = getTileCoordinate(x);
        int tileY = getTileCoordinate(y);
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 44;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += level.getTile(tileX, tileY).getMobSinkingAmount(this);
        boolean aboveLiquid = level.getTile(tileX, tileY).isLiquid;
        int speed = (int)getSpeed();
        int height = aboveLiquid ? speed / 2 : (int) GameMath.min(new float[]{(float)speed / 2.0F, getCurrentSpeed() / 2.0F});
        int anim = GameUtils.getAnim(level.getTime(), 4, 200);
        final MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        final DrawOptions options = dragonicFlamesMinion2.initDraw().sprite(aboveLiquid ? anim : sprite.x, sprite.y, 64).addMaskShader(swimMask).startGlowOptions(level, getID()).light(light).applyEnemyTracker(this, perspective).pos(drawX, drawY - height);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                swimMask.use();
                options.draw();
                swimMask.stop();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    protected TextureDrawOptions getShadowDrawOptions(Level level, int x, int y, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.dragonWhelp_shadow;
        int height = shadowTexture.getHeight();
        int width = shadowTexture.getWidth();
        int drawX = camera.getDrawX(x) - width / 2;
        int drawY = camera.getDrawY(y) - height / 2;
        float alpha = 1.0F - Math.min(getCurrentSpeed() / 2.0F / getSpeed(), 1.0F);
        return shadowTexture.initDraw().sprite(0, 0, width, height).alpha(alpha).light(light).pos(drawX, drawY + 4);
    }

    public int getRockSpeed() {
        return 10;
    }

    protected SoundSettings getAmbientSound()
    {
        return (new SoundSettings(GameResources.dragonWhelp)).volume(0.3F).basePitch(1.0F);
    }
}