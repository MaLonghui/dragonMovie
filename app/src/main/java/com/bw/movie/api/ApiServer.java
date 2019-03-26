package com.bw.movie.api;


import com.bw.movie.bean.BuyTicketBean;
import com.bw.movie.bean.CancelFollowMovieBean;
import com.bw.movie.bean.CinemaAttentionBean;
import com.bw.movie.bean.CinemaByIdBean;
import com.bw.movie.bean.CinemaByNameBean;
import com.bw.movie.bean.CancelAttentionBean;
import com.bw.movie.bean.FeedBackBean;
import com.bw.movie.bean.MovieAttentionBean;
import com.bw.movie.bean.CinemaAttentionBean;
import com.bw.movie.bean.CinemaByIdBean;
import com.bw.movie.bean.FilmAttentionBean;
import com.bw.movie.bean.CancelFollowMovieBean;
import com.bw.movie.bean.CinemaAttentionBean;
import com.bw.movie.bean.CinemaByIdBean;
import com.bw.movie.bean.CinemaByNameBean;
import com.bw.movie.bean.FilmCommentBean;
import com.bw.movie.bean.FilmDetailsBean;
import com.bw.movie.bean.FilmReviewBean;

import com.bw.movie.bean.CinemaCommentBean;
import com.bw.movie.bean.CinemaPraiseBean;
import com.bw.movie.bean.FilmFromIdBean;
import com.bw.movie.bean.FindInfoBean;
import com.bw.movie.bean.FlowllMovieBean;
import com.bw.movie.bean.JiFilmBean;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.MovieCommentReply;
import com.bw.movie.bean.MovieIdAndFilmBean;
import com.bw.movie.bean.NearbyCinemasBean;
import com.bw.movie.bean.ReFilmBean;
import com.bw.movie.bean.RecommendCinemasBean;
import com.bw.movie.bean.RecommendDetailsBean;
import com.bw.movie.bean.RegistBean;
import com.bw.movie.bean.ShangFilmBean;
import com.bw.movie.bean.SignInBean;
import com.bw.movie.bean.SysMsgBean;
import com.bw.movie.bean.SysMsgStatusBean;
import com.bw.movie.bean.UpdateInfoBean;
import com.bw.movie.bean.UpdatePwdBean;
import com.bw.movie.bean.UserHeadIconBean;
import com.bw.movie.bean.WxLoginBean;

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
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @Multipart
    Observable<UserHeadIconBean> headicon(@Url String url, @HeaderMap Map<String,Object> headMap,@Part MultipartBody.Part image);
    //查询电影信息明细
    @GET
    Observable<RecommendDetailsBean> details(@Url String url, @HeaderMap Map<String,Object> headMap,@Query("cinemaId") String cinemaId);
    //14.根据影院ID查询该影院当前排期的电影列表
    @GET
    Observable<FilmFromIdBean> filmfromid(@Url String url,@Query("cinemaId") String cinemaId);
    //15.根据电影ID和影院ID查询电影排期列表
    @GET
    Observable<MovieIdAndFilmBean> movieandfilmid(@Url String url,@Query("movieId") String movieId,@Query("cinemasId")String cinemasId);
    //8.查询影院用户评论列表
    @GET
    Observable<CinemaCommentBean> cinemacomment(@Url String url,@HeaderMap Map<String,Object> headMap,@QueryMap Map<String,Object> parms);
    //10.影院评论点赞
    @POST
    @FormUrlEncoded
    Observable<CinemaPraiseBean> cinemapraise(@Url String url,@HeaderMap Map<String,Object> headMap,@Field("commentId")String commentId);

    //添加评论
    @POST
    @FormUrlEncoded
    Observable<FilmCommentBean> filmComment(@Url String url, @HeaderMap HashMap<String,Object> headMap, @FieldMap HashMap<String,Object> parms);
    //评论点赞
    @POST
    @FormUrlEncoded
    Observable<CinemaPraiseBean> movieCommentGreat(@Url String url,@HeaderMap Map<String,Object> headMap,@Field("commentId")String commentId);
    //回复评论
    @POST
    @FormUrlEncoded
    Observable<MovieCommentReply> commentReply(@Url String url,@HeaderMap Map<String,Object> headMap,@FieldMap Map<String, Object> prams);
    //根据电影ID查询当前排片该电影的影院列表
    @GET
    Observable<CinemaByIdBean> CinemasListByMovieId(@Url String url,@Query("movieId") String movieId);
    //4.修改用户信息
    @POST
    @FormUrlEncoded
    Observable<UpdateInfoBean> updateinfo(@Url String url,@HeaderMap Map<String,Object> headMap,@FieldMap Map<String,Object> parms);
    //6.关注影院
    @GET
    Observable<CinemaAttentionBean> cinemaattention(@Url String url,@HeaderMap Map<String,Object> headMap,@Query("cinemaId") String cinemaId);


   
    //关注电影
    @GET
    Observable<FlowllMovieBean> flowllMovie(@Url String url,@HeaderMap Map<String,Object> headMap,@Query("movieId") String movieId);
    //6.查询用户关注的影片列表
    @GET
    Observable<FilmAttentionBean> filmattention(@Url String url,@HeaderMap Map<String,Object> headMap,@QueryMap Map<String,Object> parms);
    //5.查询用户关注的影院信息
    @GET
    Observable<MovieAttentionBean> attentionlist(@Url String url, @HeaderMap Map<String,Object> headMap, @QueryMap Map<String,Object> parms);
    //1.意见反馈
    @POST
    @FormUrlEncoded
    Observable<FeedBackBean> feedback(@Url String url,@HeaderMap Map<String,Object> headMap,@Field("content") String content);
    //7.取消关注
    @GET
    Observable<CancelAttentionBean> cancelattention(@Url String url,@HeaderMap Map<String,Object> headMap,@Query("cinemaId") String cinemaId);
    //3.查询系统消息列表
    @GET
    Observable<SysMsgBean> sysmsg(@Url String url,@HeaderMap Map<String,Object> headMap,@QueryMap Map<String,Object> parms);
    //3.查询系统消息列表
    @GET
    Observable<SysMsgStatusBean> msgstatus(@Url String url,@HeaderMap Map<String,Object> headMap,@Query("id") String id);
    //根据电影名称模糊查询电影院
    @GET
    Observable<CinemaByNameBean> findAllCinemas(@Url String url,@QueryMap Map<String,Object> parms);
    //取消关注电影
    @GET
    Observable<CancelFollowMovieBean> cancelFollowMovie(@Url String url,@HeaderMap Map<String,Object> headMap,@QueryMap Map<String,Object> parms);
    @POST
    @FormUrlEncoded
    Observable<UpdatePwdBean> updatepwd(@Url String url,@HeaderMap Map<String,Object> headMap,@FieldMap Map<String,Object> parms);
    //用户签到
    @GET
    Observable<SignInBean> signin(@Url String url,@HeaderMap Map<String,Object> headMap);
    //微信登录
    @POST
    @FormUrlEncoded
    Observable<WxLoginBean> wxLogin(@Url String url,@Field("code") String code);
    //购票下单
    @POST
    @FormUrlEncoded
    Observable<BuyTicketBean> buyTicket(@Url String url,@HeaderMap Map<String,Object> headMap,@FieldMap Map<String,Object> parms);

}