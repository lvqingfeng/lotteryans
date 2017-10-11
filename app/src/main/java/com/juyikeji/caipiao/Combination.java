package com.juyikeji.caipiao;

import android.util.Log;

/**
 * 泳坛夺冠——获取每一注
 */
public class Combination {
    /**
     * 任选一
     *
     * @param i1
     * @param i2
     * @param i3
     * @param i4
     */
    public static void rxy(int[] i1, int[] i2, int[] i3, int[] i4) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                Log.i("rx1", i1[i] + "");
            }
        }
        for (int i = 0; i < i2.length; i++) {
            if (i2[i] != -1) {
                Log.i("rx1", i2[i] + "");
            }
        }
        for (int i = 0; i < i3.length; i++) {
            if (i3[i] != -1) {
                Log.i("rx1", i3[i] + "");
            }
        }
        for (int i = 0; i < i4.length; i++) {
            if (i4[i] != -1) {
                Log.i("rx1", i4[i] + "");
            }
        }
    }

    /**
     * 任选二
     */
    public static void rxe(int[] i1, int[] i2, int[] i3, int[] i4) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i2.length; j++) {
                    if (i2[j] != -1) {
                        Log.i("rx2", i1[i] + "," + i2[j] + "," + " " + "," + " ");
                    }
                }
            }
        }
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i3.length; j++) {
                    if (i3[j] != -1) {
                        Log.i("rx2", i1[i] + "," + " " + "," + i3[j] + "," + " ");
                    }
                }
            }
        }
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i4.length; j++) {
                    if (i4[j] != -1) {
                        Log.i("rx2", i1[i] + "," + " " + "," + " " + "," + i4[j]);
                    }
                }
            }
        }
        for (int i = 0; i < i2.length; i++) {
            if (i2[i] != -1) {
                for (int j = 0; j < i3.length; j++) {
                    if (i3[j] != -1) {
                        Log.i("rx2", " " + "," + i2[i] + "," + i3[j] + "," + " ");
                    }
                }
            }
        }
        for (int i = 0; i < i2.length; i++) {
            if (i2[i] != -1) {
                for (int j = 0; j < i4.length; j++) {
                    if (i4[j] != -1) {
                        Log.i("rx2", " " + "," + i2[i] + "," + " " + "," + i4[j]);
                    }
                }
            }
        }
        for (int i = 0; i < i3.length; i++) {
            if (i3[i] != -1) {
                for (int j = 0; j < i4.length; j++) {
                    if (i4[j] != -1) {
                        Log.i("rx2", " " + "," + " " + "," + i3[i] + "," + i4[j]);
                    }
                }
            }
        }
    }

    /**
     * 任选二全包
     *
     * @param i1
     * @param i2
     */
    public static void rxeqb(int[] i1, int[] i2) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i2.length; j++) {
                    if (i2[j] != -1) {
                        if (i1[i] != i2[j]) {
                            Log.i("rx2qb", i1[i] + "," + i2[j] + "," + " " + "," + " ");
                            Log.i("rx2qb", i1[i] + "," + " " + "," + i2[j] + "," + " ");
                            Log.i("rx2qb", i1[i] + "," + " " + "," + " " + "," + i2[j]);

                            Log.i("rx2qb", " " + "," + i1[i] + "," + i2[j] + "," + " ");
                            Log.i("rx2qb", " " + "," + i1[i] + "," + " " + "," + i2[j]);
                            Log.i("rx2qb", " " + "," + " " + "," + i1[i] + "," + i2[j]);

                            Log.i("rx2qb", i2[j] + "," + i1[i] + "," + " " + "," + " ");
                            Log.i("rx2qb", i2[j] + "," + " " + "," + i1[i] + "," + " ");
                            Log.i("rx2qb", i2[j] + "," + " " + "," + " " + "," + i1[i]);

                            Log.i("rx2qb", " " + "," + i2[j] + "," + i1[i] + "," + " ");
                            Log.i("rx2qb", " " + "," + i2[j] + "," + " " + "," + i1[i]);
                            Log.i("rx2qb", " " + "," + " " + "," + i2[j] + "," + i1[i]);
                        } else {
                            Log.i("rx2qb", i1[i] + "," + i2[j] + "," + " " + "," + " ");
                            Log.i("rx2qb", i1[i] + "," + " " + "," + i2[j] + "," + " ");
                            Log.i("rx2qb", i1[i] + "," + " " + "," + " " + "," + i2[j]);

                            Log.i("rx2qb", " " + "," + i1[i] + "," + i2[j] + "," + " ");
                            Log.i("rx2qb", " " + "," + i1[i] + "," + " " + "," + i2[j]);
                            Log.i("rx2qb", " " + "," + " " + "," + i1[i] + "," + i2[j]);
                        }
                    }
                }
            }
        }
    }

    /**
     * 任选三
     */
    public static void rx3(int[] i1, int[] i2, int[] i3, int[] i4) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i2.length; j++) {
                    if (i2[j] != -1) {
                        for (int k = 0; k < i3.length; k++) {
                            if (i3[k] != -1) {
                                Log.i("rx3", i1[i] + "," + i2[j] + "," + i3[k] + "," + " ");
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i2.length; j++) {
                    if (i2[j] != -1) {
                        for (int k = 0; k < i4.length; k++) {
                            if (i4[k] != -1) {
                                Log.i("rx3", i1[i] + "," + i2[j] + "," + " " + "," + i4[k]);
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i3.length; j++) {
                    if (i3[j] != -1) {
                        for (int k = 0; k < i4.length; k++) {
                            if (i4[k] != -1) {
                                Log.i("rx3", i1[i] + "," + i3[j] + "," + " " + "," + i4[k]);
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < i2.length; i++) {
            if (i2[i] != -1) {
                for (int j = 0; j < i3.length; j++) {
                    if (i3[j] != -1) {
                        for (int k = 0; k < i4.length; k++) {
                            if (i4[k] != -1) {
                                Log.i("rx3", " " + "," + i2[i] + "," + i3[j] + "," + i4[k]);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 任选三全包
     */
    public static void rxsqb(int[] i1, int[] i2, int[] i3) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i2.length; j++) {
                    if (i2[j] != -1) {
                        for (int k = 0; k < i3.length; k++) {
                            if (i3[k] != -1) {
                                if (i1[i] != i2[j] && i1[i] != i3[k] && i2[j] != i3[k]) {
                                    Log.i("rx3qb", i1[i] + "," + i2[j] + "," + i3[k] + "," + " ");
                                    Log.i("rx3qb", i1[i] + "," + i2[j] + "," + " " + "," + i3[k]);
                                    Log.i("rx3qb", i1[i] + "," + " " + "," + i2[j] + "," + i3[k]);
                                    Log.i("rx3qb", " " + "," + i1[i] + "," + i2[j] + "," + i3[k]);

                                    Log.i("rx3qb", i1[i] + "," + i3[k] + "," + i2[j] + "," + " ");
                                    Log.i("rx3qb", i1[i] + "," + i3[k] + "," + " " + "," + i2[j]);
                                    Log.i("rx3qb", i1[i] + "," + " " + "," + i3[k] + "," + i2[j]);
                                    Log.i("rx3qb", " " + "," + i1[i] + "," + i3[k] + "," + i2[j]);

                                    Log.i("rx3qb", i2[j] + "," + i1[i] + "," + i3[k] + "," + " ");
                                    Log.i("rx3qb", i2[j] + "," + i1[i] + "," + " " + "," + i3[k]);
                                    Log.i("rx3qb", i2[j] + "," + " " + "," + i1[i] + "," + i3[k]);
                                    Log.i("rx3qb", " " + "," + i2[j] + "," + i1[i] + "," + i3[k]);

                                    Log.i("rx3qb", i2[j] + "," + i3[k] + "," + i1[i] + "," + " ");
                                    Log.i("rx3qb", i2[j] + "," + i3[k] + "," + " " + "," + i1[i]);
                                    Log.i("rx3qb", i2[j] + "," + " " + "," + i3[k] + "," + i1[i]);
                                    Log.i("rx3qb", " " + "," + i2[j] + "," + i3[k] + "," + i1[i]);

                                    Log.i("rx3qb", i3[k] + "," + i1[i] + "," + i2[j] + "," + " ");
                                    Log.i("rx3qb", i3[k] + "," + i1[i] + "," + " " + "," + i2[j]);
                                    Log.i("rx3qb", i3[k] + "," + " " + "," + i1[i] + "," + i2[j]);
                                    Log.i("rx3qb", " " + "," + i3[k] + "," + i1[i] + "," + i2[j]);

                                    Log.i("rx3qb", i3[k] + "," + i2[j] + "," + i1[i] + "," + " ");
                                    Log.i("rx3qb", i3[k] + "," + i2[j] + "," + " " + "," + i1[i]);
                                    Log.i("rx3qb", i3[k] + "," + " " + "," + i2[j] + "," + i1[i]);
                                    Log.i("rx3qb", " " + "," + i3[k] + "," + i2[j] + "," + i1[i]);

                                } else if ((i1[i] == i2[j] && i1[i] != i3[k]) || (i1[i] == i3[k] && i1[i] != i2[j]) ||
                                        (i3[k] == i2[j] && i1[i] != i3[k])) {
                                    Log.i("rx3qb", i1[i] + "," + i2[j] + "," + i3[k] + "," + " ");
                                    Log.i("rx3qb", i1[i] + "," + i2[j] + "," + " " + "," + i3[k]);
                                    Log.i("rx3qb", i1[i] + "," + " " + "," + i2[j] + "," + i3[k]);
                                    Log.i("rx3qb", " " + "," + i1[i] + "," + i2[j] + "," + i3[k]);

                                    Log.i("rx3qb", i2[j] + "," + i3[k] + "," + i1[i] + "," + " ");
                                    Log.i("rx3qb", i2[j] + "," + i3[k] + "," + " " + "," + i1[i]);
                                    Log.i("rx3qb", i2[j] + "," + " " + "," + i3[k] + "," + i1[i]);
                                    Log.i("rx3qb", " " + "," + i2[j] + "," + i3[k] + "," + i1[i]);

                                    Log.i("rx3qb", i3[k] + "," + i1[i] + "," + i2[j] + "," + " ");
                                    Log.i("rx3qb", i3[k] + "," + i1[i] + "," + " " + "," + i2[j]);
                                    Log.i("rx3qb", i3[k] + "," + " " + "," + i1[i] + "," + i2[j]);
                                    Log.i("rx3qb", " " + "," + i3[k] + "," + i1[i] + "," + i2[j]);
                                } else if (i1[i] == i2[j] && i2[j] == i3[k]) {
                                    Log.i("rx3qb", i1[i] + "," + i2[j] + "," + i3[k] + "," + " ");
                                    Log.i("rx3qb", i1[i] + "," + i2[j] + "," + " " + "," + i3[k]);
                                    Log.i("rx3qb", i1[i] + "," + " " + "," + i2[j] + "," + i3[k]);
                                    Log.i("rx3qb", " " + "," + i1[i] + "," + i2[j] + "," + i3[k]);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 直选单式\组选复式
     *
     * @param i1
     * @param i2
     * @param i3
     * @param i4
     */
    public static void zxds(int[] i1, int[] i2, int[] i3, int[] i4) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i2.length; j++) {
                    if (i2[j] != -1) {
                        for (int k = 0; k < i3.length; k++) {
                            if (i3[k] != -1) {
                                for (int x = 0; x < i4.length; x++) {
                                    if (i4[x] != -1) {
                                        Log.i("zxds", i1[i] + "," + i2[j] + "," + i3[k] + "," + i4[x]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 直选组合
     *
     * @param i1
     * @param i2
     * @param i3
     * @param i4
     */
    public static void zxzh(int[] i1, int[] i2, int[] i3, int[] i4) {
        rxy(i1, i2, i3, i4);
        rxe(i1, i2, i3, i4);
        rx3(i1, i2, i3, i4);
        zxds(i1, i2, i3, i4);
    }

    /**
     * 组24单式
     *
     * @param i1
     * @param i2
     * @param i3
     * @param i4
     */
    public static void zx24ds(int[] i1, int[] i2, int[] i3, int[] i4) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i2.length; j++) {
                    if (i2[j] != -1) {
                        for (int k = 0; k < i3.length; k++) {
                            if (i3[k] != -1) {
                                for (int x = 0; x < i4.length; x++) {
                                    if (i4[x] != -1) {
                                        Log.i("zxds", i1[i] + "," + i2[j] + "," + i3[k] + "," + i4[x]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 组选24复式
     *
     * @param i1
     */
    public static void zx24fs(int[] i1) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = i + 1; j < i1.length; j++) {
                    if (i1[j] != -1) {
                        for (int k = j + 1; k < i1.length; k++) {
                            if (i1[k] != -1) {
                                for (int x = k + 1; x < i1.length; x++) {
                                    if (i1[x] != -1) {
                                        Log.i("zx24fs", i1[i] + "," + i1[j] + "," + i1[k] + "," + i1[x]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 组选24胆拖
     *
     * @param i1
     * @param i2
     */
    public static void zx24dt(int[] i1, int[] i2) {
        String s = "";
        int a = 0;
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                s = s + i1[i] + ",";
                a = a + 1;
            }
        }
        if (a == 1) {
            for (int j = 0; j < i2.length; j++) {
                if (i2[j] != -1) {
                    for (int k = j + 1; k < i2.length; k++) {
                        if (i2[k] != -1) {
                            for (int x = k + 1; x < i2.length; x++) {
                                if (i2[x] != -1) {
                                    Log.i("zx24dt", s + i2[j] + "," + i2[k] + "," + i2[x]);
                                }
                            }
                        }
                    }
                }
            }
        } else if (a == 2) {
            for (int j = 0; j < i2.length; j++) {
                if (i2[j] != -1) {
                    for (int k = j + 1; k < i2.length; k++) {
                        if (i2[k] != -1) {
                            Log.i("zx24dt", s + i2[j] + "," + i2[k]);
                        }
                    }
                }
            }
        } else if (a == 3) {
            for (int j = 0; j < i2.length; j++) {
                if (i2[j] != -1) {
                    Log.i("zx24dt", s + i2[j]);
                }

            }
        }
    }

    /**
     * 组选12单式
     */
    public static void zx12ds(int[] i1, int[] i2, int[] i3, int[] i4) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i2.length; j++) {
                    if (i2[j] != -1) {
                        for (int k = 0; k < i3.length; k++) {
                            if (i3[k] != -1) {
                                for (int x = 0; x < i4.length; x++) {
                                    if (i4[x] != -1) {
                                        Log.i("zx12ds", i1[i] + "," + i2[j] + "," + i3[k] + "," + i4[x]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 组选12复式
     *
     * @param i1
     */
    public static void zx12fs(int[] i1) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = i + 1; j < i1.length; j++) {
                    if (i1[j] != -1) {
                        for (int k = j + 1; k < i1.length; k++) {
                            if (i1[k] != -1) {
                                Log.i("zx12fs", i1[i] + "," + i1[j] + "," + i1[k] + "," + i1[k]);
                                Log.i("zx12fs", i1[i] + "," + i1[k] + "," + i1[j] + "," + i1[j]);
                                Log.i("zx12fs", i1[j] + "," + i1[k] + "," + i1[i] + "," + i1[i]);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 组选12胆拖
     */
    public static void zx12dt(int[] i1, int[] i2) {
        String s = "";
        int a = 0;
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                s = s + i1[i] + ",";
                a = a + 1;
            }
        }
        if (a == 1) {
            for (int j = 0; j < i2.length; j++) {
                if (i2[j] != -1) {
                    for (int k = j + 1; k < i2.length; k++) {
                        if (i2[k] != -1) {
                            Log.i("zx12dt", s + s + i2[j] + "," + i2[k]);
                            Log.i("zx12dt", s + i2[j] + "," + i2[j] + "," + i2[k]);
                            Log.i("zx12dt", s + i2[j] + "," + i2[k] + "," + i2[k]);
                        }
                    }
                }
            }
        } else if (a == 2) {
            for (int i = 0; i < i1.length; i++) {
                if (i1[i] != -1) {
                    for (int j = 0; j < i2.length; j++) {
                        if (i2[j] != -1) {
                            Log.i("zx12dt", s + i1[i] + "," + i2[j]);
                        }
                    }
                }
            }
            for (int j = 0; j < i2.length; j++) {
                if (i2[j] != -1) {
                    Log.i("zx12dt", s + i2[j] + "," + i2[j]);
                }
            }
        }
    }

    /**
     * 组选12重胆拖
     */
    public static void zx12cdt(int[] i1, int[] i2) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i2.length; j++) {
                    if (i2[j] != -1) {
                        for (int k = j + 1; k < i2.length; k++) {
                            if (i2[k] != -1) {
                                Log.i("zx12cdt", i1[i] + "," + i1[i] + "," + i2[j] + "," + i2[k]);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * ]
     * 组选6单式
     *
     * @param i1
     * @param i2
     * @param i3
     * @param i4
     */
    public static void zx6ds(int[] i1, int[] i2, int[] i3, int[] i4) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i2.length; j++) {
                    if (i2[j] != -1) {
                        for (int k = 0; k < i3.length; k++) {
                            if (i3[k] != -1) {
                                for (int x = 0; x < i4.length; x++) {
                                    if (i4[x] != -1) {
                                        Log.i("zx6ds", i1[i] + "," + i2[j] + "," + i3[k] + "," + i4[x]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 组选6复式
     *
     * @param i1
     */
    public static void zx6fs(int[] i1) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = i + 1; j < i1.length; j++) {
                    if (i1[j] != -1) {
                        Log.i("zx6ds", i1[i] + "," + i1[i] + "," + i1[j] + "," + i1[j]);
                    }
                }
            }
        }
    }

    /**
     * 组选6胆拖
     *
     * @param i1
     * @param i2
     */
    public static void zx6dt(int[] i1, int[] i2) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i2.length; j++) {
                    if (i2[j] != -1) {
                        Log.i("zx6dt", i1[i] + "," + i1[i] + "," + i2[j] + "," + i2[j]);
                    }
                }
            }
        }
    }

    /**
     * ]
     * 组选4单式
     *
     * @param i1
     * @param i2
     * @param i3
     * @param i4
     */
    public static void zx4ds(int[] i1, int[] i2, int[] i3, int[] i4) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i2.length; j++) {
                    if (i2[j] != -1) {
                        for (int k = 0; k < i3.length; k++) {
                            if (i3[k] != -1) {
                                for (int x = 0; x < i4.length; x++) {
                                    if (i4[x] != -1) {
                                        Log.i("zx4ds", i1[i] + "," + i2[j] + "," + i3[k] + "," + i4[x]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 组选4复式
     *
     * @param i1
     */
    public static void zx4fs(int[] i1) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i1.length; j++) {
                    if (i1[j] != -1 && i != j) {
                        Log.i("zx4fs", i1[i] + "," + i1[i] + "," + i1[i] + "," + i1[j]);
                    }
                }
            }
        }
    }

    /**
     * 组选4胆拖
     *
     * @param i1
     * @param i2
     */
    public static void zx4dt(int[] i1, int[] i2) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i2.length; j++) {
                    if (i2[j] != -1) {
                        Log.i("zx4dt", i1[i] + "," + i1[i] + "," + i1[i] + "," + i2[j]);
                        Log.i("zx4dt", i1[i] + "," + i2[j] + "," + i2[j] + "," + i2[j]);
                    }
                }
            }
        }
    }

    /**
     * 组选4重胆拖
     *
     * @param i1
     * @param i2
     */
    public static void zx4cdt(int[] i1, int[] i2) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i2.length; j++) {
                    if (i2[j] != -1) {
                        Log.i("zx4cdt", i1[i] + "," + i1[i] + "," + i1[i] + "," + i2[j]);
                    }
                }
            }
        }
    }

    /**
     * 前三组三
     *
     * @param i1
     */
    public static void q3z3(int[] i1) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = i; j < i1.length; j++) {
                    if (i1[j] != -1 && i != j) {
                        Log.i("q3z3", i1[i] + "," + i1[i] + "," + i1[j] + "," + " ");
                        Log.i("q3z3", i1[i] + "," + i1[j] + "," + i1[j] + "," + " ");
                        Log.i("q3z3", i1[i] + "," + i1[j] + "," + i1[i] + "," + " ");
                        Log.i("q3z3", i1[j] + "," + i1[j] + "," + i1[i] + "," + " ");
                        Log.i("q3z3", i1[j] + "," + i1[i] + "," + i1[i] + "," + " ");
                        Log.i("q3z3", i1[j] + "," + i1[i] + "," + i1[j] + "," + " ");
                    }
                }
            }
        }
    }

    /**
     * 前3组6
     *
     * @param i1
     */
    public static void q3z6(int[] i1) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = 0; j < i1.length; j++) {
                    if (i1[j] != -1) {
                        for (int k = 0; k < i1.length; k++) {
                            if (i1[k] != -1 && i != j && j != k && i != k) {
                                Log.i("q3z6", i1[i] + "," + i1[j] + "," + i1[k] + "," + " ");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 任三组三
     */
    public static void r3z3(int[] i1, int z, int y, int w, int d) {
        Log.i("r3z3", z + "" + y + "" + w + "" + d);
        if (z == 0) {//自由泳未选
            for (int i = 0; i < i1.length; i++) {
                if (i1[i] != -1) {
                    for (int j = i + 1; j < i1.length; j++) {
                        if (i1[j] != -1) {
                            Log.i("r3z3", " " + "," + i1[i] + "," + i1[i] + "," + i1[j]);
                            Log.i("r3z3", " " + "," + i1[i] + "," + i1[j] + "," + i1[i]);
                            Log.i("r3z3", " " + "," + i1[i] + "," + i1[j] + "," + i1[j]);
                            Log.i("r3z3", " " + "," + i1[j] + "," + i1[j] + "," + i1[i]);
                            Log.i("r3z3", " " + "," + i1[j] + "," + i1[i] + "," + i1[j]);
                            Log.i("r3z3", " " + "," + i1[j] + "," + i1[i] + "," + i1[i]);
                        }
                    }
                }
            }
        }
        if (y == 0) {//仰泳未选
            for (int i = 0; i < i1.length; i++) {
                if (i1[i] != -1) {
                    for (int j = i + 1; j < i1.length; j++) {
                        if (i1[j] != -1) {
                            Log.i("r3z3", i1[i] + "," + " " + "," + i1[i] + "," + i1[j]);
                            Log.i("r3z3", i1[i] + "," + " " + "," + i1[j] + "," + i1[i]);
                            Log.i("r3z3", i1[i] + "," + " " + "," + i1[j] + "," + i1[j]);
                            Log.i("r3z3", i1[j] + "," + " " + "," + i1[j] + "," + i1[i]);
                            Log.i("r3z3", i1[j] + "," + " " + "," + i1[i] + "," + i1[j]);
                            Log.i("r3z3", i1[j] + "," + " " + "," + i1[i] + "," + i1[i]);
                        }
                    }
                }
            }
        }
        if (w == 0) {//蛙泳未选
            for (int i = 0; i < i1.length; i++) {
                if (i1[i] != -1) {
                    for (int j = i + 1; j < i1.length; j++) {
                        if (i1[j] != -1) {
                            Log.i("r3z3", i1[i] + "," + i1[i] + "," + " " + "," + i1[j]);
                            Log.i("r3z3", i1[i] + "," + i1[j] + "," + " " + "," + i1[i]);
                            Log.i("r3z3", i1[i] + "," + i1[j] + "," + " " + "," + i1[j]);
                            Log.i("r3z3", i1[j] + "," + i1[j] + "," + " " + "," + i1[i]);
                            Log.i("r3z3", i1[j] + "," + i1[i] + "," + " " + "," + i1[j]);
                            Log.i("r3z3", i1[j] + "," + i1[i] + "," + " " + "," + i1[i]);
                        }
                    }
                }
            }
        }
        if (d == 0) {//蝶泳未选
            for (int i = 0; i < i1.length; i++) {
                if (i1[i] != -1) {
                    for (int j = i + 1; j < i1.length; j++) {
                        if (i1[j] != -1) {
                            Log.i("r3z3", i1[i] + "," + i1[j] + "," + i1[i] + "," + " ");
                            Log.i("r3z3", i1[i] + "," + i1[i] + "," + i1[j] + "," + " ");
                            Log.i("r3z3", i1[i] + "," + i1[j] + "," + i1[j] + "," + " ");
                            Log.i("r3z3", i1[j] + "," + i1[i] + "," + i1[j] + "," + " ");
                            Log.i("r3z3", i1[j] + "," + i1[j] + "," + i1[i] + "," + " ");
                            Log.i("r3z3", i1[j] + "," + i1[i] + "," + i1[i] + "," + " ");
                        }
                    }
                }
            }
        }
    }

    /**
     * 任三组六
     */
    public static void r3z6(int[] i1, int z, int y, int w, int d) {
        if (z == 0) {//自由泳未选
            for (int i = 0; i < i1.length; i++) {
                if (i1[i] != -1) {
                    for (int j = 0; j < i1.length; j++) {
                        if (i1[j] != -1 && i != j) {
                            Log.i("r3z6", " " + "," + i1[i] + "," + i1[i] + "," + i1[j]);
                        }
                    }
                }
            }
        }
        if (y == 0) {//仰泳未选
            for (int i = 0; i < i1.length; i++) {
                if (i1[i] != -1) {
                    for (int j = 0; j < i1.length; j++) {
                        if (i1[j] != -1 && i != j) {
                            Log.i("r3z6", i1[i] + "," + " " + "," + i1[i] + "," + i1[j]);
                        }
                    }
                }
            }
        }
        if (w == 0) {//蛙泳未选
            for (int i = 0; i < i1.length; i++) {
                if (i1[i] != -1) {
                    for (int j = 0; j < i1.length; j++) {
                        if (i1[j] != -1 && i != j) {
                            Log.i("r3z6", i1[i] + "," + i1[i] + "," + " " + "," + i1[j]);
                        }
                    }
                }
            }
        }
        if (d == 0) {//蝶泳未选
            for (int i = 0; i < i1.length; i++) {
                if (i1[i] != -1) {
                    for (int j = 0; j < i1.length; j++) {
                        if (i1[j] != -1 && i != j) {
                            Log.i("r3z6", i1[i] + "," + i1[j] + "," + i1[i] + "," + " ");
                        }
                    }
                }
            }
        }
    }

    /**
     * 组选三码全包
     */
    public static void zx3qb(int[] i1) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = i + 1; j < i1.length; j++) {
                    if (i1[j] != -1) {
                        for (int k = j + 1; k < i1.length; k++) {
                            if (i1[k] != -1) {
                                for (int x = 1; x < 9; x++) {
                                    Log.i("zx3qb", i1[i] + "," + i1[j] + "," + i1[k] + "," + x);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * 组选2码全包
     *
     * @param i1
     */
    public static void zx2qb(int[] i1) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                for (int j = i + 1; j < i1.length; j++) {
                    if (i1[j] != -1) {
                        for (int k = 1; k < 9; k++) {
                            for (int x = k; x < 9; x++) {
                                Log.i("zx2qb", i1[i] + "," + i1[j] + "," + k + "," + x);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 选四全包
     *
     * @param i1
     */
    public static void x4qb(int a, int[] i1, int[] i2) {
        if (a == 0) {//选四不重全包
            for (int i = 0; i < i1.length; i++) {
                if (i1[i] != -1) {
                    for (int j = 0; j < i1.length; j++) {
                        if (i1[j] != -1) {
                            for (int k = 0; k < i1.length; k++) {
                                if (i1[k] != -1) {
                                    for (int l = 0; l < i1.length; l++) {
                                        if (i1[l] != -1 && i != j && i != k && i != l &&
                                                j != k && j != l && k != l) {
                                            Log.i("x4qb", i1[i] + "," + i1[j] + "," + i1[k] + "," + i1[l]);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } else if (a == 1) {//选四一对全包
            String s = "";
            for (int i = 0; i < i1.length; i++) {
                if (i1[i] != -1) {
                    s = i1[i] + "";
                }
            }
            for (int j = 0; j < i2.length; j++) {
                if (i2[j] != -1) {
                    for (int k = j + 1; k < i2.length; k++) {
                        if (i2[k] != -1) {
                            Log.i("x4qb", s + "," + s + "," + i2[j] + "," + i2[k]);
                            Log.i("x4qb", s + "," + s + "," + i2[k] + "," + i2[j]);
                            Log.i("x4qb", s + "," + i2[j] + "," + s + "," + i2[k]);
                            Log.i("x4qb", s + "," + i2[k] + "," + s + "," + i2[j]);
                            Log.i("x4qb", s + "," + i2[k] + "," + i2[j] + "," + s);
                            Log.i("x4qb", s + "," + i2[j] + "," + i2[k] + "," + s);

                            Log.i("x4qb", i2[j] + "," + s + "," + s + "," + i2[k]);
                            Log.i("x4qb", i2[j] + "," + s + "," + i2[k] + "," + s);
                            Log.i("x4qb", i2[j] + "," + i2[k] + "," + s + "," + s);

                            Log.i("x4qb", i2[k] + "," + s + "," + s + "," + i2[j]);
                            Log.i("x4qb", i2[k] + "," + s + "," + i2[j] + "," + s);
                            Log.i("x4qb", i2[k] + "," + i2[j] + "," + s + "," + s);
                        }
                    }
                }
            }
        } else if (a == 2) {//选四两对全包
            for (int i = 0; i < i1.length; i++) {
                if (i1[i] != -1) {
                    for (int j = i + 1; j < i1.length; j++) {
                        if (i1[j] != -1) {
                            Log.i("x4qb", i1[i] + "," + i1[i] + "," + i1[j] + "," + i1[j]);
                            Log.i("x4qb", i1[i] + "," + i1[j] + "," + i1[i] + "," + i1[j]);
                            Log.i("x4qb", i1[i] + "," + i1[j] + "," + i1[j] + "," + i1[i]);

                            Log.i("x4qb", i1[j] + "," + i1[j] + "," + i1[i] + "," + i1[i]);
                            Log.i("x4qb", i1[j] + "," + i1[i] + "," + i1[j] + "," + i1[i]);
                            Log.i("x4qb", i1[j] + "," + i1[i] + "," + i1[i] + "," + i1[j]);
                        }
                    }
                }
            }
        } else if (a == 3) {//选四三条全包
            for (int i = 0; i < i1.length; i++) {
                if (i1[i] != -1) {
                    for (int j = 0; j < i2.length; j++) {
                        if (i2[j] != -1) {
                            Log.i("x4qb", i1[i] + "," + i1[i] + "," + i1[i] + "," + i2[j]);
                            Log.i("x4qb", i1[i] + "," + i1[i] + "," + i2[j] + "," + i1[i]);
                            Log.i("x4qb", i1[i] + "," + i2[j] + "," + i1[i] + "," + i1[i]);
                            Log.i("x4qb", i2[j] + "," + i1[i] + "," + i1[i] + "," + i1[i]);
                        }
                    }
                }
            }
        }

    }

    /**
     * 重号全包
     *
     * @param i1
     */
    public static void chqb(int[] i1) {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != -1) {
                Log.i("chqb", i1[i] + "," + i1[i] + "," + " " + "," + " ");
                Log.i("chqb", i1[i] + "," + " " + "," + i1[i] + "," + " ");
                Log.i("chqb", i1[i] + "," + " " + "," + " " + "," + i1[i]);

                Log.i("chqb", " " + "," + i1[i] + "," + i1[i] + "," + " ");
                Log.i("chqb", " " + "," + i1[i] + "," + " " + "," + i1[i]);

                Log.i("chqb", " " + "," + " " + "," + i1[i] + "," + i1[i]);

                Log.i("chqb", i1[i] + "," + i1[i] + "," + i1[i] + "," + i1[i]);

                Log.i("chqb", " " + "," + i1[i] + "," + i1[i] + "," + i1[i]);
                Log.i("chqb", i1[i] + "," + " " + "," + i1[i] + "," + i1[i]);
                Log.i("chqb", i1[i] + "," + i1[i] + "," + " " + "," + i1[i]);
                Log.i("chqb", i1[i] + "," + i1[i] + "," + i1[i] + "," + " ");
            }
        }
    }
}
