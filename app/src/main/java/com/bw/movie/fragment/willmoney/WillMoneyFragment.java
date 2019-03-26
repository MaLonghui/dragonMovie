package com.bw.movie.fragment.willmoney;


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
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.MyWillMoneyAdapter;
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

public class WillMoneyFragment extends MVPBaseFragment<WillMoneyContract.View, WillMoneyPresenter> implements WillMoneyContract.View {

    @BindView(R.id.recycler_view_will)
    RecyclerView recyclerViewWill;
    Unbinder unbinder;
    private SharedPreferences sp;
    private String userId;
    private String sessionId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_willmoney, container, false);
        sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("userId", userId);
        headMap.put("sessionId", sessionId);
        Map<String, Object> parms = new HashMap<>();
        parms.put("page", 1);
        parms.put("count", 100);
        parms.put("status", 1);
        mPresenter.WillTicketPresenter(headMap, parms);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void WillTicketView(Object obj) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerViewWill.setLayoutManager(linearLayoutManager);
        if (obj != null) {
            TicketBean ticketBean = (TicketBean) obj;
            if (ticketBean != null) {
                MyWillMoneyAdapter myWillMoneyAdapter = new MyWillMoneyAdapter(getActivity(),ticketBean);
                recyclerViewWill.setAdapter(myWillMoneyAdapter);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
