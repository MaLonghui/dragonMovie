package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.bean.FilmDetailsBean;
import com.bw.movie.net.NetWorkUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyJuZhaoAdapter extends RecyclerView.Adapter<MyJuZhaoAdapter.ViewHolder> {

    private Context context;
    private FilmDetailsBean.ResultBean resultBean;

    public MyJuZhaoAdapter(Context context, FilmDetailsBean.ResultBean resultBean) {
        this.context = context;
        this.resultBean = resultBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.details_juzhao_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (NetWorkUtils.isNetworkAvailable(context)) {
            Uri uri = Uri.parse(resultBean.getPosterList().get(i));
            viewHolder.juzhaoSimpleView.setImageURI(uri);
        }
    }

    @Override
    public int getItemCount() {
        return resultBean.getPosterList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.juzhao_simple_view)
        SimpleDraweeView juzhaoSimpleView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
