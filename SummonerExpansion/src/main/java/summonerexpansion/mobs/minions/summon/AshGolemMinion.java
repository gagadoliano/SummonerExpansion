package summonerexpansion.mobs.minions.summon;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.leaves.*;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.minions.base.SummonWalkBase;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryDebuffs.WeaponDebuffs.ENCHANTEDWEAK;

public class AshGolemMinion extends SummonWalkBase
{
    public AshGolemMinion()
    {
        super();
        setSpeed(20.0F);
        setFriction(3.0F);
        collision = new Rectangle(-25, -15, 50, 30);
        hitBox = new Rectangle(-30, -20, 60, 40);
        selectBox = new Rectangle(-40, -50, 80, 80);
        swimMaskMove = 20;
        swimMaskOffset = -55;
        swimSinkOffset = -5;
        prioritizeVerticalDir = false;
    }

    public Point getPathMoveOffset() {
        return new Point(32, 32);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI<>(500, summonDamage, 30, 800, 640, 100));
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) {
        return summonDamage;
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = getAttackOwner();
        if (owner != null && target != null)
        {
            if(!target.isBoss())
            {
                ActiveBuff buff = new ActiveBuff(BuffRegistry.Debuffs.ASHEN_BURIAL, target, 3F, this);
                target.addBuff(buff, true);
            }
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), MobRegistry.Textures.ashGolem, i, 16, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int spriteSize = 128;
        int drawX = camera.getDrawX(x) - spriteSize / 2;
        int drawY = camera.getDrawY(y) - spriteSize + 28;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY += this.getBobbing(x, y);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        final MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        final DrawOptions options = MobRegistry.Textures.ashGolem.initDraw().sprite(sprite.x, sprite.y, spriteSize).addMaskShader(swimMask).startGlowOptions(level, this.getID()).light(light).applyEnemyTracker(this, perspective).pos(drawX, drawY + 16);
        DrawOptions shadowOptions = MobRegistry.Textures.thrumbo_shadow.initDraw().sprite(0, sprite.y, spriteSize).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                swimMask.use();
                options.draw();
                swimMask.stop();
            }
        });
        tileList.add((tm) -> shadowOptions.draw());
    }

    public Point getAnimSprite(int x, int y, int dir) {
        return Math.abs(this.dx) <= 0.01F && Math.abs(this.dy) <= 0.01F ? new Point(0, dir) : new Point((int)(this.getDistanceRan() / (double)this.getRockSpeed()) % 4 + 1, dir);
    }

    public int getRockSpeed() {
        return 10;
    }
}