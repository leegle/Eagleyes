package com.egova.baselibrary.vm;

import android.app.Application;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.UploadInfo;
import com.egova.baselibrary.repository.UploadRepository;
import com.egova.baselibrary.repository.imp.UploadRepositoryImpl;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

/**
 * 上传文件viewModel
 */
public class UploadViewModel extends AndroidViewModel {

    private UploadRepository uploadRepository;
    private MutableLiveData<List<String>> uploadParams;//上传文件列表参数
    private LiveData<Lcee<BaseResponse<List<UploadInfo>>>> uploadData;//

    public UploadViewModel(@NonNull Application application) {
        super(application);
        uploadRepository = new UploadRepositoryImpl();
    }

    public LiveData<Lcee<BaseResponse<List<UploadInfo>>>> getUploadData() {
        if (uploadData == null) {
            uploadParams = new MutableLiveData<>();
            uploadData = Transformations.switchMap(uploadParams, new Function<List<String>, LiveData<Lcee<BaseResponse<List<UploadInfo>>>>>() {
                @Override
                public LiveData<Lcee<BaseResponse<List<UploadInfo>>>> apply(List<String> input) {
                    return uploadRepository.uploadFile(input);
                }
            });
        }
        return uploadData;
    }

    /**
     * 上传文件
     *
     * @param fileNameList
     */
    public void uploadFiles(List<String> fileNameList) {
        uploadParams.setValue(fileNameList);
    }
}
