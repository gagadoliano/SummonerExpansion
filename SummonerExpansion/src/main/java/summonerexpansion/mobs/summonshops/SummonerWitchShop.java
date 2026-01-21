package summonerexpansion.mobs.summonshops;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.friendly.human.humanShop.FriendlyWitchHumanMob;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = FriendlyWitchHumanMob.class, arguments = {})
public class SummonerWitchShop
{
    @Advice.OnMethodExit
    static void onExit(@Advice.This FriendlyWitchHumanMob NPC)
    {
        NPC.shop.addSellingItem("strangecookpot",  new SellingShopItem()).setStaticPriceBasedOnHappiness(400, 2000, 10).addKilledEitherMobsRequirement("evilwitchflask", "evilwitchbow", "evilwitchgreatsword", "evilwitch");
        NPC.shop.addSellingItem("overgrowthornssapling",  new SellingShopItem(10, 1)).setStaticPriceBasedOnHappiness(500, 4000, 10).addKilledMobRequirement("pestwarden");
    }
}