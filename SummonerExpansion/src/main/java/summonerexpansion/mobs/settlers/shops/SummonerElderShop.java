package summonerexpansion.mobs.settlers.shops;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.friendly.human.ElderHumanMob;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import net.bytebuddy.asm.Advice;
import summonerexpansion.codes.registries.RegistryChallenges;

@ModConstructorPatch(target = ElderHumanMob.class, arguments = {})
public class SummonerElderShop
{
    @Advice.OnMethodExit
    static void onExit(@Advice.This ElderHumanMob NPC)
    {
        NPC.shop.addSellingItem("bannerofwater",  new SellingShopItem()).setRandomPrice(2500, 3000).addJournalChallengeCompleteRequirement(RegistryChallenges.FOREST_SURFACE_CHALLENGES_ID);
        NPC.shop.addSellingItem("giantbeet",  new SellingShopItem()).setRandomPrice(500, 2000).addJournalChallengeCompleteRequirement(RegistryChallenges.FOREST_CAVE_CHALLENGES_ID);
        NPC.shop.addSellingItem("giantonion",  new SellingShopItem()).setRandomPrice(500, 2000).addJournalChallengeCompleteRequirement(RegistryChallenges.FOREST_DEEP_CAVE_CHALLENGES_ID);
        NPC.shop.addSellingItem("xmastreescepter",  new SellingShopItem()).setRandomPrice(500, 2000).addJournalChallengeCompleteRequirement(RegistryChallenges.SNOW_SURFACE_CHALLENGES_ID);
        NPC.shop.addSellingItem("giantpotato",  new SellingShopItem()).setRandomPrice(500, 2000).addJournalChallengeCompleteRequirement(RegistryChallenges.SNOW_CAVE_CHALLENGES_ID);
        NPC.shop.addSellingItem("giantpumpkin",  new SellingShopItem()).setRandomPrice(500, 2000).addJournalChallengeCompleteRequirement(RegistryChallenges.SNOW_DEEP_CAVE_CHALLENGES_ID);
        NPC.shop.addSellingItem("glyphtrapheal",  new SellingShopItem()).setRandomPrice(500, 2000).addJournalChallengeCompleteRequirement(RegistryChallenges.DUNGEON_CHALLENGES_ID);
        NPC.shop.addSellingItem("silvergoblet",  new SellingShopItem()).setRandomPrice(500, 2000).addJournalChallengeCompleteRequirement(RegistryChallenges.PLAINS_SURFACE_CHALLENGES_ID);
        NPC.shop.addSellingItem("doomshroomshield",  new SellingShopItem()).setRandomPrice(500, 2000).addJournalChallengeCompleteRequirement(RegistryChallenges.SWAMP_CAVE_CHALLENGES_ID);
        NPC.shop.addSellingItem("jellyfishbowl",  new SellingShopItem()).setRandomPrice(500, 2000).addJournalChallengeCompleteRequirement(RegistryChallenges.SWAMP_DEEP_CAVE_CHALLENGES_ID);
        NPC.shop.addSellingItem("giantcarrot",  new SellingShopItem()).setRandomPrice(500, 2000).addJournalChallengeCompleteRequirement(RegistryChallenges.DESERT_CAVE_CHALLENGES_ID);
    }
}