package com.bw.movie.activity;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.activity.recommenddetails.RecommenddetailsActivity;
import com.bw.movie.adapter.MyCinemaByNameAdapter;
import com.bw.movie.bean.CinemaByNameBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CinemaByNameActivity extends AppCompatActivity {

    @BindView(R.id.cinema_byname_recycler)
    RecyclerView cinemaBynameRecycler;
    @BindView(R.id.seach_cinema_return)
    ImageView seachCinemaReturn;
    @BindView(R.id.cinema_relative)
    RelativeLayout cinemaRelative;
    @BindView(R.id.cinema_layout_out)
    LinearLayout cinemaLayoutOut;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_byname);
        ButterKnife.bind(this);
        getWindow().setEnterTransition(new Explode().setDuration(1000));
        getWindow().setExitTransition(new Explode().setDuration(1000));
        Intent intent = getIntent();
        List<CinemaByNameBean.ResultBean> nameBeanResult = (List<CinemaByNameBean.ResultBean>) intent.getSerializableExtra("nameBeanResult");
        //Toast.makeText(this, nameBeanResult.get(0).getName(), Toast.LENGTH_SHORT).show();
        if (nameBeanResult!=null){
            cinemaRelative.setVisibility(View.VISIBLE);
            cinemaLayoutOut.setVisibility(View.GONE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            cinemaBynameRecycler.setLayoutManager(linearLayoutManager);
            MyCinemaByNameAdapter myCinemaByNameAdapter = new MyCinemaByNameAdapter(this, nameBeanResult);
            cinemaBynameRecycler.setAdapter(myCinemaByNameAdapter);
            myCinemaByNameAdapter.setOnItemClickListener(new MyCinemaByNameAdapter.OnItemClickListener() {
                @Override
                public void click(String id) {
                      Intent intent = new Intent(CinemaByNameActivity.this, RecommenddetailsActivity.class);
                        intent.putExtra("eid",id);
                        startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(CinemaByNameActivity.this).toBundle());
                }
            });
        }else{
            cinemaRelative.setVisibility(View.GONE);
            cinemaLayoutOut.setVisibility(View.VISIBLE);
        }

        seachCinemaReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


}
