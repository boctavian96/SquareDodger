package com.octavian.game.datamodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.octavian.game.util.Utils;

/**
 * Created by octavian on 4/29/19.
 */

public class Coin extends Obstacle {
    public static final float COIN_SPEED = 300F;

    private static final float ANIMATION_SPEED = 0.3f;
    private static final int FRAME_ROWS = 2;
    private static final int FRAME_COLS = 2;

    private Animation<TextureRegion> coinAnimation;
    private float stateTime = 0;

    public Coin(Texture texture){

        super(texture);
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);

        TextureRegion[] coinFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                coinFrames[index++] = tmp[i][j];
            }
        }

        coinAnimation = new Animation<TextureRegion>(ANIMATION_SPEED, coinFrames);

    }

    @Override
    public void draw(SpriteBatch batch){
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = coinAnimation.getKeyFrame(stateTime, true);

        batch.draw(currentFrame, x, y);
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
