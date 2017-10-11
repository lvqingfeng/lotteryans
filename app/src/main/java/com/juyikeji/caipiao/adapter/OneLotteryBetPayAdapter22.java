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
public class OneLotteryBetPayAdapter22 extends BaseAdapter {

    private Context context;
    private ListGridviewAdapter adapter;
    private List<Map<String, Object>> listt;
    private TextView tv;
    private EditText idt;
    private int bs;//标识是22选5还是七乐彩
    List<String[]> list;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public OneLotteryBetPayAdapter22(Context context, List<Map<String, Object>> listt, TextView tv, EditText idt,int bs) {
        this.context = context;
        this.listt = listt;
        this.tv = tv;
        this.idt=idt;
        this.bs=bs;
        sharedPreferences= context.getSharedPreferences("numb", context.MODE_PRIVATE);
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

    //给gv的容器赋值（22选5）
    private List<String[]> getDate(int position) {
        Map<String, Object> map = listt.get(position);
        list = new ArrayList<String[]>();
        String rd = (String) map.get("rd");//胆码
        int rdsize = (Integer) map.get("rdsize");//胆码个数
        List<Integer> redlist = (List<Integer>) map.get("red");//红球
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //机选方法
    public void changeNumber() {

            int time = sharedPreferences.getInt("time", 0);
            Map<String, Object> map = new HashMap<String, Object>();
            int[] test;
            List<Integer> r = new ArrayList<Integer>();
        if (bs==0) {
            test = testRndom22();
        }else {
            test= testRndom30();
        }
            for (int i = 0; i < test.length; i++) {
                r.add(test[i]);
            }
            editor.putInt("redsize" + listt.size(), r.size());
            for (int i = 0; i < r.size(); i++) {
                editor.putInt("red" + listt.size() + i, r.get(i));
            }
            editor.putInt("time", time + 1);
            editor.commit();
            map.put("rd", "");
            map.put("rdsize", 0);
            map.put("red", r);
            listt.add(map);
        notifyDataSetChanged();
    }

    //生成随机数的方法(双色球)
    private int[] testRndom22() {
        int[] b = new int[5];
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) (Math.random() * 22 + 1);
            for (int j = 0; j <= i; j++) {

                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }
    //生成随机数的方法(双色球)
    private int[] testRndom30() {
        int[] b = new int[7];
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) (Math.random() * 30 + 1);
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
            int red = 0;
            List<String[]> listmoney = getDate(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0"://红球胆码
                        redd++;
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
            if (bs==0) {
                money = money + myCombine.combine(s, 5 - redd).size() * 2;
            }else {
                money=money+myCombine.combine(s,7-redd).size()*2;
            }
        }

        if ("".equals(idt.getText().toString())){
            tv.setText(money + "");
        }else {
            int m=Integer.parseInt(idt.getText().toString().trim());
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
                            int rds = (Integer) map1.get("rdsize");//胆码个数
                            List<Integer> redlist1 = (List<Integer>) map1.get("red");//红球
                            for (int k = 0; k < getDate(position).size(); k++) {
                                switch (getDate(position).get(k)[1]) {
                                    case "0"://红球胆码
                                        editor.remove("reddsize" + position);
                                        for (int j = 0; j < rds; j++) {
                                            editor.remove("redd" + position + j);
                                        }
                                        break;
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
                            int rds = (Integer) map1.get("rdsize");//胆码个数
                            List<Integer> redlist1 = (List<Integer>) map1.get("red");//红球
                            for (int k = 0; k < getDate(listt.size() - 1).size(); k++) {
                                switch (getDate(listt.size() - 1).get(k)[1]) {
                                    case "0"://红球胆码
                                        editor.remove("reddsize" + (listt.size() - 1));
                                        for (int j = 0; j < rds; j++) {
                                            editor.remove("redd" + (listt.size() - 1) + j);
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
                            String rdd = (String) map1.get("rd");//胆码
                            int rds = (Integer) map1.get("rdsize");//胆码个数
                            List<Integer> redlist1 = (List<Integer>) map1.get("red");//红球
                            for (int k = 0; k < getDate(position + i + 1).size(); k++) {
                                switch (getDate(position + i + 1).get(k)[1]) {
                                    case "0"://红球胆码
                                        editor.putInt("reddsize" + (position + i), rds);
                                        editor.putString("redd" + (position + i), rdd);
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
