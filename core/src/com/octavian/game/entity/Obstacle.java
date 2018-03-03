package com.octavian.game.entity;

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

    public static final float MAXIMUM_SPEED = 1000F;
    public static final float MINIMUM_SPEED = 300F;

    public static final float COLLISION_RECT_SIZE = 48F;

    private final Rectangle collisionRectangle;
    private float x;
    private float y;
    private Texture t;
    private float speed;

    public Obstacle(Texture t){
        this.t = t;
        this.x = (float)Utils.randomNumber((int)Gdx.graphics.getWidth());
        this.y = Gdx.graphics.getHeight();
        this.speed = (float)Utils.randomNumber((int)MAXIMUM_SPEED) + MINIMUM_SPEED;
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
        return this.y;
    }

    public void draw(SpriteBatch sb){
        sb.draw(this.t, this.x, this.y);
    }

    public boolean isPlayerColliding(Player player){

        Rectangle playerRect = player.getCollisionRectangle();

        return Intersector.overlaps(playerRect, collisionRectangle);
    }

    private void updateCollisionRectangle(){
        collisionRectangle.setY(this.y);
    }

}
