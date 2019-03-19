package com.bw.movie.activity.filmdetails;

import android.app.Activity;
import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.FilmDetailsBean;
import com.bw.movie.bean.FilmReviewBean;
import com.bw.movie.mvp.BasePresenterImpl;
import com.bw.movie.utils.RetrofitManager;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FilmDetailsPresenter extends BasePresenterImpl<FilmDetailsContract.View> implements FilmDetailsContract.Presenter{

    /**
     * 电影详情
     * @param headMap
     * @param prams
     */
    @Override
    public void getPresenterData(HashMap<String, Object> headMap, HashMap<String, Object> prams) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.getDetailsData(Api.FILM_DETAILS,headMap,prams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FilmDetailsBean>() {
                    @Override
                    public void accept(FilmDetailsBean filmDetailsBean) throws Exception {
                        mView.getFilmDetailsViewData(filmDetailsBean);
                    }
                });

    }

    /**
     * 影片评论
     * @param headMap
     * @param prams
     */
    @Override
    public void getReviewPresenterData(HashMap<String, Object> headMap, HashMap<String, Object> prams) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.getReviewData(Api.FILM_REVIEW,headMap,prams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FilmReviewBean>() {
                    @Override
                    public void accept(FilmReviewBean filmReviewBean) throws Exception {
                        mView.getFilmReviewData(filmReviewBean);
                    }
                });
    }
}
