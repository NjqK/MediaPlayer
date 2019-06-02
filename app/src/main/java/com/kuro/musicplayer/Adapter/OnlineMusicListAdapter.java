package com.kuro.musicplayer.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuro.musicplayer.R;
import com.kuro.musicplayer.model.Music;
import com.kuro.musicplayer.model.OnlineMusicBean;
import com.kuro.musicplayer.utils.BitMapHelper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class OnlineMusicListAdapter extends BaseAdapter {

    private Context context;
    private List<Music> musics;
    private ViewHolder viewHolder;

    public OnlineMusicListAdapter(List<Music> musics, Context context) {
        this.context = context;
        this.musics = musics;
    }

    class ViewHolder {
        TextView name;
        TextView musician;
        ImageView albumBmp;
    }

    @Override
    public int getCount() {
        return musics.size();
    }

    @Override
    public Object getItem(int position) {
        return musics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = LayoutInflater.from(context).inflate(R.layout.music_item,null);//创建布局构造器
            viewHolder = new ViewHolder();//缓存对象实例化
            viewHolder.name = v.findViewById(R.id.musicName);//绑定ID
            viewHolder.musician = v.findViewById(R.id.musician);
            viewHolder.albumBmp = v.findViewById(R.id.music_pic);
            v.setTag(viewHolder);
        } else {
            v = convertView;//创建行布局视图
            viewHolder = (ViewHolder) v.getTag();
        }
        Music m = musics.get(position);
        viewHolder.name.setText(m.getMusicName());
        viewHolder.musician.setText(m.getMusician());
        String path = m.getNetImagePath();
        if (path != null) {
            setNetImage(path);
        } else {
            Log.i("------", "lalala");
            viewHolder.albumBmp.setImageResource(R.mipmap.placeholder_disk_210);
        }
        return v;
    }

    private void setNetImage(final String path) {
        Log.i("------setNetImg", path);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Bitmap bitmap = BitMapHelper.decodeSampledBitmapFromFile(path, iWidth, iHeight);
                Bitmap bitmap = BitMapHelper.createScaleBitmap(path, 50, 50);
                Message m = Message.obtain();
                m.what = 0x123;
                m.obj = bitmap;
                handler.sendMessage(m);
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                viewHolder.albumBmp.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    /*private Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
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
    }*/

    /*private String getImageURL(OnlineMusicBean m) {
        return m.getSongs().get(0).getAl().getPicUrl();
    }

    private String getMusician(OnlineMusicBean m) {
        return m.getSongs().get(0).getAr().get(0).getName();
    }

    private String getMusicName(OnlineMusicBean m) {
        return m.getSongs().get(0).getName();
    }*/
}
