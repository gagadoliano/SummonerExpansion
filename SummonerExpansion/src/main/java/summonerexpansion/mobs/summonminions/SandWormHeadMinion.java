package summonerexpansion.mobs.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.ComputedObjectValue;
import necesse.engine.util.GameLinkedList;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerCirclingFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingWormMobHead;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class SandWormHeadMinion extends AttackingFollowingWormMobHead<SandWormBodyMinion, SandWormHeadMinion>
{
    public static GameTexture texture;
    public static float lengthPerBodyPart = 32.0F;
    public static float waveLength = 350.0F;
    public static final int totalBodyParts = 4;

    public SandWormHeadMinion()
    {
        super(10, waveLength, 70.0F, totalBodyParts, 20.0F, -24.0F);
        moveAccuracy = 10;
        setSpeed(100.0F);
        accelerationMod = 1.0F;
        decelerationMod = 1.0F;
        collision = new Rectangle(-16, -14, 32, 28);
        hitBox = new Rectangle(-20, -16, 40, 32);
        selectBox = new Rectangle();
    }

    public GameMessage getLocalization() {
        return new LocalMessage("mob", "sandwormminion");
    }

    protected float getDistToBodyPart(SandWormBodyMinion bodyPart, int index, float lastDistance) {
        return lengthPerBodyPart;
    }

    public void init()
    {
        super.init();
        this.ai = new BehaviourTreeAI(this, new PlayerCirclingFollowerCollisionChaserAI(500, null, 15, -1, 1), new FlyingAIMover());
    }

    protected SandWormBodyMinion createNewBodyPart(int index)
    {
        SandWormBodyMinion bodyPart = new SandWormBodyMinion();
        bodyPart.spriteY = index == 3 ? 2 : 1;
        bodyPart.collisionDamage = this.summonDamage.modDamage(0.8F);
        bodyPart.modifiers = this.summonModifiers;
        return bodyPart;
    }

    public boolean canBeHit(Attacker attacker) {
        return false;
    }

    protected void playMoveSound() {}

    public float getTurnSpeed(float delta) {
        return super.getTurnSpeed(delta) * 1.2F;
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return summonDamage; }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, 2, GameRandom.globalRandom.nextInt(6), 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        if (this.isVisible())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(x) - 32;
            int drawY = camera.getDrawY(y);
            float headAngle = GameMath.fixAngle(GameMath.getAngle(new Point2D.Float(this.dx, this.dy)));
            final MobDrawable headDrawable = WormMobHead.getAngledDrawable(new GameSprite(texture, 0, 0, 64), null, light, (int)this.height, headAngle, drawX, drawY, 96);
            new ComputedObjectValue(null, () -> 0.0);
            ComputedObjectValue<GameLinkedList<WormMoveLine>.Element, Double> shoulderLine = WormMobHead.moveDistance(this.moveLines.getFirstElement(), 32.0);
            final MobDrawable shoulderDrawable;
            if (shoulderLine.object != null)
            {
                Point2D.Double shoulderPos = WormMobHead.linePos(shoulderLine);
                GameLight shoulderLight = level.getLightLevel((int)(shoulderPos.x / 32.0), (int)(shoulderPos.y / 32.0));
                int shoulderDrawX = camera.getDrawX((float)shoulderPos.x) - 32;
                int shoulderDrawY = camera.getDrawY((float)shoulderPos.y);
                float shoulderHeight = this.getWaveHeight(shoulderLine.object.object.movedDist + shoulderLine.get().floatValue());
                float shoulderAngle = GameMath.fixAngle((float)GameMath.getAngle(new Point2D.Double((double)this.x - shoulderPos.x, (double)(this.y - this.height) - (shoulderPos.y - (double)shoulderHeight))));
                shoulderDrawable = WormMobHead.getAngledDrawable(new GameSprite(texture, 0, 1, 64), null, shoulderLight, (int)shoulderHeight, shoulderAngle, shoulderDrawX, shoulderDrawY, 96);
            }
            else
            {
                shoulderDrawable = null;
            }
            topList.add(new MobDrawable() {
                public void draw(TickManager tickManager) {
                    if (shoulderDrawable != null) {
                        shoulderDrawable.draw(tickManager);
                    }

                    headDrawable.draw(tickManager);
                }
            });
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
}