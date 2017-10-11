package com.juyikeji.caipiao.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.activity.OneDaletou;
import com.juyikeji.caipiao.activity.OneLotteryBet;
import com.juyikeji.caipiao.activity.OneSevenColor;
import com.juyikeji.caipiao.activity.OneSevenHappy;
import com.juyikeji.caipiao.activity.OneTwentyTwoforFive;
import com.juyikeji.caipiao.activity.OneYongTanDuoGuan;
import com.juyikeji.caipiao.adapter.FragmentOneGridviewAdapter;
import com.juyikeji.caipiao.arrangefive.OneArrangeFive;
import com.juyikeji.caipiao.bean.DateItem;
import com.juyikeji.caipiao.directly.OneDirectly;
import com.juyikeji.caipiao.fastthree.OneFastThree;
import com.juyikeji.caipiao.threed.OneThreeD;

import java.util.ArrayList;
import java.util.List;

/**
 * 购彩中心
 */
public class FragmentOne extends Fragment {
    private View view;


    private GridView gv_one;
    private FragmentOneGridviewAdapter adapter;
    private List<DateItem> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one, container, false);
        initView();

        return view;
    }

    private void initView(){
        gv_one=(GridView)view.findViewById(R.id.gv_one);
        adapter=new FragmentOneGridviewAdapter(getActivity(),getDate());
        gv_one.setAdapter(adapter);
        gv_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setShared();
                switch (position){
                    case 0:
                        //双色球
                        startActivity(new Intent(getActivity(),OneLotteryBet.class));
                        break;
                    case 1:
                        //大乐透
                        startActivity(new Intent(getActivity(),OneDaletou.class));
                        break;
                    case 2:
                        //泳坛夺冠
                        startActivity(new Intent(getActivity(), OneYongTanDuoGuan.class));
                        break;
                    case 3:
                        //七星彩
                        startActivity(new Intent(getActivity(), OneSevenColor.class));
                        break;
                    case 4://排列三
                        startActivity(new Intent(getActivity(), OneDirectly.class));
                        break;
                    case 5:
                        //22选5
                        startActivity(new Intent(getActivity(), OneTwentyTwoforFive.class));
                        break;
                    case 6://3D
                        startActivity(new Intent(getActivity(), OneThreeD.class));
                        break;
                    case 7:
                        //七乐彩
                        startActivity(new Intent(getActivity(), OneSevenHappy.class));
                        break;
                    case 8://快3
                        startActivity(new Intent(getActivity(), OneFastThree.class));
                        break;
                    case 9://排列五
                        startActivity(new Intent(getActivity(), OneArrangeFive.class));
                        break;

                }
            }
        });
    }

    String [] tv_type={"双色球","大乐透","泳坛夺金","七星彩","排列三","22选5","3D","七乐彩","快三","排列五"};
    int[] iv={R.mipmap.index_1, R.mipmap.index_2, R.mipmap.index_3, R.mipmap.index_4, R.mipmap.index_5, R.mipmap.index_6,
            R.mipmap.index_7, R.mipmap.index_8, R.mipmap.index_9, R.mipmap.index_10};
    private List<DateItem> getDate(){
        list=new ArrayList<DateItem>();
        for (int i=0;i<tv_type.length;i++){
            DateItem date=new DateItem();
            date.setType(tv_type[i]);
            date.setMoney("[奖池超8亿]");
            date.setIv(iv[i]);
            list.add(date);
        }
        return list;
    }
    /**
     * 所选号码次数的标识
     */
    private void setShared(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("numb",getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("item",-1);
        editor.putInt("time", 0);
        editor.putInt("",0);//胆码为空的标识
        editor.commit();
    }
}
