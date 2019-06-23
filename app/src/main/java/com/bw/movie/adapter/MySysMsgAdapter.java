package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.NetActivity;
import com.bw.movie.bean.SysMsgBean;
import com.bw.movie.net.NetWorkUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MySysMsgAdapter extends RecyclerView.Adapter<MySysMsgAdapter.ViewHolder> {
    Context context;
    SysMsgBean sysMsgBean;
    private SysMsgStatusClick sysMsgStatusClick;


    public MySysMsgAdapter(Context context) {
        this.context = context;
    }

    public void setList(SysMsgBean sysMsgBean) {
        this.sysMsgBean = sysMsgBean;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_xrecycler_remind_mine, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        if (NetWorkUtils.isNetworkAvailable(context)) {
            viewHolder.textTitleRemindMine.setText(sysMsgBean.getResult().get(i).getTitle());
            viewHolder.textContextRemindMine.setText(sysMsgBean.getResult().get(i).getContent());
            Date date = new Date(sysMsgBean.getResult().get(i).getPushTime());
            SimpleDateFormat sd = new SimpleDateFormat("MM-dd hh:mm");
            String format = sd.format(date);
            viewHolder.textTimeRemindMine.setText(format);
            if (sysMsgBean.getResult().get(i).getStatus().equals("0")) {
                viewHolder.picStatusRemindMine.setVisibility(View.VISIBLE);
            } else {
                viewHolder.picStatusRemindMine.setVisibility(View.GONE);
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetWorkUtils.isNetworkAvailable(context)) {
                        if (sysMsgStatusClick != null) {
                            sysMsgStatusClick.statusClick(sysMsgBean.getResult().get(i).getId());
                        }
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
        return sysMsgBean.getResult().size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title_remind_mine)
        TextView textTitleRemindMine;
        @BindView(R.id.text_context_remind_mine)
        TextView textContextRemindMine;
        @BindView(R.id.text_time_remind_mine)
        TextView textTimeRemindMine;
        @BindView(R.id.pic_status_remind_mine)
        ImageButton picStatusRemindMine;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setStatusList(SysMsgStatusClick sysMsgStatusClick){
        this.sysMsgStatusClick = sysMsgStatusClick;
    }

    public interface SysMsgStatusClick{
        void statusClick(String id);
    }
}
