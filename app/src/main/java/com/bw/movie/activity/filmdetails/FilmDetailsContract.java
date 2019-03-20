package com.bw.movie.activity.filmdetails;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.HashMap;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FilmDetailsContract {
    interface View extends BaseView {
        //电影详情
        void getFilmDetailsViewData(Object object);
        //评论
        void getFilmReviewData(Object object);
        //添加评论
        void getFilmCommentData(Object object);


    }

    interface  Presenter extends BasePresenter<View> {
        //电影详情
        void getPresenterData(HashMap<String,Object> headMap,HashMap<String,Object> prams);
        //影片评论
        void getReviewPresenterData(HashMap<String,Object> headMap,HashMap<String,Object> prams);
        //添加评论
        void getFilmCommentPresenter(HashMap<String,Object> headMap,HashMap<String,Object> prams);
    }
}
