package com.octavian.game.util;

import com.badlogic.gdx.Gdx;
import com.octavian.game.datamodel.Player;

/**
 * Created by octavian on 3/3/18.
 */

public class GameInput {

    public void queryInput(Player player){
        boolean isTouched = Gdx.input.isTouched();

        if(isTouched) {
            float x = (float) Gdx.input.getX() - 20;
            float y = Gdx.graphics.getHeight() + 50 - (float) Gdx.input.getY();
            player.update(x, y);
        }

    }

}
