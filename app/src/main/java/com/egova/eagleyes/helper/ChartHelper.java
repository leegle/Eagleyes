package com.egova.eagleyes.helper;

import android.graphics.Color;

import com.egova.baselibrary.model.NameTextValue;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

public class ChartHelper {

    //车辆画像--活跃区县饼图配置
    public static void initVehicleRegionPieChart(PieChart pieChart) {
        pieChart.setUsePercentValues(false);//使用百分比还是具体数值
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        pieChart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);//不显示图例
    }

    //车辆画像--活跃时间 折线图配置
    public static void initVehicleTimeLineChart(LineChart lineChart) {
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawGridBackground(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(mTf);
//        xAxis.setLabelCount(12);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) value + "时";
            }
        });

        YAxis leftAxis = lineChart.getAxisLeft();
//        leftAxis.setTypeface(mTf);
//        leftAxis.setLabelCount(1, false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);


        lineChart.animateX(750);

        Legend l = lineChart.getLegend();
        l.setEnabled(false);
    }

    //车辆画像--活跃卡口 柱状图配置
    public static void initVehicleTollgateBarChart(BarChart barChart) {
        barChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);
        barChart.setScaleEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setEnabled(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
        });

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        barChart.getAxisRight().setEnabled(false);

        barChart.animateY(1500);

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTextSize(14f);
        l.setWordWrapEnabled(true);
        l.setEnabled(true);//显示图例
    }

    //车辆画像--活跃区县饼图数据填充
    public static void updateRegionChartData(PieChart pieChart, List<NameTextValue<Integer>> data) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        int size = data.size();
        for (int i = 0; i < size; i++) {
            entries.add(new PieEntry(data.get(i).getValue(), data.get(i).getText()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "region dataSet");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<>();

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

        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int newValue = (int) value;
                return String.valueOf(newValue);
            }
        });
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.BLACK);
//        pieData.setValueTypeface(tf);
        pieChart.setData(pieData);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    //车辆画像--活跃时间折线图数据填充
    public static void updateTimeChartData(LineChart lineChart, List<NameTextValue<Integer>> data) {
        ArrayList<Entry> values1 = new ArrayList<>();
        int size = data.size();
        for (int i = 0; i < size; i++) {
            values1.add(new Entry(i, data.get(i).getValue()));
        }

        LineDataSet d1 = new LineDataSet(values1, "time DataSet ");
        d1.setLineWidth(2f);
        d1.setCircleRadius(3.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(true);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        LineData lineData = new LineData(sets);
        lineData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int newValue = (int) value;
                if (newValue > 0) {
                    return String.valueOf(newValue);
                }
                return "";
            }
        });
        lineData.setValueTextSize(11f);
        lineData.setValueTextColor(Color.BLACK);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    //车辆画像--活跃卡口 柱状图数据填充
    public static void updateTollgateChartData(BarChart barChart, List<NameTextValue<Integer>> data) {
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
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

        int size = data.size();
        if(size>colors.size()){
            //最多只取前25个卡口，再多就没有意义
            size=colors.size();
        }
        for (int i = 0; i < size; i++) {
            ArrayList<BarEntry> values = new ArrayList<>();
            values.add(new BarEntry(i, data.get(i).getValue()));
            BarDataSet set1 = new BarDataSet(values, data.get(i).getText());
            set1.setDrawValues(true);
            set1.setColor(colors.get(i));
            set1.setHighlightEnabled(false);
            dataSets.add(set1);
        }
        BarData barData = new BarData(dataSets);
        barData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int newValue = (int) value;
                if (newValue > 0) {
                    return String.valueOf(newValue);
                }
                return "";
            }
        });
        barChart.setData(barData);
        barChart.invalidate();
    }
}
