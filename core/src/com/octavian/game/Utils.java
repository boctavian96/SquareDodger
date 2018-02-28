package com.octavian.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.octavian.game.config.Config;
import com.octavian.game.config.GameState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by octavian on 2/21/18.
 */

public class Utils {

    /**
     * Cleas the screen
     */
    public static void clearScreen(){
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
    public static void writeGameFile(long value, char mode) throws IOException{

        FileHandle file = null;

            switch (mode) {
                case 'c':
                    file = Gdx.files.local(Config.CN_FILE);
                    break;
                case 'h':
                    file = Gdx.files.local(Config.HS_FILE);
                    break;
                default:
                    throw new IOException();
            }

        if(!file.exists()) {
            try {
                file.file().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        file.writeString(String.valueOf(value), false);
    }

    /**
     *
     * @param mode
     * @return
     * @throws IOException
     */
    public static long getGameFile(char mode) throws IOException{

        FileHandle file = null;

        switch (mode) {
            case 'h':
                file = Gdx.files.local(Config.HS_FILE);
                break;
            case 'c':
                file = Gdx.files.local(Config.CN_FILE);
                break;
            default:
                throw new IOException();
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
    public static List<Texture> loadTextures(String[] args){
        ArrayList<Texture> list = new ArrayList<Texture>();

        for(String i : args){
            list.add(new Texture(i));
        }

        return list;
    }

}
