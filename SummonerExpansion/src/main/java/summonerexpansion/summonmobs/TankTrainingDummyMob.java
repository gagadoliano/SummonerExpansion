package summonerexpansion.summonmobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.packet.PacketShowDPS;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.DPSTracker;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import summonerexpansion.summonothers.TankTrainingDummyObjectEntity;

import java.awt.*;
import java.util.List;

public class TankTrainingDummyMob extends Mob
{
    public DPSTracker trainingDummyDPSTracker;
    private int aliveTimer;

    public TankTrainingDummyMob()
    {
        super(Integer.MAX_VALUE);
        trainingDummyDPSTracker = new DPSTracker();
        this.setArmor(1000);
        this.setSpeed(0.0F);
        this.setFriction(1000.0F);
        this.setKnockbackModifier(0.0F);
        this.collision = new Rectangle(-10, -7, 20, 14);
        this.hitBox = new Rectangle(-18, -15, 36, 30);
        this.selectBox = new Rectangle(-14, -41, 28, 48);
        this.shouldSave = false;
        this.aliveTimer = 20;
        this.isStatic = true;
    }

    public void clientTick()
    {
        super.clientTick();
        this.tickAlive();
    }

    public void serverTick()
    {
        super.serverTick();
        long currentTime = this.getWorldEntity().getTime();
        this.trainingDummyDPSTracker.tick(currentTime);
        if (this.getLevel().tickManager().isFirstGameTickInSecond() && this.trainingDummyDPSTracker.isLastHitBeforeReset(currentTime))
        {
            float dps = (float)this.trainingDummyDPSTracker.getDPS(currentTime);
            if (this.isServer())
            {
                this.getLevel().getServer().network.sendToClientsWithEntity(new PacketShowDPS(this.getUniqueID(), dps), this);
            }
        }
        this.tickAlive();
    }

    public boolean canBeTargetedFromAdjacentTiles() {
        return true;
    }

    private void tickAlive()
    {
        this.setHealthHidden(this.getMaxHealth());
        --this.aliveTimer;
        if (this.aliveTimer <= 0)
        {
            this.remove();
        }
    }

    public void keepAlive(TankTrainingDummyObjectEntity entity)
    {
        this.aliveTimer = 20;
        this.setPos((float)(entity.tileX * 32 + 16), (float)(entity.tileY * 32 + 16), true);
    }

    public void playHitSound()
    {
        SoundManager.playSound(GameResources.blunthit, SoundEffect.effect(this).volume(0.7F).pitch(GameRandom.globalRandom.getFloatBetween(0.9F, 1.1F)));
    }

    public void playHitDeathSound() {
    }

    public void playDeathSound() {
    }

    public boolean canBePushed(Mob other) {
        return false;
    }

    protected int getDrawSortY(Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective, boolean fromMount) {
        return this.getTileY() * 32 + 20;
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
    }

    public boolean isHealthBarVisible() {
        return false;
    }

    public boolean canTakeDamage() {
        return true;
    }

    public boolean countDamageDealt() {
        return false;
    }

    public boolean canPushMob(Mob other) {
        return false;
    }

    public MobWasHitEvent isHit(MobWasHitEvent event, Attacker attacker)
    {
        if (getLevel() != null)
        {
            getLevel().forceGrassWeave(this.getTileX(), this.getTileY(), 200);
        }
        return super.isHit(event, attacker);
    }

    protected void doWasHitLogic(MobWasHitEvent event)
    {
        super.doWasHitLogic(event);
        this.setHealthHidden(this.getMaxHealth());
    }

    public boolean canGiveResilience(Attacker attacker)
    {
        if (attacker != null)
        {
            PlayerMob attackOwner = attacker.getFirstPlayerOwner();
            if (attackOwner != null)
            {
                return !attackOwner.buffManager.hasBuff(BuffRegistry.BOSS_NEARBY);
            }
        }
        return super.canGiveResilience(attacker);
    }

    public void setHealthHidden(int health, float knockbackX, float knockbackY, Attacker attacker, boolean fromNetworkUpdate)
    {
        int beforeHealth = this.getHealth();
        super.setHealthHidden(health, knockbackX, knockbackY, attacker, fromNetworkUpdate);
        if (this.getLevel() != null)
        {
            int afterHealth = this.getHealth();
            if (afterHealth < beforeHealth)
            {
                int delta = beforeHealth - afterHealth;
                this.trainingDummyDPSTracker.addHit(this.getWorldEntity().getTime(), (float)delta);
                if (this.isServer() && attacker != null)
                {
                    Mob attackOwner = attacker.getAttackOwner();
                    if (attackOwner != null && attackOwner.isPlayer)
                    {
                        ServerClient serverClient = ((PlayerMob)attackOwner).getServerClient();
                        serverClient.trainingDummyDPSTracker.addHit(this.getWorldEntity().getTime(), (float)delta);
                    }
                }
            }
        }

    }

    public boolean onMouseHover(GameCamera camera, PlayerMob perspective, boolean debug)
    {
        return !debug ? false : super.onMouseHover(camera, perspective, debug);
    }

    public float getArmorAfterPen(float armorPen) {
        return this.getArmor() - armorPen;
    }
}
