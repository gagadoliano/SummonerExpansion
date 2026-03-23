package summonerexpansion.codes.registries;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.buffs.staticBuffs.ShownCooldownBuff;
import summonerexpansion.buffs.*;

import java.awt.*;

import static necesse.entity.mobs.buffs.BuffModifiers.*;
import static summonerexpansion.codes.registries.RegistrySummonModifiers.*;

public class RegistryBuffs
{
    public static class WeaponBuffs
    {
        // Summon
        public static Buff WOODTOOL;
        public static Buff COPPERTOOL;
        public static Buff IRONTOOL;
        public static Buff GOLDTOOL;
        public static Buff RUNICSHIELD;
        public static Buff HONEYBEEBUFF;

        // Melee
        public static Buff CLAW_DASH_COOLDOWN;
        public static Buff CLAW_DASH_STACKS;
        public static Buff CLAWVULTURESPEED;
        public static Buff CLAWSPIDERWEB;
        public static Buff CLAWANCESTOR;
        public static Buff CLAWFALLEN;
        public static Buff CLAWDEMON;
        public static Buff RAMSTACKS;
        public static Buff GOBLINSTACKS;
        public static Buff HORRORSWORDSTACKS;
        public static Buff HORRORGLAIVESTACKS;
        public static Buff FISHIANSTACKS;

        // Magic
        public static Buff APPLEWALKBUFF;
        public static Buff PINEWOODBUFF;

        // Sentry
        public static Buff SUNFLOWERBUFF;
        public static Buff ICEBLOSSOMBUFF;
        public static Buff FIREMONEBUFF;
        public static Buff MUSHROOMBUFF;
        public static Buff XMASTREEBUFF;
        public static Buff COFFEBUFF;

        public WeaponBuffs() {
        }
    }

    public static void registerWeaponBuffs()
    {
        // Summon
        BuffRegistry.registerBuff("woodtoolbuff", WeaponBuffs.WOODTOOL = new BaseStackingBuff(20, new ModifierValue<>(MINING_RANGE, 0.10f), new ModifierValue<>(MINING_SPEED, 0.01f)));
        BuffRegistry.registerBuff("coppertoolbuff", WeaponBuffs.COPPERTOOL = new BaseStackingBuff(20, new ModifierValue<>(MINING_RANGE, 0.15f), new ModifierValue<>(MINING_SPEED, 0.02f)));
        BuffRegistry.registerBuff("irontoolbuff", WeaponBuffs.IRONTOOL = new BaseStackingBuff(20, new ModifierValue<>(MINING_RANGE, 0.20f), new ModifierValue<>(MINING_SPEED, 0.03f)));
        BuffRegistry.registerBuff("goldtoolbuff", WeaponBuffs.GOLDTOOL = new BaseStackingBuff(20, new ModifierValue<>(MINING_RANGE, 0.25f), new ModifierValue<>(MINING_SPEED, 0.04f)));
        BuffRegistry.registerBuff("runicshieldbuff", WeaponBuffs.RUNICSHIELD = new BaseStackingBuff(24, new ModifierValue<>(ARMOR_FLAT, 1)));
        BuffRegistry.registerBuff("honeybeebuff", WeaponBuffs.HONEYBEEBUFF = new BaseStackingBuff(10, new ModifierValue<>(COMBAT_HEALTH_REGEN, 0.02f)));

        // Melee
        BuffRegistry.registerBuff("clawdashcooldown", WeaponBuffs.CLAW_DASH_COOLDOWN = new ShownCooldownBuff());
        BuffRegistry.registerBuff("clawdashstacks", WeaponBuffs.CLAW_DASH_STACKS = new ClawDashStacksBuff());
        BuffRegistry.registerBuff("ramnunchuckstack", WeaponBuffs.RAMSTACKS = new BaseStackingBuff(50, new ModifierValue<>(SUMMONS_SPEED, 0.01f)));
        BuffRegistry.registerBuff("goblinswordstack", WeaponBuffs.GOBLINSTACKS = new BaseStackingBuff(50, new ModifierValue<>(STAMINA_CAPACITY, 0.01f)));
        BuffRegistry.registerBuff("horrorswordstack", WeaponBuffs.HORRORSWORDSTACKS = new BaseStackingBuff(100, new ModifierValue<>(SUMMON_ATTACK_SPEED, 0.02f)));
        BuffRegistry.registerBuff("horrorglaivestack", WeaponBuffs.HORRORGLAIVESTACKS = new BaseStackingBuff(100, new ModifierValue<>(ARMOR_PEN, 0.01f)));
        BuffRegistry.registerBuff("fishianstack", WeaponBuffs.FISHIANSTACKS = new BaseStackingBuff(100, new ModifierValue<>(SWIM_SPEED, 0.01f)));
        BuffRegistry.registerBuff("clawdemonbuff", WeaponBuffs.CLAWDEMON = new BaseClawStackingBuff());
        BuffRegistry.registerBuff("clawspiderbuff", WeaponBuffs.CLAWSPIDERWEB = new BaseClawStackingBuff());
        BuffRegistry.registerBuff("clawvulturebuff", WeaponBuffs.CLAWVULTURESPEED = new ClawVultureBuff());
        BuffRegistry.registerBuff("clawancestorbuff", WeaponBuffs.CLAWANCESTOR = new BaseClawStackingBuff());
        BuffRegistry.registerBuff("clawfallenbuff", WeaponBuffs.CLAWFALLEN = new BaseClawStackingBuff());

        // Magic
        BuffRegistry.registerBuff("applewalkbuff", WeaponBuffs.APPLEWALKBUFF = new BaseStackingBuff(100, new ModifierValue<>(SUMMON_DAMAGE, 0.01f)));
        BuffRegistry.registerBuff("pinewoodbuff", WeaponBuffs.PINEWOODBUFF = new BaseStackingBuff(20, new ModifierValue<>(ARMOR_PEN, 0.01f)));

        // Sentry
        BuffRegistry.registerBuff("sunflowerbuff", WeaponBuffs.SUNFLOWERBUFF = new BaseSentryStackingBuff(new Color(226, 177, 0), 1, new ModifierValue<>(COMBAT_HEALTH_REGEN_FLAT, 0.04F), new ModifierValue<>(HEALTH_REGEN_FLAT, 0.05F)));
        BuffRegistry.registerBuff("iceblossombuff", WeaponBuffs.ICEBLOSSOMBUFF = new BaseSentryStackingBuff(new Color(36, 174, 214), 1, new ModifierValue<>(ARMOR_FLAT, 2), new ModifierValue<>(MAX_RESILIENCE_FLAT, 2)));
        BuffRegistry.registerBuff("firemonebuff", WeaponBuffs.FIREMONEBUFF = new BaseSentryStackingBuff(new Color(225, 58, 1), 1, new ModifierValue<>(ARMOR_PEN_FLAT, 2), new ModifierValue<>(PROJECTILE_VELOCITY, 0.02F)));
        BuffRegistry.registerBuff("mushroomsentrybuff", WeaponBuffs.MUSHROOMBUFF = new MushroomSentryBuff());
        BuffRegistry.registerBuff("xmastreebuff", WeaponBuffs.XMASTREEBUFF = new BaseSentryStackingBuff(new Color(255, 221, 0), 1, new ModifierValue<>(EMITS_LIGHT, true)));
        BuffRegistry.registerBuff("coffebeambuff", WeaponBuffs.COFFEBUFF = new BaseSentryStackingBuff(new Color(121, 49, 33), 60, new ModifierValue<>(SENTRY_ATTACK_SPEED, 0.01f)));
    }

    public static class BannerBuffs
    {
        public static Buff RESILIENCEBOOST;
        public static Buff STAMINABOOST;
        public static Buff ESSENCEBOOST;
        public static Buff BOUNCEBOOST;
        public static Buff PICKUPBOOST;
        public static Buff DASHBOOST;
        public static Buff MANABOOST;

        public BannerBuffs() {
        }
    }

    public static void registerBannerBuffs()
    {
        BuffRegistry.registerBuff("bannerofresilience", BannerBuffs.RESILIENCEBOOST = new BaseBannerBuff(new ModifierValue<>(BuffModifiers.MAX_RESILIENCE, 0.10f), new ModifierValue<>(BuffModifiers.RESILIENCE_REGEN_FLAT, 0.10f)));
        BuffRegistry.registerBuff("bannerofbouncing", BannerBuffs.BOUNCEBOOST = new BaseBannerBuff(new ModifierValue<>(BuffModifiers.PROJECTILE_BOUNCES, 4)));
        BuffRegistry.registerBuff("bannerofessence", BannerBuffs.ESSENCEBOOST = new BaseBannerBuff(new ModifierValue<>(BuffModifiers.LIFE_ESSENCE_DURATION, 2.00f)));
        BuffRegistry.registerBuff("bannerofstamina", BannerBuffs.STAMINABOOST = new BaseBannerBuff(new ModifierValue<>(BuffModifiers.STAMINA_CAPACITY, 0.40f), new ModifierValue<>(BuffModifiers.STAMINA_REGEN, 0.10f), new ModifierValue<>(BuffModifiers.STAMINA_USAGE, -0.10f)));
        BuffRegistry.registerBuff("bannerofpicking", BannerBuffs.PICKUPBOOST = new BaseBannerBuff(new ModifierValue<>(BuffModifiers.ITEM_PICKUP_RANGE, 8f)));
        BuffRegistry.registerBuff("bannerofdashing", BannerBuffs.DASHBOOST = new BaseBannerBuff(new ModifierValue<>(BuffModifiers.DASH_STACKS, 1), new ModifierValue<>(BuffModifiers.DASH_COOLDOWN, -0.10f)));
        BuffRegistry.registerBuff("bannerofmana", BannerBuffs.MANABOOST = new BaseBannerBuff(new ModifierValue<>(BuffModifiers.MAX_MANA, 0.10f), new ModifierValue<>(BuffModifiers.MANA_REGEN, 0.25f)));
    }
}