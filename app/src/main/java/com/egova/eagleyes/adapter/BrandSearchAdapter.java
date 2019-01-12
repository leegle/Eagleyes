package com.egova.eagleyes.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.egova.baselibrary.model.NameTextValue;
import com.egova.eagleyes.R;

import java.util.List;

import androidx.annotation.Nullable;

public class BrandSearchAdapter extends BaseQuickAdapter<NameTextValue<String>, BaseViewHolder> {

    public BrandSearchAdapter(int layoutResId, @Nullable List<NameTextValue<String>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NameTextValue<String> item) {
        int position = helper.getLayoutPosition();
        if (position == 0) {
            helper.setVisible(R.id.letter, false);
        } else if (position == 1 || !mData.get(position - 1).getFirstLetter().equals(item.getFirstLetter())) {
            helper.setText(R.id.letter, item.getFirstLetter());
            helper.setVisible(R.id.letter, true);
        } else {
            helper.setVisible(R.id.letter, false);
        }
        helper.setText(R.id.name, item.getText());
    }
}
