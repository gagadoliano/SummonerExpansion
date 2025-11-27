package summonerexpansion.mobs.summonminions.baseminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.MaskShaderOptions;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameHair;
import necesse.gfx.GameSkin;
import necesse.gfx.HumanGender;
import necesse.gfx.HumanLook;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.InventoryItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class HumanToolBase extends AttackingFollowingMob
{
    public boolean shouldResetFacingPos;
    public float facingBuffer;
    protected HumanLook look;
    protected int lookSeed;
    public InventoryItem helmet;
    public InventoryItem chest;
    public InventoryItem boots;
    public InventoryItem weapon;

    public HumanToolBase()
    {
        super(10);
        setSpeed(50.0F);
        setFriction(3.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle(-14, -41, 28, 48);
        swimMaskMove = 16;
        swimMaskOffset = -2;
        swimSinkOffset = -4;
        look = new HumanLook();
        getLookSeed();
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
        for (int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), gameSkin, GameRandom.globalRandom.nextInt(5), 8, 32, x, y, 10.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
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
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, look, false)).sprite(sprite).dir(dir).mask(swimMask).light(light).helmet(helmet).chestplate(chest).boots(boots);
        if (isAttacking)
        {
            humanDrawOptions.itemAttack(weapon, null, animProgress, attackDir.x, attackDir.y);
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
}