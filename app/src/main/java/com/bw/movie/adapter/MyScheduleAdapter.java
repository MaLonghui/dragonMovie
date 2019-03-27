package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.seat.SeatActivity;
import com.bw.movie.bean.MovieIdAndFilmBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyScheduleAdapter extends RecyclerView.Adapter<MyScheduleAdapter.ViewHolder> {


    private Context context;
    private List<MovieIdAndFilmBean.ResultBean> resultBeans;

    public MyScheduleAdapter(Context context, List<MovieIdAndFilmBean.ResultBean> resultBeans) {
        this.context = context;
        this.resultBeans = resultBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.movieandfilm_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.textNameMovieandfilm.setText(resultBeans.get(i).getScreeningHall());
        viewHolder.textTimeMovieandfilm.setText(resultBeans.get(i).getBeginTime());
        viewHolder.textTime1Movieandfilm.setText(resultBeans.get(i).getEndTime());
        String[] split = resultBeans.get(i).getPrice().split("\\.");
        viewHolder.textPriceMovieandfilm.setText(split[0] + ".");
        viewHolder.textPrice1Movieandfilm.setText(split[1]);
        viewHolder.imgIconSit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double price = Double.parseDouble(resultBeans.get(i).getPrice());
                String beginTime = resultBeans.get(i).getBeginTime();
                String endTime = resultBeans.get(i).getEndTime();
                String seatsUseCount = resultBeans.get(i).getSeatsUseCount();
                String screeningHall = resultBeans.get(i).getScreeningHall();
                String id = resultBeans.get(i).getId();
                Intent intent = new Intent(context, SeatActivity.class);
                intent.putExtra("price",price);
                intent.putExtra("beginTime",beginTime);
                intent.putExtra("endTime",endTime);
                intent.putExtra("seatsUseCount",seatsUseCount);
                intent.putExtra("screeningHall",screeningHall);
                intent.putExtra("scheduleId",id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_name_movieandfilm)
        TextView textNameMovieandfilm;
        @BindView(R.id.text_time_movieandfilm)
        TextView textTimeMovieandfilm;
        @BindView(R.id.text_time1_movieandfilm)
        TextView textTime1Movieandfilm;
        @BindView(R.id.text_money)
        TextView textMoney;
        @BindView(R.id.text_price_movieandfilm)
        TextView textPriceMovieandfilm;
        @BindView(R.id.text_price1_movieandfilm)
        TextView textPrice1Movieandfilm;
        @BindView(R.id.img_icon_sit)
        ImageView imgIconSit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}