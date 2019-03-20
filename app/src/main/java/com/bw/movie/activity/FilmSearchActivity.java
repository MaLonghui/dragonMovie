package com.bw.movie.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bw.movie.R;
import com.bw.movie.adapter.MyJiAdapter;
import com.bw.movie.adapter.MyReMenAdapter;
import com.bw.movie.adapter.MyZhengAdapter;
import com.bw.movie.bean.JiFilmBean;
import com.bw.movie.bean.ReFilmBean;
import com.bw.movie.bean.ShangFilmBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilmSearchActivity extends Activity {

    public static final String TAG = "FilmDetailsActivity";
    @BindView(R.id.btn_re)
    Button btnRe;
    @BindView(R.id.btn_zheng)
    Button btnZheng;
    @BindView(R.id.btn_jijiang)
    Button btnJijiang;
    @BindView(R.id.search_recycler_view)
    RecyclerView searchRecyclerView;
    @BindView(R.id.search_return)
    ImageView searchReturn;
    @BindView(R.id.search_linear)
    LinearLayout searchLinear;
    private List<ReFilmBean.ResultBean> reBeanList;
    private List<ShangFilmBean.ResultBean> shangBeanList;
    private List<JiFilmBean.ResultBean> jiBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_search);
        ButterKnife.bind(this);
        //接收intent的值
        reBeanList = (List<ReFilmBean.ResultBean>) getIntent().getSerializableExtra("reBeanList");
        shangBeanList = (List<ShangFilmBean.ResultBean>) getIntent().getSerializableExtra("shangBeanList");
        jiBeanList = (List<JiFilmBean.ResultBean>) getIntent().getSerializableExtra("jiBeanList");
        //Log.i(TAG, "onCreate: " + reBeanList.get(0).getName() + shangBeanList.get(0).getName() + jiBeanList.get(0).getName());
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        searchRecyclerView.setLayoutManager(linearLayoutManager);
        MyReMenAdapter reMenAdapter = new MyReMenAdapter(this, reBeanList);
        searchRecyclerView.setAdapter(reMenAdapter);
        int type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            setReAdapter();
        } else if (type == 1) {
            setZhengAdapter();
        } else if (type == 2) {
            setJiAdapter();
        }
        searchReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @OnClick({R.id.btn_re, R.id.btn_zheng, R.id.btn_jijiang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_re:
                setReAdapter();
                break;
            case R.id.btn_zheng:
                setZhengAdapter();
                break;
            case R.id.btn_jijiang:
                setJiAdapter();
                break;
        }
    }

    private void setJiAdapter() {
        btnJijiang.setBackgroundResource(R.drawable.button_ripple);
        btnJijiang.setTextColor(Color.parseColor("#ffffff"));
        btnRe.setTextColor(Color.BLACK);
        btnRe.setBackgroundColor(Color.parseColor("#ffffff"));
        btnZheng.setTextColor(Color.BLACK);
        btnZheng.setBackgroundColor(Color.parseColor("#ffffff"));
        MyJiAdapter jiAdapter = new MyJiAdapter(this, jiBeanList);
        searchRecyclerView.setAdapter(jiAdapter);
    }

    private void setZhengAdapter() {
        btnZheng.setBackgroundResource(R.drawable.button_ripple);
        btnZheng.setTextColor(Color.parseColor("#ffffff"));
        btnRe.setTextColor(Color.BLACK);
        btnRe.setBackgroundColor(Color.parseColor("#ffffff"));
        btnJijiang.setTextColor(Color.BLACK);
        btnJijiang.setBackgroundColor(Color.parseColor("#ffffff"));
        MyZhengAdapter zhengAdapter = new MyZhengAdapter(this, shangBeanList);
        searchRecyclerView.setAdapter(zhengAdapter);
    }

    private void setReAdapter() {
        btnRe.setBackgroundResource(R.drawable.button_ripple);
        btnRe.setTextColor(Color.parseColor("#ffffff"));
        btnJijiang.setTextColor(Color.BLACK);
        btnJijiang.setBackgroundColor(Color.parseColor("#ffffff"));
        btnZheng.setTextColor(Color.BLACK);
        btnZheng.setBackgroundColor(Color.parseColor("#ffffff"));
        MyReMenAdapter reMenAdapter = new MyReMenAdapter(this, reBeanList);
        searchRecyclerView.setAdapter(reMenAdapter);
    }
}
