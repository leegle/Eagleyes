package com.egova.baselibrary.retrofit;

import com.egova.baselibrary.retrofit.upload.ProgressRequestBody;
import com.egova.baselibrary.retrofit.upload.UploadProgressListener;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RetrofitBodyUtil {

    /**
     * 将String 转为RequestBody
     *
     * @param value
     * @return
     */
    public static RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

    /**
     * 将文件转为 可以识别的请求体
     *
     * @param filePath
     * @return
     */
    public static MultipartBody.Part toRequestBodyOfFile(String filePath) {

        MultipartBody.Part part = null;
        try {
            File file = new File(filePath);
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            part = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return part;
    }

    /**
     * 将文件转为 可以识别的带有上传进度的请求体
     *
     * @param filePath
     * @param uploadProgressListener
     * @return
     */
    public static MultipartBody.Part toRequestBodyOfFile(String fileName,String filePath, UploadProgressListener uploadProgressListener) {
        MultipartBody.Part part = null;
        try {
            File file = new File(filePath);
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            part = MultipartBody.Part.createFormData(fileName, file.getName(), new ProgressRequestBody(file.getName(), fileBody, uploadProgressListener));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return part;
    }
}
