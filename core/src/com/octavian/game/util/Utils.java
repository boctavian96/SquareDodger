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
import com.octavian.game.config.Config;
import com.octavian.game.config.GameState;
import com.octavian.game.datamodel.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by octavian on 2/21/18.
 */

public class Utils {

    /**
     * Clears the screen
     */
    public static void clearScreen(){
        //Black
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Generate a random number
     * @param max maximul
     * @return generated number
     */
    public static int randomNumber(int max){
        Random rand = new Random();
        int r = rand.nextInt(max);

        return r;
    }

    /**
     * Used for highscore and coin system
     * @param value
     * @param mode - c = COINS; h = HIGHSCORE
     */
    public static void writeGameFile(long value, char mode){

        FileHandle file = null;

            switch (mode) {
                case 'c':
                    file = Gdx.files.local(Config.CN_FILE);
                    break;
                case 'h':
                    file = Gdx.files.local(Config.HS_FILE);
                    break;
                default:
            }

        if(!file.exists()) {
                try {
                    file.file().createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
        }

        file.writeString(String.valueOf(value), false);
    }

    /**
     *
     * @param mode h - highscore / c - coins
     * @return
     * @throws IOException
     */
    public static long getGameFile(char mode) {

        FileHandle file = null;

        switch (mode) {
            case 'h':
                file = Gdx.files.local(Config.HS_FILE);
                break;
            case 'c':
                file = Gdx.files.local(Config.CN_FILE);
                break;
            default:
        }

        if(!file.exists()){
            writeGameFile(0, mode);
        }
        String result = file.readString();

        if(result.isEmpty()){
            return Long.valueOf("0");
        }else{
            return Long.valueOf(file.readString());
        }
    }

    public static void setSkinsStatus(List<String> status){
        FileHandle file;

        file = Gdx.files.local(Config.SKINS_LIST);

        if(!file.exists()){
            try {
                file.file().createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }

            for(int i = 0; i < status.size(); i++){
                file.writeString(String.valueOf(i * 10), false);
            }
        }else{
            for(String i : status)
                file.writeString(i, false);
        }
    }

    public static List<String> getSkinsStatus(){
        FileHandle file;
        String[] result;
        ArrayList<String> list = new ArrayList<String>();

        file = Gdx.files.local(Config.SKINS_LIST);

        if(!file.exists()){
            file = Gdx.files.internal("hard_skins.txt");
            result = file.readString().split(" ");
        }else{
            result = file.readString().split(" ");
        }

        for(String i : result){
            list.add(i);
        }

        return list;
    }

    /**
     *
     * @param state Actual state
     * @return Menu or the actual game state
     */
    public static GameState checkBack(GameState state){
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            if(state.equals(GameState.SKINS) || state.equals(GameState.ABOUT))
                return GameState.MENU;
        }

        return state;
    }

    /**
     * Function used to load the textures in main
     * @param args String with paths to textures
     * @return
     */
    public static List<Texture> loadTextures(String[] args) {
        ArrayList<Texture> list = new ArrayList<Texture>();

        for (String i : args) {
            list.add(new Texture(i));
        }

        return list;
    }

    /**
     * Draw a text on screen
     * @param text Text to draw
     * @param bitmapFont
     * @param batch
     * @param x x coordinate
     * @param y y coordinate
     */
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

    public static int lowestSkinCost(){
        List<String> dummy = Utils.getSkinsStatus();
        List<Integer> lst = new ArrayList<Integer>();

        for(String i : dummy){
            lst.add(Integer.valueOf(i));
        }

        return Utils.getMinimum(lst);
    }

    public static int getMinimum(List<Integer> list){
        int minim = Integer.MAX_VALUE;
        for(Integer i : list){
            if(i < minim && i != 0)
                minim = i;
        }

        return minim;
    }

    public static List<Boolean> availableSkins(){
        List<String> skn_list = Utils.getSkinsStatus();
        List<Boolean> bool_list = new ArrayList<Boolean>();

        for(String i : skn_list){
           if(i.equals("0")){
                bool_list.add(true);
            }else{
                bool_list.add(false);
            }
        }

        return bool_list;
    }

}
