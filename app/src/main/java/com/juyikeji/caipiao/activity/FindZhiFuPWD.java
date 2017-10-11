package com.juyikeji.caipiao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.utils.URLConnectionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class FindZhiFuPWD extends Activity implements View.OnClickListener{

    private Button btn_close,btn_gain,btn_ok,btn_eyes;
    private EditText edit_user,edit_pwd,edit_yzm;
    private TextView tv_title;
    private boolean edit_eyes=true;
    int i = 45;
    public final static String APPKEY = "ff2dddb34d60";
    public final static String APPSECRET = "38caae7d00e11380db158c21ad794902";

    private String findpwd="";
    private String pwd_space="getpaypwd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_loginfindpwd);

        initView();
    }

    /**
     *实例化控件
     */
    private void initView(){
        tv_title=(TextView)findViewById(R.id.tv_title);
        tv_title.setText("支付密码找回");
        btn_close=(Button)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        btn_gain=(Button)findViewById(R.id.btn_gain);
        btn_gain.setOnClickListener(this);
        btn_ok=(Button)findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        btn_eyes=(Button)findViewById(R.id.btn_eyes);
        btn_eyes.setOnClickListener(this);

        edit_user=(EditText)findViewById(R.id.edit_user);
        edit_pwd=(EditText)findViewById(R.id.edit_pwd);
        edit_yzm=(EditText)findViewById(R.id.edit_yzm);

        //验证码的设置
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


    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        String phoneNums = edit_user.getText().toString().trim();
        switch (v.getId()){
            case R.id.btn_close:
                finish();
                break;
            case R.id.btn_gain:
                //                获取验证码
                if (!judgePhoneNums(phoneNums)) {
                    return;
                }
                if (!URLConnectionUtil.isOpenNetwork(this)) {
                    Toast.makeText(FindZhiFuPWD.this, "网络无连接，请检查网络！", Toast.LENGTH_SHORT).show();
                } else {
                    //  通过sdk发送短信验证
                    SMSSDK.getVerificationCode("86", phoneNums);

                    btn_gain.setClickable(false);
                    btn_gain.setText("重新发送（" + i + ")");
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
            case R.id.btn_ok:
                //确定
                showLotter();
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

        }
    }


    /**
     * 请求网络获取个人钱包
     */
    private void showLotter() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    try {
                        JSONObject jobj = new JSONObject(findpwd);
                        if ("1".equals(jobj.getString("status"))) {
                            finish();
                            Toast.makeText(FindZhiFuPWD.this, jobj.getString("msg"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FindZhiFuPWD.this, jobj.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        // 启动线程来执行任务
        new Thread() {
            public void run() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("phone", edit_user.getText().toString());
                map.put("code", edit_yzm.getText().toString().trim());
                map.put("paypwd", edit_pwd.getText().toString().trim());
                map.put("identify","android");
                // 请求网络
                try {
                    findpwd = URLConnectionUtil.sendPostRequest(pwd_space,
                            map, "UTF-8", 0);
                    Log.i("findpwd",findpwd);
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

    /**
     * 发送验证码
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                btn_gain.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                btn_gain.setText("获取验证码");
                btn_gain.setClickable(true);
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
        Toast.makeText(FindZhiFuPWD.this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
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
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
