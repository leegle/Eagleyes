package com.egova.baselibrary.api;

import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.UploadInfo;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadService {

    /**
     * 文件上传--多个文件
     *
     * @param files
     * @return
     */
    @Multipart
    @POST("/attachment/upload/files")
    Call<BaseResponse<List<UploadInfo>>> uploadFile(@Part() List<MultipartBody.Part> files);


}
