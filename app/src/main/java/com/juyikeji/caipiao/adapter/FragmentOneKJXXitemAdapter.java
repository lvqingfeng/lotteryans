package com.juyikeji.caipiao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.juyikeji.caipiao.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class FragmentOneKJXXitemAdapter extends BaseAdapter{

    private Context context;
    private List<String> list;
    private int bs;

    public FragmentOneKJXXitemAdapter(Context context, List<String> list,int bs){
        this.context=context;
        this.list=list;
        this.bs=bs;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.fragment_one_kjxxitem_item,null);
            holder.tv=(TextView)convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tv.setText(list.get(position));
        if (position==5&&bs==2||position==6){
            holder.tv.setBackgroundResource(R.drawable.shape_05);
        }else {
            holder.tv.setBackgroundResource(R.drawable.shape_06);
        }
        return convertView;
    }

    class ViewHolder{
        TextView tv;
    }
}
