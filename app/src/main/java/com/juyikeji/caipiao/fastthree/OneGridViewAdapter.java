package com.juyikeji.caipiao.fastthree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.juyikeji.caipiao.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/7 0007.
 */
public class OneGridViewAdapter extends BaseAdapter {
    private List<Map<String,String>> list;
    private Context context;

    int clickTemp=-1;

    public OneGridViewAdapter(List<Map<String, String>> list, Context context){
        this.list=list;
        this.context=context;
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
        ViewHolder holder;
        if (convertView==null) {
            holder=new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.one_gridview_item, null);
            holder.tv_pt= (TextView) convertView.findViewById(R.id.tv_pt);

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.tv_pt.setText(list.get(position).get("text"));

        if(clickTemp==position){
            holder.tv_pt.setBackgroundResource(R.mipmap.choose_yes);
        }else{
            holder.tv_pt.setBackgroundResource(R.color.white);
        }

        return convertView;
    }
    class ViewHolder{
        TextView tv_pt;
    }

    public void setSeclection(int position) {
        clickTemp = position;
        this.notifyDataSetChanged();
    }
}
