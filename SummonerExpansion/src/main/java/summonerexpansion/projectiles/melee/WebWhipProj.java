package summonerexpansion.projectiles.melee;

import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.projectile.Projectile;
import necesse.level.maps.LevelObjectHit;

import java.awt.*;

public class WebWhipProj extends WhipBaseProj
{
    public WebWhipProj()
    {

    }

    public WebWhipProj(Mob owner, int targetX, int targetY, int animTime, float frontAttackRange, GameDamage damage, int knockback, Color whipColor, boolean isUpgraded)
    {
        this.x = owner.x;
        this.y = owner.y;
        this.dir = GameMath.normalize((float)targetX - owner.x, (float)targetY - owner.y);
        int distance = 1000;
        this.speed = Projectile.getTravelSpeedForMillis(animTime, (float)distance);
        this.setDistance(distance);
        this.frontAttackRange = frontAttackRange;
        this.setDamage(damage);
        this.knockback = knockback;
        this.setOwner(owner);
        this.whipColor = whipColor;
        this.isUpgraded = isUpgraded;
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        ItemAttackerMob attackerMob = (ItemAttackerMob)getOwner();
        float count = attackerMob.serverFollowersManager.getFollowerCount("summonedwhipspiderminion");
        if (mob != null && isServer() && count <= 2.0F)
        {
            AttackingFollowingMob mobFish = (AttackingFollowingMob) MobRegistry.getMob("whipspiderminion", getLevel());
            if (GameRandom.globalRandom.nextInt(100) <= 20)
            {
                attackerMob.serverFollowersManager.addFollower("summonedwhipspiderminion", mobFish, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, (p) -> 3, null, false);
                mobFish.updateDamage(getDamage());
                getLevel().entityManager.addMob(mobFish, mob.x, mob.y);
            }
        }
    }
}