package com.bw.movie.api;



import com.bw.movie.bean.FilmFromIdBean;
import com.bw.movie.bean.FilmDetailsBean;
import com.bw.movie.bean.FilmReviewBean;
import com.bw.movie.bean.FindInfoBean;
import com.bw.movie.bean.JiFilmBean;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.MovieIdAndFilmBean;
import com.bw.movie.bean.NearbyCinemasBean;
import com.bw.movie.bean.ReFilmBean;
import com.bw.movie.bean.RecommendCinemasBean;
import com.bw.movie.bean.RecommendDetailsBean;
import com.bw.movie.bean.RegistBean;
import com.bw.movie.bean.ShangFilmBean;
import com.bw.movie.bean.UserHeadIconBean;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
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
    Observable<RecommendCinemasBean> recommend(@Url String url, @HeaderMap Map<String,Object> headMap, @QueryMap Map<String,Object> parms);
    //查询附近影院
    //查询推荐影院
    @GET
    Observable<NearbyCinemasBean> nearby(@Url String url, @HeaderMap Map<String,Object> headMap, @QueryMap Map<String,Object> parms);
    //根据用户ID查询用户信息
    @GET
    Observable<FindInfoBean> finduserid(@Url String url, @HeaderMap Map<String,Object> headMap);
    //热门电影
    @GET
    Observable<ReFilmBean> getReData(@Url String url, @HeaderMap HashMap<String,Object> headMap, @QueryMap HashMap<String,Object> parms);
    //正在上映
    @GET
    Observable<ShangFilmBean> getShangData(@Url String url, @HeaderMap HashMap<String,Object> headMap,@QueryMap HashMap<String,Object> parms);
    //即将上映
    @GET
    Observable<JiFilmBean> getJiData(@Url String url, @HeaderMap HashMap<String,Object> headMap, @QueryMap HashMap<String,Object> parms);
    //电影详情
    @GET
    Observable<FilmDetailsBean> getDetailsData(@Url String url, @HeaderMap HashMap<String,Object> headMap, @QueryMap HashMap<String,Object> parms);
    //影片评论
    @GET
    Observable<FilmReviewBean> getReviewData(@Url String url, @HeaderMap HashMap<String,Object> headMap, @QueryMap HashMap<String,Object> parms);


    //上传用户头像
    @POST
    Observable<UserHeadIconBean> headicon(@Url String url, @HeaderMap Map<String,Object> headMap, @Body MultipartBody multipartBody);
    //查询电影信息明细
    @GET
    Observable<RecommendDetailsBean> details(@Url String url, @HeaderMap Map<String,String> headMap,@Query("cinemaId") String cinemaId);
    //14.根据影院ID查询该影院当前排期的电影列表
    @GET
    Observable<FilmFromIdBean> filmfromid(@Url String url,@Query("cinemaId") String cinemaId);
    //15.根据电影ID和影院ID查询电影排期列表
    @GET
    Observable<MovieIdAndFilmBean> movieandfilmid(@Url String url,@Query("movieId") String movieId,@Query("cinemasId")String cinemasId);
}