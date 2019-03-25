package com.bw.movie.fragment.cinema;

import android.content.Context;

import com.bw.movie.mvp.BasePresenter;
import com.bw.movie.mvp.BaseView;

import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CinemaContract {
    interface View extends BaseView {
        void recommendView(Object obj);
        void nearbyView(Object obj);
        void AttentionView(Object obj);
        //查询影院
        void getCinemaByNameViewData(Object object);
        void CancelAttentionView(Object obj);
    }

    interface  Presenter extends BasePresenter<View> {
        void recommendPresenter(Map<String,Object> headMap,Map<String,Object> parms);
        void nearbyPresenter(Map<String,Object> headMap,Map<String,Object> parms);
        void AttentionPresenter(Map<String,Object> headMap,String cinemaId);
        //查询影院
        void getCinemaByNamePresenterData(Map<String,Object> parms);
        void CancelAttentionPresenter(Map<String,Object> headMap,String cinemaId);
    }
}