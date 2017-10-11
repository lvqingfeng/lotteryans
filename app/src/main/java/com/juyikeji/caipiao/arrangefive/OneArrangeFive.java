package com.juyikeji.caipiao.arrangefive;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.activity.OneLotteryBetPay;
import com.juyikeji.caipiao.activity.OneLotteryBetPayx;
import com.juyikeji.caipiao.adapter.FragmentOneKJXXAdapter;
import com.juyikeji.caipiao.fastthree.OneFastThree;
import com.juyikeji.caipiao.utils.URLConnectionUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//排列五
public class OneArrangeFive extends Activity implements View.OnClickListener {
    private GridView gridview1, gridview2, gridview3, gridview4, gridview5;
    private ImageView fanhui, iv_xl;
    //数据源
    List<Integer> list;
    //排列五每位的adapter，分别是万位、千位、百位、十位、个位
    private ArrangFiveAdapter1 adapter1;
    private ArrangFiveAdapter2 adapter2;
    private ArrangFiveAdapter3 adapter3;
    private ArrangFiveAdapter4 adapter4;
    private ArrangFiveAdapter5 adapter5;
    //用来保存每位选择的号码的个数，依次是万位，千位，百位，十位，个位
    private int a1 = 0, a2 = 0, a3 = 0, a4 = 0, a5 = 0;

    //用来保存选择的号码，依次是万位，千位，百位，十位，个位
    int i1[], i2[], i3[], i4[], i5[];
    private TextView tv_num;
    private Button btn_clear;
    private TextView tv_choose,bt_next;
    //存放随机生成的数
    private int[] sjs = new int[5];
    //保存选中的号码的集合
    private List<List<Integer>> listlist;

    //开奖日期
    String kjrq="0";
    //停售日期
    private String pausetime="";

    private LinearLayout linout1;
    private TextView tv_time;
    private ListView lv;
    //获取往期中奖信息的网络接口
    private String name_space = "center/getwinnum";
    String result = "";
    private FragmentOneKJXXAdapter fradapter;
    private List<String[]> listis;
    private boolean zst = false;//选择走势图下拉的标识

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_arrange_five);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!URLConnectionUtil.isOpenNetwork(this)){
            Toast.makeText(this,"当前网络不可用，请打开网络",Toast.LENGTH_SHORT).show();
        }
        init();
        showLotter();
        clear();
        listlist = new ArrayList<List<Integer>>();

        List<Integer> wanlist = (List<Integer>) getSharedPreferences().get("wan");
        for (int c = 0; c < wanlist.size(); c++) {
            adapter1.chiceState(wanlist.get(c));
            a1 = adapter1.i;
            i1[wanlist.get(c)] = list.get(wanlist.get(c));
            showMoney();
        }
        List<Integer> qianlist = (List<Integer>) getSharedPreferences().get("qian");
        for (int c = 0; c < qianlist.size(); c++) {
            adapter2.chiceState(qianlist.get(c));
            a2 = adapter2.i;
            i2[qianlist.get(c)] = list.get(qianlist.get(c));
            showMoney();
        }

        List<Integer> hunlist = (List<Integer>) getSharedPreferences().get("hun");
        for (int c = 0; c < hunlist.size(); c++) {
            adapter3.chiceState(hunlist.get(c));
            a3 = adapter3.i;
            i3[hunlist.get(c)] = list.get(hunlist.get(c));
            showMoney();
        }
        List<Integer> tenlist = (List<Integer>) getSharedPreferences().get("ten");
        for (int c = 0; c < tenlist.size(); c++) {
            adapter4.chiceState(tenlist.get(c));
            a4 = adapter4.i;
            i4[tenlist.get(c)] = list.get(tenlist.get(c));
            showMoney();
        }
        List<Integer> indlist = (List<Integer>) getSharedPreferences().get("ind");
        for (int c = 0; c < indlist.size(); c++) {
            adapter5.chiceState(indlist.get(c));
            a5 = adapter5.i;
            i5[indlist.get(c)] = list.get(indlist.get(c));
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
                            fradapter = new FragmentOneKJXXAdapter(OneArrangeFive.this, listis, 10);
                            lv.setAdapter(fradapter);
                        } else {
                            Toast.makeText(OneArrangeFive.this, msg2, Toast.LENGTH_SHORT).show();
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
                map.put("lotterytype", "pl5");
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
     * 实例化控件及设置其监听事件
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
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        bt_next = (TextView) findViewById(R.id.bt_next);
        bt_next.setOnClickListener(this);
        tv_choose = (TextView) findViewById(R.id.tv_choose);
        tv_choose.setOnClickListener(this);
        tv_num = (TextView) findViewById(R.id.tv_num);
        gridview1 = (GridView) findViewById(R.id.gridview1);
        gridview2 = (GridView) findViewById(R.id.gridview2);
        gridview3 = (GridView) findViewById(R.id.gridview3);
        gridview4 = (GridView) findViewById(R.id.gridview4);
        gridview5 = (GridView) findViewById(R.id.gridview5);

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
        i4 = new int[10];
        for (int a = 0; a <= 9; a++) {
            i4[a] = -1;
        }
        i5 = new int[10];
        for (int a = 0; a <= 9; a++) {
            i5[a] = -1;
        }

        adapter1 = new ArrangFiveAdapter1(list, OneArrangeFive.this);
        gridview1.setAdapter(adapter1);
        gridview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter1.chiceState(position);
                a1 = adapter1.i;
                Log.i("a1", a1 + "");
                if (i1[position] == -1) {
                    i1[position] = list.get(position);
                } else {
                    i1[position] = -1;
                }
                showMoney();
            }
        });

        adapter2 = new ArrangFiveAdapter2(list, OneArrangeFive.this);
        gridview2.setAdapter(adapter2);
        gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter2.chiceState(position);
                a2 = adapter2.i;
                Log.i("a1", a2 + "");
                if (i2[position] == -1) {
                    i2[position] = list.get(position);
                } else {
                    i2[position] = -1;
                }
                showMoney();
            }
        });

        adapter3 = new ArrangFiveAdapter3(list, OneArrangeFive.this);
        gridview3.setAdapter(adapter3);
        gridview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter3.chiceState(position);
                a3 = adapter3.i;
                Log.i("a1", a1 + "");
                if (i3[position] == -1) {
                    i3[position] = list.get(position);
                } else {
                    i3[position] = -1;
                }
                showMoney();
            }
        });

        adapter4 = new ArrangFiveAdapter4(list, OneArrangeFive.this);
        gridview4.setAdapter(adapter4);
        gridview4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter4.chiceState(position);
                a4 = adapter4.i;
                Log.i("a1", a4 + "");
                if (i4[position] == -1) {
                    i4[position] = list.get(position);
                } else {
                    i4[position] = -1;
                }
                showMoney();
            }
        });

        adapter5 = new ArrangFiveAdapter5(list, OneArrangeFive.this);
        gridview5.setAdapter(adapter5);
        gridview5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter5.chiceState(position);
                a5 = adapter5.i;
                Log.i("a1", a5 + "");
                if (i5[position] == -1) {
                    i5[position] = list.get(position);
                } else {
                    i5[position] = -1;
                }
                showMoney();
            }
        });
    }

    /**
     * 跟新UI,显示选择了多少注共多少钱
     */
    private void showMoney() {
        tv_num.setText("共" + a1 * a2 * a3 * a4 * a5 + "注" + a1 * a2 * a3 * a4 * a5 * 2 + "元");
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
            //清除
            case R.id.btn_clear:
                clear();
                break;
            //机选
            case R.id.tv_choose:
                clear();
                for (int i = 0; i < 5; i++) {
                    int x = (int) (Math.random() * 10);
                    sjs[i] = x;
                    Log.i("sjs", x + "");
                }
                //万位
                int b1 = sjs[0];
                adapter1.chiceState(b1);
                a1 = adapter1.i;
                if (i1[b1] == list.get(b1)) {
                    i1[b1] = -1;
                } else {
                    i1[b1] = list.get(b1);
                }
                showMoney();

                //千位
                int b2 = sjs[1];
                adapter2.chiceState(b2);
                a2 = adapter2.i;
                if (i2[b2] == list.get(b2)) {
                    i2[b2] = -1;
                } else {
                    i2[b2] = list.get(b2);
                }
                showMoney();

                //百位
                int b3 = sjs[2];
                adapter3.chiceState(b3);
                a3 = adapter3.i;
                if (i3[b3] == list.get(b3)) {
                    i3[b3] = -1;
                } else {
                    i3[b3] = list.get(b3);
                }
                showMoney();

                //十位
                int b4 = sjs[3];
                adapter4.chiceState(b4);
                a4 = adapter4.i;
                if (i4[b4] == list.get(b4)) {
                    i4[b4] = -1;
                } else {
                    i4[b4] = list.get(b4);
                }
                showMoney();

                //个位
                int b5 = sjs[4];
                adapter5.chiceState(b5);
                a5 = adapter5.i;
                if (i5[b5] == list.get(b5)) {
                    i5[b5] = -1;
                } else {
                    i5[b5] = list.get(b5);
                }
                showMoney();
                break;
            //下一步
            case R.id.bt_next:
                if (a1 < 1 || a2 < 1 || a3 < 1 || a4 < 1 || a5 < 1) {
                    Toast.makeText(OneArrangeFive.this, "每位最少选择一个号", Toast.LENGTH_SHORT).show();
                } else {
                    List<Integer> li1 = new ArrayList<Integer>();
                    for (int c = 0; c < i1.length; c++) {
                        if (i1[c] != -1) {
                            li1.add(i1[c]);
                        }
                    }
                    listlist.add(li1);
                    List<Integer> li2 = new ArrayList<Integer>();
                    for (int c = 0; c < i2.length; c++) {
                        if (i2[c] != -1) {
                            li2.add(i2[c]);
                        }
                    }
                    listlist.add(li2);
                    List<Integer> li3 = new ArrayList<Integer>();
                    for (int c = 0; c < i3.length; c++) {
                        if (i3[c] != -1) {
                            li3.add(i3[c]);
                        }
                    }
                    listlist.add(li3);
                    List<Integer> li4 = new ArrayList<Integer>();
                    for (int c = 0; c < i4.length; c++) {
                        if (i4[c] != -1) {
                            li4.add(i4[c]);
                        }
                    }
                    listlist.add(li4);
                    List<Integer> li5 = new ArrayList<Integer>();
                    for (int c = 0; c < i5.length; c++) {
                        if (i5[c] != -1) {
                            li5.add(i5[c]);
                        }
                    }
                    setShared(li1, li2, li3, li4, li5);
                    Intent intent = new Intent(this, OneLotteryBetPayx.class);
                    intent.putExtra("彩种", 10);
                    intent.putExtra("pausetime",pausetime);
                    intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 将选中的号码保存
     */
    private void setShared(List<Integer> wan, List<Integer> qian, List<Integer> hun, List<Integer> ten, List<Integer> ind) {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int time = sharedPreferences.getInt("time", 0);
        int item = sharedPreferences.getInt("item", 0);
        if (item >= 0) {
            editor.putInt("wansize" + item, wan.size());
            for (int i = 0; i < wan.size(); i++) {
                editor.putInt("wan" + item + i, wan.get(i));
            }
            editor.putInt("qiansize" + item, qian.size());
            for (int i = 0; i < qian.size(); i++) {
                editor.putInt("qian" + item + i, qian.get(i));
            }

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
            editor.putInt("wansize" + time, wan.size());
            for (int i = 0; i < wan.size(); i++) {
                editor.putInt("wan" + time + i, wan.get(i));
            }
            editor.putInt("qiansize" + time, qian.size());
            for (int i = 0; i < qian.size(); i++) {
                editor.putInt("qian" + time + i, qian.get(i));
            }
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
        editor.commit();
    }

    /**
     * 清楚选中号码
     */
    private void clear() {
        //万位
        for (int c = 0; c < i1.length; c++) {
            if (i1[c] != -1) {
                adapter1.chiceState(c);
                a1 = adapter1.i;
                i1[c] = -1;
                showMoney();
            }
        }
        //千位
        for (int c = 0; c < i2.length; c++) {
            if (i2[c] != -1) {
                adapter2.chiceState(c);
                a2 = adapter2.i;
                i2[c] = -1;
                showMoney();
            }
        }
        //百位
        for (int c = 0; c < i3.length; c++) {
            if (i3[c] != -1) {
                adapter3.chiceState(c);
                a3 = adapter3.i;
                i3[c] = -1;
                showMoney();
            }
        }
        //十位
        for (int c = 0; c < i4.length; c++) {
            if (i4[c] != -1) {
                adapter4.chiceState(c);
                a4 = adapter3.i;
                i4[c] = -1;
                showMoney();
            }
        }
        //个位
        for (int c = 0; c < i5.length; c++) {
            if (i5[c] != -1) {
                adapter5.chiceState(c);
                a5 = adapter5.i;
                i5[c] = -1;
                showMoney();
            }
        }
        a1 = 0;
        a2 = 0;
        a3 = 0;
        Log.i("a123", a1 + " " + a2 + " " + a3);
        tv_num.setText("共" + 0 + "注" + 0 + "元");
    }

    /**
     * 获取shared中的号码
     */
    private Map<String, Object> getSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        //万位的集合
        List<Integer> wanlist = new ArrayList<Integer>();
        //千位的集合
        List<Integer> qianlist = new ArrayList<Integer>();
        //百位的集合
        List<Integer> hunlist = new ArrayList<Integer>();
        //十位的集合
        List<Integer> tenlist = new ArrayList<Integer>();
        //个位的集合
        List<Integer> indlist = new ArrayList<Integer>();

        int item = sharedPreferences.getInt("item", 0);
        int wansize = sharedPreferences.getInt("wansize" + item, 0);//万位的个数
        for (int j = 0; j < wansize; j++) {
            wanlist.add(sharedPreferences.getInt("wan" + item + j, 0));
        }
        int qiansize = sharedPreferences.getInt("qiansize" + item, 0);//千位的个数
        for (int j = 0; j < qiansize; j++) {
            qianlist.add(sharedPreferences.getInt("qian" + item + j, 0));
        }
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
        map.put("wan", wanlist);
        map.put("qian", qianlist);
        map.put("hun", hunlist);
        map.put("ten", tenlist);
        map.put("ind", indlist);
        return map;
    }
}
