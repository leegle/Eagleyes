package com.egova.eagleyes.view;

import android.os.Bundle;
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
import com.egova.eagleyes.R;
import com.egova.eagleyes.adapter.VehiclePassListAdapter;
import com.egova.eagleyes.constants.Constants;
import com.egova.eagleyes.databinding.FragmentVehiclePassBinding;
import com.egova.eagleyes.databinding.TitleBackBarTwoBinding;
import com.egova.eagleyes.model.request.SearchCondition;
import com.egova.eagleyes.model.respose.VehiclePassingInfo;
import com.egova.eagleyes.vm.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 过车信息列表页面
 */
public class VehiclePassingFragment extends EgovaFragment<FragmentVehiclePassBinding, MainViewModel> {
    private TitleBackBarTwoBinding titleBackBarTwoBinding;

    private VehiclePassListAdapter adapter;
    private ArrayList<VehiclePassingInfo> vehiclePassingInfoList;
    private int vehiclePassingSize = 0;

    private QueryModel<SearchCondition> queryModel;


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_vehicle_pass;
    }

    @Override
    public void initView() {
        initTitleBar();
        initRecyclerView();
    }

    private void initTitleBar() {
        titleBackBarTwoBinding = binding.titleBar;
        titleBackBarTwoBinding.tvTitle.setText(String.format(getString(R.string.vehicle_info), vehiclePassingSize + ""));
        titleBackBarTwoBinding.rightText.setText(getString(R.string.take_photo_again));
        titleBackBarTwoBinding.rightText.setVisibility(View.GONE);
        titleBackBarTwoBinding.leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {
        vehiclePassingInfoList = (ArrayList<VehiclePassingInfo>) getArguments().getSerializable(Constants.BUNDLE_VEHICLE_LIST);
        vehiclePassingSize = getArguments().getInt(Constants.BUNDLE_VEHICLE_SIZE);
        if (getArguments().getSerializable(Constants.BUNDLE_VEHICLE_QUERY) != null) {
            queryModel = (QueryModel<SearchCondition>) getArguments().getSerializable(Constants.BUNDLE_VEHICLE_QUERY);
        }
    }

    @Override
    public void addVMObserver() {
        viewModel.getVehicleSearchData().observe(this, new Observer<Lcee<BaseResponse<List<VehiclePassingInfo>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<VehiclePassingInfo>>> baseResponseLcee) {
                updateData(baseResponseLcee);
            }
        });

    }

    private void initRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new VehiclePassListAdapter(R.layout.item_vehicle_pass, vehiclePassingInfoList);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.isFirstOnly(false);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //点击查看大图
                Bundle bundle = new Bundle();
                ArrayList<String> images = new ArrayList<>();
                images.add(vehiclePassingInfoList.get(position).getUrl());
                bundle.putStringArrayList("images", images);
                bundle.putInt("position", 0);
                bundle.putBoolean("isNetworkPath", true);
                bundle.putBoolean("isShowDeleteButton", false);
                ActivityLaunchUtil.launchActivity(getActivity(), PhotoPreviewActivity.class, bundle);
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

    private BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_VEHICLE, vehiclePassingInfoList.get(position));
            ActivityLaunchUtil.launchContainerActivity(getActivity(), VehicleInfoFragment.class.getCanonicalName(), bundle);

        }
    };

    private void loadData() {
        if (queryModel != null) {
            Paging paging = queryModel.getPaging();
            paging.setPageIndex(paging.getPageIndex() + 1);
            viewModel.queryVehicleByCondition(queryModel);
        } else {
            adapter.loadMoreEnd();
        }
    }

    private void updateData(Lcee<BaseResponse<List<VehiclePassingInfo>>> response) {
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

}
