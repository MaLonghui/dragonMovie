package com.bw.movie.activity.seat;

import com.bw.movie.activity.regist.RegistContract;
import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

public class SeatContract {
    interface View extends BaseView {
    }

    interface  Presenter extends BasePresenter<SeatContract.View> {
    }
}
