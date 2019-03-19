package com.bw.movie.activity.recommenddetails;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.FlowAdapter;
import com.bw.movie.adapter.MyMovieIdAndFilmAdapter;
import com.bw.movie.adapter.MyRecyclerFlowRecommendeAdapter;
import com.bw.movie.bean.FilmFromIdBean;
import com.bw.movie.bean.MovieIdAndFilmBean;
import com.bw.movie.bean.RecommendDetailsBean;
import com.bw.movie.mvp.MVPBaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RecommenddetailsActivity extends MVPBaseActivity<RecommenddetailsContract.View, RecommenddetailsPresenter> implements RecommenddetailsContract.View {

    @BindView(R.id.simPle_recommend_details)
    SimpleDraweeView simPleRecommendDetails;
    @BindView(R.id.text_name_recommend_details)
    TextView textNameRecommendDetails;
    @BindView(R.id.text_address_recommend_details)
    TextView textAddressRecommendDetails;
    @BindView(R.id.img_recommend_details)
    ImageView imgRecommendDetails;
    @BindView(R.id.recycler_flow_recommend)
    RecyclerCoverFlow recyclerFlowRecommend;
    @BindView(R.id.recycler_view_recommend)
    RecyclerView recyclerViewRecommend;
    @BindView(R.id.text_error_title)
    TextView textErrorTitle;
    @BindView(R.id.text_date_details)
    TextView textDateDetails;
    private SharedPreferences sp;
    private FilmFromIdBean filmFromIdBean;
    private String eid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        ButterKnife.bind(this);
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        String userId = sp.getString("userId", "");
        String sessionId = sp.getString("sessionId", "");
        Intent intent = getIntent();
        eid = intent.getStringExtra("eid");
//        Log.i("aa", "eid:" + eid);
        if (!userId.equals("") && !sessionId.equals("")) {
            Map<String, String> headMap = new HashMap<>();
            headMap.put("userId", userId);
            headMap.put("sessionId", sessionId);
            mPresenter.recommendDetailsPresenter(headMap, eid);
        } else {
            Map<String, String> headMap = new HashMap<>();
            mPresenter.recommendDetailsPresenter(headMap, eid);
        }
        mPresenter.filmFromIdPresenter(eid);
        recyclerFlowRecommend.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                if (!filmFromIdBean.getMessage().equals("无数据")) {
                    String id = filmFromIdBean.getResult().get(position).getId();
                    mPresenter.movieIdAndfilmIdPresenter(id, eid);
                }
            }
        });
    }


    @Override
    public void recommendDetailsView(Object obj) {
        if (obj != null) {
            RecommendDetailsBean recommendDetailsBean = (RecommendDetailsBean) obj;
            if (recommendDetailsBean.getStatus().equals("0000")) {
//                Log.i("aa","recommendDetailsBean:"+recommendDetailsBean.getMessage());
                Uri uri = Uri.parse(recommendDetailsBean.getResult().getLogo());
                simPleRecommendDetails.setImageURI(uri);
                textNameRecommendDetails.setText(recommendDetailsBean.getResult().getName());
                textAddressRecommendDetails.setText(recommendDetailsBean.getResult().getAddress());
            }
        }
    }

    @Override
    public void filmFromIdView(Object obj) {
        if (obj != null) {
            filmFromIdBean = (FilmFromIdBean) obj;
//            Log.i("aa","filmFromIdBean:"+filmFromIdBean.);
            if (filmFromIdBean.getMessage().equals("无数据")) {
                FlowAdapter flowAdapter = new FlowAdapter(this);
                recyclerFlowRecommend.setAdapter(flowAdapter);
                textErrorTitle.setVisibility(View.VISIBLE);
                recyclerViewRecommend.setVisibility(View.GONE);
//                Toast.makeText(this,"无数据",Toast.LENGTH_LONG).show();
            } else {
                for (FilmFromIdBean.ResultBean resultBean : filmFromIdBean.getResult()) {
                    String id = resultBean.getId();
                    Date date = new Date(resultBean.getReleaseTime());
                    SimpleDateFormat sd = new SimpleDateFormat("yy-MM-dd");
                    String format = sd.format(date);
                    textDateDetails.setText(format);
                    mPresenter.movieIdAndfilmIdPresenter(id, eid);
                }
                MyRecyclerFlowRecommendeAdapter myRecyclerFlowRecommende = new MyRecyclerFlowRecommendeAdapter(RecommenddetailsActivity.this, filmFromIdBean);
                recyclerFlowRecommend.setAdapter(myRecyclerFlowRecommende);
                textErrorTitle.setVisibility(View.GONE);
                recyclerViewRecommend.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void movieIdAndfilmIdView(Object obj) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecommenddetailsActivity.this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerViewRecommend.setLayoutManager(linearLayoutManager);
        if (obj != null) {
            MovieIdAndFilmBean movieIdAndFilmBean = (MovieIdAndFilmBean) obj;
//            Log.i("aa","movieIdAndFilmBean:"+movieIdAndFilmBean.getMessage());
            if (movieIdAndFilmBean != null) {
                MyMovieIdAndFilmAdapter myMovieIdAndFilmAdapter = new MyMovieIdAndFilmAdapter(this, movieIdAndFilmBean);
                recyclerViewRecommend.setAdapter(myMovieIdAndFilmAdapter);
            }
        }
    }

    @OnClick(R.id.img_recommend_details)
    public void onViewClicked() {
        View view = LayoutInflater.from(this).inflate(R.layout.popuprecommenddetails_layout, null);
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_recommend, null);
        popupWindow.showAtLocation(inflate,Gravity.BOTTOM,0,0);
    }
}
