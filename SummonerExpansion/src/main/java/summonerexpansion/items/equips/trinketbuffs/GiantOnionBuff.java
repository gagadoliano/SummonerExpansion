package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffManager;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.mobs.itemAttacker.FollowPosition;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;
import necesse.level.maps.Level;

import java.awt.*;

public class GiantOnionBuff extends TrinketBuff
{
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 40);

    public GiantOnionBuff() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.SPEED, 0.20F);
    }

    public void serverTick(ActiveBuff buff)
    {
        if (buff.owner.isItemAttacker)
        {
            ItemAttackerMob attackerMob = (ItemAttackerMob)buff.owner;
            float count = attackerMob.serverFollowersManager.getFollowerCount("onionminion");
            if (count <= 0.0F)
            {
                Level level = buff.owner.getLevel();
                AttackingFollowingMob mob = (AttackingFollowingMob) MobRegistry.getMob("onionminion", level);
                attackerMob.serverFollowersManager.addFollower("onionminion", mob, FollowPosition.WALK_CLOSE, "summonedonionminionbuff", 1.0F, 1, null, false);
                mob.updateDamage(damage);
                Point spawnPoint = new Point(attackerMob.getX() + GameRandom.globalRandom.getIntBetween(-5, 5), attackerMob.getY() + GameRandom.globalRandom.getIntBetween(-5, 5));
                level.entityManager.addMob(mob, (float)spawnPoint.x, (float)spawnPoint.y);
            }
        }
    }

    public void onRemoved(ActiveBuff buff)
    {
        BuffManager buffManager = buff.owner.buffManager;
        if (buff.owner.isServer() && buffManager.hasBuff("summonedonionminionbuff"))
        {
            buffManager.removeBuff("summonedonionminionbuff", true);
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "onionminiontip"));
        return tooltips;
    }
}
