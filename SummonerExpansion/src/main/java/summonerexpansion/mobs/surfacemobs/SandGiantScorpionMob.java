package summonerexpansion.mobs.surfacemobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MaskShaderOptions;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.ConfusedCollisionPlayerChaserWandererAI;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
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

import static summonerexpansion.codes.registries.RegistryMobTextures.sandGiantScorpion;

public class SandGiantScorpionMob extends HostileMob
{
    public static LootTable lootTable = new LootTable(new LootItem("sandgiantscorpiontail"));

    public SandGiantScorpionMob()
    {
        super(500);
        setSpeed(30.0F);
        setFriction(3.0F);
        setKnockbackModifier(0.5F);
        setArmor(20);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle(-18, -24, 36, 36);
        swimMaskMove = 16;
        swimMaskOffset = 0;
        swimSinkOffset = 0;
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new ConfusedCollisionPlayerChaserWandererAI<>(null, 600, new GameDamage(40.0F), 100, 4000));
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), sandGiantScorpion, 12, i, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 36;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        final MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        final DrawOptions body = sandGiantScorpion.initDraw().sprite(sprite.x, sprite.y, 64).addMaskShader(swimMask).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                swimMask.use();
                body.draw();
                swimMask.stop();
            }
        });
        TextureDrawOptions shadow = MobRegistry.Textures.giantScorpion.shadow.initDraw().sprite(0, sprite.y, 64).light(light).pos(drawX, drawY);
        tileList.add((tm) -> shadow.draw());
    }

    public int getRockSpeed() {
        return 10;
    }
}