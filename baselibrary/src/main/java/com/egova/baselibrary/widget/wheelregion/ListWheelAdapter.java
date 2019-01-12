package com.egova.baselibrary.widget.wheelregion;

import com.egova.baselibrary.widget.wheeltime.WheelAdapter;

import java.util.List;

public class ListWheelAdapter<T extends List> implements WheelAdapter {
    /**
     * The default items length
     */
    public static final int DEFAULT_LENGTH = -1;

    private T items;
    private int length;

    public ListWheelAdapter(T items, int length) {
        this.items = items;
        this.length = length;
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }

    @Override
    public String getItem(int index) {
        if (index >= 0 && index < items.size()) {
            if(items.get(index).toString().trim().length()>7){
                return items.get(index).toString().trim().substring(0,6).concat("...");
            }
            return items.get(index).toString().trim();
        }
        return null;
    }


    @Override
    public int getMaximumLength() {
        return items.size();
    }
}
