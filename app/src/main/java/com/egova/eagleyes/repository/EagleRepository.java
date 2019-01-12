package com.egova.eagleyes.repository;

import androidx.lifecycle.LiveData;

import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.QueryModel;
import com.egova.eagleyes.model.request.SearchCondition;
import com.egova.eagleyes.model.respose.VehiclePassingInfo;

import java.util.List;

public interface EagleRepository {
    //根据条件进行搜索
    LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>>queryVehicleBySearch(QueryModel<SearchCondition> queryModel);
    //根据车辆图片搜索车辆信息
    LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> queryVehicleByImage(String imageUrl);
    //根据车辆图片获得图片识别码
    LiveData<Lcee<BaseResponse<VehiclePassingInfo>>> queryPictureFeature(String imageUrl);
    //根据图片识别码进行搜索
    LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> queryVehicleByFeature(SearchCondition searchCondition);

}
