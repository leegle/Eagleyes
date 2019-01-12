package com.egova.eagleyes.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.egova.baselibrary.base.EgovaFragment;
import com.egova.baselibrary.model.RegionInfo;
import com.egova.baselibrary.widget.wavesidebar.Trans2PinYinUtil;
import com.egova.baselibrary.widget.wavesidebar.WaveSideBarView;
import com.egova.eagleyes.R;
import com.egova.eagleyes.adapter.TollgateSearchAdapter;
import com.egova.eagleyes.constants.Constants;
import com.egova.eagleyes.databinding.FragmentTollgateSearchBinding;
import com.egova.eagleyes.databinding.TitleBackBarBinding;
import com.egova.eagleyes.model.respose.Tollgate;
import com.egova.eagleyes.util.DictUtil;
import com.egova.eagleyes.util.TollgateLetterComparator;
import com.egova.eagleyes.vm.DictViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 卡口选择页面
 */
public class TollgateSearchFragment extends EgovaFragment<FragmentTollgateSearchBinding, DictViewModel> {
    private TitleBackBarBinding titleBackBarBinding;

    private List<Tollgate> tollgateSrc;
    private List<Tollgate> tollgateList;

    private TollgateSearchAdapter adapter;

    private RegionInfo regionInfo;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_tollgate_search;
    }

    @Override
    public void initView() {
        initTitleBar();
        initRecyclerView();
        initSearchView();
    }

    private void initTitleBar() {
        titleBackBarBinding = binding.titleBar;
        titleBackBarBinding.tvTitle.setText(getString(R.string.police_alert_tollgate));
        titleBackBarBinding.leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {
        regionInfo = (RegionInfo) getArguments().getSerializable(Constants.BUNDLE_REGION);
        tollgateList = new ArrayList<>();
        tollgateSrc = new ArrayList<>();
        DictUtil.buildTollgateData(tollgateList, regionInfo,false);
//        tollgateList.addAll(DictData.getInstance().getTollgateList());
        Collections.sort(tollgateList, new TollgateLetterComparator());
        tollgateSrc.addAll(tollgateList);
    }

    @Override
    public void addVMObserver() {

    }

    private void initRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TollgateSearchAdapter(R.layout.item_tollgate, tollgateList);
        binding.recyclerView.setAdapter(adapter);

        binding.waveSideBarView.setOnSelectIndexItemListener(new WaveSideBarView.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String letter) {
                int size = tollgateList.size();
                for (int i = 0; i < size; i++) {
                    if (tollgateList.get(i).getFirstLetter().equals(letter)) {
                        ((LinearLayoutManager) binding.recyclerView.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        showTip(letter);
                        return;
                    }
                }
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                tollgateList.get(position).setHasPoliceAlertSelected(!tollgateList.get(position).isHasPoliceAlertSelected());
                adapter.notifyItemChanged(position);
            }
        });
    }

    private void initSearchView() {
        binding.searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tollgateList.clear();
                for (Tollgate item : tollgateSrc) {
                    String str = Trans2PinYinUtil.trans2PinYin(item.getName());
                    if (str.contains(s.toString()) || item.getName().contains(s.toString())) {
                        tollgateList.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }


    //中间字母提示
    private void showTip(String letter) {
        binding.showTip.setText(letter);
        if (binding.showTip.getVisibility() == View.VISIBLE) {
            handler.removeMessages(1);
        } else {
            binding.showTip.setVisibility(View.VISIBLE);
        }
        handler.sendEmptyMessageDelayed(1, 1200);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    binding.showTip.setVisibility(View.GONE);
                    break;
            }
        }
    };
}
