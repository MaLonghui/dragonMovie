package com.bw.movie.activity.cinemabymovieid;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.ShowActivity;
import com.bw.movie.adapter.MyCinemaByIdAdapter;
import com.bw.movie.bean.CinemaByIdBean;
import com.bw.movie.mvp.MVPBaseActivity;
import com.bw.movie.net.NoStudoInterent;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CinemaByMovieIdActivity extends MVPBaseActivity<CinemaByMovieIdContract.View, CinemaByMovieIdPresenter> implements CinemaByMovieIdContract.View {

    @BindView(R.id.movie_name)
    TextView movieName;
    @BindView(R.id.movie_recycler_view)
    RecyclerView movieRecyclerView;
    @BindView(R.id.movie_return)
    ImageView movieReturn;
    public static final String TAG = "CinemaByMovieIdActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bymovieid);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String mName = intent.getStringExtra("movieName");
        String mId = intent.getStringExtra("movieId");
        movieName.setText(mName);
        if (NoStudoInterent.isNetworkAvailable(CinemaByMovieIdActivity.this)) {
            mPresenter.getPresenterData(mId);
        }

        movieReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        movieRecyclerView.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void getViewData(Object object) {
        CinemaByIdBean cinemaByIdBean = (CinemaByIdBean) object;
        //Log.i(TAG, "getViewData: "+cinemaByIdBean.getMessage());
        List<CinemaByIdBean.ResultBean> beanResult = cinemaByIdBean.getResult();
        MyCinemaByIdAdapter cinemaByIdAdapter = new MyCinemaByIdAdapter(this,beanResult);
        movieRecyclerView.setAdapter(cinemaByIdAdapter);
    }
}
