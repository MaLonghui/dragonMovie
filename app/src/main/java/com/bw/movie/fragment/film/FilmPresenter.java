package com.bw.movie.fragment.film;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.JiFilmBean;
import com.bw.movie.bean.ReFilmBean;
import com.bw.movie.bean.ShangFilmBean;
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

public class FilmPresenter extends BasePresenterImpl<FilmContract.View> implements FilmContract.Presenter{

    /**
     * 热门电影
     * @param headMap
     * @param parms
     */
    @Override
    public void getReMenPresenter(HashMap<String, Object> headMap, HashMap<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.getReData(Api.FILM_RE_URL,headMap,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ReFilmBean>() {
                    @Override
                    public void accept(ReFilmBean reFilmBean) throws Exception {
                        mView.getReMenViewData(reFilmBean);
                    }
                });
    }

    /**
     * 正在上映
     * @param headMap
     * @param parms
     */
    @Override
    public void getZhengPresenter(HashMap<String, Object> headMap, HashMap<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.getShangData(Api.FILM_ZHENG_URL,headMap,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ShangFilmBean>() {
                    @Override
                    public void accept(ShangFilmBean shangFilmBean) throws Exception {
                        mView.getZhengViewData(shangFilmBean);
                    }
                });
    }

    /**
     * 即将上映
     * @param headMap
     * @param parms
     */
    @Override
    public void getJiPresenter(HashMap<String, Object> headMap, HashMap<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.getJiData(Api.FILM_JI_URL,headMap,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JiFilmBean>() {
                    @Override
                    public void accept(JiFilmBean jiFilmBean) throws Exception {
                        mView.getJiViewData(jiFilmBean);
                    }
                });
    }
}
