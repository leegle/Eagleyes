package com.egova.baselibrary.model;

import android.text.TextUtils;

import com.egova.baselibrary.widget.wavesidebar.FirstLetterUtil;

import java.io.Serializable;

public class NameTextValue<T> implements Serializable {

    private String id;
    private String name;
    private String text;
    private T value;
    private String icon;
    private String tag;

    private String firstLetter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    /**
     * 拼音首字母
     *
     * @return
     */
    public String getFirstLetter() {
        if (TextUtils.isEmpty(firstLetter)) {
            firstLetter = FirstLetterUtil.getFirstLetter(text);
        }
        return firstLetter;
    }
}
