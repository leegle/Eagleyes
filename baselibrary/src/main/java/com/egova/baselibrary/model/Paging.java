package com.egova.baselibrary.model;

import com.egova.baselibrary.util.CommonConstants;

import java.io.Serializable;

public class Paging implements Serializable {
    // 页码
    private int pageIndex;
    // 每页显示多少条
    private int pageSize=CommonConstants.PAGE_SIZE;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
