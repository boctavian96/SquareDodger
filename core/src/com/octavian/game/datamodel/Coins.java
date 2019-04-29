package com.octavian.game.datamodel;

import com.octavian.game.config.Config;

/**
 * Created by octavian on 2/27/18.
 */

public class Coins {

    private long coins;

    public Coins(long coins){
        this.coins = coins;
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

    public void setCoins(long c){
        this.coins = c;
    }

    public void payCoins(long value){
        if(value > 0) {
            if (value >= this.coins) {
                this.coins -= value;
            }
        }else{
            if(-value >= this.coins){
                this.coins += value;
            }
        }

    }
}
