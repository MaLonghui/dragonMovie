package com.bw.movie.activity.info;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.FindInfoBean;
import com.bw.movie.bean.UpdateInfoBean;
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

public class InfoPresenter extends BasePresenterImpl<InfoContract.View> implements InfoContract.Presenter{

    @Override
    public void userInfoPresenter(Map<String, Object> headMap) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.finduserid(Api.FINDUSERID_URL,headMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FindInfoBean>() {
                    @Override
                    public void accept(FindInfoBean findInfoBean) throws Exception {
                        mView.userInfoView(findInfoBean);
                    }
                });
    }


}
