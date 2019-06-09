package com.kuro.musicplayer.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kuro.musicplayer.R;
import com.kuro.musicplayer.model.Music;
import com.kuro.musicplayer.utils.ImageLoader;

import java.util.List;

public class AsyncOnlineMusicAdapter extends BaseAdapter implements AbsListView.OnScrollListener{

    private List<Music> mList;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    private int mStart;
    private int mEnd;
    //创建静态数组保存图片的url地址
    public static String[] URLS;
    private LruCache<String, Bitmap> cache;
    private boolean mFirstIn;
    private ListView mListView;

    public AsyncOnlineMusicAdapter(Context context, List<Music> data, ListView listView) {
        mList = data;
        mListView = listView;
        mInflater = LayoutInflater.from(context);
        mImageLoader = ImageLoader.getImageLoaderSingleton();
        mImageLoader.setmListView(listView);
        URLS = new String[data.size()];
        for(int i=0;i<data.size();i++){
            URLS[i] = data.get(i).getNetImagePath();
        }
        listView.setOnScrollListener(this);
        mFirstIn = true;
        cache = ImageLoader.mCaches;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder v;
        if (convertView == null) {
            v = new ViewHolder();
            convertView = mInflater.inflate(R.layout.music_item, null);
            v.name = convertView.findViewById(R.id.musicName);//绑定ID
            v.musician = convertView.findViewById(R.id.musician);
            v.albumBmp = convertView.findViewById(R.id.music_pic);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }
        //设置默认显示的图片
        if (cache.get(URLS[position]) != null) {
            v.albumBmp.setImageBitmap(cache.get(URLS[position]));
        }else {
            Log.i("----AsyncAdapter", "cache size: " + cache.size());
            v.albumBmp.setImageResource(R.mipmap.placeholder_disk_210);
        }
        //避免缓存影响使同一位置图片加载多次混乱
        String url = mList.get(position).getNetImagePath();
        v.albumBmp.setTag(url);
        //mImageLoader.showImageByAsyncTask(v.albumBmp, url);
        v.name.setText(mList.get(position).getMusicName());
        v.musician.setText(mList.get(position).getMusician());
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState==SCROLL_STATE_IDLE){
            //加载可见项
            Log.i("-----Stop Scroll", "STOP");
            mImageLoader.loadImages(mStart,mEnd);
        }else{
            //停止加载
            mImageLoader.cancelAllTasks();
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        /*Log.i("-----加载", "start: "+mStart+" end: "+mEnd);
        if (mFirstIn && visibleItemCount>0){
            mImageLoader.loadImages(mStart,mEnd);
        }*/
    }

    class ViewHolder {
        TextView name;
        TextView musician;
        ImageView albumBmp;
    }
}
