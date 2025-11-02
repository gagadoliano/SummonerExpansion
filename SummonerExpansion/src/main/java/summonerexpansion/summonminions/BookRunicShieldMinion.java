package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFlyingFollowerCollisionChaserAI;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class BookRunicShieldMinion extends FlyingAttackingFollowingMob
{
    public static GameTexture texture;
    public static MaxHealthGetter MAX_HEALTH = new MaxHealthGetter(100, 150, 200, 250, 300);

    public BookRunicShieldMinion()
    {
        super(10);
        difficultyChanges.setMaxHealth(MAX_HEALTH);
        setArmor(10);
        setSpeed(100.0F);
        setFriction(2.0F);
        isStatic = false;
        moveAccuracy = 5;
        collision = new Rectangle(-30, -30, 60, 60);
        hitBox = new Rectangle(-30, -30, 60, 60);
        selectBox = new Rectangle();
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return summonDamage; }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI(this, new PlayerFlyingFollowerCollisionChaserAI(0, summonDamage, 105, 500, 1000, 64), new FlyingAIMover());
    }

    public int getCollisionKnockback(Mob target) {
        return 105;
    }

    @Override
    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = getAttackOwner();
        if (owner != null && target != null)
        {
            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float)knockback, this);
            collisionHitCooldowns.startCooldown(target);
            ActiveBuff buff;
            if (getAttackOwner().buffManager.hasBuff("runicsetbonus"))
            {
                buff = new ActiveBuff(BuffRegistry.getBuff("runicshieldbuff"), target, 60F, this);
            }
            else
            {
                buff = new ActiveBuff(BuffRegistry.getBuff("runicshieldbuff"), target, 30F, this);
            }
            getFollowingItemAttacker().addBuff(buff, true);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 30; ++i)
        {
            GameRandom random = GameRandom.globalRandom;
            getLevel().entityManager.addParticle(this.x, this.y, Particle.GType.COSMETIC).sprite(GameResources.particles.sprite(random.nextInt(4), 0, 18, 24)).movesConstantAngle((float)random.nextInt(360), (float)random.getIntBetween(5, 20)).sizeFades(24, 12);
        }
    }

    public void playDeathSound()
    {
        SoundManager.playSound(GameResources.shatter1, SoundEffect.effect(this).volume(0.5F));
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 10;
        DrawOptions body = texture.initDraw().sprite(0, 0, 32).light(light).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
        DrawOptions shadow = MobRegistry.Textures.rubyShield_shadow.initDraw().sprite(0, 0, 32).light(light).pos(drawX, drawY + 10);
        tileList.add((tm) -> {
            shadow.draw();
        });
    }
}