package summonerexpansion.mobs.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.objectEntity.interfaces.OEVicinityBuff;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.summonminions.baseminions.SentryBase;

import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

import static summonerexpansion.codes.registry.SummonerTextures.coffebeamSentry;

public class CoffeBeamSentry extends SentryBase implements OEVicinityBuff
{
    public float moveAngle;

    public CoffeBeamSentry()
    {
        super(3000F, 1000F);
        setFriction(2F);
        collision = new Rectangle(0, 0, 38, 54);
        hitBox = new Rectangle(0, 0, 38, 54);
        selectBox = new Rectangle(0, 0, 38, 54);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI<>(0, null, 0, 0, 90000, 64));
    }

    public Buff[] getBuffs() {
        return new Buff[]{BuffRegistry.getBuff("coffebeambuff")};
    }

    public int getBuffRange()
    {
        return 800;
    }

    public boolean shouldBuffPlayers() {
        return true;
    }

    public boolean shouldBuffMobs()
    {
        return false;
    }

    public Predicate<Mob> buffMobsFilter()
    {
        return (m) -> m.isPlayer;
    }

    public void applyBuffs(Mob mob)
    {
        Buff[] var2 = this.getBuffs();
        for (Buff buff : var2)
        {
            if (buff != null)
            {
                ActiveBuff ab = new ActiveBuff(buff, mob, 1F, this);
                mob.buffManager.addBuff(ab, false);
            }
        }
    }

    public void tickVicinityBuff(Mob mob)
    {
        Level level = mob.getLevel();
        int posX = (int) mob.x;
        int posY = (int) mob.y;
        tickVicinityBuff(level, posX, posY);
    }

    public void clientTick()
    {
        super.clientTick();
        tickVicinityBuff(this);
    }

    public void serverTick()
    {
        super.serverTick();
        tickVicinityBuff(this);
    }

    public Mob getFirstAttackOwner() {
        return this;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 5; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), coffebeamSentry, i, 0, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 20;
        DrawOptions body = coffebeamSentry.initDraw().light(light).rotate(moveAngle, 20, 32).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }
}