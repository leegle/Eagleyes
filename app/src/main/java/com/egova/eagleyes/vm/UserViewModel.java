package com.egova.eagleyes.vm;

import android.app.Application;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.eagleyes.model.respose.LogHistory;
import com.egova.eagleyes.model.respose.PersonInfo;
import com.egova.eagleyes.repository.UserInfoRepository;
import com.egova.eagleyes.repository.imp.UserInfoRepositoryImp;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

/**
 * 用户中心
 */
public class UserViewModel extends AndroidViewModel {

    private UserInfoRepository repository;
    //图片上传统计
    private MutableLiveData<String> takePhotoParams = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> takePhotoData;

    //我的--工作记录
    private MutableLiveData<String> logHistoryParams = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<LogHistory>>>> logHistoryData;

    //警员列表
    private MutableLiveData<String> personListParam = new MutableLiveData<>();
    private LiveData<Lcee<BaseResponse<List<PersonInfo>>>> personListData;


    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserInfoRepositoryImp();
    }

    //图片上传统计
    public LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> getTakePhotoData() {
        if (takePhotoData == null) {
            takePhotoData = Transformations.switchMap(takePhotoParams, new Function<String, LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> apply(String input) {
                    return repository.getTakePhotoStatistics();
                }
            });
        }
        return takePhotoData;
    }

    //我的-工作记录
    public LiveData<Lcee<BaseResponse<List<LogHistory>>>> getLogHistoryData() {
        if (logHistoryData == null) {
            logHistoryData = Transformations.switchMap(logHistoryParams, new Function<String, LiveData<Lcee<BaseResponse<List<LogHistory>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<LogHistory>>>> apply(String input) {
                    return repository.getLogHistory();
                }
            });
        }
        return logHistoryData;
    }

    //所有警员列表
    public LiveData<Lcee<BaseResponse<List<PersonInfo>>>> getPersonListData() {
        if (personListData == null) {
            personListData = Transformations.switchMap(personListParam, new Function<String, LiveData<Lcee<BaseResponse<List<PersonInfo>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<PersonInfo>>>> apply(String input) {
                    return repository.getAllPersonList();
                }
            });
        }
        return personListData;
    }

    /**
     * 获取图片上传统计
     */
    public void loadTakePhotoData() {
        takePhotoParams.setValue("");
    }

    /**
     * 我的-工作记录
     */
    public void loadLogHistoryData() {
        logHistoryParams.setValue("");
    }

    //所有警员列表
    public void loadAllPerson() {
        personListParam.setValue("");
    }
}
