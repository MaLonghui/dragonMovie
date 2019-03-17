package com.bw.movie.activity.regist;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RegistContract {
    interface View extends BaseView {
        void registView(Object obj);
    }

    interface  Presenter extends BasePresenter<View> {
        void registPresenter(Map<String,Object> parms);
    }
}
