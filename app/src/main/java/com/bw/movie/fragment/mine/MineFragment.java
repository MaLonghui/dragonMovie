package com.bw.movie.fragment.mine;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.FindInfoBean;
import com.bw.movie.mvp.MVPBaseFragment;
import com.bw.movie.utils.AlertDialogUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MineFragment extends MVPBaseFragment<MineContract.View, MinePresenter> implements MineContract.View {
    @BindView(R.id.my_message)
    ImageView myMessage;
    @BindView(R.id.my_icon)
    SimpleDraweeView myIcon;
    @BindView(R.id.my_name)
    TextView myName;
    @BindView(R.id.my_sign_in)
    Button mySignIn;
    @BindView(R.id.my_info)
    RelativeLayout myInfo;
    @BindView(R.id.my_attentions)
    RelativeLayout myAttentions;
    @BindView(R.id.my_rccord)
    RelativeLayout myRccord;
    @BindView(R.id.my_feedbacks)
    RelativeLayout myFeedbacks;
    @BindView(R.id.my_version)
    RelativeLayout myVersion;
    @BindView(R.id.my_logout)
    RelativeLayout myLogout;
    Unbinder unbinder;
    private String userId = "589";
    private String sessionId = "1552806657000";
    private FindInfoBean findInfoBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        Map<String,Object> headMap = new HashMap<>();
        headMap.put("userId",userId);
        headMap.put("sessionId",sessionId);
        mPresenter.userInfoPresenter(headMap);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void userInfoView(Object obj) {
        if (obj!=null){
            findInfoBean = (FindInfoBean) obj;
            Log.i("aa","findInfoBean:"+findInfoBean.getStatus());
        }
    }

    @OnClick({R.id.my_message, R.id.my_icon, R.id.my_name, R.id.my_sign_in, R.id.my_info, R.id.my_attentions, R.id.my_rccord, R.id.my_feedbacks, R.id.my_version, R.id.my_logout})
    public void onViewClicked(View view) {

        if (findInfoBean.getStatus().equals("9999")){
            AlertDialogUtils.AlertDialogLogin(getActivity());
        }else{
            switch (view.getId()) {
                case R.id.my_message:
                    break;
                case R.id.my_icon:
                    break;
                case R.id.my_name:
                    break;
                case R.id.my_sign_in:
                    break;
                case R.id.my_info:
                    break;
                case R.id.my_attentions:
                    break;
                case R.id.my_rccord:
                    break;
                case R.id.my_feedbacks:
                    break;
                case R.id.my_version:
                    break;
                case R.id.my_logout:
                    break;
            }
        }
    }


}
