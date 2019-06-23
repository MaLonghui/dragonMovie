package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.FilmAttentionBean;
import com.bw.movie.net.NetWorkUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFilmAttentionAdapter extends RecyclerView.Adapter<MyFilmAttentionAdapter.ViewHolder> {
    Context context;
    FilmAttentionBean filmAttentionBean;


    public MyFilmAttentionAdapter(Context context, FilmAttentionBean filmAttentionBean) {
        this.context = context;
        this.filmAttentionBean = filmAttentionBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.filmattention_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (NetWorkUtils.isNetworkAvailable(context)) {
            Uri uri = Uri.parse(filmAttentionBean.getResult().get(i).getImageUrl());
            viewHolder.simPleTitleFilmAttention.setImageURI(uri);
            viewHolder.textTitleFilmAttention.setText(filmAttentionBean.getResult().get(i).getName());
            viewHolder.textCountFilmAttention.setText(filmAttentionBean.getResult().get(i).getSummary());
            Date date = new Date(filmAttentionBean.getResult().get(i).getReleaseTime());
            SimpleDateFormat sd = new SimpleDateFormat("yy-MM-dd");
            String format = sd.format(date);
            viewHolder.textTimeFilmAttention.setText(format);
        }
    }

    @Override
    public int getItemCount() {
        return filmAttentionBean.getResult().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.simPle_title_film_attention)
        SimpleDraweeView simPleTitleFilmAttention;
        @BindView(R.id.text_title_film_attention)
        TextView textTitleFilmAttention;
        @BindView(R.id.text_count_film_attention)
        TextView textCountFilmAttention;
        @BindView(R.id.text_time_film_attention)
        TextView textTimeFilmAttention;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
