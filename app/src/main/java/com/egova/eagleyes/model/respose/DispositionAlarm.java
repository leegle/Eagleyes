package com.egova.eagleyes.model.respose;

import java.io.Serializable;

/**
 * 布控报警
 */
public class DispositionAlarm implements Serializable {
    //主键
    private String id;
    //布控ID
    private String dispositionId;
    //报警时间
    private String timestamp;
    //过车时间
    private String passTime;
    //采集时间
    private String collectTime;
    //卡口id
    private String tollgateId;
    //卡口名称
    private String tollgateName;
    //方向编号
    private String directionCode;
    private String directionName;
    //车道编号
    private String laneCode;
    //经度
    private Double longitude;
    //纬度
    private Double latitude;
    //大数据图片地址
    private String picture;
    //号牌号码
    private String plate;
    //号牌类型
    private String plateType;
    private String plateTypeName;
    //车辆品牌
    private String vehicleBrand;
    private String vehicleBrandName;
    //车身颜色
    private String vehicleColor;
    private String vehicleColorName;
    //报警等级
    private String alarmGrade;
    private String alarmGradeName;
    //报警处理状态
    private Boolean handled;
    //报警处理人
    private String handler;
    private String HandlerName;
    //处理时间
    private String handleTime;
    //布控人
    private String dispositionCreator;
    //布控创建时间
    private String dispositionTime;
    //布控原因
    private String cause;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDispositionId() {
        return dispositionId;
    }

    public void setDispositionId(String dispositionId) {
        this.dispositionId = dispositionId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public String getTollgateId() {
        return tollgateId;
    }

    public void setTollgateId(String tollgateId) {
        this.tollgateId = tollgateId;
    }

    public String getTollgateName() {
        return tollgateName;
    }

    public void setTollgateName(String tollgateName) {
        this.tollgateName = tollgateName;
    }

    public String getDirectionCode() {
        return directionCode;
    }

    public void setDirectionCode(String directionCode) {
        this.directionCode = directionCode;
    }

    public String getLaneCode() {
        return laneCode;
    }

    public void setLaneCode(String laneCode) {
        this.laneCode = laneCode;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getPlateType() {
        return plateType;
    }

    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getAlarmGrade() {
        return alarmGrade;
    }

    public void setAlarmGrade(String alarmGrade) {
        this.alarmGrade = alarmGrade;
    }

    public Boolean getHandled() {
        return handled;
    }

    public void setHandled(Boolean handled) {
        this.handled = handled;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getDispositionCreator() {
        return dispositionCreator;
    }

    public void setDispositionCreator(String dispositionCreator) {
        this.dispositionCreator = dispositionCreator;
    }

    public String getDispositionTime() {
        return dispositionTime;
    }

    public void setDispositionTime(String dispositionTime) {
        this.dispositionTime = dispositionTime;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public String getPlateTypeName() {
        return plateTypeName;
    }

    public void setPlateTypeName(String plateTypeName) {
        this.plateTypeName = plateTypeName;
    }

    public String getVehicleBrandName() {
        return vehicleBrandName;
    }

    public void setVehicleBrandName(String vehicleBrandName) {
        this.vehicleBrandName = vehicleBrandName;
    }

    public String getVehicleColorName() {
        return vehicleColorName;
    }

    public void setVehicleColorName(String vehicleColorName) {
        this.vehicleColorName = vehicleColorName;
    }

    public String getAlarmGradeName() {
        return alarmGradeName;
    }

    public void setAlarmGradeName(String alarmGradeName) {
        this.alarmGradeName = alarmGradeName;
    }

    public String getHandlerName() {
        return HandlerName;
    }

    public void setHandlerName(String handlerName) {
        HandlerName = handlerName;
    }
}
