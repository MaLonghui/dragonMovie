package com.bw.movie.fragment.cinema;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.MyNearbyAdapter;
import com.bw.movie.adapter.MyRecommendAdapter;
import com.bw.movie.bean.CinemaAttentionBean;
import com.bw.movie.bean.NearbyCinemasBean;
import com.bw.movie.bean.RecommendCinemasBean;
import com.bw.movie.mvp.MVPBaseFragment;
import com.bw.movie.net.NoStudoInterent;
import com.bw.movie.utils.AlertDialogUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    RecyclerView xrecyclerView;
    Unbinder unbinder;
    private int page = 1;
    private int count = 20;
    private String longitude = "116.30551391385724";
    private String latitude = "40.04571807462411";
    private List<Object> list = new ArrayList<>();
    private RecommendCinemasBean recommendCinemasBean;
    private MyRecommendAdapter myRecommendAdapter;
    private List<RecommendCinemasBean.ResultBean> result;
    private boolean flag = false;
    private SharedPreferences sp;
    private String userId;
    private String sessionId;
    private CinemaAttentionBean cinemaAttentionBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cinema, container, false);
        unbinder = ButterKnife.bind(this, view);
        sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");


        if (NoStudoInterent.isNetworkAvailable(getActivity())) {
            if (!userId.equals("")&&!sessionId.equals("")){
                Map<String,Object> headMap = new HashMap<>();
                headMap.put("userId", userId);
                headMap.put("sessionId", sessionId);
                Map<String,Object> parms = new HashMap<>();
                parms.put("page",page);
                parms.put("count",count);
                mPresenter.recommendPresenter(headMap, parms);
            }else{
                Map<String,Object> headMap = new HashMap<>();
                Map<String,Object> parms = new HashMap<>();
                parms.put("page",page);
                parms.put("count",count);
                mPresenter.recommendPresenter(headMap, parms);
            }

        }
        btnRecommend.setBackgroundResource(R.drawable.top_btn_shape);
        btnRecommend.setTextColor(Color.WHITE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void recommendView(Object obj) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xrecyclerView.setLayoutManager(linearLayoutManager);
        if (obj!=null){
            recommendCinemasBean = (RecommendCinemasBean) obj;
            result = recommendCinemasBean.getResult();
//            Log.i("aa","recommendCinemasBean:"+recommendCinemasBean.getMessage());
            myRecommendAdapter = new MyRecommendAdapter(getActivity());
            xrecyclerView.setAdapter(myRecommendAdapter);
            myRecommendAdapter.setList(recommendCinemasBean);
            myRecommendAdapter.setAttentionClick(new MyRecommendAdapter.AttentionClick() {
                @Override
                public void clickattention(String cinemaId,boolean b) {
                    if (b){
                        if (!userId.equals("")&&!sessionId.equals("")){
                            Toast.makeText(getActivity(),cinemaId,Toast.LENGTH_LONG).show();
                            Map<String,Object> headMap = new HashMap<>();
                            headMap.put("userId",userId);
                            headMap.put("sessionId",sessionId);
                            mPresenter.AttentionPresenter(headMap,cinemaId);
                            myRecommendAdapter.notifyDataSetChanged();
                        }else{
                            AlertDialogUtils.AlertDialogLogin(getActivity());
                        }
                        myRecommendAdapter.notifyDataSetChanged();
                    }else{

                    }
                }
            });
        }
    }

    @Override
    public void nearbyView(Object obj) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xrecyclerView.setLayoutManager(linearLayoutManager);
        if (obj!=null){
            NearbyCinemasBean nearbyCinemasBean = (NearbyCinemasBean) obj;
//            Log.i("aa","nearbyCinemasBean:"+nearbyCinemasBean.getMessage());
            if (nearbyCinemasBean!=null){
                MyNearbyAdapter myNearbyAdapter = new MyNearbyAdapter(getActivity(),nearbyCinemasBean);
                xrecyclerView.setAdapter(myNearbyAdapter);
            }
        }
    }

    @Override
    public void AttentionView(Object obj) {
        if (obj!=null){
            cinemaAttentionBean = (CinemaAttentionBean) obj;
            Log.i("aa","cinemaAttentionBean:"+ cinemaAttentionBean.getMessage());
            if (cinemaAttentionBean.getStatus().equals("0000")){
//                Map<String,Object> headMap = new HashMap<>();
//                headMap.put("userId", userId);
//                headMap.put("sessionId", sessionId);
//                Map<String,Object> parms = new HashMap<>();
//                parms.put("page",page);
//                parms.put("count",count);
//                mPresenter.recommendPresenter(headMap, parms);
            }
        }
    }

    @OnClick({R.id.btn_Recommend, R.id.btn_Nearby})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_Recommend:
                if (!userId.equals("")&&!sessionId.equals("")){
                    Map<String,Object> headMap = new HashMap<>();
                    headMap.put("userId", userId);
                    headMap.put("sessionId", sessionId);
                    Map<String,Object> parms = new HashMap<>();
                    parms.put("page",page);
                    parms.put("count",count);
                    mPresenter.recommendPresenter(headMap,parms);
                }else{
                    Map<String,Object> headMap = new HashMap<>();
                    Map<String,Object> parms = new HashMap<>();
                    parms.put("page",page);
                    parms.put("count",count);
                    mPresenter.recommendPresenter(headMap,parms);
                }

                btnRecommend.setBackgroundResource(R.drawable.button_ripple);
                btnRecommend.setTextColor(Color.WHITE);
                btnNearby.setTextColor(Color.BLACK);
                btnNearby.setBackgroundResource(R.color.colorWhite);
                break;
            case R.id.btn_Nearby:
                if (!userId.equals("")&&!sessionId.equals("")){
                    Map<String,Object> headMap = new HashMap<>();
                    headMap.put("userId", userId);
                    headMap.put("sessionId", sessionId);
                    Map<String,Object> parms1 = new HashMap<>();
                    parms1.put("page",page);
                    parms1.put("count",count);
                    parms1.put("longitude",longitude);
                    parms1.put("latitude",latitude);
                    mPresenter.nearbyPresenter(headMap,parms1);
                }else{
                    Map<String,Object> headMap = new HashMap<>();
                    Map<String,Object> parms1 = new HashMap<>();
                    parms1.put("page",page);
                    parms1.put("count",count);
                    parms1.put("longitude",longitude);
                    parms1.put("latitude",latitude);
                    mPresenter.nearbyPresenter(headMap,parms1);
                }
                btnNearby.setBackgroundResource(R.drawable.button_ripple);
                btnRecommend.setBackgroundResource(R.color.colorWhite);
                btnNearby.setTextColor(Color.WHITE);
                btnRecommend.setTextColor(Color.BLACK);
                break;
        }
    }
}
