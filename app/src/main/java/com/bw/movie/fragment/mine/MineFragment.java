package com.bw.movie.fragment.mine;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import com.bw.movie.activity.info.InfoActivity;
import com.bw.movie.bean.FindInfoBean;
import com.bw.movie.bean.UserHeadIconBean;
import com.bw.movie.mvp.MVPBaseFragment;
import com.bw.movie.utils.AlertDialogUtils;
import com.bw.movie.utils.FileImageUntils;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

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
    private Bitmap head;
    private PopupWindow popupWindow;
    //相机拍照的照片路径
    private String filepath = Environment.getExternalStorageDirectory()
            + "/file.png";
    private String mPath;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String userId = sp.getString("userId", "");
        String sessionId = sp.getString("sessionId", "");
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
            }
        }
    }

    @Override
    public void headIconView(Object object) {
        if (object!=null){
            UserHeadIconBean userHeadIconBean = (UserHeadIconBean) object;
            if (userHeadIconBean.getStatus().equals("0000")) {
               Toast.makeText(getActivity(),userHeadIconBean.getMessage(),Toast.LENGTH_LONG).show();
                //重新请求数据实现即时更新头像图片
                String userId = sp.getString("userId", "");
                String sessionId = sp.getString("sessionId", "");
                Map<String,Object> headMap = new HashMap<>();
                headMap.put("userId",userId);
                headMap.put("sessionId",sessionId);
                Map<String, String> map = new HashMap<>();
                map.put("image", filepath);
                Log.i("aa","filepath:"+filepath);
//            doPostImageData(Apis.MESSAGE_INFO_HEAD, map, InfoHeadBean.class);
                mPresenter.headIconPresenter(headMap,map);
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
                    break;
                case R.id.my_icon:
//                    Toast.makeText(getActivity(), "个人头像", Toast.LENGTH_LONG).show();
                    showChoosePicPopWindow();
                    break;
                case R.id.my_name:
                    break;
                case R.id.my_sign_in:
                    break;
                case R.id.my_info:
                    startActivity(new Intent(getActivity(),InfoActivity.class));
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
                    Toast.makeText(getActivity(), "用户退出登录", Toast.LENGTH_LONG).show();
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put("userId", "");
                    headMap.put("sessionId", "");
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
        popupWindow = new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        Button btnCamera = view.findViewById(R.id.btn_camera);
        Button btnAlbum = view.findViewById(R.id.btn_album);
        btnCamera.setOnClickListener(this);
        btnAlbum.setOnClickListener(this);
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine, null);
        popupWindow.showAsDropDown(view1,Gravity.BOTTOM,0,0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_camera:
                mPath = Environment.getExternalStorageDirectory() + "/image.png";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //在Sdcard存入图片
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mPath)));
                startActivityForResult(intent, 300);
                popupWindow.dismiss();
                initPermission();
                break;
            case R.id.btn_album:
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                //设置图片的格式
                intent1.setType("image/*");
                startActivityForResult(intent1, 100);
                popupWindow.dismiss();
                initPermission();
                break;
            case R.id.btn_qu:
                popupWindow.dismiss();
                break;

        }
    }

    /**
     * 剪切
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //调取裁剪
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //得到相册图片的路径
            Uri uri = data.getData();
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(uri, "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出的大小
            intent.putExtra("outputX", 250);
            intent.putExtra("outputY", 250);
            //将图片进行返回
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 200);

        }
        if(requestCode == 300 && resultCode == RESULT_OK ){
            //将图片设置给裁剪
            crop(Uri.fromFile(new File(mPath)));
        }
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Bitmap bitmap = data.getParcelableExtra("data");
            Log.i("TAG","bitmap=="+bitmap);
            FileImageUntils.setBitmap(bitmap, filepath, 50);
            //TODO:网络请求
            String userId = sp.getString("userId", "");
            String sessionId = sp.getString("sessionId", "");
            Map<String,Object> headMap = new HashMap<>();
            headMap.put("userId",userId);
            headMap.put("sessionId",sessionId);
            Map<String, String> map = new HashMap<>();
            map.put("image", filepath);
//            doPostImageData(Apis.MESSAGE_INFO_HEAD, map, InfoHeadBean.class);
            mPresenter.headIconPresenter(headMap,map);
        }
    }

    // 裁剪方法
    private void crop(Uri data) {
        Intent cIntent = new Intent("com.android.camera.action.CROP");
        cIntent.setDataAndType(data, "file.png");
        cIntent.putExtra("crop", true);
        cIntent.putExtra("aspectX", 1);
        cIntent.putExtra("aspectY", 1);
        cIntent.putExtra("outputX", 249);
        cIntent.putExtra("outputY", 249);
        cIntent.putExtra("return-data", true);
        startActivityForResult(cIntent, 200);
    }
    //动态权限
    private void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(getActivity(), mPermissionList, 123);
        }
    }
}
