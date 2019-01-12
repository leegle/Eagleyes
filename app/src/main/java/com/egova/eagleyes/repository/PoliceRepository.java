package com.egova.eagleyes.repository;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.QueryModel;
import com.egova.eagleyes.model.request.DispositionCondition;
import com.egova.eagleyes.model.request.DispositionModel;
import com.egova.eagleyes.model.request.PoliceCondition;
import com.egova.eagleyes.model.respose.DispositionAlarm;
import com.egova.eagleyes.model.respose.DispositionInfo;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * 布控管理
 */
public interface PoliceRepository {
    //报警管理
    LiveData<Lcee<BaseResponse<List<DispositionAlarm>>>> queryDispositionAlarm(QueryModel<PoliceCondition> queryModel);

    //将报警置为已处理
    LiveData<Lcee<BaseResponse<Boolean>>> handlerAlarm(String alarmId);

    //布控列表查询
    LiveData<Lcee<BaseResponse<List<DispositionInfo>>>> queryDispositionList(QueryModel<DispositionCondition> queryModel);

    //撤销布控
    LiveData<Lcee<BaseResponse<Boolean>>> cancelDisposition(String dispositionId);

    //新增布控
    LiveData<Lcee<BaseResponse<Boolean>>> addDisposition(DispositionModel dispositionModel);
}
