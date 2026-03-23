package summonerexpansion.items.armors.minions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.minions.base.SummonWalkBase;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryMinionTextures.setMouseMinion;

public class SetMouseMinion extends SummonWalkBase
{
    public int lifeTime = 0;

    public SetMouseMinion()
    {
        setSpeed(35.0F);
        setFriction(1.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle(-13, -18, 26, 28);
        swimMaskMove = 6;
        swimMaskOffset = 30;
        swimSinkOffset = 0;
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return summonDamage; }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = getAttackOwner();
        if (owner != null && target != null)
        {
            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float)knockback, this);
            collisionHitCooldowns.startCooldown(target);
            remove(0.0F, 0.0F, null, true);
        }
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI<>(400, null, 30, 500, 640, 64));
    }

    public void serverTick()
    {
        super.serverTick();
        lifeTime++;
        if (lifeTime >= 2400)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for (int i = 0; i < 4; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), setMouseMinion, 12, i, 16, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 22;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        boolean mirror = (dir == 0 || dir == 2) && moveX < 0.0F;
        final MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        final DrawOptions options = setMouseMinion.initDraw().sprite(sprite.x, sprite.y, 32).addMaskShader(swimMask).mirror(mirror, false).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                swimMask.use();
                options.draw();
                swimMask.stop();
            }
        });
        TextureDrawOptions shadow = MobRegistry.Textures.mouse.shadow.initDraw().sprite(0, dir, 32).mirror(mirror, false).light(light).pos(drawX, drawY);
        tileList.add((tm) -> {
            shadow.draw();
        });
    }

    public int getRockSpeed() {
        return 7;
    }
}