package com.bw.movie.activity.info;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.filmdetails.FilmDetailsActivity;
import com.bw.movie.activity.updateinfo.UpdateInfoActivity;
import com.bw.movie.bean.FindInfoBean;
import com.bw.movie.bean.UpdateInfoBean;
import com.bw.movie.mvp.MVPBaseActivity;
import com.bw.movie.net.NoStudoInterent;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class InfoActivity extends MVPBaseActivity<InfoContract.View, InfoPresenter> implements InfoContract.View {

    @BindView(R.id.info_sdv)
    SimpleDraweeView infoSdv;
    @BindView(R.id.info_nick)
    TextView infoNick;
    @BindView(R.id.info_sex)
    TextView infoSex;
    @BindView(R.id.info_date)
    TextView infoDate;
    @BindView(R.id.info_phone)
    TextView infoPhone;
    @BindView(R.id.info_mail)
    TextView infoMail;
    @BindView(R.id.info_reset_psw)
    SimpleDraweeView infoResetPsw;
    @BindView(R.id.info_request)
    SimpleDraweeView infoRequest;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getWindow().setEnterTransition(new Explode().setDuration(800));
        getWindow().setExitTransition(new Explode().setDuration(800));
        ButterKnife.bind(this);
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        String userId = sp.getString("userId", "");
        String sessionId = sp.getString("sessionId", "");
        Map<String,Object> headMap = new HashMap<>();
        headMap.put("userId",userId);
        headMap.put("sessionId",sessionId);
        if (NoStudoInterent.isNetworkAvailable(InfoActivity.this)) {
            mPresenter.userInfoPresenter(headMap);
            Map<String,Object> parms = new HashMap<>();
            parms.put("nickName","杜拉拉");
            parms.put("sex","1");
            parms.put("email","1971658757@qq.com");
            mPresenter.userInfoPresenter(headMap);
        }

    }

    @Override
    public void userInfoView(Object obj) {
        if (obj!=null){
            FindInfoBean findInfoBean = (FindInfoBean) obj;
//            Log.i("aa","findInfoBean:"+findInfoBean.getMessage());
            if (findInfoBean!=null){
                Uri uri = Uri.parse(findInfoBean.getResult().getHeadPic());
                infoSdv.setImageURI(uri);
                infoNick.setText(findInfoBean.getResult().getNickName());
                String sex = findInfoBean.getResult().getSex();
                if (sex.equals("1")){
                    infoSex.setText("男");
                }else{
                    infoSex.setText("女");
                }

                Date date = new Date(findInfoBean.getResult().getBirthday());
                SimpleDateFormat sd = new SimpleDateFormat("yy-MM-hh");
                String format = sd.format(date);
                infoDate.setText(format);
                infoPhone.setText(findInfoBean.getResult().getPhone());
                infoMail.setText(findInfoBean.getResult().getEmail());
            }
        }
    }


    @OnClick({R.id.info_sdv, R.id.info_reset_psw, R.id.info_request,R.id.info_nick,R.id.info_sex,R.id.info_mail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.info_sdv:
                break;
            case R.id.info_reset_psw:
                break;
            case R.id.info_request:
                finish();
                break;
            case R.id.info_nick:
                startActivity(new Intent(InfoActivity.this,UpdateInfoActivity.class));
                finish();
                break;
            case R.id.info_mail:
                startActivity(new Intent(InfoActivity.this,UpdateInfoActivity.class));
                finish();
                break;
            case R.id.info_sex:
                startActivity(new Intent(InfoActivity.this,UpdateInfoActivity.class));
                finish();
                break;

        }
    }


}
