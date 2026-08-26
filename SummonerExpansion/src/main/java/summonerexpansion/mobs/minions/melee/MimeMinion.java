package summonerexpansion.mobs.minions.melee;

import necesse.engine.network.gameNetworkData.GNDItemGameDamage;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.ability.IntMobAbility;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.gfx.GameResources;
import necesse.inventory.InventoryItem;
import summonerexpansion.mobs.minions.base.SummonHumanBase;

import java.awt.*;

public class MimeMinion  extends SummonHumanBase
{
    public int lifeTime = 0;
    protected long startSwingAttackTime;
    protected int swingAttackDir;
    protected final IntMobAbility startSwingAttackAbility;
    protected final IntMobAbility fireSwingAttackAbility;
    protected int swingAttackChargeUpTime = 750;
    protected int swingAttackSlashTime = 200;

    public MimeMinion()
    {
        super();
        setSpeed(50.0F);
        setFriction(3.0F);
        startSwingAttackAbility = registerAbility(new IntMobAbility()
        {
            protected void run(int value)
            {
                startSwingAttackTime = getTime();
                attackAnimTime = swingAttackChargeUpTime + 500;
                attackCooldown = swingAttackChargeUpTime + 1000;
                swingAttackDir = value;
                setDir(value);
                Point vector = getDirVector();
                attack(getX() + vector.x * 100, getY() + vector.y * 100, true);
                stopMoving();
            }
        });
        fireSwingAttackAbility = registerAbility(new IntMobAbility()
        {
            protected void run(int value)
            {
                startSwingAttackTime = 0L;
                attackAnimTime = swingAttackSlashTime;
                attackCooldown = swingAttackSlashTime + 500;
                setDir(value);
                Point vector = getDirVector();
                int aimX = vector.x * 100;
                int aimY = vector.y * 100;
                attack(getX() + aimX, getY() + aimY, true);
                if (isServer())
                {
                    InventoryItem attackItem = new InventoryItem("invisiblesword");
                    attackItem.getGndData().setItem("damage", new GNDItemGameDamage(summonDamage));
                    getLevel().entityManager.events.add(new ToolItemMobAbilityEvent(MimeMinion.this, GameRandom.globalRandom.nextInt(), attackItem, aimX, aimY, swingAttackSlashTime, swingAttackSlashTime));
                }
                else
                {
                    SoundManager.playSound(GameResources.woodGreatsword3, SoundEffect.effect(MimeMinion.this).volume(0.7F));
                }
            }
        });
    }

    public void init()
    {
        super.init();
        updateLook();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI<MimeMinion>(500, summonDamage, 50, 800, 900, 60)
        {
            public boolean attackTarget(MimeMinion mob, Mob target)
            {
                if (canAttack())
                {
                    mob.showAttack(target.getX(), target.getY(), true);
                    mob.startSwingAttackAbility.runAndSend(mob.getDir());
                    InventoryItem attackItem = new InventoryItem("invisiblesword");
                    attackItem.getGndData().setItem("damage", new GNDItemGameDamage(summonDamage));
                    getLevel().entityManager.events.add(new ToolItemMobAbilityEvent(MimeMinion.this, GameRandom.globalRandom.nextInt(), attackItem, mob.getX(), mob.getY(), attackAnimTime, attackAnimTime));
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

        helmet = new InventoryItem("mimeberet");
        chest = new InventoryItem("mimeshirt");
        boots = new InventoryItem("mimeboots");
        weapon = new InventoryItem("invisiblesword");
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections)
    {
        super.showAttack(x, y, seed, showAllDirections);
        if (isClient())
        {
            SoundManager.playSound(GameResources.swing2, SoundEffect.effect(this));
        }
    }

    public void serverTick()
    {
        super.serverTick();
        lifeTime++;
        if (lifeTime >= 1200)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }
}