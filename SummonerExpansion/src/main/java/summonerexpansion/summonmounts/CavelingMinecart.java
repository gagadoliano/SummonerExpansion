package summonerexpansion.summonmounts;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.packet.PacketMobMount;
import necesse.engine.registries.MobRegistry;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.sound.SoundPlayer;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.explosionEvent.ExplosionEvent;
import necesse.entity.levelEvent.mobAbilityLevelEvent.ToolItemMobAbilityEvent;
import necesse.entity.mobs.*;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.summon.MinecartLinePos;
import necesse.entity.mobs.summon.MinecartLines;
import necesse.entity.mobs.summon.SummonedMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.gameObject.GameObject;
import necesse.level.gameObject.MinecartTrackObject;
import necesse.level.gameObject.TrapTrackObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class CavelingMinecart extends SummonedMob
{
    public static LootTable lootTable = new LootTable(new LootItem("cavelingminecart"));
    public float minecartSpeed;
    public int minecartDir;
    public float collisionMovementBuffer;
    public Point collisionMovementLastPos;
    protected SoundPlayer movingSound;
    protected SoundPlayer breakingSound;
    protected float breakParticleBuffer;
    protected boolean breakParticleAlternate;
    public static GameTexture texture;

    public CavelingMinecart()
    {
        super(1);
        isSummoned = true;
        setSpeed(200.0F);
        setFriction(3.0F);
        accelerationMod = 0.1F;
        setKnockbackModifier(0.1F);
        collision = new Rectangle(-10, -10, 20, 14);
        hitBox = new Rectangle(-14, -15, 28, 24);
        selectBox = new Rectangle(-14, -20, 28, 30);
        swimMaskMove = 8;
        swimMaskOffset = -2;
        swimSinkOffset = 0;
        overrideMountedWaterWalking = true;
        staySmoothSnapped = true;
    }

    public void addSaveData(SaveData save)
    {
        super.addSaveData(save);
        save.addInt("minecartDir", minecartDir);
        save.addFloat("minecartSpeed", minecartSpeed);
    }

    public void applyLoadData(LoadData save)
    {
        super.applyLoadData(save);
        minecartDir = save.getInt("minecartDir", minecartDir);
        minecartSpeed = save.getFloat("minecartSpeed", minecartSpeed);
    }

    public void setupMovementPacket(PacketWriter writer)
    {
        super.setupMovementPacket(writer);
        writer.putNextFloat(minecartSpeed);
        writer.putNextMaxValue(minecartDir, 3);
    }

    public void applyMovementPacket(PacketReader reader, boolean isDirect)
    {
        super.applyMovementPacket(reader, isDirect);
        minecartSpeed = reader.getNextFloat();
        minecartDir = reader.getNextMaxValue(3);
    }

    public void tickCurrentMovement(float delta) {
        super.tickCurrentMovement(delta);
    }

    public void tickMovement(float delta) {
        super.tickMovement(delta);
    }

    protected void tickCollisionMovement(float delta, Mob rider)
    {
        int tileX = getTileX();
        int tileY = getTileY();
        GameObject object = getLevel().getObject(tileX, tileY);
        if (object instanceof MinecartTrackObject && !(object instanceof TrapTrackObject))
        {
            MinecartTrackObject trackObject = (MinecartTrackObject)object;
            float colDx = this.colDx / 20.0F;
            float colDy = this.colDy / 20.0F;
            float moveX = this.moveX;
            float moveY = this.moveY;
            MinecartLines lines = trackObject.getMinecartLines(getLevel(), tileX, tileY, moveX, moveY, false);
            MinecartLinePos pos = lines.getMinecartPos(x, y, minecartDir);
            if (pos != null)
            {
                boolean breaking = false;
                float moving = 0.0F;
                if (minecartDir == 0)
                {
                    if (moveY < 0.0F)
                    {
                        moving = 1.0F;
                    }
                    else if (moveY > 0.0F)
                    {
                        breaking = true;
                        moving = -1.0F;
                    }
                    moving -= colDy;
                    colDx = 0.0F;
                }
                else if (minecartDir == 1)
                {
                    if (moveX > 0.0F)
                    {
                        moving = 1.0F;
                    }
                    else if (moveX < 0.0F)
                    {
                        breaking = true;
                        moving = -1.0F;
                    }
                    moving += colDx;
                    colDy = 0.0F;
                }
                else if (minecartDir == 2)
                {
                    if (moveY > 0.0F)
                    {
                        moving = 1.0F;
                    }
                    else if (moveY < 0.0F)
                    {
                        breaking = true;
                        moving = -1.0F;
                    }
                    moving += colDy;
                    colDx = 0.0F;
                }
                else
                {
                    if (moveX < 0.0F)
                    {
                        moving = 1.0F;
                    }
                    else if (moveX > 0.0F)
                    {
                        breaking = true;
                        moving = -1.0F;
                    }
                    moving -= colDx;
                    colDy = 0.0F;
                }
                if (colDx != 0.0F || colDy != 0.0F)
                {
                    if (getLevel().entityManager.players.streamArea(x, y, 100).filter(this::collidesWith).anyMatch((p) -> true))
                    {
                        collisionMovementBuffer = (float)((double)collisionMovementBuffer + GameMath.diagonalMoveDistance(colDx, colDy) * (double)delta / 250.0 * 20.0);
                    }
                    collisionMovementLastPos = new Point(getX(), getY());
                    movementUpdateTime = Math.min(movementUpdateTime, getWorldEntity().getTime() - (long)(movementUpdateCooldown - 1000));
                }
                float friction = rider == null ? 2.0F : 0.0F;
                float accMod = getAccelerationModifier();
                float speed = getSpeed();
                if (friction != 0.0F)
                {
                    minecartSpeed += (speed * friction * moving - friction * minecartSpeed) * delta / 250.0F * accMod;
                }
                else if (moving != 0.0F)
                {
                    minecartSpeed += (speed * moving - minecartSpeed) * delta / 250.0F * accMod;
                }
                if (minecartSpeed < 0.0F)
                {
                    minecartDir = (minecartDir + 2) % 4;
                    minecartSpeed = 0.0F;
                }
                if (moving == 0.0F && Math.abs(minecartSpeed) < speed / 40.0F)
                {
                    minecartSpeed = 0.0F;
                }
                if (minecartSpeed > 0.0F)
                {
                    MinecartLinePos resultPos = pos.progressLines(minecartDir, minecartSpeed * delta / 250.0F, null);
                    x = resultPos.x;
                    y = resultPos.y;
                    minecartDir = resultPos.dir;
                    setDir(minecartDir);
                    int dir = getDir();
                    dx = dir != 1 && dir != 3 ? 0.0F : minecartSpeed * (float)(dir == 1 ? 1 : -1);
                    dy = dir != 0 && dir != 2 ? 0.0F : minecartSpeed * (float)(dir == 2 ? 1 : -1);
                    if (resultPos.distanceRemainingToTravel > 0.0F)
                    {
                        if (!isServer() && minecartSpeed > 25.0F)
                        {
                            SoundManager.playSound(GameResources.cling, SoundEffect.effect(this).volume(0.6F).pitch(0.8F));
                        }
                        minecartSpeed = 0.0F;
                    }
                    else if (!isServer())
                    {
                        if (breaking && moving < 0.0F)
                        {
                            if (minecartSpeed > 10.0F)
                            {
                                breakParticleBuffer += delta;
                                if (breakParticleBuffer > 10.0F)
                                {
                                    breakParticleBuffer -= 10.0F;
                                    float xOffset = GameRandom.globalRandom.floatGaussian();
                                    float yOffset = GameRandom.globalRandom.floatGaussian();
                                    boolean alternate = breakParticleAlternate;
                                    if (minecartDir == 0)
                                    {
                                        xOffset += alternate ? 8.0F : -8.0F;
                                        yOffset += 4.0F;
                                    }
                                    else if (minecartDir == 1)
                                    {
                                        yOffset += alternate ? 6.0F : -6.0F;
                                        xOffset -= 4.0F;
                                    }
                                    else if (minecartDir == 2)
                                    {
                                        xOffset += alternate ? 8.0F : -8.0F;
                                        yOffset -= 4.0F;
                                    }
                                    else
                                    {
                                        yOffset += alternate ? 6.0F : -6.0F;
                                        xOffset += 4.0F;
                                    }
                                    float var10001 = x + xOffset;
                                    float var10002 = y + yOffset;
                                    getLevel().entityManager.addParticle(var10001, var10002, Particle.GType.IMPORTANT_COSMETIC).color(new Color(210, 160, 8)).sizeFadesInAndOut(4, 8, 50, 200).movesConstant(dx / 10.0F, dy / 10.0F).lifeTime(300).height(2.0F);
                                    breakParticleAlternate = !breakParticleAlternate;
                                }
                            }
                            if (breakingSound == null || breakingSound.isDone())
                            {
                                breakingSound = SoundManager.playSound(GameResources.trainBrake, SoundEffect.effect(this).falloffDistance(1400).volume(0.0F));
                            }
                            if (breakingSound != null)
                            {
                                breakingSound.effect.volume(GameMath.limit((minecartSpeed - 10.0F) / 100.0F, 0.0F, 1.0F) * 1.5F);
                                breakingSound.refreshLooping(0.5F);
                            }
                        }
                        if (movingSound == null || movingSound.isDone())
                        {
                            movingSound = SoundManager.playSound(GameResources.train, SoundEffect.effect(this).falloffDistance(1400).volume(0.0F));
                        }
                        if (movingSound != null)
                        {
                            movingSound.effect.volume(Math.min(minecartSpeed / 200.0F, 1.0F) / 1.5F);
                            movingSound.refreshLooping(0.2F);
                        }
                    }
                }
                else
                {
                    x = pos.x;
                    y = pos.y;
                    if (pos.dir != 1 && pos.dir != 3)
                    {
                        if (minecartDir == 1 || minecartDir == 3)
                        {
                            minecartDir = pos.dir;
                        }
                    }
                    else if (minecartDir == 0 || minecartDir == 2)
                    {
                        minecartDir = pos.dir;
                    }
                    setDir(minecartDir);
                    dx = 0.0F;
                    dy = 0.0F;
                }
            }
            else
            {
                minecartSpeed = 0.0F;
                minecartDir = rider == null ? getDir() : rider.getDir();
                dx = 0.0F;
                dy = 0.0F;
            }
        }
        else
        {
            minecartDir = rider == null ? getDir() : rider.getDir();
            dx = 0.0F;
            dy = 0.0F;
            if (colDx != 0.0F || colDy != 0.0F)
            {
                if (getLevel().entityManager.players.streamArea(x, y, 100).filter(this::collidesWith).anyMatch((p) -> true))
                {
                    collisionMovementBuffer = (float)((double)collisionMovementBuffer + GameMath.diagonalMoveDistance(colDx, colDy) * (double)delta / 250.0);
                }
                collisionMovementLastPos = new Point(getX(), getY());
                movementUpdateTime = Math.min(movementUpdateTime, getWorldEntity().getTime() - (long)(movementUpdateCooldown - 1000));
            }
            super.tickCollisionMovement(delta, rider);
        }
        if (collisionMovementBuffer >= 5.0F || collisionMovementLastPos != null && GameMath.diagonalMoveDistance(collisionMovementLastPos, new Point(getX(), getY())) > 32.0)
        {
            collisionMovementBuffer = 0.0F;
            collisionMovementLastPos = null;
            sendMovementPacket(false);
        }
    }

    public void serverTick()
    {
        super.serverTick();
        if (!isMounted())
        {
            moveX = 0.0F;
            moveY = 0.0F;
        }
    }

    public void clientTick()
    {
        super.clientTick();
        if (!isMounted())
        {
            moveX = 0.0F;
            moveY = 0.0F;
        }
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public void spawnDamageText(int damage, int size, boolean isCrit) {
    }

    public boolean canBeHit(Attacker attacker)
    {
        if (!super.canBeHit(attacker))
        {
            return false;
        }
        else
        {
            return !(attacker instanceof ToolItemMobAbilityEvent) && !(attacker instanceof ExplosionEvent) ? false : attacker.getAttackOwners().stream().anyMatch((m) -> m.isPlayer);
        }
    }

    public void playHitSound() {
    }

    public void interact(PlayerMob player)
    {
        if (isServer())
        {
            if (player.getUniqueID() == rider)
            {
                player.dismount();
                getLevel().getServer().network.sendToClientsWithEntity(new PacketMobMount(player.getUniqueID(), -1, false, player.x, player.y), player);
            }
            else if (player.mount(this, false))
            {
                getLevel().getServer().network.sendToClientsWithEntity(new PacketMobMount(player.getUniqueID(), getUniqueID(), false, player.x, player.y), player);
            }
        }

    }

    public boolean canInteract(Mob mob) {
        return !isMounted() || mob.getUniqueID() == rider;
    }

    protected String getInteractTip(PlayerMob perspective, boolean debug)
    {
        return isMounted() ? null : Localization.translate("controls", "usetip");
    }

    public void playDeathSound() {
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 47;
        Point sprite = getAnimSprite(x, y, minecartDir);
        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        final MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        final DrawOptions behind = texture.initDraw().sprite(sprite.x, sprite.y, 64).addMaskShader(swimMask).light(light).pos(drawX, drawY);
        list.add(new MobDrawable()
        {
            public void draw(TickManager tickManager) {
            }
            public void drawBehindRider(TickManager tickManager)
            {
                swimMask.use();
                behind.draw();
                swimMask.stop();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public static void drawPlacePreview(Level level, int levelX, int levelY, int dir, GameCamera camera)
    {
        Mob mob = MobRegistry.getMob("cavelingminecart", level);
        int tileX;
        int tileY;
        if (mob != null)
        {
            mob.setPos((float)levelX, (float)levelY, true);
            tileX = mob.getTileX();
            tileY = mob.getTileY();
            GameObject object = level.getObject(tileX, tileY);
            if (object instanceof MinecartTrackObject)
            {
                MinecartTrackObject trackObject = (MinecartTrackObject)object;
                float moveX = 0.0F;
                float moveY = 0.0F;
                if (dir == 0)
                {
                    moveY = -1.0F;
                }
                else if (dir == 1)
                {
                    moveX = 1.0F;
                }
                else if (dir == 2)
                {
                    moveY = 1.0F;
                }
                else
                {
                    moveX = -1.0F;
                }
                MinecartLines lines = trackObject.getMinecartLines(level, tileX, tileY, moveX, moveY, false);
                MinecartLinePos pos = lines.getMinecartPos((float)levelX, (float)levelY, dir);
                if (pos != null)
                {
                    int drawX = camera.getDrawX(pos.x) - 32;
                    int drawY = camera.getDrawY(pos.y) - 47;
                    Point sprite = mob.getAnimSprite((int)pos.x, (int)pos.y, pos.dir);
                    drawY += mob.getBobbing((int)pos.x, (int)pos.y);
                    drawY += level.getTile((int)pos.x / 32, (int)pos.y / 32).getMobSinkingAmount(mob);
                    texture.initDraw().sprite(sprite.x, sprite.y, 64).alpha(0.5F).draw(drawX, drawY);
                    return;
                }
            }
        }
        tileX = camera.getDrawX(levelX) - 32;
        tileY = camera.getDrawY(levelY) - 47;
        tileY += level.getLevelTile(levelX / 32, levelY / 32).getLiquidBobbing();
        texture.initDraw().sprite(0, dir, 64).alpha(0.5F).draw(tileX, tileY);
    }

    public Point getAnimSprite(int x, int y, int dir)
    {
        Point p = new Point(0, dir);
        if (!inLiquid(x, y))
        {
            p.x = (int)(getDistanceRan() / (double)getRockSpeed()) % 2;
        }
        return p;
    }

    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera)
    {
        return getShadowDrawOptions(this, x, y, 0, minecartDir, light, camera);
    }

    public static TextureDrawOptions getShadowDrawOptions(Mob mob, int x, int y, int yOffset, int dir, GameLight light, GameCamera camera)
    {
        GameTexture shadowTexture = MobRegistry.Textures.minecart_shadow;
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 47 + yOffset;
        drawY += mob.getBobbing(x, y);
        drawY += mob.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(mob);
        return shadowTexture.initDraw().sprite(0, dir, 64).light(light).pos(drawX, drawY);
    }

    public int getRockSpeed() {
        return 10;
    }

    public int getWaterRockSpeed() {
        return 10000;
    }

    public Point getSpriteOffset(int spriteX, int spriteY)
    {
        Point p = new Point(0, 0);
        p.x += getRiderDrawXOffset();
        p.y += getRiderDrawYOffset();
        return p;
    }

    public int getRiderDrawYOffset()
    {
        return getSwimMaskShaderOptions(inLiquidFloat(getDrawX(), getDrawY())).drawYOffset - 6;
    }

    public int getRiderArmSpriteX() {
        return 0;
    }

    public GameTexture getRiderMask()
    {
        return MobRegistry.Textures.minecart_mask[GameMath.limit(minecartDir, 0, MobRegistry.Textures.minecart_mask.length - 1)];
    }

    public int getRiderMaskYOffset() {
        return -10;
    }

    public boolean isWaterWalking()
    {
        GameObject object = getLevel().getObject(getTileX(), getTileY());
        return object instanceof MinecartTrackObject ? true : super.isWaterWalking();
    }

    public Stream<ModifierValue<?>> getDefaultRiderModifiers()
    {
        return Stream.of(new ModifierValue(BuffModifiers.WATER_WALKING, true), new ModifierValue(BuffModifiers.ITEM_PICKUP_RANGE, 5F));
    }
}