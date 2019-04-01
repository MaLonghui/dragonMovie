package com.bw.movie.activity.cinemabymovieid;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.CancelAttentionBean;
import com.bw.movie.bean.CinemaAttentionBean;
import com.bw.movie.bean.CinemaByIdBean;
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

public class CinemaByMovieIdPresenter extends BasePresenterImpl<CinemaByMovieIdContract.View> implements CinemaByMovieIdContract.Presenter{

    @Override
    public void getPresenterData(Map<String,Object> headMap,String movieId) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.CinemasListByMovieId(Api.CinemasListByMovieId,headMap,movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CinemaByIdBean>() {
                    @Override
                    public void accept(CinemaByIdBean cinemaByIdBean) throws Exception {
                        mView.getViewData(cinemaByIdBean);
                    }
                });
    }

    @Override
    public void AttentionPresenter(Map<String, Object> headMap, String cinemaId) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.cinemaattention(Api.CINEMAATTENTION_URL,headMap,cinemaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CinemaAttentionBean>() {
                    @Override
                    public void accept(CinemaAttentionBean cinemaAttentionBean) throws Exception {
                        mView.AttentionView(cinemaAttentionBean);
                    }
                });
    }

    @Override
    public void CancelAttentionPresenter(Map<String, Object> headMap, String cinemaId) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.cancelattention(Api.CANCELATTENTION_URL,headMap,cinemaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CancelAttentionBean>() {
                    @Override
                    public void accept(CancelAttentionBean cancelAttentionBean) throws Exception {
                        mView.CancelAttentionView(cancelAttentionBean);
                    }
                });
    }
}
