package summonerexpansion.objects.summonobjects;

import java.awt.Color;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.ObjectRegistry;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.Item;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Tech;
import necesse.level.gameObject.container.CraftingStationUpgrade;

import static summonerexpansion.codes.registry.SummonerTechs.*;

public class TungstenSummoningTableDuo extends SummoningTableDuo
{
    public TungstenSummoningTableDuo()
    {
        mapColor = new Color(97, 95, 132);
        rarity = Item.Rarity.RARE;
    }

    public GameMessage getNewLocalization() {
        return new LocalMessage("object", "tungstensummoningtableduo");
    }

    public void loadTextures()
    {
        super.loadTextures();
        texture = GameTexture.fromFile("objects/tungstensummoningtableduo");
    }

    public CraftingStationUpgrade getStationUpgrade()
    {
        return new CraftingStationUpgrade(ObjectRegistry.getObject("fallensummoningtableduo"), new Ingredient("upgradeshard", 5));
    }

    public Tech[] getCraftingTechs()
    {
        return new Tech[]{SUMMONTABLECRAFT3, SUMMONTABLECRAFT2, SUMMONTABLECRAFT};
    }

    public static int[] registerTungstenSummoningTable()
    {
        TungstenSummoningTableDuo o1 = new TungstenSummoningTableDuo();
        TungstenSummoningTableDuo2 o2 = new TungstenSummoningTableDuo2();
        int i1 = ObjectRegistry.registerObject("tungstensummoningtableduo", o1, 75.0F, true);
        int i2 = ObjectRegistry.registerObject("tungstensummoningtableduo2", o2, 0.0F, false);
        o1.counterID = i2;
        o2.counterID = i1;
        return new int[]{i1, i2};
    }
}