package summonerexpansion.summonpreset;

import necesse.level.maps.presets.Preset;
import summonerexpansion.mobs.summonmobs.DruidHumanMob;

public class DruidSwampHousePreset extends Preset
{
    public DruidSwampHousePreset()
    {
        super("eNrVVk2P2yAU_Cv8AA7YJGvnkFN3Dz1tta3UQ5UDMS8xLYYIiHyo-t8LOHb8wW6zbaVtpUmEZx7Pw_jJyYenh48Pn7bfW8Fdvd3gGsSxdn7hhIT393b75Q4j27LmdDTM2sBiRLMLZ51WcJBam13c0JXfgtAizc4_N-sp8aai1zh4yX0CO6z3X6FyMUmCERMGo_KuxOhoAFTFzAmc75evMaqY4iHdjFCMWiGlblsmZSBWPcF91IEohwqhuG49NZQ43zhc576LPTeNVkId91p_szXIA0ZF6e910s4BP0mmXBaoYkLRQG0mlN9TbMiE8ucrcr-RA-Ot1nwPPDDlhMkxWtOi99ZofpbMOLYPB13T4RhVHZOhq3BXJpTznht9ttAH2MXXIQZ0_SIjzKQuhWR9XEb_8abk8liip77suiceKxZcC4tF55gjmSKmlnawWroejJWbcY_wyObe4xDMnJbPR7KaSjtstGNOaDVO1iNfLLr1GGT0PSAbqYNEF9L4kqbaLqWkh-xXDmeYkN3b6nExWb-BOAz9ZJDJMkUs5DdGl8RTehZeCxpxGbuFRJ6R3jyB8Ja9h0qby0C8k8DM1pkz4PimGmt_FtCtOYafg39rSIYc_tKo_JfY4SqMxqOrwXwWxv_dOTBp4cdPbsrtDw==");
        addMob("druidhuman", 5, 5, DruidHumanMob.class, (druid) -> druid.setHome(druid.getTileX(), druid.getTileY()));
    }
}