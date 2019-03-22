package com.bw.movie.activity.login;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.transition.Explode;
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
    @BindView(R.id.check_autoLogin)
    CheckBox checkAutoLogin;
    @BindView(R.id.btn_eyePwd)
    ImageView btnEyePwd;
    private String phone;
    private String encrypt;
    private SharedPreferences sp;
    private LoginBean loginBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getWindow().setEnterTransition(new Explode().setDuration(800));
        getWindow().setExitTransition(new Explode().setDuration(800));
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean flag = sp.getBoolean("flag", false);
        Log.i("aa", "flag" + flag);
        if (flag) {
            String phone = sp.getString("phone", "");
            String pwd = sp.getString("pwd", "");
            editPhone.setText(phone);
            checkLogin.setChecked(flag);
            String decrypt = EncryptUtil.decrypt(pwd);
            editPwd.setText(decrypt);
        }
//        String phone = sp.getString("phone", "");
//        String pwd = sp.getString("pwd", "");
//        String decrypt = EncryptUtil.decrypt(pwd);
//        editPhone.setText(phone);
//        editPwd.setText(decrypt);
    }

    @OnClick({R.id.jump_regist, R.id.btn_login, R.id.login_wx,R.id.btn_eyePwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jump_regist:
                startActivity(new Intent(LoginActivity.this, RegistActivity.class),ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.btn_login:
                phone = editPhone.getText().toString().trim();
                String pwd = editPwd.getText().toString().trim();
                encrypt = EncryptUtil.encrypt(pwd);
                mPresenter.loginPresenter(phone, encrypt);
                break;
            case R.id.btn_eyePwd:
                if (editPwd.getInputType()==InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                    //密码可见,点击之后设置成不可见的
                    editPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else{
                    //不可见设置成可见
                    editPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
            case R.id.login_wx:
                break;
        }
    }

    @Override
    public void loginView(Object object) {
        loginBean = (LoginBean) object;
        if (loginBean.getStatus().equals("0000")) {
            String userId = loginBean.getResult().getUserId();
            String sessionId = loginBean.getResult().getSessionId();
            SharedPreferences.Editor edit = sp.edit();
            Toast.makeText(LoginActivity.this, loginBean.getMessage(), Toast.LENGTH_LONG).show();
            edit.putBoolean("flag", checkLogin.isChecked());
            edit.putBoolean("自动登录", checkAutoLogin.isChecked());
            edit.putString("phone", phone);
            edit.putString("pwd", encrypt);
            edit.putString("userId", userId);
            edit.putString("sessionId", sessionId);
            edit.commit();
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
        }
    }
}
