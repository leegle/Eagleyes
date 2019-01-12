package com.egova.eagleyes.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.egova.eagleyes.R;
import com.egova.eagleyes.model.respose.PersonInfo;

import java.util.List;

import androidx.annotation.Nullable;

public class PersonSearchAdapter extends BaseQuickAdapter<PersonInfo, BaseViewHolder> {

    public PersonSearchAdapter(int layoutResId, @Nullable List<PersonInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonInfo item) {
        int position = helper.getLayoutPosition();
        if (position == 0 || !mData.get(position - 1).getFirstLetter().equals(item.getFirstLetter())) {
            helper.setText(R.id.letter, item.getFirstLetter());
            helper.setVisible(R.id.letter, true);
        } else {
            helper.setVisible(R.id.letter, false);
        }
        helper.setText(R.id.name, item.getName());
    }
}
