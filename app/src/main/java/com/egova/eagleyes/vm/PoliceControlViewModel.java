package com.egova.eagleyes.vm;

import android.app.Application;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.QueryModel;
import com.egova.eagleyes.model.request.DispositionCondition;
import com.egova.eagleyes.model.request.DispositionModel;
import com.egova.eagleyes.model.request.PoliceCondition;
import com.egova.eagleyes.model.respose.DispositionAlarm;
import com.egova.eagleyes.model.respose.DispositionInfo;
import com.egova.eagleyes.repository.PoliceRepository;
import com.egova.eagleyes.repository.imp.PoliceRepositoryImp;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

/**
 * 车辆布控
 */
public class PoliceControlViewModel extends AndroidViewModel {

    private PoliceRepository repository;

    //报警管理查询
    private MutableLiveData<QueryModel<PoliceCondition>> alarmSearchParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<DispositionAlarm>>>> alarmSearchData;
    //处理报警
    private MutableLiveData<String> alarmHandlerParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<Boolean>>> alarmHandlerData;

    //布控列表查询
    private MutableLiveData<QueryModel<DispositionCondition>> dispositionSearchParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<DispositionInfo>>>> dispositionSearchData;
    //撤销布控
    private MutableLiveData<String> cancelDispositionParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<Boolean>>> cancelDispositionData;
    //撤销布控
    private MutableLiveData<DispositionModel> addDispositionParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<Boolean>>> addDispositionData;


    public PoliceControlViewModel(@NonNull Application application) {
        super(application);
        repository = new PoliceRepositoryImp();
    }

    //报警管理查询
    public LiveData<Lcee<BaseResponse<List<DispositionAlarm>>>> getAlarmSearchData() {
        if (alarmSearchData == null) {
            alarmSearchData = Transformations.switchMap(alarmSearchParam, new Function<QueryModel<PoliceCondition>, LiveData<Lcee<BaseResponse<List<DispositionAlarm>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<DispositionAlarm>>>> apply(QueryModel<PoliceCondition> input) {
                    return repository.queryDispositionAlarm(input);
                }
            });
        }
        return alarmSearchData;
    }

    //处理报警
    public LiveData<Lcee<BaseResponse<Boolean>>> getAlarmHandlerData() {
        if (alarmHandlerData == null) {
            alarmHandlerData = Transformations.switchMap(alarmHandlerParam, new Function<String, LiveData<Lcee<BaseResponse<Boolean>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<Boolean>>> apply(String input) {
                    return repository.handlerAlarm(input);
                }
            });
        }
        return alarmHandlerData;
    }

    //布控列表查询
    public LiveData<Lcee<BaseResponse<List<DispositionInfo>>>> getDispositionSearchData() {
        if (dispositionSearchData == null) {
            dispositionSearchData = Transformations.switchMap(dispositionSearchParam, new Function<QueryModel<DispositionCondition>, LiveData<Lcee<BaseResponse<List<DispositionInfo>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<DispositionInfo>>>> apply(QueryModel<DispositionCondition> input) {
                    return repository.queryDispositionList(input);
                }
            });
        }
        return dispositionSearchData;
    }

    //撤销布控
    public LiveData<Lcee<BaseResponse<Boolean>>> getCancelDispositionData() {
        if (cancelDispositionData == null) {
            cancelDispositionData = Transformations.switchMap(cancelDispositionParam, new Function<String, LiveData<Lcee<BaseResponse<Boolean>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<Boolean>>> apply(String input) {
                    return repository.cancelDisposition(input);
                }
            });
        }
        return cancelDispositionData;
    }

    //添加布控
    public LiveData<Lcee<BaseResponse<Boolean>>> getAddDispositionData() {
        if (addDispositionData == null) {
            addDispositionData = Transformations.switchMap(addDispositionParam, new Function<DispositionModel, LiveData<Lcee<BaseResponse<Boolean>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<Boolean>>> apply(DispositionModel input) {
                    return repository.addDisposition(input);
                }
            });
        }
        return addDispositionData;
    }

    //报警管理查询
    public void loadDispositionAlarm(QueryModel<PoliceCondition> queryModel) {
        alarmSearchParam.setValue(queryModel);
    }

    //处理报警
    public void handlerAlarm(String alarmId) {
        alarmHandlerParam.setValue(alarmId);
    }

    //布控列表查询
    public void loadDispositionList(QueryModel<DispositionCondition> queryModel) {
        dispositionSearchParam.setValue(queryModel);
    }

    //撤销布控
    public void cancelDisposition(String dispositionId) {
        cancelDispositionParam.setValue(dispositionId);
    }

    //添加布控
    public void addDisposition(DispositionModel dispositionModel) {
        addDispositionParam.setValue(dispositionModel);
    }


}
