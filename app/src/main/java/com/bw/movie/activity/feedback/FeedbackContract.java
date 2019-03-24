package com.bw.movie.activity.feedback;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FeedbackContract {
    interface View extends BaseView {
        void FeekbackView(Object obj);
    }

    interface  Presenter extends BasePresenter<View> {
        void FeekbackPresenter(Map<String,Object> headMap,String content);
    }
}
