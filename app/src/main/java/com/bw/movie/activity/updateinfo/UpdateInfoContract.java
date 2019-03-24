package com.bw.movie.activity.updateinfo;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UpdateInfoContract {
    interface View extends BaseView {
        void updateInfoView(Object obj);
    }

    interface  Presenter extends BasePresenter<View> {
        void updateInfoPresenter(Map<String,Object> headMap, Map<String,Object> parms);
    }
}
