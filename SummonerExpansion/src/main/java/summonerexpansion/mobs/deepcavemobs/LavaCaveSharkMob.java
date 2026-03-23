package summonerexpansion.mobs.deepcavemobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.packet.PacketMobAttack;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.sound.SoundManager;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.gameAreaSearch.GameAreaStream;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.Blackboard;
import necesse.entity.mobs.ai.behaviourTree.composites.SelectorAINode;
import necesse.entity.mobs.ai.behaviourTree.event.ConfuseWanderAIEvent;
import necesse.entity.mobs.ai.behaviourTree.leaves.ConfusedWandererAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.WandererAINode;
import necesse.entity.mobs.ai.behaviourTree.trees.CollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.AIMover;
import necesse.entity.mobs.ai.behaviourTree.util.TargetFinderDistance;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.mobs.hostile.ItemAttackerRaiderMob;
import necesse.entity.mobs.networkField.BooleanNetworkField;
import necesse.entity.mobs.summon.WoodBoatMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.OneOfLootItems;
import necesse.level.maps.CollisionFilter;
import necesse.level.maps.Level;
import necesse.level.maps.TilePosition;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.function.Supplier;

import static summonerexpansion.codes.registries.RegistryMobTextures.lavaShark;

public class LavaCaveSharkMob extends HostileMob
{
    protected double deltaCounter = 0.0F;
    public BooleanNetworkField isChasing;
    protected int swimParticleOffset = 13;
    public static OneOfLootItems randomFishLoot = new OneOfLootItems(new ChanceLootItem(0.6F, "mackerel"), new ChanceLootItem(0.6F, "herring"), new ChanceLootItem(0.6F, "tuna"), new ChanceLootItem(0.6F, "salmon"), new ChanceLootItem(0.6F, "cod"));
    public static LootTable lootTable;

    public LavaCaveSharkMob()
    {
        super(900);
        setSpeed(30.0F);
        setSwimSpeed(1.0F);
        setFriction(1.0F);
        collision = new Rectangle(-16, -14, 32, 28);
        hitBox = new Rectangle(-20, -16, 40, 32);
        selectBox = new Rectangle(-20, -20, 40, 40);
        swimMaskMove = 0;
        swimMaskOffset = 0;
        swimSinkOffset = 0;
        isChasing = registerNetworkField(new BooleanNetworkField(false));
        setKnockbackModifier(0.5F);
        setTeam(-1);
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new LavaSharkAI<>(900), new AIMover());
        updateSpeed();
    }

    public float getFullInLiquidAtPercent(int x, int y) {
        return 0.4F;
    }

    public void tickMovement(float delta)
    {
        super.tickMovement(delta);
        if (isClient())
        {
            if (dx != 0.0F || dy != 0.0F)
            {
                deltaCounter += delta * Math.max(0.2F, getCurrentSpeed() / 30.0F);
                if (deltaCounter >= (double)50.0F)
                {
                    deltaCounter -= 50.0F;
                    WoodBoatMob.addParticleEffects(this, 0.0F, 0.0F, (float)swimParticleOffset);
                }
            }
        }
    }

    public void clientTick()
    {
        super.clientTick();
        updateSpeed();
    }

    public void serverTick()
    {
        super.serverTick();
        Object chaserTarget = ai.blackboard.get("chaserTarget");
        isChasing.set(chaserTarget != null);
        updateSpeed();
    }

    public void updateSpeed()
    {
        if (isChasing.get())
        {
            setSpeed(95.0F);
        }
        else
        {
            setSpeed(30.0F);
        }
    }

    public boolean isValidSpawnLocation(Server server, ServerClient client, int targetX, int targetY)
    {
        return (new MobSpawnLocation(this, targetX, targetY)).checkMobSpawnLocation().checkInLiquid().checkMaxMobsAround(1, Mob.MOB_SPAWN_AREA.maxSpawnDistance * 3, (m) -> m instanceof LavaCaveSharkMob, client).validAndApply();
    }

    public MobSpawnLocation checkSpawnLocation(MobSpawnLocation location)
    {
        return location.checkNotLevelCollides().checkTile((tileX, tileY) -> getLevel().liquidManager.getTileMobHeightPercent(null, tileX, tileY) > 0.8F);
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections)
    {
        super.showAttack(x, y, seed, showAllDirections);
        if (isClient())
        {
            SoundManager.playSound((new SoundSettings(GameResources.sharkAttack)).volume(0.4F), this);
        }
    }

    public void updateAttackDir(int x, int y, boolean showAllDirections) {
    }

    public CollisionFilter getLevelCollisionFilter() {
        return super.getLevelCollisionFilter().allLandTiles();
    }

    public boolean estimateCanMoveTo(int tileX, int tileY, boolean acceptAdjacentTile)
    {
        return getLevel().isLiquidTile(tileX, tileY) && super.estimateCanMoveTo(tileX, tileY, acceptAdjacentTile);
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 3; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), lavaShark, i, 12, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 48;
        int drawY = camera.getDrawY(y) - 48;
        Point sprite = getAnimSprite(x, y, getDir());
        final DrawOptions options = getSharkTexture().initDraw().sprite(sprite.x, sprite.y, 96).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    protected GameTexture getSharkTexture() {
        return lavaShark;
    }

    protected TextureDrawOptions getShadowDrawOptions(Level level, int x, int y, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.human_baby_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2;
        drawY += getBobbing(x, y);
        return shadowTexture.initDraw().sprite(getDir(), 0, res).light(light).pos(drawX, drawY);
    }

    public Point getAnimSprite(int x, int y, int dir)
    {
        Point p = new Point(0, dir);
        if (Math.abs(dx) <= 0.01F && Math.abs(dy) <= 0.01F)
        {
            p.x = 0;
        }
        else
        {
            p.x = (int)(getDistanceRan() / (double)getRockSpeed()) % 4 + 1;
        }
        return p;
    }

    public boolean isLavaImmune() {
        return true;
    }

    public int getRockSpeed() {
        return 14;
    }

    public int getTileWanderPriority(TilePosition pos, Biome baseBiome)
    {
        if (pos.tileID() == TileRegistry.lavaID)
        {
            int height = pos.level.liquidManager.getHeight(pos.tileX, pos.tileY);
            return height < -7 ? 1000 : Math.abs(height) * 100;
        }
        else
        {
            return -1000;
        }
    }

    protected SoundSettings getHurtSound() {
        return (new SoundSettings(GameResources.sharkHurt)).volume(0.2F);
    }

    protected SoundSettings getDeathSound() {
        return (new SoundSettings(GameResources.sharkDeath)).volume(0.7F);
    }

    static
    {
        lootTable = new LootTable(randomMapDrop, randomFishLoot, new LootItem("sharklavascales", GameRandom.getIntBetween(GameRandom.globalRandom, 1, 5)), new ChanceLootItem(0.2F, "sharktooth"));
    }

    public static class LavaSharkAI<T extends LavaCaveSharkMob> extends SelectorAINode<T>
    {
        protected long lastCritterTargetTime = 0L;
        protected long critterAttackCooldown = 8000L;

        public LavaSharkAI(int searchDistance)
        {
            this.addChild(new ConfusedWandererAINode<>());
            this.addChild(new CollisionChaserAI<T>(searchDistance, new GameDamage(50.0F), 25)
            {
                public void init(T mob, Blackboard<T> blackboard)
                {
                    super.init(mob, blackboard);
                    this.targetFinderAINode.canRegainSameTargetIfLostToTimer = false;
                    this.targetFinderAINode.loseTargetMaxCooldown = 100;
                    this.targetFinderAINode.loseTargetMinCooldown = 100;
                }

                public GameAreaStream<Mob> streamPossibleTargets(T mob, Point base, TargetFinderDistance<T> distance)
                {
                    return distance.streamMobsAndPlayersInRange(base, mob).filter((m) ->
                    {
                        if (m == mob)
                        {
                            return false;
                        }
                        else if (!mobIsInWater(m))
                        {
                            return false;
                        }
                        else if (m.isPlayer)
                        {
                            return true;
                        }
                        else if (m.isCritter)
                        {
                            return lastCritterTargetTime + critterAttackCooldown < m.getTime();
                        }
                        else return m instanceof ItemAttackerRaiderMob;
                    });
                }
                public boolean attackTarget(T mob, Mob target)
                {
                    return sharkAttack(mob, target, () -> super.attackTarget(mob, target));
                }
            });
            this.addChild(new WandererAINode<T>(0)
            {
                public void init(T mob, Blackboard<T> blackboard)
                {
                    super.init(mob, blackboard);
                    this.searchRadius = 15;
                }
            });
        }

        protected boolean sharkAttack(T mob, Mob target, Supplier<Boolean> superAttackTarget)
        {
            boolean superAttack = superAttackTarget.get();
            if (superAttack)
            {
                if (target.isCritter)
                {
                    this.lastCritterTargetTime = mob.getTime();
                }
                else
                {
                    if ((float)target.getHealth() > 0.0F)
                    {
                        target.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.SHARK_BLEED, target, 31.0F, mob), true);
                        target.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.ON_FIRE, target, 31.0F, mob), true);
                    }
                    mob.getServer().network.sendToAllClients(new PacketMobAttack(mob, mob.getX(), mob.getY(), true));
                    Point2D.Float attackDir = GameMath.normalize(target.x - mob.x, target.y - mob.y);
                    float attackAngle = GameMath.getAngle(attackDir);
                    float runAwayAngle = GameMath.fixAngle(attackAngle + GameRandom.globalRandom.getFloatBetween(-20.0F, 20.0F));
                    Point2D.Float runAwayDir = GameMath.getAngleDir(runAwayAngle);
                    int confuseTime = GameRandom.globalRandom.getIntBetween(1000, 1500);
                    this.getBlackboard().submitEvent("confuseWander", new ConfuseWanderAIEvent(confuseTime, runAwayDir));
                }
            }
            return superAttack;
        }

        protected boolean mobIsInWater(Mob targetMob)
        {
            if (targetMob == null)
            {
                return false;
            }
            else if (targetMob.getLevel() == null)
            {
                return false;
            }
            else
            {
                return targetMob.getLevel().liquidManager.getHeight(targetMob.getTileX(), targetMob.getTileY()) < -3;
            }
        }
    }
}