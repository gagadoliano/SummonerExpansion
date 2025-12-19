package summonerexpansion.items.equips.armorsetbonus;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.BuffAbility;
import necesse.entity.mobs.buffs.BuffEventSubscriber;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.armorBuffs.setBonusBuffs.SetBonusBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.mobs.itemAttacker.MobFollower;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.item.ItemStatTip;
import necesse.inventory.item.upgradeUtils.FloatUpgradeValue;
import necesse.inventory.item.upgradeUtils.IntUpgradeValue;
import summonerexpansion.allprojs.CopperSetProj;
import summonerexpansion.codes.registry.SummonerBuffs;
import summonerexpansion.codes.registry.SummonerEquips;

import java.util.LinkedList;

public class CopperMinerSetBonus extends SetBonusBuff implements BuffAbility
{
    public FloatUpgradeValue fireDamage = (new FloatUpgradeValue()).setBaseValue(0F).setUpgradedValue(1, 50.0F).setUpgradedValue(10, 100.0F);
    public FloatUpgradeValue summonRange = (new FloatUpgradeValue()).setBaseValue(0F).setUpgradedValue(1, 0.20F).setUpgradedValue(10, 0.80F);
    public IntUpgradeValue maxSummons = (new IntUpgradeValue()).setBaseValue(1).setUpgradedValue(1, 2).setUpgradedValue(10, 4);
    protected int abilityCooldown = 6;
    protected int timeBetweenShots = 150;
    public int targetX;
    public int targetY;

    public CopperMinerSetBonus() {}

    public void init(ActiveBuff buff, BuffEventSubscriber eventSubscriber)
    {
        buff.setModifier(BuffModifiers.MAX_SUMMONS, maxSummons.getValue(buff.getUpgradeTier()));
        buff.setModifier(BuffModifiers.SUMMONS_TARGET_RANGE, summonRange.getValue(buff.getUpgradeTier()));
    }

    public void serverTick(ActiveBuff buff)
    {
        long nextShotTime = buff.getGndData().getLong("nextShotTime");
        if (nextShotTime != 0L)
        {
            long currentTime = buff.owner.getTime();
            if (currentTime >= nextShotTime)
            {
                if (shotNextSummon(buff))
                {
                    buff.getGndData().setLong("nextShotTime", currentTime + (long)timeBetweenShots);
                }
                else
                {
                    buff.getGndData().clearItem("nextShotTime");
                }
            }
        }
    }

    public Packet getAbilityContent(PlayerMob player, ActiveBuff buff, GameCamera camera)
    {
        Packet content = new Packet();
        PacketWriter writer = new PacketWriter(content);
        writer.putNextInt(camera.getMouseLevelPosX());
        writer.putNextInt(camera.getMouseLevelPosY());
        return content;
    }

    public boolean canRunAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        return player.buffManager.getStacks(BuffRegistry.SUMMONED_MOB) > 0 && !buff.owner.buffManager.hasBuff(SummonerEquips.SummonerArmorBuffs.COPPERSET_COOLDOWN) && !buff.owner.buffManager.hasBuff(SummonerBuffs.SummonBuffs.COPPERSET_CONSECUTIVE);
    }

    public void runAbility(PlayerMob player, ActiveBuff buff, Packet content)
    {
        PacketReader reader = new PacketReader(content);
        targetX = reader.getNextInt();
        targetY = reader.getNextInt();

        player.buffManager.addBuff(new ActiveBuff(SummonerEquips.SummonerArmorBuffs.COPPERSET_COOLDOWN, player, abilityCooldown, null), false);
        buff.getGndData().setLong("nextShotTime", buff.owner.getTime());
        timeBetweenShots = 150;
    }

    protected boolean shotNextSummon(ActiveBuff ab)
    {
        if (!(ab.owner instanceof ItemAttackerMob))
        {
            return false;
        }
        else
        {
            MobFollower firstMob = ((ItemAttackerMob)ab.owner).serverFollowersManager.streamFollowers().filter((e) -> e.buffType != null && e.buffType.equals("summonedmob")).findAny().orElse(null);
            if (firstMob == null)
            {
                return false;
            }
            else
            {
                Mob chosenMob = firstMob.mob;
                timeBetweenShots = (int)((double)timeBetweenShots - GameMath.max((double)timeBetweenShots * 0.02, 8.0F));
                GameDamage damage = new GameDamage(DamageTypeRegistry.SUMMON, fireDamage.getValue(ab.getUpgradeTier()));
                float velocity = 150.0F * ab.owner.buffManager.getModifier(BuffModifiers.PROJECTILE_VELOCITY);
                CopperSetProj projectile = new CopperSetProj(ab.owner.getLevel(), ab.owner, firstMob.mob.x, firstMob.mob.y, (float)targetX, (float)targetY, velocity, 800, damage, 0);
                ab.owner.getLevel().entityManager.projectiles.add(projectile);
                chosenMob.remove(0.0F, 0.0F, null, true);
                ActiveBuff stackBuff = new ActiveBuff(SummonerBuffs.SummonBuffs.COPPERSET_CONSECUTIVE, ab.owner, 10.0F, ab.owner);
                ab.owner.addBuff(stackBuff, true);
                ActiveBuff cooldownBuff = ab.owner.buffManager.getBuff(SummonerEquips.SummonerArmorBuffs.COPPERSET_COOLDOWN.getID());
                if (cooldownBuff != null && cooldownBuff.getDurationLeft() < stackBuff.getDuration())
                {
                    ab.owner.buffManager.addBuff(new ActiveBuff(SummonerEquips.SummonerArmorBuffs.COPPERSET_COOLDOWN, ab.owner, stackBuff.getDuration(), null), true);
                }
                return true;
            }
        }
    }

    public ListGameTooltips getTooltip(ActiveBuff ab, GameBlackboard blackboard)
    {
        ListGameTooltips tooltips = super.getTooltip(ab, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "copperminersettip"));
        if (fireDamage.getValue(ab.getUpgradeTier()) > 0)
        {
            tooltips.add(Localization.translate("itemtooltip", "copperminersettip2"));
        }
        return tooltips;
    }

    public void addStatTooltips(LinkedList<ItemStatTip> list, ActiveBuff currentValues, ActiveBuff lastValues)
    {
        super.addStatTooltips(list, currentValues, lastValues);
        currentValues.getModifierTooltipsBuilder(true, true).addLastValues(lastValues).buildToStatList(list);
    }
}