package summonerexpansion.mobs.summonmobs;

import java.util.function.Supplier;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.util.TicketSystemList;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.level.maps.levelData.settlementData.settler.Settler;
import necesse.level.maps.levelData.settlementData.ServerSettlementData;

public class DruidSettler extends Settler
{
    public DruidSettler()
    {
        super("druidhuman");
    }

    public GameMessage getAcquireTip() {
        return new LocalMessage("settlement", "foundinvillagetip");
    }

    public void addNewRecruitSettler(ServerSettlementData data, boolean isRandomEvent, TicketSystemList<Supplier<HumanMob>> ticketSystem)
    {
        if ((isRandomEvent || !this.doesSettlementHaveThisSettler(data)))
        {
            ticketSystem.addObject(25, this.getNewRecruitMob(data));
        }
    }
}