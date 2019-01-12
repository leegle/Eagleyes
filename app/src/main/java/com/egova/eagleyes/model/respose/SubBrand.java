package com.egova.eagleyes.model.respose;

import com.egova.baselibrary.model.NameTextValue;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 子品牌
 */
public class SubBrand implements Serializable {

    private NameTextValue<String> brand;//父品牌
    private ArrayList<SubBrandItem> subbrands;//子品牌

    public NameTextValue<String> getBrand() {
        return brand;
    }

    public void setBrand(NameTextValue<String> brand) {
        this.brand = brand;
    }

    public ArrayList<SubBrandItem> getSubbrands() {
        return subbrands;
    }

    public void setSubbrands(ArrayList<SubBrandItem> subbrands) {
        this.subbrands = subbrands;
    }
}
