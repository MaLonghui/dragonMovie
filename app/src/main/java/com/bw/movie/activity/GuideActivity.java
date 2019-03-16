package com.bw.movie.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bw.movie.R;
import com.bw.movie.fragment.GuideFour;
import com.bw.movie.fragment.GuideOne;
import com.bw.movie.fragment.GuideThree;
import com.bw.movie.fragment.GuideTwo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.id_view_pager)
    ViewPager idViewPager;
    @BindView(R.id.id_linear_view_pager)
    LinearLayout idLinearViewPager;
    private List<Fragment> fragmentList;

    private ImageView[] imageViews;
    private int currentIndicator = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        initView();
        initData();


    }

    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new GuideOne());
        fragmentList.add(new GuideTwo());
        fragmentList.add(new GuideThree());
        fragmentList.add(new GuideFour());

        idViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        int len = fragmentList.size();
        imageViews = new ImageView[len];
        for (int i = 0; i < len; i++) {
            imageViews[i] = (ImageView) idLinearViewPager.getChildAt(i);
            imageViews[i].setBackgroundResource(R.drawable.image_my_car_normal);
        }
        imageViews[currentIndicator].setBackgroundResource(R.drawable.image_my_car_selected);
        idViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                imageViews[position].setBackgroundResource(R.drawable.image_my_car_selected);
                imageViews[currentIndicator].setBackgroundResource(R.drawable.image_my_car_normal);
                currentIndicator = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void initView() {

    }
}
