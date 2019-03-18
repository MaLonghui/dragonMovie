package com.bw.movie.fragment.mine;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MineContract {
    interface View extends BaseView {
        void userInfoView(Object obj);
    }

    interface  Presenter extends BasePresenter<View> {
        void userInfoPresenter(Map<String,Object> headMap);
    }
}
