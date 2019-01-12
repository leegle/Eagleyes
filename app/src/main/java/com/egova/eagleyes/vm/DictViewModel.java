package com.egova.eagleyes.vm;

import android.app.Application;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.baselibrary.model.RegionInfo;
import com.egova.eagleyes.model.respose.SubBrand;
import com.egova.eagleyes.model.respose.Tollgate;
import com.egova.eagleyes.model.respose.TollgateType;
import com.egova.eagleyes.repository.DictRepository;
import com.egova.eagleyes.repository.imp.DictRepositoryImpl;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

/**
 * 字典数据模块
 */
public class DictViewModel extends AndroidViewModel {

    private DictRepository repository;
    //省市区数据
    private MutableLiveData<String> regionParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<RegionInfo>>> regionData;
    //卡口类型
    private MutableLiveData<String> tollgateTypeParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<TollgateType>>>> tollgateTypeData;
    //所有卡口数据
    private MutableLiveData<String> tollgateParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<Tollgate>>>> tollgateData;
    //车辆品牌
    private MutableLiveData<String> brandParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> brandData;
    //车辆子品牌
    private MutableLiveData<String> subBrandParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<SubBrand>>> subBrandData;
    //车辆颜色
    private MutableLiveData<String> vehicleColorParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> vehicleColorData;


    public DictViewModel(@NonNull Application application) {
        super(application);
        repository = new DictRepositoryImpl();
    }

    //加载省市区数据
    public LiveData<Lcee<BaseResponse<RegionInfo>>> getRegionData() {
        if (regionData == null) {
            regionData = Transformations.switchMap(regionParam, new Function<String, LiveData<Lcee<BaseResponse<RegionInfo>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<RegionInfo>>> apply(String input) {
                    return repository.queryRegionData();
                }
            });
        }
        return regionData;
    }

    //加载卡口类型数据
    public LiveData<Lcee<BaseResponse<List<TollgateType>>>> getTollgateTypeData() {
        if (tollgateTypeData == null) {
            tollgateTypeData = Transformations.switchMap(tollgateTypeParam, new Function<String, LiveData<Lcee<BaseResponse<List<TollgateType>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<TollgateType>>>> apply(String input) {
                    return repository.queryTollgateTypeData();
                }
            });
        }
        return tollgateTypeData;
    }

    //加载所有卡口数据
    public LiveData<Lcee<BaseResponse<List<Tollgate>>>> getTollgateData() {
        if (tollgateData == null) {
            tollgateData = Transformations.switchMap(tollgateParam, new Function<String, LiveData<Lcee<BaseResponse<List<Tollgate>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<Tollgate>>>> apply(String input) {
                    return repository.queryTollgate();
                }
            });
        }
        return tollgateData;
    }

    //车辆品牌
    public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getBrandData() {
        if (brandData == null) {
            brandData = Transformations.switchMap(brandParam, new Function<String, LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> apply(String input) {
                    return repository.queryBrandType();
                }
            });
        }
        return brandData;
    }

    //车辆子品牌
    public LiveData<Lcee<BaseResponse<SubBrand>>> getSubBrandData() {
        if (subBrandData == null) {
            subBrandData = Transformations.switchMap(subBrandParam, new Function<String, LiveData<Lcee<BaseResponse<SubBrand>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<SubBrand>>> apply(String input) {
                    return repository.querySubBrandType(input);
                }
            });
        }
        return subBrandData;
    }

    //车辆颜色
    public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> getVehicleColorData() {
        if (vehicleColorData == null) {
            vehicleColorData = Transformations.switchMap(vehicleColorParam, new Function<String, LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> apply(String input) {
                    return repository.queryVehicleColor();
                }
            });
        }
        return vehicleColorData;
    }

    //加载省市区数据
    public void loadRegionData() {
        regionParam.setValue("");
    }

    //加载卡口类型数据
    public void loadTollgateTypeData() {
        tollgateTypeParam.setValue("");
    }

    //加载所有卡口数据
    public void loadTollgateData() {
        tollgateParam.setValue("");
    }

    //加载车辆品牌
    public void loadBrand() {
        brandParam.setValue("");
    }

    //车辆子品牌
    public void loadSubBrand(String brandId) {
        subBrandParam.setValue(brandId);
    }
}
