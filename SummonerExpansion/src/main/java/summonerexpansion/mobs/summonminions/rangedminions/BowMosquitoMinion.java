package summonerexpansion.mobs.summonminions.rangedminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.FlyingAttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import summonerexpansion.codes.registry.SummonerBuffs;

import java.awt.*;
import java.util.List;

public class BowMosquitoMinion extends FlyingAttackingFollowingMob
{
    protected final int randomAnimationOffset;
    protected double flyOffset;
    public int MosquitoHits;

    public BowMosquitoMinion()
    {
        super(10);
        randomAnimationOffset = GameRandom.globalRandom.nextInt(900);
        setSpeed(50.0F);
        setFriction(2.0F);
        collision = new Rectangle(-9, -7, 18, 14);
        hitBox = new Rectangle(-20, -34, 40, 32);
        selectBox = new Rectangle(-13, -16, 26, 30);
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return summonDamage; }

    public void init()
    {
        super.init();
        ai = new BehaviourTreeAI<>(this, new PlayerFollowerCollisionChaserAI(1200, summonDamage, 45, 800, 2000, 90));
    }

    @Override
    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = this.getAttackOwner();

        MosquitoHits++;
        if (MosquitoHits >= 20)
        {
            remove();
        }

        if (owner != null && target != null)
        {
            ActiveBuff buff = new ActiveBuff(SummonerBuffs.SummonerDebuffs.MOSQUITOWEAK, target, 60F, this);
            target.addBuff(buff, true);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY)
    {
        for(int i = 0; i < 4; ++i)
        {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), MobRegistry.Textures.mosquito, i, 8, 32, this.x, this.y, 10.0F, knockbackX, knockbackY), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    protected void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 32 + (int)this.flyOffset;
        int spriteCount = 4;
        int sprite = (int)((double)(this.getTime() + (long)this.randomAnimationOffset) / 1000.0 * 11.0) % spriteCount;
        float rotate = this.dx / 10.0F;
        final DrawOptions options = MobRegistry.Textures.mosquito.initDraw().sprite(sprite + 2, this.getDir(), 64).light(light).rotate(rotate, 32, 32).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
        this.addShadowDrawables(tileList, level, x, y, light, camera);
    }

    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera)
    {
        int spriteCount = 4;
        int sprite = (int)((double)(this.getTime() + (long)this.randomAnimationOffset) / 1000.0 * 11.0) % spriteCount;
        GameTexture shadowTexture = MobRegistry.Textures.mosquito_shadow;
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 32 - 22;
        return shadowTexture.initDraw().sprite(sprite + 2, this.getDir(), 64).light(light).pos(drawX, drawY);
    }
}
