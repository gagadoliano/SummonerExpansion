package summonerexpansion.mobs.surfacemobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.util.gameAreaSearch.GameAreaStream;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ability.BooleanMobAbility;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.CollisionChaserWandererAI;
import necesse.entity.mobs.ai.behaviourTree.util.TargetFinderDistance;
import necesse.entity.mobs.friendly.*;
import necesse.entity.mobs.hostile.*;
import necesse.entity.mobs.hostile.bosses.VultureHatchling;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.ConditionLootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.cavemobs.PoisonSwampSlimeMob;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryMobTextures.dollMaker;

public class DollMakerMob extends FriendlyMob 
{
    public static LootTable lootTable = new LootTable(
            ChanceLootItem.between(0.5f, "woodenidol", 1, 1),
            ChanceLootItem.between(0.5f, "stuffedfrog", 1, 1),
            ChanceLootItem.between(0.5f, "stuffedmosquito", 1, 1),
            ChanceLootItem.between(0.08f, "sunkenchest", 1, 2),
            ChanceLootItem.between(0.3f, "rottenbread", 1, 10),
            ChanceLootItem.between(0.2f, "swampslime", 1, 10),
            ChanceLootItem.between(0.2f, "swampsludge", 1, 5),
            ChanceLootItem.between(0.1f, "swampslimepotion", 1, 10),
            ChanceLootItem.between(0.1f, "cavespidergland", 1, 10),
            ChanceLootItem.between(0.1f, "swampfish", 1, 10),
            ChanceLootItem.between(0.05f, "leafshotcoldpack", 1, 10),
            ChanceLootItem.between(0.05f, "leafshotheatpack", 1, 10),
            ChanceLootItem.between(0.05f, "leafshotpack", 1, 10),
            ChanceLootItem.between(0.05f, "mosscoveredskull", 1, 1),
            ChanceLootItem.between(0.01f, "friendlybush", 1, 1),
            ChanceLootItem.between(0.01f, "pestwardenlamp", 1, 1),
            ChanceLootItem.offset(0.01f, "swampshooterdoll", 1, 1)
    );
    public static LootTable privateLootTable = new LootTable(new ConditionLootItem("demonelixir", (r, o) ->
    {
        ServerClient client = LootTable.expectExtra(ServerClient.class, o, 1);
        return client != null && client.playerMob.getInv().getAmount(ItemRegistry.getItem("demonelixir"), false, false, true, true, "have") == 0;
    }));
    private final HashSet<Mob> targets = new HashSet<>();
    public final BooleanMobAbility setHostileAbility;
    public int lifeTime = 0;

    public DollMakerMob() 
    {
        super(6000);
        setArmor(40);
        setSpeed(60F);
        setFriction(3F);
        attackAnimTime = 400;
        attackCooldown = 400;
        moveAccuracy = 10;
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle(-13, -30, 26, 40);
        swimMaskMove = 16;
        swimMaskOffset = 0;
        swimSinkOffset = -4;
        setHostileAbility = registerAbility(new BooleanMobAbility() {
            protected void run(boolean value) {
                isHostile = value;
            }
        });
        setTeam(-2);
    }

    public void applySpawnPacket(PacketReader reader)
    {
        super.applySpawnPacket(reader);
        isHostile = reader.getNextBoolean();
    }

    public void setupSpawnPacket(PacketWriter writer)
    {
        super.setupSpawnPacket(writer);
        writer.putNextBoolean(isHostile);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new CollisionChaserWandererAI<DollMakerMob>(null, 800, new GameDamage(50F), 100, 400*200)
        {
            public GameAreaStream<Mob> streamPossibleTargets(DollMakerMob mob, Point base, TargetFinderDistance<DollMakerMob> distance)
            {
                return distance.streamMobsAndPlayersInRange(base, mob).filter(targets::contains);
            }
        });
    }

    public void serverTick()
    {
        super.serverTick();
        targets.removeIf((m) -> m.removed() || !m.isSamePlace(this) || m.getDistance(this) > 800F);
        setHostile(!targets.isEmpty());
        if (lifeTime >= 250 && !targets.isEmpty())
        {
            if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new WoodMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new PoisonSwampSlimeMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new TrenchcoatGoblinStackedMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new CrawlingZombieMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new SwampCaveSpiderMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new SwampZombieMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new SwampSlimeMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new SwampShooterMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new EnchantedCrawlingZombieMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new MummyMageMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new DeepCaveSpiritMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new NinjaMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new CryoFlakeMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new SwampDwellerMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new MosquitoMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new ForestSpectorMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new PalworldSyndicateThugMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new MimicMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new AshGolemMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new VultureHatchling(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                getLevel().entityManager.addMob(new ThrumboMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }
            else
            {
                getLevel().entityManager.addMob(new ZombieMob(), (float)(getX() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)), (float)(getY() + (int)(GameRandom.globalRandom.nextGaussian() * 3.0)));
            }

            lifeTime = 0;
        }
        else
        {
            lifeTime++;
        }
    }

    public MobWasHitEvent isServerHit(GameDamage damage, float x, float y, float knockback, Attacker attacker)
    {
        MobWasHitEvent out = super.isServerHit(damage, x, y, knockback, attacker);
        if (out != null && !out.wasPrevented && attacker != null)
        {
            Mob attackOwner = attacker.getAttackOwner();
            if (attackOwner != null)
            {
                targets.add(attackOwner);
            }
        }
        return out;
    }

    public void setHostile(boolean hostile)
    {
        if (getLevel() != null && getLevel().getServer() != null)
        {
            if (isHostile != hostile)
            {
                setHostileAbility.runAndSend(hostile);
            }
        }
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public LootTable getPrivateLootTable() {
        return privateLootTable;
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) 
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, dollMaker)).sprite(sprite).mask(swimMask).dir(dir).light(light).applyEnemyTracker(this, perspective).alpha(0.5F);
        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable()
        {
            public void draw(TickManager tickManager)
            {
                drawOptions.draw();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera) 
    {
        GameTexture shadowTexture = MobRegistry.Textures.human_baby_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2;
        drawY += getBobbing(x, y);
        int dir = getDir();
        return shadowTexture.initDraw().sprite(dir, 0, res).light(light).pos(drawX, drawY);
    }
}