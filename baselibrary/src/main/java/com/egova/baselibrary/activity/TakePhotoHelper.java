package com.egova.baselibrary.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.egova.baselibrary.event.PhotoEvent;
import com.egova.baselibrary.util.ActivityLaunchUtil;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.LubanOptions;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.model.TakePhotoOptions;
import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * 相机或者相册
 */
public class TakePhotoHelper extends TakePhotoActivity {


    private TakePhoto takePhoto;
    private String type;//类型，相机or相册
    private String imageFolder;//图片存放目录
    private int limitPhoto;//照片数目限制
    private Uri imageUri;

    /**
     * 相机拍照
     *
     * @param context
     * @param imageFolder
     */
    public static void startTakePhotoFromCamera(Context context, String imageFolder) {
        Bundle bundle = new Bundle();
        bundle.putString("imageFolder", imageFolder);
        bundle.putString("type", "0");
        ActivityLaunchUtil.launchActivity(context, TakePhotoHelper.class, bundle);
    }

    /**
     * 从相册选择照片
     *
     * @param context
     * @param imageFolder
     */
    public static void startTakePhotoFromAlbum(Context context, String imageFolder, int limitPhoto) {
        Bundle bundle = new Bundle();
        bundle.putString("imageFolder", imageFolder);
        bundle.putString("type", "1");
        bundle.putInt("limitPhoto", limitPhoto);
        ActivityLaunchUtil.launchActivity(context, TakePhotoHelper.class, bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageFolder = getIntent().getStringExtra("imageFolder");
        type = getIntent().getStringExtra("type");
        configTakePhoto();
        if ("1".equalsIgnoreCase(type)) {
            //相册
            limitPhoto = getIntent().getIntExtra("limitPhoto", 1);
            startTakeFromAlbum();
        } else {
            // 相机
            startTakeFromCamera();
        }
    }


    //启动相机拍照
    private void startTakeFromCamera() {
        takePhoto.onPickFromCapture(imageUri);
    }

    private void startTakeFromAlbum() {
        takePhoto.onPickMultiple(limitPhoto);
    }


    //配置拍照或者相册参数
    private void configTakePhoto() {
        takePhoto = getTakePhoto();
        File file = new File(imageFolder, System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        imageUri = Uri.fromFile(file);
        configCompress();
        configTakePhotoOption();
    }

    //配置照片压缩参数
    private void configCompress() {
        LubanOptions option = new LubanOptions.Builder().setMaxHeight(1920).setMaxWidth(1080).setMaxSize(1024 * 100*2).create();
        CompressConfig config;
        config = CompressConfig.ofLuban(option);
        config.enableReserveRaw(true);//拍照压缩后保存原图片
        takePhoto.onEnableCompress(config, true);
    }

    //配置照片相册问题
    private void configTakePhotoOption() {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);//使用takePhoto自带相册
        builder.setCorrectImage(true);//纠正拍照的照片旋转角度
        takePhoto.setTakePhotoOptions(builder.create());
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        PhotoEvent photoEvent = new PhotoEvent();
        photoEvent.setResult(result);
        photoEvent.setMessage("成功");
        photoEvent.setPhotoType(PhotoEvent.photoType.takeSuccess);
        EventBus.getDefault().post(photoEvent);
        TakePhotoHelper.this.finish();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        PhotoEvent photoEvent = new PhotoEvent();
        photoEvent.setResult(result);
        photoEvent.setMessage(msg);
        photoEvent.setPhotoType(PhotoEvent.photoType.takeFail);
        EventBus.getDefault().post(photoEvent);
        TakePhotoHelper.this.finish();
    }


    @Override
    public void takeCancel() {
        super.takeCancel();
        PhotoEvent photoEvent = new PhotoEvent();
        photoEvent.setResult(null);
        photoEvent.setMessage("取消");
        photoEvent.setPhotoType(PhotoEvent.photoType.takeCancel);
        EventBus.getDefault().post(photoEvent);
        TakePhotoHelper.this.finish();
    }
}
