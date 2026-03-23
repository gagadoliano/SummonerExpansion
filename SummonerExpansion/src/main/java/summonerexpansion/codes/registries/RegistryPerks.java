package summonerexpansion.codes.registries;

import necesse.engine.incursionPerkTree.IncursionPerk;
import summonerexpansion.codes.perks.HorrorSpiritRewardPerk;

import static necesse.engine.registries.IncursionPerksRegistry.CAVELINGS_CAN_SPAWN;
import static necesse.engine.registries.IncursionPerksRegistry.registerPerk;

public class RegistryPerks
{
    public static IncursionPerk SUMMONER_WEAPONS_REWARD;

    public static void registerPerks()
    {
        SUMMONER_WEAPONS_REWARD = registerPerk("horrorspiritreward", new HorrorSpiritRewardPerk(3, 75, 6, CAVELINGS_CAN_SPAWN));
    }
}