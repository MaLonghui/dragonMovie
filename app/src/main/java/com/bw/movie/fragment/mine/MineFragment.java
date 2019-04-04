package com.bw.movie.fragment.mine;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.attention.AttentionActivity;
import com.bw.movie.activity.feedback.FeedbackActivity;
import com.bw.movie.activity.info.InfoActivity;
import com.bw.movie.activity.msg.MsgActivity;
import com.bw.movie.activity.reccord.ReccordActivity;
import com.bw.movie.activity.vision.VisionActivity;
import com.bw.movie.bean.FindInfoBean;
import com.bw.movie.bean.SignInBean;
import com.bw.movie.bean.UserHeadIconBean;
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

public class MineFragment extends MVPBaseFragment<MineContract.View, MinePresenter> implements MineContract.View, View.OnClickListener {
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
    private FindInfoBean findInfoBean;
    private SharedPreferences sp;
    private Map<String, Object> headMap;
    private PopupWindow popupWindow;
    private String userId;
    private String sessionId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
        if (!userId.equals("") && !sessionId.equals("")) {
            Map<String, Object> headMap1 = new HashMap<>();
            headMap1.put("userId", userId);
            headMap1.put("sessionId", sessionId);
            mPresenter.SignInPresenter(headMap1);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
//        Log.i("aa",userId+"++++"+sessionId);
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("userId", userId);
        headMap.put("sessionId", sessionId);
        mPresenter.userInfoPresenter(headMap);



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void userInfoView(Object obj) {
        if (obj != null) {
            findInfoBean = (FindInfoBean) obj;
//            Log.i("aa","findInfoBean:"+findInfoBean.getStatus());
            if (findInfoBean.getStatus().equals("9999")) {
                myLogout.setVisibility(View.GONE);
            } else {
                myLogout.setVisibility(View.VISIBLE);
                myIcon.setImageURI(findInfoBean.getResult().getHeadPic());
                myName.setText(findInfoBean.getResult().getNickName());
            }
        }
    }

    @Override
    public void headIconView(Object object) {
        if (object != null) {
            UserHeadIconBean userHeadIconBean = (UserHeadIconBean) object;
            Log.i("aa", "userHeadIconBean:" + userHeadIconBean.getMessage());
            if (userHeadIconBean.getStatus().equals("0000")) {
                myIcon.setImageURI(Uri.parse(userHeadIconBean.getHeadPath()));
//               Toast.makeText(getActivity(),userHeadIconBean.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void SignInView(Object obj) {
        if (obj != null) {
            SignInBean signInBean = (SignInBean) obj;

            if (signInBean.getStatus().equals("0000")) {
                mySignIn.setBackgroundResource(R.drawable.shape_bg_button);
//                Toast.makeText(getActivity(),signInBean.getMessage(),Toast.LENGTH_LONG).show();
            } else {
                mySignIn.setBackgroundResource(R.drawable.shape_signin);
//                Toast.makeText(getActivity(),signInBean.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick({R.id.my_message, R.id.my_icon, R.id.my_name, R.id.my_sign_in, R.id.my_info, R.id.my_attentions, R.id.my_rccord, R.id.my_feedbacks, R.id.my_version, R.id.my_logout})
    public void onViewClicked(View view) {
        if (findInfoBean.getStatus().equals("9999")) {
            AlertDialogUtils.AlertDialogLogin(getActivity());
        } else {
            switch (view.getId()) {
                case R.id.my_message:
                    startActivity(new Intent(getActivity(), MsgActivity.class));
                    break;
                case R.id.my_icon:
//                    Toast.makeText(getActivity(), "个人头像", Toast.LENGTH_LONG).show();
                    showChoosePicPopWindow();
                    break;
                case R.id.my_name:
                    break;
                case R.id.my_sign_in:
                    Map<String, Object> headMap1 = new HashMap<>();
                    headMap1.put("userId", userId);
                    headMap1.put("sessionId", sessionId);
                    mPresenter.SignInPresenter(headMap1);
                    break;
                case R.id.my_info:
                    startActivity(new Intent(getActivity(), InfoActivity.class));
                    break;
                case R.id.my_attentions:
                    startActivity(new Intent(getActivity(), AttentionActivity.class));
                    break;
                case R.id.my_rccord:
                    startActivity(new Intent(getActivity(), ReccordActivity.class));
                    break;
                case R.id.my_feedbacks:
                    startActivity(new Intent(getActivity(), FeedbackActivity.class));
                    break;
                case R.id.my_version:
                    startActivity(new Intent(getActivity(), VisionActivity.class));
                    break;
                case R.id.my_logout:
                    Toast.makeText(getActivity(), "用户退出登录", Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("userId", "");
                    edit.putString("sessionId", "");
                    edit.commit();
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put("userId", sp.getString("userId", userId));
                    headMap.put("sessionId", sp.getString("sessionId", sessionId));
                    mPresenter.userInfoPresenter(headMap);
                    myLogout.setVisibility(View.GONE);
                    break;
            }
        }
    }

    /**
     * 显示修改头像的对话框
     */
    private void showChoosePicPopWindow() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_layout, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        Button btnCamera = view.findViewById(R.id.btn_camera);
        Button btnAlbum = view.findViewById(R.id.btn_album);
        Button btnQu = view.findViewById(R.id.btn_qu);
        btnCamera.setOnClickListener(this);
        btnAlbum.setOnClickListener(this);
        btnQu.setOnClickListener(this);
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine, null);
        popupWindow.showAsDropDown(view1, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera:
                if (Build.VERSION.SDK_INT >= 23) {
                    String[] mPermissionList = new String[]{Manifest.permission.CAMERA};
                    ActivityCompat.requestPermissions(getActivity(), mPermissionList, 123);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getActivity().startActivityForResult(intent, 1);

                popupWindow.dismiss();
                break;
            case R.id.btn_album:
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("image/*");
                getActivity().startActivityForResult(intent1, 2);
                popupWindow.dismiss();
                break;
            case R.id.btn_qu:
                popupWindow.dismiss();
                break;

        }
    }
}
