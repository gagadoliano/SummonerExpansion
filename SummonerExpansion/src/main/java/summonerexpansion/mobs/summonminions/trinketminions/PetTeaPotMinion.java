package summonerexpansion.mobs.summonminions.trinketminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.allprojs.TeaPotProj;

import java.awt.*;
import java.util.List;

public class PetTeaPotMinion extends FlyingAttackingFollowingMob
{
    public static GameTexture texture;

    public PetTeaPotMinion()
    {
        super(10);
        setSpeed(60.0F);
        setFriction(2.0F);
        moveAccuracy = 10;
        attackAnimTime = 500;
        attackCooldown = 1800;
        collision = new Rectangle(0, 0, 38, 36);
        hitBox = new Rectangle(0, 0, 38, 36);
        selectBox = new Rectangle(0, 0, 38, 36);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<PetTeaPotMinion>(400, 200, false, false, 900, 64)
        {
            public boolean attackTarget(PetTeaPotMinion mob, Mob target)
            {
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    mob.getLevel().entityManager.projectiles.add(new TeaPotProj(mob.getLevel(), mob.x, mob.y, target.x, target.y, 220, summonDamage, mob));
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public void playDeathSound()
    {
        SoundManager.playSound(GameResources.shatter1, SoundEffect.effect(this).volume(0.5F));
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 5; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, i, 2, 32, x, y, 10.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 32 - 16;
        int dir = this.getDir();
        long time = level.getWorldEntity().getTime() % 350L;
        int sprite;
        if (time < 100L)
        {
            sprite = 0;
        }
        else if (time < 200L)
        {
            sprite = 1;
        }
        else if (time < 300L)
        {
            sprite = 2;
        }
        else
        {
            sprite = 3;
        }
        float rotate = this.dx / 10.0F;
        DrawOptions options = texture.initDraw().sprite(sprite, 0, 64).light(light).mirror(dir == 0, false).rotate(rotate, 32, 32).pos(drawX, drawY);
        topList.add((tm) -> options.draw());
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }

    protected TextureDrawOptions getShadowDrawOptions(Level level, int x, int y, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.vultureHatchling_shadow;
        int drawX = camera.getDrawX(x) - shadowTexture.getWidth() / 2;
        int drawY = camera.getDrawY(y) - shadowTexture.getHeight() / 2 + 13;
        return shadowTexture.initDraw().light(light).pos(drawX, drawY);
    }
}