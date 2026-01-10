package summonerexpansion.codes.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.gameObject.CowSkeletonObject;
import necesse.level.gameObject.SurfaceGrassObject;
import necesse.level.maps.Level;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = CowSkeletonObject.class, name = "getLootTable", arguments = {Level.class, int.class, int.class, int.class})
public class CowSkeletonPatch
{
    @Advice.OnMethodExit
    static void onExit(@Advice.This CowSkeletonObject cowObject, @Advice.Argument(0) Level level, @Advice.Return(readOnly = false) LootTable lootTable)
    {
        lootTable = new LootTable(ChanceLootItem.between(0.20f, "cowskull", 1, 1));
    }
}