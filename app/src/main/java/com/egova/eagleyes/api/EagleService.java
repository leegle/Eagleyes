package com.egova.eagleyes.api;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.QueryModel;
import com.egova.eagleyes.model.request.SearchCondition;
import com.egova.eagleyes.model.respose.VehiclePassingInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EagleService {

    //全文或者精确搜索
    @POST("/m/vehclepass/page")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<VehiclePassingInfo>>> queryVehicleBySearch(@Header("Authorization") String token, @Body QueryModel<SearchCondition> queryModel);

    //根据车辆图片地址搜索车辆信息
    @GET("/m/vehclepass/map-search")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<VehiclePassingInfo>>> queryVehicleByImage(@Header("Authorization") String token, @Query("url") String url);

    //获得车辆图片获得图片的识别码
    @GET("/m/vehclepass/identify")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<VehiclePassingInfo>> queryPictureFeature(@Header("Authorization") String token, @Query("url") String url);

    //根据图片特征码进行搜索车辆信息
    @POST("/m/vehclepass/feature")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<VehiclePassingInfo>>> queryVehicleByFeature(@Header("Authorization") String token, @Body SearchCondition condition);


}
