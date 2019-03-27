package com.bw.movie.app;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.SharedPreferences;
import android.os.Environment;
import android.widget.Toast;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MyApp extends Application {


    private static Context context;
    private SharedPreferences sp;
    public static String APP_ID="wxb3852e6a6b7d9516";
    public static IWXAPI api;

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

        //sp
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean a = sp.getBoolean("自动登录", false);
        SharedPreferences.Editor edit = sp.edit();
        context = getApplicationContext();
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.registerApp(APP_ID);
        if (!a){
            edit.putString("userId","");
            edit.putString("sessionId","");
        }
        edit.commit();
    }


    //拦截器
    public static Context getmContext() {
        return context;
    }
}