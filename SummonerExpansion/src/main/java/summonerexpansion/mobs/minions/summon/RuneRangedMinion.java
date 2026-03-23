package summonerexpansion.mobs.minions.summon;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.MaskShaderOptions;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.*;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.cosmetics.misc.ShirtArmorItem;
import necesse.inventory.item.armorItem.cosmetics.misc.ShoesArmorItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.minions.base.SummonWalkBase;

import java.awt.*;
import java.util.List;

public class RuneRangedMinion extends SummonWalkBase
{
    public HumanLook look = new HumanLook();
    public InventoryItem helmet;
    public InventoryItem chest;
    public InventoryItem boots;
    public Point baseTile;
    public int lookSeed;

    public RuneRangedMinion()
    {
        super();
        setSpeed(20.0F);
        setFriction(3.0F);
        getLookSeed();
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle(-14, -41, 28, 48);
        swimMaskMove = 16;
        swimMaskOffset = -2;
        swimSinkOffset = -4;
    }

    public void addSaveData(SaveData save)
    {
        super.addSaveData(save);
        save.addInt("lookSeed", lookSeed);
        save.addPoint("baseTile", baseTile);
    }

    public void applyLoadData(LoadData save)
    {
        super.applyLoadData(save);
        lookSeed = save.getInt("lookSeed", lookSeed);
        baseTile = save.getPoint("baseTile", new Point(getTileX(), getTileY()), false);
        getLookSeed();
        updateLook();
    }

    public void applySpawnPacket(PacketReader reader)
    {
        super.applySpawnPacket(reader);
        lookSeed = reader.getNextInt();
        updateLook();
    }

    public void setupSpawnPacket(PacketWriter writer)
    {
        super.setupSpawnPacket(writer);
        writer.putNextInt(lookSeed);
    }

    public void init()
    {
        super.init();
        if (baseTile == null)
        {
            baseTile = new Point(getX() / 32, getY() / 32);
        }
        updateLook();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<RuneRangedMinion>(500, 500, false, false, 900, 60)
        {
            public boolean attackTarget(RuneRangedMinion mob, Mob target)
            {
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    InventoryItem attackItem = new InventoryItem("brutesbattleaxe");
                    getLevel().entityManager.events.add(new ToolItemMobAbilityEvent(RuneRangedMinion.this, GameRandom.globalRandom.nextInt(), attackItem, mob.getX(), mob.getY(), attackAnimTime, attackAnimTime));
                    Projectile projectile = ProjectileRegistry.getProjectile("runeboneproj", mob.getLevel(), mob.x, mob.y, target.x, target.y, 80.0F, 640, summonDamage, mob);
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

    public void getSeededRandomRuneboundShamanGear()
    {
        GameRandom random = new GameRandom(lookSeed);
        String helmetID = random.getOneOfWeighted(String.class, 40, "runeboundcrown", 40, "runeboundcrownmask", 20, "runeboundhood");
        String chestID = random.getOneOf("runeboundrobe", "runeboundbonesrobe");
        helmet = new InventoryItem(helmetID);
        chest = new InventoryItem(chestID);
        boots = new InventoryItem("runeboundboots");
    }

    public void updateLook()
    {
        GameRandom random = new GameRandom(lookSeed);
        HumanGender gender = random.getOneOfWeighted(HumanGender.class, 30, HumanGender.MALE, 60, HumanGender.FEMALE, 10, HumanGender.NEUTRAL);
        look.setSkin(10);
        look.setEyeType(random.getOneOf(0, 2, 4));
        look.setEyeColor(random.getIntBetween(0, 10));
        look.setHair(GameHair.getRandomHairBasedOnGender(random, gender));
        if (gender == HumanGender.MALE)
        {
            look.setFacialFeature(random.getOneOf(1, 3, 4, 6, 7));
        }
        look.setHairColor(random.getOneOf(6, 7, 8, 9));
        getSeededRandomRuneboundShamanGear();
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
        float animProgress = getAttackAnimProgress();
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        boolean inLiquid = inLiquid(x, y);
        if (inLiquid) {sprite.x = 0;}
        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        HumanDrawOptions humanOptions = (new HumanDrawOptions(level, look, false)).sprite(sprite).mask(swimMask).dir(dir).light(light);
        if (inLiquid)
        {
            humanOptions.armSprite(2);
            humanOptions.mask(MobRegistry.Textures.runeboundboat_mask[sprite.y % 4], 0, -7);
        }
        if (helmet != null)
        {
            humanOptions.helmet(helmet);
        }
        if (chest != null)
        {
            humanOptions.chestplate(chest);
        }
        else
        {
            humanOptions.chestplate(ShirtArmorItem.addColorData(new InventoryItem("shirt"), look.getShirtColor()));
        }
        if (boots != null)
        {
            humanOptions.boots(boots);
        }
        else
        {
            humanOptions.boots(ShoesArmorItem.addColorData(new InventoryItem("shoes"), look.getShoesColor()));
        }
        if (isAttacking)
        {
            humanOptions.itemAttack(new InventoryItem("runeboundscepter"), null, animProgress, attackDir.x, attackDir.y);
        }
        final DrawOptions drawOptions = humanOptions.pos(drawX, drawY);
        final DrawOptions boat = inLiquid ? MobRegistry.Textures.runeboundBoat.initDraw().sprite(0, sprite.y, 64).light(light).pos(drawX, drawY + 7) : null;
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

    public float getAttackingMovementModifier() {
        return 0.0F;
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