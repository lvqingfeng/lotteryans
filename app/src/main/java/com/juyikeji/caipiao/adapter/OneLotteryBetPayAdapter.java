package com.juyikeji.caipiao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.juyikeji.caipiao.R;

import java.util.List;

/**
 * Created by jyg on 2016/3/6 0006.
 */
public class OneLotteryBetPayAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private int bs, code;

    public OneLotteryBetPayAdapter(Context context, List<String> list, int bs) {
        this.context = context;
        this.list = list;
        this.bs = bs;
    }

    public OneLotteryBetPayAdapter(Context context, List<String> list, int bs, int code) {
        this.context = context;
        this.list = list;
        this.bs = bs;
        this.code = code;
    }

    //机选方法
    public void changeNumber() {
        int[] test;
        String s = "";
        switch (bs) {
            case 1:
                test = testRndom33();
                for (int i = 0; i < test.length; i++) {
                    s = s + test[i] + " ";
                }
                int i = (int) (Math.random() * 16 + 1);
                s = s + i;
                list.add(s);
                break;
            case 2:
                test = testRndom35();
                for (int j = 0; j < test.length; j++) {
                    s = s + test[j] + " ";
                }
                list.add(s);
                break;
            case 3:

                break;
            case 4:
                test = new int[7];
                for (int k = 0; k < 7; k++) {
                    test[k] = (int) (Math.random() * 10);
                }
                for (int l = 0; l < test.length; l++) {
                    s = s + test[l] + " ";
                }
                list.add(s);
                break;
            case 5:
                switch (code) {
                    case 0:
                        test = new int[3];
                        for (int k = 0; k < 3; k++) {
                            test[k] = (int) (Math.random() * 10);
                        }
                        for (int l = 0; l < test.length; l++) {
                            s = s + test[l] + " ";
                        }
                        list.add(s);
                        break;
                    case 1:
                        test = new int[2];
                        for (int k = 0; k < 2; k++) {
                            test[k] = (int) (Math.random() * 10);
                            for (int j = 0; j <= k; j++) {
                                if ((k != j) && test[k] == test[j]) {
                                    k--;
                                }
                            }
                        }
                        for (int l = 0; l < test.length; l++) {
                            s = s + test[l] + " ";
                        }
                        list.add(s);
                        break;
                    case 2:
                        test = new int[2];
                        for (int k = 0; k < 2; k++) {
                            test[k] = (int) (Math.random() * 10);
                            for (int j = 0; j <= k; j++) {
                                if ((k != j) && test[k] == test[j]) {
                                    k--;
                                }
                            }
                        }
                        for (int l = 0; l < test.length; l++) {
                            s = s + test[l] + " ";
                        }
                        list.add(s);
                        break;
                    case 3:
                        test = new int[3];
                        for (int k = 0; k < 3; k++) {
                            test[k] = (int) (Math.random() * 10);
                            for (int j = 0; j <= k; j++) {
                                if ((k != j) && test[k] == test[j]) {
                                    k--;
                                }
                            }
                        }
                        for (int l = 0; l < test.length; l++) {
                            s = s + test[l] + " ";
                        }
                        list.add(s);
                        break;
                    case 4:
                        test = new int[1];
                        for (int k = 0; k < 1; k++) {
                            test[k] = (int) (Math.random() * 28);
                        }
                        for (int l = 0; l < test.length; l++) {
                            s = s + test[l] + " ";
                        }
                        list.add(s);
                        break;
                }
                break;
            case 6:
                //22选5
                test = testRndom22();
                for (int a = 0; a < test.length; a++) {
                    s = s + test[a] + " ";
                }
                list.add(s);
                break;
            case 7:
                if (code == 0) {
                    test = new int[3];
                    for (int k = 0; k < 3; k++) {
                        test[k] = (int) (Math.random() * 10);
                    }
                    for (int l = 0; l < test.length; l++) {
                        s = s + test[l] + " ";
                    }
                    list.add(s);
                } else if (code == 1) {
                    test = new int[2];
                    for (int k = 0; k < 2; k++) {
                        test[k] = (int) (Math.random() * 10);
                    }
                    for (int i1 = 0; i1 < test.length; i1++) {
                        for (int j = 0; j < test.length; j++) {
                            if (j != i1) {
                                s = test[i1] + " " + test[j] + " ";
                                list.add(s);
                            }
                        }
                    }
                } else if (code == 2) {
                    test = new int[3];
                    for (int k = 0; k < 3; k++) {
                        test[k] = (int) (Math.random() * 10);
                    }
                    for (int a = 0; a < test.length; a++) {
                        for (int j = a + 1; j < test.length; j++) {
                            for (int x = j + 1; x < test.length; x++) {
                                s = test[a] + " " + test[j] + " " + test[x] + " ";
                                list.add(s);
                            }
                        }
                    }
                }
                break;
            case 8:
                test = testRndom30();
                for (int a = 0; a < test.length; a++) {
                    s = s + test[a] + " ";
                }
                list.add(s);
                break;
            case 9:
                switch (code) {
                    case 0:
                        test = new int[1];
                        for (int k = 0; k < 1; k++) {
                            test[k] = (int) (Math.random() * 10);

                        }
                        for (int l = 0; l < test.length; l++) {
                            s = s + test[l] + " ";
                        }
                        list.add(s);
                        break;
                    case 1:
                        test = new int[1];
                        for (int k = 0; k < 1; k++) {
                            test[k] = (int) (Math.random() * 5 + 1);
                        }
                        for (int l = 0; l < test.length; l++) {
                            s = s + test[l] + test[l] + test[l] + " ";
                        }
                        list.add(s);
                        break;
                    case 2:
                        test = new int[2];
                        for (int k = 0; k < 2; k++) {
                            test[k] = (int) (Math.random() * 5 + 1);
                            for (int j = 0; j <= k; j++) {
                                if ((k != j) && test[k] == test[j]) {
                                    k--;
                                }
                            }
                        }
                        s = s + test[0] + test[0] + " " + test[1] + " ";
                        list.add(s);
                        break;
                    case 3:
                        test = new int[1];
                        for (int k = 0; k < 1; k++) {
                            test[k] = (int) (Math.random() * 5 + 1);
                        }
                        for (int l = 0; l < test.length; l++) {
                            s = s + test[l] + test[l] + " ";
                        }
                        list.add(s);
                        break;
                    case 4:
                        test = new int[3];
                        for (int k = 0; k < 3; k++) {
                            test[k] = (int) (Math.random() * 5 + 1);
                            for (int j = 0; j <= k; j++) {
                                if ((k != j) && test[k] == test[j]) {
                                    k--;
                                }
                            }
                        }
                        s = s + test[0] + " " + test[1] + " " + test[2] + " ";
                        list.add(s);
                        break;
                    case 5:
                        test = new int[2];
                        for (int k = 0; k < 2; k++) {
                            test[k] = (int) (Math.random() * 5 + 1);
                            for (int j = 0; j <= k; j++) {
                                if ((k != j) && test[k] == test[j]) {
                                    k--;
                                }
                            }
                        }
                        s = s + test[0] + " " + test[1] + " ";
                        list.add(s);
                        break;
                    case 6:
                        test = new int[3];
                        for (int k = 0; k < 3; k++) {
                            test[k] = (int) (Math.random() * 5 + 1);
                            for (int j = 0; j <= k; j++) {
                                if ((k != j) && test[k] == test[j]) {
                                    k--;
                                }
                            }
                        }
                        s = s + test[0] + " " + test[1] + " " + test[2] + " ";
                        list.add(s);
                        break;
                    case 7:
                        test = new int[2];
                        for (int k = 0; k < 2; k++) {
                            test[k] = (int) (Math.random() * 5 + 1);
                            for (int j = 0; j <= k; j++) {
                                if ((k != j) && test[k] == test[j]) {
                                    k--;
                                }
                            }
                        }
                        s = s + test[0] + " " + test[1] + " ";
                        list.add(s);
                        break;
                }
                break;
            case 10:
                test = new int[5];
                for (int k = 0; k < 5; k++) {
                    test[k] = (int) (Math.random() * 10);
                }
                for (int l = 0; l < test.length; l++) {
                    s = s + test[l] + " ";
                }
                list.add(s);
                break;
        }
        notifyDataSetChanged();
    }

    //生成随机数的方法(双色球)
    private int[] testRndom33() {
        int[] b = new int[6];
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) (Math.random() * 33 + 1);
            for (int j = 0; j <= i; j++) {

                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //生成随机数的方法(大乐透)
    private int[] testRndom35() {
        int l = (int) (Math.random() * 12 + 1);
        int k = (int) (Math.random() * 12 + 1);
        if (k == l) {
            testRndom35();
        }
        int[] b = new int[5];
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) (Math.random() * 35 + 1);
            for (int j = 0; j <= i; j++) {

                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        int[] a = new int[7];
        for (int ii = 0; ii < a.length; ii++) {
            if (ii < 5) {
                a[ii] = b[ii];
            } else if (ii == 5) {
                a[ii] = l;
            } else if (ii == 6) {
                a[ii] = k;
            }
        }
        return a;
    }

    //生成随机数的方法(七乐彩)
    private int[] testRndom30() {
        int[] b = new int[7];
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) (Math.random() * 30 + 1);
            for (int j = 0; j <= i; j++) {

                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    //生成随机数的方法(七乐彩)
    private int[] testRndom22() {
        int[] b = new int[5];
        for (int i = 0; i < b.length; i++) {
            b[i] = (int) (Math.random() * 22 + 1);
            for (int j = 0; j <= i; j++) {

                if ((i != j) && b[i] == b[j]) {
                    i--;
                }
            }
        }
        return b;
    }

    @Override
    public long getItemId(int position) {
        return position;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_one_lotterybetpay_item, null);
            holder.btn_clear = (Button) convertView.findViewById(R.id.btn_clear);
            holder.tv_1 = (TextView) convertView.findViewById(R.id.tv_1);
            holder.tv_2 = (TextView) convertView.findViewById(R.id.tv_2);
            holder.tv_3 = (TextView) convertView.findViewById(R.id.tv_3);
            holder.tv_4 = (TextView) convertView.findViewById(R.id.tv_4);
            holder.tv_5 = (TextView) convertView.findViewById(R.id.tv_5);
            holder.tv_6 = (TextView) convertView.findViewById(R.id.tv_6);
            holder.tv_7 = (TextView) convertView.findViewById(R.id.tv_7);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (bs == 5) {
            switch (code) {
                case 0:
                    String s = list.get(position);
                    String s1, s2, s3;
                    //红球1
                    s1 = s.substring(0, s.indexOf(" "));
                    //红球2
                    String s22 = s.substring(s.indexOf(" ") + 1);
                    s2 = s22.substring(0, s22.indexOf(" "));
                    //红球3
                    String s33 = s22.substring(s22.indexOf(" ") + 1);
                    s3 = s33.substring(0, s33.indexOf(" "));
                    holder.tv_1.setText(s1);
                    holder.tv_2.setText(s2);
                    holder.tv_3.setText(s3);
                    holder.tv_4.setVisibility(View.GONE);
                    holder.tv_5.setVisibility(View.GONE);
                    holder.tv_6.setVisibility(View.GONE);
                    holder.tv_7.setVisibility(View.GONE);
                    break;
                case 1:
                    String ss = list.get(position);
                    String ss1, ss2, ss3;
                    //红球1
                    ss1 = ss.substring(0, ss.indexOf(" "));
                    //红球2
                    String ss22 = ss.substring(ss.indexOf(" ") + 1);
                    ss2 = ss22.substring(0, ss22.indexOf(" "));
                    holder.tv_1.setText(ss1);
                    holder.tv_2.setText(ss2);
                    holder.tv_3.setVisibility(View.GONE);
                    holder.tv_4.setVisibility(View.GONE);
                    holder.tv_5.setVisibility(View.GONE);
                    holder.tv_6.setVisibility(View.GONE);
                    holder.tv_7.setVisibility(View.GONE);
                    break;
                case 2:
                    String sss = list.get(position);
                    String sss1, sss2, sss3;
                    //红球1
                    sss1 = sss.substring(0, sss.indexOf(" "));
                    //红球2
                    String sss22 = sss.substring(sss.indexOf(" ") + 1);
                    sss2 = sss22.substring(0, sss22.indexOf(" "));
                    //红球3
                    String sss33 = sss22.substring(sss22.indexOf(" ") + 1);
                    sss3 = sss33.substring(0, sss33.indexOf(" "));
                    holder.tv_1.setText(sss1);
                    holder.tv_2.setText(sss2);
                    holder.tv_3.setText(sss3);
                    holder.tv_4.setVisibility(View.GONE);
                    holder.tv_5.setVisibility(View.GONE);
                    holder.tv_6.setVisibility(View.GONE);
                    holder.tv_7.setVisibility(View.GONE);
                    break;
                case 3:
                    String ssss = list.get(position);
                    String ssss1, ssss2, ssss3;
                    //红球1
                    ssss1 = ssss.substring(0, ssss.indexOf(" "));
                    //红球2
                    String ssss22 = ssss.substring(ssss.indexOf(" ") + 1);
                    ssss2 = ssss22.substring(0, ssss22.indexOf(" "));
                    //红球3
                    String ssss33 = ssss22.substring(ssss22.indexOf(" ") + 1);
                    ssss3 = ssss33.substring(0, ssss33.indexOf(" "));
                    holder.tv_1.setText(ssss1);
                    holder.tv_2.setText(ssss2);
                    holder.tv_3.setText(ssss3);
                    holder.tv_4.setVisibility(View.GONE);
                    holder.tv_5.setVisibility(View.GONE);
                    holder.tv_6.setVisibility(View.GONE);
                    holder.tv_7.setVisibility(View.GONE);
                    break;
                case 4:
                    String sssss = list.get(position);
                    String sssss1, sssss2, sssss3;
                    //红球1
                    sssss1 = sssss.substring(0, sssss.indexOf(" "));
                    //s红球2
                    String sssss22 = sssss.substring(sssss.indexOf(" ") + 1);
                    sssss2 = sssss22.substring(0, sssss22.indexOf(" "));
                    //红球3
                    String sssss33 = sssss22.substring(sssss22.indexOf(" ") + 1);
                    sssss3 = sssss33.substring(0, sssss33.indexOf(" "));
                    holder.tv_1.setText(sssss1);
                    holder.tv_2.setText(sssss2);
                    holder.tv_3.setText(sssss3);
                    holder.tv_4.setVisibility(View.GONE);
                    holder.tv_5.setVisibility(View.GONE);
                    holder.tv_6.setVisibility(View.GONE);
                    holder.tv_7.setVisibility(View.GONE);
                    break;
            }
        } else if (bs == 7) {
            if (code == 0) {
                String s = list.get(position);
                String s1, s2, s3;
                //红球1
                s1 = s.substring(0, s.indexOf(" "));
                //s红球2
                String s22 = s.substring(s.indexOf(" ") + 1);
                s2 = s22.substring(0, s22.indexOf(" "));
                //红球3
                String s33 = s22.substring(s22.indexOf(" ") + 1);
                s3 = s33.substring(0, s33.indexOf(" "));

                holder.tv_1.setText(s1);
                holder.tv_2.setText(s2);
                holder.tv_3.setText(s3);
                holder.tv_4.setVisibility(View.GONE);
                holder.tv_5.setVisibility(View.GONE);
                holder.tv_6.setVisibility(View.GONE);
                holder.tv_7.setVisibility(View.GONE);
            } else if (code == 1) {
                String s = list.get(position);
                String s1, s2;
                //红球1
                s1 = s.substring(0, s.indexOf(" "));
                //s红球2
                String s22 = s.substring(s.indexOf(" ") + 1);
                s2 = s22.substring(0, s22.indexOf(" "));

                holder.tv_1.setText(s1);
                holder.tv_2.setText(s2);
                holder.tv_3.setVisibility(View.GONE);
                holder.tv_4.setVisibility(View.GONE);
                holder.tv_5.setVisibility(View.GONE);
                holder.tv_6.setVisibility(View.GONE);
                holder.tv_7.setVisibility(View.GONE);
            } else if (code == 2) {
                String s = list.get(position);
                String s1, s2, s3;
                //红球1
                s1 = s.substring(0, s.indexOf(" "));
                //s红球2
                String s22 = s.substring(s.indexOf(" ") + 1);
                s2 = s22.substring(0, s22.indexOf(" "));
                //红球3
                String s33 = s22.substring(s22.indexOf(" ") + 1);
                s3 = s33.substring(0, s33.indexOf(" "));

                holder.tv_1.setText(s1);
                holder.tv_2.setText(s2);
                holder.tv_3.setText(s3);
                holder.tv_4.setVisibility(View.GONE);
                holder.tv_5.setVisibility(View.GONE);
                holder.tv_6.setVisibility(View.GONE);
                holder.tv_7.setVisibility(View.GONE);
            }
        } else if (bs == 9) {
            if (code == 0 || code == 1 || code == 3) {
                String s = list.get(position);
                String s1, s2, s3;
                //红球1
                s1 = s.substring(0, s.indexOf(" "));

                holder.tv_1.setText(s1);
                holder.tv_2.setVisibility(View.GONE);
                holder.tv_3.setVisibility(View.GONE);
                holder.tv_4.setVisibility(View.GONE);
                holder.tv_5.setVisibility(View.GONE);
                holder.tv_6.setVisibility(View.GONE);
                holder.tv_7.setVisibility(View.GONE);
            } else if (code == 2) {
                String s = list.get(position);
                String s1, s2, s3;
                //红球1
                s1 = s.substring(0, s.indexOf(" "));
                //s红球2
                String s22 = s.substring(s.indexOf(" ") + 1);
                s2 = s22.substring(0, s22.indexOf(" "));

                holder.tv_1.setText(s1);
                holder.tv_2.setText(s2);
                holder.tv_3.setVisibility(View.GONE);
                holder.tv_4.setVisibility(View.GONE);
                holder.tv_5.setVisibility(View.GONE);
                holder.tv_6.setVisibility(View.GONE);
                holder.tv_7.setVisibility(View.GONE);
            } else if (code == 4) {
                String s = list.get(position);
                String s1, s2, s3;
                //红球1
                s1 = s.substring(0, s.indexOf(" "));
                //s红球2
                String s22 = s.substring(s.indexOf(" ") + 1);
                s2 = s22.substring(0, s22.indexOf(" "));
                //红球3
                String s33 = s22.substring(s22.indexOf(" ") + 1);
                s3 = s33.substring(0, s33.indexOf(" "));

                holder.tv_1.setText(s1);
                holder.tv_2.setText(s2);
                holder.tv_3.setText(s3);
                holder.tv_4.setVisibility(View.GONE);
                holder.tv_5.setVisibility(View.GONE);
                holder.tv_6.setVisibility(View.GONE);
                holder.tv_7.setVisibility(View.GONE);
            } else if (code == 5) {
                String s = list.get(position);
                String s1, s2, s3;
                //红球1
                s1 = s.substring(0, s.indexOf(" "));
                //s红球2
                String s22 = s.substring(s.indexOf(" ") + 1);
                s2 = s22.substring(0, s22.indexOf(" "));

                holder.tv_1.setText(s1);
                holder.tv_2.setText(s2);
                holder.tv_3.setVisibility(View.GONE);
                holder.tv_4.setVisibility(View.GONE);
                holder.tv_5.setVisibility(View.GONE);
                holder.tv_6.setVisibility(View.GONE);
                holder.tv_7.setVisibility(View.GONE);
            } else if (code == 6) {
                String s = list.get(position);
                String s1, s2, s3;
                //红球1
                s1 = s.substring(0, s.indexOf(" "));
                //s红球2
                String s22 = s.substring(s.indexOf(" ") + 1);
                s2 = s22.substring(0, s22.indexOf(" "));
                //红球3
                String s33 = s22.substring(s22.indexOf(" ") + 1);
                s3 = s33.substring(0, s33.indexOf(" "));

                holder.tv_1.setText(s1);
                holder.tv_2.setText(s2);
                holder.tv_3.setText(s3);
                holder.tv_4.setVisibility(View.GONE);
                holder.tv_5.setVisibility(View.GONE);
                holder.tv_6.setVisibility(View.GONE);
                holder.tv_7.setVisibility(View.GONE);
            } else if (code == 7) {
                String s = list.get(position);
                String s1, s2, s3;
                //红球1
                s1 = s.substring(0, s.indexOf(" "));
                //s红球2
                String s22 = s.substring(s.indexOf(" ") + 1);
                s2 = s22.substring(0, s22.indexOf(" "));

                holder.tv_1.setText(s1);
                holder.tv_2.setText(s2);
                holder.tv_3.setVisibility(View.GONE);
                holder.tv_4.setVisibility(View.GONE);
                holder.tv_5.setVisibility(View.GONE);
                holder.tv_6.setVisibility(View.GONE);
                holder.tv_7.setVisibility(View.GONE);
            }
        } else if (bs == 10) {
            String s = list.get(position);
            String s1, s2, s3, s4, s5;
            //红球1
            s1 = s.substring(0, s.indexOf(" "));
            //s红球2
            String s22 = s.substring(s.indexOf(" ") + 1);
            s2 = s22.substring(0, s22.indexOf(" "));
            //红球3
            String s33 = s22.substring(s22.indexOf(" ") + 1);
            s3 = s33.substring(0, s33.indexOf(" "));
            //红球4
            String s44 = s33.substring(s33.indexOf(" ") + 1);
            s4 = s44.substring(0, s44.indexOf(" "));
            //红球5
            String s55 = s44.substring(s44.indexOf(" ") + 1);
            s5 = s55.substring(0, s55.indexOf(" "));
            holder.tv_1.setText(s1);
            holder.tv_2.setText(s2);
            holder.tv_3.setText(s3);
            holder.tv_4.setText(s4);
            holder.tv_5.setText(s5);
            holder.tv_6.setVisibility(View.GONE);
            holder.tv_7.setVisibility(View.GONE);
        } else {
            String s = list.get(position);
            String s1, s2, s3, s4, s5, s6, s7;
            //红球1
            s1 = s.substring(0, s.indexOf(" "));
            //红球2
            String s22 = s.substring(s.indexOf(" ") + 1);
            s2 = s22.substring(0, s22.indexOf(" "));
            //红球3
            String s33 = s22.substring(s22.indexOf(" ") + 1);
            s3 = s33.substring(0, s33.indexOf(" "));
            //红球4
            String s44 = s33.substring(s33.indexOf(" ") + 1);
            s4 = s44.substring(0, s44.indexOf(" "));
            //红球5
            String s55 = s44.substring(s44.indexOf(" ") + 1);
            s5 = s55.substring(0, s55.indexOf(" "));
            //红球6
            if (bs != 6) {
                String s66 = s55.substring(s55.indexOf(" ") + 1);
                s6 = s66.substring(0, s66.indexOf(" "));
                //蓝球7
                s7 = s66.substring(s66.indexOf(" ") + 1).trim();
                holder.tv_1.setText(s1);
                holder.tv_2.setText(s2);
                holder.tv_3.setText(s3);
                holder.tv_4.setText(s4);
                holder.tv_5.setText(s5);
                holder.tv_6.setText(s6);
                switch (bs) {
                    case 2:
                        holder.tv_6.setBackgroundResource(R.drawable.shape_05);
                        break;
                    case 8:
                        holder.tv_7.setBackgroundResource(R.drawable.shape_06);
                }
                holder.tv_7.setText(s7);
            } else {
                holder.tv_1.setText(s1);
                holder.tv_2.setText(s2);
                holder.tv_3.setText(s3);
                holder.tv_4.setText(s4);
                holder.tv_5.setText(s5);
                holder.tv_6.setVisibility(View.GONE);
                holder.tv_7.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    class ViewHolder {
        Button btn_clear;
        TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7;
    }
}
