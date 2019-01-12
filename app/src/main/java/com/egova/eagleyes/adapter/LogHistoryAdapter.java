package com.egova.eagleyes.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.egova.baselibrary.util.DateUtil;
import com.egova.eagleyes.R;
import com.egova.eagleyes.model.respose.LogHistory;

import java.util.List;

/**
 * 工作日志
 */
public class LogHistoryAdapter extends BaseQuickAdapter<LogHistory, BaseViewHolder> {

    public LogHistoryAdapter(int layoutResId, List<LogHistory> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LogHistory item) {
        helper.setText(R.id.time, DateUtil.convertMisToStandard(item.getValue()));
        helper.setText(R.id.log, item.getName());

    }


}
