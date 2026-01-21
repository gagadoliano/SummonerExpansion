package summonerexpansion.items.potions;

import necesse.engine.localization.Localization;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.placeableItem.consumableItem.ConsumableItem;
import necesse.level.maps.Level;

import java.awt.*;
import java.awt.geom.Line2D;

public class MinionSunflowerPotion extends ConsumableItem
{
    public MinionSunflowerPotion()
    {
        super(1, false);
        rarity = Rarity.RARE;
        attackAnimTime.setBaseValue(300);
        itemCooldownTime.setBaseValue(2000);
        setItemCategory(ItemCategory.craftingManager, "consumable", "buffpotions");
    }

    public String canPlace(Level level, int x, int y, PlayerMob player, Line2D playerPositionLine, InventoryItem item, GNDItemMap mapContent) {
        return null;
    }

    public boolean shouldSendToOtherClients(Level level, int x, int y, PlayerMob player, InventoryItem item, String error, GNDItemMap mapContent) {
        return error == null;
    }

    public void onOtherPlayerPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, GNDItemMap mapContent) {
        SoundManager.playSound(GameResources.drink, SoundEffect.effect(player));
    }

    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, int seed, InventoryItem item, GNDItemMap mapContent)
    {
        if (level.isServer())
        {
            Buff flowerBuff = BuffRegistry.getBuff("minionsunflowerbuff");
            player.buffManager.addBuff(new ActiveBuff(flowerBuff, player, 180.0F, null), true);
        }
        else if (level.isClient())
        {
            SoundManager.playSound(GameResources.drink, SoundEffect.effect(player));
        }
        return item;
    }

    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent)
    {
        super.showAttack(level, x, y, attackerMob, attackHeight, item, animAttack, seed, mapContent);
        for(int i = 0; i < 20; ++i)
        {
            level.entityManager.addParticle(attackerMob.x + (float)(GameRandom.globalRandom.nextGaussian() * (double)6.0F), attackerMob.y + 2.0F + (float)(GameRandom.globalRandom.nextGaussian() * (double)4.0F), Particle.GType.IMPORTANT_COSMETIC).movesConstant(attackerMob.dx / 2.0F, attackerMob.dy / 2.0F).color(new Color(255, 207, 6)).heightMoves(36.0F, 4.0F).lifeTime(750);
        }
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "sunflowerpotiontip"));
        tooltips.add(Localization.translate("itemtooltip", "infiniteuse"));
        return tooltips;
    }

    public String getTranslatedTypeName() {
        return Localization.translate("item", "flask");
    }
}