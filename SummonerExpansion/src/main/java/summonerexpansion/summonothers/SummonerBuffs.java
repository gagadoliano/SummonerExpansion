package summonerexpansion.summonothers;

import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import summonerexpansion.summonbannerbuffs.*;

public class SummonerBuffs
{
    public static class SummonerBanners
    {
        public static Buff RESILIENCEBOOST;
        public static Buff PROJSPEEDBOOST;
        public static Buff MOVEMENTBOOST;
        public static Buff STAMINABOOST;
        public static Buff ESSENCEBOOST;
        public static Buff BOUNCEBOOST;
        public static Buff PICKUPBOOST;
        public static Buff MINERBOOST;
        public static Buff DASHBOOST;
        public static Buff MANABOOST;

        public SummonerBanners() {
        }
    }

    public static void registerSummonBuffs()
    {
        BuffRegistry.registerBuff("bannerofresilience", SummonerBanners.RESILIENCEBOOST = new ResilienceBannerBuff());
        BuffRegistry.registerBuff("bannerofbouncing", SummonerBanners.BOUNCEBOOST = new BouncingBannerBuff());
        BuffRegistry.registerBuff("bannerofessence", SummonerBanners.ESSENCEBOOST = new EssenceBannerBuff());
        BuffRegistry.registerBuff("bannerofstamina", SummonerBanners.STAMINABOOST = new StaminaBannerBuff());
        BuffRegistry.registerBuff("bannerofpicking", SummonerBanners.PICKUPBOOST = new PickingBannerBuff());
        BuffRegistry.registerBuff("bannerofdashing", SummonerBanners.DASHBOOST = new DashingBannerBuff());
        BuffRegistry.registerBuff("bannerofmana", SummonerBanners.MANABOOST = new ManaBannerBuff());

    }
}