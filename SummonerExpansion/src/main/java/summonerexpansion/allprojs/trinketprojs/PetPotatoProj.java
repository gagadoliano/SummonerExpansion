package summonerexpansion.allprojs.trinketprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.explosionEvent.ExplosionEvent;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.particle.Particle;
import necesse.entity.projectile.Projectile;
import necesse.entity.trails.Trail;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameSprite;
import necesse.inventory.InventoryItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class PetPotatoProj extends Projectile
{
    protected float deltaHeight;
    protected float boulderHeight;

    public PetPotatoProj() {
    }

    public PetPotatoProj(Level level, Mob owner, float x, float y, float targetX, float targetY, float speed, int distance, GameDamage damage, int knockback)
    {
        this.setLevel(level);
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.setTarget(targetX, targetY);
        this.setDamage(damage);
        this.knockback = knockback;
        this.setDistance(distance);
        this.setOwner(owner);
        this.boulderHeight = 18.0F;
        this.deltaHeight = 50.0F;
    }

    public void setupPositionPacket(PacketWriter writer)
    {
        super.setupPositionPacket(writer);
        writer.putNextFloat(deltaHeight);
        writer.putNextFloat(boulderHeight);
    }

    public void applyPositionPacket(PacketReader reader)
    {
        super.applyPositionPacket(reader);
        deltaHeight = reader.getNextFloat();
        boulderHeight = reader.getNextFloat();
    }

    public void init()
    {
        super.init();
        spawnTime = getWorldEntity().getTime();
        isSolid = true;
        width = 32.0F;
        trailOffset = 0.0F;
        bouncing = 2;
        dropItem = true;
    }

    protected void dropItem()
    {
        super.dropItem();
        if (GameRandom.globalRandom.nextInt(100) <= 10)
        {
            getLevel().entityManager.pickups.add((new InventoryItem("potato")).getPickupEntity(getLevel(), x, y));
        }
    }

    public float tickMovement(float delta)
    {
        float out = super.tickMovement(delta);
        float heightChange = 50.0F * delta / 250.0F;
        deltaHeight -= heightChange;
        boulderHeight += deltaHeight * delta / 250.0F;
        if (boulderHeight < 0.0F)
        {
            if (isClient())
            {
                SoundManager.playSound(GameResources.punch, SoundEffect.effect(this).volume(0.5F).pitch(1.0F));
                ExplosionEvent.spawnExplosionParticles(getLevel(), x, y, 20, 0.0F, 20.0F, (level, x, y, dirX, dirY, lifeTime, range) -> level.entityManager.addParticle(x, y, Particle.GType.CRITICAL).movesConstant(dirX, dirY).color(new Color(130, 85, 59)).lifeTime(lifeTime));
                SoundManager.playSound(GameResources.blunthit, SoundEffect.effect(this).volume(0.45F).falloffDistance(1000));
            }
            deltaHeight = -deltaHeight * 0.95F;
            boulderHeight = -boulderHeight;
            if (Math.abs(deltaHeight) < heightChange * 2.0F)
            {
                boulderHeight = -1.0F;
                deltaHeight = 0.0F;
            }
        }
        height = Math.max(boulderHeight, 0.0F);
        return out;
    }

    public Trail getTrail()
    {
        Trail trail = new Trail(this, getLevel(), new Color(130, 85, 59), 0.0F, 500, getHeight());
        trail.sprite = new GameSprite(GameResources.chains, 7, 0, 32);
        return trail;
    }

    public float getTrailThickness() {
        return 30.0F;
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!removed())
        {
            GameLight light = level.getLightLevel(getTileX(), getTileY());
            int drawX = camera.getDrawX(x) - texture.getWidth() / 2;
            int drawY = camera.getDrawY(y) - texture.getHeight() / 2;
            float angle = (float)(getWorldEntity().getTime() - spawnTime) / 1.5F;
            if (dx < 0.0F)
            {
                angle = -angle;
            }
            final TextureDrawOptions options = texture.initDraw().light(light).rotate(angle, texture.getWidth() / 2, texture.getHeight() / 2).pos(drawX, drawY - (int)getHeight());
            list.add(new EntityDrawable(this)
            {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            float shadowAlpha = Math.abs(GameMath.limit(height / 100.0F, 0.0F, 1.0F) - 1.0F);
            float sizeMod = Math.abs(GameMath.limit(height / 100.0F, 0.0F, 1.0F) - 1.0F);
            int shadowWidth = (int)((float)shadowTexture.getWidth() * sizeMod);
            int shadowHeight = (int)((float)shadowTexture.getHeight() * sizeMod);
            int shadowX = camera.getDrawX(x) - shadowWidth / 2;
            int shadowY = camera.getDrawY(y) - shadowHeight / 2;
            TextureDrawOptions shadowOptions = shadowTexture.initDraw().size(shadowWidth, shadowHeight).light(light).alpha(shadowAlpha).pos(shadowX, shadowY);
            tileList.add((tm) -> shadowOptions.draw());
        }
    }
}
