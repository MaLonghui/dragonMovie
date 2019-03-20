package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.CinemaByIdBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Url;

public class MyCinemaByIdAdapter extends RecyclerView.Adapter<MyCinemaByIdAdapter.ViewHolder> {

    private Context context;
    private List<CinemaByIdBean.ResultBean> beanResult;

    public MyCinemaByIdAdapter(Context context, List<CinemaByIdBean.ResultBean> beanResult) {
        this.context = context;
        this.beanResult = beanResult;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommend_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Uri uri = Uri.parse(beanResult.get(i).getLogo());
        viewHolder.simPleRecommend.setImageURI(uri);
        viewHolder.textNameRecommend.setText(beanResult.get(i).getName());
        viewHolder.textAddressRecommend.setText(beanResult.get(i).getAddress());
        viewHolder.textKmRecommend.setText(beanResult.get(i).getFollowCinema()+"km");
    }

    @Override
    public int getItemCount() {
        return beanResult.size();
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
}
