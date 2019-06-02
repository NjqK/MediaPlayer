package com.kuro.musicplayer.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kuro.musicplayer.Adapter.MusicListAdapter;
import com.kuro.musicplayer.R;
import com.kuro.musicplayer.Sqlite.DbHelper;
import com.kuro.musicplayer.activity.FoundMusicActivity;
import com.kuro.musicplayer.activity.PlayActivity;
import com.kuro.musicplayer.model.Music;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MyMusicFragment extends Fragment {

    private static final String TAG = "-----MusicFragment";
    private View view;
    private ListView listView;
    private LinearLayout addMusic;
    private List<Music> musics;
    private MyMusicCallbacks callbacks;
    private final File path = Environment.getExternalStorageDirectory();

    public interface MyMusicCallbacks {
        void onItemSelected(Music music);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.local_fragment, container, false);
        listView = view.findViewById(R.id.local_fragment_listview);
        addMusic = view.findViewById(R.id.new_musiclist);
        initUI();
        bindClickListener();
        return view;
    }

    private void bindClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("------Clicked Item' id", musics.get(position).toString());
                Intent intent = new Intent(getActivity(), PlayActivity.class);
                intent.putExtra("index", position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("musicList", (Serializable) musics);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        addMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FoundMusicActivity.class);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
        Log.i(TAG, "requestCode " + requestCode + " resultCode " + resultCode+" result:" + result);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NewApi")
    private void initUI() {
        musics = DbHelper.setDatabase(getActivity()).getAllMusic();

        if (musics.size() == 0) {
            Toast.makeText(getActivity(), "尚未有本地音乐, 请扫描添加!", Toast.LENGTH_SHORT).show();
        }else {
            addMusic.setVisibility(View.GONE);
            listView.setAdapter(new MusicListAdapter(musics, getActivity()));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof MyMusicCallbacks)) {
            throw new IllegalStateException("aaaa");
        }
        callbacks = (MyMusicCallbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }
}

