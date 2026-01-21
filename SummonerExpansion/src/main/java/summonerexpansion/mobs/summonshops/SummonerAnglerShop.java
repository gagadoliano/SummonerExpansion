package summonerexpansion.mobs.summonshops;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.friendly.human.humanShop.AnglerHumanMob;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = AnglerHumanMob.class, arguments = {})
public class SummonerAnglerShop
{
    @Advice.OnMethodExit
    static void onExit(@Advice.This AnglerHumanMob NPC)
    {
        NPC.shop.addSellingItem("myceliumworm",  new SellingShopItem(25, 5)).setStaticPriceBasedOnHappiness(20, 50, 5).addQuestTierCompletedRequirement("pestwarden");
        NPC.shop.addSellingItem("rainsummonhat",  new SellingShopItem()).setStaticPriceBasedOnHappiness(200, 2000, 10).addQuestTierCompletedRequirement("voidwizard");
        NPC.shop.addSellingItem("rainsummoncoat",  new SellingShopItem()).setStaticPriceBasedOnHappiness(200, 2000, 10).addQuestTierCompletedRequirement("voidwizard");
        NPC.shop.addSellingItem("rainsummonboots",  new SellingShopItem()).setStaticPriceBasedOnHappiness(200, 2000, 10).addQuestTierCompletedRequirement("voidwizard");
    }
}