package com.egova.baselibrary.repository;


import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.UploadInfo;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public interface UploadRepository {
    /**
     * 多个文件上传,带有上传进度的
     */
    MutableLiveData<Lcee<BaseResponse<List<UploadInfo>>>> uploadFile(List<String> filePathList);
}
