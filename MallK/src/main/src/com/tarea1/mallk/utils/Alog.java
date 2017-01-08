package com.tarea1.mallk.utils;

import android.util.Log;

/**
 * Created by javigm on 29/11/13.
 */
public class Alog {
    public static String logTag = "TRAZA";
    public static boolean debugging = true;
    public static int nivelDetalle = 0;

    public static void pon(String s){
        if(debugging){
            Log.i(logTag, s);
        }

    }
}
