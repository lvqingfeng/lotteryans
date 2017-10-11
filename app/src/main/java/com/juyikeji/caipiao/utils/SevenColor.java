package com.juyikeji.caipiao.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyg on 2016/3/11 0011.
 */
public class SevenColor {

    public List<String> getNumb(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        for (int a = 0; a < list.get(0).length; a++) {
            String s = "";
            s = s + list.get(0)[a] + " ";
            for (int b = 0; b < list.get(1).length; b++) {
                Log.i("kjdfo", list.get(1).length + "");
                String s6 = s;
                s = s + list.get(1)[b] + " ";
                for (int c = 0; c < list.get(2).length; c++) {
                    String s5 = s;
                    s = s + list.get(2)[c] + " ";
                    for (int d = 0; d < list.get(3).length; d++) {
                        String s4 = s;
                        s = s + list.get(3)[d] + " ";
                        for (int e = 0; e < list.get(4).length; e++) {
                            String s3 = s;
                            s = s + list.get(4)[e] + " ";
                            for (int f = 0; f < list.get(5).length; f++) {
                                String s2 = s;
                                s = s + list.get(5)[f] + " ";
                                for (int g = 0; g < list.get(6).length; g++) {
                                    String s1 = s;
                                    s = s1 + list.get(6)[g];
                                    Log.i("sevencolor", s);
                                    servnnumb.add(s);
                                    s = s1;
                                }
                                s = s2;
                            }
                            s = s3;
                        }
                        s = s4;
                    }
                    s = s5;
                }
                s = s6;
            }
        }
        return servnnumb;
    }

    /**
     *将选号组合(3d——直选)
     * @param list
     * @return
     */
    public List<String> getNumb2(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        for (int a = 0; a < list.get(0).length; a++) {
            String s = "";
            s = s + list.get(0)[a] + " ";
            for (int b = 0; b < list.get(1).length; b++) {
                String s6 = s;
                s = s + list.get(1)[b] + " ";
                for (int c = 0; c < list.get(2).length; c++) {
                    String s5 = s;
                    s = s + list.get(2)[c] + " ";
                    servnnumb.add(s);
                    s = s5;
                }
                s = s6;
            }
        }
        return servnnumb;
    }

    /**
     * 将选号组合(排列三——组三单式)
     * @param list
     * @return
     */
    public List<String> getNumb3(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        for (int a = 0; a < list.get(0).length; a++) {
            String s = "";
            s = s + list.get(0)[a] + " ";
            for (int b = 0; b < list.get(1).length; b++) {
                String s6 = s;
                s = s + list.get(1)[b] + " ";
                servnnumb.add(s);
                s = s6;
            }
        }

        return servnnumb;
    }
    /**
     * 将选号组合(排列三——组三复式)
     * @param list
     * @return
     */
    public List<String> getNumb4(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        for (int j = 0; j < list.get(0).length; j++) {
            if (j == 0) {
                for (int z = 1; z < list.get(0).length; z++) {
                    servnnumb.add(list.get(0)[j] + " " + list.get(0)[j] + " " + list.get(0)[z] + " ");
                    Log.i("listint", list.get(0)[j] + " " + list.get(0)[j] + " " + list.get(0)[z]);
                }
            } else if (j == list.get(0).length - 1) {
                for (int z = 0; z < list.get(0).length - 1; z++) {
                    servnnumb.add(list.get(0)[j] + " " + list.get(0)[j] + " " + list.get(0)[z] + " ");
                    Log.i("listint", list.get(0)[j] + " " + list.get(0)[j] + " " + list.get(0)[z]);
                }
            } else {
                for (int z = 0; z < list.get(0).length; z++) {
                    if (z != j && list.get(0)[z] != -1) {
                        servnnumb.add(list.get(0)[j] + " " + list.get(0)[j] + " " + list.get(0)[z] + " ");
                        Log.i("listint", list.get(0)[j] + " " + list.get(0)[j] + " " + list.get(0)[z]);
                    }
                }
            }
        }

        return servnnumb;
    }
    /**
     * 将选号组合(排列三——组选六)
     * @param list
     * @return
     */
    public List<String> getNumb5(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        for (int j = 0; j < list.get(0).length; j++) {
            for (int i = j + 1; i < list.get(0).length; i++) {
                for (int z = i + 1; z < list.get(0).length; z++) {
                    servnnumb.add(list.get(0)[j] + " " + list.get(0)[i] + " " + list.get(0)[z] + " ");
                }
            }
        }
        return servnnumb;
    }
    /**
     * 将选号组合(排列三——直选三和)
     * @param list
     * @return
     */
    public List<String> getNumb6(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        for (int i = 0; i < list.get(0).length; i++) {
            for (int a = 0; a < 10; a++) {
                for (int b = 0; b < 10; b++) {
                    for (int c = 0; c < 10; c++) {
                        if (a + b + c == list.get(0)[i]) {
                            Log.i("he", a + " " + b + " " + c);
                            servnnumb.add(a + " " + b + " " + c + " ");
                        }
                    }
                }
            }
        }
        return servnnumb;
    }
    /**
     * 将选号组合(排列五)
     * @param list
     * @return
     */
    public List<String> getNumb7(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        for (int a = 0; a < list.get(0).length; a++) {
            String s = "";
            s = s + list.get(0)[a] + ",";
            for (int b = 0; b < list.get(1).length; b++) {
                Log.i("kjdfo", list.get(1).length + "");
                String s6 = s;
                s = s + list.get(1)[b] + ",";
                for (int c = 0; c < list.get(2).length; c++) {
                    String s5 = s;
                    s = s + list.get(2)[c] + ",";
                    for (int d = 0; d < list.get(3).length; d++) {
                        String s4 = s;
                        s = s + list.get(3)[d] + ",";
                        for (int e = 0; e < list.get(4).length; e++) {
                            String s3 = s;
                            s = s + list.get(4)[e];
                            Log.i("sevencolor", s);
                            servnnumb.add(s);
                            s = s3;
                        }
                        s = s4;
                    }
                    s = s5;
                }
                s = s6;
            }
        }
        return servnnumb;
    }
    /**
     * 将选号组合(3d——组三)
     * @param list
     * @return
     */
    public List<String> getNumb8(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        String s = "";
        for (int i = 0; i < list.get(0).length; i++) {
            for (int j = 0; j < list.get(0).length; j++) {
                if (j != i) {
                    s = list.get(0)[i] + " " + list.get(0)[j] + " ";
                    servnnumb.add(s);
                }
            }
        }

        return servnnumb;
    }
    /**
     * 将选号组合(3d——组六)
     * @param list
     * @return
     */
    public List<String> getNumb9(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        String s = "";
        for (int i = 0; i < list.get(0).length; i++) {
            for (int j = i + 1; j < list.get(0).length; j++) {
                for (int x = j + 1; x < list.get(0).length; x++) {
                    s = list.get(0)[i] + " " + list.get(0)[j] + " " + list.get(0)[x] + " ";
                    servnnumb.add(s);
                }
            }
        }
        return servnnumb;
    }
    /**
     * 将选号组合(快三——和值、三同号、二同号复选)
     * @param list
     * @return
     */
    public List<String> getNumb10(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        String s = "";
        for (int i = 0; i < list.get(0).length; i++) {
            s = list.get(0)[i] + " ";
            servnnumb.add(s);
        }
        return servnnumb;
    }
    /**
     * 将选号组合(快三——二同号单选)
     * @param list
     * @return
     */
    public List<String> getNumb11(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        String s = "";
        for (int i = 0; i < list.get(0).length; i++) {
            for (int j = 0; j < list.get(1).length; j++) {
                s = list.get(0)[i] + " " + list.get(1)[j] + " ";
                servnnumb.add(s);
            }
        }
        return servnnumb;
    }
    /**
     * 将选号组合(快三——三不同号)
     * @param list
     * @return
     */
    public List<String> getNumb12(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        String s = "";
        for (int i = 0; i < list.get(0).length; i++) {
            for (int j = i + 1; j < list.get(0).length; j++) {
                for (int x = j + 1; x < list.get(0).length; x++) {
                    s = list.get(0)[i] + " " + list.get(0)[j] + " " + list.get(0)[x] + " ";
                    servnnumb.add(s);
                }
            }
        }
        return servnnumb;
    }
    /**
     * 将选号组合(快三——二不同号)
     * @param list
     * @return
     */
    public List<String> getNumb13(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        String s = "";
        for (int i = 0; i < list.get(0).length; i++) {
            for (int j = i + 1; j < list.get(0).length; j++) {
                s = list.get(0)[i] + " " + list.get(0)[j] + " ";
                servnnumb.add(s);
            }
        }
        return servnnumb;
    }
    /**
     * 将选号组合(快三——三不同号胆拖——1个胆球)
     * @param list
     * @return
     */
    public List<String> getNumb14(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        String s = "";
        String s1 = "";
        for (int i = 0; i < list.get(0).length; i++) {
            s = list.get(0)[i] + " ";
        }
        for (int j = 0; j < list.get(1).length; j++) {
            for (int a = j + 1; a < list.get(1).length; a++) {
                s1 = s + list.get(1)[j] + " " + list.get(1)[a] + " ";
                servnnumb.add(s1);
            }
        }
        return servnnumb;
    }
    /**
     * 将选号组合(快三——三不同号胆拖——2个胆球)
     * @param list
     * @return
     */
    public List<String> getNumb15(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        String s = "";
        String s1 = "";
        for (int i = 0; i < list.get(0).length; i++) {
            s = s + list.get(0)[i] + " ";
        }
        for (int j = 0; j < list.get(1).length; j++) {
            s1 = s + list.get(1)[j] + " ";
            servnnumb.add(s1);
        }
        return servnnumb;
    }
    /**
     * 将选号组合(快三——二不同号胆拖)
     * @param list
     * @return
     */
    public List<String> getNumb16(List<int[]> list) {
        List<String> servnnumb = new ArrayList<String>();
        String s1 = "";
        for (int j = 0; j < list.get(1).length; j++) {
            s1 = list.get(0)[0] + " " + list.get(1)[j] + " ";
            servnnumb.add(s1);
        }
        return servnnumb;
    }
}
