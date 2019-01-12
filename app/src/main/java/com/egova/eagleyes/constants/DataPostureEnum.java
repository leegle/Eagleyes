package com.egova.eagleyes.constants;

public class DataPostureEnum {
    //总过车量、今日过车量、最近30天初次入城
    public enum passFlow {
        totalPassNum, todayNum, firstPassNum
    }

    //早晚高峰
    public enum vehiclePeak {
        morning("1"), afternoon("2");
        public String code;

        vehiclePeak(String code) {
            this.code = code;
        }
    }

    //本地车，外地车
    public enum localVehicle {
        localNum, nonLocalNum
    }

    //设备在线状态
    public enum deviceStatus {
        totalNum, onlineNum
    }
    //判研结果，总数，假牌，套牌
    public enum dataResult{
        totalNum,fakeNum,similarNum
    }
}
