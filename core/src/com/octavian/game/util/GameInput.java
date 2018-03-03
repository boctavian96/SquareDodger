package com.octavian.game.util;

import com.badlogic.gdx.Gdx;
import com.octavian.game.entity.Player;

/**
 * Created by octavian on 3/3/18.
 */

public class GameInput {

    public void queryInput(Player player){
        boolean isTouched = Gdx.input.isTouched();

        if(isTouched) {
            float x = (float) Gdx.input.getX();
            float y = Gdx.graphics.getHeight() + 50 - (float) Gdx.input.getY();
            player.update(x, y);
        }

    }

}
