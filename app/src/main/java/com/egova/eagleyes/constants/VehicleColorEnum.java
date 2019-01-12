package com.egova.eagleyes.constants;

/**
 * 车辆颜色Enum
 */
public enum VehicleColorEnum {
    VehicleColor00("00", "不限", 0xffffffff),
    VehicleColor01("A", "白色", 0xffffffff),
    VehicleColor02("B", "灰色", 0xff9E9E9E),
    VehicleColor03("C", "黄色", 0xffFFCC00),
    VehicleColor04("D", "粉色", 0xffFC5BAB),
    VehicleColor05("E", "红色", 0xffFE4444),
    VehicleColor06("F", "紫色", 0xff7525BF),
    VehicleColor07("G", "绿色", 0xff19B53E),
    VehicleColor08("H", "蓝色", 0xff3C89FD),
    VehicleColor09("I", "棕色", 0xff7E6240),
    VehicleColor10("J", "黑色", 0xff000000),
    VehicleColor11("Z", "其他", 0xffBBCED6),;

    public String code;
    public String name;
    public int color;

    VehicleColorEnum(String code, String name, int color) {
        this.code = code;
        this.name = name;
        this.color = color;
    }
}
