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
import com.juyikeji.caipiao.adapter.FragmentOneGvBlueAdapterBB;
import com.juyikeji.caipiao.adapter.FragmentOneGvRedAdapterA;
import com.juyikeji.caipiao.adapter.FragmentOneGvRedAdapterAA;
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

public class OneDaletou extends Activity implements View.OnClickListener {

    private MyGridView gv_red, gv_blue;
    private FragmentOneGvRedAdapterAA adapterA;
    private FragmentOneGvBlueAdapterBB adapterB;
    private List<Integer> list;

    private Button btn_close, btn_clear;
    private TextView tv_title, tv_pt, tv_dd, tv_time, tv_next, tv_choose, tv_money, tv_xl,tv_ts;
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

    MyCombine combine=new MyCombine();
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
        adapterB = new FragmentOneGvBlueAdapterBB(this, getDateB(), getSharedPreferences());
        adapterA = new FragmentOneGvRedAdapterAA(this, getDateR(), getSharedPreferences());
        if (adapterA.limitd>0){
            choose=true;
        }else {
            choose=false;

        }
        initView();
        int item = sharedPreferences.getInt("item", 0);
        if (item >= 0) {
            int limit = adapterA.limit;
            int numb1 = adapterB.limit;
            int d = adapterA.limitd;//胆码个数
            int ld=adapterB.limitd;//蓝球胆码的个数
            List<Integer> a = adapterA.getNumb();//获取选中的红球
            List<Integer> b =adapterA.getNumb();//获取选中的蓝球
            String[] sr = new String[a.size()];
            String []sb=new String[b.size()];
            for (int i = 0; i < a.size(); i++) {
                sr[i] = a.get(i) + "";//将获取的红球存到数组里
            }
            for (int i=0;i<a.size();i++){
                sb[i]=b.get(i)+"";//将获取的蓝球存到数组里
            }
            int zs = combine.combine(sr, 5 - d).size();
            int numb=0;
            if (d==0){
                numb=getMoM(numb1);
            }else {
                numb = getMoMD(numb1);
            }
            if (choose){
                tv_money.setText(limit + "红球"+d+"红胆"+ numb + "蓝球" +ld+"蓝胆"+ " 共" +zs*numb+ "注 " + 2 * zs*numb + "元");
            }else {
                tv_money.setText(limit + "红球" + numb + "蓝球" + " 共" + zs * numb + "注 " + 2 * zs * numb + "元");
            }
        }
    }

    private void initView() {
        linout1=(LinearLayout)findViewById(R.id.linout1);
        view=(View)findViewById(R.id.view);
        lv=(ListView)findViewById(R.id.lv);

        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);

        tv_ts = (TextView) findViewById(R.id.tv_ts);
        tv_ts.setVisibility(View.GONE);
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
        tv_money.setText("至少选5红球2篮球");
        tv_xl = (TextView) findViewById(R.id.tv_xl);

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
                if (choose){
                    int numb1 = adapterB.limit;
                    int limit = adapterA.limit;
                    int d=adapterA.limitd;//胆码个数
                    int ld=adapterB.limitd;//蓝球胆码的个数
                    List<Integer> a =adapterA.getNumb();//获取选中的红球
                    List<Integer> b =adapterA.getNumb();//获取选中的蓝球
                    String []sr=new String[a.size()];
                    String []sb=new String[b.size()];
                    for (int i=0;i<a.size();i++){
                        sr[i]=a.get(i)+"";//将获取的红球存到数组里
                    }
                    for (int i=0;i<a.size();i++){
                        sb[i]=b.get(i)+"";//将获取的蓝球存到数组里
                    }

                    if (d+adapterA.limit<5||ld+adapterB.limit<2){
                        tv_money.setText("至少选5红球2篮球");
                        return;
                    }
                    int zs=combine.combine(sr,5-d).size();
                    int numb=0;
                    if (d==0){
                        numb=getMoM(numb1);
                    }else {
                        numb = getMoMD(numb1);
                    }
                    tv_money.setText(limit + "红球"+d+"红胆"+ numb1 + "蓝球" +ld+"蓝胆"+ " 共" +zs*numb+ "注 " + 2 * zs*numb + "元");
                }else {
                    showMoney();
                }
            }
        });
        gv_blue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                nowCode = gvlist.get(position).getID().toString();
                adapterB.changeSelected(position, choose);//刷新
                if (choose){
                    int numb1 = adapterB.limit;
                    int limit = adapterA.limit;
                    int d=adapterA.limitd;//胆码个数
                    int ld=adapterB.limitd;//蓝球胆码的个数
                    List<Integer> a =adapterA.getNumb();//获取选中的红球
                    List<Integer> b =adapterA.getNumb();//获取选中的蓝球
                    String []sr=new String[a.size()];
                    String []sb=new String[b.size()];
                    for (int i=0;i<a.size();i++){
                        sr[i]=a.get(i)+"";//将获取的红球存到数组里
                    }
                    for (int i=0;i<a.size();i++){
                        sb[i]=b.get(i)+"";//将获取的蓝球存到数组里
                    }

                    if (d+adapterA.limit<5||ld+adapterB.limit<2){
                        tv_money.setText("至少选5红球2篮球");
                        return;
                    }
                    int zs=combine.combine(sr,5-d).size();
                    int numb=0;
                    if (d==0){
                        numb=getMoM(numb1);
                    }else {
                        numb = getMoMD(numb1);
                    }
                    tv_money.setText(limit + "红球"+d+"红胆"+ numb1 + "蓝球" +ld+"蓝胆"+ " 共" +zs*numb+ "注 " + 2 * zs*numb + "元");
                }else {
                    showMoney();
                }
            }
        });
    }

    /**
     * 参数为0的时候是双色球的布局，其他为大乐透布局
     * 红球的布局加载容器
     *
     * @return
     */
    private List<Integer> getDateR() {
        list = new ArrayList<Integer>();
        for (int i = 1; i <= 35; i++) {
            list.add(i);
        }
        return list;
    }

    private List<Integer> getDateB() {
        list = new ArrayList<Integer>();
        for (int i = 1; i <= 12; i++) {
            list.add(i);
        }
        return list;
    }

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
                adapterB.getMselect();
                break;
            case R.id.tv_next:
                //下一步
                if (adapterA.limit+adapterA.limitd < 5 || adapterB.limit+adapterB.limitd < 2) {
                    Toast.makeText(this, "至少选5红球2篮球", Toast.LENGTH_SHORT).show();
//                    SharedPreferencesUtil.clearShared(this);//清除记录选中号码的缓存
                    return;
                }
                setSharedPreferences(adapterA.getNumb(), adapterA.getNumbd(), adapterB.getNumb(), adapterB.getNumbd());
                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                intent.putExtra("彩种", 2);
                intent.putExtra("kjrq",kjrq);
                intent.putExtra("pausetime",pausetime);
                startActivity(intent);
                break;
            case R.id.tv_pt:
                //普通投注
                tv_ts.setVisibility(View.GONE);
                tv_title.setText("普通投注");
                choose = false;
                adapterA.getMselect();
                adapterB.getMselect();
                tv_pt.setBackgroundResource(R.mipmap.choose_yes);
                tv_dd.setBackgroundResource(R.mipmap.choose_no);
                break;
            case R.id.tv_dd:
                //胆投
                tv_ts.setVisibility(View.VISIBLE);
                tv_title.setText("定胆投注");
                choose = true;
                adapterA.getMselect();
                adapterB.getMselect();
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
                adapterB.changeNumber();
                showMoney();
                break;

        }
    }
    /**
     * 根据彩球数获取注数和钱数
     */
    private void showMoney() {
        if (adapterA.limit >= 5 && adapterB.limit >= 2) {
            int numb1 = adapterB.limit;
            int numb = getMoM(numb1);
            int limit = adapterA.limit;
            switch (limit) {
                case 5:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 1 * numb + "注 " + 2 * numb + "元");
                    break;
                case 6:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 6 * numb + "注 " + 12 * numb + "元");
                    break;
                case 7:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 24 * numb + "注 " + 42 * numb + "元");
                    break;
                case 8:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 56 * numb + "注 " + 112 * numb + "元");
                    break;
                case 9:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 126 * numb + "注 " + 252 * numb + "元");
                    break;
                case 10:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 252 * numb + "注 " + 504 * numb + "元");
                    break;
                case 11:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 462 * numb + "注 " + 924 * numb + "元");
                    break;
                case 12:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 792 * numb + "注 " + 1584 * numb + "元");
                    break;
                case 13:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 1287 * numb + "注 " + 2574 * numb + "元");
                    break;
                case 14:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 2002 * numb + "注 " + 4004 * numb + "元");
                    break;
                case 15:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 3003 * numb + "注 " + 6006 * numb + "元");
                    break;
                case 16:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 4368 * numb + "注 " + 8736 * numb + "元");
                    break;
                case 17:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 6188 * numb + "注 " + 12376 * numb + "元");
                    break;
                case 18:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 8568 * numb + "注 " + 17136 * numb + "元");
                    break;
                case 19:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 11628 * numb + "注 " + 23256 * numb + "元");
                    break;
                case 20:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 15504 * numb + "注 " + 31008 * numb + "元");
                    break;
                case 21:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 20349 * numb + "注 " + 40698 * numb + "元");
                    break;
                case 22:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 26334 * numb + "注 " + 52668 * numb + "元");
                    break;
                case 23:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 33649 * numb + "注 " + 67298 * numb + "元");
                    break;
                case 24:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 42504 * numb + "注 " + 85008 * numb + "元");
                    break;
                case 25:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 53130 * numb + "注 " + 106260 * numb + "元");
                    break;
                case 26:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 65780 * numb + "注 " + 131560 * numb + "元");
                    break;
                case 27:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 80730 * numb + "注 " + 161460 * numb + "元");
                    break;
                case 28:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 98280 * numb + "注 " + 196560 * numb + "元");
                    break;
                case 29:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 118755 * numb + "注 " + 237510 * numb + "元");
                    break;
                case 30:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 142506 * numb + "注 " + 285012 * numb + "元");
                    break;
                case 31:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 169911 * numb + "注 " + 339822 * numb + "元");
                    break;
                case 32:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 201376 * numb + "注 " + 402752 * numb + "元");
                    break;
                case 33:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 237336 * numb + "注 " + 474672 * numb + "元");
                    break;
                case 34:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 278256 * numb + "注 " + 556512 * numb + "元");
                    break;
                case 35:
                    tv_money.setText(limit + "红球" + numb1 + "蓝球" + " 共" + 324632 * numb + "注 " + 649264 * numb + "元");
                    break;

            }
        } else {
            tv_money.setText("至少选5红球2篮球");
        }
    }

    /**
     * 计算彩票蓝色球的倍数
     */
    private int getMoM(int a) {
        switch (a) {
            case 2:
                return 1;
            case 3:
                return 3;
            case 4:
                return 6;
            case 5:
                return 10;
            case 6:
                return 15;
            case 7:
                return 21;
            case 8:
                return 28;
            case 9:
                return 36;
            case 10:
                return 45;
            case 11:
                return 55;
            case 12:
                return 66;
            default:
                return 0;
        }
    }
 /**
     * 计算彩票蓝色球有胆的倍数
     */
    private int getMoMD(int a) {
        switch (a) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case 8:
                return 8;
            case 9:
                return 9;
            case 10:
                return 10;
            case 11:
                return 11;
            default:
                return 0;
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
     * 请求网络获取往期中奖信息
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
                adapter=new FragmentOneKJXXAdapter(this,listis,2);
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
            Log.i("redredred", sharedPreferences.getInt("red" + item + j, 0) + "");
        }

        bluedsize = sharedPreferences.getInt("bluedsize" + item, 0);//胆码的个数
        blued = sharedPreferences.getString("blued" + item, "");//篮球胆码
        for (int i = 0; i < bluedsize; i++) {
            bluedlist.add(blued.substring(0, blued.indexOf(" ")));//截取每个胆码
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
    public void setSharedPreferences(List<Integer> red, String redd,List<Integer> blue,String blued) {
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
                //获取篮球胆码
                editor.putInt("bluedsize" + item, adapterB.limitd);
                editor.putString("blued" + item, blued);
            }
        }else {
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
                //获取篮球胆码
                editor.putInt("bluedsize" + time, adapterB.limitd);
                editor.putString("blued" + time, blued);
            }
            editor.putInt("time", time + 1);
        }
        editor.commit();
    }
}
