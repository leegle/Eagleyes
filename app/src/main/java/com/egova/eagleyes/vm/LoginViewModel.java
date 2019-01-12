package com.egova.eagleyes.vm;

import android.app.Application;

import com.egova.baselibrary.model.Lcee;
import com.egova.eagleyes.model.request.TFLoginRequest;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.eagleyes.model.respose.LoginResponse;
import com.egova.eagleyes.repository.LoginRepository;
import com.egova.eagleyes.repository.imp.LoginRepositoryImp;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

/**
 * 登录模块
 */
public class LoginViewModel extends AndroidViewModel {

    private LoginRepository loginRepository;

    //tf卡登陆
    private MutableLiveData<TFLoginRequest> tfLoginRequest = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<LoginResponse>>> tfLoginData;//

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository=new LoginRepositoryImp();
    }

    public LiveData<Lcee<BaseResponse<LoginResponse>>> getTfLoginData() {
        if(tfLoginData==null){
            tfLoginData=Transformations.switchMap(tfLoginRequest, new Function<TFLoginRequest, LiveData<Lcee<BaseResponse<LoginResponse>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<LoginResponse>>> apply(TFLoginRequest input) {
                    return loginRepository.tfLogin(input);
                }
            });
        }
        return tfLoginData;
    }

    //TF卡登陆
    public void TFLogin(TFLoginRequest tfLoginRequest){
        this.tfLoginRequest.setValue(tfLoginRequest);
    }

}
