package com.egova.eagleyes.repository.imp;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.baselibrary.model.RegionInfo;
import com.egova.baselibrary.repository.BaseRepository;
import com.egova.eagleyes.api.DictService;
import com.egova.eagleyes.model.respose.SubBrand;
import com.egova.eagleyes.model.respose.Tollgate;
import com.egova.eagleyes.model.respose.TollgateType;
import com.egova.eagleyes.repository.DictRepository;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DictRepositoryImpl extends BaseRepository<DictService> implements DictRepository {
    //获取省市区数据
    @Override
    public MutableLiveData<Lcee<BaseResponse<RegionInfo>>> queryRegionData() {
        final MutableLiveData<Lcee<BaseResponse<RegionInfo>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<RegionInfo>>error(buildNoNetWorkException()));
            return data;
        }
        service.queryRegionData().enqueue(new Callback<BaseResponse<RegionInfo>>() {
            @Override
            public void onResponse(Call<BaseResponse<RegionInfo>> call, Response<BaseResponse<RegionInfo>> response) {
                if (response.code() == 200) {
                    BaseResponse<RegionInfo> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<RegionInfo>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<RegionInfo>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<RegionInfo>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<RegionInfo>>error(buildException(t)));
            }
        });
        return data;
    }

    //获取卡口类型数据
    @Override
    public MutableLiveData<Lcee<BaseResponse<List<TollgateType>>>> queryTollgateTypeData() {
        final MutableLiveData<Lcee<BaseResponse<List<TollgateType>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<TollgateType>>>error(buildNoNetWorkException()));
            return data;
        }
        service.queryTollgateType().enqueue(new Callback<BaseResponse<List<TollgateType>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<TollgateType>>> call, Response<BaseResponse<List<TollgateType>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<TollgateType>> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<List<TollgateType>>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<List<TollgateType>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<TollgateType>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<TollgateType>>>error(buildException(t)));
            }
        });
        return data;
    }

    //获取所有卡口数据
    @Override
    public MutableLiveData<Lcee<BaseResponse<List<Tollgate>>>> queryTollgate() {
        final MutableLiveData<Lcee<BaseResponse<List<Tollgate>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<Tollgate>>>error(buildNoNetWorkException()));
            return data;
        }
        service.queryTollgate(token).enqueue(new Callback<BaseResponse<List<Tollgate>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Tollgate>>> call, Response<BaseResponse<List<Tollgate>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<Tollgate>> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<List<Tollgate>>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<List<Tollgate>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Tollgate>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<Tollgate>>>error(buildException(t)));
            }
        });
        return data;
    }

    //获取车牌品牌
    @Override
    public MutableLiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> queryBrandType() {
        final MutableLiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildNoNetWorkException()));
            return data;
        }
        service.queryBrandType(token).enqueue(new Callback<BaseResponse<List<NameTextValue<String>>>>() {
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

    //获取车辆子品牌
    @Override
    public MutableLiveData<Lcee<BaseResponse<SubBrand>>> querySubBrandType(String brandId) {
        final MutableLiveData<Lcee<BaseResponse<SubBrand>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<SubBrand>>error(buildNoNetWorkException()));
            return data;
        }
        service.querySubBrandType(token, brandId).enqueue(new Callback<BaseResponse<SubBrand>>() {
            @Override
            public void onResponse(Call<BaseResponse<SubBrand>> call, Response<BaseResponse<SubBrand>> response) {
                if (response.code() == 200) {
                    BaseResponse<SubBrand> result = response.body();
                    if (result.isHasError()) {
                        data.setValue(Lcee.<BaseResponse<SubBrand>>error(buildServerException(result.getMessage())));
                    } else {
                        data.setValue(Lcee.content(result));
                    }
                } else {
                    data.setValue(Lcee.<BaseResponse<SubBrand>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<SubBrand>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<SubBrand>>error(buildException(t)));
            }
        });
        return data;
    }

    //获取车辆颜色
    @Override
    public MutableLiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> queryVehicleColor() {
        final MutableLiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> data = new MutableLiveData<>();
        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<NameTextValue<String>>>>error(buildNoNetWorkException()));
            return data;
        }
        service.queryVehicleColor(token).enqueue(new Callback<BaseResponse<List<NameTextValue<String>>>>() {
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
