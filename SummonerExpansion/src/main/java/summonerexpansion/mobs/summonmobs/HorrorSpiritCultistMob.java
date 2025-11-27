package summonerexpansion.mobs.summonmobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.gameNetworkData.GNDItemGameDamage;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.ConfusedItemAttackerPlayerChaserWandererAI;
import necesse.entity.mobs.ai.behaviourTree.trees.ConfusedPlayerChaserWandererAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.friendly.BoarMob;
import necesse.entity.mobs.friendly.ChickenMob;
import necesse.entity.mobs.friendly.PigMob;
import necesse.entity.mobs.friendly.RoosterMob;
import necesse.entity.mobs.hostile.HostileItemAttackerMob;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.mobs.itemAttacker.CheckSlotType;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.HumanLook;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class HorrorSpiritCultistMob extends HostileMob
{
    protected int lookSeed;
    protected HumanLook look;

    public static LootTable lootTable = new LootTable(
            ChanceLootItem.between(1f, "purehorror", 1, 10)
    );

    public HorrorSpiritCultistMob()
    {
        super(1000);
        setSpeed(65.0F);
        setFriction(3.0F);
        setArmor(20);
        setKnockbackModifier(0.1F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle(-14, -41, 28, 48);
        swimMaskMove = 16;
        swimMaskOffset = -2;
        swimSinkOffset = -4;
        look = new HumanLook();
        updateLook();
    }

    public void setupSpawnPacket(PacketWriter writer)
    {
        super.setupSpawnPacket(writer);
        writer.putNextInt(lookSeed);
    }

    public void applySpawnPacket(PacketReader reader)
    {
        super.applySpawnPacket(reader);
        lookSeed = reader.getNextInt();
        updateLook();
    }

    public void init()
    {
        super.init();
        updateLook();
        if (getLevel() instanceof IncursionLevel)
        {
            setMaxHealth(2500);
            setHealthHidden(getMaxHealth());
            setArmor(40);
        }
    }

    public void updateLook()
    {
        if (lookSeed == 0)
        {
            lookSeed = GameRandom.globalRandom.nextInt();
        }
        look.setSkin(10);
    }

    public PathDoorOption getPathDoorOption()
    {
        if (getLevel() != null)
        {
            return buffManager.getModifier(BuffModifiers.CAN_BREAK_OBJECTS) ? getLevel().regionManager.CAN_BREAK_OBJECTS_OPTIONS : getLevel().regionManager.CAN_OPEN_DOORS_OPTIONS;
        }
        else
        {
            return null;
        }
    }

    public LootTable getLootTable() {return lootTable;}

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 50; ++i)
        {
            int angle = GameRandom.globalRandom.nextInt(360);
            Point2D.Float dir = GameMath.getAngleDir((float)angle);
            int lifeTime = GameRandom.globalRandom.getIntBetween(2000, 5000);
            float lifePerc = (float)lifeTime / 5000.0F;
            float startHeight = (float)GameRandom.globalRandom.getIntBetween(0, 10);
            float height = startHeight + (float)GameRandom.globalRandom.getIntBetween(70, 150) * lifePerc;
            getLevel().entityManager.addParticle(x + GameRandom.globalRandom.getFloatBetween(-10.0F, 10.0F), y + GameRandom.globalRandom.getFloatBetween(-5.0F, 5.0F), Particle.GType.IMPORTANT_COSMETIC).sprite(GameResources.puffParticles.sprite(GameRandom.globalRandom.nextInt(5), 0, 12)).sizeFades(15, 20).movesFriction((float)GameRandom.globalRandom.getIntBetween(2, 36) * dir.x, (float)GameRandom.globalRandom.getIntBetween(2, 36) * dir.y, 1.0F).heightMoves(startHeight, height).color(new Color(1, 1, 1)).lifeTime(lifeTime);
        }
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
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, look, false)).sprite(sprite).dir(dir).mask(swimMask).light(light).helmet(new InventoryItem("shadowhorrorhood")).chestplate(new InventoryItem("shadowhorrormantle")).boots(new InventoryItem("shadowhorrorboots"));
        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }
}