package com.bw.movie.activity.show;

import com.bw.movie.api.Api;
import com.bw.movie.api.ApiServer;
import com.bw.movie.bean.CinemaByNameBean;
import com.bw.movie.bean.RecommendCinemasBean;
import com.bw.movie.utils.RetrofitManager;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ShowModel implements ShowContract.IModel {
    @Override
    public void getModelData(String url, Map<String,Object> prams, final ModelInterface modelInterface) {


        ApiServer apiServer = RetrofitManager.getInstance(Api.BASE_URL).setCreate(ApiServer.class);
        apiServer.findAllCinemas(url,prams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CinemaByNameBean>() {
                    @Override
                    public void accept(CinemaByNameBean cinemaByNameBean) throws Exception {
                        modelInterface.onSuccess(cinemaByNameBean);
                    }
                });
    }
}
