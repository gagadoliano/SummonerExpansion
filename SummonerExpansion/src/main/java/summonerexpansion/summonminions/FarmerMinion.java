package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.gameNetworkData.GNDItemGameDamage;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.friendly.BoarMob;
import necesse.entity.mobs.friendly.ChickenMob;
import necesse.entity.mobs.friendly.PigMob;
import necesse.entity.mobs.friendly.RoosterMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.*;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.InventoryItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class FarmerMinion extends AttackingFollowingMob
{
    public boolean shouldResetFacingPos;
    public float facingBuffer;
    protected HumanLook look;
    protected int lookSeed;

    public FarmerMinion()
    {
        super(10);
        setSpeed(50.0F);
        setFriction(3.0F);
        getLookSeed();
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle();
        swimMaskMove = 16;
        swimMaskOffset = -2;
        swimSinkOffset = -4;
        attackAnimTime = 600;
        attackCooldown = 900;
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
        ai = new BehaviourTreeAI(this, new PlayerFollowerCollisionChaserAI<FarmerMinion>(500, summonDamage, 50, 800, 900, 60) 
        {
            public boolean attackTarget(FarmerMinion mob, Mob target) 
            {
                if (FarmerMinion.this.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), true);
                    InventoryItem attackItem = new InventoryItem("coppersword");
                    attackItem.getGndData().setItem("damage", new GNDItemGameDamage(summonDamage));
                    FarmerMinion.this.getLevel().entityManager.addLevelEvent(new ToolItemMobAbilityEvent(FarmerMinion.this, GameRandom.globalRandom.nextInt(), attackItem, mob.getX(), mob.getY(), FarmerMinion.this.attackAnimTime, FarmerMinion.this.attackAnimTime));

                    if (GameRandom.globalRandom.nextInt(100) <= 10)
                    {
                        getLevel().entityManager.pickups.add((new InventoryItem("wheat")).getPickupEntity(getLevel(), x, y));
                    }

                    if (GameRandom.globalRandom.nextInt(100) <= 2)
                    {
                        if (GameRandom.globalRandom.nextInt(100) <= 20)
                        {
                            mob.getLevel().entityManager.addMob(new RoosterMob(), x, y);
                        }
                        else if (GameRandom.globalRandom.nextInt(100) <= 20)
                        {
                            mob.getLevel().entityManager.addMob(new BoarMob(), x, y);
                        }
                        else if (GameRandom.globalRandom.nextInt(100) <= 20)
                        {
                            mob.getLevel().entityManager.addMob(new PigMob(), x, y);
                        }
                        else
                        {
                            mob.getLevel().entityManager.addMob(new ChickenMob(), x, y);
                        }
                    }
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
        HumanGender gender = random.getOneOfWeighted(HumanGender.class, 20, HumanGender.MALE, 20, HumanGender.FEMALE, 20, HumanGender.NEUTRAL);
        look.setSkin(random.getOneOf(0, 1, 2, 3, 13, 14, 17, 18));
        look.setEyeType(random.getIntBetween(0, 11));
        look.setEyeColor(random.getIntBetween(0, 11));
        look.setHair(GameHair.getRandomHairBasedOnGender(random, gender));
        if (gender == HumanGender.MALE)
        {
            look.setFacialFeature(random.getIntBetween(1, 7));
        }
        look.setHairColor(random.getIntBetween(1, 25));
    }

    public void getLookSeed()
    {
        if (lookSeed == 0)
        {
            lookSeed = GameRandom.globalRandom.nextInt();
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        GameSkin gameSkin = look.getGameSkin(true);
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), gameSkin.getBodyTexture(), GameRandom.globalRandom.nextInt(5), 8, 32, x, y, 10.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
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
        boolean inLiquid = inLiquid(x, y);
        if (inLiquid) {sprite.x = 0;}
        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, look, false)).sprite(sprite).dir(dir).mask(swimMask).light(light).helmet(new InventoryItem("farmerhat")).chestplate(new InventoryItem("farmershirt")).boots(new InventoryItem("farmershoes"));
        if (isAttacking)
        {
            humanDrawOptions.itemAttack(new InventoryItem("copperpitchfork"), null, animProgress, attackDir.x, attackDir.y);
        }
        if (inLiquid)
        {
            humanDrawOptions.armSprite(2);
            humanDrawOptions.mask(MobRegistry.Textures.boat_mask[sprite.y % 4], 0, -7);
        }
        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        final DrawOptions boat = inLiquid ? MobRegistry.Textures.steelBoat.initDraw().sprite(0, sprite.y, 64).light(light).pos(drawX, drawY + 7) : null;
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                if (boat != null)
                {
                    boat.draw();
                }
                drawOptions.draw();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public void setFacingDir(float deltaX, float deltaY)
    {
        if (!isAttacking && facingBuffer <= (float)getTime())
        {
            super.setFacingDir(deltaX, deltaY);
            shouldResetFacingPos = true;
        }
        else if (isAttacking)
        {
            facingBuffer = (float)(getTime() + 500L);
            if (shouldResetFacingPos)
            {
                super.setFacingDir(deltaX, deltaY);
                shouldResetFacingPos = false;
            }
        }
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections)
    {
        super.showAttack(x, y, seed, showAllDirections);
        if (isClient())
        {
            SoundManager.playSound(GameResources.swing2, SoundEffect.effect(this));
        }
    }
}