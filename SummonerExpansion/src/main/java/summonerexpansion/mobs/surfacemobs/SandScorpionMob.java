package summonerexpansion.mobs.surfacemobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.NetworkClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.sound.SoundSettings;
import necesse.entity.mobs.*;
import necesse.entity.mobs.friendly.critters.CritterMob;
import necesse.entity.mobs.misc.NetableMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryMobTextures.sandScorpion;

public class SandScorpionMob extends CritterMob implements NetableMob
{
    public static LootTable lootTable = new LootTable(new LootItem("sandscorpionmob"));

    public SandScorpionMob()
    {
        setSpeed(6.0F);
        setFriction(3.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle(-16, -24, 32, 30);
        swimMaskMove = 6;
        swimMaskOffset = 30;
        swimSinkOffset = 0;
    }

    public boolean canTakeDamage() {
        return false;
    }

    public boolean canBeTargeted(Mob attacker, NetworkClient attackerClient) {
        return true;
    }

    public boolean isHealthBarVisible() {
        return false;
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), sandScorpion, 12, i, 16, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 15;
        int drawY = camera.getDrawY(y) - 22;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        final DrawOptions options = sandScorpion.initDraw().sprite(sprite.x, sprite.y, 32).addMaskShader(swimMask).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
        TextureDrawOptions shadow = MobRegistry.Textures.scorpion.shadow.initDraw().sprite(0, dir, 32).light(light).pos(drawX + swimMask.drawXOffset, drawY + swimMask.drawYOffset);
        tileList.add((tm) -> shadow.draw());
    }

    public int getSwimMaskMove() {
        return getDir() == 2 ? super.getSwimMaskMove() - 4 : super.getSwimMaskMove();
    }

    public int getSwimSinkOffset() {
        return getDir() == 2 ? super.getSwimSinkOffset() - 2 : super.getSwimSinkOffset();
    }

    public int getRockSpeed() {
        return 3;
    }

    public MobSpawnLocation checkSpawnLocation(MobSpawnLocation location)
    {
        return super.checkSpawnLocation(location).checkTile((tileX, tileY) -> getLevel().getTileID(tileX, tileY) == TileRegistry.sandID);
    }

    protected SoundSettings getAmbientSound()
    {
        return (new SoundSettings(GameResources.scorpionAmbient)).volume(0.8F);
    }

    protected SoundSettings getDeathSound() {
        return (new SoundSettings(GameResources.scorpionDeath)).volume(1.2F);
    }
}