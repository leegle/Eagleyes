package com.egova.eagleyes.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.egova.baselibrary.activity.PhotoPreviewActivity;
import com.egova.baselibrary.base.EgovaFragment;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
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
import com.egova.eagleyes.databinding.FragmentPictureSearchBinding;
import com.egova.eagleyes.databinding.TitleBackBarBinding;
import com.egova.eagleyes.helper.DictData;
import com.egova.eagleyes.model.request.SearchCondition;
import com.egova.eagleyes.model.respose.VehiclePassingInfo;
import com.egova.eagleyes.util.GlideUtil;
import com.egova.eagleyes.util.VehicleUtil;
import com.egova.eagleyes.vm.MainViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * 根据图片特征码进行以图搜图
 */
public class PictureSearchFragment extends EgovaFragment<FragmentPictureSearchBinding, MainViewModel> {
    private TitleBackBarBinding titleBackBarBinding;
    private SearchCondition condition;
    private WheelMain wheelMain;
    private WheelRegion wheelRegion;//省市区联动
    private RegionInfo selectRegion;

    private int selectType = 0;
    private VehiclePassingInfo vehiclePassingInfo;

    @Override
    public void initView() {
        binding.setOnClick(onClickListener);
        initTitleBar();
    }

    private void initTitleBar() {
        titleBackBarBinding = binding.titleBar;
        titleBackBarBinding.tvTitle.setText(getString(R.string.search_by_picture));
        titleBackBarBinding.leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_picture_search;
    }

    @Override
    public void initData() {
        selectRegion = DictData.getInstance().getRegionInfo().getChildren().get(0).getChildren().get(0);
        vehiclePassingInfo = (VehiclePassingInfo) getArguments().getSerializable(Constants.BUNDLE_VEHICLE);

        condition = new SearchCondition();
        condition.setStartTimestamp(DateUtil.dateFormatMis(DateUtil.getTodayStart(new Date())));
        condition.setEndTimestamp(DateUtil.dateFormatMis(DateUtil.getTodayEnd(new Date())));
        condition.setVehicleColor(vehiclePassingInfo.getVehicleColor());
//        condition.setIword(vehiclePassingInfo.getSpeciesName());
        condition.setHmCode(vehiclePassingInfo.getFeature());

        binding.startTime.setContent(VehicleUtil.getShowTime(condition.getStartTimestamp()));
        binding.endTime.setContent(VehicleUtil.getShowTime(condition.getEndTimestamp()));
        binding.area.setContent(selectRegion.getName());
        GlideUtil.load(getActivity(), binding.carImage, vehiclePassingInfo.getUrl());
    }

    @Override
    public void addVMObserver() {

        viewModel.getPhotoFeatureData().observe(this, new Observer<Lcee<BaseResponse<VehiclePassingInfo>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<VehiclePassingInfo>> baseResponseLcee) {
                updateFeatureData(baseResponseLcee);
            }
        });
        viewModel.getVehicleSearchByFeatureData().observe(this, new Observer<Lcee<BaseResponse<List<VehiclePassingInfo>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<VehiclePassingInfo>>> baseResponseLcee) {
                updateVehicleInfo(baseResponseLcee);
            }
        });
    }

    /**
     * 开始搜索
     */
    private void loadSearchData() {
        showLoadingDialog(getString(R.string.search_loading));
        if (TextUtils.isEmpty(vehiclePassingInfo.getFeature())) {
            viewModel.queryPhotoFeature(vehiclePassingInfo.getUrl());
        } else {
            viewModel.queryVehicleByFeature(condition);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
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
                case R.id.carImage:
                    //点击查看大图
                    Bundle bundlePicture = new Bundle();
                    ArrayList<String> images = new ArrayList<>();
                    images.add(vehiclePassingInfo.getUrl());
                    bundlePicture.putStringArrayList("images", images);
                    bundlePicture.putInt("position", 0);
                    bundlePicture.putBoolean("isNetworkPath", true);
                    bundlePicture.putBoolean("isShowDeleteButton", false);
                    ActivityLaunchUtil.launchActivity(getActivity(), PhotoPreviewActivity.class, bundlePicture);
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


    private void updateFeatureData(Lcee<BaseResponse<VehiclePassingInfo>> response) {
        switch (response.status) {
            case Error:
                ToastUtil.showError(response.error.getMessage());
                break;
            case Content:
                VehiclePassingInfo info = response.data.getResult();
                condition.setHmCode(info.getFeature());
                viewModel.queryVehicleByFeature(condition);
                break;
        }
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
//                    bundle.putSerializable(Constants.BUNDLE_VEHICLE_QUERY, queryModel);
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
