package com.egova.eagleyes.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.egova.baselibrary.base.EgovaFragment;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.util.MaterialDialogUtils;
import com.egova.baselibrary.util.ToastUtil;
import com.egova.eagleyes.R;
import com.egova.eagleyes.adapter.ViewPageAdapter;
import com.egova.eagleyes.databinding.FragmentPoliceControlBinding;
import com.egova.eagleyes.databinding.TitleBackBarBinding;
import com.egova.eagleyes.helper.DictData;
import com.egova.eagleyes.model.respose.Tollgate;
import com.egova.eagleyes.model.respose.TollgateType;
import com.egova.eagleyes.vm.DictViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

/**
 * 我要布控
 */
public class PoliceControlFragment extends EgovaFragment<FragmentPoliceControlBinding, DictViewModel> {
    private TitleBackBarBinding titleBackBarBinding;

    private ViewPageAdapter pageAdapter;
    private List<Fragment> fragments;

    private boolean hasError = false;
    private int hasLoadSum = 0;
    private int needLoadSum = 2;

    private PoliceAlertFragment policeAlertFragment;//报警管理
    private DispositionOperateFragment dispositionOperateFragment;//我要布控
    private DispositionSearchFragment dispositionSearchFragment;//布控管理

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_police_control;
    }

    @Override
    public void initView() {
        initTitleBar();
    }

    private void initTitleBar() {
        titleBackBarBinding = binding.titleBar;
        titleBackBarBinding.tvTitle.setText(getString(R.string.police_control));
        titleBackBarBinding.leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {
        String[] titles = {"报警管理", "我要布控", "布控管理"};
        fragments = new ArrayList<>();
        policeAlertFragment = new PoliceAlertFragment();
        dispositionOperateFragment = new DispositionOperateFragment();
        dispositionSearchFragment = new DispositionSearchFragment();
        fragments.add(policeAlertFragment);
        fragments.add(dispositionOperateFragment);
        fragments.add(dispositionSearchFragment);

        pageAdapter = new ViewPageAdapter(getChildFragmentManager(), fragments, titles);
        binding.viewPager.setAdapter(pageAdapter);
        binding.viewPager.setOffscreenPageLimit(3);
        binding.tabBar.setupWithViewPager(binding.viewPager);

        loadAllData();
    }

    @Override
    public void addVMObserver() {
        //卡口类型
        viewModel.getTollgateTypeData().observe(this, new Observer<Lcee<BaseResponse<List<TollgateType>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<TollgateType>>> baseResponseLcee) {
                updateTollgateType(baseResponseLcee);
            }
        });
        //卡口数据
        viewModel.getTollgateData().observe(this, new Observer<Lcee<BaseResponse<List<Tollgate>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<Tollgate>>> baseResponseLcee) {
                updateTollgate(baseResponseLcee);
            }
        });

    }

    //加载数据
    private void loadAllData() {
        hasLoadSum = 0;
        showLoadingDialog("卡口数据加载中...");
        loadTollgateTypeData();//卡口类型
        loadTollgateData();//卡口数据

    }

    //卡口类型
    private void loadTollgateTypeData() {
        if (DictData.getInstance().getTollgateTypesList() == null) {
            //加载
            viewModel.loadTollgateTypeData();
        } else {
            addHasLoadSum(1);
            finishedLoad();
        }
    }

    //加载卡口数据
    private void loadTollgateData() {
        if (DictData.getInstance().getTollgateList() == null) {
            //加载
            viewModel.loadTollgateData();
        } else {
            addHasLoadSum(1);
            finishedLoad();
        }
    }

    //卡口类型
    private void updateTollgateType(Lcee<BaseResponse<List<TollgateType>>> response) {
        addHasLoadSum(1);
        switch (response.status) {
            case Error:
                hasError = true;
                break;
            case Content:
                List<TollgateType> tollgateTypeList = new ArrayList<>();
                tollgateTypeList.addAll(response.data.getResult());
                DictData.getInstance().setTollgateTypesList(tollgateTypeList);
                break;

        }
        finishedLoad();
    }

    //卡口数据
    private void updateTollgate(Lcee<BaseResponse<List<Tollgate>>> response) {
        addHasLoadSum(1);
        switch (response.status) {
            case Error:
                hasError = true;
                break;
            case Content:
                List<Tollgate> tollgateList = new ArrayList<>();
                tollgateList.addAll(response.data.getResult());
                DictData.getInstance().setTollgateList(tollgateList);
                policeAlertFragment.updateTollgateText();
                ToastUtil.showNormal("卡口数据加载完毕!");
                break;

        }
        finishedLoad();
    }


    private synchronized void addHasLoadSum(int step) {
        hasLoadSum = hasLoadSum + step;
    }

    private void finishedLoad() {
        if (hasLoadSum != needLoadSum) {
            return;
        }
        dismissLoadingDialog();
        if (hasError) {
            //如果有错
            showDictFailDialog("加载出错啦，请检查网络后重试!");
        } else {
        }
    }

    private void showDictFailDialog(String message) {
        MaterialDialogUtils.showBasicDialog(getActivity(), getString(R.string.load_error), message, getString(R.string.retry), getString(R.string.close_activity))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        getActivity().finish();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        loadAllData();
                    }
                }).show();
    }
}
