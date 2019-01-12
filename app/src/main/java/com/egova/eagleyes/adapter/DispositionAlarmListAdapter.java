package com.egova.eagleyes.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.egova.eagleyes.R;
import com.egova.eagleyes.model.respose.DispositionAlarm;
import com.egova.eagleyes.util.GlideUtil;
import com.egova.eagleyes.util.StringUtil;
import com.egova.eagleyes.util.VehicleUtil;

import java.util.List;

import androidx.annotation.Nullable;

public class DispositionAlarmListAdapter extends BaseQuickAdapter<DispositionAlarm, BaseViewHolder> {

    public DispositionAlarmListAdapter(int layoutResId, @Nullable List<DispositionAlarm> data) {

        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DispositionAlarm item) {
        helper.setText(R.id.index, "第" + (helper.getLayoutPosition() + 1) + "条");
        GlideUtil.load(mContext, helper.getView(R.id.carImage), item.getPicture());
        helper.setText(R.id.plate, VehicleUtil.formatPlateNo(item.getPlate()));
        helper.setText(R.id.time, "时间：" + item.getTimestamp());
        helper.setText(R.id.position, "卡口：" + StringUtil.formatNull(item.getTollgateName()));
        helper.setText(R.id.brandYearName, item.getVehicleBrandName());
        helper.addOnClickListener(R.id.carImage);//点击查看大图
        if (item.getHandled()) {
            helper.setText(R.id.handleBtn, "已处理");
            helper.setBackgroundRes(R.id.handleBtn, R.drawable.btn_red_selector);
        } else {
            helper.setText(R.id.handleBtn, "置为已处理");
            helper.setBackgroundRes(R.id.handleBtn, R.drawable.btn_blue_selector);
        }
        helper.addOnClickListener(R.id.handleBtn);
    }
}
