package com.bw.movie.activity.updatepwd;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UpdatePwdContract {
    interface View extends BaseView {
        void UpdatePwdView(Object obj);
    }

    interface  Presenter extends BasePresenter<View> {
        void UpdatePwdPresenter(Map<String,Object> headMap,Map<String,Object> parms);
    }
}
