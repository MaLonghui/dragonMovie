package com.bw.movie.api;


import com.bw.movie.bean.JiFilmBean;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.ReFilmBean;
import com.bw.movie.bean.RegistBean;
import com.bw.movie.bean.ShangFilmBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiServer {

    //注册
    @POST
    @FormUrlEncoded
    Observable<RegistBean> regist(@Url String url, @FieldMap Map<String,Object> parms);
    //登录
    @POST
    @FormUrlEncoded
    Observable<LoginBean> login(@Url String url, @Field("phone")String phone,@Field("pwd")String pwd);
    //查询推荐影院
    @GET
    Observable<RecommendCinemasBean> recommend(@Url String url, @HeaderMap Map<String,Object> headMap,@QueryMap Map<String,Object> parms);
    //查询附近影院
    //查询推荐影院
    @GET
    Observable<NearbyCinemasBean> nearby(@Url String url, @HeaderMap Map<String,Object> headMap, @QueryMap Map<String,Object> parms);
    //根据用户ID查询用户信息
    @GET
    Observable<FindInfoBean> finduserid(@Url String url, @HeaderMap Map<String,Object> headMap
    
    //热门电影
    @GET
    Observable<ReFilmBean> getReData(@Url String url, @HeaderMap HashMap<String,Object> headMap, @QueryMap HashMap<String,Object> parms);
    //正在上映
    @GET
    Observable<ShangFilmBean> getShangData(@Url String url, @HeaderMap HashMap<String,Object> headMap, @QueryMap HashMap<String,Object> parms);
    //即将上映
    @GET
    Observable<JiFilmBean> getJiData(@Url String url, @HeaderMap HashMap<String,Object> headMap, @QueryMap HashMap<String,Object> parms);
}