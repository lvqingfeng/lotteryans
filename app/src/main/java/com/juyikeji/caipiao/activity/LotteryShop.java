package com.juyikeji.caipiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.utils.SharedPreferencesUtil;
import com.juyikeji.caipiao.utils.URLConnectionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 身份证号，修改昵称，真实姓名
 */
public class LotteryShop extends Activity implements View.OnClickListener{
    //返回
    private ImageView fanhui;
    //编号
    private EditText ed_bh;
    //提交
    private Button bt_submit;
    private TextView biaoti;

    private String code="";

    private String result="";
    //修改昵称接口
    private String name_space="";
    private String name="",message="";
    private Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotteryshop);

        Intent intent=getIntent();
        code=intent.getStringExtra("code");
        message=intent.getStringExtra("name");

        init();
    }

    /**
     * 实例化控件并设置监听
     */
    private void init(){
        fanhui= (ImageView) findViewById(R.id.fanhui);
        fanhui.setOnClickListener(this);

        ed_bh= (EditText) findViewById(R.id.ed_bh);
        bt_submit= (Button) findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(this);

        biaoti= (TextView) findViewById(R.id.biaoti);
        if("1".equals(code)){
            biaoti.setText("身份证号");
            ed_bh.setText(message);
            name_space="editid";
//        }else if("0".equals(code)){
//            biaoti.setText("彩票店编号");
        }else if("2".equals(code)){
            biaoti.setText("修改昵称");
            ed_bh.setText(message);
            name_space="editnickname";
        }else if("3".equals(code)){
            biaoti.setText("真实姓名");
            ed_bh.setText(message);
            name_space="editname";
        }
        //设置EdiText光标的位置
        ed_bh.setSelection(message.length());
    }

    /**
     * 修改昵称——接口
     */
    private void request(){
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    JSONObject jobj = null;
                    try {
                        jobj = new JSONObject(result);
                        String code1 = jobj.getString("status");
                        String msg2=jobj.getString("msg");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        new Thread() {
            @Override
            public void run() {

                try {
                    result = URLConnectionUtil.sendPostRequest(name_space, map, "utf-8", 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message m = new Message();
                m.what = 1;
                // 发送消息到Handler
                handler.sendMessage(m);

            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fanhui:
                finish();
                break;
            //提交
            case R.id.bt_submit:
                final String token= SharedPreferencesUtil.getSharedPreferences(LotteryShop.this).get("token").toString();
                name=ed_bh.getText().toString().trim();
                if("2".equals(code)){//修改昵称
                    map = new HashMap<String, String>();
                    map.put("token", token);
                    map.put("nickname",name);

                }else if("1".equals(code)){//身份证号
                    map = new HashMap<String, String>();
                    map.put("token", token);
                    map.put("idnumber",name);

                }else if("3".equals(code)){//真实姓名
                    map = new HashMap<String, String>();
                    map.put("token", token);
                    map.put("name",name);

                }
                request();
                Intent intent = new Intent();
                intent.putExtra("nn", name);
                setResult(111, intent);
                finish();
                break;
        }
    }
}
