package com.juyikeji.caipiao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juyikeji.caipiao.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/14 0014.
 */
public class FragmentOneGvRedAdapterA extends BaseAdapter {

    private Context context;
    private List<Integer> list;

    public List<int[]> mselect ;//选中项标识
    /**
     * 普通选法
     */
    public List<Integer>  mmselect;//选中项的集合
    public int limit = 0;//限制选择数量 最少选6个
    /**
     * 胆码选法
     */
    public int limitd=0;//胆码的个数
    public String mmselectd="";//胆码选中项集合

    int White = 0xffffffff;
    int Red = 0xFFd93636;

    boolean aBoolean=true;

    public FragmentOneGvRedAdapterA(Context _context, List<Integer> _list,Map<String ,Object> map) {
        this.list = _list;
        this.context = _context;
        mselect= new ArrayList<int[]>();
        List<Integer> listred=(List<Integer>)map.get("red");

        List<String> listredd=(List<String>)map.get("redd");
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
                    Log.i("danmma","dddddd");
                    limitd++;
                }
                Log.i("danmma",listredd.get(j));
            }
            mselect.add(a);
        }

    }
    //初始化选中状态
    public void getMselect(){
        limit=0;
        limitd=0;
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
        int []test=testRndom33();
        for (int i=0;i<test.length;i++){
            int []a={test[i]-1,1};
            mselect.remove(test[i]-1);
            mselect.add(test[i]-1,a);
        }
        limit=6;
        notifyDataSetChanged();
    }
    //生成随机数的方法
    private int[] testRndom33(){
        int[] b = new int[6];
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) (Math.random() * 33+1);
            for (int j = 0; j <= i; j++) {

                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //多选的回调刷新方法
    public void changeSelected(int positon,boolean bool) {
        aBoolean=bool;

        changeSelectedd(positon);

        if (limit+limitd > 18) {
            Toast.makeText(context, "红球最多选18个", Toast.LENGTH_SHORT).show();
            limit--;
            if (limitd!=0){
                limitd--;
            }
            int[] a = {positon, 0};
            mselect.remove(positon);
            mselect.add(positon, a);
            return;
        }
        if (limitd>5&&bool){
            aBoolean=false;
            limitd=5;
            changeSelectedd(positon);
        }
        if (limitd<5&&bool){
            aBoolean=true;
        }
        notifyDataSetChanged();
    }
    private void changeSelectedd(int positon){
        if (aBoolean){
            if (mselect.get(positon)[1] == 0) {
                int[] a = {positon, 1};
                mselect.remove(positon);
                mselect.add(positon, a);
            } else if (mselect.get(positon)[1] == 1) {
                int[] a = {positon, 2};
                mselect.remove(positon);
                mselect.add(positon, a);
            } else {
                int[] a = {positon, 0};
                mselect.remove(positon);
                mselect.add(positon, a);
            }
            limit = 0;
            limitd=0;
            for (int i = 0; i < mselect.size(); i++) {
                switch (mselect.get(i)[1]){
                    case 1:
                        limit++;
                        break;
                    case 2:
                        limitd++;
                        break;

                }
            }
        }else {
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
            limit=limit-limitd;
        }
    }

    //获取选中的红球
    public List<Integer> getNumb(){
//        String[] s;
        mmselect=new ArrayList<Integer>();
        for (int i=0;i<mselect.size();i++){
            if (1==mselect.get(i)[1]){
                mmselect.add(i+1);
            }
        }
//        s=new String[mmselect.size()];
//        for (int i=0;i<mmselect.size();i++){
//            s[i]=mmselect.get(i)+"";
//        }
        return mmselect;
    }
    //获取选中的胆码红球
    public String getNumbd(){
        for (int i=0;i<mselect.size();i++){
            if (2==mselect.get(i)[1]){
                mmselectd=mmselectd+(i+1)+" ";
                Log.i("胆码",mmselectd);
            }
        }
        return mmselectd;
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
            holder.linout.setBackgroundResource(R.drawable.shape_06);
            holder.tv_1.setTextColor(White);
            holder.tv_2.setVisibility(View.GONE);
        } else if (mselect.get(position)[1] == 2) {
            holder.linout.setBackgroundResource(R.drawable.shape_06);
            holder.tv_1.setTextColor(White);
            holder.tv_2.setVisibility(View.VISIBLE);
        } else {
            holder.linout.setBackgroundResource(R.drawable.shape_01);
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
