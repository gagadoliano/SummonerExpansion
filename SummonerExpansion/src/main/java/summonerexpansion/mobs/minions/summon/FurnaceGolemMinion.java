package summonerexpansion.mobs.minions.summon;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.packet.PacketRequestLevelEvent;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.levelEvent.FurnaceGolemOverheatEvent;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.minions.base.SummonWalkBase;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryArmors.registerArmorSets.CLOUDSPEEDBUFF;
import static summonerexpansion.codes.registries.RegistryDebuffs.WeaponDebuffs.BEARBLEEDING;

public class FurnaceGolemMinion extends AttackingFollowingMob
{
    public float overheatProgress = 0.0F;
    public float overheatGainRate = 0.005F;
    public float overheatDecayRate = 0.01F;
    public int eventUniqueID;
    public FurnaceGolemOverheatEvent overheatEvent;

    public FurnaceGolemMinion()
    {
        super(500);
        setArmor(100);
        setSpeed(25.0F);
        setFriction(3.0F);
        collision = new Rectangle(-20, -15, 40, 30);
        hitBox = new Rectangle(-25, -40, 50, 60);
        selectBox = new Rectangle(-35, -60, 70, 80);
        swimMaskMove = 32;
        swimMaskOffset = -48;
        swimSinkOffset = -8;
        isStatic = false;
    }

    public void setupMovementPacket(PacketWriter writer)
    {
        super.setupMovementPacket(writer);
        writer.putNextInt(eventUniqueID);
    }

    public void applyMovementPacket(PacketReader reader, boolean isDirect)
    {
        super.applyMovementPacket(reader, isDirect);
        eventUniqueID = reader.getNextInt();
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI<>(500, summonDamage, 30, 500, 640, 64));
        if (isServer())
        {
            overheatEvent = new FurnaceGolemOverheatEvent(this, GameRandom.globalRandom);
            getLevel().entityManager.events.add(overheatEvent);
            eventUniqueID = overheatEvent.getUniqueID();
            overheatEvent.damage = summonDamage;
        }
    }

    public void clientTick()
    {
        super.clientTick();
        overheatTick();
        if (overheatProgress != 0.0F)
        {
            GameRandom random = GameRandom.globalRandom;
            getLevel().entityManager.addTopParticle(x + random.getFloatBetween(-10.0F, 10.0F), y + random.getFloatBetween(-10.0F, 10.0F) - 20.0F, Particle.GType.IMPORTANT_COSMETIC).color(new Color(255, 150, 50)).ignoreLight(true).givesLight(20.0F, 1.0F);
            for(int i = 0; (float)i < Math.max(overheatProgress * 5.0F, 1.0F); ++i)
            {
                getLevel().entityManager.addParticle(x + random.getFloatBetween(-30.0F, 30.0F), y + random.getFloatBetween(-20.0F, 20.0F) - 40.0F, Particle.GType.COSMETIC).sprite(GameResources.smokePuff.sprite(random.getIntBetween(0, 4), 0, 32)).color(new Color(205, 210, 218)).sizeFades((int)(16.0F * overheatProgress), (int)(32.0F * overheatProgress)).movesFriction(0.0F, -10.0F, 0.7F).moves((pos, delta, lifeTime, timeAlive, lifePercent) -> {
                    pos.x += random.nextBoolean() ? (float)(Math.sin((float)timeAlive / 50.0F) * (double)delta) * 0.1F : (float)(Math.cos((float)timeAlive / 50.0F) * (double)delta) * 0.1F;
                    pos.y -= (2.0F - lifePercent) * delta * 0.05F;
                }).fadesAlphaTime(100, 200).lifeTime(600 + random.getIntBetween(0, 200));
            }
        }
        if (eventUniqueID != 0)
        {
            LevelEvent event = getLevel().entityManager.events.get(eventUniqueID, false);
            if (event == null)
            {
                getClient().network.sendPacket(new PacketRequestLevelEvent(eventUniqueID));
            }
        }
    }

    public void serverTick()
    {
        super.serverTick();
        overheatTick();
        if (overheatEvent.isOver())
        {
            eventUniqueID = 0;
            overheatEvent = null;
            sendMovementPacket(false);
        }
    }

    public void overheatTick()
    {
        if (inLiquid())
        {
            updateOverheat(-overheatDecayRate * 2.0F);
        }
        else
        {
            updateOverheat(isInCombat() ? overheatGainRate : -overheatDecayRate);
        }
    }

    public void updateOverheat(float change)
    {
        overheatProgress = GameMath.limit(overheatProgress + change, 0.0F, 1.0F);
    }

    protected void onDeath(Attacker attacker, HashSet<Attacker> attackers)
    {
        if (overheatEvent != null)
        {
            overheatEvent.over();
        }
        super.onDeath(attacker, attackers);
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), MobRegistry.Textures.furnaceGolem, i, 12, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 48;
        int drawY = camera.getDrawY(y) - 78;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        final DrawOptions drawOptions = MobRegistry.Textures.furnaceGolem.initDraw().sprite(sprite.x, sprite.y, 96).addMaskShader(swimMask).light(light).pos(drawX, drawY);
        final DrawOptions lightDrawOptions = MobRegistry.Textures.furnaceGolem_light.initDraw().sprite(sprite.x, sprite.y, 96).addMaskShader(swimMask).light(light.minLevelCopy(150.0F)).pos(drawX, drawY);
        final DrawOptions overheatDrawOptions = MobRegistry.Textures.furnaceGolem_overheat.initDraw().sprite(sprite.x, sprite.y, 96).addMaskShader(swimMask).light(light.minLevelCopy(75.0F)).alpha(overheatProgress).pos(drawX, drawY);
        final DrawOptions overheatLightDrawOptions = MobRegistry.Textures.furnaceGolem_overheat_light.initDraw().sprite(sprite.x, sprite.y, 96).addMaskShader(swimMask).light(light.minLevelCopy(150.0F)).alpha(overheatProgress).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
                overheatDrawOptions.draw();
                lightDrawOptions.draw();
                overheatLightDrawOptions.draw();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    protected TextureDrawOptions getShadowDrawOptions(Level level, int x, int y, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.furnaceGolem_shadow;
        int drawX = camera.getDrawX(x) - 48;
        int drawY = camera.getDrawY(y) - 96 + 22;
        drawY += getBobbing(x, y);
        return shadowTexture.initDraw().sprite(0, 0, 96).light(light).pos(drawX, drawY);
    }

    public Point getAnimSprite(int x, int y, int dir)
    {
        int frameCount = 4;
        int spriteIndex = GameUtils.getAnim(getTime(), frameCount, frameCount * 100);
        Point p = new Point(spriteIndex, dir);
        if (inLiquid(x, y))
        {
            p.x = 8 + spriteIndex;
        }
        else if (Math.abs(dx) <= 0.01F && Math.abs(dy) <= 0.01F)
        {
            p.x = spriteIndex;
        }
        else
        {
            p.x = (int)(getDistanceRan() / (double)getRockSpeed()) % 4 + 4;
        }
        return p;
    }

    public int getRockSpeed() {
        return 20;
    }
}