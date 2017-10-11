package com.juyikeji.caipiao.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.adapter.FragmentTwoAdapter;
import com.juyikeji.caipiao.adapter.FragmentTwoGridViewAdapter;
import com.juyikeji.caipiao.bean.CpzlBean;
import com.juyikeji.caipiao.utils.SharedPreferencesUtil;
import com.juyikeji.caipiao.utils.URLConnectionUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的彩票
 */
public class FragmentTwo extends Fragment implements View.OnClickListener {
    private View view;
    private ListView lv_lotteryans;
    private List<CpzlBean> lvlist;
    private FragmentTwoAdapter lvadapter;

    private TextView tv_jyy,title;
    private RelativeLayout rl_text, qb_layout, dcp_layout, dkj_layout, yzj_layout;
    private View view_qb, view_dcp, view_dkj, view_yzj;
    private ImageView iv_top;

    private GridView gridview;
    private FragmentTwoGridViewAdapter gvadapter1,gvadapter2;

    //近一个月a，全部彩种b，c更换gv数据源true为彩种
    private boolean b = true, a = true, c = true;

    private final static String name_space="mylottery";
    private String result="";

    /**
     * 近期日起天数days/彩票名称state/彩票状态lotterytype
     */
    private String state="-1",days="-1",lotterytype="-1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two, container, false);
        init();
        request(lotterytype,state,days);
        return view;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//    }

    private void init() {
        rl_text = (RelativeLayout) view.findViewById(R.id.rl_text);
        rl_text.setOnClickListener(this);

        tv_jyy = (TextView) view.findViewById(R.id.tv_jyy);
        title = (TextView) view.findViewById(R.id.title);
        tv_jyy.setOnClickListener(this);

        qb_layout = (RelativeLayout) view.findViewById(R.id.qb_layout);
        qb_layout.setOnClickListener(this);

        dcp_layout = (RelativeLayout) view.findViewById(R.id.dcp_layout);
        dcp_layout.setOnClickListener(this);

        dkj_layout = (RelativeLayout) view.findViewById(R.id.dkj_layout);
        dkj_layout.setOnClickListener(this);

        yzj_layout = (RelativeLayout) view.findViewById(R.id.yzj_layout);
        yzj_layout.setOnClickListener(this);

        iv_top = (ImageView) view.findViewById(R.id.iv_top);

        view_qb = view.findViewById(R.id.view_qb);
        view_dcp = view.findViewById(R.id.view_dcp);
        view_dkj = view.findViewById(R.id.view_dkj);
        view_yzj = view.findViewById(R.id.view_yzj);

        lv_lotteryans = (ListView) view.findViewById(R.id.lv_lotteryans);

        gridview = (GridView) view.findViewById(R.id.gridview);
        gvadapter1 = new FragmentTwoGridViewAdapter(data2(), getActivity());
        gvadapter2 = new FragmentTwoGridViewAdapter(data3(), getActivity());

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (c) {
                    gvadapter1.setSeclection(position);
                    //根据item下标获取类型0：双色球，1：大乐透；2：泳坛夺冠；3：七星彩；4：排列3
                    //5：22选5；6：3D；7：七乐彩；8：快三；9：排列5
                    switch (position) {
                        case 1:
                            state = "ssq";
                            title.setText("双色球");
                            break;
                        case 2:
                            state = "dlt";
                            title.setText("大乐透");
                            break;
                        case 3:
                            state = "sxrytdj";
                            title.setText("泳坛夺冠");
                            break;
                        case 4:
                            state = "qxc";
                            title.setText("七星彩");
                            break;
                        case 5:
                            state = "pl3";
                            title.setText("排列三");
                            break;
                        case 6:
                            state = "zyfc22x5";
                            title.setText("22选5");
                            break;
                        case 7:
                            state = "fc3d";
                            title.setText("3D");
                            break;
                        case 8:
                            state = "qlc";
                            title.setText("七乐彩");
                            break;
                        case 9:
                            state = "hebk3";
                            title.setText("快三");
                            break;
                        case 10:
                            state = "pl5";
                            title.setText("排列五");
                            break;
                        case 0:
                            state ="-1";
                            title.setText("全部彩种");
                            break;
                    }
                } else {
                    gvadapter2.setSeclection(position);
                    //根据position获取时间0：近一周；1：近一个月;2:近三个月；3：近六个月；
                    switch (position) {
                        case 1:
                            days = "7";
                            tv_jyy.setText("近一周");
                            break;
                        case 2:
                            days = "31";
                            tv_jyy.setText("近一个月");
                            break;
                        case 3:
                            days = "92";
                            tv_jyy.setText("近三个月");
                            break;
                        case 4:
                            days = "185";
                            tv_jyy.setText("近6个月");
                            break;
                        case 0:
                            days = "-1";
                            tv_jyy.setText("全部时间");
                            break;
                    }
                }
                //请求网络获取数据
                request(lotterytype,state,days);
            }
        });
    }

    /**
     * 接口——我的彩票
     */
    private void request(final String cpzt,final String cpmc,final String cptime){
        final String token= SharedPreferencesUtil.getSharedPreferences(getActivity()).get("token").toString();

        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    getDate(result);
                }
            }
        };
        new Thread(){
            @Override
            public void run() {
                super.run();
                Map<String,String> map=new HashMap<String, String>();
                map.put("token",token);
                map.put("days",cptime);//近期日起天数
                map.put("lotterytype",cpmc);//彩票名称
                map.put("state",cpzt+"");//彩票状态

                try {
                    lvlist=new ArrayList<CpzlBean>();
                    result= URLConnectionUtil.sendPostRequest(name_space,map,"utf-8",1);
                        Log.i("FragmentTwo",result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Message m=new Message();
                m.what=1;
                handler.sendMessage(m);
            }
        }.start();

    }
    private int bs=0;
    //解析数据
    private void getDate(String json){
        try {
            JSONObject jobj = new JSONObject(json);
            String status = jobj.getString("status");
            String msg = jobj.getString("msg");
            if (status.equals("1")) {
                JSONArray data=jobj.getJSONArray("data");
                for (int i=0;i<data.length();i++){
                    CpzlBean cpzlBean=new CpzlBean();
                    JSONObject list=data.getJSONObject(i);
                    //中奖金额
                    String winmoney=list.getString("winmoney");
                    if (winmoney.equals("null")){
                        cpzlBean.setTv_zjje("");
                    }else {
                        cpzlBean.setTv_zjje(winmoney);
                    }
                    //单式复式
//                    String smalltype=list.getString("smalltype");
                    //开奖球
                    String winnumber=list.getString("winnumber");
                    if (winnumber.equals("null")){
                        cpzlBean.setTv_kjhm("");
                    }else {
                        cpzlBean.setTv_kjhm(winnumber);
                    }
                    //球
                    String lotterynumber=list.getString("lotterynumber");
                    cpzlBean.setTv_hm(lotterynumber);
                    //期数
                    String period=list.getString("period");
                    cpzlBean.setTv_qi(period);
                    //彩球类型
                    String lotterytype=list.getString("lotterytype");
                    switch (lotterytype){
                        case "ssq":
                            cpzlBean.setTv_title("双色球");
                            bs=1;
                            break;
                        case "dlt":
                            cpzlBean.setTv_title("大乐透");
                            bs=2;
                            break;
                        case "sxrytdj":
                            cpzlBean.setTv_title("泳坛夺冠");
                            bs=3;
                            break;
                        case "qxc":
                            cpzlBean.setTv_title("七星彩");
                            bs=4;
                            break;
                        case "pl3":
                            cpzlBean.setTv_title("排列三");
                            bs=5;
                            break;
                        case "zyfc22x5":
                            cpzlBean.setTv_title("22选5");
                            bs=6;
                            break;
                        case "fc3d":
                            cpzlBean.setTv_title("3D");
                            bs=7;
                            break;
                        case "qlc":
                            cpzlBean.setTv_title("七乐彩");
                            bs=8;
                            break;
                        case "hebk3":
                            cpzlBean.setTv_title("快三");
                            bs=9;
                            break;
                        case "pl5":
                            cpzlBean.setTv_title("排列五");
                            bs=10;
                            break;
                    }
                    //开奖时间
                    String createtime=list.getString("createtime");
                    cpzlBean.setTv_time(createtime);
                    //投注金额
                    int betmoney=list.getInt("betmoney");
                    cpzlBean.setTv_je(betmoney + "");
                    //id
                    String orderid=list.getString("orderid");
                    cpzlBean.setId(orderid);
                    //
                    String isprint=list.getString("isprint");
                    //
                    String lotterystate=list.getString("lotterystate");
                    //
                    int multiple=list.getInt("multiple");
                    //玩法名称
                    String playname=list.getString("playname");
                    cpzlBean.setTv_wf(playname);
                    lvlist.add(cpzlBean);
                }
                lvadapter = new FragmentTwoAdapter(lvlist, getActivity(),bs);
                lv_lotteryans.setAdapter(lvadapter);
            }else {
//                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //全部彩种
            case R.id.rl_text:
                gridview.setAdapter(gvadapter1);
                c=true;//gv数据源标识为true
                if (b) {
                    iv_top.setImageResource(R.mipmap.top_2);
                    gridview.setVisibility(View.VISIBLE);
                    b = false;
                    a=true;
                } else {
                    iv_top.setImageResource(R.mipmap.top_1);
                    gridview.setVisibility(View.GONE);
                    b = true;
                }
                break;
            //近一月
            case R.id.tv_jyy:
                c=false;//gv数据源标识为false
                gridview.setAdapter(gvadapter2);
                if (a) {
                    gridview.setVisibility(View.VISIBLE);
                    a = false;
                    b = true;
                    iv_top.setImageResource(R.mipmap.top_2);
                } else {
                    gridview.setVisibility(View.GONE);
                    iv_top.setImageResource(R.mipmap.top_1);
                    a = true;
                }
                break;
            //全部
            case R.id.qb_layout:
                //彩票状态
                lotterytype="-1";
                clear();
                view_qb.setBackgroundResource(R.color.red);
                request(lotterytype,state,days);//请求网络获取数据
                break;
            //带出票
            case R.id.dcp_layout:
                //彩票状态
                lotterytype="0";
                clear();
                view_dcp.setBackgroundResource(R.color.red);
                request(lotterytype, state, days);//请求网络获取数据
                break;
            //待开奖
            case R.id.dkj_layout:
                //彩票状态
                lotterytype="1";
                clear();
                view_dkj.setBackgroundResource(R.color.red);
                request(lotterytype,state,days);//请求网络获取数据
                break;
            //已中奖
            case R.id.yzj_layout:
                //彩票状态
                lotterytype="2";
                clear();
                view_yzj.setBackgroundResource(R.color.red);
                break;
        }
    }

    private void clear() {
        view_qb.setBackgroundResource(R.color.gray2);
        view_dcp.setBackgroundResource(R.color.gray2);
        view_dkj.setBackgroundResource(R.color.gray2);
        view_yzj.setBackgroundResource(R.color.gray2);
        lvlist=new ArrayList<CpzlBean>();
        lvadapter = new FragmentTwoAdapter(lvlist, getActivity(),bs);
        lv_lotteryans.setAdapter(lvadapter);

    }


    private List<Map<String, String>> data2() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map10 = new HashMap<String, String>();
        map10.put("text", "全部彩种");
        list.add(map10);
        Map<String, String> map = new HashMap<String, String>();
        map.put("text", "双色球");
        list.add(map);
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("text", "大乐透");
        list.add(map1);
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("text", "泳坛夺金");
        list.add(map2);
        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("text", "七星彩");
        list.add(map3);
        Map<String, String> map4 = new HashMap<String, String>();
        map4.put("text", "排列三");
        list.add(map4);
        Map<String, String> map5 = new HashMap<String, String>();
        map5.put("text", "22选5");
        list.add(map5);
        Map<String, String> map6 = new HashMap<String, String>();
        map6.put("text", "3D");
        list.add(map6);
        Map<String, String> map7 = new HashMap<String, String>();
        map7.put("text", "七乐彩");
        list.add(map7);
        Map<String, String> map8 = new HashMap<String, String>();
        map8.put("text", "快三");
        list.add(map8);
        Map<String, String> map9 = new HashMap<String, String>();
        map9.put("text", "排列五");
        list.add(map9);
        return list;
    }

    private List<Map<String, String>> data3() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map4 = new HashMap<String, String>();
        map4.put("text", "全部时间");
        list.add(map4);
        Map<String, String> map = new HashMap<String, String>();
        map.put("text","近一周");
        list.add(map);
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("text","近一个月");
        list.add(map1);
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("text","近三个月");
        list.add(map2);
        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("text","近六个月");
        list.add(map3);
        return list;
    }
}
