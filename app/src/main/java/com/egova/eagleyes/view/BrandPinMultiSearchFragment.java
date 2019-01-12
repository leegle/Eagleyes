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
import com.egova.baselibrary.model.NameTextValue;
import com.egova.baselibrary.widget.wavesidebar.Trans2PinYinUtil;
import com.egova.baselibrary.widget.wavesidebar.WaveSideBarView;
import com.egova.eagleyes.R;
import com.egova.eagleyes.adapter.BrandSearchAdapter;
import com.egova.eagleyes.constants.Constants;
import com.egova.eagleyes.databinding.FragmentPinSearchBinding;
import com.egova.eagleyes.databinding.TitleBackBarBinding;
import com.egova.eagleyes.event.BrandEvent;
import com.egova.eagleyes.util.NameTextLetterComparator;
import com.egova.eagleyes.vm.DictViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 车辆品牌拼音导航--多选框
 */
public class BrandPinMultiSearchFragment extends EgovaFragment<FragmentPinSearchBinding, DictViewModel> {

    private TitleBackBarBinding titleBackBarBinding;

    private String type;

    private BrandSearchAdapter adapter;
    private List<NameTextValue<String>> dataSrc;
    private List<NameTextValue<String>> data;


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_pin_search;
    }

    @Override
    public void initView() {
        initTitleBar();
        initRecyclerView();
        initSearchView();
    }

    private void initTitleBar() {
        binding.bottom.setVisibility(View.GONE);
        titleBackBarBinding = binding.titleBar;
        titleBackBarBinding.tvTitle.setText(getString(R.string.brand));
        titleBackBarBinding.leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {
        type=(String)getArguments().getString(Constants.BUNDLE_TYPE);
        dataSrc = (List<NameTextValue<String>>) getArguments().getSerializable(Constants.BUNDLE_KEY);
        Collections.sort(dataSrc, new NameTextLetterComparator());
        NameTextValue<String> noLimit = new NameTextValue<>();
        noLimit.setName("不限");
        noLimit.setText("不限");
        dataSrc.add(0, noLimit);
        data = new ArrayList<>();
        data.addAll(dataSrc);
    }

    @Override
    public void addVMObserver() {

    }

    private void initRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BrandSearchAdapter(R.layout.item_pinyin, data);
        binding.recyclerView.setAdapter(adapter);

        binding.waveSideBarView.setOnSelectIndexItemListener(new WaveSideBarView.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String letter) {
                int size = data.size();
                for (int i = 0; i < size; i++) {
                    if (data.get(i).getFirstLetter().equals(letter)) {
                        ((LinearLayoutManager) binding.recyclerView.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        showTip(letter);
                        return;
                    }
                }
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BrandEvent event = new BrandEvent(type,data.get(position));
                EventBus.getDefault().post(event);
                getActivity().finish();
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
                data.clear();
                for (NameTextValue<String> item : dataSrc) {
                    String str = Trans2PinYinUtil.trans2PinYin(item.getName());
                    if (str.contains(s.toString()) || item.getName().contains(s.toString())) {
                        data.add(item);
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
