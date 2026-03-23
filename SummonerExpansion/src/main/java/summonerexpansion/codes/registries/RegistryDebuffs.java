package summonerexpansion.codes.registries;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import summonerexpansion.buffs.debuffs.*;

import java.awt.*;

import static necesse.entity.mobs.buffs.BuffModifiers.*;

public class RegistryDebuffs
{
    public static class WeaponDebuffs
    {
        // Summon
        public static Buff LAMPGOLDFIRE;
        public static Buff LAMPCASTLEFIRE;
        public static Buff LAMPTUNGSTENFIRE;
        public static Buff LAMPDUNGEONFIRE;
        public static Buff REDSPIDERPOISON;
        public static Buff BEARBLEEDING;
        public static Buff POLARBEARSLOW;
        public static Buff HONEYBEESLOW;
        public static Buff BOOKFROZENSLOW;
        public static Buff ICEWIZARDFROST;
        public static Buff ENCHANTEDWEAK;
        public static Buff MOSQUITOWEAK;

        // Melee
        public static Buff CLAWPOLARSLOW;
        public static Buff CLAWLEATHERWEAK;
        public static Buff CLAWNECROPOISON;
        public static Buff CLAWPRIMORDIAL;
        
        // Magic
        
        // Sentry
        public static Buff MUSHROOMSENTRYSLOW;


        public WeaponDebuffs() {
        }
    }

    public static void registerWeaponDebuffs()
    {
        // Summon
        BuffRegistry.registerBuff("lampgolddebuff", WeaponDebuffs.LAMPGOLDFIRE = new BaseStackingDebuff(new Color(226, 170, 0), 5, new ModifierValue<>(FIRE_DAMAGE_FLAT, 0.01f)));
        BuffRegistry.registerBuff("lampdungeondebuff", WeaponDebuffs.LAMPDUNGEONFIRE = new BaseStackingDebuff(new Color(109, 96, 165), 6, new ModifierValue<>(FIRE_DAMAGE_FLAT , 1.0F)));
        BuffRegistry.registerBuff("lamptungstendebuff", WeaponDebuffs.LAMPTUNGSTENFIRE = new BaseStackingDebuff(new Color(226, 170, 0), 10, new ModifierValue<>(FIRE_DAMAGE_FLAT, 0.01f)));
        BuffRegistry.registerBuff("lampcastledebuff", WeaponDebuffs.LAMPCASTLEFIRE = new LampCastleDebuff());
        BuffRegistry.registerBuff("redspiderpoisondebuff", WeaponDebuffs.REDSPIDERPOISON = new BaseStackingDebuff(new Color(169, 37, 33), 1, new ModifierValue<>(POISON_DAMAGE_FLAT, 2F)));
        BuffRegistry.registerBuff("bearbleedingdebuff", WeaponDebuffs.BEARBLEEDING = new BearBleedingDebuff());
        BuffRegistry.registerBuff("polarslowdebuff", WeaponDebuffs.POLARBEARSLOW = new BaseStackingDebuff(new Color(149, 204, 245), 25, new ModifierValue<>(SLOW, 0.01F)));
        BuffRegistry.registerBuff("honeydebuff", WeaponDebuffs.HONEYBEESLOW = new BaseStackingDebuff(new Color(226, 166, 68), 10, new ModifierValue<>(SLOW, 0.01F)));
        BuffRegistry.registerBuff("frozenbookdebuff", WeaponDebuffs.BOOKFROZENSLOW = new BaseStackingDebuff(new Color(92, 166, 193), 25, new ModifierValue<>(SLOW, 0.02F), new ModifierValue<>(FRICTION, -0.01F), new ModifierValue<>(DECELERATION, -0.01F)));
        BuffRegistry.registerBuff("icewizarddebuff", WeaponDebuffs.ICEWIZARDFROST = new BaseStackingDebuff(new Color(92, 166, 193), 1, new ModifierValue<>(SLOW, 0.30F), new ModifierValue<>(FROST_DAMAGE_FLAT, 2.00F), new ModifierValue<>(FROST_DAMAGE, 1.00F)));
        BuffRegistry.registerBuff("enchanteddebuff", WeaponDebuffs.ENCHANTEDWEAK = new BaseStackingDebuff(new Color(76, 61, 94), 10, new ModifierValue<>(ARMOR_FLAT, -1)));

        // Melee
        BuffRegistry.registerBuff("clawleatherdebuff", WeaponDebuffs.CLAWLEATHERWEAK = new BaseClawStackingDebuff("genericdamagetakenstack", 20));
        BuffRegistry.registerBuff("clawpolardebuff", WeaponDebuffs.CLAWPOLARSLOW = new BaseClawStackingDebuff("genericslowstack", 10));
        BuffRegistry.registerBuff("clawnecrodebuff", WeaponDebuffs.CLAWNECROPOISON = new BaseClawStackingDebuff("necroticpoison"));
        BuffRegistry.registerBuff("clawprimordialdebuff", WeaponDebuffs.CLAWPRIMORDIAL = new BaseClawStackingDebuff("genericdoubledotstack"));

        // Ranged
        BuffRegistry.registerBuff("mosquitodebuff", WeaponDebuffs.MOSQUITOWEAK = new BaseStackingDebuff(new Color(76, 61, 94), 20, new ModifierValue<>(ARMOR_FLAT, -1)));

        // Sentry
        BuffRegistry.registerBuff("mushroomslowdebuff", WeaponDebuffs.MUSHROOMSENTRYSLOW = new BaseStackingDebuff(new Color(174, 161, 137), 20, new ModifierValue<>(ARMOR_FLAT, -1)));
    }

    public static class GenericDebuffs
    {
        // DOT
        public static Buff GENERICFIRE;
        public static Buff GENERICFROST;
        public static Buff GENERICPOISON;
        public static Buff GENERICBLEED;
        public static Buff GENERICDOUBLEDOT;

        // STAT
        public static Buff GENERICSLOW;
        public static Buff GENERICATTACKSLOW;
        public static Buff GENERICATTACKDOWN;
        public static Buff GENERICARMORDOWN;
        public static Buff GENERICDAMAGED;
        public static Buff GENERICSWIMSLOW;
        public static Buff GENERICVELOCITYSLOW;

        public GenericDebuffs() {
        }
    }

    public static void registerGenericDebuffs()
    {
        // Dot
        BuffRegistry.registerBuff("genericfirestack", GenericDebuffs.GENERICFIRE = new BaseStackingDebuff(new Color(172, 20, 0), 10, new ModifierValue<>(FIRE_DAMAGE_FLAT, 1.00f)));
        BuffRegistry.registerBuff("genericfroststack", GenericDebuffs.GENERICFROST = new BaseStackingDebuff(new Color(0, 247, 255), 10, new ModifierValue<>(FROST_DAMAGE_FLAT, 1.00f)));
        BuffRegistry.registerBuff("genericpoisonstack", GenericDebuffs.GENERICPOISON = new BaseStackingDebuff(new Color(17, 151, 2), 10, new ModifierValue<>(POISON_DAMAGE_FLAT, 1.00f)));
        BuffRegistry.registerBuff("genericbleedstack", GenericDebuffs.GENERICBLEED = new BaseStackingDebuff(new Color(236, 18, 18), 10, new ModifierValue<>(BLEED_DAMAGE_FLAT, 1.00f)));
        BuffRegistry.registerBuff("genericdoubledotstack", GenericDebuffs.GENERICDOUBLEDOT = new BaseStackingDebuff(new Color(236, 18, 18), 1, new ModifierValue<>(FROST_DAMAGE, 1F), new ModifierValue<>(FIRE_DAMAGE, 1F), new ModifierValue<>(POISON_DAMAGE, 1F), new ModifierValue<>(BLEED_DAMAGE, 1F)));

        // Stat
        BuffRegistry.registerBuff("genericslowstack", GenericDebuffs.GENERICSLOW = new BaseStackingDebuff(new Color(12, 189, 205), 10, new ModifierValue<>(SLOW, 0.05f)));
        BuffRegistry.registerBuff("genericswimslowstack", GenericDebuffs.GENERICSWIMSLOW = new BaseStackingDebuff(null, 10, new ModifierValue<>(SWIM_SPEED, -0.05f)));
        BuffRegistry.registerBuff("genericattackslowstack", GenericDebuffs.GENERICATTACKSLOW = new BaseStackingDebuff(null, 50, new ModifierValue<>(ATTACK_SPEED, -0.01f)));
        BuffRegistry.registerBuff("genericvelocityslowstack", GenericDebuffs.GENERICVELOCITYSLOW = new BaseStackingDebuff(null, 25, new ModifierValue<>(PROJECTILE_VELOCITY, -0.02f)));
        BuffRegistry.registerBuff("genericattackdownstack", GenericDebuffs.GENERICATTACKDOWN = new BaseStackingDebuff(null, 50, new ModifierValue<>(ALL_DAMAGE, -0.01f)));
        BuffRegistry.registerBuff("genericarmordownstack", GenericDebuffs.GENERICARMORDOWN = new BaseStackingDebuff(null, 20, new ModifierValue<>(ARMOR, -0.01f)));
        BuffRegistry.registerBuff("genericdamagetakenstack", GenericDebuffs.GENERICDAMAGED = new BaseStackingDebuff(null, 20, new ModifierValue<>(INCOMING_DAMAGE_MOD, 0.01f)));
    }
}