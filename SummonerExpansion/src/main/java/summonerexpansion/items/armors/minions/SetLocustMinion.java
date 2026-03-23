package summonerexpansion.items.armors.minions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.explosionEvent.splashEvent.LocustDeathSplashEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ability.BooleanMobAbility;
import necesse.entity.mobs.ai.behaviourTree.AINodeResult;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.Blackboard;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.ThemeColorRegistry;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.CollisionFilter;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.minions.base.SummonFlyingBase;

import java.awt.*;
import java.util.List;

public class SetLocustMinion extends SummonFlyingBase
{
    protected boolean isAngry;
    protected float angryProgress = 0.0F;
    protected Color bodyColor;
    protected Color angryColorMiddle;
    protected Color angryColorMax;
    protected final BooleanMobAbility toggleAngryAbility;
    protected Mob currentTarget;

    public SetLocustMinion()
    {
        super();
        accelerationMod = 1.0F;
        moveAccuracy = 10;
        setSpeed(60.0F);
        setFriction(1.0F);
        collision = new Rectangle(-5, 2, 10, 16);
        hitBox = new Rectangle(-8, 4, 16, 14);
        selectBox = new Rectangle(-14, -14, 28, 36);
        bodyColor = Color.WHITE;
        angryColorMiddle = new Color(68, 211, 255);
        angryColorMax = new Color(27, 83, 255);
        toggleAngryAbility = registerAbility(new BooleanMobAbility()
        {
            protected void run(boolean value)
            {
                toggleAngry(value);
            }
        });
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFlyingFollowerCollisionChaserAI<SetLocustMinion>(576, null, 15, 500, 640, 100)
        {
            public AINodeResult tick(SetLocustMinion mob, Blackboard<SetLocustMinion> blackboard)
            {
                AINodeResult out = super.tick(mob, blackboard);
                Mob chaserTarget = blackboard.getObject(Mob.class, "chaserTarget");
                if (currentTarget != chaserTarget)
                {
                    currentTarget = chaserTarget;
                    toggleAngryAbility.runAndSend(currentTarget != null && currentTarget != getFollowingMob());
                }
                return out;
            }
        }, new FlyingAIMover());
        if (isClient())
        {
            for(int i = 0; i < 20; ++i)
            {
                getLevel().entityManager.addParticle(x, y, Particle.GType.IMPORTANT_COSMETIC).movesConstant(GameRandom.globalRandom.getFloatBetween(-20.0F, 20.0F), GameRandom.globalRandom.getFloatBetween(-10.0F, 10.0F)).color(ThemeColorRegistry.SAND.getRandomColor()).height(-10.0F);
            }
            for(int i = 0; i < 10; ++i)
            {
                getLevel().entityManager.addParticle(x, y, Particle.GType.IMPORTANT_COSMETIC).movesConstant(GameRandom.globalRandom.getFloatBetween(-14.0F, 14.0F), GameRandom.globalRandom.getFloatBetween(-14.0F, 14.0F)).color(ThemeColorRegistry.SLIME.getRandomColor().darker()).height(-10.0F);
            }
            SoundManager.playSound(GameResources.blunthit, SoundEffect.effect(this).volume(0.05F).pitch(2.0F));
            SoundManager.playSound(GameResources.grass, SoundEffect.effect(this).volume(0.5F).pitch(1.0F));
        }
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter)
    {
        return new GameDamage(0.0F);
    }

    public int getFlyingHeight()
    {
        return Math.abs(dx) <= 0.01F && Math.abs(dy) <= 0.01F ? 0 : 20;
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        LocustDeathSplashEvent event = new LocustDeathSplashEvent(x, y, 128, summonDamage, 0.0F, getAttackOwner(), angryProgress <= 0.5F ? angryColorMiddle : bodyColor);
        getLevel().entityManager.events.add(event);
        remove(0.0F, 0.0F, null, true);
    }

    public void toggleAngry(boolean isAngry) {
        this.isAngry = isAngry;
    }

    public void serverTick()
    {
        super.serverTick();
        Mob attackOwner = getAttackOwner();
        if (attackOwner != null && !attackOwner.buffManager.hasBuff("pharaohsmasksetbonus"))
        {
            remove(0.0F, 0.0F, null, true);
        }
        updateBodyColor();
    }

    public void clientTick()
    {
        super.clientTick();
        updateBodyColor();
    }

    protected void updateBodyColor()
    {
        if (isAngry && angryProgress <= 1.0F)
        {
            angryProgress += 0.02F;
        }
        else if (angryProgress > 0.0F)
        {
            angryProgress -= 0.02F;
        }
        if (!(angryProgress <= 0.0F))
        {
            bodyColor = getLerpedTripleColor(GameMath.limit(angryProgress, 0.0F, 1.0F), Color.white, angryColorMiddle, angryColorMax);
        }
    }

    protected boolean shouldBeIdle()
    {
        return almostZero(angryProgress) && almostZero(dx) && almostZero(dy);
    }

    protected boolean almostZero(float value)
    {
        return value <= 0.03F && value >= -0.03F;
    }

    protected Color getLerpedTripleColor(float lerpFloat, Color zero, Color half, Color full)
    {
        int r = GameMath.lerp(lerpFloat * 2.0F, zero.getRed(), half.getRed());
        int g = GameMath.lerp(lerpFloat * 2.0F, zero.getGreen(), half.getGreen());
        int b = GameMath.lerp(lerpFloat * 2.0F, zero.getBlue(), half.getBlue());
        if (lerpFloat > 0.5F)
        {
            r = GameMath.lerp(lerpFloat * 2.0F - 1.0F, half.getRed(), full.getRed());
            g = GameMath.lerp(lerpFloat * 2.0F - 1.0F, half.getGreen(), full.getGreen());
            b = GameMath.lerp(lerpFloat * 2.0F - 1.0F, half.getBlue(), full.getBlue());
        }
        return new Color(r, g, b);
    }

    public CollisionFilter getLevelCollisionFilter()
    {
        return !isMounted() ? (new CollisionFilter()).addFilter((tp) -> !tp.object().object.isDoor).mobCollision().summonedMobCollision() : (new CollisionFilter()).mobCollision();
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), MobRegistry.Textures.locust, i, 8, 32, x, y, 10.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(GameMath.getTileCoordinate(x), GameMath.getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 32 - 8;
        int dir = getDir();
        int time = (int)((float)(level.getWorldEntity().getTime() % 300L) / 75.0F);
        Point p = new Point(time + 1, dir);
        if (shouldBeIdle()) {p.x = 0;}
        float rotate = dx / 10.0F;
        final DrawOptions options = MobRegistry.Textures.locust.initDraw().sprite(p.x, p.y, 64).light(light).rotate(rotate, 32, 32).colorLight(bodyColor, light.minLevelCopy(150.0F * GameMath.limit(angryProgress, 0.2F, 1.0F))).pos(drawX, drawY);
        list.add(new MobDrawable()
        {
            public void draw(TickManager tickManager)
            {
                options.draw();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    protected TextureDrawOptions getShadowDrawOptions(Level level, int x, int y, GameLight light, GameCamera camera)
    {
        int spriteCount = 4;
        int sprite = (int)((double)getTime() / (double)1000.0F * (double)11.0F) % spriteCount;
        GameTexture shadowTexture = MobRegistry.Textures.mosquito_shadow;
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 32 - 8;
        return shadowTexture.initDraw().sprite(sprite + 2, getDir(), 64).light(light).pos(drawX, drawY);
    }
}