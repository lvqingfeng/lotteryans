package com.juyikeji.caipiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 手机认证
 */
public class PhoneAttestation extends Activity implements View.OnClickListener {
    private EditText et_phone, et_code;
    private Button bt_code, bt_submit;
    private ImageView fanhui;

    public final static String APPKEY = "ff2dddb34d60";
    public final static String APPSECRET = "38caae7d00e11380db158c21ad794902";
    int i = 45;

    private final static String name_space="checkphone";
    private String result="",status="",message="";

    private String phone="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_attestation);

        phone=getIntent().getStringExtra("phone");
        init();

    }

    private void init() {
        fanhui = (ImageView) findViewById(R.id.fanhui);
        fanhui.setOnClickListener(this);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_phone.setText(phone);
        et_code = (EditText) findViewById(R.id.et_code);
        bt_code = (Button) findViewById(R.id.bt_code);
        bt_code.setOnClickListener(this);
        bt_submit = (Button) findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(this);

        SMSSDK.initSDK(this, APPKEY, APPSECRET);
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public void onClick(View v) {
        String phoneNums = et_phone.getText().toString().trim();
        String phoneCode=et_code.getText().toString().trim();
        switch (v.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.bt_code:
                if (!judgePhoneNums(phoneNums)) {
                    return;
                }
                if (!URLConnectionUtil.isOpenNetwork(this)) {
                    Toast.makeText(PhoneAttestation.this, "网络无连接，请检查网络！", Toast.LENGTH_SHORT).show();
                } else {
                    //  通过sdk发送短信验证
                    SMSSDK.getVerificationCode("86", phoneNums);

                    bt_code.setClickable(false);
                    bt_code.setText("重新发送（" + i + ")");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (; i > 0; i--) {
                                handler.sendEmptyMessage(-9);
                                if (i <= 0) {
                                    break;
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            handler.sendEmptyMessage(-8);
                        }
                    }).start();
                }
                break;
            case R.id.bt_submit:
                request();

                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                bt_code.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                bt_code.setText("获取验证码");
                bt_code.setClickable(true);
                i = 45;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        }

    };

    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     * @return
     */
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11) && isMobileNO(phoneNums)) {

            return true;
        }
        Toast.makeText(PhoneAttestation.this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 判断一个字符串的位数
     *
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     *
     * @param mobileNums
     * @return
     */
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    /**
     * 手机号认证——接口
     */
    private void request(){
        final String token= SharedPreferencesUtil.getSharedPreferences(PhoneAttestation.this).get("token").toString();
        final String code=et_code.getText().toString();
        final String phone=et_phone.getText().toString();
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    try {
                        JSONObject jo=new JSONObject(result);
                        status=jo.getString("status");
                        message=jo.getString("msg");
                        if("1".equals(status)){
                            Intent intent=new Intent(PhoneAttestation.this,XgPhone.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(PhoneAttestation.this,message,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(){
            @Override
            public void run() {
                super.run();
                Map<String,String> map=new HashMap<String, String>();
                map.put("token",token);
                map.put("phone",phone);
                map.put("code",code);
                map.put("identify","android");

                try {
                    result= URLConnectionUtil.sendPostRequest(name_space, map, "utf-8", 0);
                    Log.i("PhoneAttestation", result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message m=new Message();
                m.what=1;
                handler.sendMessage(m);
            }
        }.start();
    }
}
