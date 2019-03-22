package com.bw.movie.activity.movieschedulelist;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.MovieIdAndFilmBean;
import com.bw.movie.mvp.BasePresenterImpl;
import com.bw.movie.utils.RetrofitManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MovieScheduleListPresenter extends BasePresenterImpl<MovieScheduleListContract.View> implements MovieScheduleListContract.Presenter{

    @Override
    public void getPresenterData(String movieId, String cinemasId) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.movieandfilmid(Api.MOVIEIDANDFILMID_URL,movieId,cinemasId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieIdAndFilmBean>() {
                    @Override
                    public void accept(MovieIdAndFilmBean movieIdAndFilmBean) throws Exception {
                        mView.getViewData(movieIdAndFilmBean);
                    }
                });
    }
}
