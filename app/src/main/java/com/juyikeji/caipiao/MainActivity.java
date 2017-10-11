package com.juyikeji.caipiao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juyikeji.caipiao.fragment.FragmentOne;
import com.juyikeji.caipiao.fragment.FragmentThree;
import com.juyikeji.caipiao.fragment.FragmentTwo;
import com.juyikeji.caipiao.utils.SysApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity {
    private ViewPager viewpager;

    private RelativeLayout lottery_layout, Mylottery_layout, wode_layout;
    private ImageView lottery_img, Mylottery_img, wode_img;
    private TextView lottery_txt, Mylottery_txt, wode_txt;

    private FragmentOne fg1;
    private FragmentTwo fg2;
    private FragmentThree fg3;

    private MainFragmentAdapter myadapter;
    private FragmentManager fmanager;
    private List<Fragment> list;
    private MyPageChangeListener mPageChangeListener;
    private MyonClick mMyonclick;

    private int Gray = 0xFF999999;
    private int Red = 0xFFf4370f;

    private static boolean isExit = false;
    private static boolean hasTask = false;
    Timer tExit = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            isExit = false;
            hasTask = true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initvierpager();
        initstate();//初始化状态
        viewpager.setOffscreenPageLimit(0);
    }

    private void initstate() {
        lottery_img.setImageResource(R.mipmap.bottom_1_hover);
        lottery_txt.setTextColor(Red);
        viewpager.setCurrentItem(0);
    }

    private void initvierpager() {
        list = new ArrayList<Fragment>();
        fg1 = new FragmentOne();
        fg2 = new FragmentTwo();
        fg3 = new FragmentThree();
        list.add(fg1);
        list.add(fg2);
        list.add(fg3);

        myadapter = new MainFragmentAdapter(fmanager, list);
        viewpager.setAdapter(myadapter);

    }

    public class MyonClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            clearchioce();
            iconChange(v.getId());
        }
    }

    public class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        //此方法是在状态改变的时候调用，其中state这个参数有三种状态（0，1，2）
        // state ==1的时辰默示正在滑动，state==2的时辰默示滑动完毕了，state==0的时辰默示什么都没做。
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == 2) {
                int i = viewpager.getCurrentItem();
                clearchioce();
                iconChange(i);
            }

        }
    }

    /**
     * 实例化控件并设置监听
     */
    private void initview() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        fmanager = getSupportFragmentManager();

        mPageChangeListener = new MyPageChangeListener();
        viewpager.setOnPageChangeListener(mPageChangeListener);

        mMyonclick = new MyonClick();

        lottery_layout = (RelativeLayout) findViewById(R.id.lottery_layout);
        lottery_layout.setOnClickListener(mMyonclick);
        Mylottery_layout = (RelativeLayout) findViewById(R.id.Mylottery_layout);
        Mylottery_layout.setOnClickListener(mMyonclick);
        wode_layout = (RelativeLayout) findViewById(R.id.wode_layout);
        wode_layout.setOnClickListener(mMyonclick);

        lottery_txt = (TextView) findViewById(R.id.lottery_txt);
        Mylottery_txt = (TextView) findViewById(R.id.Mylottery_txt);
        wode_txt = (TextView) findViewById(R.id.wode_txt);

        lottery_img = (ImageView) findViewById(R.id.lottery_img);
        Mylottery_img = (ImageView) findViewById(R.id.Mylottery_img);
        wode_img = (ImageView) findViewById(R.id.wode_img);
    }

    /**
     * 定义一个清空选中状态的方法
     */
    private void clearchioce() {
        lottery_img.setImageResource(R.mipmap.bottom_1);
        lottery_txt.setTextColor(Gray);
        Mylottery_img.setImageResource(R.mipmap.bottom_2);
        Mylottery_txt.setTextColor(Gray);
        wode_img.setImageResource(R.mipmap.bottom_3);
        wode_txt.setTextColor(Gray);
    }

    /**
     * 定义一个底部导航栏选中变化的方法
     *
     * @param num
     */
    private void iconChange(int num) {
        switch (num) {
            case R.id.lottery_layout:
            case 0:
                lottery_img.setImageResource(R.mipmap.bottom_1_hover);
                lottery_txt.setTextColor(Red);
                viewpager.setCurrentItem(0);
                break;
            case R.id.Mylottery_layout:
            case 1:
                Mylottery_img.setImageResource(R.mipmap.bottom_2_hover);
                Mylottery_txt.setTextColor(Red);
                viewpager.setCurrentItem(1);
                break;
            case R.id.wode_layout:
            case 2:
                wode_img.setImageResource(R.mipmap.bottom_3_hover);
                wode_txt.setTextColor(Red);
                viewpager.setCurrentItem(2);
                break;

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // exitDialog();
            if (!isExit) {
                isExit = true;
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                if (!hasTask) {
                    tExit.schedule(task, 2000);
                }
            } else {
                finish();
                System.exit(0);
            }
        }
        return false;
    }
}
