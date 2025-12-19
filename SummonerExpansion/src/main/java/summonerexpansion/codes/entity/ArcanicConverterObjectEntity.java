package summonerexpansion.codes.entity;

import necesse.engine.util.GameRandom;
import necesse.entity.objectEntity.ProcessingTechInventoryObjectEntity;
import necesse.entity.particle.Particle;
import necesse.level.maps.Level;

import java.awt.*;

import static summonerexpansion.codes.registry.SummonerTechs.SUMMONCONVERTER;

public class ArcanicConverterObjectEntity extends ProcessingTechInventoryObjectEntity
{
    public ArcanicConverterObjectEntity(Level level, int x, int y)
    {
        super(level, "arcanicconverter", x, y, 1, 1, SUMMONCONVERTER);
    }

    public void clientTick()
    {
        super.clientTick();
        if (isProcessing() && GameRandom.globalRandom.nextInt(10) == 0)
        {
            int startHeight = 24 + GameRandom.globalRandom.nextInt(16);
            getLevel().entityManager.addParticle((float)(tileX * 32 + GameRandom.globalRandom.nextInt(32)), (float)(tileY * 32 + 32), Particle.GType.COSMETIC).color(new Color(134, 35, 193)).heightMoves((float)startHeight, (float)(startHeight + 20)).lifeTime(1000);
            getLevel().entityManager.addParticle((float)(tileX * 32 + GameRandom.globalRandom.nextInt(32)), (float)(tileY * 32 + 32), Particle.GType.COSMETIC).color(new Color(189, 7, 147)).heightMoves((float)startHeight, (float)(startHeight + 20)).lifeTime(1000);
        }
    }

    public int getProcessTime() {
        return 60000;
    }
}