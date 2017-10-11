package com.juyikeji.caipiao.bean;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class Bill {
    //账单类别
    private String category;
    //金额
    private String money;
    //日期
    private String date;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
