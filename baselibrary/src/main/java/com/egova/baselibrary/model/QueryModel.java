package com.egova.baselibrary.model;

import java.io.Serializable;
import java.util.Map;

public class QueryModel<T> implements Serializable {
    private T condition;
    private Paging paging;
    private Sorting sorting;
    private Map<String, Object> extras;

    public T getCondition() {
        return condition;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public Sorting getSorting() {
        return sorting;
    }

    public void setSorting(Sorting sorting) {
        this.sorting = sorting;
    }

    public Map<String, Object> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }
}
