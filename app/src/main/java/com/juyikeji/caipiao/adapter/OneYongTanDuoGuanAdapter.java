package com.juyikeji.caipiao.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.juyikeji.caipiao.R;
import com.juyikeji.caipiao.activity.OneYongTanDuoGuan;
import com.juyikeji.caipiao.fastthree.OneFastThreeListGridviewAdapter;
import com.juyikeji.caipiao.utils.MyCombine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
public class OneYongTanDuoGuanAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> listt;
    private int bs, erlei, sanlei;
    private OneYongTanDuoGuanListGridviewAdapter adapter;
    List<String[]> list;
    List<int[]> list1;
    private TextView tv;
    private EditText edt;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public OneYongTanDuoGuanAdapter(Context context, List<Map<String, Object>> listt, int bs, TextView tv, EditText edt, int erlei) {
        this.context = context;
        this.listt = listt;
        this.bs = bs;
        this.erlei = erlei;
        this.tv = tv;
        this.edt = edt;
        sharedPreferences = context.getSharedPreferences("numb", context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public OneYongTanDuoGuanAdapter(Context context, List<Map<String, Object>> listt, int bs, TextView tv, EditText edt, int erlei, int sanlei) {
        this.context = context;
        this.listt = listt;
        this.bs = bs;
        this.erlei = erlei;
        this.tv = tv;
        this.sanlei = sanlei;
        this.edt = edt;
        sharedPreferences = context.getSharedPreferences("numb", context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public int getCount() {
        return listt.size();
    }

    @Override
    public Object getItem(int position) {
        return listt.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            int yangyong = 0;
            int wayong = 0;
            int dieyong = 0;
            List<String[]> listmoney = getDate(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                    case "1":
                        yangyong++;
                        break;
                    case "2":
                        wayong++;
                        break;
                    case "3":
                        dieyong++;
                        break;
                }
            }
            money = money + (ziyouyong + yangyong + wayong + dieyong) * 2;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney1() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            int yangyong = 0;
            int wayong = 0;
            int dieyong = 0;
            List<String[]> listmoney = getDate(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                    case "1":
                        yangyong++;
                        break;
                    case "2":
                        wayong++;
                        break;
                    case "3":
                        dieyong++;
                        break;
                }
            }
            money = money + (ziyouyong * (yangyong + wayong + dieyong) + yangyong * (wayong + dieyong) + wayong * dieyong) * 2;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney2() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            Map<String, Object> map = listt.get(i);
            int ziyouyong = 0;
            int yangyong = 0;
            List<Integer> ziyouyonglist = (List<Integer>) map.get("ziyouyong");
            List<Integer> yangyonglist = (List<Integer>) map.get("yangyong");
            for (int a = 0; a < ziyouyonglist.size(); a++) {
                for (int b = 0; b < yangyonglist.size(); b++) {
                    if (ziyouyonglist.get(a) == yangyonglist.get(b)) {
                        ziyouyong++;
                    } else {
                        yangyong++;
                    }
                }
            }
            money = money + (ziyouyong * 6 + yangyong * 12) * 2;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney3() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            int yangyong = 0;
            int wayong = 0;
            int dieyong = 0;
            List<String[]> listmoney = getDate(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                    case "1":
                        yangyong++;
                        break;
                    case "2":
                        wayong++;
                        break;
                    case "3":
                        dieyong++;
                        break;
                }
            }
            money = money + (ziyouyong * yangyong * wayong + ziyouyong * yangyong * dieyong
                    + yangyong * wayong * dieyong + ziyouyong * wayong * dieyong) * 2;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney4() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            Map<String, Object> map = listt.get(i);
            int ziyouyong = 0;
            int yangyong = 0;
            int wayong = 0;
            List<Integer> ziyouyonglist = (List<Integer>) map.get("ziyouyong");
            List<Integer> yangyonglist = (List<Integer>) map.get("yangyong");
            List<Integer> wayonglist = (List<Integer>) map.get("wayong");
            for (int a = 0; a < ziyouyonglist.size(); a++) {
                for (int b = 0; b < yangyonglist.size(); b++) {
                    for (int c = 0; c < wayonglist.size(); c++) {
                        if (ziyouyonglist.get(a) == yangyonglist.get(b) && ziyouyonglist.get(a) == wayonglist.get(c) && yangyonglist.get(b) == wayonglist.get(c)) {
                            ziyouyong = ziyouyong + 1;
                        } else if (ziyouyonglist.get(a) == yangyonglist.get(b) || ziyouyonglist.get(a) == wayonglist.get(c) || yangyonglist.get(b) == wayonglist.get(c)) {
                            yangyong = yangyong + 1;
                        } else if (ziyouyonglist.get(a) != yangyonglist.get(b) || ziyouyonglist.get(a) != wayonglist.get(c) || yangyonglist.get(b) != wayonglist.get(c)) {
                            wayong = wayong + 1;
                        }
                    }
                }
                money = money + 2 * (4 * ziyouyong + 12 * yangyong + 24 * wayong);
            }
            tv.setText(money + "");

            if ("".equals(edt.getText().toString())) {
                tv.setText(money + "");
            } else {
                int m = Integer.parseInt(edt.getText().toString().trim());
                tv.setText(money * m + "");
            }
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney5() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            int yangyong = 0;
            int wayong = 0;
            int dieyong = 0;
            List<String[]> listmoney = getDate(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                    case "1":
                        yangyong++;
                        break;
                    case "2":
                        wayong++;
                        break;
                    case "3":
                        dieyong++;
                        break;
                }
            }
            money = money + ziyouyong * yangyong * wayong * dieyong * 2;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney6() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            int yangyong = 0;
            int wayong = 0;
            int dieyong = 0;
            List<String[]> listmoney = getDate(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                    case "1":
                        yangyong++;
                        break;
                    case "2":
                        wayong++;
                        break;
                    case "3":
                        dieyong++;
                        break;
                }
            }
            money = money + ((ziyouyong + yangyong + wayong + dieyong) + (ziyouyong * (yangyong + wayong + dieyong)
                    + yangyong * (wayong + dieyong) + wayong * dieyong) + (ziyouyong * yangyong * wayong +
                    ziyouyong * yangyong * dieyong + ziyouyong * wayong * dieyong + yangyong * wayong * dieyong)
                    + (ziyouyong * yangyong * wayong * dieyong)) * 2;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney7() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            List<String[]> listmoney = getDate1(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                }
            }
            switch (ziyouyong) {
                case 5:
                    money = money + 10;
                    break;
                case 6:
                    money = money + 30;
                    break;
                case 7:
                    money = money + 70;
                    break;
                case 8:
                    money = money + 140;
                    break;
            }

        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney8() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            int yangyong = 0;
            List<String[]> listmoney = getDate2(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "1":
                        yangyong++;
                        break;
                }
            }
            money = money + yangyong * 2;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney9() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            int yangyong = 0;
            List<String[]> listmoney = getDate1(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                }
            }
            switch (ziyouyong) {
                case 3:
                    money = money + 6;
                    break;
                case 4:
                    money = money + 24;
                    break;
                case 5:
                    money = money + 60;
                    break;
                case 6:
                    money = money + 120;
                    break;
                case 7:
                    money = money + 210;
                    break;
                case 8:
                    money = money + 336;
                    break;
            }
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney12() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            int yangyong = 0;
            List<String[]> listmoney = getDate1(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                }
            }
            switch (ziyouyong) {
                case 3:
                    money = money + 6;
                    break;
                case 4:
                    money = money + 12;
                    break;
                case 5:
                    money = money + 20;
                    break;
                case 6:
                    money = money + 30;
                    break;
                case 7:
                    money = money + 42;
                    break;
                case 8:
                    money = money + 56;
                    break;
            }
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney13() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            int yangyong = 0;
            List<String[]> listmoney = getDate2(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                    case "1":
                        yangyong++;
                        break;
                }
            }
            money = money + yangyong * 2;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney14() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            int yangyong = 0;
            List<String[]> listmoney = getDate1(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                    case "1":
                        yangyong++;
                        break;
                }
            }
            money = money + ziyouyong * (ziyouyong - 1) * 2;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney15() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            int yangyong = 0;
            List<String[]> listmoney = getDate2(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                    case "1":
                        yangyong++;
                        break;
                }
            }
            money = money + yangyong * 2 * 2;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney16() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            int yangyong = 0;
            List<String[]> listmoney = getDate2(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                    case "1":
                        yangyong++;
                        break;
                }
            }
            money = money + yangyong * 2;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    public void getMoney17() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            List<String[]> listmoney = getDate1(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                }
            }
            switch (ziyouyong) {
                case 2:
                    money = money + 12;
                    break;
                case 3:
                    money = money + 36;
                    break;
                case 4:
                    money = money + 72;
                    break;
                case 5:
                    money = money + 120;
                    break;
            }
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    public void getMoney27() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            List<String[]> listmoney = getDate1(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                }
            }
            switch (ziyouyong) {
                case 2:
                    money = money + 12;
                    break;
                case 3:
                    money = money + 36;
                    break;
                case 4:
                    money = money + 72;
                    break;
                case 5:
                    money = money + 120;
                    break;
            }
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    public void getMoney19() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            List<String[]> listmoney = getDate1(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                }
            }
            switch (ziyouyong) {
                case 3:
                    money = money + 16;
                    break;
                case 4:
                    money = money + 64;
                    break;
                case 5:
                    money = money + 160;
                    break;
                case 6:
                    money = money + 320;
                    break;
                case 7:
                    money = money + 560;
                    break;
                case 8:
                    money = money + 896;
                    break;

            }
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    public void getMoney20() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            List<String[]> listmoney = getDate1(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                }
            }
            switch (ziyouyong) {
                case 2:
                    money = money + 72;
                    break;
                case 3:
                    money = money + 216;
                    break;
                case 4:
                    money = money + 432;
                    break;
                case 5:
                    money = money + 720;
                    break;
                case 6:
                    money = money + 1080;
                    break;
                case 7:
                    money = money + 1512;
                    break;
                case 8:
                    money = money + 2016;
                    break;

            }
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    public void getMoney22() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            List<String[]> listmoney = getDate1(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                }
            }
            money = money + 48;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    public void getMoney24() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            List<String[]> listmoney = getDate2(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                }
            }
            money = money + 24;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    public void getMoney25() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            List<String[]> listmoney = getDate2(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                }
            }
            money = money + 8;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    public void getMoney23() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            List<String[]> listmoney = getDate1(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                }
            }
            money = money + 12;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    public void getMoney21() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            List<String[]> listmoney = getDate1(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                }
            }
            money = money + 2 * 11 * ziyouyong;
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    public void getMoney18() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            List<String[]> listmoney = getDate1(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                }
            }
            switch (ziyouyong) {
                case 3:
                    money = money + 12;
                    break;
                case 4:
                    money = money + 48;
                    break;
                case 5:
                    money = money + 120;
                    break;

            }
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    public void getMoney26() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            List<String[]> listmoney = getDate1(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                }
            }
            switch (ziyouyong) {
                case 3:
                    money = money + 12;
                    break;
                case 4:
                    money = money + 48;
                    break;
                case 5:
                    money = money + 120;
                    break;
            }
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney10() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            int yangyong = 0;
            List<String[]> listmoney = getDate2(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                    case "1":
                        yangyong++;
                        break;
                }
            }
            if (ziyouyong == 1) {
                switch (yangyong) {
                    case 2:
                        money = money + 6;
                        break;
                    case 3:
                        money = money + 18;
                        break;
                    case 4:
                        money = money + 36;
                        break;
                    case 5:
                        money = money + 60;
                        break;
                    case 6:
                        money = money + 90;
                        break;
                    case 7:
                        money = money + 126;
                        break;
                }
            } else if (ziyouyong == 2) {
                switch (yangyong) {
                    case 1:
                        money = money + 6;
                        break;
                    case 2:
                        money = money + 12;
                        break;
                    case 3:
                        money = money + 18;
                        break;
                    case 4:
                        money = money + 24;
                        break;
                    case 5:
                        money = money + 30;
                        break;
                    case 6:
                        money = money + 36;
                        break;
                }
            }
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    /**
     * 根据选球修改钱数
     */
    public void getMoney11() {
        int money = 0;
        for (int i = 0; i < listt.size(); i++) {
            int ziyouyong = 0;
            int yangyong = 0;
            List<String[]> listmoney = getDate2(i);
            for (int j = 0; j < listmoney.size(); j++) {
                switch (listmoney.get(j)[1]) {
                    case "0":
                        ziyouyong++;
                        break;
                    case "1":
                        yangyong++;
                        break;
                }
            }
            if (ziyouyong == 1) {
                switch (yangyong) {
                    case 3:
                        money = money + 6;
                        break;
                    case 4:
                        money = money + 12;
                        break;
                    case 5:
                        money = money + 20;
                        break;
                    case 6:
                        money = money + 30;
                        break;
                    case 7:
                        money = money + 42;
                        break;
                }
            }
        }
        tv.setText(money + "");

        if ("".equals(edt.getText().toString())) {
            tv.setText(money + "");
        } else {
            int m = Integer.parseInt(edt.getText().toString().trim());
            tv.setText(money * m + "");
        }
    }

    //机选方法
    public void changeNumber() {
        int time = sharedPreferences.getInt("time", 0);

        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        List<Integer> yangyonglist = new ArrayList<Integer>();
        List<Integer> wayonglist = new ArrayList<Integer>();
        List<Integer> dieyonglist = new ArrayList<Integer>();
        test = testRndom();

        ziyouyonglist.add(test[0]);
        yangyonglist.add(test[1]);
        wayonglist.add(test[2]);
        dieyonglist.add(test[3]);

        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        editor.putInt("ziyouyong" + listt.size() + 0, ziyouyonglist.get(0));

        editor.putInt("yangyongsize" + listt.size(), yangyonglist.size());
        editor.putInt("yangyong" + listt.size() + 0, yangyonglist.get(0));

        editor.putInt("wayongsize" + listt.size(), wayonglist.size());
        editor.putInt("wayong" + listt.size() + 0, wayonglist.get(0));

        editor.putInt("dieyongsize" + listt.size(), dieyonglist.size());
        editor.putInt("dieyong" + listt.size() + 0, dieyonglist.get(0));

        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        map.put("yangyong", yangyonglist);
        map.put("wayong", wayonglist);
        map.put("dieyong", dieyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //生成随机数的方法
    private int[] testRndom() {
        int[] b = new int[4];
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
        }
        return b;
    }

    //机选方法
    public void changeNumber3() {
        int time = sharedPreferences.getInt("time", 0);

        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        List<Integer> yangyonglist = new ArrayList<Integer>();
        List<Integer> wayonglist = new ArrayList<Integer>();
        List<Integer> dieyonglist = new ArrayList<Integer>();
        test = testRndom3();

        ziyouyonglist.add(test[0]);
        yangyonglist.add(test[1]);
        wayonglist.add(test[2]);
        dieyonglist.add(test[3]);

        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        editor.putInt("ziyouyong" + listt.size() + 0, ziyouyonglist.get(0));

        editor.putInt("yangyongsize" + listt.size(), yangyonglist.size());
        editor.putInt("yangyong" + listt.size() + 0, yangyonglist.get(0));

        editor.putInt("wayongsize" + listt.size(), wayonglist.size());
        editor.putInt("wayong" + listt.size() + 0, wayonglist.get(0));

        editor.putInt("dieyongsize" + listt.size(), dieyonglist.size());
        editor.putInt("dieyong" + listt.size() + 0, dieyonglist.get(0));

        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        map.put("yangyong", yangyonglist);
        map.put("wayong", wayonglist);
        map.put("dieyong", dieyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //生成随机数的方法
    private int[] testRndom3() {
        int[] b = new int[4];
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //机选方法
    public void changeNumber4() {
        int time = sharedPreferences.getInt("time", 0);

        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();

        test = testRndom4();
        for (int i = 0; i < test.length; i++) {
            ziyouyonglist.add(test[i]);
        }
        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        for (int i = 0; i < ziyouyonglist.size(); i++) {
            editor.putInt("ziyouyong" + listt.size() + i, ziyouyonglist.get(i));
        }
        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //生成随机数的方法
    private int[] testRndom4() {
        int[] b = new int[5];
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //生成随机数的方法
    private int[] testRndom5() {
        int[] b = new int[4];
        for (int i = 0; i < 3; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
            b[3] = b[0];
        }

        return b;
    }

    //生成随机数的方法
    private int[] testRndom6() {
        int[] b = new int[3];
        for (int i = 0; i < 3; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }

        return b;
    }

    //生成随机数的方法
    private int[] testRndom7() {
        int[] b = new int[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }

        return b;
    }

    //生成随机数的方法
    private int[] testRndom8() {
        int[] b = new int[4];
        for (int i = 0; i < 2; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
            b[2] = b[0];
            b[3] = b[1];
        }

        return b;
    }

    //生成随机数的方法
    private int[] testRndom9() {
        int[] b = new int[3];
        for (int i = 0; i < 3; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }

        return b;
    }

    //生成随机数的方法
    private int[] testRndom11() {
        int[] b = new int[4];
        for (int i = 0; i < 2; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
            b[2] = b[1];
            b[3] = b[1];
        }
        return b;
    }

    //生成随机数的方法
    private int[] testRndom12() {
        int[] b = new int[2];
        for (int i = 0; i < 2; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //生成随机数的方法
    private int[] testRndom13() {
        int[] b = new int[1];
        for (int i = 0; i < 1; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //生成随机数的方法
    private int[] testRndom14() {
        int[] b = new int[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //生成随机数的方法
    private int[] testRndom15() {
        int[] b = new int[3];
        for (int i = 0; i < 3; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //生成随机数的方法
    private int[] testRndom16() {
        int[] b = new int[2];
        for (int i = 0; i < 2; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
            for (int j = 0; j <= i; j++) {
                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //机选方法
    public void changeNumber5() {
        int time = sharedPreferences.getInt("time", 0);
        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        List<Integer> yangyonglist = new ArrayList<Integer>();
        test = testRndom4();
        ziyouyonglist.add(test[0]);
        for (int i = 1; i < test.length; i++) {
            yangyonglist.add(test[i]);
        }
        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        for (int i = 0; i < ziyouyonglist.size(); i++) {
            editor.putInt("ziyouyong" + listt.size() + i, ziyouyonglist.get(i));
        }
        editor.putInt("yangyongsize" + listt.size(), yangyonglist.size());
        for (int i = 0; i < yangyonglist.size(); i++) {
            editor.putInt("yangyong" + listt.size() + i, yangyonglist.get(i));
        }
        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        map.put("yangyong", yangyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //机选方法
    public void changeNumber6() {
        int time = sharedPreferences.getInt("time", 0);

        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        List<Integer> yangyonglist = new ArrayList<Integer>();
        List<Integer> wayonglist = new ArrayList<Integer>();
        List<Integer> dieyonglist = new ArrayList<Integer>();
        test = testRndom5();

        ziyouyonglist.add(test[0]);
        yangyonglist.add(test[1]);
        wayonglist.add(test[2]);
        dieyonglist.add(test[3]);

        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        editor.putInt("ziyouyong" + listt.size() + 0, ziyouyonglist.get(0));

        editor.putInt("yangyongsize" + listt.size(), yangyonglist.size());
        editor.putInt("yangyong" + listt.size() + 0, yangyonglist.get(0));

        editor.putInt("wayongsize" + listt.size(), wayonglist.size());
        editor.putInt("wayong" + listt.size() + 0, wayonglist.get(0));

        editor.putInt("dieyongsize" + listt.size(), dieyonglist.size());
        editor.putInt("dieyong" + listt.size() + 0, dieyonglist.get(0));

        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        map.put("yangyong", yangyonglist);
        map.put("wayong", wayonglist);
        map.put("dieyong", dieyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //机选方法
    public void changeNumber7() {
        int time = sharedPreferences.getInt("time", 0);

        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        test = testRndom6();
        for (int i = 0; i < test.length; i++) {
            ziyouyonglist.add(test[i]);
        }

        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        for (int i = 0; i < ziyouyonglist.size(); i++) {
            editor.putInt("ziyouyong" + listt.size() + i, ziyouyonglist.get(i));
        }
        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //机选方法
    public void changeNumber8() {
        int time = sharedPreferences.getInt("time", 0);

        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        List<Integer> yangyonglist = new ArrayList<Integer>();
        test = testRndom7();
        ziyouyonglist.add(test[0]);
        for (int i = 1; i < test.length; i++) {
            yangyonglist.add(test[i]);
        }

        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        for (int i = 0; i < ziyouyonglist.size(); i++) {
            editor.putInt("ziyouyong" + listt.size() + i, ziyouyonglist.get(i));
        }
        editor.putInt("yangyongsize" + listt.size(), yangyonglist.size());
        for (int i = 0; i < yangyonglist.size(); i++) {
            editor.putInt("yangyong" + listt.size() + i, yangyonglist.get(i));
        }
        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        map.put("yangyong", yangyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //机选方法
    public void changeNumber9() {
        int time = sharedPreferences.getInt("time", 0);

        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        List<Integer> yangyonglist = new ArrayList<Integer>();
        List<Integer> wayonglist = new ArrayList<Integer>();
        List<Integer> dieyonglist = new ArrayList<Integer>();
        test = testRndom8();
        ziyouyonglist.add(test[0]);
        yangyonglist.add(test[1]);
        wayonglist.add(test[2]);
        dieyonglist.add(test[3]);

        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        for (int i = 0; i < ziyouyonglist.size(); i++) {
            editor.putInt("ziyouyong" + listt.size() + i, ziyouyonglist.get(i));
        }
        editor.putInt("yangyongsize" + listt.size(), yangyonglist.size());
        for (int i = 0; i < yangyonglist.size(); i++) {
            editor.putInt("yangyong" + listt.size() + i, yangyonglist.get(i));
        }
        editor.putInt("wayongsize" + listt.size(), wayonglist.size());
        for (int i = 0; i < wayonglist.size(); i++) {
            editor.putInt("wayong" + listt.size() + i, wayonglist.get(i));
        }
        editor.putInt("dieyongsize" + listt.size(), dieyonglist.size());
        for (int i = 0; i < dieyonglist.size(); i++) {
            editor.putInt("dieyong" + listt.size() + i, dieyonglist.get(i));
        }
        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        map.put("yangyong", yangyonglist);
        map.put("wayong", wayonglist);
        map.put("dieyong", dieyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //机选方法
    public void changeNumber11() {
        int time = sharedPreferences.getInt("time", 0);

        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        List<Integer> yangyonglist = new ArrayList<Integer>();
        List<Integer> wayonglist = new ArrayList<Integer>();
        List<Integer> dieyonglist = new ArrayList<Integer>();
        test = testRndom11();
        ziyouyonglist.add(test[0]);
        yangyonglist.add(test[1]);
        wayonglist.add(test[2]);
        dieyonglist.add(test[3]);

        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        for (int i = 0; i < ziyouyonglist.size(); i++) {
            editor.putInt("ziyouyong" + listt.size() + i, ziyouyonglist.get(i));
        }
        editor.putInt("yangyongsize" + listt.size(), yangyonglist.size());
        for (int i = 0; i < yangyonglist.size(); i++) {
            editor.putInt("yangyong" + listt.size() + i, yangyonglist.get(i));
        }
        editor.putInt("wayongsize" + listt.size(), wayonglist.size());
        for (int i = 0; i < wayonglist.size(); i++) {
            editor.putInt("wayong" + listt.size() + i, wayonglist.get(i));
        }
        editor.putInt("dieyongsize" + listt.size(), dieyonglist.size());
        for (int i = 0; i < dieyonglist.size(); i++) {
            editor.putInt("dieyong" + listt.size() + i, dieyonglist.get(i));
        }
        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        map.put("yangyong", yangyonglist);
        map.put("wayong", wayonglist);
        map.put("dieyong", dieyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //机选方法
    public void changeNumber12() {
        int time = sharedPreferences.getInt("time", 0);

        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        test = testRndom12();
        for (int i = 0; i < test.length; i++) {
            ziyouyonglist.add(test[i]);
        }


        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        for (int i = 0; i < ziyouyonglist.size(); i++) {
            editor.putInt("ziyouyong" + listt.size() + i, ziyouyonglist.get(i));
        }
        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //机选方法
    public void changeNumber13() {
        int time = sharedPreferences.getInt("time", 0);

        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        test = testRndom13();
        for (int i = 0; i < test.length; i++) {
            ziyouyonglist.add(test[i]);
        }


        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        for (int i = 0; i < ziyouyonglist.size(); i++) {
            editor.putInt("ziyouyong" + listt.size() + i, ziyouyonglist.get(i));
        }
        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //机选方法
    public void changeNumber14() {
        int time = sharedPreferences.getInt("time", 0);

        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        test = testRndom14();
        for (int i = 0; i < test.length; i++) {
            ziyouyonglist.add(test[i]);
        }


        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        for (int i = 0; i < ziyouyonglist.size(); i++) {
            editor.putInt("ziyouyong" + listt.size() + i, ziyouyonglist.get(i));
        }
        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //机选方法
    public void changeNumber15() {
        int time = sharedPreferences.getInt("time", 0);
        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        List<Integer> yangyonglist = new ArrayList<Integer>();
        test = testRndom15();
        ziyouyonglist.add(test[0]);
        for (int i = 1; i < test.length; i++) {
            yangyonglist.add(test[i]);
        }

        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        for (int i = 0; i < ziyouyonglist.size(); i++) {
            editor.putInt("ziyouyong" + listt.size() + i, ziyouyonglist.get(i));
        }
        editor.putInt("yangyongsize" + listt.size(), yangyonglist.size());
        for (int i = 0; i < yangyonglist.size(); i++) {
            editor.putInt("yangyong" + listt.size() + i, yangyonglist.get(i));
        }
        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        map.put("yangyong", yangyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //机选方法
    public void changeNumber16() {
        int time = sharedPreferences.getInt("time", 0);
        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        List<Integer> yangyonglist = new ArrayList<Integer>();
        test = testRndom16();
        ziyouyonglist.add(test[0]);
        yangyonglist.add(test[1]);

        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        for (int i = 0; i < ziyouyonglist.size(); i++) {
            editor.putInt("ziyouyong" + listt.size() + i, ziyouyonglist.get(i));
        }
        editor.putInt("yangyongsize" + listt.size(), yangyonglist.size());
        for (int i = 0; i < ziyouyonglist.size(); i++) {
            editor.putInt("yangyong" + listt.size() + i, yangyonglist.get(i));
        }
        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        map.put("yangyong", yangyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //机选方法
    public void changeNumber10() {
        int time = sharedPreferences.getInt("time", 0);

        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        List<Integer> yangyonglist = new ArrayList<Integer>();
        test = testRndom9();
        ziyouyonglist.add(test[0]);
        for (int i = 1; i < test.length; i++) {
            yangyonglist.add(test[i]);
        }

        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        for (int i = 0; i < ziyouyonglist.size(); i++) {
            editor.putInt("ziyouyong" + listt.size() + i, ziyouyonglist.get(i));
        }
        editor.putInt("yangyongsize" + listt.size(), yangyonglist.size());
        for (int i = 0; i < yangyonglist.size(); i++) {
            editor.putInt("yangyong" + listt.size() + i, yangyonglist.get(i));
        }
        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        map.put("yangyong", yangyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //机选方法
    public void changeNumber1() {
        int time = sharedPreferences.getInt("time", 0);

        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        List<Integer> yangyonglist = new ArrayList<Integer>();
        test = testRndom1();

        ziyouyonglist.add(test[0]);
        yangyonglist.add(test[1]);

        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        editor.putInt("ziyouyong" + listt.size() + 0, ziyouyonglist.get(0));

        editor.putInt("yangyongsize" + listt.size(), yangyonglist.size());
        editor.putInt("yangyong" + listt.size() + 0, yangyonglist.get(0));

        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        map.put("yangyong", yangyonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //生成随机数的方法
    private int[] testRndom1() {
        int[] b = new int[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
        }
        return b;
    }

    //机选方法
    public void changeNumber2() {
        int time = sharedPreferences.getInt("time", 0);

        int[] test;
        List<Integer> ziyouyonglist = new ArrayList<Integer>();
        List<Integer> yangyonglist = new ArrayList<Integer>();
        List<Integer> wayonglist = new ArrayList<Integer>();
        test = testRndom2();

        ziyouyonglist.add(test[0]);
        yangyonglist.add(test[1]);
        wayonglist.add(test[2]);

        editor.putInt("ziyouyongsize" + listt.size(), ziyouyonglist.size());
        editor.putInt("ziyouyong" + listt.size() + 0, ziyouyonglist.get(0));

        editor.putInt("yangyongsize" + listt.size(), yangyonglist.size());
        editor.putInt("yangyong" + listt.size() + 0, yangyonglist.get(0));

        editor.putInt("wayongsize" + listt.size(), wayonglist.size());
        editor.putInt("wayong" + listt.size() + 0, wayonglist.get(0));

        editor.putInt("time", time + 1);
        editor.commit();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ziyouyong", ziyouyonglist);
        map.put("yangyong", yangyonglist);
        map.put("wayong", wayonglist);
        listt.add(map);
        notifyDataSetChanged();
    }

    //生成随机数的方法
    private int[] testRndom2() {
        int[] b = new int[3];
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) ((Math.random() * 8) + 1);
        }
        return b;
    }

    //给gridview的容器赋值
    private List<String[]> getDate(int position) {
        Map<String, Object> map = listt.get(position);
        list = new ArrayList<String[]>();
        List<Integer> ziyouyonglist = (List<Integer>) map.get("ziyouyong");
        List<Integer> yangyonglist = (List<Integer>) map.get("yangyong");
        List<Integer> wayonglist = (List<Integer>) map.get("wayong");
        List<Integer> dieyonglist = (List<Integer>) map.get("dieyong");
        String[] s;
        try {
            if (ziyouyonglist != null) {
                for (int i = 0; i < ziyouyonglist.size(); i++) {
                    s = new String[2];
                    s[0] = ziyouyonglist.get(i) + "";
                    s[1] = "0";
                    list.add(s);
                }
            }
            if (yangyonglist != null) {
                for (int i = 0; i < yangyonglist.size(); i++) {
                    s = new String[2];
                    s[0] = yangyonglist.get(i) + "";
                    s[1] = "1";
                    list.add(s);
                }
            }
            if (wayonglist != null) {
                for (int i = 0; i < wayonglist.size(); i++) {
                    s = new String[2];
                    s[0] = wayonglist.get(i) + "";
                    s[1] = "2";
                    list.add(s);
                }
            }
            if (dieyonglist != null) {
                for (int i = 0; i < dieyonglist.size(); i++) {
                    s = new String[2];
                    s[0] = dieyonglist.get(i) + "";
                    s[1] = "3";
                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //给gridview的容器赋值
    private List<String[]> getDate1(int position) {
        Map<String, Object> map = listt.get(position);
        list = new ArrayList<String[]>();
        List<Integer> ziyouyonglist = (List<Integer>) map.get("ziyouyong");
        String[] s;
        try {
            for (int i = 0; i < ziyouyonglist.size(); i++) {
                s = new String[2];
                s[0] = ziyouyonglist.get(i) + "";
                s[1] = "0";
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //给gridview的容器赋值
    private List<String[]> getDate3(int position) {
        Map<String, Object> map = listt.get(position);
        list = new ArrayList<String[]>();

        List<Integer> ziyouyonglist = (List<Integer>) map.get("ziyouyong");
        List<String> typelist = (List<String>) map.get("type");
        String[] s;
        try {
            for (int i = 0; i < typelist.size(); i++) {
                s = new String[2];
                s[0] = typelist.get(i) + "";
                s[1] = "3";
                list.add(s);
            }
            for (int i = 0; i < ziyouyonglist.size(); i++) {
                s = new String[2];
                s[0] = ziyouyonglist.get(i) + "";
                s[1] = "0";
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //给gridview的容器赋值
    private List<String[]> getDate2(int position) {
        Map<String, Object> map = listt.get(position);
        list = new ArrayList<String[]>();
        List<Integer> ziyouyonglist = (List<Integer>) map.get("ziyouyong");
        List<Integer> yangyonglist = (List<Integer>) map.get("yangyong");
        String[] s;
        try {
            for (int i = 0; i < ziyouyonglist.size(); i++) {
                s = new String[2];
                s[0] = ziyouyonglist.get(i) + "";
                s[1] = "0";
                list.add(s);
            }
            for (int i = 0; i < yangyonglist.size(); i++) {
                s = new String[2];
                s[0] = yangyonglist.get(i) + "";
                s[1] = "1";
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_one_lotterybetpay_item1, null);
            holder.btn_clear = (Button) convertView.findViewById(R.id.btn_clear);
            holder.btn_clear.setTag(position);
            holder.gv = (GridView) convertView.findViewById(R.id.gv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (erlei == 1) {//任选
            switch (bs) {
                case 0://
                    getMoney();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate(position), bs, erlei);
                    break;
                case 1:
                    getMoney1();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate(position), bs, erlei);
                    break;
                case 2:
                    getMoney2();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate(position), bs, erlei);
                    break;
                case 3:
                    getMoney3();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate(position), bs, erlei);
                    break;
                case 4:
                    getMoney4();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate(position), bs, erlei);
                    break;
            }
        } else if (erlei == 2) {//直选
            switch (bs) {
                case 0://
                    getMoney5();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate(position), bs, erlei);
                    break;
                case 1:
                    getMoney5();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate(position), bs, erlei);
                    break;
                case 2:
                    getMoney6();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate(position), bs, erlei);
                    break;
            }
        } else if (erlei == 3) {//组选
            switch (bs) {
                case 0://
                case 3:
                case 7:
                case 10:
                    getMoney5();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate(position), bs, erlei);
                    break;
                case 1:
                    getMoney7();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate1(position), bs, erlei);
                    break;
                case 2:
                    getMoney8();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate2(position), bs, erlei);
                    break;
                case 4:
                    getMoney9();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate1(position), bs, erlei);
                    break;
                case 5:
                    getMoney10();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate2(position), bs, erlei);
                    break;
                case 6:
                    getMoney11();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate2(position), bs, erlei);
                    break;
                case 8:
                    getMoney12();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate1(position), bs, erlei);
                    break;
                case 9:
                    getMoney13();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate2(position), bs, erlei);
                    break;
                case 11:
                    getMoney14();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate1(position), bs, erlei);
                    break;
                case 12:
                    getMoney15();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate2(position), bs, erlei);
                    break;
                case 13:
                    getMoney16();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate2(position), bs, erlei);
                    break;
            }

        } else if (erlei == 4) {
            switch (bs) {
                case 0:
                    getMoney17();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate1(position), bs, erlei);
                    break;
                case 2:
                    getMoney17();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate3(position), bs, erlei);
                    break;
                case 1:
                    getMoney18();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate1(position), bs, erlei);
                    break;
                case 3:
                    getMoney18();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate3(position), bs, erlei);
                    break;
            }
        } else if (erlei == 5) {
            switch (bs) {
                case 0:
                    getMoney19();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate1(position), bs, erlei);
                    break;
                case 1:
                    getMoney20();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate1(position), bs, erlei);
                    break;
                case 2:
                    if (sanlei == 0) {//选四不重全包
                        getMoney22();
                        adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate1(position), bs, erlei, sanlei);
                    } else if (sanlei == 1) {//选四一对全包
                        getMoney24();
                        adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate2(position), bs, erlei, sanlei);
                    } else if (sanlei == 2) {//选四两对全包
                        getMoney23();
                        adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate1(position), bs, erlei, sanlei);
                    } else if (sanlei == 3) {//选四三条全包
                        getMoney25();
                        adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate2(position), bs, erlei, sanlei);
                    }
                    break;
                case 3:
                    getMoney21();
                    adapter = new OneYongTanDuoGuanListGridviewAdapter(context, getDate1(position), bs, erlei);
                    break;
            }
        }

        holder.btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("numb", context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                final int time = sharedPreferences.getInt("time", 0);
                final Handler handler = new Handler() {
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == 1) {
                            listt.remove(position);
                            if (listt.size() == 0) {
                                tv.setText("0");
                            }
                            notifyDataSetChanged();
                        }
                    }
                };
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //将shared中选球的次数减去一位
                        editor.putInt("time", time - 1);
                        //清除shared中本项信息
                        for (int i = 0; i < getDate(position).size(); i++) {
                            Map<String, Object> map1 = listt.get(position);
                            List<Integer> ziyouyonglist1 = (List<Integer>) map1.get("ziyouyong");//
                            List<Integer> yangyonglist1 = (List<Integer>) map1.get("yangyong");//
                            List<Integer> wayonglist1 = (List<Integer>) map1.get("wayong");//
                            List<Integer> dieyonglist1 = (List<Integer>) map1.get("dieyong");//
                            for (int k = 0; k < getDate(position).size(); k++) {
                                switch (getDate(position).get(k)[1]) {
                                    case "0":
                                        editor.remove("ziyouyongsize" + position);
                                        for (int j = 0; j < ziyouyonglist1.size(); j++) {
                                            editor.remove("ziyouyong" + position + j);
                                        }
                                        break;
                                    case "1":
                                        editor.remove("yangyongsize" + position);
                                        for (int j = 0; j < yangyonglist1.size(); j++) {
                                            editor.remove("yangyong" + position + j);
                                        }
                                        break;
                                    case "2":
                                        editor.remove("wayongsize" + position);
                                        for (int j = 0; j < wayonglist1.size(); j++) {
                                            editor.remove("wayong" + position + j);
                                        }
                                        break;
                                    case "3":
                                        editor.remove("dieyongsize" + position);
                                        for (int j = 0; j < dieyonglist1.size(); j++) {
                                            editor.remove("dieyong" + position + j);
                                        }
                                        break;
                                }
                            }
                            editor.commit();
                        }
                        /**
                         * 清除最后一项在shared的缓存
                         */
                        for (int i = 0; i < getDate(listt.size() - 1).size(); i++) {
                            Map<String, Object> map1 = listt.get(listt.size() - 1);
                            List<Integer> ziyouyonglist1 = (List<Integer>) map1.get("ziyouyong");//
                            List<Integer> yangyonglist1 = (List<Integer>) map1.get("yangyong");//
                            List<Integer> wayonglist1 = (List<Integer>) map1.get("wayong");//
                            List<Integer> dieyonglist1 = (List<Integer>) map1.get("dieyong");//
                            for (int k = 0; k < getDate(listt.size() - 1).size(); k++) {
                                switch (getDate(listt.size() - 1).get(k)[1]) {
                                    case "0"://百位
                                        editor.remove("ziyouyongsize" + (listt.size() - 1));
                                        for (int j = 0; j < ziyouyonglist1.size(); j++) {
                                            editor.remove("ziyouyong" + (listt.size() - 1) + j);
                                        }
                                        break;
                                    case "1"://十位
                                        editor.remove("yangyongsize" + (listt.size() - 1));
                                        for (int j = 0; j < yangyonglist1.size(); j++) {
                                            editor.remove("yangyong" + (listt.size() - 1) + j);
                                        }
                                        break;
                                    case "2"://个位
                                        editor.remove("wayongsize" + (listt.size() - 1));
                                        for (int j = 0; j < wayonglist1.size(); j++) {
                                            editor.remove("wayong" + (listt.size() - 1) + j);
                                        }
                                        break;
                                    case "3":
                                        editor.remove("dieyongsize" + (listt.size() - 1));
                                        for (int j = 0; j < dieyonglist1.size(); j++) {
                                            editor.remove("dieyong" + (listt.size() - 1) + j);
                                        }
                                        break;
                                }
                            }
                            editor.commit();
                        }
                        /**
                         * 将删除项后面的项在shared中往前提一个位置
                         */
                        for (int i = 0; i < listt.size() - position - 1; i++) {
                            Map<String, Object> map1 = listt.get(position + i + 1);
                            List<Integer> ziyouyonglist1 = (List<Integer>) map1.get("ziyouyong");
                            List<Integer> yangyonglist1 = (List<Integer>) map1.get("yangyong");
                            List<Integer> wayonglist1 = (List<Integer>) map1.get("wayong");
                            List<Integer> dieyonglist1 = (List<Integer>) map1.get("dieyong");
                            for (int k = 0; k < getDate(position + i + 1).size(); k++) {
                                switch (getDate(position + i + 1).get(k)[1]) {
                                    case "0"://百位
                                        editor.putInt("ziyouyongsize" + (position + i), ziyouyonglist1.size());
                                        for (int j = 0; j < ziyouyonglist1.size(); j++) {
                                            editor.putInt("ziyouyong" + (position + i) + j, ziyouyonglist1.get(j));
                                        }
                                        break;
                                    case "1"://十位
                                        editor.putInt("yangyongsize" + (position + i), yangyonglist1.size());
                                        for (int j = 0; j < yangyonglist1.size(); j++) {
                                            editor.putInt("yangyong" + (position + i) + j, yangyonglist1.get(j));
                                        }
                                        break;
                                    case "2"://个位
                                        editor.putInt("wayongsize" + (position + i), wayonglist1.size());
                                        for (int j = 0; j < wayonglist1.size(); j++) {
                                            editor.putInt("wayong" + (position + i) + j, wayonglist1.get(j));
                                        }
                                        break;
                                    case "3"://个位
                                        editor.putInt("dieyongsize" + (position + i), dieyonglist1.size());
                                        for (int j = 0; j < dieyonglist1.size(); j++) {
                                            editor.putInt("dieyong" + (position + i) + j, dieyonglist1.get(j));
                                        }
                                        break;
                                }
                            }
                            editor.commit();
                        }
                        Message m = new Message();
                        m.what = 1;
                        // 发送消息到Handler
                        handler.sendMessage(m);
                    }
                }.start();
            }
        });

        holder.gv.setAdapter(adapter);
        holder.gv.setClickable(false);
        holder.gv.setPressed(false);
        holder.gv.setEnabled(false);

        return convertView;
    }

    class ViewHolder {
        Button btn_clear;
        GridView gv;
    }
}
