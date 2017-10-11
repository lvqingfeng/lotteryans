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
import com.juyikeji.caipiao.adapter.FragmentOneGvBlueAdapterB;
import com.juyikeji.caipiao.adapter.FragmentOneGvRedAdapterA;
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
import java.util.Stack;

public class OneLotteryBet extends Activity implements View.OnClickListener {

    private MyGridView gv_red, gv_blue;
    private FragmentOneGvRedAdapterA adapterA;
    private FragmentOneGvBlueAdapterB adapterB;
    private List<Integer> list;

    private RelativeLayout rel_zst;
    private Button btn_close, btn_clear;
    private TextView tv_title, tv_pt, tv_dd, tv_time, tv_next, tv_choose, tv_money, tv_xl, tv_ts;
    //获取往期中奖信息的网络接口
    private String name_space = Url.LOTTERYMESSAGE;
    String result = "";
    //最近得奖信息下拉列表
    private ListView lv;
    private FragmentOneKJXXAdapter adapter;
    private List<String[]> listis;
    private LinearLayout linout1;
    private View view;

    private ImageView iv, iv_xl;
    private RelativeLayout rel_down;

    private boolean zst = false;//选择走势图下拉的标识
    private boolean choose = false;//选择投注类型标识默认为普通投注
    private boolean titledown = false;//选择投注类型下拉按钮标识
    private int kjrq;//开奖日期
    private String pausetime="";

    MyCombine combine = new MyCombine();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_one_lotterybet);
        SysApplication.getInstance().addActivity(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        showLotter();//请求网络获取往期中奖彩票信息
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        adapterB = new FragmentOneGvBlueAdapterB(this, getDateB(), getSharedPreferences());
        adapterA = new FragmentOneGvRedAdapterA(this, getDateR(), getSharedPreferences());
        if (adapterA.limitd>0){
            choose=true;
        }else {
            choose=false;

        }
        initView();
        int item = sharedPreferences.getInt("item", 0);
        if (item >= 0) {
            int numb = adapterB.limit;
            int d = adapterA.limitd;//胆码个数
            List<Integer> a = adapterA.getNumb();//获取选中的红球
            String[] s = new String[a.size()];
            for (int i = 0; i < a.size(); i++) {
                s[i] = a.get(i) + "";//将获取的红球存到数组里
            }
            int zs = combine.combine(s, 6 - d).size();
            if (choose){
                tv_money.setText(adapterA.limit + "红球" + d + "胆码" + numb + "蓝球" + " 共" + zs * numb + "注 " + 2 * zs * numb + "元");
            }else {
                tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + zs * numb + "注 " + 2 * zs * numb + "元");
            }
        }

    }

    private void initView() {
//        rel_zst=(RelativeLayout)findViewById(R.id.rel_zst);
//        rel_zst.setOnClickListener(this);
        linout1=(LinearLayout)findViewById(R.id.linout1);
        view=(View)findViewById(R.id.view);
        lv=(ListView)findViewById(R.id.lv);

        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);

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
        tv_money.setText("0红球0蓝球共0注 0元");
        tv_xl = (TextView) findViewById(R.id.tv_xl);
        tv_ts = (TextView) findViewById(R.id.tv_ts);
        tv_ts.setVisibility(View.GONE);
        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(this);
        iv_xl = (ImageView) findViewById(R.id.iv_xl);
        iv_xl.setOnClickListener(this);

        rel_down = (RelativeLayout) findViewById(R.id.rel_down);

        gv_red = (MyGridView) findViewById(R.id.gv_red);
        gv_blue = (MyGridView) findViewById(R.id.gv_blue);
        gv_red.setAdapter(adapterA);
        gv_blue.setAdapter(adapterB);
        gv_red.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                nowCode = gvlist.get(position).getID().toString();
                adapterA.changeSelected(position, choose);//刷新
                if (choose) {
                    int numb = adapterB.limit;
                    int d = adapterA.limitd;//胆码个数
                    List<Integer> a = adapterA.getNumb();//获取选中的红球
                    String[] s = new String[a.size()];
                    for (int i = 0; i < a.size(); i++) {
                        s[i] = a.get(i) + "";//将获取的红球存到数组里
                    }
                    if (d + adapterA.limit < 6 || adapterB.limit < 1) {
                        tv_money.setText(adapterA.limit + "红球" + d + "胆码" + adapterB.limit + "蓝球共0注 0元");
                        return;
                    }
                    int zs = combine.combine(s, 6 - d).size();
                    tv_money.setText(adapterA.limit + "红球" + d + "胆码" + numb + "蓝球" + " 共" + zs * numb + "注 " + 2 * zs * numb + "元");
                } else {
                    showMoney();
                }

            }
        });
        gv_blue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                nowCode = gvlist.get(position).getID().toString();
                adapterB.changeSelected(position);//刷新
                if (choose) {
                    int numb = adapterB.limit;
                    int d = adapterA.limitd;//胆码个数
                    List<Integer> a = adapterA.getNumb();//获取选中的红球
                    String[] s = new String[a.size()];
                    for (int i = 0; i < a.size(); i++) {
                        s[i] = a.get(i) + "";//将获取的红球存到数组里
                    }
                    if (d + adapterA.limit < 6 || adapterB.limit < 1) {
                        tv_money.setText(adapterA.limit + "红球" + d + "胆码" + adapterB.limit + "蓝球共0注 0元");
                        return;
                    }
                    int zs = combine.combine(s, 6 - d).size();
                    tv_money.setText(adapterA.limit + "红球" + d + "胆码" + numb + "蓝球" + " 共" + zs * numb + "注 " + 2 * zs * numb + "元");
                } else {
                    showMoney();
                }
            }
        });
    }

    /**
     * 计算彩票注数更新ui
     */
    private void showMoney() {
        if (adapterA.limit >= 6 && adapterB.limit >= 1) {
            int numb = adapterB.limit;
            switch (adapterA.limit) {
                case 6:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 1 * numb + "注 " + 2 * numb + "元");
                    break;
                case 7:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 7 * numb + "注 " + 14 * numb + "元");
                    break;
                case 8:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 28 * numb + "注 " + 56 * numb + "元");
                    break;
                case 9:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 84 * numb + "注 " + 168 * numb + "元");
                    break;
                case 10:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 210 * numb + "注 " + 420 * numb + "元");
                    break;
                case 11:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 462 * numb + "注 " + 924 * numb + "元");
                    break;
                case 12:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 924 * numb + "注 " + 1848 * numb + "元");
                    break;
                case 13:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 1716 * numb + "注 " + 3432 * numb + "元");
                    break;
                case 14:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 3003 * numb + "注 " + 6006 * numb + "元");
                    break;
                case 15:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 5005 * numb + "注 " + 10010 * numb + "元");
                    break;
                case 16:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 8008 * numb + "注 " + 16016 * numb + "元");
                    break;
                case 17:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 12376 * numb + "注 " + 24752 * numb + "元");
                    break;
                case 18:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 18564 * numb + "注 " + 37128 * numb + "元");
                    break;
                case 19:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 27132 * numb + "注 " + 54264 * numb + "元");
                    break;
                case 20:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 38760 * numb + "注 " + 77520 * numb + "元");
                    break;
                case 21:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 54264 * numb + "注 " + 108528 * numb + "元");
                    break;
                case 22:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 74613 * numb + "注 " + 149226 * numb + "元");
                    break;
                case 23:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 100947 * numb + "注 " + 201894 * numb + "元");
                    break;
                case 24:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 134596 * numb + "注 " + 269192 * numb + "元");
                    break;
                case 25:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 177100 * numb + "注 " + 354200 * numb + "元");
                    break;
                case 26:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 230230 * numb + "注 " + 460460 * numb + "元");
                    break;
                case 27:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 296010 * numb + "注 " + 592020 * numb + "元");
                    break;
                case 28:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 376740 * numb + "注 " + 753480 * numb + "元");
                    break;
                case 29:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 475020 * numb + "注 " + 950040 * numb + "元");
                    break;
                case 30:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 593775 * numb + "注 " + 1187550 * numb + "元");
                    break;
                case 31:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 736281 * numb + "注 " + 1472562 * numb + "元");
                    break;
                case 32:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 906192 * numb + "注 " + 1812384 * numb + "元");
                    break;
                case 33:
                    tv_money.setText(adapterA.limit + "红球" + numb + "蓝球" + " 共" + 1107568 * numb + "注 " + 2215136 * numb + "元");
                    break;

            }
        } else {
            tv_money.setText(adapterA.limit + "红球" + adapterB.limit + "蓝球" + "共0注0元");
        }
    }

    /**
     * 红球的布局加载容器
     */
    private List<Integer> getDateR() {
        list = new ArrayList<Integer>();
        for (int i = 1; i <= 33; i++) {
            list.add(i);
        }
        return list;
    }

    private List<Integer> getDateB() {
        list = new ArrayList<Integer>();
        for (int i = 1; i <= 16; i++) {
            list.add(i);
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.rel_zst:
//                startActivity(new Intent(this,FragmentOneWebview.class));
//                break;
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
                adapterB.getMselect();
                break;
            case R.id.tv_next:
                //下一步
                if (adapterA.limit + adapterA.limitd < 6) {
                    Toast.makeText(this, "红球个数不能少与6个", Toast.LENGTH_SHORT).show();
//                    SharedPreferencesUtil.clearShared(this);//清除记录选中号码的缓存
                    return;
                }
                if (adapterB.limit < 1) {
                    Toast.makeText(this, "蓝球个数不能少与1个", Toast.LENGTH_SHORT).show();
//                    SharedPreferencesUtil.clearShared(this);//清除记录选中号码的缓存
                    return;
                }
                setSharedPreferences(adapterA.getNumb(), adapterA.getNumbd(), adapterB.getNumb());
                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                intent.putExtra("彩种", 1);
                intent.putExtra("kjrq",kjrq);
                intent.putExtra("pausetime",pausetime);
                startActivity(intent);
                break;
            case R.id.tv_pt:
                //普通投注
                tv_title.setText("普通投注");
                choose = false;
                adapterA.getMselect();
                adapterB.getMselect();
                tv_pt.setBackgroundResource(R.mipmap.choose_yes);
                tv_dd.setBackgroundResource(R.mipmap.choose_no);
                tv_ts.setVisibility(View.GONE);
                tv_money.setText("0红球0篮球共0注 0元");
                break;
            case R.id.tv_dd:
                //胆投
                tv_title.setText("定胆投注");
                choose = true;
                adapterA.getMselect();
                adapterB.getMselect();
                tv_pt.setBackgroundResource(R.mipmap.choose_no);
                tv_dd.setBackgroundResource(R.mipmap.choose_yes);
                tv_ts.setVisibility(View.VISIBLE);
                tv_money.setText("0红球0篮球共0注 0元");
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
                adapterB.changeNumber();
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
     * 请求网络获取彩票信息
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
                map.put("lotterytype", "ssq");
                // 请求网络
                try {
                    result = URLConnectionUtil.sendPostRequest(name_space,
                            map, "UTF-8", 1);
                    Log.i("result",result);
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
                adapter=new FragmentOneKJXXAdapter(this,listis,1);
                lv.setAdapter(adapter);
            }else {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // 获取shared中的号码
    private Map<String, Object> getSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        //红球的集合
        List<Integer> redlist = new ArrayList<Integer>();
        //红胆的集合
        List<String> reddlist = new ArrayList<String>();
        //篮球的集合
        List<Integer> bluelist = new ArrayList<Integer>();
        //篮胆的集合
        List<String> bluedlist = new ArrayList<String>();

        String redd = "", blued = "";
        int reddsize = 0, bluedsize = 0;
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

        bluedsize = sharedPreferences.getInt("bluedsize" + item, 0);//胆码的个数
        blued = sharedPreferences.getString("blued" + item, "");//篮球胆码
        for (int i = 0; i < bluedsize; i++) {
            reddlist.add(blued.substring(0, blued.indexOf(" ")));//截取每个胆码
            blued = blued.substring(blued.indexOf(" ") + 1);//将剩余胆码重置从新截取
        }
        int bluesize = sharedPreferences.getInt("bluesize" + item, 0);//蓝球的个数
        for (int j = 0; j < bluesize; j++) {
            bluelist.add(sharedPreferences.getInt("blue" + item + j, 0));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("red", redlist);
        map.put("redd", reddlist);
        map.put("blue", bluelist);
        map.put("blued", bluedlist);
        return map;
    }

    //    将选择的号码存到shared里
    public void setSharedPreferences(List<Integer> red, String redd, List<Integer> blue) {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int time = sharedPreferences.getInt("time", 0);
        int item = sharedPreferences.getInt("item", 0);
        if (item >= 0) {
            if (!choose) {
                editor.putInt("redsize" + item, red.size());
                for (int i = 0; i < red.size(); i++) {
                    editor.putInt("red" + item + i, red.get(i));
                }
                editor.putInt("bluesize" + item, blue.size());
                for (int i = 0; i < blue.size(); i++) {
                    editor.putInt("blue" + item + i, blue.get(i));
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
                // 篮球
                editor.putInt("bluesize" + item, blue.size());
                for (int i = 0; i < blue.size(); i++) {
                    editor.putInt("blue" + item + i, blue.get(i));
                }
            }
        } else {
            if (!choose) {
                editor.putInt("redsize" + time, red.size());
                for (int i = 0; i < red.size(); i++) {
                    editor.putInt("red" + time + i, red.get(i));
                }
                editor.putInt("bluesize" + time, blue.size());
                for (int i = 0; i < blue.size(); i++) {
                    editor.putInt("blue" + time + i, blue.get(i));
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
                // 篮球
                editor.putInt("bluesize" + time, blue.size());
                for (int i = 0; i < blue.size(); i++) {
                    editor.putInt("blue" + time + i, blue.get(i));
                }
            }
            editor.putInt("time", time + 1);
        }
        editor.commit();
    }
}
