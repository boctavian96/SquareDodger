package com.octavian.game.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.octavian.game.config.Config;

/**
 * Created by octavian on 4/9/19.
 */

public abstract class AbstractGameScreen extends ScreenAdapter{

    protected Camera camera;
    protected FitViewport viewport;
    protected Stage stage;

    protected float screenHeight;
    protected float screenWidth;

    public AbstractGameScreen(){
        screenHeight = Config.WORLD_HEIGHT;
        screenWidth = Config.WORLD_WIDTH;

        camera = new OrthographicCamera();
        camera.position.set(0, 0, 0);
        viewport = new FitViewport(Config.WORLD_WIDTH, Config.WORLD_HEIGHT, camera);
    }

    public abstract void update(float delta);
    public abstract void render(float delta);
    public abstract void draw();
}
