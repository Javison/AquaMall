package com.tarea1.mallk.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by javigm on 02/12/13.
 */
public class BitmapLRUCache extends LruCache<String,Bitmap> implements ImageLoader.ImageCache {
    //4 MEGAS = Tamaño cache
    //MEGAS = 4 * 1024 = Kb * 1024 = MB
    public static final int CACHE_SIZE_BYTES = 4 * 1024 * 1024;

    public BitmapLRUCache() {
        super(CACHE_SIZE_BYTES);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url,bitmap);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return(value.getRowBytes() * value.getHeight());
    }


}
