package com.bw.movie.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.bw.movie.R;
import com.bw.movie.bean.JiFilmBean;
import com.bw.movie.bean.ReFilmBean;
import com.bw.movie.bean.ShangFilmBean;

import java.util.List;

public class FilmSearchActivity extends Activity {

    public static final String TAG = "FilmDetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_search);
        //接收intent的值
        List<ReFilmBean.ResultBean> reBeanList = (List<ReFilmBean.ResultBean>) getIntent().getSerializableExtra("reBeanList");
        List<ShangFilmBean.ResultBean> shangBeanList = (List<ShangFilmBean.ResultBean>) getIntent().getSerializableExtra("shangBeanList");
        List<JiFilmBean.ResultBean> jiBeanList = (List<JiFilmBean.ResultBean>) getIntent().getSerializableExtra("jiBeanList");
        Log.i(TAG, "onCreate: "+reBeanList.get(0).getName()+shangBeanList.get(0).getName()+jiBeanList.get(0).getName());


    }
}
