package com.juyikeji.caipiao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;


import com.juyikeji.caipiao.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/27 0027.
 */
public class Guide extends Activity implements ViewPager.OnPageChangeListener{
    private ViewPager viewpager;

    private ImageView iv_start;

    private List<View> views;
    private ViewPagerAdapter vpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);

        init();
    }
    private void init(){
        viewpager= (ViewPager) findViewById(R.id.viewpager);

        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.activity_guide_one, null));
        views.add(inflater.inflate(R.layout.activity_guide_two, null));
        views.add(inflater.inflate(R.layout.activity_guide_three, null));
        views.add(inflater.inflate(R.layout.activity_guide_four, null));

        vpAdapter = new ViewPagerAdapter(this,views);
        viewpager.setAdapter(vpAdapter);

        iv_start= (ImageView) views.get(3).findViewById(R.id.iv_start);
        iv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean login= (boolean) SharedPreferencesUtil.getSharedPreferences(Guide.this).get("login");
                if (login) {
                    Intent intent = new Intent(Guide.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(Guide.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
//        viewpager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
