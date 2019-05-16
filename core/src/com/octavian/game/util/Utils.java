package com.octavian.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.octavian.game.config.Assets;
import com.octavian.game.config.Config;
import com.octavian.game.config.GameState;
import com.octavian.game.datamodel.Player;
import com.octavian.game.datamodel.Skin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by octavian on 2/21/18.
 */

public final class Utils {

    private Utils(){
        //Do not instantiate.
    }

    public static void clearScreen(){
        //Black
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static int randomNumber(int max){
        Random random = new Random();
        return random.nextInt(max);
    }


    public static boolean checkBack(){
        return Gdx.input.isKeyJustPressed(Input.Keys.BACK);
    }

    @Deprecated
    public static List<Texture> loadTextures(String[] args) {
        ArrayList<Texture> list = new ArrayList<>();

        for (String i : args) {
            list.add(new Texture(i));
        }

        return list;
    }

    @Deprecated
    public static void drawText(String text, BitmapFont bitmapFont, SpriteBatch batch, float x, float y){
            GlyphLayout gl = new GlyphLayout();
            BitmapFont scoreBounds = bitmapFont;
            scoreBounds.getData().setScale(3, 3);
            gl.setText(scoreBounds, text);
            scoreBounds.draw(batch, text, x, y);
    }

    public static void drawDebugWidthHeight(BitmapFont bitmapFont, SpriteBatch batch){

        StringBuilder sb = new StringBuilder();

        sb.append("W: ");
        sb.append(Gdx.graphics.getWidth());
        sb.append(", H: ");
        sb.append(Gdx.graphics.getHeight());

        BitmapFont coords = bitmapFont;

        coords.draw(batch, sb.toString(), 400, 400 );
    }

    public static void drawDebugPlayerCoords(Player player, BitmapFont bitmapFont, SpriteBatch batch){
            String ics = Float.toString(player.getX());
            String igrec = Float.toString(player.getY());
            String result = ics + ", " + igrec;

            GlyphLayout gl = new GlyphLayout();
            BitmapFont coords = bitmapFont;
            coords.getData().setScale(3, 3);
            gl.setText(coords, ics + igrec);

            coords.draw(batch, result, 100, 100 );
    }


    private static int getMinimum(List<Integer> list){
        int minim = Integer.MAX_VALUE;
        for(Integer i : list){
            if(i < minim && i != 0)
                minim = i;
        }

        return minim;
    }


    /**
     * Little easter egg.
     * @return TRUE
     */
    public static boolean isRichardARooster(){
        return true;
    }

    public static String readAbout(){
        FileHandle file;
        String about;
        file = Gdx.files.internal(Config.ABOUT_TEXT_FILE);
        return file.readString();
    }

    /**
     * Dummy Skins generator. Use only for debuging purposes.
     * @return 9 dummy skins.
     */
    public static Array<Skin> getDummySkins(){
        Array<Skin> skins = new Array<>();
        boolean isEven;

        for(int i = 0; i < 9; i++){
            isEven = i % 2 == 0 ? true : false;
            skins.add(new Skin("dummy" + i, Config.SKINS_ARRAY[i], i * 10, isEven));
        }

        return skins;
    }
}
