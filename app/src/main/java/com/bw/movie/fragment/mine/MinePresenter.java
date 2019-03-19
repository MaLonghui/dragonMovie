package com.bw.movie.fragment.mine;

import android.util.Log;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.FindInfoBean;
import com.bw.movie.bean.UserHeadIconBean;
import com.bw.movie.mvp.BasePresenterImpl;
import com.bw.movie.utils.RetrofitManager;

import java.io.File;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MinePresenter extends BasePresenterImpl<MineContract.View> implements MineContract.Presenter{

    @Override
    public void userInfoPresenter(Map<String, Object> headMap) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.finduserid(Api.FINDUSERID_URL,headMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FindInfoBean>() {
                    @Override
                    public void accept(FindInfoBean findUserBean) throws Exception {
                        mView.userInfoView(findUserBean);
                    }
                });
    }

    @Override
    public void headIconPresenter(Map<String, Object> headMap, Map<String, String> parms) {
        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        MultipartBody multipartBody = filesMutipar(parms);
        apiServer.headicon(Api.HEADICON_URL,headMap,multipartBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserHeadIconBean>() {
                    @Override
                    public void accept(UserHeadIconBean userHeadIconBean) throws Exception {
                        Log.i("aa","userHeadIconBean:"+userHeadIconBean.getMessage());
                        mView.headIconView(userHeadIconBean);
                    }
                });
    }
    /**
     * 10.图片传入
     *  创建MultipartBody.Builder，类型为Form
     */
    public static MultipartBody filesMutipar(Map<String,String> map) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            File file = new File(entry.getValue());
            builder.addFormDataPart(entry.getKey(), "tp.png",
                    RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }



}
