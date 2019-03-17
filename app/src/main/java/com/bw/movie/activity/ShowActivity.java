package com.bw.movie.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.fragment.CinemaFragment;
import com.bw.movie.fragment.FilmFragment;
import com.bw.movie.fragment.mine.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShowActivity extends AppCompatActivity {

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
    private CinemaFragment cinemaFragment;
    private FilmFragment filmFragment;
    private MineFragment mineFragment;
    private List<Fragment> list;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);

        //获取fragment事务
        fragmentManager = getSupportFragmentManager();
        list = new ArrayList<>();
        filmFragment = new FilmFragment();
        cinemaFragment = new CinemaFragment();
        mineFragment = new MineFragment();
        list.add(filmFragment);
        list.add(cinemaFragment);
        list.add(mineFragment);

        fragmentManager.beginTransaction()
                .add(R.id.fram_layout, filmFragment)
                .add(R.id.fram_layout, cinemaFragment)
                .add(R.id.fram_layout, mineFragment)
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
}
