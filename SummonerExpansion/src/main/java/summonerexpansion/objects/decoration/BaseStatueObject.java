package summonerexpansion.objects.decoration;

import necesse.gfx.gameTexture.GameTexture;
import necesse.level.gameObject.StatueObject;

public class BaseStatueObject extends StatueObject
{
    public BaseStatueObject(String texturePath)
    {
        super(texturePath);
    }

    public GameTexture generateItemTexture()
    {
        return GameTexture.fromFile("items/objects/" + this.getStringID());
    }
}