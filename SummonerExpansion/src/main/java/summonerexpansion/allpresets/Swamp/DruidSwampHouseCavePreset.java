package summonerexpansion.allpresets.Swamp;

import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.level.maps.presets.Preset;
import necesse.level.maps.presets.PresetUtils;
import summonerexpansion.mobs.summonmobs.DruidHumanMob;

public class DruidSwampHouseCavePreset extends Preset
{
    public DruidSwampHouseCavePreset(GameRandom random)
    {
        super("eNrtV81u2zAMfhU_gA7yX5Iectp62KlDN2CHIQfZZmyviuVJctNg2LtPP4mrxLHjJO2wFQUYwqb4faIoUnI-399-uf06_7UuM1nM_SkqoMwLOfcnSJYUPn0U8-9hiDyxJqtaSFZBTWShh5A32ZpzToSwpiny2CPwnLN1tT-2MHyKbWKA72JF57bvdyHsFPwa7DDDpQu-IFdnJ_k_rY_pSM8FYskPSKVpWIw8UnLkzfTCE84eoMqp6sOsFDUlG-T5-y29JpRqbzUZPNXAyxVUklDOmFQRaDaaFrDaSJLoJvfDyEVnjJm5ZsiTIKRsEhDKCd_EWy_O0gdlCIK9A0NBogB5KXmEnLL1UgWovKKJsq2YEBshoa7LKjdzCD2iYqaE59AdRl4cKvJ1SRVTBpoo1quxhhXLGgXcRh_rQO1AWpg0xZG_sySMPYgC6FJbg9aPJGUFUtvC1hMy_R4578o_uFEOKWNURZawJ-UR-88sVQaUJJxoP50LYvISxSrDQjbLJWQr1ggV48xXMXLITDj69UbtI21AMWygjVMNBGr-n42iVyM14Wmhd25XC6oSbNKtxka2FrM7J3QHdfB8BtXgFIeErr6GvH9Fr0LuaDzgoFtvtBrJ3AJMKZvKtU-6-kwn6C42D0PkPcym6u3em0rEjjic4-mibYXb0sdXMz4r01cHhIMrHi6E57zqQ8OcMbsITacPhonH0xuFx20_PqtDLFd0KqOdxht_WJhzeSAH-FjUx5vd0eYeGHYZ1n3TOsT4ivjw2YHiBeJMElmyyl7R10jYI-f696GsPXCkS3XU3--XA5Tr385y8HyACh2f1uKGEQxC_P2FnIRY6Sbn6HKCDjDc2bHj3w046EzXl7E-YKuHayDokZElF5xfpS8GsX8e7-xXzQcKhM8lbwDpD9ePkDLeHTJfe-7Y1T33hmW2vX_M9-RF-OkFoCh49WXN3nf3elk4zXT_YlfYGxb_72L9f3g579I2UaovpztZAP9WchDzJaECfv8Bkzstxw==");
        addMob("druidhuman", 6, 6, DruidHumanMob.class, (druid) -> {
            druid.canDespawn = false;
            druid.setHome(druid.getTileX(), druid.getTileY());
            try
            {
                druid.setTrapped();
            }
            catch (Exception var2)
            {
                druid.remove();
            }
        });
        addMobs(7, 6, false, "crawlingzombie");
        addMobs(8, 6, false, "crawlingzombie");
        addMobs(9, 6, false, "crawlingzombie");
        addInventory(LootTablePresets.hunterCookedFoodLootTable, random, 11, 9);
        addInventory(LootTablePresets.alchemistChest, random, 6, 8);
        addInventory(LootTablePresets.swampCrate, random, 6, 5);
    }
}