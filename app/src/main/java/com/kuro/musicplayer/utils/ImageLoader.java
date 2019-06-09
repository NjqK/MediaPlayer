package com.kuro.musicplayer.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.kuro.musicplayer.Adapter.AsyncOnlineMusicAdapter;
import com.kuro.musicplayer.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ImageLoader {
    private ImageView mImageview;
    private String mUrl;
    public static LruCache<String, Bitmap> mCaches;
    private ListView mListView;
    private Set<NewsAsyncTask> mTask;

    private final static ImageLoader imageLoader = new ImageLoader();

    public static ImageLoader getImageLoaderSingleton() {
        return imageLoader;
    }

    public void setmListView(ListView mListView) {
        this.mListView = mListView;
    }

    private ImageLoader() {
        mTask = new HashSet<>();
        //获得最大的内存空间
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 3;
        //Log.i("----cache max size", String.valueOf(cacheSize/1024/1024)+"m");
        mCaches = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存的时候调用
                //Log.i("----BitMapSize", String.valueOf(value.getByteCount())+"byte");
                return value.getByteCount();
            }
        };
    }

    //将图片通过url与bitmap的键值对形式添加到缓存中
    public void addBitmapToCache(String url, Bitmap bitmap) {
        if (getBitmapFromCache(url) == null) {
            //Log.i("-----Put Cache", "put url: "+url);
            mCaches.put(url, bitmap);
        }
    }

    public static Bitmap getBitmapFromCache(String url) {
        return mCaches.get(url);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mImageview.getTag().equals(mUrl)) {
                mImageview.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    //通过线程的方式去展示图片
    public void showImageByThread(ImageView imageView, String url) {
        mImageview = imageView;
        mUrl = url;
        new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapFromUrl(mUrl);
                Message message = Message.obtain();
                message.obj = bitmap;
                mHandler.sendMessage(message);
            }
        }.start();
    }

    //通过异步任务的方式去加载图片

    public void showImageByAsyncTask(ImageView imageView, String url) {
        //先从缓存中获取图片
        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap == null) {
            Log.i("-----Bitmap", "Missed " + url);
            //imageView.setImageResource(R.mipmap.placeholder_disk_210);
            NewsAsyncTask task = new NewsAsyncTask(url);
            task.execute(url);
            mTask.add(task);
        } else {
            Log.i("-----Bitmap", "Cached  " + url);
            imageView.setImageBitmap(bitmap);
        }
    }

    private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap> {

        //     private ImageView mImageView;
        private String mUrl;

        /*
         * onPreExecute是可以选择性覆写的方法
         * 在主线程中执行,在异步任务执行之前,该方法将会被调用
         * 一般用来在执行后台任务前对UI做一些标记和准备工作，
         * 如在界面上显示一个进度条。
         */

        /*
         * onProgressUpdate是可以选择性覆写的方法
         * 在主线程中执行,当后台任务的执行进度发生改变时,
         * 当然我们必须在doInBackground方法中调用publishProgress()
         * 来设置进度变化的值
         */

        /*
         * onCancelled是可以选择性覆写的方法
         * 在主线程中,当异步任务被取消时,该方法将被调用,
         * 要注意的是这个时onPostExecute将不会被执行
         */

        public NewsAsyncTask( String url) {
            mUrl = url;
        }

        /**
         * 异步任务
         * @param params
         * @return Bitmap
         * */
        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = getBitmapFromCache(url);
            if (bitmap == null) {
                ImageView imageView = (ImageView) mListView.findViewWithTag(mUrl);
                bitmap = getBitmapFromUrl(url);
                //压缩bitmap, 避免缓冲区一直溢出导致的性能问题， 避免OOM
                //Log.i("----", imageView.getWidth()+" "+imageView.getHeight());
                //内存紧张时可能就被jvm回收了
                if (imageView == null) {
                    bitmap = BitMapHelper.createScaleBitmap(bitmap, 100, 100, false);
                } else {
                    bitmap = BitMapHelper.createScaleBitmap(bitmap, imageView.getWidth(), imageView.getHeight(), false);
                }
                addBitmapToCache(url, bitmap);
            }
            return bitmap;
        }

        /**
         * onPostExecute是可以选择性覆写的方法
         * 在主线程中执行,在异步任务执行完成后,此方法会被调用
         * 一般用于更新UI或其他必须在主线程执行的操作,传递参数bitmap为
         * doInBackground方法中的返回值
         * @param bitmap 要添加的bitmap
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) mListView.findViewWithTag(mUrl);
            if (imageView!=null&&bitmap!=null){
                imageView.setImageBitmap(bitmap);
            }
            mTask.remove(this);
        }
    }

    /**
     * 滑动时加载图片
     * @param start 要加载的起始下标
     * @param end 要加载的最后一个的下标
     */
    public void loadImages(int start, int end) {
        Log.i("-----LoadImg", "start: "+start+" end: "+end);
        for (int i = start; i < end; i++) {
            String url = AsyncOnlineMusicAdapter.URLS[i];
            //先从缓存中获取图片
            Bitmap bitmap = getBitmapFromCache(url);
            if (bitmap == null) {
                NewsAsyncTask task = new NewsAsyncTask(url);
                task.execute(url);
                mTask.add(task);
            } else {
                Log.i("-----LoadImg", "id: "+i);
                ImageView imageView = (ImageView) mListView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    //停止时取消所有任务加载
    public void cancelAllTasks(){
        if (mTask!=null){
            for (NewsAsyncTask task :mTask){
                task.cancel(false);
            }
        }
    }
    //网络获取图片
    private Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        Log.i("-----URL: ", urlString);
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert is != null;
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
