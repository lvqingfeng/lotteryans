package com.juyikeji.caipiao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.utils.SharedPreferencesUtil;
import com.juyikeji.caipiao.utils.URLConnectionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 意见反馈
 */
public class Feedback extends Activity implements View.OnClickListener {
    private ImageView iv_fanhui;
    private EditText et_feed;
    private Button bt_confirm;

    private static final String url = "feedback";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        init();
    }

    /**
     * 实例化控件
     */
    private void init() {
        iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(this);
        et_feed = (EditText) findViewById(R.id.et_feed);
        bt_confirm = (Button) findViewById(R.id.bt_confirm);
        bt_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_fanhui:
                finish();
                break;
            //提交
            case R.id.bt_confirm:
                String feed = et_feed.getText().toString().trim();
                if ("".equals(feed)) {
                    Toast.makeText(Feedback.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    request(feed);
                }
                break;
        }
    }

    /**
     * 请求服务器的方法
     */
    private void request(final String feed) {
        final String token = SharedPreferencesUtil.getSharedPreferences(Feedback.this).get("token").toString();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    JSONObject jobj = null;
                    try {
                        jobj = new JSONObject(result);
                        if ("1".equals(jobj.getString("status"))) {
                            Toast.makeText(Feedback.this, "反馈成功", Toast.LENGTH_SHORT).show();
                            finish();
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
                super.run();
                Map<String, String> map = new HashMap<String, String>();
                map.put("token", token);
                map.put("backcontent", feed);

                Log.i("Feedback", map + "");
                try {
                    result = URLConnectionUtil.sendPostRequest(url, map, "utf-8", 0);
                    Log.i("Feedback", result);
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
