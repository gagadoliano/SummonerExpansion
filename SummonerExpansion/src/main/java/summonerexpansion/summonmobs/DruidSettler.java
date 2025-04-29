package summonerexpansion.summonmobs;

import java.util.function.Supplier;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.playerStats.PlayerStats;
import necesse.engine.registries.BiomeRegistry;
import necesse.engine.util.TicketSystemList;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.level.maps.biomes.Biome;
import necesse.level.maps.levelData.settlementData.SettlementLevelData;
import necesse.level.maps.levelData.settlementData.settler.Settler;

public class DruidSettler extends Settler
{
    public DruidSettler()
    {
        super("druidhuman");
    }

    public boolean isAvailableForClient(SettlementLevelData settlement, PlayerStats stats)
    {
        return super.isAvailableForClient(settlement, stats) && stats.biomes_visited.stream().filter((e) -> e.getValue() > 0).map((e) -> BiomeRegistry.getBiome(e.getKey())).anyMatch(Biome::hasVillage);
    }

    public GameMessage getAcquireTip() {
        return new LocalMessage("settlement", "foundinvillagetip");
    }

    public void addNewRecruitSettler(SettlementLevelData data, boolean isRandomEvent, TicketSystemList<Supplier<HumanMob>> ticketSystem)
    {
        if (isRandomEvent || !this.doesSettlementHaveThisSettler(data))
        {
            ticketSystem.addObject(10, this.getNewRecruitMob(data));
        }
    }
}