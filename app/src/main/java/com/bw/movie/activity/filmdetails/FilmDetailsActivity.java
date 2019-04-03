package com.bw.movie.activity.filmdetails;


import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.transition.Explode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.cinemabymovieid.CinemaByMovieIdActivity;
import com.bw.movie.adapter.FilmReviewAdapter;
import com.bw.movie.adapter.MyJuZhaoAdapter;
import com.bw.movie.adapter.MyPopwindowAdapter;
import com.bw.movie.bean.CancelFollowMovieBean;
import com.bw.movie.bean.CinemaPraiseBean;
import com.bw.movie.bean.FilmCommentBean;
import com.bw.movie.bean.FilmDetailsBean;
import com.bw.movie.bean.FilmReviewBean;
import com.bw.movie.bean.FlowllMovieBean;
import com.bw.movie.bean.MovieCommentReply;
import com.bw.movie.mvp.MVPBaseActivity;
import com.bw.movie.utils.AlertDialogUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FilmDetailsActivity extends MVPBaseActivity<FilmDetailsContract.View, FilmDetailsPresenter> implements FilmDetailsContract.View {
    @BindView(R.id.detail_title_icon)
    ImageView detailTitleIcon;
    @BindView(R.id.detail_prise)
    ImageView detailPrise;
    @BindView(R.id.detail_name)
    TextView detailName;
    @BindView(R.id.details_simple_view)
    SimpleDraweeView detailsSimpleView;
    @BindView(R.id.detail_btn_detail)
    TextView detailBtnDetail;
    @BindView(R.id.detail_btn_prevue)
    TextView detailBtnPrevue;
    @BindView(R.id.detail_btn_still)
    TextView detailBtnStill;
    @BindView(R.id.detail_btn_review)
    TextView detailBtnReview;
    @BindView(R.id.details_return)
    ImageView detailsReturn;
    @BindView(R.id.buy_ticket)
    TextView buyTicket;
    @BindView(R.id.content)
    RelativeLayout content;

    private String movieId = "";
    private int page = 1;
    private int count = 10;
    public static final String TAG = "FilmDetailsActivity";
    private TextView popName;
    private ImageView pop_down;
    private RecyclerView popRecyclerView;
    private FilmDetailsBean.ResultBean resultBean;
    private ImageView writePingunImg;
    private List<FilmReviewBean.ResultBean> reviewBeanResult;
    private RelativeLayout commtenRealtive;
    private EditText commtentEdit;
    private TextView textn_send;
    private FilmDetailsBean filmDetailsBean;
    private String userId;
    private String sessionId;
    private SharedPreferences preferences;
    private HashMap<String, Object> followHeadMap;
    private HashMap<String, Object> canclePrams;
    private FilmReviewAdapter filmReviewAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        userId = preferences.getString("userId", "");
        sessionId = preferences.getString("sessionId", "");
        //参数集合
        HashMap<String, Object> prams = new HashMap<>();
        prams.put("movieId", movieId);
        if (userId.equals("") || sessionId.equals("")) {
            HashMap<String, Object> headMapNull = new HashMap<>();
            mPresenter.getPresenterData(headMapNull, prams);

        } else {
            //电影详情请求头集合
            HashMap<String, Object> headMap = new HashMap<>();
            headMap.put("userId", userId);
            headMap.put("sessionId", sessionId);
            mPresenter.getPresenterData(headMap, prams);
        }
    }


    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_film_details);
        getWindow().setEnterTransition(new Explode().setDuration(800));
        getWindow().setExitTransition(new Explode().setDuration(800));
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        preferences = getSharedPreferences("config", MODE_PRIVATE);
        userId = preferences.getString("userId", "");
        sessionId = preferences.getString("sessionId", "");

        filmReviewAdapter = new FilmReviewAdapter(this);

        //参数集合
        HashMap<String, Object> prams = new HashMap<>();
        prams.put("movieId", movieId);
        if (userId.equals("") || sessionId.equals("")) {
            HashMap<String, Object> headMapNull = new HashMap<>();
                mPresenter.getPresenterData(headMapNull, prams);

        } else {
            //电影详情请求头集合
            HashMap<String, Object> headMap = new HashMap<>();
            headMap.put("userId", userId);
            headMap.put("sessionId", sessionId);
            mPresenter.getPresenterData(headMap, prams);

        }

        //参数集合
        HashMap<String, Object> reviewPrams = new HashMap<>();
        reviewPrams.put("movieId", movieId);
        reviewPrams.put("page", page);
        reviewPrams.put("count", count);
        if (userId.equals("") || sessionId.equals("")) {
            HashMap<String, Object> headMapNull = new HashMap<>();
            mPresenter.getReviewPresenterData(headMapNull, reviewPrams);
        } else {
            //影片评论
            HashMap<String, Object> reviewHeadMap = new HashMap<>();
            reviewHeadMap.put("userId", userId);
            reviewHeadMap.put("sessionId", sessionId);
            mPresenter.getReviewPresenterData(reviewHeadMap, reviewPrams);
        }
        buyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("resultBean", resultBean);
                Intent intent = new Intent(FilmDetailsActivity.this, CinemaByMovieIdActivity.class);
                intent.putExtras(bundle);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(FilmDetailsActivity.this).toBundle());
            }
        });
        followHeadMap = new HashMap<>();
        canclePrams = new HashMap<>();
        canclePrams.put("movieId", movieId);
        followHeadMap.put("userId", userId);
        followHeadMap.put("sessionId", sessionId);
        detailPrise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userId.equals("") || !sessionId.equals("")) {
                    if (resultBean.getFollowMovie() == 1) {
                        resultBean.setFollowMovie(2);
                        detailPrise.setImageResource(R.mipmap.com_icon_collection_default);
                        mPresenter.cancelFollowMoviePresenter(followHeadMap, canclePrams);
                    } else if (resultBean.getFollowMovie() == 2) {
                        resultBean.setFollowMovie(1);
                        detailPrise.setImageResource(R.mipmap.com_icon_collection_selected);
                        mPresenter.getFlowllMoviePresenter(followHeadMap, movieId);
                    }
                } else {
                    AlertDialogUtils.AlertDialogLogin(FilmDetailsActivity.this);
                }
            }
        });


    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEventBus(String id) {
        movieId = id;
    }

    @Override
    public void getFilmDetailsViewData(Object object) {
        if (object != null) {
            filmDetailsBean = (FilmDetailsBean) object;
            resultBean = filmDetailsBean.getResult();
            detailName.setText(resultBean.getName());
            Uri uri = Uri.parse(resultBean.getImageUrl());
            detailsSimpleView.setImageURI(uri);
            if (resultBean.getFollowMovie() == 1) {
                detailPrise.setImageResource(R.mipmap.com_icon_collection_selected);
            } else {
                detailPrise.setImageResource(R.mipmap.com_icon_collection_default);
            }
        }
    }


    @Override
    public void getFilmReviewData(Object o) {
        if (o != null) {
            FilmReviewBean filmReviewBean = (FilmReviewBean) o;
            reviewBeanResult = filmReviewBean.getResult();
            filmReviewAdapter.setList(reviewBeanResult);

        }
    }

    @Override
    public void getFilmCommentData(Object object) {
        if (object != null) {
            FilmCommentBean filmCommentBean = (FilmCommentBean) object;
            if (filmCommentBean.getStatus().equals("0000")) {
                Toast.makeText(this, filmCommentBean.getMessage(), Toast.LENGTH_SHORT).show();
                HashMap<String, Object> reviewHeadMap = new HashMap<>();
                HashMap<String, Object> reviewPrams = new HashMap<>();
                reviewPrams.put("movieId", movieId);
                reviewPrams.put("page", page);
                reviewPrams.put("count", count);
                reviewHeadMap.put("userId", userId);
                reviewHeadMap.put("sessionId", sessionId);
                mPresenter.getReviewPresenterData(reviewHeadMap,reviewPrams);

            } else if (filmCommentBean.getStatus().equals("9999")) {
                AlertDialogUtils.AlertDialogLogin(this);
            }
        }

    }

    @Override
    public void getmovieCommentData(Object object) {
        if (object != null) {
            CinemaPraiseBean cinemaPraiseBean = (CinemaPraiseBean) object;
            if (cinemaPraiseBean.getStatus().equals("9999")) {
                AlertDialogUtils.AlertDialogLogin(this);
            } else if (cinemaPraiseBean.getStatus().equals("0000")) {
                Toast.makeText(this, cinemaPraiseBean.getMessage(), Toast.LENGTH_SHORT).show();
                HashMap<String, Object> reviewHeadMap = new HashMap<>();
                HashMap<String, Object> reviewPrams = new HashMap<>();
                reviewPrams.put("movieId", movieId);
                reviewPrams.put("page", page);
                reviewPrams.put("count", count);
                reviewHeadMap.put("userId", userId);
                reviewHeadMap.put("sessionId", sessionId);
                mPresenter.getReviewPresenterData(reviewHeadMap,reviewPrams);

            }
        }
    }

    @Override
    public void getcommentReplyData(Object object) {
        if (object != null) {
            MovieCommentReply movieCommentReply = (MovieCommentReply) object;
            if (movieCommentReply.getStatus().equals("9999")) {
                AlertDialogUtils.AlertDialogLogin(this);
            } else if (movieCommentReply.getStatus().equals("0000")) {
                Toast.makeText(this, movieCommentReply.getMessage(), Toast.LENGTH_SHORT).show();
                HashMap<String, Object> reviewHeadMap = new HashMap<>();
                HashMap<String, Object> reviewPrams = new HashMap<>();
                reviewPrams.put("movieId", movieId);
                reviewPrams.put("page", page);
                reviewPrams.put("count", count);
                reviewHeadMap.put("userId", userId);
                reviewHeadMap.put("sessionId", sessionId);
                mPresenter.getReviewPresenterData(reviewHeadMap,reviewPrams);
            }
        }
    }

    @Override
    public void getFlowllMovieData(Object object) {
        if (object != null) {
            FlowllMovieBean flowllMovieBean = (FlowllMovieBean) object;
            if (flowllMovieBean.getStatus().equals("0000")) {
                Toast.makeText(this, flowllMovieBean.getMessage(), Toast.LENGTH_SHORT).show();
                HashMap<String, Object> reviewHeadMap = new HashMap<>();
                HashMap<String, Object> reviewPrams = new HashMap<>();
                reviewPrams.put("movieId", movieId);
                reviewPrams.put("page", page);
                reviewPrams.put("count", count);
                reviewHeadMap.put("userId", userId);
                reviewHeadMap.put("sessionId", sessionId);
                mPresenter.getReviewPresenterData(reviewHeadMap,reviewPrams);
            }
        }
    }

    @Override
    public void cancelFollowMovieData(Object object) {
        if (object != null) {
            CancelFollowMovieBean cancelFollowMovieBean = (CancelFollowMovieBean) object;
            if (cancelFollowMovieBean.getStatus().equals("0000")) {
                Toast.makeText(this, cancelFollowMovieBean.getMessage(), Toast.LENGTH_SHORT).show();
                HashMap<String, Object> reviewHeadMap = new HashMap<>();
                HashMap<String, Object> reviewPrams = new HashMap<>();
                reviewPrams.put("movieId", movieId);
                reviewPrams.put("page", page);
                reviewPrams.put("count", count);
                reviewHeadMap.put("userId", userId);
                reviewHeadMap.put("sessionId", sessionId);
                mPresenter.getReviewPresenterData(reviewHeadMap,reviewPrams);
            }
            //Toast.makeText(this, cancelFollowMovieBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.detail_btn_detail, R.id.detail_btn_prevue, R.id.detail_btn_still, R.id.detail_btn_review, R.id.details_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.detail_btn_detail:
                //Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show();
                initSonPopupwindow(view, resultBean);
                break;
            case R.id.detail_btn_prevue:
                //Toast.makeText(this, detailBtnPrevue.getText().toString(), Toast.LENGTH_SHORT).show();
                initPopupwindow(view, detailBtnPrevue.getText().toString(), resultBean);
                break;
            case R.id.detail_btn_still:
                initPopupwindow(view, detailBtnStill.getText().toString(), resultBean);
                break;
            case R.id.detail_btn_review:
                initPopupwindow(view, detailBtnReview.getText().toString(), resultBean);
                break;
            case R.id.details_return:
                onBackPressed();
                break;
        }
    }

    /**
     * 详情pop
     *
     * @param v
     * @param resultBean
     */
    private void initSonPopupwindow(View v, FilmDetailsBean.ResultBean resultBean) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = View.inflate(this, R.layout.details_son_popwindow_layout, null);
        SimpleDraweeView son_simpleview = view.findViewById(R.id.detail_son_simpleview);
        TextView son_type = view.findViewById(R.id.son_type);
        TextView son_director = view.findViewById(R.id.son_director);
        TextView son_timer = view.findViewById(R.id.son_timer);
        TextView son_address = view.findViewById(R.id.son_address);
        TextView text_jianjie = view.findViewById(R.id.text_jianjie);
        ImageView son_popwindow_down = view.findViewById(R.id.son_popwindow_down);
        son_type.setText("类型：" + resultBean.getMovieTypes());
        son_director.setText("导演：" + resultBean.getDirector());
        son_timer.setText("时长：" + resultBean.getDuration());
        son_address.setText("产地：" + resultBean.getPlaceOrigin());
        text_jianjie.setText(resultBean.getSummary());
        Uri uri = Uri.parse(resultBean.getImageUrl());
        son_simpleview.setImageURI(uri);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        son_popwindow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });

    }

    /**
     * 预告片、剧照、评论pop
     *
     * @param v
     * @param string
     * @param resultBean
     */
    private void initPopupwindow(View v, String string, final FilmDetailsBean.ResultBean resultBean) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = View.inflate(this, R.layout.details_popwindow_layout, null);
        popName = view.findViewById(R.id.popwindow_name);
        pop_down = view.findViewById(R.id.popwindow_down);
        popRecyclerView = view.findViewById(R.id.pop_recycler_view);
        writePingunImg = view.findViewById(R.id.write_pinglun_img);
        commtenRealtive = (RelativeLayout) view.findViewById(R.id.comment_relative_layout);
        commtentEdit = (EditText) view.findViewById(R.id.comment_ed_text);
        textn_send = (TextView) view.findViewById(R.id.comment_send);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);



        popName.setText(string);

        if (string.equals("预告片")) {
            writePingunImg.setVisibility(View.GONE);
            //布局管理器
           /* LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);*/
            popRecyclerView.setLayoutManager(linearLayoutManager);
            //设置适配器
            MyPopwindowAdapter popwindowAdapter = new MyPopwindowAdapter(this, resultBean);
            popRecyclerView.setAdapter(popwindowAdapter);
        }
        if (string.equals("剧照")) {
            writePingunImg.setVisibility(View.GONE);
            //布局管理器
            StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            popRecyclerView.setLayoutManager(gridLayoutManager);
            //设置适配器
            MyJuZhaoAdapter juZhaoAdapter = new MyJuZhaoAdapter(this, resultBean);
            popRecyclerView.setAdapter(juZhaoAdapter);
        }
        if (string.equals("影评")) {
            writePingunImg.setVisibility(View.VISIBLE);
            popRecyclerView.setLayoutManager(linearLayoutManager);
            popRecyclerView.setAdapter(filmReviewAdapter);
            filmReviewAdapter.setList(reviewBeanResult);
            filmReviewAdapter.setID(userId, sessionId);
            filmReviewAdapter.setReplyClickListener(new FilmReviewAdapter.ReplyClickListener() {
                @Override
                public void replyClick(String commentId, String replyContent) {
                    if (!userId.equals("") && !sessionId.equals("")) {
                        Map<String, Object> headMap = new HashMap<>();
                        Map<String, Object> prams = new HashMap<>();
                        prams.put("commentId", commentId);
                        prams.put("replyContent",replyContent);
                        headMap.put("userId", userId);
                        headMap.put("sessionId", sessionId);
                        mPresenter.getcommentReplyPresenter(headMap,prams);
                    } else {
                        AlertDialogUtils.AlertDialogLogin(FilmDetailsActivity.this);
                    }

                }
            });
            filmReviewAdapter.setBtnPriaseListener(new FilmReviewAdapter.BtnPriaseListener() {
                @Override
                public void praiseBtn(String commentId, String isGreate) {
                    if (isGreate.equals("0")) {
                        if (!userId.equals("") && !sessionId.equals("")) {
                            Map<String, Object> headMap = new HashMap<>();
                            headMap.put("userId", userId);
                            headMap.put("sessionId", sessionId);
                            mPresenter.getMovieCommentPresenter(headMap,commentId);
                        } else {
                            AlertDialogUtils.AlertDialogLogin(FilmDetailsActivity.this);
                        }
                    } else {
                        Toast.makeText(FilmDetailsActivity.this, "不能重复点赞", Toast.LENGTH_LONG).show();
                    }

                }

            });

            writePingunImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    writePingunImg.setVisibility(View.GONE);
                    commtenRealtive.setVisibility(View.VISIBLE);
                }
            });
            textn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String send = commtentEdit.getText().toString();
                    if (TextUtils.isEmpty(send)) {
                        Toast.makeText(FilmDetailsActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
                        String userId = preferences.getString("userId", "");
                        String sessionId = preferences.getString("sessionId", "");
                        HashMap<String, Object> comHeadMap = new HashMap<>();
                        HashMap<String, Object> comPrams = new HashMap<>();
                        comHeadMap.put("userId", userId);
                        comHeadMap.put("sessionId", sessionId);
                        comPrams.put("movieId", resultBean.getId());
                        comPrams.put("commentContent", send);
                        mPresenter.getFilmCommentPresenter(comHeadMap, comPrams);
                        writePingunImg.setVisibility(View.VISIBLE);
                        commtenRealtive.setVisibility(View.GONE);
                        commtentEdit.setText("");

                    }
                }
            });
        }


        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        /*popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });*/
        pop_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                JCVideoPlayer.releaseAllVideos();
            }
        });
        if (!popupWindow.isShowing()){
            JCVideoPlayer.releaseAllVideos();
        }
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (popupWindow.isShowing()){
                        popupWindow.dismiss();
                        JCVideoPlayer.releaseAllVideos();
                    }

                    return true;
                }
                return false;
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
