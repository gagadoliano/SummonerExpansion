package summonerexpansion.codes.registry;

import necesse.engine.modifiers.ModifierValue;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.enchants.EquipmentItemEnchant;
import necesse.inventory.enchants.ToolItemEnchantment;
import necesse.inventory.enchants.ToolItemModifiers;

import static necesse.engine.registries.EnchantmentRegistry.registerEquipmentEnchantment;
import static necesse.engine.registries.EnchantmentRegistry.registerSummonEnchantment;

public class SummonerEnchantments
{
    // Summon weapons
    public static int uncontrollable;
    public static int incapacitated;
    public static int unambiguous;
    public static int definitive;
    public static int ferocious;
    public static int frenzied;
    public static int maniacal;
    public static int untamed;
    public static int strict;
    public static int fierce;
    public static int feral;

    // Equips
    public static int sacrificial;
    public static int weaponized;
    public static int aggressive;
    public static int energetical;
    public static int druidic;
    public static int focused;
    public static int arcanic;
    public static int greedy;

    public static void registerSummonEnchantments()
    {
        SummonerEnchantments.registerSummonWeaponEnchantments();
        SummonerEnchantments.registerEquipEnchantsments();
    }

    public static void registerSummonWeaponEnchantments()
    {
        definitive = registerSummonEnchantment("definitive", new ToolItemEnchantment(20, new ModifierValue<>(ToolItemModifiers.DAMAGE, 0.05F), new ModifierValue<>(ToolItemModifiers.SUMMONS_SPEED, 0.05F), new ModifierValue<>(ToolItemModifiers.SUMMONS_TARGET_RANGE, 0.05F), new ModifierValue<>(ToolItemModifiers.CRIT_CHANCE, 0.05F), new ModifierValue<>(ToolItemModifiers.ARMOR_PEN, 5), new ModifierValue<>(ToolItemModifiers.ATTACK_SPEED, 0.05F)));
        incapacitated = registerSummonEnchantment("incapacitated", new ToolItemEnchantment(0, new ModifierValue<>(ToolItemModifiers.SUMMONS_SPEED, -0.40F), new ModifierValue<>(ToolItemModifiers.SUMMONS_TARGET_RANGE, 0.50F), new ModifierValue<>(ToolItemModifiers.ATTACK_SPEED, -0.20F)));
        frenzied = registerSummonEnchantment("frenzied", new ToolItemEnchantment(0, new ModifierValue<>(ToolItemModifiers.SUMMONS_SPEED, 0.25F), new ModifierValue<>(ToolItemModifiers.SUMMONS_TARGET_RANGE, -0.20F), new ModifierValue<>(ToolItemModifiers.ATTACK_SPEED, 0.25F)));
        uncontrollable = registerSummonEnchantment("uncontrollable", new ToolItemEnchantment(0, new ModifierValue<>(ToolItemModifiers.DAMAGE, 0.05F), new ModifierValue<>(ToolItemModifiers.SUMMONS_SPEED, 0.25F), new ModifierValue<>(ToolItemModifiers.CRIT_CHANCE, 0.05F)));
        untamed = registerSummonEnchantment("untamed", new ToolItemEnchantment(0, new ModifierValue<>(ToolItemModifiers.SUMMONS_SPEED, 0.20F), new ModifierValue<>(ToolItemModifiers.SUMMONS_TARGET_RANGE, 0.10F), new ModifierValue<>(ToolItemModifiers.CRIT_CHANCE, 0.05F)));
        unambiguous = registerSummonEnchantment("unambiguous", new ToolItemEnchantment(-10, new ModifierValue<>(ToolItemModifiers.DAMAGE, -0.20F), new ModifierValue<>(ToolItemModifiers.CRIT_CHANCE, 0.10F), new ModifierValue<>(ToolItemModifiers.ARMOR_PEN, 10)));
        maniacal = registerSummonEnchantment("maniacal", new ToolItemEnchantment(0, new ModifierValue<>(ToolItemModifiers.DAMAGE, 0.25F), new ModifierValue<>(ToolItemModifiers.CRIT_CHANCE, -0.25F), new ModifierValue<>(ToolItemModifiers.ARMOR_PEN, -10)));
        fierce = registerSummonEnchantment("fierce", new ToolItemEnchantment(0, new ModifierValue<>(ToolItemModifiers.SUMMONS_TARGET_RANGE, 0.15F), new ModifierValue<>(ToolItemModifiers.CRIT_CHANCE, 0.05F)));
        ferocious = registerSummonEnchantment("ferocious", new ToolItemEnchantment(0, new ModifierValue<>(ToolItemModifiers.DAMAGE, 0.05F), new ModifierValue<>(ToolItemModifiers.ATTACK_SPEED, 0.15F)));
        feral = registerSummonEnchantment("feral", new ToolItemEnchantment(0, new ModifierValue<>(ToolItemModifiers.SUMMONS_SPEED, 0.30F), new ModifierValue<>(ToolItemModifiers.ARMOR_PEN, 5)));
        strict = registerSummonEnchantment("strict", new ToolItemEnchantment(0, new ModifierValue<>(ToolItemModifiers.DAMAGE, 0.10F), new ModifierValue<>(ToolItemModifiers.CRIT_CHANCE, -0.10F)));
    }

    public static void registerEquipEnchantsments()
    {
        aggressive = registerEquipmentEnchantment("aggressive", new EquipmentItemEnchant(0, new ModifierValue<>(BuffModifiers.TARGET_RANGE, 0.50F), new ModifierValue<>(BuffModifiers.SUMMONS_TARGET_RANGE, 0.10F)));
        arcanic = registerEquipmentEnchantment("arcanic", new EquipmentItemEnchant(0, new ModifierValue<>(BuffModifiers.MAX_MANA_FLAT, 20), new ModifierValue<>(BuffModifiers.COMBAT_MANA_REGEN, 0.10F)));
        greedy = registerEquipmentEnchantment("greedy", new EquipmentItemEnchant(0, new ModifierValue<>(BuffModifiers.ITEM_PICKUP_RANGE, 0.40F), new ModifierValue<>(BuffModifiers.MINING_SPEED, 0.10F)));
        energetical = registerEquipmentEnchantment("energetical", new EquipmentItemEnchant(0, new ModifierValue<>(BuffModifiers.STAMINA_CAPACITY, 0.20F), new ModifierValue<>(BuffModifiers.STAMINA_REGEN, 0.10F)));
        weaponized = registerEquipmentEnchantment("weaponized", new EquipmentItemEnchant(0, new ModifierValue<>(BuffModifiers.PROJECTILE_BOUNCES, 1), new ModifierValue<>(BuffModifiers.PROJECTILE_VELOCITY, 0.10F)));
        sacrificial = registerEquipmentEnchantment("sacrificial", new EquipmentItemEnchant(0, new ModifierValue<>(BuffModifiers.MAX_SUMMONS, -1), new ModifierValue<>(BuffModifiers.SUMMON_DAMAGE, 0.10F)));
        druidic = registerEquipmentEnchantment("druidic", new EquipmentItemEnchant(0, new ModifierValue<>(BuffModifiers.MAX_SUMMONS, -1), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.10F)));
        focused = registerEquipmentEnchantment("focused", new EquipmentItemEnchant(0, new ModifierValue<>(BuffModifiers.CRIT_CHANCE, -0.10F), new ModifierValue<>(BuffModifiers.CRIT_DAMAGE, 0.20F)));
    }
}