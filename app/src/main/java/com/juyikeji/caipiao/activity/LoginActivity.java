package com.juyikeji.caipiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.bean.Url;
import com.juyikeji.caipiao.utils.SharedPreferencesUtil;
import com.juyikeji.caipiao.utils.URLConnectionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jyg on 2016/3/1 0001.
 */
public class LoginActivity extends Activity implements View.OnClickListener{

    /**
     * 页面控件
     */
    private Button btn_close,btn_eyes,btn_login;
    private TextView tv_losepwd,tv_zc;
    private EditText edit_user,edit_pwd;

    private boolean edit_eyes=true;//密码默认不可见

    String imei = "";

    //验证码的网络接口
    private String name_space = Url.LOGIN;
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        initView();
        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
    }

    /**
     *实例化控件
     */
    private void initView(){
        btn_close=(Button)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        btn_eyes=(Button)findViewById(R.id.btn_eyes);
        btn_eyes.setOnClickListener(this);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        tv_losepwd=(TextView)findViewById(R.id.tv_losepwd);
        tv_losepwd.setOnClickListener(this);
        tv_zc=(TextView)findViewById(R.id.tv_zc);
        tv_zc.setOnClickListener(this);

        edit_user=(EditText)findViewById(R.id.edit_user);
        edit_pwd=(EditText)findViewById(R.id.edit_pwd);
    }


    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close:
                finish();
                break;
            case R.id.btn_login:
                //登录
                Login();
                break;
            case R.id.btn_eyes:
                //显示密码
                if (edit_eyes){
                    edit_eyes=false;
                    // 显示密码
                    edit_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    btn_eyes.setBackgroundResource(R.mipmap.open_eyes);
                }else {
                    edit_eyes=true;
                    // 隐藏密码
                    edit_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btn_eyes.setBackgroundResource(R.mipmap.close_eyes);
                }

                break;
            case R.id.tv_losepwd:
                //忘记密码？
                startActivity(new Intent(this, LoginFindPwdActivity.class));
                break;
            case R.id.tv_zc:
                //注册
                startActivity(new Intent(this,LoginZcActivity.class));
                break;

        }
    }

    /**
     * 请求网络注册
     */
    private void Login() {
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
                map.put("username", edit_user.getText().toString());
                map.put("password", edit_pwd.getText().toString().trim());
                map.put("imei", imei);
                // 请求网络
                try {
                    result = URLConnectionUtil.sendPostRequest(name_space,
                            map, "UTF-8", 0);
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
            if (status.equals("1")) {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                String token=jobj.getString("token");
                SharedPreferencesUtil.setSharedPreferencess(LoginActivity.this, true, token);
                finish();
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

            }else {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
