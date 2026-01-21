package summonerexpansion.items.equips.trinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.LightningTrailEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

import java.awt.*;

public class LightningAmuletBuff extends TrinketBuff
{
    public int attackCooldown;
    static GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, 10);

    public LightningAmuletBuff() {}

    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber) {
    }

    public void onHasAttacked(ActiveBuff buff, MobWasHitEvent event)
    {
        if (event.damageType == DamageTypeRegistry.SUMMON && attackCooldown <= 0)
        {
            buff.owner.getLevel().entityManager.events.add(new LightningTrailEvent(buff.owner, damage, 0f, (int)buff.owner.x, (int)buff.owner.y, (int)event.target.x, (int)event.target.y, GameRandom.getIntBetween(GameRandom.globalRandom, 1, 100)));
            attackCooldown = 60;
        }
    }

    public void clientTick(ActiveBuff buff)
    {
        if (attackCooldown > 0)
        {
            attackCooldown -= 1;
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = super.getTrinketTooltip(trinketItem, item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "lightningamulettip"));
        float damageStat = damage.damage;
        if (perspective != null)
        {
            damageStat *= GameDamage.getDamageModifier(perspective.getAttackOwner(), DamageTypeRegistry.SUMMON);
        }
        tooltips.add(Localization.translate("itemtooltip", "lightningamulettip2", "damage", (int)damageStat));
        return tooltips;
    }
}