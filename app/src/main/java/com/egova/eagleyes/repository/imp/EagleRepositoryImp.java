package com.egova.eagleyes.repository.imp;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.QueryModel;
import com.egova.baselibrary.repository.BaseRepository;
import com.egova.eagleyes.api.EagleService;
import com.egova.eagleyes.model.request.SearchCondition;
import com.egova.eagleyes.model.respose.VehiclePassingInfo;
import com.egova.eagleyes.repository.EagleRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EagleRepositoryImp extends BaseRepository<EagleService> implements EagleRepository {

    //根据条件进行搜索
    @Override
    public LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> queryVehicleBySearch(QueryModel<SearchCondition> queryModel) {
        final MutableLiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<VehiclePassingInfo>>>error(buildNoNetWorkException()));
            return data;
        }
        service.queryVehicleBySearch(token, queryModel).enqueue(new Callback<BaseResponse<List<VehiclePassingInfo>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<VehiclePassingInfo>>> call, Response<BaseResponse<List<VehiclePassingInfo>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<VehiclePassingInfo>> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<List<VehiclePassingInfo>>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<List<VehiclePassingInfo>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<VehiclePassingInfo>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<VehiclePassingInfo>>>error(buildException(t)));
            }
        });
        return data;
    }

    //根据车辆图片搜索车辆信息
    @Override
    public LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> queryVehicleByImage(String imageUrl) {
        final MutableLiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<VehiclePassingInfo>>>error(buildNoNetWorkException()));
            return data;
        }
        service.queryVehicleByImage(token, imageUrl).enqueue(new Callback<BaseResponse<List<VehiclePassingInfo>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<VehiclePassingInfo>>> call, Response<BaseResponse<List<VehiclePassingInfo>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<VehiclePassingInfo>> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<List<VehiclePassingInfo>>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<List<VehiclePassingInfo>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<VehiclePassingInfo>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<VehiclePassingInfo>>>error(buildException(t)));
            }
        });
        return data;
    }

    @Override
    public LiveData<Lcee<BaseResponse<VehiclePassingInfo>>> queryPictureFeature(String imageUrl) {
        final MutableLiveData<Lcee<BaseResponse<VehiclePassingInfo>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<VehiclePassingInfo>>error(buildNoNetWorkException()));
            return data;
        }
        service.queryPictureFeature(token, imageUrl).enqueue(new Callback<BaseResponse<VehiclePassingInfo>>() {
            @Override
            public void onResponse(Call<BaseResponse<VehiclePassingInfo>> call, Response<BaseResponse<VehiclePassingInfo>> response) {
                if (response.code() == 200) {
                    BaseResponse<VehiclePassingInfo> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<VehiclePassingInfo>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<VehiclePassingInfo>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<VehiclePassingInfo>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<VehiclePassingInfo>>error(buildException(t)));
            }
        });
        return data;
    }

    // 根据图片识别码进行搜索
    @Override
    public LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> queryVehicleByFeature(SearchCondition searchCondition) {
        final MutableLiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<VehiclePassingInfo>>>error(buildNoNetWorkException()));
            return data;
        }
        service.queryVehicleByFeature(token, searchCondition).enqueue(new Callback<BaseResponse<List<VehiclePassingInfo>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<VehiclePassingInfo>>> call, Response<BaseResponse<List<VehiclePassingInfo>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<VehiclePassingInfo>> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<List<VehiclePassingInfo>>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<List<VehiclePassingInfo>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<VehiclePassingInfo>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<VehiclePassingInfo>>>error(buildException(t)));
            }
        });
        return data;
    }
}
