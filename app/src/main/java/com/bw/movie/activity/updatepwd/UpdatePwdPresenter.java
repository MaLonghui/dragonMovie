package com.bw.movie.activity.updatepwd;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.UpdatePwdBean;
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

public class UpdatePwdPresenter extends BasePresenterImpl<UpdatePwdContract.View> implements UpdatePwdContract.Presenter{

    @Override
    public void UpdatePwdPresenter(Map<String, Object> headMap, Map<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.updatepwd(Api.UPDATEPWD_URL,headMap,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdatePwdBean>() {
                    @Override
                    public void accept(UpdatePwdBean updatePwdBean) throws Exception {
                        mView.UpdatePwdView(updatePwdBean);
                    }
                });
    }
}
