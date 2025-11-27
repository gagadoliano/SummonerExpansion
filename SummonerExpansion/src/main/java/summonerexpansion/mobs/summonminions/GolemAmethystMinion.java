package summonerexpansion.mobs.summonminions;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.levelEvent.mobAbilityLevelEvent.CrystallizeShatterEvent;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerChaserAI;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerFollowerCollisionChaserAI;
import necesse.entity.mobs.buffs.BuffManager;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.hostile.CrystalGolemMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.projectile.Projectile;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class GolemAmethystMinion extends AttackingFollowingMob
{
    public static GameTexture texture;

    public GolemAmethystMinion()
    {
        super(10);
        setSpeed(25.0F);
        setFriction(1.0F);
        collision = new Rectangle(-10, -7, 20, 14);
        hitBox = new Rectangle(-14, -12, 28, 24);
        selectBox = new Rectangle();
        swimMaskMove = 32;
        swimMaskOffset = -6;
        swimSinkOffset = -4;
    }

    public GameDamage getCollisionDamage(Mob target, boolean fromPacket, ServerClient packetSubmitter) { return summonDamage; }

    public void handleCollisionHit(Mob target, GameDamage damage, int knockback)
    {
        Mob owner = this.getAttackOwner();

        if (getLevel().isServer() && owner != null && target != null)
        {
            BuffManager attackerBM = owner.buffManager;
            float thresholdMod = attackerBM.getModifier(BuffModifiers.SUMMON_CRIT_CHANCE);
            float crystallizeMod = attackerBM.getModifier(BuffModifiers.CRIT_DAMAGE) + attackerBM.getModifier(BuffModifiers.SUMMON_CRIT_CHANCE);
            int stackThreshold = (int)GameMath.limit(10.0F - 7.0F * thresholdMod, 3.0F, 10.0F);
            float crystallizeDamageMultiplier = GameMath.limit(crystallizeMod, 2.0F, (float)stackThreshold);
            Buff crystallizeBuff = BuffRegistry.Debuffs.CRYSTALLIZE_BUFF;
            ActiveBuff ab = new ActiveBuff(crystallizeBuff, target, 2000, this);
            target.buffManager.addBuff(ab, true);
            ActiveBuff buff = target.buffManager.getBuff(crystallizeBuff);
            if (buff != null && buff.getStacks() >= stackThreshold)
            {
                getLevel().entityManager.events.add(new CrystallizeShatterEvent(target, CrystallizeShatterEvent.ParticleType.AMETHYST));
                target.buffManager.removeBuff(crystallizeBuff, true);
                GameDamage finalDamage = summonDamage.modDamage(crystallizeDamageMultiplier);
                target.isServerHit(finalDamage, 0.0F, 0.0F, 0.0F, this);
            }
        }
    }

    public void init()
    {
        super.init();
        this.ai = new BehaviourTreeAI(this, new PlayerFollowerCollisionChaserAI(600, summonDamage, 120, 600, 900, 80));
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
