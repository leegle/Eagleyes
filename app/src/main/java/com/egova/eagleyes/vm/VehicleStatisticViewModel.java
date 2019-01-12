package com.egova.eagleyes.vm;

import android.app.Application;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.eagleyes.model.request.SearchCondition;
import com.egova.eagleyes.repository.VehicleStatisticRepository;
import com.egova.eagleyes.repository.imp.VehicleStatisticRepositoryImp;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

/**
 * 车辆统计
 */
public class VehicleStatisticViewModel extends AndroidViewModel {

    private VehicleStatisticRepository repository;
    //统计车辆活跃区域
    private MutableLiveData<SearchCondition> portraitByRegionCondition = new MutableLiveData<>();//
    private LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> portraitByRegionData;//

    //统计车辆活跃时间
    private MutableLiveData<SearchCondition> portraitByTimeCondition=new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> portraitByTimeData;//
    //统计车辆活跃卡口
    private MutableLiveData<SearchCondition> portraitByTollgateCondition=new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> portraitByTollgateData;//

    public VehicleStatisticViewModel(@NonNull Application application) {
        super(application);
        repository = new VehicleStatisticRepositoryImp();
    }


    public LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> getPortraitByRegionData() {
        if (portraitByRegionData == null) {
            portraitByRegionData = Transformations.switchMap(portraitByRegionCondition, new Function<SearchCondition, LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> apply(SearchCondition input) {
                    return repository.queryPortraitByRegion(input);
                }
            });
        }
        return portraitByRegionData;
    }

    public LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> getPortraitByTimeData() {
        if(portraitByTimeData==null){
            portraitByTimeData=Transformations.switchMap(portraitByTimeCondition, new Function<SearchCondition, LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> apply(SearchCondition input) {
                    return repository.queryPortraitByTime(input);
                }
            });
        }
        return portraitByTimeData;
    }

    public LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> getPortraitByTollgateData() {
        if(portraitByTollgateData==null){
            portraitByTollgateData=Transformations.switchMap(portraitByTollgateCondition, new Function<SearchCondition, LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> apply(SearchCondition input) {
                    return repository.queryPortraitByTollgate(input);
                }
            });
        }
        return portraitByTollgateData;
    }

    //统计车辆活跃区域
    public void queryPortraitByRegion(SearchCondition condition) {
        portraitByRegionCondition.setValue(condition);
    }
    //统计车辆活跃时间
    public void queryPortraitByTime(SearchCondition condition){
        portraitByTimeCondition.setValue(condition);
    }

    //统计车辆活跃卡口
    public void queryPortraitByTollgate(SearchCondition condition){
        portraitByTollgateCondition.setValue(condition);
    }

}
