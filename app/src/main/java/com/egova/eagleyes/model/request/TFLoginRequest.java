package com.egova.eagleyes.model.request;

import java.io.Serializable;

public class TFLoginRequest implements Serializable {

    private String userName;
    //身份证号
    private String idCard;
    //证书序号 Certificate serial number
    private String certSn;
    //SIM卡ICCID
    private String iccid;
    //安全卡编号
    private String tfSn;
    //终端IMEI
    private String imei;
    //用户权限
    private String certOu;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCertSn() {
        return certSn;
    }

    public void setCertSn(String certSn) {
        this.certSn = certSn;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getTfSn() {
        return tfSn;
    }

    public void setTfSn(String tfSn) {
        this.tfSn = tfSn;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getCertOu() {
        return certOu;
    }

    public void setCertOu(String certOu) {
        this.certOu = certOu;
    }
}
