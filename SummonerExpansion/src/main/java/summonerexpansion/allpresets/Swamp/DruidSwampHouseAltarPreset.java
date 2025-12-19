package summonerexpansion.allpresets.Swamp;

import necesse.engine.util.GameRandom;
import necesse.level.maps.presets.Preset;
import summonerexpansion.mobs.summonmobs.DruidHumanMob;

import static summonerexpansion.codes.registry.SummonerLoot.woodenIdolPedal;

public class DruidSwampHouseAltarPreset extends Preset
{
    public DruidSwampHouseAltarPreset(GameRandom random)
    {
        super("eNrtVk2P2yAQ_Sv8AA6As9tefOruoaesdlfqocqB2GxCRUwERFFV9b_XQGw-bDfOJqp6WGmCnJn3HsPMxOHp-fHl8bX8deS12ZYYwS3jm60pMYaGC_b1QZff7yHQR7rbbxTV2nohKPDJp41s2JuQUq0cwcPnmJWIP2fCA9gUYDbwsnz-I8SIraBc_2CVcf1CEFCuIPh8v4BAMGq2TFVU7ZmBAC-Ibedht5MNbzaGrgWrD9IFPo0ECASLu3ZZty2utaG80W1aVuNIhahoU9txwKhoHVwIebRuCO4sxztqrveC_rSgRe9r56V1EJKMlk2iQxjFWHcofyR02mZyQZ1ignSZuCO4crivEdEREg0HC4ujorHNgqgvqq9gYI6SMuEhEE0S8ghK0nc9CZpFOFdXkxQ_Ko18C9IyOqmQ8ohy4Axrg9JK95Ss8G44TkvW0BVU0lDDZRMGAQISGe7O50P-a-FCsad_6IlF5I9ZONoiA8T7ZuIkAsQh1PlxtC-JQmjAjde_cMk0N1Ymg7SRf1sv09_XHOuaejmq8yVjd902t6bOEJiVeAbyxX7Op3iOkXdhsom4Rvy2xLP0swmTtLD27-aBVVJdOspFeCtGjx-GJup7-fSSG03Lba1_JRf_ljtaXXfLisf3S3tdU6VRBwYr-7i0d7dvXLX36jcqNPv9B3ZaRVM=");
        addMob("druidhuman", 5, 5, DruidHumanMob.class, (druid) -> druid.setHome(druid.getTileX(), druid.getTileY()));
        addInventory(woodenIdolPedal, random, 3, 1);
        addInventory(woodenIdolPedal, random, 6, 1);
        addMobs(5, 5, false, "mouse");
        addMobs(5, 6, false, "mouse");
        addMobs(5, 7, false, "mouse");
    }
}