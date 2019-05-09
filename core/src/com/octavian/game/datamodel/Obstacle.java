package com.octavian.game.datamodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.octavian.game.util.Utils;

/**
 * Created by octavian on 2/21/18.
 */

public class Obstacle {

    public static final float MAXIMUM_SPEED = 900F;
    public static final float MINIMUM_SPEED = 300F;

    public static final float COLLISION_RECT_SIZE = 48F;

    protected final Rectangle collisionRectangle;
    protected float x;
    protected float y;
    protected Texture t;
    protected float speed;

    public Obstacle(Texture t){
        this.t = t;
        x = Utils.randomNumber(Gdx.graphics.getWidth());
        y = Gdx.graphics.getHeight();
        speed = Utils.randomNumber((int)MAXIMUM_SPEED) + MINIMUM_SPEED;
        collisionRectangle = new Rectangle(x, y, COLLISION_RECT_SIZE, COLLISION_RECT_SIZE);
    }

    public void setPosition(float y){
        this.y = y;
    }

    public void update(float delta){
        setPosition(y - (speed * delta));
        updateCollisionRectangle();
    }

    public float getY(){
        return y;
    }

    public void draw(SpriteBatch sb){
        sb.draw(t, x, y);
    }

    public boolean isPlayerColliding(Player player){

        Rectangle playerRect = player.getCollisionRectangle();

        return Intersector.overlaps(playerRect, collisionRectangle);
    }

    protected void updateCollisionRectangle(){
        collisionRectangle.setY(y);
        collisionRectangle.setX(x);
    }

}
