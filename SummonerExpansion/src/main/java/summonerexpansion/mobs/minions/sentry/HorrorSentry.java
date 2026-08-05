package summonerexpansion.mobs.minions.sentry;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.engine.util.GroundPillar;
import necesse.entity.Entity;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.TrainingDummyMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.ai.behaviourTree.trees.StationaryPlayerShooterAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.minions.base.SentryBase;
import summonerexpansion.projectiles.melee.HorrorSentryProj;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

import static summonerexpansion.codes.registries.RegistryMinionTextures.horrorSentry;

public class HorrorSentry extends SentryBase
{
    public int lifeTime = 0;

    public HorrorSentry()
    {
        super(3000F, 1000F);
        setSpeed(0.0F);
        setFriction(3.0F);
        setKnockbackModifier(0.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle(-14, -23, 28, 32);
    }

    public void init()
    {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new StationaryPlayerShooterAI<HorrorSentry>(600)
        {
            public void shootTarget(HorrorSentry mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);

                HorrorSentryProj projectile = new HorrorSentryProj(HorrorSentry.this.getLevel(), mob, mob.x, mob.y, target.x, target.y, (75.0F * projVel), 512, summonDamage, 50);
                projectile.setTargetPrediction(target);
                attack((int)(mob.x + projectile.dx * 100.0F), (int)(mob.y + projectile.dy * 100.0F), true);
                projectile.x += Math.signum(attackDir.x) * 10.0F;
                projectile.y += attackDir.y * 6.0F;
                getLevel().entityManager.projectiles.add(projectile);
            }

            public Stream<Mob> streamTargets(HorrorSentry mob, int shootDistance)
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
                this.getLevel().entityManager.addParticle(this, Particle.GType.IMPORTANT_COSMETIC).sprite(GameResources.puffParticles.sprite(random.nextInt(5), 0, 12)).sizeFades(12, 24).movesFriction(dx * 2.0F, dy * 2.0F, 0.8F).color(new Color(0, 0, 0)).heightMoves(0.0F, 30.0F).lifeTime(1500);
            }
            SoundManager.playSound(GameResources.magicbolt4, SoundEffect.effect(this).volume(0.3F).pitch(GameRandom.globalRandom.getFloatBetween(1.4F, 1.5F)));
        }
    }

    public void clientTick()
    {
        super.clientTick();
        if (this.isAttacking)
        {
            this.getAttackAnimProgress();
        }
    }

    public void serverTick()
    {
        super.serverTick();
        lifeTime++;
        if (lifeTime >= 6000)
        {
            remove(0.0F, 0.0F, null, true);
        }

        if (this.isAttacking)
        {
            this.getAttackAnimProgress();
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 20; ++i)
        {
            this.getLevel().entityManager.addParticle(this.x, this.y, Particle.GType.COSMETIC).movesConstantAngle((float) GameRandom.globalRandom.nextInt(360), (float)GameRandom.globalRandom.getIntBetween(5, 20)).color(new Color(10, 10, 10));
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 15;
        int drawY = camera.getDrawY(y) - 26;
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        if (this.inLiquid(x, y)) {
            drawY -= 10;
        }

        float animProgress = GameMath.limit(this.getAttackAnimProgress(), 0.0F, 1.0F);
        float wiggle;
        if (animProgress < 0.5F) {
            wiggle = animProgress * 2.0F;
        } else {
            wiggle = Math.abs((animProgress - 0.5F) * 2.0F - 1.0F);
        }

        int pixelChange = (int)(wiggle * 5.0F);
        final DrawOptions body = horrorSentry.initDraw().sprite(0, 0, 32).size(32 - pixelChange * 2, 32 - pixelChange).light(light).pos(drawX + pixelChange, drawY + pixelChange);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                body.draw();
            }
        });
        if (this.inLiquid(x, y)) {
            y -= 10;
        }

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

    public static class HorrorPillar extends GroundPillar
    {
        public GameTextureSection texture2;
        public boolean mirror;

        public HorrorPillar(int x, int y, double spawnDistance, long spawnTime) {
            super(x, y, spawnDistance, spawnTime);
            this.mirror = GameRandom.globalRandom.nextBoolean();
            this.texture2 = MobRegistry.Textures.cryoQueen == null ? null : GameRandom.globalRandom.getOneOf((new GameTextureSection(horrorSentry)).sprite(1, 0, 32), (new GameTextureSection(horrorSentry)).sprite(2, 0, 32), (new GameTextureSection(horrorSentry)).sprite(3, 0, 32), (new GameTextureSection(horrorSentry)).sprite(4, 0, 32), (new GameTextureSection(horrorSentry)).sprite(5, 0, 32));
            this.behaviour = new GroundPillar.TimedBehaviour(300, 200, 800);
        }

        public DrawOptions getDrawOptions(Level level, long currentTime, double distanceMoved, GameCamera camera, PlayerMob perspective)
        {
            GameLight light = level.getLightLevel(Entity.getTileCoordinate(this.x), Entity.getTileCoordinate(this.y));
            int drawX = camera.getDrawX(this.x);
            int drawY = camera.getDrawY(this.y);
            double height = this.getHeight(currentTime, distanceMoved);
            int endY = (int)(height * (double)this.texture2.getHeight());
            return this.texture2.section(0, this.texture2.getWidth(), 0, endY).initDraw().mirror(this.mirror, false).light(light).pos(drawX - this.texture2.getWidth() / 2, drawY - endY);
        }
    }
}