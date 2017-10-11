package com.juyikeji.caipiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.bean.Bill;

import java.util.List;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class MyWalletAdapter extends BaseAdapter{
    private Context context;
    private List<Bill> list;

    public MyWalletAdapter(Context context,List<Bill> list){
        this.context=context;
        this.list=list;
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
        Holder holder=null;
        if(convertView==null){
            holder=new Holder();

            convertView= LayoutInflater.from(context).inflate(R.layout.fragment_three_mywallet_item,null);
            holder.category= (TextView) convertView.findViewById(R.id.category);
            holder.money= (TextView) convertView.findViewById(R.id.money);
            holder.date= (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(holder);
        }else{
            holder=(Holder)convertView.getTag();
        }

        holder.category.setText(list.get(position).getCategory());
        holder.money.setText(list.get(position).getMoney());
        holder.date.setText(list.get(position).getDate());

        return convertView;
    }

    class Holder{
        private TextView category,money,date;
    }
}
