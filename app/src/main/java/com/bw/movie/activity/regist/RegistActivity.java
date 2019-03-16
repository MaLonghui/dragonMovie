package com.bw.movie.activity.regist;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.login.LoginActivity;
import com.bw.movie.aes.EncryptUtil;
import com.bw.movie.bean.RegistBean;
import com.bw.movie.mvp.MVPBaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegistActivity extends MVPBaseActivity<RegistContract.View, RegistPresenter> implements RegistContract.View {

    @BindView(R.id.edit_name_regist)
    EditText editNameRegist;
    @BindView(R.id.edit_sex_regist)
    EditText editSexRegist;
    @BindView(R.id.edit_date_regist)
    EditText editDateRegist;
    @BindView(R.id.edit_phone_regist)
    EditText editPhoneRegist;
    @BindView(R.id.edit_email_regist)
    EditText editEmailRegist;
    @BindView(R.id.edit_pwd_regist)
    EditText editPwdRegist;
    @BindView(R.id.btn_regist)
    Button btnRegist;
    private int sexNow;
    private TelephonyManager mTm;
    private DisplayMetrics metric;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        mTm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
//        int width = metric.widthPixels;  // 屏幕宽度（像素）
//        int height = metric.heightPixels;  // 屏幕高度（像素）
//        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5，小米4的是3.0）
//        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240,小米4的是480）
    }

    @Override
    public void registView(Object obj) {
        RegistBean registBean = (RegistBean) obj;
        if (registBean.getMessage().equals("注册成功")){
            Toast.makeText(RegistActivity.this,registBean.getMessage(),Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegistActivity.this,LoginActivity.class));
            finish();
        }else{
            Toast.makeText(RegistActivity.this,"注册失败",Toast.LENGTH_LONG).show();
        }
    }
//1971658757@qq.com
    @OnClick(R.id.btn_regist)
    public void onViewClicked() {
        SharedPreferences.Editor edit = sp.edit();
        String name = editNameRegist.getText().toString().trim();
        String sex = editSexRegist.getText().toString().trim();
        if (sex.equals("男")){
            sexNow = 1;
        }else if (sex.equals("nv")){
            sexNow = 2;
        }else {
            sexNow = 1;
        }
        String phone = editPhoneRegist.getText().toString().trim();
        String pwd = editPwdRegist.getText().toString().trim();
        String encrypt = EncryptUtil.encrypt(pwd);
        String date = editDateRegist.getText().toString().trim();
        String emain = editEmailRegist.getText().toString().trim();
        @SuppressLint("MissingPermission") String imei = mTm.getDeviceId();//得到用户Id
        @SuppressLint("MissingPermission") String imsi = mTm.getSubscriberId();
        @SuppressLint("MissingPermission") String deviceid = mTm.getDeviceId();//获取智能设备唯一编号
        String mtyb= android.os.Build.BRAND;//手机品牌
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5，小米4的是3.0）
        Map<String, Object> parms = new HashMap<>();
        parms.put("nickName", name);
        parms.put("phone", phone);
        parms.put("pwd", encrypt);
        parms.put("pwd2", encrypt);
        parms.put("sex",sexNow);
        parms.put("birthday",date);
        parms.put("email",emain);
        //移动设备唯一识别码
        parms.put("imei",deviceid);
        //设备类型
        parms.put("ua",mtyb);
        //屏幕尺寸
        parms.put("screenSize",density);
        //手机系统
        parms.put("os","android");
        edit.putString("phone",phone);
        edit.putBoolean("flag",true);
        edit.putString("pwd",encrypt);
        edit.commit();
        mPresenter.registPresenter(parms);
    }
}
