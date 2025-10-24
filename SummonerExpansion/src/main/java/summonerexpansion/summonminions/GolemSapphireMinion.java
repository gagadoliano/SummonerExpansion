package summonerexpansion.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.util.*;
import necesse.entity.levelEvent.mobAbilityLevelEvent.SapphireGlyphEvent;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.staticBuffs.LifeEssenceStacksBuff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.level.maps.CollisionFilter;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class GolemSapphireMinion extends AttackingFollowingMob
{
    public static GameTexture texture;
    protected int rightClickLifeEssenceCost;

    public GolemSapphireMinion()
    {
        super(10);
        setSpeed(20.0F);
        setFriction(3.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle();
        swimMaskMove = 32;
        swimMaskOffset = -6;
        swimSinkOffset = -4;
        rightClickLifeEssenceCost = LifeEssenceStacksBuff.STACKS_PER_LIFE_ESSENCE;
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return summonDamage; }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI(600, summonDamage, 30, 600, 900, 80));
    }

    @Override
    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = this.getAttackOwner();
        if (owner != null && target != null && owner.buffManager.getStacks(BuffRegistry.LIFE_ESSENCE) >= 1)
        {
            SapphireGlyphEvent event = this.getSapphireGlyphEvent(getLevel(), (int)owner.x, (int)owner.y, owner, new InventoryItem("gemsapphireshards"), GameRandom.globalRandom.nextSeeded(1));
            getLevel().entityManager.addLevelEvent(event);
        }
    }

    public SapphireGlyphEvent getSapphireGlyphEvent(Level level, int x, int y, Mob player, InventoryItem item, GameRandom random)
    {
        if (level.isServer() && player.buffManager.getStacks(BuffRegistry.LIFE_ESSENCE) >= 1)
        {
            for (int i = 0; i < this.rightClickLifeEssenceCost; ++i)
            {
                player.buffManager.removeStack(BuffRegistry.LIFE_ESSENCE, true, true);
            }
        }

        Point2D.Float targetPoints = new Point2D.Float((float)x, (float)y);
        Point2D.Float normalizedVector = GameMath.normalize(targetPoints.x - player.x, targetPoints.y - player.y);
        RayLinkedList<LevelObjectHit> hits = GameUtils.castRay(level, player.x, player.y, normalizedVector.x, normalizedVector.y, targetPoints.distance(player.x, player.y), 0, (new CollisionFilter()).projectileCollision().addFilter((tp) -> tp.object().object.isWall || tp.object().object.isRock));
        if (!hits.isEmpty())
        {
            Ray<LevelObjectHit> first = hits.getLast();
            targetPoints.x = (float)first.x2;
            targetPoints.y = (float)first.y2;
        }

        return new SapphireGlyphEvent(player, (int)targetPoints.x, (int)targetPoints.y, random, 1);
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, GameRandom.globalRandom.nextInt(5), 8, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7 - 6;
        int dir = this.getDir();
        Point sprite = this.getAnimSprite(x, y, dir);
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        if (this.isAttacking) {
            sprite.x = 0;
        }
        final MaskShaderOptions swimMask = this.getSwimMaskShaderOptions(this.inLiquidFloat(x, y));
        final DrawOptions drawOptions = texture.initDraw().sprite(sprite.x, sprite.y, 64).addMaskShader(swimMask).light(light.minLevelCopy(100.0F)).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                swimMask.use();
                drawOptions.draw();
                swimMask.stop();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }
}
