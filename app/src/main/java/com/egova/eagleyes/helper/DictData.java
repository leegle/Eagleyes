package com.egova.eagleyes.helper;


import com.egova.baselibrary.model.RegionInfo;
import com.egova.eagleyes.model.respose.Tollgate;
import com.egova.eagleyes.model.respose.TollgateType;

import java.util.List;

/**
 * 数据字典
 */
public class DictData {
    private DictData() {
    }

    private static DictData instance;

    public static DictData getInstance() {
        if (instance == null) {
            synchronized (DictData.class) {
                if (instance == null)
                    instance = new DictData();
            }
        }
        return instance;
    }

    //省市区字典数据
    private RegionInfo regionInfo;

    //卡口类型
    private List<TollgateType> tollgateTypesList;
    //卡口数据
    private List<Tollgate> tollgateList;

    public RegionInfo getRegionInfo() {
        return regionInfo;
    }

    public void setRegionInfo(RegionInfo regionInfo) {
        this.regionInfo = regionInfo;
    }

    public List<TollgateType> getTollgateTypesList() {
        return tollgateTypesList;
    }

    public void setTollgateTypesList(List<TollgateType> tollgateTypesList) {
        this.tollgateTypesList = tollgateTypesList;
    }

    public List<Tollgate> getTollgateList() {
        return tollgateList;
    }

    public void setTollgateList(List<Tollgate> tollgateList) {
        this.tollgateList = tollgateList;
    }
}
