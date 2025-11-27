package summonerexpansion.summonpreset;

import necesse.engine.util.GameRandom;
import necesse.level.maps.presets.Preset;

import static summonerexpansion.codes.summonregistry.SummonerLoot.*;

public class TrainingPlainPreset extends Preset
{
    public TrainingPlainPreset(GameRandom random)
    {
        super("eNq1VMFOwzAM_ZV8QA5pQKIcemI7cBoCJA6oh7Tz2rAsQamnCSH-naRlqCzJtmpCeqkS-9l-sdU8PM6f5s_F504usS0yTluQTYtFxihKBfezrnjlV5R0aDRUVtbrlTLGUpJT8q6E1F1jRdd5btlHOH7eex18oF_5CLHzeIXWY7w8niw_p0bcl-SeIk4iTxAYb1t-ulPp7CU11RvU2E-XUSKkGyi_zSiphLWgKMmuM0eEpqlBKdBYCa3BkTLOb_4M3lM5pwSFXqN1Zqmb5Xaz-djXGCoMcWyPg-OvJfyyH2HsgN7rYzGMc4d1xsZwk1LE0ojShp5EN-yMnEf6kYqNXKpvUVzeqaulCP0ovLmk1qBAafQw3_MRzpJPCf8P8D2OiOGX6bzwjr5pw_O6GH6qOwXCFmi3QHdCqRnUxoYuFJWChK_22wW2YF-kda_2SqgOvr4B-FdAFQ==");
        addInventory(trainingPlainBarrel1, random, 3, 1);
        addInventory(trainingPlainBarrel2, random, 10, 9);
    }
}