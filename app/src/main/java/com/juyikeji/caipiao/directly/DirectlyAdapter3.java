package com.juyikeji.caipiao.directly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.juyikeji.caipiao.R;

import java.util.List;

/**
 * Created by Administrator on 2016/3/10 0010.
 */
public class DirectlyAdapter3 extends BaseAdapter {
    //数据源
    List<Integer> list;
    Context context;
    //记录有多少个号码被选中
    public int i = 0;
    //用来记录被选择的号码
    private boolean isChice[];
    //未选中的号码的颜色
    private final static int white = 0xffffffff;
    //选中的号码的颜色
    private final static int colorAccent = 0xffe84c3d;

    public DirectlyAdapter3(List<Integer> list, Context context) {
        this.list = list;
        this.context = context;

        isChice = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            isChice[i] = false;
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.directly_gridview_item, null);

            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num2);

            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.tv_num.setText(list.get(position).toString());
        if (isChice[position] == true) {
            holder.tv_num.setBackgroundResource(R.drawable.shape_06);
            holder.tv_num.setTextColor(white);
        } else {
            holder.tv_num.setBackgroundResource(R.drawable.shape_01);
            holder.tv_num.setTextColor(colorAccent);
        }

        return convertView;
    }

    class Holder {
        TextView tv_num;
    }

    //接受被选择的号码的位置，并他是否被选中
    public void chiceState(int post,int po) {
        if (po == 0) {//直选三
            if (isChice[post]) {
                isChice[post] = false;
                i = i - 1;
            } else {
                isChice[post] = true;
                i = i + 1;
            }
        } else if (po == 1) {//组三复式
            if (isChice[post]) {
                isChice[post] = false;
//                i = i - 1;
            } else {
                isChice[post] = true;
                i = 1;
            }
        }
        this.notifyDataSetChanged();
    }
}
