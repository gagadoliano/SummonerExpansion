package summonerexpansion.modperks;

import necesse.engine.GameLog;
import necesse.engine.incursionPerkTree.IncursionPerk;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.level.maps.IncursionLevel;
import necesse.level.maps.incursion.AltarData;

import java.util.Objects;

public class HorrorSpiritRewardPerk extends IncursionPerk
{
    public HorrorSpiritRewardPerk(Integer tier, int perkCost, int xPositionOnPerkTree, IncursionPerk... prerequisitePerkRequired)
    {
        super(tier, perkCost, xPositionOnPerkTree, true, prerequisitePerkRequired);
    }

    public void onIncursionLevelGenerated(IncursionLevel level, AltarData altarData, int modifierIndex)
    {
        super.onIncursionLevelGenerated(level, altarData, modifierIndex);
        if (!Objects.equals(level.incursionData.getStringID(), "trial"))
        {
            int spawnAttempts = 100;
            int ghostToSpawn = 5;
            GameRandom random = new GameRandom(level.getSeed() + 323L);
            for (int i = 0; i < ghostToSpawn; ++i)
            {
                Mob horrorSpirit = MobRegistry.getMob("horrorspiritmob", level);
                horrorSpirit.canDespawn = false;
                horrorSpirit.shouldSave = true;
                int levelTileWidth = level.tileWidth;
                int levelTileHeight = level.tileHeight;
                for (int j = 1; j <= spawnAttempts; ++j)
                {
                    int rndX = random.getIntBetween(0, levelTileWidth) * 32 + 16;
                    int rndY = random.getIntBetween(0, levelTileHeight) * 32 + 16;
                    if (!level.isSolidTile(rndX, rndY) && !horrorSpirit.collidesWith(level, rndX, rndY))
                    {
                        horrorSpirit.onSpawned(rndX, rndY);
                        level.entityManager.addMob(horrorSpirit, (float)rndX, (float)rndY);
                        break;
                    }
                    if (j == 50)
                    {
                        GameLog.warn.println("No spawn position found for: " + horrorSpirit.getDisplayName());
                    }
                }
            }
        }
    }
}