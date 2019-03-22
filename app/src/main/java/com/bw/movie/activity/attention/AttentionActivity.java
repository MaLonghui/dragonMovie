package com.bw.movie.activity.attention;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.fragment.cinemaattention.CinemaattentionFragment;
import com.bw.movie.fragment.filmattention.FilmattentionFragment;
import com.bw.movie.mvp.MVPBaseActivity;

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
        List<Fragment> list = new ArrayList<>();
        list.add(new FilmattentionFragment());
        list.add(new CinemaattentionFragment());
    }
}
