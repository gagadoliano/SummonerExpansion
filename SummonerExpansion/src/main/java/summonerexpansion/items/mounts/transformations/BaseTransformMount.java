package summonerexpansion.items.mounts.transformations;

import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MountAbility;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.summon.summonFollowingMob.mountFollowingMob.MountFollowingMob;
import necesse.gfx.camera.GameCamera;

import java.awt.Point;

import static summonerexpansion.codes.registries.RegistrySummonModifiers.TRANSFORMATION_SPEED;

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
                return attackOwner.buffManager.getModifier(TRANSFORMATION_SPEED) * super.getSpeedModifier();
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

    /**
     * Runs on the client that triggered the ability, so the camera here belongs to
     * the player who actually aimed. The server and the other clients then read the
     * aim back out of the relayed content packet rather than consulting a camera of
     * their own, which on a dedicated server does not exist at all.
     */
    public Packet getMountAbilityContent(PlayerMob player, GameCamera camera)
    {
        Packet content = new Packet();
        PacketWriter writer = new PacketWriter(content);
        writer.putNextInt(camera.getMouseLevelPosX());
        writer.putNextInt(camera.getMouseLevelPosY());
        return content;
    }

    /**
     * Returns null when the content packet carries no aim, which callers must treat
     * as "do not fire". The packet crosses the network, so a client running an older
     * build of the mod can deliver an empty one.
     */
    protected static Point readAimTarget(Packet content)
    {
        if (content == null || content.getSize() < Packet.INT_SIZE * 2)
        {
            return null;
        }
        PacketReader reader = new PacketReader(content);
        return new Point(reader.getNextInt(), reader.getNextInt());
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