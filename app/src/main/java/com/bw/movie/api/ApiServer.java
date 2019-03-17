package com.bw.movie.api;


import com.bw.movie.bean.JiFilmBean;
import com.bw.movie.bean.ReFilmBean;
import com.bw.movie.bean.ShangFilmBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiServer {

    //热门电影
    @GET
    Observable<ReFilmBean> getReData(@Url String url, @HeaderMap HashMap<String,Object> headMap,@QueryMap HashMap<String,Object> parms);
    //正在上映
    @GET
    Observable<ShangFilmBean> getShangData(@Url String url, @HeaderMap HashMap<String,Object> headMap,@QueryMap HashMap<String,Object> parms);
    //即将上映
    @GET
    Observable<JiFilmBean> getJiData(@Url String url, @HeaderMap HashMap<String,Object> headMap, @QueryMap HashMap<String,Object> parms);

}
