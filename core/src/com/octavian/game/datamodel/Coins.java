package com.octavian.game.datamodel;

import com.badlogic.gdx.Gdx;

/**
 * Created by octavian on 2/27/18.
 */

public class Coins {

    private long coins;

    public Coins(long coins){
        this.coins = coins;
    }

    public Coins(){
        coins = 0L;
    }

    public Coins(String coins){
        this.coins = Long.valueOf(coins);
    }

    public long getCoins(){
        return coins;
    }

    public void addCoins(){
        coins += 1L;
    }

    public void addCoins(long newCoins) {
        coins += newCoins;
    }

    public void setCoins(long c){
        this.coins = c;
    }

    public boolean payCoins(long value){
        if(coins >= value && value > 0){
            coins -= value;
            Gdx.app.log("INFO", "Purchase successful!");
            return true;
        }else{
            Gdx.app.log("COINS", "Not enough coins or the value is negative!");
            return false;
        }
    }

}
