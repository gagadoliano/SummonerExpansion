package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.SparklerProjectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.summonprojs.IceWizardProj;
import summonerexpansion.summonprojs.TeaPotProj;

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

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 10;
        DrawOptions body = texture.initDraw().sprite(0, 0, 32).light(light).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
        DrawOptions shadow = MobRegistry.Textures.rubyShield_shadow.initDraw().sprite(0, 0, 32).light(light).pos(drawX, drawY + 10);
        tileList.add((tm) -> {
            shadow.draw();
        });
    }
}