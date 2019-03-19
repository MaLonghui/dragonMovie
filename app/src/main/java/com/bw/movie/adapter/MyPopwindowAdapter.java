package com.bw.movie.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.FilmDetailsBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class MyPopwindowAdapter extends RecyclerView.Adapter<MyPopwindowAdapter.ViewHolder> {

    private Context context;
    private FilmDetailsBean.ResultBean resultBean;


    public MyPopwindowAdapter(Context context, FilmDetailsBean.ResultBean resultBean) {
        this.context = context;
        this.resultBean = resultBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.yugao_item_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //图片路径
        String imageUrl = resultBean.getShortFilmList().get(i).getImageUrl();
        //视频路径
        String videoUrl = resultBean.getShortFilmList().get(i).getVideoUrl();

        if (!imageUrl.equals("")&&!videoUrl.equals("")){
            boolean what = viewHolder.videoPlay.setUp(videoUrl, JCVideoPlayer.SCREEN_LAYOUT_LIST, "");

            if (what){
                viewHolder.videoPlay.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(context).load(imageUrl).into(viewHolder.videoPlay.thumbImageView);
            }
        }

    }

    @Override
    public int getItemCount() {
        return resultBean.getShortFilmList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.video_play)
        JCVideoPlayerStandard videoPlay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
