package summonerexpansion.codes.registries;

import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.level.gameTile.SimpleFloorTile;
import summonerexpansion.objects.liquids.*;
import summonerexpansion.objects.nature.*;
import summonerexpansion.objects.tiles.*;

import java.awt.*;

import static necesse.entity.mobs.buffs.BuffModifiers.*;

public class RegistryTiles
{
    public static class TileBuffs
    {
        // Liquids
        public static Buff LIQUIDHONEYSTACKS;

        public TileBuffs() {
        }
    }

    public static int liquidHoneyID;

    public static void registerTiles()
    {
        // Terrain tiles
        TileRegistry.registerTile("ancientgrasstile", new AncientGrassTile(), 2, true);
        ItemRegistry.registerItem("ancientgrassseed", new BaseGrassSeedItem("ancientgrasstile"), 2, true);
        TileRegistry.registerTile("beehivefloor", new SimpleFloorTile("beehivefloor", new Color(158, 101, 32)), 0, true);

        // Buff tiles
        TileRegistry.registerTile("empoweredsapphire", new BaseBuffTile(false, 0.50f, new Color(0, 94, 114), 191F, "empoweredsapphire", "empoweredsapphirebuff", "empoweredsapphiretip"), 10, true);
        TileRegistry.registerTile("empoweredemerald", new BaseBuffTile(false, 0.50f, new Color(0, 79, 99), 190F, "empoweredemerald", "empoweredemeraldbuff", "empoweredemeraldtip"), 10, true);
        TileRegistry.registerTile("empoweredruby", new BaseBuffTile(false, 0.50f, new Color(92, 54, 62), 337F, "empoweredruby", "empoweredrubybuff", "empoweredrubytip"), 10, true);

        TileRegistry.registerTile("empowerednewsapphire", new BaseBuffTile(false, 0.50f, new Color(57, 137, 184), 199F, "empowerednewsapphire", "empoweredsapphirebuff", "empowerednewsapphiretip"), 10, true);
        BuffRegistry.registerBuff("empoweredsapphirebuff", new BaseFloorBuff(new ModifierValue<>(SUMMON_CRIT_CHANCE, 0.10f), new ModifierValue<>(SUMMON_CRIT_DAMAGE, 0.10f)));
        TileRegistry.registerTile("empowerednewemerald", new BaseBuffTile(false, 0.50f, new Color(72, 103, 46), 91F, "empowerednewemerald", "empoweredemeraldbuff", "empowerednewemeraldtip"), 10, true);
        BuffRegistry.registerBuff("empoweredemeraldbuff", new BaseFloorBuff(new ModifierValue<>(MAX_MANA, 0.10f), new ModifierValue<>(COMBAT_MANA_REGEN, 0.10f)));
        TileRegistry.registerTile("empowerednewruby", new BaseBuffTile(false, 0.50f, new Color(169, 37, 33), 3F, "empowerednewruby", "empoweredrubybuff", "empowerednewrubytip"), 10, true);
        BuffRegistry.registerBuff("empoweredrubybuff", new BaseFloorBuff(new ModifierValue<>(SUMMON_ATTACK_SPEED, 0.10f), new ModifierValue<>(SUMMONS_SPEED, 0.10f)));
        TileRegistry.registerTile("empoweredamethyst", new BaseBuffTile(false, 0.50f, new Color(74, 61, 99), 258F, "empoweredamethyst", "empoweredamethystbuff", "empoweredamethysttip"), 10, true);
        BuffRegistry.registerBuff("empoweredamethystbuff", new BaseFloorBuff(new ModifierValue<>(SUMMONS_TARGET_RANGE, 0.10f), new ModifierValue<>(KNOCKBACK_OUT, 0.10f)));
        TileRegistry.registerTile("empoweredtopaz", new BaseBuffTile(false, 0.50f, new Color(212, 149, 73), 32F, "empoweredtopaz", "empoweredtopazbuff", "empoweredtopaztip"), 10, true);
        BuffRegistry.registerBuff("empoweredtopazbuff", new BaseFloorBuff(new ModifierValue<>(MAX_RESILIENCE, 0.10f), new ModifierValue<>(RESILIENCE_REGEN, 0.10f)));

        // Liquid tiles
        liquidHoneyID = TileRegistry.registerTile("liquidhoneytile", new LiquidHoneyTile(), 20, true);
        BuffRegistry.registerBuff("liquidhoneybuff", TileBuffs.LIQUIDHONEYSTACKS = new LiquidHoneyBuff());
        ItemRegistry.registerItem("infinitehoneybucket", new InfiniteHoneyBucketItem(), 250.0F, true);
    }
}