package summonerexpansion.summonmobs;

import necesse.engine.eventStatusBars.EventStatusBarManager;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.MusicRegistry;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.AINode;
import necesse.entity.mobs.ai.behaviourTree.AINodeResult;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.Blackboard;
import necesse.entity.mobs.buffs.staticBuffs.BossNearbyBuff;
import necesse.entity.mobs.hostile.bosses.BossMob;
import necesse.entity.particle.Particle;
import necesse.entity.particle.SmokePuffParticle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RiftPortalMob extends BossMob
{
    public static MaxHealthGetter MAX_HEALTH = new MaxHealthGetter(1500, 1600, 1700, 1800, 2000);
    protected MobHealthScaling scaling = new MobHealthScaling(this);
    public static GameTexture texture;
    public static GameTexture icon;
    private long lifeTime;
    private boolean nextPhase = false;

    public RiftPortalMob()
    {
        super(100);
        difficultyChanges.setMaxHealth(MAX_HEALTH);
        isSummoned = true;
        isHostile = true;
        collision = new Rectangle(0, 0, 118, 118);
        hitBox = new Rectangle(0, 0, 118, 118);
        selectBox = new Rectangle(0, 0, 118, 118);
        setKnockbackModifier(0.0F);
        setArmor(100000);
    }

    public void addSaveData(SaveData save)
    {
        super.addSaveData(save);
        save.addLong("lifeTime", this.lifeTime);
    }

    public void applyLoadData(LoadData save)
    {
        super.applyLoadData(save);
        this.lifeTime = (long)save.getInt("lifeTime", 0);
    }

    public void setupHealthPacket(PacketWriter writer, boolean isFull) {
        this.scaling.setupHealthPacket(writer, isFull);
        super.setupHealthPacket(writer, isFull);
    }

    public void applyHealthPacket(PacketReader reader, boolean isFull) {
        this.scaling.applyHealthPacket(reader, isFull);
        super.applyHealthPacket(reader, isFull);
    }

    public void init()
    {
        super.init();
        lifeTime = 0L;
        this.ai = new BehaviourTreeAI(this, new RiftPortalMob.RiftPortalAINode());
    }

    public void tickMovement(float delta)
    {
        dx = 0.0F;
        dy = 0.0F;
    }

    public void clientTick()
    {
        super.clientTick();
        SoundManager.setMusic(MusicRegistry.TheFirstTrial, SoundManager.MusicPriority.EVENT, 1.5F);
        getLevel().lightManager.refreshParticleLightFloat(this.x, this.y, 270.0F, 0.7F);
        EventStatusBarManager.registerMobHealthStatusBar(this);
        BossNearbyBuff.applyAround(this);
    }

    public void serverTick()
    {
        super.serverTick();
        scaling.serverTick();
        ++this.lifeTime;
        if (this.lifeTime >= 6000L)
        {
            setHealth(0);
        }

        if (this.getHealth() <= (this.getMaxHealth() / 2))
        {
            nextPhase = true;
        }
        BossNearbyBuff.applyAround(this);
    }

    protected void playDeathSound() {
        SoundManager.playSound(GameResources.fadedeath3, SoundEffect.effect(this));
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        this.getLevel().entityManager.addParticle(new SmokePuffParticle(this.getLevel(), (float)this.getX(), (float)this.getY(), new Color(50, 50, 50)), Particle.GType.CRITICAL);
    }

    public boolean shouldDrawOnMap() {
        return true;
    }

    @Override
    public void drawOnMap(TickManager tickManager, Client client, int x, int y, double tileScale, Rectangle drawBounds, boolean isMinimap)
    {
        super.drawOnMap(tickManager, client, x, y, tileScale, drawBounds, isMinimap);
        int drawX = x - 16;
        int drawY = y - 16;
        icon.initDraw().sprite(0, 0, 64).size(32, 32).draw(drawX, drawY);
    }

    public Rectangle drawOnMapBox(double tileScale, boolean isMinimap) {
        return new Rectangle(-16, -16, 32, 32);
    }

    public GameTooltips getMapTooltips()
    {
        return new StringTooltips(this.getDisplayName() + " " + this.getHealth() + "/" + this.getMaxHealth());
    }

    public boolean canBePushed(Mob other) {
        return false;
    }

    public boolean isBoss() {return true;}

    public boolean isHealthBarVisible() {return false;}

    public int getMaxHealth()
    {
        return super.getMaxHealth() + (int)((float)(this.scaling == null ? 0 : this.scaling.getHealthIncrease()) * this.getMaxHealthModifier());
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 20;
        DrawOptions body = texture.initDraw().sprite(0, 0, 118).mirror(this.moveX < 0.0F, false).alpha(0.7F).light(light).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }

    public class RiftPortalAINode<T extends Mob> extends AINode<T>
    {
        private ArrayList<Mob> spawnedMobs = new ArrayList();

        public RiftPortalAINode() {}

        protected void onRootSet(AINode<T> root, T mob, Blackboard<T> blackboard)
        {
            blackboard.onRemoved((e) -> {
                this.spawnedMobs.forEach(Mob::remove);
            });
        }

        public void init(T mob, Blackboard<T> blackboard) {}

        public AINodeResult tick(T mob, Blackboard<T> blackboard)
        {
            if (RiftPortalMob.this.lifeTime % 100L == 0L)
            {
                Mob portalMob = MobRegistry.getMob("horrorspiritmob", RiftPortalMob.this.getLevel());
                RiftPortalMob.this.getLevel().entityManager.addMob(portalMob, (float)(RiftPortalMob.this.getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(RiftPortalMob.this.getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
                this.spawnedMobs.add(portalMob);
            }

            if (nextPhase && RiftPortalMob.this.lifeTime % 200L == 0L)
            {
                Mob portalMob2 = MobRegistry.getMob("horrorspiritbossmob", RiftPortalMob.this.getLevel());
                RiftPortalMob.this.getLevel().entityManager.addMob(portalMob2, (float)(RiftPortalMob.this.getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(RiftPortalMob.this.getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
                this.spawnedMobs.add(portalMob2);
            }
            return AINodeResult.SUCCESS;
        }
    }
}