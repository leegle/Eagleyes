package com.egova.baselibrary.model;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * 省市区数据
 */
public class RegionInfo implements Serializable {
    private String id; //区域id
    private String name; //区域名称
    private String parentId; //父id
    private String regionLevel; //级别
    private String sort;
    private String phonetic;
    private String parentName;
    private ArrayList<RegionInfo> children;
    private String firstLetter; //拼音第一个字母

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(String regionLevel) {
        this.regionLevel = regionLevel;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public ArrayList<RegionInfo> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<RegionInfo> children) {
        this.children = children;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
