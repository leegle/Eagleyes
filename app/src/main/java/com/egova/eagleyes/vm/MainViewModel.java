package com.egova.eagleyes.vm;

import android.app.Application;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.QueryModel;
import com.egova.eagleyes.model.request.SearchCondition;
import com.egova.eagleyes.model.respose.VehiclePassingInfo;
import com.egova.eagleyes.repository.EagleRepository;
import com.egova.eagleyes.repository.imp.EagleRepositoryImp;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class MainViewModel extends AndroidViewModel {

    private EagleRepository repository;
    //根据条件进行搜索车辆
    private MutableLiveData<QueryModel<SearchCondition>> searchCondition = new MutableLiveData<>();//条件
    private LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> vehicleSearchData;//

    //图片识别车辆
    private MutableLiveData<String> photoUrlLive = new MutableLiveData<>();//选择图片地址
    private LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> vehiclePhotoData;//
    //获取图片识别码
    private MutableLiveData<String> photoFeature = new MutableLiveData<>();//选择图片地址
    private LiveData<Lcee<BaseResponse<VehiclePassingInfo>>> photoFeatureData;//
    //根据图片码识别车辆
    private MutableLiveData<SearchCondition> searchByFeature = new MutableLiveData<>();//
    private LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> vehicleSearchByFeatureData;//

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new EagleRepositoryImp();
    }

    //根据条件进行搜索
    public LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> getVehicleSearchData() {
        if (vehicleSearchData == null) {
            vehicleSearchData = Transformations.switchMap(searchCondition, new Function<QueryModel<SearchCondition>, LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> apply(QueryModel<SearchCondition> input) {
                    return repository.queryVehicleBySearch(input);
                }
            });
        }
        return vehicleSearchData;
    }

    //图片识别车辆
    public LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> getVehicleByPhotoData() {
        if (vehiclePhotoData == null) {
            vehiclePhotoData = Transformations.switchMap(photoUrlLive, new Function<String, LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> apply(String input) {
                    return repository.queryVehicleByImage(input);
                }
            });
        }
        return vehiclePhotoData;
    }

    public LiveData<Lcee<BaseResponse<VehiclePassingInfo>>> getPhotoFeatureData() {
        if(photoFeatureData==null){
            photoFeatureData=Transformations.switchMap(photoFeature, new Function<String, LiveData<Lcee<BaseResponse<VehiclePassingInfo>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<VehiclePassingInfo>>> apply(String input) {
                    return repository.queryPictureFeature(input);
                }
            });
        }
        return photoFeatureData;
    }

    //根据图片码识别车辆
    public LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> getVehicleSearchByFeatureData() {
        if(vehicleSearchByFeatureData==null){
            vehicleSearchByFeatureData=Transformations.switchMap(searchByFeature, new Function<SearchCondition, LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<VehiclePassingInfo>>>> apply(SearchCondition input) {
                    return repository.queryVehicleByFeature(input);
                }
            });
        }
        return vehicleSearchByFeatureData;
    }

    /**
     * 通过条件进行车辆搜索
     *
     * @param queryModel
     */
    public void queryVehicleByCondition(QueryModel<SearchCondition> queryModel) {
        searchCondition.setValue(queryModel);
    }


    /**
     * 通过图片进行识别车辆
     *
     * @param photoUrl
     */
    public void queryVehicleByPhoto(String photoUrl) {
        photoUrlLive.setValue(photoUrl);
    }

    /**
     * 获取图片的识别码
     * @param url
     */
    public void queryPhotoFeature(String url){
        photoFeature.setValue(url);
    }
    /**
     * 根据图片码识别车辆
     * @param searchCondition
     */
    public void queryVehicleByFeature(SearchCondition searchCondition){
        searchByFeature.setValue(searchCondition);
    }
}
