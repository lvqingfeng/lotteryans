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
import com.juyikeji.caipiao.utils.SevenColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jyg on 2016/3/6 0006.
 */
public class OneLotteryBetPayAdapterScolor extends BaseAdapter {

    private Context context;
    private ListGridviewAdapterSc adapter;
    private List<List<int[]>> listt;
    private TextView tv;
    private EditText idt;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public OneLotteryBetPayAdapterScolor(Context context, List<List<int[]>> listt, TextView tv, EditText idt) {
        this.context = context;
        this.listt = listt;
        this.tv = tv;
        this.idt = idt;
        sharedPreferences = context.getSharedPreferences("numb", context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //给gv的容器赋值（双色球）
    private List<int[]> getDate(int position) {
        List<int[]> listgv = new ArrayList<int[]>();
        List<int[]> listsz = listt.get(position);//获取7个位置球的集合
        for (int i = 0; i < listsz.size(); i++) {//遍历每个位置（7）
            List<Integer> listitem = new ArrayList<Integer>();
            for (int j = 0; j < listsz.get(i).length; j++) {//每个位置球的数组
                int[] s = new int[2];
                s[0] = listsz.get(i)[j];
                s[1] = i + 1;
                listgv.add(s);
            }
        }
        return listgv;
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

    //机选方法
    public void changeNumber() {
        int time = sharedPreferences.getInt("time", 0);
        List<int[]> ll=new ArrayList<int[]>();
        for (int i=0;i<7;i++){
            int[] a=new int[1];
            a[0]=(int)(Math.random()*10);
            ll.add(a);
            editor.putInt("sevencolorsize" + time + i, 1);
            editor.putInt("sevencolor" + time + i + 0, a[0]);
        }
        listt.add(ll);
        editor.putInt("time", time + 1);
        editor.commit();
        notifyDataSetChanged();
    }
    /**
     * 根据选球修改钱数
     */
    public void getMoney() {
        SevenColor sevenColor=new SevenColor();
        int money = 0;
        for (int i=0;i<listt.size();i++){
            List<int[]> listmoney = listt.get(i);
            Log.i("money",sevenColor.getNumb(listmoney).size()+"");
            money=money+sevenColor.getNumb(listmoney).size()*2;
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
                        List<int[]> listsz = listt.get(position);//获取7个位置球的集合
                        for (int i = 0; i < listsz.size(); i++) {//遍历每个位置（7）
                            editor.remove("sevencolorsize" + position + i);
                            for (int j = 0; j < listsz.get(i).length; j++) {//每个位置球的数组
                                editor.remove("sevencolor" + position + i + j);
                            }
                        }
                        editor.commit();
                        //清除最后一项在shared的缓存
                        List<int[]> listsz1 = listt.get(listt.size() - 1);//获取7个位置球的集合
                        for (int i = 0; i < listsz1.size(); i++) {//遍历每个位置（7）
                            editor.remove("sevencolorsize" + (listt.size() - 1) + i);
                            for (int j = 0; j < listsz1.get(i).length; j++) {//每个位置球的数组
                                editor.remove("sevencolor" + (listt.size() - 1) + i + j);
                            }
                        }
                        editor.commit();
                        //将删除项后面的项在shared中往前提一个位置
                        for (int k = 0; k < listt.size() - position - 1; k++) {
                            List<int[]> listsz2 = listt.get(position + k + 1);//获取7个位置球的集合
                            for (int i = 0; i < listsz2.size(); i++) {//遍历每个位置（7）
                                editor.putInt("sevencolorsize" + (position + k) + i, listsz2.get(i).length);
                                for (int j = 0; j < listsz2.get(i).length; j++) {//每个位置球的数组
                                    editor.putInt("sevencolor" + (position + k) + i + j, listsz2.get(i)[j]);
                                }
                            }
                        }
                        editor.commit();
                        Message m = new Message();
                        m.what = 1;
                        // 发送消息到Handler
                        handler.sendMessage(m);
                    }
                }.start();
            }
        });
        adapter = new ListGridviewAdapterSc(context, getDate(position));
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
