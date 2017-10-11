package com.juyikeji.caipiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.adapter.FragmentOneKJXXAdapter;
import com.juyikeji.caipiao.adapter.OneSwimmingAdapter;
import com.juyikeji.caipiao.adapter.OneXSqbAdapter;
import com.juyikeji.caipiao.fastthree.OneGridViewAdapter;
import com.juyikeji.caipiao.utils.SysApplication;
import com.juyikeji.caipiao.utils.URLConnectionUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 泳坛夺冠
 */
public class OneYongTanDuoGuan extends Activity implements View.OnClickListener {
    private RelativeLayout layout;
    private LinearLayout linearlayout, rszs_layout;
    private ImageView iv_top;
    private TextView tv_title, tv_money, tv_text, tv_choose, bt_next;
    //弹出框对象
    private PopupWindow popupWindow;
    //玩法的adapter
    private OneGridViewAdapter adapter1, adapter2, adapter3, adapter4, adapter5;
    //记录选择的玩法
    private int po1 = 0, po2 = 0, po3 = 0, po4 = 0, po5 = 0;
    private int c = 1;
    //用来保存选择的号码，依次是自由泳、仰泳、蛙泳、蝶泳
    int i1[], i2[], i3[], i4[];
    //用来保存每位选择的号码的个数，依次是自由泳、仰泳、蛙泳、蝶泳
    private int a1 = 0, a2 = 0, a3 = 0, a4 = 0;
    //存放随机生成的数
    private int[] sjs = new int[5];
    private int[] sjs2 = new int[2];
    private boolean b = true;

    private Button btn_close, btn_clear;
    private GridView gv1, gv2, gv3, gv4, gv_qb;
    private OneSwimmingAdapter swimmingadapter1, swimmingadapter2, swimmingadapter3, swimmingadapter4;
    //数据源
    List<Integer> list;

    private List<Integer> li1, li2, li3, li4;

    private TextView ziyouyong, yangyong, wayong, dieyong;
    private LinearLayout wayong_layout, dieyong_layout, yangyong_layout;
    private View wayong_view, dieyong_view, yangyong_view;

    private CheckBox cb_ziyouyong, cb_yangyong, cb_wayong, cb_dieyong;
    //复选框选中的个数
    private int cb = 0;
    private int po = 0;
    //记录是否被选中，0未选中，1选中
    private int z = 0, y = 0, w = 0, d = 0;
    private String s = "";


    private ImageView iv_xl;
    private LinearLayout linout1;
    private TextView tv_time;
    private ListView lv;
    //获取往期中奖信息的网络接口
    private String name_space = "center/getwinnum";
    String result = "";
    private FragmentOneKJXXAdapter adapter;
    private List<String[]> listis;
    private boolean zst = false;//选择走势图下拉的标识
    //开奖日期
    private String kjrq = "0";
    //停售日期
    private String pausetime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_one_yongtan);
        SysApplication.getInstance().addActivity(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!URLConnectionUtil.isOpenNetwork(this)) {
            Toast.makeText(this, "当前网络不可用，请打开网络", Toast.LENGTH_SHORT).show();
        }
        initView();
        showLotter();
        clear();
        z = 0;
        y = 0;
        w = 0;
        d = 0;
        s = "";

        List<Integer> ziyouyonglist = (List<Integer>) getSharedPreferences().get("ziyouyong");
        for (int c = 0; c < ziyouyonglist.size(); c++) {
            swimmingadapter1.chiceState(ziyouyonglist.get(c) - 1);
            a1 = swimmingadapter1.i;
            i1[ziyouyonglist.get(c) - 1] = list.get(ziyouyonglist.get(c) - 1);
            showMoney();
        }
        List<Integer> yangyonglist = (List<Integer>) getSharedPreferences().get("yangyong");
        for (int c = 0; c < yangyonglist.size(); c++) {
            swimmingadapter2.chiceState(yangyonglist.get(c) - 1);
            a2 = swimmingadapter2.i;
            i2[yangyonglist.get(c) - 1] = list.get(yangyonglist.get(c) - 1);
            showMoney();
        }
        List<Integer> wayonglist = (List<Integer>) getSharedPreferences().get("wayong");
        for (int c = 0; c < wayonglist.size(); c++) {
            swimmingadapter3.chiceState(wayonglist.get(c) - 1);
            a3 = swimmingadapter3.i;
            i3[wayonglist.get(c) - 1] = list.get(wayonglist.get(c) - 1);
            showMoney();
        }
        List<Integer> dieyonglist = (List<Integer>) getSharedPreferences().get("dieyong");
        for (int c = 0; c < dieyonglist.size(); c++) {
            swimmingadapter4.chiceState(dieyonglist.get(c) - 1);
            a4 = swimmingadapter4.i;
            i4[dieyonglist.get(c) - 1] = list.get(dieyonglist.get(c) - 1);
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
                            adapter = new FragmentOneKJXXAdapter(OneYongTanDuoGuan.this, listis, 3);
                            lv.setAdapter(adapter);
                        } else {
                            Toast.makeText(OneYongTanDuoGuan.this, msg2, Toast.LENGTH_SHORT).show();
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
                map.put("lotterytype", "sxrytdj");
                Log.i("result", map + "");
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
     * 实例化控件及设置监听事件
     */
    private void initView() {
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

        ziyouyong = (TextView) findViewById(R.id.ziyouyong);
        yangyong = (TextView) findViewById(R.id.yangyong);
        wayong = (TextView) findViewById(R.id.wayong);
        dieyong = (TextView) findViewById(R.id.dieyong);
        wayong_layout = (LinearLayout) findViewById(R.id.wayong_layout);
        dieyong_layout = (LinearLayout) findViewById(R.id.dieyong_layout);
        yangyong_layout = (LinearLayout) findViewById(R.id.yangyong_layout);
        dieyong_view = findViewById(R.id.dieyong_view);
        wayong_view = findViewById(R.id.wayong_view);
        yangyong_view = findViewById(R.id.yangyong_view);

        bt_next = (TextView) findViewById(R.id.bt_next);
        bt_next.setOnClickListener(this);
        tv_choose = (TextView) findViewById(R.id.tv_choose);
        tv_choose.setOnClickListener(this);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_text = (TextView) findViewById(R.id.tv_text);
        layout = (RelativeLayout) findViewById(R.id.layout);
        iv_top = (ImageView) findViewById(R.id.iv_top);
        tv_title = (TextView) findViewById(R.id.tv_title);
        rszs_layout = (LinearLayout) findViewById(R.id.rszs_layout);
        linearlayout = (LinearLayout) findViewById(R.id.linearlayout);
        linearlayout.setOnClickListener(this);
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        gv_qb = (GridView) findViewById(R.id.gv_qb);

        initcheckbox();

        //初始化用来保存选择的号码
        i1 = new int[8];
        for (int a = 0; a < 8; a++) {
            i1[a] = -1;
        }
        i2 = new int[8];
        for (int a = 0; a < 8; a++) {
            i2[a] = -1;
        }
        i3 = new int[8];
        for (int a = 0; a < 8; a++) {
            i3[a] = -1;
        }
        i4 = new int[8];
        for (int a = 0; a < 8; a++) {
            i4[a] = -1;
        }

        //设置数据源
        list = new ArrayList<Integer>();
        for (int i = 1; i <= 8; i++) {
            list.add(i);
        }

        gv1 = (GridView) findViewById(R.id.gv1);
        gv2 = (GridView) findViewById(R.id.gv2);
        gv3 = (GridView) findViewById(R.id.gv3);
        gv4 = (GridView) findViewById(R.id.gv4);

        swimmingadapter1 = new OneSwimmingAdapter(list, OneYongTanDuoGuan.this);
        gv1.setAdapter(swimmingadapter1);
        gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (c == 2 && po2 == 0) {
                    swimmingadapter1.chiceState(position);
                    a1 = swimmingadapter1.i;
                    //设置只能选一个胆码
                    if (a1 <= 1) {
                        if (i1[position] != -1) {
                            i1[position] = -1;
                        } else {
                            i1[position] = list.get(position);
                        }
                        showMoney();
                    } else if (a1 > 1) {
                        swimmingadapter1.chiceState(position);
                        a1 = swimmingadapter1.i;
                        i1[position] = -1;
                        return;
                    }
                } else if (c == 3) {
                    switch (po3) {
                        case 0://组选24单式
                        case 3://组选12单式
                        case 7://组选6单式
                        case 10://组选4单式
                            swimmingadapter1.chiceState(position);
                            a1 = swimmingadapter1.i;
                            //设置只能选一个胆码
                            if (a1 <= 1) {
                                if (i1[position] != -1) {
                                    i1[position] = -1;
                                } else {
                                    i1[position] = list.get(position);
                                }
                                showMoney();
                            } else if (a1 > 1) {
                                swimmingadapter1.chiceState(position);
                                a1 = swimmingadapter1.i;
                                i1[position] = -1;
                                return;
                            }
                            break;
                        case 1://组选24复式
                        case 4://组选12复式
                        case 8://组选6复式
                        case 11://组选4复式
                            swimmingadapter1.chiceState(position);
                            a1 = swimmingadapter1.i;
                            if (i1[position] != -1) {
                                i1[position] = -1;
                            } else {
                                i1[position] = list.get(position);
                            }
                            showMoney();
                            Log.i("a1", a1 + "");
                            break;
                        case 2://组24胆拖
                            //设置胆拖不相同
                            if (i2[position] != -1) {
                                swimmingadapter2.chiceState(position);
                                a2 = swimmingadapter2.i;
                                Log.i("a1", a2 + "");
                                if (i2[position] == list.get(position)) {
                                    i2[position] = -1;
                                } else {
                                    i2[position] = list.get(position);
                                }
                                showMoney();
                            }
                            swimmingadapter1.chiceState(position);
                            a1 = swimmingadapter1.i;
                            //设置只能选3个胆码
                            if (a1 <= 3) {
                                if (i1[position] != -1) {
                                    i1[position] = -1;
                                } else {
                                    i1[position] = list.get(position);
                                }
                                showMoney();
                            } else if (a1 > 3) {
                                swimmingadapter1.chiceState(position);
                                a1 = swimmingadapter1.i;
                                i1[position] = -1;
                                return;
                            }
                            break;
                        case 5://组选12胆拖
                            //设置胆拖不相同
                            if (i2[position] != -1) {
                                swimmingadapter2.chiceState(position);
                                a2 = swimmingadapter2.i;
                                Log.i("a1", a2 + "");
                                if (i2[position] == list.get(position)) {
                                    i2[position] = -1;
                                } else {
                                    i2[position] = list.get(position);
                                }
                                showMoney();
                            }
                            swimmingadapter1.chiceState(position);
                            a1 = swimmingadapter1.i;
                            //设置只能选2个胆码
                            if (a1 <= 2) {
                                if (i1[position] != -1) {
                                    i1[position] = -1;
                                } else {
                                    i1[position] = list.get(position);
                                }
                                showMoney();
                            } else if (a1 > 2) {
                                swimmingadapter1.chiceState(position);
                                a1 = swimmingadapter1.i;
                                i1[position] = -1;
                                return;
                            }
                            break;
                        case 6://组选12重胆拖
                        case 9://组选6胆拖
                        case 12://组选4胆拖
                        case 13://组选4重胆拖
                            //设置胆拖不相同
                            if (i2[position] != -1) {
                                swimmingadapter2.chiceState(position);
                                a2 = swimmingadapter2.i;
                                Log.i("a1", a2 + "");
                                if (i2[position] == list.get(position)) {
                                    i2[position] = -1;
                                } else {
                                    i2[position] = list.get(position);
                                }
                                showMoney();
                            }
                            swimmingadapter1.chiceState(position);
                            a1 = swimmingadapter1.i;
                            //设置只能选1个胆码
                            if (a1 <= 1) {
                                if (i1[position] != -1) {
                                    i1[position] = -1;
                                } else {
                                    i1[position] = list.get(position);
                                }
                                showMoney();
                            } else if (a1 > 1) {
                                swimmingadapter1.chiceState(position);
                                a1 = swimmingadapter1.i;
                                i1[position] = -1;
                                return;
                            }
                            break;
                    }

                } else if (c == 4) {//前三组选
                    switch (po4) {
                        case 0://前三组选组三
                        case 1://前三组选组六
                        case 2://任三组选组三
                        case 3://任三组选组六
                            swimmingadapter1.chiceState(position);
                            a1 = swimmingadapter1.i;
                            if (a1 <= 5) {
                                if (i1[position] != -1) {
                                    i1[position] = -1;
                                } else {
                                    i1[position] = list.get(position);
                                }
                                showMoney();
                            } else if (a1 > 5) {
                                swimmingadapter1.chiceState(position);
                                a1 = swimmingadapter1.i;
                                i1[position] = -1;
                                return;
                            }
                            break;

                    }
                } else if (c == 5) {//新玩法
                    switch (po5) {
                        case 0://组选三码全包
                        case 1://组选二码全包
                        case 3://重号全包
                            swimmingadapter1.chiceState(position);
                            a1 = swimmingadapter1.i;
                            if (i1[position] != -1) {
                                i1[position] = -1;
                            } else {
                                i1[position] = list.get(position);
                            }
                            showMoney();
                            Log.i("a1", a1 + "");
                            break;
                        case 2://选四全包
                            if (po == 0) {//选四不重全包
                                swimmingadapter1.chiceState(position);
                                a1 = swimmingadapter1.i;
                                //设置只能选4个
                                if (a1 <= 4) {
                                    if (i1[position] != -1) {
                                        i1[position] = -1;
                                    } else {
                                        i1[position] = list.get(position);
                                    }
                                    showMoney();
                                } else if (a1 > 4) {
                                    swimmingadapter1.chiceState(position);
                                    a1 = swimmingadapter1.i;
                                    i1[position] = -1;
                                    return;
                                }

                            } else if (po == 1) {//选四一对全包
                                if (i2[position] != -1) {
                                    swimmingadapter2.chiceState(position);
                                    a2 = swimmingadapter2.i;
                                    Log.i("a1", a2 + "");
                                    if (i2[position] == list.get(position)) {
                                        i2[position] = -1;
                                    } else {
                                        i2[position] = list.get(position);
                                    }
                                    showMoney();
                                }
                                swimmingadapter1.chiceState(position);
                                a1 = swimmingadapter1.i;
                                //设置只能选2个
                                if (a1 <= 1) {
                                    if (i1[position] != -1) {
                                        i1[position] = -1;
                                    } else {
                                        i1[position] = list.get(position);
                                    }
                                    showMoney();
                                } else if (a1 > 1) {
                                    swimmingadapter1.chiceState(position);
                                    a1 = swimmingadapter1.i;
                                    i1[position] = -1;
                                    return;
                                }

                            } else if (po == 2) {//选四两对全包
                                swimmingadapter1.chiceState(position);
                                a1 = swimmingadapter1.i;
                                //设置只能选2个
                                if (a1 <= 2) {
                                    if (i1[position] != -1) {
                                        i1[position] = -1;
                                    } else {
                                        i1[position] = list.get(position);
                                    }
                                    showMoney();
                                } else if (a1 > 2) {
                                    swimmingadapter1.chiceState(position);
                                    a1 = swimmingadapter1.i;
                                    i1[position] = -1;
                                    return;
                                }

                            } else if (po == 3) {//选四三条全包
                                if (i2[position] != -1) {
                                    swimmingadapter2.chiceState(position);
                                    a2 = swimmingadapter2.i;
                                    Log.i("a1", a2 + "");
                                    if (i2[position] == list.get(position)) {
                                        i2[position] = -1;
                                    } else {
                                        i2[position] = list.get(position);
                                    }
                                    showMoney();
                                }
                                swimmingadapter1.chiceState(position);
                                a1 = swimmingadapter1.i;
                                //设置只能选2个
                                if (a1 <= 2) {
                                    if (i1[position] != -1) {
                                        i1[position] = -1;
                                    } else {
                                        i1[position] = list.get(position);
                                    }
                                    showMoney();
                                } else if (a1 > 2) {
                                    swimmingadapter1.chiceState(position);
                                    a1 = swimmingadapter1.i;
                                    i1[position] = -1;
                                    return;
                                }
                            }
                            break;
                    }
                } else {
                    swimmingadapter1.chiceState(position);
                    a1 = swimmingadapter1.i;
                    if (i1[position] != -1) {
                        i1[position] = -1;
                    } else {
                        i1[position] = list.get(position);
                    }
                    showMoney();
                    Log.i("a1", a1 + "");
                }
            }
        });

        swimmingadapter2 = new OneSwimmingAdapter(list, OneYongTanDuoGuan.this);
        gv2.setAdapter(swimmingadapter2);
        gv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (c == 2 && po2 == 0) {
                    swimmingadapter2.chiceState(position);
                    a2 = swimmingadapter2.i;
                    //设置只能选一个胆码
                    if (a2 <= 1) {
                        if (i2[position] != -1) {
                            i2[position] = -1;
                        } else {
                            i2[position] = list.get(position);
                        }
                        showMoney();
                    } else if (a2 > 1) {
                        swimmingadapter2.chiceState(position);
                        a2 = swimmingadapter2.i;
                        i2[position] = -1;
                        return;
                    }
                } else if (c == 3) {
                    switch (po3) {
                        case 0://组选24单式
                        case 3://组选12单式
                        case 7://组选6单式
                        case 10://组选4单式
                            swimmingadapter2.chiceState(position);
                            a2 = swimmingadapter2.i;
                            //设置只能选一个胆码
                            if (a2 <= 1) {
                                if (i2[position] != -1) {
                                    i2[position] = -1;
                                } else {
                                    i2[position] = list.get(position);
                                }
                                showMoney();
                            } else if (a2 > 1) {
                                swimmingadapter2.chiceState(position);
                                a2 = swimmingadapter2.i;
                                i2[position] = -1;
                                return;
                            }
                            break;
                        case 2://组24胆拖
                        case 5://组选12胆拖
                        case 6://组选12重胆拖
                        case 9://组选6胆拖
                        case 12://组选4胆拖
                        case 13://组选4重胆拖
                            //设置胆拖不相同
                            if (i1[position] != -1) {
                                swimmingadapter1.chiceState(position);
                                a1 = swimmingadapter1.i;
                                Log.i("a1", a1 + "");
                                if (i1[position] == list.get(position)) {
                                    i1[position] = -1;
                                } else {
                                    i1[position] = list.get(position);
                                }
                                showMoney();
                            }
                            swimmingadapter2.chiceState(position);
                            a2 = swimmingadapter2.i;
                            if (i2[position] != -1) {
                                i2[position] = -1;
                            } else {
                                i2[position] = list.get(position);
                            }
                            showMoney();
                            break;

                    }

                } else if (c == 5) {//新玩法
                    if (po5 == 2) {//选四全包
                        if (po == 1) {//选四一对全包
                            if (i1[position] != -1) {
                                swimmingadapter1.chiceState(position);
                                a1 = swimmingadapter1.i;
                                Log.i("a1", a1 + "");
                                if (i1[position] == list.get(position)) {
                                    i1[position] = -1;
                                } else {
                                    i1[position] = list.get(position);
                                }
                                showMoney();
                            }
                            swimmingadapter2.chiceState(position);
                            a2 = swimmingadapter2.i;
                            //设置只能选2个
                            if (a2 <= 2) {
                                if (i2[position] != -1) {
                                    i2[position] = -1;
                                } else {
                                    i2[position] = list.get(position);
                                }
                                showMoney();
                            } else if (a2 > 2) {
                                swimmingadapter2.chiceState(position);
                                a2 = swimmingadapter2.i;
                                i2[position] = -1;
                                return;
                            }

                        } else if (po == 3) {//选四三条全包
                            if (i1[position] != -1) {
                                swimmingadapter1.chiceState(position);
                                a1 = swimmingadapter1.i;
                                Log.i("a1", a1 + "");
                                if (i1[position] == list.get(position)) {
                                    i1[position] = -1;
                                } else {
                                    i1[position] = list.get(position);
                                }
                                showMoney();
                            }
                            swimmingadapter2.chiceState(position);
                            a2 = swimmingadapter2.i;
                            //设置只能选2个
                            if (a2 <= 1) {
                                if (i2[position] != -1) {
                                    i2[position] = -1;
                                } else {
                                    i2[position] = list.get(position);
                                }
                                showMoney();
                            } else if (a2 > 1) {
                                swimmingadapter2.chiceState(position);
                                a2 = swimmingadapter2.i;
                                i2[position] = -1;
                                return;
                            }
                        }
                    }
                } else {
                    swimmingadapter2.chiceState(position);
                    a2 = swimmingadapter2.i;
                    if (i2[position] != -1) {
                        i2[position] = -1;
                    } else {
                        i2[position] = list.get(position);
                    }
                    showMoney();
                    Log.i("a1", a2 + "");
                }
            }
        });

        swimmingadapter3 = new OneSwimmingAdapter(list, OneYongTanDuoGuan.this);
        gv3.setAdapter(swimmingadapter3);
        gv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (c == 2 && po2 == 0) {
                    swimmingadapter3.chiceState(position);
                    a3 = swimmingadapter3.i;
                    //设置只能选一个胆码
                    if (a3 <= 1) {
                        if (i3[position] != -1) {
                            i3[position] = -1;
                        } else {
                            i3[position] = list.get(position);
                        }
                        showMoney();
                    } else if (a3 > 1) {
                        swimmingadapter3.chiceState(position);
                        a3 = swimmingadapter3.i;
                        i3[position] = -1;
                        return;
                    }
                } else if (c == 3) {
                    switch (po3) {
                        case 0://组选24单式
                        case 3://组选12单式
                        case 7://组选6单式
                        case 10://组选4单式
                            swimmingadapter3.chiceState(position);
                            a3 = swimmingadapter3.i;
                            //设置只能选一个胆码
                            if (a3 <= 1) {
                                if (i3[position] != -1) {
                                    i3[position] = -1;
                                } else {
                                    i3[position] = list.get(position);
                                }
                                showMoney();
                            } else if (a3 > 1) {
                                swimmingadapter3.chiceState(position);
                                a3 = swimmingadapter3.i;
                                i3[position] = -1;
                                return;
                            }
                            break;

                    }

                } else {
                    swimmingadapter3.chiceState(position);
                    a3 = swimmingadapter3.i;
                    if (i3[position] != -1) {
                        i3[position] = -1;
                    } else {
                        i3[position] = list.get(position);
                    }
                    showMoney();
                    Log.i("a1", a3 + "");
                }
            }
        });

        swimmingadapter4 = new OneSwimmingAdapter(list, OneYongTanDuoGuan.this);
        gv4.setAdapter(swimmingadapter4);
        gv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (c == 2 && po2 == 0) {
                    swimmingadapter4.chiceState(position);
                    a4 = swimmingadapter4.i;
                    //设置只能选一个胆码
                    if (a4 <= 1) {
                        if (i4[position] != -1) {
                            i4[position] = -1;
                        } else {
                            i4[position] = list.get(position);
                        }
                        showMoney();
                    } else if (a4 > 1) {
                        swimmingadapter4.chiceState(position);
                        a4 = swimmingadapter4.i;
                        i4[position] = -1;
                        return;
                    }
                } else if (c == 3) {
                    switch (po3) {
                        case 0://组选24单式
                        case 3://组选12单式
                        case 7://组选6单式
                        case 10://组选4单式
                            swimmingadapter4.chiceState(position);
                            a4 = swimmingadapter4.i;
                            //设置只能选一个胆码
                            if (a4 <= 1) {
                                if (i4[position] != -1) {
                                    i4[position] = -1;
                                } else {
                                    i4[position] = list.get(position);
                                }
                                showMoney();
                            } else if (a4 > 1) {
                                swimmingadapter4.chiceState(position);
                                a4 = swimmingadapter4.i;
                                i4[position] = -1;
                                return;
                            }
                            break;

                    }

                } else {
                    swimmingadapter4.chiceState(position);
                    a4 = swimmingadapter4.i;
                    if (i4[position] != -1) {
                        i4[position] = -1;
                    } else {
                        i4[position] = list.get(position);
                    }
                    showMoney();
                    Log.i("a1", a4 + "");
                }
            }
        });
    }

    /**
     * 跟新UI,显示选择了多少注共多少钱
     */
    private void showMoney() {
        if (c == 1) {//任选
            switch (po1) {
                case 0://任选一
                    tv_money.setText("共" + (a1 + a2 + a3 + a4) + "注" + 2 * (a1 + a2 + a3 + a4) + "元");
                    break;
                case 1://任选二
                    int a = a1 * (a2 + a3 + a4) + a2 * (a3 + a4) + a3 * a4;
                    tv_money.setText("共" + a + "注" + 2 * a + "元");
                    break;
                case 2://任选二全包
                    int x = 0;
                    int j = 0;
                    for (int i = 0; i < i1.length; i++) {
                        if (i1[i] != -1) {
                            for (int z = 0; z < i2.length; z++) {
                                if (i2[z] != -1) {
                                    if (i1[i] == i2[z]) {
                                        x = x + 1;
                                    } else {
                                        j = j + 1;
                                    }
                                }
                            }
                        }
                    }
                    tv_money.setText("共" + (6 * x + 12 * j) + "注" + 2 * (6 * x + 12 * j) + "元");
                    break;
                case 3://任选三
                    int z = 0;
                    z = a1 * a2 * a3 + a1 * a2 * a4 + a1 * a3 * a4 + a2 * a3 * a4;
                    tv_money.setText("共" + z + "注" + 2 * z + "元");
                    break;
                case 4://任选三全包
                    int q = 0;//记录有几组两个相等的
                    int w = 0;//记录有几组三个相等的
                    int y = 0;//记录有几组全不相等的
                    for (int i = 0; i < i1.length; i++) {
                        if (i1[i] != -1) {
                            for (int s = 0; s < i2.length; s++) {
                                if (i2[s] != -1) {
                                    for (int d = 0; d < i3.length; d++) {
                                        if (i3[d] != -1) {
                                            if (i1[i] == i2[s] && i1[i] == i3[d] && i2[s] == i3[d]) {
                                                w = w + 1;
                                            } else if (i1[i] == i2[s] || i1[i] == i3[d] || i2[s] == i3[d]) {
                                                q = q + 1;
                                            } else if (i1[i] != i2[s] || i1[i] != i3[d] || i2[s] != i3[d]) {
                                                y = y + 1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    tv_money.setText("共" + (4 * w + 12 * q + 24 * y) + "注" + 2 * (4 * w + 12 * q + 24 * y) + "元");
                    break;
            }

        } else if (c == 2) {//直选
            switch (po2) {
                case 0://直选单式
                    tv_money.setText("共" + (a1 * a2 * a3 * a4) + "注" + 2 * (a1 * a2 * a3 * a4) + "元");
                    break;
                case 1://直选复式
                    tv_money.setText("共" + (a1 * a2 * a3 * a4) + "注" + 2 * (a1 * a2 * a3 * a4) + "元");
                    break;
                case 2://直选组合
                    if ((a1 > 0 && a2 == 0 && a3 == 0 && a4 == 0) || (a2 > 0 && a1 == 0 && a3 == 0 && a4 == 0)
                            || (a3 > 0 && a2 == 0 && a1 == 0 && a4 == 0) || (a4 > 0 && a2 == 0 && a3 == 0 && a1 == 0)) {
                        tv_money.setText("共" + 0 + "注" + 0 + "元");
                    } else {
                        int m = (a1 + a2 + a3 + a4) + (a1 * (a2 + a3 + a4) + a2 * (a3 + a4) + a3 * a4) +
                                (a1 * a2 * a3 + a1 * a2 * a4 + a1 * a3 * a4 + a2 * a3 * a4) + (a1 * a2 * a3 * a4);
                        tv_money.setText("共" + m + "注" + 2 * m + "元");
                    }
                    break;
            }

        } else if (c == 3) {//组选
            switch (po3) {
                case 0://组选24单式
                case 3://组选12单式
                case 7://组选6单式
                case 10://组选4单式
                    tv_money.setText("共" + a1 * a2 * a3 * a4 + "注" + 2 * a1 * a2 * a3 * a4 + "元");
                    break;
                case 1://组选24复式
                    switch (a1) {
                        case 0:
                            tv_money.setText("共" + 0 + "注" + 0 + "元");
                            break;
                        case 1:
                            tv_money.setText("共" + 0 + "注" + 0 + "元");
                            break;
                        case 2:
                            tv_money.setText("共" + 0 + "注" + 0 + "元");
                            break;
                        case 3:
                            tv_money.setText("共" + 0 + "注" + 0 + "元");
                            break;
                        case 4:
                            tv_money.setText("共" + 1 + "注" + 2 + "元");
                            break;
                        case 5:
                            tv_money.setText("共" + 5 + "注" + 10 + "元");
                            break;
                        case 6:
                            tv_money.setText("共" + 15 + "注" + 30 + "元");
                            break;
                        case 7:
                            tv_money.setText("共" + 35 + "注" + 70 + "元");
                            break;
                        case 8:
                            tv_money.setText("共" + 70 + "注" + 140 + "元");
                            break;
                    }
                    break;
                case 2://组选24胆拖
                    if (a1 == 1) {//1个胆码
                        switch (a2) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                tv_money.setText("共" + 0 + "注" + 0 + "元");
                                break;
                            case 4:
                                tv_money.setText("共" + 4 + "注" + 8 + "元");
                                break;
                            case 5:
                                tv_money.setText("共" + 10 + "注" + 20 + "元");
                                break;
                            case 6:
                                tv_money.setText("共" + 20 + "注" + 40 + "元");
                                break;
                            case 7:
                                tv_money.setText("共" + 35 + "注" + 70 + "元");
                                break;
                        }
                    } else if (a1 == 2) {//2个胆码
                        switch (a2) {
                            case 0:
                            case 1:
                            case 2:
                                tv_money.setText("共" + 0 + "注" + 0 + "元");
                                break;
                            case 3:
                                tv_money.setText("共" + 3 + "注" + 6 + "元");
                                break;
                            case 4:
                                tv_money.setText("共" + 6 + "注" + 12 + "元");
                                break;
                            case 5:
                                tv_money.setText("共" + 10 + "注" + 20 + "元");
                                break;
                            case 6:
                                tv_money.setText("共" + 15 + "注" + 30 + "元");
                                break;
                        }
                    } else if (a1 == 3) {//3个胆码
                        switch (a2) {
                            case 0:
                            case 1:
                                tv_money.setText("共" + 0 + "注" + 0 + "元");
                                break;
                            case 2:
                                tv_money.setText("共" + 2 + "注" + 4 + "元");
                                break;
                            case 3:
                                tv_money.setText("共" + 3 + "注" + 6 + "元");
                                break;
                            case 4:
                                tv_money.setText("共" + 4 + "注" + 8 + "元");
                                break;
                            case 5:
                                tv_money.setText("共" + 5 + "注" + 10 + "元");
                                break;
                        }
                    } else {
                        tv_money.setText("共" + 0 + "注" + 0 + "元");
                    }
                    break;
                case 4://组选12复式
                    switch (a1) {
                        case 0:
                        case 1:
                        case 2:
                            tv_money.setText("共" + 0 + "注" + 0 + "元");
                            break;
                        case 3:
                            tv_money.setText("共" + 3 + "注" + 6 + "元");
                            break;
                        case 4:
                            tv_money.setText("共" + 12 + "注" + 24 + "元");
                            break;
                        case 5:
                            tv_money.setText("共" + 30 + "注" + 60 + "元");
                            break;
                        case 6:
                            tv_money.setText("共" + 60 + "注" + 120 + "元");
                            break;
                        case 7:
                            tv_money.setText("共" + 105 + "注" + 210 + "元");
                            break;
                        case 8:
                            tv_money.setText("共" + 168 + "注" + 336 + "元");
                            break;
                    }
                    break;
                case 5://组选12胆拖
                    if (a1 == 1) {//一个胆球
                        switch (a2) {
                            case 0:
                            case 1:
                                tv_money.setText("共" + 0 + "注" + 0 + "元");
                                break;
                            case 2:
                                tv_money.setText("共" + 3 + "注" + 6 + "元");
                                break;
                            case 3:
                                tv_money.setText("共" + 9 + "注" + 18 + "元");
                                break;
                            case 4:
                                tv_money.setText("共" + 18 + "注" + 36 + "元");
                                break;
                            case 5:
                                tv_money.setText("共" + 30 + "注" + 60 + "元");
                                break;
                            case 6:
                                tv_money.setText("共" + 45 + "注" + 90 + "元");
                                break;
                            case 7:
                                tv_money.setText("共" + 63 + "注" + 126 + "元");
                                break;
                        }
                    } else if (a1 == 2) {//2个胆球
                        switch (a2) {
                            case 0:
                                tv_money.setText("共" + 0 + "注" + 0 + "元");
                                break;
                            case 1:
                                tv_money.setText("共" + 3 + "注" + 6 + "元");
                                break;
                            case 2:
                                tv_money.setText("共" + 6 + "注" + 12 + "元");
                                break;
                            case 3:
                                tv_money.setText("共" + 9 + "注" + 18 + "元");
                                break;
                            case 4:
                                tv_money.setText("共" + 12 + "注" + 24 + "元");
                                break;
                            case 5:
                                tv_money.setText("共" + 15 + "注" + 30 + "元");
                                break;
                            case 6:
                                tv_money.setText("共" + 18 + "注" + 36 + "元");
                                break;
                        }
                    }
                    break;
                case 6://组选12重胆拖
                    if (a1 == 1) {//一个胆拖
                        switch (a2) {
                            case 0:
                            case 1:
                            case 2:
                                tv_money.setText("共" + 0 + "注" + 0 + "元");
                                break;
                            case 3:
                                tv_money.setText("共" + 3 + "注" + 6 + "元");
                                break;
                            case 4:
                                tv_money.setText("共" + 6 + "注" + 12 + "元");
                                break;
                            case 5:
                                tv_money.setText("共" + 10 + "注" + 20 + "元");
                                break;
                            case 6:
                                tv_money.setText("共" + 15 + "注" + 30 + "元");
                                break;
                            case 7:
                                tv_money.setText("共" + 21 + "注" + 42 + "元");
                                break;
                        }
                    } else {
                        tv_money.setText("共" + 0 + "注" + 0 + "元");
                    }
                    break;
                case 8://组选6复式
                    switch (a1) {
                        case 0:
                        case 1:
                            tv_money.setText("共" + 0 + "注" + 0 + "元");
                            break;
                        case 2:
                            tv_money.setText("共" + 1 + "注" + 2 + "元");
                            break;
                        case 3:
                            tv_money.setText("共" + 3 + "注" + 6 + "元");
                            break;
                        case 4:
                            tv_money.setText("共" + 6 + "注" + 12 + "元");
                            break;
                        case 5:
                            tv_money.setText("共" + 10 + "注" + 20 + "元");
                            break;
                        case 6:
                            tv_money.setText("共" + 15 + "注" + 30 + "元");
                            break;
                        case 7:
                            tv_money.setText("共" + 21 + "注" + 42 + "元");
                            break;
                        case 8:
                            tv_money.setText("共" + 28 + "注" + 56 + "元");
                            break;
                    }
                    break;
                case 9://组选12重胆拖
                    if (a1 == 1) {
                        tv_money.setText("共" + a1 * a2 + "注" + 2 * a1 * a2 + "元");
                    } else {
                        tv_money.setText("共" + 0 + "注" + 0 + "元");
                    }
                    break;
                case 11://组选4复式
                    tv_money.setText("共" + a1 * (a1 - 1) + "注" + 2 * a1 * (a1 - 1) + "元");
                    break;
                case 12://组选4胆拖
                    if (a1 == 1) {
                        tv_money.setText("共" + 2 * a2 + "注" + 2 * 2 * a2 + "元");
                    } else {
                        tv_money.setText("共" + 0 + "注" + 0 + "元");
                    }
                    break;
                case 13://组选4重胆拖
                    if (a1 == 1) {
                        tv_money.setText("共" + a2 + "注" + 2 * a2 + "元");
                    } else {
                        tv_money.setText("共" + 0 + "注" + 0 + "元");
                    }
                    break;

            }

        } else if (c == 4) {//前三组选
            switch (po4) {
                case 0://前三组三
                case 2://任三组三
                    switch (a1) {
                        case 0:
                        case 1:
                            tv_money.setText("共" + 0 + "注" + 0 + "元");
                            break;
                        case 2:
                            tv_money.setText("共" + 6 + "注" + 12 + "元");
                            break;
                        case 3:
                            tv_money.setText("共" + 18 + "注" + 36 + "元");
                            break;
                        case 4:
                            tv_money.setText("共" + 36 + "注" + 72 + "元");
                            break;
                        case 5:
                            tv_money.setText("共" + 60 + "注" + 120 + "元");
                            break;
                    }
                    break;
                case 1://前三组六
                case 3://任san组六
                    switch (a1) {
                        case 0:
                        case 1:
                        case 2:
                            tv_money.setText("共" + 0 + "注" + 0 + "元");
                            break;
                        case 3:
                            tv_money.setText("共" + 6 + "注" + 12 + "元");
                            break;
                        case 4:
                            tv_money.setText("共" + 24 + "注" + 48 + "元");
                            break;
                        case 5:
                            tv_money.setText("共" + 60 + "注" + 120 + "元");
                            break;
                    }
                    break;
            }

        } else if (c == 5) {//新玩法
            switch (po5) {
                case 0://组选三码全包
                    switch (a1) {
                        case 0:
                        case 1:
                        case 2:
                            tv_money.setText("共" + 0 + "注" + 0 + "元");
                            break;
                        case 3:
                            tv_money.setText("共" + 8 + "注" + 16 + "元");
                            break;
                        case 4:
                            tv_money.setText("共" + 32 + "注" + 64 + "元");
                            break;
                        case 5:
                            tv_money.setText("共" + 80 + "注" + 160 + "元");
                            break;
                        case 6:
                            tv_money.setText("共" + 160 + "注" + 320 + "元");
                            break;
                        case 7:
                            tv_money.setText("共" + 280 + "注" + 560 + "元");
                            break;
                        case 8:
                            tv_money.setText("共" + 448 + "注" + 896 + "元");
                            break;

                    }
                    break;
                case 1://组选二码全包
                    switch (a1) {
                        case 0:
                        case 1:
                            tv_money.setText("共" + 0 + "注" + 0 + "元");
                            break;
                        case 2:
                            tv_money.setText("共" + 36 + "注" + 72 + "元");
                            break;
                        case 3:
                            tv_money.setText("共" + 108 + "注" + 216 + "元");
                            break;
                        case 4:
                            tv_money.setText("共" + 216 + "注" + 432 + "元");
                            break;
                        case 5:
                            tv_money.setText("共" + 360 + "注" + 720 + "元");
                            break;
                        case 6:
                            tv_money.setText("共" + 540 + "注" + 1080 + "元");
                            break;
                        case 7:
                            tv_money.setText("共" + 756 + "注" + 1512 + "元");
                            break;
                        case 8:
                            tv_money.setText("共" + 1008 + "注" + 2016 + "元");
                            break;

                    }
                    break;
                case 2://选四全包
                    switch (po) {
                        case 0://选四不重全包
                            if (a1 == 4) {
                                tv_money.setText("共" + 24 + "注" + 48 + "元");
                            } else {
                                tv_money.setText("共" + 0 + "注" + 0 + "元");
                            }
                            break;
                        case 1://选四一对全包
                            if (a1 == 1 && a2 == 2) {
                                tv_money.setText("共" + 12 + "注" + 24 + "元");
                            } else {
                                tv_money.setText("共" + 0 + "注" + 0 + "元");
                            }
                            break;
                        case 2://选四两对全包
                            if (a1 == 2) {
                                tv_money.setText("共" + 6 + "注" + 12 + "元");
                            } else {
                                tv_money.setText("共" + 0 + "注" + 0 + "元");
                            }
                            break;
                        case 3://选四三条全包
                            if (a1 == 1 && a2 == 1) {
                                tv_money.setText("共" + 4 + "注" + 8 + "元");
                            } else {
                                tv_money.setText("共" + 0 + "注" + 0 + "元");
                            }
                            break;
                    }
                    break;
                case 3://重号全包
                    tv_money.setText("共" + 11 * a1 + "注" + 2 * 11 * a1 + "元");
                    break;
            }
        }
    }

    /**
     * 清楚选中号码
     */

    private void clear() {
        Log.i("clear", "clear");
        //自由泳
        for (int j = 0; j < i1.length; j++) {
            if (i1[j] != -1) {
                Log.i("clear", "clear");
                swimmingadapter1.chiceState(j);
                a1 = swimmingadapter1.i;
                i1[j] = -1;
                showMoney();
            }
        }
        //仰泳
        for (int j = 0; j < i2.length; j++) {
            if (i2[j] != -1) {
                swimmingadapter2.chiceState(j);
                a2 = swimmingadapter2.i;
                i2[j] = -1;
                showMoney();
            }
        }
        //蛙泳
        for (int j = 0; j < i3.length; j++) {
            if (i3[j] != -1) {
                swimmingadapter3.chiceState(j);
                a3 = swimmingadapter3.i;
                i3[j] = -1;
                showMoney();
            }
        }
        //蝶泳
        for (int j = 0; j < i4.length; j++) {
            if (i4[j] != -1) {
                swimmingadapter4.chiceState(j);
                a4 = swimmingadapter4.i;
                i4[j] = -1;
                showMoney();
            }
        }
        a1 = 0;
        a2 = 0;
        a3 = 0;
        a4 = 0;
        tv_money.setText("共" + 0 + "注" + 0 + "元");
        //取消复选框的选中状态
        cb_ziyouyong.setChecked(false);
        cb_yangyong.setChecked(false);
        cb_wayong.setChecked(false);
        cb_dieyong.setChecked(false);
        cb = 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //机选
            case R.id.tv_choose:
                clear();
                if (c == 1) {//任选
                    switch (po1) {
                        case 0:
                        case 1:
                        case 3://任选一，任选二,任选三
                            for (int i = 0; i < 4; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                Log.i("sjs", x + "");
                            }
                            //自由泳
                            int b1 = sjs[0];
                            swimmingadapter1.chiceState(b1);
                            a1 = swimmingadapter1.i;
                            if (i1[b1] == list.get(b1)) {
                                i1[b1] = -1;
                            } else {
                                i1[b1] = list.get(b1);
                            }
                            Log.i("sjs2", i1[b1] + "");
                            showMoney();

                            //仰泳
                            int b2 = sjs[1];
                            swimmingadapter2.chiceState(b2);
                            a2 = swimmingadapter2.i;
                            if (i2[b2] == list.get(b2)) {
                                i2[b2] = -1;
                            } else {
                                i2[b2] = list.get(b2);
                            }
                            Log.i("sjs2", i2[b2] + "");
                            showMoney();

                            //自由泳
                            int b3 = sjs[2];
                            swimmingadapter3.chiceState(b3);
                            a3 = swimmingadapter3.i;
                            if (i3[b3] == list.get(b3)) {
                                i3[b3] = -1;
                            } else {
                                i3[b3] = list.get(b3);
                            }
                            Log.i("sjs2", i3[b3] + "");
                            showMoney();

                            //自由泳
                            int b4 = sjs[3];
                            swimmingadapter4.chiceState(b4);
                            a4 = swimmingadapter4.i;
                            if (i4[b4] == list.get(b4)) {
                                i4[b4] = -1;
                            } else {
                                i4[b4] = list.get(b4);
                            }
                            Log.i("sjs2", i4[b4] + "");
                            showMoney();
                            break;
                        case 2://任选二全包
                            for (int i = 0; i < 2; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                Log.i("sjs", x + "");
                            }
                            //自由泳
                            int b5 = sjs[0];
                            swimmingadapter1.chiceState(b5);
                            a1 = swimmingadapter1.i;
                            if (i1[b5] == list.get(b5)) {
                                i1[b5] = -1;
                            } else {
                                i1[b5] = list.get(b5);
                            }
                            Log.i("sjs2", i1[b5] + "");
                            showMoney();

                            //仰泳
                            int b6 = sjs[1];
                            swimmingadapter2.chiceState(b6);
                            a2 = swimmingadapter2.i;
                            if (i2[b6] == list.get(b6)) {
                                i2[b6] = -1;
                            } else {
                                i2[b6] = list.get(b6);
                            }
                            Log.i("sjs2", i2[b6] + "");
                            showMoney();
                            break;

                        case 4://任选三全包
                            for (int i = 0; i < 3; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                Log.i("sjs", x + "");
                            }
                            //自由泳
                            int b7 = sjs[0];
                            swimmingadapter1.chiceState(b7);
                            a1 = swimmingadapter1.i;
                            if (i1[b7] == list.get(b7)) {
                                i1[b7] = -1;
                            } else {
                                i1[b7] = list.get(b7);
                            }
                            Log.i("sjs2", i1[b7] + "");
                            showMoney();

                            //仰泳
                            int b8 = sjs[1];
                            swimmingadapter2.chiceState(b8);
                            a2 = swimmingadapter2.i;
                            if (i2[b8] == list.get(b8)) {
                                i2[b8] = -1;
                            } else {
                                i2[b8] = list.get(b8);
                            }
                            Log.i("sjs2", i2[b8] + "");
                            showMoney();

                            //自由泳
                            int b9 = sjs[2];
                            swimmingadapter3.chiceState(b9);
                            a3 = swimmingadapter3.i;
                            if (i3[b9] == list.get(b9)) {
                                i3[b9] = -1;
                            } else {
                                i3[b9] = list.get(b9);
                            }
                            Log.i("sjs2", i3[b9] + "");
                            showMoney();
                            break;
                    }

                } else if (c == 2) {//直选
                    switch (po2) {
                        case 0://直选单式
                        case 1://直选复式
                        case 2://直选组合
                            for (int i = 0; i < 4; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                Log.i("sjs", x + "");
                            }
                            //自由泳
                            int b1 = sjs[0];
                            swimmingadapter1.chiceState(b1);
                            a1 = swimmingadapter1.i;
                            if (i1[b1] == list.get(b1)) {
                                i1[b1] = -1;
                            } else {
                                i1[b1] = list.get(b1);
                            }
                            Log.i("sjs2", i1[b1] + "");
                            showMoney();

                            //仰泳
                            int b2 = sjs[1];
                            swimmingadapter2.chiceState(b2);
                            a2 = swimmingadapter2.i;
                            if (i2[b2] == list.get(b2)) {
                                i2[b2] = -1;
                            } else {
                                i2[b2] = list.get(b2);
                            }
                            Log.i("sjs2", i2[b2] + "");
                            showMoney();

                            //自由泳
                            int b3 = sjs[2];
                            swimmingadapter3.chiceState(b3);
                            a3 = swimmingadapter3.i;
                            if (i3[b3] == list.get(b3)) {
                                i3[b3] = -1;
                            } else {
                                i3[b3] = list.get(b3);
                            }
                            Log.i("sjs2", i3[b3] + "");
                            showMoney();

                            //自由泳
                            int b4 = sjs[3];
                            swimmingadapter4.chiceState(b4);
                            a4 = swimmingadapter4.i;
                            if (i4[b4] == list.get(b4)) {
                                i4[b4] = -1;
                            } else {
                                i4[b4] = list.get(b4);
                            }
                            Log.i("sjs2", i4[b4] + "");
                            showMoney();
                            break;
//                        case 2://直选组合
//                            for (int i = 0; i < 2; i++) {
//                                int x = (int) (Math.random() * 8);
//                                sjs[i] = x;
//                                Log.i("sjs", x + "");
//                            }
//                            //自由泳
//                            int c1 = sjs[0];
//                            swimmingadapter1.chiceState(c1);
//                            a1 = swimmingadapter1.i;
//                            if (i1[c1] == list.get(c1)) {
//                                i1[c1] = -1;
//                            } else {
//                                i1[c1] = list.get(c1);
//                            }
//                            Log.i("sjs2", i1[c1] + "");
//                            showMoney();
//
//                            //仰泳
//                            int c2 = sjs[1];
//                            swimmingadapter2.chiceState(c2);
//                            a2 = swimmingadapter2.i;
//                            if (i2[c2] == list.get(c2)) {
//                                i2[c2] = -1;
//                            } else {
//                                i2[c2] = list.get(c2);
//                            }
//                            Log.i("sjs2", i2[c2] + "");
//                            showMoney();
//                            break;
                    }

                } else if (c == 3) {//组选
                    switch (po3) {
                        case 0://组选24单式
                            for (int i = 0; i < 4; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs[i] == sjs[j]) {
                                        i--;
                                    }
                                }
                                Log.i("sjs", x + "");
                            }
                            //自由泳
                            int b1 = sjs[0];
                            swimmingadapter1.chiceState(b1);
                            a1 = swimmingadapter1.i;
                            if (i1[b1] == list.get(b1)) {
                                i1[b1] = -1;
                            } else {
                                i1[b1] = list.get(b1);
                            }
                            Log.i("sjs2", i1[b1] + "");
                            showMoney();

                            //仰泳
                            int b2 = sjs[1];
                            swimmingadapter2.chiceState(b2);
                            a2 = swimmingadapter2.i;
                            if (i2[b2] == list.get(b2)) {
                                i2[b2] = -1;
                            } else {
                                i2[b2] = list.get(b2);
                            }
                            Log.i("sjs2", i2[b2] + "");
                            showMoney();

                            //自由泳
                            int b3 = sjs[2];
                            swimmingadapter3.chiceState(b3);
                            a3 = swimmingadapter3.i;
                            if (i3[b3] == list.get(b3)) {
                                i3[b3] = -1;
                            } else {
                                i3[b3] = list.get(b3);
                            }
                            Log.i("sjs2", i3[b3] + "");
                            showMoney();

                            //自由泳
                            int b4 = sjs[3];
                            swimmingadapter4.chiceState(b4);
                            a4 = swimmingadapter4.i;
                            if (i4[b4] == list.get(b4)) {
                                i4[b4] = -1;
                            } else {
                                i4[b4] = list.get(b4);
                            }
                            Log.i("sjs2", i4[b4] + "");
                            showMoney();
                            break;
                        case 1://组选24复式
                            for (int i = 0; i < 5; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs[i] == sjs[j]) {
                                        i--;
                                    }
                                }
                                Log.i("sjs", x + "");
                            }
                            for (int a = 0; a < sjs.length; a++) {
                                swimmingadapter1.chiceState(sjs[a]);
                                a1 = swimmingadapter1.i;
                                if (i1[sjs[a]] == list.get(sjs[a])) {
                                    i1[sjs[a]] = -1;
                                } else {
                                    i1[sjs[a]] = list.get(sjs[a]);
                                }
                                Log.i("sjs2", i1[sjs[a]] + "");
                                showMoney();
                            }
                            break;
                        case 2://组选24胆拖
                            for (int i = 0; i < 5; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs[i] == sjs[j]) {
                                        i--;
                                    }
                                }
                                Log.i("sjs", x + "");
                            }
                            //胆码
                            swimmingadapter1.chiceState(sjs[0]);
                            a1 = swimmingadapter1.i;
                            if (i1[sjs[0]] == list.get(sjs[0])) {
                                i1[sjs[0]] = -1;
                            } else {
                                i1[sjs[0]] = list.get(sjs[0]);
                            }
                            Log.i("sjs2", i1[sjs[0]] + "");
                            showMoney();
                            //拖码
                            for (int a = 1; a < sjs.length; a++) {
                                swimmingadapter2.chiceState(sjs[a]);
                                a2 = swimmingadapter2.i;
                                if (i2[sjs[a]] == list.get(sjs[a])) {
                                    i2[sjs[a]] = -1;
                                } else {
                                    i2[sjs[a]] = list.get(sjs[a]);
                                }
                                Log.i("sjs2", i2[sjs[a]] + "");
                                showMoney();
                            }
                            break;
                        case 3://组选12单式
                            for (int i = 0; i < 3; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs[i] == sjs[j]) {
                                        i--;
                                    }
                                }
                                Log.i("sjs", x + "");
                            }
                            int y = (int) (Math.random() * 3);
                            if (y == 0) {
                                sjs[3] = sjs[0];
                            } else if (y == 1) {
                                sjs[3] = sjs[1];
                            } else if (y == 2) {
                                sjs[3] = sjs[2];
                            }

                            //自由泳
                            int x1 = sjs[0];
                            swimmingadapter1.chiceState(x1);
                            a1 = swimmingadapter1.i;
                            if (i1[x1] == list.get(x1)) {
                                i1[x1] = -1;
                            } else {
                                i1[x1] = list.get(x1);
                            }
                            Log.i("sjs2", i1[x1] + "");
                            showMoney();

                            //仰泳
                            int x2 = sjs[1];
                            swimmingadapter2.chiceState(x2);
                            a2 = swimmingadapter2.i;
                            if (i2[x2] == list.get(x2)) {
                                i2[x2] = -1;
                            } else {
                                i2[x2] = list.get(x2);
                            }
                            Log.i("sjs2", i2[x2] + "");
                            showMoney();

                            //蛙泳
                            int x3 = sjs[2];
                            swimmingadapter3.chiceState(x3);
                            a3 = swimmingadapter3.i;
                            if (i3[x3] == list.get(x3)) {
                                i3[x3] = -1;
                            } else {
                                i3[x3] = list.get(x3);
                            }
                            Log.i("sjs2", i3[x3] + "");
                            showMoney();

                            //蝶泳
                            int x4 = sjs[3];
                            swimmingadapter4.chiceState(x4);
                            a4 = swimmingadapter4.i;
                            if (i4[x4] == list.get(x4)) {
                                i4[x4] = -1;
                            } else {
                                i4[x4] = list.get(x4);
                            }
                            Log.i("sjs2", i4[x4] + "");
                            showMoney();

                            break;
                        case 4://组选12复式
                        case 8://组选6复式
                            for (int i = 0; i < 3; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs[i] == sjs[j]) {
                                        i--;
                                    }
                                }
                                Log.i("sjs", x + "");
                            }
                            for (int a = 0; a < sjs.length - 2; a++) {
                                swimmingadapter1.chiceState(sjs[a]);
                                a1 = swimmingadapter1.i;
                                if (i1[sjs[a]] == list.get(sjs[a])) {
                                    i1[sjs[a]] = -1;
                                } else {
                                    i1[sjs[a]] = list.get(sjs[a]);
                                }
                                Log.i("sjs2", i1[sjs[a]] + "");
                                showMoney();
                            }
                            break;
                        case 5://组选12胆拖
                        case 6://组选12重胆拖
                            for (int i = 0; i < 4; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs[i] == sjs[j]) {
                                        i--;
                                    }
                                }
                            }
                            //将随机数打印
                            for (int s = 0; s < sjs.length; s++) {
                                Log.i("sjs4", sjs[s] + "");
                            }
                            swimmingadapter1.chiceState(sjs[0]);
                            a1 = swimmingadapter1.i;
                            if (i1[sjs[0]] == list.get(sjs[0])) {
                                i1[sjs[0]] = -1;
                            } else {
                                i1[sjs[0]] = list.get(sjs[0]);
                            }
                            Log.i("sjs2", i1[sjs[0]] + "");
                            showMoney();

                            for (int a = 1; a < sjs.length - 1; a++) {
                                swimmingadapter2.chiceState(sjs[a]);
                                a2 = swimmingadapter2.i;
                                if (i2[sjs[a]] == list.get(sjs[a])) {
                                    i2[sjs[a]] = -1;
                                } else {
                                    i2[sjs[a]] = list.get(sjs[a]);
                                }
                                Log.i("sjs2", i2[sjs[a]] + "");
                                showMoney();
                            }
                            break;
                        case 7://组选6单式
                            for (int i = 0; i < 2; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs2[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs2[i] == sjs2[j]) {
                                        i--;
                                    }
                                }
                                Log.i("sjs", x + "");
                            }
                            int y1 = (int) (Math.random() * 3);
                            if (y1 == 0) {
                                sjs[0] = sjs2[0];
                                sjs[3] = sjs2[0];
                                sjs[2] = sjs2[1];
                                sjs[1] = sjs2[1];
                            } else if (y1 == 1) {
                                sjs[3] = sjs2[0];
                                sjs[1] = sjs2[0];
                                sjs[2] = sjs2[1];
                                sjs[0] = sjs2[1];
                            } else if (y1 == 2) {
                                sjs[3] = sjs2[0];
                                sjs[2] = sjs2[0];
                                sjs[1] = sjs2[1];
                                sjs[0] = sjs2[1];
                            }

                            //自由泳
                            int q1 = sjs[0];
                            swimmingadapter1.chiceState(q1);
                            a1 = swimmingadapter1.i;
                            if (i1[q1] == list.get(q1)) {
                                i1[q1] = -1;
                            } else {
                                i1[q1] = list.get(q1);
                            }
                            Log.i("sjs2", i1[q1] + "");
                            showMoney();

                            //仰泳
                            int q2 = sjs[1];
                            swimmingadapter2.chiceState(q2);
                            a2 = swimmingadapter2.i;
                            if (i2[q2] == list.get(q2)) {
                                i2[q2] = -1;
                            } else {
                                i2[q2] = list.get(q2);
                            }
                            Log.i("sjs2", i2[q2] + "");
                            showMoney();

                            //蛙泳
                            int q3 = sjs[2];
                            swimmingadapter3.chiceState(q3);
                            a3 = swimmingadapter3.i;
                            if (i3[q3] == list.get(q3)) {
                                i3[q3] = -1;
                            } else {
                                i3[q3] = list.get(q3);
                            }
                            Log.i("sjs2", i3[q3] + "");
                            showMoney();

                            //蝶泳
                            int q4 = sjs[3];
                            swimmingadapter4.chiceState(q4);
                            a4 = swimmingadapter4.i;
                            if (i4[q4] == list.get(q4)) {
                                i4[q4] = -1;
                            } else {
                                i4[q4] = list.get(q4);
                            }
                            Log.i("sjs2", i4[q4] + "");
                            showMoney();
                            break;
                        case 9://组选6胆拖
                            for (int i = 0; i < 3; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs[i] == sjs[j]) {
                                        i--;
                                    }
                                }
                            }
                            //将随机数打印
                            for (int s = 0; s < sjs.length; s++) {
                                Log.i("sjs4", sjs[s] + "");
                            }
                            swimmingadapter1.chiceState(sjs[0]);
                            a1 = swimmingadapter1.i;
                            if (i1[sjs[0]] == list.get(sjs[0])) {
                                i1[sjs[0]] = -1;
                            } else {
                                i1[sjs[0]] = list.get(sjs[0]);
                            }
                            Log.i("sjs2", i1[sjs[0]] + "");
                            showMoney();

                            for (int a = 1; a < sjs.length - 2; a++) {
                                swimmingadapter2.chiceState(sjs[a]);
                                a2 = swimmingadapter2.i;
                                if (i2[sjs[a]] == list.get(sjs[a])) {
                                    i2[sjs[a]] = -1;
                                } else {
                                    i2[sjs[a]] = list.get(sjs[a]);
                                }
                                Log.i("sjs2", i2[sjs[a]] + "");
                                showMoney();
                            }
                            break;
                        case 10://组选4单式
                            for (int i = 0; i < 2; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs2[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs2[i] == sjs2[j]) {
                                        i--;
                                    }
                                }
                                Log.i("sjs", x + "");
                            }
                            int y2 = (int) (Math.random() * 4);
                            if (y2 == 0) {
                                sjs[0] = sjs2[0];
                                sjs[3] = sjs2[0];
                                sjs[2] = sjs2[0];
                                sjs[1] = sjs2[1];
                            } else if (y2 == 1) {
                                sjs[3] = sjs2[0];
                                sjs[1] = sjs2[0];
                                sjs[2] = sjs2[0];
                                sjs[0] = sjs2[1];
                            } else if (y2 == 2) {
                                sjs[3] = sjs2[1];
                                sjs[2] = sjs2[0];
                                sjs[1] = sjs2[0];
                                sjs[0] = sjs2[0];
                            } else if (y2 == 3) {
                                sjs[3] = sjs2[0];
                                sjs[2] = sjs2[1];
                                sjs[1] = sjs2[0];
                                sjs[0] = sjs2[0];
                            }

                            //自由泳
                            int m1 = sjs[0];
                            swimmingadapter1.chiceState(m1);
                            a1 = swimmingadapter1.i;
                            if (i1[m1] == list.get(m1)) {
                                i1[m1] = -1;
                            } else {
                                i1[m1] = list.get(m1);
                            }
                            Log.i("sjs2", i1[m1] + "");
                            showMoney();

                            //仰泳
                            int m2 = sjs[1];
                            swimmingadapter2.chiceState(m2);
                            a2 = swimmingadapter2.i;
                            if (i2[m2] == list.get(m2)) {
                                i2[m2] = -1;
                            } else {
                                i2[m2] = list.get(m2);
                            }
                            Log.i("sjs2", i2[m2] + "");
                            showMoney();

                            //蛙泳
                            int m3 = sjs[2];
                            swimmingadapter3.chiceState(m3);
                            a3 = swimmingadapter3.i;
                            if (i3[m3] == list.get(m3)) {
                                i3[m3] = -1;
                            } else {
                                i3[m3] = list.get(m3);
                            }
                            Log.i("sjs2", i3[m3] + "");
                            showMoney();

                            //蝶泳
                            int m4 = sjs[3];
                            swimmingadapter4.chiceState(m4);
                            a4 = swimmingadapter4.i;
                            if (i4[m4] == list.get(m4)) {
                                i4[m4] = -1;
                            } else {
                                i4[m4] = list.get(m4);
                            }
                            Log.i("sjs2", i4[m4] + "");
                            showMoney();
                            break;
                        case 11://组选4复式
                            for (int i = 0; i < 2; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs2[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs2[i] == sjs2[j]) {
                                        i--;
                                    }
                                }
                                Log.i("sjs", x + "");
                            }
                            for (int i = 0; i < sjs2.length; i++) {
                                swimmingadapter1.chiceState(sjs2[i]);
                                a1 = swimmingadapter1.i;
                                if (i1[sjs2[i]] == list.get(sjs2[i])) {
                                    i1[sjs2[i]] = -1;
                                } else {
                                    i1[sjs2[i]] = list.get(sjs2[i]);
                                }
                                Log.i("sjs2", i1[sjs2[i]] + "");
                                showMoney();
                            }
                            break;
                        case 12://组选4胆拖
                        case 13://组选4重胆拖
                            for (int i = 0; i < 3; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs[i] == sjs[j]) {
                                        i--;
                                    }
                                }
                                Log.i("sjs", x + "");
                            }
                            swimmingadapter1.chiceState(sjs[0]);
                            a1 = swimmingadapter1.i;
                            if (i1[sjs[0]] == list.get(sjs[0])) {
                                i1[sjs[0]] = -1;
                            } else {
                                i1[sjs[0]] = list.get(sjs[0]);
                            }
                            Log.i("sjs2", i1[sjs[0]] + "");
                            showMoney();

                            for (int i = 1; i < sjs.length - 2; i++) {
                                swimmingadapter2.chiceState(sjs[i]);
                                a2 = swimmingadapter2.i;
                                if (i2[sjs[i]] == list.get(sjs[i])) {
                                    i2[sjs[i]] = -1;
                                } else {
                                    i2[sjs[i]] = list.get(sjs[i]);
                                }
                                Log.i("sjs2", i2[sjs[i]] + "");
                                showMoney();
                            }
                            break;
                    }

                } else if (c == 4) {//前三组选
                    switch (po4) {
                        case 0://前三组三
//                        case 2://任三组三
                            for (int i = 0; i < 2; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs2[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs2[i] == sjs2[j]) {
                                        i--;
                                    }
                                }
                                Log.i("sjs", x + "");
                            }
                            for (int i = 0; i < sjs2.length; i++) {
                                swimmingadapter1.chiceState(sjs2[i]);
                                a1 = swimmingadapter1.i;
                                if (i1[sjs2[i]] == list.get(sjs2[i])) {
                                    i1[sjs2[i]] = -1;
                                } else {
                                    i1[sjs2[i]] = list.get(sjs2[i]);
                                }
                                Log.i("sjs2", i1[sjs2[i]] + "");
                                showMoney();
                            }
                            break;
                        case 1://前三组六
//                        case 3://任三组六
                            for (int i = 0; i < 3; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs[i] == sjs[j]) {
                                        i--;
                                    }
                                }
                                Log.i("sjs", x + "");
                            }
                            for (int i = 0; i < sjs.length - 2; i++) {
                                swimmingadapter1.chiceState(sjs[i]);
                                a1 = swimmingadapter1.i;
                                if (i1[sjs[i]] == list.get(sjs[i])) {
                                    i1[sjs[i]] = -1;
                                } else {
                                    i1[sjs[i]] = list.get(sjs[i]);
                                }
                                Log.i("sjs2", i1[sjs[i]] + "");
                                showMoney();
                            }
                            break;
                    }

                } else if (c == 5) {//新玩法
                    switch (po5) {
                        case 0://组选三码全包
                            for (int i = 0; i < 3; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs[i] == sjs[j]) {
                                        i--;
                                    }
                                }
                                Log.i("sjs", x + "");
                            }
                            for (int i = 0; i < sjs.length - 2; i++) {
                                swimmingadapter1.chiceState(sjs[i]);
                                a1 = swimmingadapter1.i;
                                if (i1[sjs[i]] == list.get(sjs[i])) {
                                    i1[sjs[i]] = -1;
                                } else {
                                    i1[sjs[i]] = list.get(sjs[i]);
                                }
                                Log.i("sjs2", i1[sjs[i]] + "");
                                showMoney();
                            }
                            break;
                        case 1://组选二码全包
                            for (int i = 0; i < 2; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs[i] == sjs[j]) {
                                        i--;
                                    }
                                }
                                Log.i("sjs", x + "");
                            }
                            for (int i = 0; i < sjs.length - 3; i++) {
                                swimmingadapter1.chiceState(sjs[i]);
                                a1 = swimmingadapter1.i;
                                if (i1[sjs[i]] == list.get(sjs[i])) {
                                    i1[sjs[i]] = -1;
                                } else {
                                    i1[sjs[i]] = list.get(sjs[i]);
                                }
                                Log.i("sjs2", i1[sjs[i]] + "");
                                showMoney();
                            }
                            break;
                        case 2://选四全包
                            if (po == 0) {//选四不重全包
                                for (int i = 0; i < 4; i++) {
                                    int x = (int) (Math.random() * 8);
                                    sjs[i] = x;
                                    //去掉相同的随机数
                                    for (int j = 0; j <= i; j++) {
                                        if ((i != j) && sjs[i] == sjs[j]) {
                                            i--;
                                        }
                                    }
                                    Log.i("sjs", x + "");
                                }
                                for (int i = 0; i < sjs.length - 1; i++) {
                                    swimmingadapter1.chiceState(sjs[i]);
                                    a1 = swimmingadapter1.i;
                                    if (i1[sjs[i]] == list.get(sjs[i])) {
                                        i1[sjs[i]] = -1;
                                    } else {
                                        i1[sjs[i]] = list.get(sjs[i]);
                                    }
                                    Log.i("sjs2", i1[sjs[i]] + "");
                                    showMoney();
                                }
                            } else if (po == 1) {//选四一对全包
                                for (int i = 0; i < 3; i++) {
                                    int x = (int) (Math.random() * 8);
                                    sjs[i] = x;
                                    //去掉相同的随机数
                                    for (int j = 0; j <= i; j++) {
                                        if ((i != j) && sjs[i] == sjs[j]) {
                                            i--;
                                        }
                                    }
                                    Log.i("sjs", x + "");
                                }
                                swimmingadapter1.chiceState(sjs[0]);
                                a1 = swimmingadapter1.i;
                                if (i1[sjs[0]] == list.get(sjs[0])) {
                                    i1[sjs[0]] = -1;
                                } else {
                                    i1[sjs[0]] = list.get(sjs[0]);
                                }
                                Log.i("sjs2", i1[sjs[0]] + "");
                                showMoney();

                                for (int i = 1; i < sjs.length - 2; i++) {
                                    swimmingadapter2.chiceState(sjs[i]);
                                    a2 = swimmingadapter2.i;
                                    if (i2[sjs[i]] == list.get(sjs[i])) {
                                        i2[sjs[i]] = -1;
                                    } else {
                                        i2[sjs[i]] = list.get(sjs[i]);
                                    }
                                    Log.i("sjs2", i2[sjs[i]] + "");
                                    showMoney();
                                }

                            } else if (po == 2) {//选四两对全包
                                for (int i = 0; i < 2; i++) {
                                    int x = (int) (Math.random() * 8);
                                    sjs[i] = x;
                                    //去掉相同的随机数
                                    for (int j = 0; j <= i; j++) {
                                        if ((i != j) && sjs[i] == sjs[j]) {
                                            i--;
                                        }
                                    }
                                    Log.i("sjs", x + "");
                                }
                                for (int i = 0; i < sjs.length - 3; i++) {
                                    swimmingadapter1.chiceState(sjs[i]);
                                    a1 = swimmingadapter1.i;
                                    if (i1[sjs[i]] == list.get(sjs[i])) {
                                        i1[sjs[i]] = -1;
                                    } else {
                                        i1[sjs[i]] = list.get(sjs[i]);
                                    }
                                    Log.i("sjs2", i1[sjs[i]] + "");
                                    showMoney();
                                }

                            } else if (po == 3) {//选四三条全包
                                for (int i = 0; i < 2; i++) {
                                    int x = (int) (Math.random() * 8);
                                    sjs[i] = x;
                                    //去掉相同的随机数
                                    for (int j = 0; j <= i; j++) {
                                        if ((i != j) && sjs[i] == sjs[j]) {
                                            i--;
                                        }
                                    }
                                    Log.i("sjs", x + "");
                                }
                                swimmingadapter1.chiceState(sjs[0]);
                                a1 = swimmingadapter1.i;
                                if (i1[sjs[0]] == list.get(sjs[0])) {
                                    i1[sjs[0]] = -1;
                                } else {
                                    i1[sjs[0]] = list.get(sjs[0]);
                                }
                                Log.i("sjs2", i1[sjs[0]] + "");
                                showMoney();

                                swimmingadapter2.chiceState(sjs[1]);
                                a2 = swimmingadapter2.i;
                                if (i2[sjs[1]] == list.get(sjs[1])) {
                                    i2[sjs[1]] = -1;
                                } else {
                                    i2[sjs[1]] = list.get(sjs[1]);
                                }
                                Log.i("sjs2", i2[sjs[1]] + "");
                                showMoney();
                            }
                            break;
                        case 3://重号全包
                            for (int i = 0; i < 1; i++) {
                                int x = (int) (Math.random() * 8);
                                sjs[i] = x;
                                //去掉相同的随机数
                                for (int j = 0; j <= i; j++) {
                                    if ((i != j) && sjs[i] == sjs[j]) {
                                        i--;
                                    }
                                }
                                Log.i("sjs", x + "");
                            }
                            for (int i = 0; i < sjs.length - 4; i++) {
                                swimmingadapter1.chiceState(sjs[i]);
                                a1 = swimmingadapter1.i;
                                if (i1[sjs[i]] == list.get(sjs[i])) {
                                    i1[sjs[i]] = -1;
                                } else {
                                    i1[sjs[i]] = list.get(sjs[i]);
                                }
                                Log.i("sjs2", i1[sjs[i]] + "");
                                showMoney();
                            }
                            break;
                    }

                }
                break;
            //选择玩法
            case R.id.linearlayout:
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
            //返回
            case R.id.btn_close:
                if (!b) {
                    popupWindow.dismiss();
                    iv_top.setImageResource(R.mipmap.top_2);
                    b = true;
                } else {
                    finish();
                }

                break;
            //清除
            case R.id.btn_clear:
                clear();
                break;
            //下一步
            case R.id.bt_next:
                if (c == 1) {//任选
                    switch (po1) {
                        case 0://任选一
                            if (a1 > 0 || a2 > 0 || a3 > 0 || a4 > 0) {
//                                Combination.rxy(i1, i2, i3,i4);
                                li1 = new ArrayList<Integer>();
                                for (int i = 0; i < i1.length; i++) {
                                    if (i1[i] != -1) {
                                        li1.add(i1[i]);
                                    }
                                }
                                li2 = new ArrayList<Integer>();
                                for (int i = 0; i < i2.length; i++) {
                                    if (i2[i] != -1) {
                                        li2.add(i2[i]);
                                    }
                                }
                                li3 = new ArrayList<Integer>();
                                for (int i = 0; i < i3.length; i++) {
                                    if (i3[i] != -1) {
                                        li3.add(i3[i]);
                                    }
                                }
                                li4 = new ArrayList<Integer>();
                                for (int i = 0; i < i4.length; i++) {
                                    if (i4[i] != -1) {
                                        li4.add(i4[i]);
                                    }
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po1);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "至少选一项", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 1://任选二
                            if ((a1 > 0 && a2 > 0) || (a1 > 0 && a3 > 0) || (a1 > 0 && a4 > 0) ||
                                    (a2 > 0 && a3 > 0) || (a2 > 0 && a4 > 0) || (a3 > 0 && a4 > 0)) {
//                                Combination.rxe(i1, i2, i3, i4);
                                li1 = new ArrayList<Integer>();
                                for (int i = 0; i < i1.length; i++) {
                                    if (i1[i] != -1) {
                                        li1.add(i1[i]);
                                    }
                                }
                                li2 = new ArrayList<Integer>();
                                for (int i = 0; i < i2.length; i++) {
                                    if (i2[i] != -1) {
                                        li2.add(i2[i]);
                                    }
                                }
                                li3 = new ArrayList<Integer>();
                                for (int i = 0; i < i3.length; i++) {
                                    if (i3[i] != -1) {
                                        li3.add(i3[i]);
                                    }
                                }
                                li4 = new ArrayList<Integer>();
                                for (int i = 0; i < i4.length; i++) {
                                    if (i4[i] != -1) {
                                        li4.add(i4[i]);
                                    }
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po1);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "至少选两项", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 2://任选二全包
                            if (a1 > 0 && a2 > 0) {
//                                Combination.rxeqb(i1, i2);
                                li1 = new ArrayList<Integer>();
                                for (int i = 0; i < i1.length; i++) {
                                    if (i1[i] != -1) {
                                        li1.add(i1[i]);
                                    }
                                }
                                li2 = new ArrayList<Integer>();
                                for (int i = 0; i < i2.length; i++) {
                                    if (i2[i] != -1) {
                                        li2.add(i2[i]);
                                    }
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po1);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "每项至少选一个号", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 3://任选三
                            if ((a1 > 0 && a2 > 0 && a3 > 0) || (a1 > 0 && a3 > 0 && a4 > 0) || (a1 > 0 && a2 > 0 && a4 > 0) ||
                                    (a2 > 0 && a3 > 0 && a4 > 0)) {
//                                Combination.rx3(i1, i2, i3, i4);
                                li1 = new ArrayList<Integer>();
                                for (int i = 0; i < i1.length; i++) {
                                    if (i1[i] != -1) {
                                        li1.add(i1[i]);
                                    }
                                }
                                li2 = new ArrayList<Integer>();
                                for (int i = 0; i < i2.length; i++) {
                                    if (i2[i] != -1) {
                                        li2.add(i2[i]);
                                    }
                                }
                                li3 = new ArrayList<Integer>();
                                for (int i = 0; i < i3.length; i++) {
                                    if (i3[i] != -1) {
                                        li3.add(i3[i]);
                                    }
                                }
                                li4 = new ArrayList<Integer>();
                                for (int i = 0; i < i4.length; i++) {
                                    if (i4[i] != -1) {
                                        li4.add(i4[i]);
                                    }
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po1);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "至少选三项", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 4://任选三全包
                            if (a1 > 0 && a2 > 0 && a3 > 0) {
//                                Combination.rxsqb(i1, i2, i3);
                                li1 = new ArrayList<Integer>();
                                for (int i = 0; i < i1.length; i++) {
                                    if (i1[i] != -1) {
                                        li1.add(i1[i]);
                                    }
                                }
                                li2 = new ArrayList<Integer>();
                                for (int i = 0; i < i2.length; i++) {
                                    if (i2[i] != -1) {
                                        li2.add(i2[i]);
                                    }
                                }
                                li3 = new ArrayList<Integer>();
                                for (int i = 0; i < i3.length; i++) {
                                    if (i3[i] != -1) {
                                        li3.add(i3[i]);
                                    }
                                }

                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po1);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "每项至少选择一个号", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                } else if (c == 2) {//直选
                    switch (po2) {
                        case 0://直选单式
                            if (a1 == 1 && a2 == 1 && a3 == 1 && a4 == 1) {
//                                Combination.zxds(i1, i2, i3, i4);
                                li1 = new ArrayList<Integer>();
                                for (int i = 0; i < i1.length; i++) {
                                    if (i1[i] != -1) {
                                        li1.add(i1[i]);
                                    }
                                }
                                li2 = new ArrayList<Integer>();
                                for (int i = 0; i < i2.length; i++) {
                                    if (i2[i] != -1) {
                                        li2.add(i2[i]);
                                    }
                                }
                                li3 = new ArrayList<Integer>();
                                for (int i = 0; i < i3.length; i++) {
                                    if (i3[i] != -1) {
                                        li3.add(i3[i]);
                                    }
                                }
                                li4 = new ArrayList<Integer>();
                                for (int i = 0; i < i4.length; i++) {
                                    if (i4[i] != -1) {
                                        li4.add(i4[i]);
                                    }
                                }

                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po2);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "每项必须选号且只能选一个号", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 1://直选复式
                            if (a1 > 0 && a2 > 0 && a3 > 0 && a4 > 0) {
//                                Combination.zxds(i1, i2, i3, i4);
                                li1 = new ArrayList<Integer>();
                                for (int i = 0; i < i1.length; i++) {
                                    if (i1[i] != -1) {
                                        li1.add(i1[i]);
                                    }
                                }
                                li2 = new ArrayList<Integer>();
                                for (int i = 0; i < i2.length; i++) {
                                    if (i2[i] != -1) {
                                        li2.add(i2[i]);
                                    }
                                }
                                li3 = new ArrayList<Integer>();
                                for (int i = 0; i < i3.length; i++) {
                                    if (i3[i] != -1) {
                                        li3.add(i3[i]);
                                    }
                                }
                                li4 = new ArrayList<Integer>();
                                for (int i = 0; i < i4.length; i++) {
                                    if (i4[i] != -1) {
                                        li4.add(i4[i]);
                                    }
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po2);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "每项至少选一个号", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 2://直选组合
//                            if ((a1 > 0 && a2 > 0) || (a1 > 0 && a3 > 0) || (a1 > 0 && a4 > 0) ||
//                                    (a2 > 0 && a3 > 0) || (a2 > 0 && a4 > 0) || (a3 > 0 && a4 > 0)) {
                            if ((a1 > 0 && a2 > 0) || (a1 > 0 && a3 > 0) || (a1 > 0 && a4 > 0) || (a2 > 0 && a3 > 0)
                                    || (a2 > 0 && a4 > 0) || (a3 > 0 && a4 > 0)) {
//                                Combination.zxzh(i1, i2, i3, i4);
                                li1 = new ArrayList<Integer>();
                                for (int i = 0; i < i1.length; i++) {
                                    if (i1[i] != -1) {
                                        li1.add(i1[i]);
                                    }
                                }
                                li2 = new ArrayList<Integer>();
                                for (int i = 0; i < i2.length; i++) {
                                    if (i2[i] != -1) {
                                        li2.add(i2[i]);
                                    }
                                }
                                li3 = new ArrayList<Integer>();
                                for (int i = 0; i < i3.length; i++) {
                                    if (i3[i] != -1) {
                                        li3.add(i3[i]);
                                    }
                                }
                                li4 = new ArrayList<Integer>();
                                for (int i = 0; i < i4.length; i++) {
                                    if (i4[i] != -1) {
                                        li4.add(i4[i]);
                                    }
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po2);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "至少选择两种", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }

                } else if (c == 3) {//组选
                    switch (po3) {
                        case 0://组选24单式
                            if (a1 < 1 || a2 < 1 || a3 < 1 || a4 < 1) {
                                Toast.makeText(OneYongTanDuoGuan.this, "每项选择一个号码且与其它号码不能相同", Toast.LENGTH_SHORT).show();
                            }
                            for (int i = 0; i < i1.length; i++) {
                                if (i1[i] != -1) {
                                    for (int j = 0; j < i2.length; j++) {
                                        if (i2[j] != -1) {
                                            for (int k = 0; k < i3.length; k++) {
                                                if (i3[k] != -1) {
                                                    for (int x = 0; x < i4.length; x++) {
                                                        if (i4[x] != -1) {
                                                            if (i1[i] != i2[j] && i1[i] != i3[k] && i1[i] != i4[x]
                                                                    && i2[j] != i3[k] && i2[k] != i4[x] && i3[k] != i4[x]) {

//                                                                Combination.zx24ds(i1, i2, i3, i4);
                                                                li1 = new ArrayList<Integer>();
                                                                for (int a = 0; a < i1.length; a++) {
                                                                    if (i1[a] != -1) {
                                                                        li1.add(i1[a]);
                                                                    }
                                                                }
                                                                li2 = new ArrayList<Integer>();
                                                                for (int a = 0; a < i2.length; a++) {
                                                                    if (i2[a] != -1) {
                                                                        li2.add(i2[a]);
                                                                    }
                                                                }
                                                                li3 = new ArrayList<Integer>();
                                                                for (int a = 0; a < i3.length; a++) {
                                                                    if (i3[a] != -1) {
                                                                        li3.add(i3[a]);
                                                                    }
                                                                }
                                                                li4 = new ArrayList<Integer>();
                                                                for (int a = 0; a < i4.length; a++) {
                                                                    if (i4[a] != -1) {
                                                                        li4.add(i4[a]);
                                                                    }
                                                                }
                                                                setShared(li1, li2, li3, li4, s);
                                                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                                                intent.putExtra("彩种", 3);
                                                                intent.putExtra("分类", po3);
                                                                intent.putExtra("二类", c);
                                                                intent.putExtra("pausetime", pausetime);
                                                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                                                startActivity(intent);
                                                            } else {
                                                                Toast.makeText(OneYongTanDuoGuan.this, "每项选的号码与其它号码不能相同", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 1://组选24复式
                            if (a1 < 5) {
                                Toast.makeText(OneYongTanDuoGuan.this, "至少选5个号码", Toast.LENGTH_SHORT).show();
                            } else {
//                                Combination.zx24fs(i1);
                                li1 = new ArrayList<Integer>();
                                for (int a = 0; a < i1.length; a++) {
                                    if (i1[a] != -1) {
                                        li1.add(i1[a]);
                                    }
                                }

                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po3);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            }
                            break;
                        case 2://组选24胆拖
                            if (a1 < 1 || a1 > 3 || (a1 + a2) < 5) {
                                Toast.makeText(OneYongTanDuoGuan.this, "1~3个胆码，2~7个拖码，且胆码与拖码个数相加不能小于5", Toast.LENGTH_SHORT).show();
                            } else {
//                                Combination.zx24dt(i1, i2);
                                li1 = new ArrayList<Integer>();
                                for (int a = 0; a < i1.length; a++) {
                                    if (i1[a] != -1) {
                                        li1.add(i1[a]);
                                    }
                                }
                                li2 = new ArrayList<Integer>();
                                for (int a = 0; a < i2.length; a++) {
                                    if (i2[a] != -1) {
                                        li2.add(i2[a]);
                                    }
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po3);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            }
                            break;
                        case 3://组选12单式
                            if (a1 < 1 || a2 < 1 || a3 < 1 || a4 < 1) {
                                Toast.makeText(OneYongTanDuoGuan.this, "每项选择一个号码且有且只有两个号码相同", Toast.LENGTH_SHORT).show();
                            }
                            for (int i = 0; i < i1.length; i++) {
                                if (i1[i] != -1) {
                                    for (int j = 0; j < i2.length; j++) {
                                        if (i2[j] != -1) {
                                            for (int k = 0; k < i3.length; k++) {
                                                if (i3[k] != -1) {
                                                    for (int x = 0; x < i4.length; x++) {
                                                        if (i4[x] != -1) {
                                                            if (i1[i] == i2[j] || i1[i] == i3[k] || i1[i] == i4[x] || i2[j] == i3[k] ||
                                                                    i2[j] == i4[x] || i3[k] == i4[x]) {

//                                                                Combination.zx12ds(i1, i2, i3, i4);

                                                                li1 = new ArrayList<Integer>();
                                                                for (int a = 0; a < i1.length; a++) {
                                                                    if (i1[a] != -1) {
                                                                        li1.add(i1[a]);
                                                                    }
                                                                }
                                                                li2 = new ArrayList<Integer>();
                                                                for (int a = 0; a < i2.length; a++) {
                                                                    if (i2[a] != -1) {
                                                                        li2.add(i2[a]);
                                                                    }
                                                                }
                                                                li3 = new ArrayList<Integer>();
                                                                for (int a = 0; a < i3.length; a++) {
                                                                    if (i3[a] != -1) {
                                                                        li3.add(i3[a]);
                                                                    }
                                                                }
                                                                li4 = new ArrayList<Integer>();
                                                                for (int a = 0; a < i4.length; a++) {
                                                                    if (i4[a] != -1) {
                                                                        li4.add(i4[a]);
                                                                    }
                                                                }
                                                                setShared(li1, li2, li3, li4, s);
                                                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                                                intent.putExtra("彩种", 3);
                                                                intent.putExtra("分类", po3);
                                                                intent.putExtra("二类", c);
                                                                intent.putExtra("pausetime", pausetime);
                                                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                                                startActivity(intent);

                                                            } else {
                                                                Toast.makeText(OneYongTanDuoGuan.this, "每项选择一个号码且有且只有两个号码相同", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 4://组选12复式
                            if (a1 < 3) {
                                Toast.makeText(OneYongTanDuoGuan.this, "至少选三个号", Toast.LENGTH_SHORT).show();
                            } else {
//                                Combination.zx12fs(i1);
                                li1 = new ArrayList<Integer>();
                                for (int a = 0; a < i1.length; a++) {
                                    if (i1[a] != -1) {
                                        li1.add(i1[a]);
                                    }
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po3);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            }
                            break;
                        case 5://组选12胆拖
                            if (a1 > 0 && a1 < 3 && (a1 + a2) >= 4) {
//                                Combination.zx12dt(i1, i2);
                                li1 = new ArrayList<Integer>();
                                for (int a = 0; a < i1.length; a++) {
                                    if (i1[a] != -1) {
                                        li1.add(i1[a]);
                                    }
                                }
                                li2 = new ArrayList<Integer>();
                                for (int a = 0; a < i2.length; a++) {
                                    if (i2[a] != -1) {
                                        li2.add(i2[a]);
                                    }
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po3);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "1~2个胆码，2~7个拖码，且胆码与拖码个数和不小于4", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 6://组选12重胆拖
                            if (a1 == 1 && (a1 + a2) >= 4) {
//                                Combination.zx12cdt(i1, i2);
                                li1 = new ArrayList<Integer>();
                                for (int a = 0; a < i1.length; a++) {
                                    if (i1[a] != -1) {
                                        li1.add(i1[a]);
                                    }
                                }
                                li2 = new ArrayList<Integer>();
                                for (int a = 0; a < i2.length; a++) {
                                    if (i2[a] != -1) {
                                        li2.add(i2[a]);
                                    }
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po3);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "1个胆码，3~7个拖码，且胆码与拖码不能相同", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 7://组选6单式
                            if (a1 < 1 || a2 < 1 || a3 < 1 || a4 < 1) {
                                Toast.makeText(OneYongTanDuoGuan.this, "每项选择一个号码，且所选号码两两相同", Toast.LENGTH_SHORT).show();
                            } else {
                                for (int i = 0; i < i1.length; i++) {
                                    if (i1[i] != -1) {
                                        for (int j = 0; j < i2.length; j++) {
                                            if (i2[j] != -1) {
                                                for (int k = 0; k < i3.length; k++) {
                                                    if (i3[k] != -1) {
                                                        for (int l = 0; l < i4.length; l++) {
                                                            if (i4[l] != -1) {
                                                                if ((i1[i] == i2[j] && i3[k] == i4[l]) || (i1[i] == i3[k] && i2[j] == i4[l])
                                                                        || (i1[i] == i4[l] && i2[j] == i3[k])) {
//                                                                    Combination.zx6ds(i1, i2, i3, i4);
                                                                    li1 = new ArrayList<Integer>();
                                                                    for (int a = 0; a < i1.length; a++) {
                                                                        if (i1[a] != -1) {
                                                                            li1.add(i1[a]);
                                                                        }
                                                                    }
                                                                    li2 = new ArrayList<Integer>();
                                                                    for (int a = 0; a < i2.length; a++) {
                                                                        if (i2[a] != -1) {
                                                                            li2.add(i2[a]);
                                                                        }
                                                                    }
                                                                    li3 = new ArrayList<Integer>();
                                                                    for (int a = 0; a < i3.length; a++) {
                                                                        if (i3[a] != -1) {
                                                                            li3.add(i3[a]);
                                                                        }
                                                                    }
                                                                    li4 = new ArrayList<Integer>();
                                                                    for (int a = 0; a < i4.length; a++) {
                                                                        if (i4[a] != -1) {
                                                                            li4.add(i4[a]);
                                                                        }
                                                                    }

                                                                    setShared(li1, li2, li3, li4, s);
                                                                    Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                                                    intent.putExtra("彩种", 3);
                                                                    intent.putExtra("分类", po3);
                                                                    intent.putExtra("二类", c);
                                                                    intent.putExtra("pausetime", pausetime);
                                                                    intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                                                    startActivity(intent);
                                                                } else {
                                                                    Toast.makeText(OneYongTanDuoGuan.this, "每项选择一个号码，且所选号码两两相同", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 8://组选6复式
                            if (a1 < 3) {
                                Toast.makeText(OneYongTanDuoGuan.this, "至少选三个号码", Toast.LENGTH_SHORT).show();
                            } else {
//                                Combination.zx6fs(i1);
                                li1 = new ArrayList<Integer>();
                                for (int a = 0; a < i1.length; a++) {
                                    if (i1[a] != -1) {
                                        li1.add(i1[a]);
                                    }
                                }

                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po3);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            }
                            break;
                        case 9://组选6胆拖
                            if (a1 == 1) {
                                if (a2 < 2) {
                                    Toast.makeText(OneYongTanDuoGuan.this, "1个胆码，2~7个拖码，胆码与拖码不能相同", Toast.LENGTH_SHORT).show();
                                } else {
//                                    Combination.zx6dt(i1, i2);
                                    li1 = new ArrayList<Integer>();
                                    for (int a = 0; a < i1.length; a++) {
                                        if (i1[a] != -1) {
                                            li1.add(i1[a]);
                                        }
                                    }
                                    li2 = new ArrayList<Integer>();
                                    for (int a = 0; a < i2.length; a++) {
                                        if (i2[a] != -1) {
                                            li2.add(i2[a]);
                                        }
                                    }
                                    setShared(li1, li2, li3, li4, s);
                                    Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                    intent.putExtra("彩种", 3);
                                    intent.putExtra("分类", po3);
                                    intent.putExtra("二类", c);
                                    intent.putExtra("pausetime", pausetime);
                                    intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "1个胆码，2~7个拖码，胆码与拖码不能相同", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 10://组选4单式
                            if (a1 < 1 || a2 < 1 || a3 < 1 || a4 < 1) {
                                Toast.makeText(OneYongTanDuoGuan.this, "每项选择一个号码，且所选号码有且只有三个号码相同", Toast.LENGTH_SHORT).show();

                            } else {
                                for (int i = 0; i < i1.length; i++) {
                                    if (i1[i] != -1) {
                                        for (int j = 0; j < i2.length; j++) {
                                            if (i2[j] != -1) {
                                                for (int k = 0; k < i3.length; k++) {
                                                    if (i3[k] != -1) {
                                                        for (int l = 0; l < i4.length; l++) {
                                                            if (i4[l] != -1) {
                                                                if ((i1[i] == i2[j] && i1[i] == i3[k]) || (i1[i] == i3[k] && i1[i] == i4[l])
                                                                        || (i1[i] == i4[l] && i2[j] == i1[i]) || (i2[j] == i4[l] && i2[j] == i3[k])) {
//                                                                    Combination.zx4ds(i1, i2, i3, i4);
                                                                    li1 = new ArrayList<Integer>();
                                                                    for (int a = 0; a < i1.length; a++) {
                                                                        if (i1[a] != -1) {
                                                                            li1.add(i1[a]);
                                                                        }
                                                                    }
                                                                    li2 = new ArrayList<Integer>();
                                                                    for (int a = 0; a < i2.length; a++) {
                                                                        if (i2[a] != -1) {
                                                                            li2.add(i2[a]);
                                                                        }
                                                                    }
                                                                    li3 = new ArrayList<Integer>();
                                                                    for (int a = 0; a < i3.length; a++) {
                                                                        if (i3[a] != -1) {
                                                                            li3.add(i3[a]);
                                                                        }
                                                                    }
                                                                    li4 = new ArrayList<Integer>();
                                                                    for (int a = 0; a < i4.length; a++) {
                                                                        if (i4[a] != -1) {
                                                                            li4.add(i4[a]);
                                                                        }
                                                                    }

                                                                    setShared(li1, li2, li3, li4, s);
                                                                    Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                                                    intent.putExtra("彩种", 3);
                                                                    intent.putExtra("分类", po3);
                                                                    intent.putExtra("二类", c);
                                                                    intent.putExtra("pausetime", pausetime);
                                                                    intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                                                    startActivity(intent);
                                                                } else {
                                                                    Toast.makeText(OneYongTanDuoGuan.this, "每项选择一个号码，且所选号码有且只有三个号码相同", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 11://组选4复式
                            if (a1 < 2) {
                                Toast.makeText(OneYongTanDuoGuan.this, "至少选两个号", Toast.LENGTH_SHORT).show();
                            } else {
//                                Combination.zx4fs(i1);
                                li1 = new ArrayList<Integer>();
                                for (int a = 0; a < i1.length; a++) {
                                    if (i1[a] != -1) {
                                        li1.add(i1[a]);
                                    }
                                }

                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po3);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            }
                            break;
                        case 12://组选4胆拖
                        case 13://组选4重胆拖
                            if (a1 == 1) {
                                if (a2 < 2) {
                                    Toast.makeText(OneYongTanDuoGuan.this, "1个胆码，2~7个拖码，胆码与拖码不能相同", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (po3 == 12) {//组选4胆拖
//                                        Combination.zx4dt(i1, i2);

                                        li1 = new ArrayList<Integer>();
                                        for (int a = 0; a < i1.length; a++) {
                                            if (i1[a] != -1) {
                                                li1.add(i1[a]);
                                            }
                                        }
                                        li2 = new ArrayList<Integer>();
                                        for (int a = 0; a < i2.length; a++) {
                                            if (i2[a] != -1) {
                                                li2.add(i2[a]);
                                            }
                                        }
                                        setShared(li1, li2, li3, li4, s);
                                        Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                        intent.putExtra("彩种", 3);
                                        intent.putExtra("分类", po3);
                                        intent.putExtra("二类", c);
                                        intent.putExtra("pausetime", pausetime);
                                        intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                        startActivity(intent);

                                    } else {//组选4重胆拖
//                                        Combination.zx4cdt(i1, i2);
                                        li1 = new ArrayList<Integer>();
                                        for (int a = 0; a < i1.length; a++) {
                                            if (i1[a] != -1) {
                                                li1.add(i1[a]);
                                            }
                                        }
                                        li2 = new ArrayList<Integer>();
                                        for (int a = 0; a < i2.length; a++) {
                                            if (i2[a] != -1) {
                                                li2.add(i2[a]);
                                            }
                                        }
                                        setShared(li1, li2, li3, li4, s);
                                        Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                        intent.putExtra("彩种", 3);
                                        intent.putExtra("分类", po3);
                                        intent.putExtra("二类", c);
                                        intent.putExtra("pausetime", pausetime);
                                        intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                        startActivity(intent);
                                    }
                                }
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "1个胆码，2~7个拖码，胆码与拖码不能相同", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }

                } else if (c == 4) {//前三组选
                    switch (po4) {
                        case 0://前三组三
                            if (a1 >= 2) {
//                                Combination.q3z3(i1);
                                li1 = new ArrayList<Integer>();
                                for (int a = 0; a < i1.length; a++) {
                                    if (i1[a] != -1) {
                                        li1.add(i1[a]);
                                    }
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po4);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "至少选2个，最多选5个", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 1://前三组六
                            if (a1 >= 3) {
//                                Combination.q3z6(i1);
                                li1 = new ArrayList<Integer>();
                                for (int a = 0; a < i1.length; a++) {
                                    if (i1[a] != -1) {
                                        li1.add(i1[a]);
                                    }
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po4);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "至少选3个，最多选5个", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 2://任三组三
                            if (a1 >= 2 && cb == 3) {
//                                Combination.r3z3(i1, z, y, w, d);
                                li1 = new ArrayList<Integer>();
                                for (int a = 0; a < i1.length; a++) {
                                    if (i1[a] != -1) {
                                        li1.add(i1[a]);
                                    }
                                }
                                if (z == 1) {
                                    s = s + "自,";
                                }
                                if (y == 2) {
                                    s = s + "仰,";
                                }
                                if (w == 3) {
//                                    if (cb == 3) {
//                                        s = s + "蛙";
//                                    } else {
                                    s = s + "蛙,";
//                                    }
                                }
                                if (d == 4) {
                                    s = s + "碟";

                                }
//                                setsheared(s);
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po4);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "选择三项比赛，至少选2个号码，最多选5个号码", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 3://任六组六
                            if (a1 >= 3 && cb == 3) {
//                                Combination.r3z6(i1, z, y, w, d);
                                li1 = new ArrayList<Integer>();
                                for (int a = 0; a < i1.length; a++) {
                                    if (i1[a] != -1) {
                                        li1.add(i1[a]);
                                    }
                                }
                                if (z == 1) {
                                    s = s + "自,";
                                }
                                if (y == 2) {
                                    s = s + "仰,";
                                }
                                if (w == 3) {
//                                    if (cb == 3) {
//                                        s = s + "蛙";
//                                    } else {
                                    s = s + "蛙,";
//                                    }
                                }
                                if (d == 4) {
                                    s = s + "碟";
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po4);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            } else {
                                Toast.makeText(OneYongTanDuoGuan.this, "选择三项比赛，至少选3个号码，最多选5个号码", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }

                } else if (c == 5) {//新玩法
                    switch (po5) {
                        case 0://组选三码全包
                            if (a1 < 3) {
                                Toast.makeText(OneYongTanDuoGuan.this, "至少选是三个号", Toast.LENGTH_SHORT).show();
                            } else {
//                                Combination.zx3qb(i1);
                                li1 = new ArrayList<Integer>();
                                for (int a = 0; a < i1.length; a++) {
                                    if (i1[a] != -1) {
                                        li1.add(i1[a]);
                                    }
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po5);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            }
                            break;
                        case 1://组选二码全包
                            if (a1 < 2) {
                                Toast.makeText(OneYongTanDuoGuan.this, "至少选是二个号", Toast.LENGTH_SHORT).show();
                            } else {
//                                Combination.zx2qb(i1);
                                li1 = new ArrayList<Integer>();
                                for (int a = 0; a < i1.length; a++) {
                                    if (i1[a] != -1) {
                                        li1.add(i1[a]);
                                    }
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po5);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            }
                            break;
                        case 2://选四全包
                            if (po == 0) {//选四不重全包
                                if (a1 == 4) {
//                                    Combination.x4qb(0, i1, i2);
                                    li1 = new ArrayList<Integer>();
                                    for (int a = 0; a < i1.length; a++) {
                                        if (i1[a] != -1) {
                                            li1.add(i1[a]);
                                        }
                                    }
                                    setShared(li1, li2, li3, li4, s);
                                    Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                    intent.putExtra("彩种", 3);
                                    intent.putExtra("分类", po5);
                                    intent.putExtra("三分类", po);
                                    intent.putExtra("二类", c);
                                    intent.putExtra("pausetime", pausetime);
                                    intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(OneYongTanDuoGuan.this, "请选择四个号", Toast.LENGTH_SHORT).show();
                                }

                            } else if (po == 1) {//选四一对全包
                                if (a1 == 1 && a2 == 2) {
//                                    Combination.x4qb(1, i1, i2);
                                    li1 = new ArrayList<Integer>();
                                    for (int a = 0; a < i1.length; a++) {
                                        if (i1[a] != -1) {
                                            li1.add(i1[a]);
                                        }
                                    }
                                    li2 = new ArrayList<Integer>();
                                    for (int a = 0; a < i2.length; a++) {
                                        if (i2[a] != -1) {
                                            li2.add(i2[a]);
                                        }
                                    }
                                    setShared(li1, li2, li3, li4, s);
                                    Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                    intent.putExtra("彩种", 3);
                                    intent.putExtra("分类", po5);
                                    intent.putExtra("三分类", po);
                                    intent.putExtra("二类", c);
                                    intent.putExtra("pausetime", pausetime);
                                    intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(OneYongTanDuoGuan.this, "请选择一个双重号，两个不重号", Toast.LENGTH_SHORT).show();
                                }

                            } else if (po == 2) {//选四两对全包
                                if (a1 == 2) {
//                                    Combination.x4qb(2, i1, i2);
                                    li1 = new ArrayList<Integer>();
                                    for (int a = 0; a < i1.length; a++) {
                                        if (i1[a] != -1) {
                                            li1.add(i1[a]);
                                        }
                                    }
                                    setShared(li1, li2, li3, li4, s);
                                    Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                    intent.putExtra("彩种", 3);
                                    intent.putExtra("分类", po5);
                                    intent.putExtra("三分类", po);
                                    intent.putExtra("二类", c);
                                    intent.putExtra("pausetime", pausetime);
                                    intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(OneYongTanDuoGuan.this, "请选择四个号", Toast.LENGTH_SHORT).show();
                                }

                            } else if (po == 3) {//选四三条全包
                                if (a1 == 1 && a2 == 1) {
//                                    Combination.x4qb(3, i1, i2);
                                    li1 = new ArrayList<Integer>();
                                    for (int a = 0; a < i1.length; a++) {
                                        if (i1[a] != -1) {
                                            li1.add(i1[a]);
                                        }
                                    }
                                    li2 = new ArrayList<Integer>();
                                    for (int a = 0; a < i2.length; a++) {
                                        if (i2[a] != -1) {
                                            li2.add(i2[a]);
                                        }
                                    }
                                    setShared(li1, li2, li3, li4, s);
                                    Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                    intent.putExtra("彩种", 3);
                                    intent.putExtra("分类", po5);
                                    intent.putExtra("三分类", po);
                                    intent.putExtra("二类", c);
                                    intent.putExtra("pausetime", pausetime);
                                    intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(OneYongTanDuoGuan.this, "请选择一个三重号，一个不重号", Toast.LENGTH_SHORT).show();
                                }
                            }
                            break;
                        case 3://重号全包
                            if (a1 < 1) {
                                Toast.makeText(OneYongTanDuoGuan.this, "至少选是一个号", Toast.LENGTH_SHORT).show();
                            } else {
//                                Combination.chqb(i1);
                                li1 = new ArrayList<Integer>();
                                for (int a = 0; a < i1.length; a++) {
                                    if (i1[a] != -1) {
                                        li1.add(i1[a]);
                                    }
                                }
                                setShared(li1, li2, li3, li4, s);
                                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                                intent.putExtra("彩种", 3);
                                intent.putExtra("分类", po5);
                                intent.putExtra("二类", c);
                                intent.putExtra("pausetime", pausetime);
                                intent.putExtra("kjrq", Integer.parseInt(kjrq) + 1);
                                startActivity(intent);
                            }
                            break;
                    }
                }
                break;
        }
    }

    /**
     * 快三分类玩法弹出框
     */
    private void showXingBieDuiHuaKuang() {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(OneYongTanDuoGuan.this).inflate(
                R.layout.swimming_classify, null);

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

        adapter1 = new OneGridViewAdapter(data2(), OneYongTanDuoGuan.this);
        if (c == 1) {
            adapter1.setSeclection(po1);
        }
        GridView gridView1 = (GridView) contentView.findViewById(R.id.gridview1);
        gridView1.setAdapter(adapter1);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                po1 = position;
                c = 1;
                adapter1.setSeclection(po1);
                tv_title.setText(data2().get(position).get("text"));
                if (position == 0) {//任选一
                    clear();
                    ziyouyong.setText("自由泳");
                    yangyong.setText("仰泳");
                    gv_qb.setVisibility(View.GONE);
                    rszs_layout.setVisibility(View.GONE);
                    yangyong.setVisibility(View.VISIBLE);
                    yangyong_layout.setVisibility(View.VISIBLE);
                    wayong.setVisibility(View.VISIBLE);
                    wayong_layout.setVisibility(View.VISIBLE);
                    dieyong.setVisibility(View.VISIBLE);
                    dieyong_layout.setVisibility(View.VISIBLE);
                    dieyong_view.setVisibility(View.VISIBLE);
                    wayong_view.setVisibility(View.VISIBLE);
                    tv_text.setText("至少选一项");
                } else if (position == 1) {//任选二
                    clear();
                    ziyouyong.setText("自由泳");
                    yangyong.setText("仰泳");
                    wayong.setText("蛙泳");
                    gv_qb.setVisibility(View.GONE);
                    rszs_layout.setVisibility(View.GONE);
                    yangyong.setVisibility(View.VISIBLE);
                    yangyong_layout.setVisibility(View.VISIBLE);
                    wayong.setVisibility(View.VISIBLE);
                    wayong_layout.setVisibility(View.VISIBLE);
                    dieyong.setVisibility(View.VISIBLE);
                    dieyong_layout.setVisibility(View.VISIBLE);
                    dieyong_view.setVisibility(View.VISIBLE);
                    wayong_view.setVisibility(View.VISIBLE);
                    tv_text.setText("至少选两项");
                } else if (position == 2) {//任选二全包
                    clear();
                    ziyouyong.setText("第一位");
                    yangyong.setText("第二位");
                    gv_qb.setVisibility(View.GONE);
                    rszs_layout.setVisibility(View.GONE);
                    wayong.setVisibility(View.GONE);
                    wayong_layout.setVisibility(View.GONE);
                    dieyong.setVisibility(View.GONE);
                    dieyong_layout.setVisibility(View.GONE);
                    dieyong_view.setVisibility(View.GONE);
                    wayong_view.setVisibility(View.GONE);
                    tv_text.setText("没项至少选一个号");
                } else if (position == 3) {//任选三
                    clear();
                    ziyouyong.setText("自由泳");
                    yangyong.setText("仰泳");
                    gv_qb.setVisibility(View.GONE);
                    rszs_layout.setVisibility(View.GONE);
                    wayong.setVisibility(View.VISIBLE);
                    wayong_layout.setVisibility(View.VISIBLE);
                    dieyong.setVisibility(View.VISIBLE);
                    dieyong_layout.setVisibility(View.VISIBLE);
                    dieyong_view.setVisibility(View.VISIBLE);
                    wayong_view.setVisibility(View.VISIBLE);
                    tv_text.setText("至少选三项");
                } else if (position == 4) {//任选三全包
                    clear();
                    ziyouyong.setText("第一位");
                    yangyong.setText("第二位");
                    wayong.setText("第三位");
                    gv_qb.setVisibility(View.GONE);
                    rszs_layout.setVisibility(View.GONE);
                    wayong.setVisibility(View.VISIBLE);
                    wayong_layout.setVisibility(View.VISIBLE);
                    wayong_view.setVisibility(View.VISIBLE);
                    dieyong.setVisibility(View.GONE);
                    dieyong_layout.setVisibility(View.GONE);
                    dieyong_view.setVisibility(View.GONE);
                    tv_text.setText("没项至少选一个号");
                }
                popupWindow.dismiss();
                iv_top.setImageResource(R.mipmap.top_2);
                b = true;
            }
        });

        adapter2 = new OneGridViewAdapter(data3(), OneYongTanDuoGuan.this);
        if (c == 2) {
            adapter2.setSeclection(po2);
        }
        GridView gridView2 = (GridView) contentView.findViewById(R.id.gridview2);
        gridView2.setAdapter(adapter2);
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                po2 = position;
                c = 2;
                adapter2.setSeclection(po2);
                tv_title.setText(data3().get(position).get("text"));
                clear();
                ziyouyong.setText("自由泳");
                yangyong.setText("仰泳");
                gv_qb.setVisibility(View.GONE);
                rszs_layout.setVisibility(View.GONE);
                wayong.setVisibility(View.VISIBLE);
                wayong_layout.setVisibility(View.VISIBLE);
                dieyong.setVisibility(View.VISIBLE);
                dieyong_layout.setVisibility(View.VISIBLE);
                dieyong_view.setVisibility(View.VISIBLE);
                wayong_view.setVisibility(View.VISIBLE);
                if (position == 0) {
                    tv_text.setText("每项只能选一个号");
                } else if (position == 1) {
                    tv_text.setText("每项至少选一个号");
                } else if (position == 2) {
                    tv_text.setText("至少选择两项");
                }

                popupWindow.dismiss();
                iv_top.setImageResource(R.mipmap.top_2);
                b = true;
            }
        });

        adapter3 = new OneGridViewAdapter(data4(), OneYongTanDuoGuan.this);
        if (c == 3) {
            adapter3.setSeclection(po3);
        }
        GridView gridView3 = (GridView) contentView.findViewById(R.id.gridview3);
        gridView3.setAdapter(adapter3);
        gridView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                po3 = position;
                c = 3;
                adapter3.setSeclection(po3);
                tv_title.setText(data4().get(position).get("text"));
                switch (position) {
                    case 0://组选24单式
                        clear();
                        ziyouyong.setText("自由泳");
                        yangyong.setText("仰泳");
                        dieyong.setText("蝶泳");
                        gv_qb.setVisibility(View.GONE);
                        rszs_layout.setVisibility(View.GONE);
                        yangyong.setVisibility(View.VISIBLE);
                        yangyong_layout.setVisibility(View.VISIBLE);
                        wayong.setVisibility(View.VISIBLE);
                        wayong_layout.setVisibility(View.VISIBLE);
                        dieyong.setVisibility(View.VISIBLE);
                        dieyong_layout.setVisibility(View.VISIBLE);
                        dieyong_view.setVisibility(View.VISIBLE);
                        wayong_view.setVisibility(View.VISIBLE);
                        yangyong_view.setVisibility(View.VISIBLE);
                        tv_text.setText("每项选择一个号且与其它号不同");
                        break;
                    case 1://组选24复式
                        clear();
                        ziyouyong.setText("选号");
                        gv_qb.setVisibility(View.GONE);
                        rszs_layout.setVisibility(View.GONE);
                        yangyong.setVisibility(View.GONE);
                        yangyong_layout.setVisibility(View.GONE);
                        wayong.setVisibility(View.GONE);
                        wayong_layout.setVisibility(View.GONE);
                        dieyong.setVisibility(View.GONE);
                        dieyong_layout.setVisibility(View.GONE);
                        dieyong_view.setVisibility(View.GONE);
                        wayong_view.setVisibility(View.GONE);
                        yangyong_view.setVisibility(View.GONE);
                        tv_text.setText("至少选择5个号码");
                        break;
                    case 2://组选24胆拖
                        clear();
                        ziyouyong.setText("胆码");
                        yangyong.setText("拖码");
                        gv_qb.setVisibility(View.GONE);
                        rszs_layout.setVisibility(View.GONE);
                        yangyong.setVisibility(View.VISIBLE);
                        yangyong_layout.setVisibility(View.VISIBLE);
                        yangyong_view.setVisibility(View.VISIBLE);
                        wayong.setVisibility(View.GONE);
                        wayong_layout.setVisibility(View.GONE);
                        dieyong.setVisibility(View.GONE);
                        dieyong_layout.setVisibility(View.GONE);
                        dieyong_view.setVisibility(View.GONE);
                        wayong_view.setVisibility(View.GONE);
                        tv_text.setText("1~3个胆码，2~7个拖码，胆码与拖码不能相同");
                        break;
                    case 3://组选12单式
                        clear();
                        ziyouyong.setText("自由泳");
                        yangyong.setText("仰泳");
                        dieyong.setText("蝶泳");
                        gv_qb.setVisibility(View.GONE);
                        rszs_layout.setVisibility(View.GONE);
                        yangyong.setVisibility(View.VISIBLE);
                        yangyong_layout.setVisibility(View.VISIBLE);
                        wayong.setVisibility(View.VISIBLE);
                        wayong_layout.setVisibility(View.VISIBLE);
                        dieyong.setVisibility(View.VISIBLE);
                        dieyong_layout.setVisibility(View.VISIBLE);
                        dieyong_view.setVisibility(View.VISIBLE);
                        wayong_view.setVisibility(View.VISIBLE);
                        yangyong_view.setVisibility(View.VISIBLE);
                        tv_text.setText("每项选择1个号码且所选号码中有且只有两项的号码相同");
                        break;
                    case 4://组选12复式
                        clear();
                        ziyouyong.setText("选号");
                        gv_qb.setVisibility(View.GONE);
                        rszs_layout.setVisibility(View.GONE);
                        yangyong.setVisibility(View.GONE);
                        yangyong_layout.setVisibility(View.GONE);
                        wayong.setVisibility(View.GONE);
                        wayong_layout.setVisibility(View.GONE);
                        dieyong.setVisibility(View.GONE);
                        dieyong_layout.setVisibility(View.GONE);
                        dieyong_view.setVisibility(View.GONE);
                        wayong_view.setVisibility(View.GONE);
                        yangyong_view.setVisibility(View.GONE);
                        tv_text.setText("至少选择3个号码");
                        break;
                    case 5://组选12胆拖
                        clear();
                        ziyouyong.setText("胆码");
                        yangyong.setText("拖码");
                        gv_qb.setVisibility(View.GONE);
                        rszs_layout.setVisibility(View.GONE);
                        yangyong.setVisibility(View.VISIBLE);
                        yangyong_layout.setVisibility(View.VISIBLE);
                        yangyong_view.setVisibility(View.VISIBLE);
                        wayong.setVisibility(View.GONE);
                        wayong_layout.setVisibility(View.GONE);
                        dieyong.setVisibility(View.GONE);
                        dieyong_layout.setVisibility(View.GONE);
                        dieyong_view.setVisibility(View.GONE);
                        wayong_view.setVisibility(View.GONE);
                        tv_text.setText("1~2个胆码，2~7个拖码，胆码与拖码不能相同且胆码与拖码个数和不能小于4");
                        break;
                    case 6://组选12重胆拖
                        clear();
                        ziyouyong.setText("胆码");
                        yangyong.setText("拖码");
                        gv_qb.setVisibility(View.GONE);
                        rszs_layout.setVisibility(View.GONE);
                        yangyong.setVisibility(View.VISIBLE);
                        yangyong_layout.setVisibility(View.VISIBLE);
                        yangyong_view.setVisibility(View.VISIBLE);
                        wayong.setVisibility(View.GONE);
                        wayong_layout.setVisibility(View.GONE);
                        dieyong.setVisibility(View.GONE);
                        dieyong_layout.setVisibility(View.GONE);
                        dieyong_view.setVisibility(View.GONE);
                        wayong_view.setVisibility(View.GONE);
                        tv_text.setText("1个胆码，3~7个拖码，胆码与拖码不能相同");
                        break;
                    case 7://组选6单式
                        clear();
                        ziyouyong.setText("自由泳");
                        yangyong.setText("仰泳");
                        dieyong.setText("蝶泳");
                        gv_qb.setVisibility(View.GONE);
                        rszs_layout.setVisibility(View.GONE);
                        yangyong.setVisibility(View.VISIBLE);
                        yangyong_layout.setVisibility(View.VISIBLE);
                        wayong.setVisibility(View.VISIBLE);
                        wayong_layout.setVisibility(View.VISIBLE);
                        dieyong.setVisibility(View.VISIBLE);
                        dieyong_layout.setVisibility(View.VISIBLE);
                        dieyong_view.setVisibility(View.VISIBLE);
                        wayong_view.setVisibility(View.VISIBLE);
                        yangyong_view.setVisibility(View.VISIBLE);
                        tv_text.setText("每项选择1个号码且所选号码中两两相同");
                        break;
                    case 8://组选6复式
                        clear();
                        ziyouyong.setText("选号");
                        gv_qb.setVisibility(View.GONE);
                        rszs_layout.setVisibility(View.GONE);
                        yangyong.setVisibility(View.GONE);
                        yangyong_layout.setVisibility(View.GONE);
                        wayong.setVisibility(View.GONE);
                        wayong_layout.setVisibility(View.GONE);
                        dieyong.setVisibility(View.GONE);
                        dieyong_layout.setVisibility(View.GONE);
                        dieyong_view.setVisibility(View.GONE);
                        wayong_view.setVisibility(View.GONE);
                        yangyong_view.setVisibility(View.GONE);
                        tv_text.setText("至少选择3个号码");
                        break;
                    case 9://组选6胆拖
                        clear();
                        ziyouyong.setText("胆码");
                        yangyong.setText("拖码");
                        gv_qb.setVisibility(View.GONE);
                        rszs_layout.setVisibility(View.GONE);
                        yangyong.setVisibility(View.VISIBLE);
                        yangyong_layout.setVisibility(View.VISIBLE);
                        yangyong_view.setVisibility(View.VISIBLE);
                        wayong.setVisibility(View.GONE);
                        wayong_layout.setVisibility(View.GONE);
                        dieyong.setVisibility(View.GONE);
                        dieyong_layout.setVisibility(View.GONE);
                        dieyong_view.setVisibility(View.GONE);
                        wayong_view.setVisibility(View.GONE);
                        tv_text.setText("1个胆码，2~7个拖码，胆码与拖码不能相同");
                        break;
                    case 10://组选4单式
                        clear();
                        ziyouyong.setText("自由泳");
                        yangyong.setText("仰泳");
                        dieyong.setText("蝶泳");
                        gv_qb.setVisibility(View.GONE);
                        rszs_layout.setVisibility(View.GONE);
                        yangyong.setVisibility(View.VISIBLE);
                        yangyong_layout.setVisibility(View.VISIBLE);
                        wayong.setVisibility(View.VISIBLE);
                        wayong_layout.setVisibility(View.VISIBLE);
                        dieyong.setVisibility(View.VISIBLE);
                        dieyong_layout.setVisibility(View.VISIBLE);
                        dieyong_view.setVisibility(View.VISIBLE);
                        wayong_view.setVisibility(View.VISIBLE);
                        yangyong_view.setVisibility(View.VISIBLE);
                        tv_text.setText("每项选择1个号码且所选号码中有且只有三个号码相同");
                        break;
                    case 11://组选4复式
                        clear();
                        ziyouyong.setText("选号");
                        gv_qb.setVisibility(View.GONE);
                        rszs_layout.setVisibility(View.GONE);
                        yangyong.setVisibility(View.GONE);
                        yangyong_layout.setVisibility(View.GONE);
                        wayong.setVisibility(View.GONE);
                        wayong_layout.setVisibility(View.GONE);
                        dieyong.setVisibility(View.GONE);
                        dieyong_layout.setVisibility(View.GONE);
                        dieyong_view.setVisibility(View.GONE);
                        wayong_view.setVisibility(View.GONE);
                        yangyong_view.setVisibility(View.GONE);
                        tv_text.setText("至少选择2个号码");
                        break;
                    case 12://组选4胆拖
                        clear();
                        ziyouyong.setText("胆码");
                        yangyong.setText("拖码");
                        gv_qb.setVisibility(View.GONE);
                        rszs_layout.setVisibility(View.GONE);
                        yangyong.setVisibility(View.VISIBLE);
                        yangyong_layout.setVisibility(View.VISIBLE);
                        yangyong_view.setVisibility(View.VISIBLE);
                        wayong.setVisibility(View.GONE);
                        wayong_layout.setVisibility(View.GONE);
                        dieyong.setVisibility(View.GONE);
                        dieyong_layout.setVisibility(View.GONE);
                        dieyong_view.setVisibility(View.GONE);
                        wayong_view.setVisibility(View.GONE);
                        tv_text.setText("1个胆码，2~7个拖码，胆码与拖码不能相同");
                        break;
                    case 13://组选4重胆拖
                        clear();
                        ziyouyong.setText("胆码");
                        yangyong.setText("拖码");
                        gv_qb.setVisibility(View.GONE);
                        rszs_layout.setVisibility(View.GONE);
                        yangyong.setVisibility(View.VISIBLE);
                        yangyong_layout.setVisibility(View.VISIBLE);
                        yangyong_view.setVisibility(View.VISIBLE);
                        wayong.setVisibility(View.GONE);
                        wayong_layout.setVisibility(View.GONE);
                        dieyong.setVisibility(View.GONE);
                        dieyong_layout.setVisibility(View.GONE);
                        dieyong_view.setVisibility(View.GONE);
                        wayong_view.setVisibility(View.GONE);
                        tv_text.setText("1个胆码，2~7个拖码，胆码与拖码不能相同");
                        break;
                }
                popupWindow.dismiss();
                iv_top.setImageResource(R.mipmap.top_2);
                b = true;
            }
        });

        adapter4 = new OneGridViewAdapter(data5(), OneYongTanDuoGuan.this);
        if (c == 4) {
            adapter4.setSeclection(po4);
        }
        GridView gridView4 = (GridView) contentView.findViewById(R.id.gridview4);
        gridView4.setAdapter(adapter4);
        gridView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                po4 = position;
                c = 4;
                adapter4.setSeclection(po4);
                tv_title.setText(data5().get(position).get("text"));
                if (position == 0) {//前三组选组三
                    clear();
                    ziyouyong.setText("选号");
                    gv_qb.setVisibility(View.GONE);
                    rszs_layout.setVisibility(View.GONE);
                    yangyong.setVisibility(View.GONE);
                    yangyong_layout.setVisibility(View.GONE);
                    wayong.setVisibility(View.GONE);
                    wayong_layout.setVisibility(View.GONE);
                    dieyong.setVisibility(View.GONE);
                    dieyong_layout.setVisibility(View.GONE);
                    dieyong_view.setVisibility(View.GONE);
                    wayong_view.setVisibility(View.GONE);
                    yangyong_view.setVisibility(View.GONE);
                    tv_text.setText("至少选择2个号码，最多选择5个");
                } else if (position == 1) {//前三组选组六
                    clear();
                    ziyouyong.setText("选号");
                    gv_qb.setVisibility(View.GONE);
                    rszs_layout.setVisibility(View.GONE);
                    yangyong.setVisibility(View.GONE);
                    yangyong_layout.setVisibility(View.GONE);
                    wayong.setVisibility(View.GONE);
                    wayong_layout.setVisibility(View.GONE);
                    dieyong.setVisibility(View.GONE);
                    dieyong_layout.setVisibility(View.GONE);
                    dieyong_view.setVisibility(View.GONE);
                    wayong_view.setVisibility(View.GONE);
                    yangyong_view.setVisibility(View.GONE);
                    tv_text.setText("至少选择3个号码，最多选择5个");
                } else if (position == 2) {//任意三项组三
                    clear();
                    ziyouyong.setText("选号");
                    gv_qb.setVisibility(View.GONE);
                    rszs_layout.setVisibility(View.VISIBLE);
                    yangyong.setVisibility(View.GONE);
                    yangyong_layout.setVisibility(View.GONE);
                    wayong.setVisibility(View.GONE);
                    wayong_layout.setVisibility(View.GONE);
                    dieyong.setVisibility(View.GONE);
                    dieyong_layout.setVisibility(View.GONE);
                    dieyong_view.setVisibility(View.GONE);
                    wayong_view.setVisibility(View.GONE);
                    yangyong_view.setVisibility(View.GONE);
                    tv_text.setText("从四项比赛选择三项，至少选择2个号码，最多选择5个");

                } else if (position == 3) {//任意三项组六
                    clear();
                    ziyouyong.setText("选号");
                    gv_qb.setVisibility(View.GONE);
                    rszs_layout.setVisibility(View.VISIBLE);
                    yangyong.setVisibility(View.GONE);
                    yangyong_layout.setVisibility(View.GONE);
                    wayong.setVisibility(View.GONE);
                    wayong_layout.setVisibility(View.GONE);
                    dieyong.setVisibility(View.GONE);
                    dieyong_layout.setVisibility(View.GONE);
                    dieyong_view.setVisibility(View.GONE);
                    wayong_view.setVisibility(View.GONE);
                    yangyong_view.setVisibility(View.GONE);
                    tv_text.setText("从四项比赛选择三项，至少选择3个号码，最多选择5个");
                }
                popupWindow.dismiss();
                iv_top.setImageResource(R.mipmap.top_2);
                b = true;
            }
        });

        adapter5 = new OneGridViewAdapter(data6(), OneYongTanDuoGuan.this);
        if (c == 5) {
            adapter5.setSeclection(po5);
        }
        GridView gridView5 = (GridView) contentView.findViewById(R.id.gridview5);
        gridView5.setAdapter(adapter5);
        gridView5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                po5 = position;
                c = 5;
                adapter5.setSeclection(po5);
                tv_title.setText(data6().get(position).get("text"));
                if (position == 0 || position == 1 || position == 3) {//组选三码全包，组选二码全包，重号全包
                    clear();
                    ziyouyong.setText("选号");
                    gv_qb.setVisibility(View.GONE);
                    rszs_layout.setVisibility(View.GONE);
                    yangyong.setVisibility(View.GONE);
                    yangyong_layout.setVisibility(View.GONE);
                    wayong.setVisibility(View.GONE);
                    wayong_layout.setVisibility(View.GONE);
                    dieyong.setVisibility(View.GONE);
                    dieyong_layout.setVisibility(View.GONE);
                    dieyong_view.setVisibility(View.GONE);
                    wayong_view.setVisibility(View.GONE);
                    yangyong_view.setVisibility(View.GONE);
                    if (position == 0) {
                        tv_text.setText("至少选择三个号码");
                    } else if (position == 1) {
                        tv_text.setText("至少选择二个号码");
                    } else if (position == 3) {
                        tv_text.setText("至少选择一个号码");
                    }

                } else if (position == 2) {//选四全包
                    clear();
                    ziyouyong.setText("选号");
                    gv_qb.setVisibility(View.VISIBLE);
                    yangyong.setVisibility(View.GONE);
                    yangyong_layout.setVisibility(View.GONE);
                    yangyong_view.setVisibility(View.GONE);
                    rszs_layout.setVisibility(View.GONE);
                    wayong.setVisibility(View.GONE);
                    wayong_layout.setVisibility(View.GONE);
                    dieyong.setVisibility(View.GONE);
                    dieyong_layout.setVisibility(View.GONE);
                    dieyong_view.setVisibility(View.GONE);
                    wayong_view.setVisibility(View.GONE);
                    tv_text.setText("任选四个号码投注");

                    final OneXSqbAdapter adapter = new OneXSqbAdapter(data7(), OneYongTanDuoGuan.this);
                    gv_qb.setAdapter(adapter);
                    adapter.setSeclection(0);
                    gv_qb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            po = position;
                            adapter.setSeclection(position);
                            if (po == 0 || po == 2) {//选四不重全包\选四两对全包
                                clear();
                                ziyouyong.setText("选号");
                                yangyong.setVisibility(View.GONE);
                                yangyong_layout.setVisibility(View.GONE);
                                yangyong_view.setVisibility(View.GONE);
                                if (po == 0) {
                                    tv_text.setText("任选四个号码投注");
                                } else if (po == 2) {
                                    tv_text.setText("任选二个号码投注");
                                }
                            } else if (po == 1) {//选四一对全包
                                clear();
                                ziyouyong.setText("双重号");
                                yangyong.setText("不重号");
                                yangyong.setVisibility(View.VISIBLE);
                                yangyong_layout.setVisibility(View.VISIBLE);
                                yangyong_view.setVisibility(View.VISIBLE);
                                tv_text.setText("双重号选一个，不重号选两个");
                            } else if (po == 3) {//选四三条全包
                                clear();
                                ziyouyong.setText("三重号");
                                yangyong.setText("不重号");
                                yangyong.setVisibility(View.VISIBLE);
                                yangyong_layout.setVisibility(View.VISIBLE);
                                yangyong_view.setVisibility(View.VISIBLE);
                                tv_text.setText("三重号选一个，不重号选一个");
                            }
                            rszs_layout.setVisibility(View.GONE);
                            wayong.setVisibility(View.GONE);
                            wayong_layout.setVisibility(View.GONE);
                            dieyong.setVisibility(View.GONE);
                            dieyong_layout.setVisibility(View.GONE);
                            dieyong_view.setVisibility(View.GONE);
                            wayong_view.setVisibility(View.GONE);
                        }
                    });

                }
                popupWindow.dismiss();
                iv_top.setImageResource(R.mipmap.top_2);
                b = true;
            }
        });
    }

    /**
     * 获取shared中的号码
     */
    private Map<String, Object> getSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        List<Integer> yangyonglist = new ArrayList<Integer>();
        List<Integer> wayonglist = new ArrayList<Integer>();
        List<Integer> dieyonglist = new ArrayList<Integer>();

        int item = sharedPreferences.getInt("item", 0);

        int ziyouyongsize = sharedPreferences.getInt("ziyouyongsize" + item, 0);//百位的个数
        for (int j = 0; j < ziyouyongsize; j++) {
            ziyouyonglist.add(sharedPreferences.getInt("ziyouyong" + item + j, 0));
        }
        int yangyongsize = sharedPreferences.getInt("yangyongsize" + item, 0);//十位的个数
        for (int j = 0; j < yangyongsize; j++) {
            yangyonglist.add(sharedPreferences.getInt("yangyong" + item + j, 0));
        }
        int wayongsize = sharedPreferences.getInt("wayongsize" + item, 0);//十位的个数
        for (int j = 0; j < wayongsize; j++) {
            wayonglist.add(sharedPreferences.getInt("wayong" + item + j, 0));
        }
        int dieyongsize = sharedPreferences.getInt("dieyongsize" + item, 0);//十位的个数
        for (int j = 0; j < dieyongsize; j++) {
            dieyonglist.add(sharedPreferences.getInt("dieyong" + item + j, 0));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        map.put("yangyong", yangyonglist);
        map.put("wayong", wayonglist);
        map.put("dieyong", dieyonglist);
        return map;
    }

    /**
     * 将选中的号码保存
     */
    private void setShared(List<Integer> li1, List<Integer> li2, List<Integer> li3, List<Integer> li4, String s) {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int time = sharedPreferences.getInt("time", 0);
        int item = sharedPreferences.getInt("item", 0);
        if (c == 5) {//新玩法
            if (po5 == 0 || po5 == 1 || po5 == 3) {
                if (item >= 0) {
                    editor.putInt("ziyouyongsize" + item, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + item + i, li1.get(i));
                    }

                } else {
                    editor.putInt("ziyouyongsize" + time, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + time + i, li1.get(i));
                    }

                    editor.putInt("time", time + 1);
                }
            } else if (po5 == 2) {
                if (po == 0 || po == 2) {//选四不重全包//选四两对全包
                    if (item >= 0) {
                        editor.putInt("ziyouyongsize" + item, li1.size());
                        for (int i = 0; i < li1.size(); i++) {
                            editor.putInt("ziyouyong" + item + i, li1.get(i));
                        }
                    } else {
                        editor.putInt("ziyouyongsize" + time, li1.size());
                        for (int i = 0; i < li1.size(); i++) {
                            editor.putInt("ziyouyong" + time + i, li1.get(i));
                        }
                        editor.putInt("time", time + 1);
                    }
                } else if (po == 1 || po == 3) {//选四一对全包//选四三条全包
                    if (item >= 0) {
                        editor.putInt("ziyouyongsize" + item, li1.size());
                        for (int i = 0; i < li1.size(); i++) {
                            editor.putInt("ziyouyong" + item + i, li1.get(i));
                        }
                        editor.putInt("yangyongsize" + item, li2.size());
                        for (int i = 0; i < li2.size(); i++) {
                            editor.putInt("yangyong" + item + i, li2.get(i));
                        }
                    } else {
                        editor.putInt("ziyouyongsize" + time, li1.size());
                        for (int i = 0; i < li1.size(); i++) {
                            editor.putInt("ziyouyong" + time + i, li1.get(i));
                        }
                        editor.putInt("yangyongsize" + time, li2.size());
                        for (int i = 0; i < li2.size(); i++) {
                            editor.putInt("yangyong" + time + i, li2.get(i));
                        }
                        editor.putInt("time", time + 1);
                    }
                }
            }
        } else if (c == 4) {//前三组选
            if (po4 == 0 || po4 == 1) {
                if (item >= 0) {
                    editor.putInt("ziyouyongsize" + item, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + item + i, li1.get(i));
                    }

                } else {
                    editor.putInt("ziyouyongsize" + time, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + time + i, li1.get(i));
                    }

                    editor.putInt("time", time + 1);
                }
            } else if (po4 == 2 || po4 == 3) {
                if (item >= 0) {
                    editor.putString("type11" + item, s);

                    editor.putInt("ziyouyongsize" + item, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + item + i, li1.get(i));
                    }
                } else {
                    editor.putString("type11" + time, s);

                    editor.putInt("ziyouyongsize" + time, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + time + i, li1.get(i));
                    }
                    editor.putInt("time", time + 1);
                }

            }

        } else if (c == 1) {//任选
            if (po1 == 0 || po1 == 1 || po1 == 3) {//任选一
                if (item >= 0) {
                    editor.putInt("ziyouyongsize" + item, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + item + i, li1.get(i));
                    }
                    editor.putInt("yangyongsize" + item, li2.size());
                    for (int i = 0; i < li2.size(); i++) {
                        editor.putInt("yangyong" + item + i, li2.get(i));
                    }
                    editor.putInt("wayongsize" + item, li3.size());
                    for (int i = 0; i < li3.size(); i++) {
                        editor.putInt("wayong" + item + i, li3.get(i));
                    }
                    editor.putInt("dieyongsize" + item, li4.size());
                    for (int i = 0; i < li4.size(); i++) {
                        editor.putInt("dieyong" + item + i, li4.get(i));
                    }
                } else {
                    editor.putInt("ziyouyongsize" + time, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + time + i, li1.get(i));
                    }
                    editor.putInt("yangyongsize" + time, li2.size());
                    for (int i = 0; i < li2.size(); i++) {
                        editor.putInt("yangyong" + time + i, li2.get(i));
                    }
                    editor.putInt("wayongsize" + time, li3.size());
                    for (int i = 0; i < li3.size(); i++) {
                        editor.putInt("wayong" + time + i, li3.get(i));
                    }
                    editor.putInt("dieyongsize" + time, li4.size());
                    for (int i = 0; i < li4.size(); i++) {
                        editor.putInt("dieyong" + time + i, li4.get(i));
                    }
                    editor.putInt("time", time + 1);
                }
            } else if (po1 == 2) {
                if (item >= 0) {
                    editor.putInt("ziyouyongsize" + item, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + item + i, li1.get(i));
                    }
                    editor.putInt("yangyongsize" + item, li2.size());
                    for (int i = 0; i < li2.size(); i++) {
                        editor.putInt("yangyong" + item + i, li2.get(i));
                    }

                } else {
                    editor.putInt("ziyouyongsize" + time, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + time + i, li1.get(i));
                    }
                    editor.putInt("yangyongsize" + time, li2.size());
                    for (int i = 0; i < li2.size(); i++) {
                        editor.putInt("yangyong" + time + i, li2.get(i));
                    }

                    editor.putInt("time", time + 1);
                }
            } else if (po1 == 4) {
                if (item >= 0) {
                    editor.putInt("ziyouyongsize" + item, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + item + i, li1.get(i));
                    }
                    editor.putInt("yangyongsize" + item, li2.size());
                    for (int i = 0; i < li2.size(); i++) {
                        editor.putInt("yangyong" + item + i, li2.get(i));
                    }
                    editor.putInt("wayongsize" + item, li3.size());
                    for (int i = 0; i < li3.size(); i++) {
                        editor.putInt("wayong" + item + i, li3.get(i));
                    }

                } else {
                    editor.putInt("ziyouyongsize" + time, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + time + i, li1.get(i));
                    }
                    editor.putInt("yangyongsize" + time, li2.size());
                    for (int i = 0; i < li2.size(); i++) {
                        editor.putInt("yangyong" + time + i, li2.get(i));
                    }
                    editor.putInt("wayongsize" + time, li3.size());
                    for (int i = 0; i < li3.size(); i++) {
                        editor.putInt("wayong" + time + i, li3.get(i));
                    }

                    editor.putInt("time", time + 1);
                }
            }
        } else if (c == 2) {//直选
            if (po2 == 0 || po2 == 1 || po2 == 2) {//直选单式//直选复式//直选组合
                if (item >= 0) {
                    editor.putInt("ziyouyongsize" + item, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + item + i, li1.get(i));
                    }
                    editor.putInt("yangyongsize" + item, li2.size());
                    for (int i = 0; i < li2.size(); i++) {
                        editor.putInt("yangyong" + item + i, li2.get(i));
                    }
                    editor.putInt("wayongsize" + item, li3.size());
                    for (int i = 0; i < li3.size(); i++) {
                        editor.putInt("wayong" + item + i, li3.get(i));
                    }
                    editor.putInt("dieyongsize" + item, li4.size());
                    for (int i = 0; i < li4.size(); i++) {
                        editor.putInt("dieyong" + item + i, li4.get(i));
                    }
                } else {
                    editor.putInt("ziyouyongsize" + time, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + time + i, li1.get(i));
                    }
                    editor.putInt("yangyongsize" + time, li2.size());
                    for (int i = 0; i < li2.size(); i++) {
                        editor.putInt("yangyong" + time + i, li2.get(i));
                    }
                    editor.putInt("wayongsize" + time, li3.size());
                    for (int i = 0; i < li3.size(); i++) {
                        editor.putInt("wayong" + time + i, li3.get(i));
                    }
                    editor.putInt("dieyongsize" + time, li4.size());
                    for (int i = 0; i < li4.size(); i++) {
                        editor.putInt("dieyong" + time + i, li4.get(i));
                    }
                    editor.putInt("time", time + 1);
                }
            }
        } else if (c == 3) {//组选
            if (po3 == 0 || po3 == 3 || po3 == 7 || po3 == 10) {//组选24单式//组选12单式
                if (item >= 0) {
                    editor.putInt("ziyouyongsize" + item, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + item + i, li1.get(i));
                    }
                    editor.putInt("yangyongsize" + item, li2.size());
                    for (int i = 0; i < li2.size(); i++) {
                        editor.putInt("yangyong" + item + i, li2.get(i));
                    }
                    editor.putInt("wayongsize" + item, li3.size());
                    for (int i = 0; i < li3.size(); i++) {
                        editor.putInt("wayong" + item + i, li3.get(i));
                    }
                    editor.putInt("dieyongsize" + item, li4.size());
                    for (int i = 0; i < li4.size(); i++) {
                        editor.putInt("dieyong" + item + i, li4.get(i));
                    }
                } else {
                    editor.putInt("ziyouyongsize" + time, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + time + i, li1.get(i));
                    }
                    editor.putInt("yangyongsize" + time, li2.size());
                    for (int i = 0; i < li2.size(); i++) {
                        editor.putInt("yangyong" + time + i, li2.get(i));
                    }
                    editor.putInt("wayongsize" + time, li3.size());
                    for (int i = 0; i < li3.size(); i++) {
                        editor.putInt("wayong" + time + i, li3.get(i));
                    }
                    editor.putInt("dieyongsize" + time, li4.size());
                    for (int i = 0; i < li4.size(); i++) {
                        editor.putInt("dieyong" + time + i, li4.get(i));
                    }
                    editor.putInt("time", time + 1);
                }
            } else if (po3 == 1 || po3 == 4 || po3 == 8 || po3 == 11) {//组选24复式
                if (item >= 0) {
                    editor.putInt("ziyouyongsize" + item, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + item + i, li1.get(i));
                    }

                } else {
                    editor.putInt("ziyouyongsize" + time, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + time + i, li1.get(i));
                    }

                    editor.putInt("time", time + 1);
                }
            } else if (po3 == 2 || po3 == 5 || po3 == 6 || po3 == 9 || po3 == 12 || po3 == 13) {//组选24胆拖
                if (item >= 0) {
                    editor.putInt("ziyouyongsize" + item, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + item + i, li1.get(i));
                    }
                    editor.putInt("yangyongsize" + item, li2.size());
                    for (int i = 0; i < li2.size(); i++) {
                        editor.putInt("yangyong" + item + i, li2.get(i));
                    }

                } else {
                    editor.putInt("ziyouyongsize" + time, li1.size());
                    for (int i = 0; i < li1.size(); i++) {
                        editor.putInt("ziyouyong" + time + i, li1.get(i));
                    }
                    editor.putInt("yangyongsize" + time, li2.size());
                    for (int i = 0; i < li2.size(); i++) {
                        editor.putInt("yangyong" + time + i, li2.get(i));
                    }

                    editor.putInt("time", time + 1);
                }
            }
        }
        editor.commit();
    }

    /**
     * 复选框选中取消事件
     */
    private void initcheckbox() {

        cb_ziyouyong = (CheckBox) findViewById(R.id.cb_ziyouyong);
        cb_yangyong = (CheckBox) findViewById(R.id.cb_yangyong);
        cb_wayong = (CheckBox) findViewById(R.id.cb_wayong);
        cb_dieyong = (CheckBox) findViewById(R.id.cb_dieyong);

        //取消监听器
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!((CheckBox) v).isChecked()) {
                    cb = cb - 1;
                    if (((CheckBox) v).getText().equals("自由泳")) {
                        z = 0;
                    } else if (((CheckBox) v).getText().equals("仰泳")) {
                        y = 0;
                    } else if (((CheckBox) v).getText().equals("蛙泳")) {
                        w = 0;
                    } else if (((CheckBox) v).getText().equals("蝶泳")) {
                        d = 0;
                    }
                }
            }
        };
        //选中监听器
        CompoundButton.OnCheckedChangeListener occl = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    cb = cb + 1;
                    if (buttonView.getText().equals("自由泳")) {
                        z = 1;
                    } else if (buttonView.getText().equals("仰泳")) {
                        y = 2;
                    } else if (buttonView.getText().equals("蛙泳")) {
                        w = 3;
                    } else if (buttonView.getText().equals("蝶泳")) {
                        d = 4;
                    }
                }
                if (cb == 4) {
                    Toast.makeText(OneYongTanDuoGuan.this, "最多只能选三个项目", Toast.LENGTH_SHORT).show();
                    if (buttonView.getText().equals("自由泳")) {
                        cb_ziyouyong.setChecked(false);
                        z = 0;
                    } else if (buttonView.getText().equals("仰泳")) {
                        cb_yangyong.setChecked(false);
                        y = 0;
                    } else if (buttonView.getText().equals("蛙泳")) {
                        cb_wayong.setChecked(false);
                        w = 0;
                    } else if (buttonView.getText().equals("蝶泳")) {
                        cb_dieyong.setChecked(false);
                        d = 0;
                    }
                }
            }
        };

        cb_ziyouyong.setOnClickListener(ocl);
        cb_ziyouyong.setOnCheckedChangeListener(occl);
        cb_yangyong.setOnCheckedChangeListener(occl);
        cb_yangyong.setOnClickListener(ocl);
        cb_wayong.setOnClickListener(ocl);
        cb_wayong.setOnCheckedChangeListener(occl);
        cb_dieyong.setOnCheckedChangeListener(occl);
        cb_dieyong.setOnClickListener(ocl);
    }

    /**
     * 泳坛夺冠——任选分类玩法数据源
     *
     * @return
     */
    private List<Map<String, String>> data2() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("text", "任选一");
        list.add(map);
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("text", "任选二");
        list.add(map1);
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("text", "任选二全包");
        list.add(map2);
        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("text", "任选三");
        list.add(map3);
        Map<String, String> map4 = new HashMap<String, String>();
        map4.put("text", "任选三全包");
        list.add(map4);

        return list;
    }

    /**
     * 泳坛夺冠——直选分类玩法数据源
     *
     * @return
     */
    private List<Map<String, String>> data3() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("text", "直选单式");
        list.add(map);
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("text", "直选复式");
        list.add(map1);
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("text", "直选组合");
        list.add(map2);

        return list;
    }

    /**
     * 泳坛夺冠——组选分类玩法数据源
     *
     * @return
     */
    private List<Map<String, String>> data4() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("text", "组选24单式");
        list.add(map);
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("text", "组选24复式");
        list.add(map1);
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("text", "组选24胆拖");
        list.add(map2);
        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("text", "组选12单式");
        list.add(map3);
        Map<String, String> map4 = new HashMap<String, String>();
        map4.put("text", "组选12复式");
        list.add(map4);
        Map<String, String> map5 = new HashMap<String, String>();
        map5.put("text", "组选12胆拖");
        list.add(map5);
        Map<String, String> map6 = new HashMap<String, String>();
        map6.put("text", "组选12重胆拖");
        list.add(map6);
        Map<String, String> map7 = new HashMap<String, String>();
        map7.put("text", "组选6单式");
        list.add(map7);
        Map<String, String> map8 = new HashMap<String, String>();
        map8.put("text", "组选6复式");
        list.add(map8);
        Map<String, String> map9 = new HashMap<String, String>();
        map9.put("text", "组选6胆拖");
        list.add(map9);
        Map<String, String> map10 = new HashMap<String, String>();
        map10.put("text", "组选4单式");
        list.add(map10);
        Map<String, String> map11 = new HashMap<String, String>();
        map11.put("text", "组选4复式");
        list.add(map11);
        Map<String, String> map12 = new HashMap<String, String>();
        map12.put("text", "组选4胆拖");
        list.add(map12);
        Map<String, String> map13 = new HashMap<String, String>();
        map13.put("text", "组选4重胆拖");
        list.add(map13);

        return list;
    }

    /**
     * 泳坛夺冠——前三组选分类玩法数据源
     *
     * @return
     */
    private List<Map<String, String>> data5() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("text", "前三组3");
        list.add(map);
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("text", "前三组6");
        list.add(map1);
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("text", "任三组3");
        list.add(map2);
        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("text", "任三组6");
        list.add(map3);

        return list;
    }

    /**
     * 泳坛夺冠——新玩法分类玩法数据源
     *
     * @return
     */
    private List<Map<String, String>> data6() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("text", "组选三码全包");
        list.add(map);
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("text", "组选二码全包");
        list.add(map1);
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("text", "选四全包");
        list.add(map2);
        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("text", "重号全包");
        list.add(map3);

        return list;
    }

    /**
     * 选四全包
     *
     * @return
     */
    private List<Map<String, String>> data7() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("text", "选四不重全包");
        list.add(map);
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("text", "选四一对全包");
        list.add(map1);
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("text", "选四两对全包");
        list.add(map2);
        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("text", "选四三条全包");
        list.add(map3);

        return list;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!b) {
                popupWindow.dismiss();
                iv_top.setImageResource(R.mipmap.top_2);
                b = true;
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
