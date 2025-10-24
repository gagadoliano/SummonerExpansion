package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class BookBeeMinion extends FlyingAttackingFollowingMob
{
    public int BeeHitCount;

    public BookBeeMinion()
    {
        super(10);
        setSpeed(50.0F);
        setFriction(2.0F);
        setSwimSpeed(1.0F);
        collision = new Rectangle(-7, -5, 14, 10);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle();
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return summonDamage; }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI(800, summonDamage, 5, 500, 1000, 64));
    }

    @Override
    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = this.getAttackOwner();

        BeeHitCount++;
        if (BeeHitCount >= 10)
        {
            remove();
        }

        if (owner != null && target != null)
        {
            ActiveBuff buff = new ActiveBuff(BuffRegistry.getBuff("honeydebuff"), target, 600F, this);
            target.addBuff(buff, true);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 22;
        int dir = this.getDir();
        int animationTime = 1000;
        long time = level.getTime();
        time += (new GameRandom(this.getUniqueID())).nextInt(animationTime);
        Point sprite = this.getAnimSprite(x, y, dir);
        TextureDrawOptions shadow = MobRegistry.Textures.honeyBee.shadow.initDraw().sprite(0, dir, 32).light(light).pos(drawX, drawY);
        tileList.add((tm) -> {
            shadow.draw();
        });
        float bobbingFloat = GameUtils.getBobbing(time, animationTime);
        drawY -= 6;
        drawY = (int)((float)drawY + bobbingFloat * 5.0F);
        final DrawOptions options = MobRegistry.Textures.honeyBee.body.initDraw().sprite(sprite.x, sprite.y, 32).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    public Point getAnimSprite(int x, int y, int dir)
    {
        long time = this.getTime();
        time += (new GameRandom(this.getUniqueID())).nextInt(200);
        return new Point(GameUtils.getAnim(time, 2, 200), dir);
    }

    public int getFlyingHeight()
    {
        return 20;
    }
}
