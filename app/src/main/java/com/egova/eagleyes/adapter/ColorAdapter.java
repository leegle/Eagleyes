package com.egova.eagleyes.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.egova.eagleyes.R;
import com.egova.eagleyes.constants.VehicleColorEnum;

import java.util.List;

import androidx.annotation.Nullable;

public class ColorAdapter extends BaseQuickAdapter<VehicleColorEnum, BaseViewHolder> {

    public ColorAdapter(int layoutResId, @Nullable List<VehicleColorEnum> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VehicleColorEnum item) {

        if(helper.getLayoutPosition()==0){
            helper.setGone(R.id.colorLin,false);
        }else{
            helper.setGone(R.id.colorLin,true);
        }
        helper.setBackgroundColor(R.id.colors, item.color);
        helper.setText(R.id.title, item.name);

    }
}
