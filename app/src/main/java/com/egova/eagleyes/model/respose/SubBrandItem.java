package com.egova.eagleyes.model.respose;

import com.egova.baselibrary.model.NameTextValue;

import java.io.Serializable;
import java.util.ArrayList;

public class SubBrandItem implements Serializable {
    private NameTextValue<String> subbrand;//子品牌名字
    private ArrayList<NameTextValue<String>> years;//子品牌的年份

    public NameTextValue<String> getSubbrand() {
        return subbrand;
    }

    public void setSubbrand(NameTextValue<String> subbrand) {
        this.subbrand = subbrand;
    }

    public ArrayList<NameTextValue<String>> getYears() {
        return years;
    }

    public void setYears(ArrayList<NameTextValue<String>> years) {
        this.years = years;
    }
}
