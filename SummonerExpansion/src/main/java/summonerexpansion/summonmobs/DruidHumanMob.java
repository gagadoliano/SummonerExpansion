package summonerexpansion.summonmobs;

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

import java.util.*;
import java.util.List;

public class DruidHumanMob extends HumanShop
{
    public DruidHumanMob()
    {
        super(500, 300, "druid");
        attackCooldown = 600;
        attackAnimTime = 500;
        setSwimSpeed(1.0F);
        equipmentInventory.setItem(6, new InventoryItem("woodstaff"));

        shop.addSellingItem("leafshotpack", new SellingShopItem(100, 5)).setStaticPriceBasedOnHappiness(800, 1500).addKilledMobRequirement("swampzombie");
        shop.addSellingItem("leafshotcoldpack", new SellingShopItem(100, 5)).setStaticPriceBasedOnHappiness(800, 1500).addKilledMobRequirement("trapperzombie");
        shop.addSellingItem("leafshotheatpack", new SellingShopItem(100, 5)).setStaticPriceBasedOnHappiness(800, 1500).addKilledMobRequirement("enchantedzombie");
        shop.addSellingItem("cactusstaff", new SellingShopItem()).setStaticPriceBasedOnHappiness(1600, 2000);
        shop.addSellingItem("sunflowerstaff", new SellingShopItem()).setStaticPriceBasedOnHappiness(600, 1200).addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("iceblossomstaff", new SellingShopItem()).setStaticPriceBasedOnHappiness(600, 1200).addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("firemonestaff", new SellingShopItem()).setStaticPriceBasedOnHappiness(600, 1200).addRandomAvailableRequirement(0.20F);
        shop.addSellingItem("dryadessence", new SellingShopItem()).setStaticPriceBasedOnHappiness(2500, 4000).addKilledMobRequirement("thecursedcrone");

        shop.addBuyingItem("redflowerpatch", new BuyingShopItem()).setPriceBasedOnHappiness(10, 2, 3);
        shop.addBuyingItem("blueflowerpatch", new BuyingShopItem()).setPriceBasedOnHappiness(10, 2, 3);
        shop.addBuyingItem("whiteflowerpatch", new BuyingShopItem()).setPriceBasedOnHappiness(10, 2, 3);
        shop.addBuyingItem("purpleflowerpatch", new BuyingShopItem()).setPriceBasedOnHappiness(10, 2, 3);
        shop.addBuyingItem("yellowflowerpatch", new BuyingShopItem()).setPriceBasedOnHappiness(10, 2, 3);
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
        return this.getLocalMessages("druidtalk", 4);
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
                return Collections.singletonList(new InventoryItem("coin", random.getIntBetween(1200, 1500)));
            }
            else
            {
                LootTable secondItems = new LootTable(new CountOfTicketLootItems(random.getIntBetween(1, 2), 30, new LootItem("swampsludge", Integer.MAX_VALUE), 100, new LootItem("thorns", Integer.MAX_VALUE), 50, new LootItem("cattail", Integer.MAX_VALUE)));
                ArrayList<InventoryItem> out = GameLootUtils.getItemsValuedAt(random, random.getIntBetween(350, 500), 0.20000000298023224, new LootItem("coin", Integer.MAX_VALUE));
                out.addAll(GameLootUtils.getItemsValuedAt(random, random.getIntBetween(75, 150), 0.20000000298023224, secondItems));
                out.sort(Comparator.comparing(InventoryItem::getBrokerValue).reversed());
                return out;
            }
        }
    }
}