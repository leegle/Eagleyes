package com.egova.eagleyes.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.egova.baselibrary.base.EgovaFragment;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.Paging;
import com.egova.baselibrary.model.QueryModel;
import com.egova.baselibrary.util.ActivityLaunchUtil;
import com.egova.baselibrary.util.CommonConstants;
import com.egova.eagleyes.R;
import com.egova.eagleyes.adapter.DispositionListAdapter;
import com.egova.eagleyes.constants.Constants;
import com.egova.eagleyes.databinding.FragmentDispositionAlarmBinding;
import com.egova.eagleyes.databinding.TitleBackBarBinding;
import com.egova.eagleyes.event.CancelDispositionEvent;
import com.egova.eagleyes.model.request.DispositionCondition;
import com.egova.eagleyes.model.respose.DispositionInfo;
import com.egova.eagleyes.vm.PoliceControlViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 布控列表
 */
public class DispositionListFragment extends EgovaFragment<FragmentDispositionAlarmBinding, PoliceControlViewModel> {
    private TitleBackBarBinding titleBackBarBinding;
    private ArrayList<DispositionInfo> dataList;
    private QueryModel<DispositionCondition> queryModel;

    private DispositionListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

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

        dataList = (ArrayList<DispositionInfo>) getArguments().getSerializable(Constants.BUNDLE_DISPOSITION);
        queryModel = (QueryModel<DispositionCondition>) getArguments().getSerializable(Constants.BUNDLE_QUERY_MODEL);
    }


    @Override
    public void addVMObserver() {

        viewModel.getDispositionSearchData().observe(this, new Observer<Lcee<BaseResponse<List<DispositionInfo>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<DispositionInfo>>> responseLcee) {
                updateData(responseLcee);
            }
        });
    }


    private void initRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DispositionListAdapter(R.layout.item_disposition_list, dataList);
        binding.recyclerView.setAdapter(adapter);
        //加载更多
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, binding.recyclerView);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.BUNDLE_DISPOSITION, dataList.get(position));
                ActivityLaunchUtil.launchContainerActivity(getActivity(), DispositionDetailFragment.class.getCanonicalName(), bundle);
            }
        });
    }

    private void loadData() {
        Paging paging = queryModel.getPaging();
        paging.setPageIndex(paging.getPageIndex() + 1);
        viewModel.loadDispositionList(queryModel);
    }

    private void updateData(Lcee<BaseResponse<List<DispositionInfo>>> response) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEventBus(CancelDispositionEvent dispositionEvent) {

        if (dataList != null) {
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                if (dataList.get(i).getId().equalsIgnoreCase(dispositionEvent.info.getId())) {
                    dataList.get(i).setDisabled(true);
                    adapter.notifyItemChanged(i);
                    break;
                }
            }
        }
    }
}
