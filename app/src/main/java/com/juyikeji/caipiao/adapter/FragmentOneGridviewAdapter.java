package com.juyikeji.caipiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.bean.DateItem;

import java.util.List;

/**
 * Created by jyg on 2016/1/14 0014.
 */
public class FragmentOneGridviewAdapter extends BaseAdapter{

    private Context context;
    private List<DateItem> list;

    public FragmentOneGridviewAdapter(Context _context, List<DateItem> _list) {
        this.list = _list;
        this.context = _context;
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
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewHolder holder;
        if (convertView==null) {
            holder=new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.fragment_one_gridviewitem, null);
            holder.iv=(ImageView)convertView.findViewById(R.id.iv);
            holder.tv_type= (TextView) convertView.findViewById(R.id.tv_type);
            holder.tv_money= (TextView) convertView.findViewById(R.id.tv_money);
            holder.view= (TextView) convertView.findViewById(R.id.view);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.tv_type.setText(list.get(position).getType());
        holder.tv_money.setText(list.get(position).getMoney());
        holder.iv.setBackgroundResource(list.get(position).getIv());
        if (position%2==0){
            holder.view.setVisibility(View.VISIBLE);
        }else {

            holder.view.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView tv_type,tv_money,view;
    }
}
