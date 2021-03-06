package com.kuro.musicplayer.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kuro.musicplayer.R;
import com.kuro.musicplayer.model.Music;
import com.kuro.musicplayer.service.MusicPlayerService;
import com.kuro.musicplayer.utils.DisplayUtil;
import com.kuro.musicplayer.utils.FastBlurUtil;
import com.kuro.musicplayer.utils.ImageLoader;
import com.kuro.musicplayer.widget.BackgourndAnimationRelativeLayout;
import com.kuro.musicplayer.widget.DiscView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.kuro.musicplayer.widget.DiscView.DURATION_NEEDLE_ANIAMTOR;


public class PlayActivity extends BaseActivity implements DiscView.IPlayInfo, View.OnClickListener {

    private DiscView mDisc;
    private Toolbar mToolbar;
    private SeekBar mSeekBar;
    private ImageView playOrPurse, next, pre;
    private TextView currentTime, totalTime;
    private BackgourndAnimationRelativeLayout mRootLayout;
    private List<Music> music;
    public static final int MUSIC_MESSAGE = 0;
    private int currentIndex = 0;

    public static final String PARAM_MUSIC_LIST = "PARAM_MUSIC_LIST";

    private MusicReceiver mMusicReceiver = new MusicReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        bindListener();
        initMusicData();
        initDiskView();
        startService();
        initMusicReceiver();
    }

    private void initMusicReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicPlayerService.ACTION_STATUS_MUSIC_PLAY);
        intentFilter.addAction(MusicPlayerService.ACTION_STATUS_MUSIC_PAUSE);
        intentFilter.addAction(MusicPlayerService.ACTION_STATUS_MUSIC_DURATION);
        intentFilter.addAction(MusicPlayerService.ACTION_STATUS_MUSIC_COMPLETE);
        /*注册本地广播*/
        LocalBroadcastManager.getInstance(this).registerReceiver(mMusicReceiver,intentFilter);
    }

    private void bindListener() {
        mDisc.setPlayInfoListener(this);
        pre.setOnClickListener(this);
        next.setOnClickListener(this);
        playOrPurse.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentTime.setText(durationToTime(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopUpdateSeekBarProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekTo(seekBar.getProgress());
                startUpdateSeekBarProgress();
            }
        });
    }

    private void initUI() {
        setContentView(R.layout.activity_play);
        setStatusBarFullTransparent();
        bindComponent();
        setSupportActionBar(mToolbar);
        initTime();
    }

    private void initTime() {
        currentTime.setText(durationToTime(0));
        totalTime.setText(durationToTime(0));
    }

    private void bindComponent() {
        mDisc = (DiscView) findViewById(R.id.discview);
        next = (ImageView) findViewById(R.id.ivNext);
        pre = (ImageView) findViewById(R.id.ivLast);
        playOrPurse = (ImageView) findViewById(R.id.ivPlayOrPause);
        currentTime = (TextView) findViewById(R.id.tvCurrentTime);
        totalTime = (TextView) findViewById(R.id.tvTotalTime);
        mSeekBar = (SeekBar) findViewById(R.id.musicSeekBar);
        mRootLayout = (BackgourndAnimationRelativeLayout) findViewById(R.id.rootLayout);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
    }

    private void initDiskView() {
        mDisc.setMusicDataList(music);
    }

    private void stopUpdateSeekBarProgress() {
        mMusicHandler.removeMessages(MUSIC_MESSAGE);
    }

    private void initMusicData() {
        music = new ArrayList<Music>();
        Bundle bundle = getIntent().getExtras();
        List<Music> temp = (List<Music>) bundle.get("musicList") != null ? (List<Music>) bundle.get("musicList") : new ArrayList<Music>();
        currentIndex = getIntent().getIntExtra("index", 0);
        int size = temp.size();
        for (int i =currentIndex; i<size+currentIndex; i++) {
            if (i>=size) {
                music.add(temp.get(i-size));
            }else {
                music.add(temp.get(i));
            }
        }
    }

    private void startService() {
        Intent intent = new Intent(this, MusicPlayerService.class);
        intent.putExtra(PARAM_MUSIC_LIST, (Serializable) music);
        startService(intent);
    }

    private void updateMusicPicBackgroundInRes(final int musicPicRes) {
        if (mRootLayout.isNeedToUpdateBackground(musicPicRes)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = getForegroundBitmap(musicPicRes);
                    final Drawable foregroundDrawable = getForegroundDrawable(bitmap);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRootLayout.setForeground(foregroundDrawable);
                            mRootLayout.beginAnimation();
                        }
                    });
                }
            }).start();
        }
    }

    private void updateMusicPicBackgroundInUrl(final String url) {
        if (mRootLayout.isNeedToUpdateBackground(url)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = ImageLoader.getBitmapFromCache(url);
                    if (bitmap == null) {
                        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.placeholder_disk_210);
                    }
                    final Drawable foregroundDrawable = getForegroundDrawable(bitmap);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRootLayout.setForeground(foregroundDrawable);
                            mRootLayout.beginAnimation();
                        }
                    });
                }
            }).start();
        }
    }

    private float getScreenWidthHeightRatio() {
        //获得屏幕的宽高比
        return (float) (DisplayUtil.getScreenWidth(PlayActivity.this)
                * 1.0 / DisplayUtil.getScreenHeight(this) * 1.0);
    }

    private Drawable getForegroundDrawable(Bitmap bitmap) {

        int cropBitmapWidth = (int) (getScreenWidthHeightRatio() * bitmap.getHeight());
        int cropBitmapWidthX = (int) ((bitmap.getWidth() - cropBitmapWidth) / 2.0);
        /*切割部分图片*/
        Bitmap cropBitmap = Bitmap.createBitmap(bitmap, cropBitmapWidthX, 0, cropBitmapWidth,
                bitmap.getHeight());
        /*缩小图片*/
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(cropBitmap, bitmap.getWidth() / 50, bitmap
                .getHeight() / 50, false);
        /*模糊化*/
        Bitmap blurBitmap = FastBlurUtil.doBlur(scaleBitmap, 8, true);
        Drawable foregroundDrawable = new BitmapDrawable(blurBitmap);
        /*加入灰色遮罩层，避免图片过亮影响其他控件*/
        foregroundDrawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        return foregroundDrawable;
    }

    private Bitmap getForegroundBitmap(int musicPicRes) {
        int screenWidth = DisplayUtil.getScreenWidth(this);
        int screenHeight = DisplayUtil.getScreenHeight(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(getResources(), musicPicRes, options);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        if (imageWidth < screenWidth && imageHeight < screenHeight) {
            return BitmapFactory.decodeResource(getResources(), musicPicRes);
        }

        int sample = 2;
        int sampleX = imageWidth / DisplayUtil.getScreenWidth(this);
        int sampleY = imageHeight / DisplayUtil.getScreenHeight(this);

        if (sampleX > sampleY && sampleY > 1) {
            sample = sampleX;
        } else if (sampleY > sampleX && sampleX > 1) {
            sample = sampleY;
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = sample;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return BitmapFactory.decodeResource(getResources(), musicPicRes, options);
    }

    @Override
    public void onMusicInfoChanged(String musicName, String musicAuthor) {
        getSupportActionBar().setTitle(musicName);
        getSupportActionBar().setSubtitle(musicAuthor);
    }

    @Override
    public void onMusicPicChanged(int musicPicRes) {
        updateMusicPicBackgroundInRes(musicPicRes);
    }

    @Override
    public void onMusicPicChanged(String path) {
        updateMusicPicBackgroundInUrl(path);
    }

    @Override
    public void onMusicChanged(DiscView.MusicChangedStatus musicChangedStatus) {
        switch (musicChangedStatus) {
            case PLAY:{
                play();
                break;
            }
            case PAUSE:{
                pause();
                break;
            }
            case NEXT:{
                next();
                break;
            }
            case LAST:{
                last();
                break;
            }
            case STOP:{
                stop();
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == playOrPurse) {
            mDisc.playOrPause();
        } else if (v == next) {
            mDisc.next();
        } else if (v == pre) {
            mDisc.last();
        }
    }

    private void play() {
        optMusic(MusicPlayerService.ACTION_OPT_MUSIC_PLAY);
        startUpdateSeekBarProgress();
    }

    private void pause() {
        optMusic(MusicPlayerService.ACTION_OPT_MUSIC_PAUSE);
        stopUpdateSeekBarProgress();
    }

    private void stop() {
        stopUpdateSeekBarProgress();
        playOrPurse.setImageResource(R.drawable.ic_play);
        initTime();
        mSeekBar.setProgress(0);
    }

    private void next() {
        mRootLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                optMusic(MusicPlayerService.ACTION_OPT_MUSIC_NEXT);
            }
        }, DURATION_NEEDLE_ANIAMTOR);
        stopUpdateSeekBarProgress();
        initTime();
    }

    private void last() {
        mRootLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                optMusic(MusicPlayerService.ACTION_OPT_MUSIC_LAST);
            }
        }, DURATION_NEEDLE_ANIAMTOR);
        stopUpdateSeekBarProgress();
        initTime();
    }

    private void complete(boolean isOver) {
        if (isOver) {
            mDisc.stop();
        } else {
            mDisc.next();
        }
    }

    private void optMusic(final String action) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(action));
    }

    private void seekTo(int position) {
        Intent intent = new Intent(MusicPlayerService.ACTION_OPT_MUSIC_SEEK_TO);
        intent.putExtra(MusicPlayerService.PARAM_MUSIC_SEEK_TO,position);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void startUpdateSeekBarProgress() {
        /*避免重复发送Message*/
        stopUpdateSeekBarProgress();
        mMusicHandler.sendEmptyMessageDelayed(0,1000);
    }

    /*根据时长格式化称时间文本*/
    private String durationToTime(int duration) {
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        return (min < 10 ? "0" + min : min + "") + ":" + (sec < 10 ? "0" + sec : sec + "");
    }

    private void updateMusicDurationInfo(int totalDuration) {
        mSeekBar.setProgress(0);
        mSeekBar.setMax(totalDuration);
        totalTime.setText(durationToTime(totalDuration));
        currentTime.setText(durationToTime(0));
        startUpdateSeekBarProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMusicReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDisc.recyleBitmap();
    }

    class MusicReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MusicPlayerService.ACTION_STATUS_MUSIC_PLAY)) {
                playOrPurse.setImageResource(R.drawable.ic_pause);
                int currentPosition = intent.getIntExtra(MusicPlayerService.PARAM_MUSIC_CURRENT_POSITION, 0);
                mSeekBar.setProgress(currentPosition);
                if(!mDisc.isPlaying()){
                    mDisc.playOrPause();
                }
            } else if (action.equals(MusicPlayerService.ACTION_STATUS_MUSIC_PAUSE)) {
                playOrPurse.setImageResource(R.drawable.ic_play);
                if (mDisc.isPlaying()) {
                    mDisc.playOrPause();
                }
            } else if (action.equals(MusicPlayerService.ACTION_STATUS_MUSIC_DURATION)) {
                int duration = intent.getIntExtra(MusicPlayerService.PARAM_MUSIC_DURATION, 0);
                updateMusicDurationInfo(duration);
            } else if (action.equals(MusicPlayerService.ACTION_STATUS_MUSIC_COMPLETE)) {
                boolean isOver = intent.getBooleanExtra(MusicPlayerService.PARAM_MUSIC_IS_OVER, true);
                complete(isOver);
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mMusicHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mSeekBar.setProgress(mSeekBar.getProgress() + 1000);
            currentTime.setText(durationToTime(mSeekBar.getProgress()));
            startUpdateSeekBarProgress();
        }
    };

}
