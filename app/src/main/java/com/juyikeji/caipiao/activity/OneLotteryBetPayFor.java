package com.juyikeji.caipiao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juyikeji.caipiao.MainActivity;
import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.adapter.FragmentOneKJXXAdapter;
import com.juyikeji.caipiao.bean.Url;
import com.juyikeji.caipiao.utils.MyCombine;
import com.juyikeji.caipiao.utils.SevenColor;
import com.juyikeji.caipiao.utils.SysApplication;
import com.juyikeji.caipiao.utils.URLConnectionUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jyg on 2016/3/7 0007.
 */
public class OneLotteryBetPayFor extends Activity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /**
     * 彩票选球的容器
     */
    List<String> list;
    List<List<Integer>> redlist;
    List<String> reddlist;//胆码
    List<Integer> reddsizelist;//胆码
    List<String> bluedlist;//篮球胆码
    List<Integer> bluedsizelist;//篮球胆码
    List<List<Integer>> bluelist;
    //七星彩彩球的集合;
    List<List<int[]>> sevenlist;

    private Button btn_close;
    private TextView tv_pay, tv_money, tv_balance;
    private ProgressBar progress;

    //彩球总花费
    private int money = 0;
    /**
     * bs彩球大类（双色球。。）smalltype彩球小类（单排），period购彩期数
     * playname玩法名称（机选，胆选等）mymoney我的资产multiple购彩注数betmoney每注金额
     */
    private String bs = "", period = "", playname = "", mymoney = "", multiple = "";
    /**
     * 选择的彩票号码
     */
    private String lotterynumber = "";

    //获取资产的接口
    private String name_space = Url.MYMONEY;
    String result = "";

    //支付接口
    private String name_payfor = Url.PAYFOR;
    String payfor = "";

    //双色球的提交接口
    private String name_commit = Url.COMMIT;
    private String commit = "";
    //提交接口返回的支付参数
    private String jurisdiction = "";
    //令牌
    String token = "";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_one_lotterybetpayfor);
        sharedPreferences = getSharedPreferences("bill", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        initView();
    }

    @Override
    protected void onStart() {
        if (URLConnectionUtil.isOpenNetwork(this)) {
            showLotter();//获取个人财产
        } else {
            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
        }
        super.onStart();
    }

    /**
     * 实例化控件
     */
    private void initView() {
        intent = getIntent();
        period = intent.getIntExtra("kjrq", 0) + "";
        multiple = intent.getStringExtra("multiple");
        if (multiple.equals("")) {
            multiple = "1";//如果传过来的倍数为null默认为1
        }
        switch (intent.getIntExtra("bs", 0)) {
            case 1:
                bs = "ssq";
                playname = "";
                lotterynumber = getDoublecolor();
                break;
            case 2:
                bs = "dlt";
                playname = "";
                lotterynumber = getDaletou();
                break;
            case 4:
                bs = "qxc";
                playname = "";
                lotterynumber = getSevencolor();
                break;
            case 6:
                bs = "zyfc22x5";
                playname = "";
                lotterynumber = getTTF();
                break;
            case 8:
                bs = "qlc";
                playname = "";
                lotterynumber = getSevenHappy();
                break;
            case 3:
            case 5:
            case 7:
            case 9:
            case 10:
                jurisdiction = intent.getStringExtra("msg");
                break;
        }
        money = intent.getIntExtra("money", 0);
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);

        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_money.setText("￥" + money + ".0");
        tv_balance = (TextView) findViewById(R.id.tv_balance);
        tv_pay = (TextView) findViewById(R.id.tv_pay);
        tv_pay.setOnClickListener(this);

        progress = (ProgressBar) findViewById(R.id.progress);
    }

    /**
     * 页面点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                finish();
                break;
            case R.id.tv_pay:
                //支付
                if ("".equals(mymoney)) {
                    showLotter();//从新获取个人资产
                } else {
                    if ((Integer.parseInt(mymoney.trim()) - money) > 0) {
                        mmDialog();
                    } else {
                        Toast.makeText(this, "账户余额不足", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

        }
    }

    /**
     * 支付密码的弹出框
     */
    private void mmDialog() {

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .create();
//         新建一个空edittext让弹出界面出现软键盘在show方法之前
        dialog.setView(new EditText(this));

        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.fragment_one_lotterybetpayfor_dialog);
        // 设置宽高
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //去掉默认的背景
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);// 动画效果

        TextView tv_close = (TextView) window.findViewById(R.id.tv_close);
        TextView tv_losepwd = (TextView) window.findViewById(R.id.tv_losepwd);
        TextView tv_money = (TextView) window.findViewById(R.id.tv_money);
        tv_money.setText("￥ " + money);
        TextView tv_pay = (TextView) window.findViewById(R.id.tv_pay);
        final EditText edit_mm = (EditText) window.findViewById(R.id.edit_mm);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_losepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OneLotteryBetPayFor.this, FindZhiFuPWD.class));
            }
        });
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_mm.getText().toString().equals("")) {
                    Toast.makeText(OneLotteryBetPayFor.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                switch (intent.getIntExtra("bs", 0)) {
                    case 3:
                    case 5:
                    case 7:
                    case 9:
                    case 10:
                        dialog.dismiss();
                        progress.setVisibility(View.VISIBLE);
                        payFor(edit_mm);
                        break;
                    default:
                        dialog.dismiss();
                        progress.setVisibility(View.VISIBLE);
                        saveCommit(edit_mm);
                        break;
                }
            }
        });
    }

    /**
     * 支付成功的弹出框
     */
    private void PayDialog() {

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .create();
        dialog.show();
        Window window = dialog.getWindow();
        final PopupWindow popup = new PopupWindow(this);
        // 设置布局
        window.setContentView(R.layout.fragment_one_lotterybetpayfor_dialogok);
        // 设置宽高
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //去掉默认的背景
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        // 设置弹出的动画效果
//        window.setWindowAnimations(android.R.anim.overshoot_interpolator);
        window.setWindowAnimations(R.style.AnimBottom);// 动画效果

        RelativeLayout rel_tz = (RelativeLayout) window.findViewById(R.id.rel_tz);
        TextView tv_payok = (TextView) window.findViewById(R.id.tv_payok);
        rel_tz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SysApplication.getInstance().exit();
                finish();
                dialog.dismiss();
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });
        tv_payok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SysApplication.getInstance().exit();
                finish();
                dialog.dismiss();
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });
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
                        JSONObject jobj = new JSONObject(result);
                        if ("1".equals(jobj.getString("status"))) {
                            mymoney = jobj.getString("mymoney");
                            tv_balance.setText("￥" + mymoney + ".0");
                        } else {
                            Toast.makeText(OneLotteryBetPayFor.this, jobj.getString("msg"), Toast.LENGTH_SHORT).show();
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
                map.put("token", token);
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

    //支付接口
    private void payFor(final EditText editText) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    //解析
                    progress.setVisibility(View.GONE);
                    try {
                        JSONObject jobj = new JSONObject(payfor);
                        if ("1".equals(jobj.getString("status"))) {
                            PayDialog();
                        } else {
                            Toast.makeText(OneLotteryBetPayFor.this, jobj.getString("msg"), Toast.LENGTH_SHORT).show();
                            Log.i("payfor", jobj.getString("msg"));
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
                map.put("money", money + "");
                map.put("token", token);
                map.put("paypwd", editText.getText().toString().trim());
                map.put("orderids", jurisdiction);
                // 请求网络
                try {
                    payfor = URLConnectionUtil.sendPostRequest(name_payfor,
                            map, "UTF-8", 1);
                    Log.i("payfor", payfor);
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
     * 请求网络提交彩票信息
     */
    private void saveCommit(final EditText editText) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    try {
                        JSONObject jobj = new JSONObject(commit);
                        if ("1".equals(jobj.getString("status"))) {
                            jurisdiction = jobj.getString("msg");
                            Log.i("jurisdiction", "jurisdiction" + jurisdiction);
                            payFor(editText);
                        } else {
                            Toast.makeText(OneLotteryBetPayFor.this, jobj.getString("msg"), Toast.LENGTH_SHORT).show();
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
                map.put("token", token);
                map.put("lotterytype", bs);
                map.put("smalltype", "单式");
                map.put("playname", "普通投注");
                map.put("lotterynumber", lotterynumber);
                map.put("period", period);
                map.put("multiple", multiple);
                map.put("betmoney", "2");
                // 请求网络
                try {
                    commit = URLConnectionUtil.sendPostRequest(name_commit,
                            map, "UTF-8", 1);
                    Log.i("jurisdiction", commit);
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
     * 获取双色球选择的号码
     */
    private void getSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        //红球的集合
        redlist = new ArrayList<List<Integer>>();
        //篮球的集合
        bluelist = new ArrayList<List<Integer>>();

        reddlist = new ArrayList<String>();
        reddsizelist = new ArrayList<Integer>();

        bluedlist = new ArrayList<String>();
        bluedsizelist = new ArrayList<Integer>();
        int time = sharedPreferences.getInt("time", 0);

        for (int i = 0; i < time; i++) {

            int reddsize = sharedPreferences.getInt("reddsize" + i, 0);//胆码的个数
            String listnumbd = sharedPreferences.getString("redd" + i, "");
            /**
             * 到reddlist.add(listnummbd)为止，中间只是为了将10一下的数转换为01，02....
             */
            String listnummbd = "";
            for (int a = 0; a < reddsize; a++) {
                String ss = listnumbd.substring(0, listnumbd.indexOf(" "));//截取每个胆码
                if (Integer.parseInt(ss) < 10) {
                    listnummbd = listnummbd+"0" + ss+" ";
                }else {
                    listnummbd = listnummbd+ss+" ";
                }
                listnumbd = listnumbd.substring(listnumbd.indexOf(" ") + 1);//将剩余胆码重置从新截取
            }
            reddlist.add(listnummbd);
//            reddlist.add(listnumbd);
            reddsizelist.add(reddsize);
            int redsize = sharedPreferences.getInt("redsize" + i, 0);//红球的个数
            List<Integer> listnumb = new ArrayList<Integer>();
            for (int j = 0; j < redsize; j++) {
                listnumb.add(sharedPreferences.getInt("red" + i + j, 0));
            }
            redlist.add(listnumb);
        }
        for (int i = 0; i < time; i++) {
            int bluedsize = sharedPreferences.getInt("bluedsize" + i, 0);//胆码的个数
            String listnumbd = sharedPreferences.getString("blued" + i, "");
            /**
             * 到reddlist.add(listnummbd)为止，中间只是为了将10一下的数转换为01，02....
             */
            String listnummbd = "";
            for (int a = 0; a < bluedsize; a++) {
                String ss = listnumbd.substring(0, listnumbd.indexOf(" "));//截取每个胆码
                if (Integer.parseInt(ss) < 10) {
                    listnummbd = listnummbd+"0" + ss+" ";
                }else {
                    listnummbd = listnummbd+ss+" ";
                }
                listnumbd = listnumbd.substring(listnumbd.indexOf(" ") + 1);//将剩余胆码重置从新截取
            }
            bluedlist.add(listnummbd);
            bluedsizelist.add(bluedsize);
            int bluesize = sharedPreferences.getInt("bluesize" + i, 0);//蓝球的个数
            List<Integer> listnumb = new ArrayList<Integer>();
            for (int j = 0; j < bluesize; j++) {
                listnumb.add(sharedPreferences.getInt("blue" + i + j, 0));
            }
            bluelist.add(listnumb);
        }
    }

    /**
     * listview容器赋值(双色球)
     */
    private String getDoublecolor() {
        getSharedPreferences();
        list = new ArrayList<String>();
        List<String> slist = new ArrayList<String>();
        String[] s;
        //遍历获取红球的集合
        for (int i = 0; i < redlist.size(); i++) {
            int size = reddsizelist.get(i);//胆码的个数
            String sd = reddlist.get(i);//胆码
            sd = sd.replace(" ", ",");//将字符串中用来分割的标识替换成“，”
            s = new String[redlist.get(i).size()];//创建获取红球的集合
            for (int j = 0; j < redlist.get(i).size(); j++) {
                if (redlist.get(i).get(j)<10){//将小于10的数字转为String类型时前面加0
                    s[j] = "0"+redlist.get(i).get(j);
                }else {
                    s[j] = redlist.get(i).get(j) + "";
                }
            }
            slist = combine(s, 6 - size);
            for (int k = 0; k < bluelist.get(i).size(); k++) {
                for (int j = 0; j < slist.size(); j++) {
                    String blue="";
                    if(bluelist.get(i).get(k)<10){
                        blue="0"+bluelist.get(i).get(k);
                    }else {
                        blue=bluelist.get(i).get(k)+"";
                    }
                    list.add(sd + slist.get(j).substring(0, slist.get(j).length() - 1) + "+" + blue);
                }
            }
        }
        //将结果遍历出来输出String字符串
        String ssss = "";
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                ssss = ssss + "-" + list.get(i);
            } else {
                ssss = list.get(i);
            }
        }
        return ssss;
    }

    /**
     * listview容器赋值(大乐透)
     */
    private String getDaletou() {
        getSharedPreferences();
        list = new ArrayList<String>();
        List<String> slist = new ArrayList<String>();
        List<String> blist = new ArrayList<String>();
        String[] sr;//红球的集合
        String[] sb;//篮球的集合
        //遍历获取红球的集合
        for (int i = 0; i < redlist.size(); i++) {
            int size = reddsizelist.get(i);//胆码的个数
            String sd = reddlist.get(i);//胆码
            sd = sd.replace(" ", ",");//将字符串中用来分割的标识替换成“，”
            sr = new String[redlist.get(i).size()];
            for (int j = 0; j < redlist.get(i).size(); j++) {
                if (redlist.get(i).get(j)<10){//将小于10的数字转为String类型时前面加0
                    sr[j] = "0"+redlist.get(i).get(j);
                }else {
                    sr[j] = redlist.get(i).get(j) + "";
                }
            }
            slist = combine(sr, 5 - size);
            //遍历获取蓝球的集合
            int sizeb = bluedsizelist.get(i);//篮球胆码的个数
            String sdb = bluedlist.get(i);//篮球胆码
            sdb = sdb.replace(" ", ",");//将字符串中用来分割的标识替换成“，”
            sb = new String[bluelist.get(i).size()];
            for (int j = 0; j < bluelist.get(i).size(); j++) {
                if (bluelist.get(i).get(j)<10){//将小于10的数字转为String类型时前面加0
                    sb[j] = "0"+bluelist.get(i).get(j);
                }else {
                    sb[j] = bluelist.get(i).get(j) + "";
                }
            }
            blist = combine(sb, 2 - sizeb);
            for (int k = 0; k < blist.size(); k++) {
                for (int j = 0; j < slist.size(); j++) {
                    list.add(sd + slist.get(j).substring(0, slist.get(j).length() - 1) + "+" + sdb + blist.get(k).substring(0, blist.get(k).length() - 1));
                }
            }
        }

        //将结果遍历出来输出String字符串
        String ssss = "";
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                ssss = ssss + "-" + list.get(i);
            } else {
                ssss = list.get(i);
            }
        }
        return ssss;
    }

    /**
     * 获取七星彩选择的号码
     */
    private List<List<int[]>> getSevenColorShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        //球的集合
        List<List<int[]>> sevenlist = new ArrayList<List<int[]>>();

        int time = sharedPreferences.getInt("time", 0);
        for (int i = 0; i < time; i++) {
            List<int[]> listnumb = new ArrayList<int[]>();
            for (int j = 0; j < 7; j++) {//获取每个位置的号码
                int size = sharedPreferences.getInt("sevencolorsize" + i + j, 0);
                int[] sevencolor = new int[size];
                for (int k = 0; k < size; k++) {
                    sevencolor[k] = sharedPreferences.getInt("sevencolor" + i + j + k, 0);
                }
                listnumb.add(sevencolor);
            }
            sevenlist.add(listnumb);
        }
        return sevenlist;
    }

    /**
     * listview容器赋值(七星彩)
     */
    private String getSevencolor() {
        sevenlist = getSevenColorShared();
        list = new ArrayList<String>();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = getNumb(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        //将结果遍历出来输出String字符串
        String ssss = "";
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                ssss = ssss + "-" + list.get(i);
            } else {
                ssss = list.get(i);
            }
        }
        Log.i("ssss", ssss);
        return ssss;
    }

    /**
     * 获取七乐彩(22选5)选择的号码
     */
    private List<List<Integer>> getSevenHappyShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        //红球的集合
        List<List<Integer>> redlist = new ArrayList<List<Integer>>();
        int time = sharedPreferences.getInt("time", 0);
        for (int i = 0; i < time; i++) {
            int redsize = sharedPreferences.getInt("redsize" + i, 0);//红球的个数
            List<Integer> listnumb = new ArrayList<Integer>();
            for (int j = 0; j < redsize; j++) {
                listnumb.add(sharedPreferences.getInt("red" + i + j, 0));
            }
            redlist.add(listnumb);
        }
        return redlist;
    }

    /**
     * listview容器赋值(七乐彩)
     */

    private String getSevenHappy() {
        redlist = getSevenHappyShared();
        list = new ArrayList<String>();
        String[] s;
        //遍历获取红球的集合
        for (int i = 0; i < redlist.size(); i++) {
            s = new String[redlist.get(i).size()];
            for (int j = 0; j < redlist.get(i).size(); j++) {
                s[j] = redlist.get(i).get(j) + "";
            }
            list.addAll(combine(s, 7));
        }
        //将结果遍历出来输出String字符串
        String ssss = "";
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                ssss = ssss + "-" + list.get(i).substring(0, list.get(i).length() - 1);
            } else {
                ssss = list.get(i).substring(0, list.get(i).length() - 1);
            }
        }
        Log.i("ssss", ssss);
        return ssss;
    }

    /**
     * listview容器赋值(22选5)
     */

    private String getTTF() {
        list = new ArrayList<String>();
        redlist = getSevenHappyShared();
        String[] s;
        //遍历获取红球的集合
        for (int i = 0; i < redlist.size(); i++) {
            s = new String[redlist.get(i).size()];
            for (int j = 0; j < redlist.get(i).size(); j++) {
                s[j] = redlist.get(i).get(j) + "";
            }
            list.addAll(combine(s, 5));
        }
        //将结果遍历出来输出String字符串
        String ssss = "";
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                ssss = ssss + "-" + list.get(i).substring(0, list.get(i).length() - 1);
            } else {
                ssss = list.get(i).substring(0, list.get(i).length() - 1);
            }
        }
        Log.i("ssss", ssss);
        return ssss;
    }


    /**
     * 实现的算法
     *
     * @param a   数据数组
     * @param num M选N中 N的个数 参数a为M，num为N
     * @return
     */
    private List<String> combine(String[] a, int num) {
        List<String> list = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        String[] b = new String[a.length];
        for (int i = 0; i < b.length; i++) {
            if (i < num) {
                b[i] = "1";
            } else
                b[i] = "0";
        }

        int point = 0;
        int nextPoint = 0;
        int count = 0;
        int sum = 0;
        String temp = "1";
        while (true) {
            // 判断是否全部移位完毕
            for (int i = b.length - 1; i >= b.length - num; i--) {
                if (b[i].equals("1"))
                    sum += 1;
            }
            // 根据移位生成数据
            for (int i = 0; i < b.length; i++) {
                if (b[i].equals("1")) {
                    point = i;
                    sb.append(a[point]);
                    sb.append(",");
                    count++;
                    if (count == num)
                        break;
                }
            }
            // 往返回值列表添加数据
            list.add(sb.toString());

            // 当数组的最后num位全部为1 退出
            if (sum == num) {
                break;
            }
            sum = 0;

            // 修改从左往右第一个10变成01
            for (int i = 0; i < b.length - 1; i++) {
                if (b[i].equals("1") && b[i + 1].equals("0")) {
                    point = i;
                    nextPoint = i + 1;
                    b[point] = "0";
                    b[nextPoint] = "1";
                    break;
                }
            }
            // 将 i-point个元素的1往前移动 0往后移动
            for (int i = 0; i < point - 1; i++)
                for (int j = i; j < point - 1; j++) {
                    if (b[i].equals("0")) {
                        temp = b[i];
                        b[i] = b[j + 1];
                        b[j + 1] = temp;
                    }
                }
            // 清空 StringBuffer
            sb.setLength(0);
            count = 0;
        }
        //
        return list;

    }

    //七星彩的算法
    public List<String> getNumb(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        for (int a = 0; a < list.get(0).length; a++) {
            String s = "";
            if (list.get(0)[a]<10){
                s=s+"0"+list.get(0)[a]+",";
            }else {
                s = s + list.get(0)[a] + ",";
            }
            for (int b = 0; b < list.get(1).length; b++) {
                Log.i("kjdfo", list.get(1).length + "");
                String s6 = s;
                if (list.get(1)[b]<10){
                    s=s+"0"+list.get(1)[b]+",";
                }else {
                    s = s + list.get(1)[b] + ",";
                }
                for (int c = 0; c < list.get(2).length; c++) {
                    String s5 = s;
                    if (list.get(2)[c]<10){
                        s=s+"0"+list.get(2)[c]+",";
                    }else {
                        s = s + list.get(2)[c] + ",";
                    }
                    for (int d = 0; d < list.get(3).length; d++) {
                        String s4 = s;
                        if (list.get(3)[d]<10){
                            s=s+"0"+list.get(3)[d]+",";
                        }else {
                            s = s + list.get(3)[d] + ",";
                        }
                        for (int e = 0; e < list.get(4).length; e++) {
                            String s3 = s;
                            if (list.get(4)[e]<10){
                                s=s+"0"+list.get(4)[e]+",";
                            }else {
                                s = s + list.get(4)[e] + ",";
                            }
                            for (int f = 0; f < list.get(5).length; f++) {
                                String s2 = s;
                                if (list.get(5)[f]<10){
                                    s=s+"0"+list.get(5)[f]+",";
                                }else {
                                    s = s + list.get(5)[f] + ",";
                                }
                                for (int g = 0; g < list.get(6).length; g++) {
                                    String s1 = s;
                                    if (list.get(6)[g]<10){
                                        s=s1+"0"+list.get(6)[g];
                                    }else {
                                        s = s1 + list.get(6)[g];
                                    }
                                    Log.i("sevencolor", s);
                                    servnnumb.add(s);
                                    s = s1;
                                }
                                s = s2;
                            }
                            s = s3;
                        }
                        s = s4;
                    }
                    s = s5;
                }
                s = s6;
            }
        }
        return servnnumb;
    }
}
