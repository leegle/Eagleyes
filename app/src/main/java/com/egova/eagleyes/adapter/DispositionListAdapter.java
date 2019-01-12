package com.egova.eagleyes.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.egova.eagleyes.R;
import com.egova.eagleyes.model.respose.DispositionInfo;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 布控列表
 */
public class DispositionListAdapter extends BaseQuickAdapter<DispositionInfo, BaseViewHolder> {

    public DispositionListAdapter(int layoutResId, @Nullable List<DispositionInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DispositionInfo item) {
        helper.setText(R.id.index, "第" + (helper.getLayoutPosition() + 1) + "条");
        helper.setText(R.id.dispositionDate, item.getStartDate() + "至" + item.getEndDate());
        helper.setText(R.id.dispositionTime, item.getStartAlarmPeriod() + "~" + item.getEndAlarmPeriod());
        helper.setText(R.id.dispositionReason, item.getCause());
        if (item.getDisabled()) {
            helper.setText(R.id.dispositionStatus, "已撤控");
        } else {
            helper.setText(R.id.dispositionStatus, "已布控");
        }
    }
}
