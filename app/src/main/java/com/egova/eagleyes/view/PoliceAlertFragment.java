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
import com.egova.eagleyes.databinding.FragmentPoliceAlertBinding;
import com.egova.eagleyes.helper.DictData;
import com.egova.eagleyes.model.request.PoliceCondition;
import com.egova.eagleyes.model.respose.DispositionAlarm;
import com.egova.eagleyes.model.respose.Tollgate;
import com.egova.eagleyes.util.DictUtil;
import com.egova.eagleyes.vm.PoliceControlViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * 报警管理
 */
public class PoliceAlertFragment extends EgovaFragment<FragmentPoliceAlertBinding, PoliceControlViewModel> {
    private PoliceCondition condition;

    private WheelMain wheelMain;//时间控件
    private int selectType = 0;

    private WheelRegion wheelRegion;//省市区联动
    private RegionInfo selectRegion;//已选择的区域

    private List<Tollgate> selectTollgateList = new ArrayList<>();//已选择的卡口数据

    private QueryModel<PoliceCondition> queryModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_police_alert;
    }

    @Override
    public void initView() {
        setSelected(R.id.noLimit);
        binding.setOnClick(onClickListener);

    }

    @Override
    public void initData() {

        selectRegion = DictData.getInstance().getRegionInfo().getChildren().get(0).getChildren().get(0);

        condition = new PoliceCondition();
        condition.setStartTimestamp(DateUtil.dateFormatMis(DateUtil.getTodayStart(new Date())));
        condition.setEndTimestamp(DateUtil.dateFormatMis(DateUtil.getTodayEnd(new Date())));

        binding.tollgateType.setContent(getString(R.string.police_no_limit));
        binding.tollgateName.setContent(getString(R.string.police_no_limit));
        binding.alertRegion.setContent(selectRegion.getName());
        updateTimeTextViewValue();
    }

    @Override
    public void addVMObserver() {
        viewModel.getAlarmSearchData().observe(this, new Observer<Lcee<BaseResponse<List<DispositionAlarm>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<DispositionAlarm>>> baseResponseLcee) {
                updateDispositionAlarm(baseResponseLcee);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTollgateTypeText();
        updateTollgateText();
    }

    private void updateTimeTextViewValue() {
        binding.startDate.setText(DateUtil.dateFormat(DateUtil.dateFormatMis(condition.getStartTimestamp())));
        binding.endDate.setText(DateUtil.dateFormat(DateUtil.dateFormatMis(condition.getEndTimestamp())));
    }

    public void setSelected(int viewId) {
        switch (viewId) {
            case R.id.noLimit:
                binding.noLimit.setSelected(true);
                binding.deal.setSelected(false);
                binding.noDeal.setSelected(false);
                break;
            case R.id.deal:
                binding.noLimit.setSelected(false);
                binding.deal.setSelected(true);
                binding.noDeal.setSelected(false);
                break;
            case R.id.noDeal:
                binding.noLimit.setSelected(false);
                binding.deal.setSelected(false);
                binding.noDeal.setSelected(true);
                break;
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.noLimit://报警状态人-不限
                    setSelected(R.id.noLimit);
                    break;
                case R.id.deal://报警状态人-已处理
                    setSelected(R.id.deal);
                    break;
                case R.id.noDeal://报警状态人-未处理
                    setSelected(R.id.noDeal);
                    break;
                case R.id.startDate://开始时间
                    selectType = 0;
                    showTimeDialog(getString(R.string.start_time), DateUtil.dateFormatMis(condition.getStartTimestamp()));
                    break;
                case R.id.endDate://结束时间
                    selectType = 1;
                    showTimeDialog(getString(R.string.end_time), DateUtil.dateFormatMis(condition.getEndTimestamp()));
                    break;
                case R.id.alertRegion://报警区域
                    showRegionDialog(getString(R.string.search_area));
                    break;
                case R.id.tollgateType://卡口类型
                    showTollgateTypeSelector();
                    break;
                case R.id.tollgateName://卡口选择
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BUNDLE_REGION, selectRegion);
                    ActivityLaunchUtil.launchContainerActivity(getActivity(), TollgateSearchFragment.class.getCanonicalName(), bundle);
                    break;
                case R.id.startSearch://开始搜索
                    loadData();
                    break;
            }
        }
    };

    private void loadData() {
        if (binding.noLimit.isSelected()) {
            //不限
        } else if (binding.deal.isSelected()) {
            //已处理
            condition.setHandled("true");
        } else if (binding.noDeal.isSelected()) {
            condition.setHandled("false");
        }
        if (selectRegion == null || selectRegion.getId().endsWith("0000")) {
            //全选卡口时不传数据
        } else {
            //选择的卡口
            int size = selectTollgateList.size();
            ArrayList<String> selectedIds = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                selectedIds.add(selectTollgateList.get(i).getId());
            }
            condition.setTollgateId(selectedIds);
        }
        queryModel = new QueryModel<>();
        Paging paging = new Paging();
        paging.setPageIndex(1);
        queryModel.setPaging(paging);
        queryModel.setCondition(condition);
        showLoadingDialog(getString(R.string.search_loading));
        viewModel.loadDispositionAlarm(queryModel);
    }

    /**
     * 弹窗选择时间段
     *
     * @param title
     * @param date
     */
    private void showTimeDialog(String title, Date date) {
        final LinearLayout contentView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.time_wheel_layout, null);
        wheelMain = new WheelMain(contentView, 2070, false);
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
                            if (DateUtil.compareDate(condition.getEndTimestamp(), wheelMain.getTime() + " 00:00:00", "yyyy-MM-dd HH:mm:ss") < 0) {
                                ToastUtil.showNormal("起始时段不能大于结束时段");
                            } else {
                                condition.setStartTimestamp(wheelMain.getTime() + " 00:00:00");
                                updateTimeTextViewValue();
                                dialog.dismiss();
                            }
                        } else {
                            if (DateUtil.compareDate(wheelMain.getTime() + " 23:59:59", condition.getStartTimestamp(), "yyyy-MM-dd HH:mm:ss") < 0) {
                                ToastUtil.showNormal("结束时段不能小于起始时段");
                            } else {
                                condition.setEndTimestamp(wheelMain.getTime() + " 23:59:59");
                                updateTimeTextViewValue();
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
                            binding.alertRegion.setContent(selectRegion.getParentName());
                        } else if ("不限".equals(selectRegion.getParentName())) {
                            binding.alertRegion.setContent(selectRegion.getParentName());
                        } else {
                            binding.alertRegion.setContent(selectRegion.getName());
                        }
                        updateTollgateText();
                        dialog.dismiss();
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        }).autoDismiss(false).show();
    }

    //选择卡口类型
    private void showTollgateTypeSelector() {
        int size = DictData.getInstance().getTollgateTypesList().size();
        ArrayList<Integer> initChoose = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (DictData.getInstance().getTollgateTypesList().get(i).isHasPoliceAlertSelected()) {
                initChoose.add(i);
            }
        }
        Integer[] initChooseIndex = new Integer[initChoose.size()];
        initChoose.toArray(initChooseIndex);
        MaterialDialog.Builder builder = MaterialDialogUtils.getCollectionSelectDialog(getActivity(), "卡口类型选择", DictData.getInstance().getTollgateTypesList())
                .itemsCallbackMultiChoice(initChooseIndex, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        if (which.length == 0 || which.length == size) {//全选
                            for (int i = 0; i < size; i++) {
                                DictData.getInstance().getTollgateTypesList().get(i).setHasPoliceAlertSelected(true);
                            }
                        } else {//非全选
                            for (int i = 0; i < size; i++) {
                                DictData.getInstance().getTollgateTypesList().get(i).setHasPoliceAlertSelected(false);
                            }
                            for (int i = 0; i < which.length; i++) {
                                DictData.getInstance().getTollgateTypesList().get(which[i]).setHasPoliceAlertSelected(true);
                            }
                        }
                        updateTollgateTypeText();
                        updateTollgateText();
                        return true;
                    }
                })
                .positiveText(getString(R.string.ok))
                .negativeText(getString(R.string.cancel));
        builder.show();
    }


    //跟新卡口选择
    private void updateTollgateTypeText() {
        if (DictData.getInstance().getTollgateTypesList() == null) {
            binding.tollgateType.setContent("不限");
            return;
        }
        int hasChoose = 0;
        int size = DictData.getInstance().getTollgateTypesList().size();
        for (int i = 0; i < size; i++) {
            if (DictData.getInstance().getTollgateTypesList().get(i).isHasPoliceAlertSelected()) {
                hasChoose++;
            }
        }
        if (hasChoose == size) {
            binding.tollgateType.setContent("不限");
        } else {
            binding.tollgateType.setContent("已选" + hasChoose + "个类型");
        }
    }

    public void updateTollgateText() {
        selectTollgateList.clear();
        DictUtil.buildTollgateData(selectTollgateList, selectRegion, true);
        int size = selectTollgateList.size();
        binding.tollgateName.setContent("已选" + size + "个卡口");
    }


    private void updateDispositionAlarm(Lcee<BaseResponse<List<DispositionAlarm>>> response) {
        dismissLoadingDialog();
        switch (response.status) {
            case Error:
                ToastUtil.showError(response.error.getMessage());
                break;
            case Content:
                if (response.data != null && response.data.getResult() != null && response.data.getResult().size() > 0) {
                    ArrayList<DispositionAlarm> result = new ArrayList<>();
                    result.addAll(response.data.getResult());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BUNDLE_VEHICLE_QUERY, queryModel);
                    bundle.putSerializable(Constants.BUNDLE_VEHICLE_LIST, result);
                    bundle.putInt(Constants.BUNDLE_VEHICLE_SIZE, response.data.getTotalCount());
                    ActivityLaunchUtil.launchContainerActivity(getActivity(), DispositionAlarmListFragment.class.getCanonicalName(), bundle);

                } else {
                    ToastUtil.showNormal("未搜索到结果，更换条件试试");
                }
                break;
        }
    }

}
