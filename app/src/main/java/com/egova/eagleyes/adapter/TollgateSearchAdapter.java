package com.egova.eagleyes.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.egova.eagleyes.R;
import com.egova.eagleyes.model.respose.Tollgate;

import java.util.List;

import androidx.annotation.Nullable;

public class TollgateSearchAdapter extends BaseQuickAdapter<Tollgate, BaseViewHolder> {

    public TollgateSearchAdapter(int layoutResId, @Nullable List<Tollgate> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Tollgate item) {

        int position = helper.getLayoutPosition();
        if (position == 0 || !mData.get(position - 1).getFirstLetter().equals(item.getFirstLetter())) {
            helper.setText(R.id.letter, item.getFirstLetter());
            helper.setVisible(R.id.letter, true);
        } else {
            helper.setVisible(R.id.letter, false);
        }
        helper.setText(R.id.tollgateName, item.getName());
        if (item.isHasPoliceAlertSelected()) {
            helper.setImageResource(R.id.checkBox, R.mipmap.checkbox_selected);
        } else {
            helper.setImageResource(R.id.checkBox, R.mipmap.checkbox_default);
        }
        helper.addOnClickListener(R.id.itemLin);

    }
}
