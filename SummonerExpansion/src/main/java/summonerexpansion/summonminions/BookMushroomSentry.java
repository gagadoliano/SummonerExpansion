package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.objectEntity.interfaces.OEVicinityBuff;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

public class BookMushroomSentry extends AttackingFollowingMob implements OEVicinityBuff
{
    public static GameTexture texture;
    public float moveAngle;
    public int mushtimer;

    public BookMushroomSentry()
    {
        super(10);
        setSpeed(0.0F);
        setFriction(0F);
        attackAnimTime = 750;
        attackCooldown = 2000;
        collision = new Rectangle(0, 0, 86, 62);
        hitBox = new Rectangle(0, 0, 86, 62);
        selectBox = new Rectangle();
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI(150, summonDamage, 1, 500, 64000, 64));
    }

    public Buff[] getBuffs()
    {
        return new Buff[]{BuffRegistry.getBuff("mushroombuff")};
    }
    public Buff[] getDebuffs()
    {
        return new Buff[]{BuffRegistry.getBuff("mushroomdebuff")};
    }

    public int getBuffRange()
    {
        return 500;
    }

    public boolean shouldBuffPlayers(){
        return true;
    }

    public boolean shouldBuffMobs()
    {
        return true;
    }

    public Predicate<Mob> buffMobsFilter()
    {
        return (m) -> !m.isSummoned;
    }

    public void applyBuffs(Mob mob)
    {
        Buff[] var2 = this.getBuffs();
        Buff[] var3 = this.getDebuffs();
        if (mob.isPlayer)
        {
            for (Buff buff : var2)
            {
                if (buff != null)
                {
                    ActiveBuff ab = new ActiveBuff(buff, mob, 120, this);
                    mob.buffManager.addBuff(ab, false);
                }
            }
        }
        if (mob.isHostile)
        {
            for (Buff buff : var3)
            {
                if (buff != null)
                {
                    ActiveBuff ab = new ActiveBuff(buff, mob, 120, this);
                    mob.buffManager.addBuff(ab, false);
                }
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
        if (++mushtimer >= 600)
        {
            AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("mushroomminion", getFollowingItemAttacker().getLevel());
            getFollowingItemAttacker().serverFollowersManager.addFollower("mushroomminion", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, (p) -> 10, null, false);
            mob.updateDamage(summonDamage);
            getLevel().entityManager.addMob(mob, x, y);
            mushtimer = 0;
        }
        tickVicinityBuff(this);
    }

    @Override
    public Mob getFirstAttackOwner() {
        return this;
    }

    public boolean canBePushed(Mob other) {
        return false;
    }

    public boolean canPushMob(Mob other){
        return false;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 5; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), texture, i, 0, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 20;
        DrawOptions body = texture.initDraw().light(light).rotate(this.moveAngle, 16, 20).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }
}
