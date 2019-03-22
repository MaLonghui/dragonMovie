package com.bw.movie.activity.cinemabymovieid;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.movieschedulelist.MovieScheduleListActivity;
import com.bw.movie.adapter.MyCinemaByIdAdapter;
import com.bw.movie.bean.CinemaByIdBean;
import com.bw.movie.bean.FilmDetailsBean;
import com.bw.movie.mvp.MVPBaseActivity;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
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
    private String mId;
    private String mName;
    private FilmDetailsBean.ResultBean resultBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bymovieid);
        ButterKnife.bind(this);
        getWindow().setEnterTransition(new Explode().setDuration(800));
        getWindow().setExitTransition(new Explode().setDuration(800));
        Intent intent = getIntent();
        resultBean = (FilmDetailsBean.ResultBean) intent.getSerializableExtra("resultBean");
        movieName.setText(resultBean.getName());
        mPresenter.getPresenterData(resultBean.getId()+"");
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
        final CinemaByIdBean cinemaByIdBean = (CinemaByIdBean) object;
        //Log.i(TAG, "getViewData: "+cinemaByIdBean.getMessage());
        final List<CinemaByIdBean.ResultBean> beanResult = cinemaByIdBean.getResult();
        MyCinemaByIdAdapter cinemaByIdAdapter = new MyCinemaByIdAdapter(this, beanResult);
        movieRecyclerView.setAdapter(cinemaByIdAdapter);
        cinemaByIdAdapter.setOnClicklistener(new MyCinemaByIdAdapter.onClicklistener() {
            @Override
            public void click(CinemaByIdBean.ResultBean bean) {
                Bundle bundle = new Bundle();
                //FilmDetailsBean.ResultBean resultBean = (FilmDetailsBean.ResultBean) bundle.getSerializable("resultBean");
                bundle.putSerializable("resultBean",resultBean);
                bundle.putSerializable("bean",bean);
                Intent intent = new Intent(CinemaByMovieIdActivity.this, MovieScheduleListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(CinemaByMovieIdActivity.this).toBundle());
            }
        });

    }
}
