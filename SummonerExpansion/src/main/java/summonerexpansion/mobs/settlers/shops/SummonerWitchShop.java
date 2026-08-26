package summonerexpansion.mobs.settlers.shops;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.friendly.human.humanShop.BuyingShopItem;
import necesse.entity.mobs.friendly.human.humanShop.FriendlyWitchHumanMob;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = FriendlyWitchHumanMob.class, arguments = {})
public class SummonerWitchShop
{
    @Advice.OnMethodExit
    static void onExit(@Advice.This FriendlyWitchHumanMob NPC)
    {
        NPC.shop.addSellingItem("strangecookpot",  new SellingShopItem()).setStaticPriceBasedOnHappiness(400, 2000, 200).addKilledEitherMobsRequirement("evilwitchflask", "evilwitchbow", "evilwitchgreatsword", "evilwitch");
        NPC.shop.addSellingItem("overgrowthornssapling",  new SellingShopItem(10, 1)).setStaticPriceBasedOnHappiness(500, 4000, 400).addHasCompletedStoryObjectiveRequirement("defeatpestwarden");
        NPC.shop.addSellingItem("spidernesttile",  new SellingShopItem(100, 50)).setStaticPriceBasedOnHappiness(10, 100, 10).addKilledMobRequirement("vampireminibossmob");
        NPC.shop.addSellingItem("cryptash",  new SellingShopItem(100, 50)).setStaticPriceBasedOnHappiness(10, 100, 10).addKilledMobRequirement("vampireminibossmob");

        NPC.shop.addBuyingItem("swampslime", new BuyingShopItem()).setRandomPrice(12, 46);
    }
}