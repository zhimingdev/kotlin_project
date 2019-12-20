package com.test.sandev.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Network;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.test.sandev.R;
import com.test.sandev.adapter.HomeAdapter;
import com.test.sandev.api.Api;
import com.test.sandev.base.BaseFragment;
import com.test.sandev.module.*;
import com.test.sandev.ui.activity.KeFuActivty;
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
    private TabLayout tabLayout;
    private List<String> items = new ArrayList<>();
    private CommonFragment mHomeFragment;
    private CommonFragment mUploadFragment;
    private FragmentTransaction transaction;

    @NotNull
    @Override
    public View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list,null);
        bannerView = view.findViewById(R.id.banner);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_go_kefu);
        tabLayout = (TabLayout) view.findViewById(R.id.tab);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerView.start();
    }

    @Override
    protected void initSandevDate() {
        items.clear();
        items.add("推荐");
        items.add("广场");
        netWork = new NetWork();
        loadData();
        for (String item : items) {
            tabLayout.addTab(tabLayout.newTab().setText(item));
        }
        initFragmentReplace();
    }

    private void initFragmentReplace() {
        // 获取到fragment碎片管理器
        FragmentManager manager = getActivity().getSupportFragmentManager();
        // 开启事务
        transaction = manager.beginTransaction();

        // 获取到fragment的对象
        mHomeFragment = CommonFragment.Companion.getInstanca(0);
        mUploadFragment = CommonFragment.Companion.getInstanca(1);

        // 设置要显示的fragment 和 影藏的fragment
        transaction.add(R.id.fl_main, mHomeFragment, "home").show(mHomeFragment);
        transaction.add(R.id.fl_main, mUploadFragment, "upload").hide(mUploadFragment);

        // 提交事务
        transaction.commit();

    }


    private void loadData() {
//        netWork.getApi(Api.class).getBanner()
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

        netWork.getApi(Api.class).getVV()
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

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.show(mHomeFragment).hide(mUploadFragment).commit();
                }else {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.show(mUploadFragment).hide(mHomeFragment).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
