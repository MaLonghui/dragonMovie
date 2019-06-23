package com.bw.movie.fragment.filmattention;


import android.content.Context;
import android.content.SharedPreferences;
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

import com.bw.movie.R;
import com.bw.movie.adapter.MyFilmAttentionAdapter;
import com.bw.movie.bean.FilmAttentionBean;
import com.bw.movie.mvp.MVPBaseFragment;
import com.bw.movie.net.NetWorkUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FilmattentionFragment extends MVPBaseFragment<FilmattentionContract.View, FilmattentionPresenter> implements FilmattentionContract.View {

    @BindView(R.id.recycler_attention_film)
    RecyclerView recyclerAttentionFilm;
    Unbinder unbinder;
    private SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filmattention, container, false);
       if (NetWorkUtils.isNetworkAvailable(getActivity())){
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
               mPresenter.FilmAttentionPresenter(headMap,parms);
           }
       }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void FilmAttentionView(Object obj) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerAttentionFilm.setLayoutManager(linearLayoutManager);
        if (obj!=null){
            FilmAttentionBean filmAttentionBean = (FilmAttentionBean) obj;
//            Log.i("aa","filmAttentionBean:"+filmAttentionBean.getMessage());
            if (filmAttentionBean.getStatus().equals("0000")){
                MyFilmAttentionAdapter myFilmAttentionAdapter = new MyFilmAttentionAdapter(getActivity(),filmAttentionBean);
                recyclerAttentionFilm.setAdapter(myFilmAttentionAdapter);
            }
        }
    }
}
