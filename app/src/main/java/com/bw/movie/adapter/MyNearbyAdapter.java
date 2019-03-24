package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.recommenddetails.RecommenddetailsActivity;
import com.bw.movie.bean.NearbyCinemasBean;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNearbyAdapter extends RecyclerView.Adapter<MyNearbyAdapter.ViewHolder> {
    Context context;
    NearbyCinemasBean nearbyCinemasBean;
    private AttentionClick attentionClick;


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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Uri uri = Uri.parse(nearbyCinemasBean.getResult().get(i).getLogo());
        viewHolder.simPleRecommend.setImageURI(uri);
        viewHolder.textNameRecommend.setText(nearbyCinemasBean.getResult().get(i).getName());
        TextPaint paint = viewHolder.textNameRecommend.getPaint();
        paint.setFakeBoldText(true);
        viewHolder.textAddressRecommend.setText(nearbyCinemasBean.getResult().get(i).getAddress());
        viewHolder.textKmRecommend.setText(nearbyCinemasBean.getResult().get(i).getDistance() + "km");
        final String followCinema = nearbyCinemasBean.getResult().get(i).getFollowCinema();
        if (followCinema.equals("1")) {
            viewHolder.cinemaPrise.setImageResource(R.mipmap.com_icon_collection_selected);
        } else {
            viewHolder.cinemaPrise.setImageResource(R.mipmap.com_icon_collection_default);
        }
        viewHolder.cinemaPrise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attentionClick != null) {
                    if (nearbyCinemasBean.getResult().get(i).getFollowCinema().equals("1")) {
                        nearbyCinemasBean.getResult().get(i).setFollowCinema("2");
                    } else {
                        nearbyCinemasBean.getResult().get(i).setFollowCinema("1");
                    }
                    String followCinema1 = nearbyCinemasBean.getResult().get(i).getFollowCinema();
                    attentionClick.clickattention(nearbyCinemasBean.getResult().get(i).getId(), followCinema1.equals("1"));
                    notifyDataSetChanged();
                }
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecommenddetailsActivity.class);
                intent.putExtra("eid", nearbyCinemasBean.getResult().get(i).getId());
                context.startActivity(intent);
            }
        });
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
        @BindView(R.id.cinema_prise)
        ImageView cinemaPrise;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void setAttentionClick(AttentionClick attentionClick){
        this.attentionClick = attentionClick;
    }
    public interface AttentionClick{
        void clickattention(String cinemaId,boolean b);
    }
}
