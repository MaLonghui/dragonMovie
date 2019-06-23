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
import com.bw.movie.bean.MovieAttentionBean;
import com.bw.movie.net.NetWorkUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMovieAttentionAdapter extends RecyclerView.Adapter<MyMovieAttentionAdapter.ViewHolder> {
    Context context;
    MovieAttentionBean movieAttentionBean;


    public MyMovieAttentionAdapter(Context context, MovieAttentionBean movieAttentionBean) {
        this.context = context;
        this.movieAttentionBean = movieAttentionBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.movieattention_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (NetWorkUtils.isNetworkAvailable(context)) {
            Uri uri = Uri.parse(movieAttentionBean.getResult().get(i).getLogo());
            viewHolder.simPleAttentionCinema.setImageURI(uri);
            viewHolder.textTitleAttentionCinema.setText(movieAttentionBean.getResult().get(i).getName());
            viewHolder.textCountAttentionCinema.setText(movieAttentionBean.getResult().get(i).getAddress());
        }
    }

    @Override
    public int getItemCount() {
        return movieAttentionBean.getResult().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.simPle_attention_cinema)
        SimpleDraweeView simPleAttentionCinema;
        @BindView(R.id.text_title_attention_cinema)
        TextView textTitleAttentionCinema;
        @BindView(R.id.text_count_attention_cinema)
        TextView textCountAttentionCinema;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
