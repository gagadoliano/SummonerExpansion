package summonerexpansion.summonmobs;

import necesse.engine.eventStatusBars.EventStatusBarManager;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.ItemRegistry;
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
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.*;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RiftPortalMob extends BossMob
{
    public static LootTable lootTable = new LootTable(new OneOfLootItems(LootItem.between("purehorror", 2, 20)));
    public static LootTable privateLootTable = new LootTable(new ConditionLootItem("shadowhorrorbag", (r, o) -> {
        ServerClient client = LootTable.expectExtra(ServerClient.class, o, 1);
        return client != null && client.playerMob.getInv().getAmount(ItemRegistry.getItem("shadowhorrorbag"), false, false, true, true, "have") == 0;
    }));
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
        collision = new Rectangle(-40, -40, 50, 50);
        hitBox = new Rectangle(-40, -40, 50, 50);
        selectBox = new Rectangle(-40, -40, 50, 50);
        setKnockbackModifier(0.0F);
        setArmor(10000);
    }

    public void addSaveData(SaveData save)
    {
        super.addSaveData(save);
        save.addLong("lifeTime", lifeTime);
    }

    public void applyLoadData(LoadData save)
    {
        super.applyLoadData(save);
        lifeTime = save.getInt("lifeTime", 0);
    }

    public void setupHealthPacket(PacketWriter writer, boolean isFull)
    {
        scaling.setupHealthPacket(writer, isFull);
        super.setupHealthPacket(writer, isFull);
    }

    public void applyHealthPacket(PacketReader reader, boolean isFull)
    {
        scaling.applyHealthPacket(reader, isFull);
        super.applyHealthPacket(reader, isFull);
    }

    public void init()
    {
        super.init();
        lifeTime = 0L;
        ai = new BehaviourTreeAI(this, new RiftPortalMob.RiftPortalAINode());
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
        getLevel().lightManager.refreshParticleLightFloat(x, y, 270.0F, 0.7F);
        EventStatusBarManager.registerMobHealthStatusBar(this);
        BossNearbyBuff.applyAround(this);
    }

    public void serverTick()
    {
        super.serverTick();
        scaling.serverTick();
        ++lifeTime;
        if (lifeTime >= 3600L)
        {
            float armorReduced = (getArmor() * 10) / 100;
            setArmor((int)armorReduced);
            lifeTime = 0L;
        }
        if (getHealth() <= (getMaxHealth() / 2))
        {
            nextPhase = true;
        }
        BossNearbyBuff.applyAround(this);
    }

    public void playDeathSound() {
        SoundManager.playSound(GameResources.fadedeath3, SoundEffect.effect(this));
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        getLevel().entityManager.addParticle(new SmokePuffParticle(getLevel(), (float)getX(), (float)getY(), new Color(50, 50, 50)), Particle.GType.CRITICAL);
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
        return new StringTooltips(getDisplayName() + " " + getHealth() + "/" + getMaxHealth());
    }

    public boolean canBePushed(Mob other) {
        return false;
    }

    public boolean isBoss() {
        return true;
    }

    public boolean isHealthBarVisible() {
        return false;
    }

    public int getMaxHealth()
    {
        return super.getMaxHealth() + (int)((float)(scaling == null ? 0 : scaling.getHealthIncrease()) * getMaxHealthModifier());
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public LootTable getPrivateLootTable() {
        return privateLootTable;
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 60;
        int drawY = camera.getDrawY(y) - 60;
        DrawOptions body = texture.initDraw().sprite(0, 0, 118).mirror(moveX < 0.0F, false).alpha(0.7F).light(light).pos(drawX, drawY);
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
                spawnedMobs.forEach(Mob::remove);
            });
        }

        public void init(T mob, Blackboard<T> blackboard) {}

        public AINodeResult tick(T mob, Blackboard<T> blackboard)
        {
            if (nextPhase)
            {
                if (RiftPortalMob.this.lifeTime % 600L == 0L)
                {
                    Mob portalMob = MobRegistry.getMob("horrorspiritmob", RiftPortalMob.this.getLevel());
                    RiftPortalMob.this.getLevel().entityManager.addMob(portalMob, (float)(RiftPortalMob.this.getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(RiftPortalMob.this.getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
                    this.spawnedMobs.add(portalMob);
                }
                if (nextPhase && RiftPortalMob.this.lifeTime % 400L == 0L)
                {
                    Mob portalMob2 = MobRegistry.getMob("horrorspiritbossmob", RiftPortalMob.this.getLevel());
                    RiftPortalMob.this.getLevel().entityManager.addMob(portalMob2, (float)(RiftPortalMob.this.getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(RiftPortalMob.this.getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
                    this.spawnedMobs.add(portalMob2);
                }
            }
            else
            {
                if (RiftPortalMob.this.lifeTime % 100L == 0L)
                {
                    Mob portalMob = MobRegistry.getMob("horrorspiritmob", RiftPortalMob.this.getLevel());
                    RiftPortalMob.this.getLevel().entityManager.addMob(portalMob, (float)(RiftPortalMob.this.getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(RiftPortalMob.this.getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
                    this.spawnedMobs.add(portalMob);
                }
            }

            return AINodeResult.SUCCESS;
        }
    }
}