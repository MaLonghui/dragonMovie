package com.bw.movie.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.activity.NetActivity;
import com.bw.movie.net.NetWorkUtils;

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
        final ObjectAnimator anim = ObjectAnimator.ofInt(holder.img, "ImageLevel", 0, 10000);
        anim.setDuration(1000);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetWorkUtils.isNetworkAvailable(context)) {
                    onClickListener.click();
                }else{
                    context.startActivity(new Intent(context,NetActivity.class));
                    Toast.makeText(context,"没网还点啥呀",Toast.LENGTH_LONG).show();
                }
            }
        });
        anim.start();
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (NetWorkUtils.isNetworkAvailable(context)) {
            //设置图片圆角角度
            RoundedCorners roundedCorners = new RoundedCorners(8);
            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            RequestOptions options = RequestOptions
                    .bitmapTransform(roundedCorners)
                    .placeholder(R.mipmap.loading)
                    .override(300, 300);


            Glide.with(context)
                    .load(mColor[position % mColor.length])
                    .apply(options)
                    .into(holder.img);
        }
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
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void click();
    }
}
