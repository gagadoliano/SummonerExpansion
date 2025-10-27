package summonerexpansion.summonobjects;

import java.awt.Color;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.ObjectRegistry;
import necesse.inventory.item.Item;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Tech;
import necesse.level.gameObject.ObjectDamagedTextureArray;
import necesse.level.gameObject.container.CraftingStationUpgrade;

import static summonerexpansion.summonothers.SummonerTechs.SUMMONTABLECRAFT;
import static summonerexpansion.summonothers.SummonerTechs.SUMMONTABLECRAFT2;

public class DemonicSummoningTableDuo extends SummoningTableDuo
{
    public DemonicSummoningTableDuo()
    {
        mapColor = new Color(30, 30, 30);
        rarity = Item.Rarity.UNCOMMON;
    }

    public GameMessage getNewLocalization() {
        return new LocalMessage("object", "demonicsummoningtableduo");
    }

    public void loadTextures()
    {
        super.loadTextures();
        texture = ObjectDamagedTextureArray.loadAndApplyOverlay(this, "objects/demonicsummoningtableduo");
    }

    public CraftingStationUpgrade getStationUpgrade()
    {
        return new CraftingStationUpgrade(ObjectRegistry.getObject("tungstensummoningtableduo"), new Ingredient("tungstenbar", 5));
    }

    public Tech[] getCraftingTechs()
    {
        return new Tech[]{SUMMONTABLECRAFT2, SUMMONTABLECRAFT};
    }

    public static int[] registerDemonicSummoningTable()
    {
        DemonicSummoningTableDuo o1 = new DemonicSummoningTableDuo();
        DemonicSummoningTableDuo2 o2 = new DemonicSummoningTableDuo2();
        int i1 = ObjectRegistry.registerObject("demonicsummoningtableduo", o1, 50.0F, true);
        int i2 = ObjectRegistry.registerObject("demonicsummoningtableduo2", o2, 0.0F, false);
        o1.counterID = i2;
        o2.counterID = i1;
        return new int[]{i1, i2};
    }
}