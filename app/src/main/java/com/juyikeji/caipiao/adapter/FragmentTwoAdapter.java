package com.juyikeji.caipiao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.bean.CpzlBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007.
 */
public class FragmentTwoAdapter extends BaseAdapter {

    private List<CpzlBean> list;
    private Context context;
    private List<String> listitem;
    private int bs;

    public FragmentTwoAdapter(List<CpzlBean> list,Context context,int bs){
        this.list=list;
        this.context=context;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null) {
            holder=new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_two_item, null);

            holder.iv_index=(ImageView)convertView.findViewById(R.id.iv_index);
            holder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_qi= (TextView) convertView.findViewById(R.id.tv_qi);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_hm= (TextView) convertView.findViewById(R.id.tv_hm);
            holder.tv_bs= (TextView) convertView.findViewById(R.id.tv_bs);
            holder.tv_je= (TextView) convertView.findViewById(R.id.tv_je);
            holder.tv_wf= (TextView) convertView.findViewById(R.id.tv_wf);
            holder.tv_zjje= (TextView) convertView.findViewById(R.id.tv_zjje);

            holder.tv_1=(TextView)convertView.findViewById(R.id.tv_1);
            holder.tv_2=(TextView)convertView.findViewById(R.id.tv_2);
            holder.tv_3=(TextView)convertView.findViewById(R.id.tv_3);
            holder.tv_4=(TextView)convertView.findViewById(R.id.tv_4);
            holder.tv_5=(TextView)convertView.findViewById(R.id.tv_5);
            holder.tv_6=(TextView)convertView.findViewById(R.id.tv_6);
            holder.tv_7=(TextView)convertView.findViewById(R.id.tv_7);

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

//        ImageLoader.getInstance().displayImage(list.get(position).getIv_index(),holder.iv_index);
        holder.iv_index.setImageResource(R.mipmap.index_1);

        holder.tv_title.setText(list.get(position).getTv_title());
        holder.tv_qi.setText(list.get(position).getTv_qi());
        holder.tv_time.setText(list.get(position).getTv_time());
        String tv_hm=list.get(position).getTv_hm();
        holder.tv_hm.setText(tv_hm);
        holder.tv_bs.setText(list.get(position).getTv_bs());
        holder.tv_je.setText(list.get(position).getTv_je());
        holder.tv_wf.setText(list.get(position).getTv_wf());
        holder.tv_zjje.setText(list.get(position).getTv_zjje());
        if (list.get(position).getTv_kjhm().equals("")){
            holder.tv_1.setVisibility(View.GONE);
            holder.tv_2.setVisibility(View.GONE);
            holder.tv_3.setVisibility(View.GONE);
            holder.tv_4.setVisibility(View.GONE);
            holder.tv_5.setVisibility(View.GONE);
            holder.tv_6.setVisibility(View.GONE);
            holder.tv_7.setVisibility(View.GONE);
        }else {
            holder.tv_1.setVisibility(View.VISIBLE);
            holder.tv_2.setVisibility(View.VISIBLE);
            holder.tv_3.setVisibility(View.VISIBLE);
            holder.tv_4.setVisibility(View.VISIBLE);
            holder.tv_5.setVisibility(View.VISIBLE);
            holder.tv_6.setVisibility(View.VISIBLE);
            holder.tv_7.setVisibility(View.VISIBLE);
            listitem = new ArrayList<String>();
            String s = list.get(position).getTv_kjhm();
            String ss = "";
            switch (bs) {
                case 1:
                    for (int i = 0; i < 7; i++) {
                        if (i != 6) {
                            ss = s.substring(0, s.indexOf(","));//截取每个胆码
                            listitem.add(ss);//根据选择次数存放
                            s = s.substring(s.indexOf(",") + 1);//将剩余胆码重置从新截取
                        } else {
                            listitem.add(s);
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 7; i++) {
                        if (i != 6) {
                            ss = s.substring(0, s.indexOf(","));//截取每个胆码
                            listitem.add(ss);//根据选择次数存放
                            s = s.substring(s.indexOf(",") + 1);//将剩余胆码重置从新截取
                        } else {
                            listitem.add(s);
                        }
                    }
                    break;
                case 4:
                    for (int i = 0; i < 7; i++) {
                        if (i != 6) {
                            ss = s.substring(0, s.indexOf(","));//截取每个胆码
                            listitem.add(ss);//根据选择次数存放
                            s = s.substring(s.indexOf(",") + 1);//将剩余胆码重置从新截取
                        } else {
                            listitem.add(s);
                        }
                    }
                    break;
                case 6:
                    for (int i = 0; i < 5; i++) {
                        if (i != 4) {
                            ss = s.substring(0, s.indexOf(","));//截取每个胆码
                            listitem.add(ss);//根据选择次数存放
                            s = s.substring(s.indexOf(",") + 1);//将剩余胆码重置从新截取
                        } else {
                            listitem.add(s);
                        }
                    }
                    break;
                case 8:
                    for (int i = 0; i < 7; i++) {
                        if (i != 6) {
                            ss = s.substring(0, s.indexOf(","));//截取每个胆码
                            listitem.add(ss);//根据选择次数存放
                            s = s.substring(s.indexOf(",") + 1);//将剩余胆码重置从新截取
                        } else {
                            listitem.add(s);
                        }
                    }
                    break;
            }
            holder.tv_1.setText(listitem.get(0));
            holder.tv_2.setText(listitem.get(1));
            holder.tv_3.setText(listitem.get(2));
            holder.tv_4.setText(listitem.get(3));
            holder.tv_5.setText(listitem.get(4));
            holder.tv_6.setText(listitem.get(5));
            holder.tv_7.setText(listitem.get(6));
        }
        ForegroundColorSpan blue = new ForegroundColorSpan(Color.BLUE);
        ForegroundColorSpan red = new ForegroundColorSpan(Color.RED);
        SpannableStringBuilder builder = new SpannableStringBuilder(holder.tv_hm.getText().toString());
        builder.setSpan(red, 0,tv_hm.length()-2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(blue, tv_hm.length()-2,tv_hm.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return convertView;
    }
    class ViewHolder{
        ImageView iv_index;
        TextView tv_title,tv_qi,tv_time,tv_hm,tv_bs,tv_je,tv_wf,tv_zjje;
        TextView tv_1,tv_2,tv_3,tv_4,tv_5,tv_6,tv_7;
    }
}
