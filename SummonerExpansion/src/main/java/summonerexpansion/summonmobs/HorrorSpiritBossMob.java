package summonerexpansion.summonmobs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.util.GameRandom;
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
import summonerexpansion.summonprojs.HorrorSentryProj;

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
        collision = new Rectangle(0, 0, 68, 108);
        hitBox = new Rectangle(0, 0, 68, 108);
        selectBox = new Rectangle(0, 0, 68, 108);
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

        this.ai = new BehaviourTreeAI(this, new ConfusedPlayerChaserWandererAI<HorrorSpiritBossMob>(null, 600, 600, 40000, false, false)
        {
            public boolean attackTarget(HorrorSpiritBossMob mob, Mob target)
            {
                if (mob.canAttack())
                {
                    HorrorSentryProj projectile = new HorrorSentryProj(HorrorSpiritBossMob.this.getLevel(), mob, mob.x, mob.y, target.x, target.y, 75.0F, 512, damage, 50);
                    projectile.x -= projectile.dx * 20.0F;
                    projectile.y -= projectile.dy * 20.0F;
                    HorrorSpiritBossMob.this.attack((int)(mob.x + projectile.dx * 100.0F), (int)(mob.y + projectile.dy * 100.0F), false);
                    HorrorSpiritBossMob.this.getLevel().entityManager.projectiles.add(projectile);
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

    protected void playDeathSound() {SoundManager.playSound(GameResources.fadedeath3, SoundEffect.effect(this));}

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x);
        int drawY = camera.getDrawY(y);
        DrawOptions body = texture.initDraw().light(light).rotate(moveAngle, 0, 0).pos(drawX, drawY);
        topList.add((tm) -> {
            body.draw();
        });
    }
}