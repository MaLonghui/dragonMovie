package com.bw.movie.activity.login;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginContract {
    interface View extends BaseView {
        void loginView(Object object);
    }

    interface  Presenter extends BasePresenter<View> {
        void loginPresenter(String phone,String pwd);
    }
}
