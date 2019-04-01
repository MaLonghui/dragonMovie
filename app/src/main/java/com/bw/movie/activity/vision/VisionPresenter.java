package com.bw.movie.activity.vision;

import android.content.Context;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.VisionBean;
import com.bw.movie.mvp.BasePresenterImpl;
import com.bw.movie.utils.RetrofitManager;

import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class VisionPresenter extends BasePresenterImpl<VisionContract.View> implements VisionContract.Presenter{

    @Override
    public void visionPresenter(Map<String, Object> headMap) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.vision(Api.VISION_UPDATE,headMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VisionBean>() {
                    @Override
                    public void accept(VisionBean visionBean) throws Exception {
                        mView.visionView(visionBean);
                    }
                });
    }
}
