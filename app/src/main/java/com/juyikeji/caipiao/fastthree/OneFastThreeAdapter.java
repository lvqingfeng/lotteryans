package com.juyikeji.caipiao.fastthree;

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
import com.juyikeji.caipiao.directly.OneDirectlyListGridviewAdapter;
import com.juyikeji.caipiao.utils.MyCombine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/28 0028.
 */
public class OneFastThreeAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> listt;
    private int bs;
    private OneFastThreeListGridviewAdapter adapter;
    List<String[]> list;
    List<int[]> list1;
    private TextView tv;
    private EditText edt;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public OneFastThreeAdapter(Context context, List<Map<String, Object>> listt, int bs, TextView tv, EditText edt) {
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
            int hun = 0;
            List<String[]> listmoney = getDate(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0"://红球胆码
                        hun++;
                        break;
                }
            }
            money = money + hun * 2;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney1() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int hun = 0;
            int ten = 0;
            List<String[]> listmoney = getDate1(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0"://红球胆码
                        hun++;
                        break;
                    case "1":
                        ten++;
                        break;
                }
            }
            money = money + hun * ten * 2;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney2() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int hun = 0;
            List<String[]> listmoney = getDate2(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0"://红球胆码
                        hun++;
                        break;
                }
            }
            MyCombine myCombine = new MyCombine();
            String[] s = new String[hun];
            for (int k = 0; k < hun; k++) {
                s[k] = k + "";
            }
            money = money + myCombine.combine(s, 3).size() * 2;
        }
        tv.setText(money + "");
        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney3() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int hun = 0;
            List<String[]> listmoney = getDate2(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0"://红球胆码
                        hun++;
                        break;
                }
            }
            switch (hun) {
                case 2:
                    money = money + 2;
                    break;
                case 3:
                    money = money + 6;
                    break;
                case 4:
                    money = money + 12;
                    break;
                case 5:
                    money = money + 20;
                    break;
                case 6:
                    money = money + 30;
                    break;
            }
        }
        tv.setText(money + "");
        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney4() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int hun = 0;
            int ten = 0;
            List<String[]> listmoney = getDate1(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0"://红球胆码
                        hun++;
                        break;
                    case "1":
                        ten++;
                        break;
                }
            }
            if (hun == 1) {
                switch (ten) {
                    case 3:
                        money = money + 6;
                        break;
                    case 4:
                        money = money + 12;
                        break;
                    case 5:
                        money = money + 20;
                        break;
                }
            } else if (hun == 2) {
                money = money + ten * 2;
            }
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
        List<Integer> hunlist = new ArrayList<Integer>();
        test = testRndom33();

        hunlist.add(test[0]);

        editor.putInt("hunsize" + listt.size(), hunlist.size());
        editor.putInt("hun" + listt.size() + 0, hunlist.get(0));

        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hun", hunlist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //生成随机数的方法(排列三)
    private int[] testRndom33() {
        int[] b = new int[1];
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

    //机选方法
    public void changeNumber1() {
        int time = sharedPreferences.getInt("time", 0);
        int[] test;
        //百位的集合
        List<Integer> hunlist = new ArrayList<Integer>();

        test = testRndom1();
        hunlist.add(test[0]);

        editor.putInt("hunsize" + listt.size(), hunlist.size());
        editor.putInt("hun" + listt.size() + 0, hunlist.get(0));

        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hun", hunlist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //生成随机数的方法(排列三)
    private int[] testRndom1() {
        int[] b = new int[1];
        for (int i = 0; i < 1; i++) {
            int x = (int) ((Math.random() * 6) + 1);
            b[i] = x;
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //机选方法
    public void changeNumber2() {
        int time = sharedPreferences.getInt("time", 0);
        int[] test;
        //百位的集合
        List<Integer> hunlist = new ArrayList<Integer>();
        //百位的集合
        List<Integer> tenlist = new ArrayList<Integer>();
        test = testRndom2();
        hunlist.add(test[0]);
        tenlist.add(test[1]);
        editor.putInt("hunsize" + listt.size(), hunlist.size());
        for (int a = 0; a < hunlist.size(); a++) {
            editor.putInt("hun" + listt.size() + a, hunlist.get(a));
        }
        editor.putInt("tensize" + listt.size(), tenlist.size());
        for (int a = 0; a < tenlist.size(); a++) {
            editor.putInt("ten" + listt.size() + a, tenlist.get(a));
        }
        editor.putInt("time", time + 1);
        editor.commit();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hun", hunlist);
        map.put("ten", tenlist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //生成随机数的方法(二同号单选)
    private int[] testRndom2() {
        int[] b = new int[2];
        for (int i = 0; i < 2; i++) {
            int x = (int) ((Math.random() * 6) + 1);
            b[i] = x;
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //机选方法
    public void changeNumber3() {
        int time = sharedPreferences.getInt("time", 0);
        int[] test;
        //百位的集合
        List<Integer> hunlist = new ArrayList<Integer>();

        test = testRndom3();
        for (int i = 0; i < test.length; i++) {
            hunlist.add(test[i]);
        }
        editor.putInt("hunsize" + listt.size(), hunlist.size());
        for (int a = 0; a < hunlist.size(); a++) {
            editor.putInt("hun" + listt.size() + a, hunlist.get(a));
        }
        editor.putInt("time", time + 1);
        editor.commit();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hun", hunlist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //机选方法
    public void changeNumber4() {
        int time = sharedPreferences.getInt("time", 0);
        int[] test;
        //百位的集合
        List<Integer> hunlist = new ArrayList<Integer>();

        test = testRndom4();
        for (int i = 0; i < test.length; i++) {
            hunlist.add(test[i]);
        }
        editor.putInt("hunsize" + listt.size(), hunlist.size());
        for (int a = 0; a < hunlist.size(); a++) {
            editor.putInt("hun" + listt.size() + a, hunlist.get(a));
        }
        editor.putInt("time", time + 1);
        editor.commit();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hun", hunlist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //生成随机数的方法(排列三)
    private int[] testRndom3() {
        int[] b = new int[3];
        for (int i = 0; i < 3; i++) {
            int x = (int) ((Math.random() * 6) + 1);
            b[i] = x;
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //机选方法
    public void changeNumber5() {
        int time = sharedPreferences.getInt("time", 0);
        int[] test;
        //百位的集合
        List<Integer> hunlist = new ArrayList<Integer>();
        List<Integer> tenlist = new ArrayList<Integer>();

        test = testRndom5();
        hunlist.add(test[0]);
        for (int i = 1; i < test.length; i++) {
            tenlist.add(test[i]);
        }
        editor.putInt("hunsize" + listt.size(), hunlist.size());
        for (int a = 0; a < hunlist.size(); a++) {
            editor.putInt("hun" + listt.size() + a, hunlist.get(a));
        }
        editor.putInt("tensize" + listt.size(), tenlist.size());
        for (int a = 0; a < tenlist.size(); a++) {
            editor.putInt("ten" + listt.size() + a, tenlist.get(a));
        }
        editor.putInt("time", time + 1);
        editor.commit();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hun", hunlist);
        map.put("ten", tenlist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //生成随机数的方法(排列三)
    private int[] testRndom5() {
        int[] b = new int[4];
        for (int i = 0; i < 4; i++) {
            int x = (int) ((Math.random() * 6) + 1);
            b[i] = x;
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //机选方法
    public void changeNumber6() {
        int time = sharedPreferences.getInt("time", 0);
        int[] test;
        //百位的集合
        List<Integer> hunlist = new ArrayList<Integer>();
        List<Integer> tenlist = new ArrayList<Integer>();

        test = testRndom6();
        hunlist.add(test[0]);
        for (int i = 1; i < test.length; i++) {
            tenlist.add(test[i]);
        }
        editor.putInt("hunsize" + listt.size(), hunlist.size());
        for (int a = 0; a < hunlist.size(); a++) {
            editor.putInt("hun" + listt.size() + a, hunlist.get(a));
        }
        editor.putInt("tensize" + listt.size(), tenlist.size());
        for (int a = 0; a < tenlist.size(); a++) {
            editor.putInt("ten" + listt.size() + a, tenlist.get(a));
        }
        editor.putInt("time", time + 1);
        editor.commit();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hun", hunlist);
        map.put("ten", tenlist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //生成随机数的方法(排列三)
    private int[] testRndom6() {
        int[] b = new int[3];
        for (int i = 0; i < 3; i++) {
            int x = (int) ((Math.random() * 6) + 1);
            b[i] = x;
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //生成随机数的方法(排列三)
    private int[] testRndom4() {
        int[] b = new int[2];
        for (int i = 0; i < 2; i++) {
            int x = (int) ((Math.random() * 6) + 1);
            b[i] = x;
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //给gridview的容器赋值（快三——和值）
    private List<String[]> getDate(int position) {
        Map<String, Object> map = listt.get(position);
        list = new ArrayList<String[]>();
        List<Integer> hunlist = (List<Integer>) map.get("hun");//百位
        String[] s;
        try {
            for (int i = 0; i < hunlist.size(); i++) {
                s = new String[2];
                s[0] = hunlist.get(i) + "";
                s[1] = "0";//百位号码标识
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //给gridview的容器赋值（排列三）
    private List<String[]> getDate1(int position) {
        Map<String, Object> map = listt.get(position);
        list = new ArrayList<String[]>();
        List<Integer> hunlist = (List<Integer>) map.get("hun");//百位
        List<Integer> tenlist = (List<Integer>) map.get("ten");//十位
        String[] s;
        try {
            for (int i = 0; i < hunlist.size(); i++) {
                s = new String[2];
                s[0] = hunlist.get(i) + "";
                s[1] = "0";//百位号码标识
                list.add(s);
            }
            for (int i = 0; i < tenlist.size(); i++) {
                s = new String[2];
                s[0] = tenlist.get(i) + "";
                s[1] = "1";//十位号码标识
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //给gridview的容器赋值（快3）
    private List<String[]> getDate2(int position) {
        Map<String, Object> map = listt.get(position);
        list = new ArrayList<String[]>();
        List<Integer> hunlist = (List<Integer>) map.get("hun");//百位
        String[] s;
        try {
            for (int i = 0; i < hunlist.size(); i++) {
                s = new String[2];
                s[0] = hunlist.get(i) + "";
                s[1] = "0";//百位号码标识
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //给gridview的容器赋值（排列三）
    private List<int[]> getDate4(int position) {
        Map<String, Object> map = listt.get(position);
        list1 = new ArrayList<int[]>();
        List<Integer> hunlist = (List<Integer>) map.get("hun");//百位
        Log.i("as", hunlist.size() + "");
        int[] s;
        try {
            for (int i = 0; i < hunlist.size(); i++) {
                s = new int[2];
                s[0] = hunlist.get(i);
                s[1] = -1;//百位号码标识
                list1.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list1;
    }

    private List<String[]> get(List<int[]> list1) {
        Log.i("as1", list1.size() + "");
        list = new ArrayList<String[]>();
        String[] s;
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list1.get(i).length - 1; j++) {
                s = new String[2];
                s[0] = list1.get(i)[0] + "";
                s[1] = "0";//百位号码标识
                list.add(s);
            }
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
        switch (bs) {
            case 0://和值
                getMoney();
                adapter = new OneFastThreeListGridviewAdapter(context, getDate(position), bs);
                break;
            case 1://三同号
                getMoney();
                adapter = new OneFastThreeListGridviewAdapter(context, getDate(position), bs);
                break;
            case 2://二同号单选
            case 7:
                getMoney1();
                adapter = new OneFastThreeListGridviewAdapter(context, getDate1(position), bs);
                break;
            case 3://二同号复选
                getMoney();
                adapter = new OneFastThreeListGridviewAdapter(context, getDate(position), bs);
                break;
            case 4://三不同号
                getMoney2();
                adapter = new OneFastThreeListGridviewAdapter(context, getDate2(position), bs);
                break;
            case 5://二不同号
                getMoney3();
                adapter = new OneFastThreeListGridviewAdapter(context, getDate2(position), bs);
                break;
            case 6://三不同号胆拖
                getMoney4();
                adapter = new OneFastThreeListGridviewAdapter(context, getDate1(position), bs);
                break;
        }

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
