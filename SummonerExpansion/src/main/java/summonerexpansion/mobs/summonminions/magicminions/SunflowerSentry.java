package summonerexpansion.mobs.summonminions.magicminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.JournalChallengeRegistry;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.objectEntity.interfaces.OEVicinityBuff;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptionsEnd;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.mobs.summonminions.baseminions.SentryBase;

import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

import static summonerexpansion.codes.registry.SummonerChallenges.*;

public class SunflowerSentry extends SentryBase implements OEVicinityBuff
{
    public int flowerRange = 500;
    public static GameTexture texture;
    public float moveAngle;

    public SunflowerSentry()
    {
        super(3000F, 1000F);
        collision = new Rectangle(0, 0, 34, 66);
        hitBox = new Rectangle(0, 0, 34, 66);
        selectBox = new Rectangle(0, 0, 34, 66);
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI<>(0, null, 0, 0, 90000, 64));
    }

    public Buff[] getBuffs()
    {
        return new Buff[]{BuffRegistry.getBuff("sunflowerbuff")};
    }

    public int getBuffRange()
    {
        return flowerRange;
    }

    public boolean shouldBuffPlayers() {
        return true;
    }

    public boolean shouldBuffMobs()
    {
        return true;
    }

    public Predicate<Mob> buffMobsFilter()
    {
        return (m) -> m.isHuman && !m.isSummoned && !m.isHostile;
    }

    public void applyBuffs(Mob mob)
    {
        Buff[] var2 = this.getBuffs();
        for (Buff buff : var2)
        {
            if (buff != null && getClient() != null)
            {
                ActiveBuff ab = new ActiveBuff(buff, mob, 1F, this);
                if (JournalChallengeRegistry.getChallenge(UPGRADE_TIER4_ID).isCompleted(getClient()))
                {
                    mob.buffManager.addBuff(ab, false).setStacks(20, 60, getAttackOwner());
                }
                else if (JournalChallengeRegistry.getChallenge(UPGRADE_TIER3_ID).isCompleted(getClient()))
                {
                    mob.buffManager.addBuff(ab, false).setStacks(10, 60, getAttackOwner());
                }
                else if (JournalChallengeRegistry.getChallenge(UPGRADE_TIER2_ID).isCompleted(getClient()))
                {
                    mob.buffManager.addBuff(ab, false).setStacks(6, 60, getAttackOwner());
                }
                else if (JournalChallengeRegistry.getChallenge(UPGRADE_TIER1_ID).isCompleted(getClient()))
                {
                    mob.buffManager.addBuff(ab, false).setStacks(4, 60, getAttackOwner());
                }
                else
                {
                    mob.buffManager.addBuff(ab, false).setStacks(1, 60, getAttackOwner());
                }
            }
        }
    }

    public void tickVicinityBuff(Mob mob)
    {
        Level level = mob.getLevel();
        int posX = (int) mob.x;
        int posY = (int) mob.y;
        tickVicinityBuff(level, posX, posY);
    }

    public void clientTick()
    {
        super.clientTick();
        tickVicinityBuff(this);
    }

    public void serverTick()
    {
        super.serverTick();
        tickVicinityBuff(this);
    }

    public Mob getFirstAttackOwner() {
        return this;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, i, 8, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y)).minLevelCopy(100.0F);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 55;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY = (int)((float)drawY);
        drawY += level.getTile(getTileCoordinate(x), getTileCoordinate(y)).getMobSinkingAmount(this);
        final TextureDrawOptionsEnd drawOptions = texture.initDraw().sprite(sprite.x, sprite.y, 64).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }

    public Point getAnimSprite(int x, int y, int dir)
    {
        return new Point(GameUtils.getAnim(this.getWorldEntity().getTime(), 4, 100*60), dir);
    }

    public int getRockSpeed() {
        return 50*60;
    }
}