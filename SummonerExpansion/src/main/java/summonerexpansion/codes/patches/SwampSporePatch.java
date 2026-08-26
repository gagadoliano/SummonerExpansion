package summonerexpansion.codes.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ChanceLootItem;
import necesse.level.gameObject.SwampSporeObject;
import necesse.level.maps.Level;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = SwampSporeObject.class, name = "getLootTable", arguments = {Level.class, int.class, int.class, int.class})
public class SwampSporePatch
{
    @Advice.OnMethodExit
    static void onExit(@Advice.This SwampSporeObject sporeObject, @Advice.Argument(0) Level level, @Advice.Return(readOnly = false) LootTable lootTable)
    {
        lootTable = new LootTable(ChanceLootItem.between(0.20f, "sporebag", 1, 1));
    }
}