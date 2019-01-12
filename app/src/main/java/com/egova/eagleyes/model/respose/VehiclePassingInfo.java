package com.egova.eagleyes.model.respose;

import java.io.Serializable;

/**
 * 过车信息
 */
public class VehiclePassingInfo implements Serializable {

    private String vehiclePosition; //
    private String tollgateId; //卡口id
    private String id;
    private String regionId; //区域id
    private String regionName;
    private String cityId; //城市id
    private String cityName;
    private String vehicleColor; //车辆颜色代码
    private String communityId;
    private String tollgateType; //卡口类型
    private String dataCompanyId;
    private PlateInfo plate;
    private String bearing;
    private String debut;
    private int speed;
    private String time; //"2018-12-04 14:28:29",
    private String url; //图片地址
    private String year;
    private String month;
    private String day;
    private String hour;
    private String week;
    private String species;
    private String brand;
    private String childBrand;
    private String manufacturer;
    private String brandYear;
    private int reliability;
    private String danger;
    private String dangerScore;
    private String phone;
    private int phoneScore;
    private String slag;
    private int slagScore;
    private String belt;
    private int beltScore;
    private int tag;
    private int paper;
    private String drop;
    private String sun;
    private String rack;
    private String rackScore;
    private String sunroof;
    private int sunroofScore;
    private String feature;
    private String score;
    private String tollgateName;
    private String tollgateTypeName;
//    private Tollgate tollgate;
    private String vehicleColorName; //车辆颜色 "绿色",
    private String bearingName;//"车头",
    private String speciesName;//"轿车",
    private String manufacturerName; //"一汽大众",
    private String brandYearName; //"大众-捷达-2013&2015(舒适型)",
    private String dangerName;
    private String phoneName;
    private String slagName;
    private String beltName;
    private String dropName;
    private String sunName;
    private String rackName;
    private String sunroofName;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getVehiclePosition() {
        return vehiclePosition;
    }

    public void setVehiclePosition(String vehiclePosition) {
        this.vehiclePosition = vehiclePosition;
    }

    public String getTollgateId() {
        return tollgateId;
    }

    public void setTollgateId(String tollgateId) {
        this.tollgateId = tollgateId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getTollgateType() {
        return tollgateType;
    }

    public void setTollgateType(String tollgateType) {
        this.tollgateType = tollgateType;
    }

    public String getDataCompanyId() {
        return dataCompanyId;
    }

    public void setDataCompanyId(String dataCompanyId) {
        this.dataCompanyId = dataCompanyId;
    }

    public PlateInfo getPlate() {
        return plate;
    }

    public void setPlate(PlateInfo plate) {
        this.plate = plate;
    }

    public String getBearing() {
        return bearing;
    }

    public void setBearing(String bearing) {
        this.bearing = bearing;
    }

    public String getDebut() {
        return debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBrandYear() {
        return brandYear;
    }

    public void setBrandYear(String brandYear) {
        this.brandYear = brandYear;
    }

    public int getReliability() {
        return reliability;
    }

    public void setReliability(int reliability) {
        this.reliability = reliability;
    }

    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
    }

    public String getDangerScore() {
        return dangerScore;
    }

    public void setDangerScore(String dangerScore) {
        this.dangerScore = dangerScore;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPhoneScore() {
        return phoneScore;
    }

    public void setPhoneScore(int phoneScore) {
        this.phoneScore = phoneScore;
    }

    public String getSlag() {
        return slag;
    }

    public void setSlag(String slag) {
        this.slag = slag;
    }

    public int getSlagScore() {
        return slagScore;
    }

    public void setSlagScore(int slagScore) {
        this.slagScore = slagScore;
    }

    public String getBelt() {
        return belt;
    }

    public void setBelt(String belt) {
        this.belt = belt;
    }

    public int getBeltScore() {
        return beltScore;
    }

    public void setBeltScore(int beltScore) {
        this.beltScore = beltScore;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getPaper() {
        return paper;
    }

    public void setPaper(int paper) {
        this.paper = paper;
    }

    public String getDrop() {
        return drop;
    }

    public void setDrop(String drop) {
        this.drop = drop;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public String getRackScore() {
        return rackScore;
    }

    public void setRackScore(String rackScore) {
        this.rackScore = rackScore;
    }

    public String getSunroof() {
        return sunroof;
    }

    public void setSunroof(String sunroof) {
        this.sunroof = sunroof;
    }

    public int getSunroofScore() {
        return sunroofScore;
    }

    public void setSunroofScore(int sunroofScore) {
        this.sunroofScore = sunroofScore;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTollgateName() {
        return tollgateName;
    }

    public void setTollgateName(String tollgateName) {
        this.tollgateName = tollgateName;
    }

    public String getTollgateTypeName() {
        return tollgateTypeName;
    }

    public void setTollgateTypeName(String tollgateTypeName) {
        this.tollgateTypeName = tollgateTypeName;
    }

    public String getVehicleColorName() {
        return vehicleColorName;
    }

    public void setVehicleColorName(String vehicleColorName) {
        this.vehicleColorName = vehicleColorName;
    }

    public String getBearingName() {
        return bearingName;
    }

    public void setBearingName(String bearingName) {
        this.bearingName = bearingName;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getBrandYearName() {
        return brandYearName;
    }

    public void setBrandYearName(String brandYearName) {
        this.brandYearName = brandYearName;
    }

    public String getDangerName() {
        return dangerName;
    }

    public void setDangerName(String dangerName) {
        this.dangerName = dangerName;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getSlagName() {
        return slagName;
    }

    public void setSlagName(String slagName) {
        this.slagName = slagName;
    }

    public String getBeltName() {
        return beltName;
    }

    public void setBeltName(String beltName) {
        this.beltName = beltName;
    }

    public String getDropName() {
        return dropName;
    }

    public void setDropName(String dropName) {
        this.dropName = dropName;
    }

    public String getSunName() {
        return sunName;
    }

    public void setSunName(String sunName) {
        this.sunName = sunName;
    }

    public String getRackName() {
        return rackName;
    }

    public void setRackName(String rackName) {
        this.rackName = rackName;
    }

    public String getSunroofName() {
        return sunroofName;
    }

    public void setSunroofName(String sunroofName) {
        this.sunroofName = sunroofName;
    }
}
