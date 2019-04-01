package com.bw.movie.activity.filmsearch;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.HashMap;
import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FilmSearchContract {
    interface View extends BaseView {

        //关注电影
        void getFlowllMovieData(Object object);
        //取消关注
        void cancelFollowMovieData(Object object);
    }

    interface  Presenter extends BasePresenter<View> {
        //关注电影
        void getFlowllMoviePresenter(Map<String,Object> headMap, String movieId);
        //取消关注
        void cancelFollowMoviePresenter(HashMap<String,Object> headMap, HashMap<String,Object> prams);
    }
}
