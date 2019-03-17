package com.bw.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlowAdapter extends RecyclerView.Adapter<FlowAdapter.ViewHolder> {

    private Context context;
    private int mColor[] = {R.mipmap.tiexue, R.mipmap.bali, R.mipmap.wushuang, R.mipmap.mengchong, R.mipmap.maoxian};

    public FlowAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(8);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

        Glide.with(context).load(mColor[position % mColor.length]).apply(options)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mColor.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
