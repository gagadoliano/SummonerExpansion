package summonerexpansion.codes.pickups;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.ClientClient;
import necesse.engine.network.packet.PacketPickupEntityPickup;
import necesse.engine.network.server.ServerClient;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.particle.Particle;
import necesse.entity.pickup.PickupEntity;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.hudManager.floatText.DamageText;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryBuffs.WeaponBuffs.SPIDERSWORDSTACKS;
import static summonerexpansion.codes.registries.RegistryParticlesTextures.spiderHeartPickup;

public class SpiderSwordPickupEntity extends PickupEntity
{
    protected ParticleTypeSwitcher particleTypeSwitcher;
    protected int tickCounter;
    public float healPercent;

    public SpiderSwordPickupEntity()
    {
        particleTypeSwitcher = new ParticleTypeSwitcher(Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC, Particle.GType.CRITICAL);
        healPercent = 0.01F;
    }

    public SpiderSwordPickupEntity(Level level, float x, float y, float dx, float dy)
    {
        super(level, x, y, dx, dy);
        particleTypeSwitcher = new ParticleTypeSwitcher(Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC, Particle.GType.CRITICAL);
        healPercent = 0.01F;
        bouncy = 0.75F;
    }

    public void init()
    {
        super.init();
        if (isClient())
        {
            GameRandom random = GameRandom.globalRandom;
            for(int i = 0; i < 10; ++i)
            {
                getLevel().entityManager.addParticle(x + random.floatGaussian() * 16.0F, y + random.floatGaussian() * 12.0F, Particle.GType.IMPORTANT_COSMETIC).sizeFades(24, 12).sprite(GameResources.bubbleParticle.sprite(0, 0, 12)).color(new Color(28, 113, 44)).movesFrictionAngle((float)random.getIntBetween(0, 360), 50.0F, 0.5F).lifeTime(1000);
            }
        }
    }

    public void clientTick()
    {
        ++tickCounter;
        if (tickCounter > 200)
        {
            remove();
        }
        else
        {
            super.clientTick();
            getLevel().entityManager.addParticle(x + (float)(GameRandom.globalRandom.nextGaussian() * (double)8.0F), y + (float)(GameRandom.globalRandom.nextGaussian() * (double)6.0F), particleTypeSwitcher.next()).color(new Color(35, 110, 17)).movesFriction(0.0F, -30.0F, 0.8F).givesLight(353.0F, 0.8F).ignoreLight(true).lifeTime(500);
        }
    }

    public void serverTick()
    {
        ++tickCounter;
        if (tickCounter > 200)
        {
            remove();
        }
        else
        {
            super.serverTick();
        }
    }

    public boolean isValidTarget(ServerClient client)
    {
        return client.playerMob.getHealth() < client.playerMob.getMaxHealth();
    }

    public void onPickup(ServerClient client)
    {
        int healthGained = Math.max(1, (int)((float)client.playerMob.getMaxHealth() * healPercent));
        client.playerMob.setHealth(client.playerMob.getHealth() + healthGained);
        Packet content = new Packet();
        PacketWriter writer = new PacketWriter(content);
        writer.putNextInt(healthGained);
        writer.putNextInt(client.playerMob.getHealth());
        getLevel().getServer().network.sendToClientsWithEntity(new PacketPickupEntityPickup(this, content), this);

        client.playerMob.buffManager.addBuff(new ActiveBuff(SPIDERSWORDSTACKS, client.playerMob, 10F, null), true);

        remove();
    }

    public void onPickup(ClientClient client, Packet data)
    {
        super.onPickup(client, data);
        if (client.playerMob != null)
        {
            Level level = client.playerMob.getLevel();
            if (level != null)
            {
                PacketReader reader = new PacketReader(data);
                int healthGained = reader.getNextInt();
                int finalHealth = reader.getNextInt();
                client.playerMob.setHealth(finalHealth);
                if (level.isSamePlace(client.getClient().getLevel()))
                {
                    SoundManager.playSound(GameResources.fadedeath2, SoundEffect.effect(client.playerMob).volume(0.5F).pitch(1.6F));
                    level.hudManager.addElement(new DamageText(client.playerMob, healthGained, Color.GREEN, GameRandom.globalRandom.getIntBetween(30, 40)));
                }
                remove();
            }
        }
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        GameLight light = level.getLightLevel((int)Math.floor((double)x / (double)32.0F), (int)Math.floor((double)y / (double)32.0F));
        int drawX = camera.getDrawX(x);
        int drawY = camera.getDrawY(y) - 16;
        int timePerFrame = 100;
        int spriteIndex = (int)(getWorldEntity().getTime() / (long)timePerFrame) % 4;
        final DrawOptions pickupOptions = spiderHeartPickup.initDraw().sprite(spriteIndex, 0, 32).light(light).size(32 - spriteIndex * 2, 32 - spriteIndex * 2).pos(drawX - 16 + spriteIndex, drawY - 16 + spriteIndex);
        final DrawOptions pickupShadowOptions = GameResources.item_shadow.initDraw().light(light).size(24, 16).pos(drawX - 12, drawY + 8);
        topList.add(new LevelSortedDrawable(this)
        {
            public int getSortY() {
                return 0;
            }

            public void draw(TickManager tickManager)
            {
                pickupShadowOptions.draw();
                pickupOptions.draw();
            }
        });
    }
}