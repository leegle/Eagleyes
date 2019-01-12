package com.egova.eagleyes.api;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.eagleyes.model.respose.PointAlarm;
import com.egova.eagleyes.model.respose.VehicleNum;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 * 数据态势
 */
public interface DataPostureService {

    //总过车量、今日过车量、最近30天初次入城
    @GET("/m/statistic/vehicle/flow&state=days")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<NameTextValue<String>>>> getPassFlow(@Header("Authorization") String token);

    //获取手机归属地车辆积分预警数据量（昨天）
    @GET("/m/statistic/point/alarm&state=yesterday")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<PointAlarm>> getPointAlarm(@Header("Authorization") String token);

    //获取机动车保有量
    @GET("/m/statistic/vehicle/num&state=month")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<VehicleNum>> getVehicleNum(@Header("Authorization") String token);

    //查询昨日的高低峰流量
    @GET("/m/statistic/vehicle/peak&state=yesterday")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<NameTextValue<String>>>> getVehiclePeak(@Header("Authorization") String token);

    //获取昨天的本省外省机动车流量
    @GET("/m/statistic/vehicle/flow/province&state=yesterday")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<NameTextValue<String>>>> getLocalVehicleFlow(@Header("Authorization") String token);

    //查看当前用户所属区域的昨日设备在线数
    @GET("/m/statistic/equipment/online&state=yesterday")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<NameTextValue<String>>>> getEquipmentOnlineNum(@Header("Authorization") String token);

    //研判结果,获取本月经过确认的假牌套牌车数量
    @GET("/m/statistic/vehicle/fake&state=month")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<NameTextValue<String>>>> getAnalysisResult(@Header("Authorization") String token);


}
