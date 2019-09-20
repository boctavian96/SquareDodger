package com.octavian.game.datamodel;

import com.badlogic.gdx.utils.Array;
import com.octavian.game.config.Assets;
import com.octavian.game.config.Config;

/**
 * Created by octavian on 4/14/19.
 */

public class SkinStatus {
    private Array<Skin> skins;
    public SkinStatus(){
        skins = new Array<>();
        skins.add(new Skin("The Square", Config.SKINS_ARRAY[0], 0, true));
        skins.add(new Skin("The Weirdo", Config.SKINS_ARRAY[1], 10, false));
        skins.add(new Skin("The Grass", Config.SKINS_ARRAY[2], 20, false));
        skins.add(new Skin("Teddy", Config.SKINS_ARRAY[3], 30, false));
        skins.add(new Skin("Bling Bling", Config.SKINS_ARRAY[4], 40, false));
        skins.add(new Skin("Richard", Config.SKINS_ARRAY[5], 50, false));
        skins.add(new Skin("Dovle", Config.SKINS_ARRAY[6], 60, false));
        skins.add(new Skin("Smash Mouth", Config.SKINS_ARRAY[7], 70, false));
        skins.add(new Skin("Shawn", Config.SKINS_ARRAY[8], 150, false));/*aka Adeleena oitza*/
        skins.add(new Skin("Horica", Config.SKINS_ARRAY[9], 170, false));
        skins.add(new Skin("Piggy", Config.SKINS_ARRAY[10], 190, false));
        skins.add(new Skin("Don", Config.SKINS_ARRAY[11], 200, false));
    }
    public SkinStatus(Array<Skin> inSkins){
      this.skins = inSkins;
    }

    public void setSkins(Array<Skin> skins){
        this.skins = skins;
    }

    public Array<Skin> getSkins() {
        return skins;
    }
}
