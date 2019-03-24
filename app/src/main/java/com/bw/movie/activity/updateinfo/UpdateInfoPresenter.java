package com.bw.movie.activity.updateinfo;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
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

public class UpdateInfoPresenter extends BasePresenterImpl<UpdateInfoContract.View> implements UpdateInfoContract.Presenter{
    @Override
    public void updateInfoPresenter(Map<String, Object> headMap, Map<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.updateinfo(Api.UPDATEINFO_URL,headMap,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateInfoBean>() {
                    @Override
                    public void accept(UpdateInfoBean updateInfoBean) throws Exception {
                        mView.updateInfoView(updateInfoBean);
                    }
                });
    }
}
