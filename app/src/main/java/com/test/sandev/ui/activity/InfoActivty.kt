package com.test.sandev.ui.activity

import android.graphics.Color
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.test.sandev.R
import com.test.sandev.base.BaseActivity
import com.test.sandev.view.DayAxisValueFormatter
import com.test.sandev.view.MyAxisValueFormatter
import com.test.sandev.view.XYMarkerView
import kotlinx.android.synthetic.main.activity_infmore.*
import java.util.*

class InfoActivty : BaseActivity(), OnChartValueSelectedListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_infmore
    }

    override fun initData() {
        initChart()
        initLineraChart()
    }

    private fun initLineraChart() {
        chart.setOnChartValueSelectedListener(this)
        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)
        chart.description.isEnabled = false
        chart.setMaxVisibleValueCount(60)
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)
        val xAxisFormatter: IAxisValueFormatter = DayAxisValueFormatter(chart)
        val xAxis: XAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f // only intervals of 1 day
        xAxis.setLabelCount(8, false)
        xAxis.valueFormatter = xAxisFormatter
        val custom: IAxisValueFormatter = MyAxisValueFormatter()
        val leftAxis: YAxis = chart.axisLeft
        //获取到图形右边的Y轴，并设置为不显示
        chart.axisRight.isEnabled = false
        leftAxis.setLabelCount(7, true)
        leftAxis.valueFormatter = custom
        leftAxis.axisMaximum = 6f
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        val l: Legend = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.form = Legend.LegendForm.SQUARE
        l.formSize = 9f
        l.textSize = 11f
        l.xEntrySpace = 4f
        setBarData()
        val mv = XYMarkerView(this, xAxisFormatter)
        mv.chartView = chart // For bounds control
        chart.marker = mv
    }

    private fun setBarData() {
        val yVals1 = ArrayList<BarEntry>()
        yVals1.add(BarEntry(0f, (Math.random() * 4).toFloat()))
        yVals1.add(BarEntry(1f, (Math.random() * 4).toFloat()))
        yVals1.add(BarEntry(2f, (Math.random() * 4).toFloat()))
        yVals1.add(BarEntry(3f, (Math.random() * 4).toFloat()))
        yVals1.add(BarEntry(4f, (Math.random() * 4).toFloat()))
        yVals1.add(BarEntry(5f, (Math.random() * 4).toFloat()))
        yVals1.add(BarEntry(6f, (Math.random() * 4).toFloat()))
        var barDataSet: BarDataSet? = null
        if (chart.data != null &&
                chart.data.dataSetCount > 0) {
            barDataSet = chart!!.data!!.getDataSetByIndex(0) as BarDataSet
            barDataSet!!.values = yVals1
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            barDataSet = BarDataSet(yVals1, "2019-12")
            val colors = ArrayList<Int>()
            for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
            for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
            for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
            for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
            for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
            for (c in ColorTemplate.MATERIAL_COLORS) colors.add(c)
            colors.add(ColorTemplate.getHoloBlue())
            barDataSet.colors = colors
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(barDataSet)
            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.barWidth = 0.9f
            chart.data = data
        }
    }

    private fun initChart() {
        chart1.setUsePercentValues(true)
        chart1.description.isEnabled = false
        chart1.setExtraOffsets(5f, 10f, 5f, 5f)
        chart1.dragDecelerationFrictionCoef = 0.95f
        chart1.setExtraOffsets(20f, 0f, 20f, 0f)
        chart1.isDrawHoleEnabled = true
        chart1.setHoleColor(Color.WHITE)
        chart1.setTransparentCircleColor(Color.WHITE)
        chart1.setTransparentCircleAlpha(110)
        chart1.holeRadius = 58f
        chart1.transparentCircleRadius = 61f
        chart1.setDrawCenterText(true)
        chart1.rotationAngle = 0f
        chart1.isRotationEnabled = true
        chart1.isHighlightPerTapEnabled = true
        chart1.setOnChartValueSelectedListener(this)
        chart1.animateXY(1400, 1400)
        setData()
        val l: Legend = chart1.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = false
    }

    private fun setData() {
        val entries = ArrayList<PieEntry>()
        entries.clear()
        entries.add(PieEntry(70.59f, "罗马"))
        entries.add(PieEntry(52.49f, "亚特兰大"))
        entries.add(PieEntry(52.49f, "国际米兰"))
        entries.add(PieEntry(55.56f, "巴塞罗那"))
        entries.add(PieEntry(78.95f, "谢菲尔德联"))
        //        entries.add(new PieEntry(70.59f,"西湖区"));
//        entries.add(new PieEntry(52.49f,"余杭区"));
//        entries.add(new PieEntry(52.49f,"江干区"));
//        entries.add(new PieEntry(55.56f,"萧山区"));
//        entries.add(new PieEntry(78.95f,"滨江区"));
        val dataSet = PieDataSet(entries, "Election Results")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        val colors = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        dataSet.valueLinePart1OffsetPercentage = 80f
        dataSet.valueLinePart1Length = 0.2f
        dataSet.valueLinePart2Length = 0.4f
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        chart1.data = data
        chart1.highlightValues(null)
        chart1.invalidate()
    }

    override fun onNothingSelected() {
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
    }

}