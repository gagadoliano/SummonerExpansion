package summonerexpansion.mobs.summonminions;

import necesse.engine.network.gameNetworkData.GNDItemGameDamage;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.friendly.BoarMob;
import necesse.entity.mobs.friendly.ChickenMob;
import necesse.entity.mobs.friendly.PigMob;
import necesse.entity.mobs.friendly.RoosterMob;
import necesse.gfx.*;
import necesse.inventory.InventoryItem;
import summonerexpansion.mobs.summonminions.baseminions.HumanToolBase;

public class FarmerMinion extends HumanToolBase
{
    public FarmerMinion()
    {
        super();
        setSpeed(50.0F);
        setFriction(3.0F);
    }

    public void init()
    {
        super.init();
        updateLook();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI<FarmerMinion>(500, summonDamage, 50, 800, 900, 60)
        {
            public boolean attackTarget(FarmerMinion mob, Mob target) 
            {
                if (FarmerMinion.this.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), true);
                    InventoryItem attackItem = new InventoryItem("coppersword");
                    attackItem.getGndData().setItem("damage", new GNDItemGameDamage(summonDamage));
                    FarmerMinion.this.getLevel().entityManager.events.add(new ToolItemMobAbilityEvent(FarmerMinion.this, GameRandom.globalRandom.nextInt(), attackItem, mob.getX(), mob.getY(), FarmerMinion.this.attackAnimTime, FarmerMinion.this.attackAnimTime));

                    if (GameRandom.globalRandom.nextInt(100) <= 10)
                    {
                        getLevel().entityManager.pickups.add((new InventoryItem("wheat")).getPickupEntity(getLevel(), x, y));
                    }

                    if (GameRandom.globalRandom.nextInt(100) <= 2)
                    {
                        if (GameRandom.globalRandom.nextInt(100) <= 20)
                        {
                            mob.getLevel().entityManager.addMob(new RoosterMob(), x, y);
                        }
                        else if (GameRandom.globalRandom.nextInt(100) <= 20)
                        {
                            mob.getLevel().entityManager.addMob(new BoarMob(), x, y);
                        }
                        else if (GameRandom.globalRandom.nextInt(100) <= 20)
                        {
                            mob.getLevel().entityManager.addMob(new PigMob(), x, y);
                        }
                        else
                        {
                            mob.getLevel().entityManager.addMob(new ChickenMob(), x, y);
                        }
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

        helmet = new InventoryItem("farmerhat");
        chest = new InventoryItem("farmershirt");
        boots = new InventoryItem("farmershoes");
        weapon = new InventoryItem("copperpitchfork");
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections)
    {
        super.showAttack(x, y, seed, showAllDirections);
        if (isClient())
        {
            SoundManager.playSound(GameResources.swing2, SoundEffect.effect(this));
        }
    }
}