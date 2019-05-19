package com.octavian.game.datamodel;

import com.badlogic.gdx.graphics.Texture;
import com.octavian.game.config.Assets;
import com.octavian.game.util.Utils;

import java.util.List;

/**
 * Created by octavian on 2/25/18.
 */

public class Skin{

    private String skinName;
    private String texturePath;
    private int cost;
    private boolean isUnlocked;

    public Skin(){

    }

    public Skin(String name, String texturePath, int cost, boolean isUnlocked){
        skinName = name;
        this.texturePath = texturePath;
        this.cost = cost;
        this.isUnlocked = isUnlocked;
    }

    public String getName(){
        return skinName;
    }

    public Texture getTexture(){
        return Assets.loadTexture(texturePath);
    }

    public int getCost(){
        return cost;
    }

    public boolean isUnlocked(){
        return isUnlocked;
    }

    public void unlock(){ isUnlocked = true;}
}
