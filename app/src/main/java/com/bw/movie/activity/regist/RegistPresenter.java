package com.bw.movie.activity.regist;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.RegistBean;
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

public class RegistPresenter extends BasePresenterImpl<RegistContract.View> implements RegistContract.Presenter{

    @Override
    public void registPresenter( Map<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.regist(Api.REGIST_URL,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RegistBean>() {
                    @Override
                    public void accept(RegistBean registBean) throws Exception {
                        mView.registView(registBean);
                    }
                });
    }
}
