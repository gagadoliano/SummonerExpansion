package summonerexpansion.objects.summontiles;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.particle.Particle;
import necesse.entity.particle.ParticleOption;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.LevelTileTerrainDrawOptions;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.gameTile.LiquidTile;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.Biome;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registry.SummonerBuffs.SummonBuffs.LIQUIDHONEYSTACKS;

public class LiquidHoneyTile extends LiquidTile
{
    public GameTextureSection texture;
    protected final GameRandom drawRandom;

    public LiquidHoneyTile()
    {
        super(new Color(250, 132, 12), "liquidhoney");
        drawRandom = new GameRandom();
        overridesCannotPlaceOnLiquid = true;
    }

    protected void loadTextures()
    {
        super.loadTextures();
        texture = tileTextures.addTexture(GameTexture.fromFile("tiles/liquidhoney"));
    }

    public float getMinLiquidAlpha(Level level) {
        return 0.9F;
    }

    public float getMaxLiquidAlpha(Level level) {
        return 0.9F;
    }

    public LiquidTile.TextureIndexes getTextureIndexes(Level level, int tileX, int tileY, Biome biome)
    {
        return new LiquidTile.TextureIndexes(0, 0, 0, 0, 250, 250);
    }

    public float getItemSinkingRate(float currentSinking) {
        return TickManager.getTickDelta(60.0F);
    }

    public float getItemMaxSinking() {
        return 1.0F;
    }

    public float getLiquidMobHeightPercent(Level level, int tileX, int tileY, Mob perspective, int height)
    {
        if (perspective != null)
        {
            ActiveBuff buff = perspective.buffManager.getBuff(LIQUIDHONEYSTACKS);
            if (buff != null)
            {
                return (float)buff.getStacks() / (float)buff.getMaxStacks();
            }
        }
        return 0.0F;
    }

    public void tick(Mob mob, Level level, int x, int y)
    {
        if (mob.canLevelInteract() && !mob.isFlying() && !mob.isWaterWalking() && level.inLiquid(mob.getX(), mob.getY()) && !mob.isOnGenericCooldown("honeysink"))
        {
            int maxStacks = Integer.MAX_VALUE;
            if (mob.isHostile)
            {
                maxStacks = 50;
            }
            else if (mob.isAccelerating())
            {
                maxStacks = 80;
            }

            ActiveBuff buff = mob.buffManager.getBuff(LIQUIDHONEYSTACKS);
            if (buff == null || buff.getStacks() < maxStacks)
            {
                mob.buffManager.addBuff(new ActiveBuff(LIQUIDHONEYSTACKS, mob, 0.5F, null), false);
                mob.startGenericCooldown("honeysink", 100L);
            }
        }
    }

    public Color getLiquidColor(Level level, int tileX, int tileY, Biome biome) {
        return getLiquidColor(5);
    }

    public void tickEffect(Level level, int x, int y)
    {
        if (GameRandom.globalRandom.getEveryXthChance(200) && level.getObjectID(x, y) == 0)
        {
            int spriteRes = 12;
            Color particleColor = new Color(255, 154, 0);
            level.entityManager.addParticle(ParticleOption.base((float)(x * 32 + GameRandom.globalRandom.nextInt(32 - spriteRes)), (float)(y * 32 + GameRandom.globalRandom.nextInt(32 - spriteRes))), Particle.GType.COSMETIC).lifeTime(1000).sprite((options, lifeTime, timeAlive, lifePercent) ->
            {
                int frames = GameResources.liquidBlobParticle.getWidth() / spriteRes;
                return options.add(GameResources.liquidBlobParticle.sprite(Math.min((int)(lifePercent * (float)frames), frames - 1), 0, spriteRes));
            }).color(particleColor);
        }
    }

    protected void addLiquidTopDrawables(LevelTileTerrainDrawOptions list, List<LevelSortedDrawable> sortedList, Level level, int tileX, int tileY, GameCamera camera, TickManager tickManager) {
    }

    public int getLiquidBobbing(Level level, int tileX, int tileY) {
        return super.getLiquidBobbing(level, tileX, tileY) / 2;
    }
}