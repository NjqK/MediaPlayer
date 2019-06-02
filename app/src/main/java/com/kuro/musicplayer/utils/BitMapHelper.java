package com.kuro.musicplayer.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitMapHelper {
    /**
     * @param reqWidth 要求的宽，一般是ImageView的大小
     * @note 获得图片的采样率，避免浪费资源或者图片太大显示不出来
     * */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.i("------Image真实大小", " "+width+" "+height);
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap createScaleBitmap(Bitmap src, int dstWidth, int dstHeight) {
        // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响，我们这里是缩小图片，所以直接设置为false
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }

    public static Bitmap createScaleBitmap(Bitmap src, int dstWidth, int dstHeight, boolean fliter) {
        // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响，我们这里是缩小图片，所以直接设置为false
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, fliter);
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }

    public static Bitmap createScaleBitmap(String src, int dstWidth, int dstHeight) {
        Bitmap temp = getURLimage(src);
        return createScaleBitmap(temp, dstWidth, dstHeight);
    }

    public static Bitmap decodeSampledBitmapFromFile(String pathName, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return createScaleBitmap(src, reqWidth, reqHeight);
    }

    private static Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myURL = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(6000);//设置超时
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();//获得图片的数据流
                BitmapFactory.Options options = new BitmapFactory.Options();

                bmp = BitmapFactory.decodeStream(is);
                is.close();
            } else {
                Log.i("-----OnlineAdapter", "getImageFailed: " + url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }
}
