package com.bw.movie.fragment.filmattention;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.FilmAttentionBean;
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

public class FilmattentionPresenter extends BasePresenterImpl<FilmattentionContract.View> implements FilmattentionContract.Presenter{

    @Override
    public void FilmAttentionPresenter(Map<String, Object> headMap, Map<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.filmattention(Api.FILMATTENTION_URL,headMap,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FilmAttentionBean>() {
                    @Override
                    public void accept(FilmAttentionBean filmAttentionBean) throws Exception {
                        mView.FilmAttentionView(filmAttentionBean);
                    }
                });
    }
}
