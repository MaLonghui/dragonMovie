package com.bw.movie.fragment.cinema;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.CancelAttentionBean;
import com.bw.movie.bean.CinemaAttentionBean;
import com.bw.movie.bean.CinemaByNameBean;
import com.bw.movie.bean.NearbyCinemasBean;
import com.bw.movie.bean.RecommendCinemasBean;
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

public class CinemaPresenter extends BasePresenterImpl<CinemaContract.View> implements CinemaContract.Presenter{

    @Override
    public void recommendPresenter(Map<String, Object> headMap, Map<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.recommend(Api.RECOMMEND_URL,headMap,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RecommendCinemasBean>() {
                    @Override
                    public void accept(RecommendCinemasBean recommendCinemasBean) throws Exception {
                        mView.recommendView(recommendCinemasBean);
                    }
                });
    }

    @Override
    public void nearbyPresenter(Map<String, Object> headMap, Map<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.nearby(Api.NEARBY_URL,headMap,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NearbyCinemasBean>() {
                    @Override
                    public void accept(NearbyCinemasBean nearbyCinemasBean) throws Exception {
                        mView.nearbyView(nearbyCinemasBean);
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
    public void getCinemaByNamePresenterData(Map<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.findAllCinemas(Api.FINDAllCinemas, parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CinemaByNameBean>() {
                    @Override
                    public void accept(CinemaByNameBean cinemaByNameBean) throws Exception {
                        mView.getCinemaByNameViewData(cinemaByNameBean);
                    }
                });
    }

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
