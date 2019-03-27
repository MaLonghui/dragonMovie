package com.bw.movie.fragment.willmoney;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.PayBean;
import com.bw.movie.bean.TicketBean;
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

public class WillMoneyPresenter extends BasePresenterImpl<WillMoneyContract.View> implements WillMoneyContract.Presenter{

    @Override
    public void WillTicketPresenter(Map<String, Object> headMap, Map<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.ticket(Api.SELECTTICKET_URL,headMap,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TicketBean>() {
                    @Override
                    public void accept(TicketBean ticketBean) throws Exception {
                        mView.WillTicketView(ticketBean);
                    }
                });
    }

    @Override
    public void getPayPresenter(Map<String, Object> headMap, Map<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.wzPay(Api.WZ_PAY,headMap,parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PayBean>() {
                    @Override
                    public void accept(PayBean payBean) throws Exception {
                        mView.getPayViewData(payBean);
                    }
                });
    }
}
