package com.bw.movie.fragment.mine;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MineContract {
    interface View extends BaseView {
        void userInfoView(Object obj);
        void headIconView(Object object);
    }

    interface  Presenter extends BasePresenter<View> {
        void userInfoPresenter(Map<String,Object> headMap);
        void headIconPresenter(Map<String,Object> headMap, Map<String,String> parms);
    }
}
