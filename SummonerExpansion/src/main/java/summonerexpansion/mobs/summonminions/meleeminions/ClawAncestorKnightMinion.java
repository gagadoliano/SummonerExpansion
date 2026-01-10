package summonerexpansion.mobs.summonminions.meleeminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.MobRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AncestorFollowingMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AncestorKnightFollowingMob;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registry.SummonerTextures.ancestorKnightMinion;

public class ClawAncestorKnightMinion extends AncestorFollowingMob
{
    private int hitCount;

    public ClawAncestorKnightMinion()
    {
        setSpeed(60.0F);
        setFriction(3.0F);
        attackAnimTime = 400;
        attackCooldown = 400;
        moveAccuracy = 10;
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle(-13, -30, 26, 40);
        swimMaskMove = 16;
        swimMaskOffset = 0;
        swimSinkOffset = -4;
    }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerChaserAI<ClawAncestorKnightMinion>(600, 64, false, false, 900, 64)
        {
            public boolean attackTarget(ClawAncestorKnightMinion mob, Mob target)
            {
                if (mob.canAttack())
                {
                    mob.attack(target.getX(), target.getY(), false);
                    target.isServerHit(summonDamage, mob.dx, mob.dy, 15.0F, mob);
                    hitCount++;
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public void serverTick()
    {
        super.serverTick();
        if (hitCount >= 6)
        {
            remove(0.0F, 0.0F, null, true);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        float animProgress = getAttackAnimProgress();
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, ancestorKnightMinion)).sprite(sprite).mask(swimMask).dir(dir).light(light).alpha(0.5F);
        if (isAttacking)
        {
            humanDrawOptions.itemAttack(new InventoryItem("ancestorsword"), null, animProgress, attackDir.x, attackDir.y).alpha(0.25F);
        }
        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }
}