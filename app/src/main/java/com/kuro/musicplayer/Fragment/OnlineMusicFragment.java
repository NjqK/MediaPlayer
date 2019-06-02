package com.kuro.musicplayer.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.kuro.musicplayer.Adapter.AsyncOnlineMusicAdapter;
import com.kuro.musicplayer.Adapter.OnlineMusicListAdapter;
import com.kuro.musicplayer.R;
import com.kuro.musicplayer.activity.PlayActivity;
import com.kuro.musicplayer.model.Music;
import com.kuro.musicplayer.model.OnlineMusicBean;
import com.kuro.musicplayer.model.OnlineMusicDownloadBean;
import com.kuro.musicplayer.model.OnlineSongSheetBean;
import com.kuro.musicplayer.utils.WebRequestUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OnlineMusicFragment extends Fragment {
    private static final String TAG = "-----MusicFragment";
    private View view;
    private ListView listView;
    //private List<OnlineMusicBean> musics;
    private List<Music> music;
    private LinearLayout addMusic;

    public interface OnlineCallBack {
        void downloadMusic();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.local_fragment, container, false);
        listView = view.findViewById(R.id.local_fragment_listview);
        addMusic = view.findViewById(R.id.new_musiclist);
        addMusic.setVisibility(View.GONE);
        //initUI();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("------Online", String.valueOf(music.get(position).toString()));
                Intent intent = new Intent(getActivity(), PlayActivity.class);
                intent.putExtra("index", position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("musicList", (Serializable) music);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initUI();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NewApi")
    private void initUI() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //去获取数据
                OnlineSongSheetBean songSheet = WebRequestUtil.getSongSheet();
                List<OnlineMusicBean> result = WebRequestUtil.getOnlineMusicsBySongSheet(songSheet);
                List<OnlineMusicDownloadBean> urls = WebRequestUtil.getOnlineSong(songSheet);
                List<Music> musics = new ArrayList<Music>();
                for (int i=0; i<result.size(); i++) {
                    Music m = new Music();
                    m.setMusicName(result.get(i).getSongs().get(0).getName());
                    m.setMusician(result.get(i).getSongs().get(0).getAr().get(0).getName());
                    m.setPath(urls.get(i).getData().get(0).getUrl());
                    m.setNetImagePath(result.get(i).getSongs().get(0).getAl().getPicUrl());
                    musics.add(m);
                }
                Message m = new Message();
                m.what = 0x123;
                m.obj = musics;
                handler.sendMessage(m);
            }
        }).start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            /*switch (msg.what) {
                case 0x123:
                    new GetMusicDetailThread((OnlineSongSheetBean) msg.obj).start();
                    new GetMusicThread((OnlineSongSheetBean) msg.obj).start();
                    break;
                case 0x124:
                    List<OnlineMusicBean> result = (List<OnlineMusicBean>) msg.obj;
                    for (int i=0; i<result.size(); i++) {
                        if (i > music.size()) {
                            Music music = new Music();
                        }
                    }
            }*/
            if (msg.what == 0x123) {
                music = (List<Music>) msg.obj;
                if (music == null || music.size() == 0) {
                    Toast.makeText(getActivity(), "获取列表出错", Toast.LENGTH_SHORT).show();
                } else {
                    //listView.setAdapter(new OnlineMusicListAdapter(music, getActivity()));
                    listView.setAdapter(new AsyncOnlineMusicAdapter(getActivity(),music, listView));
                }
            }
        }
    };
}