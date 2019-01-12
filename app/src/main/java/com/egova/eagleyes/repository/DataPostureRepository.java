package com.egova.eagleyes.repository;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.eagleyes.model.respose.PointAlarm;
import com.egova.eagleyes.model.respose.VehicleNum;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * 数据态势
 */
public interface DataPostureRepository {
    //总过车量、今日过车量、最近30天初次入城
    LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getPassFlow();

    //获取手机归属地车辆积分预警数据量（昨天）
    LiveData<Lcee<BaseResponse<PointAlarm>>> getPointAlarm();

    //获取机动车保有量
    LiveData<Lcee<BaseResponse<VehicleNum>>> getVehicleNum();

    //查询昨日的高低峰流量
    LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getVehiclePeak();

    //获取昨天的本省外省机动车流量
    LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getLocalVehicleFlow();

    //查看当前用户所属区域的昨日设备在线数
    LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getEquipmentOnlineNum();

    //研判结果,获取本月经过确认的假牌套牌车数量
    LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getAnalysisResult();

}
