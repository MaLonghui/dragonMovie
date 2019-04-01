package com.bw.movie.activity.vision;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class VisionContract {
    interface View extends BaseView {
        void visionView(Object obj);
    }

    interface  Presenter extends BasePresenter<View> {
        void visionPresenter(Map<String,Object> headMap);
    }
}
