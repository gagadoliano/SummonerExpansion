package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ability.EmptyMobAbility;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.pathProjectile.StaticJellyfishProjectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptionsEnd;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class PetJellyfishMinion extends FlyingAttackingFollowingMob
{
    public ParticleTypeSwitcher particleTypes;
    public final EmptyMobAbility playBoltSoundAbility;
    
    public PetJellyfishMinion()
    {
        super(10);
        setSpeed(70.0F);
        setFriction(0.5F);
        moveAccuracy = 20;
        attackCooldown = 6000;
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle(-16, -28, 32, 34);
        particleTypes = new ParticleTypeSwitcher(Particle.GType.COSMETIC, Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC);
        playBoltSoundAbility = this.registerAbility(new EmptyMobAbility()
        {
            protected void run()
            {
                if (PetJellyfishMinion.this.isClient())
                {
                    SoundManager.playSound(GameResources.magicbolt2, SoundEffect.globalEffect().volume(0.5F).pitch(1.1F));
                }
            }
        });
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<PetJellyfishMinion>(500, 300, false, false, 800, 70)
        {
            public boolean attackTarget(PetJellyfishMinion mob, Mob target)
            {
                if (PetJellyfishMinion.this.canAttack() && !PetJellyfishMinion.this.isOnGenericCooldown("attackCooldown"))
                {
                    mob.attack(target.getX(), target.getY(), false);
                    StaticJellyfishProjectile projectile = new StaticJellyfishProjectile(mob.x, mob.y, target.x, target.y, 70.0F, 35.0F, 80.0F, summonDamage, mob);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    PetJellyfishMinion.this.startGenericCooldown("attackCooldown", PetJellyfishMinion.this.attackCooldown);
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
        if (GameRandom.globalRandom.getChance(0.2F))
        {
            float particleX = this.x + GameRandom.globalRandom.floatGaussian() * 10.0F;
            float particleY = this.y + GameRandom.globalRandom.floatGaussian() * 10.0F;
            float moveX = GameRandom.globalRandom.floatGaussian() * 10.0F;
            float moveY = GameRandom.globalRandom.floatGaussian() * 10.0F;
            this.getLevel().entityManager.addParticle(particleX, particleY, this.particleTypes.next()).sprite(GameResources.magicSparkParticles.sprite(GameRandom.globalRandom.nextInt(4), 0, 22)).sizeFades(10, 20).movesFriction(moveX, moveY, 0.8F).color(new Color(95, 205, 228)).givesLight(190.0F, 0.9F).height(24.0F).ignoreLight(true).lifeTime(500);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), MobRegistry.Textures.staticJellyfish.body, GameRandom.globalRandom.nextInt(4), 3, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void playDeathSound() {
        SoundManager.playSound(GameResources.waterblob, SoundEffect.effect(this));
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32).minLevelCopy(100.0F);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 60;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY += (int)(GameUtils.getBobbing(this.getTime(), 1000) * 5.0F);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        final TextureDrawOptionsEnd drawOptions = MobRegistry.Textures.staticJellyfish.body.initDraw().sprite(sprite.x, sprite.y, 64).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        TextureDrawOptions shadow = MobRegistry.Textures.small_shadow.initDraw().light(light).posMiddle(camera.getDrawX(x), camera.getDrawY(y));
        tileList.add((tm) -> {
            shadow.draw();
        });
    }

    public Point getAnimSprite(int x, int y, int dir)
    {
        return new Point(GameUtils.getAnim(this.getTime(), 4, 400), 0);
    }
}