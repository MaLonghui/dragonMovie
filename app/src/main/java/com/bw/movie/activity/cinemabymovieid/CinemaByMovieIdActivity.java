package com.bw.movie.activity.cinemabymovieid;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.movieschedulelist.MovieScheduleListActivity;
import com.bw.movie.adapter.MyCinemaByIdAdapter;
import com.bw.movie.bean.CancelAttentionBean;
import com.bw.movie.bean.CinemaAttentionBean;
import com.bw.movie.bean.CinemaByIdBean;
import com.bw.movie.bean.FilmDetailsBean;
import com.bw.movie.mvp.MVPBaseActivity;
import com.bw.movie.net.NoStudoInterent;
import com.bw.movie.utils.AlertDialogUtils;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private FilmDetailsBean.ResultBean resultBean;
    private SharedPreferences sp;
    private String userId;
    private String sessionId;
    private String id;
    private MyCinemaByIdAdapter cinemaByIdAdapter;
    private List<CinemaByIdBean.ResultBean> beanResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bymovieid);
        ButterKnife.bind(this);
        getWindow().setEnterTransition(new Explode().setDuration(800));
        getWindow().setExitTransition(new Explode().setDuration(800));
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
        Intent intent = getIntent();
        resultBean = (FilmDetailsBean.ResultBean) intent.getSerializableExtra("resultBean");
        id = resultBean.getId()+"";
        movieName.setText(resultBean.getName());
        Map<String,Object> headMap = new HashMap<>();
        headMap.put("userId",userId);
        headMap.put("sessionId",sessionId);
        mPresenter.getPresenterData(headMap,id);
        movieReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        movieRecyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
        if (!userId.equals("")&&!sessionId.equals("")){
            Map<String,Object> headMap = new HashMap<>();
            headMap.put("userId",userId);
            headMap.put("sessionId",sessionId);
            mPresenter.getPresenterData(headMap,id);
        }else {
            Map<String,Object> headMap = new HashMap<>();
            mPresenter.getPresenterData(headMap,id);
        }

    }

    @Override
    public void getViewData(Object object) {
        final CinemaByIdBean cinemaByIdBean = (CinemaByIdBean) object;
        //Log.i(TAG, "getViewData: "+cinemaByIdBean.getMessage());
        beanResult = cinemaByIdBean.getResult();
        cinemaByIdAdapter = new MyCinemaByIdAdapter(this, beanResult);

        movieRecyclerView.setAdapter(cinemaByIdAdapter);
        cinemaByIdAdapter.setAttentionClick(new MyCinemaByIdAdapter.CinemaAttentionClick() {
            @Override
            public void clickattention(String cinemaId, boolean b) {
                if (b) {
                    if (!userId.equals("") && !sessionId.equals("")) {
                        Map<String, Object> headMap = new HashMap<>();
                        headMap.put("userId", userId);
                        headMap.put("sessionId", sessionId);
                        mPresenter.AttentionPresenter(headMap, cinemaId);
                        cinemaByIdAdapter.notifyDataSetChanged();
                        for (CinemaByIdBean.ResultBean bean : beanResult) {
                            Log.i("aa","bean:"+bean.getFollowCinema());
                        }
                    } else {
                        AlertDialogUtils.AlertDialogLogin(CinemaByMovieIdActivity.this);
                    }
                    cinemaByIdAdapter.notifyDataSetChanged();
                } else {
                    if (!userId.equals("") && !sessionId.equals("")) {
                        Map<String, Object> headMap = new HashMap<>();
                        headMap.put("userId", userId);
                        headMap.put("sessionId", sessionId);
                        mPresenter.CancelAttentionPresenter(headMap, cinemaId);
                        cinemaByIdAdapter.notifyDataSetChanged();
                    } else {
                        AlertDialogUtils.AlertDialogLogin(CinemaByMovieIdActivity.this);
                    }
                }
            }
        });
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

    @Override
    public void AttentionView(Object obj) {
        if (obj!=null){
            CinemaAttentionBean cinemaAttentionBean = (CinemaAttentionBean) obj;
            if (cinemaAttentionBean.getStatus().equals("0000")){
                Toast.makeText(CinemaByMovieIdActivity.this, cinemaAttentionBean.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void CancelAttentionView(Object obj) {
        CancelAttentionBean cancelAttentionBean = (CancelAttentionBean) obj;
        if (cancelAttentionBean.getStatus().equals("0000")) {
            Toast.makeText(CinemaByMovieIdActivity.this, cancelAttentionBean.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}