package summonerexpansion.mobs.minions.melee;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.minions.base.SummonFlyingBase;

import java.awt.*;
import java.util.List;

public class PlanetMinion extends SummonFlyingBase
{
    public GameTexture planetTexture;
    public static MaxHealthGetter MAX_HEALTH = new MaxHealthGetter(5, 10, 20, 30, 40);

    public PlanetMinion()
    {
        super();
    }

    public PlanetMinion(GameTexture planetTexture)
    {
        super();
        difficultyChanges.setMaxHealth(MAX_HEALTH);
        setArmor(10);
        setSpeed(100.0F);
        setFriction(2.0F);
        isStatic = false;
        moveAccuracy = 5;
        collision = new Rectangle(-30, -30, 60, 60);
        hitBox = new Rectangle(-30, -30, 60, 60);
        selectBox = new Rectangle();
        this.planetTexture = planetTexture;
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFlyingFollowerCollisionChaserAI<>(0, summonDamage, 50, 500, 1000, 800), new FlyingAIMover());
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 10;
        DrawOptions body = planetTexture.initDraw().sprite(0, 0, 48).light(light).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
        DrawOptions shadow = MobRegistry.Textures.rubyShield_shadow.initDraw().sprite(0, 0, 48).light(light).pos(drawX, drawY + 10);
        tileList.add((tm) -> {
            shadow.draw();
        });
    }
}