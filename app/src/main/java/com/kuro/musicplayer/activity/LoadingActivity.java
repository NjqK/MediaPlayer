package com.kuro.musicplayer.activity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import com.kuro.musicplayer.R;
import com.kuro.musicplayer.Sqlite.DbHelper;
import com.kuro.musicplayer.utils.PermissionUtil;

public class LoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyForPermission();
        initUI();
        createDatabase();
        dispatcherToMainActivity();
    }

    private void dispatcherToMainActivity() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                goHome();
            }
        }, 3000);
    }

    private void applyForPermission() {
        PermissionUtil.verifyStoragePermissions(this);
    }

    private void initUI() {
        setStatusBarFullTransparent();
        setContentView(R.layout.activity_loading);
    }

    @SuppressLint("NewApi")
    private void createDatabase() {
        DbHelper helper = DbHelper.setDatabase(this);
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
