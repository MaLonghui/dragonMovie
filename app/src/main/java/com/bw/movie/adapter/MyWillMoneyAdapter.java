package com.bw.movie.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.login.LoginActivity;
import com.bw.movie.bean.TicketBean;
import com.umeng.analytics.MobclickAgent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWillMoneyAdapter extends RecyclerView.Adapter<MyWillMoneyAdapter.ViewHolder> {
    Context context;
    TicketBean ticketBean;
    private Date date1;
    private Date date2;


    public MyWillMoneyAdapter(Context context, TicketBean ticketBean) {
        this.context = context;
        this.ticketBean = ticketBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.willmoney_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        //电影名
        viewHolder.willTitle.setText(ticketBean.getResult().get(i).getMovieName());
        viewHolder.willCode.setText("订单号："+ticketBean.getResult().get(i).getOrderId());
        viewHolder.willCinema.setText("影院："+ticketBean.getResult().get(i).getCinemaName());
        viewHolder.willHall.setText("影厅："+ticketBean.getResult().get(i).getScreeningHall());
        Date date = new Date(ticketBean.getResult().get(i).getCreateTime());
        SimpleDateFormat sd = new SimpleDateFormat("yy-MM-dd hh:mm");
        String format = sd.format(date);

        viewHolder.willTime.setText("时间："+format);
        viewHolder.willAmount.setText("数量："+ticketBean.getResult().get(i).getAmount());
        final double price = ticketBean.getResult().get(i).getPrice();
        int amoung = Integer.parseInt(ticketBean.getResult().get(i).getAmount());
        viewHolder.willPrice.setText("金额："+price*amoung);
        viewHolder.willButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mWaitCallBack!=null){
                    mWaitCallBack.waitcallback(price,i);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return ticketBean.getResult().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.will_title)
        TextView willTitle;
        @BindView(R.id.will_butt)
        Button willButt;
        @BindView(R.id.will_code)
        TextView willCode;
        @BindView(R.id.will_cinema)
        TextView willCinema;
        @BindView(R.id.will_hall)
        TextView willHall;
        @BindView(R.id.will_time)
        TextView willTime;
        @BindView(R.id.will_amount)
        TextView willAmount;
        @BindView(R.id.will_price)
        TextView willPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public interface WaitCallBack{
        void waitcallback(double price,int position);
    }
    public WaitCallBack mWaitCallBack;

    public void setWaitCallBack(WaitCallBack waitCallBack) {
        mWaitCallBack = waitCallBack;
    }
}
