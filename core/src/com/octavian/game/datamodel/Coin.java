package com.octavian.game.datamodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.octavian.game.util.Utils;

/**
 * Created by octavian on 4/29/19.
 */

public class Coin extends Obstacle {
    public static final float COIN_SPEED = 300F;

    public Coin(Texture texture){
        super(texture);
    }

    public void update(float delta){
        setPosition(y - (COIN_SPEED * delta));
        updateCollisionRectangle();
    }

    public void reset(float y){
        this.y = y;
        x = Utils.randomNumber(Gdx.graphics.getWidth());
        updateCollisionRectangle();
    }


}
