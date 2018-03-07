package com.octavian.game.statistics;

import com.badlogic.gdx.Gdx;

/**
 * Created by octavian on 3/7/18.
 */

public class MemoryUsage {
    private long memUsageJavaHeap;
    private long memUsageJavaNativeHeap;

    public MemoryUsage(){
        memUsageJavaHeap = Gdx.app.getJavaHeap();
        memUsageJavaNativeHeap = Gdx.app.getNativeHeap();
    }

    public String getInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("Java Heap : ");
        sb.append(toMega(memUsageJavaHeap));
        sb.append(" MB JN Heap : ");
        sb.append(toMega(memUsageJavaNativeHeap));
        sb.append(" MB");

        return sb.toString();
    }

    private long toMega(long v){
        return v/(1024 * 1024);
    }
}
