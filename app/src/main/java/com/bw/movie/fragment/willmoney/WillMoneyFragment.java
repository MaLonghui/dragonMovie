package com.bw.movie.fragment.willmoney;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bw.movie.R;
import com.bw.movie.adapter.MyWillMoneyAdapter;
import com.bw.movie.bean.PayBean;
import com.bw.movie.bean.PayResult;
import com.bw.movie.bean.TicketBean;
import com.bw.movie.mvp.MVPBaseFragment;
import com.bw.movie.net.NetWorkUtils;
import com.bw.movie.wxapi.WXPayEntryActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WillMoneyFragment extends MVPBaseFragment<WillMoneyContract.View, WillMoneyPresenter> implements WillMoneyContract.View {

    @BindView(R.id.recycler_view_will)
    RecyclerView recyclerViewWill;
    Unbinder unbinder;
    private SharedPreferences sp;
    private String userId;
    private String sessionId;
    private View mView;
    private ImageView popup_request;
    private CheckBox popup_wei;
    private CheckBox popup_zhi;
    private Button popup_button;
    private SpannableString mSpannableString;
    private PopupWindow mPopupWindow;
    private int payType;
    private String orderId;
    private Map<String, Object> headMap;
    private MyWillMoneyAdapter myWillMoneyAdapter;
    private static final int SDK_PAY_FLAG = 1001;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
    private RelativeLayout weiRela;
    private RelativeLayout zhifRela;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_willmoney, container, false);
       if (NetWorkUtils.isNetworkAvailable(getActivity())){
           sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
           userId = sp.getString("userId", "");
           sessionId = sp.getString("sessionId", "");
           headMap = new HashMap<>();
           headMap.put("userId", userId);
           headMap.put("sessionId", sessionId);
           Map<String, Object> parms = new HashMap<>();
           parms.put("page", 1);
           parms.put("count", 100);
           parms.put("status", 1);
           mPresenter.WillTicketPresenter(headMap, parms);
           unbinder = ButterKnife.bind(this, view);
       }
        return view;
    }

    @Override
    public void WillTicketView(Object obj) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerViewWill.setLayoutManager(linearLayoutManager);
        if (obj != null) {
            final TicketBean ticketBean = (TicketBean) obj;
            if (ticketBean != null) {
                myWillMoneyAdapter = new MyWillMoneyAdapter(getActivity(),ticketBean);
                recyclerViewWill.setAdapter(myWillMoneyAdapter);
                myWillMoneyAdapter.setWaitCallBack(new MyWillMoneyAdapter.WaitCallBack() {
                    @Override
                    public void waitcallback(double price, int position) {
                        orderId = ticketBean.getResult().get(position).getOrderId();
                        initpopup(price);
                        MobclickAgent.setSessionContinueMillis(1000*40);
                        MobclickAgent.onEvent(getActivity(), "willmoney");
                    }
                });

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
        MobclickAgent.onResume(getActivity()); //统计时长
        MobclickAgent.onPageStart("willmoney"); //统计页面("MainScreen"为页面名称，可自定义)
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
        MobclickAgent.onPause(getActivity()); //统计时长
        MobclickAgent.onPageEnd("willmoney");
    }

    @Override
    public void getPayViewData(Object object) {
        if (object!=null){
            final PayBean payBean = (PayBean) object;
          if (payBean.getStatus().equals("0000")){
              //Log.i(TAG, "支付宝信息---" + payBean.get);
              mPopupWindow.dismiss();

              if (payBean.getResult()!=null){
                  Runnable payRunnable = new Runnable() {
                      @Override
                      public void run() {
                          PayTask alipay = new PayTask(getActivity());
                          Map<String, String> result = alipay.payV2(payBean.getResult(), true);
                          Message msg = new Message();
                          msg.what = SDK_PAY_FLAG;
                          msg.obj = result;
                          mHandler.sendMessage(msg);
                      }
                  };
                  // 必须异步调用
                  Thread payThread = new Thread(payRunnable);
                  payThread.start();
              }else{
                  //带值到微信支付页
                  Intent intent = new Intent(getContext(), WXPayEntryActivity.class);
                  intent.putExtra("appId", payBean.getAppId());
                  intent.putExtra("nonceStr", payBean.getNonceStr());
                  intent.putExtra("partnerId", payBean.getPartnerId());
                  intent.putExtra("prepayId", payBean.getPrepayId());
                  intent.putExtra("sign", payBean.getSign());
                  intent.putExtra("timeStamp", payBean.getTimeStamp());
                  intent.putExtra("packageValue", payBean.getPackageValue());
                  startActivity(intent);
              }


          }
        }

    }

    private void initpopup(double price) {
        mView = View.inflate(getContext(), R.layout.seat_ok_popup, null);
        popup_request = (ImageView) mView.findViewById(R.id.popup_request);
        popup_wei = (CheckBox) mView.findViewById(R.id.popup_wei);
        popup_zhi = (CheckBox) mView.findViewById(R.id.popup_zhi);
        popup_button = (Button) mView.findViewById(R.id.popup_button);
        weiRela = (RelativeLayout) mView.findViewById(R.id.wei_rela);
        zhifRela = (RelativeLayout) mView.findViewById(R.id.zhif_rela);
        //得到价钱
        mSpannableString = changTVsize(String.valueOf(price));
        popup_button.setText("微信支付" + mSpannableString + "元");
        mPopupWindow = new PopupWindow(mView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.showAtLocation(mView, Gravity.BOTTOM, 0, 0);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        //点击事件
        popup_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        weiRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_button.setText("微信支付" + mSpannableString + "元");
                popup_wei.setChecked(true);
                popup_zhi.setChecked(false);
            }
        });
        popup_wei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_button.setText("微信支付" + mSpannableString + "元");
                popup_zhi.setChecked(false);
            }
        });
        zhifRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_button.setText("支付宝支付" + mSpannableString + "元");
                popup_wei.setChecked(false);
                popup_zhi.setChecked(true);
            }
        });
        popup_zhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_button.setText("支付宝支付" + mSpannableString + "元");
                popup_wei.setChecked(false);
            }
        });

        //下单的点击事件
        popup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> prams = new HashMap<>();
                //如果微信选中，payType==1；
                if (popup_wei.isChecked()) {
                    payType = 1;
                    //支付宝选中，payType=2;
                } else if (popup_zhi.isChecked()) {
                    payType = 2;

                }
                //网络请求
                prams.put("payType", payType + "");
                prams.put("orderId", orderId);
                mPresenter.getPayPresenter(headMap,prams);
            }
        });
    }

    /**
     * 小数点前后大小不一致
     *
     * @param value
     * @return
     */
    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.6f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
