package summonerexpansion.mobs.minions.melee;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.minions.base.SummonFishBase;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registries.RegistryMinionTextures.fishTroutMinion;

public class FishTroutMinion extends SummonFishBase
{
    public FishTroutMinion()
    {
        super(400, new Color(150, 154, 38));
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 16;
        int drawY = camera.getDrawY(y) - 20;
        DrawOptions body = fishTroutMinion.initDraw().light(light).rotate(this.moveAngle, 14, 14).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }
}