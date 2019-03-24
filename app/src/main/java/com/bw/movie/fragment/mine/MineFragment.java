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
import com.bw.movie.activity.attention.AttentionActivity;
import com.bw.movie.activity.feedback.FeedbackActivity;
import com.bw.movie.activity.info.InfoActivity;
import com.bw.movie.activity.msg.MsgActivity;
import com.bw.movie.bean.FindInfoBean;
import com.bw.movie.bean.UserHeadIconBean;
import com.bw.movie.mvp.MVPBaseFragment;
import com.bw.movie.net.NoStudoInterent;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
    private PopupWindow popupWindow;
    private final int CAIJIAN_FLAG=200;
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
        if (NoStudoInterent.isNetworkAvailable(getActivity())) {
            mPresenter.userInfoPresenter(headMap);
        }

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
        if (object!=null){
            UserHeadIconBean userHeadIconBean = (UserHeadIconBean) object;
            if (userHeadIconBean.getStatus().equals("0000")) {
               Toast.makeText(getActivity(),userHeadIconBean.getMessage(),Toast.LENGTH_LONG).show();
                //重新请求数据实现即时更新头像图片

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
                    startActivity(new Intent(getActivity(),MsgActivity.class));
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
                    startActivity(new Intent(getActivity(),AttentionActivity.class));
                    break;
                case R.id.my_rccord:
                    break;
                case R.id.my_feedbacks:
                    startActivity(new Intent(getActivity(),FeedbackActivity.class));
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
        Button btnQu = view.findViewById(R.id.btn_qu);
        btnCamera.setOnClickListener(this);
        btnAlbum.setOnClickListener(this);
        btnQu.setOnClickListener(this);
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine, null);
        popupWindow.showAsDropDown(view1,Gravity.BOTTOM,0,0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_camera:
                //意图
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //带值跳转
                startActivityForResult(intent,111);
                popupWindow.dismiss();
                break;
            case R.id.btn_album:
                //意图
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                //类型
                intent1.setType("image/*");
                //带值跳转
                startActivityForResult(intent1,222);
                popupWindow.dismiss();
                break;
            case R.id.btn_qu:
                popupWindow.dismiss();
                break;

        }
    }
    /**
     * 吊起相机
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 111:
                Toast.makeText(getActivity(),"滴滴滴",Toast.LENGTH_LONG).show();
                Bitmap bitmap = data.getParcelableExtra("data");
                ArrayList<Object> list = new ArrayList<>();
                list.add(bitmap);
                String s = bitmapToString(bitmap);
                myIcon.setImageURI(UriUtil.parseUriOrNull("file://"+s));
                Map<String,Object> headMap = new HashMap<>();
                headMap.put("userId",userId);
                headMap.put("sessionId",sessionId);
                Map<String, String> map = new HashMap<>();
                map.put("image", s);
                Log.i("aa","filepath:"+s);
//            doPostImageData(Apis.MESSAGE_INFO_HEAD, map, InfoHeadBean.class);
                mPresenter.headIconPresenter(headMap,map);
                break;
            case 222:
                //路径
                if (data!=null){
                    Uri uri = data.getData();
                    crop(uri);
                }
                break;
            case CAIJIAN_FLAG:
                if (data!=null) {
                    Bitmap bitmap1 = data.getParcelableExtra("data");
                    String s1 = bitmapToString(bitmap1);
                    myIcon.setImageURI(UriUtil.parseUriOrNull("file://"+s1));
                    Toast.makeText(getActivity(),s1,Toast.LENGTH_LONG).show();
                    findInfoBean.getResult().setHeadPic("http://172.17.8.100/images/movie/stills/ws/ws1.jpg");
                    Map<String,Object> headMap1 = new HashMap<>();
                    headMap1.put("userId",userId);
                    headMap1.put("sessionId",sessionId);
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("image", s1);
                    Log.i("aa","filepath:"+s1);
//            doPostImageData(Apis.MESSAGE_INFO_HEAD, map, InfoHeadBean.class);
                    mPresenter.headIconPresenter(headMap1,map1);
                }
                break;

        }
    }
    public String bitmapToString(Bitmap bitmap){
        //将bitmap转换为uri
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, null,null));

        String[] proj = { MediaStore.Images.Media.DATA };

        Cursor actualimagecursor =getActivity().managedQuery(uri,proj,null,null,null);

        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        actualimagecursor.moveToFirst();

        String img_path = actualimagecursor.getString(actual_image_column_index);
        return img_path;
    }

    //剪裁图片
    private void crop(Uri uri){
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        //支持裁剪
        intent.putExtra("CROP",true);
        //裁剪的比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //裁剪后输出图片的尺寸大小
        intent.putExtra("outputX",250);
        intent.putExtra("outputY",250);
        //将图片返回给data
        intent.putExtra("return-data",true);
        startActivityForResult(intent,CAIJIAN_FLAG);
    }


    public static MultipartBody filesMutipar(Map<String,String> map){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (Map.Entry<String,String> entry:map.entrySet()){
            if (entry.getKey().equals("image")){
                File file = new File(entry.getValue());
                builder.addFormDataPart(entry.getKey(),"tp.png", RequestBody.create(MediaType.parse("multipart/form-data"),file));
            }
        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }
    //动态权限
    private void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(getActivity(), mPermissionList, 123);
        }
    }
}
