package com.bw.movie.activity.seat;

import com.bw.movie.activity.regist.RegistContract;
import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.BuyTicketBean;
import com.bw.movie.mvp.BasePresenterImpl;
import com.bw.movie.utils.RetrofitManager;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class SeatPresenter extends BasePresenterImpl<SeatContract.View> implements SeatContract.Presenter {

    @Override
    public void getTicketPresenterData(Map<String, Object> headMap, Map<String, Object> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.buyTicket(Api.BUY_TICKET, headMap, parms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BuyTicketBean>() {
                    @Override
                    public void accept(BuyTicketBean buyTicketBean) throws Exception {
                        mView.getTicketViewData(buyTicketBean);
                    }
                });
    }
}
