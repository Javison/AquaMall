package com.tarea1.mallk.utils;

/**
 * Created by javigm on 01/12/13.
 */
public class InstagramHelper {
    public final static String INSTAGRAM_API_KEY = "InsertYourInstagramKey";
    public final static String BASE_API_URL = "https://api.instagram.com/v1/";

    public static String getRecentUrl(String tag){
        return BASE_API_URL + "tags/" + tag + "/media/recent?client_id=" + INSTAGRAM_API_KEY;
    }

    //https://api.instagram.com/v1/tags/guatemala/media/recent?client_id=yyyy
    //https://api.instagram.com/v1/tags/search?q=money&client_id=xxxx
}
