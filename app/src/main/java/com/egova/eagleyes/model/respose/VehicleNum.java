package com.egova.eagleyes.model.respose;

public class VehicleNum {
    private long bigVehicleNum;
    private long smallVehicleNum;
    private long totalVehicleNum;
    private long passFlow;
    private String captureNum = "0";

    public long getBigVehicleNum() {
        return bigVehicleNum;
    }

    public void setBigVehicleNum(long bigVehicleNum) {
        this.bigVehicleNum = bigVehicleNum;
    }

    public long getSmallVehicleNum() {
        return smallVehicleNum;
    }

    public void setSmallVehicleNum(long smallVehicleNum) {
        this.smallVehicleNum = smallVehicleNum;
    }

    public long getTotalVehicleNum() {
        return totalVehicleNum;
    }

    public void setTotalVehicleNum(long totalVehicleNum) {
        this.totalVehicleNum = totalVehicleNum;
    }

    public long getPassFlow() {
        return passFlow;
    }

    public void setPassFlow(long passFlow) {
        this.passFlow = passFlow;
    }

    public String getCaptureNum() {
        return captureNum;
    }

    public void setCaptureNum(String captureNum) {
        this.captureNum = captureNum;
    }
}
