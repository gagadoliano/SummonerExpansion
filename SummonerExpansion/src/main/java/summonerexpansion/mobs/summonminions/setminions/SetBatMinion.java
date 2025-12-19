package summonerexpansion.mobs.summonminions.setminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptionsEnd;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class SetBatMinion extends FlyingAttackingFollowingMob
{
    public static GameTexture texture;
    public int lifeTime = 0;

    public SetBatMinion()
    {
        super(10);
        setSpeed(50.0F);
        setFriction(2.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle(-14, -41, 28, 40);
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return summonDamage; }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI(400, summonDamage, 10, 600, 9000, 80));
    }

    public void serverTick()
    {
        super.serverTick();
        lifeTime++;
        if (lifeTime >= 300)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        super.handleCollisionHit(target, damage, knockback);
        lifeTime += 10;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 20; ++i)
        {
            this.getLevel().entityManager.addParticle(this.x, this.y, Particle.GType.COSMETIC).movesConstantAngle((float) GameRandom.globalRandom.nextInt(360), (float)GameRandom.globalRandom.getIntBetween(5, 20)).color(new Color(10, 10, 10));
        }
    }

    public int getFlyingHeight() {
        return 20;
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 55;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        float bobbing = GameUtils.getBobbing(this.getWorldEntity().getTime(), 1000) * 5.0F;
        drawY = (int)((float)drawY + bobbing);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        final TextureDrawOptionsEnd drawOptions = texture.initDraw().sprite(sprite.x, sprite.y, 64).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public Point getAnimSprite(int x, int y, int dir)
    {
        return new Point(GameUtils.getAnim(this.getWorldEntity().getTime(), 4, 300), dir);
    }
}