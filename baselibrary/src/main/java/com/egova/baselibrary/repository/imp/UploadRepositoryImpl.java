package com.egova.baselibrary.repository.imp;


import com.egova.baselibrary.api.UploadService;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.Loading;
import com.egova.baselibrary.model.UploadInfo;
import com.egova.baselibrary.repository.BaseRepository;
import com.egova.baselibrary.repository.UploadRepository;
import com.egova.baselibrary.retrofit.RetrofitBodyUtil;
import com.egova.baselibrary.retrofit.upload.UploadProgressListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 文件上传
 */
public class UploadRepositoryImpl extends BaseRepository<UploadService> implements UploadRepository {

    /**
     * 多个文件上传,带有上传进度的
     *
     * @param filePathList
     */
    @Override
    public MutableLiveData<Lcee<BaseResponse<List<UploadInfo>>>> uploadFile(List<String> filePathList) {
        final MutableLiveData<Lcee<BaseResponse<List<UploadInfo>>>> data = new MutableLiveData<>();

        if (!isNetWorkConnected()) {
            data.setValue(Lcee.<BaseResponse<List<UploadInfo>>>error(buildNoNetWorkException()));
            return data;
        }
        List<MultipartBody.Part> parts = new ArrayList<>();

        final HashMap<String, Loading> loadingHashMap = new HashMap<>();
        int size = filePathList.size();
        for (int i = 0; i < size; i++) {
            parts.add(RetrofitBodyUtil.toRequestBodyOfFile("file", filePathList.get(i), new UploadProgressListener() {
                @Override
                public void onProgress(String key, long currentBytesCount, long totalBytesCount) {
                    Loading loading = loadingHashMap.get(key);
                    if (loading != null) {
                        loading.setCurrentLength(currentBytesCount);
                        loading.setTotalLength(totalBytesCount);
                    } else {
                        loading = new Loading(key, currentBytesCount, totalBytesCount);
                        loadingHashMap.put(key, loading);
                    }
                    //上传进度
                    data.postValue(Lcee.<BaseResponse<List<UploadInfo>>>loading(loadingHashMap));
                }
            }));
        }
        service.uploadFile(parts).enqueue(new Callback<BaseResponse<List<UploadInfo>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<UploadInfo>>> call, Response<BaseResponse<List<UploadInfo>>> response) {
                if (response.code() == 200) {
                    BaseResponse<List<UploadInfo>> result = response.body();
                    data.setValue(Lcee.content(result));
                } else {
                    data.setValue(Lcee.<BaseResponse<List<UploadInfo>>>error(buildException(null)));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<UploadInfo>>> call, Throwable t) {
                data.setValue(Lcee.<BaseResponse<List<UploadInfo>>>error(buildException(t)));
            }
        });
        return data;
    }
}
