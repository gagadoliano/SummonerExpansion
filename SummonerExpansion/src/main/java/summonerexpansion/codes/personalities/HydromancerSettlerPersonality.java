package summonerexpansion.codes.personalities;

import necesse.engine.localization.Localization;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MobWasHitEvent;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.GameColor;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.level.maps.levelData.settlementData.settler.personalities.SimpleQoLModifierSettlerPersonality;

public class HydromancerSettlerPersonality extends SimpleQoLModifierSettlerPersonality
{
    public static int MILLISECONDS_INVIS_COOLDOWN = 10000;
    public long lastTriggerTime = 0L;

    public HydromancerSettlerPersonality(HumanMob mob)
    {
        super(mob);
        modifiers.add(new SimpleQoLModifierSettlerPersonality.QoLModifierValue(1, 2, false, new ModifierValue<>(BuffModifiers.SWIM_SPEED, 0.50F), new ModifierValue<>(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 0.1F)));
        modifiers.add(new SimpleQoLModifierSettlerPersonality.QoLModifierValue(3, 4, false, new ModifierValue<>(BuffModifiers.SWIM_SPEED, 1.00F), new ModifierValue<>(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 0.2F)));
        modifiers.add(new SimpleQoLModifierSettlerPersonality.QoLModifierValue(5, -1, false, new ModifierValue<>(BuffModifiers.SWIM_SPEED, 2.00F), new ModifierValue<>(BuffModifiers.COMBAT_HEALTH_REGEN_FLAT, 0.5F), new ModifierValue<>(BuffModifiers.WATER_WALKING, true)));
        modifiers.add(new SimpleQoLModifierSettlerPersonality.QoLModifierValue(6, -1, true, new ModifierValue<>(BuffModifiers.SPEED, 0.10F), new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.05F)));
    }

    public void addAdditionalModifierTooltip(ListGameTooltips tooltips, SimpleQoLModifierSettlerPersonality.QoLModifierValue modifier)
    {
        super.addAdditionalModifierTooltip(tooltips, modifier);
        if (modifier.minQualityOfLifeLevel >= 5 && modifier.maxQualityOfLifeLevel == -1 && !modifier.stackEachLevelAbove)
        {
            tooltips.add(new StringTooltips(Localization.translate("personalities", "hydromancerspray"), this.mob.getSettlerQualityOfLife() >= 5 ? GameColor.GREEN : GameColor.GRAY, 400));
        }
    }

    public void addAdditionalCurrentBonusTooltip(ListGameTooltips tooltips)
    {
        super.addAdditionalCurrentBonusTooltip(tooltips);
        if (this.mob.getSettlerQualityOfLife() >= 5)
        {
            tooltips.add(new StringTooltips(Localization.translate("personalities", "hydromancerspray"), GameColor.GREEN, 400));
        }
    }

    public void addSaveData(SaveData save)
    {
        super.addSaveData(save);
        save.addLong("lastTriggerTime", this.lastTriggerTime);
    }

    public void applyLoadData(LoadData save)
    {
        super.applyLoadData(save);
        this.lastTriggerTime = save.getLong("lastTriggerTime", this.lastTriggerTime, false);
    }

    public void onMobHasAttacked(MobWasHitEvent event)
    {
        super.onMobHasAttacked(event);
        if (this.mob.getSettlerQualityOfLife() >= 3 && (this.lastTriggerTime + (long)MILLISECONDS_INVIS_COOLDOWN < this.mob.getTime() || this.lastTriggerTime == 0L))
        {
            this.lastTriggerTime = this.mob.getTime();
            GameDamage fireDamage = new GameDamage(DamageTypeRegistry.SUMMON, mob.getSettlerQualityOfLife() * 10);
            Projectile projectile = ProjectileRegistry.getProjectile("waterspray", mob.getLevel(), mob.x, mob.y, event.target.x, event.target.y, 80.0F, 640, fireDamage, mob);
            projectile.setTargetPrediction(event.target, -20.0F);
            projectile.moveDist(20.0);
            mob.getLevel().entityManager.projectiles.add(projectile);
        }
    }
}