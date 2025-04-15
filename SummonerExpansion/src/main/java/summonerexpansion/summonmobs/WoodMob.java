package summonerexpansion.summonmobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.CollisionPlayerChaserWandererAI;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

public class WoodMob extends HostileMob
{
    public static GameTexture texture;
    public float moveAngle;

    public static LootTable lootTable = new LootTable(
            ChanceLootItem.between(1f, "oaklog", 1, 10)
    );

    public WoodMob()
    {
        super(1);
        setSpeed(0);
        setFriction(0);
        collision = new Rectangle(-18, -15, 36, 36);
        hitBox = new Rectangle(-18, -15, 36, 36);
        selectBox = new Rectangle(-20, -34, 36, 36);
    }

    @Override
    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new CollisionPlayerChaserWandererAI<>(null, 0, new GameDamage(0), 0, 0));
    }

    @Override
    protected void onDeath(Attacker attacker, HashSet<Attacker> attackers)
    {
        super.onDeath(attacker, attackers);
        this.remove();
    }

    @Override
    public LootTable getLootTable() {
        return lootTable;
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 20;
        DrawOptions body = texture.initDraw().light(light).rotate(moveAngle, 16, 20).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }

    public boolean canPushMob(Mob other)
    {
        return false;
    }

    public boolean canBePushed(Mob other)
    {
        return false;
    }
}
