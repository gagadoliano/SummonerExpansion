package summonerexpansion.mobs.summonminions.setminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.leaves.PlayerFollowerAINode;
import necesse.entity.mobs.ai.behaviourTree.util.FlyingAIMover;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.Particle;
import necesse.gfx.ThemeColorRegistry;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.CollisionFilter;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.codes.registry.SummonerEquips;
import summonerexpansion.items.equips.armorsetbonus.TitaniumRangedSetBonus;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registry.SummonerTextures.titaniumRangedMinion;

public class SetTitaniumRangedMinion extends FlyingAttackingFollowingMob
{
    protected float displayAngle;
    protected float targetAngle;
    protected int rotationDisableDuration = 20;
    protected int rotationDisabledTimer = 0;

    public SetTitaniumRangedMinion()
    {
        super(10);
        setSpeed(70F);
        setFriction(2.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-12, -16, 24, 28);
        selectBox = new Rectangle(-16, -40, 32, 50);
        swimMaskMove = 22;
        swimMaskOffset = 16;
        swimSinkOffset = -12;
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerAINode<>(600, 96), new FlyingAIMover());
    }

    public float getDesiredHeight()
    {
        float perc = GameUtils.getAnimFloat(getLevel().getWorldEntity().getTime(), 3000);
        return GameMath.sin(perc * 360.0F) * 10.0F - 20.0F;
    }

    public void clientTick()
    {
        super.clientTick();
        if (isClient())
        {
            updateBowRotation();
            if (rotationDisabledTimer > 0)
            {
                --rotationDisabledTimer;
            }

            if (moveX != 0.0F || moveY != 0.0F || GameRandom.globalRandom.getEveryXthChance(4))
            {
                getLevel().entityManager.addParticle(x + GameRandom.globalRandom.getFloatBetween(-15.0F, 15.0F), y + GameRandom.globalRandom.getFloatBetween(-15.0F, 15.0F) + getDesiredHeight(), Particle.GType.IMPORTANT_COSMETIC).movesConstant(0.0F, -8.0F).color(216,216,216,255).sizeFades(7, 12).lifeTime(400);
            }
        }
    }

    protected void updateBowRotation()
    {
        if (rotationDisabledTimer <= 0)
        {
            Mob followingMob = getFollowingMob();
            Point mousePos;
            if (followingMob != null)
            {
                ActiveBuff buff = followingMob.buffManager.getBuff(SummonerEquips.SummonerArmorBuffs.TITANIUMRANGEDBUFF);
                if (buff != null && followingMob.isPlayer)
                {
                    mousePos = TitaniumRangedSetBonus.getMousePos(buff);
                }
                else
                {
                    Point dirVector = followingMob.getDirVector();
                    mousePos = new Point(followingMob.getX() + dirVector.x * 400, followingMob.getY() + dirVector.y * 350);
                }
            }
            else
            {
                mousePos = new Point();
            }
            setBowRotation(mousePos.x, mousePos.y);
            displayAngle = lerpAngle(0.25F, displayAngle, targetAngle);
        }
    }

    protected void setBowRotation(int targetLevelX, int targetLevelY)
    {
        targetAngle = GameMath.getAngle(GameMath.normalize(x - (float)targetLevelX, y - (float)targetLevelY));
    }

    public float lerpAngle(float t, float a, float b)
    {
        a = (a % 360.0F + 360.0F) % 360.0F;
        b = (b % 360.0F + 360.0F) % 360.0F;
        float diff = (b - a + 540.0F) % 360.0F - 180.0F;
        return a + diff * t;
    }

    public void showAttack(int x, int y, int seed, boolean showAllDirections)
    {
        super.showAttack(x, y, seed, showAllDirections);
        setBowRotation(x, y);
        displayAngle = GameMath.getAngle(GameMath.normalize(x - (float)x, y - (float)y));
        rotationDisabledTimer = rotationDisableDuration;
    }

    public CollisionFilter getLevelCollisionFilter() {
        return null;
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(GameMath.getTileCoordinate(x), GameMath.getTileCoordinate(y));
        GameTexture texture = titaniumRangedMinion;
        int drawX = camera.getDrawX(x) - 15;
        int drawY = camera.getDrawY(y) - 26 + (int)getDesiredHeight();
        int spriteX = GameUtils.getAnim(getTime(), 10, 1000);
        final DrawOptions options = texture.initDraw().sprite(spriteX, 0, 30, 52).rotate(this.displayAngle).light(light).pos(drawX, drawY).size(90);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    public int getRockSpeed() {
        return 10;
    }

    public Attacker getAttackerDamageProxy()
    {
        Mob maLeader = this.getFollowingMob();
        return maLeader != null ? maLeader : super.getAttackerDamageProxy();
    }
}