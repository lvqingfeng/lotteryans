package com.juyikeji.caipiao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.juyikeji.caipiao.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jyg on 2016/3/1
 * SlideShowView
 */
public class SharedPreferencesUtil {

    /**
     * 从SharedPreferences缓存获取登录状态
     */
    public static Map<String, Object> getSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences("bill", context.MODE_PRIVATE);
        // Boolean bool=sharedPreferences_read.getBoolean("boolean",
        // false);//默认false
        // float floa=sharedPreferences_read.getFloat("float", 0);//默认0
        boolean login = sharedPreferences.getBoolean("login", false);// 默认false
        String token = sharedPreferences.getString("token", "");// 默认null
        String count = sharedPreferences.getString("count", "");//默认""
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("login", login);
        map.put("token", token);
        map.put("count", count);
        return map;
    }
//
    /**
     * 缓存登录状态
     * @param context
     * @param result
     */
    public static void setSharedPreferences(Context context,Boolean result) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("bill",
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("login", result);
        editor.commit();
    }

    /**
     * 缓存登录状态
     * @param context
     * @param result
     */
    public static void setSharedPreferencess(Context context,Boolean result,String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("bill",
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("login", result);
        editor.putString("token", token);
        editor.commit();
    }

    /**
     * ImageLoader加载设置默认图片
     *
     * @return
     */
    public static DisplayImageOptions getDefaultOptions() {
        // 设置图片加载的属性
        DisplayImageOptions.Builder b = new DisplayImageOptions.Builder();
        b.showImageForEmptyUri(R.mipmap.touxiang);
        b.showImageOnFail(R.mipmap.touxiang);
//        b.showImageOnLoading(R.mipmap.loading_proc);
        b.resetViewBeforeLoading(Boolean.TRUE);
        b.cacheOnDisk(Boolean.TRUE);
        b.cacheInMemory(Boolean.TRUE);
        b.imageScaleType(ImageScaleType.EXACTLY_STRETCHED);
        return b.bitmapConfig(Bitmap.Config.RGB_565).build();
    }

//
//    /**
//     * 获取通讯录信息
//     * @param context
//     * @return
//     */
//    public static String get(Context context){
//        SharedPreferences sp = context.getSharedPreferences("list", context.MODE_PRIVATE);
//        String result = sp.getString("friend", "");
//        return result;
//    }

    /**
     * 清楚shared缓存
     */
    public static void clearShared(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("numb",
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
