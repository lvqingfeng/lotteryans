package com.juyikeji.caipiao.threed;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.activity.OneLotteryBetPay;
import com.juyikeji.caipiao.activity.OneLotteryBetPayx;
import com.juyikeji.caipiao.adapter.FragmentOneKJXXAdapter;
import com.juyikeji.caipiao.utils.URLConnectionUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//3D
public class OneThreeD extends Activity implements View.OnClickListener {
    private RelativeLayout layout;
    private LinearLayout rl_text;
    private TextView tv_bt, tv_num, tv_choose, tv_text, tv_hun,bt_next;
    private ImageView iv_top, fanhui, iv_xl;
    private Button bt_clear;
    private GridView gridview1, gridview2, gridview3;
    private GridView gridview;
    //数据源
    List<Integer> list;
    //3D Adapter对象，分别为百位，十位，个位
    private ThreeDAdapter1 adapter1;
    private ThreeDAdapter2 adapter2;
    private ThreeDAdapter3 adapter3;

    private boolean b = true;
    //弹出框对象
    private PopupWindow popupWindow;
    //弹出框中gridview的adapter
    private OneGridViewAdapter adapter;
    //记录选择的玩法
    private int po = 0;
    //用来保存每位选择的号码的个数，依次是百位，十位，个位
    private int a1 = 0, a2 = 0, a3 = 0;
    //用来保存选择的号码，依次是百位，十位，个位
    int i1[], i2[], i3[];

    private View view_view;
    private LinearLayout hun_linearlayout, ten_linearlayout, ind_linearlayout;
    //存放随机生成的数
    private int[] sjs = new int[3];
    //保存选中的号码
    private List<List<Integer>> listlist;
    private List<Integer> li1, li2, li3;


    private LinearLayout linout1;
    private TextView tv_time;
    private ListView lv;
    //获取往期中奖信息的网络接口
    private String name_space = "center/getwinnum";
    String result = "";
    private FragmentOneKJXXAdapter fradapter;
    private List<String[]> listis;
    private boolean zst = false;//选择走势图下拉的标识

    //开奖日期
    private String kjrq = "0";
    //停售日期
    private String pausetime="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_three_d);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!URLConnectionUtil.isOpenNetwork(this)){
            Toast.makeText(this,"当前网络不可用，请打开网络",Toast.LENGTH_SHORT).show();
        }
        listlist = new ArrayList<List<Integer>>();
        init();
        showLotter();
        clear();

        List<Integer> hunlist = (List<Integer>) getSharedPreferences().get("hun");
        for (int c = 0; c < hunlist.size(); c++) {
            adapter1.chiceState(hunlist.get(c));
            a1 = adapter1.i;
            i1[hunlist.get(c)] = list.get(hunlist.get(c));
            showMoney();
        }
        List<Integer> tenlist = (List<Integer>) getSharedPreferences().get("ten");
        for (int c = 0; c < tenlist.size(); c++) {
            adapter2.chiceState(tenlist.get(c));
            a2 = adapter2.i;
            i2[tenlist.get(c)] = list.get(tenlist.get(c));
            showMoney();
        }
        List<Integer> indlist = (List<Integer>) getSharedPreferences().get("ind");
        for (int c = 0; c < indlist.size(); c++) {
            adapter3.chiceState(indlist.get(c));
            a3 = adapter3.i;
            i3[indlist.get(c)] = list.get(indlist.get(c));
            showMoney();
        }
    }

    /**
     * 请求网络获取彩票信息
     */
    private void showLotter() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    try {
                        JSONObject jobj = new JSONObject(result);
                        String status = jobj.getString("status");
                        String msg2 = jobj.getString("msg");
                        pausetime=jobj.getString("pausetime");
                        listis = new ArrayList<String[]>();
                        if (status.equals("1")) {
                            //开奖日期
                            String date = jobj.getString("date");
                            tv_time.setText(date);
                            JSONArray data = jobj.getJSONArray("data");
                            kjrq = data.getJSONObject(0).getString("expect");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject datai = data.getJSONObject(i);
                                //彩票开票的日期
                                String expect = datai.getString("expect");
                                //开奖号码
                                String opencode = datai.getString("opencode");
                                String[] s = {expect, opencode};
                                listis.add(s);
                            }
                            fradapter = new FragmentOneKJXXAdapter(OneThreeD.this, listis, 7);
                            lv.setAdapter(fradapter);
                        } else {
                            Toast.makeText(OneThreeD.this, msg2, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        // 启动线程来执行任务
        new Thread() {
            public void run() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("lotterytype", "fc3d");
                // 请求网络
                try {
                    result = URLConnectionUtil.sendPostRequest(name_space,
                            map, "UTF-8", 1);
                    Log.i("result22", result);
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

    /**
     * 实例化控件及设置相应的单击监听事件
     */
    private void init() {
        tv_time = (TextView) findViewById(R.id.tv_time);
        lv = (ListView) findViewById(R.id.lv);
        linout1 = (LinearLayout) findViewById(R.id.linout1);
        iv_xl = (ImageView) findViewById(R.id.iv_xl);
        iv_xl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //走势图下拉列表
                if (zst) {
                    zst = false;
                    iv_xl.setBackgroundResource(R.mipmap.icon_xl);
                    lv.setVisibility(View.GONE);
                    linout1.setVisibility(View.GONE);
//                    view.setVisibility(View.GONE);
                } else {
                    iv_xl.setBackgroundResource(R.mipmap.icon_sl);
                    lv.setVisibility(View.VISIBLE);
                    linout1.setVisibility(View.VISIBLE);
//                    view.setVisibility(View.VISIBLE);
                    zst = true;
                }
            }
        });

        fanhui = (ImageView) findViewById(R.id.fanhui);
        fanhui.setOnClickListener(this);
        tv_hun = (TextView) findViewById(R.id.tv_hun);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_bt = (TextView) findViewById(R.id.tv_bt);
        iv_top = (ImageView) findViewById(R.id.iv_top);
        bt_next = (TextView) findViewById(R.id.bt_next);
        bt_next.setOnClickListener(this);
        bt_clear = (Button) findViewById(R.id.btn_clear);
        bt_clear.setOnClickListener(this);
        tv_choose = (TextView) findViewById(R.id.tv_choose);
        tv_choose.setOnClickListener(this);
        tv_text = (TextView) findViewById(R.id.tv_text);

        layout = (RelativeLayout) findViewById(R.id.layout);

        hun_linearlayout = (LinearLayout) findViewById(R.id.hun_linearlayout);
        ten_linearlayout = (LinearLayout) findViewById(R.id.ten_linearlayout);
        ind_linearlayout = (LinearLayout) findViewById(R.id.ind_linearlayout);

        view_view = findViewById(R.id.view_view);

        gridview1 = (GridView) findViewById(R.id.hun_gridview);
        gridview2 = (GridView) findViewById(R.id.ten_gridview);
        gridview3 = (GridView) findViewById(R.id.ind_gridview);

        rl_text = (LinearLayout) findViewById(R.id.rl_text);
        rl_text.setOnClickListener(this);

        //设置数据源
        list = new ArrayList<Integer>();
        for (int i = 0; i <= 9; i++) {
            list.add(i);
        }
        //初始化用来保存选择的号码
        i1 = new int[10];
        for (int a = 0; a <= 9; a++) {
            i1[a] = -1;
        }
        i2 = new int[10];
        for (int a = 0; a <= 9; a++) {
            i2[a] = -1;
        }
        i3 = new int[10];
        for (int a = 0; a <= 9; a++) {
            i3[a] = -1;
        }

        adapter1 = new ThreeDAdapter1(list, OneThreeD.this);
        gridview1.setAdapter(adapter1);
        gridview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter1.chiceState(position);
                a1 = adapter1.i;
                if (i1[position] != -1) {
                    i1[position] = -1;
                } else {
                    i1[position] = list.get(position);
                }
                showMoney();
            }
        });

        adapter2 = new ThreeDAdapter2(list, OneThreeD.this);
        gridview2.setAdapter(adapter2);
        gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter2.chiceState(position);
                a2 = adapter2.i;
                Log.i("a2", a2 + "");
                if (i2[position] != -1) {
                    i2[position] = -1;
                } else {
                    i2[position] = list.get(position);
                }
                showMoney();
            }
        });

        adapter3 = new ThreeDAdapter3(list, OneThreeD.this);
        gridview3.setAdapter(adapter3);
        gridview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter3.chiceState(position);
                a3 = adapter3.i;
                Log.i("a3", a3 + "");
                if (i3[position] != -1) {
                    i3[position] = -1;
                } else {
                    i3[position] = list.get(position);
                }
                showMoney();
            }
        });

    }

    /**
     * 跟新UI,显示选择了多少注共多少钱
     */
    private void showMoney() {
        if (po == 0) {//直选
            tv_num.setText("共" + a1 * a2 * a3 + "注" + a1 * a2 * a3 * 2 + "元");
        } else if (po == 1) {//组三
            switch (a1) {
                case 0:
                    tv_num.setText("共" + 0 + "注" + 0 + "元");
                    break;
                case 1:
                    tv_num.setText("共" + 0 + "注" + 0 + "元");
                    break;
                case 2:
                    tv_num.setText("共" + 2 + "注" + 4 + "元");
                    break;
                case 3:
                    tv_num.setText("共" + 6 + "注" + 12 + "元");
                    break;
                case 4:
                    tv_num.setText("共" + 12 + "注" + 24 + "元");
                    break;
                case 5:
                    tv_num.setText("共" + 20 + "注" + 80 + "元");
                    break;
                case 6:
                    tv_num.setText("共" + 30 + "注" + 60 + "元");
                    break;
                case 7:
                    tv_num.setText("共" + 42 + "注" + 84 + "元");
                    break;
                case 8:
                    tv_num.setText("共" + 56 + "注" + 112 + "元");
                    break;
                case 9:
                    tv_num.setText("共" + 72 + "注" + 144 + "元");
                    break;
                case 10:
                    tv_num.setText("共" + 90 + "注" + 180 + "元");
                    break;
            }
        } else if (po == 2) {//组六
            switch (a1) {
                case 0:
                    tv_num.setText("共" + 0 + "注" + 0 + "元");
                    break;
                case 1:
                    tv_num.setText("共" + 0 + "注" + 0 + "元");
                    break;
                case 2:
                    tv_num.setText("共" + 0 + "注" + 0 + "元");
                    break;
                case 3:
                    tv_num.setText("共" + 1 + "注" + 2 + "元");
                    break;
                case 4:
                    tv_num.setText("共" + 4 + "注" + 8 + "元");
                    break;
                case 5:
                    tv_num.setText("共" + 10 + "注" + 20 + "元");
                    break;
                case 6:
                    tv_num.setText("共" + 20 + "注" + 40 + "元");
                    break;
                case 7:
                    tv_num.setText("共" + 35 + "注" + 70 + "元");
                    break;
                case 8:
                    tv_num.setText("共" + 56 + "注" + 112 + "元");
                    break;
                case 9:
                    tv_num.setText("共" + 84 + "注" + 168 + "元");
                    break;
                case 10:
                    tv_num.setText("共" + 120 + "注" + 240 + "元");
                    break;
            }
        }
    }

    /**
     * 将选中的号码保存
     */
    private void setShared(List<Integer> hun, List<Integer> ten, List<Integer> ind) {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int time = sharedPreferences.getInt("time", 0);
        int item = sharedPreferences.getInt("item", 0);
        if (po == 0) {//直选
            if (item >= 0) {
                editor.putInt("hunsize" + item, hun.size());
                for (int i = 0; i < hun.size(); i++) {
                    editor.putInt("hun" + item + i, hun.get(i));
                }
                editor.putInt("tensize" + item, ten.size());
                for (int i = 0; i < ten.size(); i++) {
                    editor.putInt("ten" + item + i, ten.get(i));
                }
                editor.putInt("indsize" + item, ind.size());
                for (int i = 0; i < ind.size(); i++) {
                    editor.putInt("ind" + item + i, ind.get(i));
                }
            } else {
                editor.putInt("hunsize" + time, hun.size());
                for (int i = 0; i < hun.size(); i++) {
                    editor.putInt("hun" + time + i, hun.get(i));
                }
                editor.putInt("tensize" + time, ten.size());
                for (int i = 0; i < ten.size(); i++) {
                    editor.putInt("ten" + time + i, ten.get(i));
                }
                editor.putInt("indsize" + time, ind.size());
                for (int i = 0; i < ind.size(); i++) {
                    editor.putInt("ind" + time + i, ind.get(i));
                }
                editor.putInt("time", time + 1);
            }
        } else if (po == 1) {//组三
            if (item >= 0) {
                editor.putInt("hunsize" + item, hun.size());
                for (int i = 0; i < hun.size(); i++) {
                    editor.putInt("hun" + item + i, hun.get(i));
                }
            } else {
                editor.putInt("hunsize" + time, hun.size());
                for (int i = 0; i < hun.size(); i++) {
                    editor.putInt("hun" + time + i, hun.get(i));
                }
                editor.putInt("time", time + 1);
            }
        } else if (po == 2) {//组六
            if (item >= 0) {
                editor.putInt("hunsize" + item, hun.size());
                for (int i = 0; i < hun.size(); i++) {
                    editor.putInt("hun" + item + i, hun.get(i));
                }
            } else {
                editor.putInt("hunsize" + time, hun.size());
                for (int i = 0; i < hun.size(); i++) {
                    editor.putInt("hun" + time + i, hun.get(i));
                }
                editor.putInt("time", time + 1);
            }
        }
        editor.commit();
    }

    /**
     * 获取shared中的号码
     */
    private Map<String, Object> getSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        //百位的集合
        List<Integer> hunlist = new ArrayList<Integer>();
        //十位的集合
        List<Integer> tenlist = new ArrayList<Integer>();
        //个位的集合
        List<Integer> indlist = new ArrayList<Integer>();

        int item = sharedPreferences.getInt("item", 0);

        int hunsize = sharedPreferences.getInt("hunsize" + item, 0);//百位的个数
        for (int j = 0; j < hunsize; j++) {
            hunlist.add(sharedPreferences.getInt("hun" + item + j, 0));
        }
        int tensize = sharedPreferences.getInt("tensize" + item, 0);//十位的个数
        for (int j = 0; j < tensize; j++) {
            tenlist.add(sharedPreferences.getInt("ten" + item + j, 0));
        }
        int indsize = sharedPreferences.getInt("indsize" + item, 0);//个位的个数
        for (int j = 0; j < indsize; j++) {
            indlist.add(sharedPreferences.getInt("ind" + item + j, 0));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hun", hunlist);
        map.put("ten", tenlist);
        map.put("ind", indlist);
        return map;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //走势图下拉
            case R.id.iv_xl:
                break;
            //返回
            case R.id.fanhui:
                finish();
                break;
            //下一步
            case R.id.bt_next:
                if (po == 0) {//直选
                    if (a1 < 1 || a2 < 1 || a3 < 1) {
                        Toast.makeText(OneThreeD.this, "每位最少选择一个号", Toast.LENGTH_SHORT).show();
                    } else {
                        li1 = new ArrayList<Integer>();
                        for (int c = 0; c < i1.length; c++) {
                            if (i1[c] != -1) {
                                li1.add(i1[c]);
                            }
                        }
                        listlist.add(li1);
                        li2 = new ArrayList<Integer>();
                        for (int c = 0; c < i2.length; c++) {
                            if (i2[c] != -1) {
                                li2.add(i2[c]);
                            }
                        }
                        listlist.add(li2);
                        li3 = new ArrayList<Integer>();
                        for (int c = 0; c < i3.length; c++) {
                            if (i3[c] != -1) {
                                li3.add(i3[c]);
                            }
                        }
                        setShared(li1, li2, li3);
                        Intent intent = new Intent(this, OneLotteryBetPayx.class);
                        intent.putExtra("彩种", 7);
                        intent.putExtra("分类", po);
                        intent.putExtra("pausetime",pausetime);
                        intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                        startActivity(intent);
                    }
                } else if (po == 1) {//组三
                    if (a1 < 2) {
                        Toast.makeText(OneThreeD.this, "至少选择两个号", Toast.LENGTH_SHORT).show();
                    } else {
                        li1 = new ArrayList<Integer>();
                        for (int c = 0; c < i1.length; c++) {
                            if (i1[c] != -1) {
                                li1.add(i1[c]);
                            }
                        }
                        setShared(li1, li2, li3);
                        Intent intent = new Intent(this, OneLotteryBetPayx.class);
                        intent.putExtra("彩种", 7);
                        intent.putExtra("分类", po);
                        intent.putExtra("pausetime",pausetime);
                        intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                        startActivity(intent);
                    }
                } else if (po == 2) {//组六
                    if (a1 < 3) {
                        Toast.makeText(OneThreeD.this, "至少选择三个号", Toast.LENGTH_SHORT).show();
                    } else {
                        li1 = new ArrayList<Integer>();
                        for (int c = 0; c < i1.length; c++) {
                            if (i1[c] != -1) {
                                li1.add(i1[c]);
                            }
                        }
                        setShared(li1, li2, li3);
                        Intent intent = new Intent(this, OneLotteryBetPayx.class);
                        intent.putExtra("彩种", 7);
                        intent.putExtra("分类", po);
                        intent.putExtra("pausetime",pausetime);
                        intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                        startActivity(intent);
                    }
                }
                break;
            //清除
            case R.id.btn_clear:
                clear();
                break;
            //选择
            case R.id.rl_text:
                if (b) {
                    showXingBieDuiHuaKuang();
                    iv_top.setImageResource(R.mipmap.top_1);
                    b = false;
                } else {
                    popupWindow.dismiss();
                    iv_top.setImageResource(R.mipmap.top_2);
                    b = true;
                }
                break;
            //机选
            case R.id.tv_choose:
                clear();
                if (po == 0) {//直选
                    for (int i = 0; i < 3; i++) {
                        int x = (int) (Math.random() * 10);
                        sjs[i] = x;
                        Log.i("sjs", x + "");
                    }
                    //百位
                    int b1 = sjs[0];
                    adapter1.chiceState(b1);
                    a1 = adapter1.i;
                    if (i1[b1] == list.get(b1)) {
                        i1[b1] = -1;
                    } else {
                        i1[b1] = list.get(b1);
                    }
                    showMoney();

                    //十位
                    int b2 = sjs[1];
                    adapter2.chiceState(b2);
                    a2 = adapter2.i;
                    if (i2[b2] == list.get(b2)) {
                        i2[b2] = -1;
                    } else {
                        i2[b2] = list.get(b2);
                    }
                    showMoney();

                    //个位
                    int b3 = sjs[2];
                    adapter3.chiceState(b3);
                    a3 = adapter3.i;
                    if (i3[b3] == list.get(b3)) {
                        i3[b3] = -1;
                    } else {
                        i3[b3] = list.get(b3);
                    }
                    showMoney();
                } else if (po == 1) {//组三
                    for (int i = 0; i < 2; i++) {
                        int x = (int) (Math.random() * 10);
                        sjs[i] = x;
                        for (int j = 0; j <= i; j++) {
                            if ((i != j) && sjs[i] == sjs[j]) {
                                i--;
                            }
                        }
                        Log.i("sjs", x + "");
                    }
                    for (int p = 0; p < 2; p++) {
                        int b1 = sjs[p];
                        adapter1.chiceState(b1);
                        a1 = adapter1.i;
                        if (i1[b1] == list.get(b1)) {
                            i1[b1] = -1;
                        } else {
                            i1[b1] = list.get(b1);
                        }
                        showMoney();
                    }
                } else if (po == 2) {//组六
                    for (int i = 0; i < 3; i++) {
                        int x = (int) (Math.random() * 10);
                        sjs[i] = x;
                        //去掉生成的重复的数
                        for (int j = 0; j <= i; j++) {
                            if ((i != j) && sjs[i] == sjs[j]) {
                                i--;
                            }
                        }
                        Log.i("sjs", x + "");
                    }
                    for (int p = 0; p < 3; p++) {
                        int b1 = sjs[p];
                        adapter1.chiceState(b1);
                        a1 = adapter1.i;
                        if (i1[b1] == list.get(b1)) {
                            i1[b1] = -1;
                        } else {
                            i1[b1] = list.get(b1);
                        }
                        showMoney();
                    }
                }
                break;
        }
    }

    /**
     * 弹出框
     */
    private void showXingBieDuiHuaKuang() {
        adapter = new OneGridViewAdapter(data2(), OneThreeD.this);
        adapter.setSeclection(po);

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(OneThreeD.this).inflate(
                R.layout.directly_classify, null);

        popupWindow = new PopupWindow(contentView,
                ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });


        // 设置好参数之后再show
        popupWindow.showAsDropDown(layout);

        View view3 = contentView.findViewById(R.id.view3);
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                b = true;
            }
        });
        gridview = (GridView) contentView.findViewById(R.id.gridview);
        gridview.setAdapter(adapter);
        Log.i("podsition", "position + ");

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("podsition", position + "");
                po = position;
                adapter.setSeclection(po);
                tv_bt.setText(data2().get(position).get("text"));
                if (position == 0) {//直选
                    clear();
                    ind_linearlayout.setVisibility(View.VISIBLE);
                    ten_linearlayout.setVisibility(View.VISIBLE);
                    view_view.setVisibility(View.VISIBLE);
                    tv_hun.setText("百位");
                    adapter1 = new ThreeDAdapter1(list, OneThreeD.this);
                    gridview1.setAdapter(adapter1);
                } else if (position == 1) {//组三
                    clear();
                    ten_linearlayout.setVisibility(View.GONE);
                    ind_linearlayout.setVisibility(View.GONE);
                    view_view.setVisibility(View.GONE);
                    tv_hun.setText("选号");
                    tv_text.setText("至少选两个号");
                    adapter1 = new ThreeDAdapter1(list, OneThreeD.this);
                    gridview1.setAdapter(adapter1);

                } else if (position == 2) {//组六
                    clear();
                    view_view.setVisibility(View.GONE);
                    ind_linearlayout.setVisibility(View.GONE);
                    ten_linearlayout.setVisibility(View.GONE);
                    tv_hun.setText("选号");
                    tv_text.setText("至少选三个号");
                    adapter1 = new ThreeDAdapter1(list, OneThreeD.this);
                    gridview1.setAdapter(adapter1);

                }
                popupWindow.dismiss();
                iv_top.setImageResource(R.mipmap.top_2);
                b = true;
            }
        });
    }

    /**
     * 3D分类玩法
     *
     * @return
     */
    private List<Map<String, String>> data2() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("text", "直选");
        list.add(map);
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("text", "组三");
        list.add(map1);
        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("text", "组六");
        list.add(map3);

        return list;
    }

    //清楚选中号码
    private void clear() {
        if (po == 0) {//直选
            //百位
            for (int c = 0; c < i1.length; c++) {
                if (i1[c] != -1) {
                    adapter1.chiceState(c);
                    a1 = adapter1.i;
                    i1[c] = -1;
                    showMoney();
                }
            }
            //十位
            for (int c = 0; c < i2.length; c++) {
                if (i2[c] != -1) {
                    adapter2.chiceState(c);
                    a2 = adapter2.i;
                    i2[c] = -1;
                    showMoney();
                }
            }
            //个位
            for (int c = 0; c < i3.length; c++) {
                if (i3[c] != -1) {
                    adapter3.chiceState(c);
                    a3 = adapter3.i;
                    i3[c] = -1;
                    showMoney();
                }
            }

        } else if (po == 1) {//组三
            //百位
            for (int c = 0; c < i1.length; c++) {
                if (i1[c] != -1) {
                    adapter1.chiceState(c);
                    a1 = adapter1.i;
                    i1[c] = -1;
                    showMoney();
                }
            }
        } else if (po == 2) {//组六
            //百位
            for (int c = 0; c < i1.length; c++) {
                if (i1[c] != -1) {
                    adapter1.chiceState(c);
                    a1 = adapter1.i;
                    i1[c] = -1;
                    showMoney();
                }
            }
        }
        a1 = 0;
        a2 = 0;
        a3 = 0;
        Log.i("a123", a1 + " " + a2 + " " + a3);
        tv_num.setText("共" + 0 + "注" + 0 + "元");
    }
}
