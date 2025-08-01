package summonerexpansion.summontrinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffManager;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.DryadSpiritFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import summonerexpansion.summonminions.MummyMagicMinion;
import summonerexpansion.summonminions.MummySummonMinion;

import java.awt.geom.Point2D;
import java.util.function.BiConsumer;

public class ScryingMagiciansCardBuff extends TrinketBuff
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 50);

    public ScryingMagiciansCardBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber buffEventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_SUMMONS, 5);
        buff.setModifier(BuffModifiers.SUMMONS_SPEED, 0.45F);
        buff.setModifier(BuffModifiers.SUMMON_ATTACK_SPEED, 0.45F);
        buff.setModifier(BuffModifiers.MAGIC_ATTACK_SPEED, 0.45F);
        buff.setModifier(BuffModifiers.COMBAT_MANA_REGEN, 2.5F);
        buff.setModifier(BuffModifiers.MANA_USAGE, 1.0F);
        buff.setModifier(BuffModifiers.MAX_MANA, 0.60F);
        updateModifiers(buff);
    }

    public void clientTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    public void serverTick(ActiveBuff buff) {
        updateModifiers(buff);
    }

    private void updateModifiers(ActiveBuff buff)
    {
        float damageConversion = buff.owner.buffManager.getModifier(BuffModifiers.SUMMON_DAMAGE);
        buff.setModifier(BuffModifiers.MAGIC_DAMAGE, damageConversion / 2);
        buff.owner.buffManager.forceUpdateBuffs();
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event)
    {
        super.onHasAttacked(buff, event);
        if (!event.wasPrevented)
        {
            if (event.damageType == DamageTypeRegistry.MAGIC)
            {
                BuffManager targetBM = event.target.buffManager;
                targetBM.addBuff(new ActiveBuff(BuffRegistry.getBuff("mummymagicdebuff"), event.target, 10.0F, event.attacker), event.target.isServer());
                if (targetBM.getStacks(BuffRegistry.getBuff("mummymagicdebuff")) >= 10)
                {
                    targetBM.removeBuff(BuffRegistry.getBuff("mummymagicdebuff"), true);
                    spawnMagicMummy(event.attacker.getAttackOwner());
                }
            }
            if (event.damageType == DamageTypeRegistry.SUMMON)
            {
                BuffManager targetBM = event.target.buffManager;
                targetBM.addBuff(new ActiveBuff(BuffRegistry.getBuff("mummysummondebuff"), event.target, 10.0F, event.attacker), event.target.isServer());
                if (targetBM.getStacks(BuffRegistry.getBuff("mummysummondebuff")) >= 50)
                {
                    targetBM.removeBuff(BuffRegistry.getBuff("mummysummondebuff"), true);
                    spawnSummonMummy(event.attacker.getAttackOwner());
                }
            }
        }
    }

    public static void spawnMagicMummy(Mob owner)
    {
        if (owner != null && owner.isServer())
        {
            int maxSummons = 2;
            MummyMagicMinion summonedMob = (MummyMagicMinion) MobRegistry.getMob("mummymagicminion", owner.getLevel());
            ((ItemAttackerMob)owner).serverFollowersManager.addFollower("mummymagicdebuff", summonedMob, FollowPosition.WALK_CLOSE, "summonedmob", 1.0F, (p) -> maxSummons, null, false);
            summonedMob.updateDamage(damage);
            Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(summonedMob, owner.getLevel(), owner.x, owner.y);
            owner.getLevel().entityManager.addMob(summonedMob, spawnPoint.x, spawnPoint.y);
        }
    }

    public static void spawnSummonMummy(Mob owner)
    {
        if (owner != null && owner.isServer())
        {
            int maxSummons = 4;
            MummySummonMinion summonedMob = (MummySummonMinion) MobRegistry.getMob("mummysummonminion", owner.getLevel());
            ((ItemAttackerMob)owner).serverFollowersManager.addFollower("mummysummondebuff", summonedMob, FollowPosition.PYRAMID, "summonedmob", 1.0F, (p) -> maxSummons, null, false);
            summonedMob.updateDamage(damage);
            Point2D.Float spawnPoint = SummonToolItem.findSpawnLocation(summonedMob, owner.getLevel(), owner.x, owner.y);
            owner.getLevel().entityManager.addMob(summonedMob, spawnPoint.x, spawnPoint.y);
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "scryingmagicianscardtip"));
        return tooltips;
    }
}