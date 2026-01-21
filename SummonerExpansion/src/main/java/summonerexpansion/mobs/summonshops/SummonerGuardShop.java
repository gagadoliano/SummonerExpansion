package summonerexpansion.mobs.summonshops;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.friendly.human.GuardHumanMob;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = GuardHumanMob.class, arguments = {})
public class SummonerGuardShop
{
    @Advice.OnMethodExit
    static void onExit(@Advice.This GuardHumanMob NPC)
    {
        NPC.shop.addSellingItem("goblinsword",  new SellingShopItem()).setStaticPriceBasedOnHappiness(500, 1000, 50).addKilledAllMobsRequirement("trenchcoatgoblinhelmet", "trenchcoatgoblinchestplate", "trenchcoatgoblinshoes");
        NPC.shop.addSellingItem("duelistdolls",  new SellingShopItem()).setStaticPriceBasedOnHappiness(500, 4000, 100).addQuestTierCompletedRequirement("evilsprotector");
    }
}