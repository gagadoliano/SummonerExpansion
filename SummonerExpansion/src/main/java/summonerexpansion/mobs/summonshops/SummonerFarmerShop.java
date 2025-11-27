package summonerexpansion.mobs.summonshops;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.friendly.human.humanShop.FarmerHumanMob;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = FarmerHumanMob.class, arguments = {})
public class SummonerFarmerShop
{
    @Advice.OnMethodExit
    static void onExit(@Advice.This FarmerHumanMob farmerNPC)
    {
        farmerNPC.shop.addSellingItem("goldpitchfork",  new SellingShopItem()).setStaticPriceBasedOnHappiness(1500, 5000, 1000).addQuestTierCompletedRequirement("swampguardian");
    }
}