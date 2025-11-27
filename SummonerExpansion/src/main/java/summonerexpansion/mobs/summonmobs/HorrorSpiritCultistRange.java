package summonerexpansion.mobs.summonmobs;

import necesse.engine.registries.ProjectileRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.ConfusedPlayerChaserWandererAI;
import necesse.entity.projectile.Projectile;
import necesse.level.maps.IncursionLevel;

public class HorrorSpiritCultistRange extends HorrorSpiritCultistMob
{
    public static GameDamage baseDamage = new GameDamage(25.0F);
    public static GameDamage incursionDamage = new GameDamage(100.0F);

    public HorrorSpiritCultistRange()
    {

    }

    @Override
    public void init()
    {
        GameDamage damage;
        if (this.getLevel() instanceof IncursionLevel)
        {
            damage = incursionDamage;
        }
        else
        {
            damage = baseDamage;
        }
        ai = new BehaviourTreeAI(this, new ConfusedPlayerChaserWandererAI<HorrorSpiritCultistRange>(null, 1000, 600, 40000, false, false)
        {
            public boolean attackTarget(HorrorSpiritCultistRange mob, Mob target)
            {
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("horrorcultarrowproj", mob.getLevel(), mob.x, mob.y, target.x, target.y, 180.0F, 640, damage, mob);
                    projectile.setTargetPrediction(target, -20.0F);
                    projectile.moveDist(20.0);
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

}