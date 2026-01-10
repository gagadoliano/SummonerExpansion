package summonerexpansion.mobs.summonmobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.NetworkClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.ConfusedCollisionPlayerChaserWandererAI;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.mobs.misc.NetableMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registry.SummonerTextures.honeyBeeGuard;

public class HoneyBeeGuardMob extends HostileMob implements NetableMob
{
    public static LootTable lootTable = new LootTable(new LootItem("honeybeeguardmob"));
    public static MaxHealthGetter MAX_HEALTH = new MaxHealthGetter(100, 200, 300, 400, 500);
    public static GameDamage damage = new GameDamage(20F);
    public static GameTexture texture;

    public HoneyBeeGuardMob()
    {
        super(100);
        difficultyChanges.setMaxHealth(MAX_HEALTH);
        setSpeed(40.0F);
        setFriction(2.0F);
        collision = new Rectangle(-7, -5, 14, 10);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle(-16, -28, 32, 34);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new ConfusedCollisionPlayerChaserWandererAI<>(null, 50, damage, 10, 40));
    }

    public int getFlyingHeight() {
        return 20;
    }

    public boolean canTakeDamage() {
        return false;
    }

    public boolean canBeTargeted(Mob attacker, NetworkClient attackerClient) {
        return true;
    }

    public boolean canPushMob(Mob other) {
        return false;
    }

    public boolean canBePushed(Mob other) {
        return false;
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 22;
        int dir = this.getDir();
        int animationTime = 1000;
        long time = level.getTime();
        time += (new GameRandom(this.getUniqueID())).nextInt(animationTime);
        Point sprite = this.getAnimSprite(x, y, dir);
        float bobbingFloat = GameUtils.getBobbing(time, animationTime);
        float animProgress = 0.8F / 0.2F;
        bobbingFloat *= animProgress;
        float cosProgress = (float)Math.cos((double)animProgress * Math.PI) / 2.0F + 0.5F;
        drawY = (int)((float)drawY + cosProgress * 12.0F);
        drawY -= 6;
        drawY = (int)((float)drawY + bobbingFloat * 5.0F);
        final DrawOptions options = texture.initDraw().sprite(sprite.x, sprite.y, 32).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    public Point getAnimSprite(int x, int y, int dir)
    {
        long time = this.getTime();
        time += (new GameRandom(this.getUniqueID())).nextInt(200);
        return new Point(GameUtils.getAnim(time, 2, 200), dir);
    }
}