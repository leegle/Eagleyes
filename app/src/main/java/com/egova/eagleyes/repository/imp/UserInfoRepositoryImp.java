package com.egova.eagleyes.repository.imp;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.baselibrary.repository.BaseRepository;
import com.egova.eagleyes.api.UserInfoService;
import com.egova.eagleyes.model.respose.LogHistory;
import com.egova.eagleyes.model.respose.PersonInfo;
import com.egova.eagleyes.repository.UserInfoRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoRepositoryImp extends BaseRepository<UserInfoService> implements UserInfoRepository {

    /**
     * 用户上传图片数据统计
     *
     * @return
     */
    @Override
    public LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> getTakePhotoStatistics() {
        final MutableLiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<NameTextValue<Integer>>>>error(buildNoNetWorkException()));
            return data;
        }
        service.getTakePhotoStatistics(token).enqueue(new Callback<BaseResponse<List<NameTextValue<Integer>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<NameTextValue<Integer>>>> call, Response<BaseResponse<List<NameTextValue<Integer>>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<NameTextValue<Integer>>> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<List<NameTextValue<Integer>>>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<List<NameTextValue<Integer>>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<NameTextValue<Integer>>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<NameTextValue<Integer>>>>error(buildException(t)));
            }
        });
        return data;
    }

    /**
     * 工作记录
     *
     * @return
     */
    @Override
    public LiveData<Lcee<BaseResponse<List<LogHistory>>>> getLogHistory() {
        final MutableLiveData<Lcee<BaseResponse<List<LogHistory>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<LogHistory>>>error(buildNoNetWorkException()));
            return data;
        }
        service.getLogHistory(token).enqueue(new Callback<BaseResponse<List<LogHistory>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<LogHistory>>> call, Response<BaseResponse<List<LogHistory>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<LogHistory>> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<List<LogHistory>>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<List<LogHistory>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<LogHistory>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<LogHistory>>>error(buildException(t)));
            }
        });
        return data;
    }

    //所有警员列表
    @Override
    public LiveData<Lcee<BaseResponse<List<PersonInfo>>>> getAllPersonList() {
        final MutableLiveData<Lcee<BaseResponse<List<PersonInfo>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<PersonInfo>>>error(buildNoNetWorkException()));
            return data;
        }
        service.getAllPersonList(token).enqueue(new Callback<BaseResponse<List<PersonInfo>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<PersonInfo>>> call, Response<BaseResponse<List<PersonInfo>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<PersonInfo>> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<List<PersonInfo>>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<List<PersonInfo>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<PersonInfo>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<PersonInfo>>>error(buildException(t)));
            }
        });
        return data;
    }
}
