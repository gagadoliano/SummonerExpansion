package summonerexpansion.items.equips.summontrinketbuffs;

import necesse.engine.localization.Localization;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.trinketBuffs.TrinketBuff;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.trinketItem.TrinketItem;

public class ChallengerArmorPieceBuff extends TrinketBuff
{
    public ChallengerArmorPieceBuff() {}

    public void init(ActiveBuff activeBuff, BuffEventSubscriber buffEventSubscriber)
    {
        activeBuff.setModifier(BuffModifiers.MAX_RESILIENCE_FLAT, 65);
        activeBuff.setModifier(BuffModifiers.RESILIENCE_DECAY, -0.75F);
    }

    public void clientTick(ActiveBuff buff)
    {
        this.updateModifiers(buff);
        if (buff.owner.isVisible() && buff.owner.buffManager.hasBuff(BuffRegistry.BOSS_NEARBY))
        {
            Mob owner = buff.owner;
            owner.getLevel().entityManager.addParticle(owner.x + (float)(GameRandom.globalRandom.nextGaussian() * 8.0), owner.y + (float)(GameRandom.globalRandom.nextGaussian() * 8.0), Particle.GType.IMPORTANT_COSMETIC).sprite(GameResources.magicSparkParticles.sprite(GameRandom.globalRandom.getIntBetween(0, 3), 0, 22)).sizeFades(10, 30).movesConstant(owner.dx / 10.0F, -20.0F).flameColor(50.0F).givesLight(50.0F, 1.0F).height(16.0F);
        }
    }

    public void serverTick(ActiveBuff buff) {
        this.updateModifiers(buff);
    }

    public void updateModifiers(ActiveBuff buff)
    {
        if (buff.owner.buffManager.hasBuff(BuffRegistry.BOSS_NEARBY))
        {
            buff.setModifier(BuffModifiers.RESILIENCE_GAIN, 1.0F);
        }
        else
        {
            buff.setModifier(BuffModifiers.RESILIENCE_GAIN, 0.5F);
        }
    }

    public ListGameTooltips getTrinketTooltip(TrinketItem trinketItem, InventoryItem item, PlayerMob perspective)
    {
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(Localization.translate("itemtooltip", "challengeraptip"));
        return tooltips;
    }
}