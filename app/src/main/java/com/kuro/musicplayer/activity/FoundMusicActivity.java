package com.kuro.musicplayer.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kuro.musicplayer.Adapter.FoundMusicListAdapter;
import com.kuro.musicplayer.R;
import com.kuro.musicplayer.Sqlite.DbHelper;
import com.kuro.musicplayer.model.Music;
import com.kuro.musicplayer.utils.FindLocalMusicsHelper;
import com.kuro.musicplayer.utils.StatueBarHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FoundMusicActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView thePath, seaching, head, allChoose;
    private LinearLayout box;
    private ImageView out;
    private Button begin,sure;
    private ListView musicList;
    //sd卡里找的mp3
    private List<Music> foundResult;
    //选中的
    private List<Music> selectedResult;
    private FoundMusicListAdapter musicListAdapter;
    private DbHelper helper;
    private boolean all = false;

    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    private void init() {
        foundResult = new ArrayList<Music>();
        helper = DbHelper.setDatabase(this);
        initUI();
        bindComponent();
        bindListener();
    }

    private void bindListener() {
        musicList.setOnItemClickListener(this);
        out.setOnClickListener(this);
        begin.setOnClickListener(this);
        sure.setOnClickListener(this);
        allChoose.setOnClickListener(this);
    }

    private void bindListAdapter(List<Music> list) {
        musicListAdapter = new FoundMusicListAdapter(list, this);
        musicList.setAdapter(musicListAdapter);
    }

    private void bindComponent() {
        seaching= (TextView) findViewById(R.id.searching);
        head= (TextView) findViewById(R.id.head_local);
        thePath = (TextView) findViewById(R.id.what_path);
        out= (ImageView) findViewById(R.id.out_local);
        begin= (Button) findViewById(R.id.begin_search);
        sure= (Button) findViewById(R.id.sure_list);
        box= (LinearLayout) findViewById(R.id.show_local);
        musicList = (ListView) findViewById(R.id.show_music);
        allChoose = (TextView) findViewById(R.id.allChoose);
    }

    private void initUI() {
        setContentView(R.layout.found_local_music);
        ((LinearLayout)findViewById(R.id.lay1)).setPadding(0, StatueBarHelper.getStatusBarHeight(this), 0, 0);
        setStatusBarFullTransparent();
    }


    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.out_local:
                //添加新的
                if (selectedResult != null) {
                    helper.addMusicList(selectedResult);
                }
                finish();
                break;
            case R.id.begin_search:
                begin.setVisibility(View.GONE);
                seaching.setVisibility(View.VISIBLE);
                thePath.setVisibility(View.VISIBLE);
                new searchMusicThread().start();
                break;//开始搜索
            case R.id.allChoose:
                if (foundResult.size() == 0) {
                    Toast.makeText(this, "请先扫描！", Toast.LENGTH_SHORT).show();
                }else {
                    if(all){
                        //取消全选
                        allChoose.setText("全选");
                        all = false;
                        musicListAdapter.cancelAll();
                        musicListAdapter.notifyDataSetChanged();
                    }else{
                        //全选
                        allChoose.setText("取消全选");
                        all = true;
                        musicListAdapter.chooseAll();
                        musicListAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.sure_list:
                List<Music> selectedResult = musicListAdapter.getSelected();
                this.selectedResult = new ArrayList<Music>();
                //数据库里的
                List<Music> dbResult = helper.getAllMusicByType(0);
                for (Music m : selectedResult
                        ) {
                    if (!dbResult.contains(m)) {
                        this.selectedResult.add(m);
                    }
                }
                head.setText(String.format(getResources().getString(R.string.head), this.selectedResult.size()));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FoundMusicListAdapter.ViewHolder vHollder = (FoundMusicListAdapter.ViewHolder) view.getTag();
        vHollder.cb.toggle();
        musicListAdapter.setSelected(position,vHollder.cb.isChecked());
    }

    private class searchMusicThread extends Thread{
        @Override
        public void run() {
            boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
            if(sdCardExist){
                Message m=new Message();
                File path = Environment.getExternalStorageDirectory();
                m.what = 0x122;
                m.obj = path.getAbsolutePath();
                myHandler.sendMessage(m);
                List<Music> result = FindLocalMusicsHelper.getSDcardFile(path);
                Log.i("-----FoundMusicActivity", "path: "+path+" selectedResult size: "+result.size());
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                m = new Message();
                m.what=0x123;
                m.obj=result;
                myHandler.sendMessage(m);
            }else{
                Toast.makeText(getApplicationContext(),"请插入sd卡",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("HandlerLeak")
    Handler myHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x123:
                    seaching.setVisibility(View.GONE);
                    thePath.setVisibility(View.GONE);
                    foundResult = (List<Music>) msg.obj;
                    bindListAdapter(foundResult);
                    break;
                case 0x122:
                    thePath.setText((String)msg.obj);
                    break;
            }
        }
    };
}
