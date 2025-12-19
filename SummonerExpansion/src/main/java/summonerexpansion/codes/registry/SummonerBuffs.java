package summonerexpansion.codes.registry;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.*;
import summonerexpansion.buffs.bannerbuffs.*;
import summonerexpansion.buffs.*;
import summonerexpansion.buffs.debuffs.*;
import summonerexpansion.buffs.followerbuffs.*;

import java.awt.*;

public class SummonerBuffs
{
    public static class SummonerBanners
    {
        public static Buff RESILIENCEBOOST;
        public static Buff STAMINABOOST;
        public static Buff ESSENCEBOOST;
        public static Buff BOUNCEBOOST;
        public static Buff PICKUPBOOST;
        public static Buff DASHBOOST;
        public static Buff MANABOOST;

        public SummonerBanners() {
        }
    }

    public static class SummonerAuras
    {
        public static Buff WATERBANNERBOOST;
        public static Buff CAVEGLOWLAMPBOOST;

        public SummonerAuras() {
        }
    }

    public static class SummonBuffs
    {
        public static Buff CLAW_DASH_STACKS;
        public static Buff CLAW_DASH_COOLDOWN;
        public static Buff CLAWVULTURESPEED;
        public static Buff CLAWSPIDERWEB;
        public static Buff CLAWANCESTOR;
        public static Buff CLAWDEMON;
        public static Buff CLAWFALLEN;

        public static Buff COPPERSET_CONSECUTIVE;

        public SummonBuffs() {
        }
    }

    public static class SummonerDebuffs
    {
        public static Buff LAMPTUNGSTENFIRE;
        public static Buff LAMPDUNGEONFIRE;
        public static Buff REDSPIDERPOISON;
        public static Buff LAMPCASTLEFIRE;
        public static Buff BLEEDINGPOISON;
        public static Buff REDSPIDERARMOR;
        public static Buff BOOKFROZENSLOW;
        public static Buff ICEWIZARDFROST;
        public static Buff ENCHANTEDWEAK;
        public static Buff MOSQUITOWEAK;
        public static Buff MUSHROOMSLOW;
        public static Buff LAMPGOLDFIRE;
        public static Buff HONEYSLOW;
        public static Buff POLARSLOW;

        public static Buff TRINKETMUMMYSUMMON;
        public static Buff TRINKETMUMMYMAGIC;

        public static Buff COPPERSETFIRE;

        public static Buff CLAWPOLARSLOW;
        public static Buff CLAWLEATHERWEAK;
        public static Buff CLAWNECROPOISON;
        public static Buff CLAWPRIMORDIAL;

        public static Buff SUMMON_GENERIC_WEAKNESS;
        public static Buff SUMMON_GENERIC_INCREASEDOT;
        public static Buff SUMMON_GENERIC_TARGETRANGE;
        public static Buff SUMMON_GENERIC_HEALTH;

        public SummonerDebuffs() {
        }
    }

    public static void registerSummonBuffs()
    {
        // Banners
        BuffRegistry.registerBuff("bannerofresilience", SummonerBanners.RESILIENCEBOOST = new ResilienceBannerBuff());
        BuffRegistry.registerBuff("bannerofbouncing", SummonerBanners.BOUNCEBOOST = new BouncingBannerBuff());
        BuffRegistry.registerBuff("bannerofessence", SummonerBanners.ESSENCEBOOST = new EssenceBannerBuff());
        BuffRegistry.registerBuff("bannerofstamina", SummonerBanners.STAMINABOOST = new StaminaBannerBuff());
        BuffRegistry.registerBuff("bannerofpicking", SummonerBanners.PICKUPBOOST = new PickingBannerBuff());
        BuffRegistry.registerBuff("bannerofdashing", SummonerBanners.DASHBOOST = new DashingBannerBuff());
        BuffRegistry.registerBuff("bannerofmana", SummonerBanners.MANABOOST = new ManaBannerBuff());

        // Object Auras
        BuffRegistry.registerBuff("bannerofwater", SummonerAuras.WATERBANNERBOOST = new WaterBannerBuff());
        BuffRegistry.registerBuff("waterbannerbuff", new WaterBannerBuff());
        BuffRegistry.registerBuff("caveglowlamp", SummonerAuras.CAVEGLOWLAMPBOOST = new CaveglowLampBuff());
        BuffRegistry.registerBuff("caveglowlampbuff", new CaveglowLampBuff());

        // Minion Buffs
        BuffRegistry.registerBuff("runicshieldbuff", new RunicShieldBuff());
        BuffRegistry.registerBuff("coppertoolbuff", new CopperToolBuff());
        BuffRegistry.registerBuff("irontoolbuff", new IronToolBuff());
        BuffRegistry.registerBuff("goldtoolbuff", new GoldToolBuff());
        BuffRegistry.registerBuff("woodtoolbuff", new WoodToolBuff());
        BuffRegistry.registerBuff("honeybuff", new HoneyBuff());

        // Sentry Buffs
        BuffRegistry.registerBuff("iceblossombufft5", new IceBlossomBuffT5());
        BuffRegistry.registerBuff("iceblossombufft1", new IceBlossomBuffT1());
        BuffRegistry.registerBuff("sunflowerbufft5", new SunflowerBuffT5());
        BuffRegistry.registerBuff("sunflowerbufft1", new SunflowerBuffT1());
        BuffRegistry.registerBuff("firemonebufft5", new FiremoneBuffT5());
        BuffRegistry.registerBuff("firemonebufft1", new FiremoneBuffT1());
        BuffRegistry.registerBuff("iceblossombuff", new IceBlossomBuff());
        BuffRegistry.registerBuff("sunflowerbuff", new SunflowerBuff());
        BuffRegistry.registerBuff("firemonebuff", new FiremoneBuff());
        BuffRegistry.registerBuff("mushroombuff", new MushroomBuff());
        BuffRegistry.registerBuff("xmastreebuff", new XmasTreeBuff());

        // Melee Buffs
        BuffRegistry.registerBuff("horrorglaivecooldowndebuff", new ShownItemCooldownBuff(1, true, "items/horrorglaive"));
        BuffRegistry.registerBuff("goblincooldowndebuff", new ShownItemCooldownBuff(1, true, "items/goblinsword"));
        BuffRegistry.registerBuff("clawdashcooldown", SummonBuffs.CLAW_DASH_COOLDOWN = new ShownCooldownBuff());
        BuffRegistry.registerBuff("clawdashstacks", SummonBuffs.CLAW_DASH_STACKS = new ClawDashStacksBuff());
        BuffRegistry.registerBuff("clawvulturebuff", SummonBuffs.CLAWVULTURESPEED = new ClawVultureBuff());
        BuffRegistry.registerBuff("clawancestorbuff", SummonBuffs.CLAWANCESTOR = new AncestorClawBuff());
        BuffRegistry.registerBuff("clawspiderbuff", SummonBuffs.CLAWSPIDERWEB = new ClawSpiderBuff());
        BuffRegistry.registerBuff("clawfallenbuff", SummonBuffs.CLAWFALLEN = new FallenClawBuff());
        BuffRegistry.registerBuff("clawdemonbuff", SummonBuffs.CLAWDEMON = new DemonClawBuff());
        BuffRegistry.registerBuff("horrorglaivestack", new HorrorGlaiveStackBuff());
        BuffRegistry.registerBuff("goblinswordstack", new GoblinSwordStackBuff());
        BuffRegistry.registerBuff("horrorswordstack", new HorrorSwordStackBuff());
        BuffRegistry.registerBuff("fishianstack", new FishianStackBuff());

        // Floor Buffs
        BuffRegistry.registerBuff("amethystfloorbuff", new FloorAmethystBuff());
        BuffRegistry.registerBuff("sapphirefloorbuff", new FloorSapphireBuff());
        BuffRegistry.registerBuff("emeraldfloorbuff", new FloorEmeraldBuff());
        BuffRegistry.registerBuff("topazfloorbuff", new FloorTopazBuff());
        BuffRegistry.registerBuff("rubyfloorbuff", new FloorRubyBuff());

        // Armor Sets Buffs
        BuffRegistry.registerBuff("coppersetconsecutive", SummonBuffs.COPPERSET_CONSECUTIVE = new CopperSetConsecutiveBuff());

        // Summon Count Buffs
        BuffRegistry.registerBuff("summonedagedchampionminionbuff", new SummonedAgedChampionMinionBuff());
        BuffRegistry.registerBuff("summonedsharpshooterbuff", new SummonedSharpshooterMinionBuff());
        BuffRegistry.registerBuff("summonedjellyfishminionbuff", new SummonedJellyfishMinionBuff());
        BuffRegistry.registerBuff("summonedteapotminionbuff", new SummonedTeaPotMinionBuff());
        BuffRegistry.registerBuff("summonedbeetminionbuff", new SummonedBeetMinionBuff());
    }

    public static void registerSummonDebuffs()
    {
        // Minion Debuffs
        BuffRegistry.registerBuff("redspiderpoisondebuff", SummonerDebuffs.REDSPIDERPOISON = new RedSpiderPoisonDebuff());
        BuffRegistry.registerBuff("lamptungstendebuff", SummonerDebuffs.LAMPTUNGSTENFIRE = new LampTungstenDebuff());
        BuffRegistry.registerBuff("lampdungeondebuff", SummonerDebuffs.LAMPDUNGEONFIRE = new LampDungeonDebuff());
        BuffRegistry.registerBuff("redspiderdebuff", SummonerDebuffs.REDSPIDERARMOR = new RedSpiderSetDebuff());
        BuffRegistry.registerBuff("lampcastledebuff", SummonerDebuffs.LAMPCASTLEFIRE = new LampCastleDebuff());
        BuffRegistry.registerBuff("frozenbookdebuff", SummonerDebuffs.BOOKFROZENSLOW = new FrozenBookDebuff());
        BuffRegistry.registerBuff("icewizarddebuff", SummonerDebuffs.ICEWIZARDFROST = new IceWizardDebuff());
        BuffRegistry.registerBuff("enchanteddebuff", SummonerDebuffs.ENCHANTEDWEAK = new EnchantedDebuff());
        BuffRegistry.registerBuff("bleedingdebuff", SummonerDebuffs.BLEEDINGPOISON = new BleedingDebuff());
        BuffRegistry.registerBuff("mosquitodebuff", SummonerDebuffs.MOSQUITOWEAK = new MosquitoDebuff());
        BuffRegistry.registerBuff("lampgolddebuff", SummonerDebuffs.LAMPGOLDFIRE = new LampGoldDebuff());
        BuffRegistry.registerBuff("polarslowdebuff", SummonerDebuffs.POLARSLOW = new PolarSlowDebuff());
        BuffRegistry.registerBuff("honeydebuff", SummonerDebuffs.HONEYSLOW = new HoneyDebuff());

        // Sentry Debuffs
        BuffRegistry.registerBuff("mushroomdebuff", SummonerDebuffs.MUSHROOMSLOW = new MushroomDebuff());

        // Melee Debuffs
        BuffRegistry.registerBuff("clawprimordialdebuff", SummonerDebuffs.CLAWPRIMORDIAL = new PrimordialClawDebuff());
        BuffRegistry.registerBuff("clawleatherdebuff", SummonerDebuffs.CLAWLEATHERWEAK = new LeatherClawDebuff());
        BuffRegistry.registerBuff("clawnecrodebuff", SummonerDebuffs.CLAWNECROPOISON = new NecroClawDebuff());
        BuffRegistry.registerBuff("clawpolardebuff", SummonerDebuffs.CLAWPOLARSLOW = new PolarClawDebuff());

        // Trinket Debuffs
        BuffRegistry.registerBuff("mummysummondebuff", SummonerDebuffs.TRINKETMUMMYSUMMON = new MummySummonDebuff());
        BuffRegistry.registerBuff("mummymagicdebuff", SummonerDebuffs.TRINKETMUMMYMAGIC = new MummyMagicDebuff());

        // Armor Set Debuffs
        BuffRegistry.registerBuff("coppersetfiredebuff", SummonerDebuffs.COPPERSETFIRE = new CopperSetFireDebuff());

        // Generic Debuffs
        BuffRegistry.registerBuff("summongenericweaknessdebuff", SummonerDebuffs.SUMMON_GENERIC_WEAKNESS = new SimpleDebuff(new Color(98, 104, 113), "summongenericweaknessdebuff", new ModifierValue<>(BuffModifiers.INCOMING_DAMAGE_MOD, 1.2F)));
        BuffRegistry.registerBuff("summongenericdotdebuff", SummonerDebuffs.SUMMON_GENERIC_INCREASEDOT = new SimpleDebuff(new Color(158, 62, 209), "summongenericdotdebuff", new ModifierValue<>(BuffModifiers.FROST_DAMAGE, 1F), new ModifierValue<>(BuffModifiers.FIRE_DAMAGE, 1F), new ModifierValue<>(BuffModifiers.POISON_DAMAGE, 1F)));
        BuffRegistry.registerBuff("summongenericrangedebuff", SummonerDebuffs.SUMMON_GENERIC_TARGETRANGE = new SimpleDebuff(new Color(56, 58, 61), "summongenericrangedebuff", new ModifierValue<>(BuffModifiers.TARGET_RANGE, 0.5F)));
        BuffRegistry.registerBuff("summongenerichealthdebuff", SummonerDebuffs.SUMMON_GENERIC_HEALTH = new SimpleDebuff(new Color(112, 15, 15), "summongenerichealthdebuff", new ModifierValue<>(BuffModifiers.MAX_HEALTH, -0.20F)));
    }
}