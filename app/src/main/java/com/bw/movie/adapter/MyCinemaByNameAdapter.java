package com.bw.movie.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
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
import com.bw.movie.activity.CinemaByNameActivity;
import com.bw.movie.activity.recommenddetails.RecommenddetailsActivity;
import com.bw.movie.bean.CinemaByNameBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCinemaByNameAdapter extends RecyclerView.Adapter<MyCinemaByNameAdapter.ViewHolder> {

    private Context context;
    private List<CinemaByNameBean.ResultBean> nameBeanResult;

    public MyCinemaByNameAdapter(Context context, List<CinemaByNameBean.ResultBean> nameBeanResult) {
        this.context = context;
        this.nameBeanResult = nameBeanResult;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommend_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Uri uri = Uri.parse(nameBeanResult.get(i).getLogo());
        viewHolder.simPleRecommend.setImageURI(uri);
        viewHolder.textNameRecommend.setText(nameBeanResult.get(i).getName());
        TextPaint paint = viewHolder.textNameRecommend.getPaint();
        paint.setFakeBoldText(true);
        viewHolder.textAddressRecommend.setText(nameBeanResult.get(i).getAddress());
        viewHolder.textKmRecommend.setText(nameBeanResult.get(i).getDistance() + "km");
        final String followCinema = nameBeanResult.get(i).getFollowCinema();
        if (followCinema.equals("1")){
            viewHolder.cinemaPrise.setImageResource(R.mipmap.com_icon_collection_selected);
        }else{
            viewHolder.cinemaPrise.setImageResource(R.mipmap.com_icon_collection_default);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onItemClickListener.click(nameBeanResult.get(i).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameBeanResult.size();
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
            ButterKnife.bind(this,itemView);
        }
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void click(String id);
    }
}
