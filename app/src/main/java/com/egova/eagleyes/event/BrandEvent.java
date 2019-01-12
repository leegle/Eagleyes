package com.egova.eagleyes.event;

import com.egova.baselibrary.model.NameTextValue;

public class BrandEvent {

    public static final String brandType="0";
    public static final String subBrandType="1";

    private String type;
    private NameTextValue<String>nameTextValue;

    public BrandEvent(String type,NameTextValue<String> nameTextValue) {
        this.type=type;
        this.nameTextValue = nameTextValue;
    }

    public NameTextValue<String> getNameTextValue() {
        return nameTextValue;
    }

    public void setNameTextValue(NameTextValue<String> nameTextValue) {
        this.nameTextValue = nameTextValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
