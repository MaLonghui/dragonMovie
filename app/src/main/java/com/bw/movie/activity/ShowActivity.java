package com.bw.movie.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Explode;
import android.view.KeyEvent;
import android.util.Log;
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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bw.movie.R;
import com.bw.movie.activity.show.ShowContract;
import com.bw.movie.activity.show.ShowPresenter;
import com.bw.movie.bean.CinemaByNameBean;
import com.bw.movie.fragment.cinema.CinemaFragment;
import com.bw.movie.fragment.film.FilmFragment;
import com.bw.movie.fragment.mine.MineFragment;
import com.umeng.analytics.MobclickAgent;
import com.bw.movie.utils.ImageUtil;
import com.zaaach.citypicker.CityPickerActivity;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;


public class ShowActivity extends AppCompatActivity implements ShowContract.IView {

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
    private int REQUEST_CODE_PICK_CITY = 0;
    public static final String TAG = "ShowActivity";
    private SharedPreferences mConfig;
    private String mString, mStrings;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private SharedPreferences sp;
    private String userId;
    private String sessionId;

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            cinemaFragment = (CinemaFragment) fragmentManager.findFragmentByTag("cinemaFragment");
            filmFragment = (FilmFragment) fragmentManager.findFragmentByTag("filmFragment");
            mineFragment = (MineFragment) fragmentManager.findFragmentByTag("mineFragment");
        }
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
        getWindow().setEnterTransition(new Explode().setDuration(800));
        getWindow().setExitTransition(new Explode().setDuration(800));

        showPresenter = new ShowPresenter(this);

        mConfig = getSharedPreferences("configs", Context.MODE_PRIVATE);
        mString = mConfig.getString("city", "");
        cinemaDwAddr.setText(mString);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        } else {
            startLocaion();//进入页面开始定位
            // Toast.makeText(this, "已开启定位权限", Toast.LENGTH_LONG).show();
        }


        filmSeachIma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initfsi();
            }
        });
        cinemaDingwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(ShowActivity.this, CityPickerActivity.class), REQUEST_CODE_PICK_CITY);

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

                            MobclickAgent.onEvent(ShowActivity.this, "film");
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
                            MobclickAgent.onEvent(ShowActivity.this, "cinema");
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
                            MobclickAgent.onEvent(ShowActivity.this, "mine");
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


        stateNetWork();
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < 2000) {
                System.exit(0);
            } else {
                Toast.makeText(ShowActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                cinemaDwAddr.setText(city);
            }
        }
        if (data == null) {
            return;
        } else {
            switch (requestCode) {
                case 1:
                    Bitmap bitmap = data.getParcelableExtra("data");
                    Uri uri1 = Uri.parse(MediaStore.Images.Media.insertImage(ShowActivity.this.getContentResolver(), bitmap, null, null));
                    if (uri1 != null) {
                        //调用工具类将uri图片转为path
                        String path = ImageUtil.getPath(ShowActivity.this, uri1);
                        if (path != null) {
                            //将图片转为file
                            File file = new File(path);
                            //调用P层
                            userId = sp.getString("userId", "");
                            sessionId = sp.getString("sessionId", "");
                            Map<String, Object> headMap = new HashMap<>();
                            headMap.put("userId", userId);
                            headMap.put("sessionId", sessionId);
                            mineFragment.mPresenter.headIconPresenter(headMap, file);
                        }
                    }
                    break;
                case 2:
                    Uri uri = data.getData();
                    if (uri != null) {
                        //调用工具类将uri图片转为path
                        String path = ImageUtil.getPath(ShowActivity.this, uri);
                        //Toast.makeText(ShowActivity.this, path, Toast.LENGTH_LONG).show();
                        if (path != null) {
                            //将图片转为file
                            File file = new File(path);
                            //调用P层
                            userId = sp.getString("userId", "");
                            sessionId = sp.getString("sessionId", "");
                            Map<String, Object> headMap = new HashMap<>();
                            headMap.put("userId", userId);
                            headMap.put("sessionId", sessionId);
                            mineFragment.mPresenter.headIconPresenter(headMap, file);
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }


    private SharedPreferences mSP;
    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        private String mCity;

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    Log.i(TAG, "当前定位结果来源-----" + amapLocation.getLocationType());//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    Log.i(TAG, "纬度 ----------------" + amapLocation.getLatitude());//获取纬度
                    Log.i(TAG, "经度-----------------" + amapLocation.getLongitude());//获取经度
                    Log.i(TAG, "精度信息-------------" + amapLocation.getAccuracy());//获取精度信息
                    Log.i(TAG, "地址-----------------" + amapLocation.getAddress());//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    Log.i(TAG, "国家信息-------------" + amapLocation.getCountry());//国家信息
                    Log.i(TAG, "省信息---------------" + amapLocation.getProvince());//省信息
                    Log.i(TAG, "城市信息-------------" + amapLocation.getCity());//城市信息
                    Log.i(TAG, "城区信息-------------" + amapLocation.getDistrict());//城区信息
                    Log.i(TAG, "街道信息-------------" + amapLocation.getStreet());//街道信息
                    Log.i(TAG, "街道门牌号信息-------" + amapLocation.getStreetNum());//街道门牌号信息
                    Log.i(TAG, "城市编码-------------" + amapLocation.getCityCode());//城市编码
                    Log.i(TAG, "地区编码-------------" + amapLocation.getAdCode());//地区编码
                    Log.i(TAG, "当前定位点的信息-----" + amapLocation.getAoiName());//获取当前定位点的AOI信息
                    Toast.makeText(ShowActivity.this, "当前定位城市：" + amapLocation.getCity(), Toast.LENGTH_SHORT).show();
                    //创建SharedPreferences储存数据
                    mSP = getSharedPreferences("configs", Context.MODE_PRIVATE);
                    mSP.edit().putString("city", amapLocation.getCity()).commit();
                    mCity = amapLocation.getCity();
                    cinemaDwAddr.setText(amapLocation.getCity());
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    private void startLocaion() {
        mLocationClient = new AMapLocationClient(this);
        mLocationClient.setLocationListener(mLocationListener);

        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    //动态注册权限
    private void stateNetWork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
            ActivityCompat.requestPermissions(this, mStatenetwork, 100);
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
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ShowActivity.this).toBundle());
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