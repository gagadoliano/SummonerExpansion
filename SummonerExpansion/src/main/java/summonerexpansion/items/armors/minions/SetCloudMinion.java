package summonerexpansion.items.armors.minions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.Random;

import static summonerexpansion.codes.registries.RegistryArmors.registerArmorSets.CLOUDSPEEDBUFF;
import static summonerexpansion.codes.registries.RegistryMinionTextures.setCloudMinion;

public class SetCloudMinion extends FlyingAttackingFollowingMob
{
    public SetCloudMinion()
    {
        super(10);
        setSpeed(60.0F);
        setFriction(1.0F);
        moveAccuracy = 10;
        accelerationMod = 1.0F;
        collision = new Rectangle(-20, -10, 58, 58);
        hitBox = new Rectangle(-20, -10, 58, 58);
        selectBox = new Rectangle(-20, -10, 58, 58);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<SetCloudMinion>(300, 10, false, false, 900, 50)
        {
            public boolean attackTarget(SetCloudMinion mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);

                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("cloudwaterproj", mob.getLevel(), mob.x + (GameRandom.getIntBetween(new Random(), 1, 25)), mob.y - 60, target.x, target.y, (80.0F * projVel), 100, summonDamage, mob);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    mob.buffManager.addBuff(new ActiveBuff(CLOUDSPEEDBUFF, mob, 20.0F, null), true);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public void serverTick()
    {
        super.serverTick();
        updateAttackSpeed();
    }

    public void clientTick()
    {
        super.clientTick();
        updateAttackSpeed();
    }

    public void updateAttackSpeed()
    {
        attackCooldown = (int)(1050.0F * (1.0F / buffManager.getModifier(BuffModifiers.ATTACK_SPEED)));
        attackAnimTime = (int)(1050.0F * (1.0F / buffManager.getModifier(BuffModifiers.ATTACK_SPEED)));
    }

    public int getFlyingHeight()
    {
        return 100;
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 10;
        drawY -= getFlyingHeight();
        DrawOptions body = setCloudMinion.initDraw().sprite(0, 0, 58).size(58, 58).light(light).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }
}