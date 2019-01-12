package com.egova.eagleyes.repository.imp;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.QueryModel;
import com.egova.baselibrary.repository.BaseRepository;
import com.egova.eagleyes.api.PoliceService;
import com.egova.eagleyes.model.request.DispositionCondition;
import com.egova.eagleyes.model.request.DispositionModel;
import com.egova.eagleyes.model.request.PoliceCondition;
import com.egova.eagleyes.model.respose.DispositionAlarm;
import com.egova.eagleyes.model.respose.DispositionInfo;
import com.egova.eagleyes.repository.PoliceRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PoliceRepositoryImp extends BaseRepository<PoliceService> implements PoliceRepository {

    //报警管理查询
    @Override
    public LiveData<Lcee<BaseResponse<List<DispositionAlarm>>>> queryDispositionAlarm(QueryModel<PoliceCondition> queryModel) {
        final MutableLiveData<Lcee<BaseResponse<List<DispositionAlarm>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<DispositionAlarm>>>error(buildNoNetWorkException()));
            return data;
        }
        service.queryDispositionAlarm(token, queryModel).enqueue(new Callback<BaseResponse<List<DispositionAlarm>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<DispositionAlarm>>> call, Response<BaseResponse<List<DispositionAlarm>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<DispositionAlarm>> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<List<DispositionAlarm>>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<List<DispositionAlarm>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<DispositionAlarm>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<DispositionAlarm>>>error(buildException(t)));
            }
        });
        return data;
    }

    //将报警置为已处理
    @Override
    public LiveData<Lcee<BaseResponse<Boolean>>> handlerAlarm(String alarmId) {
        final MutableLiveData<Lcee<BaseResponse<Boolean>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<Boolean>>error(buildNoNetWorkException()));
            return data;
        }
        service.handlerAlarm(token, alarmId).enqueue(new Callback<BaseResponse<Boolean>>() {
            @Override
            public void onResponse(Call<BaseResponse<Boolean>> call, Response<BaseResponse<Boolean>> response) {
                if (response.code() == 200) {
                    BaseResponse<Boolean> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<Boolean>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<Boolean>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Boolean>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<Boolean>>error(buildException(t)));
            }
        });
        return data;
    }

    //布控列表查询
    @Override
    public LiveData<Lcee<BaseResponse<List<DispositionInfo>>>> queryDispositionList(QueryModel<DispositionCondition> queryModel) {
        final MutableLiveData<Lcee<BaseResponse<List<DispositionInfo>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<DispositionInfo>>>error(buildNoNetWorkException()));
            return data;
        }
        service.queryDispositionList(token, queryModel).enqueue(new Callback<BaseResponse<List<DispositionInfo>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<DispositionInfo>>> call, Response<BaseResponse<List<DispositionInfo>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<DispositionInfo>> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<List<DispositionInfo>>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<List<DispositionInfo>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<DispositionInfo>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<DispositionInfo>>>error(buildException(t)));
            }
        });
        return data;
    }

    //撤销布控
    @Override
    public LiveData<Lcee<BaseResponse<Boolean>>> cancelDisposition(String dispositionId) {
        final MutableLiveData<Lcee<BaseResponse<Boolean>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<Boolean>>error(buildNoNetWorkException()));
            return data;
        }
        service.cancelDisposition(token, dispositionId).enqueue(new Callback<BaseResponse<Boolean>>() {
            @Override
            public void onResponse(Call<BaseResponse<Boolean>> call, Response<BaseResponse<Boolean>> response) {
                if (response.code() == 200) {
                    BaseResponse<Boolean> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<Boolean>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<Boolean>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Boolean>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<Boolean>>error(buildException(t)));
            }
        });
        return data;
    }

    @Override
    public LiveData<Lcee<BaseResponse<Boolean>>> addDisposition(DispositionModel dispositionModel) {
        final MutableLiveData<Lcee<BaseResponse<Boolean>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<Boolean>>error(buildNoNetWorkException()));
            return data;
        }
        service.addDisposition(token, dispositionModel).enqueue(new Callback<BaseResponse<Boolean>>() {
            @Override
            public void onResponse(Call<BaseResponse<Boolean>> call, Response<BaseResponse<Boolean>> response) {
                if (response.code() == 200) {
                    BaseResponse<Boolean> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<Boolean>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<Boolean>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Boolean>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<Boolean>>error(buildException(t)));
            }
        });
        return data;
    }
}
