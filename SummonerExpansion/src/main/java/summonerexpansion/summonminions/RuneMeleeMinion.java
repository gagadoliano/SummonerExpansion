package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.gameNetworkData.GNDItemGameDamage;
import necesse.engine.registries.MobRegistry;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
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

import java.awt.*;
import java.util.List;

public class RuneMeleeMinion extends AttackingFollowingMob
{
    public HumanLook look = new HumanLook();
    public boolean shouldResetFacingPos;
    public float facingBuffer;
    public InventoryItem helmet;
    public InventoryItem chest;
    public InventoryItem boots;
    public Point baseTile;
    public int lookSeed;

    public RuneMeleeMinion()
    {
        super(10);
        setSpeed(20.0F);
        setFriction(3.0F);
        getLookSeed();
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle();
        swimMaskMove = 16;
        swimMaskOffset = -2;
        swimSinkOffset = -4;
        attackAnimTime = 300;
        attackCooldown = 700;
    }

    public void addSaveData(SaveData save)
    {
        super.addSaveData(save);
        save.addInt("lookSeed", this.lookSeed);
        save.addPoint("baseTile", this.baseTile);
    }

    public void applyLoadData(LoadData save)
    {
        super.applyLoadData(save);
        this.lookSeed = save.getInt("lookSeed", this.lookSeed);
        this.baseTile = save.getPoint("baseTile", new Point(this.getTileX(), this.getTileY()), false);
        this.getLookSeed();
        this.updateLook();
    }

    public void applySpawnPacket(PacketReader reader)
    {
        super.applySpawnPacket(reader);
        this.lookSeed = reader.getNextInt();
        this.updateLook();
    }

    public void setupSpawnPacket(PacketWriter writer)
    {
        super.setupSpawnPacket(writer);
        writer.putNextInt(this.lookSeed);
    }

    public void init()
    {
        super.init();
        if (baseTile == null)
        {
            baseTile = new Point(getX() / 32, getY() / 32);
        }
        updateLook();
        ai = new BehaviourTreeAI(this, new PlayerFollowerCollisionChaserAI<RuneMeleeMinion>(500, summonDamage, 50, 800, 900, 60) 
        {
            public boolean attackTarget(RuneMeleeMinion mob, Mob target) 
            {
                if (RuneMeleeMinion.this.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), true);
                    InventoryItem attackItem = new InventoryItem("brutesbattleaxe");
                    attackItem.getGndData().setItem("damage", new GNDItemGameDamage(summonDamage));
                    RuneMeleeMinion.this.getLevel().entityManager.addLevelEvent(new ToolItemMobAbilityEvent(RuneMeleeMinion.this, GameRandom.globalRandom.nextInt(), attackItem, mob.getX(), mob.getY(), RuneMeleeMinion.this.attackAnimTime, RuneMeleeMinion.this.attackAnimTime));
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public void getSeededRandomRuneboundBruteGear()
    {
        GameRandom random = new GameRandom(this.lookSeed);
        String helmetID = random.getOneOfWeighted(String.class, 40, "runeboundhornhelmet", 40, "runeboundhelmet", 20, "runeboundhood");
        String chestID = random.getOneOf("runeboundbackbones", "runeboundleatherchest");
        this.helmet = new InventoryItem(helmetID);
        this.chest = new InventoryItem(chestID);
        this.boots = new InventoryItem("runeboundboots");
    }

    public void updateLook()
    {
        GameRandom random = new GameRandom(this.lookSeed);
        HumanGender gender = random.getOneOfWeighted(HumanGender.class, new Object[]{60, HumanGender.MALE, 30, HumanGender.FEMALE, 10, HumanGender.NEUTRAL});
        this.look.setSkin(10);
        this.look.setEyeType(random.getOneOf(0, 2, 4));
        this.look.setEyeColor(random.getIntBetween(0, 10));
        this.look.setHair(GameHair.getRandomHairBasedOnGender(random, gender));
        if (gender == HumanGender.MALE)
        {
            this.look.setFacialFeature(random.getOneOf(1, 3, 4, 6, 7));
        }
        this.look.setHairColor(random.getOneOf(6, 7, 8, 9));
        this.getSeededRandomRuneboundBruteGear();
    }

    public void getLookSeed()
    {
        if (this.lookSeed == 0)
        {
            this.lookSeed = GameRandom.globalRandom.nextInt();
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        GameSkin gameSkin = this.look.getGameSkin(true);
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), gameSkin.getBodyTexture(), GameRandom.globalRandom.nextInt(5), 8, 32, this.x, this.y, 10.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        float animProgress = this.getAttackAnimProgress();
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        boolean inLiquid = this.inLiquid(x, y);
        if (inLiquid) {sprite.x = 0;}
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        HumanDrawOptions humanOptions = (new HumanDrawOptions(level, this.look, false)).sprite(sprite).mask(swimMask).dir(dir).light(light);
        if (inLiquid)
        {
            humanOptions.armSprite(2);
            humanOptions.mask(MobRegistry.Textures.runeboundboat_mask[sprite.y % 4], 0, -7);
        }
        if (this.helmet != null)
        {
            humanOptions.helmet(this.helmet);
        }
        if (this.chest != null)
        {
            humanOptions.chestplate(this.chest);
        }
        else
        {
            humanOptions.chestplate(ShirtArmorItem.addColorData(new InventoryItem("shirt"), this.look.getShirtColor()));
        }
        if (this.boots != null)
        {
            humanOptions.boots(this.boots);
        }
        else
        {
            humanOptions.boots(ShoesArmorItem.addColorData(new InventoryItem("shoes"), this.look.getShoesColor()));
        }
        if (this.isAttacking)
        {
            humanOptions.itemAttack(new InventoryItem("brutesbattleaxe"), null, animProgress, this.attackDir.x, this.attackDir.y);
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
        this.addShadowDrawables(tileList, x, y, light, camera);
    }

    public void setFacingDir(float deltaX, float deltaY)
    {
        if (!this.isAttacking && this.facingBuffer <= (float)this.getTime())
        {
            super.setFacingDir(deltaX, deltaY);
            this.shouldResetFacingPos = true;
        }
        else if (this.isAttacking)
        {
            this.facingBuffer = (float)(this.getTime() + 500L);
            if (this.shouldResetFacingPos)
            {
                super.setFacingDir(deltaX, deltaY);
                this.shouldResetFacingPos = false;
            }
        }
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections)
    {
        super.showAttack(x, y, seed, showAllDirections);
        if (this.isClient())
        {
            SoundManager.playSound(GameResources.swing2, SoundEffect.effect(this));
        }
    }
}