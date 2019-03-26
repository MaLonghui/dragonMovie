package com.bw.movie.fragment.willmoney;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class WillMoneyContract {
    interface View extends BaseView {
        void WillTicketView(Object obj);
    }

    interface  Presenter extends BasePresenter<View> {
        void WillTicketPresenter(Map<String,Object> headMap,Map<String,Object> parms);
    }
}
