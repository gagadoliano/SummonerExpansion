package summonerexpansion.summonothers;

import necesse.engine.registries.ItemRegistry;
import necesse.inventory.item.Item;
import summonerexpansion.summonmaterials.PureHorror;
import summonerexpansion.summonmaterials.ShadowHorrorPortal;
import summonerexpansion.summonweapons.*;

public class SummonerWeapons
{
    public static void registerSummonWeapons()
    {
        // --Weapons T1--

        // Minion
        ItemRegistry.registerItem("enchantedbrainonastick", new EnchantedBrainOnAStick(), 50, true);
        ItemRegistry.registerItem("magiccopperlamp", new MagicCopperLamp(), 50, true);
        ItemRegistry.registerItem("royalhive", new RoyalHive(), 50, true);
        ItemRegistry.registerItem("polarhead", new PolarHead(), 50, true);
        ItemRegistry.registerItem("bearhead", new BearHead(), 50, true);
        // Melee
        ItemRegistry.registerItem("wormbucket", new WormBucket(), 50, true);
        // Ranged

        // Secondary
        ItemRegistry.registerItem("explosivesnowball", new ExplosiveSnowball(), 50, true);
        ItemRegistry.registerItem("magictools", new MagicTools(), 50, true);
        ItemRegistry.registerItem("bookmagma", new BookMagma(), 50, true);
        ItemRegistry.registerItem("bookbee", new BookBee(), 50, true);
        // Sentry
        ItemRegistry.registerItem("iceblossomstaff", new IceBlossomStaff(), 50, true);
        ItemRegistry.registerItem("sunflowerstaff", new SunflowerStaff(), 50, true);
        ItemRegistry.registerItem("firemonestaff", new FiremoneStaff(), 50, true);
        ItemRegistry.registerItem("bookmushroom", new BookMushroom(), 50, true);

        // --Weapons T2--

        // Minion
        ItemRegistry.registerItem("magicdungeoncandelabra", new MagicDungeonCandelabra(), 100, true);
        ItemRegistry.registerItem("magicgoldlamp", new MagicGoldLamp(), 100, true);
        ItemRegistry.registerItem("runebonestaff", new RuneboneStaff(), 100, true);
        ItemRegistry.registerItem("vampirewings", new VampireWings(), 100, true);
        // Melee
        ItemRegistry.registerItem("goblinsword", new GoblinSword(), 100, true);
        // Ranged

        // Secondary
        ItemRegistry.registerItem("bookfrozen", new BookFrozen(), 100, true);
        ItemRegistry.registerItem("bookrunic", new BookRunic(), 100, true);
        // Sentry


        // --Weapons T3--

        // Minion
        ItemRegistry.registerItem("magiccastlecandelabra", new MagicCastleCandelabra(), 200, true);
        ItemRegistry.registerItem("magictungstenlamp", new MagicTungstenLamp(), 200, true);
        ItemRegistry.registerItem("sandwormstaff", new SandWormStaff(), 200, true);
        ItemRegistry.registerItem("dryadessence", new DryadEssence(), 200, true);
        // Melee
        ItemRegistry.registerItem("fishianspear", new FishianSpear(), 200, true);
        ItemRegistry.registerItem("horrorscythe", new HorrorScythe(), 200, true);
        ItemRegistry.registerItem("horrorglaive", new HorrorGlaive(), 200, true);
        ItemRegistry.registerItem("horrorsword", new HorrorSword(), 200, true);
        // Ranged
        ItemRegistry.registerItem("mosquitobow", new MosquitoBow(), 200, true);
        // Secondary

        // Sentry


        // --Weapons T4--

        // Minion
        ItemRegistry.registerItem("gemamethystshards", new AmethystShards(), 400, true);
        ItemRegistry.registerItem("gemsapphireshards", new SapphireShards(), 400, true);
        ItemRegistry.registerItem("gememeraldshards", new EmeraldShards(), 400, true);
        ItemRegistry.registerItem("gemrubyshards", new RubyShards(), 400, true);
        // Melee

        // Ranged

        // Secondary

        // Sentry
        ItemRegistry.registerItem("caveglowstaff", new CaveglowStaff(), 400, true);
        ItemRegistry.registerItem("vampirecoffin", new VampireCoffin(), 400, true);
    }
}