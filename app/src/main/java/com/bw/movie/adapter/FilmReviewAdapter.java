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
import com.bw.movie.bean.FilmReviewBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmReviewAdapter extends RecyclerView.Adapter<FilmReviewAdapter.ViewHolder> {


    private Context context;
    private List<FilmReviewBean.ResultBean> reviewBeanResult;

    public FilmReviewAdapter(Context context, List<FilmReviewBean.ResultBean> reviewBeanResult) {
        this.context = context;
        this.reviewBeanResult = reviewBeanResult;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.film_review_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Uri uri = Uri.parse(reviewBeanResult.get(i).getCommentHeadPic());
        viewHolder.reviewSimpleView.setImageURI(uri);
        viewHolder.reviewName.setText(reviewBeanResult.get(i).getCommentUserName());
        viewHolder.reviewComment.setText(reviewBeanResult.get(i).getCommentContent());

        Date date = new Date(reviewBeanResult.get(i).getCommentTime());
        SimpleDateFormat sd = new SimpleDateFormat("MM-dd hh:mm");
        String format = sd.format(date);
        viewHolder.reviewTime.setText(format);
        viewHolder.reviewPriseNum.setText(reviewBeanResult.get(i).getGreatNum());
        viewHolder.reviewReplyNum.setText(reviewBeanResult.get(i).getReplyNum());
    }




    @Override
    public int getItemCount() {
        return reviewBeanResult.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.review_simple_view)
        SimpleDraweeView reviewSimpleView;
        @BindView(R.id.review_comment)
        TextView reviewComment;
        @BindView(R.id.review_time)
        TextView reviewTime;
        @BindView(R.id.review_prise_img)
        ImageView reviewPriseImg;
        @BindView(R.id.review_prise_num)
        TextView reviewPriseNum;
        @BindView(R.id.review_reply_img)
        ImageView reviewReplyImg;
        @BindView(R.id.review_reply_num)
        TextView reviewReplyNum;
        @BindView(R.id.review_name)
        TextView reviewName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
