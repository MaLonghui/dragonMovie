package com.bw.movie.fragment.mine;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.io.File;
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
        void SignInView(Object obj);
    }

    interface  Presenter extends BasePresenter<View> {
        void userInfoPresenter(Map<String,Object> headMap);
        void headIconPresenter(Map<String,Object> headMap, File file);
        void SignInPresenter(Map<String,Object> headMap);
    }
}
