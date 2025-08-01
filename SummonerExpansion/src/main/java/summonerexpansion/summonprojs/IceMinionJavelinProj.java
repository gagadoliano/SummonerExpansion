package summonerexpansion.summonprojs;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.journal.ImpaleIceJavelinsJournalChallenge;
import necesse.engine.journal.JournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.JournalChallengeRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.particle.Particle;
import necesse.entity.particle.ProjectileHitStuckParticle;
import necesse.entity.projectile.IceJavelinProjectile;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.followingProjectile.FollowingProjectile;
import necesse.entity.trails.Trail;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.EntityDrawable;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import necesse.level.maps.LevelObjectHit;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class IceMinionJavelinProj extends Projectile
{
    public IceMinionJavelinProj() {}

    public void init()
    {
        super.init();
        height = 18.0F;
        setWidth(4.0F);
        trailOffset = -50.0F;
        heightBasedOnDistance = true;
    }

    public Trail getTrail()
    {
        return new Trail(this, this.getLevel(), new Color(69, 187, 224), 10.0F, 250, 18.0F);
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, OrderableDrawables overlayList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
    {
        if (!this.removed())
        {
            GameLight light = level.getLightLevel(this);
            int drawX = camera.getDrawX(this.x) - 2;
            int drawY = camera.getDrawY(this.y) - 2;
            final TextureDrawOptions options = this.texture.initDraw().light(light).rotate(this.getAngle() + 45.0F, 2, 2).pos(drawX, drawY - (int)this.getHeight());
            list.add(new EntityDrawable(this)
            {
                public void draw(TickManager tickManager) {
                    options.draw();
                }
            });
            this.addShadowDrawables(tileList, drawX, drawY, light, this.getAngle() + 45.0F, 2, 2);
        }
    }

    public void doHitLogic(Mob mob, LevelObjectHit object, float x, float y)
    {
        super.doHitLogic(mob, object, x, y);
        if (this.isServer() && mob != null)
        {
            Mob owner = this.getOwner();
            ActiveBuff ab = new ActiveBuff(BuffRegistry.getBuff("frozenbookdebuff"), mob, 30.0F, this.getOwner());
            mob.addBuff(ab, true);
            if (owner.isPlayer && ((PlayerMob)owner).isServerClient())
            {
                ServerClient serverClient = ((PlayerMob)owner).getServerClient();
                JournalChallenge challenge = JournalChallengeRegistry.getChallenge(JournalChallengeRegistry.IMPALE_FIVE_ICE_JAVELINS_ID);
                ((ImpaleIceJavelinsJournalChallenge)challenge).submitIceJavelinImpale(serverClient, mob);
            }
        }

        if (this.isClient() && this.traveledDistance < (float)this.distance)
        {
            final float height = this.getHeight();
            this.getLevel().entityManager.addParticle(new ProjectileHitStuckParticle(mob, this, x, y, mob == null ? 10.0F : 40.0F, 5000L)
            {
                public void addDrawables(Mob target, float x, float y, float angle, List<LevelSortedDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, TickManager tickManager, GameCamera camera, PlayerMob perspective)
                {
                    GameLight light = level.getLightLevel(this);
                    int drawX = camera.getDrawX(x) - 2;
                    int drawY = camera.getDrawY(y - height) - 2;
                    float alpha = 1.0F;
                    long lifeCycleTime = this.getLifeCycleTime();
                    int fadeTime = 1000;
                    if (lifeCycleTime >= this.lifeTime - (long)fadeTime)
                    {
                        alpha = Math.abs((float)(lifeCycleTime - (this.lifeTime - (long)fadeTime)) / (float)fadeTime - 1.0F);
                    }
                    int cut = target == null ? 8 : 0;
                    final TextureDrawOptions options = IceMinionJavelinProj.this.texture.initDraw().section(cut, IceMinionJavelinProj.this.texture.getWidth(), cut, IceMinionJavelinProj.this.texture.getHeight()).light(light).rotate(IceMinionJavelinProj.this.getAngle() + 45.0F, 2, 2).alpha(alpha).pos(drawX, drawY);
                    EntityDrawable drawable = new EntityDrawable(this)
                    {
                        public void draw(TickManager tickManager) {
                            options.draw();
                        }
                    };
                    if (target != null)
                    {
                        topList.add(drawable);
                    }
                    else
                    {
                        list.add(drawable);
                    }
                }
            }, Particle.GType.IMPORTANT_COSMETIC);

            if (this.isServer())
            {
                if (mob != null)
                {
                    if (this.modifier != null)
                    {
                        this.modifier.doHitLogic(mob, object, x, y);
                    }
                }
            }
        }
    }

    protected void playHitSound(float x, float y)
    {
        SoundManager.playSound(GameResources.tap, SoundEffect.effect(x, y).volume(0.5F).pitch(0.8F));
    }
}
