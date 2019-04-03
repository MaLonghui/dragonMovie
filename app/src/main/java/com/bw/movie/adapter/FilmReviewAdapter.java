package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.FilmReviewBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmReviewAdapter extends RecyclerView.Adapter<FilmReviewAdapter.ViewHolder> {


    private Context context;
    private List<FilmReviewBean.ResultBean> reviewBeanResult;
    private BtnPriaseListener btnPriaseListener;
    private String userId;
    private String sessionId;

    public FilmReviewAdapter(Context context) {
        this.context = context;
        this.reviewBeanResult = new ArrayList<>();
    }

    public void setList(List<FilmReviewBean.ResultBean> reviewBeanResult) {
        this.reviewBeanResult = reviewBeanResult;
        notifyDataSetChanged();
    }

    public void setID(String userId, String sessionId) {
        this.userId = userId;
        this.sessionId = sessionId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.film_review_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Uri uri = Uri.parse(reviewBeanResult.get(i).getCommentHeadPic());
        viewHolder.reviewSimpleView.setImageURI(uri);
        viewHolder.reviewName.setText(reviewBeanResult.get(i).getCommentUserName());
        viewHolder.reviewComment.setText(reviewBeanResult.get(i).getCommentContent());
        viewHolder.reviewReplyNum.setText(reviewBeanResult.get(i).getReplyNum());
        Date date = new Date(reviewBeanResult.get(i).getCommentTime());
        SimpleDateFormat sd = new SimpleDateFormat("MM-dd hh:mm");
        String format = sd.format(date);
        if (reviewBeanResult.get(i).getIsGreat().equals("1")) {
            viewHolder.reviewPriseImg.setImageResource(R.mipmap.com_icon_praise_selected);
        } else {
            viewHolder.reviewPriseImg.setImageResource(R.mipmap.com_icon_praise_default);
        }

        viewHolder.reviewReplyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewHolder.replyRelative.setVisibility(View.VISIBLE);

            }
        });

        viewHolder.replyText.setOnClickListener(new View.OnClickListener() {

            private String replyContent;

            @Override
            public void onClick(View v) {
                replyContent = viewHolder.replyEdit.getText().toString();
                if (!userId.equals("") && !sessionId.equals("")) {
                    //Toast.makeText(context, replyContent, Toast.LENGTH_SHORT).show();
                    viewHolder.reviewReplyNum.setText(reviewBeanResult.get(i).getReplyNum());
                    if (TextUtils.isEmpty(replyContent)) {
                        Toast.makeText(context, "请输入回复内容", Toast.LENGTH_SHORT).show();
                    } else {
                        String replyNum = reviewBeanResult.get(i).getReplyNum();
                        int integer = Integer.parseInt(replyNum);
                        integer++;
                        viewHolder.reviewReplyNum.setText(integer + "");
                        viewHolder.replyRelative.setVisibility(View.GONE);
                        viewHolder.replyEdit.setText("");

                    }
                }
                replyClickListener.replyClick(reviewBeanResult.get(i).getCommentId(), replyContent);
                notifyDataSetChanged();

            }
        });


        viewHolder.reviewPriseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnPriaseListener != null) {
                    if (reviewBeanResult.get(i).getIsGreat().equals("0")) {
                        viewHolder.reviewPriseImg.setImageResource(R.mipmap.com_icon_praise_default);
                    } else {
                        viewHolder.reviewPriseImg.setImageResource(R.mipmap.com_icon_praise_selected);
                    }
                    if (!userId.equals("") && !sessionId.equals("")) {
                        viewHolder.reviewPriseImg.setImageResource(R.mipmap.com_icon_praise_selected);
                        if (reviewBeanResult.get(i).getIsGreat().equals("0")) {
                            String greatNum = reviewBeanResult.get(i).getGreatNum();
                            int i1 = Integer.parseInt(greatNum);
                            i1++;
                            viewHolder.reviewPriseNum.setText(String.valueOf(i1));
                        }
                    } else {
                        viewHolder.reviewPriseImg.setImageResource(R.mipmap.com_icon_praise_default);
                    }
                    btnPriaseListener.praiseBtn(reviewBeanResult.get(i).getCommentId(), reviewBeanResult.get(i).getIsGreat());
                }
            }
        });
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
        @BindView(R.id.reply_edit)
        EditText replyEdit;
        @BindView(R.id.reply_text)
        TextView replyText;
        @BindView(R.id.reply_relative)
        RelativeLayout replyRelative;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setBtnPriaseListener(BtnPriaseListener btnPriaseListener) {
        this.btnPriaseListener = btnPriaseListener;
    }

    public interface BtnPriaseListener {
        void praiseBtn(String commentId, String isGreate);
    }

    private ReplyClickListener replyClickListener;

    public void setReplyClickListener(ReplyClickListener replyClickListener) {
        this.replyClickListener = replyClickListener;
    }

    public interface ReplyClickListener {
        void replyClick(String commentId, String replyContent);
    }
}
