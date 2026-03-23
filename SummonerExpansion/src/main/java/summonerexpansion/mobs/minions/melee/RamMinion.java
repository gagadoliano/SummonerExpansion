package summonerexpansion.mobs.minions.melee;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryMinionTextures.ramMinion;

public class RamMinion extends AttackingFollowingMob
{
    public int HitCount;

    public RamMinion()
    {
        super(10);
        setSpeed(60.0F);
        setFriction(3.0F);
        attackCooldown = 1000;
        hitCooldown = 1000;
        collision = new Rectangle(-12, -9, 24, 18);
        hitBox = new Rectangle(-16, -12, 32, 24);
        selectBox = new Rectangle(-18, -30, 36, 36);
        swimMaskMove = 16;
        swimMaskOffset = 0;
        swimSinkOffset = 0;
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI<>(500, summonDamage, 80, hitCooldown, 700, 70));
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return summonDamage; }

    public void serverTick()
    {
        super.serverTick();
        HitCount++;
        if (HitCount >= 300)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), ramMinion, GameRandom.globalRandom.nextInt(5), 8, 32, x, y, 10.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        TextureDrawOptions shadow = MobRegistry.Textures.sheep_shadow.initDraw().sprite(0, dir, 64).light(light).pos(drawX, drawY);
        tileList.add((tm) -> shadow.draw());
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        final MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        final DrawOptions options = ramMinion.initDraw().sprite(sprite.x, sprite.y, 64).light(light).addMaskShader(swimMask).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                swimMask.use();
                options.draw();
                swimMask.stop();
            }
        });
    }

    public int getRockSpeed() { return 10; }

    protected SoundSettings getAmbientSound() {
        return (new SoundSettings(GameResources.ramAmbient)).volume(0.4F);
    }
}