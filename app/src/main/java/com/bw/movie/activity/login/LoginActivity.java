package com.bw.movie.activity.login;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.ShowActivity;
import com.bw.movie.activity.regist.RegistActivity;
import com.bw.movie.aes.EncryptUtil;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.check_login)
    CheckBox checkLogin;
    @BindView(R.id.jump_regist)
    TextView jumpRegist;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.login_wx)
    ImageView loginWx;
    @BindView(R.id.dsf)
    TextView dsf;
    @BindView(R.id.layout_login)
    LinearLayout layoutLogin;
    private String phone;
    private String encrypt;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean flag = sp.getBoolean("flag",false);
        Log.i("aa","flag"+flag);
        checkLogin.setChecked(flag);
        if (flag){
            String phone = sp.getString("phone", "");
            String pwd = sp.getString("pwd", "");
            editPhone.setText(phone);
            String decrypt = EncryptUtil.decrypt(pwd);
            editPwd.setText(decrypt);
        }
//        String phone = sp.getString("phone", "");
//        String pwd = sp.getString("pwd", "");
//        String decrypt = EncryptUtil.decrypt(pwd);
//        editPhone.setText(phone);
//        editPwd.setText(decrypt);
    }

    @OnClick({R.id.jump_regist, R.id.btn_login, R.id.login_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jump_regist:
                startActivity(new Intent(LoginActivity.this,RegistActivity.class));
                break;
            case R.id.btn_login:
                phone = editPhone.getText().toString().trim();
                String pwd = editPwd.getText().toString().trim();
                encrypt = EncryptUtil.encrypt(pwd);
                mPresenter.loginPresenter(phone, encrypt);
                startActivity(new Intent(this,ShowActivity.class));
                break;
            case R.id.login_wx:
                break;
        }
    }

    @Override
    public void loginView(Object object) {
        LoginBean loginBean = (LoginBean) object;
        if (loginBean.getStatus().equals("0000")){

            SharedPreferences.Editor edit = sp.edit();
            Toast.makeText(LoginActivity.this,loginBean.getMessage(),Toast.LENGTH_LONG).show();
            if (checkLogin.isChecked()){
                edit.putBoolean("flag",true);
                edit.putString("phone",phone);
                edit.putString("pwd",encrypt);
            }else{
                edit.putBoolean("flag",false);
            }
            edit.commit();
        }else{
            Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_LONG).show();
        }
    }
}
