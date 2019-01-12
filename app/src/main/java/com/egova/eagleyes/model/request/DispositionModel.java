package com.egova.eagleyes.model.request;

import com.egova.eagleyes.model.respose.DispositionInfo;
import com.egova.eagleyes.model.respose.PlateInfo;

import java.util.List;

public class DispositionModel {
    private DispositionInfo disposition;//布控信息
    private List<PlateInfo> plates;//车牌
    private List<DispositionRecipient> recipients;//报警接收人
    private List<DispositionArea> areas; //布控区域

    public DispositionInfo getDisposition() {
        return disposition;
    }

    public void setDisposition(DispositionInfo disposition) {
        this.disposition = disposition;
    }

    public List<DispositionRecipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<DispositionRecipient> recipients) {
        this.recipients = recipients;
    }

    public List<PlateInfo> getPlates() {
        return plates;
    }

    public void setPlates(List<PlateInfo> plates) {
        this.plates = plates;
    }

    public List<DispositionArea> getAreas() {
        return areas;
    }

    public void setAreas(List<DispositionArea> areas) {
        this.areas = areas;
    }
}
