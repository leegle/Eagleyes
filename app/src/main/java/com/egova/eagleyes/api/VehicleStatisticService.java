package com.egova.eagleyes.api;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.eagleyes.model.request.SearchCondition;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 车辆统计
 */
public interface VehicleStatisticService {

    //活跃区县
    @POST("/m/statistic/active/city&state=latest")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<NameTextValue<Integer>>>> queryPortraitByRegion(@Header("Authorization") String token, @Body SearchCondition searchCondition);

    //活跃时间
    @POST("/m/statistic/active/time&state=latest")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<NameTextValue<Integer>>>> queryPortraitByTime(@Header("Authorization") String token, @Body SearchCondition searchCondition);

    //活跃卡口
    @POST("/m/statistic/active/toll&state=latest")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<NameTextValue<Integer>>>> queryPortraitByTollgate(@Header("Authorization") String token, @Body SearchCondition searchCondition);


}
