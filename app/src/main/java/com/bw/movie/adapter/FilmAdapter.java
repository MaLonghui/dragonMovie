package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.filmsearch.FilmSearchActivity;
import com.bw.movie.bean.JiFilmBean;
import com.bw.movie.bean.ReFilmBean;
import com.bw.movie.bean.ShangFilmBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import recycler.coverflow.RecyclerCoverFlow;

public class FilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPEONE = 0;
    public static final int TYPETWO = 1;
    public static final int TYPETHREE = 2;


    private Context context;
    private List<ReFilmBean.ResultBean> reFilmBeanResult;
    private List<ShangFilmBean.ResultBean> shangFilmBeanResult;
    private List<JiFilmBean.ResultBean> jiFilmBeanResult;
    private String s;

    public FilmAdapter(Context context) {
        this.context = context;
        reFilmBeanResult = new ArrayList<>();
        shangFilmBeanResult = new ArrayList<>();
        jiFilmBeanResult = new ArrayList<>();
    }

    public void setReFilmBeanResult(List<ReFilmBean.ResultBean> reFilmBeanResult) {
        this.reFilmBeanResult = reFilmBeanResult;
        notifyDataSetChanged();
    }

    public void setShangFilmBeanResult(List<ShangFilmBean.ResultBean> shangFilmBeanResult) {
        this.shangFilmBeanResult = shangFilmBeanResult;
        notifyDataSetChanged();
    }

    public void setJiFilmBeanResult(List<JiFilmBean.ResultBean> jiFilmBeanResult) {
        this.jiFilmBeanResult = jiFilmBeanResult;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPEONE) {
            return new ItemOneViewHoder(LayoutInflater.from(context).inflate(R.layout.film_item_one_layout, parent, false));
        } else if (viewType == TYPETWO) {
            return new ItemTwoViewHolder(LayoutInflater.from(context).inflate(R.layout.film_item_layout, parent, false));
        } else if (viewType == TYPETHREE) {
            return new ItemThreeViewHolder(LayoutInflater.from(context).inflate(R.layout.film_item_layout, parent, false));
        }
        return new ItemFourViewHolder(LayoutInflater.from(context).inflate(R.layout.film_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemOneViewHoder) {
            ((ItemOneViewHoder) holder).recyclerFlow.setAdapter(new FlowAdapter(context));


        }


        if (holder instanceof ItemTwoViewHolder) {
            ((ItemTwoViewHolder) holder).filmTitle.setText("热门电影");
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
            ((ItemTwoViewHolder) holder).filmItemRecyclerView.setLayoutManager(linearLayoutManager);
            if (reFilmBeanResult.size() != 0) {
                ReRecylerAdapter reRecylerAdapter = new ReRecylerAdapter(context, reFilmBeanResult);
                ((ItemTwoViewHolder) holder).filmItemRecyclerView.setAdapter(reRecylerAdapter);
            }
            //点击进入电影详情
            ((ItemTwoViewHolder) holder).flimImgNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("reBeanList", (Serializable) reFilmBeanResult);
                    bundle.putSerializable("shangBeanList", (Serializable) shangFilmBeanResult);
                    bundle.putSerializable("jiBeanList", (Serializable) jiFilmBeanResult);
                    Intent intent = new Intent(context, FilmSearchActivity.class);
                    intent.putExtras(bundle);
                    intent.putExtra("type", 0);
                    context.startActivity(intent);


                }
            });
        }
        if (holder instanceof ItemThreeViewHolder) {
            ((ItemThreeViewHolder) holder).filmTitle.setText("正在热映");
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
            ((ItemThreeViewHolder) holder).filmItemRecyclerView.setLayoutManager(linearLayoutManager);
            if (shangFilmBeanResult.size() != 0) {
                ZhengRecyclerAdapter zhengRecyclerAdapter = new ZhengRecyclerAdapter(context, shangFilmBeanResult);
                ((ItemThreeViewHolder) holder).filmItemRecyclerView.setAdapter(zhengRecyclerAdapter);
            }
            ((ItemThreeViewHolder) holder).flimImgNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("reBeanList", (Serializable) reFilmBeanResult);
                    bundle.putSerializable("shangBeanList", (Serializable) shangFilmBeanResult);
                    bundle.putSerializable("jiBeanList", (Serializable) jiFilmBeanResult);
                    Intent intent = new Intent(context, FilmSearchActivity.class);
                    intent.putExtras(bundle);
                    intent.putExtra("type", 1);
                    context.startActivity(intent);
                }
            });
        }
        if (holder instanceof ItemFourViewHolder) {
            ((ItemFourViewHolder) holder).filmTitle.setText("即将上映");
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
            ((ItemFourViewHolder) holder).filmItemRecyclerView.setLayoutManager(linearLayoutManager);
            if (jiFilmBeanResult.size() != 0) {
                JiRecyclerAdapter jiRecyclerAdapter = new JiRecyclerAdapter(context, jiFilmBeanResult);
                ((ItemFourViewHolder) holder).filmItemRecyclerView.setAdapter(jiRecyclerAdapter);
            }
            ((ItemFourViewHolder) holder).flimImgNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("reBeanList", (Serializable) reFilmBeanResult);
                    bundle.putSerializable("shangBeanList", (Serializable) shangFilmBeanResult);
                    bundle.putSerializable("jiBeanList", (Serializable) jiFilmBeanResult);
                    Intent intent = new Intent(context, FilmSearchActivity.class);
                    intent.putExtras(bundle);
                    intent.putExtra("type", 2);
                    context.startActivity(intent);
                }
            });
        }
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public int getItemCount() {
        return 4;
    }


    @Override
    public int getItemViewType(int position) {
        return position % 4;
    }

    public class ItemOneViewHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.recycler_flow)
        RecyclerCoverFlow recyclerFlow;


        public ItemOneViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ItemTwoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.flim_img_next)
        ImageView flimImgNext;
        @BindView(R.id.film_item_recycler_view)
        RecyclerView filmItemRecyclerView;
        @BindView(R.id.film_title)
        TextView filmTitle;

        public ItemTwoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ItemThreeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.flim_img_next)
        ImageView flimImgNext;
        @BindView(R.id.film_item_recycler_view)
        RecyclerView filmItemRecyclerView;
        @BindView(R.id.film_title)
        TextView filmTitle;

        public ItemThreeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ItemFourViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.flim_img_next)
        ImageView flimImgNext;
        @BindView(R.id.film_item_recycler_view)
        RecyclerView filmItemRecyclerView;
        @BindView(R.id.film_title)
        TextView filmTitle;

        public ItemFourViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnSearchClick onSearchClick;

    public void setOnSearchClick(OnSearchClick onSearchClick) {
        this.onSearchClick = onSearchClick;
    }

    public interface OnSearchClick{
        void searchClick(View view,String s);
        void textClick(String s);
    }
}
