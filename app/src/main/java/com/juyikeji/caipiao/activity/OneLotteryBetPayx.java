package com.juyikeji.caipiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.adapter.OneLotteryBetPayAdapter22;
import com.juyikeji.caipiao.adapter.OneLotteryBetPayAdapterDLT;
import com.juyikeji.caipiao.adapter.OneLotteryBetPayAdapterScolor;
import com.juyikeji.caipiao.adapter.OneLotteryBetPayAdapterx;
import com.juyikeji.caipiao.adapter.OneYongTanDuoGuanAdapter;
import com.juyikeji.caipiao.arrangefive.OneArrangefiveAdapter;
import com.juyikeji.caipiao.directly.OneDirectlyAdapter;
import com.juyikeji.caipiao.fastthree.OneFastThreeAdapter;
import com.juyikeji.caipiao.threed.OneThreeDAdapter;
import com.juyikeji.caipiao.utils.MyCombine;
import com.juyikeji.caipiao.utils.SevenColor;
import com.juyikeji.caipiao.utils.SharedPreferencesUtil;
import com.juyikeji.caipiao.utils.SysApplication;
import com.juyikeji.caipiao.utils.URLConnectionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jyg on 2016/3/6 0006.
 */
public class OneLotteryBetPayx extends Activity implements View.OnClickListener {

    private Button btn_close, btn_agree;
    private TextView tv_stoptime, tv_goon, tv_choose, tv_money, tv_next;
    private EditText edit_number;
    private ListView lv;
    private OneLotteryBetPayAdapterx adapter;
    private OneLotteryBetPayAdapterDLT adapterDLT;
    private OneLotteryBetPayAdapter22 adapter22;
    private OneDirectlyAdapter adapter1;
    private OneArrangefiveAdapter adapter2;
    private OneThreeDAdapter adapter3;
    private OneFastThreeAdapter adapter4;
    private OneLotteryBetPayAdapterScolor adapterScolor;
    private List<String> list;
    private MyCombine myCombine = new MyCombine();

    //双色球红球和蓝球的集合
    List<List<Integer>> redlist;
    List<List<Integer>> bluelist;
    List<String> reddlist;//胆码
    List<Integer> reddsizelist;//胆码长度
    List<String> bluedlist;//篮球胆码
    List<Integer> bluedsizelist;//篮球胆码长度
    //七星彩彩球的集合;
    List<List<int[]>> sevenlist;
    //排列三选号集合
    List<List<Integer>> wanlist;//万位
    List<List<Integer>> qianlist;//千位
    List<List<Integer>> hunlist;//百位
    List<List<Integer>> tenlist;//十位
    List<List<Integer>> indlist;//个位
    //泳坛夺冠
    List<List<Integer>> ziyouyonglist;//自由泳选号
    List<List<String>> ziyouyonglist1;//自由泳分类
    List<List<Integer>> yangyonglist;//仰泳
    List<List<Integer>> wayonglist;//蛙泳
    List<List<Integer>> dieyonglist;//蝶泳

    int code, erlei, sanlei, cs = 3;
    int kjrq, caizhong;
    //停售日期
    private String pausetime = "";

    private OneYongTanDuoGuanAdapter ytdgadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_one_lotterybetpay);
        SysApplication.getInstance().addActivity(this);
        init();
    }

    /**
     * 实例化控件
     */
    private void init() {
        tv_money = (TextView) findViewById(R.id.tv_money);
        edit_number = (EditText) findViewById(R.id.edit_number);
        lv = (ListView) findViewById(R.id.lv);
        switch (witchOne()) {
            case 0:
                //默认参数表示传输错误

                break;
            case 1:
                //双色球
                adapter = new OneLotteryBetPayAdapterx(this, getSharedPreferences(), tv_money, edit_number);
                lv.setAdapter(adapter);
                break;
            case 2:
                //大乐透
                adapterDLT = new OneLotteryBetPayAdapterDLT(this, getSharedPreferences(), tv_money, edit_number);
                lv.setAdapter(adapterDLT);
                break;
            case 3://泳坛夺冠
                if (erlei == 5) {
                    ytdgadapter = new OneYongTanDuoGuanAdapter(this, getSharedPreferences6(), code, tv_money, edit_number, erlei, sanlei);
                } else {
                    ytdgadapter = new OneYongTanDuoGuanAdapter(this, getSharedPreferences6(), code, tv_money, edit_number, erlei);
                }
                lv.setAdapter(ytdgadapter);
                break;
            case 4:
                adapterScolor = new OneLotteryBetPayAdapterScolor(this, getSevenColorShared(), tv_money, edit_number);
                lv.setAdapter(adapterScolor);
                break;
            case 5://排列三
                adapter1 = new OneDirectlyAdapter(this, getSharedPreferences2(), code, tv_money, edit_number);
                lv.setAdapter(adapter1);
                break;
            case 6:
                //22选5
                adapter22 = new OneLotteryBetPayAdapter22(this, getSevenHappyShared(), tv_money, edit_number, 0);
                lv.setAdapter(adapter22);
                break;
            case 8:
                //七乐彩
                adapter22 = new OneLotteryBetPayAdapter22(this, getSevenHappyShared(), tv_money, edit_number, 1);
                lv.setAdapter(adapter22);
                break;
            case 7://3D
                adapter3 = new OneThreeDAdapter(this, getSharedPreferences4(), code, tv_money, edit_number);
                lv.setAdapter(adapter3);
                break;
            case 9://快三
                adapter4 = new OneFastThreeAdapter(this, getSharedPreferences5(), code, tv_money, edit_number);
                lv.setAdapter(adapter4);
                break;
            case 10://排列五
                adapter2 = new OneArrangefiveAdapter(this, getSharedPreferences3(), code, tv_money, edit_number);
                lv.setAdapter(adapter2);
                break;
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("item", position);
                editor.commit();
                finish();
            }
        });

        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        btn_agree = (Button) findViewById(R.id.btn_agree);
        btn_agree.setOnClickListener(this);

        tv_stoptime = (TextView) findViewById(R.id.tv_stoptime);
        tv_stoptime.setText(pausetime);

        tv_goon = (TextView) findViewById(R.id.tv_goon);
        tv_goon.setOnClickListener(this);
        tv_choose = (TextView) findViewById(R.id.tv_choose);
        tv_choose.setOnClickListener(this);
        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);

        //edittext监听事件
        edit_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (witchOne()) {
                    case 1:
                        adapter.getMoney();
                        break;
                    case 2:
                        adapterDLT.getMoney();
                        break;
                    case 4:
                        adapterScolor.getMoney();
                        break;
                    case 6:
                        adapter22.getMoney();
                        break;
                    case 8:
                        adapter22.getMoney();
                        break;
                    case 3://泳坛夺冠
                        if (erlei == 1) {//任选
                            switch (code) {
                                case 0://任选一
                                    ytdgadapter.getMoney();
                                    break;
                                case 1://任选二
                                    ytdgadapter.getMoney1();
                                    break;
                                case 2://任选二全包
                                    ytdgadapter.getMoney2();
                                    break;
                                case 3://任选三
                                    ytdgadapter.getMoney3();
                                    break;
                                case 4://任选三全包
                                    ytdgadapter.getMoney4();
                                    break;
                            }
                        } else if (erlei == 2) {//直选
                            switch (code) {
                                case 0://直选单式
                                    ytdgadapter.getMoney5();
                                    break;
                                case 1://直选复式
                                    ytdgadapter.getMoney5();
                                    break;
                                case 2://直选组合
                                    ytdgadapter.getMoney6();
                                    break;
                            }
                        } else if (erlei == 3) {//组选
                            switch (code) {
                                case 0://组选24单式
                                case 3://
                                case 7:
                                case 10:
                                    ytdgadapter.getMoney5();
                                    break;
                                case 1://
                                    ytdgadapter.getMoney7();
                                    break;
                                case 2://
                                    ytdgadapter.getMoney8();
                                    break;
                                case 4://
                                    ytdgadapter.getMoney9();
                                    break;
                                case 5:
                                    ytdgadapter.getMoney10();
                                    break;
                                case 6:
                                    ytdgadapter.getMoney11();
                                    break;
                                case 8:
                                    ytdgadapter.getMoney12();
                                    break;
                                case 9:
                                    ytdgadapter.getMoney13();
                                    break;
                                case 11:
                                    ytdgadapter.getMoney14();
                                    break;
                                case 12://组选4胆拖
                                    ytdgadapter.getMoney15();
                                    break;
                                case 13://组选4重胆拖
                                    ytdgadapter.getMoney16();
                                    break;
                            }
                        } else if (erlei == 4) {//前三组选
                            switch (code) {
                                case 0://前三组3
                                case 2://任三组3
                                    ytdgadapter.getMoney17();
                                    break;
                                case 1://前三组6
                                case 3://任三组6
                                    ytdgadapter.getMoney18();
                                    break;
                            }
                        } else if (erlei == 5) {//新玩法
                            switch (code) {
                                case 0://组选3码全包
                                    ytdgadapter.getMoney19();
                                    break;
                                case 1://组选2码全包
                                    ytdgadapter.getMoney20();
                                    break;
                                case 2://选4全包
                                    if (sanlei == 0) {//选四不重全包
                                        ytdgadapter.getMoney22();
                                    } else if (sanlei == 1) {//选四一对全包
                                        ytdgadapter.getMoney24();
                                    } else if (sanlei == 2) {//选四两对全包
                                        ytdgadapter.getMoney23();
                                    } else if (sanlei == 3) {//选四三条全包
                                        ytdgadapter.getMoney25();
                                    }
                                    break;
                                case 3://重号全包
                                    ytdgadapter.getMoney21();
                                    break;
                            }
                        }
                        break;
                    case 5://排列三
                        switch (code) {
                            case 0://直选三
                                adapter1.getMoney();
                                break;
                            case 1://组三单式
                                adapter1.getMoney1();
                                break;
                            case 2://组三复式
                                adapter1.getMoney2();
                                break;
                            case 3://组选六
                                adapter1.getMoney3();
                                break;
                            case 4://直选三和
                                adapter1.getMoney4();
                                break;
                        }
                        break;
                    case 7://3d
                        switch (code) {
                            case 0://直选
                                adapter3.getMoney();
                                break;
                            case 1://组三
                                adapter3.getMoney1();
                                break;
                            case 2://组六
                                adapter3.getMoney2();
                                break;
                        }

                        break;
                    case 9://快三
                        switch (code) {
                            case 0://和值
                                adapter4.getMoney();
                                break;
                            case 1://三同号
                            case 3://二同号复选
                                adapter4.getMoney();
                                break;
                            case 2://二同号单选
                                adapter4.getMoney1();
                                break;
                            case 4://三不同号
                                adapter4.getMoney2();
                                break;
                            case 5://二不同号
                                adapter4.getMoney3();
                                break;
                            case 6://三不同号胆拖
                                adapter4.getMoney4();
                                break;
                            case 7://二不同号胆拖
                                adapter4.getMoney1();
                                break;

                        }
                        break;
                    case 10://排列五
                        adapter2.getMoney();
                        break;
                }
            }
        });
    }

    /**
     * 判断跳转过来的是哪个彩种
     */
    private int witchOne() {
        Intent intent = getIntent();
        code = intent.getIntExtra("分类", 0);
        erlei = intent.getIntExtra("二类", 0);
        sanlei = intent.getIntExtra("三分类", 0);
        kjrq = intent.getIntExtra("kjrq", 0);
        caizhong = intent.getIntExtra("彩种", 0);
        pausetime = intent.getStringExtra("pausetime");
        return caizhong;
    }

    /**
     * 页面点击事件
     */
    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (v.getId()) {
            case R.id.btn_close:
                editor.clear();
                editor.putInt("time", 0);
                editor.putInt("item", -1);
                editor.commit();
                finish();
                break;
            case R.id.tv_goon:
                //继续添加
                editor.putInt("item", -1);
                editor.commit();
                finish();
                break;
            case R.id.tv_choose:
                //机选一注
                switch (witchOne()) {
                    case 1:
                        adapter.changeNumber();
                        break;
                    case 2:
                        adapterDLT.changeNumber();
                        break;
                    case 4:
                        adapterScolor.changeNumber();
                        break;
                    case 6:
                        adapter22.changeNumber();
                        break;
                    case 8:
                        adapter22.changeNumber();
                        break;
                    case 3://泳坛夺冠
                        if (erlei == 1) {//任选
                            switch (code) {
                                case 0://任选一
                                case 1://任选二
                                    ytdgadapter.changeNumber();
                                    break;
                                case 2://任选二全包
                                    ytdgadapter.changeNumber1();
                                    break;
                                case 3://任选三
                                    ytdgadapter.changeNumber();
                                    break;
                                case 4://任选三全包
                                    ytdgadapter.changeNumber2();
                                    break;
                            }
                        } else if (erlei == 2) {//直选
                            switch (code) {
                                case 0://直选单式
                                case 1://直选复式
                                case 2://直选组合
                                    ytdgadapter.changeNumber();
                                    break;
                            }

                        } else if (erlei == 3) {//组选
                            switch (code) {
                                case 0://组选24单式
                                    ytdgadapter.changeNumber3();
                                    break;
                                case 1://组选24复式
                                    ytdgadapter.changeNumber4();
                                    break;
                                case 2://组选24胆拖
                                    ytdgadapter.changeNumber5();
                                    break;
                                case 3://组选12单式
                                    ytdgadapter.changeNumber6();
                                    break;
                                case 4://组选12复式
                                case 8://组选6复式
                                    ytdgadapter.changeNumber7();
                                    break;
                                case 5://组选12胆拖
                                    ytdgadapter.changeNumber8();
                                    break;
                                case 6://组选12重胆拖
                                    ytdgadapter.changeNumber8();
                                    break;
                                case 7://组选6单式
                                    ytdgadapter.changeNumber9();
                                    break;
                                case 9://组选6胆拖
                                case 12://组选4胆拖
                                case 13://组选4重胆拖
                                    ytdgadapter.changeNumber10();
                                    break;
                                case 10://组选4单式
                                    ytdgadapter.changeNumber11();
                                    break;
                                case 11://组选4复式
                                    ytdgadapter.changeNumber12();
                                    break;
                            }
                        } else if (erlei == 4) {
                            switch (code) {
                                case 0://前三组3
                                    ytdgadapter.changeNumber12();
                                    break;
                                case 1://前三组6
                                    ytdgadapter.changeNumber7();
                                    break;
                                case 2://任三组3
                                    break;
                                case 3://任三组6
                                    break;
                            }
                        } else if (erlei == 5) {//新玩法
                            switch (code) {
                                case 0://组选3码全包
                                    ytdgadapter.changeNumber7();
                                    break;
                                case 1://组选2码全包
                                    ytdgadapter.changeNumber12();
                                    break;
                                case 2://选4全包
                                    if (sanlei == 0) {//选四不重全包
                                        ytdgadapter.changeNumber14();
                                    } else if (sanlei == 1) {//选四一对全包
                                        ytdgadapter.changeNumber15();
                                    } else if (sanlei == 2) {//选四两对全包
                                        ytdgadapter.changeNumber12();
                                    } else if (sanlei == 3) {//选四三条全包
                                        ytdgadapter.changeNumber16();
                                    }
                                    break;
                                case 3://重号全包
                                    ytdgadapter.changeNumber13();
                                    break;
                            }
                        }
                        break;
                    case 5:
                        switch (code) {
                            case 0://直选三
                                adapter1.changeNumber();
                                break;
                            case 1://组三单式
                                adapter1.changeNumber1();
                                break;
                            case 2://组选复式
                                adapter1.changeNumber2();
                                break;
                            case 3://组选六
                                adapter1.changeNumber3();
                                break;
                            case 4://组选三和
                                adapter1.changeNumber4();
                                break;
                        }
                        break;
                    case 7://3d
                        switch (code) {
                            case 0://直选
                                adapter3.changeNumber();
                                break;
                            case 1://组三
                                adapter3.changeNumber1();
                                break;
                            case 2://组六
                                adapter3.changeNumber2();
                                break;
                        }
                        break;
                    case 9://快三
                        switch (code) {
                            case 0://和值
                                adapter4.changeNumber();
                                break;
                            case 1://三同号
                            case 3://二同号复选
                                adapter4.changeNumber1();
                                break;
                            case 2://二同号单选
                                adapter4.changeNumber2();
                                break;
                            case 4://三不同号
                                adapter4.changeNumber3();
                                break;
                            case 5://二不同号
                                adapter4.changeNumber4();
                                break;
                            case 6://三不同号胆拖
                                adapter4.changeNumber5();
                                break;
                            case 7://二不同号胆拖
                                adapter4.changeNumber6();
                                break;
                        }
                        break;
                    case 10://排列五
                        adapter2.changeNumber();
                        break;
                }
                break;
            case R.id.btn_agree:
                //同意协议

                break;
            case R.id.tv_next:
                if (!URLConnectionUtil.isOpenNetwork(this)) {
                    Toast.makeText(this, "当前网络不可用，请打开网络", Toast.LENGTH_SHORT).show();
                } else {
                    if (!(boolean) SharedPreferencesUtil.getSharedPreferences(OneLotteryBetPayx.this).get("login")) {
                        Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
                    } else {
                        switch (witchOne()) {
                            case 1:
                            case 2:
                            case 4:
                            case 6:
                            case 8:
                                //提交
                                Intent intent = new Intent(OneLotteryBetPayx.this, OneLotteryBetPayFor.class);
                                intent.putExtra("bs", caizhong);
                                intent.putExtra("kjrq", kjrq);
                                intent.putExtra("multiple", edit_number.getText().toString().trim());
                                intent.putExtra("money", Integer.parseInt(tv_money.getText().toString().trim()));
                                startActivity(intent);
                                break;
                            case 3:
                            case 5:
                            case 7:
                            case 9:
                            case 10:
                                request();
                                break;
                        }
                    }
                }
                break;

        }
    }

    //提交
    private static final String name_space = "center/doorder";
    private String result = "";

    /**
     * 提交选号
     */
    private void request() {
        String arrangefivestr = "";
        String smalltype = "单式";
        String playname = "单式";
        String lotterytype = "";
        switch (witchOne()) {
            case 3://泳坛夺冠
                lotterytype = "sxrytdj";//sxrytdj
                List<Map<String, Object>> list5 = getSharedPreferences6();
                if (erlei == 1) {//任选
                    switch (code) {
                        case 0://任选一
                            smalltype = "直选组合";
                            playname = "任选一";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//自由泳
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//仰泳
                                List<Integer> wayonglist = (List<Integer>) list5.get(i).get("wayong");//蛙泳
                                List<Integer> dieyonglist = (List<Integer>) list5.get(i).get("dieyong");//蝶泳
                                if (ziyouyonglist != null) {
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + "_" + "," + "_" + "-";
                                    }
                                }
                                if (yangyonglist != null) {
                                    for (int a = 0; a < yangyonglist.size(); a++) {
                                        arrangefivestr = arrangefivestr + "_" + "," + yangyonglist.get(a) + "," + "_" + "," + "_" + "-";
                                    }
                                }
                                if (wayonglist != null) {
                                    for (int a = 0; a < wayonglist.size(); a++) {
                                        arrangefivestr = arrangefivestr + "_" + "," + "_" + "," + wayonglist.get(a) + "," + "_" + "-";
                                    }
                                }
                                if (dieyonglist != null) {
                                    for (int a = 0; a < dieyonglist.size(); a++) {
                                        arrangefivestr = arrangefivestr + "_" + "," + "_" + "," + "_" + "," + dieyonglist.get(a) + "-";
                                    }
                                }
                            }
                            break;
                        case 1://任选二
                            smalltype = "直选组合";
                            playname = "任选二";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//自由泳
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//仰泳
                                List<Integer> wayonglist = (List<Integer>) list5.get(i).get("wayong");//蛙泳
                                List<Integer> dieyonglist = (List<Integer>) list5.get(i).get("dieyong");//蝶泳
                                if (ziyouyonglist != null && yangyonglist != null) {
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < yangyonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + "_" + "," + "_" + "-";
                                        }
                                    }
                                }
                                if (ziyouyonglist != null && wayonglist != null) {
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < wayonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + wayonglist.get(b) + "," + "_" + "-";
                                        }
                                    }
                                }
                                if (ziyouyonglist != null && dieyonglist != null) {
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < dieyonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + "_" + "," + dieyonglist.get(a) + "-";
                                        }
                                    }
                                }
                                if (yangyonglist != null && wayonglist != null) {
                                    for (int a = 0; a < yangyonglist.size(); a++) {
                                        for (int b = 0; b < wayonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + "_" + "," + yangyonglist.get(a) + "," + wayonglist.get(b) + "," + "_" + "-";
                                        }
                                    }
                                }
                                if (yangyonglist != null && dieyonglist != null) {
                                    for (int a = 0; a < yangyonglist.size(); a++) {
                                        for (int b = 0; b < dieyonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + "_" + "," + yangyonglist.get(a) + "," + "_" + "," + dieyonglist.get(b) + "-";
                                        }
                                    }
                                }
                                if (wayonglist != null && dieyonglist != null) {
                                    for (int a = 0; a < wayonglist.size(); a++) {
                                        for (int b = 0; b < dieyonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + "_" + "," + "_" + "," + wayonglist.get(a) + "," + dieyonglist.get(b) + "-";
                                        }
                                    }
                                }
                            }
                            break;
                        case 2://任选二全包
                            smalltype = "任二全包";
                            playname = "任选二全包";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//自由泳
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//仰泳

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int j = 0; j < yangyonglist.size(); j++) {
                                        if (ziyouyonglist.get(a) != yangyonglist.get(j)) {
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "," + "_" + "," + "_" + "-";
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + yangyonglist.get(j) + "," + "_" + "-";
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + "_" + "," + yangyonglist.get(j) + "-";

                                            arrangefivestr = arrangefivestr + "_" + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "," + "_" + "-";
                                            arrangefivestr = arrangefivestr + "_" + "," + ziyouyonglist.get(a) + "," + "_" + "," + yangyonglist.get(j) + "-";
                                            arrangefivestr = arrangefivestr + "_" + "," + "_" + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "-";

                                            arrangefivestr = arrangefivestr + yangyonglist.get(j) + "," + ziyouyonglist.get(a) + "," + "_" + "," + "_" + "-";
                                            arrangefivestr = arrangefivestr + yangyonglist.get(j) + "," + "_" + "," + ziyouyonglist.get(a) + "," + "_" + "-";
                                            arrangefivestr = arrangefivestr + yangyonglist.get(j) + "," + "_" + "," + "_" + "," + ziyouyonglist.get(a) + "-";

                                            arrangefivestr = arrangefivestr + "_" + "," + yangyonglist.get(j) + "," + ziyouyonglist.get(a) + "," + "_" + "-";
                                            arrangefivestr = arrangefivestr + "_" + "," + yangyonglist.get(j) + "," + "_" + "," + ziyouyonglist.get(a) + "-";
                                            arrangefivestr = arrangefivestr + "_" + "," + "_" + "," + yangyonglist.get(j) + "," + ziyouyonglist.get(a) + "-";
                                        } else {
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "," + "_" + "," + "_" + "-";
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + yangyonglist.get(j) + "," + "_" + "-";
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + "_" + "," + yangyonglist.get(j) + "-";

                                            arrangefivestr = arrangefivestr + "_" + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "," + "_" + "-";
                                            arrangefivestr = arrangefivestr + "_" + "," + ziyouyonglist.get(a) + "," + "_" + "," + yangyonglist.get(j) + "-";
                                            arrangefivestr = arrangefivestr + "_" + "," + "_" + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "-";
                                        }
                                    }
                                }
                            }

                            break;
                        case 3://任选三
                            smalltype = "直选组合";
                            playname = "任选三";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//自由泳
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//仰泳
                                List<Integer> wayonglist = (List<Integer>) list5.get(i).get("wayong");//蛙泳
                                List<Integer> dieyonglist = (List<Integer>) list5.get(i).get("dieyong");//蝶泳
                                if (ziyouyonglist != null && yangyonglist != null && wayonglist != null) {
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < yangyonglist.size(); b++) {
                                            for (int c = 0; c < wayonglist.size(); c++) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + wayonglist.get(c) + "," + "_" + "-";
                                            }
                                        }
                                    }
                                }
                                if (ziyouyonglist != null && yangyonglist != null && dieyonglist != null) {
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < yangyonglist.size(); b++) {
                                            for (int c = 0; c < dieyonglist.size(); c++) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + "_" + "," + dieyonglist.get(c) + "-";
                                            }
                                        }
                                    }
                                }
                                if (ziyouyonglist != null && wayonglist != null && dieyonglist != null) {
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < wayonglist.size(); b++) {
                                            for (int c = 0; c < dieyonglist.size(); c++) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + wayonglist.get(b) + "," + dieyonglist.get(c) + "-";
                                            }
                                        }
                                    }
                                }
                                if (yangyonglist != null && wayonglist != null && dieyonglist != null) {
                                    for (int a = 0; a < yangyonglist.size(); a++) {
                                        for (int b = 0; b < wayonglist.size(); b++) {
                                            for (int c = 0; c < dieyonglist.size(); c++) {
                                                arrangefivestr = arrangefivestr + "_" + "," + yangyonglist.get(a) + "," + wayonglist.get(b) + "," + dieyonglist.get(c) + "-";
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 4://任选三全包
                            smalltype = "任三全包";
                            playname = "任选三全包";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//自由泳
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//仰泳
                                List<Integer> wayonglist = (List<Integer>) list5.get(i).get("wayong");//蛙泳
                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int j = 0; j < yangyonglist.size(); j++) {
                                        for (int k = 0; k < wayonglist.size(); k++) {
                                            if (ziyouyonglist.get(a) != yangyonglist.get(j) && ziyouyonglist.get(a) != wayonglist.get(k) && yangyonglist.get(j) != wayonglist.get(k)) {

                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "," + wayonglist.get(k) + "," + "_" + "-";
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "," + "_" + "," + wayonglist.get(k) + "-";
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + yangyonglist.get(j) + "," + wayonglist.get(k) + "-";
                                                arrangefivestr = arrangefivestr + "_" + ziyouyonglist.get(a) + "," + "," + yangyonglist.get(j) + "," + wayonglist.get(k) + "-";

                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + wayonglist.get(k) + "," + yangyonglist.get(j) + "," + "_" + "-";
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + wayonglist.get(k) + "," + "_" + "," + yangyonglist.get(j) + "-";
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + wayonglist.get(k) + "," + yangyonglist.get(j) + "-";
                                                arrangefivestr = arrangefivestr + "_" + ziyouyonglist.get(a) + "," + "," + wayonglist.get(k) + "," + yangyonglist.get(j) + "-";

                                                arrangefivestr = arrangefivestr + yangyonglist.get(j) + "," + ziyouyonglist.get(a) + "," + wayonglist.get(k) + "," + "_" + "-";
                                                arrangefivestr = arrangefivestr + yangyonglist.get(j) + "," + ziyouyonglist.get(a) + "," + "_" + "," + wayonglist.get(k) + "-";
                                                arrangefivestr = arrangefivestr + yangyonglist.get(j) + "," + "_" + "," + ziyouyonglist.get(a) + "," + wayonglist.get(k) + "-";
                                                arrangefivestr = arrangefivestr + "_" + yangyonglist.get(j) + "," + "," + ziyouyonglist.get(a) + "," + wayonglist.get(k) + "-";

                                                arrangefivestr = arrangefivestr + yangyonglist.get(j) + "," + wayonglist.get(k) + "," + ziyouyonglist.get(a) + "," + "_" + "-";
                                                arrangefivestr = arrangefivestr + yangyonglist.get(j) + "," + wayonglist.get(k) + "," + "_" + "," + ziyouyonglist.get(a) + "-";
                                                arrangefivestr = arrangefivestr + yangyonglist.get(j) + "," + "_" + "," + wayonglist.get(k) + "," + ziyouyonglist.get(a) + "-";
                                                arrangefivestr = arrangefivestr + "_" + "," + yangyonglist.get(j) + "," + wayonglist.get(k) + "," + ziyouyonglist.get(a) + "-";

                                                arrangefivestr = arrangefivestr + wayonglist.get(k) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "," + "_" + "-";
                                                arrangefivestr = arrangefivestr + wayonglist.get(k) + "," + ziyouyonglist.get(a) + "," + "_" + "," + yangyonglist.get(j) + "-";
                                                arrangefivestr = arrangefivestr + wayonglist.get(k) + "," + "_" + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "-";
                                                arrangefivestr = arrangefivestr + "_" + "," + wayonglist.get(k) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "-";

                                                arrangefivestr = arrangefivestr + wayonglist.get(k) + "," + yangyonglist.get(j) + "," + ziyouyonglist.get(a) + "," + "_" + "-";
                                                arrangefivestr = arrangefivestr + wayonglist.get(k) + "," + yangyonglist.get(j) + "," + "_" + "," + ziyouyonglist.get(a) + "-";
                                                arrangefivestr = arrangefivestr + wayonglist.get(k) + "," + "_" + "," + yangyonglist.get(j) + "," + ziyouyonglist.get(a) + "-";
                                                arrangefivestr = arrangefivestr + "_" + "," + wayonglist.get(k) + "," + yangyonglist.get(j) + "," + ziyouyonglist.get(a) + "-";

                                            } else if ((ziyouyonglist.get(a) == yangyonglist.get(j) && ziyouyonglist.get(a) != wayonglist.get(k))
                                                    || (ziyouyonglist.get(a) == wayonglist.get(k) && ziyouyonglist.get(a) != yangyonglist.get(j))
                                                    || (wayonglist.get(k) == yangyonglist.get(j) && ziyouyonglist.get(a) != wayonglist.get(k))) {

                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "," + wayonglist.get(k) + "," + "_" + "-";
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "," + "_" + "," + wayonglist.get(k) + "-";
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + yangyonglist.get(j) + "," + wayonglist.get(k) + "-";
                                                arrangefivestr = arrangefivestr + "_" + ziyouyonglist.get(a) + "," + "," + yangyonglist.get(j) + "," + wayonglist.get(k) + "-";

                                                arrangefivestr = arrangefivestr + yangyonglist.get(j) + "," + wayonglist.get(k) + "," + ziyouyonglist.get(a) + "," + "_" + "-";
                                                arrangefivestr = arrangefivestr + yangyonglist.get(j) + "," + wayonglist.get(k) + "," + "_" + "," + ziyouyonglist.get(a) + "-";
                                                arrangefivestr = arrangefivestr + yangyonglist.get(j) + "," + "_" + "," + wayonglist.get(k) + "," + ziyouyonglist.get(a) + "-";
                                                arrangefivestr = arrangefivestr + "_" + "," + yangyonglist.get(j) + "," + wayonglist.get(k) + "," + ziyouyonglist.get(a) + "-";

                                                arrangefivestr = arrangefivestr + wayonglist.get(k) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "," + "_" + "-";
                                                arrangefivestr = arrangefivestr + wayonglist.get(k) + "," + ziyouyonglist.get(a) + "," + "_" + "," + yangyonglist.get(j) + "-";
                                                arrangefivestr = arrangefivestr + wayonglist.get(k) + "," + "_" + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "-";
                                                arrangefivestr = arrangefivestr + "_" + "," + wayonglist.get(k) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "-";

                                            } else if (ziyouyonglist.get(a) == yangyonglist.get(j) && yangyonglist.get(j) == wayonglist.get(k)) {

                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "," + wayonglist.get(k) + "," + "_" + "-";
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "," + "_" + "," + wayonglist.get(k) + "-";
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + yangyonglist.get(j) + "," + wayonglist.get(k) + "-";
                                                arrangefivestr = arrangefivestr + "_" + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(j) + "," + wayonglist.get(k) + "-";
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                    }
                } else if (erlei == 2) {//直选
                    switch (code) {
                        case 0://直选单式
                            smalltype = "直选单式";
                            playname = "直选单式";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//自由泳
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//仰泳
                                List<Integer> wayonglist = (List<Integer>) list5.get(i).get("wayong");//蛙泳
                                List<Integer> dieyonglist = (List<Integer>) list5.get(i).get("dieyong");//蝶泳

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = 0; b < yangyonglist.size(); b++) {
                                        for (int c = 0; c < wayonglist.size(); c++) {
                                            for (int d = 0; d < dieyonglist.size(); d++) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + wayonglist.get(c) + "," + dieyonglist.get(d) + "-";
                                            }
                                        }
                                    }
                                }

                            }
                            break;
                        case 1://直选复式
                            smalltype = "直选复式";
                            playname = "直选复式";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//自由泳
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//仰泳
                                List<Integer> wayonglist = (List<Integer>) list5.get(i).get("wayong");//蛙泳
                                List<Integer> dieyonglist = (List<Integer>) list5.get(i).get("dieyong");//蝶泳

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = 0; b < yangyonglist.size(); b++) {
                                        for (int c = 0; c < wayonglist.size(); c++) {
                                            for (int d = 0; d < dieyonglist.size(); d++) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + wayonglist.get(c) + "," + dieyonglist.get(d) + "-";
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 2://直选组合
                            smalltype = "直选组合";
                            playname = "直选组合";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//自由泳
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//仰泳
                                List<Integer> wayonglist = (List<Integer>) list5.get(i).get("wayong");//蛙泳
                                List<Integer> dieyonglist = (List<Integer>) list5.get(i).get("dieyong");//蝶泳
                                //任选一
                                if (ziyouyonglist != null) {
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + "_" + "," + "_" + "-";
                                    }
                                }
                                if (yangyonglist != null) {
                                    for (int a = 0; a < yangyonglist.size(); a++) {
                                        arrangefivestr = arrangefivestr + "_" + "," + yangyonglist.get(a) + "," + "_" + "," + "_" + "-";
                                    }
                                }
                                if (wayonglist != null) {
                                    for (int a = 0; a < wayonglist.size(); a++) {
                                        arrangefivestr = arrangefivestr + "_" + "," + "_" + "," + wayonglist.get(a) + "," + "_" + "-";
                                    }
                                }
                                if (dieyonglist != null) {
                                    for (int a = 0; a < dieyonglist.size(); a++) {
                                        arrangefivestr = arrangefivestr + "_" + "," + "_" + "," + "_" + "," + dieyonglist.get(a) + "-";
                                    }
                                }
                                //任选二
                                if (ziyouyonglist != null && yangyonglist != null) {
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < yangyonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + "_" + "," + "_" + "-";
                                        }
                                    }
                                }
                                if (ziyouyonglist != null && wayonglist != null) {
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < wayonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + wayonglist.get(b) + "," + "_" + "-";
                                        }
                                    }
                                }
                                if (ziyouyonglist != null && dieyonglist != null) {
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < dieyonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + "_" + "," + dieyonglist.get(a) + "-";
                                        }
                                    }
                                }
                                if (yangyonglist != null && wayonglist != null) {
                                    for (int a = 0; a < yangyonglist.size(); a++) {
                                        for (int b = 0; b < wayonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + "_" + "," + yangyonglist.get(a) + "," + wayonglist.get(b) + "," + "_" + "-";
                                        }
                                    }
                                }
                                if (yangyonglist != null && dieyonglist != null) {
                                    for (int a = 0; a < yangyonglist.size(); a++) {
                                        for (int b = 0; b < dieyonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + "_" + "," + yangyonglist.get(a) + "," + "_" + "," + dieyonglist.get(b) + "-";
                                        }
                                    }
                                }
                                if (wayonglist != null && dieyonglist != null) {
                                    for (int a = 0; a < wayonglist.size(); a++) {
                                        for (int b = 0; b < dieyonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + "_" + "," + "_" + "," + wayonglist.get(a) + "," + dieyonglist.get(b) + "-";
                                        }
                                    }
                                }
                                //任选三
                                if (ziyouyonglist != null && yangyonglist != null && wayonglist != null) {
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < yangyonglist.size(); b++) {
                                            for (int c = 0; c < wayonglist.size(); c++) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + wayonglist.get(c) + "," + "_" + "-";
                                            }
                                        }
                                    }
                                }
                                if (ziyouyonglist != null && yangyonglist != null && dieyonglist != null) {
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < yangyonglist.size(); b++) {
                                            for (int c = 0; c < dieyonglist.size(); c++) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + "_" + "," + dieyonglist.get(c) + "-";
                                            }
                                        }
                                    }
                                }
                                if (ziyouyonglist != null && wayonglist != null && dieyonglist != null) {
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < wayonglist.size(); b++) {
                                            for (int c = 0; c < dieyonglist.size(); c++) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + wayonglist.get(b) + "," + dieyonglist.get(c) + "-";
                                            }
                                        }
                                    }
                                }
                                if (yangyonglist != null && wayonglist != null && dieyonglist != null) {
                                    for (int a = 0; a < yangyonglist.size(); a++) {
                                        for (int b = 0; b < wayonglist.size(); b++) {
                                            for (int c = 0; c < dieyonglist.size(); c++) {
                                                arrangefivestr = arrangefivestr + "_" + "," + yangyonglist.get(a) + "," + wayonglist.get(b) + "," + dieyonglist.get(c) + "-";
                                            }
                                        }
                                    }
                                }
                                //直选单式
                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = 0; b < yangyonglist.size(); b++) {
                                        for (int c = 0; c < wayonglist.size(); c++) {
                                            for (int d = 0; d < dieyonglist.size(); d++) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + wayonglist.get(c) + "," + dieyonglist.get(d) + "-";
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                    }
                } else if (erlei == 3) {//组选
                    switch (code) {
                        case 0://组选24单式
                            smalltype = "组选单式";
                            playname = "组选24单式";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//自由泳
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//仰泳
                                List<Integer> wayonglist = (List<Integer>) list5.get(i).get("wayong");//蛙泳
                                List<Integer> dieyonglist = (List<Integer>) list5.get(i).get("dieyong");//蝶泳

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = 0; b < yangyonglist.size(); b++) {
                                        for (int c = 0; c < wayonglist.size(); c++) {
                                            for (int d = 0; d < dieyonglist.size(); d++) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + wayonglist.get(c) + "," + dieyonglist.get(d) + "-";
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 1://组选24复式
                            smalltype = "组选24复式";
                            playname = "组选24复式";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//自由泳

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = a + 1; b < ziyouyonglist.size(); b++) {
                                        for (int c = b + 1; c < ziyouyonglist.size(); c++) {
                                            for (int d = c + 1; d < ziyouyonglist.size(); d++) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(c) + "," + ziyouyonglist.get(d) + "-";
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 2://组选24胆拖
                            smalltype = "组选24胆拖";
                            playname = "组选24胆拖";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//胆码
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//拖码

                                //胆码
                                String s = "";
                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    s = s + ziyouyonglist.get(a) + ",";
                                }

                                if (ziyouyonglist.size() == 1) {//一个胆码
                                    for (int b = 0; b < yangyonglist.size(); b++) {
                                        for (int c = b + 1; c < yangyonglist.size(); c++) {
                                            for (int d = c + 1; d < yangyonglist.size(); d++) {
                                                arrangefivestr = arrangefivestr + s + yangyonglist.get(b) + "," + yangyonglist.get(c) + "," + yangyonglist.get(d) + "-";
                                            }
                                        }
                                    }
                                }
                                if (ziyouyonglist.size() == 2) {//2个胆码
                                    for (int b = 0; b < yangyonglist.size(); b++) {
                                        for (int c = 0; c < yangyonglist.size(); c++) {
                                            arrangefivestr = arrangefivestr + s + yangyonglist.get(b) + "," + yangyonglist.get(c) + "-";
                                        }
                                    }
                                }
                                if (ziyouyonglist.size() == 3) {//3个胆码
                                    for (int b = 0; b < yangyonglist.size(); b++) {
                                        arrangefivestr = arrangefivestr + s + yangyonglist.get(b) + "-";
                                    }
                                }
                            }
                            break;
                        case 3://组选12单式
                            smalltype = "组选单式";
                            playname = "组选12单式";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//自由泳
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//仰泳
                                List<Integer> wayonglist = (List<Integer>) list5.get(i).get("wayong");//蛙泳
                                List<Integer> dieyonglist = (List<Integer>) list5.get(i).get("dieyong");//蝶泳

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = 0; b < yangyonglist.size(); b++) {
                                        for (int c = 0; c < wayonglist.size(); c++) {
                                            for (int d = 0; d < dieyonglist.size(); d++) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + wayonglist.get(c) + "," + dieyonglist.get(d) + "-";
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 4://组选12复式
                            smalltype = "组选12复式";
                            playname = "组选12复式";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//自由泳

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = a + 1; b < ziyouyonglist.size(); b++) {
                                        for (int c = b + 1; c < ziyouyonglist.size(); c++) {
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(c) + "," + ziyouyonglist.get(c)
                                                    + "-" + ziyouyonglist.get(a) + "," + ziyouyonglist.get(c) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(b)
                                                    + "-" + ziyouyonglist.get(b) + "," + ziyouyonglist.get(c) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "-";
                                        }
                                    }
                                }
                            }
                            break;
                        case 5://组选12胆拖
                            smalltype = "组选12胆拖";
                            playname = "组选12胆拖";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//胆码
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//拖码

                                //胆码
                                String s = "";
                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    s = s + ziyouyonglist.get(a) + ",";
                                }

                                if (ziyouyonglist.size() == 1) {//一个胆码
                                    for (int b = 0; b < yangyonglist.size(); b++) {
                                        for (int c = b + 1; c < yangyonglist.size(); c++) {
                                            arrangefivestr = arrangefivestr + s + s + yangyonglist.get(b) + "," + yangyonglist.get(c)
                                                    + "-" + s + yangyonglist.get(b) + "," + yangyonglist.get(b) + "," + yangyonglist.get(c)
                                                    + "-" + s + yangyonglist.get(b) + "," + yangyonglist.get(c) + "," + yangyonglist.get(c) + "-";
                                        }
                                    }
                                }
                                if (ziyouyonglist.size() == 2) {//二个胆码
                                    for (int b = 0; b < ziyouyonglist.size(); b++) {
                                        for (int c = 0; c < yangyonglist.size(); c++) {
                                            arrangefivestr = arrangefivestr + s + ziyouyonglist.get(b) + "," + yangyonglist.get(c) + "-";
                                        }
                                    }
                                    for (int c = 0; c < yangyonglist.size(); c++) {
                                        arrangefivestr = arrangefivestr + s + ziyouyonglist.get(c) + "," + yangyonglist.get(c) + "-";
                                    }
                                }
                            }
                            break;
                        case 6://组选12重胆拖
                            smalltype = "组选12重胆拖";
                            playname = "组选12重胆拖";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//胆码
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//拖码

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = 0; b < yangyonglist.size(); b++) {
                                        for (int c = b + 1; c < yangyonglist.size(); c++) {
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + yangyonglist.get(c) + "-";
                                        }
                                    }
                                }
                            }
                            break;
                        case 7://组选6单式
                            smalltype = "组选单式";
                            playname = "组选6单式";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//自由泳
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//仰泳
                                List<Integer> wayonglist = (List<Integer>) list5.get(i).get("wayong");//蛙泳
                                List<Integer> dieyonglist = (List<Integer>) list5.get(i).get("dieyong");//蝶泳
                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = 0; b < yangyonglist.size(); b++) {
                                        for (int c = 0; c < wayonglist.size(); c++) {
                                            for (int d = 0; d < dieyonglist.size(); d++) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + wayonglist.get(c) + "," + dieyonglist.get(d) + "-";
                                            }
                                        }
                                    }

                                }
                            }
                            break;
                        case 8://组选6复式
                            smalltype = "组选6复式";
                            playname = "组选6复式";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = a + 1; b < ziyouyonglist.size(); b++) {
                                        arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "-";
                                    }
                                }
                            }
                            break;
                        case 9://组选6胆拖
                            smalltype = "组选6胆拖";
                            playname = "组选6胆拖";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//胆码
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//拖码

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = 0; b < yangyonglist.size(); b++) {
                                        arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + yangyonglist.get(b) + "-";
                                    }
                                }
                            }
                            break;
                        case 10://组选4单式
                            smalltype = "组选单式";
                            playname = "组选4单式";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//自由泳
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//仰泳
                                List<Integer> wayonglist = (List<Integer>) list5.get(i).get("wayong");//蛙泳
                                List<Integer> dieyonglist = (List<Integer>) list5.get(i).get("dieyong");//蝶泳
                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = 0; b < yangyonglist.size(); b++) {
                                        for (int c = 0; c < wayonglist.size(); c++) {
                                            for (int d = 0; d < dieyonglist.size(); d++) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + wayonglist.get(c) + "," + dieyonglist.get(d) + "-";
                                            }
                                        }
                                    }

                                }
                            }
                            break;
                        case 11://组选4复式
                            smalltype = "组选4复式";
                            playname = "组选4复式";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//自由泳

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = 0; b < ziyouyonglist.size(); b++) {
                                        if (a != b) {
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "-";
                                        }
                                    }
                                }
                            }
                            break;
                        case 12://组选4胆拖
                            smalltype = "组选4胆拖";
                            playname = "组选4胆拖";

                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//胆码
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//拖码

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = 0; b < yangyonglist.size(); b++) {
                                        arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(b)
                                                + "-" + yangyonglist.get(b) + "," + yangyonglist.get(b) + "," + yangyonglist.get(b) + "," + ziyouyonglist.get(a) + "-";
                                    }
                                }
                            }
                            break;
                        case 13://组选4重胆拖
                            smalltype = "组选4重胆拖";
                            playname = "组选4重胆拖";

                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");//胆码
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");//拖码

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = 0; b < yangyonglist.size(); b++) {
                                        arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "-";
                                    }
                                }
                            }
                            break;
                    }
                } else if (erlei == 4) {//前三组选
                    switch (code) {
                        case 0://前3组3
                            smalltype = "前三组";
                            playname = "前三组3";

                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = a + 1; b < ziyouyonglist.size(); b++) {
                                        arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + "_" + "-"
                                                + ziyouyonglist.get(b) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + "_" + "-"
                                                + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + "_" + "-"
                                                + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + "_" + "-"
                                                + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + "_" + "-"
                                                + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(b) + "," + "_" + "-";
                                    }
                                }
                            }
                            break;
                        case 1://前三组6
                            smalltype = "前三组6";
                            playname = "前三组6";

                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = 0; b < ziyouyonglist.size(); b++) {
                                        for (int c = 0; c < ziyouyonglist.size(); c++) {
                                            if (a != b && a != c && b != c) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(c) + "," + "_" + "-";
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 2://任三组三
                            playname = "任三组3";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");
                                List<String> ziyouyonglist1 = (List<String>) list5.get(i).get("type");
                                if (ziyouyonglist1.get(i).equals("自,仰,蛙,")) {
                                    smalltype = "蝶泳";
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = a + 1; b < ziyouyonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + "_" + "-"
                                                    + ziyouyonglist.get(b) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + "_" + "-"
                                                    + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + "_" + "-"
                                                    + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + "_" + "-"
                                                    + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + "_" + "-"
                                                    + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(b) + "," + "_" + "-";
                                        }
                                    }
                                } else if (ziyouyonglist1.get(i).equals("自,仰,碟")) {
                                    smalltype = "蛙泳";
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = a + 1; b < ziyouyonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + "_" + "," + ziyouyonglist.get(b) + "-"
                                                    + ziyouyonglist.get(b) + "," + ziyouyonglist.get(b) + "," + "_" + "," + ziyouyonglist.get(a) + "-"
                                                    + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + "_" + "," + ziyouyonglist.get(a) + "-"
                                                    + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + "_" + "," + ziyouyonglist.get(a) + "-"
                                                    + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + "_" + "," + ziyouyonglist.get(b) + "-"
                                                    + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + "_" + "," + ziyouyonglist.get(b) + "-";
                                        }
                                    }
                                } else if (ziyouyonglist1.get(i).equals("自,蛙,碟")) {
                                    smalltype = "仰泳";
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = a + 1; b < ziyouyonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "-"
                                                    + ziyouyonglist.get(b) + "," + "_" + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "-"
                                                    + ziyouyonglist.get(a) + "," + "_" + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "-"
                                                    + ziyouyonglist.get(b) + "," + "_" + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "-"
                                                    + ziyouyonglist.get(b) + "," + "_" + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "-"
                                                    + ziyouyonglist.get(a) + "," + "_" + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(b) + "-";
                                        }
                                    }
                                } else if (ziyouyonglist1.get(i).equals("仰,蛙,碟")) {
                                    smalltype = "自由泳";
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = a + 1; b < ziyouyonglist.size(); b++) {
                                            arrangefivestr = arrangefivestr + "_" + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "-"
                                                    + "_" + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "-"
                                                    + "_" + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "-"
                                                    + "_" + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "-"
                                                    + "_" + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "-"
                                                    + "_" + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(b) + "-";
                                        }
                                    }
                                }
                            }
                            break;
                        case 3://任三组6
                            playname = "任三组6";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");
                                List<String> ziyouyonglist1 = (List<String>) list5.get(i).get("type");
                                if (ziyouyonglist1.get(i).equals("自,仰,蛙,")) {
                                    smalltype = "蝶泳6";
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < ziyouyonglist.size(); b++) {
                                            for (int c = 0; c < ziyouyonglist.size(); c++) {
                                                if (a != b && a != c && b != c) {
                                                    arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(c) + "," + "_" + "-";
                                                }
                                            }
                                        }
                                    }
                                } else if (ziyouyonglist1.get(i).equals("自,仰,碟")) {
                                    smalltype = "蛙泳6";
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < ziyouyonglist.size(); b++) {
                                            for (int c = 0; c < ziyouyonglist.size(); c++) {
                                                if (a != b && a != c && b != c) {
                                                    arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + "_" + "," + ziyouyonglist.get(c) + "-";
                                                }
                                            }
                                        }
                                    }
                                } else if (ziyouyonglist1.get(i).equals("自,蛙,碟")) {
                                    smalltype = "仰泳6";
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < ziyouyonglist.size(); b++) {
                                            for (int c = 0; c < ziyouyonglist.size(); c++) {
                                                if (a != b && a != c && b != c) {
                                                    arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(c) + "-";
                                                }
                                            }
                                        }
                                    }
                                } else if (ziyouyonglist1.get(i).equals("仰,蛙,碟")) {
                                    smalltype = "自由泳6";
                                    for (int a = 0; a < ziyouyonglist.size(); a++) {
                                        for (int b = 0; b < ziyouyonglist.size(); b++) {
                                            for (int c = 0; c < ziyouyonglist.size(); c++) {
                                                if (a != b && a != c && b != c) {
                                                    arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + "_" + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(c) + "-";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                    }
                } else if (erlei == 5) {//新玩法
                    switch (code) {
                        case 0://组选三码全包
                            smalltype = "三码全包";
                            playname = "组选三码全包";
                            int[] num = new int[4];
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    for (int b = a + 1; b < ziyouyonglist.size(); b++) {
                                        for (int c = b + 1; c < ziyouyonglist.size(); c++) {
                                            for (int d = 1; d < 9; d++) {
                                                num[0] = ziyouyonglist.get(a);
                                                num[1] = ziyouyonglist.get(b);
                                                num[2] = ziyouyonglist.get(c);
                                                num[3] = d;

                                                num = bubbleSort(num);
                                                arrangefivestr = arrangefivestr + num[0] + "," + num[1] + "," + num[2] + "," + num[3] + "-";
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 1://组选二码全包
                            smalltype = "两码全包";
                            playname = "组选二码全包";
                            int[] num1 = new int[4];
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");
                                Log.i("arrangefive", "list:" + ziyouyonglist);
                                for (int a = 0; a < ziyouyonglist.size(); a++) {
//                                    for (int b = a + 1; b < ziyouyonglist.size(); b++) {
//                                        for (int c = 1; c < 9; c++) {
//                                            for (int d = c; d < 9; d++) {
//                                                num1[0] = ziyouyonglist.get(a);
//                                                num1[1] = ziyouyonglist.get(b);
//                                                num1[2] = c;
//                                                num1[3] = d;

//                                                num1 = bubbleSort(num1);
                                    if (a == ziyouyonglist.size()) {
                                        arrangefivestr = arrangefivestr + ziyouyonglist.get(a);
                                    } else {
                                        arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + ",";
                                    }
//                                            }
//                                        }
                                }
                                arrangefivestr = arrangefivestr + "-";
//                                }
                            }
                            break;
                        case 2://选四全包
                            playname = "选四全包";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");
                                List<Integer> yangyonglist = (List<Integer>) list5.get(i).get("yangyong");
                                switch (sanlei) {
                                    case 0://选四不重全包
                                        smalltype = "选四全包";
                                        for (int a = 0; a < ziyouyonglist.size(); a++) {
//                                            for (int b = 0; b < ziyouyonglist.size(); b++) {
//                                                for (int c = 0; c < ziyouyonglist.size(); c++) {
//                                                    for (int d = 0; d < ziyouyonglist.size(); d++) {
//                                                        if (a != b && a != c && a != d && b != c && b != d && c != d) {
                                            if (a == ziyouyonglist.size() - 1) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a);
                                            } else {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + ",";
                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
                                        }
                                        arrangefivestr = arrangefivestr + "-";
                                        break;
                                    case 1://选四一对全包
                                        smalltype = "选四一对全包";
                                        for (int a = 0; a < ziyouyonglist.size(); a++) {
                                            for (int b = 0; b < yangyonglist.size(); b++) {
//                                                for (int c = b + 1; c < yangyonglist.size(); c++) {
//                                                    arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + yangyonglist.get(c) + "-"
//                                                            + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(c) + "," + yangyonglist.get(b) + "-"
//                                                            + ziyouyonglist.get(a) + "," + yangyonglist.get(c) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "-"
//                                                            + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(c) + "-"
//                                                            + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + yangyonglist.get(c) + "," + ziyouyonglist.get(a) + "-"
//                                                            + ziyouyonglist.get(a) + "," + yangyonglist.get(c) + "," + yangyonglist.get(b) + "," + ziyouyonglist.get(a) + "-"
//                                                            + yangyonglist.get(c) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "-"
//                                                            + yangyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(c) + "-"
//                                                            + yangyonglist.get(b) + "," + yangyonglist.get(c) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "-"
//                                                            + yangyonglist.get(c) + "," + yangyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "-"
//                                                            + yangyonglist.get(c) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + ziyouyonglist.get(a) + "-"
//                                                            + yangyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(c) + "," + ziyouyonglist.get(a) + "-";
//
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + yangyonglist.get(b + 1);
                                                b = b + 1;
//                                                }
                                            }
//                                            arrangefivestr = arrangefivestr.substring(0, arrangefivestr.length() - 1);
                                            arrangefivestr = arrangefivestr + "-";
                                        }
                                        break;
                                    case 2://选四两对全包
                                        smalltype = "选四两对全包";
                                        for (int a = 0; a < ziyouyonglist.size(); a++) {
                                            arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a + 1) + "," + ziyouyonglist.get(a + 1);
                                            a = a + 1;
//                                            for (int b = a + 1; b < ziyouyonglist.size(); b++) {
//                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(b) + "-"
//                                                        + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "-"
//                                                        + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "-"
//                                                        + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "-"
//                                                        + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "-"
//                                                        + ziyouyonglist.get(b) + "," + ziyouyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "-";
//                                            }
                                        }
                                        arrangefivestr = arrangefivestr + "-";
                                        break;
                                    case 3://选四三条全包
                                        smalltype = "选四三条全包";
                                        for (int a = 0; a < ziyouyonglist.size(); a++) {
                                            for (int b = 0; b < yangyonglist.size(); b++) {
                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "-";
//                                                arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "-"
//                                                        + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + ziyouyonglist.get(a) + "-"
//                                                        + ziyouyonglist.get(a) + "," + yangyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "-"
//                                                        + yangyonglist.get(b) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "-";
                                            }
                                        }
                                        break;
                                }
                            }
                            break;
                        case 3://重号全包
                            smalltype = "重号全包";
                            playname = "重号全包";
                            for (int i = 0; i < list5.size(); i++) {
                                List<Integer> ziyouyonglist = (List<Integer>) list5.get(i).get("ziyouyong");
                                Log.i("arrangefive", "list:" + ziyouyonglist);

                                for (int a = 0; a < ziyouyonglist.size(); a++) {
                                    arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + ",";
//                                    arrangefivestr = arrangefivestr + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + "_" + "," + "_" + "-"
//                                            + ziyouyonglist.get(a) + "," + "_" + "," + ziyouyonglist.get(a) + "," + "_" + "-"
//                                            + ziyouyonglist.get(a) + "," + "_" + "," + "_" + "," + ziyouyonglist.get(a) + "-"
//                                            + "_" + "," + ziyouyonglist.get(a) + "," + "_" + "," + ziyouyonglist.get(a) + "-"
//                                            + "_" + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + "_" + "-"
//                                            + "_" + "," + "_" + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "-"
//                                            + "_" + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "-"
//                                            + ziyouyonglist.get(a) + "," + "_" + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "-"
//                                            + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + "_" + "," + ziyouyonglist.get(a) + "-"
//                                            + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + "_" + "-"
//                                            + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "," + ziyouyonglist.get(a) + "-";
                                }
                            }
                            break;
                    }
                }

                break;
            case 5://排列三
                lotterytype = "pl3";
                List<Map<String, Object>> list1 = getSharedPreferences2();
                switch (code) {
                    case 0://直选三
                        smalltype = "单式";
                        playname = "直选三";

                        for (int i = 0; i < list1.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list1.get(i).get("hun");//百位
                            List<Integer> tenlist1 = (List<Integer>) list1.get(i).get("ten");//十位
                            List<Integer> indlist1 = (List<Integer>) list1.get(i).get("ind");//个位
                            for (int c = 0; c < hunlist1.size(); c++) {
                                for (int d = 0; d < tenlist1.size(); d++) {
                                    for (int e = 0; e < indlist1.size(); e++) {
                                        arrangefivestr = arrangefivestr + hunlist1.get(c) + ","
                                                + tenlist1.get(d) + "," + indlist1.get(e) + "-";
                                    }
                                }
                            }
                        }
                        break;
                    case 1://组三单式
                        smalltype = "组选单式";
                        playname = "组三单式";

                        for (int i = 0; i < list1.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list1.get(i).get("hun");//百位
                            List<Integer> tenlist1 = (List<Integer>) list1.get(i).get("ten");//十位
                            for (int c = 0; c < hunlist1.size(); c++) {
                                for (int d = 0; d < tenlist1.size(); d++) {
                                    arrangefivestr = arrangefivestr + hunlist1.get(c) + ","
                                            + hunlist1.get(c) + "," + tenlist1.get(d) + "," + "-";
                                }
                            }
                        }
                        break;
                    case 2://组三复式
                        smalltype = "组选3复式";
                        playname = "组三复式";

                        for (int i = 0; i < list1.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list1.get(i).get("hun");//百位
                            for (int c = 0; c < hunlist1.size(); c++) {
                                for (int d = 0; d < hunlist1.size(); d++) {
                                    if (c != d) {
                                        arrangefivestr = arrangefivestr + hunlist1.get(c) + ","
                                                + hunlist1.get(c) + "," + hunlist1.get(d) + "-";
                                    }
                                }
                            }
                        }
                        break;
                    case 3://组选六
                        smalltype = "组选6复式";
                        playname = "组选六";

                        for (int i = 0; i < list1.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list1.get(i).get("hun");//百位
                            for (int c = 0; c < hunlist1.size(); c++) {
                                for (int d = c + 1; d < hunlist1.size(); d++) {
                                    for (int e = d + 1; e < hunlist1.size(); e++) {
                                        arrangefivestr = arrangefivestr + hunlist1.get(c) + ","
                                                + hunlist1.get(d) + "," + hunlist1.get(e) + "-";
                                    }
                                }
                            }
                        }
                        break;
                    case 4://直选三和值
                        smalltype = "直选和值";
                        playname = "直选三和值";

                        for (int i = 0; i < list1.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list1.get(i).get("hun");//百位
                            for (int c = 0; c < hunlist1.size(); c++) {
                                for (int d = 0; d < 10; d++) {
                                    for (int e = 0; e < 10; e++) {
                                        for (int f = 0; f < 10; f++) {
                                            if ((d + e + f) == hunlist1.get(c)) {
                                                arrangefivestr = arrangefivestr + d + ","
                                                        + e + "," + f + "-";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                }

                break;
            case 7://3D
                lotterytype = "fc3d";
                List<Map<String, Object>> list2 = getSharedPreferences4();
                switch (code) {
                    case 0://直选
                        smalltype = "单式";
                        playname = "直选";

                        for (int i = 0; i < list2.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list2.get(i).get("hun");//百位
                            List<Integer> tenlist1 = (List<Integer>) list2.get(i).get("ten");//十位
                            List<Integer> indlist1 = (List<Integer>) list2.get(i).get("ind");//个位
                            for (int c = 0; c < hunlist1.size(); c++) {
                                for (int d = 0; d < tenlist1.size(); d++) {
                                    for (int e = 0; e < indlist1.size(); e++) {
                                        arrangefivestr = arrangefivestr + hunlist1.get(c) + ","
                                                + tenlist1.get(d) + "," + indlist1.get(e) + "-";
                                    }
                                }
                            }
                        }
                        break;
                    case 1://组三
                        smalltype = "组选3复式";
                        playname = "组三";
                        for (int i = 0; i < list2.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list2.get(i).get("hun");//百位
                            for (int c = 0; c < hunlist1.size(); c++) {
                                for (int d = 0; d < hunlist1.size(); d++) {
                                    if (c != d) {
                                        arrangefivestr = arrangefivestr + hunlist1.get(c) + ","
                                                + hunlist1.get(c) + "," + hunlist1.get(d) + "-";
                                    }
                                }
                            }
                        }
                        break;
                    case 2://组六
                        smalltype = "组选6复式";
                        playname = "组六";
                        for (int i = 0; i < list2.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list2.get(i).get("hun");//百位
                            for (int c = 0; c < hunlist1.size(); c++) {
                                for (int d = c + 1; d < hunlist1.size(); d++) {
                                    for (int e = d + 1; e < hunlist1.size(); e++) {
                                        arrangefivestr = arrangefivestr + hunlist1.get(c) + ","
                                                + hunlist1.get(d) + "," + hunlist1.get(e) + "-";
                                    }
                                }
                            }
                        }
                        break;
                }

                break;
            case 9://快三
                lotterytype = "hebk3";
                List<Map<String, Object>> list4 = getSharedPreferences5();
                switch (code) {
                    case 0://和值
                        smalltype = "和值";
                        playname = "和值";

                        for (int i = 0; i < list4.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list4.get(i).get("hun");//百位
                            for (int c = 0; c < hunlist1.size(); c++) {
                                for (int d = 0; d < 6; d++) {
                                    for (int e = 0; e < 6; e++) {
                                        for (int f = 0; f < 6; f++) {
                                            if ((d + e + f) == hunlist1.get(c)) {
                                                arrangefivestr = arrangefivestr + d + ","
                                                        + e + "," + f + "-";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 1://三同号
                        smalltype = "三同号单选";
                        playname = "三同号";

                        for (int i = 0; i < list4.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list4.get(i).get("hun");//百位
                            for (int c = 0; c < hunlist1.size(); c++) {
                                arrangefivestr = arrangefivestr + hunlist1.get(c) + ","
                                        + hunlist1.get(c) + "," + hunlist1.get(c) + "-";
                            }
                        }
                        break;
                    case 2://二同号单选
                        smalltype = "和值";
                        playname = "和值";

                        for (int i = 0; i < list4.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list4.get(i).get("hun");//百位
                            List<Integer> tenlist1 = (List<Integer>) list4.get(i).get("ten");//十位
                            for (int c = 0; c < hunlist1.size(); c++) {
                                for (int d = 0; d < tenlist1.size(); d++) {
                                    arrangefivestr = arrangefivestr + hunlist1.get(c) + ","
                                            + hunlist1.get(c) + "," + tenlist1.get(d) + "-";
                                }
                            }
                        }
                        break;
                    case 3://二同号复选
                        smalltype = "和值";
                        playname = "和值";

                        for (int i = 0; i < list4.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list4.get(i).get("hun");//百位
                            for (int c = 0; c < hunlist1.size(); c++) {
                                arrangefivestr = arrangefivestr + hunlist1.get(c) + ","
                                        + hunlist1.get(c) + "-";
                            }
                        }
                        break;
                    case 4://三不同号
                        smalltype = "和值";
                        playname = "和值";

                        for (int i = 0; i < list4.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list4.get(i).get("hun");//百位
                            for (int c = 0; c < hunlist1.size(); c++) {
                                for (int d = c + 1; d < hunlist1.size(); d++) {
                                    for (int e = d + 1; e < hunlist1.size(); e++) {
                                        arrangefivestr = arrangefivestr + hunlist1.get(c) + ","
                                                + hunlist1.get(d) + "," + hunlist1.get(e) + "-";
                                    }
                                }
                            }
                        }
                        break;
                    case 5://二不同号
                        smalltype = "和值";
                        playname = "和值";

                        for (int i = 0; i < list4.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list4.get(i).get("hun");//百位
                            for (int c = 0; c < hunlist1.size(); c++) {
                                for (int d = c + 1; d < hunlist1.size(); d++) {
                                    arrangefivestr = arrangefivestr + hunlist1.get(c) + ","
                                            + hunlist1.get(d) + "-";
                                }
                            }
                        }
                        break;
                    case 6://三不同号胆码
                        smalltype = "和值";
                        playname = "和值";

                        for (int i = 0; i < list4.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list4.get(i).get("hun");//百位
                            List<Integer> tenlist1 = (List<Integer>) list4.get(i).get("ten");//百位

                            if (hunlist1.size() == 1) {//1个胆码
                                for (int c = 0; c < hunlist1.size(); c++) {
                                    for (int d = 0; d < tenlist1.size(); d++) {
                                        for (int e = d + 1; e < tenlist1.size(); e++) {
                                            arrangefivestr = arrangefivestr + hunlist1.get(c) + ","
                                                    + tenlist1.get(d) + "," + tenlist1.get(e) + "-";
                                        }
                                    }
                                }
                            } else if (hunlist1.size() == 2) {//2个胆码
                                for (int c = 0; c < hunlist1.size(); c++) {
                                    for (int e = c + 1; e < hunlist1.size(); e++) {
                                        for (int d = 0; d < tenlist1.size(); d++) {
                                            arrangefivestr = arrangefivestr + hunlist1.get(c) + ","
                                                    + hunlist1.get(e) + "," + tenlist1.get(d) + "-";
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 7://二不同号胆码
                        smalltype = "和值";
                        playname = "和值";

                        for (int i = 0; i < list4.size(); i++) {
                            List<Integer> hunlist1 = (List<Integer>) list4.get(i).get("hun");//百位
                            List<Integer> tenlist1 = (List<Integer>) list4.get(i).get("ten");//百位

                            if (hunlist1.size() == 1) {//1个胆码
                                for (int c = 0; c < hunlist1.size(); c++) {
                                    for (int d = 0; d < tenlist1.size(); d++) {
                                        for (int e = d + 1; e < tenlist1.size(); e++) {
                                            arrangefivestr = arrangefivestr + hunlist1.get(c) + ","
                                                    + tenlist1.get(d) + "," + tenlist1.get(e) + "-";
                                        }
                                    }
                                }
                            }
                        }
                        break;
                }
                break;
            case 10://排列五
                lotterytype = "pl5";
                List<Map<String, Object>> list3 = getSharedPreferences3();
                for (int i = 0; i < list3.size(); i++) {
                    List<Integer> wanlist1 = (List<Integer>) list3.get(i).get("wan");//万位
                    List<Integer> qianlist1 = (List<Integer>) list3.get(i).get("qian");//千位
                    List<Integer> hunlist1 = (List<Integer>) list3.get(i).get("hun");//百位
                    List<Integer> tenlist1 = (List<Integer>) list3.get(i).get("ten");//十位
                    List<Integer> indlist1 = (List<Integer>) list3.get(i).get("ind");//个位
                    for (int a = 0; a < wanlist1.size(); a++) {
                        for (int b = 0; b < qianlist1.size(); b++) {
                            for (int c = 0; c < hunlist1.size(); c++) {
                                for (int d = 0; d < tenlist1.size(); d++) {
                                    for (int e = 0; e < indlist1.size(); e++) {
                                        arrangefivestr = arrangefivestr + wanlist1.get(a) + "," + qianlist1.get(b) + ","
                                                + hunlist1.get(c) + "," + tenlist1.get(d) + "," + indlist1.get(e) + "-";
                                    }
                                }
                            }
                        }
                    }
                }
                break;
        }

        final String token = SharedPreferencesUtil.getSharedPreferences(OneLotteryBetPayx.this).get("token").toString();
        Log.i("arrangefive", token);
        final String finalArrangefivestr = arrangefivestr;

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    try {
                        JSONObject jobj = new JSONObject(result);
                        String status = jobj.getString("status");
                        if ("1".equals(status)) {
                            //提交
                            Intent intent = new Intent(OneLotteryBetPayx.this, OneLotteryBetPayFor.class);
                            intent.putExtra("bs", caizhong);
                            intent.putExtra("msg", jobj.getString("msg"));
                            intent.putExtra("multiple", edit_number.getText().toString().trim());
                            intent.putExtra("money", Integer.parseInt(tv_money.getText().toString().trim()));
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        final String finalSmalltype = smalltype;
        final String finalPlayname = playname;
        final String finalLotterytype = lotterytype;
        new Thread() {
            @Override
            public void run() {
                super.run();

                Map<String, String> map = new HashMap<String, String>();
                map.put("token", token);
                map.put("lotterytype", finalLotterytype);
                map.put("smalltype", finalSmalltype);
                map.put("lotterynumber", finalArrangefivestr.substring(0, finalArrangefivestr.length() - 1));
                map.put("playname", finalPlayname);
                map.put("period", kjrq + "");
                map.put("multiple", Integer.parseInt(edit_number.getText().toString().trim()) + "");
                map.put("betmoney", 2 + "");

                Log.i("arrangefive", map + "");
                Log.i("arrangefive2", finalArrangefivestr);

                try {
                    result = URLConnectionUtil.sendPostRequest(name_space, map, "utf-8", 1);
                    Log.i("arrangefive", result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message ms = new Message();
                ms.what = 1;
                handler.sendMessage(ms);
            }
        }.start();
    }

    /**
     * 获取选择的号码
     */
    private List<Map<String, Object>> getSharedPreferences6() {
        List<Map<String, Object>> l = null;
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        ziyouyonglist = new ArrayList<List<Integer>>();
        ziyouyonglist1 = new ArrayList<List<String>>();
        yangyonglist = new ArrayList<List<Integer>>();
        wayonglist = new ArrayList<List<Integer>>();
        dieyonglist = new ArrayList<List<Integer>>();

        int time = sharedPreferences.getInt("time", 0);
        if (erlei == 5) {//新玩法
            if (code == 0 || code == 1 || code == 3) {
                for (int i = 0; i < time; i++) {
                    int ziyouyongsize = sharedPreferences.getInt("ziyouyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < ziyouyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("ziyouyong" + i + j, 0));
                    }
                    if (ziyouyongsize != 0) {
                        ziyouyonglist.add(listnumb);
                    }
                }

                l = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < ziyouyonglist.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ziyouyong", ziyouyonglist.get(i));
                    l.add(map);
                }
            } else if (code == 2) {
                if (sanlei == 0 || sanlei == 2) {//选四不重全包//选四两对全包
                    for (int i = 0; i < time; i++) {
                        int ziyouyongsize = sharedPreferences.getInt("ziyouyongsize" + i, 0);
                        List<Integer> listnumb = new ArrayList<Integer>();
                        for (int j = 0; j < ziyouyongsize; j++) {
                            listnumb.add(sharedPreferences.getInt("ziyouyong" + i + j, 0));
                        }
                        if (ziyouyongsize != 0) {
                            ziyouyonglist.add(listnumb);
                        }
                    }
                    l = new ArrayList<Map<String, Object>>();
                    for (int i = 0; i < ziyouyonglist.size(); i++) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("ziyouyong", ziyouyonglist.get(i));
                        l.add(map);
                    }
                } else if (sanlei == 1 || sanlei == 3) {//选四一对全包//选四三条全包
                    for (int i = 0; i < time; i++) {
                        int ziyouyongsize = sharedPreferences.getInt("ziyouyongsize" + i, 0);
                        List<Integer> listnumb = new ArrayList<Integer>();
                        for (int j = 0; j < ziyouyongsize; j++) {
                            listnumb.add(sharedPreferences.getInt("ziyouyong" + i + j, 0));
                        }
                        if (ziyouyongsize != 0) {
                            ziyouyonglist.add(listnumb);
                        }
                    }
                    for (int i = 0; i < time; i++) {
                        int yangyongsize = sharedPreferences.getInt("yangyongsize" + i, 0);
                        List<Integer> listnumb = new ArrayList<Integer>();
                        for (int j = 0; j < yangyongsize; j++) {
                            listnumb.add(sharedPreferences.getInt("yangyong" + i + j, 0));
                        }
                        if (yangyongsize != 0) {
                            yangyonglist.add(listnumb);
                        }
                    }
                    l = new ArrayList<Map<String, Object>>();
                    for (int i = 0; i < ziyouyonglist.size(); i++) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("ziyouyong", ziyouyonglist.get(i));
                        map.put("yangyong", yangyonglist.get(i));
                        l.add(map);
                    }
                }
            }
        } else if (erlei == 4) {//前三任选
            if (code == 0 || code == 1) {
                for (int i = 0; i < time; i++) {
                    int ziyouyongsize = sharedPreferences.getInt("ziyouyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < ziyouyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("ziyouyong" + i + j, 0));
                    }
                    if (ziyouyongsize != 0) {
                        ziyouyonglist.add(listnumb);
                    }
                }

                l = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < ziyouyonglist.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ziyouyong", ziyouyonglist.get(i));
                    l.add(map);
                }
            } else if (code == 2 || code == 3) {
                for (int i = 0; i < time; i++) {
                    List<String> listnumb1 = new ArrayList<String>();
                    String type = sharedPreferences.getString("type11" + i, "");
                    listnumb1.add(type);

                    int ziyouyongsize = sharedPreferences.getInt("ziyouyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < ziyouyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("ziyouyong" + i + j, 0));
                    }
                    if (ziyouyongsize != 0) {
                        ziyouyonglist.add(listnumb);
                    }
                    if (!type.equals("")) {
                        ziyouyonglist1.add(listnumb1);
                    }
                }
                l = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < ziyouyonglist1.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ziyouyong", ziyouyonglist.get(i));
                    map.put("type", ziyouyonglist1.get(i));
                    l.add(map);
                }
            }

        } else if (erlei == 1) {//任选
            if (code == 0 || code == 1 || code == 3 || code == 10) {
                for (int i = 0; i < time; i++) {
                    int ziyouyongsize = sharedPreferences.getInt("ziyouyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < ziyouyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("ziyouyong" + i + j, 0));
                    }
                    if (ziyouyongsize != 0) {
                        ziyouyonglist.add(listnumb);
                    }
                }

                for (int i = 0; i < time; i++) {
                    int yangyongsize = sharedPreferences.getInt("yangyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < yangyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("yangyong" + i + j, 0));
                    }
                    if (yangyongsize != 0) {
                        yangyonglist.add(listnumb);
                    }
                }

                for (int i = 0; i < time; i++) {
                    int wayongsize = sharedPreferences.getInt("wayongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < wayongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("wayong" + i + j, 0));
                    }
                    if (wayongsize != 0) {
                        wayonglist.add(listnumb);
                    }
                }

                for (int i = 0; i < time; i++) {
                    int dieyongsize = sharedPreferences.getInt("dieyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < dieyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("dieyong" + i + j, 0));
                    }
                    if (dieyongsize != 0) {
                        dieyonglist.add(listnumb);
                    }
                }
                int a = 0;
                l = new ArrayList<Map<String, Object>>();
                if (ziyouyonglist.size() != 0) {
                    a = ziyouyonglist.size();
                } else if (yangyonglist.size() != 0) {
                    a = yangyonglist.size();
                } else if (wayonglist.size() != 0) {
                    a = wayonglist.size();
                } else if (dieyonglist.size() != 0) {
                    a = dieyonglist.size();
                }
                for (int i = 0; i < a; i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    if (ziyouyonglist.size() != 0) {
                        map.put("ziyouyong", ziyouyonglist.get(i));
                    }
                    if (yangyonglist.size() != 0) {
                        map.put("yangyong", yangyonglist.get(i));
                    }
                    if (wayonglist.size() != 0) {
                        map.put("wayong", wayonglist.get(i));
                    }
                    if (dieyonglist.size() != 0) {
                        map.put("dieyong", dieyonglist.get(i));
                    }
                    l.add(map);
                }
            } else if (code == 2) {
                for (int i = 0; i < time; i++) {
                    int ziyouyongsize = sharedPreferences.getInt("ziyouyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < ziyouyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("ziyouyong" + i + j, 0));
                    }
                    if (ziyouyongsize != 0) {
                        ziyouyonglist.add(listnumb);
                    }
                }

                for (int i = 0; i < time; i++) {
                    int yangyongsize = sharedPreferences.getInt("yangyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < yangyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("yangyong" + i + j, 0));
                    }
                    if (yangyongsize != 0) {
                        yangyonglist.add(listnumb);
                    }
                }

                int a = 0;
                l = new ArrayList<Map<String, Object>>();
                if (ziyouyonglist.size() != 0) {
                    a = ziyouyonglist.size();
                } else if (yangyonglist.size() != 0) {
                    a = yangyonglist.size();
                } else if (wayonglist.size() != 0) {
                    a = wayonglist.size();
                } else if (dieyonglist.size() != 0) {
                    a = dieyonglist.size();
                }
                for (int i = 0; i < a; i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ziyouyong", ziyouyonglist.get(i));
                    map.put("yangyong", yangyonglist.get(i));
                    l.add(map);
                }
            } else if (code == 4) {
                for (int i = 0; i < time; i++) {
                    int ziyouyongsize = sharedPreferences.getInt("ziyouyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < ziyouyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("ziyouyong" + i + j, 0));
                    }
                    if (ziyouyongsize != 0) {
                        ziyouyonglist.add(listnumb);
                    }
                }

                for (int i = 0; i < time; i++) {
                    int yangyongsize = sharedPreferences.getInt("yangyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < yangyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("yangyong" + i + j, 0));
                    }
                    if (yangyongsize != 0) {
                        yangyonglist.add(listnumb);
                    }
                }

                for (int i = 0; i < time; i++) {
                    int wayongsize = sharedPreferences.getInt("wayongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < wayongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("wayong" + i + j, 0));
                    }
                    if (wayongsize != 0) {
                        wayonglist.add(listnumb);
                    }
                }

                int a = 0;
                l = new ArrayList<Map<String, Object>>();
                if (ziyouyonglist.size() != 0) {
                    a = ziyouyonglist.size();
                } else if (yangyonglist.size() != 0) {
                    a = yangyonglist.size();
                } else if (wayonglist.size() != 0) {
                    a = wayonglist.size();
                } else if (dieyonglist.size() != 0) {
                    a = dieyonglist.size();
                }
                for (int i = 0; i < a; i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ziyouyong", ziyouyonglist.get(i));
                    map.put("yangyong", yangyonglist.get(i));
                    map.put("wayong", wayonglist.get(i));
                    l.add(map);
                }
            }
        } else if (erlei == 2) {//直选
            if (code == 0 || code == 1 || code == 2) {
                for (int i = 0; i < time; i++) {
                    int ziyouyongsize = sharedPreferences.getInt("ziyouyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < ziyouyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("ziyouyong" + i + j, 0));
                    }
                    if (ziyouyongsize != 0) {
                        ziyouyonglist.add(listnumb);
                    }
                }

                for (int i = 0; i < time; i++) {
                    int yangyongsize = sharedPreferences.getInt("yangyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < yangyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("yangyong" + i + j, 0));
                    }
                    if (yangyongsize != 0) {
                        yangyonglist.add(listnumb);
                    }
                }

                for (int i = 0; i < time; i++) {
                    int wayongsize = sharedPreferences.getInt("wayongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < wayongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("wayong" + i + j, 0));
                    }
                    if (wayongsize != 0) {
                        wayonglist.add(listnumb);
                    }
                }

                for (int i = 0; i < time; i++) {
                    int dieyongsize = sharedPreferences.getInt("dieyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < dieyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("dieyong" + i + j, 0));
                    }
                    if (dieyongsize != 0) {
                        dieyonglist.add(listnumb);
                    }
                }

                int a = 0;
                l = new ArrayList<Map<String, Object>>();
                if (ziyouyonglist.size() != 0) {
                    a = ziyouyonglist.size();
                } else if (yangyonglist.size() != 0) {
                    a = yangyonglist.size();
                } else if (wayonglist.size() != 0) {
                    a = wayonglist.size();
                } else if (dieyonglist.size() != 0) {
                    a = dieyonglist.size();
                }
                for (int i = 0; i < a; i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    if (ziyouyonglist.size() != 0) {
                        map.put("ziyouyong", ziyouyonglist.get(i));
                    }
                    if (yangyonglist.size() != 0) {
                        map.put("yangyong", yangyonglist.get(i));
                    }
                    if (wayonglist.size() != 0) {
                        map.put("wayong", wayonglist.get(i));
                    }
                    if (dieyonglist.size() != 0) {
                        map.put("dieyong", dieyonglist.get(i));
                    }
                    l.add(map);
                }
            }
        } else if (erlei == 3) {//组选
            if (code == 0 || code == 3 || code == 7 || code == 10) {
                for (int i = 0; i < time; i++) {
                    int ziyouyongsize = sharedPreferences.getInt("ziyouyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < ziyouyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("ziyouyong" + i + j, 0));
                    }
                    if (ziyouyongsize != 0) {
                        ziyouyonglist.add(listnumb);
                    }
                }

                for (int i = 0; i < time; i++) {
                    int yangyongsize = sharedPreferences.getInt("yangyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < yangyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("yangyong" + i + j, 0));
                    }
                    if (yangyongsize != 0) {
                        yangyonglist.add(listnumb);
                    }
                }

                for (int i = 0; i < time; i++) {
                    int wayongsize = sharedPreferences.getInt("wayongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < wayongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("wayong" + i + j, 0));
                    }
                    if (wayongsize != 0) {
                        wayonglist.add(listnumb);
                    }
                }

                for (int i = 0; i < time; i++) {
                    int dieyongsize = sharedPreferences.getInt("dieyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < dieyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("dieyong" + i + j, 0));
                    }
                    if (dieyongsize != 0) {
                        dieyonglist.add(listnumb);
                    }
                }

                int a = 0;
                l = new ArrayList<Map<String, Object>>();
                if (ziyouyonglist.size() != 0) {
                    a = ziyouyonglist.size();
                } else if (yangyonglist.size() != 0) {
                    a = yangyonglist.size();
                } else if (wayonglist.size() != 0) {
                    a = wayonglist.size();
                } else if (dieyonglist.size() != 0) {
                    a = dieyonglist.size();
                }
                for (int i = 0; i < a; i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    if (ziyouyonglist.size() != 0) {
                        map.put("ziyouyong", ziyouyonglist.get(i));
                    }
                    if (yangyonglist.size() != 0) {
                        map.put("yangyong", yangyonglist.get(i));
                    }
                    if (wayonglist.size() != 0) {
                        map.put("wayong", wayonglist.get(i));
                    }
                    if (dieyonglist.size() != 0) {
                        map.put("dieyong", dieyonglist.get(i));
                    }
                    l.add(map);
                }
            } else if (code == 1 || code == 4 || code == 8 || code == 11) {
                for (int i = 0; i < time; i++) {
                    int ziyouyongsize = sharedPreferences.getInt("ziyouyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < ziyouyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("ziyouyong" + i + j, 0));
                    }
                    if (ziyouyongsize != 0) {
                        ziyouyonglist.add(listnumb);
                    }
                }

                l = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < ziyouyonglist.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ziyouyong", ziyouyonglist.get(i));

                    l.add(map);
                }
            } else if (code == 2 || code == 5 || code == 6 || code == 9 || code == 12 || code == 13) {
                for (int i = 0; i < time; i++) {
                    int ziyouyongsize = sharedPreferences.getInt("ziyouyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < ziyouyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("ziyouyong" + i + j, 0));
                    }
                    if (ziyouyongsize != 0) {
                        ziyouyonglist.add(listnumb);
                    }
                }

                for (int i = 0; i < time; i++) {
                    int yangyongsize = sharedPreferences.getInt("yangyongsize" + i, 0);
                    List<Integer> listnumb = new ArrayList<Integer>();
                    for (int j = 0; j < yangyongsize; j++) {
                        listnumb.add(sharedPreferences.getInt("yangyong" + i + j, 0));
                    }
                    if (yangyongsize != 0) {
                        yangyonglist.add(listnumb);
                    }
                }

                l = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < ziyouyonglist.size(); i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ziyouyong", ziyouyonglist.get(i));
                    map.put("yangyong", yangyonglist.get(i));
                    l.add(map);
                }
            }
        }
        return l;
    }

    /**
     * 获取双色球选择的号码
     */
    private List<Map<String, Object>> getSharedPreferences() {
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
            int redsize = sharedPreferences.getInt("redsize" + i, 0);//红球的个数
            int reddsize = sharedPreferences.getInt("reddsize" + i, 0);//胆码的个数
            String listnumbd = sharedPreferences.getString("redd" + i, "");
            if (redsize != 0) {
                reddlist.add(listnumbd);
                reddsizelist.add(reddsize);
            }
            List<Integer> listnumb = new ArrayList<Integer>();
            for (int j = 0; j < redsize; j++) {
                listnumb.add(sharedPreferences.getInt("red" + i + j, 0));
            }
            if (redsize != 0) {
                redlist.add(listnumb);
            }
        }
        for (int i = 0; i < time; i++) {
            int bluesize = sharedPreferences.getInt("bluesize" + i, 0);//蓝球的个数
            int bluedsize = sharedPreferences.getInt("bluedsize" + i, 0);//胆码的个数
            String listnumbd = sharedPreferences.getString("blued" + i, "");
            if (bluesize != 0) {
                bluedlist.add(listnumbd);
                bluedsizelist.add(bluedsize);
            }
            List<Integer> listnumb = new ArrayList<Integer>();
            for (int j = 0; j < bluesize; j++) {
                listnumb.add(sharedPreferences.getInt("blue" + i + j, 0));
            }
            if (bluesize != 0) {
                bluelist.add(listnumb);
            }
        }
        List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < redlist.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rd", reddlist.get(i));
            map.put("rdsize", reddsizelist.get(i));
            map.put("bd", bluedlist.get(i));
            map.put("bdsize", bluedsizelist.get(i));
            map.put("red", redlist.get(i));
            map.put("blue", bluelist.get(i));
            l.add(map);
        }
        return l;
    }

    /**
     * 获取七乐彩(22选5)选择的号码
     */
    private List<Map<String, Object>> getSevenHappyShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        //红球的集合
        redlist = new ArrayList<List<Integer>>();
        reddlist = new ArrayList<String>();
        reddsizelist = new ArrayList<Integer>();
        int time = sharedPreferences.getInt("time", 0);
        for (int i = 0; i < time; i++) {
            int redsize = sharedPreferences.getInt("redsize" + i, 0);//红球的个数
            int reddsize = sharedPreferences.getInt("reddsize" + i, 0);//胆码的个数
            String listnumbd = sharedPreferences.getString("redd" + i, "");
            if (redsize != 0) {
                reddlist.add(listnumbd);
                reddsizelist.add(reddsize);
            }
            List<Integer> listnumb = new ArrayList<Integer>();
            for (int j = 0; j < redsize; j++) {
                listnumb.add(sharedPreferences.getInt("red" + i + j, 0));
            }
            if (redsize != 0) {
                redlist.add(listnumb);
            }
        }
        List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < redlist.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rd", reddlist.get(i));
            map.put("rdsize", reddsizelist.get(i));
            map.put("red", redlist.get(i));
            l.add(map);
        }
        return l;
    }

    /**
     * 获取排列五选择的号码
     */
    private List<Map<String, Object>> getSharedPreferences3() {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        //万位的集合
        wanlist = new ArrayList<List<Integer>>();
        //千位的集合
        qianlist = new ArrayList<List<Integer>>();
        //百位的集合
        hunlist = new ArrayList<List<Integer>>();
        //十位的集合
        tenlist = new ArrayList<List<Integer>>();
        //个位的集合
        indlist = new ArrayList<List<Integer>>();

        int time = sharedPreferences.getInt("time", 0);
        for (int i = 0; i < time; i++) {
            int wansize = sharedPreferences.getInt("wansize" + i, 0);//百位的个数
            List<Integer> listnumb = new ArrayList<Integer>();
            for (int j = 0; j < wansize; j++) {
                listnumb.add(sharedPreferences.getInt("wan" + i + j, 0));
            }
            if (wansize != 0) {
                wanlist.add(listnumb);
            }
        }
        for (int i = 0; i < time; i++) {
            int qiansize = sharedPreferences.getInt("qiansize" + i, 0);//百位的个数
            List<Integer> listnumb = new ArrayList<Integer>();
            for (int j = 0; j < qiansize; j++) {
                listnumb.add(sharedPreferences.getInt("qian" + i + j, 0));
            }
            if (qiansize != 0) {
                qianlist.add(listnumb);
            }
        }
        for (int i = 0; i < time; i++) {
            int hunsize = sharedPreferences.getInt("hunsize" + i, 0);//百位的个数
            List<Integer> listnumb = new ArrayList<Integer>();
            for (int j = 0; j < hunsize; j++) {
                listnumb.add(sharedPreferences.getInt("hun" + i + j, 0));
            }
            if (hunsize != 0) {
                hunlist.add(listnumb);
            }
        }
        for (int i = 0; i < time; i++) {
            int tensize = sharedPreferences.getInt("tensize" + i, 0);//十位的个数
            List<Integer> listnumb = new ArrayList<Integer>();
            for (int j = 0; j < tensize; j++) {
                listnumb.add(sharedPreferences.getInt("ten" + i + j, 0));
            }
            if (tensize != 0) {
                tenlist.add(listnumb);
            }
        }
        for (int i = 0; i < time; i++) {
            int indsize = sharedPreferences.getInt("indsize" + i, 0);//十位的个数
            List<Integer> listnumb = new ArrayList<Integer>();
            for (int j = 0; j < indsize; j++) {
                listnumb.add(sharedPreferences.getInt("ind" + i + j, 0));
            }
            if (indsize != 0) {
                indlist.add(listnumb);
            }
        }
        List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < wanlist.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("wan", wanlist.get(i));
            map.put("qian", qianlist.get(i));
            map.put("hun", hunlist.get(i));
            map.put("ten", tenlist.get(i));
            map.put("ind", indlist.get(i));
            l.add(map);
        }
        return l;

    }

    /**
     * 获取3d选择的号码
     */
    private List<Map<String, Object>> getSharedPreferences4() {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        //百位的集合
        hunlist = new ArrayList<List<Integer>>();
        //十位的集合
        tenlist = new ArrayList<List<Integer>>();
        //个位的集合
        indlist = new ArrayList<List<Integer>>();

        int time = sharedPreferences.getInt("time", 0);
        if (code == 0) {//直选
            for (int i = 0; i < time; i++) {
                int hunsize = sharedPreferences.getInt("hunsize" + i, 0);//百位的个数
                List<Integer> listnumb = new ArrayList<Integer>();
                for (int j = 0; j < hunsize; j++) {
                    listnumb.add(sharedPreferences.getInt("hun" + i + j, 0));
                }
                if (hunsize != 0) {
                    hunlist.add(listnumb);
                }
            }
            for (int i = 0; i < time; i++) {
                int tensize = sharedPreferences.getInt("tensize" + i, 0);//十位的个数
                List<Integer> listnumb = new ArrayList<Integer>();
                for (int j = 0; j < tensize; j++) {
                    listnumb.add(sharedPreferences.getInt("ten" + i + j, 0));
                }
                if (tensize != 0) {
                    tenlist.add(listnumb);
                }
            }
            for (int i = 0; i < time; i++) {
                int indsize = sharedPreferences.getInt("indsize" + i, 0);//个位的个数
                List<Integer> listnumb = new ArrayList<Integer>();
                for (int j = 0; j < indsize; j++) {
                    listnumb.add(sharedPreferences.getInt("ind" + i + j, 0));
                }
                if (indsize != 0) {
                    indlist.add(listnumb);
                }
            }
            List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < hunlist.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("hun", hunlist.get(i));
                map.put("ten", tenlist.get(i));
                map.put("ind", indlist.get(i));
                l.add(map);
            }
            return l;
        } else if (code == 1 || code == 2) {//组三
            for (int i = 0; i < time; i++) {
                int hunsize = sharedPreferences.getInt("hunsize" + i, 0);//百位的个数
                List<Integer> listnumb = new ArrayList<Integer>();
                for (int j = 0; j < hunsize; j++) {
                    listnumb.add(sharedPreferences.getInt("hun" + i + j, 0));
                }
                if (hunsize != 0) {
                    hunlist.add(listnumb);
                }
            }
            List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < hunlist.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("hun", hunlist.get(i));
                l.add(map);
            }
            return l;
        }
        return null;
    }

    /**
     * 获取快3选择的号码
     */
    private List<Map<String, Object>> getSharedPreferences5() {
        List<Map<String, Object>> l = null;
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        //百位的集合
        hunlist = new ArrayList<List<Integer>>();
        //十位的集合
        tenlist = new ArrayList<List<Integer>>();

        int time = sharedPreferences.getInt("time", 0);
        if (code == 0 || code == 1 || code == 3 || code == 4 || code == 5) {//和值//三同号
            for (int i = 0; i < time; i++) {
                int hunsize = sharedPreferences.getInt("hunsize" + i, 0);//百位的个数
                List<Integer> listnumb = new ArrayList<Integer>();
                for (int j = 0; j < hunsize; j++) {
                    listnumb.add(sharedPreferences.getInt("hun" + i + j, 0));
                }
                if (hunsize != 0) {
                    hunlist.add(listnumb);
                }
            }

            l = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < hunlist.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("hun", hunlist.get(i));
                l.add(map);
            }
        } else if (code == 2 || code == 6 || code == 7) {//二同号单选
            for (int i = 0; i < time; i++) {
                int hunsize = sharedPreferences.getInt("hunsize" + i, 0);//百位的个数
                List<Integer> listnumb = new ArrayList<Integer>();
                for (int j = 0; j < hunsize; j++) {
                    listnumb.add(sharedPreferences.getInt("hun" + i + j, 0));
                }
                if (hunsize != 0) {
                    hunlist.add(listnumb);
                }
            }
            for (int i = 0; i < time; i++) {
                int tensize = sharedPreferences.getInt("tensize" + i, 0);//百位的个数
                List<Integer> listnumb = new ArrayList<Integer>();
                for (int j = 0; j < tensize; j++) {
                    listnumb.add(sharedPreferences.getInt("ten" + i + j, 0));
                }
                if (tensize != 0) {
                    tenlist.add(listnumb);
                }
            }
            l = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < hunlist.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("hun", hunlist.get(i));
                map.put("ten", tenlist.get(i));
                l.add(map);
            }
        }
        return l;
    }

    /**
     * 获取排列三选择的号码
     */
    private List<Map<String, Object>> getSharedPreferences2() {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        //百位的集合
        hunlist = new ArrayList<List<Integer>>();
        //十位的集合
        tenlist = new ArrayList<List<Integer>>();
        //个位的集合
        indlist = new ArrayList<List<Integer>>();

        int time = sharedPreferences.getInt("time", 0);

        for (int i = 0; i < time; i++) {
            int hunsize = sharedPreferences.getInt("hunsize" + i, 0);//百位的个数
            List<Integer> listnumb = new ArrayList<Integer>();
            for (int j = 0; j < hunsize; j++) {
                listnumb.add(sharedPreferences.getInt("hun" + i + j, 0));
            }
            if (hunsize != 0) {
                hunlist.add(listnumb);
            }
        }
        for (int i = 0; i < time; i++) {
            int tensize = sharedPreferences.getInt("tensize" + i, 0);//十位的个数
            List<Integer> listnumb = new ArrayList<Integer>();
            for (int j = 0; j < tensize; j++) {
                listnumb.add(sharedPreferences.getInt("ten" + i + j, 0));
            }
            if (tensize != 0) {
                tenlist.add(listnumb);
            }
        }
        for (int i = 0; i < time; i++) {
            int indsize = sharedPreferences.getInt("indsize" + i, 0);//十位的个数
            List<Integer> listnumb = new ArrayList<Integer>();
            for (int j = 0; j < indsize; j++) {
                listnumb.add(sharedPreferences.getInt("ind" + i + j, 0));
            }
            if (indsize != 0) {
                indlist.add(listnumb);
            }
        }
        List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < hunlist.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("hun", hunlist.get(i));
            map.put("ten", tenlist.get(i));
            map.put("ind", indlist.get(i));
            l.add(map);
        }
        return l;
    }


    /**
     * 获取七星彩选择的号码
     */
    private List<List<int[]>> getSevenColorShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        //球的集合
        sevenlist = new ArrayList<List<int[]>>();

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
    private List<String> getSevencolor() {
        Log.i("xiabiao", sevenlist.get(0).size() + "");
        list = new ArrayList<String>();
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = color.getNumb(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        return list;
    }

    /**
     * listview容器赋值(七乐彩)
     */

    private List<String> getSevenHappy() {
        list = new ArrayList<String>();
        List<String> slist = new ArrayList<String>();
        String[] s;
        //遍历获取红球的集合
        for (int i = 0; i < redlist.size(); i++) {
            s = new String[redlist.get(i).size()];
            for (int j = 0; j < redlist.get(i).size(); j++) {
                s[j] = redlist.get(i).get(j) + "";
            }
            list.addAll(myCombine.combine(s, 7));
        }
        return list;
    }


    /**
     * 获取选择的号码
     */
    private void getDirectlyShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        //球的集合
        sevenlist = new ArrayList<List<int[]>>();

        int time = sharedPreferences.getInt("time", 0);
        for (int i = 0; i < time; i++) {
            List<int[]> listnumb = new ArrayList<int[]>();
            for (int j = 0; j < cs; j++) {//获取每个位置的号码
                int size = sharedPreferences.getInt("sevencolorsize" + i + j, 0);
                int[] sevencolor = new int[size];
                for (int k = 0; k < size; k++) {
                    sevencolor[k] = sharedPreferences.getInt("sevencolor" + i + j + k, 0);
                    Log.i("abccsd", sevencolor[k] + "");
                }
                listnumb.add(sevencolor);
            }
            sevenlist.add(listnumb);
            Log.i("lista", sevenlist + "");
        }
    }

    /**
     * listview容器赋值(排列三——直选三)
     */
    private List<String> getDirectly() {
        Log.i("xiabiao", sevenlist.get(0).size() + "");
        list = new ArrayList<String>();
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = color.getNumb2(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        return list;
    }

    /**
     * listview容器赋值(排列三——组三单式)
     */
    private List<String> getDirectly1() {
        Log.i("xiabiao", sevenlist.get(0).size() + "");
        list = new ArrayList<String>();
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = color.getNumb3(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        return list;
    }

    /**
     * listview容器赋值(排列三——组三复式)
     */
    private List<String> getDirectly2() {
        Log.i("xiabiao", sevenlist.get(0).size() + "");
        list = new ArrayList<String>();
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = color.getNumb4(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        Log.i("list", list + "");
        return list;
    }

    /**
     * listview容器赋值(排列三——组选六)
     */
    private List<String> getDirectly3() {
        Log.i("xiabiao", sevenlist.get(0).size() + "");
        list = new ArrayList<String>();
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = color.getNumb5(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        Log.i("list", list + "");
        return list;
    }

    /**
     * listview容器赋值(排列三——直选三和)
     */
    private List<String> getDirectly4() {
        Log.i("xiabiao", sevenlist.get(0).size() + "");
        list = new ArrayList<String>();
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = color.getNumb6(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        Log.i("list", list + "");
        return list;
    }

    /**
     * listview容器赋值(排列五)
     */
    private List<String> getArrangeFive() {
        Log.i("xiabiao", sevenlist.get(0).size() + "");
        list = new ArrayList<String>();
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = color.getNumb7(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        return list;
    }

    /**
     * listview容器赋值(快三——和值、三同号、二同号复选)
     */
    private List<String> getFastThree() {
        Log.i("xiabiao", sevenlist.get(0).size() + "");
        list = new ArrayList<String>();
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = color.getNumb10(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        return list;
    }

    /**
     * listview容器赋值(快三——二同号单选)
     */
    private List<String> getFastThree2() {
        Log.i("xiabiao", sevenlist.get(0).size() + "");
        list = new ArrayList<String>();
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = color.getNumb11(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        return list;
    }

    /**
     * listview容器赋值(快三——三不同号)
     */
    private List<String> getFastThree3() {
        Log.i("xiabiao", sevenlist.get(0).size() + "");
        list = new ArrayList<String>();
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = color.getNumb12(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        return list;
    }

    /**
     * listview容器赋值(快三——二不同号)
     */
    private List<String> getFastThree4() {
        Log.i("xiabiao", sevenlist.get(0).size() + "");
        list = new ArrayList<String>();
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = color.getNumb13(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        return list;
    }

    /**
     * listview容器赋值(快三——三不同号胆拖)
     */
    private List<String> getFastThree5() {
        list = new ArrayList<String>();
        List<String> slist = new ArrayList<String>();
        Log.i("xiabiao", sevenlist.get(0).size() + "");
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            if (sevenlist.get(i).get(0).length == 1) {
                slist = color.getNumb14(sevenlist.get(i));

            } else if (sevenlist.get(i).get(0).length == 2) {
                slist = color.getNumb15(sevenlist.get(i));
            }
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        return list;
    }

    /**
     * listview容器赋值(快三——二不同号胆拖)
     */
    private List<String> getFastThree6() {
        Log.i("xiabiao", sevenlist.get(0).size() + "");
        list = new ArrayList<String>();
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = color.getNumb16(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        return list;
    }

    /**
     * listview容器赋值(3d——直选)
     */
    private List<String> getThreeD() {
        Log.i("xiabiao", sevenlist.get(0).size() + "");
        list = new ArrayList<String>();
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = color.getNumb2(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        return list;
    }

    /**
     * listview容器赋值(3d——组三)
     */
    private List<String> getThreeD2() {
        Log.i("xiabiaoxiabiao", sevenlist.get(0).size() + "");
        list = new ArrayList<String>();
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = color.getNumb8(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        return list;
    }

    /**
     * listview容器赋值(3d——组六)
     */
    private List<String> getThreeD3() {
        Log.i("xiabiaoxiabiao", sevenlist.get(0).size() + "");
        list = new ArrayList<String>();
        SevenColor color = new SevenColor();
        for (int i = 0; i < sevenlist.size(); i++) {
            List<String> slist = color.getNumb9(sevenlist.get(i));
            for (int j = 0; j < slist.size(); j++) {
                list.add(slist.get(j));
            }
        }
        return list;
    }

    /**
     * 冒泡法排序
     * <p>
     * <li>比较相邻的元素。如果第一个比第二个大，就交换他们两个。</li>
     * <li>对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。</li>
     * <li>针对所有的元素重复以上的步骤，除了最后一个。</li>
     * <li>持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。</li>
     *
     * @param numbers 需要排序的整型数组
     */
    private int[] bubbleSort(int[] numbers) {
        int temp; // 记录临时中间值
        int size = numbers.length; // 数组大小
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (numbers[i] > numbers[j]) { // 交换两数的位置
                    temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                }
            }
        }
        return numbers;
    }
}
