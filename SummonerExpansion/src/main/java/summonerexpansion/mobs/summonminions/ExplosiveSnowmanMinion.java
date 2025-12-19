package summonerexpansion.mobs.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserOnlyAI;
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
import summonerexpansion.codes.events.*;

import java.awt.*;
import java.util.List;

public class ExplosiveSnowmanMinion extends AttackingFollowingMob
{
    public static GameTexture texture;
    public int SnowHitCount;

    public ExplosiveSnowmanMinion()
    {
        super(10);
        setSpeed(60.0F);
        setFriction(2.0F);
        attackCooldown = 1000;
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle(-16, -30, 32, 36);
        swimMaskMove = 12;
        swimMaskOffset = 4;
        swimSinkOffset = 0;
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return summonDamage; }

    public int getCollisionKnockback(Mob target) {
        return 15;
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        SnowHitCount++;

        if (SnowHitCount >= 5)
        {
            if (getAttackOwner().buffManager.hasBuff("frostcrownsetbonus"))
            {
                SnowmanSetLevelEvent explosionLevelEvent = new SnowmanSetLevelEvent(x, y, 250, new GameDamage(summonDamage.damage * 3.0F), false, 0, this);
                getLevel().entityManager.addLevelEvent(explosionLevelEvent);
            }
            else
            {
                SnowmanExplosionLevelEvent explosionLevelEvent = new SnowmanExplosionLevelEvent(x, y, 150, new GameDamage(summonDamage.damage * 2.0F), false, 0, this);
                getLevel().entityManager.addLevelEvent(explosionLevelEvent);
            }

            remove(0.0F, 0.0F, null, true);
        }

        Mob owner = this.getAttackOwner();
        if (owner != null && target != null)
        {
            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float)knockback, this);
            this.collisionHitCooldowns.startCooldown(target);
        }
    }

    public void init()
    {
        super.init();
        this.ai = new BehaviourTreeAI(this, new PlayerFollowerChaserOnlyAI<ExplosiveSnowmanMinion>(500, 64, false, false, 900, 64) {
            public boolean attackTarget(ExplosiveSnowmanMinion mob, Mob target)
            {
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    target.isServerHit(ExplosiveSnowmanMinion.this.summonDamage, mob.dx, mob.dy, 15.0F, mob);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public void playDeathSound()
    {
        float pitch = (Float) GameRandom.globalRandom.getOneOf(new Float[]{0.95F, 1.0F, 1.05F});
        SoundManager.playSound(GameResources.npcdeath, SoundEffect.effect(this).volume(0.1F).pitch(pitch));
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 5; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), texture, i, 8, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 44 - 3;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        final MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        final DrawOptions options = texture.initDraw().sprite(sprite.x, sprite.y, 64).addMaskShader(swimMask).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                swimMask.use();
                options.draw();
                swimMask.stop();
            }
        });
    }

    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.human_baby_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2;
        drawY += this.getBobbing(x, y);
        int dir = this.getDir();
        return shadowTexture.initDraw().sprite(dir, 0, res).light(light).pos(drawX, drawY);
    }

    public int getRockSpeed() { return 10; }
}
