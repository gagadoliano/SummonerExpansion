package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.objectEntity.interfaces.OEVicinityBuff;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

public class SunflowerSentry extends AttackingFollowingMob implements OEVicinityBuff
{
    public IntUpgradeValue flowerLevel = new IntUpgradeValue(250, 0.0F).setUpgradedValue(1, 500);
    public int flowerRange;
    public static GameTexture texture;
    public float moveAngle;

    public SunflowerSentry()
    {
        super(10);
        setSpeed(0.0F);
        setFriction(0F);
        attackAnimTime = 750;
        attackCooldown = 2000;
        collision = new Rectangle(0, 0, 34, 66);
        hitBox = new Rectangle(0, 0, 34, 66);
        selectBox = new Rectangle();
        flowerLevel.setBaseValue(250).setUpgradedValue(1, 500);
    }

    public void init()
    {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<SunflowerSentry>(600, 900, false, false, 90000, 64)
        {
            public boolean attackTarget(SunflowerSentry mob, Mob target)
            {
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("sunflowerproj", mob.getLevel(), mob.x, mob.y, target.x, target.y, 60F, 900, summonDamage, mob);
                    projectile.setTargetPrediction(target, -20.0F);
                    projectile.moveDist(40.0);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public Buff[] getBuffs()
    {
        return new Buff[]{BuffRegistry.getBuff("sunflowerbuff")};
    }

    public int getBuffRange()
    {
        ActiveBuff ab = new ActiveBuff(BuffRegistry.getBuff("sunflowerbuff"), getAttackOwner(), 120, this);
        flowerRange = flowerLevel.getValue(ab.getUpgradeTier());
        return flowerRange;
    }

    public boolean shouldBuffPlayers() {
        return true;
    }

    public boolean shouldBuffMobs()
    {
        return true;
    }

    public Predicate<Mob> buffMobsFilter()
    {
        return (m) -> m.isHuman && !m.isSummoned && !m.isHostile;
    }

    public void applyBuffs(Mob mob)
    {
        Buff[] var2 = this.getBuffs();
        for (Buff buff : var2)
        {
            if (buff != null)
            {
                ActiveBuff ab = new ActiveBuff(buff, mob, 120, this);
                mob.buffManager.addBuff(ab, false);
            }
        }
    }

    public void tickVicinityBuff(Mob mob)
    {
        Level level = mob.getLevel();
        int posX = (int) mob.x;
        int posY = (int) mob.y;
        tickVicinityBuff(level, posX, posY);
    }

    public void clientTick()
    {
        super.clientTick();
        tickVicinityBuff(this);
    }

    public void serverTick()
    {
        super.serverTick();
        tickVicinityBuff(this);
    }

    @Override
    public Mob getFirstAttackOwner() {
        return this;
    }

    public boolean canBePushed(Mob other) {
        return false;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 5; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), texture, i, 0, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 20;
        DrawOptions body = texture.initDraw().light(light).rotate(this.moveAngle, 16, 20).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }
}
