package com.lone.wjm.dating.Application;

import android.app.Application;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.avos.avoscloud.AVOSCloud;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by: Lone on 2016/5/30.
 * Contact: 4951048@qq.com
 */
public class MyApplication extends Application {
    SharedPreferences sp;
    SharedPreferences.Editor mEditor;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, "HlwakbmtCWWz0rPxfiMbO3LU-gzGzoHsz", "JGAio8Hc4KgcmX9BqPPBEbWy");
        sp = getSharedPreferences("DatingInfo",MODE_PRIVATE);
        mEditor = sp.edit();

        mRequestQueue = Volley.newRequestQueue(this);

        // 配置缓存路径
        File cacheDir = StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), "lone/images");
        // 1.完成ImageLoaderConfiguration的配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                // max width, max,height，即保存的每个缓存文件的最大长宽
                 .memoryCacheExtraOptions(300, 300)
                // 线程池内加载的数量
                .threadPoolSize(3)
                // 线程优先级
                .threadPriority(Thread.NORM_PRIORITY - 2)
                // 硬盘缓存50MB
                .discCacheSize(50 * 1024 * 1024)
                // 将保存的时候的URI名称用MD5
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用HASHCODE加密
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // 缓存的File数量
                .discCacheFileCount(100)
                // 自定义缓存路径
                .discCache(new UnlimitedDiscCache(cacheDir))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                // connectTimeout (5 s), readTimeout (30 s)超时时间
                .imageDownloader(
                        new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
                .build();
        ImageLoader.getInstance().init(config);
//     使用   ImageLoader.getInstance().displayImage("url", ImageView, Options.getListOptions());
    }


    public SharedPreferences.Editor getEditor() {
        return mEditor;
    }

    public SharedPreferences getSp() {
        return sp;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

}
