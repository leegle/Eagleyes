package com.egova.eagleyes.repository;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.eagleyes.model.request.SearchCondition;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * 车辆统计
 */
public interface VehicleStatisticRepository {

    //活跃区县
    LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> queryPortraitByRegion(SearchCondition searchCondition);
    //活跃时间
    LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> queryPortraitByTime(SearchCondition searchCondition);
    //活跃卡口
    LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> queryPortraitByTollgate(SearchCondition searchCondition);
}
