package summonerexpansion.biomes.presets.Desert;

import necesse.engine.util.GameRandom;
import necesse.level.maps.presets.Preset;

import static summonerexpansion.codes.registries.RegistryChestLoot.sandLibraryBookcase;

public class SandLibraryPreset extends Preset
{
    public SandLibraryPreset(GameRandom random)
    {
        super("eNrtXVtvozgU_iv5ATyAaVrngaftaDQPq45mV9pdjfrgEDewIcAYZ6JV1f--tsmdSxzCfU71HRqMsT-fc2x87JR-_fbpj09_Ou9bf8E95wEbHvWXHncQNrgf0C_PifN9ioxJQsKFTDAmU5yeJTwK6Zz57uotiCImLsxOLsSEezL_qypmV0gjMjsRNBDBO5nlyP5aXzljTf64B23BV0SXf9ttwZpShX-T7cA3yj3862wHrih18L-3Hbgn_HHL3Jvgj1vk3hR_3BL3JvnjFrg3zR-PkD8eGH88Mv7g_93zH_r4OYbn1xjmD2OYv41h_jyG-GUs8eNY4vexrJ-Maf2q0_XDVyOa_0tdrtZTTWNCfGZMLHNmpUumLvlJWeSuVBo6T0sz2ueJyZoEgbgwE6wXlMaHddetSkemeZG-UEu0-OHJmETBIg4Ip-J0Kuqfs2hFw0OKrN1fxwFN3E0Q8w1Tqba6zfVI4Ls0MSZPsweRb7WRlc1s66Iywli05YzE4tZHrKrYhnOR7BIWUy542zNB0GWqzqlUfUyC9TpabALCOJmrdeZHM00WtUptTR-t9HxOQ9cTJVuiLUtGaSiqjf1wNY-iVSLzoZN8SGYUFBhdHK7bu-vy3KPBm8iCrF1ZMlGei_siRsIl3SdIA3Dirg7lYCRU8GPjB4GsX7TNW9OQ7w2dmrkEqU3zL0mrHg_KmIWfjgf9Cu7ERb3K_oV5laVVlnK6FamoLpR2miJ-6UHx0C1VZc4rpgn99aS0JlpapMty-xV5cQlD1amPh2yKGhXMy5ssjTvv1FEVF1TEMmmoginrydeUL9zGS8dSJbarq5wy-tUVWuif1btxYe9RD9zbCd7nk8ebpgVDwYGamjNUNlRzLpBNUUyPDcpV9L417eOC3cEpzql1zC-rwiM_NQUcmjc8mnnt6bE_mL1kZxaPW_33hKIxroePiBqmK3U-ICpNDjqfGeRPbMont61OvjTUeh4uVgifKvpneYBQIXI5cNUICgpcVsfsVwMbexRhoTmgwPqOdQrtRYqrEavOmkWzKz-6eDVYxAn3o_DqatVQkFoYFRztFpmg889ZNF076kdRdt1qR3tDW5WY2HmtQ5nPZkG2kpzWLaxSJki7vajYu672CKtWG6Fi99a8vZwS0rbRxeei7mbq2Q4VaAbV1IPQ7XdZzfAxKxWStZqtZxed_lWhhFxHQt3pBxWQ6ZDPBSV04j932guN0V52K4_mmwZGu7v-ZRWMP2ZH9jKLnxr3j4qoJia5z9Pm-pfO87SWaVhd8w2UNx9DDfBBN87HshOzeu1lZYbi1npNvSWYpROt5u6qRszKm7WizICPmoz-zGuRYA3aSP_M8kXvqycAAAAAAACGi-726vtRPwAAAAAAY0Ma0H-rZ3--_f1n_S1E1MC61ND1AxgQ-uYwd-7Pt6CfPnT5PlsQ8CsPJr9s7QAAAAAAjC-cly_meKZuxHab9L8FlDCHsw011AsvTq_BBj4AAAD0fw92ijqr2eqhOuRLkTJpFnjKhUosfHb21AtPti_tBusB5_rI_Qa1eqdaJ4TUS9jALgAAADCALf1DoA8b-7CxD4CNfdjYH44Fu4pi7V52jZ4oZxA7-Vb_TAb2uvrFB-jvAAAAACgJ6l25kf_CPcr-8hlNnDcSJNTYis_OdzyV6-og-OkGGVKbhm4T8E0QEBAQEBAQEBAQEBAQEIjfgf94168al1cjiJa--5lwmjjvS_HLeZdvNfnbQbZ6vck_jm0knPnh8suzk9AwiZjMZSwIJ857tOHxhieOKX7UeurLLsGSCXFA_qMsSf-CKiZJ4v-kv0fz_fqrFyWyhpMU9d98HfTxYeQyQQ8dUPn4-B_STuJg");
        addInventory(sandLibraryBookcase, random, 11, 11);
        addInventory(sandLibraryBookcase, random, 13, 17);
        addInventory(sandLibraryBookcase, random, 10, 14);
        addInventory(sandLibraryBookcase, random, 5, 14);
        addInventory(sandLibraryBookcase, random, 27, 11);
        addInventory(sandLibraryBookcase, random, 31, 14);
        addInventory(sandLibraryBookcase, random, 33, 17);
        addMobs(24, 14, false, "mummymage");
        addMobs(24, 15, false, "mummymage");
        addMobs(24, 16, false, "mummymage");
        addMobs(24, 17, false, "mummy");
        addMobs(25, 14, false, "mummy");
        addMobs(25, 15, false, "mummy");
        addMobs(25, 17, false, "sandscorpionmob");
        addMobs(25, 16, false, "sandscorpionmob");
        addMobs(25, 18, false, "sandscorpionmob");
        addMobs(25, 19, false, "sandscorpionmob");
    }
}