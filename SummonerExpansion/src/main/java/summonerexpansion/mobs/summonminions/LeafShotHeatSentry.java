package summonerexpansion.mobs.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.objectEntity.interfaces.OEVicinityBuff;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

public class LeafShotHeatSentry extends AttackingFollowingMob
{
    public static GameTexture texture;
    
    public LeafShotHeatSentry()
    {
        super(10);
        setSpeed(0.0F);
        setFriction(3.0F);
        setKnockbackModifier(0.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle();
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<LeafShotHeatSentry>(600, 600, false, false, 90000, 64)
        {
            public boolean attackTarget(LeafShotHeatSentry mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);

                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("leafheatballproj", mob.getLevel(), mob.x, mob.y, target.x, target.y, (120.0F * projVel), 640, summonDamage, mob);
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

    public void clientTick()
    {
        super.clientTick();
        if (isAttacking)
        {
            getAttackAnimProgress();
        }
    }

    public void serverTick()
    {
        super.serverTick();
        if (isAttacking)
        {
            getAttackAnimProgress();
        }
    }

    public boolean canBePushed(Mob other) {
        return false;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, 10, i, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 56;
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        boolean mirror = false;
        int spriteY;
        float animProgress;
        if (this.attackDir != null)
        {
            animProgress = 0.4F;
            if (Math.abs(this.attackDir.x) - Math.abs(this.attackDir.y) <= animProgress)
            {
                spriteY = this.attackDir.y < 0.0F ? 0 : 2;
                if (this.attackDir.x < 0.0F)
                {
                    mirror = true;
                }
            }
            else
            {
                spriteY = this.attackDir.x < 0.0F ? 3 : 1;
            }
        }
        else
        {
            int dir = this.getDir();
            if (dir != 0 && dir != 1)
            {
                spriteY = 3;
            }
            else
            {
                spriteY = 1;
            }
        }
        animProgress = this.getAttackAnimProgress();
        int spriteX;
        if (this.isAttacking)
        {
            spriteX = 1 + Math.min((int)(animProgress * 4.0F), 3);
        }
        else
        {
            spriteX = 0;
        }
        final DrawOptions body = texture.initDraw().sprite(spriteX, spriteY, 64).mirror(mirror, false).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                body.draw();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }

    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.human_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2;
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        return shadowTexture.initDraw().sprite(0, 0, res).light(light).pos(drawX, drawY);
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections)
    {
        super.showAttack(x, y, seed, showAllDirections);
        if (this.isClient())
        {
            SoundManager.playSound(GameResources.flick, SoundEffect.effect(this).pitch(1.2F));
        }
    }
}
