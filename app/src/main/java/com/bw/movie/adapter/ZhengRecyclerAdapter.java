package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.filmdetails.FilmDetailsActivity;
import com.bw.movie.bean.ShangFilmBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhengRecyclerAdapter extends RecyclerView.Adapter<ZhengRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<ShangFilmBean.ResultBean> shangFilmBeanResult;
    public ZhengRecyclerAdapter(Context context, List<ShangFilmBean.ResultBean> shangFilmBeanResult) {
        this.context = context;
        this.shangFilmBeanResult = shangFilmBeanResult;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.film_item_show_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.filmName.getBackground().setAlpha(200);
        holder.filmName.setText(shangFilmBeanResult.get(position).getName());
        Uri uri = Uri.parse(shangFilmBeanResult.get(position).getImageUrl());
        holder.filmSimpleView.setImageURI(uri);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,FilmDetailsActivity.class));
                EventBus.getDefault().postSticky(shangFilmBeanResult.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return shangFilmBeanResult.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.film_simple_view)
        SimpleDraweeView filmSimpleView;
        @BindView(R.id.film_name)
        TextView filmName;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
