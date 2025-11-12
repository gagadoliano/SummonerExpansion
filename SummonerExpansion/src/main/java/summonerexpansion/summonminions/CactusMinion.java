package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GroundPillar;
import necesse.engine.util.GroundPillarList;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.ConfusedPlayerChaserWandererAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.DrawOptionsList;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.lootTable.LootTable;
import necesse.level.gameTile.GameTile;
import necesse.level.maps.CollisionFilter;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class CactusMinion extends AttackingFollowingMob
{
    public static GameTexture texture;
    private final GroundPillarList<Mound> mounds = new GroundPillarList();
    private int moundCounter;
    private double moveBuffer;
    private double moveCounter;
    private long stateChangeTime;
    private boolean isUp;
    private boolean nextIsUp;
    private boolean wantIsUp;
    private int wantIsUpCounter;

    public CactusMinion()
    {
        super(10);
        setSpeed(40.0F);
        setFriction(3.0F);
        attackCooldown = 1200;
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle();
        isUp = true;
        nextIsUp = true;
    }


    public void setupMovementPacket(PacketWriter writer)
    {
        super.setupMovementPacket(writer);
        writer.putNextBoolean(this.isUp);
        writer.putNextBoolean(this.nextIsUp);
        writer.putNextBoolean(this.wantIsUp);
        long stateChangeTimeDelta = this.getWorldEntity().getLocalTime() - this.stateChangeTime;
        writer.putNextLong(stateChangeTimeDelta);
        writer.putNextByteUnsigned(this.wantIsUpCounter);
    }

    public void applyMovementPacket(PacketReader reader, boolean isDirect)
    {
        super.applyMovementPacket(reader, isDirect);
        this.isUp = reader.getNextBoolean();
        this.nextIsUp = reader.getNextBoolean();
        this.wantIsUp = reader.getNextBoolean();
        long stateChangeTimeDelta = reader.getNextLong();
        this.stateChangeTime = this.getWorldEntity().getLocalTime() - stateChangeTimeDelta;
        this.wantIsUpCounter = reader.getNextByteUnsigned();
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<CactusMinion>(600, 400, true, false, 900, 70)
        {
            public boolean attackTarget(CactusMinion mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);

                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("cactusproj", mob.getLevel(), mob.x, mob.y, target.x, target.y, (80.0F * projVel), 640, summonDamage, mob);
                    projectile.setTargetPrediction(target, -20.0F);
                    projectile.moveDist(20.0);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public boolean canAttack() {
        return super.canAttack() && this.isUp && this.nextIsUp;
    }

    public void tickMovement(float delta)
    {
        label16:
        while(true)
        {
            if (delta > 0.0F)
            {
                int rockSpeed = this.getRockSpeed();
                Point2D.Float lastPos = new Point2D.Float(this.x, this.y);
                super.tickMovement(Math.min((float)(rockSpeed * 2), delta));
                delta -= (float)(rockSpeed * 2);
                double dist = lastPos.distance(this.x, this.y);
                this.moveBuffer += dist;
                this.moveCounter += dist;
                while(true)
                {
                    if (!(this.moveBuffer > (double)rockSpeed))
                    {
                        continue label16;
                    }
                    this.addNewMound(GameMath.normalize(this.x - lastPos.x, this.y - lastPos.y), false);
                    this.moveBuffer -= rockSpeed;
                }
            }
            return;
        }
    }

    protected void calcAcceleration(float speed, float friction, float moveX, float moveY, float delta)
    {
        super.calcAcceleration(speed, friction, moveX, moveY, delta);
        if (this.isUp || this.nextIsUp)
        {
            this.dx = 0.0F;
            this.dy = 0.0F;
        }
    }

    public void setFacingDir(float deltaX, float deltaY) {
    }

    public void clientTick()
    {
        super.clientTick();
        this.tickStateChange();
        synchronized(this.mounds)
        {
            this.mounds.clean(this.getWorldEntity().getLocalTime(), this.moveCounter);
        }
    }

    public void serverTick()
    {
        super.serverTick();
        this.tickStateChange();
        synchronized(this.mounds)
        {
            this.mounds.clean(this.getWorldEntity().getLocalTime(), this.moveCounter);
        }
    }

    public CollisionFilter getLevelCollisionFilter() {
        return super.getLevelCollisionFilter().allLiquidTiles();
    }

    private void tickStateChange()
    {
        if (this.nextIsUp != this.isUp)
        {
            long currentTime = this.getWorldEntity().getLocalTime();
            if (currentTime > this.stateChangeTime + 200L)
            {
                this.isUp = this.nextIsUp;
            }
        }
        else if (this.isAccelerating() && this.isUp && this.canAttack())
        {
            this.changeIsUp(false);
        }
        else if (!this.isAccelerating() && !this.isUp)
        {
            this.changeIsUp(true);
        }
    }

    private float getStateChangeProgress()
    {
        if (this.nextIsUp != this.isUp)
        {
            long currentTime = this.getWorldEntity().getLocalTime();
            if (currentTime <= this.stateChangeTime + 200L)
            {
                return GameMath.limit((float)(currentTime - this.stateChangeTime) / 200.0F, 0.0F, 1.0F);
            }
        }
        return 1.0F;
    }

    private void changeIsUp(boolean isUp)
    {
        if (this.nextIsUp != isUp)
        {
            if (this.wantIsUp != isUp)
            {
                this.wantIsUp = isUp;
                this.wantIsUpCounter = 0;
            }
            else
            {
                ++this.wantIsUpCounter;
                if (this.wantIsUpCounter >= 4)
                {
                    this.stateChangeTime = this.getWorldEntity().getLocalTime();
                    this.nextIsUp = isUp;
                    this.wantIsUpCounter = 0;
                }
            }
        }
    }

    private void addNewMound(Point2D.Float dir, boolean isFirst)
    {
        Point2D.Float perpDir = GameMath.getPerpendicularDir(dir.x, dir.y);
        int offset = this.moundCounter % 2 == 0 ? 5 : -5;
        synchronized(this.mounds)
        {
            this.mounds.add(new Mound(this.getX() + (int)(perpDir.x * (float)offset + dir.x * 10.0F) + GameRandom.globalRandom.getIntBetween(-2, 2) + (isFirst ? offset : 0), this.getY() + (int)(perpDir.y * (float)offset * 0.7F + dir.y * 10.0F) + GameRandom.globalRandom.getIntBetween(-2, 2), this.moveCounter - (double)(isFirst ? 20 : 0), this.getWorldEntity().getLocalTime()));
        }

        ++this.moundCounter;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, 2, i, 16, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    protected void addExtraDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addExtraDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        long currentTime = this.getWorldEntity().getLocalTime();
        synchronized(this.mounds)
        {
            this.mounds.clean(currentTime, this.moveCounter);
            Iterator var13 = this.mounds.iterator();
            while(var13.hasNext())
            {
                final Mound mound = (Mound)var13.next();
                final DrawOptions moundDraw = mound.getDrawOptions(level, currentTime, this.moveCounter, camera);
                if (moundDraw != null)
                {
                    list.add(new LevelSortedDrawable(this)
                    {
                        public int getSortY() {
                            return mound.y;
                        }

                        public void draw(TickManager tickManager) {
                            moundDraw.draw();
                        }
                    });
                }
            }
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x);
        int drawY = camera.getDrawY(y);
        int dir = this.getDir();
        int spriteY = 0;
        if (dir == 3 || dir == 2)
        {
            spriteY = 1;
        }
        float changeProgress = this.getStateChangeProgress();
        if (!this.nextIsUp)
        {
            changeProgress = Math.abs(changeProgress - 1.0F);
        }
        int changeProgressY = (int)(changeProgress * 32.0F);
        final DrawOptionsList draws = new DrawOptionsList();
        draws.add(texture.initDraw().spriteSection(0, spriteY, 32, 0, 32, 0, changeProgressY).light(light).pos(drawX - 16, drawY + 32 - changeProgressY - 30));
        GameTile tile = level.getTile(x / 32, y / 32);
        if (!tile.isLiquid)
        {
            Color moundColor = tile.getMapColor(level, x / 32, y / 32);
            int moundWidth = MobRegistry.Textures.mound1.getWidth();
            int moundHeight = MobRegistry.Textures.mound1.getHeight();
            int moundProgressY = (int)(changeProgress * (float)moundHeight);
            draws.add(MobRegistry.Textures.mound1.initDraw().section(0, moundWidth, 0, moundProgressY).color(moundColor).light(light).pos(drawX - moundWidth / 2 - 5, drawY + moundHeight / 2 - moundProgressY));
            draws.add(MobRegistry.Textures.mound1.initDraw().section(0, moundWidth, 0, moundProgressY).color(moundColor).light(light).pos(drawX - moundWidth / 2 + 5, drawY + moundHeight / 2 - moundProgressY));
        }
        float attackProgress = this.getAttackAnimProgress();
        final DrawOptions arms;
        if (this.isAttacking)
        {
            arms = ItemAttackDrawOptions.start(dir).armSprite(texture, 0, 2, 32).setOffsets(dir == 3 ? 34 : 28, 18, 10, 15, 12, 4, 12).swingRotation(attackProgress).light(light).pos(drawX - 31, drawY - 45);
        }
        else
        {
            arms = null;
        }
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                draws.draw();
                if (arms != null) {
                    arms.draw();
                }
            }
        });
    }

    public int getRockSpeed() {
        return 5;
    }

    private static class Mound extends GroundPillar
    {
        public GameTexture texture;

        public Mound(int x, int y, double spawnDistance, long spawnTime)
        {
            super(x, y, spawnDistance, spawnTime);
            this.texture = GameRandom.globalRandom.getOneOf(MobRegistry.Textures.mound1, MobRegistry.Textures.mound2, MobRegistry.Textures.mound3);
        }

        public DrawOptions getDrawOptions(Level level, long currentTime, double distanceMoved, GameCamera camera)
        {
            GameTile tile = level.getTile(this.x / 32, this.y / 32);
            if (tile.isLiquid)
            {
                return null;
            }
            else
            {
                GameLight light = level.getLightLevel(this.x / 32, this.y / 32);
                Color color = tile.getMapColor(level, this.x / 32, this.y / 32);
                int drawX = camera.getDrawX(this.x);
                int drawY = camera.getDrawY(this.y);
                double height = this.getHeight(currentTime, distanceMoved);
                int endY = (int)(height * (double)this.texture.getHeight());
                return this.texture.initDraw().section(0, this.texture.getWidth(), 0, endY).color(color).light(light).pos(drawX - this.texture.getWidth() / 2, drawY - endY);
            }
        }
    }
}