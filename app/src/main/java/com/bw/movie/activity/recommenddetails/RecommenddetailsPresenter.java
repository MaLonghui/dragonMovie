package com.bw.movie.activity.recommenddetails;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.CinemaCommentBean;
import com.bw.movie.bean.CinemaPraiseBean;
import com.bw.movie.bean.FilmFromIdBean;
import com.bw.movie.bean.MovieIdAndFilmBean;
import com.bw.movie.bean.RecommendDetailsBean;
import com.bw.movie.mvp.BasePresenterImpl;
import com.bw.movie.utils.RetrofitManager;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RecommenddetailsPresenter extends BasePresenterImpl<RecommenddetailsContract.View> implements RecommenddetailsContract.Presenter{

    @Override
    public void recommendDetailsPresenter(Map<String, Object> headMap, String cinemaId) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.details(Api.RECOMMENDDETAILS_URL,headMap,cinemaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RecommendDetailsBean>() {
                    @Override
                    public void accept(RecommendDetailsBean recommendDetailsBean) throws Exception {
                        mView.recommendDetailsView(recommendDetailsBean);
                    }
                });
    }

    @Override
    public void filmFromIdPresenter(String cinemaId) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.filmfromid(Api.FILMFROMID_URL,cinemaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FilmFromIdBean>() {
                    @Override
                    public void accept(FilmFromIdBean filmFromIdBean) throws Exception {
                        mView.filmFromIdView(filmFromIdBean);
                    }
                });
    }

    @Override
    public void movieIdAndfilmIdPresenter(String movieId, String cinemasId) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.movieandfilmid(Api.MOVIEIDANDFILMID_URL,movieId,cinemasId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieIdAndFilmBean>() {
                    @Override
                    public void accept(MovieIdAndFilmBean movieIdAndFilmBean) throws Exception {
                        mView.movieIdAndfilmIdView(movieIdAndFilmBean);
                    }
                });
    }

    @Override
    public void cinemaCommentPresenter(Map<String, Object> headMap, Map<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.cinemacomment(Api.CINEMACOMMENT_URL,headMap,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CinemaCommentBean>() {
                    @Override
                    public void accept(CinemaCommentBean cinemaCommentBean) throws Exception {
                        mView.cinemaCommentView(cinemaCommentBean);
                    }
                });

    }

    @Override
    public void cinemaPriasePresenter(Map<String, Object> headMap, String commentId) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.cinemapraise(Api.CINEMAPRAISE_URL,headMap,commentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CinemaPraiseBean>() {
                    @Override
                    public void accept(CinemaPraiseBean cinemaPraiseBean) throws Exception {
                        mView.cinemaPriaseView(cinemaPraiseBean);
                    }
                });
    }
}
