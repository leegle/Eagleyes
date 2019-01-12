package com.egova.eagleyes.repository.imp;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.repository.BaseRepository;
import com.egova.baselibrary.util.CommonConstants;
import com.egova.baselibrary.util.SharedPreferencesUtil;
import com.egova.eagleyes.api.LoginService;
import com.egova.eagleyes.model.request.TFLoginRequest;
import com.egova.eagleyes.model.respose.LoginResponse;
import com.egova.eagleyes.repository.LoginRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepositoryImp extends BaseRepository<LoginService> implements LoginRepository {

    @Override
    public LiveData<Lcee<BaseResponse<LoginResponse>>> tfLogin(TFLoginRequest loginRequest) {
        final MutableLiveData<Lcee<BaseResponse<LoginResponse>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<LoginResponse>>error(buildNoNetWorkException()));
            return data;
        }
        service.tfLogin(loginRequest).enqueue(new Callback<BaseResponse<LoginResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<LoginResponse>> call, Response<BaseResponse<LoginResponse>> response) {
                if (response.code() == 200) {
                    BaseResponse<LoginResponse> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<LoginResponse>>error(buildServerException(result.getMessage())));
                    } else {
                        SharedPreferencesUtil.saveStringAsSP(CommonConstants.SP_TOKEN, result.getResult().getToken_type() + result.getResult().getAccess_token());
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<LoginResponse>>error(buildException(null)));
                }

            }

            @Override
            public void onFailure(Call<BaseResponse<LoginResponse>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<LoginResponse>>error(buildException(t)));
            }
        });
        return data;
    }
}
