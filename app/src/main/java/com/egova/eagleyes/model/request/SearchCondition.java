package com.egova.eagleyes.model.request;

import java.io.Serializable;
import java.util.Map;

/**
 * 全文搜索条件
 */
public class SearchCondition implements Serializable {
    private String iword;//搜索关键字，用空格分割
    private String startTimestamp;//起始时段
    private String endTimestamp;//结束时段
    private String regionID;;//区县区域id
    private String cityID;//市区域id
    private String tollgateId;//卡口id
    private String[] plateList;//车牌列表
    private String vehicleColor;//车辆颜色
    private String hmCode;//图片特征码


    public String getIword() {
        return iword;
    }

    public void setIword(String iword) {
        this.iword = iword;
    }

    public String getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(String startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(String endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public String getRegionID() {
        return regionID;
    }

    public void setRegionID(String regionID) {
        this.regionID = regionID;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getTollgateId() {
        return tollgateId;
    }

    public void setTollgateId(String tollgateId) {
        this.tollgateId = tollgateId;
    }

    public String[] getPlateList() {
        return plateList;
    }

    public void setPlateList(String[] plateList) {
        this.plateList = plateList;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getHmCode() {
        return hmCode;
    }

    public void setHmCode(String hmCode) {
        this.hmCode = hmCode;
    }

}
