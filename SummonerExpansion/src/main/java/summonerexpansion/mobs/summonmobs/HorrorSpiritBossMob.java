package summonerexpansion.mobs.summonmobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.ConfusedPlayerChaserWandererAI;
import necesse.entity.mobs.hostile.FlyingHostileMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.allprojs.HorrorSentryProj;

import java.awt.*;
import java.util.List;

public class HorrorSpiritBossMob extends FlyingHostileMob
{
    public float moveAngle;
    public static GameTexture texture;
    public static GameDamage baseDamage = new GameDamage(150.0F);
    public static GameDamage incursionDamage = new GameDamage(300.0F);

    public static LootTable lootTable = new LootTable(
            ChanceLootItem.between(1f, "purehorror", 5, 10)
    );

    public HorrorSpiritBossMob()
    {
        super(2500);
        setArmor(15);
        setSpeed(25.0F);
        setFriction(0.5F);
        setKnockbackModifier(0.2F);
        moveAccuracy = 10;
        attackCooldown = 3000;
        collision = new Rectangle(-50, -60, 108, 108);
        hitBox = new Rectangle(-50, -60, 108, 108);
        selectBox = new Rectangle(-50, -60, 108, 108);
    }

    public void init()
    {
        super.init();
        GameDamage damage;
        if (this.getLevel() instanceof IncursionLevel)
        {
            this.setMaxHealth(5500);
            this.setHealthHidden(this.getMaxHealth());
            this.setArmor(300);
            damage = incursionDamage;
        }
        else
        {
            damage = baseDamage;
        }

        ai = new BehaviourTreeAI(this, new ConfusedPlayerChaserWandererAI<HorrorSpiritBossMob>(null, 900, 600, 40000, false, false)
        {
            public boolean attackTarget(HorrorSpiritBossMob mob, Mob target)
            {
                if (mob.canAttack())
                {
                    HorrorSentryProj projectile = new HorrorSentryProj(getLevel(), mob, mob.x, mob.y, target.x, target.y, 75.0F, 512, damage, 50);
                    projectile.x -= projectile.dx * 20.0F;
                    projectile.y -= projectile.dy * 20.0F;
                    attack((int)(mob.x + projectile.dx * 100.0F), (int)(mob.y + projectile.dy * 100.0F), false);
                    getLevel().entityManager.projectiles.add(projectile);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 20; ++i)
        {
            this.getLevel().entityManager.addParticle(x, y, Particle.GType.COSMETIC).movesConstantAngle((float)GameRandom.globalRandom.nextInt(360), (float)GameRandom.globalRandom.getIntBetween(5, 20)).color(new Color(10, 10, 10));
        }
    }

    @Override
    public LootTable getLootTable() {return lootTable;}

    public void playDeathSound() {SoundManager.playSound(GameResources.fadedeath3, SoundEffect.effect(this));}

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(getTileCoordinate(x), getTileCoordinate(y));
        int drawX = camera.getDrawX(x) - 50;
        int drawY = camera.getDrawY(y) - 60;
        int dir = this.getDir();
        int frame = GameUtils.getAnim(this.getWorldEntity().getTime(), 4, 750);
        DrawOptions body = texture.initDraw().sprite(frame, 0, 108).size(108, 108).light(light).mirror(dir == 0, false).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }
}