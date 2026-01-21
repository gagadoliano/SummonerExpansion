package summonerexpansion.mobs.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.summonminions.baseminions.ButterflyBase;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registry.SummonerTextures.butterflyBlueMinion;

public class BookBlueButterflyMinion extends ButterflyBase
{
    public BookBlueButterflyMinion()
    {
        super();
        setSpeed(30.0F);
        setFriction(2.0F);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFlyingFollowerCollisionChaserAI<>(1000, summonDamage, 5, 1200, 9000, 100), new FlyingAIMover());
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 22;
        int dir = getDir();
        int animationTime = 1000;
        long time = level.getTime();
        time += (new GameRandom(getUniqueID())).nextInt(animationTime);
        Point sprite = getAnimSprite(x, y, dir);
        TextureDrawOptions shadow = MobRegistry.Textures.bird_shadow.initDraw().sprite(0, dir, 32).light(light).pos(drawX, drawY);
        tileList.add((tm) -> shadow.draw());
        float bobbingFloat = GameUtils.getBobbing(time, animationTime);
        drawY -= 6;
        drawY = (int)((float)drawY + bobbingFloat * 5.0F);
        final DrawOptions options = butterflyBlueMinion.initDraw().sprite(sprite.x, sprite.y, 32).light(light).pos(drawX, drawY);
        list.add(new MobDrawable()
        {
            public void draw(TickManager tickManager)
            {
                options.draw();
            }
        });
    }

    public Point getAnimSprite(int x, int y, int dir)
    {
        long time = getTime();
        time += (new GameRandom(getUniqueID())).nextInt(200);
        return new Point(GameUtils.getAnim(time, 2, 600), getDir());
    }
}