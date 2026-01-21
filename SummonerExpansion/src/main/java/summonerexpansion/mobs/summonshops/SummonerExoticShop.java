package summonerexpansion.mobs.summonshops;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.friendly.human.humanShop.ExoticMerchantHumanMob;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = ExoticMerchantHumanMob.class, arguments = {})
public class SummonerExoticShop
{
    @Advice.OnMethodExit
    static void onExit(@Advice.This ExoticMerchantHumanMob NPC)
    {
        NPC.shop.addSellingItem("alienparasite",  new SellingShopItem()).setRandomPrice(500, 3000).addRandomAvailableRequirement(0.25F);
        NPC.shop.addSellingItem("cowskull",  new SellingShopItem()).setRandomPrice(500, 3000).addRandomAvailableRequirement(0.25F).addKilledMobRequirement("ancientvulture");
    }
}