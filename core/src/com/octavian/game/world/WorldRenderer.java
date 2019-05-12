package com.octavian.game.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.octavian.game.Score;
import com.octavian.game.config.Assets;
import com.octavian.game.config.Config;
import com.octavian.game.datamodel.Coin;
import com.octavian.game.datamodel.Obstacle;
import com.octavian.game.datamodel.Player;
import com.octavian.game.util.Utils;


/**
 * Created by octavian on 4/20/19.
 */

public class WorldRenderer {

    private Score playerScore;

    private Array<Obstacle> obstacles;
    private int obstaclesNumber;
    private int obstaclesCreated;

    private Coin coin;
    private boolean coinPassed;

    public WorldRenderer(){
        playerScore = new Score();
        obstacles = new Array<Obstacle>();
        obstaclesNumber = 8;
        obstaclesCreated = 0;
        coin = new Coin(Assets.coinAnimation);
    }

    private void createNewObstacle(){
        if(obstacles.size < Config.MAXIMUM_OBSTACLES) {
            Obstacle newObstacle = new Obstacle(Assets.obstacleTextures.get(Utils.randomNumber(Config.NUMBER_OF_TEXTURES)));
            obstacles.add(newObstacle);
            obstaclesCreated++;
        }
    }

    public boolean isPlayerColliding(Player player){
        for(Obstacle obstacle : obstacles){
            if(obstacle.isPlayerColliding(player)){
                return true;
            }
        }
        return false;
    }

    public void checkIfObstacleIsNeeded(){
        if(obstacles.size < obstaclesNumber){
            createNewObstacle();
        }
        if(obstaclesCreated == 10){
            obstaclesNumber++;
            obstaclesCreated = 0;
        }
    }

    public void updateObstacles(float delta){
        for(Obstacle obstacle : obstacles){
            obstacle.update(delta);
        }

        checkIfObstacleIsNeeded();
        removeObstaclesIfPassed();
    }


    private void removeObstaclesIfPassed(){
        for(Obstacle obstacle : obstacles){
            if (obstacle.getY() < 0){
                obstacles.removeValue(obstacle, true);
                playerScore.addScore();
            }
        }
    }

    public void drawObstacles(SpriteBatch batch){
        for(Obstacle obstacle : obstacles){
            obstacle.draw(batch);
        }
    }

    public String getScore(){
        return "" + playerScore.getScore();
    }

    public void createCoin(){
        if(coinPassed){
            coin = new Coin(Assets.coinAnimation);
        }
    }

    public void dropCoin(SpriteBatch batch){
        coin.draw(batch);
    }

    public void updateCoin(float delta){
        coin.update(delta);
        if(isCoinPassed()){
            coin.reset(Config.WORLD_HEIGHT + 100F);
        }
    }

    public boolean isCoinColliding(Player player){
        if (coin.isPlayerColliding(player)){
            coin.setPosition(-50F);
            return true;
        }

        return false;
    }

    private boolean isCoinPassed(){
        if(coin.getY() < 0){
            return true;
        }
        return false;
    }

}