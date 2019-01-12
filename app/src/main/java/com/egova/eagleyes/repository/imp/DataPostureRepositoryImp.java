package com.egova.eagleyes.repository.imp;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.baselibrary.repository.BaseRepository;
import com.egova.eagleyes.api.DataPostureService;
import com.egova.eagleyes.model.respose.PointAlarm;
import com.egova.eagleyes.model.respose.VehicleNum;
import com.egova.eagleyes.repository.DataPostureRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPostureRepositoryImp extends BaseRepository<DataPostureService> implements DataPostureRepository {
    //总过车量、今日过车量、最近30天初次入城
    @Override
    public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getPassFlow() {
        final MutableLiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildNoNetWorkException()));
            return data;
        }
        service.getPassFlow(token).enqueue(new Callback<BaseResponse<List<NameTextValue<String>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<NameTextValue<String>>>> call, Response<BaseResponse<List<NameTextValue<String>>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<NameTextValue<String>>> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<NameTextValue<String>>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildException(t)));
            }
        });

        return data;
    }

    //获取手机归属地车辆积分预警数据量（昨天）
    @Override
    public LiveData<Lcee<BaseResponse<PointAlarm>>> getPointAlarm() {
        final MutableLiveData<Lcee<BaseResponse<PointAlarm>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<PointAlarm>>error(buildNoNetWorkException()));
            return data;
        }
        service.getPointAlarm(token).enqueue(new Callback<BaseResponse<PointAlarm>>() {
            @Override
            public void onResponse(Call<BaseResponse<PointAlarm>> call, Response<BaseResponse<PointAlarm>> response) {
                if (response.code() == 200) {
                    BaseResponse<PointAlarm> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<PointAlarm>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<PointAlarm>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PointAlarm>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<PointAlarm>>error(buildException(t)));
            }
        });

        return data;
    }

    //获取机动车保有量
    @Override
    public LiveData<Lcee<BaseResponse<VehicleNum>>> getVehicleNum() {
        final MutableLiveData<Lcee<BaseResponse<VehicleNum>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<VehicleNum>>error(buildNoNetWorkException()));
            return data;
        }
        service.getVehicleNum(token).enqueue(new Callback<BaseResponse<VehicleNum>>() {
            @Override
            public void onResponse(Call<BaseResponse<VehicleNum>> call, Response<BaseResponse<VehicleNum>> response) {
                if (response.code() == 200) {
                    BaseResponse<VehicleNum> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<VehicleNum>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<VehicleNum>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<VehicleNum>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<VehicleNum>>error(buildException(t)));
            }
        });

        return data;
    }

    //查询昨日的高低峰流量
    @Override
    public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getVehiclePeak() {
        final MutableLiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildNoNetWorkException()));
            return data;
        }
        service.getVehiclePeak(token).enqueue(new Callback<BaseResponse<List<NameTextValue<String>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<NameTextValue<String>>>> call, Response<BaseResponse<List<NameTextValue<String>>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<NameTextValue<String>>> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<NameTextValue<String>>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildException(t)));
            }
        });

        return data;
    }

    //获取昨天的本省外省机动车流量
    @Override
    public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getLocalVehicleFlow() {
        final MutableLiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildNoNetWorkException()));
            return data;
        }
        service.getLocalVehicleFlow(token).enqueue(new Callback<BaseResponse<List<NameTextValue<String>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<NameTextValue<String>>>> call, Response<BaseResponse<List<NameTextValue<String>>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<NameTextValue<String>>> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<NameTextValue<String>>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildException(t)));
            }
        });

        return data;
    }

    //查看当前用户所属区域的昨日设备在线数
    @Override
    public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getEquipmentOnlineNum() {
        final MutableLiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildNoNetWorkException()));
            return data;
        }
        service.getEquipmentOnlineNum(token).enqueue(new Callback<BaseResponse<List<NameTextValue<String>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<NameTextValue<String>>>> call, Response<BaseResponse<List<NameTextValue<String>>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<NameTextValue<String>>> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<NameTextValue<String>>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildException(t)));
            }
        });

        return data;
    }

    //研判结果,获取本月经过确认的假牌套牌车数量
    @Override
    public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getAnalysisResult() {
        final MutableLiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildNoNetWorkException()));
            return data;
        }
        service.getAnalysisResult(token).enqueue(new Callback<BaseResponse<List<NameTextValue<String>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<NameTextValue<String>>>> call, Response<BaseResponse<List<NameTextValue<String>>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<NameTextValue<String>>> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<NameTextValue<String>>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildException(t)));
            }
        });

        return data;
    }
}
