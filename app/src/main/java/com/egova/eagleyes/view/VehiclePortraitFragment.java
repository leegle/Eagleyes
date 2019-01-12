package com.egova.eagleyes.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.egova.baselibrary.base.EgovaFragment;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.baselibrary.util.DateUtil;
import com.egova.baselibrary.util.DensityUtil;
import com.egova.baselibrary.util.MaterialDialogUtils;
import com.egova.baselibrary.util.ToastUtil;
import com.egova.baselibrary.widget.wheeltime.ScreenInfo;
import com.egova.baselibrary.widget.wheeltime.WheelMain;
import com.egova.eagleyes.R;
import com.egova.eagleyes.constants.Constants;
import com.egova.eagleyes.databinding.ChartBarBinding;
import com.egova.eagleyes.databinding.ChartLineBinding;
import com.egova.eagleyes.databinding.ChartPieBinding;
import com.egova.eagleyes.databinding.FragmentVehiclePortraitBinding;
import com.egova.eagleyes.databinding.TitleBackBarBinding;
import com.egova.eagleyes.helper.ChartHelper;
import com.egova.eagleyes.model.request.SearchCondition;
import com.egova.eagleyes.model.respose.VehiclePassingInfo;
import com.egova.eagleyes.util.StringUtil;
import com.egova.eagleyes.vm.VehicleStatisticViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

/**
 * 车辆画像
 */
public class VehiclePortraitFragment extends EgovaFragment<FragmentVehiclePortraitBinding, VehicleStatisticViewModel> {

    private TitleBackBarBinding titleBackBarBinding;
    private VehiclePassingInfo vehiclePassingInfo;

    private ChartPieBinding chartPieBinding;
    private ChartLineBinding chartLineBinding;
    private ChartBarBinding chartBarBinding;

    private View regionChartView, timeChartView, tollgateChartView;//活跃区县，时间，卡口 图表
    private LinearLayout.LayoutParams layoutParams;

    private WheelMain wheelMain;
    private String startDate, endDate;
    private int selectType = 0;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_vehicle_portrait;
    }


    @Override
    public void initView() {
        initTitleBar();
        initOtherView();
        initChart();
        loadAllData();
    }

    private void initTitleBar() {
        titleBackBarBinding = binding.titleBar;
        titleBackBarBinding.tvTitle.setText(getString(R.string.vehicle_portrait));
        titleBackBarBinding.leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {
        vehiclePassingInfo = (VehiclePassingInfo) getArguments().getSerializable(Constants.BUNDLE_VEHICLE);
        startDate = DateUtil.dateFormatMis(DateUtil.getTodayStart(new Date()));
        endDate = DateUtil.dateFormatMis(DateUtil.getTodayEnd(new Date()));

    }


    @Override
    public void addVMObserver() {
        //活跃区县
        viewModel.getPortraitByRegionData().observe(this, new Observer<Lcee<BaseResponse<List<NameTextValue<Integer>>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<NameTextValue<Integer>>>> baseResponseLcee) {
                updateRegionData(baseResponseLcee);
            }
        });
        //活跃时间
        viewModel.getPortraitByTimeData().observe(this, new Observer<Lcee<BaseResponse<List<NameTextValue<Integer>>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<NameTextValue<Integer>>>> baseResponseLcee) {
                updateTimeData(baseResponseLcee);
            }
        });
        //活跃卡口
        viewModel.getPortraitByTollgateData().observe(this, new Observer<Lcee<BaseResponse<List<NameTextValue<Integer>>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<NameTextValue<Integer>>>> baseResponseLcee) {
                updateTollgateData(baseResponseLcee);
            }
        });
    }

    private void initOtherView() {
        binding.setOnClick(onClickListener);
        binding.todayBtn.setSelected(true);

        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        regionChartView = inflater.inflate(R.layout.chart_pie, null);
        chartPieBinding = DataBindingUtil.bind(regionChartView);

        timeChartView = inflater.inflate(R.layout.chart_line, null);
        chartLineBinding = DataBindingUtil.bind(timeChartView);

        tollgateChartView = inflater.inflate(R.layout.chart_bar, null);
        chartBarBinding = DataBindingUtil.bind(tollgateChartView);

        binding.regionChart.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRegionData();
            }
        });

        binding.timeChart.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTimeData();
            }
        });
        binding.tollgateChart.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTollgateData();
            }
        });
    }

    private void initChart() {
        //活跃区县
        ChartHelper.initVehicleRegionPieChart(chartPieBinding.pieChart);
        //活跃时间
        ChartHelper.initVehicleTimeLineChart(chartLineBinding.lineChart);
        //活跃卡口
        ChartHelper.initVehicleTollgateBarChart(chartBarBinding.barChart);
    }

    private void loadAllData() {
        updateDateTimeView();
        loadRegionData();//活跃区县
        loadTimeData();//活跃时间
        loadTollgateData();//活跃卡口
    }

    //加载活跃区县数据
    private void loadRegionData() {
        binding.regionChart.showLoading();
        SearchCondition condition = new SearchCondition();
        String[] plate = new String[]{vehiclePassingInfo.getPlate().getNo()};
        condition.setPlateList(plate);
        condition.setStartTimestamp(startDate);
        condition.setEndTimestamp(endDate);
        viewModel.queryPortraitByRegion(condition);
    }

    //加载活跃时间数据
    private void loadTimeData() {
        binding.timeChart.showLoading();
        SearchCondition condition = new SearchCondition();
        String[] plate = new String[]{vehiclePassingInfo.getPlate().getNo()};
        condition.setPlateList(plate);
        condition.setStartTimestamp(startDate);
        condition.setEndTimestamp(endDate);
        viewModel.queryPortraitByTime(condition);
    }

    //加载活跃卡口数据
    private void loadTollgateData() {
        binding.timeChart.showLoading();
        SearchCondition condition = new SearchCondition();
        String[] plate = new String[]{vehiclePassingInfo.getPlate().getNo()};
        condition.setPlateList(plate);
        condition.setStartTimestamp(startDate);
        condition.setEndTimestamp(endDate);
        viewModel.queryPortraitByTollgate(condition);
    }

    //更新活跃区县数据
    private void updateRegionData(Lcee<BaseResponse<List<NameTextValue<Integer>>>> response) {
        switch (response.status) {
            case Error:
                binding.regionChart.showError();
                break;
            case Content:
                ChartHelper.updateRegionChartData(chartPieBinding.pieChart, response.data.getResult());
                binding.mostRegion.setText(getMostActive(response.data.getResult(), 0));
                binding.regionChart.showContent(regionChartView, layoutParams);
                break;
        }
    }

    //更新活跃时间数据
    private void updateTimeData(Lcee<BaseResponse<List<NameTextValue<Integer>>>> response) {
        switch (response.status) {
            case Error:
                binding.timeChart.showError();
                break;
            case Content:
                ChartHelper.updateTimeChartData(chartLineBinding.lineChart, response.data.getResult());
                binding.mostTime.setText(getMostActive(response.data.getResult(), 1));
                binding.timeChart.showContent(timeChartView, layoutParams);
                break;
        }
    }

    //更新活跃卡口数据
    private void updateTollgateData(Lcee<BaseResponse<List<NameTextValue<Integer>>>> response) {
        switch (response.status) {
            case Error:
                binding.tollgateChart.showError();
                break;
            case Content:
                int size = response.data.getResult().size();
                LinearLayout.LayoutParams chartParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getActivity(), 180 + 25 * size));
                binding.tollgateChart.setLayoutParams(chartParams);
                binding.tollgateChart.invalidate();
                ChartHelper.updateTollgateChartData(chartBarBinding.barChart, response.data.getResult());
                binding.mostTollgate.setText(getMostActive(response.data.getResult(), 2));
                binding.tollgateChart.showContent(tollgateChartView, layoutParams);
                break;
        }
    }

    private String getMostActive(List<NameTextValue<Integer>> data, int type) {
        int size = data.size();
        NameTextValue<Integer> largest = new NameTextValue<>();
        largest.setValue(0);
        for (int i = 0; i < size; i++) {
            if (data.get(i).getValue() > largest.getValue()) {
                largest = data.get(i);
            }
        }
        if (type == 0) {
            return StringUtil.formatNull(largest.getText()) + " 活动最活跃";
        } else if (type == 1) {
            return StringUtil.formatNull(largest.getText()) + "时 活动最活跃";
        } else if (type == 2) {
            return StringUtil.formatNull(largest.getText()) + " 活动最活跃";
        }
        return "";
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.todayBtn:
                    startDate = DateUtil.dateFormatMis(DateUtil.getTodayStart(new Date()));
                    endDate = DateUtil.dateFormatMis(DateUtil.getTodayEnd(new Date()));
                    binding.todayBtn.setSelected(true);
                    binding.sevenDayBtn.setSelected(false);
                    loadAllData();
                    break;
                case R.id.sevenDayBtn:
                    startDate = DateUtil.dateFormatMis(DateUtil.getTodayStart(DateUtil.getDateAddDays(-6)));
                    endDate = DateUtil.dateFormatMis(DateUtil.getTodayEnd(new Date()));
                    binding.todayBtn.setSelected(false);
                    binding.sevenDayBtn.setSelected(true);
                    loadAllData();
                    break;
                case R.id.startDateEdit:
                    selectType = 0;
                    showTimeDialog(getString(R.string.start_time), startDate);
                    break;
                case R.id.endDateEdit:
                    selectType = 1;
                    showTimeDialog(getString(R.string.end_time), endDate);
                    break;
            }
        }
    };

    //更新时间显示
    private void updateDateTimeView() {
        binding.startDateEdit.setText(DateUtil.dateFormat(DateUtil.dateFormatMis(startDate)));
        binding.endDateEdit.setText(DateUtil.dateFormat(DateUtil.dateFormatMis(endDate)));
    }

    /**
     * 弹窗选择时间段
     *
     * @param title
     * @param date
     */
    private void showTimeDialog(String title, String date) {
        final LinearLayout contentView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.time_wheel_layout, null);
        wheelMain = new WheelMain(contentView, 2070, false);
        ScreenInfo screenInfo = new ScreenInfo(getActivity());
        wheelMain.screenheight = screenInfo.getHeight();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.dateFormatMis(date));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        wheelMain.initDateTimePicker(year, month, day);
        MaterialDialogUtils.getCustomDialog(getActivity(), contentView, title, getString(R.string.ok), getString(R.string.cancel))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (selectType == 0) {
                            String selectDate = wheelMain.getTime() + " 00:00:00";
//                            if (DateUtil.compareDate(endDate, selectDate, "yyyy-MM-dd HH:mm:ss") < 0) {
//                                ToastUtil.showNormal("起始时段不能大于结束时段");
//                            } else
                            if (DateUtil.compareDate(DateUtil.dateFormatMis(DateUtil.getTodayEnd(new Date())), selectDate) < 0) {
                                ToastUtil.showNormal("起始时段不能大于今天");
                            } else {
                                startDate = wheelMain.getTime() + " 00:00:00";
                                int daysInterval = DateUtil.differentDaysByMillisecond(DateUtil.getTodayStart(DateUtil.dateFormatMis(startDate)), DateUtil.getTodayStart(DateUtil.dateFormatMis(endDate)));
                                if (daysInterval > 7) {
//                                    ToastUtil.showNormal("两个日期间隔最多7天，已为您自动转换");
                                    endDate = DateUtil.dateFormatMis(DateUtil.getTodayEnd(DateUtil.getDateAddDays(DateUtil.dateFormatMis(startDate), 7)));
                                } else if (daysInterval < 0) {
                                    endDate = DateUtil.dateFormatMis(DateUtil.getTodayEnd(DateUtil.dateFormatMis(startDate)));
                                }
                                loadAllData();
                                dialog.dismiss();
                            }
                        } else {
                            String selectDate = wheelMain.getTime() + " 23:59:59";
//                            if (DateUtil.compareDate(selectDate, startDate, "yyyy-MM-dd HH:mm:ss") < 0) {
//                                ToastUtil.showNormal("结束时段不能小于起始时段");
//                            } else
                            if (DateUtil.compareDate(DateUtil.dateFormatMis(DateUtil.getTodayEnd(new Date())), selectDate) < 0) {
                                ToastUtil.showNormal("结束时段不能大于今天");
                            } else {
                                endDate = wheelMain.getTime() + " 23:59:59";
                                int daysInterval = DateUtil.differentDaysByMillisecond(DateUtil.getTodayStart(DateUtil.dateFormatMis(startDate)), DateUtil.getTodayStart(DateUtil.dateFormatMis(endDate)));
                                if (daysInterval > 7) {
//                                        ToastUtil.showNormal("两个日期间隔最多7天，已为您自动转换");
                                    startDate = DateUtil.dateFormatMis(DateUtil.getTodayStart(DateUtil.getDateAddDays(DateUtil.dateFormatMis(endDate), -7)));
                                } else if (daysInterval < 0) {
                                    startDate = DateUtil.dateFormatMis(DateUtil.getTodayStart(DateUtil.dateFormatMis(endDate)));
                                }
                                loadAllData();
                                dialog.dismiss();
                            }
                        }
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        }).autoDismiss(false).show();
    }
}
