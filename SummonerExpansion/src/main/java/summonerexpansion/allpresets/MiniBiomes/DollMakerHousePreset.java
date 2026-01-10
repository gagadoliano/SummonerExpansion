package summonerexpansion.allpresets.MiniBiomes;

import necesse.engine.util.GameRandom;
import necesse.inventory.InventoryItem;
import necesse.level.maps.presets.Preset;

import java.util.concurrent.atomic.AtomicInteger;

public class DollMakerHousePreset extends Preset
{
    public DollMakerHousePreset(GameRandom random, AtomicInteger chestRotation)
    {
        super("eNqlVs2OmzAQfhUewAebEH4OnLp76Gmr3ZV6qHIw4IAbgre2aVRVffcamxALTICsNCD45psZe34M316f357f078XWsgqRT6oCC0rmSIIJK3J1yeR_khi4OEmp6SRJcdCdArg7YF3Yaw41oxx4IXm7QPLqlMftHVv-5lrb8kiJ9Ti5A8EmxbazHCFLPPWx9uyelc27udk-ToAlv0kudQlhqrCVJURBZEPPCHb45EUR85K4MVQKQtWftStqLpXRShow_r3YI-Al6kmKITEtBHaRzT4yCgvNBTfoLolA5wMcI4bzP90YAwtkKtYuNYwusEVzU8a2w8Yx1lGpQZv4cWvlnJOlH0QKpTh05kVbY25xFnXxHHfxRdcK07cd3GhWzpKAuXn1HaaKFHEMxMiZ78JV34NjIKdMhHt-cwa2pTaadEyrYgcCv-adJNyI90aXBe0xMb1Tka3kd2Uajtzm01o8Jpgy0qTwwlvJB3jZmg7ge5tuWLDaynh_cXqBtQ3OOU6c2GqZko0zcViXWziAXAmsaSssSu6UnxL5rR3EN9lYj_slhaArOfdauYoHHLR7KUil7eRbMib-bS8mDH6UhPMU8lbAroRfiI541OVHj9bt71W8HqImRNyjhD1x8YITiZ9je7FiR9Y3cJEwsWNLcY324OuzemPgr03fV5uK-pQo9fHJwrOj5NRoZmBmZuoNarHZPcJGnLRkKX1NyY_72blRVaEf6dc_bYdcS3Iv__KWx-r");
        width = 12;
        height = 10;
        addHuman("druidhuman", 5, 6, (humanMob) ->
        {
            humanMob.equipmentInventory.clearInventory();
            InventoryItem hat = new InventoryItem("leathersummonerhood", 1);
            humanMob.equipmentInventory.setItem(0, hat);
            InventoryItem shirt = new InventoryItem("leathershirt", 1);
            humanMob.equipmentInventory.setItem(1, shirt);
            InventoryItem shoes = new InventoryItem("leatherboots", 1);
            humanMob.equipmentInventory.setItem(2, shoes);
            InventoryItem weapon = new InventoryItem("applewalkingstick", 1);
            humanMob.equipmentInventory.setItem(6, weapon);
        }, random);
    }
}