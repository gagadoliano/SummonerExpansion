package summonerexpansion.mobs.summonminions.setminions;

import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.projectile.ButchersCleaverBoomerangProjectile;
import necesse.entity.projectile.Projectile;
import necesse.inventory.InventoryItem;
import summonerexpansion.mobs.summonminions.baseminions.HumanToolBase;

public class SetChefMinion  extends HumanToolBase
{
    public int lifeTime = 0;
    public int lifeStart = 0;

    public SetChefMinion()
    {
        super();
        setSpeed(30.0F);
        setFriction(3.0F);
    }

    public void init()
    {
        super.init();
        updateLook();
        ai = new BehaviourTreeAI(this, new PlayerFollowerChaserAI<SetChefMinion>(600, 600, true, false, 900, 80)
        {
            public boolean attackTarget(SetChefMinion mob, Mob target)
            {
                float projVel = getAttackOwner().buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);

                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    Projectile projectile = ProjectileRegistry.getProjectile("chefsspecialrollingpin", mob.getLevel(), mob.x, mob.y, target.x, target.y, (180.0F * projVel), 600, summonDamage, mob);
                    projectile.setTargetPrediction(target, -20.0F);
                    projectile.moveDist(40.0);

                    if (GameRandom.globalRandom.nextInt(5) == 1)
                    {
                        mob.getLevel().entityManager.projectiles.add(projectile);
                    }
                    else
                    {
                        mob.getLevel().entityManager.projectiles.add(new ButchersCleaverBoomerangProjectile(mob.getLevel(), mob, mob.x, mob.y, target.x, target.y, 180.0F, 600, summonDamage, 0, 25, 3, false));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

        helmet = new InventoryItem("battlechefhat");
        chest = new InventoryItem("battlechefchestplate");
        boots = new InventoryItem("battlechefboots");
        weapon = new InventoryItem("rollingpin");
    }

    public void serverTick()
    {
        super.serverTick();
        lifeStart++;
        if (lifeStart >= lifeTime)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }
}