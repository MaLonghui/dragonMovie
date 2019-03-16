package com.bw.movie.app;

import android.app.Application;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //设置磁盘缓存
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryName("images")
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                .build();
        //设置磁盘缓存的配置,生成配置文件
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(this,config);
    }
}
