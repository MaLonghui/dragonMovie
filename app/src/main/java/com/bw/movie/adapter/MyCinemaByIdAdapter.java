package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.NetActivity;
import com.bw.movie.bean.CinemaByIdBean;
import com.bw.movie.net.NetWorkUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Url;

public class MyCinemaByIdAdapter extends RecyclerView.Adapter<MyCinemaByIdAdapter.ViewHolder> {

    private Context context;
    private List<CinemaByIdBean.ResultBean> beanResult;
    private onClicklistener onClicklistener;
    private CinemaAttentionClick attentionClick;

    public void setOnClicklistener(MyCinemaByIdAdapter.onClicklistener onClicklistener) {
        this.onClicklistener = onClicklistener;
    }

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (NetWorkUtils.isNetworkAvailable(context)) {
            Uri uri = Uri.parse(beanResult.get(i).getLogo());
            viewHolder.simPleRecommend.setImageURI(uri);
            viewHolder.textNameRecommend.setText(beanResult.get(i).getName());
            viewHolder.textAddressRecommend.setText(beanResult.get(i).getAddress());
            viewHolder.textKmRecommend.setText(beanResult.get(i).getDistance() + "km");
            String followCinema = beanResult.get(i).getFollowCinema();
            if (followCinema.equals("1")) {
                viewHolder.cinemaPrise.setImageResource(R.mipmap.com_icon_collection_selected);
            } else {
                viewHolder.cinemaPrise.setImageResource(R.mipmap.com_icon_collection_default);
            }
            viewHolder.cinemaPrise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (attentionClick != null) {
                        if (beanResult.get(i).getFollowCinema().equals("1")) {
                            beanResult.get(i).setFollowCinema("0");
                        } else {
                            beanResult.get(i).setFollowCinema("1");
                        }
                        String followCinema1 = beanResult.get(i).getFollowCinema();
                        attentionClick.clickattention(beanResult.get(i).getId(), followCinema1.equals("1"));
                        notifyDataSetChanged();
                    }
                }
            });
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetWorkUtils.isNetworkAvailable(context)) {
                        onClicklistener.click(beanResult.get(i));
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
    public interface onClicklistener{
        void click(CinemaByIdBean.ResultBean id);
    }

    public void setAttentionClick(CinemaAttentionClick attentionClick){
        this.attentionClick = attentionClick;
    }
    public interface CinemaAttentionClick{
        void clickattention(String cinemaId,boolean b);
    }
}
