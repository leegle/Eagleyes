package com.egova.baselibrary.model;

import java.io.Serializable;

public class Sorting implements Serializable {

    public static enum SortingMode {

        Ascending,

        Descending,
    }

    private SortingMode mode;
    private String[] fields;


    public Sorting() {
    }

    public Sorting(String... fields) {
        this(SortingMode.Ascending, fields);
    }

    public Sorting(SortingMode mode, String... fields) {
        if (fields == null || fields.length == 0) {
            throw new IllegalArgumentException("fields");
        }

        this.setMode(mode);
        this.fields = fields;
    }

    public SortingMode getMode() {
        return mode;
    }

    public void setMode(SortingMode mode) {
        this.mode = mode;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }
}
