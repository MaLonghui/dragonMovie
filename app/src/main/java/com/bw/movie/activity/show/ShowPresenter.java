package com.bw.movie.activity.show;

import com.bw.movie.activity.ShowActivity;
import com.bw.movie.api.Api;

import java.util.Map;

public class ShowPresenter implements ShowContract.IPresenter {

    ShowActivity showActivity;
    private ShowModel showModel;

    public ShowPresenter(ShowActivity showActivity) {
        this.showActivity = showActivity;
        showModel = new ShowModel();
    }

    @Override
    public void getCinemaByNamePresenterData(Map<String, Object> parms) {
        showModel.getModelData(Api.FINDAllCinemas, parms, new ShowContract.IModel.ModelInterface() {
            @Override
            public void onSuccess(Object o) {
                showActivity.getVIewData(o);
            }
        });
    }
    public void onDetach(){
        if (showModel!=null){
            showModel = null;
            showActivity = null;
        }
    }
}
