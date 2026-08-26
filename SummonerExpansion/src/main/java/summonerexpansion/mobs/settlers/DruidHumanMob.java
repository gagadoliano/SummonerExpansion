package summonerexpansion.mobs.settlers;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameLootUtils;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.human.humanShop.BuyingShopItem;
import necesse.entity.mobs.friendly.human.humanShop.HumanShop;
import necesse.entity.mobs.friendly.human.humanShop.SellingShopItem;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.inventory.InventoryItem;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.CountOfTicketLootItems;
import necesse.inventory.lootTable.lootItem.LootItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DruidHumanMob extends HumanShop
{
    public DruidHumanMob()
    {
        super(500, 300, "druid");
        attackCooldown = 600;
        attackAnimTime = 500;
        setSwimSpeed(1.0F);
        equipmentInventory.setItem(6, new InventoryItem("druidleatherclaw"));

        // Consumables
        shop.addSellingItem("leafshotpack", new SellingShopItem(100, 5)).setStaticPriceBasedOnHappiness(200, 1500).addKilledMobRequirement("swampzombie");
        shop.addSellingItem("leafshotcoldpack", new SellingShopItem(100, 5)).setStaticPriceBasedOnHappiness(200, 1500).addKilledMobRequirement("trapperzombie");
        shop.addSellingItem("leafshotheatpack", new SellingShopItem(100, 5)).setStaticPriceBasedOnHappiness(200, 1500).addKilledMobRequirement("enchantedzombie");
        shop.addSellingItem("coffebeampack", new SellingShopItem(100, 5)).setStaticPriceBasedOnHappiness(400, 2500).addKilledMobRequirement("incursioncrawlingzombie");
        // Summon weapons
        shop.addSellingItem("cactusstaff", new SellingShopItem()).setStaticPriceBasedOnHappiness(1600, 2000).addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("bookmushroom", new SellingShopItem()).setStaticPriceBasedOnHappiness(1200, 2000).addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("stabbybush", new SellingShopItem()).setStaticPriceBasedOnHappiness(800, 2000).addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("bashybush", new SellingShopItem()).setStaticPriceBasedOnHappiness(1200, 3000).addKilledMobRequirement("voidwizard").addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("dryadbranch", new SellingShopItem()).setStaticPriceBasedOnHappiness(2500, 4000).addKilledMobRequirement("thecursedcrone").addRandomAvailableRequirement(0.20F);
        // Claw weapons
        shop.addSellingItem("druidleatherclaw", new SellingShopItem()).setStaticPriceBasedOnHappiness(200, 1100);
        shop.addSellingItem("druidpolarclaw", new SellingShopItem()).setStaticPriceBasedOnHappiness(300, 1200).addKilledMobRequirement("polarbear");
        shop.addSellingItem("druiddemonclaw", new SellingShopItem()).setStaticPriceBasedOnHappiness(400, 1300).addKilledMobRequirement("evilsprotector");
        shop.addSellingItem("druidspiderclaw", new SellingShopItem()).setStaticPriceBasedOnHappiness(500, 1400).addKilledMobRequirement("queenspider");
        shop.addSellingItem("druidnecroticclaw", new SellingShopItem()).setStaticPriceBasedOnHappiness(600, 1500).addKilledMobRequirement("evilwitch");
        shop.addSellingItem("druidvultureclaw", new SellingShopItem()).setStaticPriceBasedOnHappiness(700, 1600).addKilledMobRequirement("ancientvulture");
        shop.addSellingItem("druidancestorclaw", new SellingShopItem()).setStaticPriceBasedOnHappiness(800, 1700).addKilledMobRequirement("ancientskeletonmage");
        shop.addSellingItem("druidprimordialclaws", new SellingShopItem()).setStaticPriceBasedOnHappiness(900, 1800).addKilledMobRequirement("sageandgrit");
        shop.addSellingItem("druidfallendragonclaw", new SellingShopItem()).setStaticPriceBasedOnHappiness(1000, 1900).addKilledMobRequirement("fallenwizard");
        // Magic weapons
        shop.addSellingItem("sunflowerstaff", new SellingShopItem()).setStaticPriceBasedOnHappiness(600, 1200).addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("iceblossomstaff", new SellingShopItem()).setStaticPriceBasedOnHappiness(600, 1200).addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("firemonestaff", new SellingShopItem()).setStaticPriceBasedOnHappiness(600, 1200).addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("thornstaff", new SellingShopItem()).setStaticPriceBasedOnHappiness(800, 2000).addKilledMobRequirement("evilsprotector").addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("applewalkingstick", new SellingShopItem()).setStaticPriceBasedOnHappiness(1000, 2000).addKilledMobRequirement("swampguardian").addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("pinewoodstaff", new SellingShopItem()).setStaticPriceBasedOnHappiness(1000, 4000).addKilledMobRequirement("pestwarden").addRandomAvailableRequirement(0.20F);
        // Trinkets
        shop.addSellingItem("flowerbrooch", new SellingShopItem()).setStaticPriceBasedOnHappiness(200, 800).addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("cactusemblem", new SellingShopItem()).setStaticPriceBasedOnHappiness(200, 800).addRandomAvailableRequirement(0.20F);
        // Objects
        shop.addSellingItem("caveglowlamp", new SellingShopItem()).setStaticPriceBasedOnHappiness(50, 200).addKilledMobRequirement("skeleton");
        shop.addSellingItem("woodenidol", new SellingShopItem()).setStaticPriceBasedOnHappiness(150, 500).addKilledMobRequirement("dollmakermob").addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("stuffedfrog", new SellingShopItem()).setStaticPriceBasedOnHappiness(150, 500).addKilledMobRequirement("dollmakermob").addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("stuffedmouse", new SellingShopItem()).setStaticPriceBasedOnHappiness(150, 500).addKilledMobRequirement("dollmakermob").addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("stuffedmosquito", new SellingShopItem()).setStaticPriceBasedOnHappiness(150, 500).addKilledMobRequirement("dollmakermob").addRandomAvailableRequirement(0.20F);

        // Sell items
        shop.addBuyingItem("redflowerpatch", new BuyingShopItem()).setPriceBasedOnHappiness(10, 2, 3);
        shop.addBuyingItem("blueflowerpatch", new BuyingShopItem()).setPriceBasedOnHappiness(10, 2, 3);
        shop.addBuyingItem("whiteflowerpatch", new BuyingShopItem()).setPriceBasedOnHappiness(10, 2, 3);
        shop.addBuyingItem("purpleflowerpatch", new BuyingShopItem()).setPriceBasedOnHappiness(10, 2, 3);
        shop.addBuyingItem("yellowflowerpatch", new BuyingShopItem()).setPriceBasedOnHappiness(10, 2, 3);
        shop.addBuyingItem("prettyflower", new BuyingShopItem()).setPriceBasedOnHappiness(500, 250, 30);
        shop.addBuyingItem("prettybouquet", new BuyingShopItem()).setPriceBasedOnHappiness(5000, 1000, 30);
    }

    public LootTable getLootTable() {
        return super.getLootTable();
    }

    public void setDefaultArmor(HumanDrawOptions drawOptions)
    {
        drawOptions.helmet(new InventoryItem("dryadscarf"));
        drawOptions.chestplate(new InventoryItem("dryadchestplate"));
        drawOptions.boots(new InventoryItem("dryadboots"));
    }

    protected ArrayList<GameMessage> getMessages(ServerClient client)
    {
        return this.getLocalMessages("druidtalk", 12);
    }

    public List<InventoryItem> getRecruitItems(ServerClient client)
    {
        if (this.isTrapped())
        {
            return Collections.emptyList();
        } else
        {
            GameRandom random = new GameRandom((long)this.getSettlerSeed() * 200L);
            if (this.isVisitor())
            {
                return Collections.singletonList(new InventoryItem("coin", random.getIntBetween(2500, 5000)));
            }
            else
            {
                LootTable secondItems = new LootTable(new CountOfTicketLootItems(random.getIntBetween(1, 2), 20, new LootItem("swampsludge", 20), 20, new LootItem("thorns", 20), 20, new LootItem("cattail", 20)));
                ArrayList<InventoryItem> out = GameLootUtils.getItemsValuedAt(random, random.getIntBetween(350, 500), 0.20000000298023224, new LootItem("coin", 500));
                out.addAll(GameLootUtils.getItemsValuedAt(random, random.getIntBetween(75, 150), 0.20000000298023224, secondItems));
                out.sort(Comparator.comparing(InventoryItem::getBrokerValue).reversed());
                return out;
            }
        }
    }
}