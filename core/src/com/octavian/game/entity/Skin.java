package com.octavian.game.entity;

import com.octavian.game.util.Utils;

import java.util.List;

/**
 * Created by octavian on 2/25/18.
 */

public class Skin{

    private List<String> skins_values;

    public Skin(){
        skins_values = Utils.getSkinsStatus();
    }

    public Skin(List<String> args){
        this.skins_values = args;
    }

    public List<String> readValues(){
        skins_values = Utils.getSkinsStatus();
        return skins_values;
    }

    public void writeValues(){
        Utils.setSkinsStatus(skins_values);
    }

}
