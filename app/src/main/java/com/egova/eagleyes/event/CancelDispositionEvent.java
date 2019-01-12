package com.egova.eagleyes.event;

import com.egova.eagleyes.model.respose.DispositionInfo;

public class CancelDispositionEvent {
    public DispositionInfo info;

    public CancelDispositionEvent(DispositionInfo info) {
        this.info = info;
    }
}
