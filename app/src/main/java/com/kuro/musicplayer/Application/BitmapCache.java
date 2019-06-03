package com.kuro.musicplayer.Application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

public class BitmapCache extends Application {
    private static LruCache<String, Bitmap> mCaches;

    @Override
    public void onCreate() {
        super.onCreate();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        //赋予缓存区最大缓存的四分之一进行缓存
        int cacheSize = maxMemory / 4;
        Log.i("----cache max size", String.valueOf(cacheSize/1024/1024)+"m");
        mCaches = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存的时候调用
                Log.i("----BitMapSize", String.valueOf(value.getByteCount())+"byte");
                return value.getByteCount();
            }

        };
    }
}
