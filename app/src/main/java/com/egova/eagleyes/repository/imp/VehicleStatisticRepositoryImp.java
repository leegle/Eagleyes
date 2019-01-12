package com.egova.eagleyes.repository.imp;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.baselibrary.repository.BaseRepository;
import com.egova.eagleyes.api.VehicleStatisticService;
import com.egova.eagleyes.model.request.SearchCondition;
import com.egova.eagleyes.repository.VehicleStatisticRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 车辆统计
 */
public class VehicleStatisticRepositoryImp extends BaseRepository<VehicleStatisticService> implements VehicleStatisticRepository {

    //统计区县
    @Override
    public LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> queryPortraitByRegion(SearchCondition searchCondition) {
        final MutableLiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<NameTextValue<Integer>>>>error(buildNoNetWorkException()));
            return data;
        }

        service.queryPortraitByRegion(token, searchCondition).enqueue(new Callback<BaseResponse<List<NameTextValue<Integer>>>>() {
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

    //活跃时间
    @Override
    public LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> queryPortraitByTime(SearchCondition searchCondition) {
        final MutableLiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<NameTextValue<Integer>>>>error(buildNoNetWorkException()));
            return data;
        }
        service.queryPortraitByTime(token, searchCondition).enqueue(new Callback<BaseResponse<List<NameTextValue<Integer>>>>() {
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

    //活跃卡口
    @Override
    public LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> queryPortraitByTollgate(SearchCondition searchCondition) {
        final MutableLiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<NameTextValue<Integer>>>>error(buildNoNetWorkException()));
            return data;
        }
        service.queryPortraitByTollgate(token, searchCondition).enqueue(new Callback<BaseResponse<List<NameTextValue<Integer>>>>() {
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
}
