package com.egova.eagleyes.repository;

import com.egova.baselibrary.model.Lcee;
import com.egova.eagleyes.model.request.TFLoginRequest;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.eagleyes.model.respose.LoginResponse;

import androidx.lifecycle.LiveData;

public interface LoginRepository {
    //TF卡登陆
    LiveData<Lcee<BaseResponse<LoginResponse>>> tfLogin(TFLoginRequest loginRequest);
}
