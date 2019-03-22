package com.bw.movie.api;

public class Api {
    //base
    public static final String BASE_URL = "http://mobile.bwstudent.com/";
    //注册
    public static final String REGIST_URL = "movieApi / user / v1 / registerUser";
    //登录
    public static final String LOGIN_URL = "movieApi/user/v1/login";
    //查询推荐影院
    public static final String RECOMMEND_URL = "movieApi/cinema/v1/findRecommendCinemas";
    //查询附近影院
    public static final String NEARBY_URL = "movieApi/cinema/v1/findNearbyCinemas";
    //根据用户ID查询用户信息
    public static final String FINDUSERID_URL = "movieApi/user/v1/verify/getUserInfoByUserId";

    //查询热门电影列表
    public static final String FILM_RE_URL = "movieApi/movie/v1/findHotMovieList";
    //查询正在上映电影列表
    public static final String FILM_ZHENG_URL = "movieApi/movie/v1/findReleaseMovieList";
    //查询即将上映电影列表
    public static final String FILM_JI_URL = "movieApi/movie/v1/findComingSoonMovieList";
    //查看电影详情
    public static final String FILM_DETAILS = "movieApi/movie/v1/findMoviesDetail";
    //.查询影片评论
    public static final String FILM_REVIEW = "movieApi/movie/v1/findAllMovieComment";
    

    //上传用户头像
    public static final String HEADICON_URL = "movieApi/user/v1/verify/uploadHeadPic";
    //查询电影信息明细
    public static final String RECOMMENDDETAILS_URL = "movieApi/cinema/v1/findCinemaInfo";
    //14.根据影院ID查询该影院当前排期的电影列表
    public static final String FILMFROMID_URL = "movieApi/movie/v1/findMovieListByCinemaId";
    //15.根据电影ID和影院ID查询电影排期列表
    public static final String MOVIEIDANDFILMID_URL = "movieApi/movie/v1/findMovieScheduleList";
    //添加评论
    public static final String FILM_COMMENT = "movieApi/movie/v1/verify/movieComment";
    //根据电影ID查询当前排片该电影的影院列表
    public static final String CinemasListByMovieId = "movieApi/movie/v1/findCinemasListByMovieId";
    //8.查询影院用户评论列表
    public static final String CINEMACOMMENT_URL = "movieApi/cinema/v1/findAllCinemaComment";
    //10.影院评论点赞
    public static final String CINEMAPRAISE_URL = "movieApi/cinema/v1/verify/cinemaCommentGreat";
    //关注电影
    public static final String  FOLLOW_MOVIE = "movieApi/movie/v1/verify/followMovie";

}