package summonerexpansion.allpresets.Swamp;

import necesse.engine.util.GameRandom;
import necesse.level.maps.presets.Preset;
import summonerexpansion.mobs.summonmobs.DruidHumanMob;

import static summonerexpansion.codes.registry.SummonerLoot.*;

public class DruidSwampHouseSidePreset extends Preset
{
    public DruidSwampHouseSidePreset(GameRandom random)
    {
        super("eNrVVcGO2yAQ_RV_AAeM7Wx68Km7h5622q3UQ-UDticxFTEREFlV1X8vYIPAbrtJtVpppReUPB4z48dk_Pnp4fnhS_1zYr0e6hyjAdhx0PUeacbh072qv-1QpiZ6Oh8lVcqyKCvyhVNajHDgQsjGHZjl18CGiD8vbKeyv-3epr6tov9Bg0T7HTrtfMQoo0yirCpLlE2MczG10BOU7XeG4ED1ALKj8gzaij54US9BKTAHc1x4bqKcG1GVe6KjYw-ctpJaXUjQm5uxOhIIps6c_jAiQpKLNUxZmRKVvhwO0J_ERYENtQ8p2diLyco8oyVYyc7_5uLYwtgNhrtbc6aAqgjsSfQXTqWmre2mqghJumGxqIgs8i7OHs6FOy-WZR8Wz2GHtdLlcVUsS3Jmo7bm4giOiPOW6X4MtxnHIutYZFVvJLZXj5c-cU5sHqx8yQCncA-0nNjNlxI4Tyw2NUgKTTUT42zyDBOIOBSbB7Q5ot0gINGKUzJP46xihmgkOkUiPmawT0rSXGSDPE2Rb7_Pg-sx6rBr4P6zqwW_OV4192zE0x_64N8gm5t7S7xa3gbZoXoPnZBLM3w0M1nWWl4AuUkV713tDvaD9d2jiVy4vUtwOiferwmd7YpH-67-ysybuT5QruDXbxj78Fc=");
        addMob("druidhuman", 4, 4, DruidHumanMob.class, (druid) -> druid.setHome(druid.getTileX(), druid.getTileY()));
        addInventory(woodenIdolPedal, random, 2, 4);
        addInventory(woodenIdolPedal, random, 7, 4);
    }
}