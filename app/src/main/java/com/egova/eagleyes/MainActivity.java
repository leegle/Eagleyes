package com.egova.eagleyes;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.egova.baselibrary.activity.TakePhotoHelper;
import com.egova.baselibrary.base.EgovaActivity;
import com.egova.baselibrary.event.PhotoEvent;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.RegionInfo;
import com.egova.baselibrary.model.UploadInfo;
import com.egova.baselibrary.util.ActivityLaunchUtil;
import com.egova.baselibrary.util.ActivityManagerUtil;
import com.egova.baselibrary.util.MaterialDialogUtils;
import com.egova.baselibrary.util.ToastUtil;
import com.egova.baselibrary.vm.UploadViewModel;
import com.egova.eagleyes.constants.Constants;
import com.egova.eagleyes.databinding.ActivityMainBinding;
import com.egova.eagleyes.helper.DictData;
import com.egova.eagleyes.model.request.TFLoginRequest;
import com.egova.eagleyes.model.respose.LoginResponse;
import com.egova.eagleyes.model.respose.VehiclePassingInfo;
import com.egova.eagleyes.util.DictUtil;
import com.egova.eagleyes.view.DataPostureFragment;
import com.egova.eagleyes.view.GlobalSearchFragment;
import com.egova.eagleyes.view.PoliceControlFragment;
import com.egova.eagleyes.view.UserInfoFragment;
import com.egova.eagleyes.view.VehiclePassingFragment;
import com.egova.eagleyes.vm.DictViewModel;
import com.egova.eagleyes.vm.LoginViewModel;
import com.egova.eagleyes.vm.MainViewModel;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

public class MainActivity extends EgovaActivity<ActivityMainBinding, MainViewModel> {


    private LoginViewModel loginViewModel;//登录
    private DictViewModel dictViewModel;//字典数据
    private UploadViewModel uploadViewModel;//文件上传

    private TFLoginRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        //首页关闭滑动返回手势
        getSwipeBackLayout().setEnableGesture(false);

        binding.setClickListener(onClickListener);
        binding.bottomBtn.setVisibility(View.GONE);
        binding.searchView.setVisibility(View.GONE);
        requestPermissions();//请求权限
        addLoginViewModel();//登录model
        addDictViewModel();//字典model
        addUploadViewModel();//文件上传model
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    public void addVMObserver() {
        //以图搜图
        viewModel.getVehicleByPhotoData().observe(this, new Observer<Lcee<BaseResponse<List<VehiclePassingInfo>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<VehiclePassingInfo>>> baseResponseLcee) {
                updateVehicleInfo(baseResponseLcee);
            }
        });
    }

    //添加登陆model
    private void addLoginViewModel() {
        loginViewModel = createViewModel(this, LoginViewModel.class);
        loginViewModel.getTfLoginData().observe(this, new Observer<Lcee<BaseResponse<LoginResponse>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<LoginResponse>> baseResponseLcee) {
                updateLoginInfo(baseResponseLcee);
            }
        });
    }

    //添加字典model
    private void addDictViewModel() {
        dictViewModel = createViewModel(this, DictViewModel.class);
        //读取省市区数据
        dictViewModel.getRegionData().observe(this, new Observer<Lcee<BaseResponse<RegionInfo>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<RegionInfo>> baseResponseLcee) {
                updateRegionDictData(baseResponseLcee);
            }
        });

    }

    //添加文件上传model
    private void addUploadViewModel() {
        uploadViewModel = createViewModel(this, UploadViewModel.class);
        uploadViewModel.getUploadData().observe(this, new Observer<Lcee<BaseResponse<List<UploadInfo>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<UploadInfo>>> baseResponseLcee) {
                updateUploadInfo(baseResponseLcee);
            }
        });
    }

    //权限检查(定位和存储权限)
    private void requestPermissions() {
        AndPermission.with(this).runtime().permission(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE).onGranted(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                startAnimation();
            }
        }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                showPermissionDialog();
            }
        }).start();
    }

    private void startAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1f, 0.5f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.25f);
        //3秒完成动画
        scaleAnimation.setDuration(1500);
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(scaleAnimation);
        animationSet.setFillAfter(true);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.searchView.setVisibility(View.VISIBLE);
                binding.bottomBtn.setVisibility(View.VISIBLE);
                login();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        binding.logoImage.startAnimation(animationSet);
    }

    //权限弹窗提醒
    private void showPermissionDialog() {
        MaterialDialogUtils.showBasicDialog(MainActivity.this, getString(R.string.permission_failed), getString(R.string.permission_required), getString(R.string.permission_again_take), getString(R.string.exit_tip))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ActivityManagerUtil.getAppManager().AppExit();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        requestPermissions();
                    }
                }).show();
    }

    //登录失败弹窗提醒
    private void showLoginFailDialog(String message) {
        MaterialDialogUtils.showBasicDialog(MainActivity.this, getString(R.string.user_recognition_fail), message, getString(R.string.login_try), getString(R.string.exit_tip))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ActivityManagerUtil.getAppManager().AppExit();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        login();
                    }
                }).show();
    }

    //字典数据失败提醒
    private void showDictFailDialog(String message) {
        MaterialDialogUtils.showBasicDialog(MainActivity.this, getString(R.string.load_dict_fail), message, getString(R.string.retry), getString(R.string.exit_tip))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ActivityManagerUtil.getAppManager().AppExit();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        loadDict();
                    }
                }).show();
    }

    private void login() {

        showLoadingDialog(getString(R.string.user_login_wait));

        request = new TFLoginRequest();
        request.setUserName("贾卫宁");
        request.setIdCard("530102196309101838");
        request.setCertSn("082C0000000039EC");
        request.setIccid("898600C8247542416108");
        request.setTfSn("MYN0079907R");
        request.setImei("869511020555039");
        request.setCertOu("云南省公安厅科技信息化处信息综合应用指导科");
        loginViewModel.TFLogin(request);
    }

    private void loadDict() {
        showLoadingDialog(getString(R.string.load_dict));
        dictViewModel.loadRegionData();
    }

    //弹窗选择拍照或者相册
    private void showSelectImageDialog() {
        MaterialDialogUtils.getSelectDialog(MainActivity.this, null, MainActivity.this.getResources().getStringArray(R.array.photoArray))
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if (position == 0) {
                            //拍照
                            TakePhotoHelper.startTakePhotoFromCamera(MainActivity.this, Constants.imageFolder);
                        } else {
                            //从相册选择
                            TakePhotoHelper.startTakePhotoFromAlbum(MainActivity.this, Constants.imageFolder, 1);
                        }
                    }
                }).show();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.userInfo://用户信息
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BUNDLE_USER, request);
                    ActivityLaunchUtil.launchContainerActivity(MainActivity.this, UserInfoFragment.class.getCanonicalName(), bundle);
                    break;
                case R.id.btn_text_search://全文检索
                    ActivityLaunchUtil.launchContainerActivity(MainActivity.this, GlobalSearchFragment.class.getCanonicalName());
                    break;
                case R.id.btn_camera_search://拍照检索
                    showSelectImageDialog();
                    break;
                case R.id.vehicle_control://车辆布控
                    ActivityLaunchUtil.launchContainerActivity(MainActivity.this, PoliceControlFragment.class.getCanonicalName());
                    break;
                case R.id.data_posture://数据态势
                    ActivityLaunchUtil.launchContainerActivity(MainActivity.this, DataPostureFragment.class.getCanonicalName());
                    break;

            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PhotoEvent event) {
        //拍照或者选择相册返回
        if (PhotoEvent.photoType.takeCancel.equals(event.getPhotoType())) {
            showToast(getString(R.string.cancel_text));
        } else if (PhotoEvent.photoType.takeFail.equals(event.getPhotoType())) {
            showToast(getString(R.string.photo_take_fail));
        } else {
            showLoadingDialog(getString(R.string.wait_file_upload));
            List<String> files = new ArrayList<>();
            int size = event.getResult().getImages().size();
            for (int i = 0; i < size; i++) {
                files.add(event.getResult().getImages().get(i).getOriginalPath());
            }
            uploadViewModel.uploadFiles(files);
        }
    }

    private long preTime = 0;

    @Override
    public void onBackPressed() {
        long nowTime = System.currentTimeMillis();
        if (nowTime - preTime > 2000) {
            showToast(getString(R.string.exit_app));
            preTime = nowTime;
        } else {
            ActivityManagerUtil.getAppManager().AppExit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().register(this);
    }

    //登录结果
    private void updateLoginInfo(Lcee<BaseResponse<LoginResponse>> response) {
//        dismissLoadingDialog();
        switch (response.status) {
            case Error:
                dismissLoadingDialog();
                showLoginFailDialog(response.error.getMessage());
                break;
            case Content:
                loadDict();
//                ToastUtil.showSuccess(response.data.getMessage());
                break;
        }
    }

    //省市区数据加载完毕
    private void updateRegionDictData(Lcee<BaseResponse<RegionInfo>> response) {
        dismissLoadingDialog();
        switch (response.status) {
            case Error:
                showDictFailDialog(response.error.getMessage());
                break;
            case Content:
                DictData.getInstance().setRegionInfo(DictUtil.buildRegionAddNoLimit(response.data.getResult()));
//                ToastUtil.showSuccess(getString(R.string.load_dict_success));
                break;
        }
    }

    //文件上传结果
    private void updateUploadInfo(Lcee<BaseResponse<List<UploadInfo>>> response) {
        switch (response.status) {
            case Error:
                dismissLoadingDialog();
                ToastUtil.showError(response.error.getMessage());
                break;
            case Content:
                if (response.data.getResult() != null && response.data.getResult().size() > 0) {
                    showLoadingDialog(getString(R.string.wait_identify));
                    viewModel.queryVehicleByPhoto(response.data.getResult().get(0).getValue());
//                    String url = "http://120.202.26.100:8888/t_pic/(2824).jpg";
//                    viewModel.queryVehicleByPhoto(url);
                } else {
                    ToastUtil.showError(getString(R.string.error_upload));
                }
                break;
        }
    }

    //以图搜图结果
    private void updateVehicleInfo(Lcee<BaseResponse<List<VehiclePassingInfo>>> response) {
        dismissLoadingDialog();
        switch (response.status) {
            case Content:
                if (response.data != null && response.data.getResult() != null && response.data.getResult().size() > 0) {
                    ArrayList<VehiclePassingInfo> result = new ArrayList<>();
                    result.addAll(response.data.getResult());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BUNDLE_VEHICLE_LIST, result);
                    bundle.putInt(Constants.BUNDLE_VEHICLE_SIZE, response.data.getTotalCount());
                    ActivityLaunchUtil.launchContainerActivity(MainActivity.this, VehiclePassingFragment.class.getCanonicalName(), bundle);
                } else {
                    ToastUtil.showNormal("抱歉，未查询到车型信息");
                }
                break;
            case Error:
                ToastUtil.showError(response.error.getMessage());
                break;
        }
    }

}
