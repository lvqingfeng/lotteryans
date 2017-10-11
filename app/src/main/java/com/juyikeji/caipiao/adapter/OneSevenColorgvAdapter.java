package com.juyikeji.caipiao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juyikeji.caipiao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyg on 2016/3/10 0010.
 */
public class OneSevenColorgvAdapter extends BaseAdapter {

    private Context context;
    private int[] ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private List<int[]> mselect;
    public List<Integer>  mmselect;//选中项的集合
    public int limit = 0;


    private int White = 0xffffffff;
    private int Red = 0xffd93636;

    public void showColor(int position) {
        limit = 0;
        if (mselect.get(position)[1] == 0) {
            int[] a = {position, 1};
            mselect.remove(position);
            mselect.add(position, a);
        } else {
            int[] a = {position, 0};
            mselect.remove(position);
            mselect.add(position, a);
        }
        for (int i = 0; i < mselect.size(); i++) {
            if (mselect.get(i)[1] != 0) {
                limit++;
            }
        }
        notifyDataSetChanged();
    }
    //机选方法
    public void changeNumber(){
        getMselect(); //初始化mselect
        int i=(int) (Math.random() * 10);
        int[] a={i,1};
        mselect.remove(i);
        mselect.add(i, a);
        limit = 1;
        notifyDataSetChanged();
    }

    //初始化选中状态
    public void getMselect() {
        limit = 0;
        mselect = new ArrayList<int[]>();
        for (int i = 1; i <= ints.length; i++) {
            int a[] = {i, 0};
            mselect.add(a);
        }
        notifyDataSetChanged();
    }

    //获取选中的彩球
    public List<Integer> getNumb(){
        mmselect=new ArrayList<Integer>();
        for (int i=0;i<mselect.size();i++){
            if (0!=mselect.get(i)[1]){
                mmselect.add(i);
            }
        }
        return mmselect;
    }

    public OneSevenColorgvAdapter(Context context,int[] b) {
        this.context = context;
        limit = 0;
        mselect = new ArrayList<int[]>();
        for (int i = 1; i <= ints.length; i++) {
            int a[] = new int[2];
            a[0]=i;
            a[1]=0;
            for (int j=0;j<b.length;j++){
                if ((i-1)==b[j]){
                    a[0]=i;
                    a[1]=1;
                    limit++;
                }
            }
            mselect.add(a);
        }
    }

    @Override
    public int getCount() {
        return ints.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return ints[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_one_sevencolor_gvitem, null);
            holder.linout = (LinearLayout) convertView.findViewById(R.id.linout);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_num.setText(ints[position] + "");
        if (mselect.get(position)[1] == 1) {
            holder.linout.setBackgroundResource(R.drawable.shape_06);
            holder.tv_num.setTextColor(White);
        } else {
            holder.linout.setBackgroundResource(R.drawable.shape_01);
            holder.tv_num.setTextColor(Red);
        }

        return convertView;
    }

    class ViewHolder {
        LinearLayout linout;
        TextView tv_num;
    }
}
