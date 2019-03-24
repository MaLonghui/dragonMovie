package com.bw.movie.fragment.cinemaattention;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CinemaattentionContract {
    interface View extends BaseView {
        void MovieAttentionView(Object obj);
    }

    interface  Presenter extends BasePresenter<View> {
        void MovieAttentionPresenter(Map<String,Object> headMap,Map<String,Object> parms);
    }
}
