package com.juyikeji.caipiao.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juyikeji.caipiao.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
public class OneYongTanDuoGuanListGridviewAdapter extends BaseAdapter {
    private Context context;
    private List<String[]> list;
    private List<int[]> list1;
    private int bs;
    private int erlei, sanlei;

    public OneYongTanDuoGuanListGridviewAdapter(Context context, List<String[]> list, int bs, int erlei) {
        this.context = context;
        this.list = list;
        this.bs = bs;
        this.erlei = erlei;
    }

    public OneYongTanDuoGuanListGridviewAdapter(Context context, List<String[]> list, int bs, int erlei, int sanlei) {
        this.context = context;
        this.list = list;
        this.bs = bs;
        this.erlei = erlei;
        this.sanlei = sanlei;
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
            holder.type = (TextView) convertView.findViewById(R.id.type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (erlei == 1) {//任选
            if (bs == 0 || bs == 1 || bs == 3) {
                switch (list.get(position)[1]) {
                    case "0":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("自");
                        holder.type.setVisibility(View.GONE);
                        break;
                    case "1":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("仰");
                        holder.type.setVisibility(View.GONE);
                        break;
                    case "2":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("蛙");
                        holder.type.setVisibility(View.GONE);
                        break;
                    case "3":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("蝶");
                        holder.type.setVisibility(View.GONE);
                        break;
                }
            } else if (bs == 2) {
                switch (list.get(position)[1]) {
                    case "0":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("一");
                        holder.type.setVisibility(View.GONE);
                        break;
                    case "1":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("二");
                        holder.type.setVisibility(View.GONE);
                        break;
                }
            } else if (bs == 4) {
                switch (list.get(position)[1]) {
                    case "0":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("一");
                        holder.type.setVisibility(View.GONE);
                        break;
                    case "1":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("二");
                        holder.type.setVisibility(View.GONE);
                        break;
                    case "2":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("三");
                        holder.type.setVisibility(View.GONE);
                        break;
                }
            }
        } else if (erlei == 2) {//直选
            if (bs == 0 || bs == 1 || bs == 2) {
                switch (list.get(position)[1]) {
                    case "0":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("自");
                        holder.type.setVisibility(View.GONE);
                        break;
                    case "1":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("仰");
                        holder.type.setVisibility(View.GONE);
                        break;
                    case "2":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("蛙");
                        holder.type.setVisibility(View.GONE);
                        break;
                    case "3":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("蝶");
                        holder.type.setVisibility(View.GONE);
                        break;
                }
            }

        } else if (erlei == 3) {//组选
            if (bs == 0 || bs == 3 || bs == 7 || bs == 10) {
                switch (list.get(position)[1]) {
                    case "0":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("自");
                        holder.type.setVisibility(View.GONE);
                        break;
                    case "1":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("仰");
                        holder.type.setVisibility(View.GONE);
                        break;
                    case "2":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("蛙");
                        holder.type.setVisibility(View.GONE);
                        break;
                    case "3":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("蝶");
                        holder.type.setVisibility(View.GONE);
                        break;
                }
            } else if (bs == 1 || bs == 4 || bs == 8 || bs == 11) {
                switch (list.get(position)[1]) {
                    case "0":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.GONE);
                        holder.tvtext.setText("自");
                        holder.type.setVisibility(View.GONE);
                        break;
                }
            } else if (bs == 2 || bs == 5 || bs == 6 || bs == 9 || bs == 12 || bs == 13) {
                switch (list.get(position)[1]) {
                    case "0":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("胆");
                        holder.type.setVisibility(View.GONE);
                        break;
                    case "1":
                        holder.linout.setBackgroundResource(R.drawable.shape_06);
                        holder.tvnum.setText(list.get(position)[0]);
                        holder.tvtext.setVisibility(View.VISIBLE);
                        holder.tvtext.setText("拖");
                        holder.type.setVisibility(View.GONE);
                        break;
                }
            }
        } else if (erlei == 4) {
            switch (bs) {
                case 0:
                case 1:
                    switch (list.get(position)[1]) {
                        case "0":
                            holder.linout.setBackgroundResource(R.drawable.shape_06);
                            holder.tvnum.setText(list.get(position)[0]);
                            holder.tvtext.setVisibility(View.GONE);
                            holder.tvtext.setText("自");
                            holder.type.setVisibility(View.GONE);
                            break;
                    }
                    break;
                case 2:
                case 3:
                    switch (list.get(position)[1]) {
                        case "0":
                            holder.linout.setBackgroundResource(R.drawable.shape_06);
                            holder.tvnum.setText(list.get(position)[0]);
                            holder.tvtext.setVisibility(View.GONE);
                            holder.tvtext.setText("自");
                            break;
                        case "3":
                            holder.type.setVisibility(View.VISIBLE);
                            holder.type.setText(list.get(position)[0]);
                            break;
                    }
                    break;
            }
        } else if (erlei == 5) {
            switch (bs) {
                case 0:
                case 1:
                case 3:
                    switch (list.get(position)[1]) {
                        case "0":
                            holder.linout.setBackgroundResource(R.drawable.shape_06);
                            holder.tvnum.setText(list.get(position)[0]);
                            holder.tvtext.setVisibility(View.GONE);
                            holder.tvtext.setText("自");
                            holder.type.setVisibility(View.GONE);
                            break;
                    }
                    break;
                case 2:
                    if (sanlei == 0) {//选四不重全包
                        switch (list.get(position)[1]) {
                            case "0":
                                holder.linout.setBackgroundResource(R.drawable.shape_06);
                                holder.tvnum.setText(list.get(position)[0]);
                                holder.tvtext.setVisibility(View.GONE);
                                holder.tvtext.setText("自");
                                holder.type.setVisibility(View.GONE);
                                break;
                        }
                    } else if (sanlei == 1) {//选四一对全包
                        switch (list.get(position)[1]) {
                            case "0":
                                holder.linout.setBackgroundResource(R.drawable.shape_06);
                                holder.tvnum.setText(list.get(position)[0]);
                                holder.tvtext.setVisibility(View.VISIBLE);
                                holder.tvtext.setText("重");
                                holder.type.setVisibility(View.GONE);
                                break;
                            case "1":
                                holder.linout.setBackgroundResource(R.drawable.shape_06);
                                holder.tvnum.setText(list.get(position)[0]);
                                holder.tvtext.setVisibility(View.VISIBLE);
                                holder.tvtext.setText("不");
                                holder.type.setVisibility(View.GONE);
                                break;
                        }
                    } else if (sanlei == 2) {//选四两对全包
                        switch (list.get(position)[1]) {
                            case "0":
                                holder.linout.setBackgroundResource(R.drawable.shape_06);
                                holder.tvnum.setText(list.get(position)[0]);
                                holder.tvtext.setVisibility(View.GONE);
                                holder.tvtext.setText("自");
                                holder.type.setVisibility(View.GONE);
                                break;
                        }
                    } else if (sanlei == 3) {//选四三条全包
                        switch (list.get(position)[1]) {
                            case "0":
                                holder.linout.setBackgroundResource(R.drawable.shape_06);
                                holder.tvnum.setText(list.get(position)[0]);
                                holder.tvtext.setVisibility(View.VISIBLE);
                                holder.tvtext.setText("重");
                                holder.type.setVisibility(View.GONE);
                                break;
                            case "1":
                                holder.linout.setBackgroundResource(R.drawable.shape_06);
                                holder.tvnum.setText(list.get(position)[0]);
                                holder.tvtext.setVisibility(View.VISIBLE);
                                holder.tvtext.setText("不");
                                holder.type.setVisibility(View.GONE);
                                break;
                        }
                    }
                    break;
            }
        }

        return convertView;
    }

    class ViewHolder {
        LinearLayout linout;
        TextView tvnum, tvtext, type;
    }
}
