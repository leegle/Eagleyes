package com.egova.eagleyes.api;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.QueryModel;
import com.egova.eagleyes.model.request.DispositionCondition;
import com.egova.eagleyes.model.request.DispositionModel;
import com.egova.eagleyes.model.request.PoliceCondition;
import com.egova.eagleyes.model.respose.DispositionAlarm;
import com.egova.eagleyes.model.respose.DispositionInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 车辆布控模块
 */
public interface PoliceService {

    //报警管理查询
    @POST("/m/alarm/page")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<DispositionAlarm>>> queryDispositionAlarm(@Header("Authorization") String token, @Body QueryModel<PoliceCondition> queryModel);

    //将报警置为已处理
    @GET("/m/alarm/handle")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<Boolean>> handlerAlarm(@Header("Authorization") String token, @Query("id") String alarmId);

    //布控列表查询
    @POST("/m/disposition/page")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<DispositionInfo>>> queryDispositionList(@Header("Authorization") String token, @Body QueryModel<DispositionCondition> queryModel);

    //撤销布控
    @GET("/m/disposition/cancel")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<Boolean>> cancelDisposition(@Header("Authorization") String token, @Query("id") String dispositionId);

    //新增布控
    //将报警置为已处理
    @POST("/m/disposition/add")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<Boolean>> addDisposition(@Header("Authorization") String token, @Body DispositionModel dispositionModel);

}
