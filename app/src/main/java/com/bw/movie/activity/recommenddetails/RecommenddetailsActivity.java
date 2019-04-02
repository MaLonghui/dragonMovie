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
import android.transition.Explode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.AMapActivity;
import com.bw.movie.adapter.FlowAdapter;
import com.bw.movie.adapter.MyCinemaCommmentAdapter;
import com.bw.movie.adapter.MyMovieIdAndFilmAdapter;
import com.bw.movie.adapter.MyRecyclerFlowRecommendeAdapter;
import com.bw.movie.bean.CinemaCommentBean;
import com.bw.movie.bean.CinemaPraiseBean;
import com.bw.movie.bean.FilmFromIdBean;
import com.bw.movie.bean.MovieIdAndFilmBean;
import com.bw.movie.bean.RecommendDetailsBean;
import com.bw.movie.mvp.MVPBaseActivity;
import com.bw.movie.utils.AlertDialogUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

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

public class RecommenddetailsActivity extends MVPBaseActivity<RecommenddetailsContract.View, RecommenddetailsPresenter> implements RecommenddetailsContract.View, View.OnClickListener {

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
    private PopupWindow popupWindow;
    private View viewdetailsPop;
    private View viewcommentPop;
    private LinearLayout linearLayout;
    private XRecyclerView recyclerContent;
    private RecommendDetailsBean recommendDetailsBean;
    private int page = 1;
    private int count = 5;
    private String userId;
    private String sessionId;
    private TextView textErrorComment;
    private MyCinemaCommmentAdapter myCinemaCommmentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        ButterKnife.bind(this);
        getWindow().setEnterTransition(new Explode().setDuration(800));
        getWindow().setExitTransition(new Explode().setDuration(800));
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
        Intent intent = getIntent();
        eid = intent.getStringExtra("eid");
//        Log.i("aa", "eid:" + eid);
            if (!userId.equals("") && !sessionId.equals("")) {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put("userId", userId);
                headMap.put("sessionId", sessionId);
                mPresenter.recommendDetailsPresenter(headMap, eid);
            } else {
                Map<String, Object> headMap = new HashMap<>();
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

        imgRecommendDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommenddetailsActivity.this, AMapActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
//        handler.sendEmptyMessageDelayed(0,1000);
    }

    @Override
    public void recommendDetailsView(Object obj) {
        if (obj != null) {
            recommendDetailsBean = (RecommendDetailsBean) obj;
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

    @Override
    public void cinemaCommentView(Object obj) {
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(RecommenddetailsActivity.this);
        linearLayoutManager1.setOrientation(OrientationHelper.VERTICAL);
        recyclerContent.setLayoutManager(linearLayoutManager1);
        if (obj!=null){
            CinemaCommentBean cinemaCommentBean = (CinemaCommentBean) obj;
            Log.i("aa","cinemaCommentBean:"+cinemaCommentBean.getMessage());
            if (cinemaCommentBean.getMessage().equals("无数据")){
                textErrorComment.setVisibility(View.VISIBLE);
                recyclerContent.setVisibility(View.GONE);
            }else{
                myCinemaCommmentAdapter = new MyCinemaCommmentAdapter(RecommenddetailsActivity.this);
                recyclerContent.setAdapter(myCinemaCommmentAdapter);
                myCinemaCommmentAdapter.setList(cinemaCommentBean);
                myCinemaCommmentAdapter.setID(userId,sessionId);
                myCinemaCommmentAdapter.setListener(new MyCinemaCommmentAdapter.BtnPriaseListener() {
                    @Override
                    public void praiseBtn(String commentId,String isGreate) {
                        if (isGreate.equals("0")){
                            if (!userId.equals("")&&!sessionId.equals("")){
                                Map<String,Object> headMap = new HashMap<>();
                                headMap.put("userId",userId);
                                headMap.put("sessionId",sessionId);
                                mPresenter.cinemaPriasePresenter(headMap,commentId);
                            }else{
                                AlertDialogUtils.AlertDialogLogin(RecommenddetailsActivity.this);
                            }
                        }else{
                            Toast.makeText(RecommenddetailsActivity.this,"不能重复点赞",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }


    @Override
    public void cinemaPriaseView(Object obj) {
        if (obj!=null){
            CinemaPraiseBean cinemaPraiseBean = (CinemaPraiseBean) obj;
            Log.i("aa","cinemaPraiseBean:"+cinemaPraiseBean.getMessage());
        }
    }

    @OnClick({R.id.simPle_recommend_details,R.id.img_recommend_details})
    public void onViewClicked(View view2) {
        switch (view2.getId()){
            case R.id.simPle_recommend_details:
                View view = LayoutInflater.from(this).inflate(R.layout.popuprecommenddetails_layout, null);
                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setAnimationStyle(R.style.popwin_anim_style);
                popupWindow.setTouchable(true);
                popupWindow.setOutsideTouchable(false);
                popupWindow.setFocusable(true);
                View inflate = LayoutInflater.from(this).inflate(R.layout.activity_recommend, null);
                popupWindow.showAtLocation(inflate,Gravity.BOTTOM,0,0);
                TextView textdetailsPop = view.findViewById(R.id.text_details_pop);
                TextView textcommentPop = view.findViewById(R.id.text_comment_pop);
                viewdetailsPop = view.findViewById(R.id.view_details_pop);
                viewcommentPop = view.findViewById(R.id.view_comment_pop);
                ImageView imgDown = view.findViewById(R.id.img_down);
                linearLayout = view.findViewById(R.id.linear_layout);
                recyclerContent = (XRecyclerView)view.findViewById(R.id.recyclerView_content);
                TextView textAddress = view.findViewById(R.id.text_address_details);
                TextView textIphone = view.findViewById(R.id.text_iphone_details);
                TextView textLine = view.findViewById(R.id.text_line_details);
                textErrorComment = view.findViewById(R.id.text_error_comment);
                textdetailsPop.setOnClickListener(this);
                textcommentPop.setOnClickListener(this);
                imgDown.setOnClickListener(this);
                textAddress.setText(recommendDetailsBean.getResult().getAddress());
                textIphone.setText(recommendDetailsBean.getResult().getPhone());
                textLine.setText(recommendDetailsBean.getResult().getVehicleRoute());
                break;
            case R.id.img_recommend_details:

//                Intent intent = new Intent(RecommenddetailsActivity.this,AMapActivity.class);
//                startActivity(intent);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_details_pop:
                viewdetailsPop.setVisibility(View.VISIBLE);
                viewcommentPop.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                recyclerContent.setVisibility(View.GONE);
                textErrorComment.setVisibility(View.GONE);
                break;
            case R.id.text_comment_pop:
                viewdetailsPop.setVisibility(View.INVISIBLE);
                viewcommentPop.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                recyclerContent.setVisibility(View.VISIBLE);
                    if (!userId.equals("")&&!sessionId.equals("")){
                        Map<String,Object> headMap = new HashMap<>();
                        headMap.put("userId",userId);
                        headMap.put("sessionId",sessionId);
                        Map<String,Object> parms = new HashMap<>();
                        parms.put("cinemaId",eid);
                        parms.put("page",page);
                        parms.put("count",count);
                        mPresenter.cinemaCommentPresenter(headMap,parms);
                    }else{
                        Map<String,Object> headMap = new HashMap<>();
                        Map<String,Object> parms = new HashMap<>();
                        parms.put("cinemaId",eid);
                        parms.put("page",page);
                        parms.put("count",count);
                        mPresenter.cinemaCommentPresenter(headMap,parms);
                    }
                break;
            case R.id.img_down:
                popupWindow.dismiss();
                break;
                default:
                    popupWindow.dismiss();
                    break;
        }
    }
}