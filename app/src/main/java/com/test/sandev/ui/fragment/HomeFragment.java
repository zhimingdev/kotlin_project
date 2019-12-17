package com.test.sandev.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Network;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.test.sandev.R;
import com.test.sandev.adapter.HomeAdapter;
import com.test.sandev.api.Api;
import com.test.sandev.base.BaseFragment;
import com.test.sandev.module.*;
import com.test.sandev.ui.activity.WebActivity;
import com.test.sandev.utils.ApiBaseResponse;
import com.test.sandev.utils.NetWork;
import com.test.sandev.utils.UpdateDialog;
import com.test.sandev.utils.UsualDialogger;
import com.test.sandev.adapter.HomeAdapter;
import com.test.sandev.api.Api;
import com.test.sandev.base.BaseFragment;
import com.test.sandev.module.*;
import com.test.sandev.ui.activity.WebActivity;
import com.test.sandev.utils.ApiBaseResponse;
import com.test.sandev.utils.NetWork;
import com.test.sandev.utils.UpdateDialog;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends BaseFragment {

    private MZBannerView bannerView;
    private List<BannerBean> list;
    private RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    private String customurl;
    private TextView textView;
    private UpdateDialog dialogger;
    private NetWork netWork;

    @NotNull
    @Override
    public View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list,null);
        bannerView = view.findViewById(R.id.banner);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        textView = (TextView) view.findViewById(R.id.tv_url);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerView.start();
    }

    @Override
    protected void initDate() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        netWork = new NetWork();
        netWork.getApi(Api.class).getUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiBaseResponse<UpdateModule>(getActivity()) {
                    @Override
                    public void onFail(@NotNull ApiError e) {

                    }

                    @Override
                    public void onCodeError(@NotNull BaseResponse<?> tBaseReponse) {

                    }

                    @Override
                    public void onSuccess(@Nullable UpdateModule updateModule) {
                        if (updateModule.getIsupdate()) {
                            showdialog(updateModule.getWeburl());
                        }
                    }
                });
        loadData();
    }

    private void loadData() {
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
                        customurl = homeBean.getCustomurl();
                        if (homeAdapter == null) {
                            homeAdapter = new HomeAdapter(getContext(),homeBean.getDatas());
                        }
                        recyclerView.setAdapter(homeAdapter);
                    }
                });
    }

    public void showdialog (final String url) {
        dialogger = UpdateDialog.Builder(getActivity())
                .setOnCancelClickListener("取消",new UpdateDialog.onCancelClickListener(){
                    @Override
                    public void onClick(View view) {
                        dialogger.dismiss();
                        Intent intent = new Intent(getContext(), WebActivity.class);
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
                startActivity(intent);
            }
        }).build().shown();
    }

    @Override
    protected void initListenter() {
        bannerView.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                BannerBean bannerBean = list.get(i);
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url",bannerBean.getUrl());
                startActivity(intent);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra("url",customurl);
                startActivity(intent);
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            loadData();
        }
    }
}
