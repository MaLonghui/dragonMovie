package com.bw.movie.activity.attention;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.fragment.cinemaattention.CinemaattentionFragment;
import com.bw.movie.fragment.filmattention.FilmattentionFragment;
import com.bw.movie.mvp.MVPBaseActivity;
import com.bw.movie.net.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AttentionActivity extends MVPBaseActivity<AttentionContract.View, AttentionPresenter> implements AttentionContract.View {

    @BindView(R.id.followmovie)
    RadioButton followmovie;
    @BindView(R.id.followcamera)
    RadioButton followcamera;
    @BindView(R.id.followgroup)
    RadioGroup followgroup;
    @BindView(R.id.followviewpager)
    ViewPager followviewpager;
    @BindView(R.id.flowimages)
    ImageView flowimages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);
        ButterKnife.bind(this);
        if (NetWorkUtils.isNetworkAvailable(AttentionActivity.this)){
            final List<Fragment> list = new ArrayList<>();
            list.add(new FilmattentionFragment());
            list.add(new CinemaattentionFragment());
            followviewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int i) {
                    return list.get(i);
                }

                @Override
                public int getCount() {
                    return list.size();
                }
            });
            followviewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    followgroup.check(followgroup.getChildAt(i).getId());
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
            followgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId)
                    {
                        case R.id.followmovie:
                            followviewpager.setCurrentItem(0);
                            followmovie.setBackgroundResource(R.drawable.shape_btn);
                            followmovie.setTextColor(Color.WHITE);
                            followcamera.setBackgroundResource(R.drawable.shape_btn_yuan);
                            followcamera.setTextColor(Color.BLACK);
                            break;
                        case R.id.followcamera:
                            followviewpager.setCurrentItem(1);
                            followcamera.setBackgroundResource(R.drawable.shape_btn);
                            followcamera.setTextColor(Color.WHITE);
                            followmovie.setBackgroundResource(R.drawable.shape_btn_yuan);
                            followmovie.setTextColor(Color.BLACK);
                            break;

                    }
                }
            });
            flowimages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
