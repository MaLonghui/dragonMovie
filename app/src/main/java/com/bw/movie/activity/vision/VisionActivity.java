package com.bw.movie.activity.vision;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ansen.http.net.HTTPCaller;
import com.ansen.http.net.RequestDataCallback;
import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.bean.VisionBean;
import com.bw.movie.mvp.MVPBaseActivity;
import com.bw.movie.net.NetWorkUtils;
import com.bw.movie.utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.lizhangqu.coreprogress.ProgressUIListener;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class VisionActivity extends MVPBaseActivity<VisionContract.View, VisionPresenter> implements VisionContract.View, View.OnClickListener {

    private SharedPreferences sp;
    private String userId;
    private String sessionId;
    private ProgressDialog progressDialog;
    private String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private final int PERMS_REQUEST_CODE = 200;
    private VisionBean visionBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision);
        ButterKnife.bind(this);
        if (NetWorkUtils.isNetworkAvailable(VisionActivity.this)) {
            sp = getSharedPreferences("config", Context.MODE_PRIVATE);
            userId = sp.getString("userId", "");
            sessionId = sp.getString("sessionId", "");


            TextView tvCurrentVersionCode = (TextView) findViewById(R.id.tv_current_version_code);
            tvCurrentVersionCode.setText("当前版本:" + Utils.getVersionCode(this));

            //Android 6.0以上版本需要临时获取权限
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 &&
                    PackageManager.PERMISSION_GRANTED != checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                requestPermissions(perms, PERMS_REQUEST_CODE);
            } else {
                findViewById(R.id.btn_check_update).setOnClickListener(this);
            }


        }
    }

    @Override
    public void visionView(Object obj) {
        if (NetWorkUtils.isNetworkAvailable(VisionActivity.this)) {
            if (obj != null) {
                visionBean = (VisionBean) obj;
                if (visionBean.getStatus().equals("0000")) {//有新版本
                    HTTPCaller.getInstance().get(VisionBean.class, visionBean.getDownloadUrl(), null, null);
                    showUpdaloadDialog(((VisionBean) obj).getDownloadUrl());
                } else {//没有新版本
                    Toast.makeText(VisionActivity.this, ((VisionBean) obj).getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (NetWorkUtils.isNetworkAvailable(VisionActivity.this)) {
            switch (v.getId()) {
                case R.id.btn_check_update://检查更新
//                Log.i("aa","visionBean:"+ visionBean.getDownloadUrl());
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put("userId", userId);
                    headMap.put("sessionId", sessionId);
                    headMap.put("ak", "0110010010000");
                    mPresenter.visionPresenter(headMap);
                    break;
            }
        }
    }

//    private RequestDataCallback<VisionBean> requestDataCallback=new RequestDataCallback<VisionBean>(){
//        @Override
//        public void dataCallback(VisionBean obj) {
//            if(obj!=null){
//                if(obj.getFlag().equals("1")){//有新版本
//                    showUpdaloadDialog(obj.getDownloadUrl());
//                }else{//没有新版本
//                    Toast.makeText(VisionActivity.this,obj.getMessage(),Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//    };

    /**
     * 显示更新对话框
     *
     * @param downloadUrl
     */
    private void showUpdaloadDialog(final String downloadUrl) {
        // 这里的属性可以一直设置，因为每次设置后返回的是一个builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置提示框的标题
        builder.setTitle("版本升级").
                setIcon(R.mipmap.ic_launcher). // 设置提示框的图标
                setMessage("发现新版本！请及时更新").// 设置要显示的信息
                setPositiveButton("确定", new DialogInterface.OnClickListener() {// 设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startUpload(downloadUrl);//下载最新的版本程序
            }
        }).setNegativeButton("取消", null);//设置取消按钮,null是什么都不做，并关闭对话框
        AlertDialog alertDialog = builder.create();
        // 显示对话框
        alertDialog.show();
    }

    /**
     * 开始下载
     *
     * @param downloadUrl 下载url
     */
    private void startUpload(String downloadUrl) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("正在下载新版本");
        progressDialog.setCancelable(false);//不能手动取消下载进度对话框

        final String fileSavePath = Utils.getSaveFilePath(downloadUrl);
        HTTPCaller.getInstance().downloadFile(downloadUrl, fileSavePath, null, new ProgressUIListener() {

            @Override
            public void onUIProgressStart(long totalBytes) {//下载开始
                progressDialog.setMax((int) totalBytes);
                progressDialog.show();
            }

            //更新进度
            @Override
            public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                progressDialog.setProgress((int) numBytes);
            }

            @Override
            public void onUIProgressFinish() {//下载完成
                Toast.makeText(VisionActivity.this, "下载完成", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                openAPK(fileSavePath);
            }
        });
    }

    /**
     * 下载完成安装apk
     *
     * @param fileSavePath
     */
    private void openAPK(String fileSavePath) {
        File file = new File(fileSavePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判断版本大于等于7.0
            // "com.ansen.checkupdate.fileprovider"即是在清单文件中配置的authorities
            // 通过FileProvider创建一个content类型的Uri
            data = FileProvider.getUriForFile(this, "com.ansen.checkupdate.fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);// 给目标应用一个临时授权
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case PERMS_REQUEST_CODE:
                boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (storageAccepted) {
                    findViewById(R.id.btn_check_update).setOnClickListener(this);
                }
                break;

        }
    }

}
