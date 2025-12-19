package summonerexpansion.mobs.summonminions.wormminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.gameLoop.tickManager.TicksPerSecond;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.ComputedValue;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.mobs.*;
import necesse.entity.mobs.summon.FollowingWormMobBody;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Stream;

public class SandWormBodyMinion extends FollowingWormMobBody<SandWormHeadMinion, SandWormBodyMinion>
{
    public static GameTexture texture;
    ParticleTypeSwitcher pTypeSwitcher;
    public int spriteY;
    private TicksPerSecond particleSpawner;
    public ModifierValue<?>[] modifiers;
    public GameDamage collisionDamage;

    public SandWormBodyMinion()
    {
        super(10);
        pTypeSwitcher = new ParticleTypeSwitcher(Particle.GType.COSMETIC, Particle.GType.IMPORTANT_COSMETIC);
        particleSpawner = TicksPerSecond.ticksPerSecond(20);
        modifiers = new ModifierValue[0];
        collisionDamage = new GameDamage(0.0F);
        collision = new Rectangle(-20, -15, 40, 30);
        hitBox = new Rectangle(-25, -20, 50, 40);
        selectBox = new Rectangle();
    }

    public GameMessage getLocalization() {
        return new LocalMessage("mob", "sandwormminion");
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return collisionDamage; }

    public void clientTick()
    {
        super.clientTick();
        if (this.isVisible())
        {
            this.particleSpawner.gameTick();
            while(true)
            {
                while(this.particleSpawner.shouldTick())
                {
                    ComputedValue<GameObject> obj = new ComputedValue(() ->
                            this.getLevel().getObject(this.getX() / 32, this.getY() / 32));
                    if (this.height < 20.0F && (obj.get().isWall || obj.get().isRock))
                    {
                        this.getLevel().entityManager.addTopParticle(this.x + GameRandom.globalRandom.floatGaussian() * 10.0F, this.y + GameRandom.globalRandom.floatGaussian() * 7.0F + 5.0F, this.pTypeSwitcher.next()).movesConstant(GameRandom.globalRandom.floatGaussian() * 6.0F, GameRandom.globalRandom.floatGaussian() * 3.0F).smokeColor().heightMoves(10.0F, GameRandom.globalRandom.getFloatBetween(30.0F, 40.0F)).lifeTime(200);
                    } else if (this.height < 0.0F)
                    {
                        this.getLevel().entityManager.addParticle(this.x + GameRandom.globalRandom.floatGaussian() * 10.0F, this.y + GameRandom.globalRandom.floatGaussian() * 7.0F + 5.0F, this.pTypeSwitcher.next()).movesConstant(GameRandom.globalRandom.floatGaussian() * 6.0F, GameRandom.globalRandom.floatGaussian() * 3.0F).smokeColor().heightMoves(10.0F, GameRandom.globalRandom.getFloatBetween(30.0F, 40.0F)).lifeTime(200);
                    }
                }
                return;
            }
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        if (this.isVisible())
        {
            for(int i = 0; i < 3; ++i)
            {
                this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, 2, GameRandom.globalRandom.nextInt(6), 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
            }
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        if (this.isVisible())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - 32;
            int drawY = camera.getDrawY(y);
            if (this.next != null)
            {
                Point2D.Float dir = new Point2D.Float(this.next.x - (float)x, this.next.y - this.next.height - ((float)y - this.height));
                float angle = GameMath.fixAngle(GameMath.getAngle(dir));
                MobDrawable drawOptions = WormMobHead.getAngledDrawable(new GameSprite(texture, 0, this.spriteY, 64), null, light, (int)this.height, angle, drawX, drawY, 96);
                topList.add(drawOptions);
            }
            this.addShadowDrawables(tileList, level, x, y, light, camera);
        }
    }

    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.sandWorm_shadow;
        int drawX = camera.getDrawX(x) - shadowTexture.getWidth() / 2;
        int drawY = camera.getDrawY(y) - shadowTexture.getHeight() / 2;
        drawY += this.getBobbing(x, y);
        return shadowTexture.initDraw().light(light).pos(drawX, drawY);
    }

    public Stream<ModifierValue<?>> getDefaultModifiers()
    {
        return this.modifiers == null ? super.getDefaultModifiers() : Stream.of(this.modifiers);
    }
}