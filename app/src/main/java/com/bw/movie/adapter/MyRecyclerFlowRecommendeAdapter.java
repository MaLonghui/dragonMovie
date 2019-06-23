package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.bean.FilmFromIdBean;
import com.bw.movie.net.NetWorkUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRecyclerFlowRecommendeAdapter extends RecyclerView.Adapter<MyRecyclerFlowRecommendeAdapter.ViewHolder> {
    Context context;
    FilmFromIdBean filmFromIdBean;


    public MyRecyclerFlowRecommendeAdapter(Context context, FilmFromIdBean filmFromIdBean) {
        this.context = context;
        this.filmFromIdBean = filmFromIdBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (NetWorkUtils.isNetworkAvailable(context)) {
        /*//设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(8);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

        Glide.with(context).load( filmFromIdBean.getResult().get(i).getImageUrl()).apply(options)
                .into(viewHolder.img);*/
            Uri uri = Uri.parse(filmFromIdBean.getResult().get(i).getImageUrl());
            viewHolder.img.setImageURI(uri);
        }
    }

    @Override
    public int getItemCount() {
        return filmFromIdBean.getResult().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        SimpleDraweeView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
