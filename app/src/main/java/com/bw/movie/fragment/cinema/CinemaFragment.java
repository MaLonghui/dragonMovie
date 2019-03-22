package com.bw.movie.fragment.cinema;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.MyNearbyAdapter;
import com.bw.movie.adapter.MyRecommendAdapter;
import com.bw.movie.bean.NearbyCinemasBean;
import com.bw.movie.bean.RecommendCinemasBean;
import com.bw.movie.mvp.MVPBaseFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.View.VISIBLE;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CinemaFragment extends MVPBaseFragment<CinemaContract.View, CinemaPresenter> implements CinemaContract.View {

    @BindView(R.id.btn_Recommend)
    Button btnRecommend;
    @BindView(R.id.btn_Nearby)
    Button btnNearby;
    @BindView(R.id.xrecycler_view)
    XRecyclerView xrecyclerView;
    Unbinder unbinder;
    @BindView(R.id.cinema_dingwei_icon)
    ImageView cinemaDingweiIcon;
    @BindView(R.id.cinema_text_addr)
    TextView cinemaTextAddr;
    @BindView(R.id.film_seach_ima)
    ImageView filmSeachIma;
    @BindView(R.id.film_seach_edit)
    EditText filmSeachEdit;
    @BindView(R.id.film_seach_text)
    TextView filmSeachText;
    @BindView(R.id.film_seach_relative)
    RelativeLayout filmSeachRelative;
    @BindView(R.id.cinema_relative)
    RelativeLayout cinemaRelative;

    private String userId = "589";
    private String sessionId = "1552806657000";
    private int page = 1;
    private int count = 10;
    private String longitude = "116.30551391385724";
    private String latitude = "40.04571807462411";
    private Map<String, Object> headMap;
    private Map<String, Object> parms;
    private Map<String, Object> parms1;
    private List<Object> list = new ArrayList<>();
    private RecommendCinemasBean recommendCinemasBean;
    private MyRecommendAdapter myRecommendAdapter;
    private List<RecommendCinemasBean.ResultBean> result;
    private boolean flag = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cinema, container, false);
        unbinder = ButterKnife.bind(this, view);
        headMap = new HashMap<>();
        headMap.put("userId", userId);
        headMap.put("sessionId", sessionId);
        parms = new HashMap<>();
        parms.put("page", page);
        parms.put("count", count);
        parms1 = new HashMap<>();
        parms1.put("page", page);
        parms1.put("count", count);
        parms1.put("longitude", longitude);
        parms1.put("latitude", latitude);
        mPresenter.recommendPresenter(headMap, parms);
        btnRecommend.setBackgroundResource(R.drawable.top_btn_shape);
        btnRecommend.setTextColor(Color.WHITE);
        xrecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                flag = true;
                count = 10;
                parms.put("count", count);
                parms1.put("count", count);
                mPresenter.recommendPresenter(headMap, parms);
                mPresenter.nearbyPresenter(headMap, parms1);
                myRecommendAdapter.notifyDataSetChanged();
                xrecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                list.addAll(result);
                count++;
                parms.put("count", count);
                parms1.put("count", count);
                mPresenter.recommendPresenter(headMap, parms);
                mPresenter.nearbyPresenter(headMap, parms1);
                myRecommendAdapter.notifyDataSetChanged();
                xrecyclerView.loadMoreComplete();
            }
        });
        filmSeachIma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initfsi();
            }
        });

        filmSeachText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = filmSeachEdit.getText().toString();
                if (TextUtils.isEmpty(s)){
                    initfst();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //点击图标拉伸搜索框
    boolean mBoolean = true;

    private void initfsi() {
        if (mBoolean) {
            ObjectAnimator translationX = ObjectAnimator.ofFloat(filmSeachRelative, "translationX", 0, (dp2px(getActivity(), -170)));
            ObjectAnimator alpha = ObjectAnimator.ofFloat(filmSeachEdit, "alpha", 0.0f, 1.0f);
            ObjectAnimator alphaButton = ObjectAnimator.ofFloat(filmSeachText, "alpha", 0.0f, 1.0f);
            alphaButton.setDuration(1000);
            filmSeachText.setVisibility(VISIBLE);
            alphaButton.start();
            alpha.setDuration(1000);
            filmSeachEdit.setVisibility(VISIBLE);
            alpha.start();
            //动画时间
            translationX.setDuration(1000);
            translationX.start();
            mBoolean = !mBoolean;
        }
    }

    //收缩搜索框
    private void initfst() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(filmSeachRelative, "translationX", (dp2px(getActivity(), -170)), 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(filmSeachEdit, "alpha", 1.0f, 0.5f, 0.0f);
        ObjectAnimator alphaButton = ObjectAnimator.ofFloat(filmSeachText, "alpha", 1.0f, 0.5f, 0.0f);
        alphaButton.setDuration(1000);
        alphaButton.start();
        alpha.setDuration(1000);
        alpha.start();
        translationX.setDuration(1000);
        translationX.start();
        mBoolean = !mBoolean;
    }



    @Override
    public void recommendView(Object obj) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xrecyclerView.setLayoutManager(linearLayoutManager);
        if (obj != null) {
            recommendCinemasBean = (RecommendCinemasBean) obj;
            result = recommendCinemasBean.getResult();
//            Log.i("aa","recommendCinemasBean:"+recommendCinemasBean.getMessage());
            myRecommendAdapter = new MyRecommendAdapter(getActivity(), recommendCinemasBean);
            xrecyclerView.setAdapter(myRecommendAdapter);
        }
    }

    @Override
    public void nearbyView(Object obj) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xrecyclerView.setLayoutManager(linearLayoutManager);
        if (obj != null) {
            NearbyCinemasBean nearbyCinemasBean = (NearbyCinemasBean) obj;
//            Log.i("aa","nearbyCinemasBean:"+nearbyCinemasBean.getMessage());
            if (nearbyCinemasBean != null) {
                MyNearbyAdapter myNearbyAdapter = new MyNearbyAdapter(getActivity(), nearbyCinemasBean);
                xrecyclerView.setAdapter(myNearbyAdapter);
            }
        }
    }

    @OnClick({R.id.btn_Recommend, R.id.btn_Nearby})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_Recommend:
                mPresenter.recommendPresenter(headMap, parms);
                btnRecommend.setBackgroundResource(R.drawable.button_ripple);
                btnRecommend.setTextColor(Color.WHITE);
                btnNearby.setTextColor(Color.BLACK);
                btnNearby.setBackgroundResource(R.color.colorWhite);
                break;
            case R.id.btn_Nearby:
                mPresenter.nearbyPresenter(headMap, parms1);
                btnNearby.setBackgroundResource(R.drawable.button_ripple);
                btnRecommend.setBackgroundResource(R.color.colorWhite);
                btnNearby.setTextColor(Color.WHITE);
                btnRecommend.setTextColor(Color.BLACK);
                break;
        }
    }
}
