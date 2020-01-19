package com.test.sandev.view;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by philipp on 02/06/16.
 */
public class DayAxisValueFormatter implements IAxisValueFormatter {

    private String[] xStrs = new String[]{"曼城", "莱切城", "巴萨", "阿拉维","国米","热那亚","意大利"};
//    private String[] xStrs = new String[]{"西湖区", "萧山区", "滨江区", "江干区","余杭区","拱墅区"};

    private BarLineChartBase<?> chart;
    public DayAxisValueFormatter(BarChart barChart) {
        this.chart = barChart;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        System.out.println("======"+value);
        int position = (int) value;
        if (position >= 7) {
            position = 0;
        }
        return xStrs[position];
    }
}
