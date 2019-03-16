package com.bw.movie.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.activity.ShowActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class GuideFour extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guide_four, container, false);

        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            Flowable.intervalRange(0,3,0,1,TimeUnit.SECONDS)
                    .doOnNext(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {

                        }
                    })
                    .doOnComplete(new Action() {
                        @Override
                        public void run() throws Exception {
                          startActivity(new Intent(getActivity(),ShowActivity.class));
                        }
                    })
                    .subscribe();
        }
    }
}