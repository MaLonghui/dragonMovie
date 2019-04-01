package com.bw.movie.activity.show;

import java.util.Map;

public class ShowContract {


    interface IModel{
        void getModelData(String url,Map<String,Object> prams,ModelInterface modelInterface);
        interface ModelInterface{
            void onSuccess(Object o);
        }
    }

    interface IPresenter{
        //查询影院
        void getCinemaByNamePresenterData(Map<String,Object> parms);
    }

    public interface IView{
        void getVIewData(Object object);
    }
}
