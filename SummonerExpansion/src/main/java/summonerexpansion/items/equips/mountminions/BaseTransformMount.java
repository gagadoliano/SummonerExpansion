package summonerexpansion.items.equips.mountminions;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MountAbility;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.summon.summonFollowingMob.mountFollowingMob.MountFollowingMob;
import summonerexpansion.codes.registry.SummonerModifiers;

public class BaseTransformMount extends MountFollowingMob implements MountAbility
{
    public int abilityCooldown = 0;

    public BaseTransformMount(Float baseSpeed)
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
                return attackOwner.buffManager.getModifier(SummonerModifiers.TRANSFORMATION_SPEED) * super.getSpeedModifier();
            }
        }
        return super.getSpeedModifier();
    }

    public void runMountAbility(PlayerMob player, Packet content)
    {

    }

    public boolean canRunMountAbility(PlayerMob player, Packet content) {
        return abilityCooldown == 0;
    }

    public void serverTick()
    {
        super.serverTick();
        if (abilityCooldown > 0)
        {
            abilityCooldown--;
        }
    }

    public void clientTick()
    {
        super.clientTick();
        if (abilityCooldown > 0)
        {
            abilityCooldown--;
        }
    }

    protected String getInteractTip(PlayerMob perspective, boolean debug)
    {
        return isMounted() ? null : Localization.translate("controls", "usetip");
    }

    public boolean shouldDrawRider() {
        return false;
    }
}