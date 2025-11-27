package summonerexpansion.mobs.summonminions.setminions;

import necesse.engine.registries.ProjectileRegistry;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.projectile.Projectile;
import necesse.inventory.InventoryItem;
import summonerexpansion.mobs.summonminions.baseminions.HumanToolBase;

public class SetSharpshooterMinion extends HumanToolBase
{
    public SetSharpshooterMinion()
    {
        super();
        setSpeed(50.0F);
        setFriction(3.0F);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<SetSharpshooterMinion>(600, 300, false, false, 800, 64)
        {
            public boolean attackTarget(SetSharpshooterMinion mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);

                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("sharpshooterproj", mob.getLevel(), mob.x, mob.y, target.x, target.y, (200.0F * projVel), 800, summonDamage, mob);
                    projectile.setTargetPrediction(target, -20.0F);
                    mob.getLevel().entityManager.projectiles.add(projectile);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

        helmet = new InventoryItem("sharpshooterhat");
        chest = new InventoryItem("sharpshootercoat");
        boots = new InventoryItem("sharpshooterboots");
        weapon = new InventoryItem("sixshooter");
    }
}