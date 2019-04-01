package com.bw.movie.activity.filmsearch;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.CancelFollowMovieBean;
import com.bw.movie.bean.FlowllMovieBean;
import com.bw.movie.mvp.BasePresenterImpl;
import com.bw.movie.utils.RetrofitManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FilmSearchPresenter extends BasePresenterImpl<FilmSearchContract.View> implements FilmSearchContract.Presenter{

    @Override
    public void getFlowllMoviePresenter(Map<String, Object> headMap, String movieId) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.flowllMovie(Api.FOLLOW_MOVIE,headMap,movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FlowllMovieBean>() {
                    @Override
                    public void accept(FlowllMovieBean flowllMovieBean) throws Exception {
                        mView.getFlowllMovieData(flowllMovieBean);
                    }
                });
    }

    @Override
    public void cancelFollowMoviePresenter(HashMap<String, Object> headMap, HashMap<String, Object> prams) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.cancelFollowMovie(Api.CancelFollowMovie,headMap,prams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CancelFollowMovieBean>() {
                    @Override
                    public void accept(CancelFollowMovieBean cancelFollowMovieBean) throws Exception {
                        mView.cancelFollowMovieData(cancelFollowMovieBean);
                    }
                });
    }
}
