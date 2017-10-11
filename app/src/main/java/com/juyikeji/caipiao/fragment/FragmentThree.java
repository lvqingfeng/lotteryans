package com.juyikeji.caipiao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.activity.ChangePassword;
import com.juyikeji.caipiao.activity.Feedback;
import com.juyikeji.caipiao.activity.LoginActivity;
import com.juyikeji.caipiao.activity.MyWallet;
import com.juyikeji.caipiao.activity.Myprofile;
import com.juyikeji.caipiao.activity.PhoneAttestation2;
import com.juyikeji.caipiao.utils.SharedPreferencesUtil;
import com.juyikeji.caipiao.utils.URLConnectionUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人中心
 */
public class FragmentThree extends Fragment implements View.OnClickListener {
    private View view;
    //登录或注册、我的钱包、彩票店编号、手机认证、密码管理、意见反馈
    private RelativeLayout LoginOrRegister, mywallet, lotteryshop,
            phoneattestation, pwdmanage, fankui;
    //安全退出
    private Button bt_exit;
    private ImageView iv_touxiang, iv_right;

    private TextView tv_user, tv_bh;

    private String result = "";
    //获取个人信息
    private String name_space = "";
    private String phone="";

    private ImageLoader imageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_three, container, false);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

        init();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if ((Boolean) SharedPreferencesUtil.getSharedPreferences(getActivity()).get("login")) {
            iv_right.setVisibility(View.GONE);
            LoginOrRegister.setClickable(false);

            bt_exit.setVisibility(View.VISIBLE);
            request();
        } else {
            tv_user.setText("立即登录/注册");
            iv_right.setVisibility(View.VISIBLE);
            LoginOrRegister.setClickable(true);

            bt_exit.setVisibility(View.GONE);
        }
    }

    /**
     * 实例化控件并设置监听
     */
    private void init() {
        LoginOrRegister = (RelativeLayout) view.findViewById(R.id.LoginOrRegister);
        LoginOrRegister.setOnClickListener(this);

        mywallet = (RelativeLayout) view.findViewById(R.id.mywallet);
        mywallet.setOnClickListener(this);

        phoneattestation = (RelativeLayout) view.findViewById(R.id.phoneattestation);
        phoneattestation.setOnClickListener(this);

        pwdmanage = (RelativeLayout) view.findViewById(R.id.pwdmanage);
        pwdmanage.setOnClickListener(this);

        fankui = (RelativeLayout) view.findViewById(R.id.fankui);
        fankui.setOnClickListener(this);

        bt_exit = (Button) view.findViewById(R.id.bt_exit);
        bt_exit.setOnClickListener(this);

        iv_touxiang = (ImageView) view.findViewById(R.id.iv_touxiang);
        iv_touxiang.setOnClickListener(this);

        tv_user = (TextView) view.findViewById(R.id.tv_user);
        iv_right = (ImageView) view.findViewById(R.id.iv_right);
        //彩票店编号
        tv_bh = (TextView) view.findViewById(R.id.tv_bh);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登录或注册
            case R.id.LoginOrRegister:
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);
                break;
            //我的钱包
            case R.id.mywallet:
                if ((Boolean) SharedPreferencesUtil.getSharedPreferences(getActivity()).get("login")) {
                    Intent intent2 = new Intent(getActivity(), MyWallet.class);
                    startActivity(intent2);
                }else {
                    Toast.makeText(getActivity(),"请登录",Toast.LENGTH_SHORT).show();
                }
                break;
            //手机认证
            case R.id.phoneattestation:
                if ((Boolean) SharedPreferencesUtil.getSharedPreferences(getActivity()).get("login")) {
                    Intent intent4 = new Intent(getActivity(), PhoneAttestation2.class);
                    intent4.putExtra("phone", phone);
                    startActivity(intent4);
                }else {
                    Toast.makeText(getActivity(),"请登录",Toast.LENGTH_SHORT).show();
                }
                break;
            //密码管理
            case R.id.pwdmanage:
                if ((Boolean) SharedPreferencesUtil.getSharedPreferences(getActivity()).get("login")) {
                    Intent intent6 = new Intent(getActivity(), ChangePassword.class);
                    intent6.putExtra("code", "0");
                    startActivity(intent6);
                }else {
                    Toast.makeText(getActivity(),"请登录",Toast.LENGTH_SHORT).show();
                }
                break;
            //意见反馈
            case R.id.fankui:
                startActivity(new Intent(getActivity(), Feedback.class));
                break;
            //安全退出
            case R.id.bt_exit:
                if ((Boolean) SharedPreferencesUtil.getSharedPreferences(getActivity()).get("login")) {
                    exit();
                }else {
                    Toast.makeText(getActivity(),"请登录",Toast.LENGTH_SHORT).show();
                }
                break;
            //我的资料
            case R.id.iv_touxiang:
                if ((Boolean) SharedPreferencesUtil.getSharedPreferences(getActivity()).get("login")) {
                    Intent intent = new Intent(getActivity(), Myprofile.class);
                    intent.putExtra("result", result);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"请登录",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 获取用户信息
     */
    private void request() {
        final String token = SharedPreferencesUtil.getSharedPreferences(getActivity()).get("token").toString();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    JSONObject jobj = null;
                    try {
                        jobj = new JSONObject(result);
                        JSONObject jo = jobj.getJSONObject("data");
                        String headImg = jo.getString("headImg");
                        String nickname = jo.getString("nickname");
                        String shopnumber = jo.getString("shopnumber");
                        phone=jo.getString("phone");

                        tv_bh.setText(shopnumber);
                        if(!"null".equals(nickname)){
                            tv_user.setText(nickname);
                        }else {
                            tv_user.setText("请设置昵称");
                        }

                        imageLoader.displayImage(headImg, iv_touxiang, SharedPreferencesUtil.getDefaultOptions());

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
                    Log.i("FragmentThree", result);
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

    /**
     * 确定要退出吗——弹出框
     */
    private void exit() {
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.confirm_dialog);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        TextView tv_cancel = (TextView) window.findViewById(R.id.tv_cancel);
        TextView tv_confirm = (TextView) window.findViewById(R.id.tv_confirm);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferencesUtil.setSharedPreferences(getActivity(), false);
                SharedPreferencesUtil.setSharedPreferencess(getActivity(), false, "");

                alertDialog.dismiss();
                tv_user.setText("立即登录/注册");
                iv_right.setVisibility(View.VISIBLE);
                LoginOrRegister.setClickable(true);

                iv_touxiang.setImageResource(R.mipmap.touxiang);
                bt_exit.setVisibility(View.GONE);
                tv_bh.setText("");
            }
        });
    }

}
