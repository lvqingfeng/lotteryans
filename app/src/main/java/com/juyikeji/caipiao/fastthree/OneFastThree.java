package com.juyikeji.caipiao.fastthree;

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

//快三
public class OneFastThree extends Activity implements View.OnClickListener {
    private RelativeLayout layout;
    private LinearLayout rl_text;
    private ImageView iv_top, fanhui;
    private Button btn_clear;
    private TextView tv_bt, tv_num, tv_choose, tv_text, tv_hun, tv_ten;
    private TextView bt_next;
    private LinearLayout linearlayout;

    private List<Integer> li1, li2;
    //数据源
    List<Integer> list, list2;
    private GridView gridview;
    private GridView hun_gridview, ten_gridview;
    //快三的两个adapter
    private FastThreeAdapter1 adapter1;
    private FastThreeAdapter2 adapter2;
    //用来保存每位选择的号码的个数
    private int a1 = 0, a2 = 0;
    //用来保存选择的号码，依次是百位，十位，个位
    int i1[], i2[];

    private boolean b = true;
    //弹出框对象
    private PopupWindow popupWindow;
    private OneGridViewAdapter adapter;
    //记录选择的玩法
    private int po = 0;
    //保存选中的号码的集合
    private List<List<Integer>> listlist;


    private ImageView iv_xl;
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
    private String kjrq;
    //停售日期
    private String pausetime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_fastthree);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!URLConnectionUtil.isOpenNetwork(this)) {
            Toast.makeText(this, "当前网络不可用，请打开网络", Toast.LENGTH_SHORT).show();
        }
        init();
        showLotter();
        clear();
        listlist = new ArrayList<List<Integer>>();

        if (po == 1 || po == 3 || po == 4 || po == 5) {//三同号
            list = new ArrayList<Integer>();
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(4);
            list.add(5);
            list.add(6);
            adapter1 = new FastThreeAdapter1(list, OneFastThree.this);
            hun_gridview.setAdapter(adapter1);
        } else if (po == 2 || po == 6 || po == 7) {//二同号单选
            list = new ArrayList<Integer>();
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(4);
            list.add(5);
            list.add(6);
            adapter1 = new FastThreeAdapter1(list, OneFastThree.this);
            hun_gridview.setAdapter(adapter1);
            tv_text.setText("猜对子号（有两个号相同）");

            list2 = new ArrayList<Integer>();
            list2.add(1);
            list2.add(2);
            list2.add(3);
            list2.add(4);
            list2.add(5);
            list2.add(6);
            adapter2 = new FastThreeAdapter2(list2, OneFastThree.this);
            ten_gridview.setAdapter(adapter2);
        }

        List<Integer> hunlist = (List<Integer>) getSharedPreferences().get("hun");
        for (int c = 0; c < hunlist.size(); c++) {
            if (po == 0) {
                adapter1.chiceState(hunlist.get(c) - 4);
                a1 = adapter1.i;
                i1[hunlist.get(c) - 4] = list.get(hunlist.get(c) - 4);
                showMoney();
            } else if (po == 1 || po == 2 || po == 3 || po == 4 || po == 5 || po == 6 || po == 7) {
                adapter1.chiceState(hunlist.get(c) - 1);
                a1 = adapter1.i;
                i1[hunlist.get(c) - 1] = list.get(hunlist.get(c) - 1);
                showMoney();
            } else {
                adapter1.chiceState(hunlist.get(c));
                a1 = adapter1.i;
                i1[hunlist.get(c)] = list.get(hunlist.get(c));
                showMoney();
            }
        }
        List<Integer> tenlist = (List<Integer>) getSharedPreferences().get("ten");
        for (int c = 0; c < tenlist.size(); c++) {
            if (po == 2 || po == 6 || po == 7) {
                adapter2.chiceState(tenlist.get(c) - 1);
                a2 = adapter2.i;
                i2[tenlist.get(c) - 1] = list.get(tenlist.get(c) - 1);
                showMoney();
            } else {
                adapter2.chiceState(tenlist.get(c));
                a2 = adapter2.i;
                i2[tenlist.get(c)] = list.get(tenlist.get(c));
                showMoney();
            }
        }
    }

    /**
     * 跟新UI,显示选择了多少注共多少钱
     */
    private void showMoney() {
        if (po == 0) {//和值
            tv_num.setText("共" + a1 + "注" + a1 * 2 + "元");
        } else if (po == 1) {//三同号
            tv_num.setText("共" + a1 + "注" + a1 * 2 + "元");
        } else if (po == 2) {//二同号单选
            tv_num.setText("共" + a1 * a2 + "注" + a1 * a2 * 2 + "元");
        } else if (po == 3) {//二同号复选
            tv_num.setText("共" + a1 + "注" + a1 * 2 + "元");
        } else if (po == 4) {//三不同号
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
            }
        } else if (po == 5) {//二不同号
            switch (a1) {
                case 0:
                    tv_num.setText("共" + 0 + "注" + 0 + "元");
                    break;
                case 1:
                    tv_num.setText("共" + 0 + "注" + 0 + "元");
                    break;
                case 2:
                    tv_num.setText("共" + 1 + "注" + 2 + "元");
                    break;
                case 3:
                    tv_num.setText("共" + 3 + "注" + 6 + "元");
                    break;
                case 4:
                    tv_num.setText("共" + 6 + "注" + 12 + "元");
                    break;
                case 5:
                    tv_num.setText("共" + 10 + "注" + 20 + "元");
                    break;
                case 6:
                    tv_num.setText("共" + 15 + "注" + 30 + "元");
                    break;
            }
        } else if (po == 6) {//三不同号胆拖
            switch (a1) {
                case 0:
                    tv_num.setText("共" + 0 + "注" + 0 + "元");
                    break;
                case 1:
                    if (a2 == 0) {
                        tv_num.setText("共" + 0 + "注" + 0 + "元");
                    } else if (a2 == 1) {
                        tv_num.setText("共" + 0 + "注" + 0 + "元");
                    } else if (a2 == 2) {
                        tv_num.setText("共" + 0 + "注" + 0 + "元");
                    } else if (a2 == 3) {
                        tv_num.setText("共" + 3 + "注" + 6 + "元");
                    } else if (a2 == 4) {
                        tv_num.setText("共" + 6 + "注" + 12 + "元");
                    } else if (a2 == 5) {
                        tv_num.setText("共" + 10 + "注" + 20 + "元");
                    }

                    break;
                case 2:
                    if (a2 == 0) {
                        tv_num.setText("共" + 0 + "注" + 0 + "元");
                    } else if (a2 == 1) {
                        tv_num.setText("共" + 0 + "注" + 0 + "元");
                    } else if (a2 == 2) {
                        tv_num.setText("共" + 2 + "注" + 4 + "元");
                    } else if (a2 == 3) {
                        tv_num.setText("共" + 3 + "注" + 6 + "元");
                    } else if (a2 == 4) {
                        tv_num.setText("共" + 4 + "注" + 8 + "元");
                    }
                    break;
            }
        } else if (po == 7) {//二不同号胆拖
            if (a1 == 1) {
                switch (a2) {
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
                        tv_num.setText("共" + 3 + "注" + 6 + "元");
                        break;
                    case 4:
                        tv_num.setText("共" + 4 + "注" + 8 + "元");
                        break;
                    case 5:
                        tv_num.setText("共" + 5 + "注" + 10 + "元");
                        break;
                    case 6:
                        tv_num.setText("共" + 0 + "注" + 0 + "元");
                        break;
                }
            } else {
                tv_num.setText("共" + 0 + "注" + 0 + "元");
            }
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
                        pausetime = jobj.getString("pausetime");
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
                            fradapter = new FragmentOneKJXXAdapter(OneFastThree.this, listis, 9);
                            lv.setAdapter(fradapter);
                        } else {
                            Toast.makeText(OneFastThree.this, msg2, Toast.LENGTH_SHORT).show();
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
                map.put("lotterytype", "hebk3");
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
     * 实例化控件及设置其单击监听事件
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
        tv_text = (TextView) findViewById(R.id.tv_text);
        tv_choose = (TextView) findViewById(R.id.tv_choose);
        tv_choose.setOnClickListener(this);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        bt_next = (TextView) findViewById(R.id.bt_next);
        bt_next.setOnClickListener(this);
        tv_num = (TextView) findViewById(R.id.tv_num);
        rl_text = (LinearLayout) findViewById(R.id.rl_text);
        rl_text.setOnClickListener(this);
        iv_top = (ImageView) findViewById(R.id.iv_top);
        tv_bt = (TextView) findViewById(R.id.tv_bt);
        layout = (RelativeLayout) findViewById(R.id.layout);
        linearlayout = (LinearLayout) findViewById(R.id.linearlayout);
        tv_hun = (TextView) findViewById(R.id.tv_hun);
        tv_ten = (TextView) findViewById(R.id.tv_ten);

        hun_gridview = (GridView) findViewById(R.id.hun_gridview);
        ten_gridview = (GridView) findViewById(R.id.ten_gridview);
        //设置数据源
        list = new ArrayList<Integer>();
        for (int i = 4; i <= 17; i++) {
            list.add(i);
        }
        //初始化用来保存选择的号码
        i1 = new int[14];
        for (int a = 0; a <= 13; a++) {
            i1[a] = -1;
        }
        i2 = new int[14];
        for (int a = 0; a <= 13; a++) {
            i2[a] = -1;
        }

        adapter1 = new FastThreeAdapter1(list, OneFastThree.this);
        hun_gridview.setAdapter(adapter1);
        hun_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (po == 6) {//三不同号胆拖
                    //设置胆拖不相同
                    if (i2[position] != -1) {
                        adapter2.chiceState(position);
                        a2 = adapter2.i;
                        Log.i("a1", a2 + "");
                        if (i2[position] == list2.get(position)) {
                            i2[position] = -1;
                        } else {
                            i2[position] = list2.get(position);
                        }
                        showMoney();
                    }
                    adapter1.chiceState(position);
                    a1 = adapter1.i;
                    Log.i("a1", a1 + "");
                    //设置胆码最多选两个
                    if (a1 <= 2) {
                        if (i1[position] == list.get(position)) {
                            i1[position] = -1;
                        } else {
                            i1[position] = list.get(position);
                        }
                        showMoney();
                    } else if (a1 > 2) {
                        adapter1.chiceState(position);
                        a1 = adapter1.i;
                        i1[position] = -1;
                        return;
                    }
                } else if (po == 7) {//二不同胆拖
                    if (i2[position] != -1) {
                        adapter2.chiceState(position);
                        a2 = adapter2.i;
                        Log.i("a1", a2 + "");
                        if (i2[position] == list2.get(position)) {
                            i2[position] = -1;
                        } else {
                            i2[position] = list2.get(position);
                        }
                        showMoney();
                    }
                    adapter1.chiceState(position);
                    a1 = adapter1.i;
                    Log.i("a1", a1 + "");
                    //设置只能选一个胆码
                    if (a1 <= 1) {
                        if (i1[position] != -1) {
                            i1[position] = -1;
                        } else {
                            i1[position] = list.get(position);
                        }
                        showMoney();
                    } else if (a1 > 1) {
                        adapter1.chiceState(position);
                        a1 = adapter1.i;
                        i1[position] = -1;
                        return;
                    }
                } else {//其它
                    if (i2[position] != -1) {
                        adapter2.chiceState(position);
                        a2 = adapter2.i;
                        Log.i("a1", a2 + "");
                        if (i2[position] == list2.get(position)) {
                            i2[position] = -1;
                        } else {
                            i2[position] = list2.get(position);
                        }
                        showMoney();
                    }
                    adapter1.chiceState(position);
                    a1 = adapter1.i;
                    Log.i("a1", a1 + "");
                    if (i1[position] == list.get(position)) {
                        i1[position] = -1;
                    } else {
                        i1[position] = list.get(position);
                    }
                    showMoney();
                }
            }
        });
        ten_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (i1[position] != -1) {
                    adapter1.chiceState(position);
                    a1 = adapter1.i;
                    Log.i("a1", a1 + "");
                    if (i1[position] == list.get(position)) {
                        i1[position] = -1;
                    } else {
                        i1[position] = list.get(position);
                    }
                    showMoney();
                }
                adapter2.chiceState(position);
                a2 = adapter2.i;
                Log.i("a2", a2 + "");
                if (i2[position] == list2.get(position)) {
                    i2[position] = -1;
                } else {
                    i2[position] = list2.get(position);
                }
                showMoney();
            }
        });
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
            //选择玩法
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
            //下一步
            case R.id.bt_next:
                if (po == 0 || po == 1 || po == 3) {//和值、三同号、二同号复选
                    if (a1 < 1) {
                        Toast.makeText(OneFastThree.this, "至少选择一个号", Toast.LENGTH_SHORT).show();
                    } else {
                        li1 = new ArrayList<Integer>();
                        for (int c = 0; c < i1.length; c++) {
                            if (i1[c] != -1) {
                                li1.add(i1[c]);
                            }
                        }
                        setShared(li1, li2);
                        Intent intent = new Intent(this, OneLotteryBetPayx.class);
                        intent.putExtra("彩种", 9);
                        intent.putExtra("分类", po);
                        intent.putExtra("pausetime", pausetime);
                        intent.putExtra("kjrq", Long.parseLong(kjrq) + 1);
                        startActivity(intent);
                    }
                } else if (po == 2) {//二同号单选
                    if (a1 < 1) {
                        Toast.makeText(OneFastThree.this, "相同号与不同号至少都选择一个，且不同号不能与相同号中的一位相同！", Toast.LENGTH_SHORT).show();
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
                        setShared(li1, li2);
                        Intent intent = new Intent(this, OneLotteryBetPayx.class);
                        intent.putExtra("彩种", 9);
                        intent.putExtra("分类", po);
                        intent.putExtra("pausetime", pausetime);
                        intent.putExtra("kjrq", Long.parseLong(kjrq) + 1);
                        startActivity(intent);
                    }
                } else if (po == 4) {//三不同号
                    if (a1 < 3) {
                        Toast.makeText(OneFastThree.this, "至少选择三个号", Toast.LENGTH_SHORT).show();
                    } else {
                        li1 = new ArrayList<Integer>();
                        for (int c = 0; c < i1.length; c++) {
                            if (i1[c] != -1) {
                                li1.add(i1[c]);
                            }
                        }
                        setShared(li1, li2);
                        Intent intent = new Intent(this, OneLotteryBetPayx.class);
                        intent.putExtra("彩种", 9);
                        intent.putExtra("分类", po);
                        intent.putExtra("pausetime", pausetime);
                        intent.putExtra("kjrq", Long.parseLong(kjrq) + 1);
                        startActivity(intent);
                    }
                } else if (po == 5) {//二不同号
                    if (a1 < 2) {
                        Toast.makeText(OneFastThree.this, "至少选择两个号", Toast.LENGTH_SHORT).show();
                    } else {
                        li1 = new ArrayList<Integer>();
                        for (int c = 0; c < i1.length; c++) {
                            if (i1[c] != -1) {
                                li1.add(i1[c]);
                            }
                        }
                        setShared(li1, li2);
                        Intent intent = new Intent(this, OneLotteryBetPayx.class);
                        intent.putExtra("彩种", 9);
                        intent.putExtra("分类", po);
                        intent.putExtra("pausetime", pausetime);
                        intent.putExtra("kjrq", Long.parseLong(kjrq) + 1);
                        startActivity(intent);
                    }
                } else if (po == 6) {//三不同号胆拖
                    if (a1 > 2 || a1 < 1 || a2 < 2 || a2 > 5 || (a2 + a1) < 4) {
                        Toast.makeText(OneFastThree.this, "请选择：1~2个胆码；2~5个拖码；胆码与拖码之和不小于4个", Toast.LENGTH_SHORT).show();
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
                        setShared(li1, li2);
                        Intent intent = new Intent(this, OneLotteryBetPayx.class);
                        intent.putExtra("彩种", 9);
                        intent.putExtra("分类", po);
                        intent.putExtra("pausetime", pausetime);
                        intent.putExtra("kjrq", Long.parseLong(kjrq) + 1);
                        startActivity(intent);
                    }
                } else if (po == 7) {//二不同号胆拖
                    if (a1 == 1) {
                        if (a2 < 2) {
                            Toast.makeText(OneFastThree.this, "请选择：1个胆码；2~5个拖码；胆码与拖码之和不小于3个", Toast.LENGTH_SHORT).show();
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
                            setShared(li1, li2);
                            Intent intent = new Intent(this, OneLotteryBetPayx.class);
                            intent.putExtra("彩种", 9);
                            intent.putExtra("分类", po);
                            intent.putExtra("pausetime", pausetime);
                            intent.putExtra("kjrq", Long.parseLong(kjrq) + 1);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(OneFastThree.this, "请选择：1个胆码；2~5个拖码；胆码与拖码之和不小于3个", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            //清除
            case R.id.btn_clear:
                clear();
                break;
            //机选
            case R.id.tv_choose:
                clear();
                if (po == 0) {//和值
                    //存放随机生成的数
                    int[] sjs = new int[1];
                    for (int i = 0; i < 1; i++) {
                        int x = (int) (Math.random() * 10);
                        sjs[i] = x;
                        Log.i("sjs", x + "");
                    }
                    int b1 = sjs[0];
                    adapter1.chiceState(b1);
                    a1 = adapter1.i;
                    if (i1[b1] == list.get(b1)) {
                        i1[b1] = -1;
                    } else {
                        i1[b1] = list.get(b1);
                    }
                    showMoney();
                } else if (po == 1) {//三同号
                    //存放随机生成的数
                    int[] sjs = new int[1];
                    for (int i = 0; i < 1; i++) {
                        int x = (int) (Math.random() * 6);
                        sjs[i] = x;
                        Log.i("sjs", x + "");
                    }
                    int b1 = sjs[0];
                    adapter1.chiceState(b1);
                    a1 = adapter1.i;
                    if (i1[b1] == list.get(b1)) {
                        i1[b1] = -1;
                    } else {
                        i1[b1] = list.get(b1);
                    }
                    showMoney();
                } else if (po == 2) {//二同号单选
                    //存放随机生成的数
                    int[] sjs = new int[2];
                    for (int i = 0; i < 2; i++) {
                        int x = (int) (Math.random() * 6);
                        sjs[i] = x;
                        //去掉相同的随机数
                        for (int j = 0; j <= i; j++) {
                            if ((i != j) && sjs[i] == sjs[j]) {
                                i--;
                            }
                        }
                        Log.i("sjs", x + "");
                    }
                    int b1 = sjs[0];
                    adapter1.chiceState(b1);
                    a1 = adapter1.i;
                    if (i1[b1] == list.get(b1)) {
                        i1[b1] = -1;
                    } else {
                        i1[b1] = list.get(b1);
                    }
                    showMoney();

                    int b2 = sjs[1];
                    adapter2.chiceState(b2);
                    a2 = adapter2.i;
                    if (i2[b2] == list2.get(b2)) {
                        i2[b2] = -1;
                    } else {
                        i2[b2] = list2.get(b2);
                    }
                    showMoney();
                } else if (po == 3) {//二同号复选
                    int x = 0;
                    //存放随机生成的数
//                    int[] sjs = new int[1];
                    for (int i = 0; i < 1; i++) {
                        x = (int) (Math.random() * 6);
//                        sjs[i] = x;
                        Log.i("sjs", x + "");
                    }
//                    int b1 = sjs[0];
                    adapter1.chiceState(x);
                    a1 = adapter1.i;
                    if (i1[x] == list.get(x)) {
                        i1[x] = -1;
                    } else {
                        i1[x] = list.get(x);
                    }
                    showMoney();
                } else if (po == 4) {//三不同号
                    //存放随机生成的数
                    int[] sjs = new int[3];
                    for (int i = 0; i < 3; i++) {
                        int x = (int) (Math.random() * 6);
                        sjs[i] = x;
                        for (int j = 0; j <= i; j++) {
                            if ((i != j) && sjs[i] == sjs[j]) {
                                i--;
                            }
                        }
                        Log.i("sjs", x + "");
                    }
                    for (int i = 0; i < sjs.length; i++) {
                        int b1 = sjs[i];
                        adapter1.chiceState(b1);
                        a1 = adapter1.i;
                        if (i1[b1] == list.get(b1)) {
                            i1[b1] = -1;
                        } else {
                            i1[b1] = list.get(b1);
                        }
                        showMoney();
                    }
                } else if (po == 5) {//二不同号
                    //存放随机生成的数
                    int[] sjs = new int[2];
                    for (int i = 0; i < 2; i++) {
                        int x = (int) (Math.random() * 6);
                        sjs[i] = x;
                        for (int j = 0; j <= i; j++) {
                            if ((i != j) && sjs[i] == sjs[j]) {
                                i--;
                            }
                        }
                        Log.i("sjs", x + "");
                    }
                    for (int i = 0; i < sjs.length; i++) {
                        int b1 = sjs[i];
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
     * 将选中的号码保存
     */
    private void setShared(List<Integer> hun, List<Integer> ten) {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int time = sharedPreferences.getInt("time", 0);
        int item = sharedPreferences.getInt("item", 0);
        if (po == 0 || po == 1 || po == 3 || po == 4 || po == 5) {//和值//三同号//二同号复选//二不同号
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
        } else if (po == 2 || po == 6 || po == 7) {//二同号单选
            if (item >= 0) {
                editor.putInt("hunsize" + item, hun.size());
                for (int i = 0; i < hun.size(); i++) {
                    editor.putInt("hun" + item + i, hun.get(i));
                }
                editor.putInt("tensize" + item, ten.size());
                for (int i = 0; i < ten.size(); i++) {
                    editor.putInt("ten" + item + i, ten.get(i));
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

        int item = sharedPreferences.getInt("item", 0);

        int hunsize = sharedPreferences.getInt("hunsize" + item, 0);//百位的个数
        for (int j = 0; j < hunsize; j++) {
            hunlist.add(sharedPreferences.getInt("hun" + item + j, 0));
        }
        int tensize = sharedPreferences.getInt("tensize" + item, 0);//十位的个数
        for (int j = 0; j < tensize; j++) {
            tenlist.add(sharedPreferences.getInt("ten" + item + j, 0));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hun", hunlist);
        map.put("ten", tenlist);
        return map;
    }

    /**
     * 快三分类玩法弹出框
     */
    private void showXingBieDuiHuaKuang() {
        adapter = new OneGridViewAdapter(data2(), OneFastThree.this);
        adapter.setSeclection(po);

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(OneFastThree.this).inflate(
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
                if (position == 0) {//和值
                    clear();
                    linearlayout.setVisibility(View.GONE);
                    list = new ArrayList<Integer>();
                    for (int i = 4; i <= 17; i++) {
                        list.add(i);
                    }
                    tv_hun.setText("选号");
                    adapter1 = new FastThreeAdapter1(list, OneFastThree.this);
                    hun_gridview.setAdapter(adapter1);
                    tv_text.setText("猜开奖号码相加的和");

                } else if (position == 1) {//三同号
                    clear();
                    tv_hun.setText("三同号");
                    linearlayout.setVisibility(View.GONE);
                    list = new ArrayList<Integer>();
                    list.add(1);
                    list.add(2);
                    list.add(3);
                    list.add(4);
                    list.add(5);
                    list.add(6);
                    adapter1 = new FastThreeAdapter1(list, OneFastThree.this);
                    hun_gridview.setAdapter(adapter1);
                    tv_text.setText("猜豹子号");
                } else if (position == 2) {//二同号单选
                    clear();
                    linearlayout.setVisibility(View.VISIBLE);
                    tv_hun.setText("相同号");
                    tv_ten.setText("不同号");
                    list = new ArrayList<Integer>();
                    list.add(1);
                    list.add(2);
                    list.add(3);
                    list.add(4);
                    list.add(5);
                    list.add(6);
                    adapter1 = new FastThreeAdapter1(list, OneFastThree.this);
                    hun_gridview.setAdapter(adapter1);
                    tv_text.setText("猜对子号（有两个号相同）");

                    list2 = new ArrayList<Integer>();
                    list2.add(1);
                    list2.add(2);
                    list2.add(3);
                    list2.add(4);
                    list2.add(5);
                    list2.add(6);
                    adapter2 = new FastThreeAdapter2(list2, OneFastThree.this);
                    ten_gridview.setAdapter(adapter2);

                } else if (position == 3) {//二同号复选
                    clear();
                    linearlayout.setVisibility(View.GONE);
                    list = new ArrayList<Integer>();
                    list.add(1);
                    list.add(2);
                    list.add(3);
                    list.add(4);
                    list.add(5);
                    list.add(6);
                    adapter1 = new FastThreeAdapter1(list, OneFastThree.this);
                    hun_gridview.setAdapter(adapter1);
                    tv_text.setText("猜对子号（有两个号相同）");
                    tv_hun.setText("二同号");


                } else if (position == 4) {//三不同号
                    clear();
                    linearlayout.setVisibility(View.GONE);
                    list = new ArrayList<Integer>();
                    list.add(1);
                    list.add(2);
                    list.add(3);
                    list.add(4);
                    list.add(5);
                    list.add(6);
                    adapter1 = new FastThreeAdapter1(list, OneFastThree.this);
                    hun_gridview.setAdapter(adapter1);
                    tv_text.setText("猜开奖的三个不同的号码");
                    tv_hun.setText("选号");

                } else if (position == 5) {//二不同号
                    clear();
                    linearlayout.setVisibility(View.GONE);
                    list = new ArrayList<Integer>();
                    list.add(1);
                    list.add(2);
                    list.add(3);
                    list.add(4);
                    list.add(5);
                    list.add(6);
                    adapter1 = new FastThreeAdapter1(list, OneFastThree.this);
                    hun_gridview.setAdapter(adapter1);
                    tv_text.setText("猜开奖号码中2个不同的号码");
                    tv_hun.setText("选号");

                } else if (position == 6) {//三不同号胆拖
                    clear();
                    linearlayout.setVisibility(View.VISIBLE);
                    tv_choose.setVisibility(View.GONE);
                    tv_hun.setText("胆码");
                    tv_ten.setText("拖码");
                    list = new ArrayList<Integer>();
                    list.add(1);
                    list.add(2);
                    list.add(3);
                    list.add(4);
                    list.add(5);
                    list.add(6);
                    adapter1 = new FastThreeAdapter1(list, OneFastThree.this);
                    hun_gridview.setAdapter(adapter1);
                    tv_text.setText("猜3个不同的号码（1~2个胆码，2~5个拖码）");

                    list2 = new ArrayList<Integer>();
                    list2.add(1);
                    list2.add(2);
                    list2.add(3);
                    list2.add(4);
                    list2.add(5);
                    list2.add(6);
                    adapter2 = new FastThreeAdapter2(list2, OneFastThree.this);
                    ten_gridview.setAdapter(adapter2);
                } else if (position == 7) {//二不同号胆拖
                    clear();
                    linearlayout.setVisibility(View.VISIBLE);
                    tv_choose.setVisibility(View.GONE);
                    tv_hun.setText("胆码");
                    tv_ten.setText("拖码");
                    list = new ArrayList<Integer>();
                    list.add(1);
                    list.add(2);
                    list.add(3);
                    list.add(4);
                    list.add(5);
                    list.add(6);
                    adapter1 = new FastThreeAdapter1(list, OneFastThree.this);
                    hun_gridview.setAdapter(adapter1);
                    tv_text.setText("猜2个不同的号码（1个胆码，2~5个拖码）");

                    list2 = new ArrayList<Integer>();
                    list2.add(1);
                    list2.add(2);
                    list2.add(3);
                    list2.add(4);
                    list2.add(5);
                    list2.add(6);
                    adapter2 = new FastThreeAdapter2(list2, OneFastThree.this);
                    ten_gridview.setAdapter(adapter2);
                }
                popupWindow.dismiss();
                iv_top.setImageResource(R.mipmap.top_2);
                b = true;
            }
        });
    }

    /**
     * 快三分类玩法数据源
     *
     * @return
     */
    private List<Map<String, String>> data2() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("text", "和值");
        list.add(map);
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("text", "三同号");
        list.add(map1);
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("text", "二同号单选");
        list.add(map2);
        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("text", "二同号复选");
        list.add(map3);
        Map<String, String> map4 = new HashMap<String, String>();
        map4.put("text", "三不同号");
        list.add(map4);
        Map<String, String> map5 = new HashMap<String, String>();
        map5.put("text", "二不同号");
        list.add(map5);
        Map<String, String> map6 = new HashMap<String, String>();
        map6.put("text", "三不同号胆拖");
        list.add(map6);
        Map<String, String> map7 = new HashMap<String, String>();
        map7.put("text", "二不同号胆拖");
        list.add(map7);


        return list;
    }

    /**
     * 清楚选中号码
     */
    private void clear() {
        if (po == 0 || po == 1 || po == 3 || po == 4 || po == 5) {//和值、三同号、二同号复选、三不同号、二不同号
            for (int c = 0; c < i1.length; c++) {
                if (i1[c] != -1) {
                    adapter1.chiceState(c);
                    a1 = adapter1.i;
                    i1[c] = -1;
                }
            }
        } else if (po == 2 || po == 6 || po == 7) {//二同号单选、三不同号胆拖、二不同号胆拖
            for (int c = 0; c < i1.length; c++) {
                if (i1[c] != -1) {
                    adapter1.chiceState(c);
                    a1 = adapter1.i;
                    i1[c] = -1;
                }
            }
            for (int c = 0; c < i2.length; c++) {
                if (i2[c] != -1) {
                    adapter2.chiceState(c);
                    a2 = adapter2.i;
                    i2[c] = -1;
                }
            }
        }
        a1 = 0;
        a2 = 0;
        tv_num.setText("共" + 0 + "注" + 0 + "元");
    }

}
