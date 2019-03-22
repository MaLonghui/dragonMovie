package com.bw.movie.activity.movieschedulelist;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MovieScheduleListContract {
    interface View extends BaseView {
        void getViewData(Object object);
    }

    interface  Presenter extends BasePresenter<View> {
        void getPresenterData(String movieId,String cinemasId);
    }
}
