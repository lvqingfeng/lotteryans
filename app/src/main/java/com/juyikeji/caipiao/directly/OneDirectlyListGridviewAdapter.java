package com.juyikeji.caipiao.directly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juyikeji.caipiao.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/24 0024.
 */
public class OneDirectlyListGridviewAdapter extends BaseAdapter {
    private Context context;
    private List<String[]> list;
    private List<int[]> list1;
    private int bs;

    public OneDirectlyListGridviewAdapter(Context context, List<String[]> list, int bs) {
        this.context = context;
        this.list = list;
        this.bs = bs;
    }

    /**
     * 让adapter中的所有item不可以点击
     */
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    /**
     * 下标为position 的item不可选中，不可点击
     */
    @Override
    public boolean isEnabled(int position) {
        return false;
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_one_lotterybet_gvitem, null);
            holder.linout = (LinearLayout) convertView.findViewById(R.id.linout);
            holder.tvnum = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tvtext = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (bs == 0) {//直选三
            switch (list.get(position)[1]) {
                case "0"://百位号码
                    holder.linout.setBackgroundResource(R.drawable.shape_06);
                    holder.tvnum.setText(list.get(position)[0]);
                    holder.tvtext.setVisibility(View.VISIBLE);
                    holder.tvtext.setText("百");
                    break;
                case "1"://十位号码
                    holder.linout.setBackgroundResource(R.drawable.shape_06);
                    holder.tvnum.setText(list.get(position)[0]);
                    holder.tvtext.setVisibility(View.VISIBLE);
                    holder.tvtext.setText("十");
                    break;
                case "2"://个位号码
                    holder.linout.setBackgroundResource(R.drawable.shape_06);
                    holder.tvnum.setText(list.get(position)[0]);
                    holder.tvtext.setVisibility(View.VISIBLE);
                    holder.tvtext.setText("个");
                    break;
            }
        } else if (bs == 1 || bs == 2 || bs == 3 || bs == 4) {//组三单式
            switch (list.get(position)[1]) {
                case "0":
                    holder.linout.setBackgroundResource(R.drawable.shape_06);
                    holder.tvnum.setText(list.get(position)[0]);
                    holder.tvtext.setVisibility(View.GONE);
                    break;
                case "1":
                    holder.linout.setBackgroundResource(R.drawable.shape_06);
                    holder.tvnum.setText(list.get(position)[0]);
                    holder.tvtext.setVisibility(View.GONE);
                    break;
                case "2":
                    holder.linout.setBackgroundResource(R.drawable.shape_06);
                    holder.tvnum.setText(list.get(position)[0]);
                    holder.tvtext.setVisibility(View.GONE);
                    break;
            }
        }

        return convertView;
    }

    class ViewHolder {
        LinearLayout linout;
        TextView tvnum, tvtext;
    }
}
