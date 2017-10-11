package com.juyikeji.caipiao.adapter;

import android.content.Context;
import android.test.RenamingDelegatingContext;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.juyikeji.caipiao.R;

import java.util.List;
import java.util.Map;

/**
 * 选四全包
 */
public class OneXSqbAdapter extends BaseAdapter{
    private List<Map<String,String>> list;
    private Context context;

    private final static int white=0xffffffff;
    private final static int black=0xff000000;

    int clickTemp=-1;

    public OneXSqbAdapter(List<Map<String, String>> list, Context context){
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
            holder.tv_pt.setBackgroundResource(R.drawable.ifaccept);
            holder.tv_pt.setTextColor(white);
        }else{
            holder.tv_pt.setBackgroundResource(R.drawable.shape_02);
            holder.tv_pt.setTextColor(black);
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
