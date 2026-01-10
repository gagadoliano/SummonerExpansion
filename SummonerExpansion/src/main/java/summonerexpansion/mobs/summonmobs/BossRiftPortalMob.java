package summonerexpansion.mobs.summonmobs;

import necesse.engine.eventStatusBars.EventStatusBarManager;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
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
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.BossNearbyBuff;
import necesse.entity.mobs.hostile.bosses.FlyingBossMob;
import necesse.entity.particle.Particle;
import necesse.entity.particle.SmokePuffParticle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.*;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BossRiftPortalMob extends FlyingBossMob
{
    public static LootTable lootTable = new LootTable(new OneOfLootItems(LootItem.between("purehorror", 2, 20)));
    public static LootTable privateLootTable = new LootTable(new ConditionLootItem("shadowhorrorbag", (r, o) -> {
        ServerClient client = LootTable.expectExtra(ServerClient.class, o, 1);
        return client != null && client.playerMob.getInv().getAmount(ItemRegistry.getItem("shadowhorrorbag"), false, false, true, true, "have") == 0;
    }));
    public static MaxHealthGetter MAX_HEALTH = new MaxHealthGetter(6500, 7000, 7500, 8000, 9000);
    protected MobHealthScaling scaling = new MobHealthScaling(this);
    public static GameTexture texture;
    public static GameTexture icon;
    private long lifeTime;
    private int nextPhase = 0;

    public BossRiftPortalMob()
    {
        super(100);
        difficultyChanges.setMaxHealth(MAX_HEALTH);
        isSummoned = true;
        isHostile = true;
        collision = new Rectangle(-70, -70, 126, 102);
        hitBox = new Rectangle(-70, -70, 126, 102);
        selectBox = new Rectangle(-70, -70, 126, 102);
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
        ai = new BehaviourTreeAI<>(this, new BossRiftPortalMob.RiftPortalAINode<>());
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
        lifeTime++;
        if (getHealthPercent() > 0.8f)
        {
            nextPhase = 0;
            setArmor(10000);
        }
        else if (getHealthPercent() > 0.6f)
        {
            nextPhase = 1;
            setArmor(5000);
        }
        else if (getHealthPercent() > 0.4f)
        {
            nextPhase = 2;
            setArmor(2500);
        }
        else
        {
            nextPhase = 3;
            setArmor(1250);
        }

        BossNearbyBuff.applyAround(this);
    }

    public void playDeathSound()
    {
        SoundManager.playSound(GameResources.fadedeath3, SoundEffect.effect(this));
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        getLevel().entityManager.addParticle(new SmokePuffParticle(getLevel(), (float)getX(), (float)getY(), new Color(50, 50, 50)), Particle.GType.CRITICAL);
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
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 64;
        int drawY = camera.getDrawY(y) - 50;
        int dir = this.getDir();
        int sprite;
        if (getHealthPercent() > 0.8f)
        {
            sprite = 0;
        }
        else if (getHealthPercent() > 0.6f)
        {
            sprite = 1;
        }
        else if (getHealthPercent() > 0.4f)
        {
            sprite = 2;
        }
        else
        {
            sprite = 3;
        }
        DrawOptions options = texture.initDraw().sprite(sprite, 0, 126).size(126, 102).light(light).mirror(dir == 0, false).pos(drawX, drawY);
        TextureDrawOptions shadowOptions = this.getShadowDrawOptions(level, x, y, light, camera);
        topList.add((tm) -> {
            shadowOptions.draw();
            options.draw();
        });
    }

    protected TextureDrawOptions getShadowDrawOptions(Level level, int x, int y, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.reaper_shadow;
        int drawX = camera.getDrawX(x) - shadowTexture.getWidth() / 2;
        int drawY = camera.getDrawY(y) - shadowTexture.getHeight() / 2 + 20;
        return shadowTexture.initDraw().light(light).pos(drawX, drawY);
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
        texture.initDraw().sprite(0, 1, 126, 102).size(48, 48).draw(drawX, drawY);
    }

    public Rectangle drawOnMapBox(double tileScale, boolean isMinimap) {
        return new Rectangle(-16, -16, 32, 32);
    }

    public GameTooltips getMapTooltips()
    {
        return new StringTooltips(getDisplayName() + " " + getHealth() + "/" + getMaxHealth());
    }

    public Stream<ModifierValue<?>> getDefaultModifiers()
    {
        return Stream.of((new ModifierValue<>(BuffModifiers.SLOW, 0.0F)).max(0F), (new ModifierValue<>(BuffModifiers.POISON_DAMAGE, 0F)).max(0F), (new ModifierValue<>(BuffModifiers.FIRE_DAMAGE, 0F)).max(0F), (new ModifierValue<>(BuffModifiers.FROST_DAMAGE, 0F)).max(0F));
    }

    public class RiftPortalAINode<T extends Mob> extends AINode<T>
    {
        private ArrayList<Mob> spawnedMobs = new ArrayList<>();
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
            if (nextPhase == 0)
            {
                if (lifeTime >= 150)
                {
                    Mob portalMob = MobRegistry.getMob("horrorspiritmob", BossRiftPortalMob.this.getLevel());
                    BossRiftPortalMob.this.getLevel().entityManager.addMob(portalMob, (float)(BossRiftPortalMob.this.getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(BossRiftPortalMob.this.getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
                    this.spawnedMobs.add(portalMob);
                    lifeTime = 0;
                }
            }
            if (nextPhase == 1)
            {
                if (lifeTime == 150)
                {
                    Mob portalMob = MobRegistry.getMob("horrorspiritmob", BossRiftPortalMob.this.getLevel());
                    BossRiftPortalMob.this.getLevel().entityManager.addMob(portalMob, (float)(BossRiftPortalMob.this.getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(BossRiftPortalMob.this.getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
                    this.spawnedMobs.add(portalMob);
                }
                if (lifeTime >= 450)
                {
                    Mob portalMob = MobRegistry.getMob("horrorspiritbossmob", BossRiftPortalMob.this.getLevel());
                    BossRiftPortalMob.this.getLevel().entityManager.addMob(portalMob, (float)(BossRiftPortalMob.this.getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(BossRiftPortalMob.this.getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
                    this.spawnedMobs.add(portalMob);
                    lifeTime = 0;
                }
            }
            if (nextPhase == 2)
            {
                if (lifeTime == 150)
                {
                    Mob portalMob = MobRegistry.getMob("horrorspiritmob", BossRiftPortalMob.this.getLevel());
                    BossRiftPortalMob.this.getLevel().entityManager.addMob(portalMob, (float)(BossRiftPortalMob.this.getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(BossRiftPortalMob.this.getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
                    this.spawnedMobs.add(portalMob);
                }
                if (lifeTime == 400)
                {
                    Mob portalMob = MobRegistry.getMob("horrorspiritmob", BossRiftPortalMob.this.getLevel());
                    BossRiftPortalMob.this.getLevel().entityManager.addMob(portalMob, (float)(BossRiftPortalMob.this.getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(BossRiftPortalMob.this.getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
                    this.spawnedMobs.add(portalMob);
                }
                if (lifeTime >= 500)
                {
                    Mob portalMob = MobRegistry.getMob("horrorspiritbossmob", BossRiftPortalMob.this.getLevel());
                    BossRiftPortalMob.this.getLevel().entityManager.addMob(portalMob, (float)(BossRiftPortalMob.this.getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(BossRiftPortalMob.this.getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
                    this.spawnedMobs.add(portalMob);
                    lifeTime = 0;
                }
            }
            if (nextPhase == 3)
            {
                if (lifeTime == 450)
                {
                    Mob portalMob = MobRegistry.getMob("horrorspiritcultmelee", BossRiftPortalMob.this.getLevel());
                    BossRiftPortalMob.this.getLevel().entityManager.addMob(portalMob, (float)(BossRiftPortalMob.this.getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(BossRiftPortalMob.this.getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
                    this.spawnedMobs.add(portalMob);
                }
                if (lifeTime == 500)
                {
                    Mob portalMob = MobRegistry.getMob("horrorspiritcultmagic", BossRiftPortalMob.this.getLevel());
                    BossRiftPortalMob.this.getLevel().entityManager.addMob(portalMob, (float)(BossRiftPortalMob.this.getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(BossRiftPortalMob.this.getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
                    this.spawnedMobs.add(portalMob);
                }
                if (lifeTime >= 550)
                {
                    Mob portalMob = MobRegistry.getMob("horrorspiritcultrange", BossRiftPortalMob.this.getLevel());
                    BossRiftPortalMob.this.getLevel().entityManager.addMob(portalMob, (float)(BossRiftPortalMob.this.getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(BossRiftPortalMob.this.getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
                    this.spawnedMobs.add(portalMob);
                    lifeTime = 0;
                }
            }

            return AINodeResult.SUCCESS;
        }
    }
}