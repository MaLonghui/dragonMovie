package com.bw.movie.activity.filmdetails;

import android.app.Activity;
import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.FilmCommentBean;
import com.bw.movie.bean.FilmDetailsBean;
import com.bw.movie.bean.FilmReviewBean;
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
 * 邮箱 784787081@qq.com
 */

public class FilmDetailsPresenter extends BasePresenterImpl<FilmDetailsContract.View> implements FilmDetailsContract.Presenter {

    /**
     * 电影详情
     *
     * @param headMap
     * @param prams
     */
    @Override
    public void getPresenterData(HashMap<String, Object> headMap, HashMap<String, Object> prams) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.getDetailsData(Api.FILM_DETAILS, headMap, prams)
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
     *
     * @param headMap
     * @param prams
     */
    @Override
    public void getReviewPresenterData(HashMap<String, Object> headMap, HashMap<String, Object> prams) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.getReviewData(Api.FILM_REVIEW, headMap, prams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FilmReviewBean>() {
                    @Override
                    public void accept(FilmReviewBean filmReviewBean) throws Exception {
                        mView.getFilmReviewData(filmReviewBean);
                    }
                });
    }

    /**
     * 添加评论
     *
     * @param headMap
     * @param prams
     */
    @Override
    public void getFilmCommentPresenter(HashMap<String, Object> headMap, HashMap<String, Object> prams) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.filmComment(Api.FILM_COMMENT, headMap, prams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FilmCommentBean>() {
                    @Override
                    public void accept(FilmCommentBean filmCommentBean) throws Exception {
                        mView.getFilmCommentData(filmCommentBean);
                    }
                });
    }

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
    public void cancelFollowMoviePresenter() {

    }
}
