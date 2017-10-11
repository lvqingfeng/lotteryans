package com.juyikeji.caipiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.adapter.OneLotteryBetPayAdapter;
import com.juyikeji.caipiao.utils.MyCombine;
import com.juyikeji.caipiao.utils.SevenColor;
import com.juyikeji.caipiao.utils.SysApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyg on 2016/3/6 0006.
 */
public class OneLotteryBetPay extends Activity implements View.OnClickListener {

    private Button btn_close, btn_agree;
    private TextView tv_stoptime, tv_goon, tv_choose, tv_money, tv_next;
    private EditText edit_number;
    private ListView lv;
    private OneLotteryBetPayAdapter adapter;
    private List<String> list;
    private MyCombine myCombine = new MyCombine();

    //双色球红球和蓝球的集合
    List<List<Integer>> redlist;
    List<String> reddlist;//胆码
    List<Integer> reddsizelist;//胆码
    List<String> bluedlist;//篮球胆码
    List<Integer> bluedsizelist;//篮球胆码
    List<List<Integer>> bluelist;
    //七星彩彩球的集合;
    List<List<int[]>> sevenlist;

    int code, code2, cs = 3;

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
        lv = (ListView) findViewById(R.id.lv);
        switch (witchOne()) {
            case 0:
                //默认参数表示传输错误

                break;
            case 1:
                //双色球
                getSharedPreferences();
                adapter = new OneLotteryBetPayAdapter(this, getDoublecolor(), 1);
                break;
            case 2:
                //大乐透
                getSharedPreferences();
                adapter = new OneLotteryBetPayAdapter(this, getDaletou(), 2);
                break;
            case 3:
                //泳坛夺冠
                break;
            case 4:
                //七星彩
                getSevenColorShared();
                adapter = new OneLotteryBetPayAdapter(this, getSevencolor(), 4);
                break;
            case 5:
                //排列三
                if (code == 0) {//直选三
                    cs = 3;//表示有几个位置
                    getDirectlyShared();
                    adapter = new OneLotteryBetPayAdapter(this, getDirectly(), 5, code);
                } else if (code == 1) {//组三单式
                    cs = 2;
                    getDirectlyShared();
                    adapter = new OneLotteryBetPayAdapter(this, getDirectly1(), 5, code);
                } else if (code == 2) {//组三复式
                    cs = 1;
                    getDirectlyShared();
                    adapter = new OneLotteryBetPayAdapter(this, getDirectly2(), 5, code);
                } else if (code == 3) {//组选六
                    cs = 1;
                    getDirectlyShared();
                    adapter = new OneLotteryBetPayAdapter(this, getDirectly3(), 5, code);
                } else if (code == 4) {//直选三和
                    cs = 1;
                    getDirectlyShared();
                    adapter = new OneLotteryBetPayAdapter(this, getDirectly4(), 5, code);
                }
                break;
            case 6:
                //22选5
                getSevenHappyShared();
                adapter = new OneLotteryBetPayAdapter(this, getTTF(), 6);
                break;
            case 7:
                //3D
                if (code == 0) {//直选
                    cs = 3;//表示有几个位置
                    getDirectlyShared();
                    adapter = new OneLotteryBetPayAdapter(this, getThreeD(), 7, code);
                } else if (code == 1) {//组三
                    cs = 1;
                    getDirectlyShared();
                    adapter = new OneLotteryBetPayAdapter(this, getThreeD2(), 7, code);
                } else if (code == 2) {//组六
                    cs = 1;
                    getDirectlyShared();
                    adapter = new OneLotteryBetPayAdapter(this, getThreeD3(), 7, code);
                }
                break;
            case 8:
                //七乐彩
                getSevenHappyShared();
                adapter = new OneLotteryBetPayAdapter(this, getSevenHappy(), 8);
                break;
            case 9:
                //快三
                if (code == 0 || code == 1 || code == 3) {//和值、三同号、二同号复选
                    cs = 1;//表示有几个位置
                    getDirectlyShared();
                    adapter = new OneLotteryBetPayAdapter(this, getFastThree(), 9, code);
                } else if (code == 2) {//二同号单选
                    cs = 2;
                    getDirectlyShared();
                    adapter = new OneLotteryBetPayAdapter(this, getFastThree2(), 9, code);
                } else if (code == 4) {//三不同号
                    cs = 1;
                    getDirectlyShared();
                    adapter = new OneLotteryBetPayAdapter(this, getFastThree3(), 9, code);
                } else if (code == 5) {//二不同号
                    cs = 1;
                    getDirectlyShared();
                    adapter = new OneLotteryBetPayAdapter(this, getFastThree4(), 9, code);
                } else if (code == 6) {//三不同号胆拖
                    cs = 2;
                    getDirectlyShared();
                    adapter = new OneLotteryBetPayAdapter(this, getFastThree5(), 9, code);
                } else if (code == 7) {//二不同号胆拖
                    cs = 2;
                    getDirectlyShared();
                    adapter = new OneLotteryBetPayAdapter(this, getFastThree6(), 9, code);
                }
                break;
            case 10:
                //排列五
                cs = 5;//表示有几个位置
                getDirectlyShared();
                adapter = new OneLotteryBetPayAdapter(this, getArrangeFive(), 10);
                break;
        }

        lv.setAdapter(adapter);

        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        btn_agree = (Button) findViewById(R.id.btn_agree);
        btn_agree.setOnClickListener(this);

        tv_stoptime = (TextView) findViewById(R.id.tv_stoptime);
        tv_goon = (TextView) findViewById(R.id.tv_goon);
        tv_goon.setOnClickListener(this);
        tv_choose = (TextView) findViewById(R.id.tv_choose);
        tv_choose.setOnClickListener(this);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);

        edit_number = (EditText) findViewById(R.id.edit_number);
    }

    /**
     * 判断跳转过来的是哪个彩种
     */
    private int witchOne() {
        Intent intent = getIntent();
        code = intent.getIntExtra("分类", 0);
        return intent.getIntExtra("彩种", 0);
    }

    /**
     * 页面点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.putInt("time", 0);
                editor.commit();
                finish();
                break;
            case R.id.tv_goon:
                //继续添加
                finish();
                break;
            case R.id.tv_choose:
                //机选一注
                adapter.changeNumber();
                break;
            case R.id.btn_agree:
                //同意协议
                //测试七乐彩算法
//                MyCombine combine=new MyCombine();
//                String[] a={"1","2","3","4","5","6","7","8","9","1"};
//                int b=combine.combine(a,7).size();
//                Toast.makeText(this,""+b,Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_next:
                //提交
                startActivity(new Intent(this, OneLotteryBetPayFor.class));
                break;

        }
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

        bluedlist=new ArrayList<String>();
        bluedsizelist=new ArrayList<Integer>();
        int time = sharedPreferences.getInt("time", 0);

        for (int i = 0; i < time; i++) {

            int reddsize = sharedPreferences.getInt("reddsize" + i, 0);//胆码的个数
            String listnumbd = sharedPreferences.getString("redd" + i, "");
            reddlist.add(listnumbd);
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
            bluedlist.add(listnumbd);
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
    private List<String> getDoublecolor() {
        list = new ArrayList<String>();
        List<String> slist = new ArrayList<String>();
        String[] s;
        //遍历获取红球的集合
        for (int i = 0; i < redlist.size(); i++) {
            int size = reddsizelist.get(i);//胆码的个数
            String sd = reddlist.get(i);//胆码
            s = new String[redlist.get(i).size()];//创建获取红球的集合
            for (int j = 0; j < redlist.get(i).size(); j++) {
                s[j] = redlist.get(i).get(j) + "";
            }
            slist = myCombine.combine(s, 6 - size);
            for (int k = 0; k < bluelist.get(i).size(); k++) {
                for (int j = 0; j < slist.size(); j++) {
                    list.add(sd + slist.get(j) + bluelist.get(i).get(k));
                }
            }
        }
        return list;
    }

    /**
     * listview容器赋值(大乐透)
     */
    private List<String> getDaletou() {
        list = new ArrayList<String>();
        List<String> slist = new ArrayList<String>();
        List<String> blist = new ArrayList<String>();
        String[] sr;//红球的集合
        String[] sb;//篮球的集合
        //遍历获取红球的集合
        for (int i = 0; i < redlist.size(); i++) {
            int size=reddsizelist.get(i);//胆码的个数
            String sd=reddlist.get(i);//胆码
            sr = new String[redlist.get(i).size()];
            for (int j = 0; j < redlist.get(i).size(); j++) {
                sr[j] = redlist.get(i).get(j) + "";
            }
            slist = myCombine.combine(sr, 5-size);
            //遍历获取蓝球的集合
            int sizeb=bluedsizelist.get(i);//篮球胆码的个数
            String sdb=bluedlist.get(i);//篮球胆码
            sb = new String[bluelist.get(i).size()];
            for (int j = 0; j < bluelist.get(i).size(); j++) {
                sb[j] = bluelist.get(i).get(j) + "";
            }
            blist = myCombine.combine(sb, 2-sizeb);
            for (int k = 0; k < blist.size(); k++) {
                for (int j = 0; j < slist.size(); j++) {
                    list.add(sd+slist.get(j) + sdb+blist.get(k));
                }
            }
        }

        return list;
    }

    /**
     * 获取七星彩选择的号码
     */
    private void getSevenColorShared() {
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
                    Log.i("abccsd", sharedPreferences.getInt("sevencolor" + i + j + k, 0) + "");
                }
                listnumb.add(sevencolor);
            }
            sevenlist.add(listnumb);
        }
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
     * 获取七乐彩(22选5)选择的号码
     */
    private void getSevenHappyShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("numb", MODE_PRIVATE);
        //红球的集合
        redlist = new ArrayList<List<Integer>>();
        int time = sharedPreferences.getInt("time", 0);
        for (int i = 0; i < time; i++) {
            int redsize = sharedPreferences.getInt("redsize" + i, 0);//红球的个数
            List<Integer> listnumb = new ArrayList<Integer>();
            for (int j = 0; j < redsize; j++) {
                listnumb.add(sharedPreferences.getInt("red" + i + j, 0));
            }
            redlist.add(listnumb);
        }
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
     * listview容器赋值(22选5)
     */

    private List<String> getTTF() {
        list = new ArrayList<String>();
        List<String> slist = new ArrayList<String>();
        String[] s;
        //遍历获取红球的集合
        for (int i = 0; i < redlist.size(); i++) {
            s = new String[redlist.get(i).size()];
            for (int j = 0; j < redlist.get(i).size(); j++) {
                s[j] = redlist.get(i).get(j) + "";
            }
            list.addAll(myCombine.combine(s, 5));
        }
        Toast.makeText(this, list.size() + "", Toast.LENGTH_SHORT).show();
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
        List<String> slist=new ArrayList<String>();
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
}
