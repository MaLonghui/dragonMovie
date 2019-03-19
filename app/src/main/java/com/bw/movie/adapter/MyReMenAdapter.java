package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.filmdetails.FilmDetailsActivity;
import com.bw.movie.bean.ReFilmBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.EventListener;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyReMenAdapter extends RecyclerView.Adapter<MyReMenAdapter.ViewHolder> {
    private Context context;
    private List<ReFilmBean.ResultBean> reBeanList;

    public MyReMenAdapter(Context context, List<ReFilmBean.ResultBean> reBeanList) {
        this.context = context;
        this.reBeanList = reBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_show_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Uri uri = Uri.parse(reBeanList.get(position).getImageUrl());
        holder.searchSimpleView.setImageURI(uri);
        holder.searchFilmName.setText(reBeanList.get(position).getName());
        holder.searchSummary.setText("简介："+reBeanList.get(position).getSummary());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context,FilmDetailsActivity.class));
                EventBus.getDefault().postSticky(reBeanList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return reBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.search_simple_view)
        SimpleDraweeView searchSimpleView;
        @BindView(R.id.search_film_name)
        TextView searchFilmName;
        @BindView(R.id.search_collection)
        ImageView searchCollection;
        @BindView(R.id.search_summary)
        TextView searchSummary;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
