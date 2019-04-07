package com.bw.movie.activity.seat;


import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.filmdetails.FilmDetailsActivity;
import com.bw.movie.activity.login.LoginActivity;
import com.bw.movie.activity.reccord.ReccordActivity;
import com.bw.movie.bean.BuyTicketBean;
import com.bw.movie.bean.MoveSeatAmount;
import com.bw.movie.mvp.MVPBaseActivity;
import com.bw.movie.net.NetWorkUtils;
import com.bw.movie.utils.AlertDialogUtils;
import com.bw.movie.utils.MD5Utils;
import com.bw.movie.bean.MoveSeatAmount;
import com.bw.movie.mvp.MVPBaseActivity;
import com.bw.movie.utils.AlertDialogUtils;
import com.bw.movie.utils.MD5Utils;
import com.bw.movie.activity.filmdetails.FilmDetailsActivity;
import com.bw.movie.activity.reccord.ReccordActivity;
import com.bw.movie.bean.BuyTicketBean;
import com.bw.movie.bean.MoveSeatAmount;
import com.bw.movie.mvp.MVPBaseActivity;
import com.bw.movie.utils.AlertDialogUtils;
import com.bw.movie.bean.BuyTicketBean;
import com.bw.movie.bean.MoveSeatAmount;
import com.bw.movie.mvp.MVPBaseActivity;
import com.bw.movie.utils.AlertDialogUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SeatActivity extends MVPBaseActivity<SeatContract.View, SeatPresenter> implements SeatContract.View {
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.seat_begingtime)
    TextView seatBegingtime;
    @BindView(R.id.seat_endtime)
    TextView seatEndtime;
    @BindView(R.id.seat_hall)
    TextView seatHall;
    @BindView(R.id.linear1)
    LinearLayout linear1;
    @BindView(R.id.linear2)
    LinearLayout linear2;
    @BindView(R.id.seat_moveseat_view)
    MoveSeatView seatMoveseatView;
    @BindView(R.id.seat_price)
    TextView seatPrice;
    @BindView(R.id.seat_ok)
    ImageView seatOk;
    @BindView(R.id.seat_no)
    ImageView seatNo;
    private Handler handler = new Handler();
    private int mNum;
    private SpannableString mSpannableString;
    private double mMPrice;
    private ImageView popup_request;
    private CheckBox popup_wei;
    private CheckBox popup_zhi;
    private Button popup_button;
    private PopupWindow mPopupWindow;
    private SharedPreferences preferences;
    private String userId;
    private String sessionId;
    private String scheduleId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        ButterKnife.bind(this);
        if (NetWorkUtils.isNetworkAvailable(SeatActivity.this)){
            preferences = getSharedPreferences("config", MODE_PRIVATE);
            userId = preferences.getString("userId", "");
            sessionId = preferences.getString("sessionId", "");

            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
            Intent intent = getIntent();
            mMPrice = intent.getDoubleExtra("price", 0);
            //Toast.makeText(this, mMPrice +"", Toast.LENGTH_SHORT).show();
            String beginTime = intent.getStringExtra("beginTime");
            String endTime = intent.getStringExtra("endTime");
            scheduleId = intent.getStringExtra("scheduleId");
            String screeningHall = intent.getStringExtra("screeningHall");
            seatBegingtime.setText(beginTime + " - ");
            seatEndtime.setText(endTime);
            seatHall.setText(screeningHall);
            double v = mMPrice * 0;
            mSpannableString = changTVsize(v + "");
            seatPrice.setText(mSpannableString);
            seatMoveseatView.setData(10, 15);
            seatOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (userId.equals("") || sessionId.equals("")) {
                        AlertDialogUtils.AlertDialogLogin(SeatActivity.this);
                    } else {
                        Map<String, Object> headMap = new HashMap<>();
                        Map<String, Object> prams = new HashMap<>();
                        headMap.put("userId", userId);
                        headMap.put("sessionId", sessionId);
                        prams.put("scheduleId", scheduleId);
                        prams.put("amount", mNum + "");
                        String signMD5 = userId + scheduleId + mNum + "movie";
                        String sign = MD5Utils.string2MD5(signMD5);
                        prams.put("sign", sign);
                        mPresenter.getTicketPresenterData(headMap, prams);
                    }
                    MobclickAgent.setSessionContinueMillis(1000 * 40);
                    MobclickAgent.onEvent(SeatActivity.this, "seat_ok");
                }
            });
            seatNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                    finish();
                }
            });


            seatNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                    finish();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetWorkUtils.isNetworkAvailable(SeatActivity.this)) {
            userId = preferences.getString("userId", "");
            sessionId = preferences.getString("sessionId", "");
            MobclickAgent.onResume(this);
        }
    }

    private void initpopup() {
        if (NetWorkUtils.isNetworkAvailable(SeatActivity.this)) {
            View view = View.inflate(this, R.layout.seat_ok_popup, null);
            popup_request = (ImageView) view.findViewById(R.id.popup_request);
            popup_wei = (CheckBox) view.findViewById(R.id.popup_wei);
            popup_zhi = (CheckBox) view.findViewById(R.id.popup_zhi);
            popup_button = (Button) view.findViewById(R.id.popup_button);
            popup_button.setText("微信支付" + mSpannableString + "元");
            mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setTouchable(true);
            //点击事件
            popup_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });

            popup_wei.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup_button.setText("微信支付" + mSpannableString + "元");
                    popup_zhi.setChecked(false);
                }
            });
            popup_zhi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup_button.setText("支付宝支付" + mSpannableString + "元");
                    popup_wei.setChecked(false);
                }
            });
        }
    }

    @Subscribe(sticky = true)
    public void onMoveSeatAmount(MoveSeatAmount moveSeatBean) {
        if (NetWorkUtils.isNetworkAvailable(SeatActivity.this)) {
            mNum = moveSeatBean.getNum();

            if (moveSeatBean.getNum() == 0) {
                double v = mMPrice * 0;
                mSpannableString = changTVsize(v + "");
                seatPrice.setText(mSpannableString);
            } else {
                if (mNum > 3) {
                    mNum = 3;
                }
                double v = mMPrice * mNum;
                //保证总价为double
                BigDecimal bg = new BigDecimal(v);
                double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                mSpannableString = changTVsize(f1 + "");
                //TODO：初始价格-->未选座
                seatPrice.setText(mSpannableString);
            }
        }
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
    public void getTicketViewData(Object object) {
        if (NetWorkUtils.isNetworkAvailable(SeatActivity.this)) {
            if (object != null) {
                BuyTicketBean buyTicketBean = (BuyTicketBean) object;
                Toast.makeText(this, buyTicketBean.getMessage(), Toast.LENGTH_SHORT).show();
                if (buyTicketBean.getStatus().equals("0000")) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SeatActivity.this, ReccordActivity.class), ActivityOptions.makeSceneTransitionAnimation(SeatActivity.this).toBundle());
                            finish();
                        }
                    }, 1000);
                }
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}