package summonerexpansion.items.mounts.transformations;

import necesse.engine.Settings;
import necesse.engine.input.Control;
import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.entity.mobs.ActiveMountAbility;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.staticBuffs.StaminaBuff;
import necesse.entity.mobs.summon.summonFollowingMob.mountFollowingMob.MountFollowingMob;

import static summonerexpansion.codes.registries.RegistrySummonModifiers.TRANSFORMATION_SPEED;

public class BaseActiveTransformMount extends MountFollowingMob implements ActiveMountAbility
{
    public BaseActiveTransformMount(Float baseSpeed)
    {
        super(100);
        setSpeed(baseSpeed);
    }

    public float getSpeedModifier()
    {
        if (isFollowing())
        {
            Mob attackOwner = getAttackOwner();
            if (attackOwner != null)
            {
                return attackOwner.buffManager.getModifier(TRANSFORMATION_SPEED) * super.getSpeedModifier();
            }
        }
        return super.getSpeedModifier();
    }

    public boolean canRunMountAbility(PlayerMob player, Packet content)
    {
        return player.isServer() && !Settings.strictServerAuthority || StaminaBuff.canStartStaminaUsage(player);
    }

    public boolean tickActiveMountAbility(PlayerMob player, boolean isRunningClient)
    {
        return !isRunningClient || Control.TRINKET_ABILITY.isDown();
    }

    public void onActiveMountAbilityStarted(PlayerMob player, Packet content) {

    }

    public void onActiveMountAbilityUpdate(PlayerMob player, Packet content) {
    }

    public void onActiveMountAbilityStopped(PlayerMob player) {

    }

    protected String getInteractTip(PlayerMob perspective, boolean debug)
    {
        return isMounted() ? null : Localization.translate("controls", "usetip");
    }

    public boolean shouldDrawRider() {
        return false;
    }
}