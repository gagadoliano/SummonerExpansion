package summonerexpansion.mobs.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawOptions.itemAttack.ItemAttackDrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.codes.registry.SummonerBuffs;

import java.awt.*;
import java.util.List;

import static summonerexpansion.codes.registry.SummonerTextures.enchantedBabyZombieMinion;

public class EnchantedBabyZombieMinion extends AttackingFollowingMob
{
    public EnchantedBabyZombieMinion()
    {
        super(10);
        setSpeed(50.0F);
        setFriction(2.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-12, -14, 24, 24);
        selectBox = new Rectangle(-16, -30, 32, 36);
        swimMaskMove = 12;
        swimMaskOffset = 0;
        swimSinkOffset = 0;
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return summonDamage; }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI(500, summonDamage, 30, 500, 640, 64));
    }

    @Override
    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = getAttackOwner();
        if (owner != null && target != null)
        {
            ActiveBuff buff = new ActiveBuff(SummonerBuffs.SummonerDebuffs.ENCHANTEDWEAK, target, 60F, this);
            target.addBuff(buff, true);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 5; ++i)
        {
            getLevel().entityManager.addParticle(new FleshParticle(getLevel(), enchantedBabyZombieMinion.body, i, 6, 32, x, y, 20.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) 
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 44 - 7;
        int dir = getDir();
        Point sprite = getAnimSprite(x, y, dir);
        drawY += getBobbing(x, y);
        drawY += getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        MaskShaderOptions swimMask = getSwimMaskShaderOptions(inLiquidFloat(x, y));
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(level, enchantedBabyZombieMinion)).sprite(sprite).mask(swimMask).dir(dir).light(light);
        if (dir == 1 || dir == 3) 
        {
            humanDrawOptions.attackArmPosOffset(2, 0);
        }
        float animProgress = getAttackAnimProgress();
        if (isAttacking) 
        {
            ItemAttackDrawOptions attackOptions = ItemAttackDrawOptions.start(dir).armSprite(enchantedBabyZombieMinion.body, 0, 8, 32).swingRotation(animProgress).light(light);
            humanDrawOptions.attackAnim(attackOptions, animProgress);
        }
        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        addShadowDrawables(tileList, level, x, y, light, camera);
    }

    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera) 
    {
        GameTexture shadowTexture = MobRegistry.Textures.human_baby_shadow;
        int res = shadowTexture.getHeight();
        int drawX = camera.getDrawX(x) - res / 2;
        int drawY = camera.getDrawY(y) - res / 2;
        drawY += getBobbing(x, y);
        int dir = getDir();
        return shadowTexture.initDraw().sprite(dir, 0, res).light(light).pos(drawX, drawY);
    }

    public int getRockSpeed() {
        return 10;
    }
}