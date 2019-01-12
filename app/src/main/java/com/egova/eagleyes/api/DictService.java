package com.egova.eagleyes.api;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.baselibrary.model.RegionInfo;
import com.egova.eagleyes.model.respose.SubBrand;
import com.egova.eagleyes.model.respose.Tollgate;
import com.egova.eagleyes.model.respose.TollgateType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * 获取其他字典数据
 * 1./dictionary/kind/plateLetter 属于get请求，kind后面跟的是数据库配的名字，这里plateLetter就是数据库里的名字
 * 2./dictionary/type/bean  属于get请求，bean指的是工程里面配置的@Bean对象
 */
public interface DictService {
    //获取省市区数据
    @GET("/region/list&state=tree")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<RegionInfo>> queryRegionData();

    //获取卡口类型
    @GET("/dictionary/kind/tollgateType")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<TollgateType>>> queryTollgateType();

    //获取所有卡口数据
    @GET("/m/tollgate/all")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<Tollgate>>> queryTollgate(@Header("Authorization") String token);

    //获取车辆品牌
    @GET("/dictionary/type/VehicleBrandProvider")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<NameTextValue<String>>>> queryBrandType(@Header("Authorization") String token);

    //获取车辆子品牌
    @GET("/m/vehicle-subbrand/scope/{id}")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<SubBrand>> querySubBrandType(@Header("Authorization") String token, @Path("id") String id);

    //获取车辆颜色
    @GET("/dictionary/kind/vehicleColor")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<NameTextValue<String>>>> queryVehicleColor(@Header("Authorization") String token);
}
