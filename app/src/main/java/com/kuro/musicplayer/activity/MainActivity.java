package com.kuro.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kuro.musicplayer.Fragment.MyMusicFragment;
import com.kuro.musicplayer.Fragment.OnlineMusicFragment;
import com.kuro.musicplayer.R;
import com.kuro.musicplayer.utils.StatueBarHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ImageView more;
    private Intent intent;
    private final List<Fragment> fragments = new ArrayList<Fragment>();
    private final int[] textViews = new int[] {R.id.tv_myMusic, R.id.tv_online_music};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        initUI();
        bindListener();
        initViewPager();
    }

    private void bindComponent() {
        more = findViewById(R.id.topbar_menu);
    }

    private void bindListener() {
        intent = new Intent(this, FoundMusicActivity.class);
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
        bindComponent();
    }

    private void initViewPager() {
        fragments.add(new MyMusicFragment());
        fragments.add(new OnlineMusicFragment());

        ViewPager vp = findViewById(R.id.vpFragment);
        vp.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                return super.instantiateItem(container, position);
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                super.setPrimaryItem(container, position, object);
                Log.i("----Adapter", "position" + position);
                for (int i=0; i<textViews.length; i++) {
                    TextView tv = findViewById(textViews[i]);
                    if (i == position) {
                        tv.setTextColor(getResources().getColor(R.color.Active));
                    } else {
                        tv.setTextColor(getResources().getColor(R.color.notActive));
                    }
                }
            }
        });
    }
}
