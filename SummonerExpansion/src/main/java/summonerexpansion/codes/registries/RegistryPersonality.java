package summonerexpansion.codes.registries;

import necesse.engine.registries.SettlerPersonalityRegistry;
import necesse.entity.mobs.friendly.human.humanShop.HumanShop;
import necesse.level.maps.levelData.settlementData.settler.personalities.SimplePersonalityFilter;
import summonerexpansion.codes.personalities.*;

public class RegistryPersonality
{
    public static void registerPersonalities()
    {
        //Classes
        SettlerPersonalityRegistry.registerSettlerPersonality("pyrotechnician", PyrotechnicianSettlerPersonality.class, (new SimplePersonalityFilter(100)).filterPersonalityStringID("warrior", "ranger", "magician", "summoner", "shamanic", "lightwaver", "holypriest", "hydromancer", "slimeaddict"), true);
        SettlerPersonalityRegistry.registerSettlerPersonality("shamanic", ShamanicSettlerPersonality.class, (new SimplePersonalityFilter(100)).filterPersonalityStringID("warrior", "ranger", "magician", "summoner", "pyrotechnician", "lightwaver", "holypriest", "hydromancer", "slimeaddict"), true);
        SettlerPersonalityRegistry.registerSettlerPersonality("lightwaver", LightwaverSettlerPersonality.class, (new SimplePersonalityFilter(100)).filterPersonalityStringID("warrior", "ranger", "magician", "summoner", "pyrotechnician", "shamanic", "holypriest", "hydromancer", "slimeaddict"), true);
        SettlerPersonalityRegistry.registerSettlerPersonality("holypriest", HolyPriestSettlerPersonality.class, (new SimplePersonalityFilter(100)).filterPersonalityStringID("warrior", "ranger", "magician", "summoner", "pyrotechnician", "shamanic", "lightwaver", "holypriest", "hydromancer", "slimeaddict"), true);
        SettlerPersonalityRegistry.registerSettlerPersonality("hydromancer", HydromancerSettlerPersonality.class, (new SimplePersonalityFilter(100)).filterPersonalityStringID("warrior", "ranger", "magician", "summoner", "pyrotechnician", "shamanic", "lightwaver", "holypriest", "slimeaddict"), true);
        SettlerPersonalityRegistry.registerSettlerPersonality("slimeaddict", SlimeAddictSettlerPersonality.class, (new SimplePersonalityFilter(100)).filterPersonalityStringID("warrior", "ranger", "magician", "summoner", "pyrotechnician", "shamanic", "lightwaver", "holypriest", "hydromancer"), true);
        SettlerPersonalityRegistry.registerSettlerPersonality("druidic", DruidicSettlerPersonality.class, (new SimplePersonalityFilter(100)).makeSettlerStringIDsWhitelist().filterSettlerStringID("druid"), true);

        //Shops
        //SettlerPersonalityRegistry.registerSettlerPersonality("druidpotionseller", DruidPotionSellerSettlerPersonality.class, (new SimplePersonalityFilter(100)).makeSettlerStringIDsWhitelist().filterSettlerStringID("druid").addHumanMobFilter((mob) -> mob instanceof HumanShop), true);









    }
}