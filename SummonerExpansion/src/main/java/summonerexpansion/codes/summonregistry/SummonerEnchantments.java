package summonerexpansion.codes.summonregistry;

import necesse.engine.modifiers.ModifierValue;
import necesse.inventory.enchants.ToolItemEnchantment;
import necesse.inventory.enchants.ToolItemModifiers;

import static necesse.engine.registries.EnchantmentRegistry.registerSummonEnchantment;

public class SummonerEnchantments
{
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

    public static void registerSummonEnchantments()
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
}