package com.octavian.game.windows;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.octavian.game.config.Config;

/**
 * Created by octavian on 4/9/19.
 */

public abstract class AbstractGameScreen extends ScreenAdapter{

    protected Vector3 touchPoint;
    protected OrthographicCamera camera;
    protected FitViewport viewport;
    protected Stage stage;

    protected float screenHeight;
    protected float screenWidth;

    public AbstractGameScreen(){
        camera = new OrthographicCamera(Config.WORLD_WIDTH, Config.WORLD_HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        viewport = new FitViewport(Config.WORLD_WIDTH, Config.WORLD_HEIGHT, camera);

        screenHeight = Config.WORLD_HEIGHT;
        screenWidth = Config.WORLD_WIDTH;
    }

}
