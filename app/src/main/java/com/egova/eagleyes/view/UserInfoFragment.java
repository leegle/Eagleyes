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
import com.egova.baselibrary.model.NameTextValue;
import com.egova.baselibrary.util.FileCacheUtils;
import com.egova.baselibrary.util.MaterialDialogUtils;
import com.egova.eagleyes.R;
import com.egova.eagleyes.adapter.LogHistoryAdapter;
import com.egova.eagleyes.constants.Constants;
import com.egova.eagleyes.databinding.FragmentUserInfoBinding;
import com.egova.eagleyes.databinding.MineItemStaticBinding;
import com.egova.eagleyes.databinding.TitleBackBarBinding;
import com.egova.eagleyes.model.request.TFLoginRequest;
import com.egova.eagleyes.model.respose.LogHistory;
import com.egova.eagleyes.vm.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 用户信息页面
 */
public class UserInfoFragment extends EgovaFragment<FragmentUserInfoBinding, UserViewModel> {

    private TitleBackBarBinding titleBackBarBinding;
    private MineItemStaticBinding photoStaticBinding;//统计

    private View historyEmptyView;//工作日志--空视图
    private View historyErrorView;//工作日志--错误视图
    private View historyLoadingView;//工作日志--加载中

    private LogHistoryAdapter logHistoryAdapter;
    private List<LogHistory> logHistoryData;

    private TFLoginRequest request;


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_user_info;
    }

    @Override
    public void initView() {
        initTitleBar();
        initOther();
        loadData();
        updateCacheData();
    }

    private void initTitleBar() {
        titleBackBarBinding = binding.titleBar;
        titleBackBarBinding.tvTitle.setText(getString(R.string.user_info));
        titleBackBarBinding.leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        binding.clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialogUtils.showBasicDialog(getActivity(), "清除缓存", "确定要清除缓存吗?", "确定", "取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                FileCacheUtils.startClearCacheSize(getActivity(), new FileCacheUtils.ClearCacheListener() {
                                    @Override
                                    public void onClearCacheFinished() {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                updateCacheData();
                                            }
                                        });
                                    }
                                }, Constants.folderName);
                            }
                        }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }
                }).autoDismiss(true).show();
            }
        });
    }

    @Override
    public void initData() {
        request = (TFLoginRequest) getArguments().getSerializable(Constants.BUNDLE_USER);
        binding.userName.setText(request.getUserName());
        binding.department.setText(request.getCertOu());
        binding.idCard.setText(request.getIdCard());
    }

    private void initOther() {
        //用户上传图片统计视图
        binding.photoStatistics.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTakePhotoData();
            }
        });
        //初始化工作记录
        initHistoryRecyclerView();
    }

    private void initHistoryRecyclerView() {
        //工作记录
        logHistoryData = new ArrayList<>();
        binding.workRecordRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        logHistoryAdapter = new LogHistoryAdapter(R.layout.mine_item_log_history, logHistoryData);
        historyLoadingView = getLayoutInflater().inflate(R.layout.status_loading, (ViewGroup) binding.workRecordRecyclerView.getParent(), false);
        historyEmptyView = getLayoutInflater().inflate(R.layout.status_empty, (ViewGroup) binding.workRecordRecyclerView.getParent(), false);
        historyErrorView = getLayoutInflater().inflate(R.layout.status_error, (ViewGroup) binding.workRecordRecyclerView.getParent(), false);
        historyEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLogHistoryData();
            }
        });
        historyErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLogHistoryData();
            }
        });
        binding.workRecordRecyclerView.setAdapter(logHistoryAdapter);
    }

    private void updateCacheData() {
        FileCacheUtils.startCalculateCacheSize(getActivity(), new FileCacheUtils.CacheSizeCalculateListener() {
            @Override
            public void onCalculateFinished(long cacheSize) {
                binding.cacheSize.setText(FileCacheUtils.getFormatSize(cacheSize));
            }
        }, Constants.folderName);
    }

    @Override
    public void addVMObserver() {
        //用户上传图片统计
        viewModel.getTakePhotoData().observe(this, new Observer<Lcee<BaseResponse<List<NameTextValue<Integer>>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<NameTextValue<Integer>>>> baseResponseLcee) {
                updateTakePhotoData(baseResponseLcee);
            }
        });
        //我的-工作记录
        viewModel.getLogHistoryData().observe(this, new Observer<Lcee<BaseResponse<List<LogHistory>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<LogHistory>>> baseResponseLcee) {
                updateLogHistoryData(baseResponseLcee);
            }
        });
    }

    private void loadData() {
        loadTakePhotoData();
        loadLogHistoryData();
    }


    //加载用户上传图片统计
    private void loadTakePhotoData() {
        binding.photoStatistics.showLoading();
        viewModel.loadTakePhotoData();
    }

    //加载工作记录
    private void loadLogHistoryData() {
        logHistoryData.clear();
        logHistoryAdapter.setEmptyView(historyLoadingView);
        viewModel.loadLogHistoryData();
    }


    //照片上传统计
    private void updateTakePhotoData(Lcee<BaseResponse<List<NameTextValue<Integer>>>> response) {
        switch (response.status) {
            case Content:
                List<NameTextValue<Integer>> result = response.data.getResult();
                int size = result.size();
                photoStaticBinding = binding.photoStatisticsItem;
                for (int i = 0; i < size; i++) {
                    if ("total".equals(result.get(i).getName())) {
                        photoStaticBinding.photoTotal.setText(result.get(i).getValue() + "");
                    } else if ("week".equals(result.get(i).getName())) {
                        photoStaticBinding.photoWeek.setText(result.get(i).getValue() + "");
                    } else if ("today".equals(result.get(i).getName())) {
                        photoStaticBinding.photoToday.setText(result.get(i).getValue() + "");
                    }
                }
                binding.photoStatistics.showContent();
                break;
            case Error:
                binding.photoStatistics.showError();

        }
    }

    //工作记录
    private void updateLogHistoryData(Lcee<BaseResponse<List<LogHistory>>> response) {

        switch (response.status) {
            case Content:
                if (response.data.getResult().size() == 0) {
                    logHistoryAdapter.setEmptyView(historyEmptyView);
                } else {
                    logHistoryData.addAll(response.data.getResult());
                    logHistoryAdapter.notifyDataSetChanged();
                }
                break;
            case Error:
                if (logHistoryData.size() == 0) {
                    logHistoryAdapter.setEmptyView(historyErrorView);
                }

                break;
        }
    }

}
