package com.egova.eagleyes.vm;

import android.app.Application;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.eagleyes.model.respose.PointAlarm;
import com.egova.eagleyes.model.respose.VehicleNum;
import com.egova.eagleyes.repository.DataPostureRepository;
import com.egova.eagleyes.repository.imp.DataPostureRepositoryImp;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

/**
 * 数据态势
 */
public class DataPostureViewModel extends AndroidViewModel {

    private DataPostureRepository repository;
    //总过车量、今日过车量、最近30天初次入城
    private MutableLiveData<String> passFlowParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> passFlowData;
    //获取手机归属地车辆积分预警数据量（昨天）
    private MutableLiveData<String> pointAlarmParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<PointAlarm>>> pointAlarmData;
    //获取机动车保有量
    private MutableLiveData<String> vehicleNumParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<VehicleNum>>> vehicleNumData;
    //查询昨日的高低峰流量
    private MutableLiveData<String> vehiclePeakParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> vehiclePeakData;
    //获取昨天的本省外省机动车流量
    private MutableLiveData<String> localVehicleFlowParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> localVehicleFlowData;
    //查看当前用户所属区域的昨日设备在线数
    private MutableLiveData<String> equipmentOnlineNumParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> equipmentOnlineNumData;
    //研判结果,获取本月经过确认的假牌套牌车数量
    private MutableLiveData<String> analysisResultParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> analysisResultData;


    public DataPostureViewModel(@NonNull Application application) {
        super(application);
        repository = new DataPostureRepositoryImp();
    }

    //总过车量、今日过车量、最近30天初次入城
    public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getPassFlowData() {
        if (passFlowData == null) {
            passFlowData = Transformations.switchMap(passFlowParam, new Function<String, LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> apply(String input) {
                    return repository.getPassFlow();
                }
            });
        }
        return passFlowData;
    }

    //获取手机归属地车辆积分预警数据量（昨天）
    public LiveData<Lcee<BaseResponse<PointAlarm>>> getPointAlarmData() {
        if (pointAlarmData == null) {
            pointAlarmData = Transformations.switchMap(pointAlarmParam, new Function<String, LiveData<Lcee<BaseResponse<PointAlarm>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<PointAlarm>>> apply(String input) {
                    return repository.getPointAlarm();
                }
            });
        }
        return pointAlarmData;
    }

    //获取机动车保有量
    public LiveData<Lcee<BaseResponse<VehicleNum>>> getVehicleNumData() {
        if (vehicleNumData == null) {
            vehicleNumData = Transformations.switchMap(vehicleNumParam, new Function<String, LiveData<Lcee<BaseResponse<VehicleNum>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<VehicleNum>>> apply(String input) {
                    return repository.getVehicleNum();
                }
            });
        }
        return vehicleNumData;
    }

    //查询昨日的高低峰流量
    public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getVehiclePeakData() {
        if (vehiclePeakData == null) {
            vehiclePeakData = Transformations.switchMap(vehiclePeakParam, new Function<String, LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> apply(String input) {
                    return repository.getVehiclePeak();
                }
            });
        }
        return vehiclePeakData;
    }

    //获取昨天的本省外省机动车流量
    public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getLocalVehicleFlowData() {
        if (localVehicleFlowData == null) {
            localVehicleFlowData = Transformations.switchMap(localVehicleFlowParam, new Function<String, LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> apply(String input) {
                    return repository.getLocalVehicleFlow();
                }
            });
        }
        return localVehicleFlowData;
    }

    //查看当前用户所属区域的昨日设备在线数
    public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getEquipmentOnlineNumData() {
        if (equipmentOnlineNumData == null) {
            equipmentOnlineNumData = Transformations.switchMap(equipmentOnlineNumParam, new Function<String, LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> apply(String input) {
                    return repository.getEquipmentOnlineNum();
                }
            });
        }
        return equipmentOnlineNumData;
    }

    //研判结果,获取本月经过确认的假牌套牌车数量
    public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getAnalysisResultData() {
        if (analysisResultData == null) {
            analysisResultData = Transformations.switchMap(analysisResultParam, new Function<String, LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> apply(String input) {
                    return repository.getAnalysisResult();
                }
            });
        }
        return analysisResultData;
    }

    //总过车量、今日过车量、最近30天初次入城
    public void loadPassFlow() {
        passFlowParam.setValue("");
    }

    //获取手机归属地车辆积分预警数据量（昨天）
    public void loadPointAlarm() {
        pointAlarmParam.setValue("");
    }

    //获取机动车保有量
    public void loadVehicleNum() {
        vehicleNumParam.setValue("");
    }

    //查询昨日的高低峰流量
    public void loadVehiclePeak() {
        vehiclePeakParam.setValue("");
    }

    //获取昨天的本省外省机动车流量
    public void loadLocalVehicleFlow() {
        localVehicleFlowParam.setValue("");
    }

    //查看当前用户所属区域的昨日设备在线数
    public void loadEquipmentOnlineNum() {
        equipmentOnlineNumParam.setValue("");
    }

    //研判结果,获取本月经过确认的假牌套牌车数量
    public void loadAnalysisResult() {
        analysisResultParam.setValue("");
    }
}
