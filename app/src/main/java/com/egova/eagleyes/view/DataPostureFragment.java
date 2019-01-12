package com.egova.eagleyes.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egova.baselibrary.base.EgovaFragment;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.baselibrary.util.CalKit;
import com.egova.eagleyes.R;
import com.egova.eagleyes.constants.DataPostureEnum;
import com.egova.eagleyes.databinding.FragmentDataPostureBinding;
import com.egova.eagleyes.databinding.ItemDataDeviceStatusBinding;
import com.egova.eagleyes.databinding.ItemDataResultBinding;
import com.egova.eagleyes.databinding.ItemDataVehicleOutsideBinding;
import com.egova.eagleyes.databinding.ItemDataVehiclePassBinding;
import com.egova.eagleyes.databinding.ItemDataVehiclePeakBinding;
import com.egova.eagleyes.databinding.ItemDataVehicleSumBinding;
import com.egova.eagleyes.databinding.ItemDataVehicleWaringBinding;
import com.egova.eagleyes.databinding.TitleBackBarBinding;
import com.egova.eagleyes.model.respose.PointAlarm;
import com.egova.eagleyes.model.respose.VehicleNum;
import com.egova.eagleyes.vm.DataPostureViewModel;

import java.math.BigDecimal;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * 数据态势
 */
public class DataPostureFragment extends EgovaFragment<FragmentDataPostureBinding, DataPostureViewModel> {

    private TitleBackBarBinding titleBackBarBinding;
    //总过车量、今日过车量、最近30天初次入城
    private ItemDataVehiclePassBinding passFlowBinding;
    //获取手机归属地车辆积分预警数据量（昨天）
    private ItemDataVehicleWaringBinding pointAlarmBinding;
    //机动车保有量
    private ItemDataVehicleSumBinding vehicleNumBinding;
    //早晚高峰期
    private ItemDataVehiclePeakBinding vehiclePeakBinding;
    //外地车
    private ItemDataVehicleOutsideBinding localVehicleBinding;
    //设备状态
    private ItemDataDeviceStatusBinding deviceStatusBinding;
    //判研结果
    private ItemDataResultBinding dataResultBinding;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_data_posture;
    }

    @Override
    public void initView() {
        initTitleBar();
        initOtherBinding();
    }

    private void initOtherBinding() {
        passFlowBinding = binding.passFlow;
        pointAlarmBinding = binding.pointAlarm;
        vehicleNumBinding = binding.vehicleNumBind;
        vehiclePeakBinding = binding.vehiclePeakBind;
        localVehicleBinding = binding.localVehicleFlow;
        deviceStatusBinding = binding.deviceStatusBind;
        dataResultBinding = binding.dataResultBind;
    }

    private void initTitleBar() {
        titleBackBarBinding = binding.titleBar;
        titleBackBarBinding.tvTitle.setText(getString(R.string.data_posture));
        titleBackBarBinding.leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {
        //总过车量、今日过车量、最近30天初次入城
        loadPassFlow();
        binding.vehiclePass.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPassFlow();
            }
        });
        //获取手机归属地车辆积分预警数据量（昨天）
        loadPointAlarm();
        binding.vehicleWaring.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPointAlarm();
            }
        });
        //机动车保有量
        loadVehicleNum();
        binding.vehicleSum.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVehicleNum();
            }
        });
        //早晚高峰期
        loadVehiclePeak();
        binding.vehiclePeak.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVehiclePeak();
            }
        });
        //外地车
        loadLocalVehicleFlow();
        binding.vehicleOutside.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLocalVehicleFlow();
            }
        });
        //设备状态
        loadDeviceStatus();
        binding.deviceStatus.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDeviceStatus();
            }
        });
        //判研结果
        loadDataResult();
        binding.dataResult.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataResult();
            }
        });
    }

    @Override
    public void addVMObserver() {
        //总过车量、今日过车量、最近30天初次入城
        viewModel.getPassFlowData().observe(this, new Observer<Lcee<BaseResponse<List<NameTextValue<String>>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<NameTextValue<String>>>> baseResponseLcee) {
                updatePassFlow(baseResponseLcee);
            }
        });
        //获取手机归属地车辆积分预警数据量（昨天）
        viewModel.getPointAlarmData().observe(this, new Observer<Lcee<BaseResponse<PointAlarm>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<PointAlarm>> responseLcee) {
                updatePointAlarm(responseLcee);
            }
        });
        //机动车保有量
        viewModel.getVehicleNumData().observe(this, new Observer<Lcee<BaseResponse<VehicleNum>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<VehicleNum>> responseLcee) {
                updateVehicleNum(responseLcee);
            }
        });
        //早晚高峰期
        viewModel.getVehiclePeakData().observe(this, new Observer<Lcee<BaseResponse<List<NameTextValue<String>>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<NameTextValue<String>>>> responseLcee) {
                updateVehiclePeak(responseLcee);
            }
        });
        //外地车
        viewModel.getLocalVehicleFlowData().observe(this, new Observer<Lcee<BaseResponse<List<NameTextValue<String>>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<NameTextValue<String>>>> responseLcee) {
                updateLocalVehicleFlow(responseLcee);
            }
        });
        //设置状态
        viewModel.getEquipmentOnlineNumData().observe(this, new Observer<Lcee<BaseResponse<List<NameTextValue<String>>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<NameTextValue<String>>>> responseLcee) {
                updateDeviceStatus(responseLcee);
            }
        });
        //判研结果
        viewModel.getAnalysisResultData().observe(this, new Observer<Lcee<BaseResponse<List<NameTextValue<String>>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<NameTextValue<String>>>> responseLcee) {
                updateDataResult(responseLcee);
            }
        });
    }

    //总过车量、今日过车量、最近30天初次入城
    private void loadPassFlow() {
        binding.vehiclePass.showLoading();
        viewModel.loadPassFlow();
    }

    //总过车量、今日过车量、最近30天初次入城
    private void updatePassFlow(Lcee<BaseResponse<List<NameTextValue<String>>>> response) {
        switch (response.status) {
            case Error:
                binding.vehiclePass.showError();
                break;
            case Content:
                List<NameTextValue<String>> itemList = response.data.getResult();
                binding.vehiclePass.showContent();
                if (itemList == null) {
                    return;
                }
                int size = itemList.size();
                for (int i = 0; i < size; i++) {
                    if (DataPostureEnum.passFlow.totalPassNum.name().equals(itemList.get(i).getName())) {
                        passFlowBinding.totalPassNum.setValue(itemList.get(i).getValue());
                    } else if (DataPostureEnum.passFlow.todayNum.name().equals(itemList.get(i).getName())) {
                        passFlowBinding.todayNum.setValue(itemList.get(i).getValue());
                    } else if (DataPostureEnum.passFlow.firstPassNum.name().equals(itemList.get(i).getName())) {
                        passFlowBinding.firstPassNum.setValue(itemList.get(i).getValue());
                    }
                }
                break;
        }
    }

    //获取手机归属地车辆积分预警数据量（昨天）
    private void loadPointAlarm() {
        binding.vehicleWaring.showLoading();
        viewModel.loadPointAlarm();
    }

    //获取手机归属地车辆积分预警数据量（昨天）
    private void updatePointAlarm(Lcee<BaseResponse<PointAlarm>> response) {
        switch (response.status) {
            case Error:
                binding.vehicleWaring.showError();
                break;
            case Content:
                PointAlarm item = response.data.getResult();
                String str1 = CalKit.multiply(CalKit.divide(item.getValue() + "", item.getTotal() + "", 4).toString(), "100").toString();
                pointAlarmBinding.integral.setValue(str1 + "%");
                BigDecimal noIn = CalKit.subtract(item.getTotal() + "", item.getValue() + "");
                String str2 = CalKit.multiply(CalKit.divide(noIn.toString(), item.getTotal() + "").toString(), "100").toString();
                pointAlarmBinding.noIntegral.setValue(str2 + "%");
                binding.vehicleWaring.showContent();
                break;
        }
    }

    //机动车保有量
    private void loadVehicleNum() {
        binding.vehicleSum.showLoading();
        viewModel.loadVehicleNum();
    }

    //机动车保有量
    private void updateVehicleNum(Lcee<BaseResponse<VehicleNum>> response) {
        switch (response.status) {
            case Error:
                binding.vehicleSum.showError();
                break;
            case Content:
                VehicleNum info = response.data.getResult();
                vehicleNumBinding.totalVehicleNum.setValue(info.getTotalVehicleNum() + "");
                vehicleNumBinding.bigVehicleNum.setValue(info.getBigVehicleNum() + "");
                vehicleNumBinding.smallVehicleNum.setValue(info.getSmallVehicleNum() + "");
                vehicleNumBinding.passFlow.setValue(info.getPassFlow() + "");
                vehicleNumBinding.captureNum.setValue(info.getCaptureNum());
                binding.vehicleSum.showContent();
                break;
        }
    }

    //早晚高峰期
    private void loadVehiclePeak() {
        binding.vehiclePeak.showLoading();
        viewModel.loadVehiclePeak();
    }

    //早晚高峰期
    private void updateVehiclePeak(Lcee<BaseResponse<List<NameTextValue<String>>>> response) {
        switch (response.status) {
            case Error:
                binding.vehiclePeak.showError();
                break;
            case Content:
                List<NameTextValue<String>> result = response.data.getResult();
                binding.vehiclePeak.showContent();
                if (result == null) {
                    return;
                }
                int size = result.size();
                for (int i = 0; i < size; i++) {
                    int firstTime = Integer.valueOf(result.get(i).getText());
                    int secondTime = firstTime + 1;
                    if (result.get(i).getName().equals(DataPostureEnum.vehiclePeak.morning.code)) {
                        //早高峰
                        vehiclePeakBinding.morning.setTitle(firstTime + ":00-" + secondTime + ":00");
                        vehiclePeakBinding.morning.setValue(result.get(i).getValue());
                    } else if (result.get(i).getName().equals(DataPostureEnum.vehiclePeak.afternoon.code)) {
                        //晚高峰
                        vehiclePeakBinding.evening.setTitle(firstTime + ":00-" + secondTime + ":00");
                        vehiclePeakBinding.evening.setValue(result.get(i).getValue());
                    }
                }
                break;
        }
    }

    //外地车
    private void loadLocalVehicleFlow() {
        binding.vehicleOutside.showLoading();
        viewModel.loadLocalVehicleFlow();
    }

    //外地车
    private void updateLocalVehicleFlow(Lcee<BaseResponse<List<NameTextValue<String>>>> response) {
        switch (response.status) {
            case Error:
                binding.vehicleOutside.showError();
                break;
            case Content:
                List<NameTextValue<String>> result = response.data.getResult();
                binding.vehicleOutside.showContent();
                if (result == null) {
                    return;
                }
                int size = result.size();
                BigDecimal count = new BigDecimal("0");
                for (int i = 0; i < size; i++) {
                    count = CalKit.add(count.toString(), result.get(i).getValue());
                    if (DataPostureEnum.localVehicle.localNum.name().equals(result.get(i).getName())) {
                        localVehicleBinding.localNum.setTitle(result.get(i).getValue());
                    } else if (DataPostureEnum.localVehicle.nonLocalNum.name().equals(result.get(i).getName())) {
                        localVehicleBinding.nonLocalNum.setTitle(result.get(i).getValue());
                    }
                }
                localVehicleBinding.localNum.setValue(CalKit.divide(localVehicleBinding.localNum.getTitleText(), count.toString()).toString() + "%");
                localVehicleBinding.nonLocalNum.setValue(CalKit.divide(localVehicleBinding.nonLocalNum.getTitleText(), count.toString()).toString() + "%");

                break;
        }
    }

    //设备状态
    private void loadDeviceStatus() {
        binding.deviceStatus.showLoading();
        viewModel.loadEquipmentOnlineNum();
    }

    //设备状态
    private void updateDeviceStatus(Lcee<BaseResponse<List<NameTextValue<String>>>> response) {
        switch (response.status) {
            case Error:
                binding.deviceStatus.showError();
                break;
            case Content:
                List<NameTextValue<String>> result = response.data.getResult();
                binding.deviceStatus.showContent();
                if (result == null) {
                    return;
                }
                int size = result.size();
                for (int i = 0; i < size; i++) {
                    if (DataPostureEnum.deviceStatus.totalNum.name().equals(result.get(i).getName())) {
                        deviceStatusBinding.totalNum.setValue(result.get(i).getValue());
                    } else if (DataPostureEnum.deviceStatus.onlineNum.name().equals(result.get(i).getName())) {
                        deviceStatusBinding.onlineNum.setValue(result.get(i).getValue());
                    }
                }
                deviceStatusBinding.offlineNum.setValue(CalKit.subtract(deviceStatusBinding.totalNum.getValueText(), deviceStatusBinding.onlineNum.getValueText(), 0).toString());
                break;
        }
    }

    //判研结果
    private void loadDataResult() {
        binding.dataResult.showLoading();
        viewModel.loadAnalysisResult();
    }

    //判研结果
    private void updateDataResult(Lcee<BaseResponse<List<NameTextValue<String>>>> response) {
        switch (response.status) {
            case Error:
                binding.dataResult.showError();
                break;
            case Content:
                binding.dataResult.showContent();
                if (response.data.getResult() == null) {
                    return;
                }
                int size = response.data.getResult().size();
                for (int i = 0; i < size; i++) {
                    NameTextValue<String> item = response.data.getResult().get(i);
                    if (DataPostureEnum.dataResult.totalNum.name().equals(item.getName())) {
                        //总数
                        dataResultBinding.totalNum.setValue(item.getValue());
                    } else if (DataPostureEnum.dataResult.fakeNum.name().equals(item.getName())) {
                        //假牌
                        dataResultBinding.fakeNum.setValue(item.getValue());
                    } else if (DataPostureEnum.dataResult.similarNum.name().equals(item.getName())) {
                        //套牌
                        dataResultBinding.similarNum.setValue(item.getValue());
                    }

                }

                break;
        }

    }

}
