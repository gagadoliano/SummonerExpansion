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
import static summonerexpansion.summonothers.SummonerTechs.SUMMONTABLECRAFT3;
import static summonerexpansion.summonothers.SummonerTechs.SUMMONTABLECRAFT4;

public class FallenSummoningTableDuo extends SummoningTableDuo
{
    public FallenSummoningTableDuo()
    {
        mapColor = new Color(0, 107, 109);
        rarity = Item.Rarity.EPIC;
    }

    public GameMessage getNewLocalization() {
        return new LocalMessage("object", "fallensummoningtableduo");
    }

    public void loadTextures()
    {
        super.loadTextures();
        texture = ObjectDamagedTextureArray.loadAndApplyOverlay(this, "objects/fallensummoningtableduo");
    }

    public CraftingStationUpgrade getStationUpgrade()
    {
        return null;
    }

    public Tech[] getCraftingTechs() {return new Tech[]{SUMMONTABLECRAFT4, SUMMONTABLECRAFT3, SUMMONTABLECRAFT2, SUMMONTABLECRAFT};}

    public static int[] registerFallenSummoningTable()
    {
        FallenSummoningTableDuo o1 = new FallenSummoningTableDuo();
        FallenSummoningTableDuo2 o2 = new FallenSummoningTableDuo2();
        int i1 = ObjectRegistry.registerObject("fallensummoningtableduo", o1, 100.0F, true);
        int i2 = ObjectRegistry.registerObject("fallensummoningtableduo2", o2, 0.0F, false);
        o1.counterID = i2;
        o2.counterID = i1;
        return new int[]{i1, i2};
    }
}