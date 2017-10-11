package com.juyikeji.caipiao.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.utils.MyCombine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jyg on 2016/3/6 0006.
 */
public class OneLotteryBetPayAdapterDLT extends BaseAdapter {

    private Context context;
    private ListGridviewAdapter adapter;
    private List<Map<String, Object>> listt;
    private TextView tv;
    private EditText edt;
    List<String[]> list;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public OneLotteryBetPayAdapterDLT(Context context, List<Map<String, Object>> listt, TextView tv,EditText edt) {
        this.context = context;
        this.listt = listt;
        this.tv = tv;
        this.edt=edt;
        sharedPreferences = context.getSharedPreferences("numb", context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public int getCount() {
        return listt.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listt.get(position);
    }

    //给gv的容器赋值（大乐透）
    private List<String[]> getDate(int position) {
        Map<String, Object> map = listt.get(position);
        list = new ArrayList<String[]>();
        String rd = (String) map.get("rd");//胆码
        int rdsize = (Integer) map.get("rdsize");//胆码个数
        String bd = (String) map.get("bd");//蓝胆码
        int bdsize = (Integer) map.get("bdsize");//蓝胆码个数
        List<Integer> redlist = (List<Integer>) map.get("red");//红球
        List<Integer> bluelist = (List<Integer>) map.get("blue");//篮球
        String[] s;
        try {
            for (int i = 0; i < rdsize; i++) {
                s = new String[2];
                s[0] = rd.substring(0, rd.indexOf(" "));//截取每个胆码
                s[1] = "0";
                list.add(s);//根据选择次数存放
                rd = rd.substring(rd.indexOf(" ") + 1);//将剩余胆码重置从新截取
            }
            for (int i = 0; i < redlist.size(); i++) {
                s = new String[2];
                s[0] = redlist.get(i) + "";
                s[1] = "1";//非胆码红球标识
                list.add(s);
            }
            for (int i = 0; i < bdsize; i++) {
                s = new String[2];
                s[0] = bd.substring(0, bd.indexOf(" "));//截取每个胆码
                s[1] = "3";
                list.add(s);//根据选择次数存放
                bd = bd.substring(rd.indexOf(" ") + 1);//将剩余胆码重置从新截取
            }
            for (int i = 0; i < bluelist.size(); i++) {
                s = new String[2];
                s[0] = bluelist.get(i) + "";
                s[1] = "2";//蓝球标识
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //机选方法
    public void changeNumber() {
        int b1 = (int) (Math.random() * 12 + 1);
        int b2 = (int) (Math.random() * 12 + 1);
        if (b1 == b2) {
            changeNumber();
            return;
        }

        int time=sharedPreferences.getInt("time",0);
        Map<String, Object> map = new HashMap<String, Object>();
        int[] test;
        List<Integer> r = new ArrayList<Integer>();
        List<Integer> b = new ArrayList<Integer>();
        test = testRndom35();
        for (int i = 0; i < test.length; i++) {
            r.add(test[i]);
        }
        b.add(b1);
        b.add(b2);
        editor.putInt("bluesize" + listt.size(), b.size());
        editor.putInt("blue" + listt.size() + 0, b1);
        editor.putInt("blue" + listt.size() + 1, b2);
        editor.putInt("redsize" + listt.size(), r.size());
        for (int i = 0; i < r.size(); i++) {
            editor.putInt("red" + listt.size() + i, r.get(i));
        }
        editor.putInt("time",time+1);
        editor.commit();

        map.put("rd", "");
        map.put("rdsize", 0);
        map.put("bd", "");
        map.put("bdsize", 0);
        map.put("red", r);
        map.put("blue", b);
        listt.add(map);
        notifyDataSetChanged();
    }

    //生成随机数的方法(大乐透)
    private int[] testRndom35() {
        int[] b = new int[5];
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) (Math.random() * 35 + 1);
            for (int j = 0; j <= i; j++) {

                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney() {
        MyCombine myCombine = new MyCombine();
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int redd = 0;
            int bdd = 0;
            int red = 0;
            int blue = 0;
            List<String[]> listmoney = getDate(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0"://红球胆码
                        redd++;
                        break;
                    case "2":
                        blue++;//非胆蓝球
                        break;
                    case "3":
                        bdd++;//蓝胆
                        break;
                    default://非胆码红球
                        red++;
                        break;
                }
            }
            String[] s = new String[red];
            for (int k = 0; k < red; k++) {
                s[k] = k + "";
            }
            String[] s1 = new String[blue];
            for (int k = 0; k < blue; k++) {
                s1[k] = k + "";
            }
            money = money + myCombine.combine(s, 5 - redd).size() * myCombine.combine(s1, 2 - bdd).size() * 2;
        }
        if ("".equals(edt.getText().toString())){
            tv.setText(money + "");
        }else {
            int m=Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money*m+"");
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_one_lotterybetpay_item1, null);
            holder.btn_clear = (Button) convertView.findViewById(R.id.btn_clear);
            holder.btn_clear.setTag(position);
            holder.gv = (GridView) convertView.findViewById(R.id.gv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        getMoney();
        holder.btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int time = sharedPreferences.getInt("time", 0);
                final Handler handler = new Handler() {
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == 1) {
                            listt.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                };
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //将shared中选球的次数减去一位
                        editor.putInt("time", time - 1);
                        //清除shared中本项信息
                        for (int i = 0; i < getDate(position).size(); i++) {
                            Map<String, Object> map1 = listt.get(position);
                            int rds = (Integer) map1.get("rdsize");//红胆个数
                            int bds = (Integer) map1.get("bdsize");//蓝胆个数
                            List<Integer> redlist1 = (List<Integer>) map1.get("red");//红球
                            List<Integer> bluelist1 = (List<Integer>) map1.get("blue");//篮球
                            for (int k = 0; k < getDate(position).size(); k++) {
                                switch (getDate(position).get(k)[1]) {
                                    case "0"://红球胆码
                                        editor.remove("reddsize" + position);
                                        for (int j = 0; j < rds; j++) {
                                            editor.remove("redd" + position + j);
                                        }
                                        break;
                                    case "2":
                                        editor.remove("bluesize" + position);
                                        for (int j = 0; j < bluelist1.size(); j++) {
                                            editor.remove("blue" + position + j);
                                        }
                                        break;
                                    case "3":
                                        //蓝胆
                                        editor.remove("bluedsize" + position);
                                        for (int j = 0; j < bds; j++) {
                                            editor.remove("blued" + position + j);
                                        }
                                    default://非胆码红球
                                        editor.remove("redsize" + position);
                                        for (int j = 0; j < redlist1.size(); j++) {
                                            editor.remove("red" + position + j);
                                        }
                                        break;
                                }
                            }
                            editor.commit();
                        }
                        /**
                         * 清除最后一项在shared的缓存
                         */
                        for (int i = 0; i < getDate(listt.size() - 1).size(); i++) {
                            Map<String, Object> map1 = listt.get(listt.size() - 1);
                            int rds = (Integer) map1.get("rdsize");//红胆码个数
                            int bds = (Integer) map1.get("bdsize");//蓝胆码个数
                            List<Integer> redlist1 = (List<Integer>) map1.get("red");//红球
                            List<Integer> bluelist1 = (List<Integer>) map1.get("blue");//篮球
                            for (int k = 0; k < getDate(listt.size() - 1).size(); k++) {
                                switch (getDate(listt.size() - 1).get(k)[1]) {
                                    case "0"://红球胆码
                                        editor.remove("reddsize" + (listt.size() - 1));
                                        for (int j = 0; j < rds; j++) {
                                            editor.remove("redd" + (listt.size() - 1) + j);
                                        }
                                        break;
                                    case "2":
                                        editor.remove("bluesize" + (listt.size() - 1));
                                        for (int j = 0; j < bluelist1.size(); j++) {
                                            editor.remove("blue" + (listt.size() - 1) + j);
                                        }
                                        break;
                                    case "3"://蓝球胆码
                                        editor.remove("bluedsize" + (listt.size() - 1));
                                        for (int j = 0; j < bds; j++) {
                                            editor.remove("blued" + (listt.size() - 1) + j);
                                        }
                                        break;
                                    default://非胆码红球
                                        editor.remove("redsize" + (listt.size() - 1));
                                        for (int j = 0; j < redlist1.size(); j++) {
                                            editor.remove("red" + (listt.size() - 1) + i);
                                        }
                                        break;
                                }
                            }
                            editor.commit();
                        }
                        /**
                         * 将删除项后面的项在shared中往前提一个位置
                         */
                        for (int i = 0; i < listt.size() - position - 1; i++) {
                            Map<String, Object> map1 = listt.get(position + i + 1);
                            String rdd = (String) map1.get("rd");//红胆码
                            int rds = (Integer) map1.get("rdsize");//红胆码个数
                            String bdd = (String) map1.get("rd");//蓝胆码
                            int bds = (Integer) map1.get("rdsize");//蓝胆码个数
                            List<Integer> redlist1 = (List<Integer>) map1.get("red");//红球
                            List<Integer> bluelist1 = (List<Integer>) map1.get("blue");//篮球
                            for (int k = 0; k < getDate(position + i + 1).size(); k++) {
                                switch (getDate(position + i + 1).get(k)[1]) {
                                    case "0"://红球胆码
                                        editor.putInt("reddsize" + (position + i), rds);
                                        editor.putString("redd" + (position + i), rdd);
                                        break;
                                    case "2":
                                        editor.putInt("bluesize" + (position + i), bluelist1.size());
                                        for (int j = 0; j < bluelist1.size(); j++) {
                                            editor.putInt("blue" + (position + i) + j, bluelist1.get(j));
                                        }
                                        break;
                                    case "3"://篮胆码
                                        editor.putInt("bluedsize" + (position + i), bds);
                                        editor.putString("blued" + (position + i), bdd);
                                        break;
                                    default://非胆码红球
                                        editor.putInt("redsize" + (position + i), redlist1.size());
                                        for (int j = 0; j < redlist1.size(); j++) {
                                            editor.putInt("red" + (position + i) + j, redlist1.get(j));
                                        }
                                        break;
                                }
                            }
                            editor.commit();
                        }
                        Message m = new Message();
                        m.what = 1;
                        // 发送消息到Handler
                        handler.sendMessage(m);
                    }
                }.start();
            }
        });
        adapter = new ListGridviewAdapter(context, getDate(position));
        holder.gv.setAdapter(adapter);
        holder.gv.setClickable(false);
        holder.gv.setPressed(false);
        holder.gv.setEnabled(false);
        return convertView;
    }

    class ViewHolder {
        Button btn_clear;
        GridView gv;
    }
}
