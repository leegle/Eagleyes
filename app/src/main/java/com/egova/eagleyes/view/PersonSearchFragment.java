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
import com.egova.baselibrary.widget.wavesidebar.Trans2PinYinUtil;
import com.egova.baselibrary.widget.wavesidebar.WaveSideBarView;
import com.egova.eagleyes.R;
import com.egova.eagleyes.adapter.PersonSearchAdapter;
import com.egova.eagleyes.constants.Constants;
import com.egova.eagleyes.databinding.FragmentTollgateSearchBinding;
import com.egova.eagleyes.databinding.TitleBackBarBinding;
import com.egova.eagleyes.model.respose.PersonInfo;
import com.egova.eagleyes.util.PersonLetterComparator;
import com.egova.eagleyes.vm.DictViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

public class PersonSearchFragment extends EgovaFragment<FragmentTollgateSearchBinding, DictViewModel> {
    private TitleBackBarBinding titleBackBarBinding;

    private PersonSearchAdapter adapter;
    private List<PersonInfo> dataListSrc;
    private List<PersonInfo> dataList;

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

    @Override
    public void initData() {
        dataListSrc = (ArrayList<PersonInfo>) getArguments().getSerializable(Constants.BUNDLE_USER);
        Collections.sort(dataListSrc, new PersonLetterComparator());
        dataList = new ArrayList<>();
        dataList.addAll(dataListSrc);
    }

    @Override
    public void addVMObserver() {

    }

    private void initTitleBar() {
        titleBackBarBinding = binding.titleBar;
        titleBackBarBinding.tvTitle.setText(getString(R.string.choose_person));
        titleBackBarBinding.leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private void initRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PersonSearchAdapter(R.layout.item_pinyin, dataList);
        binding.recyclerView.setAdapter(adapter);

        binding.waveSideBarView.setOnSelectIndexItemListener(new WaveSideBarView.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String letter) {
                int size = dataList.size();
                for (int i = 0; i < size; i++) {
                    if (dataList.get(i).getFirstLetter().equals(letter)) {
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
                EventBus.getDefault().post(dataList.get(position));
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
                dataList.clear();
                for (PersonInfo item : dataListSrc) {
                    String str = Trans2PinYinUtil.trans2PinYin(item.getName());
                    if (str.contains(s.toString()) || item.getName().contains(s.toString())) {
                        dataList.add(item);
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
