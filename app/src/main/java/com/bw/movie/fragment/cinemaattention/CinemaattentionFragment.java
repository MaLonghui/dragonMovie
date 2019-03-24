package com.bw.movie.fragment.cinemaattention;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.adapter.MyMovieAttentionAdapter;
import com.bw.movie.bean.MovieAttentionBean;
import com.bw.movie.mvp.MVPBaseFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CinemaattentionFragment extends MVPBaseFragment<CinemaattentionContract.View, CinemaattentionPresenter> implements CinemaattentionContract.View {

    @BindView(R.id.recycler_attention_movie)
    RecyclerView recyclerAttentionMovie;
    Unbinder unbinder;
    private SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cinemaattention, container, false);
        unbinder = ButterKnife.bind(this, view);
        sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        String userId = sp.getString("userId", "");
        String sessionId = sp.getString("sessionId", "");
        if (!userId.equals("")&&!sessionId.equals("")){
            Map<String,Object> headMap = new HashMap<>();
            headMap.put("userId",userId);
            headMap.put("sessionId",sessionId);
            Map<String,Object> parms = new HashMap<>();
            parms.put("page",1);
            parms.put("count",10);
            mPresenter.MovieAttentionPresenter(headMap,parms);
        }
        return view;
    }

    @Override
    public void MovieAttentionView(Object obj) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerAttentionMovie.setLayoutManager(linearLayoutManager);
        if (obj!=null){
            MovieAttentionBean movieAttentionBean = (MovieAttentionBean) obj;
            if (movieAttentionBean.getStatus().equals("0000")){
                MyMovieAttentionAdapter myMovieAttentionAdapter = new MyMovieAttentionAdapter(getActivity(),movieAttentionBean);
                recyclerAttentionMovie.setAdapter(myMovieAttentionAdapter);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
