package summonerexpansion.mobs.minions.magic;

import necesse.engine.registries.ProjectileRegistry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.projectile.Projectile;
import necesse.inventory.InventoryItem;
import summonerexpansion.mobs.minions.base.SummonHumanBase;

public class SwampWitchMinion extends SummonHumanBase
{
    public int attackLife = 0;

    public SwampWitchMinion()
    {
        super();
        setSpeed(50.0F);
        setFriction(3.0F);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<SwampWitchMinion>(600, 300, false, false, 900, 80)
        {
            public boolean attackTarget(SwampWitchMinion mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("evilwitchgreatswordwave", mob.getLevel(), mob.x, mob.y, target.x, target.y, (40.0F * projVel), 400, summonDamage, mob);
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

        helmet = new InventoryItem("witchhat");
        chest = new InventoryItem("witchrobe");
        boots = new InventoryItem("witchshoes");
        weapon = new InventoryItem("necroticgreatsword");
    }

    public void serverTick()
    {
        super.serverTick();
        if (attackLife >= 6)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }
}