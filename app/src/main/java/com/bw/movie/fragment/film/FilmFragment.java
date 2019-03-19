package com.bw.movie.fragment.film;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.bw.movie.adapter.FilmAdapter;
import com.bw.movie.bean.JiFilmBean;
import com.bw.movie.bean.ReFilmBean;
import com.bw.movie.bean.ShangFilmBean;
import com.bw.movie.mvp.MVPBaseFragment;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FilmFragment extends MVPBaseFragment<FilmContract.View, FilmPresenter> implements FilmContract.View {
    @BindView(R.id.film_recycler)
    RecyclerView filmRecycler;
    Unbinder unbinder;
    public static final String TAG = "FilmFragment";
    private String userId = "589";
    private String sessionId = "1552717727805589";
    private List<ReFilmBean.ResultBean> reFilmBeanResult;
    private List<ShangFilmBean.ResultBean> shangFilmBeanResult;
    private List<JiFilmBean.ResultBean> jiFilmBeanResult;
    private FilmAdapter filmAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film, container, false);
        unbinder = ButterKnife.bind(this, view);

        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        filmRecycler.setLayoutManager(linearLayoutManager);


        //热门电影请求头集合
        HashMap<String, Object> headMap = new HashMap<>();
        //参数集合
        HashMap<String, Object> prams = new HashMap<>();
        headMap.put("userId", userId);
        headMap.put("sessionId", sessionId);
        prams.put("page", 1);
        prams.put("count", 10);

        mPresenter.getReMenPresenter(headMap, prams);
        mPresenter.getZhengPresenter(headMap, prams);
        mPresenter.getJiPresenter(headMap, prams);
        //设置适配器
        filmAdapter = new FilmAdapter(getActivity());
        filmRecycler.setAdapter(filmAdapter);
        return view;

    }


    /**
     * 热门电影
     *
     * @param object
     */
    @Override
    public void getReMenViewData(Object object) {
        if (object != null) {
            ReFilmBean reFilmBean = (ReFilmBean) object;
            //Log.i(TAG, "getReMenViewData: "+reFilmBean.getMessage());
            reFilmBeanResult = reFilmBean.getResult();
            filmAdapter.setReFilmBeanResult(reFilmBeanResult);
        }
    }

    /**
     * 正在上映
     *
     * @param object
     */
    @Override
    public void getZhengViewData(Object object) {
        if (object != null) {
            ShangFilmBean shangFilmBean = (ShangFilmBean) object;
            shangFilmBeanResult = shangFilmBean.getResult();
            //Log.i(TAG, "getZhengViewData: "+shangFilmBean.getMessage());
            filmAdapter.setShangFilmBeanResult(shangFilmBeanResult);
        }
    }

    /**
     * 即将上映
     *
     * @param object
     */
    @Override
    public void getJiViewData(Object object) {
        if (object != null) {
            JiFilmBean jiFilmBean = (JiFilmBean) object;
            jiFilmBeanResult = jiFilmBean.getResult();
            if (jiFilmBeanResult != null) {
                filmAdapter.setJiFilmBeanResult(jiFilmBeanResult);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
