package summonerexpansion.codes.registries;

import necesse.entity.mobs.hostile.*;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;

public class RegistryDropLoot
{
    public static void registerLoot()
    {
        FrostSentryMob.lootTable.items.add(
                new ChanceLootItem(0.15F, "frostsentrycore", 1)
        );

        EnchantedZombieMob.lootTable.items.add(
                new ChanceLootItem(0.05F, "spoiledfood", 4)
        );

        EnchantedZombieArcherMob.lootTable.items.add(
                new ChanceLootItem(0.05F, "spoiledfood", 4)
        );

        EnchantedCrawlingZombieMob.lootTable.items.add(
                new ChanceLootItem(0.05F, "spoiledfood", 4)
        );
    }
}
