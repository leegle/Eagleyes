package com.egova.eagleyes.model.respose;

import android.text.TextUtils;

import com.egova.baselibrary.widget.wavesidebar.FirstLetterUtil;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 卡口
 */
public class Tollgate implements Serializable {
    private String id;//530602000057  ,
    private String name;//昭阳区珠泉路上高速入口处卡口2  ,
    private String tollgateCode;//530602000057  ,
    private String tollgateType;//1  ,
    private String roadId;//所属道路
    private String laneNumber;// 车道数
    private String departmentId;// 所属单位,
    private String longitude;// 103.412460327148,
    private String latitude;// 27.1991806030273,
    private String direction;// null,
    private String regionId;//区域代码  ,
    private String buildDate;// null,
    private String manufacturer;// null,
    private String projectCode;// null,
    private String phonetic;// null,
    private String disabled;// false,
    private String panoramaUrl;// null,
    private String foregroundUrl;// null,
    private String airscapeUrl;// null,
    private String status;//Test  ,
    private String borderType;// null,
    private String tollgateTypeName;//公路卡口  ,
    private String roadName;//  ,
    private String departmentName;//  ,
    private String directionName;// null,
    private String manufacturerName;// null,
    private String projectName;// null,
    private String statusName;//测试设备  ,
    private String BorderTypeName;// null

    private String firstLetter;

    //本地辅助字段，判断是否被选择--报警管理
    @Expose(serialize = false)
    private boolean hasPoliceAlertSelected = true;


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

    public String getTollgateCode() {
        return tollgateCode;
    }

    public void setTollgateCode(String tollgateCode) {
        this.tollgateCode = tollgateCode;
    }

    public String getTollgateType() {
        return tollgateType;
    }

    public void setTollgateType(String tollgateType) {
        this.tollgateType = tollgateType;
    }

    public String getRoadId() {
        return roadId;
    }

    public void setRoadId(String roadId) {
        this.roadId = roadId;
    }

    public String getLaneNumber() {
        return laneNumber;
    }

    public void setLaneNumber(String laneNumber) {
        this.laneNumber = laneNumber;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getPanoramaUrl() {
        return panoramaUrl;
    }

    public void setPanoramaUrl(String panoramaUrl) {
        this.panoramaUrl = panoramaUrl;
    }

    public String getForegroundUrl() {
        return foregroundUrl;
    }

    public void setForegroundUrl(String foregroundUrl) {
        this.foregroundUrl = foregroundUrl;
    }

    public String getAirscapeUrl() {
        return airscapeUrl;
    }

    public void setAirscapeUrl(String airscapeUrl) {
        this.airscapeUrl = airscapeUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBorderType() {
        return borderType;
    }

    public void setBorderType(String borderType) {
        this.borderType = borderType;
    }

    public String getTollgateTypeName() {
        return tollgateTypeName;
    }

    public void setTollgateTypeName(String tollgateTypeName) {
        this.tollgateTypeName = tollgateTypeName;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getBorderTypeName() {
        return BorderTypeName;
    }

    public void setBorderTypeName(String borderTypeName) {
        BorderTypeName = borderTypeName;
    }

    public boolean isHasPoliceAlertSelected() {
        return hasPoliceAlertSelected;
    }

    public void setHasPoliceAlertSelected(boolean hasPoliceAlertSelected) {
        this.hasPoliceAlertSelected = hasPoliceAlertSelected;
    }

    /**
     * 拼音首字母
     *
     * @return
     */
    public String getFirstLetter() {
        if (TextUtils.isEmpty(firstLetter)) {
            firstLetter = FirstLetterUtil.getFirstLetter(name);
        }
        return firstLetter;
    }
}
