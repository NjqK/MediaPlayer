package com.kuro.musicplayer.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuro.musicplayer.model.Music;
import com.kuro.musicplayer.R;

import java.util.List;

public class MusicListAdapter extends BaseAdapter {

    private List<Music> music;
    private Context context;

    public MusicListAdapter(List<Music> music, Context context) {
        this.music = music;
        this.context = context;
    }

    class ViewHolder {
        TextView name;
        TextView musician;
        ImageView albumBmp;
    }

    @Override
    public int getCount() {
        return music.size();
    }

    @Override
    public Object getItem(int position) {
        return music.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View v;
        ViewHolder viewHolder;
        if (view == null) {
            v = LayoutInflater.from(context).inflate(R.layout.music_item,null);//创建布局构造器
            viewHolder = new ViewHolder();//缓存对象实例化
            viewHolder.name = v.findViewById(R.id.musicName);//绑定ID
            viewHolder.musician = v.findViewById(R.id.musician);
            viewHolder.albumBmp = v.findViewById(R.id.music_pic);
            v.setTag(viewHolder);
        } else {
            v = view;//创建行布局视图
            viewHolder = (ViewHolder) v.getTag();
        }
        Music m = music.get(position);
        viewHolder.name.setText(m.getMusicName());
        viewHolder.musician.setText(m.getMusician());
        if (m.getImagePath() != null) {
            viewHolder.albumBmp.setImageResource(m.getImagePath());
        } else {
            viewHolder.albumBmp.setImageResource(R.mipmap.placeholder_disk_210);
        }

        return v;
    }
}
