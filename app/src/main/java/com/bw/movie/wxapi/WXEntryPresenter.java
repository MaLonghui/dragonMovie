package com.bw.movie.wxapi;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.WxLoginBean;
import com.bw.movie.mvp.BasePresenterImpl;
import com.bw.movie.utils.RetrofitManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class WXEntryPresenter extends BasePresenterImpl<WXEntryContract.View> implements WXEntryContract.Presenter{

    @Override
    public void getWxPresenter(String code) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.wxLogin(Api.WX_LOGIN,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WxLoginBean>() {
                    @Override
                    public void accept(WxLoginBean wxLoginBean) throws Exception {
                        mView.getWxViewData(wxLoginBean);
                    }
                });
    }
}
