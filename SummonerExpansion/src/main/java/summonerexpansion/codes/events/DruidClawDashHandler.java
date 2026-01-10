package summonerexpansion.codes.events;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.network.packet.PacketShowAttack;
import necesse.engine.network.server.ServerClient;
import necesse.engine.sound.SoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.sound.SoundPlayer;
import necesse.engine.sound.SoundSettings;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.ParticleTypeSwitcher;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.attackHandler.MousePositionAttackHandler;
import necesse.entity.mobs.buffs.ActiveBuff;
import necesse.entity.mobs.itemAttacker.ItemAttackSlot;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.particle.Particle;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.SortedDrawable;
import necesse.gfx.ui.HUD;
import necesse.inventory.InventoryItem;
import necesse.level.maps.hudManager.HudDrawElement;
import summonerexpansion.codes.registry.SummonerBuffs;
import summonerexpansion.items.equips.allweapons.basesummon.DruidClaw;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class DruidClawDashHandler  extends MousePositionAttackHandler
{
    protected SoundPlayer clawChargeSoundPlayer;
    public int chargeTime;
    public boolean fullyCharged;
    public DruidClaw clawItem;
    public long startTime;
    public InventoryItem item;
    public int seed;
    public Color particleColors;
    public boolean endedByInteract;
    protected int endAttackBuffer;
    protected HudDrawElement hudDrawElement;

    public DruidClawDashHandler(ItemAttackerMob attackerMob, ItemAttackSlot slot, InventoryItem item, DruidClaw clawItem, int chargeTime, Color particleColors, int seed)
    {
        super(attackerMob, slot, 20);
        this.item = item;
        this.clawItem = clawItem;
        this.chargeTime = chargeTime;
        this.particleColors = particleColors;
        this.seed = seed;
        this.startTime = attackerMob.getWorldEntity().getLocalTime();
        if (attackerMob.isClient())
        {
            clawChargeSoundPlayer = SoundManager.playSound(GameResources.katanaChargeBegin, SoundEffect.effect(attackerMob).volume(0.3F).pitch(GameRandom.globalRandom.getFloatBetween(0.95F, 1.05F)));
        }
    }

    public long getTimeSinceStart()
    {
        return attackerMob.getWorldEntity().getLocalTime() - startTime;
    }

    public float getChargePercent()
    {
        return (float)getTimeSinceStart() / (float)chargeTime;
    }

    protected Color startColor()
    {
        return new Color(0, 0, 0, 0);
    }

    protected Color endColor()
    {
        return new Color(220, 255, 255, 100);
    }

    protected Color edgeColor()
    {
        return new Color(0, 0, 0, 100);
    }

    public void onUpdate()
    {
        super.onUpdate();
        if (attackerMob.isClient() && hudDrawElement == null)
        {
            hudDrawElement = attackerMob.getLevel().hudManager.addElement(new HudDrawElement()
            {
                public void addDrawables(List<SortedDrawable> list, GameCamera camera, PlayerMob perspective)
                {
                    if (attackerMob.getAttackHandler() != DruidClawDashHandler.this)
                    {
                        remove();
                    }
                    else
                    {
                        float distance = getChargeDistance(getChargePercent());
                        if (distance > 0.0F)
                        {
                            Point2D.Float dir = GameMath.normalize((float) lastX - attackerMob.x, (float) lastY - attackerMob.y);
                            final DrawOptions drawOptions = HUD.getArrowHitboxIndicator(attackerMob.x, attackerMob.y, dir.x, dir.y, (int)distance, 50, startColor(), endColor(), edgeColor(), camera);
                            list.add(new SortedDrawable()
                            {
                                public int getPriority()
                                {
                                    return 1000;
                                }

                                public void draw(TickManager tickManager)
                                {
                                    drawOptions.draw();
                                }
                            });
                        }

                    }
                }
            });
        }

        float chargePercent = getChargePercent();
        if (!attackerMob.isPlayer && chargePercent >= 1.0F)
        {
            endAttackBuffer += updateInterval;
            if (endAttackBuffer >= 350)
            {
                endAttackBuffer = 0;
                attackerMob.endAttackHandler(true);
                return;
            }
        }

        InventoryItem showItem = item.copy();
        showItem.getGndData().setFloat("chargePercent", chargePercent);
        showItem.getGndData().setBoolean("chargeUp", true);
        GNDItemMap attackMap = new GNDItemMap();
        attackerMob.showItemAttack(showItem, lastX, lastY, 0, seed, attackMap);
        if (attackerMob.isServer())
        {
            if (attackerMob.isPlayer)
            {
                PlayerMob player = (PlayerMob)attackerMob;
                ServerClient client = player.getServerClient();
                attackerMob.getServer().network.sendToClientsWithEntityExcept(new PacketShowAttack(player, showItem, lastX, lastY, 0, seed, attackMap), attackerMob, client);
            }
            else
            {
                attackerMob.showItemAttackMobAbility.runAndSend(showItem, lastX, lastY, 0, seed, attackMap);
            }
        }

        if (chargePercent >= 1.0F && !fullyCharged)
        {
            fullyCharged = true;
            if (attackerMob.isClient())
            {
                int particles = 35;
                float anglePerParticle = 360.0F / (float)particles;
                ParticleTypeSwitcher typeSwitcher = new ParticleTypeSwitcher(Particle.GType.CRITICAL, Particle.GType.IMPORTANT_COSMETIC, Particle.GType.COSMETIC);

                for(int i = 0; i < particles; ++i)
                {
                    int angle = (int)((float)i * anglePerParticle + GameRandom.globalRandom.nextFloat() * anglePerParticle);
                    float dx = (float)Math.sin(Math.toRadians(angle)) * (float)GameRandom.globalRandom.getIntBetween(30, 50);
                    float dy = (float)Math.cos(Math.toRadians(angle)) * (float)GameRandom.globalRandom.getIntBetween(30, 50) * 0.8F;
                    attackerMob.getLevel().entityManager.addParticle(attackerMob, typeSwitcher.next()).movesFriction(dx, dy, 0.8F).color(particleColors).heightMoves(0.0F, 30.0F).lifeTime(500);
                }

                SoundManager.playSound((new SoundSettings(GameResources.katanaDashReady)).volume(0.8F), attackerMob);
                if (attackerMob.isClient() && clawChargeSoundPlayer != null)
                {
                    clawChargeSoundPlayer.stop();
                }
            }
        }
    }

    public void onMouseInteracted(int levelX, int levelY)
    {
        endedByInteract = true;
        attackerMob.endAttackHandler(false);
    }

    public void onControllerInteracted(float aimX, float aimY)
    {
        endedByInteract = true;
        attackerMob.endAttackHandler(false);
    }

    public void onEndAttack(boolean bySelf)
    {
        float chargePercent = getChargePercent();
        if (attackerMob.isClient() && clawChargeSoundPlayer != null)
        {
            clawChargeSoundPlayer.fadeOutAndStop(0.4F);
        }

        if (!endedByInteract && chargePercent >= 0.5F)
        {
            if (attackerMob.isPlayer)
            {
                ((PlayerMob)attackerMob).constantAttack = true;
            }

            InventoryItem attackItem = item.copy();
            attackItem.getGndData().setBoolean("sliceDash", true);
            attackItem.getGndData().setFloat("chargePercent", chargePercent);
            attackerMob.showAttackAndSendAttacker(attackItem, lastX, lastY, 0, seed);
            Point2D.Float dir = GameMath.normalize((float)lastX - attackerMob.x, (float)lastY - attackerMob.y);
            chargePercent = Math.min(chargePercent, 1.0F);
            DruidClawDashLevelEvent event = new DruidClawDashLevelEvent(attackerMob, seed, dir.x, dir.y, getChargeDistance(chargePercent), (int)(200.0F * chargePercent), clawItem.getAttackDamage(item).modDamage(2.0F), clawItem.maxDashStacks.getValue(clawItem.getUpgradeTier(item)));
            attackerMob.addAndSendAttackerLevelEvent(event);
            attackerMob.buffManager.addBuff(new ActiveBuff(SummonerBuffs.SummonBuffs.CLAW_DASH_COOLDOWN, attackerMob, 3.0F, null), attackerMob.isServer());
            if (attackerMob.isClient())
            {
                SoundManager.playSound(new SoundSettings(GameResources.swing1), attackerMob);
                SoundManager.playSound(new SoundSettings(GameResources.katanaDash), attackerMob);
            }
        }

        if (hudDrawElement != null)
        {
            hudDrawElement.remove();
        }
    }

    public float getChargeDistance(float chargePercent)
    {
        chargePercent = Math.min(chargePercent, 1.0F);
        return chargePercent > 0.5F ? (chargePercent - 0.5F) * 2.0F * (float)clawItem.dashRange.getValue(clawItem.getUpgradeTier(item)) : 0.0F;
    }
}