package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.MaskShaderOptions;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.ButchersCleaverBoomerangProjectile;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameHair;
import necesse.gfx.GameSkin;
import necesse.gfx.HumanGender;
import necesse.gfx.HumanLook;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class SetChefMinion  extends AttackingFollowingMob
{
    protected HumanLook look;
    protected int lookSeed;
    private String mobName = "";
    public int lifeTime = 0;
    public int lifeStart = 0;

    public SetChefMinion()
    {
        super(10);
        setSpeed(30.0F);
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
        writer.putNextString(mobName);
    }

    public void applySpawnPacket(PacketReader reader)
    {
        super.applySpawnPacket(reader);
        lookSeed = reader.getNextInt();
        mobName = reader.getNextString();
        updateLook();
    }

    public void init()
    {
        super.init();
        updateLook();
        this.ai = new BehaviourTreeAI(this, new PlayerFollowerChaserAI<SetChefMinion>(600, 600, true, false, 900, 80)
        {
            public boolean attackTarget(SetChefMinion mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);

                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("chefsspecialrollingpin", mob.getLevel(), mob.x, mob.y, target.x, target.y, (180.0F * projVel), 600, summonDamage, mob);
                    projectile.setTargetPrediction(target, -20.0F);
                    projectile.moveDist(40.0);

                    if (GameRandom.globalRandom.nextInt(5) == 1)
                    {
                        mob.getLevel().entityManager.projectiles.add(projectile);
                    }
                    else
                    {
                        mob.getLevel().entityManager.projectiles.add(new ButchersCleaverBoomerangProjectile(mob.getLevel(), mob, mob.x, mob.y, target.x, target.y, 180.0F, 600, summonDamage, 0, 25, 3, false));
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

    public void serverTick()
    {
        super.serverTick();
        lifeStart++;
        if (lifeStart >= lifeTime)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }

    public void updateLook()
    {
        if (lookSeed == 0)
        {
            lookSeed = GameRandom.globalRandom.nextInt();
        }
        GameRandom random = new GameRandom(lookSeed);
        HumanGender gender = random.getOneOfWeighted(HumanGender.class, 40, HumanGender.MALE, 40, HumanGender.FEMALE, 20, HumanGender.NEUTRAL);
        look.setSkin(random.getOneOf(0, 1, 2, 3, 13, 14, 17, 18));
        look.setEyeType(random.getIntBetween(0, 11));
        look.setEyeColor(random.getIntBetween(0, 11));
        look.setHair(GameHair.getRandomHairBasedOnGender(random, gender));
        if (gender == HumanGender.MALE)
        {
            look.setFacialFeature(random.getIntBetween(1, 7));
        }
        look.setHairColor(GameHair.getRandomHairColorAboveColorWeight(random, GameHair.UNCOMMON_HAIR_COLOR_WEIGHT));
        mobName = HumanMob.getRandomName(random, gender);
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
        GameSkin gameSkin = this.look.getGameSkin(true);
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), gameSkin, GameRandom.globalRandom.nextInt(5), 8, 32, this.x, this.y, 10.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
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
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, look, false)).sprite(sprite).dir(dir).mask(swimMask).light(light).helmet(new InventoryItem("battlechefhat")).chestplate(new InventoryItem("battlechefchestplate")).boots(new InventoryItem("battlechefboots"));
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
}