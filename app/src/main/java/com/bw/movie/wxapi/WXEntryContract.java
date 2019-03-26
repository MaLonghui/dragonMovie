package com.bw.movie.wxapi;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class WXEntryContract {
    interface View extends BaseView {
        void getWxViewData(Object object);
    }

    interface  Presenter extends BasePresenter<View> {
        void getWxPresenter(String code);
    }
}
