package com.bw.movie.activity.updateinfo;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.info.InfoActivity;
import com.bw.movie.bean.UpdateInfoBean;
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

public class UpdateInfoActivity extends MVPBaseActivity<UpdateInfoContract.View, UpdateInfoPresenter> implements UpdateInfoContract.View {

    @BindView(R.id.edit_nick_update)
    EditText editNickUpdate;
    @BindView(R.id.edit_sex_update)
    EditText editSexUpdate;
    @BindView(R.id.edit_email_update)
    EditText editEmailUpdate;
    @BindView(R.id.btn_update_info)
    Button btnUpdateInfo;
    private SharedPreferences sp;
    private String sessionId;
    private String userId;
    private int sex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateinfo);
        ButterKnife.bind(this);
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
        Intent intent = getIntent();
        String nickName = intent.getStringExtra("nickName");
        String sexse = intent.getStringExtra("sex");
        if (sexse.equals("1")){
            editSexUpdate.setText("男");
        }else{
            editSexUpdate.setText("女");
        }
        String email = intent.getStringExtra("email");
        editNickUpdate.setText(nickName);
        editEmailUpdate.setText(email);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
    }

    @Override
    public void updateInfoView(Object obj) {
        if (obj != null) {
            UpdateInfoBean updateInfoBean = (UpdateInfoBean) obj;
            Log.i("aa", "updateInfoBean:" + updateInfoBean.getMessage());
            if (updateInfoBean.getStatus().equals("0000")){
                startActivity(new Intent(UpdateInfoActivity.this,InfoActivity.class));
            }
        }

    }

    @OnClick(R.id.btn_update_info)
    public void onViewClicked() {
        String editNick = editNickUpdate.getText().toString().trim();
        String editSex = editSexUpdate.getText().toString().trim();
        if (editSex.equals("男")){
            sex = 1;
        }else{
            sex = 2;
        }
        String editEmail = editEmailUpdate.getText().toString().trim();
        if (TextUtils.isEmpty(editNick)&&TextUtils.isEmpty(editSex)&&TextUtils.isEmpty(editEmail)){
            Toast.makeText(UpdateInfoActivity.this,"内容不能为空",Toast.LENGTH_LONG).show();
        }else{
            Map<String,Object> headMap = new HashMap<>();
            headMap.put("userId",userId);
            headMap.put("sessionId",sessionId);
            Map<String,Object> parms = new HashMap<>();
            parms.put("nickName",editNick);
            parms.put("sex", sex);
            parms.put("email",editEmail);
            mPresenter.updateInfoPresenter(headMap,parms);
        }
        finish();
    }
}
