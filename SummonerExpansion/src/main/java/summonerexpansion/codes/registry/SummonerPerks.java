package summonerexpansion.codes.registry;

import necesse.engine.incursionPerkTree.IncursionPerk;
import summonerexpansion.modperks.*;

import static necesse.engine.registries.IncursionPerksRegistry.CAVELINGS_CAN_SPAWN;
import static necesse.engine.registries.IncursionPerksRegistry.registerPerk;

public class SummonerPerks
{
    public static IncursionPerk SUMMONER_WEAPONS_REWARD;

    public static void registerSummonPerks()
    {
        SUMMONER_WEAPONS_REWARD = registerPerk("horrorspiritreward", new HorrorSpiritRewardPerk(3, 75, 6, CAVELINGS_CAN_SPAWN));
    }
}