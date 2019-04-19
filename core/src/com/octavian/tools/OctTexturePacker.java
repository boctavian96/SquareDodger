package com.octavian.tools;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * Created by octavian on 4/15/19.
 */

public class OctTexturePacker {
    public static void main (String[] args) throws Exception {
        //inputDir, outputDir, packFileName
        TexturePacker.process(args[0], args[1], args[2]);
    }
}
