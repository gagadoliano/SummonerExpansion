package summonerexpansion.allpresets.Swamp;

import necesse.engine.util.GameRandom;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.level.maps.presets.Preset;
import necesse.level.maps.presets.PresetUtils;
import summonerexpansion.mobs.summonmobs.DruidHumanMob;

public class DruidSwampHouseDuoPreset extends Preset
{
    public DruidSwampHouseDuoPreset(GameRandom random)
    {
        super("eNrdVs2OmzAQfhUegAP_SQ45tXvoaattpR6qHAw4QBdwZJzSquq7d2YAYwJZNupKrSIZC4-_-fU3ho9PD58ePu9_tUWq8r3r2zkvslzt3dBWRck_vG_2XyPbalpWnTLJmgaltgVA68hkVbI6tS3f7RGNEjU_lkLIA6l3yq8caOXa85boqWQVu-7oZoXb8ngJ8Xca0S0K0e0DWTI8b4V8s3GwRfyNJ4oo7tgWKyQk6oW2lQCpieSOD-FU5yaXQlRA65YDJAg3tnUqkrpIYqD_M1eA9LxJkyBqe4HyULi7EIJ9L9r1qjlPM3Qb2FZblOBOSQ7rIALVSjTNz0bx06moM2ozcOJtHFM1YwrVQ1O9UefqhDbAU8lkxueGQCUaVEqRxbxOcqiEj0IhUlhSPRpKdHOJhNg2W6yIUIqnJ0gLkwqxch0wySEIBO0moBBB2lol0jOEp1iMhQ_97ahN5-IGGI74zmUmIbFcyBryDwNP41hc1BihHwJXT6yoFeSXnpNnhOmSpOIMHmKeojSaSY9CKBe3NrMtD8XbRQ0PvW5Gr1TnVsgyrRjU3ttBLRIhStiKxQ-0s9N2JG8aZJVPfFkygI5DR-dZCkwJqDlIWlaWCHHHUtQpL1ksGeKCMWQhEacVY6ZydY6xuBgQMOV4hISkyBAWXMAojJFaAm535IOjS9IWdSraoaugpyjGV0_UQd3s6CXu0CFTevTmDH1JFOteCed0nTOa6IXzYeyYbklMB2nA-il4VRJ0iMQSItcsAuLmpXE6XGIc8bST-V0U1Ai0MKftWhzEuCt59z79_oypLVdKNd2mYoyrcKlcfk8WYjpdD0suZqe_UuHxELYLykF_lXZXhTl3YkLRXdp7j7rrTLPgJW1nomoO2u6mzWQ5ddiv8ANAn4Zg5JnJRKd35U8Fk-442FIopgpRQ5Mh666MCfMgqGH0cQ_yC9giDeYw13jvjLvDrMcFTPvVw50Z10LXgM2j8gzLc4lO011SXCD5DKDTuQZzZvVc3DIxnlEuf2pWD2daVf-K8dWMXohtsSxO97v-2F3d70rO5F7JM7fx4_KeJ0LOt-hbbe7RX9Sdje7TOB6LH_0fcdF_qnPf42BQ7Gm88e4vU2-tnf9tSPfKrgTvskeVc_mlgN_w_ZGVDf_9B7TNmpI=");
        addMob("druidhuman", 3, 3, DruidHumanMob.class, (druid) -> druid.setHome(druid.getTileX(), druid.getTileY()));
        addMob("druidhuman", 2, 3, DruidHumanMob.class, (druid) -> druid.setHome(druid.getTileX(), druid.getTileY()));
        addInventory(LootTablePresets.hunterCookedFoodLootTable, random, 5, 6);
        addInventory(LootTablePresets.basicCrate, random, 6, 1);
    }
}