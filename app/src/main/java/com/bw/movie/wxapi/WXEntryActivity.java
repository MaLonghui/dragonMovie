package com.bw.movie.wxapi;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.ShowActivity;
import com.bw.movie.activity.filmdetails.FilmDetailsActivity;
import com.bw.movie.bean.WxLoginBean;
import com.bw.movie.mvp.MVPBaseActivity;
import com.bw.movie.utils.WeiXinUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.lang.reflect.Field;
import java.util.Map;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class WXEntryActivity extends MVPBaseActivity<WXEntryContract.View, WXEntryPresenter> implements IWXAPIEventHandler,WXEntryContract.View {

    private String code;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        WeiXinUtil.reg(WXEntryActivity.this).handleIntent(getIntent(), this);
    }

    @Override
    public void getWxViewData(Object object) {
        if (object!=null){
            WxLoginBean wxLoginBean = (WxLoginBean) object;
            if (wxLoginBean.getStatus().equals("0000")){
                //登录成功
                Toast.makeText(WXEntryActivity.this, "" + wxLoginBean.getMessage(), Toast.LENGTH_SHORT).show();
                //将登录信息存入数据库
                SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
                sp.edit().putString("userId", wxLoginBean.getResult().getUserId()).putString("sessionId", wxLoginBean.getResult().getSessionId()).commit();
                //startActivity(new Intent(WXEntryActivity.this,ShowActivity.class),ActivityOptions.makeSceneTransitionAnimation(WXEntryActivity.this).toBundle());
                finish();
            }
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WeiXinUtil.detach();
        cleanWXLeak();
    }

    /**
     * 清除微信memory leak
     */
    public static void cleanWXLeak() {
        try {
            Class clazz = com.tencent.a.a.a.a.g.class;
            Field field = clazz.getDeclaredField("V");
            field.setAccessible(true);
            Object obj = field.get(clazz);
            if (obj != null) {
                com.tencent.a.a.a.a.g g = (com.tencent.a.a.a.a.g) obj;
                Field mapField = clazz.getDeclaredField("U");
                mapField.setAccessible(true);
                Map map = (Map) mapField.get(g);
                map.clear();
            }
            field.set(clazz, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                    code = ((SendAuth.Resp) baseResp).code;
                    Log.i("sss", "onResp: "+code);
                    //Toast.makeText(this, "code"+ code, Toast.LENGTH_SHORT).show();
                    mPresenter.getWxPresenter(code);
                break;
            default:
                break;
        }
    }
}