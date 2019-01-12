package com.egova.eagleyes.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egova.baselibrary.base.EgovaFragment;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.util.ToastUtil;
import com.egova.eagleyes.R;
import com.egova.eagleyes.constants.Constants;
import com.egova.eagleyes.databinding.FragmentDispositionDetailBinding;
import com.egova.eagleyes.databinding.TitleBackBarBinding;
import com.egova.eagleyes.event.CancelDispositionEvent;
import com.egova.eagleyes.model.respose.DispositionInfo;
import com.egova.eagleyes.vm.PoliceControlViewModel;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * 布控详情
 */
public class DispositionDetailFragment extends EgovaFragment<FragmentDispositionDetailBinding, PoliceControlViewModel> {

    private TitleBackBarBinding titleBackBarBinding;
    private DispositionInfo dispositionInfo;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_disposition_detail;
    }

    @Override
    public void initView() {
        initTitleBar();
        binding.setDispositionInfo(dispositionInfo);
        binding.setOnClick(onClickListener);
    }

    private void initTitleBar() {
        titleBackBarBinding = binding.titleBar;
        titleBackBarBinding.tvTitle.setText(getString(R.string.disposition_detail));
        titleBackBarBinding.leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {
        dispositionInfo = (DispositionInfo) getArguments().getSerializable(Constants.BUNDLE_DISPOSITION);
    }

    @Override
    public void addVMObserver() {

        viewModel.getCancelDispositionData().observe(this, new Observer<Lcee<BaseResponse<Boolean>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<Boolean>> baseResponseLcee) {
                switch (baseResponseLcee.status) {
                    case Error:
                        ToastUtil.showError(baseResponseLcee.error.getMessage());
                        break;
                    case Content:
                        ToastUtil.showSuccess(baseResponseLcee.data.getMessage());
                        EventBus.getDefault().post(new CancelDispositionEvent(dispositionInfo));
                        getActivity().finish();
                        break;
                }
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cancelBtn) {
                showLoadingDialog();
                viewModel.cancelDisposition(dispositionInfo.getId());
            }

        }
    };
}
