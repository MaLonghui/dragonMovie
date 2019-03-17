package com.bw.movie.api;

public class Api {
    public static final String BASE_URL = "http://mobile.bwstudent.com/";
    //注册
    public static final String REGIST_URL = "movieApi/user/v1/registerUser";
    //登录
    public static final String LOGIN_URL = "movieApi/user/v1/login";
    //查询推荐影院
    public static final String RECOMMEND_URL = "movieApi/cinema/v1/findRecommendCinemas";
    //查询附近影院
    public static final String NEARBY_URL = "movieApi/cinema/v1/findNearbyCinemas";
    //根据用户ID查询用户信息
    public static final String FINDUSERID_URL = "movieApi/user/v1/verify/getUserInfoByUserId";

}
