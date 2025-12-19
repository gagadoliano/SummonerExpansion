package summonerexpansion.items.equips.mounts;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.MountAbility;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.summon.summonFollowingMob.mountFollowingMob.MountFollowingMob;

import java.awt.*;

public class BaseTransformMount extends MountFollowingMob implements MountAbility
{
    public int abilityCooldown = 0;

    public BaseTransformMount()
    {
        super(100);
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