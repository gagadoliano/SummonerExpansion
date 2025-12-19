package summonerexpansion.allpresets.Forest;

import necesse.engine.util.GameRandom;
import necesse.level.maps.presets.Preset;

import static summonerexpansion.codes.registry.SummonerLoot.*;

public class HorrorSmallArenaPreset extends Preset
{
    public HorrorSmallArenaPreset(GameRandom random)
    {
        super("eNrtlrFOwzAQhl8lD-AhcZKWDpmgA1MRIDGgDk56bUxDXNkuDIh3x-ckKEnT4LawIdlS9Pl-3_nubOXufv4wf0w-3vlK50kQkRz4JtdJEBLNC7i9UclzdEW8FcBOimyLkHixXxGlRQmp5Nl2XQghl1ZTK5qJtmOzbeuqGdK6atraUzX_82_mufW7pF9O788lEekLZNpeCrPCuDQ4psRLTfevlGa8VMSjM4q341WUWQ5KG5OJMVYadjtebuyVUQgD4hVMbqCzQrwgnBnzFK12fAvvrCiMdRjVXtZiX6IjZHGPUYSTHgwRTnswQnjVgzHCWQ-a7aLI78EpwqAHzXbTmdlYbfcYchAEcfVKZOwN8O2wbNJl0sJpF6pXc-gm21WuLx-Vm2NLcROcX5d0ZN1vijQ4bA5Oiqzr78C77SfHg9k-sY1hG8EW_gfn2Aa27LbMtqwO57Ltaxu7G-93Yg5z1ggbtVtGanvHSnYic8r94fb1WBIpNNNclL_VgfQ4p0MGtEWOacMLYqBnCcPWGJf0LMPR4Gn3vH4rJyNpGQ-VOp-XOmdj2LL681lUD9Z1AUwmWu6B4Ot9A5mQh0uapQUcWcvwc6FzkE9cmh-qNSsUfH4BMIkGBw==");
        addMobs(6, 8, false, "horrorspiritcultmelee");
        addMobs(0, 9, false, "horrorspiritmob");
        addMobs(2, 9, false, "horrorspiritmob");
        addMobs(4, 9, false, "horrorspiritmob");
        addMobs(6, 9, false, "horrorspiritmob");
        addMobs(8, 9, false, "horrorspiritmob");
        addMobs(10, 9, false, "horrorspiritmob");
        addInventory(smallHorrorChest, random, 6, 5);
    }
}