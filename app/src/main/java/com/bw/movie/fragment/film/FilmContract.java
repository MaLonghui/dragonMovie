package com.bw.movie.fragment.film;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.HashMap;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FilmContract {

    interface View extends BaseView {
        //热门电影
        void getReMenViewData(Object object);
        //正在上映
        void getZhengViewData(Object object);
        //即将上映
        void getJiViewData(Object object);
    }

    interface  Presenter extends BasePresenter<View> {
        //热门电影
        void getReMenPresenter(HashMap<String,Object> headMap,HashMap<String,Object> parms);
        //正在上映
        void getZhengPresenter(HashMap<String,Object> headMap,HashMap<String,Object> parms);
        //即将上映
        void getJiPresenter(HashMap<String,Object> headMap,HashMap<String,Object> parms);
    }
}
