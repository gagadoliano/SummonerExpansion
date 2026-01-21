package summonerexpansion.mobs.summonshops;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.friendly.human.humanShop.AlchemistHumanMob;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = AlchemistHumanMob.class, arguments = {})
public class SummonerAlchemistShop
{
    @Advice.OnMethodExit
    static void onExit(@Advice.This AlchemistHumanMob NPC)
    {
        NPC.shop.addSellingItem("minionattackspeedpotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(50, 200, 5).addQuestTierCompletedRequirement("evilsprotector");
        NPC.shop.addSellingItem("minioncritchancepotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(50, 200, 5).addQuestTierCompletedRequirement("queenspider");
        NPC.shop.addSellingItem("minioncritpotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(50, 200, 5).addQuestTierCompletedRequirement("queenspider");
        NPC.shop.addSellingItem("minionrangepotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(50, 200, 5).addQuestTierCompletedRequirement("voidwizard");
        NPC.shop.addSellingItem("minionspeedpotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(50, 200, 5).addQuestTierCompletedRequirement("ancientvulture");
        NPC.shop.addSellingItem("minionequinoxpotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(50, 200, 5).addQuestTierCompletedRequirement("chieftain");
        NPC.shop.addSellingItem("minioncloserangepotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(50, 200, 5).addQuestTierCompletedRequirement("piratecaptain");
        NPC.shop.addSellingItem("minionfarmpotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(100, 300, 5).addQuestTierCompletedRequirement("piratecaptain");
    }
}