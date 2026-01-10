package summonerexpansion.allpresets.Plains;

import necesse.engine.util.GameRandom;
import necesse.level.maps.presets.Preset;

import static summonerexpansion.codes.registry.SummonerLoot.*;

public class BeehiveAreaPreset extends Preset
{
    public BeehiveAreaPreset(GameRandom random)
    {
        super("eNrtlkFPwyAUx79KPwAHWs1iD5zcDp5mpokH0wOtbwNDxgJPdzB-d6Fkc6tsoqvZlpi817S8__8XGniE28nobnTP3pbyCQXLB0SAnAlkeUFQKrgZWvZYliSrAYR8hanS2pCsuCLZQnE5tzPDrfXKqtU7ta9tpnfHsquL5S5vCivVG2P81Ntl_NZ_CnnM_-9r_fraQ6mcT11FdP0MDbaNQ0nGpWuX_LKk6xZqBFh0Y3SQr8fcZ3Gx3VMrkMOEGl2JaCxCaVPQce3xRlFxZjvpFO8-zXcQ-veELVq7Or1Hj5M8BBXdEoku_6yI0chR6nnY0OcSxf9MTi3CFWEcjrVrBdwwNC9AllypITTafC0hrxXsqDX-dYwCzIM07uYx5crC-wf6ULaX");
        addInventory(beehiveChest, random, 7, 6);
        addMobs(4, 4, false, "honeybeeguardmob");
        addMobs(4, 5, false, "honeybeeguardmob");
        addMobs(5, 5, false, "honeybeeguardmob");
        addMobs(5, 6, false, "honeybeeguardmob");
    }
}