package com.juyikeji.caipiao.arrangefive;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.directly.OneDirectlyListGridviewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class OneArrangefiveAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> listt;
    private int bs;
    private OneArrangefiveListGridviewAdapter adapter;
    List<String[]> list;
    List<int[]> list1;
    private TextView tv;
    private EditText edt;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public OneArrangefiveAdapter(Context context, List<Map<String, Object>> listt, int bs, TextView tv, EditText edt) {
        this.context = context;
        this.listt = listt;
        this.bs = bs;
        this.tv = tv;
        this.edt = edt;
        sharedPreferences = context.getSharedPreferences("numb", context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public int getCount() {
        return listt.size();
    }

    @Override
    public Object getItem(int position) {
        return listt.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int wan = 0;
            int qian = 0;
            int hun = 0;
            int ten = 0;
            int ind = 0;
            List<String[]> listmoney = getDate(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        wan++;
                        break;
                    case "1":
                        qian++;
                        break;
                    case "2":
                        hun++;
                        break;
                    case "3":
                        ten++;
                        break;
                    case "4":
                        ind++;
                        break;
                }
            }
            money = money + wan * qian * hun * ten * ind * 2;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    //机选方法
    public void changeNumber() {
        int time = sharedPreferences.getInt("time", 0);

        int[] test;
        //百位的集合
        List<Integer> wanlist = new ArrayList<Integer>();
        //百位的集合
        List<Integer> qianlist = new ArrayList<Integer>();
        //百位的集合
        List<Integer> hunlist = new ArrayList<Integer>();
        //十位的集合
        List<Integer> tenlist = new ArrayList<Integer>();
        //个位的集合
        List<Integer> indlist = new ArrayList<Integer>();
        test = testRndom33();

        wanlist.add(test[0]);
        qianlist.add(test[1]);
        hunlist.add(test[2]);
        tenlist.add(test[3]);
        indlist.add(test[4]);
        editor.putInt("wansize" + listt.size(), wanlist.size());
        editor.putInt("wan" + listt.size() + 0, wanlist.get(0));
        editor.putInt("qiansize" + listt.size(), qianlist.size());
        editor.putInt("qian" + listt.size() + 0, qianlist.get(0));
        editor.putInt("hunsize" + listt.size(), hunlist.size());
        editor.putInt("hun" + listt.size() + 0, hunlist.get(0));
        editor.putInt("tensize" + listt.size(), tenlist.size());
        editor.putInt("ten" + listt.size() + 0, tenlist.get(0));
        editor.putInt("indsize" + listt.size(), indlist.size());
        editor.putInt("ind" + listt.size() + 0, indlist.get(0));

        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("wan",wanlist);
        map.put("qian",qianlist);
        map.put("hun", hunlist);
        map.put("ten", tenlist);
        map.put("ind", indlist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //生成随机数的方法(排列三)
    private int[] testRndom33() {
        int[] b = new int[5];
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) (Math.random() * 10);
            for (int j = 0; j <= i; j++) {

                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //给gridview的容器赋值（排列五）
    private List<String[]> getDate(int position) {
        Map<String, Object> map = listt.get(position);
        list = new ArrayList<String[]>();
        List<Integer> wanlist = (List<Integer>) map.get("wan");//万位
        List<Integer> qianlist = (List<Integer>) map.get("qian");//千位
        List<Integer> hunlist = (List<Integer>) map.get("hun");//百位
        List<Integer> tenlist = (List<Integer>) map.get("ten");//十位
        List<Integer> bluelist = (List<Integer>) map.get("ind");//个位
        String[] s;
        try {

            for (int i = 0; i < wanlist.size(); i++) {
                s = new String[2];
                s[0] = wanlist.get(i) + "";
                s[1] = "0";//万位号码标识
                list.add(s);
            }
            for (int i = 0; i < qianlist.size(); i++) {
                s = new String[2];
                s[0] = qianlist.get(i) + "";
                s[1] = "1";//千位号码标识
                list.add(s);
            }
            for (int i = 0; i < hunlist.size(); i++) {
                s = new String[2];
                s[0] = hunlist.get(i) + "";
                s[1] = "2";//百位号码标识
                list.add(s);
            }
            for (int i = 0; i < tenlist.size(); i++) {
                s = new String[2];
                s[0] = tenlist.get(i) + "";
                s[1] = "3";//十位号码标识
                list.add(s);
            }
            for (int i = 0; i < bluelist.size(); i++) {
                s = new String[2];
                s[0] = bluelist.get(i) + "";
                s[1] = "4";//个位号码标识
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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
                SharedPreferences sharedPreferences = context.getSharedPreferences("numb", context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                final int time = sharedPreferences.getInt("time", 0);
                final Handler handler = new Handler() {
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == 1) {
                            listt.remove(position);
                            if (listt.size() == 0) {
                                tv.setText("0");
                            }
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
                            List<Integer> hunlist1 = (List<Integer>) map1.get("hun");//百位
                            List<Integer> tenlist1 = (List<Integer>) map1.get("ten");//十位
                            List<Integer> indlist1 = (List<Integer>) map1.get("ind");//个位
                            for (int k = 0; k < getDate(position).size(); k++) {
                                switch (getDate(position).get(k)[1]) {
                                    case "0"://百位
                                        editor.remove("hunsize" + position);
                                        for (int j = 0; j < hunlist1.size(); j++) {
                                            editor.remove("hun" + position + j);
                                        }
                                        break;
                                    case "1":
                                        editor.remove("tensize" + position);
                                        for (int j = 0; j < tenlist1.size(); j++) {
                                            editor.remove("ten" + position + j);
                                        }
                                        break;
                                    case "2":
                                        editor.remove("indsize" + position);
                                        for (int j = 0; j < indlist1.size(); j++) {
                                            editor.remove("ind" + position + j);
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
                            List<Integer> hunlist1 = (List<Integer>) map1.get("hun");//百位
                            List<Integer> tenlist1 = (List<Integer>) map1.get("ten");//十位
                            List<Integer> indlist1 = (List<Integer>) map1.get("ind");//个位
                            for (int k = 0; k < getDate(listt.size() - 1).size(); k++) {
                                switch (getDate(listt.size() - 1).get(k)[1]) {
                                    case "0"://百位
                                        editor.remove("hunsize" + (listt.size() - 1));
                                        for (int j = 0; j < hunlist1.size(); j++) {
                                            editor.remove("hun" + (listt.size() - 1) + j);
                                        }
                                        break;
                                    case "1"://十位
                                        editor.remove("tensize" + (listt.size() - 1));
                                        for (int j = 0; j < tenlist1.size(); j++) {
                                            editor.remove("ten" + (listt.size() - 1) + j);
                                        }
                                        break;
                                    case "2"://个位
                                        editor.remove("indsize" + (listt.size() - 1));
                                        for (int j = 0; j < indlist1.size(); j++) {
                                            editor.remove("ind" + (listt.size() - 1) + j);
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
                            List<Integer> hunlist1 = (List<Integer>) map1.get("hun");//百位
                            List<Integer> tenlist1 = (List<Integer>) map1.get("ten");//十位
                            List<Integer> indlist1 = (List<Integer>) map1.get("ten");//个位
                            for (int k = 0; k < getDate(position + i + 1).size(); k++) {
                                switch (getDate(position + i + 1).get(k)[1]) {
                                    case "0"://百位
                                        editor.putInt("hunsize" + (position + i), hunlist1.size());
                                        for (int j = 0; j < hunlist1.size(); j++) {
                                            editor.putInt("hun" + (position + i) + j, hunlist1.get(j));
                                        }
                                        break;
                                    case "1"://十位
                                        editor.putInt("tensize" + (position + i), tenlist1.size());
                                        for (int j = 0; j < tenlist1.size(); j++) {
                                            editor.putInt("ten" + (position + i) + j, tenlist1.get(j));
                                        }
                                        break;
                                    case "2"://个位
                                        editor.putInt("indsize" + (position + i), indlist1.size());
                                        for (int j = 0; j < indlist1.size(); j++) {
                                            editor.putInt("ind" + (position + i) + j, indlist1.get(j));
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
        adapter = new OneArrangefiveListGridviewAdapter(context, getDate(position), bs);
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