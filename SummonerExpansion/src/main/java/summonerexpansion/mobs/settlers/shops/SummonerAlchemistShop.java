package summonerexpansion.mobs.settlers.shops;

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
        NPC.shop.addSellingItem("minionattackspeedpotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(50, 200, 5).addHasCompletedStoryObjectiveRequirement("defeatevilsprotector");
        NPC.shop.addSellingItem("minioncritchancepotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(50, 200, 5).addHasCompletedStoryObjectiveRequirement("defeatqueenspider");
        NPC.shop.addSellingItem("minioncritpotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(50, 200, 5).addHasCompletedStoryObjectiveRequirement("defeatqueenspider");
        NPC.shop.addSellingItem("minionrangepotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(50, 200, 5).addHasCompletedStoryObjectiveRequirement("defeatvoidwizard");
        NPC.shop.addSellingItem("swampslimepotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(50, 200, 5).addHasCompletedStoryObjectiveRequirement("defeatswampguardian");
        NPC.shop.addSellingItem("minionspeedpotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(50, 200, 5).addHasCompletedStoryObjectiveRequirement("defeatancientvulture");
        NPC.shop.addSellingItem("minionequinoxpotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(50, 200, 5).addHasCompletedStoryObjectiveRequirement("defeatchieftain");
        NPC.shop.addSellingItem("minioncloserangepotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(50, 200, 5).addHasCompletedStoryObjectiveRequirement("defeatpiratecaptain");
        NPC.shop.addSellingItem("minionfarmpotion",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(100, 300, 5).addHasCompletedStoryObjectiveRequirement("defeatpiratecaptain");
    }
}