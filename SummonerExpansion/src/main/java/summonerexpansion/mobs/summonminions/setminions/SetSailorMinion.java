package summonerexpansion.mobs.summonminions.setminions;

import necesse.engine.registries.ProjectileRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.projectile.Projectile;
import necesse.inventory.InventoryItem;
import summonerexpansion.mobs.summonminions.baseminions.HumanToolBase;

public class SetSailorMinion extends HumanToolBase
{
    public int attackLife = 0;

    public SetSailorMinion()
    {
        super();
        setSpeed(50.0F);
        setFriction(3.0F);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<SetSailorMinion>(600, 900, false, false, 900, 80)
        {
            public boolean attackTarget(SetSailorMinion mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("sharpshooterproj", mob.getLevel(), mob.x, mob.y, target.x, target.y, (200.0F * projVel), 800, summonDamage, mob);
                    projectile.setTargetPrediction(target, -20.0F);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    attackLife++;
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

        helmet = new InventoryItem("sailorsummonhat");
        chest = new InventoryItem("sailorsummonshirt");
        boots = new InventoryItem("sailorsummonshoes");
        weapon = new InventoryItem("handgun");
    }

    public void serverTick()
    {
        super.serverTick();
        if (attackLife >= 5)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }
}