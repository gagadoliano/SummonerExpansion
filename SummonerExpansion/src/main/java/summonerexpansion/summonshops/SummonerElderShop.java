package summonerexpansion.summonshops;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.friendly.human.ElderHumanMob;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import net.bytebuddy.asm.Advice;
import summonerexpansion.summonothers.SummonerChallenges;

@ModConstructorPatch(target = ElderHumanMob.class, arguments = {})
public class SummonerElderShop
{
    @Advice.OnMethodExit
    static void onExit(@Advice.This ElderHumanMob elderMan)
    {
        elderMan.shop.addSellingItem("bannerofwater",  new SellingShopItem()).setRandomPrice(2500, 3000).addJournalChallengeCompleteRequirement(SummonerChallenges.SUMMON_FOREST_SURFACE_CHALLENGES_ID);
        elderMan.shop.addSellingItem("giantbeet",  new SellingShopItem()).setRandomPrice(500, 2000).addJournalChallengeCompleteRequirement(SummonerChallenges.SUMMON_FOREST_CAVES_CHALLENGES_ID);
        elderMan.shop.addSellingItem("xmastreescepter",  new SellingShopItem()).setRandomPrice(500, 2000).addJournalChallengeCompleteRequirement(SummonerChallenges.SUMMON_SNOW_SURFACE_CHALLENGES_ID);
    }
}