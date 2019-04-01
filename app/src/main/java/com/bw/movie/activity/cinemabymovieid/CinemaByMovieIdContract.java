package com.bw.movie.activity.cinemabymovieid;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CinemaByMovieIdContract {
    interface View extends BaseView {
        void getViewData(Object object);
        void AttentionView(Object obj);
        void CancelAttentionView(Object obj);
    }

    interface  Presenter extends BasePresenter<View> {
        void getPresenterData(Map<String,Object> headMap,String movieId);
        void AttentionPresenter(Map<String,Object> headMap, String cinemaId);
        void CancelAttentionPresenter(Map<String,Object> headMap,String cinemaId);
    }
}
