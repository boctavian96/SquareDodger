package com.octavian.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.octavian.game.config.Config;

public class DebugGrid {
    public static void render(ShapeRenderer shapeRenderer){

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for(int x = 0; x <= Gdx.graphics.getWidth(); x+=Config.WORLD_UNIT) {
            for(int y = 0; y <= Gdx.graphics.getHeight(); y+=Config.WORLD_UNIT) {
                shapeRenderer.rect(x, y, Config.WORLD_UNIT, Config.WORLD_UNIT); //assuming you have created those x, y, width and height variables
            }
        }

        shapeRenderer.end();
    }
}
