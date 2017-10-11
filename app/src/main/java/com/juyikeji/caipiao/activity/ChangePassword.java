package com.juyikeji.caipiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.utils.SharedPreferencesUtil;
import com.juyikeji.caipiao.utils.URLConnectionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改密码
 */
public class ChangePassword extends Activity implements View.OnClickListener {
    //返回
    private ImageView fanhui;
    //当前密码，新密码，新密码2
    private EditText dq_password, new_password, new_password2;
    //提交
    private Button bt_submit;
    private TextView tv_bt;

    private String result="";
    private String name_space="";

    private String code="",pwd="";
    private RelativeLayout dq_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Intent intent=getIntent();
        code=intent.getStringExtra("code");
        pwd=intent.getStringExtra("pwd");

        init();

        if ("1".equals(code)) {//支付密码
            if("-1".equals(pwd)){//没有支付密码
                dq_layout.setVisibility(View.GONE);
                tv_bt.setText("设置支付密码");
                name_space = "setpaypwd";
//                Toast.makeText(ChangePassword.this,"设置支付密码",Toast.LENGTH_SHORT).show();
            }else{//有支付密码
                tv_bt.setText("修改支付密码");
                name_space = "editpaypwd";
//                Toast.makeText(ChangePassword.this,"修改支付密码",Toast.LENGTH_SHORT).show();
            }
        } else if ("0".equals(code)) {//用户密码
            tv_bt.setText("修改用户密码");
            name_space = "editpassword";
//            Toast.makeText(ChangePassword.this,"修改用户密码",Toast.LENGTH_SHORT).show();
        }

    }

    private void init() {
        fanhui = (ImageView) findViewById(R.id.fanhui);
        fanhui.setOnClickListener(this);
        dq_password = (EditText) findViewById(R.id.dq_password);
        new_password = (EditText) findViewById(R.id.new_password);
        new_password2 = (EditText) findViewById(R.id.new_password2);
        bt_submit= (Button) findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(this);

        tv_bt= (TextView) findViewById(R.id.tv_bt);

        dq_layout= (RelativeLayout) findViewById(R.id.dq_layout);
    }

    @Override
    public void onClick(View v) {
        String token = SharedPreferencesUtil.getSharedPreferences(ChangePassword.this).get("token").toString();
        String repassword = new_password.getText().toString().trim();
        switch (v.getId()) {
            case R.id.fanhui:
                finish();
                break;
            //提交
            case R.id.bt_submit:
                if ("1".equals(code)) {//支付密码
                    if (!"-1".equals(pwd)) {//有支付密码
                        String password = dq_password.getText().toString().trim();
                        String repassword2 = new_password2.getText().toString().trim();

                        if (repassword2.equals(repassword)) {
                            Map<String, String> map = new HashMap<String, String>();
                            // 获取令牌放入请求参数；
                            map.put("token", token);
                            map.put("password", password);
                            map.put("repassword", repassword);
                            request(map);

                        } else {
                            Toast.makeText(ChangePassword.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                        }
                    } else {//没有支付密码
                        //设置支付密码
                        Map<String, String> map = new HashMap<String, String>();
                        // 获取令牌放入请求参数；
                        map.put("token", token);
                        map.put("paypwd", repassword);

                        request(map);

                    }
                }else{//用户密码
                    String password = dq_password.getText().toString().trim();
                    String repassword2 = new_password2.getText().toString().trim();
                    if (repassword2.equals(repassword)) {
                        Map<String, String> map = new HashMap<String, String>();
                        // 获取令牌放入请求参数；
                        map.put("token", token);
                        map.put("password", password);
                        map.put("repassword", repassword);
                        request(map);

                    } else {
                        Toast.makeText(ChangePassword.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void request(final Map<String,String> map){
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    JSONObject jobj = null;
                    try {
                        jobj = new JSONObject(result);
                        String code = jobj.getString("status");
                        String msg2=jobj.getString("msg");
                        if (code.equals("1")) {
                            Toast.makeText(ChangePassword.this, msg2, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChangePassword.this, msg2, Toast.LENGTH_SHORT).show();
                        }
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
                    Log.i("map",map.toString()+name_space);
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
}
