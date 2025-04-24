package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GroundPillar;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.summonprojs.HorrorSentryProj;

import java.awt.*;
import java.util.List;

public class HorrorSentry extends AttackingFollowingMob
{
    public static GameTexture texture;
    public int lifeTime = 0;

    public HorrorSentry()
    {
        super(100);
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
        this.ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<HorrorSentry>(600, 600, false, false, 90000, 64)
        {
            public boolean attackTarget(HorrorSentry mob, Mob target)
            {
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    HorrorSentryProj projectile = new HorrorSentryProj(HorrorSentry.this.getLevel(), mob, mob.x, mob.y, target.x, target.y, 75.0F, 512, summonDamage, 50);
                    projectile.x -= projectile.dx * 20.0F;
                    projectile.y -= projectile.dy * 20.0F;
                    mob.attack((int)(mob.x + projectile.dx * 100.0F), (int)(mob.y + projectile.dy * 100.0F), false);
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

    public boolean canBePushed(Mob other) {return false;}

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
        final DrawOptions body = texture.initDraw().sprite(0, 0, 32).size(32 - pixelChange * 2, 32 - pixelChange).light(light).pos(drawX + pixelChange, drawY + pixelChange);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                body.draw();
            }
        });
        if (this.inLiquid(x, y)) {
            y -= 10;
        }

        this.addShadowDrawables(tileList, x, y, light, camera);
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

        public HorrorPillar(int x, int y, double spawnDistance, long spawnTime)
        {
            super(x, y, spawnDistance, spawnTime);
            this.mirror = GameRandom.globalRandom.nextBoolean();
            this.texture2 = MobRegistry.Textures.cryoQueen == null ? null : GameRandom.globalRandom.getOneOf((new GameTextureSection(texture)).sprite(1, 0, 32), (new GameTextureSection(texture)).sprite(2, 0, 32), (new GameTextureSection(texture)).sprite(3, 0, 32), (new GameTextureSection(texture)).sprite(4, 0, 32), (new GameTextureSection(texture)).sprite(5, 0, 32));
            this.behaviour = new GroundPillar.TimedBehaviour(300, 200, 800);
        }

        public DrawOptions getDrawOptions(Level level, long currentTime, double distanceMoved, GameCamera camera)
        {
            GameLight light = level.getLightLevel(this.x / 32, this.y / 32);
            int drawX = camera.getDrawX(this.x);
            int drawY = camera.getDrawY(this.y);
            double height = this.getHeight(currentTime, distanceMoved);
            int endY = (int)(height * (double)this.texture2.getHeight());
            return this.texture2.section(0, this.texture2.getWidth(), 0, endY).initDraw().mirror(this.mirror, false).light(light).pos(drawX - this.texture2.getWidth() / 2, drawY - endY);
        }
    }
}