package summonerexpansion.objects.crafting;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.ObjectRegistry;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.Item;
import necesse.inventory.recipe.Tech;
import necesse.level.gameObject.container.CraftingStationUpgrade;

import java.awt.*;

import static summonerexpansion.codes.registries.RegistryTechs.*;

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
        texture = GameTexture.fromFile("objects/crafting/fallensummoningtableduo");
    }

    public CraftingStationUpgrade getStationUpgrade()
    {
        return null;
    }

    public Tech[] getCraftingTechs() {return new Tech[]{SUMMONTABLECRAFT4, SUMMONTABLECRAFT3, SUMMONTABLECRAFT2, SUMMONTABLECRAFT};}

    public static void registerFallenSummoningTable()
    {
        FallenSummoningTableDuo o1 = new FallenSummoningTableDuo();
        FallenSummoningTableDuo2 o2 = new FallenSummoningTableDuo2();
        int i1 = ObjectRegistry.registerObject("fallensummoningtableduo", o1, 100.0F, true);
        o1.counterID = ObjectRegistry.registerObject("fallensummoningtableduo2", o2, 0.0F, false);
        o2.counterID = i1;
    }
}