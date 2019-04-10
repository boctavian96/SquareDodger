package com.octavian.game.datamodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


/**
 * Created by octavian on 2/21/18.
 */

public class Player {

    private static float SIZE = 48F;

    private final Rectangle collisionRectangle;
    private float x=0;
    private float y=0;
    private Texture t;

    public Player(Texture t, float x, float y){
        this.t = t;
        this.x = x;
        this.y = y;
        collisionRectangle = new Rectangle(x, y, SIZE, SIZE);

    }

    public void update(float x, float y){
        if(y < Gdx.graphics.getHeight() - 100) {
            setY(y);
        }else{
            setY(Gdx.graphics.getHeight() - 100);
        }
        if (y < 50){
            setY(50);
        }

        setX(x);
        updateCollisionRectangle();
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
        this.collisionRectangle.setPosition(this.x, this.y);
    }


}
