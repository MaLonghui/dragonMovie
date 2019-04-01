package com.bw.movie.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Explode;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.show.ShowContract;
import com.bw.movie.activity.show.ShowPresenter;
import com.bw.movie.bean.CinemaByNameBean;
import com.bw.movie.fragment.cinema.CinemaFragment;
import com.bw.movie.fragment.film.FilmFragment;
import com.bw.movie.fragment.mine.MineFragment;
import com.bw.movie.net.NoStudoInterent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;


public class ShowActivity extends AppCompatActivity implements ShowContract.IView{

    @BindView(R.id.fram_layout)
    FrameLayout framLayout;
    @BindView(R.id.rb_film)
    RadioButton rbFilm;
    @BindView(R.id.rb_cinema)
    RadioButton rbCinema;
    @BindView(R.id.rb_my)
    RadioButton rbMy;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.cinema_dingwei)
    ImageView cinemaDingwei;
    @BindView(R.id.cinema_dw_addr)
    TextView cinemaDwAddr;
    @BindView(R.id.relative)
    RelativeLayout relative;
    @BindView(R.id.film_seach_ima)
    ImageView filmSeachIma;
    @BindView(R.id.film_seach_edit)
    EditText filmSeachEdit;
    @BindView(R.id.film_seach_text)
    TextView filmSeachText;
    @BindView(R.id.film_seach_relative)
    RelativeLayout filmSeachRelative;
    @BindView(R.id.show_relative)
    RelativeLayout showRelative;
    private CinemaFragment cinemaFragment;
    private FilmFragment filmFragment;
    private MineFragment mineFragment;
    private List<Fragment> list;
    private FragmentManager fragmentManager;
    private ShowPresenter showPresenter;
    private int page = 1;
    private int count = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            cinemaFragment = (CinemaFragment) fragmentManager.findFragmentByTag("cinemaFragment");
            filmFragment = (FilmFragment) fragmentManager.findFragmentByTag("filmFragment");
            mineFragment = (MineFragment) fragmentManager.findFragmentByTag("mineFragment");
        }
        super.onCreate(savedInstanceState);
        showPresenter = new ShowPresenter(this);

        getWindow().setEnterTransition(new Explode().setDuration(800));
        getWindow().setExitTransition(new Explode().setDuration(800));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        filmSeachIma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initfsi();
            }
        });
        cinemaDingwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),
//                        REQUEST_CODE_PICK_CITY);
            }
        });
        filmSeachText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = filmSeachEdit.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    initfst();
                } else {
                    HashMap<String, Object> pram = new HashMap<>();
                    pram.put("page", page);
                    pram.put("count", count);
                    pram.put("cinemaName", s);
                    showPresenter.getCinemaByNamePresenterData(pram);
                }
            }
        });


        if (NoStudoInterent.isNetworkAvailable(ShowActivity.this)) {
            //获取fragment事务
            list = new ArrayList<>();
            filmFragment = new FilmFragment();
            cinemaFragment = new CinemaFragment();
            mineFragment = new MineFragment();
            list.add(filmFragment);
            list.add(cinemaFragment);
            list.add(mineFragment);

            fragmentManager.beginTransaction()
                    .add(R.id.fram_layout, filmFragment, "filmFragment")
                    .add(R.id.fram_layout, cinemaFragment, "cinemaFragment")
                    .add(R.id.fram_layout, mineFragment, "mineFragment")
                    .hide(cinemaFragment)
                    .hide(mineFragment)
                    .commit();


            //添加动画
            ObjectAnimator.ofFloat(rbFilm, "scaleX", 1f, 1.16f).start();
            ObjectAnimator.ofFloat(rbFilm, "scaleY", 1f, 1.16f).start();
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                private ObjectAnimator myScaleY;
                private ObjectAnimator myScaleX;
                private ObjectAnimator cinemaScaleY;
                private ObjectAnimator cinemaScaleX;
                private ObjectAnimator filmScaleY;
                private ObjectAnimator filmScaleX;

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        //影片
                        case R.id.rb_film:
                            //开启事务
                            fragmentManager.beginTransaction()
                                    .show(filmFragment)
                                    .hide(cinemaFragment)
                                    .hide(mineFragment)
                                    .commit();

                            cinemaDingwei.setImageResource(R.mipmap.com_icon_loc_white_default);
                            cinemaDwAddr.setTextColor(Color.parseColor("#ffffff"));
                            showRelative.setVisibility(View.VISIBLE);

                            //选中设置选中图片
                            rbFilm.setBackgroundResource(R.mipmap.com_icon_film_selected);
                            //未选中图片
                            rbCinema.setBackgroundResource(R.mipmap.com_icon_cinema_default);
                            rbMy.setBackgroundResource(R.mipmap.com_icon_my_default);
                            //选中放大
                            filmScaleX = ObjectAnimator.ofFloat(rbFilm, "scaleX", 1f, 1.16f);
                            filmScaleY = ObjectAnimator.ofFloat(rbFilm, "scaleY", 1f, 1.16f);
                            filmScaleX.start();
                            filmScaleY.start();
                            //第二个缩小
                            cinemaScaleX = ObjectAnimator.ofFloat(rbCinema, "scaleX", 1f, 1f);
                            cinemaScaleY = ObjectAnimator.ofFloat(rbCinema, "scaleY", 1f, 1f);
                            cinemaScaleX.start();
                            cinemaScaleY.start();
                            //第三个缩小
                            myScaleX = ObjectAnimator.ofFloat(rbMy, "scaleX", 1f, 1f);
                            myScaleY = ObjectAnimator.ofFloat(rbMy, "scaleY", 1f, 1f);
                            myScaleX.start();
                            myScaleY.start();

                            break;
                        //影院
                        case R.id.rb_cinema:
                            fragmentManager.beginTransaction()
                                    .hide(filmFragment)
                                    .show(cinemaFragment)
                                    .hide(mineFragment)
                                    .commit();
                            cinemaDingwei.setImageResource(R.mipmap.cinemadetail_icon_location_default);
                            cinemaDwAddr.setTextColor(Color.parseColor("#333333"));
                            showRelative.setVisibility(View.VISIBLE);

                            rbFilm.setBackgroundResource(R.mipmap.com_icon_film_fault);
                            rbCinema.setBackgroundResource(R.mipmap.com_icon_cinema_selected);
                            rbMy.setBackgroundResource(R.mipmap.com_icon_my_default);
                            //第一个缩小
                            filmScaleX = ObjectAnimator.ofFloat(rbFilm, "scaleX", 1f, 1f);
                            filmScaleY = ObjectAnimator.ofFloat(rbFilm, "scaleY", 1f, 1f);
                            filmScaleX.start();
                            filmScaleY.start();
                            //选中放大
                            cinemaScaleX = ObjectAnimator.ofFloat(rbCinema, "scaleX", 1f, 1.16f);
                            cinemaScaleY = ObjectAnimator.ofFloat(rbCinema, "scaleY", 1f, 1.16f);
                            cinemaScaleX.start();
                            cinemaScaleY.start();
                            //第三个缩小
                            myScaleX = ObjectAnimator.ofFloat(rbMy, "scaleX", 1f, 1f);
                            myScaleY = ObjectAnimator.ofFloat(rbMy, "scaleY", 1f, 1f);
                            myScaleX.start();
                            myScaleY.start();
                            break;
                        //我的
                        case R.id.rb_my:
                            fragmentManager.beginTransaction()
                                    .hide(filmFragment)
                                    .hide(cinemaFragment)
                                    .show(mineFragment)
                                    .commit();

                            showRelative.setVisibility(View.GONE);
                            rbFilm.setBackgroundResource(R.mipmap.com_icon_film_fault);
                            rbCinema.setBackgroundResource(R.mipmap.com_icon_cinema_default);
                            rbMy.setBackgroundResource(R.mipmap.com_icon_my_selected);
                            //第一个缩小
                            filmScaleX = ObjectAnimator.ofFloat(rbFilm, "scaleX", 1f, 1f);
                            filmScaleY = ObjectAnimator.ofFloat(rbFilm, "scaleY", 1f, 1f);
                            filmScaleX.start();
                            filmScaleY.start();
                            //第二个缩小
                            cinemaScaleX = ObjectAnimator.ofFloat(rbCinema, "scaleX", 1f, 1f);
                            cinemaScaleY = ObjectAnimator.ofFloat(rbCinema, "scaleY", 1f, 1f);
                            cinemaScaleX.start();
                            cinemaScaleY.start();
                            //选中放大
                            myScaleX = ObjectAnimator.ofFloat(rbMy, "scaleX", 1f, 1.16f);
                            myScaleY = ObjectAnimator.ofFloat(rbMy, "scaleY", 1f, 1.16f);
                            myScaleX.start();
                            myScaleY.start();
                            break;
                    }


                }
            });


        }
        stateNetWork();
    }

    //动态注册权限
    private void stateNetWork() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            String[] mStatenetwork = new String[]{
                    //写的权限
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    //读的权限
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    //入网权限
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    //WIFI权限
                    Manifest.permission.ACCESS_WIFI_STATE,
                    //读手机权限
                    Manifest.permission.READ_PHONE_STATE,
                    //网络权限
                    Manifest.permission.INTERNET,
                    //相机
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_APN_SETTINGS,
                    Manifest.permission.ACCESS_NETWORK_STATE,
            };
            ActivityCompat.requestPermissions(this,mStatenetwork,100);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    @Override
    public void getVIewData(Object object) {
        if (object != null) {
            CinemaByNameBean cinemaByNameBean = (CinemaByNameBean) object;
            List<CinemaByNameBean.ResultBean> nameBeanResult = cinemaByNameBean.getResult();
            if (nameBeanResult != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("nameBeanResult", (Serializable) nameBeanResult);
                Intent intent = new Intent(this, CinemaByNameActivity.class);
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
                Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
            }

        }
    }


    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //点击图标拉伸搜索框
    boolean mBoolean = true;

    private void initfsi() {
        if (mBoolean) {
            ObjectAnimator translationX = ObjectAnimator.ofFloat(filmSeachRelative, "translationX", 0, (dp2px(this, -170)));
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
        ObjectAnimator translationX = ObjectAnimator.ofFloat(filmSeachRelative, "translationX", (dp2px(this, -170)), 0);
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
    protected void onDestroy() {
        super.onDestroy();
        showPresenter.onDetach();
    }
}
