package com.bw.movie.fragment.cinema;


import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.CinemaByNameActivity;
import com.bw.movie.adapter.MyNearbyAdapter;
import com.bw.movie.adapter.MyRecommendAdapter;
import com.bw.movie.bean.CancelAttentionBean;
import com.bw.movie.bean.CinemaAttentionBean;
import com.bw.movie.bean.CinemaByNameBean;
import com.bw.movie.bean.NearbyCinemasBean;
import com.bw.movie.bean.RecommendCinemasBean;
import com.bw.movie.mvp.MVPBaseFragment;
import com.bw.movie.net.NoStudoInterent;
import com.bw.movie.utils.AlertDialogUtils;
import com.zaaach.citypicker.CityPickerActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;
import static android.view.View.VISIBLE;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CinemaFragment extends MVPBaseFragment<CinemaContract.View, CinemaPresenter> implements CinemaContract.View {

    @BindView(R.id.btn_Recommend)
    Button btnRecommend;
    @BindView(R.id.btn_Nearby)
    Button btnNearby;
    @BindView(R.id.xrecycler_view)
    RecyclerView xrecyclerView;
    Unbinder unbinder;
    @BindView(R.id.cinema_dingwei)
    ImageView cinemaDingwei;
    @BindView(R.id.cinema_dw_addr)
    TextView cinemaDwAddr;
    @BindView(R.id.relative)
    RelativeLayout relative;
    @BindView(R.id.film_seach_ima)
    ImageView filmSeachIma;
    @BindView(R.id.film_seach_relative)
    RelativeLayout filmSeachRelative;
    @BindView(R.id.film_seach_edit)
    EditText filmSeachEdit;
    @BindView(R.id.film_seach_text)
    TextView filmSeachText;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;

    private String userId;
    private String sessionId;
    private int page = 1;
    private int count = 20;
    private String longitude = "116.30551391385724";
    private String latitude = "40.04571807462411";
    private List<Object> list = new ArrayList<>();
    private RecommendCinemasBean recommendCinemasBean;
    private MyRecommendAdapter myRecommendAdapter;
    private List<RecommendCinemasBean.ResultBean> result;
    private boolean flag = false;
    private SharedPreferences sp;
    private CinemaAttentionBean cinemaAttentionBean;
    private Map<String, Object> headMap;
    private Map<String, Object> parms;
    private Map<String, Object> parms1;
    private String s;
    private static final int REQUEST_CODE_PICK_CITY = 0;

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //解决键盘顶起导航栏
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = inflater.inflate(R.layout.fragment_cinema_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");

        //定位
        cinemaDingwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);
            }
        });


        if (NoStudoInterent.isNetworkAvailable(getActivity())) {
            if (!userId.equals("") && !sessionId.equals("")) {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put("userId", userId);
                headMap.put("sessionId", sessionId);
                Map<String, Object> parms = new HashMap<>();
                parms.put("page", page);
                parms.put("count", count);
                mPresenter.recommendPresenter(headMap, parms);
            } else {
                Map<String, Object> headMap = new HashMap<>();
                Map<String, Object> parms = new HashMap<>();
                parms.put("page", page);
                parms.put("count", count);
                mPresenter.recommendPresenter(headMap, parms);
            }

        }
        btnRecommend.setBackgroundResource(R.drawable.top_btn_shape);
        btnRecommend.setTextColor(Color.WHITE);
        if (!userId.equals("") && !sessionId.equals("")) {
            headMap = new HashMap<>();
            headMap.put("userId", userId);
            headMap.put("sessionId", sessionId);
            parms = new HashMap<>();
            parms.put("page", page);
            parms.put("count", count);
            parms1 = new HashMap<>();
            parms1.put("page", page);
            parms1.put("count", count);
            parms1.put("longitude", longitude);
            parms1.put("latitude", latitude);
            mPresenter.recommendPresenter(headMap, parms);
            mPresenter.nearbyPresenter(headMap, parms1);
        }

        btnRecommend.setBackgroundResource(R.drawable.top_btn_shape);
        btnRecommend.setTextColor(Color.WHITE);
        filmSeachIma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initfsi();
            }
        });

        filmSeachText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = filmSeachEdit.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    initfst();
                } else {
                    HashMap<String, Object> pram = new HashMap<>();
                    pram.put("page", page);
                    pram.put("count", count);
                    pram.put("cinemaName", s);
                    mPresenter.getCinemaByNamePresenterData(pram);

                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
        if (!userId.equals("")&&!sessionId.equals("")){
            Map<String,Object> headMap = new HashMap<>();
            headMap.put("userId", userId);
            headMap.put("sessionId", sessionId);
            Map<String,Object> parms = new HashMap<>();
            parms.put("page",page);
            parms.put("count",count);
            mPresenter.recommendPresenter(headMap, parms);
        }else{
            Map<String,Object> headMap = new HashMap<>();
            Map<String,Object> parms = new HashMap<>();
            parms.put("page",page);
            parms.put("count",count);
            mPresenter.recommendPresenter(headMap, parms);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //点击图标拉伸搜索框
    boolean mBoolean = true;

    private void initfsi() {
        if (mBoolean) {
            ObjectAnimator translationX = ObjectAnimator.ofFloat(filmSeachRelative, "translationX", 0, (dp2px(getActivity(), -170)));
            ObjectAnimator alpha = ObjectAnimator.ofFloat(filmSeachEdit, "alpha", 0.0f, 1.0f);
            ObjectAnimator alphaButton = ObjectAnimator.ofFloat(filmSeachText, "alpha", 0.0f, 1.0f);
            alphaButton.setDuration(1000);
            filmSeachText.setVisibility(VISIBLE);
            alphaButton.start();
            alpha.setDuration(1000);
            filmSeachEdit.setVisibility(VISIBLE);
            alpha.start();
            //动画时间
            translationX.setDuration(1000);
            translationX.start();
            mBoolean = !mBoolean;
        }
    }

    //收缩搜索框
    private void initfst() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(filmSeachRelative, "translationX", (dp2px(getActivity(), -170)), 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(filmSeachEdit, "alpha", 1.0f, 0.5f, 0.0f);
        ObjectAnimator alphaButton = ObjectAnimator.ofFloat(filmSeachText, "alpha", 1.0f, 0.5f, 0.0f);
        alphaButton.setDuration(1000);
        alphaButton.start();
        alpha.setDuration(1000);
        alpha.start();
        translationX.setDuration(1000);
        translationX.start();
        mBoolean = !mBoolean;
    }


    @Override
    public void recommendView(Object obj) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xrecyclerView.setLayoutManager(linearLayoutManager);
        if (obj != null) {
            recommendCinemasBean = (RecommendCinemasBean) obj;
            result = recommendCinemasBean.getResult();
//            Log.i("aa","recommendCinemasBean:"+recommendCinemasBean.getMessage());
            myRecommendAdapter = new MyRecommendAdapter(getActivity());
            xrecyclerView.setAdapter(myRecommendAdapter);
            myRecommendAdapter.setList(recommendCinemasBean);
            myRecommendAdapter.setAttentionClick(new MyRecommendAdapter.AttentionClick() {
                @Override
                public void clickattention(String cinemaId, boolean b) {
                    if (b) {
                        if (!userId.equals("") && !sessionId.equals("")) {
                            Map<String, Object> headMap = new HashMap<>();
                            headMap.put("userId", userId);
                            headMap.put("sessionId", sessionId);
                            mPresenter.AttentionPresenter(headMap, cinemaId);
                            myRecommendAdapter.notifyDataSetChanged();
                        } else {
                            AlertDialogUtils.AlertDialogLogin(getActivity());
                        }
                        myRecommendAdapter.notifyDataSetChanged();
                    }else{
                        if (!userId.equals("")&&!sessionId.equals("")){
                            Map<String,Object> headMap = new HashMap<>();
                            headMap.put("userId",userId);
                            headMap.put("sessionId",sessionId);
                            mPresenter.CancelAttentionPresenter(headMap,cinemaId);
                            myRecommendAdapter.notifyDataSetChanged();
                        }else{
                            AlertDialogUtils.AlertDialogLogin(getActivity());
                        }
                    }
                }
            });
        }
    }

    @Override
    public void nearbyView(Object obj) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xrecyclerView.setLayoutManager(linearLayoutManager);
        if (obj != null) {
            NearbyCinemasBean nearbyCinemasBean = (NearbyCinemasBean) obj;
//            Log.i("aa","nearbyCinemasBean:"+nearbyCinemasBean.getMessage());
            if (nearbyCinemasBean != null) {
                final MyNearbyAdapter myNearbyAdapter = new MyNearbyAdapter(getActivity(), nearbyCinemasBean);
                xrecyclerView.setAdapter(myNearbyAdapter);
                myNearbyAdapter.setAttentionClick(new MyNearbyAdapter.AttentionClick() {
                    @Override
                    public void clickattention(String cinemaId,boolean b) {
                        if (b){
                            if (!userId.equals("")&&!sessionId.equals("")){
                                Map<String,Object> headMap = new HashMap<>();
                                headMap.put("userId",userId);
                                headMap.put("sessionId",sessionId);
                                mPresenter.AttentionPresenter(headMap,cinemaId);
                                myNearbyAdapter.notifyDataSetChanged();
                            }else{
                                AlertDialogUtils.AlertDialogLogin(getActivity());
                            }
                            myNearbyAdapter.notifyDataSetChanged();
                        }else{
                            if (!userId.equals("")&&!sessionId.equals("")){
                                Map<String,Object> headMap = new HashMap<>();
                                headMap.put("userId",userId);
                                headMap.put("sessionId",sessionId);
                                mPresenter.CancelAttentionPresenter(headMap,cinemaId);
                                myNearbyAdapter.notifyDataSetChanged();
                            }else{
                                AlertDialogUtils.AlertDialogLogin(getActivity());
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public void AttentionView(Object obj) {
        if (obj != null) {
            cinemaAttentionBean = (CinemaAttentionBean) obj;
            Log.i("aa", "cinemaAttentionBean:" + cinemaAttentionBean.getMessage());
            if (cinemaAttentionBean.getStatus().equals("0000")) {
                Toast.makeText(getActivity(), cinemaAttentionBean.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("aa", "cinemaAttentionBean:" + cinemaAttentionBean.getMessage());
                if (cinemaAttentionBean.getStatus().equals("0000")) {
//                Map<String,Object> headMap = new HashMap<>();
//                headMap.put("userId", userId);
//                headMap.put("sessionId", sessionId);
//                Map<String,Object> parms = new HashMap<>();
//                parms.put("page",page);
//                parms.put("count",count);
//                mPresenter.recommendPresenter(headMap, parms);
                Log.i("aa", "cinemaAttentionBean:" + cinemaAttentionBean.getMessage());
                if (cinemaAttentionBean.getStatus().equals("0000")) {
                    Toast.makeText(getActivity(), cinemaAttentionBean.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }


        }
    }

    @Override
    public void CancelAttentionView(Object obj) {
        CancelAttentionBean cancelAttentionBean = (CancelAttentionBean) obj;
        if (cancelAttentionBean.getStatus().equals("0000")) {
            Toast.makeText(getActivity(), cancelAttentionBean.getMessage(), Toast.LENGTH_LONG).show();

        }
    }
    @Override
        public void getCinemaByNameViewData(Object object) {
        if (object != null) {
            CinemaByNameBean cinemaByNameBean = (CinemaByNameBean) object;
            List<CinemaByNameBean.ResultBean> nameBeanResult = cinemaByNameBean.getResult();
            if (nameBeanResult != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("nameBeanResult", (Serializable) nameBeanResult);
                Intent intent = new Intent(getActivity(), CinemaByNameActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                filmSeachEdit.setText("");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initfst();
                    }
                }, 1000);

            } else {
                Toast.makeText(getActivity(), "暂无数据", Toast.LENGTH_SHORT).show();
            }

        }
    }
   
    @OnClick({R.id.btn_Recommend, R.id.btn_Nearby})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_Recommend:
                if (!userId.equals("") && !sessionId.equals("")) {
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put("userId", userId);
                    headMap.put("sessionId", sessionId);
                    Map<String, Object> parms = new HashMap<>();
                    parms.put("page", page);
                    parms.put("count", count);
                    mPresenter.recommendPresenter(headMap, parms);
                } else {
                    Map<String, Object> headMap = new HashMap<>();
                    Map<String, Object> parms = new HashMap<>();
                    parms.put("page", page);
                    parms.put("count", count);
                    mPresenter.recommendPresenter(headMap, parms);
                }
                btnRecommend.setBackgroundResource(R.drawable.button_ripple);
                btnRecommend.setTextColor(Color.WHITE);
                btnNearby.setTextColor(Color.BLACK);
                btnNearby.setBackgroundResource(R.color.colorWhite);
                break;
            case R.id.btn_Nearby:
                if (!userId.equals("") && !sessionId.equals("")) {
                    Map<String, Object> headMap = new HashMap<>();
                    headMap.put("userId", userId);
                    headMap.put("sessionId", sessionId);
                    Map<String, Object> parms1 = new HashMap<>();
                    parms1.put("page", page);
                    parms1.put("count", count);
                    parms1.put("longitude", longitude);
                    parms1.put("latitude", latitude);
                    mPresenter.nearbyPresenter(headMap, parms1);
                } else {
                    Map<String, Object> headMap = new HashMap<>();
                    Map<String, Object> parms1 = new HashMap<>();
                    parms1.put("page", page);
                    parms1.put("count", count);
                    parms1.put("longitude", longitude);
                    parms1.put("latitude", latitude);
                    mPresenter.nearbyPresenter(headMap, parms1);
                }
                btnNearby.setBackgroundResource(R.drawable.button_ripple);
                btnRecommend.setBackgroundResource(R.color.colorWhite);
                btnNearby.setTextColor(Color.WHITE);
                btnRecommend.setTextColor(Color.BLACK);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
            if (data != null){
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                cinemaDwAddr.setText(city);
            }
        }

    }
}