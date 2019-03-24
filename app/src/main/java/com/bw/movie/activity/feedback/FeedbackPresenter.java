package com.bw.movie.activity.feedback;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.FeedBackBean;
import com.bw.movie.mvp.BasePresenterImpl;
import com.bw.movie.utils.RetrofitManager;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FeedbackPresenter extends BasePresenterImpl<FeedbackContract.View> implements FeedbackContract.Presenter{

    @Override
    public void FeekbackPresenter(Map<String, Object> headMap, String content) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.feedback(Api.FEEDBACK_URL,headMap,content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FeedBackBean>() {
                    @Override
                    public void accept(FeedBackBean feedBackBean) throws Exception {
                        mView.FeekbackView(feedBackBean);
                    }
                });
    }
}
