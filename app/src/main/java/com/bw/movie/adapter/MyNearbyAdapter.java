package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.NearbyCinemasBean;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNearbyAdapter extends RecyclerView.Adapter<MyNearbyAdapter.ViewHolder> {
    Context context;
    NearbyCinemasBean nearbyCinemasBean;


    public MyNearbyAdapter(Context context, NearbyCinemasBean nearbyCinemasBean) {
        this.context = context;
        this.nearbyCinemasBean = nearbyCinemasBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommend_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Uri uri = Uri.parse(nearbyCinemasBean.getResult().get(i).getLogo());
        viewHolder.simPleRecommend.setImageURI(uri);
        viewHolder.textNameRecommend.setText(nearbyCinemasBean.getResult().get(i).getName());
        TextPaint paint = viewHolder.textNameRecommend.getPaint();
        paint.setFakeBoldText(true);
        viewHolder.textAddressRecommend.setText(nearbyCinemasBean.getResult().get(i).getAddress());
        viewHolder.textKmRecommend.setText(nearbyCinemasBean.getResult().get(i).getDistance()+"km");
    }

    @Override
    public int getItemCount() {
        return nearbyCinemasBean.getResult().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.simPle_recommend)
        SimpleDraweeView simPleRecommend;
        @BindView(R.id.text_name_recommend)
        TextView textNameRecommend;
        @BindView(R.id.text_address_recommend)
        TextView textAddressRecommend;
        @BindView(R.id.text_km_recommend)
        TextView textKmRecommend;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
