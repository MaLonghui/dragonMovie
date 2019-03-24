package com.bw.movie.fragment.cinemaattention;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.MovieAttentionBean;
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

public class CinemaattentionPresenter extends BasePresenterImpl<CinemaattentionContract.View> implements CinemaattentionContract.Presenter{

    @Override
    public void MovieAttentionPresenter(Map<String, Object> headMap, Map<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.attentionlist(Api.MOVIEATTENTION_URL,headMap,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieAttentionBean>() {
                    @Override
                    public void accept(MovieAttentionBean movieAttentionBean) throws Exception {
                        mView.MovieAttentionView(movieAttentionBean);
                    }
                });
    }
}
