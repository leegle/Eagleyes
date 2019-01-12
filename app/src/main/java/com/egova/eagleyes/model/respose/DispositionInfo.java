package com.egova.eagleyes.model.respose;

import java.io.Serializable;

/**
 * 布控管理--布控信息
 */
public class DispositionInfo implements Serializable {
    //("主键")
    private String id;
    //("号牌号码")
    private String plate;
    //("号牌类型")
    private String plateType;
    private String plateTypeName;
    //("身份证号码")
    private String identityNo;
    //("车身颜色")
    private String vehicleColor;
    private String vehicleColorName;
    //("车辆品牌")
    private String brand;
    private String brandName;
    //("车辆子品牌")
    private String childBrand;
    private String childBrandName;
    //("车辆年款")
    private String brandYear;
    private String brandYearName;
    //("车辆种类")
    private String vehicleSpecies;
    private String vehicleSpeciesName;
    //("布控人")
    private String creator;
    //("创建时间")
    private String createTime;
    //("修改人")
    private String modifier;
    //("修改时间")
    private String modifyTime;
    //("布控开始日期")
    private String startDate;
    //("布控截止日期")
    private String endDate;
    //("报警开始时段")
    private String startAlarmPeriod;
    //("报警结束时段")
    private String endAlarmPeriod;
    //("布控原因")
    private String cause;
    //("撤控原因")
    private String revokeCause;
    //("是否失效")
    private Boolean disabled;
    //("布控类型")
    private String type;
    private String typeName;
    //("是否短信通知")
    private Boolean noticing;
    //("是否报过警")
    private Boolean beAlarmed;
    //("报警等级")
    private String alarmGrade;
    private String alarmGradeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getChildBrand() {
        return childBrand;
    }

    public void setChildBrand(String childBrand) {
        this.childBrand = childBrand;
    }

    public String getBrandYear() {
        return brandYear;
    }

    public void setBrandYear(String brandYear) {
        this.brandYear = brandYear;
    }

    public String getVehicleSpecies() {
        return vehicleSpecies;
    }

    public void setVehicleSpecies(String vehicleSpecies) {
        this.vehicleSpecies = vehicleSpecies;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPlateTypeName() {
        return plateTypeName;
    }

    public void setPlateTypeName(String plateTypeName) {
        this.plateTypeName = plateTypeName;
    }

    public String getVehicleColorName() {
        return vehicleColorName;
    }

    public void setVehicleColorName(String vehicleColorName) {
        this.vehicleColorName = vehicleColorName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getChildBrandName() {
        return childBrandName;
    }

    public void setChildBrandName(String childBrandName) {
        this.childBrandName = childBrandName;
    }

    public String getBrandYearName() {
        return brandYearName;
    }

    public void setBrandYearName(String brandYearName) {
        this.brandYearName = brandYearName;
    }

    public String getVehicleSpeciesName() {
        return vehicleSpeciesName;
    }

    public void setVehicleSpeciesName(String vehicleSpeciesName) {
        this.vehicleSpeciesName = vehicleSpeciesName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAlarmGradeName() {
        return alarmGradeName;
    }

    public void setAlarmGradeName(String alarmGradeName) {
        this.alarmGradeName = alarmGradeName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartAlarmPeriod() {
        return startAlarmPeriod;
    }

    public void setStartAlarmPeriod(String startAlarmPeriod) {
        this.startAlarmPeriod = startAlarmPeriod;
    }

    public String getEndAlarmPeriod() {
        return endAlarmPeriod;
    }

    public void setEndAlarmPeriod(String endAlarmPeriod) {
        this.endAlarmPeriod = endAlarmPeriod;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getRevokeCause() {
        return revokeCause;
    }

    public void setRevokeCause(String revokeCause) {
        this.revokeCause = revokeCause;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getNoticing() {
        return noticing;
    }

    public void setNoticing(Boolean noticing) {
        this.noticing = noticing;
    }

    public Boolean getBeAlarmed() {
        return beAlarmed;
    }

    public void setBeAlarmed(Boolean beAlarmed) {
        this.beAlarmed = beAlarmed;
    }

    public String getAlarmGrade() {
        return alarmGrade;
    }

    public void setAlarmGrade(String alarmGrade) {
        this.alarmGrade = alarmGrade;
    }
}
