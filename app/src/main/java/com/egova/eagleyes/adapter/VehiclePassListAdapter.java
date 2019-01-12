package com.egova.eagleyes.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.egova.baselibrary.util.DateUtil;
import com.egova.eagleyes.R;
import com.egova.eagleyes.model.respose.VehiclePassingInfo;
import com.egova.eagleyes.util.GlideUtil;
import com.egova.eagleyes.util.StringUtil;
import com.egova.eagleyes.util.VehicleUtil;

import java.util.List;

import androidx.annotation.Nullable;

public class VehiclePassListAdapter extends BaseQuickAdapter<VehiclePassingInfo, BaseViewHolder> {
    public VehiclePassListAdapter(int layoutResId, @Nullable List<VehiclePassingInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VehiclePassingInfo item) {

        helper.setText(R.id.index, "第" + (helper.getLayoutPosition() + 1) + "条");
        GlideUtil.load(mContext, helper.getView(R.id.carImage), item.getUrl());
        helper.setText(R.id.plate,VehicleUtil.formatPlateNo(item.getPlate().getNo()));
        helper.setText(R.id.time, "时间：" + item.getTime());
        helper.setText(R.id.position, "卡口：" + StringUtil.formatNull(item.getCityName())+StringUtil.formatNull(item.getRegionName())+StringUtil.formatNull(item.getTollgateName()));
        helper.setText(R.id.brandYearName,item.getBrandYearName());

        helper.addOnClickListener(R.id.carImage);//点击查看大图

    }
}
