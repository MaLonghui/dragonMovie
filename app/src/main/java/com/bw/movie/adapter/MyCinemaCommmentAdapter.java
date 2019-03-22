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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.CinemaCommentBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCinemaCommmentAdapter extends RecyclerView.Adapter<MyCinemaCommmentAdapter.ViewHolder> {
    Context context;
    CinemaCommentBean cinemaCommentBean;
    private BtnPriaseListener btnPriaseListener;
    private String userId;
    private String sessionId;

    public MyCinemaCommmentAdapter(Context context) {
        this.context = context;
    }

    public void setList(CinemaCommentBean cinemaCommentBean) {
        this.cinemaCommentBean = cinemaCommentBean;
        notifyDataSetChanged();
    }
    public void setID(String userId, String sessionId) {
        this.userId = userId;
        this.sessionId = sessionId;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cinemacomment_layou, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Uri uri = Uri.parse(cinemaCommentBean.getResult().get(i).getCommentHeadPic());
        viewHolder.simPleCinemaComment.setImageURI(uri);
        viewHolder.textNameCinemaComment.setText(cinemaCommentBean.getResult().get(i).getCommentUserName());
        viewHolder.textContentCinemaComment.setText(cinemaCommentBean.getResult().get(i).getCommentContent());
        Date date = new Date(cinemaCommentBean.getResult().get(i).getCommentTime());
        SimpleDateFormat sd = new SimpleDateFormat("MM-dd hh:mm");
        String format = sd.format(date);
        viewHolder.textDateCinemaComment.setText(format);
        viewHolder.textPraiseNum.setText(cinemaCommentBean.getResult().get(i).getGreatNum());

        if (cinemaCommentBean.getResult().get(i).getIsGreat().equals("0")){
            viewHolder.btnPraiseCinema.setImageResource(R.mipmap.com_icon_praise_default);
        }else{
            viewHolder.btnPraiseCinema.setImageResource(R.mipmap.com_icon_praise_selected);
        }
        viewHolder.btnPraiseCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnPriaseListener!=null){
                    if (cinemaCommentBean.getResult().get(i).getIsGreat().equals("0")){
                        viewHolder.btnPraiseCinema.setImageResource(R.mipmap.com_icon_praise_default);
                    }else{
                        viewHolder.btnPraiseCinema.setImageResource(R.mipmap.com_icon_praise_selected);
                    }
                    if (!userId.equals("")&&!sessionId.equals("")){
                        viewHolder.btnPraiseCinema.setImageResource(R.mipmap.com_icon_praise_selected);
                        if (cinemaCommentBean.getResult().get(i).getIsGreat().equals("0")){
                            String greatNum = cinemaCommentBean.getResult().get(i).getGreatNum();
                            int i1 = Integer.parseInt(greatNum);
                            i1++;
                            viewHolder.textPraiseNum.setText(String.valueOf(i1));
                        }
                    }else{
                        viewHolder.btnPraiseCinema.setImageResource(R.mipmap.com_icon_praise_default);
                    }
                    btnPriaseListener.praiseBtn(cinemaCommentBean.getResult().get(i).getCommentId(),cinemaCommentBean.getResult().get(i).getIsGreat());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cinemaCommentBean.getResult().size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.simPle_cinema_comment)
        SimpleDraweeView simPleCinemaComment;
        @BindView(R.id.text_name_cinema_comment)
        TextView textNameCinemaComment;
        @BindView(R.id.text_content_cinema_comment)
        TextView textContentCinemaComment;
        @BindView(R.id.text_date_cinema_comment)
        TextView textDateCinemaComment;
        @BindView(R.id.btn_praise_cinema)
        ImageView btnPraiseCinema;
        @BindView(R.id.text_praise_num)
        TextView textPraiseNum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public void setListener(BtnPriaseListener btnPriaseListener){
        this.btnPriaseListener = btnPriaseListener;
    }

    public interface BtnPriaseListener{
        void praiseBtn(String commentId,String isGreate);
    }
}
