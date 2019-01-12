package com.egova.eagleyes.model.respose;

import android.text.TextUtils;

import com.egova.baselibrary.widget.wavesidebar.FirstLetterUtil;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 警员信息
 */
public class PersonInfo implements Serializable {
    private String id;
    //("人员编号")
    private String personNo;
    //("与人员关联的设备编号")
    private String deviceNo;
    //("人员名称")
    private String name;
    //("人员性别")
    private String sex;
    private String sexName;
    //("所属单位")
    private String departmentId;
    private String departmentName;
    //("职位类型")
    private String positionType;
    private String positionName;
    //("直属领导")
    private String parentId;
    private String parentName;
    private String birthday;
    //("相片")
    private String photo;
    //("联系电话")
    private String phone;
    //("系统帐号")
    private String userName;
    //("拼音")
    private String phonetic;
    //("人员简介")
    private String introduce;
    //("创建人")
    private String creator;
    //("修改人")
    private String modifier;
    //("创建时间")
    private String createTime;
    //("修改时间")
    private String modifyTime;
    //("启用")
    private Boolean disabled;
    //("经度")
    private Double longitude;
    //("纬度")
    private Double latitude;
    //("身份证号")
    private String identityNo;
    //("状态")
    private Integer status;
    //("活跃时间")
    private String activeTime;

    //本地辅助字段，判断是否被选择--我要布控
    @Expose(serialize = false)
    private Boolean hasSelected;

    private String firstLetter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
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

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public Boolean getHasSelected() {
        return hasSelected;
    }

    public void setHasSelected(Boolean hasSelected) {
        this.hasSelected = hasSelected;
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
