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
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.NetActivity;
import com.bw.movie.activity.recommenddetails.RecommenddetailsActivity;
import com.bw.movie.bean.RecommendCinemasBean;
import com.bw.movie.net.NetWorkUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRecommendAdapter extends RecyclerView.Adapter<MyRecommendAdapter.ViewHolder> {
    Context context;
    RecommendCinemasBean recommendCinemasBean;
    private AttentionClick attentionClick;


    public MyRecommendAdapter(Context context) {
        this.context = context;
    }
    public void setList(RecommendCinemasBean recommendCinemasBean) {
        this.recommendCinemasBean = recommendCinemasBean;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommend_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        if (NetWorkUtils.isNetworkAvailable(context)) {
            Uri uri = Uri.parse(recommendCinemasBean.getResult().get(i).getLogo());
            viewHolder.simPleRecommend.setImageURI(uri);
            viewHolder.textNameRecommend.setText(recommendCinemasBean.getResult().get(i).getName());
            TextPaint paint = viewHolder.textNameRecommend.getPaint();
            paint.setFakeBoldText(true);
            viewHolder.textAddressRecommend.setText(recommendCinemasBean.getResult().get(i).getAddress());
            viewHolder.textKmRecommend.setText(recommendCinemasBean.getResult().get(i).getDistance() + "km");
            final String followCinema = recommendCinemasBean.getResult().get(i).getFollowCinema();
            if (followCinema.equals("1")) {
                viewHolder.cinemaPrise.setImageResource(R.mipmap.com_icon_collection_selected);
            } else {
                viewHolder.cinemaPrise.setImageResource(R.mipmap.com_icon_collection_default);
            }
            viewHolder.cinemaPrise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (attentionClick != null) {
                        if (recommendCinemasBean.getResult().get(i).getFollowCinema().equals("1")) {
                            recommendCinemasBean.getResult().get(i).setFollowCinema("2");
                        } else {
                            recommendCinemasBean.getResult().get(i).setFollowCinema("1");
                        }
                        String followCinema1 = recommendCinemasBean.getResult().get(i).getFollowCinema();
                        attentionClick.clickattention(recommendCinemasBean.getResult().get(i).getId(), followCinema1.equals("1"));
                        notifyDataSetChanged();
                    }
                }
            });
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetWorkUtils.isNetworkAvailable(context)) {
                        Intent intent = new Intent(context, RecommenddetailsActivity.class);
                        intent.putExtra("eid", recommendCinemasBean.getResult().get(i).getId());
                        context.startActivity(intent);
                    }else{
                        context.startActivity(new Intent(context,NetActivity.class));
                        Toast.makeText(context,"没网还点啥呀",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return recommendCinemasBean.getResult().size();
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
