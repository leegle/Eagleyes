package com.egova.eagleyes.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egova.baselibrary.activity.PhotoPreviewActivity;
import com.egova.baselibrary.base.EgovaFragment;
import com.egova.baselibrary.util.ActivityLaunchUtil;
import com.egova.baselibrary.util.ToastUtil;
import com.egova.eagleyes.R;
import com.egova.eagleyes.constants.Constants;
import com.egova.eagleyes.databinding.FragmentDispositionInfoBinding;
import com.egova.eagleyes.databinding.TitleBackBarBinding;
import com.egova.eagleyes.model.respose.DispositionAlarm;
import com.egova.eagleyes.model.respose.PlateInfo;
import com.egova.eagleyes.model.respose.VehiclePassingInfo;
import com.egova.eagleyes.vm.PoliceControlViewModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 * 报警详情
 */
public class DispositionAlarmDetailFragment extends EgovaFragment<FragmentDispositionInfoBinding, PoliceControlViewModel> {
    private TitleBackBarBinding titleBackBarBinding;
    private DispositionAlarm dispositionAlarm;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_disposition_info;
    }

    @Override
    public void initView() {
        initTitleBar();
    }

    private void initTitleBar() {
        titleBackBarBinding = binding.titleBar;
        titleBackBarBinding.tvTitle.setText(getString(R.string.alarm_detail));
        titleBackBarBinding.leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {
        dispositionAlarm = (DispositionAlarm) getArguments().getSerializable(Constants.BUNDLE_VEHICLE);
        if (dispositionAlarm != null) {
            binding.setAlarmDetail(dispositionAlarm);
        }
        binding.setMClick(onClickListener);
    }

    @Override
    public void addVMObserver() {

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.vehicle_picture:
                    //车辆图片
                    Bundle bundlePicture = new Bundle();
                    ArrayList<String> images = new ArrayList<>();
                    images.add(dispositionAlarm.getPicture());
                    bundlePicture.putStringArrayList("images", images);
                    bundlePicture.putInt("position", 0);
                    bundlePicture.putBoolean("isNetworkPath", true);
                    bundlePicture.putBoolean("isShowDeleteButton", false);
                    ActivityLaunchUtil.launchActivity(getActivity(), PhotoPreviewActivity.class, bundlePicture);
                    break;
                case R.id.search_by_picture:
                    //以图搜图
                    VehiclePassingInfo vehiclePassingInfo=new VehiclePassingInfo();
                    vehiclePassingInfo.setUrl(dispositionAlarm.getPicture());
                    vehiclePassingInfo.setVehicleColor(dispositionAlarm.getVehicleColor());
                    Bundle bundleSearch = new Bundle();
                    bundleSearch.putSerializable(Constants.BUNDLE_VEHICLE, vehiclePassingInfo);
                    ActivityLaunchUtil.launchContainerActivity(getActivity(), PictureSearchFragment.class.getCanonicalName(), bundleSearch);
                    break;
                case R.id.vehicle_portrait:
                    //车辆画像
                    if (dispositionAlarm.getPlate()== null || dispositionAlarm.getPlate().contains("--")) {
                        ToastUtil.showNormal("无牌车无法查看车辆画像功能");
                        return;
                    }
                    VehiclePassingInfo vehiclePassingInfo1=new VehiclePassingInfo();
                    PlateInfo plateInfo=new PlateInfo();
                    plateInfo.setNo(dispositionAlarm.getPlate());
                    vehiclePassingInfo1.setPlate(plateInfo);
                    Bundle bundlePortrait = new Bundle();
                    bundlePortrait.putSerializable(Constants.BUNDLE_VEHICLE, vehiclePassingInfo1);
                    ActivityLaunchUtil.launchContainerActivity(getActivity(), VehiclePortraitFragment.class.getCanonicalName(), bundlePortrait);
                    break;
            }

        }
    };
}
