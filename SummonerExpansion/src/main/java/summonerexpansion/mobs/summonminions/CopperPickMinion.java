package summonerexpansion.mobs.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.BuffRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.summonminions.baseminions.MagicToolBase;

import java.awt.*;
import java.util.List;

public class CopperPickMinion extends MagicToolBase
{
    public static GameTexture texture;

    public CopperPickMinion()
    {
        super();
        color = new Color(233, 134, 39);
        toolLimit = 12;
        searchRange = 450;
        setSpeed(45.0F);
    }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = getAttackOwner();
        if (owner != null && target != null)
        {
            target.isServerHit(damage, target.x - owner.x, target.y - owner.y, (float)knockback, this);
            collisionHitCooldowns.startCooldown(target);
            ActiveBuff buff = new ActiveBuff(BuffRegistry.getBuff("coppertoolbuff"), target, 60F, this);
            owner.buffManager.addBuff(buff, true);
            toolHit++;
            if (toolHit >= toolLimit)
            {
                remove(0.0F, 0.0F, null, true);
            }
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 20;
        DrawOptions body = texture.initDraw().light(light).rotate(moveAngle, 14, 14).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }
}