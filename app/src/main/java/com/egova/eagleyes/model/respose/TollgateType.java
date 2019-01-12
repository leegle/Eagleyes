package com.egova.eagleyes.model.respose;

import com.google.gson.annotations.Expose;

import androidx.annotation.NonNull;

/**
 * 卡口类型
 */
public class TollgateType {
    private String id;
    private String text;
    private String name;
    private String value;
    private String icon;
    private String tag;

    //本地辅助字段，判断是否被选择--报警管理
    @Expose(serialize = false)
    private boolean hasPoliceAlertSelected =true;

    public boolean isHasPoliceAlertSelected() {
        return hasPoliceAlertSelected;
    }

    public void setHasPoliceAlertSelected(boolean hasPoliceAlertSelected) {
        this.hasPoliceAlertSelected = hasPoliceAlertSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @NonNull
    @Override
    public String toString() {
        return text;
    }
}
