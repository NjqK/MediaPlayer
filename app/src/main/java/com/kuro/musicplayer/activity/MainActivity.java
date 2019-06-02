package com.kuro.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kuro.musicplayer.Fragment.MyMusicFragment;
import com.kuro.musicplayer.Fragment.OnlineMusicFragment;
import com.kuro.musicplayer.R;
import com.kuro.musicplayer.model.Music;
import com.kuro.musicplayer.utils.StatueBarHelper;

public class MainActivity extends BaseActivity implements MyMusicFragment.MyMusicCallbacks, OnlineMusicFragment.OnlineCallBack {

    private final FragmentManager fm = getSupportFragmentManager();
    private ImageView more;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        initUI();
        intent = new Intent(this, FoundMusicActivity.class);
        more = findViewById(R.id.topbar_menu);
        bindListener();
    }

    private void bindListener() {
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        ((LinearLayout)findViewById(R.id.lay1)).setPadding(0, StatueBarHelper.getStatusBarHeight(this), 0, 0);
        setStatusBarFullTransparent();
        fm.beginTransaction().add(R.id.main_activity_fragment, new OnlineMusicFragment()).commit();
        //fm.beginTransaction().add(R.id.main_activity_fragment, new MyMusicFragment()).commit();
    }

    @Override
    public void onItemSelected(Music music) {
        Log.i("-----MainActivity",music.getId()+" "+music.getMusicName()+" "+music.getMusician()+" "+music.getPath()+" "+music.getImagePath());
    }

    @Override
    public void downloadMusic() {

    }
}
