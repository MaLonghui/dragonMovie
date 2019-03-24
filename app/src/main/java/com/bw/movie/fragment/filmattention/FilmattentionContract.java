package com.bw.movie.fragment.filmattention;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FilmattentionContract {
    interface View extends BaseView {
        void FilmAttentionView(Object obj);
    }

    interface  Presenter extends BasePresenter<View> {
        void FilmAttentionPresenter(Map<String,Object> headMap,Map<String,Object> parms);
    }
}
