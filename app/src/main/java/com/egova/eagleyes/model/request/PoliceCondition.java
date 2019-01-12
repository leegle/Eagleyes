package com.egova.eagleyes.model.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 报警管理
 */
public class PoliceCondition implements Serializable {

    private String startTimestamp; //开始报警时间
    private String endTimestamp;//结束报警时间
    private String plate;//车牌号码
    private ArrayList<String> tollgateId;//卡口id
    private String handled;//处理状态

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

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public ArrayList<String> getTollgateId() {
        return tollgateId;
    }

    public void setTollgateId(ArrayList<String> tollgateId) {
        this.tollgateId = tollgateId;
    }

    public String getHandled() {
        return handled;
    }

    public void setHandled(String handled) {
        this.handled = handled;
    }
}
