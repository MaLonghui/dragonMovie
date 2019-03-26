package com.bw.movie.activity.seat;


import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

public class SeatContract {
    interface View extends BaseView {
        void getTicketViewData(Object object);
    }

    interface  Presenter extends BasePresenter<View> {
        void getTicketPresenterData(Map<String,Object> headMap,Map<String,Object> parms);
    }


}
