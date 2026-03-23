package summonerexpansion.codes.registries;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.LevelIdentifier;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.item.baitItem.BaitItem;
import necesse.inventory.item.matItem.FishItem;
import necesse.inventory.item.matItem.MatItem;
import necesse.inventory.item.placeableItem.consumableItem.TrinketSlotsIncreaseItem;
import necesse.inventory.item.placeableItem.mapItem.WorldPresetMapItem;
import necesse.level.maps.levelData.settlementData.settler.Settler;
import summonerexpansion.items.boss.*;
import summonerexpansion.items.fishing.*;
import summonerexpansion.items.foods.*;
import summonerexpansion.items.materials.*;
import summonerexpansion.items.potions.*;
import summonerexpansion.items.tools.*;

import static summonerexpansion.codes.registries.RegistrySummonModifiers.*;

public class RegistryItems
{
    public static void registerItems()
    {
        RegistryItems.registerMaterials();
        RegistryItems.registerFoods();
        RegistryItems.registerMaps();
        RegistryItems.registerFishing();
        RegistryItems.registerBanner();
        RegistryItems.registerPermanent();
        RegistryItems.registerBags();
        RegistryItems.registerConsumables();
    }

    public static void registerMaterials()
    {
        // Woods
        ItemRegistry.registerItem("ancientlog", (new BaseMatItem(500, "anylog")).setItemCategory("materials", "logs"), 2, true);
        // Ores & Bars
        ItemRegistry.registerItem("titaniumbar", (new BaseMatItem(250, Item.Rarity.RARE)).setItemCategory("materials", "bars"), 4, true);
        ItemRegistry.registerItem("titaniumore", (new BaseMatItem(500, Item.Rarity.RARE)).setItemCategory("materials", "ore"), 1, true);
        // Mob drops
        ItemRegistry.registerItem("sandgiantscorpiontail", (new BaseMatItem(500, Item.Rarity.RARE)).setItemCategory("materials", "mobdrops"), 18, true);
        ItemRegistry.registerItem("fossilhoney", (new BaseMatItem(999, Item.Rarity.UNCOMMON)).setItemCategory("materials", "mobdrops"), 10, true);
        ItemRegistry.registerItem("sharklavascales", (new BaseMatItem(999, Item.Rarity.RARE)).setItemCategory("materials", "mobdrops"), 10, true);
        ItemRegistry.registerItem("purehorror", new PureHorror(), 50, true);
    }

    public static void registerFoods()
    {
        //Simple
        ItemRegistry.registerItem("berrytrio", (new BaseFoodItemConsumable(250, Item.Rarity.NORMAL, Settler.FOOD_FINE, 10, 1200, new ModifierValue<>(BuffModifiers.SUMMON_CRIT_CHANCE, 0.05F), new ModifierValue<>(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.05F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.05F))).spoilDuration(500), 10F, true);
        ItemRegistry.registerItem("rottenbread", (new BaseFoodItemConsumable(250, Item.Rarity.NORMAL, Settler.FOOD_SIMPLE, 15, 1200, new ModifierValue<>(BuffModifiers.MAX_HEALTH, -0.20F), new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 1))).spoilDuration(500), 0F, true);
        ItemRegistry.registerItem("cookedworm", (new BaseFoodItemConsumable(250, Item.Rarity.NORMAL, Settler.FOOD_SIMPLE, 15, 1200, new ModifierValue<>(BuffModifiers.FISHING_POWER, 10), new ModifierValue<>(BuffModifiers.FISHING_LINES, 1))).spoilDuration(500), 10F, true);
        ItemRegistry.registerItem("cookedswampworm", (new BaseFoodItemConsumable(250, Item.Rarity.NORMAL, Settler.FOOD_SIMPLE, 15, 1200, new ModifierValue<>(BuffModifiers.FISHING_POWER, 20), new ModifierValue<>(BuffModifiers.FISHING_LINES, 2))).spoilDuration(500), 10F, true);
        ItemRegistry.registerItem("orcasugar", (new BaseFoodItemConsumable(250, Item.Rarity.NORMAL, Settler.FOOD_SIMPLE, 5, 1200, new ModifierValue<>(BuffModifiers.STAMINA_USAGE, -0.20F), new ModifierValue<>(BuffModifiers.STAMINA_CAPACITY, 0.10F))).spoilDuration(500), 0F, true);
        ItemRegistry.registerItem("rayjam", (new BaseFoodItemConsumable(250, Item.Rarity.NORMAL, Settler.FOOD_SIMPLE, 10, 1200, new ModifierValue<>(BuffModifiers.EMITS_LIGHT, true), new ModifierValue<>(BuffModifiers.BOUNCY, true))).spoilDuration(500), 0F, true);
        //Fine
        ItemRegistry.registerItem("livingsharksoup", (new BaseFoodItemConsumable(250, Item.Rarity.UNCOMMON, Settler.FOOD_FINE, 25, 1200, new ModifierValue<>(BuffModifiers.SWIM_SPEED, 0.20F), new ModifierValue<>(TRANSFORMATION_SPEED, 0.20F))).spoilDuration(500), 20F, true);
        ItemRegistry.registerItem("fullpicnicbasket", (new BaseFoodItemConsumable(250, Item.Rarity.UNCOMMON, Settler.FOOD_FINE, 25, 1200, new ModifierValue<>(BuffModifiers.MAX_HEALTH, 0.10F), new ModifierValue<>(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.15F))).spoilDuration(500), 20F, true);
        ItemRegistry.registerItem("enchantedgoblet", (new BaseFoodItemConsumable(250, Item.Rarity.UNCOMMON, Settler.FOOD_FINE, 25, 1200, new ModifierValue<>(BuffModifiers.MAX_MANA, 0.20F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.20F))).spoilDuration(500), 20F, true);
        ItemRegistry.registerItem("runicmeal", (new BaseFoodItemConsumable(250, Item.Rarity.UNCOMMON, Settler.FOOD_FINE, 25, 1200, new ModifierValue<>(BuffModifiers.MANA_USAGE, -0.15F), new ModifierValue<>(BuffModifiers.SUMMONS_TARGET_RANGE, 0.25F))).spoilDuration(500), 20F, true);
        ItemRegistry.registerItem("waterzombiesoup", (new BaseFoodItemConsumable(250, Item.Rarity.UNCOMMON, Settler.FOOD_FINE, 35, 1200, new ModifierValue<>(TRANSFORMATION_SPEED, 0.25F), new ModifierValue<>(BuffModifiers.SWIM_SPEED, 0.25F))).spoilDuration(500), 20F, true);
        ItemRegistry.registerItem("icecube", (new BaseFoodItemConsumable(250, Item.Rarity.UNCOMMON, Settler.FOOD_FINE, 25, 1200, new ModifierValue<>(BuffModifiers.SPEED, -0.20F), new ModifierValue<>(TRANSFORMATION_SPEED, -0.20F), new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, -0.20F), new ModifierValue<>(BuffModifiers.SUMMON_DAMAGE, 0.25F))).spoilDuration(500), 20F, true);
        ItemRegistry.registerItem("pigbanquet", (new BaseFoodItemConsumable(250, Item.Rarity.RARE, Settler.FOOD_FINE, 30, 1200, new ModifierValue<>(BuffModifiers.SPEED, -0.20F), new ModifierValue<>(BuffModifiers.ARMOR, 0.10F), new ModifierValue<>(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.10F))).spoilDuration(500), 50F, true);
        ItemRegistry.registerItem("caveglowjam", (new BaseFoodItemConsumable(250, Item.Rarity.RARE, Settler.FOOD_FINE, 30, 1200, new ModifierValue<>(EMITS_SUMMON_LIGHT, true), new ModifierValue<>(BuffModifiers.SUMMON_DAMAGE, 0.10F))).spoilDuration(500), 50F, true);
        ItemRegistry.registerItem("spicycoffee", (new BaseFoodItemConsumable(250, Item.Rarity.EPIC, Settler.FOOD_FINE, 25, 1800, new ModifierValue<>(TRANSFORMATION_SPEED, 0.45F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.25F))).spoilDuration(500), 100F, true);
        // Gourmet
        ItemRegistry.registerItem("dragondonut", (new BaseFoodItemConsumable(250, Item.Rarity.EPIC, Settler.FOOD_GOURMET, 30, 1200, new ModifierValue<>(TRANSFORMATION_SPEED, 0.20F), new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.20F), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.20F), new ModifierValue<>(BuffModifiers.SUMMON_DAMAGE, 0.20F))).spoilDuration(500), 100F, true);
    }

    public static void registerMaps()
    {
        ItemRegistry.registerItem("ancientforestmap", new WorldPresetMapItem(Item.Rarity.RARE, LevelIdentifier.SURFACE_IDENTIFIER, 800, "ancientforesticon", new LocalMessage("biome", "ancientforestminibiome"), "ancientforestminibiome"), 40f, true);
        ItemRegistry.registerItem("beehivemap", new WorldPresetMapItem(Item.Rarity.RARE, LevelIdentifier.SURFACE_IDENTIFIER, 800, "beehivechesticon", new LocalMessage("biome", "beehivearea"), "beehivearea"), 40f, true);
        ItemRegistry.registerItem("titaniumnodemap", new WorldPresetMapItem(Item.Rarity.RARE, LevelIdentifier.CAVE_IDENTIFIER, 800, "titaniumicon", new LocalMessage("biome", "titaniumorenode"), "titaniumorenode"), 40f, true);
        ItemRegistry.registerItem("druidhousemap", new WorldPresetMapItem(Item.Rarity.RARE, LevelIdentifier.SURFACE_IDENTIFIER, 800, "druidhomeicon", new LocalMessage("biome", "druidswamphouse"), "druidswamphouse", "druidswamphouseduo", "druidswamphouseside", "druidswamphouseloot", "druidswamphousecave", "druidswamphousealtar"), 40f, true);
        ItemRegistry.registerItem("horrorcultmap", new WorldPresetMapItem(Item.Rarity.RARE, LevelIdentifier.DEEP_CAVE_IDENTIFIER, 800, "horrorculticon", new LocalMessage("biome", "horrorsmallarena"), "horrorsmallarena")
        {
            public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
            {
                ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
                tooltips.add(Localization.translate("itemtooltip", "horrorcultmaptip"));
                return tooltips;
            }
        }, 40f, true);
    }

    public static void registerFishing()
    {
        // Rods
        ItemRegistry.registerItem("ancientwoodfishingrod", new AncientWoodFishingRod(), 40, true);

        // Bait
        ItemRegistry.registerItem("myceliumworm", new BaseBaitItem(true, 50), 20, true);

        // Fish
        ItemRegistry.registerItem("inspectorclouseau", (new BaseFishItem(250, Item.Rarity.UNCOMMON)).setItemCategory("materials", "specialfish"), 16, true);
        ItemRegistry.registerItem("runicfish", (new BaseFishItem(250, Item.Rarity.UNCOMMON)).setItemCategory("materials", "specialfish"), 16, true);
        ItemRegistry.registerItem("tinyorca", (new BaseFishItem(250, Item.Rarity.UNCOMMON)).setItemCategory("materials", "specialfish"), 16, true);
        ItemRegistry.registerItem("waterzombie", (new BaseFishItem(250, Item.Rarity.UNCOMMON)).setItemCategory("materials", "specialfish"), 16, true);
        ItemRegistry.registerItem("shadowcreature", (new BaseFishItem(250, Item.Rarity.RARE)).setItemCategory("materials", "specialfish"), 50, true);
    }

    public static void registerBanner()
    {
        ItemRegistry.registerItem("bannerofresilience", new BaseBannerItem(Item.Rarity.COMMON, 480, (m) -> RegistryBuffs.BannerBuffs.RESILIENCEBOOST), 200, true);
        ItemRegistry.registerItem("bannerofbouncing", new BaseBannerItem(Item.Rarity.COMMON, 480, (m) -> RegistryBuffs.BannerBuffs.BOUNCEBOOST), 200, true);
        ItemRegistry.registerItem("bannerofessence", new BaseBannerItem(Item.Rarity.COMMON, 480, (m) -> RegistryBuffs.BannerBuffs.ESSENCEBOOST), 200, true);
        ItemRegistry.registerItem("bannerofstamina", new BaseBannerItem(Item.Rarity.COMMON, 480, (m) -> RegistryBuffs.BannerBuffs.STAMINABOOST), 200, true);
        ItemRegistry.registerItem("bannerofpicking", new BaseBannerItem(Item.Rarity.COMMON, 480, (m) -> RegistryBuffs.BannerBuffs.PICKUPBOOST), 200, true);
        ItemRegistry.registerItem("bannerofdashing", new BaseBannerItem(Item.Rarity.COMMON, 480, (m) -> RegistryBuffs.BannerBuffs.DASHBOOST), 200, true);
        ItemRegistry.registerItem("bannerofmana", new BaseBannerItem(Item.Rarity.COMMON, 480, (m) -> RegistryBuffs.BannerBuffs.MANABOOST), 200, true);
    }

    public static void registerPermanent()
    {
        // Stat increases
        ItemRegistry.registerItem("fusedessences", new TrinketSlotsIncreaseItem(9), 500, true, true);
    }

    public static void registerBags()
    {
        // Inventory
        ItemRegistry.registerItem("shadowhorrorbag", new ShadowHorrorBag(), 50, true);
        // Lootbags
        ItemRegistry.registerItem("sunkenchest", new SunkenChest(), 50, true);
    }

    public static void registerConsumables()
    {
        // Boss Summon
        ItemRegistry.registerItem("shadowhorrorportal", new ShadowHorrorPortal(), 50, true);

        // Potions
        ItemRegistry.registerItem("minionrangepotion", new BaseConsumePotion(100, Item.Rarity.COMMON,"minionrangebuff", 300, "minionrangetip"), 10, true);
        BuffRegistry.registerBuff("minionrangebuff", new BasePotionBuff(new ModifierValue<>(BuffModifiers.SUMMONS_TARGET_RANGE, 0.50f)));
        ItemRegistry.registerItem("minionattackspeedpotion", new BaseConsumePotion(100, Item.Rarity.COMMON,"minionattackspeedbuff", 300, "minionattackspeedtip"), 10, true);
        BuffRegistry.registerBuff("minionattackspeedbuff", new BasePotionBuff(new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.20f)));
        ItemRegistry.registerItem("minionspeedpotion", new BaseConsumePotion(100, Item.Rarity.COMMON,"minionspeedbuff", 300, "minionspeedtip"), 10, true);
        BuffRegistry.registerBuff("minionspeedbuff", new BasePotionBuff(new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.20f)));
        ItemRegistry.registerItem("minioncritchancepotion", new BaseConsumePotion(100, Item.Rarity.COMMON,"minioncritchancebuff", 300, "minioncritchancetip"), 10, true);
        BuffRegistry.registerBuff("minioncritchancebuff", new BasePotionBuff(new ModifierValue<>(BuffModifiers.SUMMON_CRIT_CHANCE, 0.10f)));
        ItemRegistry.registerItem("minioncritpotion", new BaseConsumePotion(100, Item.Rarity.COMMON,"minioncritbuff", 300, "minioncrittip"), 10, true);
        BuffRegistry.registerBuff("minioncritbuff", new BasePotionBuff(new ModifierValue<>(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.50f)));
        ItemRegistry.registerItem("minioncloserangepotion", new BaseConsumePotion(100, Item.Rarity.COMMON,"minioncloserangebuff", 300, "minioncloserangetip"), 10, true);
        BuffRegistry.registerBuff("minioncloserangebuff", new BasePotionBuff(new ModifierValue<>(BuffModifiers.SUMMONS_TARGET_RANGE, -0.80f), new ModifierValue<>(BuffModifiers.SUMMON_ATTACK_SPEED, 0.20f), new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.20f)));
        ItemRegistry.registerItem("minionfarmpotion", new BaseConsumePotion(100, Item.Rarity.COMMON,"minionfarmbuff", 300, "minionfarmtip"), 10, true);
        BuffRegistry.registerBuff("minionfarmbuff", new BasePotionBuff(new ModifierValue<>(BuffModifiers.SPEED, -0.90f), new ModifierValue<>(BuffModifiers.ITEM_PICKUP_RANGE, 50f), new ModifierValue<>(BuffModifiers.MOB_SPAWN_RATE, 0.60f), new ModifierValue<>(BuffModifiers.SUMMONS_SPEED, 0.20f), new ModifierValue<>(BuffModifiers.SUMMONS_TARGET_RANGE, 0.20f)));
        ItemRegistry.registerItem("minionequinoxpotion", new BaseConsumePotion(100, Item.Rarity.COMMON,"minionequinoxbuff", 300, "minionequinoxtip"), 10, true);
        BuffRegistry.registerBuff("minionequinoxbuff", new MinionEquinoxBuff());
        ItemRegistry.registerItem("minionsunflowerpotion", new MinionSunflowerPotion(), 10, true);
        BuffRegistry.registerBuff("minionsunflowerbuff", new BasePotionBuff(new ModifierValue<>(SENTRY_ATTACK_SPEED, 0.20f), new ModifierValue<>(BuffModifiers.STAMINA_CAPACITY, 0.50f), new ModifierValue<>(BuffModifiers.BLINDNESS, 0f).max(0f)));
    }
}