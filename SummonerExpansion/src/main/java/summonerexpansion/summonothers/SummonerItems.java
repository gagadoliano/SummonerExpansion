package summonerexpansion.summonothers;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.inventory.item.Item;
import necesse.inventory.item.miscItem.BannerItem;
import necesse.inventory.item.placeableItem.consumableItem.TrinketSlotsIncreaseItem;
import necesse.inventory.item.placeableItem.consumableItem.food.FoodConsumableItem;
import necesse.level.maps.levelData.settlementData.settler.Settler;
import summonerexpansion.summonbannerbuffs.*;
import summonerexpansion.summonbuffs.*;
import summonerexpansion.summonmaterials.*;
import summonerexpansion.summonpotions.*;
import summonerexpansion.summonmisc.*;

public class SummonerItems
{
    public static void registerSummonItems()
    {
        // Materials
        ItemRegistry.registerItem("purehorror", new PureHorror(), 50, true);

        // Boss Summon
        ItemRegistry.registerItem("shadowhorrorportal", new ShadowHorrorPortal(), 50, true);

        // Bags
        ItemRegistry.registerItem("shadowhorrorbag", new ShadowHorrorBag(), 10, true);

        // Stat increases
        ItemRegistry.registerItem("fusedessences", new TrinketSlotsIncreaseItem(8), 500.0F, true, true);

        // Foods
        ItemRegistry.registerItem("pigbanquet", (new FoodConsumableItem(250, Item.Rarity.UNCOMMON, Settler.FOOD_FINE, 25, 1200, new ModifierValue<>(BuffModifiers.SPEED, -0.20F), new ModifierValue<>(BuffModifiers.ARMOR, 0.10F), new ModifierValue<>(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.10F))).spoilDuration(500), 30F, true);
        ItemRegistry.registerItem("caveglowjam", (new FoodConsumableItem(250, Item.Rarity.UNCOMMON, Settler.FOOD_FINE, 20, 1200, new ModifierValue<>(BuffModifiers.EMITS_LIGHT, true), new ModifierValue<>(BuffModifiers.SUMMON_DAMAGE, 0.10F))).spoilDuration(500), 20F, true);
        ItemRegistry.registerItem("rottenbread", (new FoodConsumableItem(250, Item.Rarity.NORMAL, Settler.FOOD_SIMPLE, 10, 1200, new ModifierValue<>(BuffModifiers.MAX_HEALTH, -0.20F), new ModifierValue<>(BuffModifiers.MAX_SUMMONS, 1))).spoilDuration(500), 0F, true);
        ItemRegistry.registerItem("berrytrio", (new FoodConsumableItem(250, Item.Rarity.NORMAL, Settler.FOOD_SIMPLE, 10, 1200, new ModifierValue<>(BuffModifiers.SUMMON_CRIT_CHANCE, 0.05F), new ModifierValue<>(BuffModifiers.SUMMON_CRIT_DAMAGE, 0.05F), new ModifierValue(BuffModifiers.SUMMON_ATTACK_SPEED, 0.05F))).spoilDuration(500), 10F, true);

        // Banners
        ItemRegistry.registerItem("bannerofresilience", new BannerItem(Item.Rarity.COMMON, 480, (m) -> SummonerBuffs.SummonerBanners.RESILIENCEBOOST), 200.0F, true);
        BuffRegistry.registerBuff("resiliencebannerbuff", new ResilienceBannerBuff());
        ItemRegistry.registerItem("bannerofbouncing", new BannerItem(Item.Rarity.COMMON, 480, (m) -> SummonerBuffs.SummonerBanners.BOUNCEBOOST), 200.0F, true);
        BuffRegistry.registerBuff("bouncingbannerbuff", new BouncingBannerBuff());
        ItemRegistry.registerItem("bannerofessence", new BannerItem(Item.Rarity.COMMON, 480, (m) -> SummonerBuffs.SummonerBanners.ESSENCEBOOST), 200.0F, true);
        BuffRegistry.registerBuff("essencebannerbuff", new EssenceBannerBuff());
        ItemRegistry.registerItem("bannerofstamina", new BannerItem(Item.Rarity.COMMON, 480, (m) -> SummonerBuffs.SummonerBanners.STAMINABOOST), 200.0F, true);
        BuffRegistry.registerBuff("staminabannerbuff", new StaminaBannerBuff());
        ItemRegistry.registerItem("bannerofpicking", new BannerItem(Item.Rarity.COMMON, 480, (m) -> SummonerBuffs.SummonerBanners.PICKUPBOOST), 200.0F, true);
        BuffRegistry.registerBuff("pickingbannerbuff", new PickingBannerBuff());
        ItemRegistry.registerItem("bannerofdashing", new BannerItem(Item.Rarity.COMMON, 480, (m) -> SummonerBuffs.SummonerBanners.DASHBOOST), 200.0F, true);
        BuffRegistry.registerBuff("dashingbannerbuff", new DashingBannerBuff());
        ItemRegistry.registerItem("bannerofmana", new BannerItem(Item.Rarity.COMMON, 480, (m) -> SummonerBuffs.SummonerBanners.MANABOOST), 200.0F, true);
        BuffRegistry.registerBuff("manabannerbuff", new ManaBannerBuff());

        // Potions
        ItemRegistry.registerItem("minionattackspeedpotion", new MinionAttackSpeedPotion(), 10, true);
        BuffRegistry.registerBuff("minionattackspeedbuff", new MinionAttackSpeedBuff());
        ItemRegistry.registerItem("minioncloserangepotion", new MinionCloseRangePotion(), 10, true);
        BuffRegistry.registerBuff("minioncloserangebuff", new MinionCloseRangeBuff());
        ItemRegistry.registerItem("minioncritchancepotion", new MinionCritChancePotion(), 10, true);
        BuffRegistry.registerBuff("minioncritchancebuff", new MinionCritChanceBuff());
        ItemRegistry.registerItem("minionrangepotion", new MinionRangePotion(), 10, true);
        BuffRegistry.registerBuff("minionrangebuff", new MinionRangeBuff());
        ItemRegistry.registerItem("minionspeedpotion", new MinionSpeedPotion(), 10, true);
        BuffRegistry.registerBuff("minionspeedbuff", new MinionSpeedBuff());
        ItemRegistry.registerItem("minioncritpotion", new MinionCritPotion(), 10, true);
        BuffRegistry.registerBuff("minioncritbuff", new MinionCritBuff());
        ItemRegistry.registerItem("minionfarmpotion", new MinionFarmPotion(), 10, true);
        BuffRegistry.registerBuff("minionfarmbuff", new MinionFarmBuff());
    }
}