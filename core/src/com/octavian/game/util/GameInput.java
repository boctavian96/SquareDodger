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
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() + 50 - (float) Gdx.input.getY();
            player.update(x, y);

            Gdx.app.log("X: ", "" + x);
            Gdx.app.log("Y: ", "" + y);
        }

    }

    public boolean isSwipeRight(){
        return (Gdx.input.isTouched() && Gdx.input.getDeltaX() > 0);
    }

    public boolean isSwipeLeft(){
        return (Gdx.input.isTouched() && Gdx.input.getDeltaX() < 0);
    }

    public boolean isSwipeUp(){
        return (Gdx.input.isTouched() && Gdx.input.getDeltaY()>0);
    }

    public boolean isSwipeDown(){
        return (Gdx.input.isTouched() && Gdx.input.getDeltaX()<0);
    }

}
