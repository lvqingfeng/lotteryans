package com.juyikeji.caipiao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.utils.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class FragmentOneKJXXAdapter extends BaseAdapter {

    private Context context;
    private List<String[]> list;
    private FragmentOneKJXXitemAdapter adapter;
    private List<String> listitem;
    private int bs;

    public FragmentOneKJXXAdapter(Context context, List<String[]> list, int bs) {
        this.context = context;
        this.list = list;
        this.bs = bs;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        listitem = new ArrayList<String>();
        String s = list.get(position)[1];
        Log.i("ssss", s);
        String ss = "";
        switch (bs) {
            case 1:
                for (int i = 0; i < 7; i++) {
                    if (i != 6) {
                        ss = s.substring(0, s.indexOf(","));//截取每个胆码
                        listitem.add(ss);//根据选择次数存放
                        s = s.substring(s.indexOf(",") + 1);//将剩余胆码重置从新截取
                    }else {
                        listitem.add(s);
                    }
                }
                break;
            case 2:
                for (int i = 0; i < 7; i++) {
                    if (i != 6) {
                        ss = s.substring(0, s.indexOf(","));//截取每个胆码
                        listitem.add(ss);//根据选择次数存放
                        s = s.substring(s.indexOf(",") + 1);//将剩余胆码重置从新截取
                    } else {
                        listitem.add(s);
                    }
                }
                break;
            case 3:
                for (int i = 0; i < 4; i++) {
                    if (i != 3) {
                        ss = s.substring(0, s.indexOf(","));//截取每个胆码
                        listitem.add(ss);//根据选择次数存放
                        s = s.substring(s.indexOf(",") + 1);//将剩余胆码重置从新截取
                    } else {
                        listitem.add(s);
                    }
                }
                break;
            case 4:
                for (int i = 0; i < 7; i++) {
                    if (i != 6) {
                        ss = s.substring(0, s.indexOf(","));//截取每个胆码
                        listitem.add(ss);//根据选择次数存放
                        s = s.substring(s.indexOf(",") + 1);//将剩余胆码重置从新截取
                    } else {
                        listitem.add(s);
                    }
                }
                break;
            case 5:
                for (int i = 0; i < 3; i++) {
                    if (i != 2) {
                        ss = s.substring(0, s.indexOf(","));//截取每个胆码
                        listitem.add(ss);//根据选择次数存放
                        s = s.substring(s.indexOf(",") + 1);//将剩余胆码重置从新截取
                    } else {
                        listitem.add(s);
                    }
                }
                break;
            case 6:
                for (int i = 0; i < 5; i++) {
                    if (i != 4) {
                        ss = s.substring(0, s.indexOf(","));//截取每个胆码
                        listitem.add(ss);//根据选择次数存放
                        s = s.substring(s.indexOf(",") + 1);//将剩余胆码重置从新截取
                    } else {
                        listitem.add(s);
                    }
                }
                break;
            case 7:
                for (int i = 0; i < 3; i++) {
                    if (i != 2) {
                        ss = s.substring(0, s.indexOf(","));//截取每个胆码
                        listitem.add(ss);//根据选择次数存放
                        s = s.substring(s.indexOf(",") + 1);//将剩余胆码重置从新截取
                    } else {
                        listitem.add(s);
                    }
                }
                break;
            case 8:
                for (int i = 0; i < 7; i++) {
                    if (i != 6) {
                        ss = s.substring(0, s.indexOf(","));//截取每个胆码
                        listitem.add(ss);//根据选择次数存放
                        s = s.substring(s.indexOf(",") + 1);//将剩余胆码重置从新截取
                    } else {
                        listitem.add(s);
                    }
                }
                break;
            case 9:
                for (int i = 0; i < 3; i++) {
                    if (i != 2) {
                        ss = s.substring(0, s.indexOf(","));//截取每个胆码
                        listitem.add(ss);//根据选择次数存放
                        s = s.substring(s.indexOf(",") + 1);//将剩余胆码重置从新截取
                    } else {
                        listitem.add(s);
                    }
                }
                break;
            case 10:
                for (int i = 0; i < 5; i++) {
                    if (i != 4) {
                        ss = s.substring(0, s.indexOf(","));//截取每个胆码
                        listitem.add(ss);//根据选择次数存放
                        s = s.substring(s.indexOf(",") + 1);//将剩余胆码重置从新截取
                    } else {
                        listitem.add(s);
                    }
                }
                break;
        }
        adapter = new FragmentOneKJXXitemAdapter(context, listitem, bs);
        Log.i("gvitem", list.get(position)[0] + "a");
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_one_kjxxitem, null);
            holder.tv = (TextView) convertView.findViewById(R.id.tv);
            holder.gv = (MyGridView) convertView.findViewById(R.id.gv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.gv.setAdapter(adapter);
        holder.tv.setText(list.get(position)[0]);
        return convertView;
    }

    class ViewHolder {
        TextView tv;
        MyGridView gv;
    }
}
