package com.juyikeji.caipiao.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.juyikeji.caipiao.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by jyg on 2016/3/1.
 */
public class MyApplication extends Application{

    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance=this;
        //初始化推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        /**
         * 初始化异步图片加载
         */
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 获取Context对象
     * @return
     */
    public static Context getInstance() {
        return instance;
    }

    /**
     * ImageLoader加载设置默认图片
     *
     * @return
     */
    public static DisplayImageOptions getDefaultOptions() {
        // 设置图片加载的属性
        DisplayImageOptions.Builder b = new DisplayImageOptions.Builder();
        b.showImageForEmptyUri(R.mipmap.no_pic_proc);
        b.showImageOnFail(R.mipmap.no_pic_proc);
        b.showImageOnLoading(R.mipmap.loading_proc);
        b.resetViewBeforeLoading(Boolean.TRUE);
        b.cacheOnDisk(Boolean.TRUE);
        b.cacheInMemory(Boolean.TRUE);
        b.imageScaleType(ImageScaleType.EXACTLY_STRETCHED);
        return b.bitmapConfig(Bitmap.Config.RGB_565).build();
    }
}
