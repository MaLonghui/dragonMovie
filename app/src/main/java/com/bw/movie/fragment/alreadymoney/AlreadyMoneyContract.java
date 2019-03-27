package com.bw.movie.fragment.alreadymoney;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AlreadyMoneyContract {
    interface View extends BaseView {
        void AlreadyTicketView(Object obj);
    }



    interface  Presenter extends BasePresenter<View> {
        void AlreadyTicketPresenter(Map<String,Object> headMap, Map<String,Object> parms);
    }
}
