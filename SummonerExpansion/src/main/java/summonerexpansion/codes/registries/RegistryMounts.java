package summonerexpansion.codes.registries;

import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.inventory.item.Item;
import summonerexpansion.items.mounts.*;
import summonerexpansion.items.mounts.buffs.*;
import summonerexpansion.items.mounts.minions.*;
import summonerexpansion.items.mounts.transformations.*;

public class RegistryMounts
{
    public static void registerItems()
    {
        RegistryMounts.registerTier1();
        RegistryMounts.registerTier2();
        RegistryMounts.registerTier3();
        RegistryMounts.registerTier4();
    }

    public static class MountBuffs
    {
        //Buffs
        public static Buff CHIEFARMOR;

        //Debuffs
        public static Buff FORESTDRAIN;

        public MountBuffs() {
        }
    }

    public static void registerTier1()
    {
        ItemRegistry.registerItem("sharkdivingmask", new BaseTransformationItem(Item.Rarity.UNCOMMON, "sharksummonmount", "sharkdivingmasktip"), 50, true);
        MobRegistry.registerMob("sharksummonmount", SharkSummonMount.class, false);
        ItemRegistry.registerItem("zombiearrow", new BaseTransformationItem(Item.Rarity.UNCOMMON, "zombiearchersummonmount", "zombiearrowtip"), 50, true);
        MobRegistry.registerMob("zombiearchersummonmount", ZombieArcherSummonMount.class, false);
    }

    public static void registerTier2()
    {
        ItemRegistry.registerItem("frozendwarfbeard", new BaseTransformationItem(Item.Rarity.RARE, "frozendwarfsummonmount", "frozendwarfbeardtip"), 100, true);
        MobRegistry.registerMob("frozendwarfsummonmount", FrozenDwarfSummonMount.class, false);
        ItemRegistry.registerItem("cavelingminecart", new BaseMountItem(Item.Rarity.RARE, "cavelingminecartmount", "cavelingminecarttip"), 100, true);
        MobRegistry.registerMob("cavelingminecartmount", CavelingMinecartMount.class, false);
        ItemRegistry.registerItem("cavespidergoo", new BaseTransformationItem(Item.Rarity.RARE, "cavespidersummonmount", "cavespidergootip"), 100, true);
        MobRegistry.registerMob("cavespidersummonmount", CaveSpiderSummonMount.class, false);
        ItemRegistry.registerItem("minivultureegg", new BaseTransformationItem(Item.Rarity.RARE, "vulturesummonmount", "minivultureeggtip"), 100, true);
        MobRegistry.registerMob("vulturesummonmount", VultureSummonMount.class, false);
        ItemRegistry.registerItem("chieftainhat", new BaseTransformationItem(Item.Rarity.RARE, "chiefsummonmount", "chieftainhattip"), 100, true);
        MobRegistry.registerMob("chiefsummonmount", ChiefSummonMount.class, false);
        BuffRegistry.registerBuff("chiefbuff", MountBuffs.CHIEFARMOR = new MountChiefBuff());
        ItemRegistry.registerItem("magiccheese", new BaseTransformationItem(Item.Rarity.RARE, "mousesummonmount", "magiccheesetip"), 100, true);
        MobRegistry.registerMob("mousesummonmount", MouseSummonMount.class, false);

    }

    public static void registerTier3()
    {
        ItemRegistry.registerItem("forestspectorhands", new BaseTransformationItem(Item.Rarity.EPIC, "spectorsummonmount", "forestspectorhandstip"), 200, true);
        MobRegistry.registerMob("spectorsummonmount", SpectorSummonMount.class, false);
        MobRegistry.registerMob("mountspectorsummonminion", MountSpectorSummonMinion.class, false);
        BuffRegistry.registerBuff("forestspiritdebuff", MountBuffs.FORESTDRAIN = new ForestSpiritDebuff());
        ItemRegistry.registerItem("bouncingbone", new BaseTransformationItem(Item.Rarity.EPIC, "skeletonthrowersummonmount", "bouncingbonetip"), 200, true);
        MobRegistry.registerMob("skeletonthrowersummonmount", SkeletonThrowerSummonMount.class, false);
        ItemRegistry.registerItem("swampdwellerbow", new BaseTransformationItem(Item.Rarity.EPIC, "dwellersummonmount", "swampdwellerbowtip"), 200, true);
        MobRegistry.registerMob("dwellersummonmount", DwellerSummonMount.class, false);
        ItemRegistry.registerItem("snowwolftail", new BaseTransformationItem(Item.Rarity.EPIC, "wolfsummonmount", "snowwolftailtip"), 200, true);
        MobRegistry.registerMob("wolfsummonmount", WolfSummonMount.class, false);
        MobRegistry.registerMob("mountwolfsummonminion", MountWolfSummonMinion.class, false);

    }

    public static void registerTier4()
    {
        ItemRegistry.registerItem("cryptfangs", new BaseTransformationItem(Item.Rarity.LEGENDARY, "cryptvampiresummonmount", "cryptfangstip"), 400, true);
        MobRegistry.registerMob("cryptvampiresummonmount", CryptVampireSummonMount.class, false);
    }
}