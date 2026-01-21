package summonerexpansion.mobs.summonshops;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.friendly.human.humanShop.BuyingShopItem;
import necesse.entity.mobs.friendly.human.humanShop.PirateHumanMob;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = PirateHumanMob.class, arguments = {})
public class SummonerPirateShop
{
    @Advice.OnMethodExit
    static void onExit(@Advice.This PirateHumanMob NPC)
    {
        NPC.shop.addSellingItem("titaniumbar",  new SellingShopItem(50, 5)).setStaticPriceBasedOnHappiness(500, 1000, 50).addKilledMobRequirement("evilwitchbow");
        NPC.shop.addBuyingItem("titaniumbar",  new BuyingShopItem()).setPriceBasedOnHappiness(30, 10, 2).addKilledMobRequirement("evilwitchbow");
    }
}