package summonerexpansion.summonmobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.CollisionPlayerChaserWandererAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.hostile.FlyingHostileMob;
import necesse.entity.particle.Particle;
import necesse.entity.particle.TopFleshParticle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

public class HorrorSpiritMob extends FlyingHostileMob
{
    public static GameTexture texture;
    public static GameDamage baseDamage = new GameDamage(60.0F);
    public static GameDamage incursionDamage = new GameDamage(80.0F);

    public static LootTable lootTable = new LootTable(
            ChanceLootItem.between(0.8f, "purehorror", 1, 4)
    );

    public HorrorSpiritMob()
    {
        super(250);
        setArmor(50);
        setSpeed(35.0F);
        setFriction(0.5F);
        setKnockbackModifier(0.2F);
        moveAccuracy = 10;
        collision = new Rectangle(-12, -12, 24, 24);
        hitBox = new Rectangle(-16, -16, 32, 32);
        selectBox = new Rectangle(-18, -40, 36, 54);
    }

    public void init()
    {
        super.init();
        GameDamage damage;
        if (getLevel() instanceof IncursionLevel)
        {
            setMaxHealth(550);
            setHealthHidden(getMaxHealth());
            setArmor(60);
            damage = incursionDamage;
        }
        else
        {
            damage = baseDamage;
        }

        ai = new BehaviourTreeAI(this, new CollisionPlayerChaserWandererAI(null, 600, damage, 100, 40000), new FlyingAIMover());
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            int sprite = GameRandom.globalRandom.nextInt(4);
            getLevel().entityManager.addParticle(new TopFleshParticle(getLevel(), texture, 4 + sprite % 2, sprite / 2, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    @Override
    public LootTable getLootTable() {
        return lootTable;
    }

    public void playDeathSound() {
        SoundManager.playSound(GameResources.fadedeath3, SoundEffect.effect(this));
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int bobbing = (int)(GameUtils.getBobbing(level.getWorldEntity().getTime(), 1000) * 5.0F);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 48 + bobbing;
        int anim = Math.abs(GameUtils.getAnim(level.getWorldEntity().getTime(), 4, 1000) - 3);
        DrawOptions body = texture.initDraw().sprite(0, anim, 64).mirror(moveX < 0.0F, false).alpha(0.7F).light(light).pos(drawX, drawY);
        int minLight = 100;
        DrawOptions eyes = texture.initDraw().sprite(1, anim, 64).mirror(moveX < 0.0F, false).alpha(0.7F).light(light.minLevelCopy((float)minLight)).pos(drawX, drawY);
        addShadowDrawables(topList, level, x, y, light, camera);
        topList.add((tm) -> {
            body.draw();
            eyes.draw();
        });
    }

    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.human_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2 + 4;
        return shadowTexture.initDraw().sprite(0, 0, res).light(light).pos(drawX, drawY);
    }
}