package com.bw.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("confing", Context.MODE_PRIVATE);
        edit = preferences.edit();

        Flowable.intervalRange(0,3,0,1,TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {

                        if (preferences.getBoolean("flag",true)){
                            edit.putBoolean("flag",false);
                            edit.commit();
                            startActivity(new Intent(MainActivity.this,GuideActivity.class));
                            finish();
                        }else{
                            startActivity(new Intent(MainActivity.this,ShowActivity.class));
                            finish();
                        }



                    }
                })
                .subscribe();
    }
}
