package com.egova.eagleyes.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.egova.baselibrary.base.EgovaFragment;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.baselibrary.model.RegionInfo;
import com.egova.baselibrary.util.ActivityLaunchUtil;
import com.egova.baselibrary.util.DateUtil;
import com.egova.baselibrary.util.MaterialDialogUtils;
import com.egova.baselibrary.util.ToastUtil;
import com.egova.baselibrary.widget.wheelregion.WheelRegion;
import com.egova.baselibrary.widget.wheeltime.ScreenInfo;
import com.egova.baselibrary.widget.wheeltime.WheelMain;
import com.egova.eagleyes.R;
import com.egova.eagleyes.adapter.ColorAdapter;
import com.egova.eagleyes.constants.Constants;
import com.egova.eagleyes.constants.VehicleColorEnum;
import com.egova.eagleyes.databinding.FragmentDispositionOperateBinding;
import com.egova.eagleyes.event.BrandEvent;
import com.egova.eagleyes.helper.DictData;
import com.egova.eagleyes.model.request.DispositionArea;
import com.egova.eagleyes.model.request.DispositionModel;
import com.egova.eagleyes.model.request.DispositionRecipient;
import com.egova.eagleyes.model.respose.DispositionInfo;
import com.egova.eagleyes.model.respose.PersonInfo;
import com.egova.eagleyes.model.respose.PlateInfo;
import com.egova.eagleyes.model.respose.SubBrand;
import com.egova.eagleyes.vm.DictViewModel;
import com.egova.eagleyes.vm.PoliceControlViewModel;
import com.egova.eagleyes.vm.UserViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 我要布控
 */
public class DispositionOperateFragment extends EgovaFragment<FragmentDispositionOperateBinding, PoliceControlViewModel> {

    private DispositionModel dispositionModel;
    private DispositionInfo dispositionInfo;
    private WheelMain wheelMain;//时间控件
    private int selectType = 0;

    private WheelRegion wheelRegion;//省市区联动
    private RegionInfo selectRegion;//已选择的区域

    private DictViewModel dictViewModel;
    private ArrayList<NameTextValue<String>> brandData = new ArrayList<>();//车辆品牌
    private NameTextValue<String> selectBrand;
    private SubBrand subBrand;//车辆子品牌
    private NameTextValue<String> selectSubBrand;

    //车辆颜色
    private RecyclerView colorRecyclerView;
    private List<VehicleColorEnum> colorEnumList = new ArrayList<>();
    private MaterialDialog colorDialog;
    private ColorAdapter colorAdapter;
    private VehicleColorEnum selectColor;

    //布控人员
    private UserViewModel userViewModel;
    private ArrayList<PersonInfo> personList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_disposition_operate;
    }

    @Override
    public void initView() {

        binding.setOnClick(onClickListener);
        binding.bigCar.setSelected(true);
    }

    @Override
    public void initData() {

        selectRegion = DictData.getInstance().getRegionInfo().getChildren().get(0).getChildren().get(0);
        dispositionModel = new DispositionModel();
        dispositionInfo = new DispositionInfo();
        dispositionModel.setDisposition(dispositionInfo);

        dispositionInfo.setStartDate(DateUtil.dateFormat(new Date()));
        dispositionInfo.setEndDate(DateUtil.dateFormat(DateUtil.getDateAddDays(7)));
        dispositionInfo.setStartAlarmPeriod("00:00:00");
        dispositionInfo.setEndAlarmPeriod("23:59:59");

        dispositionInfo.setPlateType("01");//01-大车，02-小车

        binding.receivePerson.setContent("请选择");
        binding.brand.setContent("不限");
        binding.subBrand.setContent("不限");
        binding.vehicleColor.setContent("不限");
        binding.startDate.setText(dispositionInfo.getStartDate());
        binding.endDate.setText(dispositionInfo.getEndDate());
        binding.startTime.setText("00:00");
        binding.endTime.setText("23:59");
        setSelect(R.id.noSendMsg);

        colorEnumList.add(VehicleColorEnum.VehicleColor00);
        colorEnumList.add(VehicleColorEnum.VehicleColor01);
        colorEnumList.add(VehicleColorEnum.VehicleColor02);
        colorEnumList.add(VehicleColorEnum.VehicleColor03);
        colorEnumList.add(VehicleColorEnum.VehicleColor04);
        colorEnumList.add(VehicleColorEnum.VehicleColor05);
        colorEnumList.add(VehicleColorEnum.VehicleColor06);
        colorEnumList.add(VehicleColorEnum.VehicleColor07);
        colorEnumList.add(VehicleColorEnum.VehicleColor08);
        colorEnumList.add(VehicleColorEnum.VehicleColor09);
        colorEnumList.add(VehicleColorEnum.VehicleColor10);
        colorEnumList.add(VehicleColorEnum.VehicleColor11);
    }

    @Override
    public void addVMObserver() {
        //添加布控
        viewModel.getAddDispositionData().observe(this, new Observer<Lcee<BaseResponse<Boolean>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<Boolean>> baseResponseLcee) {
                dismissLoadingDialog();
                switch (baseResponseLcee.status) {
                    case Error:
                        ToastUtil.showError(baseResponseLcee.error.getMessage());
                        break;
                    case Content:
                        ToastUtil.showSuccess(baseResponseLcee.data.getMessage());
                        break;
                }
            }
        });
        dictViewModel = createViewModel(this, DictViewModel.class);
        //车辆品牌
        dictViewModel.getBrandData().observe(this, new Observer<Lcee<BaseResponse<List<NameTextValue<String>>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<NameTextValue<String>>>> responseLcee) {
                dismissLoadingDialog();
                switch (responseLcee.status) {
                    case Error:
                        ToastUtil.showError(responseLcee.error.getMessage());
                        break;
                    case Content:
                        brandData.clear();
                        brandData.addAll(responseLcee.data.getResult());
                        jumpToBrand();
                        break;
                }
            }
        });
        //车辆子品牌
        dictViewModel.getSubBrandData().observe(this, new Observer<Lcee<BaseResponse<SubBrand>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<SubBrand>> baseResponseLcee) {
                dismissLoadingDialog();
                switch (baseResponseLcee.status) {
                    case Error:
                        ToastUtil.showError(baseResponseLcee.error.getMessage());
                        break;
                    case Content:
                        subBrand = baseResponseLcee.data.getResult();
                        jumpToSubBrand();
                        break;
                }
            }
        });
        //报警接收人
        userViewModel = createViewModel(this, UserViewModel.class);
        userViewModel.getPersonListData().observe(this, new Observer<Lcee<BaseResponse<List<PersonInfo>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<PersonInfo>>> baseResponseLcee) {
                dismissLoadingDialog();
                switch (baseResponseLcee.status) {
                    case Error:
                        ToastUtil.showError(baseResponseLcee.error.getMessage());
                        break;
                    case Content:
                        personList.clear();
                        personList.addAll(baseResponseLcee.data.getResult());
                        jumpToPerson();
                        break;
                }

            }
        });
    }

    private void setSelect(int viewId) {
        switch (viewId) {
            case R.id.noSendMsg:
                binding.noSendMsg.setSelected(true);
                binding.sendMsg.setSelected(false);
                dispositionInfo.setNoticing(false);
                break;
            case R.id.sendMsg:
                binding.noSendMsg.setSelected(false);
                binding.sendMsg.setSelected(true);
                dispositionInfo.setNoticing(true);
                break;

        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.noSendMsg://短信发送
                case R.id.sendMsg:
                    setSelect(v.getId());
                    break;
                case R.id.bigCar:
                    binding.bigCar.setSelected(true);
                    binding.smallCar.setSelected(false);
                    dispositionInfo.setPlateType("01");//01-大车，02-小车
                    break;
                case R.id.smallCar:
                    binding.bigCar.setSelected(false);
                    binding.smallCar.setSelected(true);
                    dispositionInfo.setPlateType("02");//01-大车，02-小车
                    break;
                case R.id.startDate://布控日期
                    selectType = 0;
                    showDateDialog(getString(R.string.start_time), DateUtil.dateFormat(dispositionInfo.getStartDate()));
                    break;
                case R.id.endDate:
                    selectType = 1;
                    showDateDialog(getString(R.string.start_time), DateUtil.dateFormat(dispositionInfo.getEndDate()));
                    break;
                case R.id.startTime://报警时段
                    selectType = 0;
                    showTimeDialog(getString(R.string.start_time), DateUtil.dateFormatMis(DateUtil.dateFormat(new Date()) + " " + dispositionInfo.getStartAlarmPeriod()));
                    break;
                case R.id.endTime:
                    selectType = 1;
                    showTimeDialog(getString(R.string.start_time), DateUtil.dateFormatMis(DateUtil.dateFormat(new Date()) + " " + dispositionInfo.getEndAlarmPeriod()));
                    break;
                case R.id.brand://车辆品牌
                    if (brandData == null || brandData.size() == 0) {
                        loadBrand();
                    } else {
                        jumpToBrand();
                    }
                    break;
                case R.id.subBrand://车辆子品牌
                    if (selectBrand == null || selectBrand.getName().equalsIgnoreCase("不限")) {
                        ToastUtil.showNormal("请选择车辆品牌");
                    } else if (subBrand == null) {
                        loadSubBrand();
                    } else {
                        jumpToSubBrand();
                    }
                    break;
                case R.id.vehicleColor://车辆颜色
                    showVehicleColorDialog();
                    break;
                case R.id.region://区域
                    showRegionDialog(getString(R.string.search_area));
                    break;
                case R.id.receivePerson://报警接收人
                    if (personList == null || personList.size() == 0) {
                        loadPerson();
                    } else {
                        jumpToPerson();
                    }
                    break;
                case R.id.operateBtn://布控
                    startOperate();
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
    private void showDateDialog(String title, Date date) {
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
                            if (DateUtil.compareDate(dispositionInfo.getEndDate(), wheelMain.getTime(), "yyyy-MM-dd") < 0) {
                                ToastUtil.showNormal("起始时段不能大于结束时段");
                            } else {
                                dispositionInfo.setStartDate(wheelMain.getTime());
                                binding.startDate.setText(dispositionInfo.getStartDate());
                                dialog.dismiss();
                            }
                        } else {
                            if (DateUtil.compareDate(wheelMain.getTime(), dispositionInfo.getStartDate(), "yyyy-MM-dd") < 0) {
                                ToastUtil.showNormal("结束时段不能小于起始时段");
                            } else if (DateUtil.compareDate(wheelMain.getTime(), DateUtil.dateFormat(new Date()), "yyyy-MM-dd") <= 0) {
                                ToastUtil.showNormal("结束时段不能小于今天");
                            } else {
                                dispositionInfo.setEndDate(wheelMain.getTime());
                                binding.endDate.setText(dispositionInfo.getEndDate());
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

    /**
     * 弹窗选择报警时间段
     *
     * @param title
     * @param date
     */
    private void showTimeDialog(String title, Date date) {
        final LinearLayout contentView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.time_wheel_layout, null);
        contentView.findViewById(R.id.year).setVisibility(View.GONE);
        contentView.findViewById(R.id.month).setVisibility(View.GONE);
        contentView.findViewById(R.id.day).setVisibility(View.GONE);

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
                            if (DateUtil.compareDate(dispositionInfo.getEndAlarmPeriod(), wheelMain.getMinTime(), "HH:mm") < 0) {
                                ToastUtil.showNormal("起始时段不能大于结束时段");
                            } else {
                                dispositionInfo.setStartAlarmPeriod(wheelMain.getMinTime() + ":00");
                                binding.startTime.setText(wheelMain.getMinTime());
                                dialog.dismiss();
                            }
                        } else {
                            if (DateUtil.compareDate(wheelMain.getMinTime(), dispositionInfo.getStartAlarmPeriod(), "HH:mm") < 0) {
                                ToastUtil.showNormal("结束时段不能小于起始时段");
                            } else {
                                dispositionInfo.setEndAlarmPeriod(wheelMain.getMinTime() + ":59");
                                binding.endTime.setText(wheelMain.getMinTime());
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

    //弹窗选择颜色
    private void showVehicleColorDialog() {
        if (colorRecyclerView == null) {
            buildColorView();
        }
        colorDialog = MaterialDialogUtils.getCustomDialog(getActivity(), colorRecyclerView, "车辆颜色")
                .cancelable(true)
                .show();

    }

    private void buildColorView() {
        colorRecyclerView = (RecyclerView) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_recyclerview, null);
        colorRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        colorAdapter = new ColorAdapter(R.layout.item_color, colorEnumList);
        colorRecyclerView.setAdapter(colorAdapter);
        colorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectColor = colorEnumList.get(position);
                binding.vehicleColor.setContent(selectColor.name);
                colorDialog.dismiss();
            }
        });
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
                            binding.region.setContent(selectRegion.getParentName());
                        } else if ("不限".equals(selectRegion.getParentName())) {
                            binding.region.setContent(selectRegion.getParentName());
                        } else {
                            binding.region.setContent(selectRegion.getName());
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

    //车辆品牌
    private void loadBrand() {
        showLoadingDialog();
        dictViewModel.loadBrand();

    }

    //车辆子品牌
    private void loadSubBrand() {
        showLoadingDialog();
        dictViewModel.loadSubBrand(selectBrand.getValue());
    }

    private void loadPerson() {
        showLoadingDialog();
        userViewModel.loadAllPerson();
    }

    private void jumpToBrand() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.BUNDLE_KEY, brandData);
        bundle.putString(Constants.BUNDLE_TYPE, BrandEvent.brandType);
        ActivityLaunchUtil.launchContainerActivity(getActivity(), BrandPinMultiSearchFragment.class.getCanonicalName(), bundle);
    }

    private void jumpToSubBrand() {
        ArrayList<NameTextValue<String>> datas = new ArrayList<>();
        int size = subBrand.getSubbrands() != null ? subBrand.getSubbrands().size() : 0;
        if (size == 0) {
            datas.add(subBrand.getBrand());
        } else {
            for (int i = 0; i < size; i++) {
                int subSize = subBrand.getSubbrands().get(i).getYears() != null ? subBrand.getSubbrands().get(i).getYears().size() : 0;
                if (subSize == 0) {
                    datas.add(subBrand.getSubbrands().get(i).getSubbrand());
                } else {
                    for (int j = 0; j < subSize; j++) {
                        datas.add(subBrand.getSubbrands().get(i).getYears().get(j));
                    }
                }
            }
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.BUNDLE_KEY, datas);
        bundle.putString(Constants.BUNDLE_TYPE, BrandEvent.subBrandType);
        ActivityLaunchUtil.launchContainerActivity(getActivity(), BrandPinMultiSearchFragment.class.getCanonicalName(), bundle);
    }

    private void jumpToPerson() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.BUNDLE_USER, personList);
        ActivityLaunchUtil.launchContainerActivity(getActivity(), PersonMultiSearchFragment.class.getCanonicalName(), bundle);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleBrandEventBus(BrandEvent event) {
        if (BrandEvent.brandType.equals(event.getType())) {
            if (selectBrand != null && selectBrand.getText().equalsIgnoreCase(event.getNameTextValue().getText())) {
                return;
            }
            selectBrand = event.getNameTextValue();
            binding.brand.setContent(selectBrand.getText());
            binding.subBrand.setContent("不限");
            subBrand = null;
            selectSubBrand = null;
        } else {

            if (selectSubBrand != null && selectSubBrand.getText().equalsIgnoreCase(event.getNameTextValue().getText())) {
                return;
            }
            selectSubBrand = event.getNameTextValue();
            binding.subBrand.setContent(selectSubBrand.getText());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlePersonEventBus(List<PersonInfo> event) {
        int size = event.size();
        int hasChooseSize = 0;
        for (int i = 0; i < size; i++) {
            if (event.get(i).getHasSelected() != null && event.get(i).getHasSelected()) {
                hasChooseSize = hasChooseSize + 1;
            }
        }

        if (hasChooseSize == 0) {
            binding.receivePerson.setContent("请选择");
        } else {
            binding.receivePerson.setContent("已选择" + hasChooseSize + "人");
        }
        personList.clear();
        personList.addAll(event);
    }

    private void startOperate() {
        if (TextUtils.isEmpty(binding.plateEdit.getText())) {
            ToastUtil.showError("请输入布控车牌号码");
            return;
        }
        if (TextUtils.isEmpty(binding.reasonEdit.getText())) {
            ToastUtil.showError("请输入布控原因");
            return;
        }
        //短信接收人员
        List<DispositionRecipient> recipients = new ArrayList<>();
        int size = personList.size();
        for (int i = 0; i < size; i++) {
            if (personList.get(i).getHasSelected() != null && personList.get(i).getHasSelected()) {
                DispositionRecipient item = new DispositionRecipient();
                item.setRecipientId(personList.get(i).getPersonNo());
                recipients.add(item);
            }
        }
        if (recipients.size() == 0) {
            ToastUtil.showError("请选择报警接收人");
            return;
        }
        dispositionModel.setRecipients(recipients);

        showLoadingDialog("正在布控...");
        //车牌号
        List<PlateInfo> plates = new ArrayList<>();
        PlateInfo plateInfo = new PlateInfo();
        plateInfo.setPlate(binding.plateEdit.getText().toString());
        plates.add(plateInfo);
        dispositionModel.setPlates(plates);
        //车辆品牌
        if (selectBrand == null) {
            dispositionInfo.setBrand(null);
            dispositionInfo.setChildBrand(null);
        } else {
            dispositionInfo.setBrand(selectBrand.getValue());
            if (selectSubBrand == null) {
                dispositionInfo.setChildBrand(null);
            } else {
                dispositionInfo.setChildBrand(selectSubBrand.getValue());
            }
        }

        //布控原因
        dispositionInfo.setCause(binding.reasonEdit.getText().toString());
        //车辆颜色
        if (selectColor == null || selectColor.name.equals("不限")) {
            dispositionInfo.setVehicleColor(null);
        } else {
            dispositionInfo.setVehicleColor(selectColor.code);
        }
        //布控区域
        List<DispositionArea> areas = new ArrayList<>();
        DispositionArea areaId = new DispositionArea();
        areaId.setPointId(selectRegion.getId());
        areas.add(areaId);
        dispositionModel.setAreas(areas);
        viewModel.addDisposition(dispositionModel);

    }
}
