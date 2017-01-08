package com.tarea1.mallk.utils;

/**
 * Created by javigm on 01/12/13.
 */
public class InstagramHelper {
    public final static String INSTAGRAM_API_KEY = "33feddd0b4394e60aaee785471cd76a6";
    public final static String BASE_API_URL = "https://api.instagram.com/v1/";

    public static String getRecentUrl(String tag){
        return BASE_API_URL + "tags/" + tag + "/media/recent?client_id=" + INSTAGRAM_API_KEY;
    }

    //https://api.instagram.com/v1/tags/guatemala/media/recent?client_id=33feddd0b4394e60aaee785471cd76a6
    //https://api.instagram.com/v1/tags/search?q=money&client_id=f40dfb17ddd144598d562a6f58179006
}
