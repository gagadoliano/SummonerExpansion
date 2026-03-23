package summonerexpansion.mobs.minions.melee;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryMinionTextures.planetVenusMinion;

public class PlanetVenusMinion extends FlyingAttackingFollowingMob
{
    public static MaxHealthGetter MAX_HEALTH = new MaxHealthGetter(5, 10, 20, 30, 40);

    public PlanetVenusMinion()
    {
        super(10);
        difficultyChanges.setMaxHealth(MAX_HEALTH);
        setArmor(10);
        setSpeed(100.0F);
        setFriction(2.0F);
        isStatic = false;
        moveAccuracy = 5;
        collision = new Rectangle(-30, -30, 60, 60);
        hitBox = new Rectangle(-30, -30, 60, 60);
        selectBox = new Rectangle();
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return summonDamage; }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFlyingFollowerCollisionChaserAI<>(0, summonDamage, 50, 500, 1000, 80), new FlyingAIMover());
    }

    public int getCollisionKnockback(Mob target) {
        return 50;
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = getAttackOwner();
        if (owner != null && target != null)
        {
            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float)knockback, this);
            collisionHitCooldowns.startCooldown(target);
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 10;
        DrawOptions body = planetVenusMinion.initDraw().sprite(0, 0, 32).light(light).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
        DrawOptions shadow = MobRegistry.Textures.rubyShield_shadow.initDraw().sprite(0, 0, 32).light(light).pos(drawX, drawY + 10);
        tileList.add((tm) -> {
            shadow.draw();
        });
    }
}