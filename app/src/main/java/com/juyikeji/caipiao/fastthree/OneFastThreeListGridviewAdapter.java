package com.juyikeji.caipiao.fastthree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juyikeji.caipiao.R;

import java.util.List;

/**
 * Created by Administrator on 2016/3/24 0024.
 */
public class OneFastThreeListGridviewAdapter extends BaseAdapter {
    private Context context;
    private List<String[]> list;
    private List<int[]> list1;
    private int bs;

    public OneFastThreeListGridviewAdapter(Context context, List<String[]> list, int bs) {
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
        if (bs == 0 || bs == 1 || bs == 3 || bs == 4 || bs == 5) {//和值//三同号//二同号复选
            switch (list.get(position)[1]) {
                case "0"://百位号码
                    holder.linout.setBackgroundResource(R.drawable.shape_06);
                    holder.tvnum.setText(list.get(position)[0]);
                    holder.tvtext.setVisibility(View.GONE);
                    holder.tvtext.setText("百");
                    break;
            }
        } else if (bs == 2) {//二同号单选
            switch (list.get(position)[1]) {
                case "0"://百位号码
                    holder.linout.setBackgroundResource(R.drawable.shape_06);
                    holder.tvnum.setText(list.get(position)[0]);
                    holder.tvtext.setVisibility(View.VISIBLE);
                    holder.tvtext.setText("相");
                    break;
                case "1"://十位号码
                    holder.linout.setBackgroundResource(R.drawable.shape_06);
                    holder.tvnum.setText(list.get(position)[0]);
                    holder.tvtext.setVisibility(View.VISIBLE);
                    holder.tvtext.setText("不");
                    break;
            }
        } else if (bs == 6 || bs == 7) {//三不同号胆拖//而不同胆拖
            switch (list.get(position)[1]) {
                case "0"://百位号码
                    holder.linout.setBackgroundResource(R.drawable.shape_06);
                    holder.tvnum.setText(list.get(position)[0]);
                    holder.tvtext.setVisibility(View.VISIBLE);
                    holder.tvtext.setText("胆");
                    break;
                case "1"://十位号码
                    holder.linout.setBackgroundResource(R.drawable.shape_06);
                    holder.tvnum.setText(list.get(position)[0]);
                    holder.tvtext.setVisibility(View.VISIBLE);
                    holder.tvtext.setText("拖");
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
