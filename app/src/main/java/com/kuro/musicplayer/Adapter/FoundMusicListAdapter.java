package com.kuro.musicplayer.Adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kuro.musicplayer.R;
import com.kuro.musicplayer.model.Music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoundMusicListAdapter extends BaseAdapter {
    private List<Music> musics;
    // replace Map<Integer, Boolean> to SparseBooleanArray for better performance
    private SparseBooleanArray isSelected;
    private Context context;

    public FoundMusicListAdapter(List<Music> musics, Context context){
        this.context = context;
        this.musics = musics;
        //初始时都没有选中
        isSelected = new SparseBooleanArray();
        for (int i=0; i<musics.size(); i++) {
            isSelected.put(i, false);
        }
    }

    public void setSelected(int position, boolean state) {
        isSelected.put(position, state);
    }

    public List<Music> getSelected() {
        List<Music> result = new ArrayList<Music>();
        for (int i =0; i<musics.size(); i++) {
            if (isSelected.get(i)) {
                result.add(musics.get(i));
            }
        }
        return result;
    }

    public void chooseAll() {
        for (int i =0; i<musics.size(); i++) {
            setSelected(i, true);
        }
    }

    public void cancelAll() {
        for (int i =0; i<musics.size(); i++) {
            setSelected(i, false);
        }
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
        ViewHolder holder;
        if(convertView == null){
            v = LayoutInflater.from(context).inflate(R.layout.musicitem, null);
            holder=new ViewHolder();
            holder.cb = v.findViewById(R.id.check_music);
            holder.musicName = v.findViewById(R.id.music_name);
            v.setTag(holder);
        }else{
            v = convertView;//创建行布局视图
            holder = (ViewHolder) v.getTag();
        }
        Music music = musics.get(position);
        holder.cb.setChecked(isSelected.get(position));
        holder.musicName.setText(music.getMusicName() + "-" + music.getMusician());
        return v;
    }

    public class ViewHolder{
        public CheckBox cb;
        TextView musicName;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
