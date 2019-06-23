package com.bw.movie.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.net.NetWorkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NetActivity extends AppCompatActivity {

    @BindView(R.id.wang)
    ImageView wang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.wang)
    public void onViewClicked() {
        if (NetWorkUtils.isNetworkAvailable(NetActivity.this)) {
            Toast.makeText(NetActivity.this,"adasd",Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
