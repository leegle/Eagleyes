package com.egova.eagleyes.repository;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.baselibrary.model.RegionInfo;
import com.egova.eagleyes.model.respose.SubBrand;
import com.egova.eagleyes.model.respose.Tollgate;
import com.egova.eagleyes.model.respose.TollgateType;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public interface DictRepository {
    //获取省市区数据
    MutableLiveData<Lcee<BaseResponse<RegionInfo>>> queryRegionData();

    //获取卡口类型
    MutableLiveData<Lcee<BaseResponse<List<TollgateType>>>> queryTollgateTypeData();

    //获取所有卡口数据
    MutableLiveData<Lcee<BaseResponse<List<Tollgate>>>> queryTollgate();

    //获取车辆品牌
    MutableLiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> queryBrandType();

    //获取车辆子品牌
    MutableLiveData<Lcee<BaseResponse<SubBrand>>> querySubBrandType(String brandId);

    //获取车辆颜色
    MutableLiveData<Lcee<BaseResponse<List<NameTextValue<String>>>>> queryVehicleColor();
}
