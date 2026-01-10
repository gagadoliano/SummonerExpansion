package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.registries.ItemRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.particle.Particle;
import necesse.inventory.PlayerInventoryManager;
import necesse.inventory.item.Item;
import necesse.level.maps.Level;

import java.awt.*;

public class TitaniumSetBonus extends SetBonusBuff
{
    public TitaniumSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
    }

    public void serverTick(ActiveBuff buff)
    {
        super.serverTick(buff);
        updateActiveBuff(buff);
    }

    public void clientTick(ActiveBuff buff)
    {
        updateActiveBuff(buff);
        Mob buffOwner = buff.owner;
        Level level = buffOwner.getLevel();
        if (level != null && buffOwner.isVisible() && level.tickManager().getTotalTicks() % 2L == 0L)
        {
            level.entityManager.addParticle(buffOwner.x + (float)(GameRandom.globalRandom.nextGaussian() * (double)10.0F), buffOwner.y + (float)(GameRandom.globalRandom.nextGaussian() * (double)8.0F), Particle.GType.IMPORTANT_COSMETIC).sizeFades(5, 10).lifeTime(1500).movesConstantAngle((float)GameRandom.globalRandom.getIntBetween(0, 360), 3.0F).color(216,216,216,255).height(16.0F);
        }
    }

    public void updateActiveBuff(ActiveBuff buff)
    {
        float value = 0.005F * (float)this.getUsedSlots(buff);
        if (!(value < 0.005F))
        {
            buff.setModifier(BuffModifiers.SUMMON_DAMAGE, value);
        }
    }

    private int getUsedSlots(ActiveBuff ab)
    {
        if (ab.owner instanceof PlayerMob)
        {
            PlayerInventoryManager inv = ((PlayerMob)ab.owner).getInv();
            return inv.main.getUsedSlots();
        }
        else
        {
            return 0;
        }
    }
}