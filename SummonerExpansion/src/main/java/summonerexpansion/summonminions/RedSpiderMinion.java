package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class RedSpiderMinion extends AttackingFollowingMob
{
    public RedSpiderMinion()
    {
        super(10);
        setSpeed(50.0F);
        setFriction(2.0F);
        attackCooldown = 500;
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle();
        swimMaskMove = 8;
        swimMaskOffset = 30;
        swimSinkOffset = 0;
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI(this, new PlayerFollowerCollisionChaserAI(600, summonDamage, 80, 600, 650, 68));
    }

    public GameDamage getCollisionDamage(Mob target) {
        return summonDamage;
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = getAttackOwner();
        if (owner != null && target != null)
        {
            ActiveBuff buff2 = new ActiveBuff(BuffRegistry.getBuff("redspiderpoisondebuff"), target, 5F, this);
            target.addBuff(buff2, true);

            if (owner.buffManager.hasBuff("redspidersetbonus"))
            {
                ActiveBuff buff1 = new ActiveBuff(BuffRegistry.getBuff("redspiderdebuff"), target, 5F, this);
                target.addBuff(buff1, true);
            }
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), MobRegistry.Textures.spider.body, 12, i, 16, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 22;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        final MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        final DrawOptions options = MobRegistry.Textures.spider.body.initDraw().sprite(sprite.x, sprite.y, 32).addMaskShader(swimMask).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                swimMask.use();
                options.draw();
                swimMask.stop();
            }
        });
        TextureDrawOptions shadow = MobRegistry.Textures.spider.shadow.initDraw().sprite(0, dir, 32).light(light).pos(drawX, drawY);
        tileList.add((tm) -> {
            shadow.draw();
        });
    }

    public int getRockSpeed() {
        return 5;
    }
}
