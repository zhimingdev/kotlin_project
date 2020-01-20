package com.test.sandev.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Network;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.squareup.picasso.Picasso;
import com.test.sandev.R;
import com.test.sandev.adapter.HomeAdapter;
import com.test.sandev.api.Api;
import com.test.sandev.base.BaseFragment;
import com.test.sandev.module.*;
import com.test.sandev.ui.activity.HotMatcher;
import com.test.sandev.ui.activity.InfoActivty;
import com.test.sandev.ui.activity.KeFuActivty;
import com.test.sandev.ui.activity.LoginActivity;
import com.test.sandev.ui.activity.MatcherDetailActivity;
import com.test.sandev.ui.activity.NewWeb;
import com.test.sandev.ui.activity.SearchActivity;
import com.test.sandev.ui.activity.WebActivity;
import com.test.sandev.ui.activity.YuCeActivity;
import com.test.sandev.utils.ApiBaseResponse;
import com.test.sandev.utils.CircleTransform;
import com.test.sandev.utils.NetWork;
import com.test.sandev.utils.UpdateDialog;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends BaseFragment {

    private MZBannerView bannerView;
    private List<BannerBean> list;
    private UpdateDialog dialogger;
    private NetWork netWork;
    private LinearLayout linearLayout;
    private PieChart pieChart;
    private BarChart barChart;
    private TextView tv_one;
    private TextView tv_two;
    private LinearLayout linearLayout1;
    private LinearLayout llmore;
    private LinearLayout infomore,rl_news;
    private RelativeLayout news;
    private RecyclerView recyclerView;
    private CardView cdone,cdtwo,cdthree;
    private RelativeLayout relativeLayout;
    private ImageView search;
    private Toolbar toolbar;
    private NestedScrollView nestedScrollView;

    @NotNull
    @Override
    public View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list,null);
        bannerView = view.findViewById(R.id.banner);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_go_kefu);
        pieChart = (PieChart) view.findViewById(R.id.chart1);
        barChart = (BarChart) view.findViewById(R.id.chart);
        tv_one = (TextView) view.findViewById(R.id.tv_title_one);
        tv_two = (TextView) view.findViewById(R.id.tv_title_two);
        linearLayout1 = (LinearLayout) view.findViewById(R.id.ll_hot);
        llmore = (LinearLayout) view.findViewById(R.id.rl_more);
        infomore = (LinearLayout) view.findViewById(R.id.ll_info_more);
        rl_news = (LinearLayout) view.findViewById(R.id.rl_news);
        news = (RelativeLayout) view.findViewById(R.id.rl_one);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_home);
        cdone = (CardView) view.findViewById(R.id.cd_one);
        cdtwo = (CardView) view.findViewById(R.id.cd_two);
        cdthree = (CardView) view.findViewById(R.id.cd_three);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.rl_search);
        search = (ImageView) view.findViewById(R.id.iv_search);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.home_ns);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerView.start();
    }

    @Override
    protected void initSandevDate() {
        netWork = new NetWork();
        loadData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    private void loadData() {
        netWork.getApi(Api.class).getHot()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiBaseResponse<List<HotBean>>(getActivity()) {
                    @Override
                    public void onFail(@NotNull ApiError e) {

                    }

                    @Override
                    public void onCodeError(@NotNull BaseResponse<?> tBaseReponse) {

                    }

                    @Override
                    public void onSuccess(@Nullable List<HotBean> hotBeans) {
                        show(hotBeans);
                    }
                });

        netWork.getApi(Api.class).getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiBaseResponse<List<BannerBean>>(getActivity()) {
                    @Override
                    public void onFail(@NotNull ApiError e) {}

                    @Override
                    public void onCodeError(@NotNull BaseResponse<?> tBaseReponse) {}

                    @Override
                    public void onSuccess(@Nullable List<BannerBean> bannerBeans) {
                        list = bannerBeans;
                        bannerView.setPages(list, new MZHolderCreator<BannerViewHolder>() {
                            @Override
                            public BannerViewHolder createViewHolder() {
                                return new BannerViewHolder();
                            }
                        });
                        bannerView.start();
                    }
                });

        netWork.getApi(Api.class).getHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiBaseResponse<HomeBean>(getActivity()) {
                    @Override
                    public void onFail(@NotNull ApiError e) {

                    }

                    @Override
                    public void onCodeError(@NotNull BaseResponse<?> tBaseReponse) {

                    }

                    @Override
                    public void onSuccess(@Nullable HomeBean homeBean) {
                        HomeAdapter adapter = new HomeAdapter(getActivity(),homeBean.getDatas());
                        recyclerView.setAdapter(adapter);
                    }
                });

//        netWork.getApi(Api.class).getVV()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ApiBaseResponse<List<BannerBean>>(getActivity()) {
//                    @Override
//                    public void onFail(@NotNull ApiError e) {}
//
//                    @Override
//                    public void onCodeError(@NotNull BaseResponse<?> tBaseReponse) {}
//
//                    @Override
//                    public void onSuccess(@Nullable List<BannerBean> bannerBeans) {
//                        list = bannerBeans;
//                        bannerView.setPages(list, new MZHolderCreator<BannerViewHolder>() {
//                            @Override
//                            public BannerViewHolder createViewHolder() {
//                                return new BannerViewHolder();
//                            }
//                        });
//                        bannerView.start();
//                    }
//                });
    }

    private void show(List<HotBean> hotBeans) {
        for (int i = 0;i<hotBeans.size();i++){
            final HotBean hotBean = hotBeans.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_hot, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 40;
            params.width = (int) ((Integer)ScreenUtils.getScreenWidth()*0.8);
            view.setLayoutParams(params);
            TextView tv_time = (TextView) view.findViewById(R.id.tv_hot_time);
            TextView tv_type = (TextView) view.findViewById(R.id.tv_hot_type);
            ImageView iv_host = (ImageView) view.findViewById(R.id.iv_hot_host);
            TextView tv_host = (TextView) view.findViewById(R.id.tv_hot_host);
            ImageView iv_guest = (ImageView) view.findViewById(R.id.iv_hot_guest);
            TextView tv_guest = (TextView) view.findViewById(R.id.tv_hot_guest);
            final int position = i;
            ((CardView) view.findViewById(R.id.cd_item)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent = new Intent(getContext(), MatcherDetailActivity.class);
                   intent.putExtra("host",hotBean.getHostteam());
                   intent.putExtra("hostname",hotBean.getHostname());
                   intent.putExtra("guest",hotBean.getGuestteam());
                   intent.putExtra("guestname",hotBean.getGuestname());
                   intent.putExtra("type",hotBean.getType());
                   intent.putExtra("time",hotBean.getTypetime());
                   intent.putExtra("position", position+"");
                   intent.putExtra("bifen", hotBean.getBifen());
                   startActivity(intent);
                }
            });

            tv_time.setText(hotBean.getTypetime());
            tv_type.setText(hotBean.getType());
            Picasso.with(getContext()).load(hotBean.getHostteam())
                    .transform(new CircleTransform())
                    .into(iv_host);
            tv_host.setText(hotBean.getHostname());
            Picasso.with(getContext()).load(hotBean.getGuestteam())
                    .transform(new CircleTransform())
                    .into(iv_guest);
            tv_guest.setText(hotBean.getGuestname());
            linearLayout1.addView(view);
        }
    }

    public void showdialog(final String url, final String fromwhich) {
        dialogger = UpdateDialog.Builder(getActivity())
                .setOnCancelClickListener("取消",new UpdateDialog.onCancelClickListener(){
                    @Override
                    public void onClick(View view) {
                        dialogger.dismiss();
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("fromwhicih",fromwhich);
                        intent.putExtra("url",url);
                        startActivity(intent);
                    }
                })
        .setOnConfirmClickListener("更新",new UpdateDialog.onConfirmClickListener(){
            @Override
            public void onClick(View view) {
                dialogger.dismiss();
                Intent intent = new Intent(getContext(),WebActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("fromwhicih",fromwhich);
                startActivity(intent);
            }
        }).build().shown();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initSandevListenter() {
        bannerView.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                BannerBean bannerBean = list.get(i);
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url",bannerBean.getUrl());
                startActivity(intent);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KeFuActivty.class);
                startActivity(intent);
            }
        });
        llmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HotMatcher.class);
                startActivity(intent);
            }
        });

        infomore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InfoActivty.class);
                startActivity(intent);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url","https://live.qq.com/news/20200199315901.html");
                startActivity(intent);
            }
        });

        rl_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("news"));
            }
        });

        cdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int loginid = SPUtils.getInstance().getInt("loginid");
                if (loginid != -1) {
                    Intent intent = new Intent(getActivity(), YuCeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        cdtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int loginid = SPUtils.getInstance().getInt("loginid");
                if (loginid != -1) {
                    Intent intent = new Intent(getActivity(), NewWeb.class);
                    intent.putExtra("url","http://hbimg.huabanimg.com/9ad42c33b4e1d36a3230d9a93ef93af25fa5ae43bc5d9-Q5xI6Q_fw658");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        cdthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int loginid = SPUtils.getInstance().getInt("loginid");
                if (loginid != -1) {
                    Intent intent = new Intent(getActivity(), NoFragment.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), search, search.getTransitionName());
                    startActivity(new Intent(getActivity(), SearchActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(getActivity(), SearchActivity.class));
                }
            }
        });

        nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                float height = bannerView.getHeight();  //获取图片的高度
                if (scrollY < height){
                    System.out.println("===阻尼 "+scrollY/height);
                    toolbar.setAlpha(scrollY/height);   // 0~255 透明度
                }else if (scrollY == 0){
                    toolbar.setAlpha(0);
                }else {
                    toolbar.setAlpha(1);
                }
            }
        });
    }

    public static class BannerViewHolder implements MZViewHolder<BannerBean> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, BannerBean data) {
            // 数据绑定
            Picasso.with(context).load(data.getImageurl()).into(mImageView);
        }
    }
}
