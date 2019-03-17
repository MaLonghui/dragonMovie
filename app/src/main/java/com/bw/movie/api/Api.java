package com.bw.movie.api;

public class Api {
    //base
    public static final String BASE_URL = "http://mobile.bwstudent.com/";
    //注册
    public static final String REGIST_URL = "movieApi / user / v1 / registerUser";
    //登录
    public static final String LOGIN_URL = "movieApi / user / v1 / login";

    //查询热门电影列表
    public static final String FILM_RE_URL = "movieApi/movie/v1/findHotMovieList";
    //查询正在上映电影列表
    public static final String FILM_ZHENG_URL = "movieApi/movie/v1/findReleaseMovieList";
    //查询即将上映电影列表
    public static final String FILM_JI_URL = "movieApi/movie/v1/findComingSoonMovieList";

}
