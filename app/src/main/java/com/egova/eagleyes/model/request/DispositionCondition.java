package com.egova.eagleyes.model.request;

import java.io.Serializable;

import retrofit2.http.Body;

/**
 * 布控查询条件
 */
public class DispositionCondition implements Serializable {
    private Boolean disabled;//布控状态-是否启用
    private String plate;//车牌号码
    private String creator;//布控人
    private String startCreateTime;//布控开始日期
    private String endCreateTime;//布控结束日期

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(String startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }
}
