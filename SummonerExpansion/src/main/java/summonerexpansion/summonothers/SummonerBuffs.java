package summonerexpansion.summonothers;

import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.buffs.staticBuffs.ShownItemCooldownBuff;
import summonerexpansion.summonbannerbuffs.*;
import summonerexpansion.summonbuffs.*;
import summonerexpansion.summondebuffs.*;

public class SummonerBuffs
{
    public static class SummonerBanners
    {
        public static Buff WATERBANNERBOOST;
        public static Buff RESILIENCEBOOST;
        public static Buff STAMINABOOST;
        public static Buff ESSENCEBOOST;
        public static Buff BOUNCEBOOST;
        public static Buff PICKUPBOOST;
        public static Buff DASHBOOST;
        public static Buff MANABOOST;

        public SummonerBanners() {
        }
    }

    public static void registerSummonBuffs()
    {
        // Banners
        BuffRegistry.registerBuff("bannerofresilience", SummonerBanners.RESILIENCEBOOST = new ResilienceBannerBuff());
        BuffRegistry.registerBuff("bannerofbouncing", SummonerBanners.BOUNCEBOOST = new BouncingBannerBuff());
        BuffRegistry.registerBuff("bannerofwater", SummonerBanners.WATERBANNERBOOST = new WaterBannerBuff());
        BuffRegistry.registerBuff("bannerofessence", SummonerBanners.ESSENCEBOOST = new EssenceBannerBuff());
        BuffRegistry.registerBuff("bannerofstamina", SummonerBanners.STAMINABOOST = new StaminaBannerBuff());
        BuffRegistry.registerBuff("bannerofpicking", SummonerBanners.PICKUPBOOST = new PickingBannerBuff());
        BuffRegistry.registerBuff("bannerofdashing", SummonerBanners.DASHBOOST = new DashingBannerBuff());
        BuffRegistry.registerBuff("bannerofmana", SummonerBanners.MANABOOST = new ManaBannerBuff());
        BuffRegistry.registerBuff("waterbannerbuff", new WaterBannerBuff());

        // Minion buffs
        BuffRegistry.registerBuff("iceblossombufft1", new IceBlossomBuffT1());
        BuffRegistry.registerBuff("sunflowerbufft1", new SunflowerBuffT1());
        BuffRegistry.registerBuff("runicshieldbuff", new RunicShieldBuff());
        BuffRegistry.registerBuff("firemonebufft1", new FiremoneBuffT1());
        BuffRegistry.registerBuff("iceblossombuff", new IceBlossomBuff());
        BuffRegistry.registerBuff("sunflowerbuff", new SunflowerBuff());
        BuffRegistry.registerBuff("woodtoolbuff", new WoodToolBuff());
        BuffRegistry.registerBuff("firemonebuff", new FiremoneBuff());
        BuffRegistry.registerBuff("mushroombuff", new MushroomBuff());
        BuffRegistry.registerBuff("xmastreebuff", new XmasTreeBuff());
        BuffRegistry.registerBuff("honeybuff", new HoneyBuff());

        // Minion debuffs
        BuffRegistry.registerBuff("lamptungstendebuff", new LampTungstenDebuff());
        BuffRegistry.registerBuff("lampdungeondebuff", new LampDungeonDebuff());
        BuffRegistry.registerBuff("lampcastledebuff", new LampCastleDebuff());
        BuffRegistry.registerBuff("enchanteddebuff", new EnchantedDebuff());
        BuffRegistry.registerBuff("lampgolddebuff", new LampGoldDebuff());
        BuffRegistry.registerBuff("mushroomdebuff", new MushroomDebuff());
        BuffRegistry.registerBuff("mosquitodebuff", new MosquitoDebuff());
        BuffRegistry.registerBuff("bleedingdebuff", new BleedingDebuff());
        BuffRegistry.registerBuff("honeydebuff", new HoneyDebuff());

        // Melee buffs
        BuffRegistry.registerBuff("horrorglaivecooldowndebuff", new ShownItemCooldownBuff(1, true, "items/horrorglaive"));
        BuffRegistry.registerBuff("goblincooldowndebuff", new ShownItemCooldownBuff(1, true, "items/goblinsword"));
        BuffRegistry.registerBuff("horrorswordstack", new HorrorSwordStackBuff());

        // Others
        BuffRegistry.registerBuff("summonedbeetminionbuff", new SummonedBeetMinionBuff());
    }
}