package com.kuro.musicplayer.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.kuro.musicplayer.R;
import com.kuro.musicplayer.Sqlite.DbHelper;
import com.kuro.musicplayer.activity.PlayActivity;
import com.kuro.musicplayer.model.Music;
import com.kuro.musicplayer.model.OnlineMusicBean;
import com.kuro.musicplayer.model.OnlineMusicDownloadBean;
import com.kuro.musicplayer.model.OnlineMusicPlaylistDetail;
import com.kuro.musicplayer.model.OnlineMusicsDetail;
import com.kuro.musicplayer.model.OnlineSongSheetBean;
import com.kuro.musicplayer.utils.ImageLoader;
import com.kuro.musicplayer.utils.WebRequestUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OnlineMusicFragment extends Fragment {
    private static final String TAG = "-----MusicFragment";
    private View view;
    private ListView listView;
    private List<Music> music;
    private LinearLayout addMusic;
    private DbHelper helper;


    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.local_fragment, container, false);
        listView = view.findViewById(R.id.local_fragment_listview);
        addMusic = view.findViewById(R.id.new_musiclist);
        addMusic.setVisibility(View.GONE);
        //initUI();
        bindOnClickListener();
        helper = DbHelper.setDatabase(getActivity());
        return view;
    }

    private void bindOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("------Online", String.valueOf(music.get(position).toString()));
                Intent intent = new Intent(getActivity(), PlayActivity.class);
                intent.putExtra("index", position);
                Bundle bundle = new Bundle();
                /*if (ImageLoader.mCaches != null) {
                    bundle.putSerializable("bitmaps", (Serializable) ImageLoader.mCaches);
                }*/
                //bundle.putSerializable("bitmaps", (Serializable) ImageLoader.mCaches);
                bundle.putSerializable("musicList", (Serializable) music);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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
                /*OnlineSongSheetBean songSheet = WebRequestUtil.getSongSheetThird();
                List<OnlineMusicBean> result = WebRequestUtil.getOnlineMusicsBySongSheetThird(songSheet);
                List<OnlineMusicDownloadBean> urls = WebRequestUtil.getOnlineSongThird(songSheet);
                List<Music> musics = new ArrayList<Music>();
                for (int i=0; i<result.size(); i++) {
                    Music m = new Music();
                    m.setMusicName(result.get(i).getSongs().get(0).getName());
                    m.setMusician(result.get(i).getSongs().get(0).getAr().get(0).getName());
                    m.setPath(urls.get(i).getData().get(0).getUrl());
                    m.setNetImagePath(result.get(i).getSongs().get(0).getAl().getPicUrl());
                    musics.add(m);
                }*/
                List<Music> musics = new ArrayList<Music>();
                List<Music> db = helper.getAllMusicByType(1);
                OnlineMusicPlaylistDetail detail = WebRequestUtil.getPlaylistDetailOfficial();
                //OnlineMusicsDetail musicsDetail = WebRequestUtil.getMusicsDetail()
                if (detail.getCode() != 200) {
                    Log.i("----Net", "网络请求失败");
                    return;
                }
                OnlineMusicPlaylistDetail.ResultBean resultBean = detail.getResult();
                List<OnlineMusicPlaylistDetail.ResultBean.TracksBean> tracks = resultBean.getTracks();
                int[] ids = new int[tracks.size()];
                for (int i = 0; i < ids.length; i++) {
                    ids[i] = tracks.get(i).getId();
                }
                //List<OnlineMusicDownloadBean> urls = WebRequestUtil.getOnlineSongByIds(ids);
                /*OnlineMusicsDetail musicsDetail = WebRequestUtil.getMusicsDetail(ids);*/
                for (int i = 0; i < tracks.size(); i++) {
                    Music music = new Music();
                    music.setnId(tracks.get(i).getId());
                    music.setMusicName(tracks.get(i).getName());
                    music.setMusician(tracks.get(i).getArtists().get(0).getName());
                    //http://music.163.com/song/media/outer/url?id=476592630.mp3
                    music.setPath("http://music.163.com/song/media/outer/url?id="+music.getnId());
                    music.setNetImagePath(tracks.get(i).getAlbum().getPicUrl());
                    music.setType(1);
                    if (!db.contains(music)) {
                        musics.add(music);
                    }
                }

                //网络的结果也放到数据库里把
                helper.addMusicList(musics);
                //添加新的到数据库
                db.addAll(musics);

                Message m = new Message();
                m.what = 0x123;
                m.obj = db;
                handler.sendMessage(m);
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                music = (List<Music>) msg.obj;
                if (music == null || music.size() == 0) {
                    Toast.makeText(getActivity(), "获取列表出错", Toast.LENGTH_SHORT).show();
                } else {
                    AsyncOnlineMusicAdapter adapter = new AsyncOnlineMusicAdapter(getActivity(), music, listView);
                    listView.setAdapter(adapter);
                }
            }
        }
    };
}
