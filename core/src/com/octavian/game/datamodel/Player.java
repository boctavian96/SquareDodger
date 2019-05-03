package com.octavian.game.datamodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.octavian.game.config.Config;


/**
 * Created by octavian on 2/21/18.
 */

public class Player {

    private static float SIZE = 48F;

    private final Rectangle collisionRectangle;
    private float x;
    private float y;
    private Texture t;

    public Player(Texture t, float x, float y){
        this.t = t;
        this.x = x;
        this.y = y;
        collisionRectangle = new Rectangle(x, y, SIZE, SIZE);

    }

    public void update(float x, float y){
        float virtualY = Config.WORLD_HEIGHT + 800 - y;

        if(virtualY < Gdx.graphics.getHeight()) {
            setY(virtualY);
        }else{
            setY(Gdx.graphics.getHeight() - 500);
        }

        if (virtualY < 100){
            setY(100);
        }

        if (x < Gdx.graphics.getWidth()){
            setX(x);
        }else{
            setX(Gdx.graphics.getWidth() - 50);
        }

        if(x < 0){
            setX(50);
        }
        updateCollisionRectangle();
    }

    public void update(Vector3 touchPoint){
        update(touchPoint.x, touchPoint.y);
    }

    public void draw(SpriteBatch sb){
        sb.draw(t, this.x, this.y);
    }

    public void setX(float x){
        this.x = x;
        updateCollisionRectangle();
    }

    public void setY(float y){
        this.y = y;
        updateCollisionRectangle();
    }


    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public Texture getTexture(){
        return t;
    }

    public Rectangle getCollisionRectangle(){
        return collisionRectangle;
    }

    private void updateCollisionRectangle(){
        collisionRectangle.setPosition(x, y);
    }


}
