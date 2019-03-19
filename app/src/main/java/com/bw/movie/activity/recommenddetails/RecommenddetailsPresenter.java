package com.bw.movie.activity.recommenddetails;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
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
    public void recommendDetailsPresenter(Map<String, String> headMap, String cinemaId) {
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
}
