package com.bw.movie.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private final String baseUrl;
    private static RetrofitManager instance;
    private static OkHttpClient okHttpClient;

    //静态块
    static {
        getOkHttpClient();
    }

    private Retrofit retrofit;

    private RetrofitManager(String baseUrl){
        this.baseUrl = baseUrl;
        initRetrofit();
    }


    public static synchronized RetrofitManager getInstance(String baseUrl){
        if (instance==null){
            synchronized (RetrofitManager.class){
                if (null==instance){
                    instance = new RetrofitManager(baseUrl);
                }
            }
        }
        return instance;
    }

    public static OkHttpClient getOkHttpClient(){

        if (okHttpClient==null){
            synchronized (OkHttpClient.class){
                if (null==okHttpClient){
                    okHttpClient = new OkHttpClient.Builder()
                            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .connectTimeout(3000,TimeUnit.SECONDS)
                            .writeTimeout(3000,TimeUnit.SECONDS)
                            .readTimeout(3000,TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    private void initRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

    }

    public <T> T setCreate(Class<T> meq){
        return retrofit.create(meq);
    }

}
