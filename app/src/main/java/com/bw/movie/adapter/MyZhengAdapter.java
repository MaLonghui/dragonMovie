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
import com.bw.movie.bean.ShangFilmBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyZhengAdapter extends RecyclerView.Adapter<MyZhengAdapter.ViewHolder> {
    private Context context;
    private List<ShangFilmBean.ResultBean> shangBeanList;

    public MyZhengAdapter(Context context, List<ShangFilmBean.ResultBean> shangBeanList) {
        this.context = context;
        this.shangBeanList = shangBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_show_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Uri uri = Uri.parse(shangBeanList.get(position).getImageUrl());
        holder.searchSimpleView.setImageURI(uri);
        holder.searchFilmName.setText(shangBeanList.get(position).getName());
        holder.searchSummary.setText("简介："+shangBeanList.get(position).getSummary());
        int followMovie = shangBeanList.get(position).getFollowMovie();
        if (followMovie ==1){
            holder.searchCollection.setImageResource(R.mipmap.com_icon_collection_selected);
        }else{
            holder.searchCollection.setImageResource(R.mipmap.com_icon_collection_default);
        }
        holder.searchCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attentionClick!=null){
                    if (shangBeanList.get(position).getFollowMovie()==1){
                        shangBeanList.get(position).setFollowMovie(2);
                    }else{
                        shangBeanList.get(position).setFollowMovie(1);
                    }
                    int followMovie = shangBeanList.get(position).getFollowMovie();
                    attentionClick.clickattention(shangBeanList.get(position).getId(),position,followMovie ==1);
                    notifyDataSetChanged();
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context,FilmDetailsActivity.class));
                EventBus.getDefault().postSticky(shangBeanList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return shangBeanList.size();
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
    public AttentionClick attentionClick;

    public void setAttentionClick(AttentionClick attentionClick) {
        this.attentionClick = attentionClick;
    }

    public interface AttentionClick{
        void clickattention(String cinemaId,int i,boolean b);
    }
}
