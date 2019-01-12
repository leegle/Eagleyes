package com.egova.eagleyes.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.egova.baselibrary.base.EgovaFragment;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.Paging;
import com.egova.baselibrary.model.QueryModel;
import com.egova.baselibrary.model.RegionInfo;
import com.egova.baselibrary.util.ActivityLaunchUtil;
import com.egova.baselibrary.util.DateUtil;
import com.egova.baselibrary.util.MaterialDialogUtils;
import com.egova.baselibrary.util.ToastUtil;
import com.egova.baselibrary.widget.wheelregion.WheelRegion;
import com.egova.baselibrary.widget.wheeltime.ScreenInfo;
import com.egova.baselibrary.widget.wheeltime.WheelMain;
import com.egova.eagleyes.R;
import com.egova.eagleyes.constants.Constants;
import com.egova.eagleyes.databinding.FragmentGlobalSearchBinding;
import com.egova.eagleyes.databinding.TitleBackBarBinding;
import com.egova.eagleyes.helper.DictData;
import com.egova.eagleyes.model.request.SearchCondition;
import com.egova.eagleyes.model.respose.VehiclePassingInfo;
import com.egova.eagleyes.util.VehicleUtil;
import com.egova.eagleyes.vm.MainViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;
import razerdp.widget.QuickPopup;

/**
 * 全文检索
 */
public class GlobalSearchFragment extends EgovaFragment<FragmentGlobalSearchBinding, MainViewModel> {
    private TitleBackBarBinding titleBackBarBinding;
    private QueryModel<SearchCondition> queryModel;
    private SearchCondition condition;
    private WheelMain wheelMain;
    private WheelRegion wheelRegion;//省市区联动

    private RegionInfo selectRegion;
    private int selectType = 0;

    private QuickPopup popup;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_global_search;
    }

    @Override
    public void initView() {
        binding.setOnClick(onClickListener);
        initTitleBar();
    }

    private void initTitleBar() {
        titleBackBarBinding = binding.titleBar;
        titleBackBarBinding.tvTitle.setText(getString(R.string.text_search));
        titleBackBarBinding.leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {
        selectRegion = DictData.getInstance().getRegionInfo().getChildren().get(0).getChildren().get(0);

        condition = new SearchCondition();
        condition.setStartTimestamp(DateUtil.dateFormatMis(DateUtil.getTodayStart(new Date())));
        condition.setEndTimestamp(DateUtil.dateFormatMis(DateUtil.getTodayEnd(new Date())));

        binding.startTime.setContent(VehicleUtil.getShowTime(condition.getStartTimestamp()));
        binding.endTime.setContent(VehicleUtil.getShowTime(condition.getEndTimestamp()));
        binding.area.setContent(selectRegion.getName());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                popup = QuickPopupBuilder.with(getActivity())
                        .contentView(R.layout.tip_layout)
                        .config(new QuickPopupConfig().blurBackground(false))
                        .wrapContentMode()
                        .show(R.id.icon_tip);
            }
        }, 50);

    }

    @Override
    public void addVMObserver() {
        //搜索结果
        viewModel.getVehicleSearchData().observe(this, new Observer<Lcee<BaseResponse<List<VehiclePassingInfo>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<VehiclePassingInfo>>> baseResponseLcee) {
                updateVehicleInfo(baseResponseLcee);
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.icon_clear://清空输入
                    binding.editQuery.setText("");
                    break;
                case R.id.icon_tip://小提示
                    if (popup != null) {
                        popup.showPopupWindow(R.id.icon_tip);
                    }
                    break;
                case R.id.startTime:
                    selectType = 0;
                    showTimeDialog(getString(R.string.start_time), DateUtil.dateFormatMis(condition.getStartTimestamp()));
                    break;
                case R.id.endTime:
                    selectType = 1;
                    showTimeDialog(getString(R.string.end_time), DateUtil.dateFormatMis(condition.getEndTimestamp()));
                    break;
                case R.id.startSearch:
                    loadSearchData();
                    break;
                case R.id.area:
                    showRegionDialog(getString(R.string.search_area));
                    break;
            }
        }
    };


    /**
     * 弹窗选择时间段
     *
     * @param title
     * @param date
     */
    private void showTimeDialog(String title, Date date) {
        final LinearLayout contentView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.time_wheel_layout, null);
        wheelMain = new WheelMain(contentView, 2070, true);
        ScreenInfo screenInfo = new ScreenInfo(getActivity());
        wheelMain.screenheight = screenInfo.getHeight();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        wheelMain.initDateTimePicker(year, month, day, hour, min);
        MaterialDialogUtils.getCustomDialog(getActivity(), contentView, title, getString(R.string.ok), getString(R.string.cancel))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (selectType == 0) {
                            if (DateUtil.compareDate(condition.getEndTimestamp(), wheelMain.getTime() + ":00", "yyyy-MM-dd HH:mm:ss") < 0) {
                                ToastUtil.showNormal("起始时段不能大于结束时段");
                            } else {
                                binding.startTime.setContent(wheelMain.getTime());
                                condition.setStartTimestamp(wheelMain.getTime() + ":00");
                                dialog.dismiss();
                            }
                        } else {
                            if (DateUtil.compareDate(wheelMain.getTime() + ":59", condition.getStartTimestamp(), "yyyy-MM-dd HH:mm:ss") < 0) {
                                ToastUtil.showNormal("结束时段不能小于起始时段");
                            } else {
                                binding.endTime.setContent(wheelMain.getTime());
                                condition.setEndTimestamp(wheelMain.getTime() + ":59");
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

    //选择区域
    private void showRegionDialog(String title) {
        final LinearLayout contentView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.region_wheel_layout, null);
        wheelRegion = new WheelRegion(contentView, WheelRegion.level2);
        ScreenInfo screenInfo = new ScreenInfo(getActivity());
        wheelRegion.screenHeight = screenInfo.getHeight();
        wheelRegion.initRegionPicker(DictData.getInstance().getRegionInfo().getChildren());
        MaterialDialogUtils.getCustomDialog(getActivity(), contentView, title, getString(R.string.ok), getString(R.string.cancel))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        selectRegion = wheelRegion.getItem();
                        if (selectRegion.getParentName() != null && !"不限".equals(selectRegion.getParentName())) {
                            binding.area.setContent(selectRegion.getParentName());
                            condition.setCityID(selectRegion.getId());
                        } else if ("不限".equals(selectRegion.getParentName())) {
                            binding.area.setContent(selectRegion.getParentName());
                        } else {
                            binding.area.setContent(selectRegion.getName());
                            condition.setRegionID(selectRegion.getId());
                        }
                        dialog.dismiss();
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        }).autoDismiss(false).show();
    }

    /**
     * 开始搜索
     */
    private void loadSearchData() {
        queryModel = new QueryModel<>();
        queryModel.setCondition(VehicleUtil.buildSearchKeyWord(condition, binding.editQuery.getText().toString()));
        Paging paging = new Paging();
        paging.setPageIndex(1);
        queryModel.setPaging(paging);
        showLoadingDialog(getString(R.string.search_loading));
        viewModel.queryVehicleByCondition(queryModel);
    }

    private void updateVehicleInfo(Lcee<BaseResponse<List<VehiclePassingInfo>>> response) {
        dismissLoadingDialog();
        switch (response.status) {
            case Error:
                ToastUtil.showError(response.error.getMessage());
                break;
            case Content:
                if (response.data != null && response.data.getResult() != null && response.data.getResult().size() > 0) {
                    ArrayList<VehiclePassingInfo> result = new ArrayList<>();
                    result.addAll(response.data.getResult());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BUNDLE_VEHICLE_QUERY, queryModel);
                    bundle.putSerializable(Constants.BUNDLE_VEHICLE_LIST, result);
                    bundle.putInt(Constants.BUNDLE_VEHICLE_SIZE, response.data.getTotalCount());
                    ActivityLaunchUtil.launchContainerActivity(getActivity(), VehiclePassingFragment.class.getCanonicalName(), bundle);

                } else {
                    ToastUtil.showNormal("抱歉，未搜索到结果");
                }
                break;
        }
    }

}
