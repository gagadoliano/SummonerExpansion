package summonerexpansion.mobs.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.objectEntity.interfaces.OEVicinityBuff;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptionsEnd;
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
    public int mushStart = 0;
    public int mushTimer = 0;
    public int minionLimit = 0;

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
        return 600;
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
        if (++mushStart >= mushTimer && getFollowingItemAttacker().serverFollowersManager.getFollowerCount("mouseminion") < minionLimit)
        {
            AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("mushroomminion", getFollowingItemAttacker().getLevel());
            getFollowingItemAttacker().serverFollowersManager.addFollower("mushroomminion", mob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, (p) -> minionLimit, null, false);
            mob.updateDamage(summonDamage);
            getLevel().entityManager.addMob(mob, x, y);
            mushStart = 0;
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
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, i, 8, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y)).minLevelCopy(100.0F);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 55;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY = (int)((float)drawY);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
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
        return new Point(GameUtils.getAnim(this.getWorldEntity().getTime(), 6, 200*60), dir);
    }

    public int getRockSpeed() {
        return 100*60;
    }
}