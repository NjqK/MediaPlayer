package com.kuro.musicplayer.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.kuro.musicplayer.R;
import com.kuro.musicplayer.Sqlite.DbHelper;
import com.kuro.musicplayer.Sqlite.SqliteHelper;
import com.kuro.musicplayer.utils.PermissionUtil;

public class LoadingActivity extends BaseActivity {

    private DbHelper helper;
    private static final long SPLASH_DELAY_MILLIS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtil.verifyStoragePermissions(this);
        initUI();
        createDatabase();
        // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
        new Handler().postDelayed(new Runnable() {
            public void run() {
                goHome();
            }
        }, SPLASH_DELAY_MILLIS);
    }

    private void initUI() {
        hideStatusNavigationBar();
        setContentView(R.layout.activity_loading);
    }

    @SuppressLint("NewApi")
    private void createDatabase() {
        helper = DbHelper.setDatabase(this);
        helper.deleteAllMusics();
    }

    private void goHome() {
        Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void hideStatusNavigationBar(){
        if(Build.VERSION.SDK_INT<16){
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN //hide statusBar
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //hide navigationBar
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }
}
