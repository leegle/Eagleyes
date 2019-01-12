package com.egova.eagleyes.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.egova.baselibrary.activity.PhotoPreviewActivity;
import com.egova.baselibrary.base.EgovaFragment;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.Paging;
import com.egova.baselibrary.model.QueryModel;
import com.egova.baselibrary.util.ActivityLaunchUtil;
import com.egova.baselibrary.util.CommonConstants;
import com.egova.baselibrary.util.ToastUtil;
import com.egova.eagleyes.R;
import com.egova.eagleyes.adapter.DispositionAlarmListAdapter;
import com.egova.eagleyes.constants.Constants;
import com.egova.eagleyes.databinding.FragmentDispositionAlarmBinding;
import com.egova.eagleyes.databinding.TitleBackBarBinding;
import com.egova.eagleyes.model.request.PoliceCondition;
import com.egova.eagleyes.model.respose.DispositionAlarm;
import com.egova.eagleyes.vm.PoliceControlViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 报警管理车辆列表
 */
public class DispositionAlarmListFragment extends EgovaFragment<FragmentDispositionAlarmBinding, PoliceControlViewModel> {

    private TitleBackBarBinding titleBackBarBinding;
    private int dispositionAlarmSize = 0;
    private ArrayList<DispositionAlarm> dataList;
    private QueryModel<PoliceCondition> queryModel;

    private DispositionAlarmListAdapter adapter;

    private int selectPosition = 0;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_disposition_alarm;
    }

    @Override
    public void initView() {
        initTitleBar();
        initRecyclerView();
    }

    private void initTitleBar() {
        titleBackBarBinding = binding.titleBar;
        titleBackBarBinding.tvTitle.setText(getString(R.string.police_control) + "(" + dispositionAlarmSize + "条)");
        titleBackBarBinding.leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {
        dispositionAlarmSize = getArguments().getInt(Constants.BUNDLE_VEHICLE_SIZE);
        dataList = (ArrayList<DispositionAlarm>) getArguments().getSerializable(Constants.BUNDLE_VEHICLE_LIST);
        queryModel = (QueryModel<PoliceCondition>) getArguments().getSerializable(Constants.BUNDLE_VEHICLE_QUERY);
    }

    @Override
    public void addVMObserver() {
        //查询报警
        viewModel.getAlarmSearchData().observe(this, new Observer<Lcee<BaseResponse<List<DispositionAlarm>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<DispositionAlarm>>> responseLcee) {
                updateData(responseLcee);
            }
        });
        //处理报警
        viewModel.getAlarmHandlerData().observe(this, new Observer<Lcee<BaseResponse<Boolean>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<Boolean>> baseResponseLcee) {
                updateHandlerData(baseResponseLcee);
            }
        });
    }

    private void initRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DispositionAlarmListAdapter(R.layout.item_disposition_alarm, dataList);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.isFirstOnly(false);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.carImage) {
                    //点击查看大图
                    Bundle bundle = new Bundle();
                    ArrayList<String> images = new ArrayList<>();
                    images.add(dataList.get(position).getPicture());
                    bundle.putStringArrayList("images", images);
                    bundle.putInt("position", 0);
                    bundle.putBoolean("isNetworkPath", true);
                    bundle.putBoolean("isShowDeleteButton", false);
                    ActivityLaunchUtil.launchActivity(getActivity(), PhotoPreviewActivity.class, bundle);
                } else if (view.getId() == R.id.handleBtn) {
                    if (dataList.get(position).getHandled()) {
                        ToastUtil.showNormal("该车辆已处理");
                    } else {
                        showLoadingDialog("正在处理中...");
                        adapter.closeLoadAnimation();
                        selectPosition = position;
                        viewModel.handlerAlarm(dataList.get(position).getId());
                    }
                }
            }
        });
        //加载更多
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, binding.recyclerView);
    }

    private void loadData() {
        Paging paging = queryModel.getPaging();
        paging.setPageIndex(paging.getPageIndex() + 1);
        viewModel.loadDispositionAlarm(queryModel);
    }

    //报警查询
    private void updateData(Lcee<BaseResponse<List<DispositionAlarm>>> response) {
        switch (response.status) {
            case Content:
                adapter.addData(response.data.getResult());
                if (response.data.getResult().size() < CommonConstants.PAGE_SIZE) {
                    adapter.loadMoreEnd();
                } else {
                    adapter.loadMoreComplete();
                }
                break;
            case Error:
                Paging paging = queryModel.getPaging();
                paging.setPageIndex(paging.getPageIndex() - 1);
                adapter.loadMoreFail();
                break;
        }
    }

    //报警处理
    private void updateHandlerData(Lcee<BaseResponse<Boolean>> response) {
        dismissLoadingDialog();
        switch (response.status) {
            case Error:
                ToastUtil.showError(response.error.getMessage());
                break;
            case Content:
                dataList.get(selectPosition).setHandled(true);
                adapter.notifyItemChanged(selectPosition);
                ToastUtil.showNormal(response.data.getMessage());
                break;
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
        handler.sendEmptyMessageDelayed(1, 300);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1)
                adapter.openLoadAnimation();
        }
    };


    private BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            DispositionAlarm item = dataList.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_VEHICLE, item);
            ActivityLaunchUtil.launchContainerActivity(getActivity(), DispositionAlarmDetailFragment.class.getCanonicalName(), bundle);
        }
    };
}
