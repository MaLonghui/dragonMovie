package com.bw.movie.activity.feedback;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.activity.cinemabymovieid.CinemaByMovieIdActivity;
import com.bw.movie.bean.FeedBackBean;
import com.bw.movie.mvp.MVPBaseActivity;
import com.bw.movie.net.NetWorkUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FeedbackActivity extends MVPBaseActivity<FeedbackContract.View, FeedbackPresenter> implements FeedbackContract.View {

    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.btn_tj)
    Button btnTj;
    @BindView(R.id.relat_feek)
    RelativeLayout relatFeek;
    private SharedPreferences sp;
    private String sessionId;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
       if (NetWorkUtils.isNetworkAvailable(FeedbackActivity.this)){
           sp = getSharedPreferences("config", Context.MODE_PRIVATE);
           userId = sp.getString("userId", "");
           sessionId = sp.getString("sessionId", "");
       }
    }

    @Override
    public void FeekbackView(Object obj) {
        if (NetWorkUtils.isNetworkAvailable(FeedbackActivity.this)) {
            if (obj != null) {
                FeedBackBean feedBackBean = (FeedBackBean) obj;
                if (feedBackBean.getStatus().equals("0000")) {
                    relatFeek.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @OnClick(R.id.btn_tj)
    public void onViewClicked() {
        if (NetWorkUtils.isNetworkAvailable(FeedbackActivity.this)) {
            Map<String, Object> headMap = new HashMap<>();
            headMap.put("userId", userId);
            headMap.put("sessionId", sessionId);
            String trim = editContent.getText().toString().trim();
            mPresenter.FeekbackPresenter(headMap, trim);
        }
    }
}
