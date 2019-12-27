package com.test.sandev.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
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
import com.test.sandev.view.DayAxisValueFormatter;
import com.test.sandev.view.MyAxisValueFormatter;
import com.test.sandev.view.XYMarkerView;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends BaseFragment implements OnChartValueSelectedListener {

    private MZBannerView bannerView;
    private List<BannerBean> list;
    private UpdateDialog dialogger;
    private NetWork netWork;
    private LinearLayout linearLayout;
    private PieChart pieChart;
    private BarChart barChart;

    @NotNull
    @Override
    public View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list,null);
        bannerView = view.findViewById(R.id.banner);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_go_kefu);
        pieChart = (PieChart) view.findViewById(R.id.chart1);
        barChart = (BarChart) view.findViewById(R.id.chart);
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
        initChart();
        initLineraChart();
    }

    private void initLineraChart() {
        barChart.setOnChartValueSelectedListener(this);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(barChart);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(8,false);
        xAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = barChart.getAxisLeft();
        //获取到图形右边的Y轴，并设置为不显示
        barChart.getAxisRight().setEnabled(false);
        leftAxis.setLabelCount(7, true);
        leftAxis.setValueFormatter(custom);
        leftAxis.setAxisMaximum(6);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        setBarData();
        XYMarkerView mv = new XYMarkerView(getContext(), xAxisFormatter);
        mv.setChartView(barChart); // For bounds control
        barChart.setMarker(mv);
    }

    private void setBarData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        yVals1.add(new BarEntry(0,3));
        yVals1.add(new BarEntry(1,1));
        yVals1.add(new BarEntry(2,4));
        yVals1.add(new BarEntry(3,1));
        yVals1.add(new BarEntry(4,4));
        yVals1.add(new BarEntry(5,2));

        BarDataSet barDataSet;
        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            barDataSet = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            barDataSet.setValues(yVals1);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            barDataSet = new BarDataSet(yVals1, "2019-12");
            ArrayList<Integer> colors = new ArrayList<Integer>();
            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.MATERIAL_COLORS)
                colors.add(c);

            colors.add(ColorTemplate.getHoloBlue());
            barDataSet.setColors(colors);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(barDataSet);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            barChart.setData(data);
        }
    }

    private void initChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setCenterText("胜率圆形图");
        pieChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setOnChartValueSelectedListener(this);
        pieChart.animateXY(1400, 1400);
        setData();
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
    }

    private void setData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.clear();
        entries.add(new PieEntry(70.59f,"罗马"));
        entries.add(new PieEntry(52.49f,"亚特兰大"));
        entries.add(new PieEntry(52.49f,"国际米兰"));
        entries.add(new PieEntry(55.56f,"巴塞罗那"));
        entries.add(new PieEntry(78.95f,"谢菲尔德联"));
        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();
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
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

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
