package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.TicketBean;
import com.bw.movie.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAlreadyMoneyAdapter extends RecyclerView.Adapter<MyAlreadyMoneyAdapter.ViewHolder> {
    Context context;
    TicketBean ticketBean;


    public MyAlreadyMoneyAdapter(Context context, TicketBean ticketBean) {
        this.context = context;
        this.ticketBean = ticketBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.alreadymoney_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //标题
        viewHolder.finshTitle.setText(ticketBean.getResult().get(i).getMovieName());
        //播放时间
        String beginTime = ticketBean.getResult().get(i).getBegintime();
        String endTime = ticketBean.getResult().get(i).getEndTime();
        String date = DateUtils.getDateToStrings(ticketBean.getResult().get(i).getCreateTime());
        viewHolder.finshCode.setText("订单号："+ticketBean.getResult().get(i).getOrderId());
        viewHolder.finshCinema.setText("影院："+ticketBean.getResult().get(i).getCinemaName());
        viewHolder.finshOrdertime.setText("下单时间："+date);
        viewHolder.finshHall.setText("影厅："+ticketBean.getResult().get(i).getScreeningHall());
        viewHolder.finshAmount.setText("数量："+ticketBean.getResult().get(i).getAmount());
        viewHolder.finshPrice.setText("金额："+ticketBean.getResult().get(i).getPrice());
    }

    @Override
    public int getItemCount() {
        return ticketBean.getResult().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.finsh_title)
        TextView finshTitle;
        @BindView(R.id.finsh_code)
        TextView finshCode;
        @BindView(R.id.finsh_ordertime)
        TextView finshOrdertime;
        @BindView(R.id.finsh_cinema)
        TextView finshCinema;
        @BindView(R.id.finsh_hall)
        TextView finshHall;
        @BindView(R.id.finsh_amount)
        TextView finshAmount;
        @BindView(R.id.finsh_price)
        TextView finshPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
