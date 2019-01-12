package com.egova.eagleyes.api;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.eagleyes.model.respose.LogHistory;
import com.egova.eagleyes.model.respose.PersonInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface UserInfoService {


    /**
     * 我的--照片上传数据统计
     *
     * @return
     */
    @GET("/m/statistic/takephoto/num")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<NameTextValue<Integer>>>> getTakePhotoStatistics(@Header("Authorization") String token);

    /**
     * 我的--工作记录
     *
     * @param token
     * @return
     */
    @GET("/m/log/history")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<LogHistory>>> getLogHistory(@Header("Authorization") String token);

    //查询所有的警员
    @GET("/m/person/all")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<List<PersonInfo>>> getAllPersonList(@Header("Authorization") String token);

    //排行榜

}

