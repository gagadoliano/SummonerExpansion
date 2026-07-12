package summonerexpansion.items.trinkets;

import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketForceOfWind;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.MaskShaderOptions;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.gfx.GameResources;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import necesse.inventory.lootTable.presets.TrinketsLootTable;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;

public class DefinitiveShell extends BaseTrinketItem
{
    public DefinitiveShell()
    {
        super(Item.Rarity.UNCOMMON, "definitiveshellbuff", 200, TrinketsLootTable.trinkets);
        attackAnimTime.setBaseValue(200);
    }

    public Point getControllerAttackLevelPos(Level level, float aimDirX, float aimDirY, PlayerMob player, InventoryItem item)
    {
        return new Point((int)(player.x + aimDirX * 224.0F), (int)(player.y + aimDirY * 224.0F));
    }

    public InventoryItem onAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, ItemAttackSlot slot, int animAttack, int seed, GNDItemMap mapContent)
    {
        int strength = 150;
        Point2D.Float dir = GameMath.normalize((float)x - attackerMob.x, (float)y - attackerMob.y);
        PacketForceOfWind.applyToMob(level, attackerMob, dir.x, dir.y, (float)strength);
        PacketForceOfWind.addCooldownStack(attackerMob, 3.0F, level.isServer());
        attackerMob.buffManager.addBuff(new ActiveBuff(BuffRegistry.FOW_ACTIVE, attackerMob, 0.15F, null), level.isServer());
        attackerMob.buffManager.addBuff(new ActiveBuff(BuffRegistry.Debuffs.GUARDIAN_SHELL_COOLDOWN, attackerMob, 3F, null), false);
        attackerMob.buffManager.addBuff(new ActiveBuff(BuffRegistry.GUARDIAN_SHELL_ACTIVE, attackerMob, 2F, null), false);
        attackerMob.buffManager.forceUpdateBuffs();
        if (level.isServer()) {
            attackerMob.sendAttackerPacket(attackerMob, new PacketForceOfWind(attackerMob, dir.x, dir.y, (float)strength));
        }
        return item;
    }

    public void showAttack(Level level, int x, int y, ItemAttackerMob attackerMob, int attackHeight, InventoryItem item, int animAttack, int seed, GNDItemMap mapContent)
    {
        if (level.isClient())
        {
            SoundManager.playSound(GameResources.swoosh, SoundEffect.effect(attackerMob).volume(0.4F));
        }
    }

    public String canAttack(Level level, int x, int y, ItemAttackerMob attackerMob, InventoryItem item)
    {
        String out = super.canAttack(level, x, y, attackerMob, item);
        if (out != null)
        {
            return out;
        }
        else
        {
            return !attackerMob.isRiding() && !attackerMob.buffManager.hasBuff(BuffRegistry.Debuffs.GUARDIAN_SHELL_COOLDOWN) && !PacketForceOfWind.isOnCooldown(attackerMob) ? null : "";
        }
    }

    public boolean holdItemInFrontOfArms(InventoryItem item, Level level, PlayerMob player, int spriteX, int spriteY, int drawX, int drawY, int width, int height, boolean mirrorX, boolean mirrorY, GameLight light, boolean hasGlowEffect, int glowHash, float alpha, MaskShaderOptions mask) {
        return true;
    }
}