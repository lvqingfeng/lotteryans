package com.juyikeji.caipiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.adapter.MyWalletAdapter;
import com.juyikeji.caipiao.bean.Bill;
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
 * 我的钱包
 */
public class MyWallet extends Activity implements View.OnClickListener {
    private ImageView fanhui;
    private ListView bill_listview;
    private TextView tv_xgpwd, balance;

    private final static String name_space = "wallet";
    private String result = "";
    //余额，是否有密码（-1没有密码）
    private String mymoney, paypwd;
    //收支明细数据源
    private List<Bill> list;
    //收支明细adapter
    private MyWalletAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        init();
        request();
    }

    private void init() {
        tv_xgpwd = (TextView) findViewById(R.id.tv_xgpwd);
        tv_xgpwd.setOnClickListener(this);
        fanhui = (ImageView) findViewById(R.id.fanhui);
        fanhui.setOnClickListener(this);
        bill_listview = (ListView) findViewById(R.id.bill_listview);

        balance = (TextView) findViewById(R.id.balance);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.tv_xgpwd:
                Intent intent3 = new Intent(MyWallet.this, ChangePassword.class);
                intent3.putExtra("code", "1");
                intent3.putExtra("pwd", paypwd);
                startActivity(intent3);
                break;
        }
    }

    /**
     * 我的钱包——接口
     */
    private void request() {
        final String token = SharedPreferencesUtil.getSharedPreferences(MyWallet.this).get("token").toString();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    try {
                        JSONObject jo = new JSONObject(result);
                        mymoney = jo.getString("mymoney");
                        paypwd = jo.getString("paypwd");

                        balance.setText(mymoney+"元");
                        if ("-1".equals(paypwd)) {
                            tv_xgpwd.setText("设置支付密码");
                        } else {
                            tv_xgpwd.setText("修改支付密码");
                        }
                        list = new ArrayList<Bill>();
                        JSONArray ja = jo.getJSONArray("data");
                        for (int i=0;i<ja.length();i++) {
                            //获取账单信息
                            Bill b = new Bill();
                            if("1".equals(ja.getJSONObject(i).getString("runtype"))){
                                b.setCategory("支出");
                                b.setMoney("-"+ja.getJSONObject(i).getString("qty")+"元");
                            }else {
                                b.setCategory("充值");
                                b.setMoney("+"+ja.getJSONObject(i).getString("qty")+"元");
                            }
                                b.setDate(ja.getJSONObject(i).getString("crttime"));

                            list.add(b);
                        }

                        adapter = new MyWalletAdapter(MyWallet.this, list);
                        bill_listview.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                super.run();
                Map<String, String> map = new HashMap<String, String>();
                map.put("token", token);

                try {
                    result = URLConnectionUtil.sendPostRequest(name_space, map, "utf-8", 0);
                    Log.i("MyWallet", result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message m = new Message();
                m.what = 1;
                handler.sendMessage(m);
            }
        }.start();
    }
}
