package com.juyikeji.caipiao.directly;

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

//排列三
public class OneDirectly extends Activity implements View.OnClickListener {
    //百位，十位，个位
    private GridView hun_gridview, ten_gridview, ind_gridview;
    //下一步
    private TextView bt_next;
    //用来保存选择的号码，依次是百位，十位，个位，直选三和
    int i[], i1[], i2[], i11[];
    //多少注多少钱
    private TextView tv_num;
    //用来保存每位选择的号码的个数，依次是百位，十位，个位
    private int a1 = 0, a2 = 0, a3 = 0;
    //数据源
    List<Integer> list;
    List<Integer> list2;
    //依次是百位，十位，个位的adapter
    DirectlyAdapter1 directlyAdapter1;
    DirectlyAdapter2 directlyAdapter2;
    DirectlyAdapter3 directlyAdapter3;
    //机选
    private TextView tv_choose;
    private TextView tv_bt;
    //删除
    private Button btn_clear;
    //存放随机生成的数
    private int[] sjs = new int[3];
    private int[] sjs2 = new int[28];

    private RelativeLayout layout;
    //弹出框对象
    private PopupWindow popupWindow;
    private boolean b = true;
    GridView gridview;
    //记录选择的玩法
    private int po = 0;
    private OneGridViewAdapter adapter;
    //百位布局，十位，个位
    private LinearLayout hun_linearlayout, ten_linearlayout, ind_linearlayout;
    private TextView tv_hun, tv_ten, tv_text;
    private View view_view;

    private ImageView iv_top, fanhui, iv_xl;
    private LinearLayout rl_text;
    //保存选择的号码
    private List<List<Integer>> listlist;

    List<Integer> li1, li2, li3;


    private LinearLayout linout1;
    private TextView tv_time;
    private ListView lv;
    //获取往期中奖信息的网络接口
    private String name_space = "center/getwinnum";
    String result = "";
    private FragmentOneKJXXAdapter adapter1;
    private List<String[]> listis;
    private boolean zst = false;//选择走势图下拉的标识
    //开奖日期
    private String kjrq = "0";
    //停售日期
    private String pausetime="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directly);

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

        if (po == 4) {
            directlyAdapter1 = new DirectlyAdapter1(list2, OneDirectly.this);
            hun_gridview.setAdapter(directlyAdapter1);
        }

        List<Integer> hunlist = (List<Integer>) getSharedPreferences().get("hun");
        for (int c = 0; c < hunlist.size(); c++) {
            directlyAdapter1.chiceState(hunlist.get(c), po);
            a1 = directlyAdapter1.i;
            if (po != 4) {
                i[hunlist.get(c)] = list.get(hunlist.get(c));
            } else {
                i11[hunlist.get(c)] = list2.get(hunlist.get(c));
            }
            showMoney();
        }
        List<Integer> tenlist = (List<Integer>) getSharedPreferences().get("ten");
        for (int c = 0; c < tenlist.size(); c++) {
            directlyAdapter2.chiceState(tenlist.get(c), po);
            a2 = directlyAdapter2.i;
            i1[tenlist.get(c)] = list.get(tenlist.get(c));
            showMoney();
        }
        List<Integer> indlist = (List<Integer>) getSharedPreferences().get("ind");
        for (int c = 0; c < indlist.size(); c++) {
            directlyAdapter3.chiceState(indlist.get(c), po);
            a3 = directlyAdapter3.i;
            i2[indlist.get(c)] = list.get(indlist.get(c));
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
                            adapter1 = new FragmentOneKJXXAdapter(OneDirectly.this, listis, 5);
                            lv.setAdapter(adapter1);
                        } else {
                            Toast.makeText(OneDirectly.this, msg2, Toast.LENGTH_SHORT).show();
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
                map.put("lotterytype", "pl3");
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
     * 实例化控件
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
        iv_top = (ImageView) findViewById(R.id.iv_top);
        rl_text = (LinearLayout) findViewById(R.id.rl_text);
        rl_text.setOnClickListener(this);
        layout = (RelativeLayout) findViewById(R.id.layout);
        tv_num = (TextView) findViewById(R.id.tv_num);
        bt_next = (TextView) findViewById(R.id.bt_next);
        bt_next.setOnClickListener(this);
        tv_choose = (TextView) findViewById(R.id.tv_choose);
        tv_choose.setOnClickListener(this);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        tv_bt = (TextView) findViewById(R.id.tv_bt);

        hun_gridview = (GridView) findViewById(R.id.hun_gridview);
        ten_gridview = (GridView) findViewById(R.id.ten_gridview);
        ind_gridview = (GridView) findViewById(R.id.ind_gridview);

        //设置数据源
        list = new ArrayList<Integer>();
        for (int i = 0; i <= 9; i++) {
            list.add(i);
        }
        list2 = new ArrayList<Integer>();
        for (int i = 0; i <= 27; i++) {
            list2.add(i);
        }

        directlyAdapter1 = new DirectlyAdapter1(list, OneDirectly.this);
        directlyAdapter2 = new DirectlyAdapter2(list, OneDirectly.this);
        directlyAdapter3 = new DirectlyAdapter3(list, OneDirectly.this);
        hun_gridview.setAdapter(directlyAdapter1);
        ten_gridview.setAdapter(directlyAdapter2);
        ind_gridview.setAdapter(directlyAdapter3);

        //初始化用来保存选择的号码
        i = new int[10];
        for (int a = 0; a <= 9; a++) {
            i[a] = -1;
        }
        i1 = new int[10];
        for (int a = 0; a <= 9; a++) {
            i1[a] = -1;
        }
        i2 = new int[10];
        for (int a = 0; a <= 9; a++) {
            i2[a] = -1;
        }
        i11 = new int[28];
        for (int a = 0; a <= 27; a++) {
            i11[a] = -1;
        }

        hun_linearlayout = (LinearLayout) findViewById(R.id.hun_linearlayout);
        ten_linearlayout = (LinearLayout) findViewById(R.id.ten_linearlayout);
        ind_linearlayout = (LinearLayout) findViewById(R.id.ind_linearlayout);
        tv_hun = (TextView) findViewById(R.id.tv_hun);
        tv_ten = (TextView) findViewById(R.id.tv_ten);
        tv_text = (TextView) findViewById(R.id.tv_text);

        view_view = findViewById(R.id.view4);

        hun_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (po == 1) {//组三单式
                    //限制只能选一个
                    for (int c = 0; c < i.length; c++) {
                        if (i[c] != -1) {
                            directlyAdapter1.chiceState(c, po);
                            a1 = directlyAdapter1.i;
                            i[c] = -1;
                            showMoney();
                        }
                    }
                    //相同号与不同号不能相等
                    if (i1[position] != -1) {
                        directlyAdapter2.chiceState(position, po);
                        a2 = directlyAdapter2.i;
                        Log.i("a22222222", a2 + "");
                        Log.i("a22222222", a1 + "");
                        if (i1[position] == list.get(position)) {
                            i1[position] = -1;
                        } else {
                            i1[position] = list.get(position);
                        }
                        showMoney();
                    }
                    directlyAdapter1.chiceState(position, po);
                    a1 = directlyAdapter1.i;
                    for (int a = 0; a <= 9; a++) {
                        i[a] = -1;
                    }
                    i[position] = list.get(position);
                    showMoney();
                } else if (po == 4) {//直选三和
                    directlyAdapter1.chiceState(position, po);
                    a1 = directlyAdapter1.i;
                    if (i11[position] == list2.get(position)) {
                        i11[position] = -1;
                    } else {
                        i11[position] = list2.get(position);
                    }

                    showMoney();
                } else {//其他
                    directlyAdapter1.chiceState(position, po);
                    a1 = directlyAdapter1.i;
                    if (i[position] == list.get(position)) {
                        i[position] = -1;
                    } else {
                        i[position] = list.get(position);
                    }
                    showMoney();
                }
            }
        });
        ten_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                directlyAdapter2.chiceState(position, po);
                if (po == 1) {//组三单式
                    //只能选一个
                    for (int c = 0; c < i1.length; c++) {
                        if (i1[c] != -1) {
                            directlyAdapter2.chiceState(c, po);
                            a2 = directlyAdapter2.i;
                            i1[c] = -1;
                            showMoney();
                        }
                    }
                    //相同号与不同号不能相等
                    if (i[position] != -1) {
                        directlyAdapter1.chiceState(position, po);
                        a1 = directlyAdapter1.i;
                        Log.i("a22222222", a1 + "");
                        Log.i("a22222222", a2 + "");
                        if (i[position] == list.get(position)) {
                            i[position] = -1;
                        } else {
                            i[position] = list.get(position);
                        }
                        showMoney();
                    }
                    a2 = directlyAdapter2.i;
                    if (po == 1) {//组三单式
                        for (int a = 0; a <= 9; a++) {
                            i1[a] = -1;
                        }
                        i1[position] = list.get(position);
                    } else {
                        if (i1[position] == list.get(position)) {
                            i1[position] = -1;
                        } else {
                            i1[position] = list.get(position);
                        }
                    }
                    Log.i("int", i1[0] + " " + i1[1] + " " + i1[2] + " " + i1[3] + " " + i1[4] + " " + i1[5] + " " + i1[6] + " " + i1[7] + " " + i1[8] + " " + i1[9]);
                    Log.i("a2", a2 + "");
                    showMoney();
                } else {
                    a2 = directlyAdapter2.i;
                    if (po == 1) {//组三单式
                        for (int a = 0; a <= 9; a++) {
                            i1[a] = -1;
                        }
                        i1[position] = list.get(position);
                    } else {
                        if (i1[position] == list.get(position)) {
                            i1[position] = -1;
                        } else {
                            i1[position] = list.get(position);
                        }
                    }
                    showMoney();
                }
            }
        });
        ind_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                directlyAdapter3.chiceState(position, po);
                a3 = directlyAdapter3.i;

                if (i2[position] == list.get(position)) {
                    i2[position] = -1;
                } else {
                    i2[position] = list.get(position);
                }
                Log.i("a3", a3 + "");
                showMoney();
            }
        });

    }

    /**
     * 跟新UI,显示选择了多少注共多少钱
     */
    private void showMoney() {
        if (po != 0) {
            a3 = 1;
        }
        if (po == 2) {//组三复式
            tv_num.setText("共" + a1 * (a1 - 1) + "注" + a1 * (a1 - 1) * 2 + "元");
        } else if (po == 3) {//组选六
            switch (a1) {
                case 1:
                case 2:
                    tv_num.setText("共" + 0 + "注" + 0 + "元");
                    break;
                case 3:
                    tv_num.setText("共" + 1 + "注" + 1 * 2 + "元");
                    break;
                case 4:
                    tv_num.setText("共" + 4 + "注" + 4 * 2 + "元");
                    break;
                case 5:
                    tv_num.setText("共" + 10 + "注" + 10 * 2 + "元");
                    break;
                case 6:
                    tv_num.setText("共" + 20 + "注" + 20 * 2 + "元");
                    break;
                case 7:
                    tv_num.setText("共" + 35 + "注" + 35 * 2 + "元");
                    break;
                case 8:
                    tv_num.setText("共" + 56 + "注" + 56 * 2 + "元");
                    break;
                case 9:
                    tv_num.setText("共" + 84 + "注" + 84 * 2 + "元");
                    break;
                case 10:
                    tv_num.setText("共" + 120 + "注" + 120 * 2 + "元");
                    break;
            }
        } else if (po == 0) {//直选三
            tv_num.setText("共" + a1 * a2 * a3 + "注" + a1 * a2 * a3 * 2 + "元");
        } else if (po == 1) {//组三单式
            if (a1 == 1 && a2 == 1) {
                tv_num.setText("共" + a1 * a2 + "注" + a1 * a2 * 2 + "元");
            } else {
                tv_num.setText("共" + 0 + "注" + 0 + "元");
            }
        } else if (po == 4) {//组直选三和
            int d = 0;
            for (int q = 0; q < i11.length; q++) {
                if (i11[q] != -1) {
                    for (int a = 0; a < 10; a++) {
                        for (int b = 0; b < 10; b++) {
                            for (int c = 0; c < 10; c++) {
                                if (a + b + c == i11[q]) {
                                    d = d + 1;
                                }
                            }
                        }
                    }
                }
            }
            tv_num.setText("共" + d + "注" + 2 * d + "元");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.fanhui:
                finish();
                break;
            //下一步
            case R.id.bt_next:
                if (po == 0) {//直选三
                    if (a1 < 1 || a2 < 1 || a3 < 1) {
                        Toast.makeText(OneDirectly.this, "每位最少选择一个号", Toast.LENGTH_SHORT).show();
                    } else {
                        li1 = new ArrayList<Integer>();
                        for (int c = 0; c < i.length; c++) {
                            if (i[c] != -1) {
                                li1.add(i[c]);
                            }
                        }
                        listlist.add(li1);
                        li2 = new ArrayList<Integer>();
                        for (int c = 0; c < i1.length; c++) {
                            if (i1[c] != -1) {
                                li2.add(i1[c]);
                            }
                        }
                        listlist.add(li2);
                        li3 = new ArrayList<Integer>();
                        for (int c = 0; c < i2.length; c++) {
                            if (i2[c] != -1) {
                                li3.add(i2[c]);
                            }
                        }
                        setShared(li1, li2, li3);
                        Intent intent = new Intent(this, OneLotteryBetPayx.class);
                        intent.putExtra("彩种", 5);
                        intent.putExtra("分类", po);
                        intent.putExtra("pausetime",pausetime);
                        intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                        startActivity(intent);
                    }
                } else if (po == 1) {//组三单式
                    if (a1 < 1 || a2 < 1) {
                        Toast.makeText(OneDirectly.this, "每位最少选择一个号", Toast.LENGTH_SHORT).show();
                    } else {
                        li1 = new ArrayList<Integer>();
                        for (int c = 0; c < i.length; c++) {
                            if (i[c] != -1) {
                                li1.add(i[c]);
                            }
                        }
                        listlist.add(li1);
                        li2 = new ArrayList<Integer>();
                        for (int c = 0; c < i1.length; c++) {
                            if (i1[c] != -1) {
                                li2.add(i1[c]);
                            }
                        }
                        setShared(li1, li2, li3);
                        Intent intent = new Intent(this, OneLotteryBetPayx.class);
                        intent.putExtra("彩种", 5);
                        intent.putExtra("分类", po);
                        intent.putExtra("pausetime",pausetime);
                        intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                        startActivity(intent);
                    }
                } else if (po == 2) {//组三复式
                    if (a1 < 2) {
                        Toast.makeText(OneDirectly.this, "至少选择两个号", Toast.LENGTH_SHORT).show();
                    } else {
                        li1 = new ArrayList<Integer>();
                        for (int c = 0; c < i.length; c++) {
                            if (i[c] != -1) {
                                li1.add(i[c]);
                            }
                        }
                        setShared(li1, li2, li3);
                        Intent intent = new Intent(this, OneLotteryBetPayx.class);
                        intent.putExtra("彩种", 5);
                        intent.putExtra("分类", po);
                        intent.putExtra("pausetime",pausetime);
                        intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                        startActivity(intent);
                    }
                } else if (po == 3) {//组选六
                    if (a1 < 3) {
                        Toast.makeText(OneDirectly.this, "至少选择三个号", Toast.LENGTH_SHORT).show();
                    } else {
                        li1 = new ArrayList<Integer>();
                        for (int c = 0; c < i.length; c++) {
                            if (i[c] != -1) {
                                li1.add(i[c]);
                            }
                        }
                        setShared(li1, li2, li3);
                        Intent intent = new Intent(this, OneLotteryBetPayx.class);
                        intent.putExtra("彩种", 5);
                        intent.putExtra("分类", po);
                        intent.putExtra("pausetime",pausetime);
                        intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                        startActivity(intent);
                    }
                } else if (po == 4) {//直选三和值
                    if (a1 < 1) {
                        Toast.makeText(OneDirectly.this, "至少选择一个号", Toast.LENGTH_SHORT).show();
                    } else {
                        List<Integer> li1 = new ArrayList<Integer>();
                        for (int c = 0; c < i11.length; c++) {
                            if (i11[c] != -1) {
                                li1.add(i11[c]);
                            }
                        }
                        setShared(li1, li2, li3);
                        Intent intent = new Intent(this, OneLotteryBetPayx.class);
                        intent.putExtra("彩种", 5);
                        intent.putExtra("分类", po);
                        intent.putExtra("pausetime",pausetime);
                        intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                        startActivity(intent);
                    }
                }
                break;
            //机选
            case R.id.tv_choose:
                clear();
                if (po == 0) {//直选三
                    for (int i = 0; i < 3; i++) {
                        int x = (int) (Math.random() * 10);
                        sjs[i] = x;
                        Log.i("sjs", x + "");
                    }
                    //百位
                    int b1 = sjs[0];
                    directlyAdapter1.chiceState(b1, po);
                    a1 = directlyAdapter1.i;
                    if (i[b1] == list.get(b1)) {
                        i[b1] = -1;
                    } else {
                        i[b1] = list.get(b1);
                    }
                    Log.i("a2", a2 + "");
                    showMoney();

                    //十位
                    int b2 = sjs[1];
                    directlyAdapter2.chiceState(b2, po);
                    a2 = directlyAdapter2.i;
                    if (i1[b2] == list.get(b2)) {
                        i1[b2] = -1;
                    } else {
                        i1[b2] = list.get(b2);
                    }
                    Log.i("a2", a2 + "");
                    showMoney();

                    //个位
                    int b3 = sjs[2];
                    directlyAdapter3.chiceState(b3, po);
                    a3 = directlyAdapter3.i;
                    if (i2[b3] == list.get(b3)) {
                        i2[b3] = -1;
                    } else {
                        i2[b3] = list.get(b3);
                    }
                    Log.i("a2", a2 + "");
                    showMoney();
                } else if (po == 1) {//组三单式
                    for (int i = 0; i < 3; i++) {
                        int x = (int) (Math.random() * 10);
                        sjs[i] = x;
                        for (int j = 0; j <= i; j++) {
                            if ((i != j) && sjs[i] == sjs[j]) {
                                i--;
                            }
                        }
                        Log.i("sjs", x + "");
                    }
                    //百位
                    int b1 = sjs[0];
                    directlyAdapter1.chiceState(b1, po);
                    a1 = directlyAdapter1.i;
                    if (i[b1] == list.get(b1)) {
                        i[b1] = -1;
                    } else {
                        i[b1] = list.get(b1);
                    }
                    Log.i("a2", a2 + "");
                    showMoney();

                    //十位
                    int b2 = sjs[1];
                    directlyAdapter2.chiceState(b2, po);
                    a2 = directlyAdapter2.i;
                    if (i1[b2] == list.get(b2)) {
                        i1[b2] = -1;
                    } else {
                        i1[b2] = list.get(b2);
                    }
                    Log.i("a2", a2 + "");
                    showMoney();
                } else if (po == 2) {//组三复式
                    for (int i = 0; i < 2; i++) {
                        int x = (int) (Math.random() * 10);
                        sjs[i] = x;
                        for (int j = 0; j <= i; j++) {
                            //取消重复的随机数
                            if ((i != j) && sjs[i] == sjs[j]) {
                                i--;
                            }
                        }
                        Log.i("sjs", x + "");
                    }
                    for (int p = 0; p < 2; p++) {
                        int b1 = sjs[p];
                        directlyAdapter1.chiceState(b1, po);
                        a1 = directlyAdapter1.i;
                        if (i[b1] == list.get(b1)) {
                            i[b1] = -1;
                        } else {
                            i[b1] = list.get(b1);
                        }
                        showMoney();
                    }
                } else if (po == 3) {//组选六
                    for (int i = 0; i < 3; i++) {
                        int x = (int) (Math.random() * 10);
                        sjs[i] = x;
                        for (int j = 0; j <= i; j++) {

                            if ((i != j) && sjs[i] == sjs[j]) {
                                i--;
                            }
                        }
                        Log.i("sjs", x + "");
                    }
                    for (int p = 0; p < 3; p++) {
                        int b1 = sjs[p];
                        directlyAdapter1.chiceState(b1, po);
                        a1 = directlyAdapter1.i;
                        if (i[b1] == list.get(b1)) {
                            i[b1] = -1;
                        } else {
                            i[b1] = list.get(b1);
                        }
                        Log.i("a2", a2 + "");
                        showMoney();
                    }
                } else if (po == 4) {//直选三和
                    for (int i = 0; i < 1; i++) {
                        int x = (int) (Math.random() * 28);
                        sjs2[i] = x;

                        Log.i("sjs", x + "");
                    }
                    //百位
                    int b1 = sjs2[0];
                    directlyAdapter1.chiceState(b1, po);
                    a1 = directlyAdapter1.i;
                    if (i11[b1] == list2.get(b1)) {
                        i11[b1] = -1;
                    } else {
                        i11[b1] = list2.get(b1);
                    }
                    Log.i("a2", a1 + "");
                    showMoney();
                }

                break;
            //删除
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
        }

    }

    /**
     * 清楚选中号码
     */
    private void clear() {
        if (po != 4) {
            //百位
            for (int c = 0; c < i.length; c++) {
                if (i[c] != -1) {
                    directlyAdapter1.chiceState(c, po);
                    a1 = directlyAdapter1.i;
                    i[c] = -1;
                    showMoney();
                }
            }
            //十位
            for (int c = 0; c < i1.length; c++) {
                if (i1[c] != -1) {
                    directlyAdapter2.chiceState(c, po);
                    a2 = directlyAdapter2.i;
                    i1[c] = -1;
                    showMoney();
                }
            }
            //个位
            for (int c = 0; c < i2.length; c++) {
                if (i2[c] != -1) {
                    directlyAdapter3.chiceState(c, po);
                    a3 = directlyAdapter3.i;
                    i2[c] = -1;
                    showMoney();
                }
            }
        } else {
            for (int c = 0; c < i11.length; c++) {
                if (i11[c] != -1) {
                    directlyAdapter1.chiceState(c, po);
                    a1 = directlyAdapter1.i;
                    i11[c] = -1;
                    showMoney();
                }
            }
            for (int a = 0; a <= 27; a++) {
                i11[a] = -1;
            }
        }
        a1 = 0;
        a2 = 0;
        a3 = 0;
        Log.i("a123", a1 + " " + a2 + " " + a3);
        tv_num.setText("共" + 0 + "注" + 0 + "元");
    }

    /**
     * 弹出框
     */
    private void showXingBieDuiHuaKuang() {
        adapter = new OneGridViewAdapter(data2(), OneDirectly.this);
        adapter.setSeclection(po);

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(OneDirectly.this).inflate(
                R.layout.directly_classify, null);

        popupWindow = new PopupWindow(contentView,
                ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

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
                if (position == 0) {//直选三
                    clear();
                    ind_linearlayout.setVisibility(View.VISIBLE);
                    ten_linearlayout.setVisibility(View.VISIBLE);
                    view_view.setVisibility(View.VISIBLE);
                    tv_hun.setText("百位");
                    tv_ten.setText("十位");
                    directlyAdapter1 = new DirectlyAdapter1(list, OneDirectly.this);
                    hun_gridview.setAdapter(directlyAdapter1);
                } else if (position == 1) {//组三单式
                    clear();
                    ind_linearlayout.setVisibility(View.GONE);
                    ten_linearlayout.setVisibility(View.VISIBLE);
                    tv_hun.setText("相同号");
                    tv_ten.setText("不同号");
                    tv_text.setText("每位只能选一个");
                    directlyAdapter1 = new DirectlyAdapter1(list, OneDirectly.this);
                    hun_gridview.setAdapter(directlyAdapter1);

                } else if (position == 2) {//组三复式
                    clear();
                    view_view.setVisibility(View.GONE);
                    ind_linearlayout.setVisibility(View.GONE);
                    ten_linearlayout.setVisibility(View.GONE);
                    tv_hun.setText("选号");
                    tv_text.setText("至少选两个号");
                    tv_num.setText("共0注0元");
                    directlyAdapter1 = new DirectlyAdapter1(list, OneDirectly.this);
                    hun_gridview.setAdapter(directlyAdapter1);

                } else if (position == 3) {//组选六
                    clear();
                    view_view.setVisibility(View.GONE);
                    ind_linearlayout.setVisibility(View.GONE);
                    ten_linearlayout.setVisibility(View.GONE);
                    tv_hun.setText("选号");
                    tv_text.setText("至少选三个号");
                    directlyAdapter1 = new DirectlyAdapter1(list, OneDirectly.this);
                    hun_gridview.setAdapter(directlyAdapter1);

                } else if (position == 4) {//直选三和值
                    clear();
                    view_view.setVisibility(View.GONE);
                    ind_linearlayout.setVisibility(View.GONE);
                    ten_linearlayout.setVisibility(View.GONE);
                    tv_hun.setText("选号");
                    tv_text.setText("至少选一个号");
                    directlyAdapter1 = new DirectlyAdapter1(list2, OneDirectly.this);
                    hun_gridview.setAdapter(directlyAdapter1);

                }
                popupWindow.dismiss();
                iv_top.setImageResource(R.mipmap.top_2);
                b = true;
            }
        });
    }

    /**
     * 将选中的号码用SharedPreferences保存
     */
    private void setShared(List<Integer> hun, List<Integer> ten, List<Integer> ind) {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int time = sharedPreferences.getInt("time", 0);
        int item = sharedPreferences.getInt("item", 0);
        if (po == 0) {//直选三
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
        } else if (po == 1) {//组三单式
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
        } else if (po == 2 || po == 3 || po == 4) {//组三复式//组选六//直选三和
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

    //排列三分类玩法数据源
    private List<Map<String, String>> data2() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("text", "直选三");
        list.add(map);
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("text", "组三单式");
        list.add(map1);
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("text", "组三复式");
        list.add(map2);
        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("text", "组选六");
        list.add(map3);
        Map<String, String> map4 = new HashMap<String, String>();
        map4.put("text", "直选三和值");
        list.add(map4);

        return list;
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

}
