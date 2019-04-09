package com.octavian.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by octavian on 4/9/19.
 */

public final class FontFactory {
    public static final String FONT_PRESS_START2P = "fonts/PressStart2P.ttf";

    private static FontFactory instance;

    private BitmapFont font;

    private FontFactory(){super();}

    public static synchronized FontFactory getInstance(){
        if(instance == null){
            instance = new FontFactory();
        }
        return instance;
    }

    public BitmapFont generateFont(String fontName, int size, Color color){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = size;
        parameter.color = color;

        font = generator.generateFont(parameter);
        generator.dispose();

        return font;
    }

    public void dispose(){
        font.dispose();
    }
}
