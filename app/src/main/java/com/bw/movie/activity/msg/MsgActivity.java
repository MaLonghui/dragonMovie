package com.bw.movie.activity.msg;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.MySysMsgAdapter;
import com.bw.movie.bean.SysMsgBean;
import com.bw.movie.bean.SysMsgStatusBean;
import com.bw.movie.mvp.MVPBaseActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MsgActivity extends MVPBaseActivity<MsgContract.View, MsgPresenter> implements MsgContract.View {

    @BindView(R.id.xRecycler_msg)
    XRecyclerView xRecyclerMsg;
    @BindView(R.id.text_msg)
    TextView textMsg;
    private SharedPreferences sp;
    private String sessionId;
    private String userId;
    private int page = 1;
    private int count = 10;
    private int flag = 0;
    private  int max = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sysmsg);
        ButterKnife.bind(this);
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("userId", userId);
        headMap.put("sessionId", sessionId);
        Map<String, Object> parms = new HashMap<>();
        parms.put("page", page);
        parms.put("count", count);
        mPresenter.SysMsgPresenter(headMap, parms);
        xRecyclerMsg.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                Map<String, Object> headMap = new HashMap<>();
                headMap.put("userId", userId);
                headMap.put("sessionId", sessionId);
                Map<String, Object> parms = new HashMap<>();
                parms.put("page", page);
                parms.put("count", count);
                mPresenter.SysMsgPresenter(headMap, parms);
                xRecyclerMsg.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                page++;
                Map<String, Object> headMap = new HashMap<>();
                headMap.put("userId", userId);
                headMap.put("sessionId", sessionId);
                Map<String, Object> parms = new HashMap<>();
                parms.put("page", page);
                parms.put("count", count);
                mPresenter.SysMsgPresenter(headMap, parms);
                xRecyclerMsg.loadMoreComplete();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void SysMsgView(Object object) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MsgActivity.this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerMsg.setLayoutManager(linearLayoutManager);
        if (object != null) {
            SysMsgBean sysMsgBean = (SysMsgBean) object;
            List<SysMsgBean.ResultBean> result = sysMsgBean.getResult();
            for (SysMsgBean.ResultBean resultBean : result) {
                if (resultBean.getStatus().equals("0")) {
                    max = max+ 1;
                }
                textMsg.setText("系统消息"+"("+max+"条未读)");
            }
//            Log.i("aa","sysMsgBean:"+sysMsgBean.getMessage());
            if (sysMsgBean.getStatus().equals("0000")) {
                MySysMsgAdapter mySysMsgAdapter = new MySysMsgAdapter(MsgActivity.this);
                xRecyclerMsg.setAdapter(mySysMsgAdapter);
                mySysMsgAdapter.setList(sysMsgBean);
                mySysMsgAdapter.setStatusList(new MySysMsgAdapter.SysMsgStatusClick() {
                    @Override
                    public void statusClick(String id) {
                        Map<String, Object> headMap1 = new HashMap<>();
                        headMap1.put("userId", userId);
                        headMap1.put("sessionId", sessionId);
                        mPresenter.SysMsgStatusPresenter(headMap1, id);

                    }
                });
            }
        }
        max = 0;
    }

    @Override
    public void SysMsgStatusView(Object obj) {
        if (obj != null) {
            SysMsgStatusBean sysMsgStatusBean = (SysMsgStatusBean) obj;
            if (sysMsgStatusBean.getStatus().equals("0000")) {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put("userId", userId);
                headMap.put("sessionId", sessionId);
                Map<String, Object> parms = new HashMap<>();
                parms.put("page", page);
                parms.put("count", count);
                mPresenter.SysMsgPresenter(headMap, parms);
                Toast.makeText(MsgActivity.this, sysMsgStatusBean.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
