package com.bw.movie.activity.msg;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MsgContract {
    interface View extends BaseView {
        void SysMsgView(Object object);
        void SysMsgStatusView(Object obj);
    }

    interface  Presenter extends BasePresenter<View> {
        void SysMsgPresenter(Map<String,Object> headMap,Map<String,Object> parms);
        void SysMsgStatusPresenter(Map<String,Object> headMap,String id);
    }
}
