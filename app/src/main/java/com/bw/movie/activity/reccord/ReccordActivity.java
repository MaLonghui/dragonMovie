package com.bw.movie.activity.reccord;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.transition.Explode;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.PayBean;
import com.bw.movie.fragment.alreadymoney.AlreadyMoneyFragment;
import com.bw.movie.fragment.cinemaattention.CinemaattentionFragment;
import com.bw.movie.fragment.filmattention.FilmattentionFragment;
import com.bw.movie.fragment.willmoney.WillMoneyFragment;
import com.bw.movie.mvp.MVPBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ReccordActivity extends MVPBaseActivity<ReccordContract.View, ReccordPresenter> implements ReccordContract.View {

    @BindView(R.id.WillMoney)
    RadioButton WillMoney;
    @BindView(R.id.AlreadyMoney)
    RadioButton AlreadyMoney;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.ViewPageMoney)
    ViewPager ViewPageMoney;
    @BindView(R.id.images_back_Money)
    ImageView imagesBackMoney;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reccord);
        getWindow().setEnterTransition(new Explode().setDuration(800));
        getWindow().setExitTransition(new Explode().setDuration(800));
        ButterKnife.bind(this);
        final List<Fragment> list = new ArrayList<>();
        list.add(new WillMoneyFragment());
        list.add(new AlreadyMoneyFragment());
        ViewPageMoney.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        ViewPageMoney.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                radiogroup.check(radiogroup.getChildAt(i).getId());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.WillMoney:
                        ViewPageMoney.setCurrentItem(0);
                        WillMoney.setBackgroundResource(R.drawable.shape_btn);
                        WillMoney.setTextColor(Color.WHITE);
                        AlreadyMoney.setBackgroundResource(R.drawable.shape_btn_yuan);
                        AlreadyMoney.setTextColor(Color.BLACK);
                        break;
                    case R.id.AlreadyMoney:
                        ViewPageMoney.setCurrentItem(1);
                        AlreadyMoney.setBackgroundResource(R.drawable.shape_btn);
                        AlreadyMoney.setTextColor(Color.WHITE);
                        WillMoney.setBackgroundResource(R.drawable.shape_btn_yuan);
                        WillMoney.setTextColor(Color.BLACK);
                        break;
                }
            }
        });
        imagesBackMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
