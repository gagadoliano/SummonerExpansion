package summonerexpansion.summonminions;

import necesse.engine.CameraShake;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.engine.util.MovedRectangle;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ability.CoordinateMobAbility;
import necesse.entity.mobs.ability.IntMobAbility;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.leaves.CooldownAttackTargetAINode;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerCirclingFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingJumpingMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.PouncingSlimeFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.entity.trails.TrailVector;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class BookMagmaSlimeMinion extends AttackingFollowingJumpingMob
{
    public int lifeTime = 12000;
    public static GameTexture texture;

    public BookMagmaSlimeMinion()
    {
        super(10);
        setSpeed(60.0F);
        setFriction(2.0F);
        jumpStats.setJumpAnimationTime(250);
        jumpStats.setJumpStrength(150.0F);
        jumpStats.setJumpCooldown(50);
        jumpStats.jumpStrengthUseSpeedMod = false;
        collision = new Rectangle(-8, -6, 16, 12);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle();
        swimMaskMove = 8;
        swimMaskOffset = -2;
        swimSinkOffset = 0;
    }

    public int getCollisionKnockback(Mob target) {
        return 30;
    }
    public GameDamage getCollisionDamage(Mob target) {
        return this.summonDamage;
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = this.getAttackOwner();
        if (owner != null && target != null)
        {
            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float)knockback, this);
            this.collisionHitCooldowns.startCooldown(target);

            if (target.buffManager.hasBuff(BuffRegistry.Debuffs.ABLAZE))
            {
                target.addBuff(new ActiveBuff(BuffRegistry.Debuffs.ABLAZE, target, 216000.0F, owner), true);
            }

            if (target.buffManager.hasBuff(BuffRegistry.Debuffs.ON_FIRE))
            {
                target.addBuff(new ActiveBuff(BuffRegistry.Debuffs.ON_FIRE, target, 216000.0F, owner), true);
            }

            if (target.buffManager.hasBuff(BuffRegistry.Debuffs.FROSTBURN))
            {
                target.addBuff(new ActiveBuff(BuffRegistry.Debuffs.FROSTBURN, target, 216000.0F, owner), true);
            }

            if (target.buffManager.hasBuff(BuffRegistry.getBuff("lampgolddebuff")))
            {
                target.addBuff(new ActiveBuff(BuffRegistry.getBuff("lampgolddebuff"), target, 216000.0F, owner), true);
            }

            if (target.buffManager.hasBuff(BuffRegistry.getBuff("lamptungstendebuff")))
            {
                target.addBuff(new ActiveBuff(BuffRegistry.getBuff("lamptungstendebuff"), target, 216000.0F, owner), true);
            }

            if (target.buffManager.hasBuff(BuffRegistry.getBuff("lampcastledebuff")))
            {
                target.addBuff(new ActiveBuff(BuffRegistry.getBuff("lampcastledebuff"), target, 216000.0F, owner), true);
            }

            if (target.buffManager.hasBuff(BuffRegistry.getBuff("lampdungeondebuff")))
            {
                target.addBuff(new ActiveBuff(BuffRegistry.getBuff("lampdungeondebuff"), target, 216000.0F, owner), true);
            }

            this.remove(0.0F, 0.0F, null, true);
        }
    }

    public void init()
    {
        super.init();
        this.ai = new BehaviourTreeAI(this, new PlayerFollowerCollisionChaserAI(140, (GameDamage)null, 30, 500, 200, 80));
    }

    public void serverTick()
    {
        super.serverTick();
        this.lifeTime--;
        if (this.lifeTime <= 0)
        {
            this.remove(0.0F, 0.0F, (Attacker)null, true);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, i, 4, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void playDeathSound()
    {
        float pitch = (Float)GameRandom.globalRandom.getOneOf(new Float[]{0.95F, 1.0F, 1.05F});
        SoundManager.playSound(GameResources.npcdeath, SoundEffect.effect(this).volume(0.1F).pitch(pitch));
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 26 - 32;
        boolean inLiquid = this.inLiquid(x, y);
        int spriteX;
        if (inLiquid)
        {
            spriteX = GameUtils.getAnim(this.getWorldEntity().getTime(), 2, 1000);
        }
        else
        {
            spriteX = this.getJumpAnimationFrame(6);
        }
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        final MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        final DrawOptions options = texture.initDraw().sprite(spriteX, inLiquid ? 1 : 0, 64).addMaskShader(swimMask).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                swimMask.use();
                options.draw();
                swimMask.stop();
            }
        });
        TextureDrawOptions shadow = MobRegistry.Textures.pouncingSlime_shadow.initDraw().sprite(spriteX, 0, 64).light(light).pos(drawX, drawY);
        tileList.add((tm) -> {
            shadow.draw();
        });
    }
}
