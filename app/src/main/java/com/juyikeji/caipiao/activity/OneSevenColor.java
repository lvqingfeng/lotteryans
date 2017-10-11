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
import android.widget.TextView;
import android.widget.Toast;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.adapter.FragmentOneKJXXAdapter;
import com.juyikeji.caipiao.adapter.OneSevenColorgvAdapter;
import com.juyikeji.caipiao.bean.Url;
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

/**
 * Created by jyg on 2016/3/10 0010.
 */
public class OneSevenColor extends Activity implements View.OnClickListener {

    private Button btn_close, btn_clear;
    private ImageView iv_xl;
    private TextView tv_time, tv_money, tv_choose, tv_next;
    //获取往期中奖信息的网络接口
    private String name_space = Url.LOTTERYMESSAGE;
    String result = "";
    //最近得奖信息下拉列表
    private ListView lv;
    private FragmentOneKJXXAdapter adapter;
    private List<String[]> listis;
    private LinearLayout linout1;
    private View view;

    private List<List<Integer>> list;
    private MyGridView gv1, gv2, gv3, gv4, gv5, gv6, gv7;
    private OneSevenColorgvAdapter adapter1, adapter2, adapter3, adapter4, adapter5, adapter6, adapter7;

    private boolean zst = false;
    private int kjrq;//开奖日期
    private String pausetime="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_one_sevencolor);
        SysApplication.getInstance().addActivity(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        showLotter();//请求网络获取往期中奖彩票信息
        list = new ArrayList<List<Integer>>();
        initView();
        int item = sharedPreferences.getInt("item", 0);
        if (item >= 0) {
            showMoney();
        }
    }

    private void getGv() {
        gv1 = (MyGridView) findViewById(R.id.gv1);
        gv2 = (MyGridView) findViewById(R.id.gv2);
        gv3 = (MyGridView) findViewById(R.id.gv3);
        gv4 = (MyGridView) findViewById(R.id.gv4);
        gv5 = (MyGridView) findViewById(R.id.gv5);
        gv6 = (MyGridView) findViewById(R.id.gv6);
        gv7 = (MyGridView) findViewById(R.id.gv7);
        adapter1 = new OneSevenColorgvAdapter(this, getSharedPreferences().get(0));
        adapter2 = new OneSevenColorgvAdapter(this,getSharedPreferences().get(1));
        adapter3 = new OneSevenColorgvAdapter(this,getSharedPreferences().get(2));
        adapter4 = new OneSevenColorgvAdapter(this,getSharedPreferences().get(3));
        adapter5 = new OneSevenColorgvAdapter(this,getSharedPreferences().get(4));
        adapter6 = new OneSevenColorgvAdapter(this,getSharedPreferences().get(5));
        adapter7 = new OneSevenColorgvAdapter(this,getSharedPreferences().get(6));
        gv1.setAdapter(adapter1);
        gv2.setAdapter(adapter2);
        gv3.setAdapter(adapter3);
        gv4.setAdapter(adapter4);
        gv5.setAdapter(adapter5);
        gv6.setAdapter(adapter6);
        gv7.setAdapter(adapter7);
        gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                adapter1.showColor(i);
                showMoney();
            }
        });
        gv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                adapter2.showColor(i);
                showMoney();
            }
        });
        gv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                adapter3.showColor(i);
                showMoney();
            }
        });
        gv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                adapter4.showColor(i);
                showMoney();
            }
        });
        gv5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                adapter5.showColor(i);
                showMoney();
            }
        });
        gv6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                adapter6.showColor(i);
                showMoney();
            }
        });
        gv7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                adapter7.showColor(i);
                showMoney();
            }
        });
    }

    /**
     * 实例化控件
     */
    private void initView() {
        linout1 = (LinearLayout) findViewById(R.id.linout1);
        view = (View) findViewById(R.id.view);
        lv = (ListView) findViewById(R.id.lv);
        //griview实例化并加载adapter
        getGv();
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);

        iv_xl = (ImageView) findViewById(R.id.iv_xl);
        iv_xl.setOnClickListener(this);

        tv_choose = (TextView) findViewById(R.id.tv_choose);
        tv_choose.setOnClickListener(this);
        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_money.setText("七个位置都要选择号码");
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                //返回
                finish();
                break;
            case R.id.btn_clear:
                //删除
                adapter1.getMselect();
                adapter2.getMselect();
                adapter3.getMselect();
                adapter4.getMselect();
                adapter5.getMselect();
                adapter6.getMselect();
                adapter7.getMselect();
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
            case R.id.tv_choose:
                //机选
                adapter1.changeNumber();
                adapter2.changeNumber();
                adapter3.changeNumber();
                adapter4.changeNumber();
                adapter5.changeNumber();
                adapter6.changeNumber();
                adapter7.changeNumber();
                showMoney();
                break;
            case R.id.tv_next:
                //下一步
                if (isEmpty()) {
                    Toast.makeText(this, "七个位置都要选择号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                list.add(adapter1.getNumb());
                list.add(adapter2.getNumb());
                list.add(adapter3.getNumb());
                list.add(adapter4.getNumb());
                list.add(adapter5.getNumb());
                list.add(adapter6.getNumb());
                list.add(adapter7.getNumb());
                setShared(list);
                Intent intent = new Intent(this, OneLotteryBetPayx.class);
                intent.putExtra("彩种", 4);
                intent.putExtra("kjrq", kjrq);
                intent.putExtra("pausetime", pausetime);
                startActivity(intent);
                break;
        }
    }

    /**
     * 根据彩球数获取注数和钱数
     */
    private void showMoney() {
        if (!isEmpty()) {
            int numb1 = adapter1.limit + adapter2.limit + adapter3.limit + adapter4.limit + adapter5.limit + adapter6.limit + adapter7.limit;
            int numb2 = adapter1.limit * adapter2.limit * adapter3.limit * adapter4.limit * adapter5.limit * adapter6.limit * adapter7.limit;
            tv_money.setText(numb1 + "个球" + " 共" + numb2 + "注 " + 2 * numb2 + "元");
        } else {
            tv_money.setText("七个位置都要选择号码");
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
                map.put("lotterytype", "ssq");
                // 请求网络
                try {
                    result = URLConnectionUtil.sendPostRequest(name_space,
                            map, "UTF-8", 1);
                    Log.i("result", result);
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
            listis = new ArrayList<String[]>();
            if (status.equals("1")) {
                //开奖日期
                String date = jobj.getString("date");
                tv_time.setText(date);
                pausetime=jobj.getString("pausetime");
                JSONArray data = jobj.getJSONArray("data");
                kjrq=Integer.parseInt(data.getJSONObject(0).getString("expect"))+1;
                for (int i = 0; i < data.length(); i++) {
                    JSONObject datai = data.getJSONObject(i);
                    //彩票开票的日期
                    String expect = datai.getString("expect");
                    //开奖号码
                    String opencode = datai.getString("opencode");
                    String[] s = {expect, opencode};
                    listis.add(s);
                }
                adapter = new FragmentOneKJXXAdapter(this, listis, 4);
                lv.setAdapter(adapter);
            } else {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 获取shared中的号码
    private List<int[]> getSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        //球的集合
        int item = sharedPreferences.getInt("item", 0);

        List<int[]> listnumb = new ArrayList<int[]>();
        for (int j = 0; j < 7; j++) {//获取每个位置的号码
            int size = sharedPreferences.getInt("sevencolorsize" + item + j, 0);
            int[] sevencolor = new int[size];
            for (int k = 0; k < size; k++) {
                sevencolor[k] = sharedPreferences.getInt("sevencolor" + item + j + k, 0);
            }
            listnumb.add(sevencolor);
        }
        return listnumb;
    }

    /**
     * 将小球保存到shared
     *
     * @param list
     */
    private void setShared(List<List<Integer>> list) {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int time = sharedPreferences.getInt("time", 0);
        int item = sharedPreferences.getInt("item", 0);
        if (item >= 0) {
            for (int k = 0; k < list.size(); k++) {//这一层循环为区分7个号码位置
                editor.putInt("sevencolorsize" + item + k, list.get(k).size());
                for (int i = 0; i < list.get(k).size(); i++) {//这一层循环为设置每一位置的被选中号码
                    editor.putInt("sevencolor" + item + k + i, list.get(k).get(i));
                }
            }
        } else {
            for (int k = 0; k < list.size(); k++) {//这一层循环为区分7个号码位置
                editor.putInt("sevencolorsize" + time + k, list.get(k).size());
                for (int i = 0; i < list.get(k).size(); i++) {//这一层循环为设置每一位置的被选中号码
                    editor.putInt("sevencolor" + time + k + i, list.get(k).get(i));
                }
            }
            editor.putInt("time", time + 1);
        }
        editor.commit();
    }

    /**
     * 判断选球是否有位置选择为空
     *
     * @return
     */
    private boolean isEmpty() {

        if (0 == adapter1.getNumb().size() || 0 == adapter2.getNumb().size() ||
                0 == adapter3.getNumb().size() || 0 == adapter4.getNumb().size() ||
                0 == adapter5.getNumb().size() || 0 == adapter6.getNumb().size() ||
                0 == adapter7.getNumb().size()) {
            return true;
        }
        return false;
    }
}
