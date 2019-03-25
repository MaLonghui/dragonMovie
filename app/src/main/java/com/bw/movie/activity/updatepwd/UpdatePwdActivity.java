package com.bw.movie.activity.updatepwd;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.aes.EncryptUtil;
import com.bw.movie.bean.UpdatePwdBean;
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

public class UpdatePwdActivity extends MVPBaseActivity<UpdatePwdContract.View, UpdatePwdPresenter> implements UpdatePwdContract.View {

    @BindView(R.id.pwd_oldpwd)
    EditText pwdOldpwd;
    @BindView(R.id.pwd_newpwd)
    EditText pwdNewpwd;
    @BindView(R.id.pwd_aginpwd)
    EditText pwdAginpwd;
    @BindView(R.id.pwd_but)
    Button pwdBut;
    private SharedPreferences sp;
    private String userId;
    private String sessionId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepwd);
        ButterKnife.bind(this);
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
    }

    @Override
    public void UpdatePwdView(Object obj) {
        if (obj != null) {
            UpdatePwdBean updatePwdBean = (UpdatePwdBean) obj;
            Toast.makeText(UpdatePwdActivity.this,updatePwdBean.getMessage()+",下次登录使用新密码",Toast.LENGTH_LONG).show();
            if (updatePwdBean.getStatus().equals("0000")){
                finish();
            }
        }
    }

    @OnClick(R.id.pwd_but)
    public void onViewClicked() {
        String pwdOld = pwdOldpwd.getText().toString();
        String pwdNewPwd = pwdNewpwd.getText().toString();
        String pwdAginPwd = pwdAginpwd.getText().toString();
        String pwdOldencrypt = EncryptUtil.encrypt(pwdOld);
        String pwdNewPwdencrypt = EncryptUtil.encrypt(pwdNewPwd);
        String pwdAginPwdencrypt = EncryptUtil.encrypt(pwdAginPwd);
        if (TextUtils.isEmpty(pwdOld)&&TextUtils.isEmpty(pwdNewPwd)&&TextUtils.isEmpty(pwdAginPwd)){
            Toast.makeText(UpdatePwdActivity.this,"内容不能为空",Toast.LENGTH_LONG).show();
        }else{
            Map<String,Object> headMap = new HashMap<>();
            headMap.put("userId",userId);
            headMap.put("sessionId",sessionId);
            Map<String,Object> parms = new HashMap<>();
            parms.put("oldPwd",pwdOldencrypt);
            parms.put("newPwd",pwdNewPwdencrypt);
            parms.put("newPwd2",pwdAginPwdencrypt);
            mPresenter.UpdatePwdPresenter(headMap,parms);
        }

    }
}
