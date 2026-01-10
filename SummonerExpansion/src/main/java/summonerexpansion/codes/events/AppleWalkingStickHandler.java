package summonerexpansion.codes.events;

import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketFireEmeraldWand;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.ai.behaviourTree.leaves.ChaserAINode;
import necesse.entity.mobs.attackHandler.MousePositionAttackHandler;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.GameResources;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import summonerexpansion.items.equips.allweapons.magicsummon.AppleWalkingStick;

public class AppleWalkingStickHandler extends MousePositionAttackHandler
{
    private final InventoryItem item;
    private final AppleWalkingStick toolItem;
    private long lastTime;
    private long timeBuffer;
    private final int attackSeed;
    private int shots;
    private final GameRandom random = new GameRandom();

    public AppleWalkingStickHandler(ItemAttackerMob attackerMob, ItemAttackSlot slot, InventoryItem item, AppleWalkingStick toolItem, int seed, int startTargetX, int startTargetY)
    {
        super(attackerMob, slot, 50, startTargetX, startTargetY);
        this.item = item;
        this.toolItem = toolItem;
        attackSeed = seed;
        lastTime = attackerMob.getLocalTime();
    }

    public void onUpdate()
    {
        super.onUpdate();
        if (!attackerMob.isPlayer && lastItemAttackerTarget != null && !ChaserAINode.hasLineOfSightToTarget(attackerMob, lastItemAttackerTarget, 5.0F))
        {
            attackerMob.endAttackHandler(true);
        }
        else
        {
            int attackX = lastX;
            int attackY = lastY;
            long currentTime = attackerMob.getLevel().getLocalTime();
            if (toolItem.canAttack(attackerMob.getLevel(), attackX, attackY, attackerMob, item) == null)
            {
                timeBuffer += currentTime - lastTime;
                int seed = Item.getRandomAttackSeed(random.seeded(GameRandom.prime(attackSeed * shots)));
                InventoryItem attackItem = item.copy();
                attackItem.getGndData().setBoolean("charging", true);
                attackerMob.showAttackAndSendAttacker(attackItem, attackX, attackY, 0, seed);
                while(true)
                {
                    int cooldown = getShootCooldown();
                    if (timeBuffer < (long)cooldown)
                    {
                        break;
                    }
                    timeBuffer -= cooldown;
                    seed = Item.getRandomAttackSeed(random.nextSeeded(GameRandom.prime(attackSeed * shots)));
                    ++shots;
                    toolItem.fireProjectile(attackerMob.getLevel(), attackX, attackY, attackerMob, item, seed);
                    GNDItemMap attackMap = attackerMob.showAttackAndSendAttacker(item, attackX, attackY, seed, 0);
                    for(ActiveBuff b : attackerMob.buffManager.getArrayBuffs())
                    {
                        b.onItemAttacked(attackX, attackY, attackerMob, attackerMob.getCurrentAttackHeight(), item, slot, 0, attackMap);
                    }
                    if (attackerMob.isClient())
                    {
                        playFireSound(attackerMob);
                    }
                    else if (attackerMob.isServer())
                    {
                        attackerMob.sendAttackerPacket(attackerMob, new PacketFireEmeraldWand(attackerMob));
                    }
                }
            }
            lastTime = currentTime;
        }
    }

    public static void playFireSound(Mob target)
    {
        SoundManager.playSound(GameResources.jingle, SoundEffect.effect(target).pitch(GameRandom.globalRandom.getFloatBetween(1.5F, 1.75F)));
    }

    private int getShootCooldown()
    {
        float multiplier = 1.0F / toolItem.getAttackSpeedModifier(item, attackerMob) * (float)(attackerMob.buffManager.hasBuff(BuffRegistry.Debuffs.MANA_EXHAUSTION) ? 2 : 1);
        return (int)(multiplier * 100.0F);
    }

    public void onEndAttack(boolean bySelf)
    {
        attackerMob.doAndSendStopAttackAttacker(false);
    }
}