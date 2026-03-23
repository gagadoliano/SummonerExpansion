package summonerexpansion.codes.registries;

import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;

import static necesse.gfx.GameResources.particlesTextureGenerator;

public class RegistryParticlesTextures
{
    public static GameTextureSection pumpkinExplosionParticles;
    public static GameTextureSection titaniumMeleeParticle;
    public static GameTextureSection horrorWaveParticles;
    public static GameTextureSection mosquitoBowVisual;
    public static GameTextureSection healGlyphParticles;

    public static GameTexture healGlyphParticle;
    public static GameTexture titaniumLightningGlyph;
    public static GameTexture spiritGhoulPool;
    public static GameTexture pineWoodSpike;
    public static GameTexture thornSpike;

    public static void initResources()
    {
        pumpkinExplosionParticles = particlesTextureGenerator.addTexture(GameTexture.fromFile("particles/pumpkinparticles"));
        titaniumMeleeParticle = particlesTextureGenerator.addTexture(GameTexture.fromFile("particles/settitaniummeleeminion"));
        horrorWaveParticles = particlesTextureGenerator.addTexture(GameTexture.fromFile("projectiles/horrorwaveproj"));
        GameTexture MosquitoBowTexture = GameTexture.fromFile("particles/mosquitobowpool");
        mosquitoBowVisual = particlesTextureGenerator.addTexture(MosquitoBowTexture);
        healGlyphParticle = GameTexture.fromFile("particles/healglyph");

        healGlyphParticles = particlesTextureGenerator.addTexture(GameTexture.fromFile("particles/healglyphparticles"));
        titaniumLightningGlyph = GameTexture.fromFile("particles/titaniumlightningglyph");
        spiritGhoulPool = GameTexture.fromFile("particles/spiritghoulpool");
        pineWoodSpike = GameTexture.fromFile("particles/pinestaffspikes");
        thornSpike = GameTexture.fromFile("particles/thornspikes");
    }
}