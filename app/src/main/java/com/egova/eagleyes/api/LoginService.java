package com.egova.eagleyes.api;

import com.egova.eagleyes.model.request.TFLoginRequest;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.eagleyes.model.respose.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginService {

    /**
     * TF卡登陆
     *
     * @param tfLoginRequest
     * @return
     */
    @POST("/login/tfcard")
    @Headers("Content-Type:application/json")
    Call<BaseResponse<LoginResponse>> tfLogin(@Body TFLoginRequest tfLoginRequest);
}
