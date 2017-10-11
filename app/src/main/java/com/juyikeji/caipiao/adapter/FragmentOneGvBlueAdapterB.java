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
import java.util.Map;

/**
 * Created by Administrator on 2016/1/14 0014.
 */
public class FragmentOneGvBlueAdapterB extends BaseAdapter {

    private Context context;
    private List<Integer> list;

   public List<int[]> mselect;//选中项的标识
    public List<Integer> mmselect;//选中项的集合
    public int limit = 0;//限制选择数量 最多选6个

    int White = 0xffffffff;
    int Red = 0xFF303F9F;
    int Gray = 0xffdddddd;

    public FragmentOneGvBlueAdapterB(Context _context, List<Integer> _list,Map<String ,Object> map) {
        this.list = _list;
        this.context = _context;
        mselect= new ArrayList<int[]>();
        List<Integer> listred=(List<Integer>)map.get("blue");
        List<String> listredd=(List<String>)map.get("blued");
        for (int i = 1; i <= list.size(); i++) {
            int a[]=new int[2];
            a[0]=i;
            a[1]=0;
            for (int j=0;j<listred.size();j++){
                if (i==listred.get(j)){
                    a[0]=i-1;
                    a[1]=1;
                    limit++;
                }

            }
            for (int j=0;j<listredd.size();j++){
                if ((i+"").equals(listredd.get(j))){
                    a[0]=i-1;
                    a[1]=2;
                }
            }
            mselect.add(a);
        }
    }

    //初始化选中状态
    public void getMselect(){
        limit = 0;
        mselect= new ArrayList<int[]>();
        for (int i = 1; i <= list.size(); i++) {
            int a[] = {i, 0};
            mselect.add(a);
        }
        notifyDataSetChanged();
    }
    //机选方法
    public void changeNumber(){
        getMselect(); //初始化mselect
        int i=(int) (Math.random() * 16+1);
        int[] a={i-1,1};
            mselect.remove(i-1);
            mselect.add(i- 1, a);
        limit = 1;
        notifyDataSetChanged();
    }

    public void changeSelected(int positon) { //刷新方法
        limit = 0;
        if (mselect.get(positon)[1] == 0) {
            int[] a = {positon, 1};
            mselect.remove(positon);
            mselect.add(positon, a);
        } else {
            int[] a = {positon, 0};
            mselect.remove(positon);
            mselect.add(positon, a);
        }
        for (int i = 0; i < mselect.size(); i++) {
            if (mselect.get(i)[1] != 0) {
                limit++;
            }
        }
//        if (limit > 6) {
//            Toast.makeText(context, "红球最多选6个", Toast.LENGTH_SHORT).show();
//            int[] a = {positon, 0};
//            mselect.remove(positon);
//            mselect.add(positon, a);
//            return;
//        }
        notifyDataSetChanged();
    }

    //获取选中的篮球
    public List<Integer> getNumb(){
        mmselect=new ArrayList<Integer>();
        for (int i=0;i<mselect.size();i++){
            if (0!=mselect.get(i)[1]){
                mmselect.add(i+1);
            }
        }
        return mmselect;
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.fragment_one_lotterybet_gvitem, null);
            holder.linout = (LinearLayout) convertView.findViewById(R.id.linout);
            holder.tv_1 = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tv_2 = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_1.setText(list.get(position).toString());
        if (mselect.get(position)[0] == position && mselect.get(position)[1] == 1) {
            holder.linout.setBackgroundResource(R.drawable.shape_05);
            holder.tv_1.setTextColor(White);
            holder.tv_2.setVisibility(View.GONE);
        } else if (mselect.get(position)[1] == 2) {
            holder.linout.setBackgroundResource(R.drawable.shape_05);
            holder.tv_1.setTextColor(White);
            holder.tv_2.setVisibility(View.VISIBLE);
        } else {
            holder.linout.setBackgroundResource(R.drawable.shape_011);
            holder.tv_1.setTextColor(Red);
            holder.tv_2.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHolder {
        LinearLayout linout;
        TextView tv_1, tv_2;
    }
}
