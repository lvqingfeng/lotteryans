package com.juyikeji.caipiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.adapter.FragmentOneGvBlueAdapterBB;
import com.juyikeji.caipiao.adapter.FragmentOneGvRedAdapterAA;
import com.juyikeji.caipiao.adapter.FragmentOneGvRedAdapterSH;
import com.juyikeji.caipiao.adapter.FragmentOneGvRedAdapterTT;
import com.juyikeji.caipiao.adapter.FragmentOneKJXXAdapter;
import com.juyikeji.caipiao.bean.Url;
import com.juyikeji.caipiao.utils.MyCombine;
import com.juyikeji.caipiao.utils.MyGridView;
import com.juyikeji.caipiao.utils.SysApplication;
import com.juyikeji.caipiao.utils.URLConnectionUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OneTwentyTwoforFive extends Activity implements View.OnClickListener {

    private MyGridView gv_red;
    private FragmentOneGvRedAdapterSH adapterA;
    private List<Integer> list;

    private LinearLayout linout1;
    private View view;
    private Button btn_close, btn_clear;
    private TextView tv_title, tv_pt, tv_dd, tv_time, tv_next, tv_choose, tv_money, tv_xl, tv_ts;
    private ImageView iv, iv_xl;
    private RelativeLayout rel_down;

    //获取往期中奖信息的网络接口
    private String name_space = Url.LOTTERYMESSAGE;
    String result = "";
    //最近得奖信息下拉列表
    private ListView lv;
    private FragmentOneKJXXAdapter adapter;
    private List<String[]> listis;

    private boolean zst = false;//选择走势图下拉的标识
    private boolean choose = false;//选择投注类型标识默认为普通投注
    private boolean titledown = false;//选择投注类型下拉按钮标识
    private SharedPreferences sharedPreferences;
    private MyCombine myCombine;
    private int kjrq;//开奖日期
    private String pausetime;//开奖日期


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_one_lotterybet);
        SysApplication.getInstance().addActivity(this);
        showLotter();
    }


    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        adapterA = new FragmentOneGvRedAdapterSH(this, getDateR(), getSharedPreferences());
        if (adapterA.limitd>0){
            choose=true;
        }else {
            choose=false;

        }
        myCombine = new MyCombine();
        initView();
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        int item = sharedPreferences.getInt("item", 0);
        if (item >= 0) {
            int d = adapterA.limitd;//胆码个数
            List<Integer> a = adapterA.getNumb();//获取选中的红球
            String[] s = new String[a.size()];
            for (int i = 0; i < a.size(); i++) {
                s[i] = a.get(i) + "";//将获取的红球存到数组里
            }
            int zs = myCombine.combine(s, 5 - d).size();
            if (choose) {
                tv_money.setText(adapterA.limit + "红球" + d + "胆码共" + zs + "注 " + 2 * zs + "元");
            } else {
                tv_money.setText(adapterA.limit + "红球共" + zs + "注 " + 2 * zs + "元");
            }
        }

    }

    private void initView() {
        linout1=(LinearLayout)findViewById(R.id.linout1);
        view=(View)findViewById(R.id.view);
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);

        lv=(ListView)findViewById(R.id.lv);

        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setOnClickListener(this);
        tv_pt = (TextView) findViewById(R.id.tv_pt);
        tv_pt.setOnClickListener(this);
        tv_dd = (TextView) findViewById(R.id.tv_dd);
        tv_dd.setOnClickListener(this);
        if (choose){
            tv_dd.setBackgroundResource(R.mipmap.choose_yes);
            tv_pt.setBackgroundResource(R.mipmap.choose_no);
        }else {
            tv_pt.setBackgroundResource(R.mipmap.choose_yes);
            tv_dd.setBackgroundResource(R.mipmap.choose_no);
        }
        tv_choose = (TextView) findViewById(R.id.tv_choose);
        tv_choose.setOnClickListener(this);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_money.setText("最少选5个彩球");
        tv_xl = (TextView) findViewById(R.id.tv_xl);
        tv_ts = (TextView) findViewById(R.id.tv_ts);
        tv_ts.setVisibility(View.GONE);

        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(this);
        iv_xl = (ImageView) findViewById(R.id.iv_xl);
        iv_xl.setOnClickListener(this);

        rel_down = (RelativeLayout) findViewById(R.id.rel_down);

        gv_red = (MyGridView) findViewById(R.id.gv_red);
        gv_red.setAdapter(adapterA);
        gv_red.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterA.changeSelected(position, choose);//刷新
                showMoney();
            }
        });
    }

    /**
     * 彩球的布局加载容器
     *
     * @return
     */
    private List<Integer> getDateR() {
        list = new ArrayList<Integer>();
        for (int i = 1; i <= 22; i++) {
            list.add(i);
        }
        return list;
    }

    /**
     * 页面点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                finish();
                break;
            case R.id.iv_xl:
                //走势图下拉列表
                if (zst) {
                    zst = false;
                    iv_xl.setBackgroundResource(R.mipmap.icon_xl);
                    lv.setVisibility(View.GONE);
                    linout1.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                } else {
                    iv_xl.setBackgroundResource(R.mipmap.icon_sl);
                    lv.setVisibility(View.VISIBLE);
                    linout1.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);
                    zst = true;
                }

                break;
            case R.id.btn_clear:
                //删除
                adapterA.getMselect();
                break;
            case R.id.tv_next:
                //下一步
                if (adapterA.limit + adapterA.limitd < 5) {
                    Toast.makeText(this, "最少选5个彩球", Toast.LENGTH_SHORT).show();
                    return;
                }
                setSharedPreferences(adapterA.getNumb(),adapterA.getNumbd());
                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                intent.putExtra("彩种", 6);
                intent.putExtra("kjrq", kjrq);
                intent.putExtra("pausetime", pausetime);
                startActivity(intent);
                break;
            case R.id.tv_pt:
                //普通投注
                choose = false;
                tv_ts.setVisibility(View.GONE);
                adapterA.getMselect();
                tv_pt.setBackgroundResource(R.mipmap.choose_yes);
                tv_dd.setBackgroundResource(R.mipmap.choose_no);
                break;
            case R.id.tv_dd:
                //胆投
                choose = true;
                tv_ts.setVisibility(View.VISIBLE);
                adapterA.getMselect();
                tv_pt.setBackgroundResource(R.mipmap.choose_no);
                tv_dd.setBackgroundResource(R.mipmap.choose_yes);
                break;
            case R.id.tv_title:
                //普通胆投下拉按钮
                Choose();
                break;
            case R.id.iv:
                //普通胆投下注按钮
                Choose();
                break;
            case R.id.tv_choose:
                //机选
                adapterA.changeNumber();
                showMoney();
                break;

        }
    }

    //普通胆投下拉按钮
    private void Choose() {

        if (!titledown) {
            iv.setBackgroundResource(R.mipmap.top_1);
            rel_down.setVisibility(View.VISIBLE);
            tv_pt.setVisibility(View.VISIBLE);
            tv_dd.setVisibility(View.VISIBLE);
            tv_time.setVisibility(View.GONE);
            titledown = true;
        } else {
            iv.setBackgroundResource(R.mipmap.top_2);
            rel_down.setVisibility(View.GONE);
            tv_pt.setVisibility(View.GONE);
            tv_dd.setVisibility(View.GONE);
            tv_time.setVisibility(View.VISIBLE);
            titledown = false;
        }
    }

    /**
     * 计算彩票注数更新ui
     */
    private void showMoney() {
        if (adapterA.limit >= 5) {
            switch (adapterA.limit) {
                case 5:
                    tv_money.setText(adapterA.limit + "彩球" + " 共" + 1 + "注 " + 2 + "元");
                    break;
                case 6:
                    tv_money.setText(adapterA.limit + "彩球" + " 共" + 6 + "注 " + 12 + "元");
                    break;
                case 7:
                    tv_money.setText(adapterA.limit + "彩球" + " 共" + 21 + "注 " + 42 + "元");
                    break;
                case 8:
                    tv_money.setText(adapterA.limit + "彩球" + " 共" + 56 + "注 " + 112 + "元");
                    break;
                case 9:
                    tv_money.setText(adapterA.limit + "彩球" + " 共" + 126 + "注 " + 232 + "元");
                    break;
                case 10:
                    tv_money.setText(adapterA.limit + "彩球" + " 共" + 256 + "注 " + 512 + "元");
                    break;
                case 11:
                    tv_money.setText(adapterA.limit + "彩球" + " 共" + 462 + "注 " + 924 + "元");
                    break;
                case 12:
                    tv_money.setText(adapterA.limit + "彩球" + " 共" + 792 + "注 " + 1584 + "元");
                    break;
                case 13:
                    tv_money.setText(adapterA.limit + "彩球" + " 共" + 1287 + "注 " + 2574 + "元");
                    break;
                case 14:
                    tv_money.setText(adapterA.limit + "彩球" + " 共" + 2002 + "注 " + 4004 + "元");
                    break;
                case 15:
                    tv_money.setText(adapterA.limit + "彩球" + " 共" + 3003 + "注 " + 6006 + "元");
                    break;
                case 16:
                    tv_money.setText(adapterA.limit + "彩球" + " 共" + 4368 + "注 " + 8736 + "元");
                    break;
                case 17:
                    tv_money.setText(adapterA.limit + "彩球" + " 共" + 6188 + "注 " + 12376 + "元");
                    break;
            }
        } else {
            tv_money.setText(adapterA.limit + "彩球" + "共0注0元");
        }
    }


    /**
     * 请求网络注册
     */
    private void showLotter() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    getRegister(result);
                }
            }
        };
        // 启动线程来执行任务
        new Thread() {
            public void run() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("lotterytype", "zyfc22x5");
                // 请求网络
                try {
                    result = URLConnectionUtil.sendPostRequest(name_space,
                            map, "UTF-8", 1);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Message m = new Message();
                m.what = 1;
                // 发送消息到Handler
                handler.sendMessage(m);
            }
        }.start();
    }

    private void getRegister(String json) {
        try {
            JSONObject jobj = new JSONObject(json);
            String status = jobj.getString("status");
            String msg = jobj.getString("msg");
            listis=new ArrayList<String[]>();
            if (status.equals("1")) {
                //开奖日期
                String date=jobj.getString("date");
                tv_time.setText(date);
                pausetime=jobj.getString("pausetime");
                JSONArray data=jobj.getJSONArray("data");
                kjrq=Integer.parseInt(data.getJSONObject(0).getString("expect"))+1;
                for (int i=0;i<data.length();i++){
                    JSONObject datai=data.getJSONObject(i);
                    //彩票开票的日期
                    String expect=datai.getString("expect");
                    //开奖号码
                    String opencode=datai.getString("opencode");
                    String [] s={expect,opencode};
                    listis.add(s);
                }
                adapter=new FragmentOneKJXXAdapter(this,listis,6);
                lv.setAdapter(adapter);
            }else {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> getSharedPreferences() {
        //红球的集合
        List<Integer> redlist = new ArrayList<Integer>();
        //红胆的集合
        List<String> reddlist = new ArrayList<String>();
        String redd = "";
        int reddsize = 0;
        int item = sharedPreferences.getInt("item", 0);
        reddsize = sharedPreferences.getInt("reddsize" + item, 0);//胆码的个数
        redd = sharedPreferences.getString("redd" + item, "");//红球胆码
        for (int i = 0; i < reddsize; i++) {
            reddlist.add(redd.substring(0, redd.indexOf(" ")));//截取每个胆码
            redd = redd.substring(redd.indexOf(" ") + 1);//将剩余胆码重置从新截取
        }
        int redsize = sharedPreferences.getInt("redsize" + item, 0);//红球的个数
        for (int j = 0; j < redsize; j++) {
            redlist.add(sharedPreferences.getInt("red" + item + j, 0));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("red", redlist);
        map.put("redd", reddlist);
        return map;
    }

    //    将选择的号码存到shared里
    public void setSharedPreferences(List<Integer> red, String redd) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int time = sharedPreferences.getInt("time", 0);
        int item = sharedPreferences.getInt("item", 0);
        if (item >= 0) {
            if (!choose) {
                editor.putInt("redsize" + item, red.size());
                for (int i = 0; i < red.size(); i++) {
                    editor.putInt("red" + item + i, red.get(i));
                }
            } else {
                //红球
                editor.putInt("redsize" + item, red.size());
                for (int i = 0; i < red.size(); i++) {
                    editor.putInt("red" + item + i, red.get(i));
                }
                //获取胆码
                editor.putInt("reddsize" + item, adapterA.limitd);
                editor.putString("redd" + item, redd);
            }
        }else {
            if (!choose) {
                editor.putInt("redsize" + time, red.size());
                for (int i = 0; i < red.size(); i++) {
                    editor.putInt("red" + time + i, red.get(i));
                }
            } else {
                //红球
                editor.putInt("redsize" + time, red.size());
                for (int i = 0; i < red.size(); i++) {
                    editor.putInt("red" + time + i, red.get(i));
                }
                //获取胆码
                editor.putInt("reddsize" + time, adapterA.limitd);
                editor.putString("redd" + time, redd);
            }
            editor.putInt("time", time + 1);
        }
        editor.commit();
    }
}
