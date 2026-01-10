package summonerexpansion.mobs.summonminions.setminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.gameNetworkData.GNDItemGameDamage;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.HumanLook;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.InventoryItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class SetAgedChampionMinion extends AttackingFollowingMob
{
    protected int lookSeed;
    protected HumanLook look;

    public SetAgedChampionMinion()
    {
        super(10);
        setSpeed(45.0F);
        setFriction(3.0F);
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
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI<SetAgedChampionMinion>(500, summonDamage, 60, 1000, 900, 80)
        {
            public boolean attackTarget(SetAgedChampionMinion mob, Mob target)
            {
                if (SetAgedChampionMinion.this.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), true);
                    InventoryItem attackItem = new InventoryItem("agedchampionsword");
                    attackItem.getGndData().setItem("damage", new GNDItemGameDamage(summonDamage));
                    SetAgedChampionMinion.this.getLevel().entityManager.events.add(new ToolItemMobAbilityEvent(SetAgedChampionMinion.this, GameRandom.globalRandom.nextInt(), attackItem, mob.getX(), mob.getY(), SetAgedChampionMinion.this.attackAnimTime, SetAgedChampionMinion.this.attackAnimTime));
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public void updateLook()
    {
        if (lookSeed == 0)
        {
            lookSeed = GameRandom.globalRandom.nextInt();
        }
        GameRandom random = new GameRandom(lookSeed);
        look.setFacialFeature(0);
        look.setSkin(random.getIntBetween(0, 5));
    }

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
            getLevel().entityManager.addParticle(x + GameRandom.globalRandom.getFloatBetween(-10.0F, 10.0F), y + GameRandom.globalRandom.getFloatBetween(-5.0F, 5.0F), Particle.GType.IMPORTANT_COSMETIC).sprite(GameResources.puffParticles.sprite(GameRandom.globalRandom.nextInt(5), 0, 12)).sizeFades(15, 20).movesFriction((float)GameRandom.globalRandom.getIntBetween(2, 36) * dir.x, (float)GameRandom.globalRandom.getIntBetween(2, 36) * dir.y, 1.0F).heightMoves(startHeight, height).color(getDeathParticleColor(GameRandom.globalRandom)).lifeTime(lifeTime);
        }
    }

    protected Color getDeathParticleColor(GameRandom random)
    {
        return new Color(random.getIntBetween(22, 88), random.getIntBetween(22, 33), random.getIntBetween(22, 88));
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        int dir = getDir();
        float animProgress = getAttackAnimProgress();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, look, false)).sprite(sprite).dir(dir).mask(swimMask).light(light).helmet(new InventoryItem("agedchampionhelmet")).chestplate(new InventoryItem("agedchampionchestplate")).boots(new InventoryItem("agedchampiongreaves"));
        if (isAttacking)
        {
            humanDrawOptions.itemAttack(new InventoryItem("agedchampionsword"), null, animProgress, attackDir.x, attackDir.y);
        }
        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }
}