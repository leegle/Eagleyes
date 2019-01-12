package com.egova.eagleyes.repository;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.eagleyes.model.respose.LogHistory;
import com.egova.eagleyes.model.respose.PersonInfo;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface UserInfoRepository {

    /**
     * 用户上传图片数据统计
     *
     * @return
     */
    LiveData<Lcee<BaseResponse<List<NameTextValue<Integer>>>>> getTakePhotoStatistics();

    /**
     * 我的--工作记录
     *
     * @return
     */
    LiveData<Lcee<BaseResponse<List<LogHistory>>>> getLogHistory();

    /**
     * 所有警员列表
     *
     * @return
     */
    LiveData<Lcee<BaseResponse<List<PersonInfo>>>> getAllPersonList();
}
