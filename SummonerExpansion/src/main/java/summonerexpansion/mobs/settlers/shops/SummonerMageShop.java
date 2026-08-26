package summonerexpansion.mobs.settlers.shops;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.friendly.human.humanShop.MageHumanMob;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = MageHumanMob.class, arguments = {})
public class SummonerMageShop
{
    @Advice.OnMethodExit
    static void onExit(@Advice.This MageHumanMob NPC)
    {
        NPC.shop.addSellingItem("enchantedgoblet",  new SellingShopItem()).setStaticPriceBasedOnHappiness(20, 400, 5).addRandomAvailableRequirement(0.25F).addKilledMobRequirement("voidwizard");
        //Trinkets
        NPC.shop.addSellingItem("apprendicestatue",  new SellingShopItem()).setStaticPriceBasedOnHappiness(1000, 4000, 100).addRandomAvailableRequirement(0.25F).addKilledMobRequirement("voidwizard");
        NPC.shop.addSellingItem("overchargedvoidshards",  new SellingShopItem()).setStaticPriceBasedOnHappiness(400, 2000, 50).addRandomAvailableRequirement(0.25F).addKilledMobRequirement("voidwizard");
        NPC.shop.addSellingItem("fishianeggs",  new SellingShopItem()).setStaticPriceBasedOnHappiness(600, 2000, 50).addRandomAvailableRequirement(0.25F).addKilledMobRequirement("fishianhookwarrior");
        NPC.shop.addSellingItem("lightningamulet",  new SellingShopItem()).setStaticPriceBasedOnHappiness(300, 2000, 50).addRandomAvailableRequirement(0.25F).addKilledMobRequirement("voidwizard");
        //Weapons
        NPC.shop.addSellingItem("icewizardstaff",  new SellingShopItem()).setStaticPriceBasedOnHappiness(700, 2000, 50).addRandomAvailableRequirement(0.25F).addKilledMobRequirement("queenspider");
    }
}