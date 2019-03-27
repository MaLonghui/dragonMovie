package com.bw.movie.fragment.alreadymoney;


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
import com.bw.movie.adapter.MyAlreadyMoneyAdapter;
import com.bw.movie.bean.TicketBean;
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

public class AlreadyMoneyFragment extends MVPBaseFragment<AlreadyMoneyContract.View, AlreadyMoneyPresenter> implements AlreadyMoneyContract.View {

    @BindView(R.id.recycler_view_already)
    RecyclerView recyclerViewAlready;
    Unbinder unbinder;
    private SharedPreferences sp;
    private String userId;
    private String sessionId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alreadymoney, container, false);
        unbinder = ButterKnife.bind(this, view);
        sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("userId", userId);
        headMap.put("sessionId", sessionId);
        Map<String, Object> parms = new HashMap<>();
        parms.put("page", 1);
        parms.put("count", 100);
        parms.put("status", 2);
        mPresenter.AlreadyTicketPresenter(headMap, parms);
        return view;
    }

    @Override
    public void AlreadyTicketView(Object obj) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerViewAlready.setLayoutManager(linearLayoutManager);
        if (obj!=null){
            TicketBean ticketBean = (TicketBean) obj;
            if (ticketBean!=null){
                MyAlreadyMoneyAdapter myAlreadyMoneyAdapter = new MyAlreadyMoneyAdapter(getActivity(),ticketBean);
                recyclerViewAlready.setAdapter(myAlreadyMoneyAdapter);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
