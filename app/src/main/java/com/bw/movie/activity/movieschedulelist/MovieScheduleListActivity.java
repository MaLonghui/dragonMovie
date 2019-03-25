package com.bw.movie.activity.movieschedulelist;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.MyScheduleAdapter;
import com.bw.movie.bean.CinemaByIdBean;
import com.bw.movie.bean.FilmDetailsBean;
import com.bw.movie.bean.MovieIdAndFilmBean;
import com.bw.movie.mvp.MVPBaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MovieScheduleListActivity extends MVPBaseActivity<MovieScheduleListContract.View, MovieScheduleListPresenter> implements MovieScheduleListContract.View {


    @BindView(R.id.schedule_ciname_name)
    TextView scheduleCinameName;
    @BindView(R.id.schedule_address)
    TextView scheduleAddress;
    @BindView(R.id.schedule_img)
    ImageView scheduleImg;
    @BindView(R.id.schedule_simpleview)
    SimpleDraweeView scheduleSimpleview;
    @BindView(R.id.schdule_movie_name)
    TextView schduleMovieName;
    @BindView(R.id.schedule_type)
    TextView scheduleType;
    @BindView(R.id.schedule_director)
    TextView scheduleDirector;
    @BindView(R.id.schedule_timer)
    TextView scheduleTimer;
    @BindView(R.id.schedule_movie_address)
    TextView scheduleMovieAddress;
    @BindView(R.id.schedule_return)
    ImageView scheduleReturn;
    @BindView(R.id.schedule_recycler_view)
    RecyclerView scheduleRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ButterKnife.bind(this);
        getWindow().setEnterTransition(new Explode().setDuration(800));
        getWindow().setExitTransition(new Explode().setDuration(800));
        Intent intent = getIntent();
        FilmDetailsBean.ResultBean resultBean = (FilmDetailsBean.ResultBean) intent.getSerializableExtra("resultBean");
        CinemaByIdBean.ResultBean bean = (CinemaByIdBean.ResultBean) intent.getSerializableExtra("bean");
        //Log.i("aa", "onCreate: "+resultBean.getMovieTypes()+bean.getAddress());
        scheduleCinameName.setText(bean.getName());
        scheduleAddress.setText(bean.getAddress());
        schduleMovieName.setText(resultBean.getName());
        scheduleType.setText("类型：" + resultBean.getMovieTypes());
        scheduleAddress.setText(bean.getAddress());
        scheduleMovieAddress.setText("产地：" + resultBean.getPlaceOrigin());
        scheduleTimer.setText("时长：" + resultBean.getDuration());
        scheduleDirector.setText("导演：" + resultBean.getDirector());
        Uri uri = Uri.parse(resultBean.getImageUrl());
        scheduleSimpleview.setImageURI(uri);
        scheduleReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        scheduleRecyclerView.setLayoutManager(linearLayoutManager);
        //  System.out.println(resultBean.getName()+bean.getId()+"");
        mPresenter.getPresenterData(resultBean.getId() + "", bean.getId() + "");

    }

    @Override
    public void getViewData(Object object) {
        MovieIdAndFilmBean movieIdAndFilmBean = (MovieIdAndFilmBean) object;
        List<MovieIdAndFilmBean.ResultBean> resultBeans = movieIdAndFilmBean.getResult();
        //Log.i("aa", "getViewData: " + movieIdAndFilmBean.getMessage());
        MyScheduleAdapter scheduleAdapter = new MyScheduleAdapter(this,resultBeans);
        scheduleRecyclerView.setAdapter(scheduleAdapter);
    }
}
