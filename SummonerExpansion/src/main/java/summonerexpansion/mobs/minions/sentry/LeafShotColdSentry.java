package summonerexpansion.mobs.minions.sentry;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.TrainingDummyMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.ai.behaviourTree.trees.StationaryPlayerShooterAI;
import necesse.entity.mobs.buffs.BuffModifiers;
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
import summonerexpansion.mobs.minions.base.SentryBase;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

import static summonerexpansion.codes.registries.RegistryMinionTextures.leafShotColdSentry;

public class LeafShotColdSentry extends SentryBase
{
    public LeafShotColdSentry()
    {
        super(3000F, 1000F);
        setFriction(2.0F);
        setKnockbackModifier(0.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle();
    }

    public void init()
    {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new StationaryPlayerShooterAI<LeafShotColdSentry>(600)
        {
            public void shootTarget(LeafShotColdSentry mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);

                Projectile projectile = ProjectileRegistry.getProjectile("leafcoldballproj", mob.getLevel(), mob.x, mob.y, target.x, target.y, (120.0F * projVel), 640, summonDamage, mob);
                projectile.setTargetPrediction(target);
                attack((int)(mob.x + projectile.dx * 100.0F), (int)(mob.y + projectile.dy * 100.0F), true);
                projectile.x += Math.signum(attackDir.x) * 10.0F;
                projectile.y += attackDir.y * 6.0F;
                getLevel().entityManager.projectiles.add(projectile);
            }

            public Stream<Mob> streamTargets(LeafShotColdSentry mob, int shootDistance)
            {
                return GameUtils.streamTargets(mob, GameUtils.rangeBounds(x, y, 500)).filter((m) -> mob.isHostile || m.isHostile || m instanceof TrainingDummyMob).filter((m) -> m.getDistance(mob) <= 500.0F);
            }
        });
        if (this.isClient())
        {
            GameRandom random = GameRandom.globalRandom;
            float anglePerParticle = 36.0F;
            for(int i = 0; i < 10; ++i)
            {
                int angle = (int)((float)i * anglePerParticle + random.nextFloat() * anglePerParticle);
                float dx = (float)Math.sin(Math.toRadians(angle)) * 20.0F;
                float dy = (float)Math.cos(Math.toRadians(angle)) * 20.0F;
                this.getLevel().entityManager.addParticle(this, Particle.GType.IMPORTANT_COSMETIC).sprite(GameResources.puffParticles.sprite(random.nextInt(5), 0, 12)).sizeFades(12, 24).movesFriction(dx * 2.0F, dy * 2.0F, 0.8F).color(new Color(29, 97, 17)).heightMoves(0.0F, 30.0F).lifeTime(1500);
            }
            SoundManager.playSound(GameResources.magicbolt4, SoundEffect.effect(this).volume(0.3F).pitch(GameRandom.globalRandom.getFloatBetween(1.4F, 1.5F)));
        }
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

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), leafShotColdSentry, 10, i, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
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
        final DrawOptions body = leafShotColdSentry.initDraw().sprite(spriteX, spriteY, 64).mirror(mirror, false).light(light).pos(drawX, drawY);
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