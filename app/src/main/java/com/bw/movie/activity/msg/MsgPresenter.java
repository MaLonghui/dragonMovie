package com.bw.movie.activity.msg;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.SysMsgBean;
import com.bw.movie.bean.SysMsgStatusBean;
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

public class MsgPresenter extends BasePresenterImpl<MsgContract.View> implements MsgContract.Presenter{

    @Override
    public void SysMsgPresenter(Map<String, Object> headMap, Map<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.sysmsg(Api.SYSMSG_URL,headMap,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SysMsgBean>() {
                    @Override
                    public void accept(SysMsgBean sysMsgBean) throws Exception {
                        mView.SysMsgView(sysMsgBean);
                    }
                });
    }

    @Override
    public void SysMsgStatusPresenter(Map<String, Object> headMap, String id) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.msgstatus(Api.SYSMSGSTATUS_URL,headMap,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SysMsgStatusBean>() {
                    @Override
                    public void accept(SysMsgStatusBean sysMsgStatusBean) throws Exception {
                        mView.SysMsgStatusView(sysMsgStatusBean);
                    }
                });
    }
}
